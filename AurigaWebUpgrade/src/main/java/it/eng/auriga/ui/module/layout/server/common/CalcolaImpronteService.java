package it.eng.auriga.ui.module.layout.server.common;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.ConversionePdfBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.FileDaConvertireBean;
import it.eng.auriga.util.PdfSignatureUtils;
import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.sign.FileElaborate;
import it.eng.utility.ui.sign.SignerHashUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

/**
 * Questa classe viene utilizzata per calcolare l'impronta del file quando firmo tramite applet o hybrid
 *
 */

@Datasource(id = "CalcolaImpronteService")
public class CalcolaImpronteService extends AbstractServiceDataSource<ConversionePdfBean, ConversionePdfBean> {
	
	private static Logger mLogger = Logger.getLogger(CalcolaImpronteService.class);

	@Override
	public ConversionePdfBean call(ConversionePdfBean bean) throws Exception {
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		for (FileDaConvertireBean lFileBean : bean.getFiles()) {
			if (lFileBean.getInfoFile() != null && lFileBean.getInfoFile().getMimetype() != null
					&& lFileBean.getInfoFile().getMimetype().equals("application/pdf")) {
				// Calcolo l'impronta pdf del file che mi serve nel caso di firma PAdES
				try {
					FileElaborate fileElaborate = new FileElaborate();
					File lFileDaFirmare = StorageImplementation.getStorage().extractFile(lFileBean.getUri());
					if (lFileBean.getInfoFile() != null && lFileBean.getInfoFile().isFirmato() && lFileBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
						P7MDetector lP7MDetector = new P7MDetector();
						File lFileSbustato = StorageImplementation.getStorage().extractFile(StorageImplementation.getStorage().storeStream(lP7MDetector.getContent(lFileDaFirmare), new String[] {}));
						fileElaborate.setUnsigned(lFileSbustato);
					} else {
						fileElaborate.setUnsigned(lFileDaFirmare);
					}
					SignerHashUtil.calcolaHashPdf(fileElaborate, lDocumentConfiguration.getAlgoritmo(), lDocumentConfiguration.getEncoding());
					lFileBean.getInfoFile().setImprontaPdf(fileElaborate.getEncodedHash());
					getSession().getServletContext().setAttribute("fileElaborate" + lFileBean.getIdFile(), fileElaborate);
				} catch (Exception e) {
					mLogger.warn(e);
				}
			}
			/*
			 * Se si tratta di un file il cui formato è diverso da pdf si arriva qui.
			 * Se si tratta di un file p7m viene generata un eccezione che lo porta ad uscire dal ramo precedente e ad arrivare
			 * qui.
			 */ 
			boolean isFirmaCongiunta = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FIRMA_CONGIUNTA");
			if (getExtraparams().get("firmaCongiunta") != null && !"".equals(getExtraparams().get("firmaCongiunta"))) {
				isFirmaCongiunta = getExtraparams().get("firmaCongiunta").equalsIgnoreCase("true");
			}
			if (isFirmaCongiunta) {
				if (lFileBean.getInfoFile() != null && lFileBean.getInfoFile().isFirmato() && lFileBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
					MimeTypeFirmaBean lMimeTypeFirmaBeanPrec = lFileBean.getInfoFile(); 
					InfoFileUtility lFileUtility = new InfoFileUtility();
					MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(StorageImplementation.getStorage().getRealFile(lFileBean.getUri()).toURI().toString(), lFileBean.getNomeFile(), false, null);
					// Devo ripristinare le informazioni che getInfoFromFile non è in grado di calcolare
					if (lMimeTypeFirmaBeanPrec != null && lMimeTypeFirmaBean != null && lMimeTypeFirmaBeanPrec.getInfoFirmaMarca() != null && lMimeTypeFirmaBean.getInfoFirmaMarca() != null) {
						lMimeTypeFirmaBean.getInfoFirmaMarca().setBustaCrittograficaFattaDaAuriga(lMimeTypeFirmaBeanPrec.getInfoFirmaMarca().isBustaCrittograficaFattaDaAuriga());
						lMimeTypeFirmaBean.getInfoFirmaMarca().setFirmeExtraAuriga(lMimeTypeFirmaBeanPrec.getInfoFirmaMarca().isFirmeExtraAuriga());
						lMimeTypeFirmaBean.getInfoFirmaMarca().setMarcaTemporaleAppostaDaAuriga(lMimeTypeFirmaBeanPrec.getInfoFirmaMarca().isMarcaTemporaleAppostaDaAuriga());
					}
					lFileBean.setInfoFile(lMimeTypeFirmaBean);
					// Calcolo l'impronta del file sbustato che mi serve nel caso di firma congiunta di un file giÃ  firmato
					try {
						File lFileDaFirmare = StorageImplementation.getStorage().extractFile(lFileBean.getUri());
						P7MDetector lP7MDetector = new P7MDetector();
						byte[] hash = null;
						if (lDocumentConfiguration.getAlgoritmo().equals(DigestAlgID.SHA_256)) {
							hash = DigestUtils.sha256(lP7MDetector.getContent(lFileDaFirmare));
						} else {
							hash = DigestUtils.sha(lP7MDetector.getContent(lFileDaFirmare));
						}
						String encodedHash = null;
						if (lDocumentConfiguration.getEncoding().equals(DigestEncID.BASE_64)) {
							encodedHash = Base64.encodeBase64String(hash);
						} else if (lDocumentConfiguration.getEncoding().equals(DigestEncID.HEX)) {
							encodedHash = Hex.encodeHexString(hash);
						}
						lFileBean.getInfoFile().setImprontaSbustato(encodedHash);
					} catch (Exception e) {
						mLogger.warn(e);
					}
				}
			}
		}
		if(getSession() != null && getSession().getId() != null &&
				!"".equalsIgnoreCase(getSession().getId())) {
			bean.setjSessionId(getSession().getId());
		}
		return bean;
	}

