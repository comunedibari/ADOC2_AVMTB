package it.eng.document.function;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddfolderBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDelfolderBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreTrovarepositoryobjBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdfolderBean;
import it.eng.auriga.database.store.dmpk_core.store.Addfolder;
import it.eng.auriga.database.store.dmpk_core.store.Delfolder;
import it.eng.auriga.database.store.dmpk_core.store.Trovarepositoryobj;
import it.eng.auriga.database.store.dmpk_core.store.Updfolder;
import it.eng.auriga.database.store.dmpk_core.store.impl.AdddocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddfolderImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.Del_ud_doc_verImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpddocudImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpdfolderImpl;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginMarktokenusageBean;
import it.eng.auriga.database.store.dmpk_login.store.impl.MarktokenusageImpl;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoaddettfascxguimodprotBean;
import it.eng.auriga.database.store.dmpk_repository_gui.store.Loaddettfascxguimodprot;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.auriga.repository2.jaxws.webservices.common.ConnectionWrapper;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.CancellaFascicoloIn;
import it.eng.document.function.bean.CancellaFascicoloOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.LoadFascicoloIn;
import it.eng.document.function.bean.LoadFascicoloOut;
import it.eng.document.function.bean.ModificaFascicoloIn;
import it.eng.document.function.bean.ModificaFascicoloOut;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.SalvaFascicoloIn;
import it.eng.document.function.bean.SalvaFascicoloOut;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.TrovaDocFolderOut;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.function.bean.XmlFascicoloOut;
import it.eng.document.storage.DocumentStorage;
import it.eng.storeutil.AnalyzeResult;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Service(name = "GestioneFascicoli")
public class GestioneFascicoli {

	private static Logger mLogger = Logger.getLogger(GestioneFascicoli.class);

