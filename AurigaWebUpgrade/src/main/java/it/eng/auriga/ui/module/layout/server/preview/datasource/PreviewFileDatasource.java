package it.eng.auriga.ui.module.layout.server.preview.datasource;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.common.MergeDocument;
import it.eng.auriga.ui.module.layout.server.preview.datasource.bean.PreviewAttachmentEmailBean;
import it.eng.auriga.ui.module.layout.server.preview.datasource.bean.PreviewEmailBean;
import it.eng.auriga.ui.module.layout.server.preview.datasource.bean.PreviewFileBean;
import it.eng.auriga.ui.module.layout.server.preview.datasource.bean.PreviewFileOmissisBean;
import it.eng.auriga.ui.module.layout.server.preview.datasource.bean.PreviewFileRequestBean;
import it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean;
import it.eng.aurigamailbusiness.bean.SinteticMailAttachmentsBean;
import it.eng.client.MailProcessorService;
import it.eng.client.RecuperoFile;
import it.eng.core.business.FileUtil;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.CheckPdfUtil;
import it.eng.utility.ConvertToPdfUtil;
import it.eng.utility.FormatConverterInterface;
import it.eng.utility.RitagliPdfUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.html.HtmlNormalizeUtil;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;

@Datasource(id = "PreviewFileDatasource")
public class PreviewFileDatasource extends AbstractServiceDataSource<PreviewFileRequestBean, PreviewFileBean> {

	private static Logger mLogger = Logger.getLogger(PreviewFileDatasource.class);

