package it.eng.utility.ui.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSSignedData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.auriga.ui.module.layout.shared.bean.WSEndPointArubaHsmBean;
import it.eng.common.bean.SignerObjectBean;
import it.eng.common.type.SignerType;
import it.eng.hsm.TsrGenerator;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.sign.FileElaborate;
import it.eng.utility.ui.sign.SignerHashUtil;

@Controller
@RequestMapping("/UploadMultiHashSignerApplet")
public class UploadMultiHashSignerApplet {

	private static Logger logger = Logger.getLogger(UploadMultiHashSignerApplet.class);

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("idDoc") String idDoc, @RequestParam("signedBean") String signedBean,
			@RequestParam("firmatario") String firmatario, @RequestParam(value = "pathFileTemp", required = false) String pathFileTemp,
			@RequestParam("versioneDoc") String versioneDoc, @RequestParam(value = "firmaPresente", required = false) String firmaPresente,
			@RequestParam(value = "firmaValida", required = false) String firmaValida,
			@RequestParam(value = "modalitaFirma", required = false) String modalitaFirma, final HttpSession session, HttpServletRequest servletrequest,
			HttpServletResponse servletresponse) throws Exception {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		try {
			Cookie ck[] = servletrequest.getCookies();  
			if (ck != null){
				for(int i = 0; i < ck.length; i++){  
					logger.debug("Trovato cookie: " + ck[i].getName() + " " + ck[i].getValue());  
				}  
			} else {
				logger.debug("Nessuno cookies presente");
			}
			SignerObjectBean bean = (SignerObjectBean) SerializationUtils.deserialize(Base64.decodeBase64(signedBean));
			logger.debug("Recupero il file originale");
			String[] infoFile = idDoc.split("#");
			String idFile = infoFile[0];
			String uriFile = URLDecoder.decode(infoFile[1], "UTF-8");
			String fileNameOriginale = infoFile[2];
			File lFileFirmato = File.createTempFile("sign", "");
			logger.debug("Path lFileFirmato prima della marca: " + lFileFirmato.getPath());
			boolean isCades = true;
			if (bean.getFileType() != null && bean.getFileType() == SignerType.PDF) {
				File lFileDaFirmare = StorageImplementation.getStorage().extractFile(uriFile);
				FileElaborate fileElaborate = (FileElaborate) servletrequest.getSession().getServletContext().getAttribute("fileElaborate" + idFile);
				servletrequest.getSession().getServletContext().removeAttribute("fileElaborate" + idFile);
				fileElaborate.setUnsigned(lFileDaFirmare);
				fileElaborate.setSigned(lFileFirmato);
				SignerHashUtil.attachPdf(bean.getSignerInfo(), fileElaborate);
				isCades = false;
			} else {
				if (!fileNameOriginale.toLowerCase().endsWith(".p7m")) {
					fileNameOriginale = fileNameOriginale + ".p7m";
				}
				InputStream lInputStream = StorageImplementation.getStorage().extract(uriFile);
				FileOutputStream outputStream = FileUtils.openOutputStream(lFileFirmato);
				byte[] originalData = IOUtils.toByteArray(lInputStream);
				// caso firma parallela
				if (modalitaFirma != null && modalitaFirma.equalsIgnoreCase("congiunta")) {
					// il file era firmato in CAdES e con una firma CAdES valida -> parallela
					if ((firmaPresente != null && firmaPresente.equalsIgnoreCase("true")) && (firmaValida != null && firmaValida.equalsIgnoreCase("true"))) {
						CMSSignedData data = SignerHashUtil.createSignedData(bean, originalData);
						SignerHashUtil.addSignerInfo(originalData, data, outputStream);
					}
					// il file non era firmato in CAdES, o aveva una firma CAdES non valida -> verticale
					else {
						CMSSignedData data = SignerHashUtil.signerP7M(bean, originalData);
						ByteArrayInputStream bis = new ByteArrayInputStream(data.getEncoded());
						SignerHashUtil.attachP7M(originalData, bis, outputStream);
					}
				}
				// caso firma verticale
				else {
					CMSSignedData data = SignerHashUtil.signerP7M(bean, originalData);
					ByteArrayInputStream bis = new ByteArrayInputStream(data.getEncoded());
					SignerHashUtil.attachP7M(originalData, bis, outputStream);
				}
				outputStream.flush();
				outputStream.close();
				lInputStream.close();
			}
			String uriFileFirmato = null;
			String tipoMarca = null;
			try {
				WSEndPointArubaHsmBean wsEndPointArubaHsmBean = (WSEndPointArubaHsmBean) SpringAppContext.getContext().getBean("WSEndPointArubaHsm");
				if (wsEndPointArubaHsmBean != null && StringUtils.isNotBlank(wsEndPointArubaHsmBean.getTipoMarcaForzataPerApplet())) {
					if ("true".equalsIgnoreCase(wsEndPointArubaHsmBean.getTipoMarcaForzataPerApplet())){
						tipoMarca = "HSM";
					} else {
						tipoMarca = wsEndPointArubaHsmBean.getTipoMarcaForzataPerApplet();
					}
				}
			} catch(Exception e) {
				tipoMarca = null;
				logger.error("Errore nella lettura di WSEndPointArubaHsm: " + e.getMessage(), e);
			}
			
			boolean isFileDaMarcare = false;
			if(tipoMarca != null && (tipoMarca.equalsIgnoreCase("HSM") || tipoMarca.equalsIgnoreCase("SERVER"))){
				isFileDaMarcare = true;
			}
			
			if (isFileDaMarcare || bean.marca) {
				boolean useAuth = false;
				WSEndPointArubaHsmBean wsEndPointArubaHsmBean = (WSEndPointArubaHsmBean) SpringAppContext.getContext().getBean("WSEndPointArubaHsm");
				if(wsEndPointArubaHsmBean!=null && wsEndPointArubaHsmBean.getMarcaServiceUid()!=null && 
						!wsEndPointArubaHsmBean.getMarcaServiceUid().equalsIgnoreCase("")){
					useAuth = true;
				}
				TsrGenerator tsr = new TsrGenerator(wsEndPointArubaHsmBean.getMarcaServiceUrl(), wsEndPointArubaHsmBean.getMarcaServiceUid(),
						wsEndPointArubaHsmBean.getMarcaServicePwd(), useAuth);
				// ProxyUtil.initProxyConnection();
				
				byte[] byteFileMarcato = null;
				if( isCades ){
					byteFileMarcato = tsr.addMarca(lFileFirmato);
				} else {
					byteFileMarcato = tsr.addMarcaPdf(lFileFirmato);
				}
				uriFileFirmato = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(byteFileMarcato));
			} else {
				uriFileFirmato = StorageImplementation.getStorage().store(lFileFirmato, new String[] {});
			}
			logger.debug("uriFileFirmato dopo marca: " + uriFileFirmato);
			String fileFirmato = StringEscapeUtils.escapeJava(java.net.URLEncoder.encode(fileNameOriginale, "UTF-8") + "#" + uriFileFirmato + "#" + idFile);
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append(fileFirmato);
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore: " + e.getMessage(), e);
			return new ResponseEntity<String>("OPERATION_ERROR", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
