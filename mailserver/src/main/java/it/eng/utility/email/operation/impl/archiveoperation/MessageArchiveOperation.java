package it.eng.utility.email.operation.impl.archiveoperation;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.ProtocolloAutomaticoConfigBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.database.dao.DaoTEmailMgo;
import it.eng.aurigamailbusiness.database.dao.DaoTParameters;
import it.eng.aurigamailbusiness.database.dao.MailProcessorService;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.util.ListUtil;
import it.eng.utility.FileUtil;
import it.eng.utility.database.MailboxConfigDefaultValue;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.operation.AbstractMessageOperationArchiver;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.operation.impl.archiveoperation.composer.InteropManager;
import it.eng.utility.email.operation.impl.archiveoperation.storage.StorageDirector;
import it.eng.utility.email.operation.impl.archiveoperation.utils.AccountUtils;
import it.eng.utility.email.operation.impl.archiveoperation.utils.BeanUtils;
import it.eng.utility.email.operation.impl.archiveoperation.utils.Constants;
import it.eng.utility.email.operation.impl.archiveoperation.utils.DaoUtils;
import it.eng.utility.email.operation.impl.archiveoperation.utils.Utilities;
import it.eng.utility.email.operation.impl.interoperation.AbstractInterbean;
import it.eng.utility.email.process.exception.MailServerException;
import it.eng.utility.email.reader.config.MailBoxConfigKey;

/**
 * operazione di archiviazione delle email
 * 
 * @author jravagnan
 * 
 */

@MessageOperation(description = "Effettua tutte le logiche di archiviazione del messaggio email", name = "MessageArchiveOperation")
public class MessageArchiveOperation extends AbstractMessageOperationArchiver {

	@ConfigOperation(title = "Stringa di connessione a database", name = "messagearchiveoperation.databaseurl", description = "Indica la stringa di connessione a database")
	private String dburl = null;

	private Logger log = LogManager.getLogger(MessageArchiveOperation.class);

	private Map<String, TParametersBean> parametri = null;

	private String idCasella = null;
	
	private static final String AUTO_PROTOCOL = "ProtocolloAutomaticoConfigBean";
	private static final String ERROR_FILE_NOT_FOUND = "populateAttachEmailMgoBean - File non trovato";

	private StorageDirector storageDirect = new StorageDirector();