	public String calcolaImprontaWithoutFileOp(File file, String algoritmo, String encoding) throws Exception {
		try {
			byte[] hash = null;
			if (StringUtils.isBlank(algoritmo)) {
				throw new Exception("Algoritmo per il calcolo dell'impronta non valorizzato");
			} else if (algoritmo.equalsIgnoreCase("SHA-256")) {
				hash = DigestUtils.sha256(new FileInputStream(file));
			} else if (algoritmo.equalsIgnoreCase("SHA-1")) {
				hash = DigestUtils.sha(new FileInputStream(file));
			} else {
				throw new Exception("L'algoritmo " + algoritmo + " non è uno di quelli previsti");
			}
			String encodedHash = null;
			if (StringUtils.isBlank(encoding)) {
				throw new Exception("Encoding per il calcolo dell'impronta non valorizzato");
			} else if (encoding.equalsIgnoreCase("base64")) {
				encodedHash = Base64.encodeBase64String(hash);
			} else if (encoding.equalsIgnoreCase("hex")) {
				encodedHash = Hex.encodeHexString(hash);
			} else {
				throw new Exception("L'encoding " + encoding + " non è uno di quelli previsti");
			}
			return encodedHash;
		} catch (Exception e) {
			throw new Exception("Si è verificato un errore durante il calcolo dell'impronta: " + e.getMessage(), e);
		}
	}