	@Operation(name = "loadFascicolo")
	public LoadFascicoloOut loadFascicolo(AurigaLoginBean pAurigaLoginBean, LoadFascicoloIn pLoadFascicoloIn) throws Exception {

		DmpkRepositoryGuiLoaddettfascxguimodprotBean bean = new DmpkRepositoryGuiLoaddettfascxguimodprotBean();

		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		bean.setIdfolderin(pLoadFascicoloIn.getIdFolderIn());
		bean.setFlgsoloabilazioniin(StringUtils.isNotBlank(pLoadFascicoloIn.getFlgSoloAbilAzioni()) ? new Integer(pLoadFascicoloIn.getFlgSoloAbilAzioni())
				: null);
		bean.setFlgfascincreazionein(StringUtils.isNotBlank(pLoadFascicoloIn.getFlgFascInCreazione()) ? new Integer(pLoadFascicoloIn.getFlgFascInCreazione())
				: null);

		Loaddettfascxguimodprot store = new Loaddettfascxguimodprot();
		StoreResultBean<DmpkRepositoryGuiLoaddettfascxguimodprotBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);
		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			LoadFascicoloOut lLoadFascicoloOut = new LoadFascicoloOut();
			BeanUtilsBean2.getInstance().copyProperties(lLoadFascicoloOut, lStoreResultBean);
			return lLoadFascicoloOut;
		}

		String lStrXml = lStoreResultBean.getResultBean().getDatifascxmlout();
		mLogger.debug(lStrXml);
		XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
		XmlFascicoloOut lXmlFascicoloOut = new XmlFascicoloOut();
		lXmlFascicoloOut = lXmlUtilityDeserializer.unbindXml(lStrXml, XmlFascicoloOut.class);

		LoadFascicoloOut result = new LoadFascicoloOut();
		result.setXmlFascicoloOut(lXmlFascicoloOut);

		return result;
	}

	@Operation(name = "cancellaFascicolo")
	public CancellaFascicoloOut cancellaFascicolo(AurigaLoginBean pAurigaLoginBean, CancellaFascicoloIn pCancellaFascicoloIn) throws Exception {

		DmpkCoreDelfolderBean bean = new DmpkCoreDelfolderBean();
		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		bean.setFlgcancfisicain(pCancellaFascicoloIn.getFlgCancFisicaIn());
		bean.setFolderpathin(pCancellaFascicoloIn.getFolderPath());
		bean.setIdfolderin(pCancellaFascicoloIn.getIdFolderIn());
		bean.setIdlibraryin(pCancellaFascicoloIn.getIdLibrary());
		bean.setNomelibraryin(pCancellaFascicoloIn.getNomeLibrary());

		Delfolder store = new Delfolder();
		StoreResultBean<DmpkCoreDelfolderBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);

		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			CancellaFascicoloOut lCancellaFascicoloOut = new CancellaFascicoloOut();
			BeanUtilsBean2.getInstance().copyProperties(lCancellaFascicoloOut, lStoreResultBean);
			return lCancellaFascicoloOut;
		}

		CancellaFascicoloOut lCancellaFascicoloOut = new CancellaFascicoloOut();
		lCancellaFascicoloOut.setUriOut(lStoreResultBean.getResultBean().getUriout());

		return lCancellaFascicoloOut;

	}

	@Operation(name = "salvaFascicolo")
	public SalvaFascicoloOut salvaFascicolo(AurigaLoginBean pAurigaLoginBean, SalvaFascicoloIn pSalvaFascicoloIn) throws Exception {

		SalvaFascicoloOut lSalvaFascicoloOut = new SalvaFascicoloOut();
		
		DmpkCoreAddfolderBean lAddfolderBean = new DmpkCoreAddfolderBean();

		lAddfolderBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lAddfolderBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXml(pSalvaFascicoloIn.getXmlFascicolo());
		mLogger.debug(lStrXml);
		lAddfolderBean.setAttributixmlin(lStrXml);
		
		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();

		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				final AddfolderImpl store = new AddfolderImpl();
				store.setBean(lAddfolderBean);

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreAddfolderBean> result = new StoreResultBean<DmpkCoreAddfolderBean>();
				AnalyzeResult.analyze(lAddfolderBean, result);
				result.setResultBean(lAddfolderBean);

				lSalvaFascicoloOut.setIdFolderOut(result.getResultBean().getIdfolderout());

				final MarktokenusageImpl lMarktokenusage = new MarktokenusageImpl();
				DmpkLoginMarktokenusageBean lMarktokenusageBean = new DmpkLoginMarktokenusageBean();
				if (pAurigaLoginBean.getToken() != null)
					lMarktokenusageBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lMarktokenusageBean.setFlgautocommitin(0);
				lMarktokenusage.setBean(lMarktokenusageBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						lMarktokenusage.execute(paramConnection);
					}
				});

				if (result.isInError()) {
					throw new StoreException(result);
				} else {
					if (pSalvaFascicoloIn != null) {
						
						Iterator<File> nextFile = null;
						if (pSalvaFascicoloIn.getFileAllegati() != null)
							nextFile = pSalvaFascicoloIn.getFileAllegati().iterator();
						
						if (pSalvaFascicoloIn.getIsNull() != null) {							
							for (int i = 0; i < pSalvaFascicoloIn.getIsNull().size(); i++) {
								AllegatoStoreBean lDocumentoBean = new AllegatoStoreBean();
								// Setto la natura a NULL perchè non lo sto salvando come allegato ma come file primario
								lDocumentoBean.setNatura("P");
								lDocumentoBean.setDescrizione(pSalvaFascicoloIn.getDescrizione().get(i));
								lDocumentoBean.setIdDocType(pSalvaFascicoloIn.getDocType().get(i));
								lDocumentoBean.setSezionePratica(pSalvaFascicoloIn.getSezionePratica().get(i));													
								List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
								FolderCustom folderCustom = new FolderCustom();
								folderCustom.setId(String.valueOf(lSalvaFascicoloOut.getIdFolderOut().longValue()));
								listaFolderCustom.add(folderCustom);
								lDocumentoBean.setFolderCustom(listaFolderCustom);
								lDocumentoBean.setFlgEreditaPermessi("1");
								lDocumentoBean.setIdFolderEreditaPerm(lSalvaFascicoloOut.getIdFolderOut());
								if (pSalvaFascicoloIn.getFlgParteDispositivo() != null) {
									Boolean flgParteDispositivo = pSalvaFascicoloIn.getFlgParteDispositivo().get(i);
									lDocumentoBean.setFlgParteDispositivo(flgParteDispositivo != null && flgParteDispositivo ? "1" : null);
								}
								if (pSalvaFascicoloIn.getIdTask() != null) {
									lDocumentoBean.setIdTask(pSalvaFascicoloIn.getIdTask().get(i));
								}
								if (pSalvaFascicoloIn.getFlgNoPubbl() != null) {
									Boolean flgNoPubbl = pSalvaFascicoloIn.getFlgNoPubbl().get(i);
									lDocumentoBean.setFlgNoPubbl(flgNoPubbl != null && flgNoPubbl ? "1" : null);
								}
								if (pSalvaFascicoloIn.getFlgPubblicaSeparato() != null) {
									Boolean flgPubblicaSeparato = pSalvaFascicoloIn.getFlgPubblicaSeparato().get(i);
									lDocumentoBean.setFlgPubblicaSeparato(flgPubblicaSeparato != null && flgPubblicaSeparato ? "1" : null);
								}
								if (pSalvaFascicoloIn.getFlgOriginaleCartaceo() != null) {
									Boolean flgOriginaleCartaceo = pSalvaFascicoloIn.getFlgOriginaleCartaceo().get(i);
									lDocumentoBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo != null && flgOriginaleCartaceo ? "1" : null);
								}
								if (pSalvaFascicoloIn.getFlgCopiaSostitutiva() != null) {
									Boolean flgCopiaSostitutiva = pSalvaFascicoloIn.getFlgCopiaSostitutiva().get(i);
									lDocumentoBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva != null && flgCopiaSostitutiva ? "1" : null);
								}
								String attributi = lXmlUtilitySerializer.bindXmlCompleta(lDocumentoBean);
								
								DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
								lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
								lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
								lAdddocBean.setAttributiuddocxmlin(attributi);
								final AdddocImpl lAdddoc = new AdddocImpl();
								lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
								lAdddoc.setBean(lAdddocBean);
								session.doWork(new Work() {
	
									@Override
									public void execute(Connection paramConnection) throws SQLException {
										lAdddoc.execute(paramConnection);
									}
								});
								StoreResultBean<DmpkCoreAdddocBean> lAdddocResult = new StoreResultBean<DmpkCoreAdddocBean>();
								AnalyzeResult.analyze(lAdddocBean, lAdddocResult);
								lAdddocResult.setResultBean(lAdddocBean);
								final MarktokenusageImpl lMarktokenusageAllegato = new MarktokenusageImpl();
								DmpkLoginMarktokenusageBean lMarktokenusageBeanAllegato = new DmpkLoginMarktokenusageBean();
								if (pAurigaLoginBean.getToken() != null)
									lMarktokenusageBeanAllegato.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
								lMarktokenusageBeanAllegato.setFlgautocommitin(0);
								lMarktokenusageAllegato.setBean(lMarktokenusageBeanAllegato);
								session.doWork(new Work() {
	
									@Override
									public void execute(Connection paramConnection) throws SQLException {
										lMarktokenusageAllegato.execute(paramConnection);
									}
								});
								if (lAdddocResult.isInError()) {
									throw new StoreException(lAdddocResult);
								} else {
									mLogger.debug("idDoc vale " + lAdddocResult.getResultBean().getIddocout());
									if (!pSalvaFascicoloIn.getIsNull().get(i)) {
										RebuildedFile lRebuildedFile = new RebuildedFile();
										lRebuildedFile.setFile(nextFile.next());
										lRebuildedFile.setInfo(pSalvaFascicoloIn.getInfo().get(i));
										lRebuildedFile.setIdDocumento(lAdddocResult.getResultBean().getIddocout());
										lRebuildedFile.setIdTask(pSalvaFascicoloIn.getIdTaskVer().get(i));
										versioni.add(lRebuildedFile);
									}
								}
							}		
						}
					}
				}

				// Parte di versionamento
				Map<String, String> fileErrors = new HashMap<String, String>();
				fileErrors.putAll(aggiungiFilesInTransaction(pAurigaLoginBean, versioni, session));
				lSalvaFascicoloOut.setFileInErrors(fileErrors);

				session.flush();
				lTransaction.commit();
				
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				lSalvaFascicoloOut.setInError(true);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lSalvaFascicoloOut, ((StoreException) e).getError());
					return lSalvaFascicoloOut;
				} else {
					lSalvaFascicoloOut.setDefaultMessage(e.getMessage());
					return lSalvaFascicoloOut;
				}
			} finally {
				HibernateUtil.release(session);
			}

		} catch (Exception e) {
			lSalvaFascicoloOut.setInError(true);
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lSalvaFascicoloOut, ((StoreException) e).getError());
				return lSalvaFascicoloOut;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lSalvaFascicoloOut.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lSalvaFascicoloOut;
			}
		}

		return lSalvaFascicoloOut;
	}

	@Operation(name = "modificaFascicolo")
	public ModificaFascicoloOut modificaFascicolo(AurigaLoginBean pAurigaLoginBean, ModificaFascicoloIn pModificaFascicoloIn) throws Exception {

		ModificaFascicoloOut lModificaFascicoloOut = new ModificaFascicoloOut(); 

		XmlFascicoloOut lXmlFascicoloOut = null;
		try {
			LoadFascicoloIn lLoadFascicoloIn = new LoadFascicoloIn();
			lLoadFascicoloIn.setIdFolderIn(pModificaFascicoloIn.getIdFolderIn());
			LoadFascicoloOut lLoadFascicoloOut = loadFascicolo(pAurigaLoginBean, lLoadFascicoloIn);
			lXmlFascicoloOut = lLoadFascicoloOut.getXmlFascicoloOut();
		} catch (Exception e) {
			lModificaFascicoloOut.setInError(true);
			mLogger.error("Errore " + e.getMessage(), e);
			lModificaFascicoloOut.setDefaultMessage("Il fascicolo da aggiornare e' inesistente");
			return lModificaFascicoloOut;
		}

		DmpkCoreUpdfolderBean lUpdfolderBean = new DmpkCoreUpdfolderBean();

		lUpdfolderBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lUpdfolderBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		lUpdfolderBean.setIdfolderin(pModificaFascicoloIn.getIdFolderIn());
		lUpdfolderBean.setIdlibraryin(pModificaFascicoloIn.getIdLibrary());
		lUpdfolderBean.setNomelibraryin(pModificaFascicoloIn.getNomeLibrary());
		lUpdfolderBean.setFolderpathin(pModificaFascicoloIn.getFolderPath());

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXmlCompleta(pModificaFascicoloIn.getModificaFascicolo());
		lUpdfolderBean.setAttributixmlin(lStrXml);

		lUpdfolderBean.setFlgautocommitin(0); // blocco l'autocommit

		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		List<RebuildedFile> documentiDaRimuovere = new ArrayList<RebuildedFile>();

		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {

				final UpdfolderImpl store = new UpdfolderImpl();
				store.setBean(lUpdfolderBean);

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreUpdfolderBean> result = new StoreResultBean<DmpkCoreUpdfolderBean>();
				AnalyzeResult.analyze(lUpdfolderBean, result);
				result.setResultBean(lUpdfolderBean);

				lModificaFascicoloOut.setUriPerAggiornamentoContainer(result.getResultBean().getUrixaggcontainerout());

				final MarktokenusageImpl lMarktokenusage = new MarktokenusageImpl();
				DmpkLoginMarktokenusageBean lMarktokenusageBean = new DmpkLoginMarktokenusageBean();
				if (pAurigaLoginBean.getToken() != null)
					lMarktokenusageBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lMarktokenusageBean.setFlgautocommitin(0);
				lMarktokenusage.setBean(lMarktokenusageBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						lMarktokenusage.execute(paramConnection);
					}
				});

				if (result.isInError()) {
					throw new StoreException(result);
				} else {
					if (pModificaFascicoloIn != null) {
						if (lXmlFascicoloOut.getListaDocumentiIstruttoria() != null) {
							HashMap<String, AllegatiOutBean> documentiMap = new HashMap<String, AllegatiOutBean>();
							for (AllegatiOutBean doc : lXmlFascicoloOut.getListaDocumentiIstruttoria()) {
								boolean trovato = false;
								for (int i = 0; i < pModificaFascicoloIn.getIdDocumento().size(); i++) {
									String idDoc = (pModificaFascicoloIn.getIdDocumento().get(i) != null) ? pModificaFascicoloIn.getIdDocumento().get(i)
											.longValue()
											+ "" : null;
									if (idDoc != null && idDoc.equals(doc.getIdDoc())) {
										trovato = true;
										documentiMap.put(idDoc, doc);
										break;
									}
								}
								//TODO Vers. con omissis																
								if (!trovato && pModificaFascicoloIn.getFlgAppendDocFascIstruttoria() != Flag.SETTED) {
									RebuildedFile lRebuildedFile = new RebuildedFile();
									lRebuildedFile.setIdDocumento(new BigDecimal(doc.getIdDoc()));
									FileInfoBean info = new FileInfoBean();
									info.setTipo(TipoFile.PRIMARIO);
									GenericFile allegatoRiferimento = new GenericFile();
									allegatoRiferimento.setDisplayFilename(doc.getDisplayFileName());
									info.setAllegatoRiferimento(allegatoRiferimento);
									lRebuildedFile.setInfo(info);
									documentiDaRimuovere.add(lRebuildedFile);
								}
							}
							mLogger.debug("documentiMap " + documentiMap);
						}
						Iterator<File> nextFile = null;
						if (pModificaFascicoloIn.getFileAllegati() != null)
							nextFile = pModificaFascicoloIn.getFileAllegati().iterator();

						if (pModificaFascicoloIn.getIdDocumento() != null) {
							for (int i = 0; i < pModificaFascicoloIn.getIdDocumento().size(); i++) {
								AllegatoStoreBean lDocumentoBean = new AllegatoStoreBean();
								// Setto la natura a NULL perchè non lo sto salvando come allegato ma come file primario
								lDocumentoBean.setNatura("P");
								lDocumentoBean.setDescrizione(pModificaFascicoloIn.getDescrizione().get(i));
								lDocumentoBean.setIdDocType(pModificaFascicoloIn.getDocType().get(i));
								lDocumentoBean.setSezionePratica(pModificaFascicoloIn.getSezionePratica().get(i));																																			
								List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
								FolderCustom folderCustom = new FolderCustom();
								folderCustom.setId(String.valueOf(pModificaFascicoloIn.getIdFolderIn().longValue()));
								listaFolderCustom.add(folderCustom);
								lDocumentoBean.setFolderCustom(listaFolderCustom);
								lDocumentoBean.setFlgEreditaPermessi("1");
								lDocumentoBean.setIdFolderEreditaPerm(pModificaFascicoloIn.getIdFolderIn());
								if (pModificaFascicoloIn.getFlgParteDispositivo() != null) {
									Boolean flgParteDispositivo = pModificaFascicoloIn.getFlgParteDispositivo().get(i);
									lDocumentoBean.setFlgParteDispositivo(flgParteDispositivo != null && flgParteDispositivo ? "1" : null);
								}
								if (pModificaFascicoloIn.getIdTask() != null) {
									lDocumentoBean.setIdTask(pModificaFascicoloIn.getIdTask().get(i));
								}
								if (pModificaFascicoloIn.getFlgNoPubbl() != null) {
									Boolean flgNoPubbl = pModificaFascicoloIn.getFlgNoPubbl().get(i);
									lDocumentoBean.setFlgNoPubbl(flgNoPubbl != null && flgNoPubbl ? "1" : null);
								}
								if (pModificaFascicoloIn.getFlgPubblicaSeparato() != null) {
									Boolean flgPubblicaSeparato = pModificaFascicoloIn.getFlgPubblicaSeparato().get(i);
									lDocumentoBean.setFlgPubblicaSeparato(flgPubblicaSeparato != null && flgPubblicaSeparato ? "1" : null);
								}								
								if (pModificaFascicoloIn.getFlgOriginaleCartaceo() != null) {
									Boolean flgOriginaleCartaceo = pModificaFascicoloIn.getFlgOriginaleCartaceo().get(i);
									lDocumentoBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo != null && flgOriginaleCartaceo ? "1" : null);
								}
								if (pModificaFascicoloIn.getFlgCopiaSostitutiva() != null) {
									Boolean flgCopiaSostitutiva = pModificaFascicoloIn.getFlgCopiaSostitutiva().get(i);
									lDocumentoBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva != null && flgCopiaSostitutiva ? "1" : null);
								}								
								if (pModificaFascicoloIn.getFlgDaProtocollare() != null) {
									Boolean flgDaProtocollare = pModificaFascicoloIn.getFlgDaProtocollare().get(i);
									if(flgDaProtocollare != null && flgDaProtocollare) {
										List<TipoNumerazioneBean> listaTipiNumerazioni = new ArrayList<TipoNumerazioneBean>();
										TipoNumerazioneBean lTipoProtocolloGenerale = new TipoNumerazioneBean();								
										lTipoProtocolloGenerale.setCategoria("PG");
										lTipoProtocolloGenerale.setSigla(null);
										listaTipiNumerazioni.add(lTipoProtocolloGenerale);
										lDocumentoBean.setTipoNumerazioni(listaTipiNumerazioni);	
									}
								}								
								String attributi = lXmlUtilitySerializer.bindXmlCompleta(lDocumentoBean);
								String idDoc = (pModificaFascicoloIn.getIdDocumento().get(i) != null) ? pModificaFascicoloIn.getIdDocumento().get(i).longValue() + "" : null;
								if (idDoc != null) {
									DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
									lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
									lUpddocudBean.setFlgtipotargetin("D");
									lUpddocudBean.setIduddocin(pModificaFascicoloIn.getIdDocumento().get(i));
									lUpddocudBean.setAttributiuddocxmlin(attributi);
									final UpddocudImpl lUpddocud = new UpddocudImpl();
									lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lUpddocud.setBean(lUpddocudBean);
									session.doWork(new Work() {

										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lUpddocud.execute(paramConnection);
										}
									});
									StoreResultBean<DmpkCoreUpddocudBean> lUpddocudResult = new StoreResultBean<DmpkCoreUpddocudBean>();
									AnalyzeResult.analyze(lUpddocudBean, lUpddocudResult);
									lUpddocudResult.setResultBean(lUpddocudBean);
									final MarktokenusageImpl lMarktokenusageDoc = new MarktokenusageImpl();
									DmpkLoginMarktokenusageBean lMarktokenusageBeanDoc = new DmpkLoginMarktokenusageBean();
									if (pAurigaLoginBean.getToken() != null)
										lMarktokenusageBeanDoc.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lMarktokenusageBeanDoc.setFlgautocommitin(0);
									lMarktokenusageDoc.setBean(lMarktokenusageBeanDoc);
									session.doWork(new Work() {

										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lMarktokenusageDoc.execute(paramConnection);
										}
									});
									if (lUpddocudResult.isInError()) {
										throw new StoreException(lUpddocudResult);
									} else {
										if (!pModificaFascicoloIn.getIsNull().get(i) && pModificaFascicoloIn.getIsNewOrChanged().get(i)) {
											RebuildedFile lRebuildedFile = new RebuildedFile();
											lRebuildedFile.setFile(nextFile.next());
											lRebuildedFile.setInfo(pModificaFascicoloIn.getInfo().get(i));
											lRebuildedFile.setIdDocumento(new BigDecimal(idDoc));
											lRebuildedFile.setIdTask(pModificaFascicoloIn.getIdTaskVer().get(i));
											versioni.add(lRebuildedFile);
										} else if (pModificaFascicoloIn.getIsNull().get(i)) {
											int pos = 1;
											for (AllegatiOutBean doc : lXmlFascicoloOut.getListaDocumentiIstruttoria()) {
												if (doc.getIdDoc().equals(idDoc)) {
													if (doc.getUri() != null && StringUtils.isNotBlank(doc.getUri())) {
														RebuildedFile lRebuildedFile = new RebuildedFile();
														lRebuildedFile.setIdDocumento(new BigDecimal(idDoc));
														FileInfoBean info = new FileInfoBean();
														info.setTipo(TipoFile.PRIMARIO);
														GenericFile allegatoRiferimento = new GenericFile();
														allegatoRiferimento.setDisplayFilename(doc.getDisplayFileName());
														info.setAllegatoRiferimento(allegatoRiferimento);
														info.setPosizione(pos);
														lRebuildedFile.setInfo(info);
														lRebuildedFile.setPosizione(pos);
														versioniDaRimuovere.add(lRebuildedFile);
													}
												}
												pos++;
											}
										}
									}
								} else {
									DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
									lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
									lAdddocBean.setAttributiuddocxmlin(attributi);
									final AdddocImpl lAdddoc = new AdddocImpl();
									lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lAdddoc.setBean(lAdddocBean);
									session.doWork(new Work() {

										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lAdddoc.execute(paramConnection);
										}
									});
									StoreResultBean<DmpkCoreAdddocBean> lAdddocResult = new StoreResultBean<DmpkCoreAdddocBean>();
									AnalyzeResult.analyze(lAdddocBean, lAdddocResult);
									lAdddocResult.setResultBean(lAdddocBean);
									final MarktokenusageImpl lMarktokenusageAllegato = new MarktokenusageImpl();
									DmpkLoginMarktokenusageBean lMarktokenusageBeanAllegato = new DmpkLoginMarktokenusageBean();
									if (pAurigaLoginBean.getToken() != null)
										lMarktokenusageBeanAllegato.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
									lMarktokenusageBeanAllegato.setFlgautocommitin(0);
									lMarktokenusageAllegato.setBean(lMarktokenusageBeanAllegato);
									session.doWork(new Work() {

										@Override
										public void execute(Connection paramConnection) throws SQLException {
											lMarktokenusageAllegato.execute(paramConnection);
										}
									});
									if (lAdddocResult.isInError()) {
										throw new StoreException(lAdddocResult);
									} else {
										mLogger.debug("idDoc vale " + lAdddocResult.getResultBean().getIddocout());
										if (!pModificaFascicoloIn.getIsNull().get(i)) {
											RebuildedFile lRebuildedFile = new RebuildedFile();
											lRebuildedFile.setFile(nextFile.next());
											lRebuildedFile.setInfo(pModificaFascicoloIn.getInfo().get(i));
											lRebuildedFile.setIdDocumento(lAdddocResult.getResultBean().getIddocout());
											lRebuildedFile.setIdTask(pModificaFascicoloIn.getIdTaskVer().get(i));
											versioni.add(lRebuildedFile);
										}
									}
								}
							}
						}
						//TODO Vers. con omissis
					}
				}

				// Parte di versionamento
				Map<String, String> fileErrors = new HashMap<String, String>();
				fileErrors.putAll(aggiungiFilesInTransaction(pAurigaLoginBean, versioni, session));
				fileErrors.putAll(rimuoviFilesInTransaction(pAurigaLoginBean, versioniDaRimuovere, documentiDaRimuovere, session));
				lModificaFascicoloOut.setFileInErrors(fileErrors);

				session.flush();
				lTransaction.commit();

			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaFascicoloOut.setInError(true);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lModificaFascicoloOut, ((StoreException) e).getError());
					return lModificaFascicoloOut;
				} else {
					lModificaFascicoloOut.setDefaultMessage(e.getMessage());
					return lModificaFascicoloOut;
				}
			} finally {
				HibernateUtil.release(session);
			}

		} catch (Exception e) {
			lModificaFascicoloOut.setInError(true);
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaFascicoloOut, ((StoreException) e).getError());
				return lModificaFascicoloOut;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaFascicoloOut.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lModificaFascicoloOut;
			}
		}

		return lModificaFascicoloOut;
	}

	protected Map<String, String> aggiungiFilesInTransaction(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioni, Session session) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumentoInTransaction(pAurigaLoginBean, lVersionaDocumentoInBean, session);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				fileErrors.put(String.valueOf(lRebuildedFile.getIdDocumento()),
						"Il file " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " non è stato salvato correttamente."
								+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
			}
		}

		return fileErrors;
	}

	protected Map<String, String> rimuoviFilesInTransaction(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioniDaRimuovere,
			List<RebuildedFile> allegatiDaRimuovere, Session session) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioniDaRimuovere) {
			try {
				rimuoviVersioneDocumentoInTransaction(lRebuildedFile, pAurigaLoginBean, session);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				fileErrors.put(
						String.valueOf(lRebuildedFile.getIdDocumento()),
						"Il file " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " non è stato eliminato."
								+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
			}
		}

		for (RebuildedFile lRebuildedFile : allegatiDaRimuovere) {
			try {
				rimuoviDocumentoInTransaction(lRebuildedFile, pAurigaLoginBean, session);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				fileErrors.put(String.valueOf(lRebuildedFile.getIdDocumento()), "Il documento con id. " + lRebuildedFile.getIdDocumento()
						+ " non è stato eliminato." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
			}
		}

		return fileErrors;
	}

	public void rimuoviVersioneDocumentoInTransaction(RebuildedFile lRebuildedFile, AurigaLoginBean pAurigaLoginBean, Session session) throws Exception {
		try {

			DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
			lDel_ud_doc_verBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lDel_ud_doc_verBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			lDel_ud_doc_verBean.setFlgtipotargetin("V");
			lDel_ud_doc_verBean.setNroprogrverio(new BigDecimal(-1)); // in questo modo annullo tutte le versioni presenti sula file e non solo l'ultima
			lDel_ud_doc_verBean.setFlgcancfisicain(new Integer(0));
			lDel_ud_doc_verBean.setIduddocin(lRebuildedFile.getIdDocumento());

			final Del_ud_doc_verImpl store = new Del_ud_doc_verImpl();
			store.setBean(lDel_ud_doc_verBean);
			mLogger.debug("Chiamo la del_Ud_Doc_Ver");

			LoginService lLoginService = new LoginService();
			lLoginService.login(pAurigaLoginBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});

			StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = new StoreResultBean<DmpkCoreDel_ud_doc_verBean>();
			AnalyzeResult.analyze(lDel_ud_doc_verBean, lStoreResultBean);
			lStoreResultBean.setResultBean(lDel_ud_doc_verBean);

			final MarktokenusageImpl lMarktokenusage = new MarktokenusageImpl();
			DmpkLoginMarktokenusageBean lMarktokenusageBean = new DmpkLoginMarktokenusageBean();
			if (pAurigaLoginBean.getToken() != null)
				lMarktokenusageBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lMarktokenusageBean.setFlgautocommitin(0);
			lMarktokenusage.setBean(lMarktokenusageBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lMarktokenusage.execute(paramConnection);
				}
			});

			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean);
			}

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			if (e instanceof StoreException) {
				errorMessage = ((StoreException) e).getError() != null ? ((StoreException) e).getError().getDefaultMessage() : ((StoreException) e)
						.getMessage();
			}
			mLogger.error("Errore " + errorMessage, e);
			throw new Exception(errorMessage);
		}
	}

	private void rimuoviDocumentoInTransaction(RebuildedFile lRebuildedFile, AurigaLoginBean pAurigaLoginBean, Session session) throws Exception {

		try {

			final ConnectionWrapper lConnectionWrapper = new ConnectionWrapper();
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lConnectionWrapper.setConnection(paramConnection);
				}
			});

			BigDecimal idUd = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = lConnectionWrapper.getConnection().prepareStatement("select t.ID_UD from DMT_DOCUMENTS t where t.ID_DOC = ?");
				pstmt.setBigDecimal(1, lRebuildedFile.getIdDocumento());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					idUd = rs.getBigDecimal(1);
				}
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (Exception e) {
				}
				;
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) {
				}
				;
			}

			if (idUd != null) {

				DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
				lDel_ud_doc_verBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lDel_ud_doc_verBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lDel_ud_doc_verBean.setFlgtipotargetin("U");
				lDel_ud_doc_verBean.setNroprogrverio(null);
				lDel_ud_doc_verBean.setFlgcancfisicain(new Integer(0)); // faccio la cancellazione logica
				lDel_ud_doc_verBean.setIduddocin(idUd);

				final Del_ud_doc_verImpl store = new Del_ud_doc_verImpl();
				store.setBean(lDel_ud_doc_verBean);
				mLogger.debug("Chiamo la del_Ud_Doc_Ver");

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = new StoreResultBean<DmpkCoreDel_ud_doc_verBean>();
				AnalyzeResult.analyze(lDel_ud_doc_verBean, lStoreResultBean);
				lStoreResultBean.setResultBean(lDel_ud_doc_verBean);

				final MarktokenusageImpl lMarktokenusage = new MarktokenusageImpl();
				DmpkLoginMarktokenusageBean lMarktokenusageBean = new DmpkLoginMarktokenusageBean();
				if (pAurigaLoginBean.getToken() != null)
					lMarktokenusageBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lMarktokenusageBean.setFlgautocommitin(0);
				lMarktokenusage.setBean(lMarktokenusageBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						lMarktokenusage.execute(paramConnection);
					}
				});

				if (lStoreResultBean.isInError()) {
					throw new StoreException(lStoreResultBean);
				}

			} else {
				mLogger.debug("Non è stato possibile recuperare l'unita documentaria relativa al documento da eliminare -> idDoc = "
						+ lRebuildedFile.getIdDocumento());
				throw new Exception("Non è stato possibile recuperare l'unita documentaria relativa al documento da eliminare");
			}

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			if (e instanceof StoreException) {
				errorMessage = ((StoreException) e).getError() != null ? ((StoreException) e).getError().getDefaultMessage() : ((StoreException) e)
						.getMessage();
			}
			mLogger.error("Errore " + errorMessage, e);
			throw new Exception(errorMessage);
		}
	}

	public VersionaDocumentoOutBean versionaDocumentoInTransaction(AurigaLoginBean pAurigaLoginBean, VersionaDocumentoInBean lVersionaDocumentoInBean,
			Session session) throws Exception {
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		try {

			String uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			mLogger.debug("Salvato " + uriVer);

			FileStoreBean lFileStoreBean = new FileStoreBean();
			lFileStoreBean.setUri(uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());

			try {
				BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean, lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
			} catch (Exception e) {
				mLogger.warn(e);
			}

			lFileStoreBean.setIdTask(lVersionaDocumentoInBean.getIdTask());
			
			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				mLogger.debug("attributiVerXml " + lStringXml);
			} catch (Exception e) {
				mLogger.warn(e);
			}

			DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
			lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lAddverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
			lAddverdocBean.setAttributiverxmlin(lStringXml);

			final AddverdocImpl store = new AddverdocImpl();
			store.setBean(lAddverdocBean);
			mLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);

			LoginService lLoginService = new LoginService();
			lLoginService.login(pAurigaLoginBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});

			StoreResultBean<DmpkCoreAddverdocBean> lStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
			AnalyzeResult.analyze(lAddverdocBean, lStoreResultBean);
			lStoreResultBean.setResultBean(lAddverdocBean);

			final MarktokenusageImpl lMarktokenusage = new MarktokenusageImpl();
			DmpkLoginMarktokenusageBean lMarktokenusageBean = new DmpkLoginMarktokenusageBean();
			if (pAurigaLoginBean.getToken() != null)
				lMarktokenusageBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lMarktokenusageBean.setFlgautocommitin(0);
			lMarktokenusage.setBean(lMarktokenusageBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lMarktokenusage.execute(paramConnection);
				}
			});

			if (lStoreResultBean.isInError()) {
				mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
				mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
				mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
				BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lStoreResultBean);
				return lVersionaDocumentoOutBean;
			}

		} catch (Throwable e) {

			if (StringUtils.isNotBlank(e.getMessage())) {
				mLogger.error(e.getMessage(), e);
				lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
			} else {
				mLogger.error("Errore generico", e);
				lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
			}
			return lVersionaDocumentoOutBean;
		}

		return lVersionaDocumentoOutBean;
	}

	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}

	/***********************************************************************************
	 * 
	 * Funzioni utilizzate dai WS
	 * 
	 ************************************************************************************/

	/*
	 * WS per creare un nuovo fascicolo
	 * 
	 * @param XML : XML con gli attributi del fascicolo da creare
	 * 
	 * @return IdFolderOut : id del folder creato
	 * 
	 * @exception Exception
	 * 
	 * Viene invocato dal ws WSAddFolder.
	 */

	@Operation(name = "salvaFascicoloWS")
	public SalvaFascicoloOut salvaFascicoloWS(AurigaLoginBean pAurigaLoginBean, String xmlIn) throws Exception {

		DmpkCoreAddfolderBean bean = new DmpkCoreAddfolderBean();

		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		bean.setAttributixmlin(xmlIn);

		Addfolder store = new Addfolder();
		StoreResultBean<DmpkCoreAddfolderBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);
		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			SalvaFascicoloOut lSalvaFascicoloOut = new SalvaFascicoloOut();
			BeanUtilsBean2.getInstance().copyProperties(lSalvaFascicoloOut, lStoreResultBean);
			return lSalvaFascicoloOut;
		}

		SalvaFascicoloOut lSalvaFascicoloOut = new SalvaFascicoloOut();
		lSalvaFascicoloOut.setIdFolderOut(lStoreResultBean.getResultBean().getIdfolderout());

		return lSalvaFascicoloOut;
	}

	/*
	 * WS per modificare i metadati di un fascicolo
	 * 
	 * @param idFolder : riferimento del folder
	 * 
	 * @param XML : XML con gli attributi da modicare
	 * 
	 * @return URI : riferimento uri
	 * 
	 * @exception Exception
	 * 
	 * Viene invocato dal ws WSUpdFolder.
	 */
	@Operation(name = "modificaFascicoloWS")
	public ModificaFascicoloOut modificaFascicoloWS(AurigaLoginBean pAurigaLoginBean, String idFolderIn, String attributiXmlIn) throws Exception {

		DmpkCoreUpdfolderBean bean = new DmpkCoreUpdfolderBean();

		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		if (idFolderIn != null && !idFolderIn.equalsIgnoreCase(""))
			bean.setIdfolderin(new BigDecimal(idFolderIn));

		bean.setAttributixmlin(attributiXmlIn);

		Updfolder store = new Updfolder();
		StoreResultBean<DmpkCoreUpdfolderBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);
		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			ModificaFascicoloOut lModificaFascicoloOut = new ModificaFascicoloOut();
			BeanUtilsBean2.getInstance().copyProperties(lModificaFascicoloOut, lStoreResultBean);
			return lModificaFascicoloOut;
		}

		ModificaFascicoloOut lModificaFascicoloOut = new ModificaFascicoloOut();
		lModificaFascicoloOut.setUriPerAggiornamentoContainer(lStoreResultBean.getResultBean().getUrixaggcontainerout());

		return lModificaFascicoloOut;
	}

	/*
	 * WS per cercare i documenti/folder
	 * 
	 * @param FindRepositoryObjectBean : tutti i valori del bean
	 * 
	 * @return DettagliCercaInFolderOut : XML contenente attributi e dati del folder CercaInFolderIO, e se questo e' NULL quelli dell'eventuale workspace
	 * specificato nei criteri avanzati di ricerca
	 * 
	 * @return NroRecInPaginaOut : E' il numero di record nella pagina
	 * 
	 * @return NroTotRecOut : E' il n.ro di record complessivi trovati
	 * 
	 * @return Percorsoricercaxmlio : Lista con nomi e id. (da quella di livello più alto in giù) delle cartelle/folder (libreria inclusa) che compongono il
	 * percorso in cui si cerca ora (ovvero CercaInFolderIO).
	 * 
	 * @return ResultOut : Lista delle unita' documentarie e folder trovati
	 * 
	 * @exception Exception
	 * 
	 * Viene invocato dal ws WSTrovaDocFolder.
	 */
	@Operation(name = "trovaDocFolderWS")
	public TrovaDocFolderOut trovaDocFolderWS(AurigaLoginBean pAurigaLoginBean, FindRepositoryObjectBean pBeanIn) throws Exception {

		DmpkCoreTrovarepositoryobjBean bean = new DmpkCoreTrovarepositoryobjBean();
		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		if (pBeanIn.getIdFolderSearchIn() != null)
			bean.setCercainfolderio(new BigDecimal(pBeanIn.getIdFolderSearchIn()));

		if (pBeanIn.getFlgSubfoderSearchIn() != null && !pBeanIn.getFlgSubfoderSearchIn().equalsIgnoreCase(""))
			bean.setFlgcercainsubfolderio(Integer.valueOf(pBeanIn.getFlgSubfoderSearchIn()));

		bean.setColorderbyio(pBeanIn.getColsOrderBy());
		bean.setColtoreturnin(pBeanIn.getColsToReturn());
		bean.setCriteriavanzatiio(pBeanIn.getAdvancedFilters());
		bean.setCriteripersonalizzatiio(pBeanIn.getCustomFilters());
		bean.setFinalitain(pBeanIn.getFinalita());
		bean.setFlgbatchsearchin(pBeanIn.getOnline());
		bean.setFlgdescorderbyio(pBeanIn.getFlgDescOrderBy());
		bean.setFlgfmtestremiregio(pBeanIn.getFormatoEstremiReg());
		bean.setFlgsenzapaginazionein(pBeanIn.getFlgSenzaPaginazione());
		bean.setFlgudfolderio(pBeanIn.getFlgUdFolder());
		bean.setNropaginaio(pBeanIn.getNumPagina());
		bean.setOverflowlimitin(pBeanIn.getOverflowlimitin());
		bean.setPercorsoricercaxmlio(pBeanIn.getPercorsoRicerca());
		bean.setBachsizeio(pBeanIn.getNumRighePagina());

		Trovarepositoryobj store = new Trovarepositoryobj();
		StoreResultBean<DmpkCoreTrovarepositoryobjBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);
		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			TrovaDocFolderOut lTrovaDocFolderOut = new TrovaDocFolderOut();
			BeanUtilsBean2.getInstance().copyProperties(lTrovaDocFolderOut, lStoreResultBean);
			return lTrovaDocFolderOut;
		}

		TrovaDocFolderOut lTrovaDocFolderOut = new TrovaDocFolderOut();
		lTrovaDocFolderOut.setDettagliCercaInFolderOut(lStoreResultBean.getResultBean().getDettaglicercainfolderout());
		lTrovaDocFolderOut.setNroRecInPaginaOut(lStoreResultBean.getResultBean().getNrorecinpaginaout());
		lTrovaDocFolderOut.setNroTotRecOut(lStoreResultBean.getResultBean().getNrototrecout());
		lTrovaDocFolderOut.setPercorsoRicercaXMLOut(lStoreResultBean.getResultBean().getPercorsoricercaxmlio());
		lTrovaDocFolderOut.setResultOut(lStoreResultBean.getResultBean().getResultout());
		
		lTrovaDocFolderOut.setDefaultMessage(lStoreResultBean.getDefaultMessage());
		lTrovaDocFolderOut.setErrorContext(lStoreResultBean.getErrorContext());
		lTrovaDocFolderOut.setErrorCode(lStoreResultBean.getErrorCode());
		

		return lTrovaDocFolderOut;
	}
}