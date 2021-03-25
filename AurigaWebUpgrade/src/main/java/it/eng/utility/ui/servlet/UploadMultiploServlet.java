package it.eng.utility.ui.servlet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.CheckPdfUtil;
import it.eng.utility.FormatConverterInterface;
import it.eng.utility.FormatiAmmessiUtil;
import it.eng.utility.module.config.ModuleConfig;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.impl.StorageServiceImpl;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.resolver.CustomMultipartProperty;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.servlet.utils.ServletUtils;

@Controller
@RequestMapping("/UploadMultiploServlet")
public class UploadMultiploServlet {

	private static Logger logger = Logger.getLogger(UploadServlet.class);
	private StorageService storageService;
	private Thread lThread;
	private TimeAdvance lTimeAdvance;
	public static Long maxSize;

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleIOException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
		StringBuffer lStringBuffer = new StringBuffer();
		lStringBuffer.append("<html>");
		lStringBuffer.append("<body>");
		lStringBuffer.append("<script type=\"text/javascript\">");
		lStringBuffer.append("</script>");
		lStringBuffer.append("</body>");
		lStringBuffer.append("</html>");
		return lStringBuffer.toString();
	}

	@RequestMapping(value = "/**", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> get(final HttpSession session, HttpServletRequest servletrequest, HttpServletResponse servletresponse) {
		logger.debug(servletrequest.getRequestURI());
		logger.debug(servletrequest.getQueryString());
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		return new ResponseEntity<String>("<html><h1>Hi!!!</h1></html>", responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("smartId") String smartId, @RequestParam("isExternalPortlet") boolean isExternalPortlet,
			@RequestParam("fileUploadAttr") Object fileUploadAttr, final HttpSession session, HttpServletRequest servletrequest,
			HttpServletResponse servletresponse) throws Exception {
		List<MultipartFile> files;
		if (fileUploadAttr instanceof List) {
			files = (List) fileUploadAttr;
		} else {
			files = new LinkedList<MultipartFile>();
			files.add((MultipartFile) fileUploadAttr);
		}

		return uploadFiles(smartId, isExternalPortlet, files, session, servletrequest, servletresponse);
	}

	private ResponseEntity<String> uploadFiles(@RequestParam("smartId") String smartId, @RequestParam("isExternalPortlet") boolean isExternalPortlet,
			@RequestParam("fileUploadAttr") List<MultipartFile> fileUploadAttr, final HttpSession session, HttpServletRequest servletrequest,
			HttpServletResponse servletresponse) throws Exception {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		StringBuffer lStringBuffer = new StringBuffer();

		//Metodo che verifica che lo smartID non sia stato manomesso ai fini di eseguire script malevoli
		/**VERIFICA DI VULNERABILITA*/
		if(!ServletUtils.findMatchSecurity(smartId)) {
			logger.error("ATTENZIONE!! Lo smart id ricevuto non corrisponde con quello atteso: possibile caso di script malevolo\n");
			logger.error("SmartId ricevuto: " + smartId); 
			if (lThread != null && lTimeAdvance != null) {
				lTimeAdvance.setEnd();
			}
			lStringBuffer.append("<html>");
			lStringBuffer.append("<body>");
			lStringBuffer.append("<script type=\"text/javascript\">");
			lStringBuffer.append("alert('ATTENZIONE!! Rilevata presenza di codice malevolo nei parametri inviati al servizio di upload del file')");
			lStringBuffer.append("</script>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		}
			
		try {
			session.setAttribute("semaforo", true);
			session.setAttribute("finito", false);
			int numFiles = fileUploadAttr.size();
			lTimeAdvance = new TimeAdvance(session, numFiles);
			lThread = new Thread(lTimeAdvance);
			lThread.start();
			lStringBuffer.append("<html>");
			lStringBuffer.append("<body>");

			if(maxSize == null) {
				try {
					CustomMultipartProperty lCustomMultipartProperty = (CustomMultipartProperty) SpringAppContext.getContext().getBean("CustomMultipartProperty");
					maxSize = lCustomMultipartProperty != null ? lCustomMultipartProperty.getMaxSize() : null;
				} catch(Exception e) {}
			}
			
			for (MultipartFile lMultipartFile : fileUploadAttr) {				
				if (maxSize != null && lMultipartFile.getSize() > maxSize) {
//					throw new Exception("Il file risulta eccedere la massima dimensione di " + maxSize + " bytes");
					String errorMessage = "Il file " + lMultipartFile.getOriginalFilename() + " risulta eccedere la massima dimensione di " + maxSize + " bytes";
					lStringBuffer.append("<script type=\"text/javascript\">");
					lStringBuffer.append("try {" + (isExternalPortlet ? "parent.document" : "window.top") + ".manageError_" + smartId + "('"
							+ StringEscapeUtils.escapeJava(errorMessage) + "'); } " + "catch(err) {for (var p in err) alert(err[p])}");
					lStringBuffer.append("</script>");
					continue;
				}				
				
				initStorageService();

				logger.debug(session.getAttribute("caricato"));
				String filename = lMultipartFile.getOriginalFilename().replaceAll("#", "");
				String lStrPath = storageService.storeStream(lMultipartFile.getInputStream());

				MimeTypeFirmaBean lMimeTypeFirmaBean = null;
				try {
					IdFileBean lIdFileBean = retrieveFileWithInfo(lStrPath, filename, session);
					lMimeTypeFirmaBean = lIdFileBean.getInfoFile();
					filename = lIdFileBean.getNomeFile();
					lStrPath = lIdFileBean.getUri();
				} catch (Exception e) {
					logger.warn(e);
					lTimeAdvance.setEnd();
					throw e;
				}

				lStringBuffer.append("<script type=\"text/javascript\">");
				lStringBuffer.append("try {" + (isExternalPortlet ? "parent.document" : "window.top") + ".changeNameFile_" + smartId + "('"
						+ StringEscapeUtils.escapeJavaScript(filename + "#" + lStrPath + "#" + fileUploadAttr.size()) + "'); } " + "catch(err) {alert('changeNameFile ' + err)}");
				GsonBuilder builder = GsonBuilderFactory.getIstance();
				Gson gson = builder.create();
				String result = gson.toJson(lMimeTypeFirmaBean);
				lStringBuffer.append("try {" + (isExternalPortlet ? "parent.document" : "window.top") + ".uploadInfo_" + smartId + "('" + result + "'); } "
						+ "catch(err) {alert('uploadInfo ' + err)}");
				lStringBuffer.append("</script>");
				lTimeAdvance.nextFile();
			}
			lTimeAdvance.setEnd();
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");
			session.removeAttribute("finito");
			session.removeAttribute("caricato");
			session.removeAttribute("numero");
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			if (lThread != null && lTimeAdvance != null) {
				lTimeAdvance.setEnd();
			}
			lStringBuffer.append("<html>");
			lStringBuffer.append("<body>");
			lStringBuffer.append("<script type=\"text/javascript\">");
			lStringBuffer.append("try {" + (isExternalPortlet ? "parent.document" : "window.top") + ".manageError_" + smartId + "('"
					+ StringEscapeUtils.escapeJava(e.getMessage()) + "'); } " + "catch(err) {for (var p in err) alert(err[p])}");
			lStringBuffer.append("</script>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		}
	}

	private void initStorageService() {
		storageService = StorageServiceImpl.newInstance(new GenericStorageInfo() {

			public String getUtilizzatoreStorageId() {
				ModuleConfig mc = (ModuleConfig) SpringAppContext.getContext().getBean("moduleConfig");
				logger.debug("Recuperato module config");
				if (mc != null && StringUtils.isNotBlank(mc.getIdDbStorage())) {
					logger.debug("Id Storage vale " + mc.getIdDbStorage());
					return mc.getIdDbStorage();
				} else {
					logger.error("L'identificativo del DB di storage non è correttamente configurato, " + "controllare il file di configurazione del modulo.");
					return null;
				}
			}
		});
	}

	private class TimeAdvance implements Runnable {

		private boolean end = false;
		private HttpSession session;
		private int porzione = 0;
		private int actualFile;
		private int value = 0;

		public TimeAdvance(HttpSession session, int numFiles) {
			this.session = session;
			porzione = 100 / numFiles;
			actualFile = 1;
			value = 0;
		}

		@Override
		public void run() {
			while (!end) {
				if (value <= actualFile * porzione) {
					value++;
					session.setAttribute("caricato", value);
				}
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {

					logger.warn(e);
				}
			}
			session.setAttribute("caricato", 95);
			session.setAttribute("finito", true);

		}

		public void setEnd() {
			end = true;
			session.setAttribute("finito", true);
			session.setAttribute("caricato", 1);
		}

		public void nextFile() {
			value = actualFile * porzione;
			actualFile++;

		}

	}

	private IdFileBean retrieveFileWithInfo(String lStrPath, String pStrDisplayFileName, HttpSession session) throws Exception {
		IdFileBean lIdFileBean = new IdFileBean();
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean.setDaScansione(false);
		try {
			String realFilePath = storageService.getRealFile(lStrPath).toURI().toString();
			// In caso di path modificato aggiustare realFilePath di conseguenza. es: realFilePath = realFilePath.replaceAll("/mnt/gpaFileStorage","//172.27.1.7/Repository");
			lMimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(realFilePath, pStrDisplayFileName, false, null);
		    
			String attivaConversionePdfNonLeggibiliDaItext = (String) session.getAttribute("ATTIVA_CONV_IMG_PDF_CORROTTI");
		    if(attivaConversionePdfNonLeggibiliDaItext != null && "true".equalsIgnoreCase(attivaConversionePdfNonLeggibiliDaItext)) {
		    	/*Controllo introdotto per gestire in fase di upload i pdf corrotti cioè non lavorabili da itext, li convertiamo 
		    	 * in immagine cosi riusciamo a fare tutte le operazioni di timbratura, unione ecc*/
//				if ("application/pdf".equalsIgnoreCase(lMimeTypeFirmaBean.getMimetype()) && !pStrDisplayFileName.toLowerCase().endsWith(".p7m") && !pStrDisplayFileName.toLowerCase().endsWith(".tsd") && !CheckPdfUtil.checkPdfReadItext(realFilePath)) {
				if (!lMimeTypeFirmaBean.isFirmato() && "application/pdf".equalsIgnoreCase(lMimeTypeFirmaBean.getMimetype()) && !pStrDisplayFileName.toLowerCase().endsWith(".tsd") && !CheckPdfUtil.checkPdfReadItext(realFilePath)) {
					try {
						IdFileBean idFileBean = CheckPdfUtil.managePdfCorrotti(realFilePath, pStrDisplayFileName,
								false, null, lMimeTypeFirmaBean);
						if (idFileBean.getInfoFile() != null && StringUtils.isNotBlank(idFileBean.getUri())) {
							lMimeTypeFirmaBean = idFileBean.getInfoFile();
							lStrPath = idFileBean.getUri();
						}
					} catch (Exception e) {
						throw new Exception("Il file pdf" + pStrDisplayFileName + " risulta avere una struttura corrotta e non può essere caricato. Per poterlo caricare si suggerisce di aprirlo con Acrobat e di farne una stampa pdf");
					}

				}
		    }
			
			
			String attivaGestPdfEditabiliInUpload = (String) session.getAttribute("ATTIVA_GEST_PDF_EDITABILI_IN_UPLOAD");
			if(attivaGestPdfEditabiliInUpload != null && "true".equalsIgnoreCase(attivaGestPdfEditabiliInUpload)) {
		    	/*Controllo introdotto per gestire in fase di upload i pdf editabili individuati da fileop*/
			    IdFileBean idFileBean = CheckPdfUtil.manageEditablePdf(realFilePath, pStrDisplayFileName, false, null, lMimeTypeFirmaBean);
			    if(idFileBean.getInfoFile()!=null && StringUtils.isNotBlank(idFileBean.getUri())) {
			    	lMimeTypeFirmaBean = idFileBean.getInfoFile();
			    	lStrPath = idFileBean.getUri();
			    }
		    }
			
			
			String attivaGestPdfConCommenti = (String) session.getAttribute("ATTIVA_GEST_PDF_CON_COMMENTI");
		    if(StringUtils.isNotBlank(attivaGestPdfConCommenti) && "true".equalsIgnoreCase(attivaGestPdfConCommenti)) {
		    	/*Controllo introdotto per gestire in fase di upload i pdf con commenti individuati da fileop*/
		    	IdFileBean idFileBean = CheckPdfUtil.managePdfConCommenti(realFilePath, pStrDisplayFileName, false, null, lMimeTypeFirmaBean);
		    	if(idFileBean.getInfoFile()!=null && StringUtils.isNotBlank(idFileBean.getUri())) {
		    		lMimeTypeFirmaBean = idFileBean.getInfoFile();
		    		lStrPath = idFileBean.getUri();
		    	}
		    }
			
			FormatiAmmessiUtil lFormatiAmmessiUtil = new FormatiAmmessiUtil();
			if (!lFormatiAmmessiUtil.isFormatiAmmessi(session, lMimeTypeFirmaBean)) {
				throw new Exception("il file " + pStrDisplayFileName + " risulta in un formato non riconosciuto o non ammesso");
			}
			lMimeTypeFirmaBean.setConvertibile(SpringAppContext.getContext().getBean(FormatConverterInterface.class)
					.isValidFormat(lMimeTypeFirmaBean.getMimetype(), session));
			lIdFileBean.setInfoFile(lMimeTypeFirmaBean);
			lIdFileBean.setNomeFile(lMimeTypeFirmaBean.getCorrectFileName());
			lIdFileBean.setUri(lStrPath);
		} catch (Exception e) {
			throw e;
		}
		return lIdFileBean;
	}

}