	@Override
	public MessageArchiveBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione
			// corrente
			throw new InterruptedException();
		}

		log.debug("Invocata la MessageArchiveOperation per il messaggio con id: " + message.getHeaderinfo().getMessageid());
		
		String idMailbox = getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailboxid() : 
			getMailreceiverPop3().getConfigbean().getMailboxid();
		idCasella = idMailbox;
		// TODO: gestire il dburl per decidere la banca dati su cui salvare
		MessageArchiveBean result = null;
		// partendo dalla mailbox ricavo l'idaccount di interesse
		String idAccount = AccountUtils.retrieveIdAccount(idCasella);
		// verifico se esiste già una mail per la stessa casella e lo stesso
		// message-id
		if (log.isInfoEnabled()) {
			log.info("Verifico se esiste già una mail con lo stesso message-id");
		}
		DaoTEmailMgo daoTEmail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
		TFilterFetch<TEmailMgoBean> filterFetch = new TFilterFetch<>();
		TEmailMgoBean beanFiltro = new TEmailMgoBean();
		beanFiltro.setIdCasella(idAccount);
		beanFiltro.setMessageId(message.getHeaderinfo().getMessageid());
		filterFetch.setFilter(beanFiltro);
		List<TEmailMgoBean> listaEmails = daoTEmail.search(filterFetch).getData();
		if (ListUtil.isNotEmpty(listaEmails)) {
			// trovata già una mail con lo stesso message-id
			if (log.isInfoEnabled()) {
				log.info("Esiste già una mail con lo stesso message-id");
			}
			// blocco l'archiviazione della mail
			result = new MessageArchiveBean();
			result.setIsok(false);
			result.setDuplicated(true);
			return result;
		}

		String uri = null;
		if (message.getUri() != null) {
			log.debug("Inizio archiviazione in storage");
			// ricavo l'uri relativo alla email da archiviare
			uri = storageDirect.process(message, (getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailboxid() : getMailreceiverPop3().getConfigbean().getMailboxid()));
			log.debug("Fine archiviazione in storage" + ": " + uri);
		}
		if (uri != null) {
			// istanzio il processor di elaborazione della email
			// VALORIZZO I PARAMETRI CHE SERVIRANNO PER LA PROFILAZIONE
			// NB: per il dialogo con i servizi REST l'inizializzazione del
			// client
			// viene fatta nella Servlet di partenza del progetto(mailui) che
			// chiama il mailserver
			getParametersValue();
			Long size = getMailSize(uri);
			result = this.process(message, idAccount, uri, parametri, size, idCasella);
		}
		return result;
	}

	/**
	 * metodo che legge tutti i parametri di configurazione e li inserisce in una mappa
	 */
	private void getParametersValue() {
		parametri = new ConcurrentHashMap<String, TParametersBean>();
		TFilterFetch<TParametersBean> filter = new TFilterFetch<TParametersBean>();
		try {
			List<TParametersBean> listaRis = ((DaoTParameters) DaoFactory.getDao(DaoTParameters.class)).getAllParameters(filter).getData();
			for (TParametersBean parametro : listaRis) {
				parametri.put(parametro.getParKey(), parametro);
			}
		} catch (Exception e) {
			log.error("Impossibile caricare i parametri, si useranno le impostazioni di default: ", e);
		}
	}

	/**
	 * ricavo la dimensione del file
	 * 
	 * @param uri
	 * @return
	 * @throws Exception
	 */

	private Long getMailSize(String uri) throws Exception {
		Long ret = null;
		File temporaneo = null;
		try {
			temporaneo = storageDirect.retrieveFile(uri, (getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailboxid() : getMailreceiverPop3().getConfigbean().getMailboxid()));
			ret = temporaneo.length();
		} catch (Exception e) {
			log.error("impossibile calcolare la dimensione dell'email", e);
			throw e;
		} finally {
			FileUtil.deleteFile(temporaneo);
		}
		return ret;
	}

	public MessageArchiveBean process(MessageInfos message, String idAccount, String uri, Map<String, TParametersBean> parametri, Long size, String idCasella)
			throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		EmailBean bean = new EmailBean();
		String idMessagio = message.getHeaderinfo().getMessageid();
		log.debug("MessageArchive - processamento della mail con message id: " + idMessagio);
		try {
			if (uri != null) {
				Utilities util = new Utilities();
				// SALVATAGGIO EMAIL
				TEmailMgoBean mgoBean = BeanUtils.populateMgoBean(message, uri, idAccount, size);
				bean.setMail(mgoBean);
				// ESEGUO LE LOGICHE NEL CASO SIA RICEVUTA
				String idFruitore = null;
				if (message.isRicevuta() || message.isDeliveryStatusNotification()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
						throw new InterruptedException();
					}
					log.debug("la mail è una ricevuta, effettuo le logiche relative");
					idFruitore = util.ricavaFruitoreCasella(idAccount);
					Boolean cancellaPecInviata = null;
					if (this.getMailreceiver() != null)
						cancellaPecInviata = Boolean.parseBoolean(this.getMailreceiver().getConfigbean().getMailconfig()
							.getProperty(MailBoxConfigKey.MAILBOX_DELETE_SENT_PEC.keyname(), MailboxConfigDefaultValue.DEFAULT_DELETE_SENT_PEC.toString()));
					if (this.getMailreceiverPop3() != null)
						cancellaPecInviata = Boolean.parseBoolean(this.getMailreceiverPop3().getConfigbean().getMailconfig()
							.getProperty(MailBoxConfigKey.MAILBOX_DELETE_SENT_PEC.keyname(), MailboxConfigDefaultValue.DEFAULT_DELETE_SENT_PEC.toString()));
					bean = BeanUtils.eseguiLogicheRicevuta(bean, message, idFruitore, idCasella, this.maxTryOperation, this.currentNumTryOperation,
							cancellaPecInviata);
				}
				if (message.isDispositionNotificationDisplayed()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
						throw new InterruptedException();
					}
					log.debug("la mail è una richiesta di lettura, effettuo le logiche relative");
					bean = BeanUtils.eseguiLogicheRichiestaLettura(bean, message, idCasella);
				}
				if (message.getRichiestaAvvenutaLetturaWithPrincipalMail()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
						throw new InterruptedException();
					}
					// la mail richiede l'invio della conferma di avvenuta lettura, al momento non implementato
					bean.getMail().setFlgRichConfLettura(true);
				}
				log.debug("TEmailMgoBean creato");
				if (util.salvataggioInRubricaMittente(message, parametri)) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
						throw new InterruptedException();
					}
					if (idFruitore == null) {
						idFruitore = util.ricavaFruitoreCasella(idAccount);
					}
					TRubricaEmailBean voceRubricaMitt = BeanUtils.populateRubricaMittente(mgoBean.getAccountMittente(), idFruitore, message);
					bean.setVoceRubricaMittente(voceRubricaMitt);
					log.debug("TRubricaEmailBean mittente creato");
				}
				if (BeanUtils.isNotificaInteroperabile(message)) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
						throw new InterruptedException();
					}
					log.debug("la mail è interoperabile, effettuo le logiche relative");
					List<Object> interbeanlist = message.getOpResultWithStartName(Constants.OPERAZIONI_INTEROPERABILITA);
					AbstractInterbean interbean = (AbstractInterbean) (interbeanlist.get(0));
					bean = new InteropManager(interbean, mgoBean.getIdEmail(), mgoBean.getAccountMittente()).gestisciNotificheInteroperabili(bean, idCasella);
				}
				// SALVATAGGIO REL_FOLDER_EMAIL
				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
					throw new InterruptedException();
				}
				bean = BeanUtils.populateRelEmailFolderBean(message, idAccount, bean, parametri, idCasella);
				log.debug("TRelEmailFolderBean creato");
				// SALVATAGGIO ATTACHMENT
				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
					throw new InterruptedException();
				}
				bean = BeanUtils.populateAttachEmailMgoBean(message, bean);
				log.debug("List<TAttachEmailMgoBean>  creato");
				// SALVATAGGIO DESTINATARI E AGGIORNAMENTO RUBRICA TRAMITE PROFILAZIONE
				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
					throw new InterruptedException();
				}
				List<TDestinatariEmailMgoBean> destinatari = BeanUtils.populateDestinatari(message, mgoBean.getIdEmail());
				// Prima di salvare i destinatari valuto se sia richiesto anche il salvataggio in rubrica
				// se sì salvo in rubrica e poi aggiorno il valore di rubrica del destinatario
				// prima di salvare il destinatario stesso
				if (util.salvataggioInRubricaDestinatari(message, parametri)) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
						throw new InterruptedException();
					}
					if (idFruitore == null) {
						idFruitore = util.ricavaFruitoreCasella(idAccount);
					}
					List<TRubricaEmailBean> vociRubricaDestinatari = BeanUtils.populateRubricaDestinatari(destinatari, idFruitore);
					Map<String, String> relEmailRubrica = new ConcurrentHashMap<>();
					for (TRubricaEmailBean voce : vociRubricaDestinatari) {
						// hashmap d'appoggio per ottenere il valore della voce rubrica
						// relativa al destinatario
						relEmailRubrica.put(voce.getAccountEmail().trim(), voce.getIdVoceRubricaEmail());
					}
					bean.setVociRubricaDestinatari(vociRubricaDestinatari);
					log.debug("List<TRubricaEmailBean> creata");
					for (TDestinatariEmailMgoBean destinatario : destinatari) {
						destinatario.setIdVoceRubricaDest(relEmailRubrica.get(destinatario.getAccountDestinatario().trim()));
					}
					bean.setDestinatari(destinatari);
					// se non bisogna salvare i destinatari in rubrica li salvo direttamente
				} else {
					bean.setDestinatari(destinatari);
				}
				log.debug("List<TDestinatariEmailMgoBean> creata");
			}
			// Invoco il servizio rest di salvataggio delle email
			log.debug("salvo la email con idMessage: " + bean.getMail().getMessageId());
			salvaEmail(bean, idCasella);
			//Parte relativa alla protocollazione automatica.
			if((getMailreceiver() != null && Boolean.parseBoolean(this.getMailreceiver().getConfigbean().getMailconfig()
					.getProperty(MailBoxConfigKey.MAILBOX_AUTO_PROTOCOL.keyname(), MailboxConfigDefaultValue.DEFAULT_AUTO_PROTOCOL.toString()))) 
				||
				(getMailreceiverPop3() != null && Boolean.parseBoolean(this.getMailreceiverPop3().getConfigbean().getMailconfig()
						.getProperty(MailBoxConfigKey.MAILBOX_AUTO_PROTOCOL.keyname(), MailboxConfigDefaultValue.DEFAULT_AUTO_PROTOCOL.toString())))) {
				log.debug("Inizio procedura di protocollazione automatica per la mail con IdMessage: " + bean.getMail().getMessageId() +
						" associata alla casella mail: " + idCasella);
				protocollazioneEmail(bean, idCasella);
				log.debug("Fine procedura di protocollazione per la mail: " + bean.getMail().getMessageId());
			} 
		} catch (Exception e) {
			if (uri != null) {
				try {
					new StorageDirector().delete(uri, idCasella);
				} catch (Exception e1) {
					log.error("impossibile cancellare il file con uri: " + uri + " a causa della seguente eccezione: ", e);
				}
			}
			log.error("MessageArchive - Eccezione nel processamento della mail con message id: " + idMessagio, e);
			// Diego L. 
			// se non viene trovato il file per qualche motivo, riprocesso in automatico la mail
//			if (e instanceof MailServerException &&
//					e.getLocalizedMessage().equalsIgnoreCase(ERROR_FILE_NOT_FOUND)) {
//				FactoryMailBusiness.getInstance().riprocessaMailboxMessage(idCasella, idMessagio);				
//			}
			throw e;				
		}
		return new MessageArchiveBean(true, uri);
	}

	private EmailBean salvaEmail(EmailBean bean, String idCasella) throws Exception {
		SubjectBean sb = new SubjectBean();
		sb.setIdDominio(DaoUtils.getDominioToUse(idCasella));
		SubjectUtil.subject.set(sb);
		MailProcessorService saveService = new MailProcessorService();
		return saveService.save(bean);
	}

	private void protocollazioneEmail(EmailBean bean, String idCasella) throws Exception {
		ProtocolloAutomaticoConfigBean autoProtConfig = (ProtocolloAutomaticoConfigBean) SpringAppContext.getContext().getBean(AUTO_PROTOCOL);
		if(autoProtConfig != null) {
			MailProcessorService protService = new MailProcessorService();
			protService.registrazioneAutoProt(autoProtConfig, bean.getMail());
		} else {
			log.warn("Bean ProtocolloAutomaticoConfigBean non configurato correttamente.");
		}
	}

	public String getDburl() {
		return dburl;
	}

	public void setDburl(String dburl) {
		this.dburl = dburl;
	}

	public String getIdCasella() {
		return idCasella;
	}

}