	// Questo metodo viene chiamato solamente se la firma ha evidenza grafica
	public ConversionePdfBean calcolaImprontaFirmaPades(ConversionePdfBean bean) throws Exception {
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		// Per ogni file che deve essere firmato
		for (FileDaConvertireBean lFileBean : bean.getFiles()) {
			if (lFileBean.getInfoFile() != null && lFileBean.getInfoFile().getMimetype() != null
					&& lFileBean.getInfoFile().getMimetype().equals("application/pdf")) {
				// Calcolo l'impronta pdf del file che mi serve nel caso di firma PAdES
				try {
					FileElaborate fileElaborate = new FileElaborate();
					File lFileDaFirmare = StorageImplementation.getStorage().extractFile(lFileBean.getUri()); // Prelevo il file che deve essere elaborato				
					if (lFileBean.getInfoFile() != null && lFileBean.getInfoFile().isFirmato() && lFileBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
						P7MDetector lP7MDetector = new P7MDetector();
						File lFileSbustato = StorageImplementation.getStorage().extractFile(StorageImplementation.getStorage().storeStream(lP7MDetector.getContent(lFileDaFirmare), new String[] {}));
						fileElaborate.setUnsigned(lFileSbustato);
					} else {
						fileElaborate.setUnsigned(lFileDaFirmare);
					}
					// SignerHashUtil.calcolaHashPdf(fileElaborate, lDocumentConfiguration.getAlgoritmo(), lDocumentConfiguration.getEncoding());
					// Utilizzo la funzione creata per calcolare l'hash del pdf con il rettangolo contentente la firma
					PdfSignatureUtils pdfUtil = new PdfSignatureUtils();
					pdfUtil.calcolaHashPdf(fileElaborate, lDocumentConfiguration.getAlgoritmo(), lDocumentConfiguration.getEncoding(),
							bean.getRettangoloFirmaPades());
					// Setto l'impronta pdf
					lFileBean.getInfoFile().setImprontaPdf(fileElaborate.getEncodedHash());
					getSession().getServletContext().setAttribute("fileElaborate" + lFileBean.getIdFile(), fileElaborate);

				} catch (Exception e) {
					mLogger.warn(e);
				}
			}
			/*
			 * Se si tratta di un file il cui formato è diverso da pdf si arriva qui.
			 * Se si tratta di un file p7m viene generata un eccezione che lo porta ad uscire dal ramo precedente e ad arrivare
			 * qui.
			 */ 
			boolean isFirmaCongiunta = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FIRMA_CONGIUNTA");
			if (getExtraparams().get("firmaCongiunta") != null && !"".equals(getExtraparams().get("firmaCongiunta"))) {
				isFirmaCongiunta = getExtraparams().get("firmaCongiunta").equalsIgnoreCase("true");
			}
			if (isFirmaCongiunta) {
				if (lFileBean.getInfoFile() != null && lFileBean.getInfoFile().isFirmato() && lFileBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
					InfoFileUtility lFileUtility = new InfoFileUtility();
					MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(
							StorageImplementation.getStorage().getRealFile(lFileBean.getUri()).toURI().toString(), lFileBean.getNomeFile(), false, null);
					lFileBean.setInfoFile(lMimeTypeFirmaBean);
					// Calcolo l'impronta del file sbustato che mi serve nel caso di firma congiunta di un file giÃ  firmato
					try {
						File lFileDaFirmare = StorageImplementation.getStorage().extractFile(lFileBean.getUri());
						P7MDetector lP7MDetector = new P7MDetector();
						byte[] hash = null;
						if (lDocumentConfiguration.getAlgoritmo().equals(DigestAlgID.SHA_256)) {
							hash = DigestUtils.sha256(lP7MDetector.getContent(lFileDaFirmare));
						} else {
							hash = DigestUtils.sha(lP7MDetector.getContent(lFileDaFirmare));
						}
						String encodedHash = null;
						if (lDocumentConfiguration.getEncoding().equals(DigestEncID.BASE_64)) {
							encodedHash = Base64.encodeBase64String(hash);
						} else if (lDocumentConfiguration.getEncoding().equals(DigestEncID.HEX)) {
							encodedHash = Hex.encodeHexString(hash);
						}
						lFileBean.getInfoFile().setImprontaSbustato(encodedHash);
					} catch (Exception e) {
						mLogger.warn(e);
					}
				}
			}
			/*
			 * Poichè questa procedura è fatta per i PDF la firma è sempre congiunta e quindi non serve controllare questa situazione
			 */

		} // End for
		
		if(getSession() != null && getSession().getId() != null &&
				!"".equalsIgnoreCase(getSession().getId())) {
			bean.setjSessionId(getSession().getId());
		}
		
		return bean;
	}

}