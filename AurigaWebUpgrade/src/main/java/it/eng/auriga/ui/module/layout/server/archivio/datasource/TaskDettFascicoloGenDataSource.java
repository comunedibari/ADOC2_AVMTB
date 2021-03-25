package it.eng.auriga.ui.module.layout.server.archivio.datasource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.TaskFascicoloFileFirmatiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.client.DmpkCoreDel_ud_doc_ver;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "TaskDettFascicoloGenDataSource")
public class TaskDettFascicoloGenDataSource extends AbstractServiceDataSource<ArchivioBean, TaskFileDaFirmareBean>{

	private static Logger mLogger = Logger.getLogger(TaskDettFascicoloGenDataSource.class);

	public TaskFileDaFirmareBean getFileDaFirmare(ArchivioBean pArchivioBean) throws StorageException, Exception{
		boolean fileDaTimbrare = getExtraparams().get("fileDaTimbrare") != null ? new Boolean(getExtraparams().get("fileDaTimbrare")) : false;
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		lTaskFileDaFirmareBean.setFiles(getListaFileDaFirmare(pArchivioBean, fileDaTimbrare));		
		return lTaskFileDaFirmareBean;
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaFirmare(ArchivioBean pArchivioBean, boolean fileDaTimbrare) throws StorageException, Exception{
		ArrayList<FileDaFirmareBean> listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Per ogni documento devo recuperare solo quello che mi serve
		if (pArchivioBean.getListaDocumentiIstruttoria()!=null && pArchivioBean.getListaDocumentiIstruttoria().size()>0){
			for (AllegatoProtocolloBean lDocumentoIstruttoriaBean : pArchivioBean.getListaDocumentiIstruttoria()){
				// se è la risposta (che è quella con ruolo AFIN)
				if (lDocumentoIstruttoriaBean.getRuoloUd()!=null && "AFIN".equals(lDocumentoIstruttoriaBean.getRuoloUd())){
					if (StringUtils.isNotBlank(lDocumentoIstruttoriaBean.getUriFileAllegato())){
						aggiungiDocumento(listaFileDaFirmare, lDocumentoIstruttoriaBean, fileDaTimbrare);
					}
				}
			}
		}
		return listaFileDaFirmare;
	}
	
	public FileDaFirmareBean creaDocumentoUnioneFile(ArchivioBean pArchivioBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		File lFileUnione = buildUnioneFilePdf(pArchivioBean);				
		String idTipoDocFileUnione = StringUtils.isNotBlank(getExtraparams().get("idTipoDocFileUnione")) ? getExtraparams().get("idTipoDocFileUnione") : null;
		String nomeTipoDocFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeTipoDocFileUnione")) ? getExtraparams().get("nomeTipoDocFileUnione") : null;
		String nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnione")) ? getExtraparams().get("nomeFileUnione") : "RispostaCompleta.pdf";
		String uriFileUnione = StorageImplementation.getStorage().store(lFileUnione, new String[] {});
		FileDaFirmareBean lFileUnioneBean = new FileDaFirmareBean();
		lFileUnioneBean.setUri(uriFileUnione);
		lFileUnioneBean.setNomeFile(nomeFileUnione);		
		lFileUnioneBean.setInfoFile(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFileUnione).toURI().toString(), nomeFileUnione, false, null));		
		AllegatoProtocolloBean lRispostaCompletaBean = null;
		if(StringUtils.isNotBlank(idTipoDocFileUnione)) {
			if (pArchivioBean.getListaDocumentiIstruttoria()!=null && pArchivioBean.getListaDocumentiIstruttoria().size()>0){
				for (AllegatoProtocolloBean lDocumentoIstruttoriaBean : pArchivioBean.getListaDocumentiIstruttoria()){
					if (lDocumentoIstruttoriaBean.getIdTipoFileAllegato()!=null && lDocumentoIstruttoriaBean.getIdTipoFileAllegato().equals(idTipoDocFileUnione)){
						lRispostaCompletaBean = new AllegatoProtocolloBean();
						BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lRispostaCompletaBean, lDocumentoIstruttoriaBean); 						
					}
				}
			}
		}
		if(lRispostaCompletaBean == null) {
			// Creo il documento relativo al file unione da aggiungere al fascicolo
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
			lCreaModDocumentoInBean.setTipoDocumento(idTipoDocFileUnione);
			lCreaModDocumentoInBean.setNomeDocType(nomeTipoDocFileUnione);
			lCreaModDocumentoInBean.setOggetto("Risposta completa (con allegati integrati)");
			List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
			FolderCustom folderCustom = new FolderCustom();
			folderCustom.setId(String.valueOf(pArchivioBean.getIdUdFolder()));
			listaFolderCustom.add(folderCustom);
			lCreaModDocumentoInBean.setFolderCustom(listaFolderCustom);
			CreaModDocumentoOutBean lCreaModDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), loginBean, lCreaModDocumentoInBean, null, null);
			if (lCreaModDocumentoOutBean.getDefaultMessage() != null) {
				throw new StoreException(lCreaModDocumentoOutBean);
			}
			lRispostaCompletaBean = new AllegatoProtocolloBean();
			lRispostaCompletaBean.setIdUdAppartenenza(String.valueOf(lCreaModDocumentoOutBean.getIdUd().longValue()));
			lRispostaCompletaBean.setIdDocAllegato(lCreaModDocumentoOutBean.getIdDoc());
		}
		boolean fileDaTimbrare = getExtraparams().get("fileDaTimbrare") != null ? new Boolean(getExtraparams().get("fileDaTimbrare")) : false;
		String idUd = lRispostaCompletaBean.getIdUdAppartenenza();
		String idDoc = String.valueOf(lRispostaCompletaBean.getIdDocAllegato().longValue());
		if (fileDaTimbrare) {	
			FileDaFirmareBean lFileUnioneTimbratoBean = timbraFile(lFileUnioneBean, idUd);
			lFileUnioneTimbratoBean.setIdUd(idUd);
			lFileUnioneTimbratoBean.setIdDoc(idDoc);						
			return lFileUnioneTimbratoBean;
		} else {
			lFileUnioneBean.setIdUd(idUd);
			lFileUnioneBean.setIdDoc(idDoc);			
			return lFileUnioneBean;
		}
	}
	
	public FileDaFirmareBean versionaDocumentoUnioneFile(FileDaFirmareBean lFileUnioneBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		lVersionaDocumentoInBean.setIdDocumento(new BigDecimal(lFileUnioneBean.getIdDoc()));
		lVersionaDocumentoInBean.setFile(StorageImplementation.getStorage().extractFile(lFileUnioneBean.getUri()));
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		GenericFile lGenericFile = new GenericFile();
		setProprietaFirma(lGenericFile, lFileUnioneBean.getInfoFile());
		lGenericFile.setMimetype(lFileUnioneBean.getInfoFile().getMimetype());
		lGenericFile.setDisplayFilename(lFileUnioneBean.getNomeFile());
		lGenericFile.setImpronta(lFileUnioneBean.getInfoFile().getImpronta());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lVersionaDocumentoInBean.setInfo(lFileInfoBean);
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);
		if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
			mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutBean);
		}	
		return lFileUnioneBean;
	}
	
	public FileDaFirmareBean cancellaDocumentoUnioneFile(FileDaFirmareBean lFileUnioneBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		DmpkCoreDel_ud_doc_verBean delUdDocVerInput = new DmpkCoreDel_ud_doc_verBean();
		delUdDocVerInput.setFlgtipotargetin("U");
		delUdDocVerInput.setIduddocin(new BigDecimal(lFileUnioneBean.getIdUd()));
		delUdDocVerInput.setFlgcancfisicain(new Integer(1));
		DmpkCoreDel_ud_doc_ver delUdDocVer = new DmpkCoreDel_ud_doc_ver();
		StoreResultBean<DmpkCoreDel_ud_doc_verBean> output = delUdDocVer.execute(getLocale(), loginBean, delUdDocVerInput);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return lFileUnioneBean;		
	}
	
	private File buildUnioneFilePdf(ArchivioBean pArchivioBean) throws Exception {
		try {
			mLogger.debug("UNIONE DEI FILE");
			List<FileDaFirmareBean> lListFileDaUnireBean = getListaFileDaFirmare(pArchivioBean, false);
			if (lListFileDaUnireBean != null && !lListFileDaUnireBean.isEmpty()) {
				List<InputStream> lListInputStream = new ArrayList<InputStream>(lListFileDaUnireBean.size());
				for (FileDaFirmareBean lFileDaUnireBean : lListFileDaUnireBean) {
					mLogger.debug("File " + lFileDaUnireBean.getNomeFile() + ": " + lFileDaUnireBean.getUri());
					if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
						mLogger.debug("Il file è firmato in CAdES");
						FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaUnireBean);
						if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
							mLogger.debug("Il file sbustato è un pdf, quindi lo aggiungo");
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoBean.getUri()));
						} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
							mLogger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
							mLogger.debug("mimetype: " + lFileDaUnireBean.getInfoFile().getMimetype());							
							try {
								FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
								lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoConvertitoBean.getUri()));	
							} catch (Exception e) {
								String errorMessage = "Errore durante la conversione del file sbustato";
								if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
									errorMessage = "Errore durante la conversione del file sbustato " + lFileSbustatoBean.getNomeFile();
								}
								mLogger.error(errorMessage + " : " + e.getMessage(), e);
								throw new StoreException(errorMessage);
							}
						} else {
							String errorMessage = "Il file sbustato non è un pdf e non è convertibile.";
							if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
								errorMessage = "Il file sbustato" + lFileSbustatoBean.getNomeFile() + " non è un pdf e non è convertibile.";
							}
							mLogger.error(errorMessage);
							throw new StoreException(errorMessage);
						}
					} else if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
						if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().equalsIgnoreCase("PADES")) {
							mLogger.debug("Il file è firmato in PAdES quindi devo prendere la versione precedente la firma");
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUriVerPreFirma()));
						} else {
							mLogger.debug("Il file è un pdf, quindi lo aggiungo");
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUri()));
						}
					} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
						mLogger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
						try {
							FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaUnireBean);
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileConvertitoBean.getUri()));	
						} catch (Exception e) {
							String errorMessage = "Errore durante la conversione del file";
							if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
								errorMessage = "Errore durante la conversione del file " + lFileDaUnireBean.getNomeFile();
							}
							mLogger.error(errorMessage + " : " + e.getMessage(), e);
							throw new StoreException(errorMessage);
							
						}
					} else {
						String errorMessage = "Il file non è un pdf e non è convertibile.";
						if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
							errorMessage = "Il file " + lFileDaUnireBean.getNomeFile() + " non è un pdf e non è convertibile.";
						}
						mLogger.error(errorMessage);
						throw new StoreException(errorMessage);
					
					}					
				}
				return unioneFilePdf(lListInputStream);				
			} else {
				String errorMessage = "E' obbligatorio inserire almeno un file per procedere.";
				mLogger.error(errorMessage);
				throw new StoreException(errorMessage);
			}	
		} catch(StoreException e) {
			mLogger.error("Si è verificato un errore durante l'unione dei file. " + e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante l'unione dei file. " + e.getMessage());
		} catch (Exception e1) {
			mLogger.error("Si è verificato un errore durante l'unione dei file. " + e1.getMessage(), e1);
			throw new StoreException("Si è verificato un errore durante l'unione dei file.");
		}
	}
	
	public File unioneFilePdf(List<InputStream> lListInputStream) throws Exception {
		Document document = new Document();
		// Istanzio una copia nell'output
		File lFile = File.createTempFile("pdf", ".pdf");
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(lFile));
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;
		// Per ogni file passato
		for (InputStream lInputStream : lListInputStream) {
			// Leggo il file
			reader = new PdfReader(lInputStream);
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// e per ogni pagina
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
		}
		// Chiudo il documento
		document.close();
		return lFile;
	}

	private void aggiungiDocumento(ArrayList<FileDaFirmareBean> listaFileDaFirmare, AllegatoProtocolloBean lDocumentoIstruttoriaBean, boolean fileDaTimbrare) throws Exception {
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setIdFile(lDocumentoIstruttoriaBean.getUriFileAllegato());
		lFileDaFirmareBean.setInfoFile(lDocumentoIstruttoriaBean.getInfoFile());
		lFileDaFirmareBean.setNomeFile(lDocumentoIstruttoriaBean.getNomeFileAllegato());
		lFileDaFirmareBean.setUri(lDocumentoIstruttoriaBean.getUriFileAllegato());
		lFileDaFirmareBean.setUriVerPreFirma(lDocumentoIstruttoriaBean.getUriFileVerPreFirma());
		lFileDaFirmareBean.setIdUd(lDocumentoIstruttoriaBean.getIdUdAppartenenza());
		lFileDaFirmareBean.setIdDoc(lDocumentoIstruttoriaBean.getIdDocAllegato() != null ? String.valueOf(lDocumentoIstruttoriaBean.getIdDocAllegato().longValue()) : null);
		if(listaFileDaFirmare == null) {
			listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		}
		if (fileDaTimbrare && StringUtils.isNotBlank(lDocumentoIstruttoriaBean.getIdUdAppartenenza())) {			
			listaFileDaFirmare.add(timbraFile(lFileDaFirmareBean, lDocumentoIstruttoriaBean.getIdUdAppartenenza()));
		} else {
			listaFileDaFirmare.add(lFileDaFirmareBean);
		}		
	}
	
	public ArchivioBean aggiornaFileFirmati(TaskFascicoloFileFirmatiBean pTaskFascicoloFileFirmatiBean) throws Exception{
		ArchivioBean lArchivioBean = pTaskFascicoloFileFirmatiBean.getFascicoloOriginale();
		boolean firmaNonValida = false;
		for (FileDaFirmareBean lFileDaFirmareBean : pTaskFascicoloFileFirmatiBean.getFileFirmati().getFiles()){
			if(lFileDaFirmareBean.getInfoFile().isFirmato() && !lFileDaFirmareBean.getInfoFile().isFirmaValida()) {
				if (lFileDaFirmareBean.getIdFile().equals("primario")){
					mLogger.error("La firma del file primario " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: " + lFileDaFirmareBean.getUri());
				} else {
					mLogger.error("La firma del file allegato " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: " + lFileDaFirmareBean.getUri());
				}
				firmaNonValida = true;
			}
			aggiornaDocumento(lArchivioBean, lFileDaFirmareBean);			
		}
		if(firmaNonValida) {
			throw new StoreException("La firma di uno o più file risulta essere non valida");
		}
		return lArchivioBean;
	}
	
	public ArchivioBean aggiornaFile(TaskFascicoloFileFirmatiBean pTaskFascicoloFileFirmatiBean) throws Exception{
		ArchivioBean lArchivioBean = pTaskFascicoloFileFirmatiBean.getFascicoloOriginale();
		for (FileDaFirmareBean lFileDaFirmareBean : pTaskFascicoloFileFirmatiBean.getFileFirmati().getFiles()){			
			aggiornaDocumento(lArchivioBean, lFileDaFirmareBean);			
		}		
		return lArchivioBean;
	}

	private void aggiornaDocumento(ArchivioBean lArchivioBean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile();
		for (AllegatoProtocolloBean lDocumentoIstruttoriaBean : lArchivioBean.getListaDocumentiIstruttoria()){
			if (lDocumentoIstruttoriaBean.getUriFileAllegato() != null && lDocumentoIstruttoriaBean.getUriFileAllegato().equals(uriFileOriginale)){
				lDocumentoIstruttoriaBean.setUriFileAllegato(lFileDaFirmareBean.getUri());
				lDocumentoIstruttoriaBean.setNomeFileAllegato(lFileDaFirmareBean.getNomeFile());
				lDocumentoIstruttoriaBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
				lDocumentoIstruttoriaBean.setIdTaskVer(getExtraparams().get("idTaskCorrente"));
				lDocumentoIstruttoriaBean.setIsChanged(true);
				break;
			}
		}
	}

	private FileDaFirmareBean timbraFile(FileDaFirmareBean lFileDaTimbrareBean, String idUd) throws Exception {
		mLogger.debug("TIMBRO FILE");		
		mLogger.debug("File " + lFileDaTimbrareBean.getNomeFile() + ": " + lFileDaTimbrareBean.getUri());
		if (StringUtils.isNotBlank(idUd)) {			
			mLogger.debug("idUd: " + idUd);
			if (lFileDaTimbrareBean.getInfoFile().isFirmato() && lFileDaTimbrareBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
				mLogger.debug("Il file è firmato in CAdES");
				FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaTimbrareBean);
				if (lFileDaTimbrareBean.getInfoFile().getMimetype().equals("application/pdf")) {
					mLogger.debug("Il file sbustato è un pdf, quindi lo timbro");
					return timbraFilePdf(lFileSbustatoBean, idUd);						
				} else if (lFileDaTimbrareBean.getInfoFile().isConvertibile()) {
					mLogger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo timbro");
					mLogger.debug("mimetype: " + lFileDaTimbrareBean.getInfoFile().getMimetype());							
					try {
						FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
						return timbraFilePdf(lFileSbustatoConvertitoBean, idUd);	
					} catch (Exception e) {
						mLogger.debug("Errore durante la conversione del file sbustato: " + e.getMessage(), e);
					}
				} else {
					mLogger.debug("Il file sbustato non è un pdf e non è convertibile, quindi non lo timbro");
				}
			} else if (lFileDaTimbrareBean.getInfoFile().getMimetype().equals("application/pdf")) {
				mLogger.debug("Il file è un pdf, quindi lo timbro");
				return timbraFilePdf(lFileDaTimbrareBean, idUd);		
			} else if (lFileDaTimbrareBean.getInfoFile().isConvertibile()) {
				mLogger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo timbro");
				try {
					FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaTimbrareBean);
					return timbraFilePdf(lFileConvertitoBean, idUd);	
				} catch (Exception e) {
					mLogger.debug("Errore durante la conversione del file: " + e.getMessage(), e);
				}
			} else {
				mLogger.debug("Il file non è un pdf e non è convertibile, quindi non lo timbro");
			}							
		} else {
			mLogger.debug("idUd non valorizzata, quindi non lo timbro");
		}
		return lFileDaTimbrareBean;
	}
	
	private FileDaFirmareBean convertiFile(FileDaFirmareBean lFileDaConvertireBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());		
		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(lFileDaConvertireBean.getUri());
		FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), loginBean, lFileExtractedIn);
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));				
		String nomeFile = lFileDaConvertireBean.getInfoFile().getCorrectFileName() != null ? lFileDaConvertireBean.getInfoFile().getCorrectFileName() : "";
		String nomeFilePdf = FilenameUtils.getBaseName(nomeFile) + ".pdf";
		String uriPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(lFile.toURI().toString(), nomeFile));
		lFileDaConvertireBean.setNomeFile(nomeFilePdf);
		lFileDaConvertireBean.setUri(uriPdf);
		lFileDaConvertireBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaConvertireBean;			
	}
	
	private FileDaFirmareBean sbustaFile(FileDaFirmareBean lFileDaSbustareBean) throws Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(lFileDaSbustareBean.getUri());				
		String nomeFile = lFileDaSbustareBean.getInfoFile().getCorrectFileName() != null ? lFileDaSbustareBean.getInfoFile().getCorrectFileName() : "";		
		String nomeFileSbustato = (nomeFile != null && nomeFile.toLowerCase().endsWith(".p7m")) ? nomeFile.substring(0, nomeFile.length() - 4) : nomeFile;		
		String uriSbustato = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(lFile.toURI().toString(), nomeFile));		
		lFileDaSbustareBean.setNomeFile(nomeFileSbustato);
		lFileDaSbustareBean.setUri(uriSbustato);
		lFileDaSbustareBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaSbustareBean;				
	}
	
	private FileDaFirmareBean timbraFilePdf(FileDaFirmareBean lFileDaTimbrareBean, String idUd) throws Exception {
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		lOpzioniTimbroBean.setMimetype("application/pdf");
		lOpzioniTimbroBean.setUri(lFileDaTimbrareBean.getUri());
		lOpzioniTimbroBean.setNomeFile(lFileDaTimbrareBean.getNomeFile());
		lOpzioniTimbroBean.setIdUd(idUd);
		lOpzioniTimbroBean.setRemote(true);
		TimbraUtility timbraUtility = new TimbraUtility();
		lOpzioniTimbroBean = timbraUtility.loadSegnatureRegistrazioneDefault(lOpzioniTimbroBean, getSession(), getLocale());

		// Setto i parametri del timbro utilizzando dal config.xml il bean OpzioniTimbroAutoDocRegBean
		try{
			OpzioniTimbroAttachEmail lOpzTimbroAutoDocRegBean = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAutoDocRegBean");
			if(lOpzTimbroAutoDocRegBean != null){
				lOpzioniTimbroBean.setPosizioneTimbro(lOpzTimbroAutoDocRegBean.getPosizioneTimbro() != null &&
						!"".equals(lOpzTimbroAutoDocRegBean.getPosizioneTimbro()) ? lOpzTimbroAutoDocRegBean.getPosizioneTimbro().value() : "altoSn");
				lOpzioniTimbroBean.setRotazioneTimbro(lOpzTimbroAutoDocRegBean.getRotazioneTimbro() != null &&
						!"".equals(lOpzTimbroAutoDocRegBean.getRotazioneTimbro()) ? lOpzTimbroAutoDocRegBean.getRotazioneTimbro().value() : "verticale");
				if (lOpzTimbroAutoDocRegBean.getPaginaTimbro() != null) {
					if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina() != null) {
						lOpzioniTimbroBean.setTipoPagina(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina().value());
					} else if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine() != null) {
						lOpzioniTimbroBean.setTipoPagina("intervallo");
						if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
							lOpzioniTimbroBean.setPaginaDa(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa()));
						}
						if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
							lOpzioniTimbroBean.setPaginaA(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaA()));
						}
					}
				}
				lOpzioniTimbroBean.setTimbroSingolo(lOpzTimbroAutoDocRegBean.isTimbroSingolo());
				lOpzioniTimbroBean.setMoreLines(lOpzTimbroAutoDocRegBean.isRigheMultiple());
			}
		} catch (NoSuchBeanDefinitionException e) {
			/**
			 * Se il Bean OpzioniTimbroAutoDocRegBean non è correttamente configurato vengono utilizzare le preference del 
			 * bean OpzioniTimbroAttachEmail affinchè la timbratura vada a buon fine.
			 */
			mLogger.warn("OpzioniTimbroAutoDocRegBean non definito nel file di configurazione");
		}
			
		// Timbro il file
		TimbraResultBean lTimbraResultBean = timbraUtility.timbra(lOpzioniTimbroBean, getSession());
		// Verifico se la timbratura è andata a buon fine
		if (lTimbraResultBean.isResult()) {
			// Aggiungo il file timbrato nella lista dei file da pubblicare
			lFileDaTimbrareBean.setUri(lTimbraResultBean.getUri());
		} else {
			// // La timbratura è fallita, pubblico il file sbustato
			// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
			String errorMessage = "Si è verificato un errore durante la timbratura del file";
			if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
				errorMessage += ": " + lTimbraResultBean.getError();
			}
			throw new Exception(errorMessage);
		}		
		File lFileTimbrato = StorageImplementation.getStorage().extractFile(lFileDaTimbrareBean.getUri());		
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lFileTimbrato.toURI().toString(), lFileDaTimbrareBean.getNomeFile(), false, null);
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setFirmaValida(false);
		lMimeTypeFirmaBean.setConvertibile(false);
		lMimeTypeFirmaBean.setDaScansione(false);
		lFileDaTimbrareBean.setInfoFile(lMimeTypeFirmaBean);				
		return lFileDaTimbrareBean;
	}
	
	@Override
	public TaskFileDaFirmareBean call(ArchivioBean pInBean)
			throws Exception {
		return null;
	}

}