	@Override
	public PreviewFileBean call(PreviewFileRequestBean pInBean) throws Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		PreviewFileBean lPreviewFileBean = new PreviewFileBean();
		// Se è pdf non faccio modifiche, lo porto fuori cosi com'è
		if (pInBean.getInfoFile().getMimetype().equals("application/pdf")) {
			if (pInBean.getInfoFile().isFirmato() && isFirmatoCades(pInBean.getInfoFile())) {
				InputStream lInputStream = null;
				if (!pInBean.isRemote()) {
					lInputStream = lInfoFileUtility.sbusta(StorageImplementation.getStorage().extractFile(pInBean.getUri()).toURI().toString(), "");
				} else {
					RecuperoFile lRecuperoFile = new RecuperoFile();
					FileExtractedIn lFileExtractedIn = new FileExtractedIn();
					lFileExtractedIn.setUri(pInBean.getUri());
					FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
					File fileTmp = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
					lInputStream = lInfoFileUtility.sbusta(fileTmp.toURI().toString(), "");
				}
				String uri = StorageImplementation.getStorage().storeStream(lInputStream);
				lPreviewFileBean.setMimetype(pInBean.getInfoFile().getMimetype());
				lPreviewFileBean.setRemote(false);
				lPreviewFileBean.setUri(uri);
				lPreviewFileBean.setNomeFile(pInBean.getNomeFile().substring(0, pInBean.getNomeFile().length() - 4));
				lPreviewFileBean.setRecordType(pInBean.getRecordType());
				MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
				if (lPreviewFileBean.getInfoFile() != null && lPreviewFileBean.getInfoFile().getBytes() != 0) {
					lMimeTypeFirmaBean.setBytes(lPreviewFileBean.getInfoFile().getBytes());
				}else {
					File fileConvertito = StorageImplementation.getStorage().extractFile(uri);
					lMimeTypeFirmaBean.setBytes(fileConvertito.length());
				}
				lPreviewFileBean.setInfoFile(lMimeTypeFirmaBean);
				
				/*CONTROLLO INTRODOTTO PER GESTIRE LA PREVIEW DEI PDF FIRMATI CADES CHE HANNO COMMENTI*/
//				if(StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_GEST_PDF_CON_COMMENTI")) &&
//						   "true".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_GEST_PDF_CON_COMMENTI"))) {
//					setPreviewPDfFirmatiConCommenti(uri, lPreviewFileBean);
//				}
				
			} else {
				lPreviewFileBean.setMimetype(pInBean.getInfoFile().getMimetype());
				lPreviewFileBean.setRemote(pInBean.isRemote());
				lPreviewFileBean.setUri(pInBean.getUri());
				lPreviewFileBean.setNomeFile(pInBean.getNomeFile());
				lPreviewFileBean.setRecordType(pInBean.getRecordType());
				
				/*CONTROLLO INTRODOTTO PER GESTIRE LA PREVIEW DEI PDF FIRMATI PADES CHE HANNO COMMENTI*/
//				if(StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_GEST_PDF_CON_COMMENTI")) &&
//						   "true".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_GEST_PDF_CON_COMMENTI")) &&
//						   pInBean.getInfoFile().isFirmato() ) {
//					setPreviewPDfFirmatiConCommenti(pInBean.getUri(), lPreviewFileBean);
//				}
			}
		} else if (pInBean.getInfoFile().getMimetype().startsWith("image") && !pInBean.getInfoFile().getMimetype().startsWith("image/tif")) {
			// Controllo la soglia per la preview
			int sogliaPreviewMB = ParametriDBUtil.getParametroDB(getSession(), "DIMENSIONE_MAX_FILE_PREVIEW") != null ? Integer.parseInt(ParametriDBUtil.getParametroDB(getSession(), "DIMENSIONE_MAX_FILE_PREVIEW")) : 0;
			long dimensioneFileMB = pInBean.getInfoFile().getBytes() / 1000000;
			if (sogliaPreviewMB == 0 || sogliaPreviewMB >= dimensioneFileMB) {
				String uri = null;
				String nomeFile = null;
				boolean remote;
				if (pInBean.getInfoFile().isFirmato() && isFirmatoCades(pInBean.getInfoFile())) {
					remote = false;
					InputStream lInputStream;
					if (!pInBean.isRemote()) {
						lInputStream = lInfoFileUtility.sbusta(StorageImplementation.getStorage().extractFile(pInBean.getUri()).toURI().toString(), "");
					} else {
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(pInBean.getUri());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
						File fileTmp = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
						lInputStream = lInfoFileUtility.sbusta(fileTmp.toURI().toString(), "");
					}
					uri = StorageImplementation.getStorage().storeStream(lInputStream);
					nomeFile = pInBean.getNomeFile().substring(0, pInBean.getNomeFile().length() - 4);
					
				} else {
					uri = pInBean.getUri();
					nomeFile = pInBean.getNomeFile();
					remote = pInBean.isRemote();
				}
				Dimension lDimension = getDimensionFromImage(pInBean.getInfoFile().getMimetype(), remote, uri);
				// int width = pInBean.getWidth() * 80 / 100;
				// int height = pInBean.getHeight() * 80 / 100;
				lPreviewFileBean.setWidth(new Double(lDimension.getWidth()).intValue());
				lPreviewFileBean.setHeight(new Double(lDimension.getHeight()).intValue());
				// Dimension lNewDimension = calculateDimension(width, height, lDimension);
				// lPreviewFileBean.setWidth(new Double(lNewDimension.getWidth()).intValue());
				// lPreviewFileBean.setHeight(new Double(lNewDimension.getHeight()).intValue());
				lPreviewFileBean.setMimetype(pInBean.getInfoFile().getMimetype());
				lPreviewFileBean.setRemote(remote);
				lPreviewFileBean.setUri(uri);
				lPreviewFileBean.setNomeFile(FilenameUtils.getBaseName(nomeFile) + ".pdf");				
				lPreviewFileBean.setRecordType(pInBean.getRecordType());
			} else {
				// Il file è troppo grande per essere mostrato nell'anteprima
				lPreviewFileBean.setFileDimensionExceedToImagePreview(true);
			}
		} else if (pInBean.getInfoFile().getMimetype().startsWith("image/tif")) {
			InputStream lInputStream = null;
			ByteArrayOutputStream lByteArrayOutputStream = null;
			ByteArrayInputStream lByteArrayInputStream = null;
			String nomeFile = null;
			try {
				if (!pInBean.isRemote()) {
					if (pInBean.getInfoFile().isFirmato() && isFirmatoCades(pInBean.getInfoFile())) {
						lInputStream = lInfoFileUtility.sbusta(StorageImplementation.getStorage().extractFile(pInBean.getUri()).toURI().toString(), "");
						nomeFile = pInBean.getNomeFile().substring(0, pInBean.getNomeFile().length() - 4);
					} else {
						lInputStream = StorageImplementation.getStorage().extract(pInBean.getUri());
						nomeFile = pInBean.getNomeFile();
					}
				} else {
					RecuperoFile lRecuperoFile = new RecuperoFile();
					FileExtractedIn lFileExtractedIn = new FileExtractedIn();
					lFileExtractedIn.setUri(pInBean.getUri());
					FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
					if (pInBean.getInfoFile().isFirmato() && isFirmatoCades(pInBean.getInfoFile())) {
						File fileTmp = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
						lInputStream = lInfoFileUtility.sbusta(fileTmp.toURI().toString(), "");
						nomeFile = pInBean.getNomeFile().substring(0, pInBean.getNomeFile().length() - 4);
					} else {
						lInputStream = new FileInputStream(out.getExtracted());
						nomeFile = pInBean.getNomeFile();
					}
				}
				Image lImage = Image.getInstance(IOUtils.toByteArray(lInputStream));
				Document document = new Document(new Rectangle(lImage.getWidth(), lImage.getHeight()), 0, 0, 0, 0);
				lByteArrayOutputStream = new ByteArrayOutputStream();
				PdfWriter writer = PdfWriter.getInstance(document, lByteArrayOutputStream);
				int pages = 0;
				document.open();
				PdfContentByte cb = writer.getDirectContent();
				document.add(lImage);
				// comps = TiffImage.getNumberOfPages(ra);
				mLogger.debug("Processing: ");
				// for (int c = 0; c < comps; ++c) {
				// try {
				// Image img = TiffImage.getTiffImage(ra, c+1);
				// if (img != null) {
				// img.scalePercent
				// (7200f / img.getDpiX(), 7200f / img.getDpiY());
				// document.setPageSize
				// (new Rectangle(img.getScaledWidth(), img.getScaledHeight()));
				// img.setAbsolutePosition(0, 0);
				// cb.addImage(img);
				// document.newPage();
				// ++pages;
				// }
				// }
				// catch (Throwable e) {
				// + (c + 1) + " " + e.getMessage());
				// }
				// }
				int comps = 0;
				document.close();
				lByteArrayInputStream = new ByteArrayInputStream(lByteArrayOutputStream.toByteArray());
				String uri = StorageImplementation.getStorage().storeStream(lByteArrayInputStream);
				// Verifico se il file è troppo grande per essere mostrato nell'anteprima
				File storedFile = StorageImplementation.getStorage().getRealFile(uri);
				int sogliaPreviewMB = ParametriDBUtil.getParametroDB(getSession(), "DIMENSIONE_MAX_FILE_PREVIEW") != null ? Integer.parseInt(ParametriDBUtil.getParametroDB(getSession(), "DIMENSIONE_MAX_FILE_PREVIEW")) : 0;
				long dimensioneFileStoredMB = storedFile.length() / 1000000;
				if (sogliaPreviewMB == 0 || sogliaPreviewMB >= dimensioneFileStoredMB) {
					lPreviewFileBean.setUri(uri);
					lPreviewFileBean.setNomeFile(FilenameUtils.getBaseName(nomeFile) + ".pdf");				
					lPreviewFileBean.setRecordType(pInBean.getRecordType());
					lPreviewFileBean.setMimetype("application/pdf");
				} else {
					// Il file è troppo grande per essere mostrato nell'anteprima
					lPreviewFileBean.setFileDimensionExceedToImagePreview(true);
				}
			} catch (Throwable e) {
				lPreviewFileBean.setMimetype("notValid");
			} finally {			
				if(lByteArrayInputStream != null) {
					try {
						lByteArrayInputStream.close(); 
					} catch (Exception e) {}
				}
				if(lByteArrayOutputStream != null) {
					try {
						lByteArrayOutputStream.close(); 
					} catch (Exception e) {}
				}
				if(lInputStream != null) {
					try {
						lInputStream.close(); 
					} catch (Exception e) {}
				}
			}
		} else {
			if (pInBean.getInfoFile().isConvertibile()) {
				String fileUrl = null;
				File file = null;
				if (!pInBean.isRemote()) {
					file =  StorageImplementation.getStorage().getRealFile(pInBean.getUri());
					fileUrl = file.toURI().toString();
				} else {
					RecuperoFile lRecuperoFile = new RecuperoFile();
					FileExtractedIn lFileExtractedIn = new FileExtractedIn();
					lFileExtractedIn.setUri(pInBean.getUri());
					FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
					file = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
					fileUrl = file.toURI().toString();
				}
				// Verifico che la dimensione del file non sia troppo grande per la conversione
				int sogliaConversioneMB = ParametriDBUtil.getParametroDB(getSession(), "DIMENSIONE_MAX_FILE_CONVERSIONE") != null ? Integer.parseInt(ParametriDBUtil.getParametroDB(getSession(), "DIMENSIONE_MAX_FILE_CONVERSIONE")) : 0;
				long dimensioneFileMB = file.length() / 1000000;
				if (sogliaConversioneMB == 0 || sogliaConversioneMB >= dimensioneFileMB) {
					try {
						String nomeFile = pInBean.getInfoFile().getCorrectFileName() != null ? pInBean.getInfoFile().getCorrectFileName() : "";
						if (pInBean.getInfoFile().isFirmato() && isFirmatoCades(pInBean.getInfoFile())) {
							nomeFile = nomeFile.substring(0, pInBean.getNomeFile().length() - 4);
						}
						String uriPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileUrl, nomeFile));
						File fileConvertito = StorageImplementation.getStorage().extractFile(uriPdf);
						MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
						lMimeTypeFirmaBean.setBytes(fileConvertito.length());
						lPreviewFileBean.setUri(uriPdf);
						lPreviewFileBean.setNomeFile(FilenameUtils.getBaseName(nomeFile) + ".pdf");				
						lPreviewFileBean.setRemote(false);
						lPreviewFileBean.setRecordType(pInBean.getRecordType());
						lPreviewFileBean.setMimetype("application/pdf");
						lPreviewFileBean.setInfoFile(lMimeTypeFirmaBean);						
					} catch (Exception e) {
						mLogger.error("Errore " + e.getMessage(), e);
						lPreviewFileBean.setMimetype("notValid");						
					}
				} else {
					// Il file è troppo grande per essere convertito
					lPreviewFileBean.setFileDimensionExceedToImagePreview(true);					
				}
			} else {
				lPreviewFileBean.setMimetype("notValid");
			}
		}
		return lPreviewFileBean;
	}

	private void setPreviewPDfFirmatiConCommenti(String uri, PreviewFileBean lPreviewFileBean) {
		try {
			String realPathFile = StorageImplementation.getStorage().extractFile(uri).getPath();
			
			List<Integer> listaPagineConCommenti = CheckPdfUtil.returnPageWithCommentBox(realPathFile);
			
			if(listaPagineConCommenti.size()>0) {
				MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
				lMimeTypeFirmaBean.setMimetype("application/pdfc");
				
				IdFileBean idFileBean = CheckPdfUtil.managePdfConCommenti(realPathFile, lPreviewFileBean.getNomeFile(), false, null, lMimeTypeFirmaBean);
				
				if (idFileBean.getInfoFile() != null && StringUtils.isNotBlank(idFileBean.getUri())) {
					MimeTypeFirmaBean lMimeTypeStaticizzatoBean = idFileBean.getInfoFile();
					String uriFileStaticizzato = idFileBean.getUri();
					
					lPreviewFileBean.setUri(uriFileStaticizzato);
					lPreviewFileBean.setInfoFile(lMimeTypeStaticizzatoBean);
					lPreviewFileBean.setMimetype(lMimeTypeStaticizzatoBean.getMimetype());
				}
			}
			
			
		} catch (Exception e) {
			mLogger.error("Errore durante il check del file firmato con commenti: " + e.getMessage(), e);
		}
		
	}

	private boolean isFirmatoCades(MimeTypeFirmaBean infoFile) {
		if (FilenameUtils.getExtension(infoFile.getCorrectFileName()).equalsIgnoreCase("p7m") ||
				FilenameUtils.getExtension(infoFile.getCorrectFileName()).equalsIgnoreCase("tsd") ||
				FilenameUtils.getExtension(infoFile.getCorrectFileName()).equalsIgnoreCase("m7m")) {
			return true;
		}
		return false;
	}

	private Dimension calculateDimension(int width, int height, Dimension lDimension) {
		double imageWidth = lDimension.getWidth();
		double imageHeight = lDimension.getHeight();
		if (imageWidth < width && imageHeight < height)
			return lDimension;
		double widthProp = imageWidth / new Double(width);
		double heightProp = imageHeight / new Double(height);
		if (widthProp >= heightProp) {
			double resultWidth = imageWidth / widthProp;
			double resultHeight = imageHeight / widthProp;
			return new Dimension(new Double(resultWidth).intValue(), new Double(resultHeight).intValue());
		} else {
			double resultWidth = imageWidth / heightProp;
			double resultHeight = imageHeight / heightProp;
			return new Dimension(new Double(resultWidth).intValue(), new Double(resultHeight).intValue());
		}
	}

	private Dimension getDimensionFromImage(String mimetype, boolean remote, String uri) throws StorageException {
		Iterator<ImageReader> iter = ImageIO.getImageReadersByMIMEType(mimetype);
		if (iter.hasNext()) {
			ImageReader reader = iter.next();
			ImageInputStream stream = null;
			try {
				if (!remote) {
					stream = new FileImageInputStream(StorageImplementation.getStorage().getRealFile(uri));
				} else {
					RecuperoFile lRecuperoFile = new RecuperoFile();
					FileExtractedIn lFileExtractedIn = new FileExtractedIn();
					lFileExtractedIn.setUri(uri);
					try {
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
						stream = new FileImageInputStream(out.getExtracted());
					} catch (Exception e) {
						mLogger.warn(e);
					}

				}
				reader.setInput(stream);
				int width = reader.getWidth(reader.getMinIndex());
				int height = reader.getHeight(reader.getMinIndex());
				return new Dimension(width, height);
			} catch (IOException e) {
				mLogger.error("Error reading: " + e.getMessage(), e);
			} finally {
				reader.dispose();
				if(stream != null) {
					try {
						stream.close();
					} catch (Exception e) {}
				}
			}
		}
		return null;
	}

	public PreviewEmailBean recuperaDatiEmail(SimpleKeyValueBean pSimpleKeyValueBean) throws StoreException {
		String uri = pSimpleKeyValueBean.getValue();
		MailProcessorService lMailProcessorService = new MailProcessorService();
		SinteticEmailInfoBean lSinteticEmailInfoBean;
		try {
			// recupero info mail dal file eml in input
			lSinteticEmailInfoBean = lMailProcessorService.getsinteticmail(getLocale(), StorageImplementation.getStorage().extractFile(uri));
			PreviewEmailBean lPreviewEmailBean = new PreviewEmailBean();
			lPreviewEmailBean.setNomeFileEml(pSimpleKeyValueBean.getKey());
			lPreviewEmailBean.setUriFileEml(pSimpleKeyValueBean.getValue());
			lPreviewEmailBean.setDate(lSinteticEmailInfoBean.getDate());
			lPreviewEmailBean.setDestinataricc(buildStringLista(lSinteticEmailInfoBean.getDestinataricc()));
			lPreviewEmailBean.setDestinatari(buildStringLista(lSinteticEmailInfoBean.getDestinatario()));
			lPreviewEmailBean.setBody(lSinteticEmailInfoBean.getMessaggio());
			lPreviewEmailBean.setOggetto(lSinteticEmailInfoBean.getOggetto());
			lPreviewEmailBean.setMittente(lSinteticEmailInfoBean.getMittente());
			lPreviewEmailBean.setMessaggio(HtmlNormalizeUtil.getPlainXmlWithCarriageReturn(lSinteticEmailInfoBean.getMessaggio()));
			if (lSinteticEmailInfoBean.getAllegati() != null && !lSinteticEmailInfoBean.getAllegati().isEmpty()) {
				InfoFileUtility lInfoFileUtility = new InfoFileUtility();
				List<PreviewAttachmentEmailBean> lListAllegati = new ArrayList<PreviewAttachmentEmailBean>();
				for (SinteticMailAttachmentsBean lSinteticMailAttachmentsBean : lSinteticEmailInfoBean.getAllegati()) {
					PreviewAttachmentEmailBean lPreviewAttachmentEmailBean = new PreviewAttachmentEmailBean();
					String uriAllegato = StorageImplementation.getStorage().store(lSinteticMailAttachmentsBean.getFileAttach());
					lPreviewAttachmentEmailBean.setUri(uriAllegato);
					lPreviewAttachmentEmailBean.setNomeFile(lSinteticMailAttachmentsBean.getFilename());
					lPreviewAttachmentEmailBean.setDisplayFileName(lSinteticMailAttachmentsBean.getFilename());
					MimeTypeFirmaBean lMimeTypeFirmaBean = lInfoFileUtility.getInfoFromFile(
							StorageImplementation.getStorage().getRealFile(uriAllegato).toURI().toString(), lSinteticMailAttachmentsBean.getFilename(), false,
							lSinteticEmailInfoBean.getDate());
					lMimeTypeFirmaBean.setConvertibile(SpringAppContext.getContext().getBean(FormatConverterInterface.class)
							.isValidFormat(lMimeTypeFirmaBean.getMimetype(), getSession()));
					lPreviewAttachmentEmailBean.setInfoFile(lMimeTypeFirmaBean);
					lPreviewAttachmentEmailBean.setFlgFirmato(lMimeTypeFirmaBean.isFirmato());
					lListAllegati.add(lPreviewAttachmentEmailBean);
				}
				lPreviewEmailBean.setAllegati(lListAllegati);
			}
			return lPreviewEmailBean;
		} catch (Exception e) {
			mLogger.error("Errore nel recupero delle informazioni della mail " + e.getMessage(), e);
			throw new StoreException("Impossibile visualizzare la mail");
		}
	}
	
	public PreviewFileBean getVersioneRidottaFile(PreviewFileBean previewOriginale) throws StoreException {
		try {
			// Bean da restituire contenente la preview ridotta
			PreviewFileBean previewRidottaBean = new PreviewFileBean();
			// File originale
			File fileOriginale = StorageImplementation.getStorage().extractFile(previewOriginale.getUri());
			// File della preview ridotta
			File fileRidotto = File.createTempFile("anteprima_ridotta", ".pdf");
			FileOutputStream osFileRidotto = new FileOutputStream(fileRidotto);
			
		    // Copio le prime pagine del file originale per creare la versione ridotta
		    MergeDocument mergeDoc = new MergeDocument();
		    // Creo la versione ridotta dal file da visulizzare in preview, legato al parametro DIMENSIONE_MAX_FILE_PREVIEW
		    mergeDoc.getFirstPages(fileOriginale, osFileRidotto, 5);
		   
		    // Chiudo i flussi
		    osFileRidotto.flush();
	        osFileRidotto.close();
	        
	        // Consolido il file della preview
	        String uriFileRidotto = StorageImplementation.getStorage().store(fileRidotto);
	        
	        previewRidottaBean.setUri(uriFileRidotto);
	        previewRidottaBean.setNomeFile(previewOriginale.getNomeFile());
	        previewRidottaBean.setRemote(false);
	        previewRidottaBean.setRecordType(previewOriginale.getRecordType());
	        previewRidottaBean.setMimetype("application/pdf");
			return previewRidottaBean;
		} catch (Exception e) {
			mLogger.error(e.getMessage(), e);
			addMessage("Non è stato possibile creare una anteprima ridotta del file", "", MessageType.WARNING);
			return previewOriginale;
		}
		
	}
	
	/**
	 * Metodo per l'apposizione di peccette
	 * - se il file su cui si vuole applicare la peccetta non è un PDF viene prima convertito
	 * - si tenta l'eliminazione del testo sottostante la peccetta (vedi commento "FEDERICA BUONO 03/03/2020")
	 * - al termine di questo passaggio vengono eliminate le pagine selezionate con il metodo <b>rimuoviPagineDaPdf<b>
	 * 
	 * @param inputBean
	 * @return
	 * @throws Exception
	 */
	public PreviewFileOmissisBean creaFileConOmissis(PreviewFileOmissisBean inputBean) throws Exception {
		// File originale
		InputStream isReader = null;
		try {
			File fileOriginale = null;
			File fileRuotato = null;
			if (!FilenameUtils.getExtension(inputBean.getFileName()).equals("pdf")) {
				InputStream is = null;
				is = ConvertToPdfUtil.convertToPdf(inputBean.getUri(), false, false);
				
				if (is != null) {
					fileOriginale = File.createTempFile("pdf", ".pdf");
					FileUtil.writeStreamToFile(is, fileOriginale);
				} else {
					throw new Exception("Si è verificato un errore durante la conversione in pdf del file");
				}
			} else {
				fileOriginale = StorageImplementation.getStorage().extractFile(inputBean.getUri());
			}
			
			/**
			 *  FEDERICA BUONO 03/03/2020
			 * 
			 *  Per alcuni file l'apposizione delle pecette su TESTO non va a buon fine 
			 *  es: generati con ItextSharp del BUR
			 *  
			 *  Apponiamo le pecette su tutti i file, se proviamo a pulire del testo (e non immagini)
			 *  a. se attivo il parametro ATTIVA_CHECK_FILE_CORROTTI 
			 *  			i. se è attivo il parametro DB ATTIVA_CONV_IMG_PRE_PECCETTE, il file pdf viene convertito in immagine,  viene riconvertito in pdf,
			 *  				infine viene apposto il rettangolo sopra;
			 *  			ii. se NON è attivo il parametro DB ATTIVA_CONV_IMG_PRE_PECCETTE, viene rilanciata un'eccezione che avverte l'utente
			 *  				che non è stato possibile apporre le peccette.
			 *  b. se NON attivo il parametro ATTIVA_CHECK_FILE_CORROTTI o ASSENTE
			 *  			i. viene ritornato il file con apposta peccetta che però non ha cancellato il testo e quindi in effetti non serve a nulla.
			 *  
			 */
			
			File fileConRitagli = null;
			boolean ritagliNotBlank = StringUtils.isNotBlank(inputBean.getRitagli()) && !inputBean.getRitagli().equals("[]");
			boolean esitoOkPeccette = true;
			
			// Ruoto il pdf
			if (inputBean.getRotatePdf() != null && inputBean.getRotatePdf()>0) {
				fileRuotato = it.eng.utility.PdfUtility.rotatePdf(fileOriginale, inputBean.getRotatePdf());
			}
			
			// Se ci sono delle peccette da apporre provo ad apporle
			if (ritagliNotBlank) {
				
				fileConRitagli = RitagliPdfUtility.addRitagliAndClean(fileRuotato == null ? fileOriginale : fileRuotato, inputBean);
				
				// se almeno una peccetta è stata applicata su testo ed è attivo il controllo sui file corrotti
				if (inputBean.isTryToCleanText() && ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_CHECK_FILE_CORROTTI")) {
					
					if (RitagliPdfUtility.isCorruptedFile(fileRuotato == null ? fileOriginale : fileRuotato, fileConRitagli, inputBean)) {
						boolean isAttivaConvImg = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_CONV_IMG_PRE_PECCETTE");
						if (isAttivaConvImg) {
//							/*converto il file pdf in immagini*/
//							List<String> listaImagePath = RitagliPdfUtility.fromPdfToImage(fileOriginale);					
//							/*converto le immagini in pdf*/
//							File fileFromImage = PdfUtility.fromImageToPdf(listaImagePath, false);
							File fileFromImage = RitagliPdfUtility.rewriteDocument(fileRuotato == null ? fileOriginale : fileRuotato, inputBean.getRitagli());
							/*applico le peccette*/
							fileConRitagli = RitagliPdfUtility.addRitagliNotClean(fileFromImage, inputBean.getRitagli(), false);
						} else {
							esitoOkPeccette = false;
							fileConRitagli = null;
						}
					}
				}
			}

			isReader = new FileInputStream(fileRuotato == null ? fileOriginale : fileRuotato);
			PdfReader reader = new PdfReader(isReader);
			//il file generato dovrà essere un PDFA se quello di origine era un PDFA, altrimenti no.
			boolean isPDFA = reader.getMetadata() != null && (new String(reader.getMetadata())).contains("pdfa");
			PreviewFileOmissisBean fileConOmissis = null;
			if (fileRuotato == null ) {
				fileConOmissis = rimuoviPagineDaPdf(inputBean, fileConRitagli != null ? fileConRitagli : fileOriginale, isPDFA);
			} else {
				fileConOmissis = rimuoviPagineDaPdf(inputBean, fileConRitagli != null ? fileConRitagli : fileRuotato, isPDFA);
			}
			
			fileConOmissis.setEsitoPeccette(esitoOkPeccette ? "ok" : "ko");
			return fileConOmissis;

		} catch (Exception e) {
			mLogger.error("Errore durante la creazione del file omissis: " + e.getMessage(), e);
			throw new Exception("Errore durante la creazione del file omissis");
		} finally {
			if(isReader != null) {
				try {
					isReader.close(); 
				} catch (Exception e) {}
			}
		}
	}
	
	public PreviewFileOmissisBean rimuoviPagineDaPdf(PreviewFileOmissisBean inputBean, File fileConRitagli, boolean isPDFA) throws StoreException {
		try {
			// Bean da restituire contenente la preview ridotta
			String newFileName = inputBean.getFileName();
			if (newFileName != null && !newFileName.toUpperCase().endsWith(".PDF")){
				newFileName = newFileName.substring(0, newFileName.lastIndexOf(".")) + ".pdf";
			}
			PreviewFileOmissisBean previewRidottaBean = new PreviewFileOmissisBean();
			previewRidottaBean.setFileName(newFileName);
			// File con le pagine rimosse
			File fileconPagineRimosse = File.createTempFile("anteprima_ridotta", ".pdf");
			FileOutputStream osFileconPagineRimosse = new FileOutputStream(fileconPagineRimosse);
			// Creo l'indice delle pagine da rimuove
			int[] pagesToRemove = null;
			if (StringUtils.isNotBlank(inputBean.getPagesToRemove())) {
				String[] strPagesToRemove = inputBean.getPagesToRemove().split(",");
				pagesToRemove = new int[strPagesToRemove.length];
				for (int i = 0; i < strPagesToRemove.length; i++) {
					pagesToRemove[i] = Integer.parseInt(strPagesToRemove[i]);
				}
			}
			
		    MergeDocument mergeDoc = new MergeDocument();
		    mergeDoc.removePages(fileConRitagli, osFileconPagineRimosse, pagesToRemove, isPDFA);
		   
		    // Chiudo i flussi
		    osFileconPagineRimosse.flush();
		    osFileconPagineRimosse.close();
	        
	        // Consolido il file della preview
	        String uriFileRidotto = StorageImplementation.getStorage().store(fileconPagineRimosse);
	        
	        MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			InfoFileUtility lFileUtility = new InfoFileUtility();
			lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFileRidotto).toURI().toString(), inputBean.getFileName(), false, null);
	        
	        previewRidottaBean.setUri(uriFileRidotto);
	        previewRidottaBean.setRemote(false);
	        previewRidottaBean.setRecordType(inputBean.getRecordType());
	        previewRidottaBean.setMimetype("application/pdf");
	        previewRidottaBean.setInfoFile(lMimeTypeFirmaBean);
			return previewRidottaBean;
		} catch (Exception e) {
			mLogger.error(e.getMessage(), e);
			addMessage("Non è stato possibile creare la versione con omissis", "", MessageType.ERROR);
			return null;
		}
		
	}

	private String buildStringLista(List<String> destinataricc) {
		if (destinataricc == null)
			return "";
		if (destinataricc.size() == 0)
			return "";
		StringBuilder lStringBuilder = new StringBuilder();
		boolean first = true;
		for (String lString : destinataricc) {
			if (first) {
				first = false;
			} else
				lStringBuilder.append(";");
			lStringBuilder.append(lString);
		}
		return lStringBuilder.toString();
	}

}
