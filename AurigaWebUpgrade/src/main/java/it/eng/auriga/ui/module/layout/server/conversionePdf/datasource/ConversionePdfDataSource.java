package it.eng.auriga.ui.module.layout.server.conversionePdf.datasource;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.ConversionePdfBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.FileDaConvertireBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "ConversionePdfDataSource")
public class ConversionePdfDataSource extends AbstractServiceDataSource<ConversionePdfBean, ConversionePdfBean> {

	private static Logger mLogger = Logger.getLogger(ConversionePdfDataSource.class);

	@Override
	public ConversionePdfBean call(ConversionePdfBean pInBean) throws Exception {
		
		List<FileDaConvertireBean> lFileDaConvertireBeanList = new ArrayList<FileDaConvertireBean>();
				
		boolean conversionePerFirma =  (getExtraparams() != null ? ((StringUtils.isNotBlank(getExtraparams().get("SCOPO"))) ? "FIRMA".equalsIgnoreCase(getExtraparams().get("SCOPO")) : false) : false );
		
		boolean abilitaPdfA = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FIRMA_ABILITA_PDFA");
		for (FileDaConvertireBean lFileDaConvertireBean : pInBean.getFiles()) {
			String nomeFile = lFileDaConvertireBean.getNomeFile();
			String uri = lFileDaConvertireBean.getUri();
			MimeTypeFirmaBean infoFile = lFileDaConvertireBean.getInfoFile();
			// se è già un pdf
			boolean skipConversione = false;
			boolean conversionePdfPdfa = false;
			if (infoFile != null) {
				// Controllo se devo convertire il pdf in pdfa
				if (infoFile.getMimetype() != null && infoFile.getMimetype().equals("application/pdf") && !infoFile.isFirmato() && abilitaPdfA) {
					// Dovrei mettere skipConversione a true se è già un pdfa
					if (!InfoFileUtility.checkPdfA(StorageImplementation.getStorage().extract(uri))) {
						skipConversione = false;
						conversionePdfPdfa = true;
					} else {
						skipConversione = true;
					}
				} else if ((infoFile.getMimetype() != null && infoFile.getMimetype().equals("application/pdf")) || infoFile.isFirmato() || !infoFile.isConvertibile()) {
					skipConversione = true;
				}
			} else if (nomeFile != null && (nomeFile.toLowerCase().endsWith(".pdf") || nomeFile.toLowerCase().endsWith(".p7m"))) {
				skipConversione = true;
			}
			if (!skipConversione) {
				String uriPdf = "";
				if (conversionePdfPdfa) {
					try {
						File filePdfa = File.createTempFile("filePdfa", ".pdf");
						InfoFileUtility lFileUtility = new InfoFileUtility();
						lFileUtility.convertiPdfToPdfA(StorageImplementation.getStorage().extract(uri), new FileOutputStream(filePdfa));
						uriPdf = StorageImplementation.getStorage().store(filePdfa);
					} catch(Exception e) {
						mLogger.error("Si è verificato un errore durante la conversione in PDFA del file " + nomeFile, e);
						throw new StoreException("Si è verificato un errore durante la conversione in PDFA del file " + nomeFile);
					}
				} else {
					try {											
						uriPdf = convertPdf(uri, nomeFile, conversionePerFirma && abilitaPdfA);
					} catch(Exception e) {
						mLogger.error("Si è verificato un errore durante la conversione in PDF del file " + nomeFile, e);
						throw new StoreException("Si è verificato un errore durante la conversione in PDF del file " + nomeFile);
					}
				}
				String impronta = calculateHash(uriPdf);
				if (infoFile == null || infoFile.getImpronta() == null || !infoFile.getImpronta().equals(impronta)) {
					lFileDaConvertireBean.setNomeFile(FilenameUtils.getBaseName(nomeFile) + ".pdf");
					lFileDaConvertireBean.setUri(uriPdf);
					MimeTypeFirmaBean infoFilePdf = new MimeTypeFirmaBean();
					infoFilePdf.setMimetype("application/pdf");
					infoFilePdf.setImpronta(impronta);
					infoFilePdf.setCorrectFileName(lFileDaConvertireBean.getNomeFile());
					lFileDaConvertireBean.setInfoFile(infoFilePdf);
					lFileDaConvertireBean.setNomeFilePrec(nomeFile);
					lFileDaConvertireBean.setUriPrec(uri);
					lFileDaConvertireBean.setInfoFilePrec(infoFile);
				}
			}
			lFileDaConvertireBeanList.add(lFileDaConvertireBean);
		}
		pInBean.setFiles(lFileDaConvertireBeanList);
		return pInBean;
	}

	public String convertPdf(String uri, String nomeFile, boolean pdfA) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uri).toURI().toString();
			String uriPdfGenerato = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(fileUrl, nomeFile, null, pdfA));
			return uriPdfGenerato;
		} catch (Exception e) {
			mLogger.error("Errore durante la conversione in pdf: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile convertire il file: " + e.getMessage());
		}
	}

	public String calculateHash(String uri) throws StoreException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(uri).toURI().toString();
			return lInfoFileUtility.calcolaImpronta(fileUrl, "");
		} catch (Exception e) {
			mLogger.error("Errore durante il calcolo dell'impronta: " + e.getMessage(), e);
			throw new StoreException("Non è stato possibile calcolare l'impronta del file");
		}
	}

}
