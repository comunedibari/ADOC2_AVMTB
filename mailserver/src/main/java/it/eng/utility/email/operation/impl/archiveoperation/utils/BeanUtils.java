package it.eng.utility.email.operation.impl.archiveoperation.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.Categoria;
import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TFolderCaselleBean;
import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.bean.TValDizionarioBean;
import it.eng.aurigamailbusiness.bean.TipoRicevuta;
import it.eng.aurigamailbusiness.database.dao.DaoMailboxAccount;
import it.eng.aurigamailbusiness.database.dao.DaoTAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.dao.DaoTDestinatariEmailMgo;
import it.eng.aurigamailbusiness.database.dao.DaoTEmailMgo;
import it.eng.aurigamailbusiness.database.dao.DaoTFolderCaselle;
import it.eng.aurigamailbusiness.database.dao.DaoTRelEmailFolder;
import it.eng.aurigamailbusiness.database.dao.DaoTRubricaEmail;
import it.eng.aurigamailbusiness.database.utility.send.FolderEmail;
import it.eng.aurigamailbusiness.database.utility.send.InputOutput;
import it.eng.aurigamailbusiness.database.utility.send.SendUtils;
import it.eng.aurigamailbusiness.database.utility.send.TipoDestinatario;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.core.business.KeyGenerator;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;
import it.eng.module.foutility.beans.generated.Response;
import it.eng.module.foutility.beans.generated.ResponseFileDigestType;
import it.eng.module.foutility.beans.generated.ResponseFormatRecognitionType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.util.ListUtil;
import it.eng.utility.email.config.MailServerSpringAppContext;
import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.operation.impl.archiveoperation.composer.InteropManager;
import it.eng.utility.email.operation.impl.archiveoperation.composer.InteropVerificationData;
import it.eng.utility.email.operation.impl.archiveoperation.composer.ReceiptManager;
import it.eng.utility.email.operation.impl.interoperation.AbstractInterbean;
import it.eng.utility.email.operation.impl.interoperation.InteroperabilitaType;
import it.eng.utility.email.operation.impl.signeroperation.SignerAttachmentsBean;
import it.eng.utility.email.operation.impl.signeroperation.SignerMailBean;
import it.eng.utility.email.process.exception.MailServerException;

/**
 * classe di utilità per il popolamento dei singoli bean relativi all'archiviazione delle email
 * 
 * @author jravagnan
 * 
 */
public class BeanUtils {

	private static Logger logger = LogManager.getLogger(BeanUtils.class);

	private static final String AVVERTIMENTO_ATTACHMENT_MALFIRMATI = "Non tutti gli attachment sono firmati correttamente";

	private static final String PARAMETER_SPECIFIC_SENDER = "SPECIFIC_SENDER_CONFIG";

	private static final String PARAMETER_SPECIFIC_FOLDER = "SPECIFIC_FOLDER_CONFIG";

	private static final String ERRORE_CREAZIONE_BEAN = "Impossibile creare il bean della casella";

	private static final String WILDCARD = "*";

	public static TEmailMgoBean populateMgoBean(MessageInfos message, String uri, String idAccountCasella, Long mailSize) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		TEmailMgoBean bean = new TEmailMgoBean();
		try {
			// per l'indicizzazione su Lucene elimino il carattere
			// speciale 'meno' dall'UID
			bean.setIdEmail(KeyGenerator.gen().replace("-", ""));
			bean.setMessageId(message.getHeaderinfo().getMessageid());
			bean.setIdCasella(idAccountCasella);
			bean.setFlgIo(InputOutput.INGRESSO.getValue());
			bean.setCategoria(identificaCategoria(message).getValue());
			bean.setDimensione(mailSize);
			bean.setUriEmail(uri);
			if (message.isSpam()) {
				bean.setFlgSpam(true);
			} else {
				bean.setFlgSpam(false);
			}
			bean.setNroAllegati((short) message.getAttachmentsWithPrincipalMail().size());
			bean.setNroAllegatiFirmati(getNroAttachFirmati(message));
			bean.setFlgEmailFirmata(false);
			if (StringUtils.isNotBlank(message.getMittenteWithPrincipalMail())) {
				bean.setAccountMittente(message.getMittenteWithPrincipalMail().trim());
			}
			// pulizia di eventuali caratteri non ASCII prima dell'inserimento in database
			bean.setOggetto(Util.clearNotASCII(message.getSubjectWithPrincipalMail()));

			String corpo = null;
			if (StringUtils.isNotBlank(message.getBodyHtmlWithPrincipalMail())) {
				// ricerca eventuali tag br sostituendoli con \n
				corpo = Util.clearCarriageReturn(message.getBodyHtmlWithPrincipalMail());
			} else {
				corpo = message.getBodyTextPlainOnlyWithPrincipalMail();
			}

			bean.setCorpo(Util.clearNotASCII(corpo));
		} catch (Exception e) {
			logger.error("Eccezione nel metodo populateMgoBean", e);
			throw e;
		}
		bean.setFlgInviataRisposta(false);
		// gestiamo la priorità accettando valori numerici o valori H,N,L
		if (StringUtils.isNotEmpty(message.getPriorityWithPrincipalMail())) {
			try {
				bean.setLivPriorita(new Long(message.getPriorityWithPrincipalMail().substring(0, 1)));
			} catch (NumberFormatException nfe) {
				if (message.getPriorityWithPrincipalMail().substring(0, 1).equalsIgnoreCase("H")) {
					bean.setLivPriorita(1L);
				} else {
					if (message.getPriorityWithPrincipalMail().substring(0, 1).equalsIgnoreCase("N")) {
						bean.setLivPriorita(3L);
					} else {
						if (message.getPriorityWithPrincipalMail().substring(0, 1).equalsIgnoreCase("L")) {
							bean.setLivPriorita(5L);
						} else {
							// NON FACCIO NIENTE, NON SO INTERPRETARE LA
							// PRIORITA'
						}
					}
				}
			} catch (StringIndexOutOfBoundsException sbe) {
				// NON FACCIO NIENTE, NON SO INTERPRETARE LA PRIORITA'
			}
		}
		// pongo sempre a false la richiesta di conferma
		bean.setFlgRichConferma(false);
		bean.setFlgInoltrata(false);
		// i flag sono a false perchè riguardano solo le mail inviate
		bean.setFlgNotifInteropAgg(false);
		bean.setFlgNotifInteropAnn(false);
		bean.setFlgNotifInteropConf(false);
		bean.setFlgNotifInteropEcc(false);
		bean.setTsRicezione(new Date());
		bean.setTsInvio(message.getHeaderinfo().getSendDate());
		bean.setTsInvioClient(message.getHeaderinfo().getSendDate());
		return bean;
	}

	public static EmailBean eseguiLogicheRicevuta(EmailBean bean, MessageInfos message, String idFruitore, String idCasella, Short maxTryOperation,
			Short currentNumTryOperation, Boolean cancellaPecInviata) throws Exception {

		if (!message.isDeliveryStatusNotification()) {
			// SETTO IL TIPO DI RICEVUTA (COMPLETA, SINTETICA, BREVE, NULLA)
			if (message.getDaticert() != null && message.getDaticert().getDati() != null && message.getDaticert().getDati().getRicevuta() != null) {
				bean.getMail().setFlgRicevutaCbs(retrieveReceiptType(message.getDaticert().getDati().getRicevuta().getTipo()));
			}
			// SALVO EVENTUALE ERRORE
			if (message.getDaticert() != null && message.getDaticert().getDati() != null
					&& StringUtils.isNotBlank(message.getDaticert().getDati().getErroreEsteso())) {
				bean.getMail().setErrMessageRicPec(message.getDaticert().getDati().getErroreEsteso());
			}
		}
		List<TEmailMgoBean> risu = null;
		String messageIdRif = null;
		try {
			if (message.isDeliveryStatusNotification()) {
				// Se e' una delivery salvo il message id interno
				if (message.getSubinfos() != null && !message.getSubinfos().isEmpty()) {
					if (message.getSubinfos().get(0).isPec()) {
						if (message.getSubinfos().get(0).getDaticert() != null) {
							if (message.getSubinfos().get(0).getDaticert().getDati().getMsgid() != null) {
								messageIdRif = (StringUtils.remove(StringUtils.remove(message.getSubinfos().get(0).getDaticert().getDati().getMsgid(), "<"),
										">"));
							} else {
								messageIdRif = (message.getSubinfos().get(0).getHeaderinfo().getMessageid());
							}
						} else {
							messageIdRif = (message.getSubinfos().get(0).getHeaderinfo().getMessageid());
						}
					} else {
						// Se internamente ha un riferimento
						if (message.getSubinfos().get(0).getSubinfos() != null && !message.getSubinfos().get(0).getSubinfos().isEmpty()) {
							if (StringUtils.isNotEmpty(message.getSubinfos().get(0).getSubinfos().get(0).getHeaderinfo().getRiferimentomessageid())) {
								messageIdRif = message.getSubinfos().get(0).getSubinfos().get(0).getHeaderinfo().getRiferimentomessageid();
							} else {
								messageIdRif = message.getSubinfos().get(0).getSubinfos().get(0).getHeaderinfo().getMessageid();
							}
						} else
							messageIdRif = message.getSubinfos().get(0).getHeaderinfo().getMessageid();
					}
				}
			} else {
				messageIdRif = message.getHeaderinfo().getRiferimentomessageid();
			}
			if (messageIdRif != null) {
				// trovato un message id di riferimento, lo aggiorno nella ricevuta
				bean.getMail().setMessageIdRif(messageIdRif);
				// ricerco la relazione con la mail originale
				DaoTEmailMgo daoEmail = (DaoTEmailMgo) DaoUtils.getDao(DaoTEmailMgo.class, idCasella);
				TFilterFetch<TEmailMgoBean> filterFetch = new TFilterFetch<TEmailMgoBean>();
				TEmailMgoBean filtro = new TEmailMgoBean();
				filtro.setMessageId(messageIdRif);
				filtro.setIdCasella(bean.getMail().getIdCasella());
				filterFetch.setFilter(filtro);
				risu = daoEmail.search(filterFetch).getData();
				// modifica 21/10/2014 se non trovo alcuna associazione con il
				// messageID provo a farlo con il messageidRiferimento
				if (risu == null || risu.isEmpty()) {
					filtro = new TEmailMgoBean();
					filtro.setMessageIdTrasporto(messageIdRif);
					filtro.setIdCasella(bean.getMail().getIdCasella());
					filterFetch.setFilter(filtro);
					risu = daoEmail.search(filterFetch).getData();
				}
				// Se non ho trovato nulla neanche in questo modo, provo con
				// tutti i message id in textplain
				if (risu == null || risu.isEmpty()) {
					logger.warn("Non ho trovato ancora il messaggio con cui legarla, reupero tutti gli id message in text plain");
					List<String> lListIds = message.getAllMessageIds();
					if (lListIds != null) {
						for (String lString : lListIds) {
							risu = findMessage(lString, bean.getMail().getIdCasella());
							if (ListUtil.isNotEmpty(risu)) {
								break;
							} else {
								logger.warn("Ancora non trovato il legame");
							}
						}
					} else {
						logger.warn("Nessun legame trovato");
					}
				}
			}
		} catch (Exception e) {
			logger.error("Impossibile eseguire le logiche sulla ricevuta: ", e);
			throw e;
		}
		if (risu == null || risu.isEmpty()) {
			if (message.isDeliveryStatusNotification()) {
				bean.getMail().setFlgNoAssocAuto(true);
			} else {
				// se è una PEC riprovo ad eseguire l'operazione, in alcuni casi la ricezione è più rapida dell'aggiornamento del message-id della mail inviata
				if (maxTryOperation != null && currentNumTryOperation != null && maxTryOperation != -1 && currentNumTryOperation.intValue() >= maxTryOperation.intValue()) {
					// archivio comunque la mail
					bean.getMail().setFlgNoAssocAuto(true);
				} else {
					// rilancio l'eccezione
					throw new MailServerException("Nessuna mail trovata per message-id " + messageIdRif + " per creare la relazione con questa ricevuta");
				}
			}
		} else {
			bean = new ReceiptManager().process(bean, risu.get(0), message, idFruitore, idCasella, cancellaPecInviata);
		}
		return bean;
	}

	private static List<TEmailMgoBean> findMessage(String lString, String idCasella) throws Exception {
		logger.debug("Recupero il legame con il message id " + lString + " tramite messageId");
		DaoTEmailMgo daoEmail = (DaoTEmailMgo) DaoUtils.getDao(DaoTEmailMgo.class, idCasella);
		TFilterFetch<TEmailMgoBean> filterFetch = new TFilterFetch<TEmailMgoBean>();
		TEmailMgoBean filtro = new TEmailMgoBean();
		filtro.setMessageId(lString);
		filtro.setIdCasella(idCasella);
		filterFetch.setFilter(filtro);
		List<TEmailMgoBean> risu = daoEmail.search(filterFetch).getData();
		if (risu == null || risu.isEmpty()) {
			logger.debug("Non ho trovato nulla. Recupero il legame con il message id " + lString + " tramite messageIdTrasporto");
			filtro = new TEmailMgoBean();
			filtro.setMessageIdTrasporto(lString);
			filtro.setIdCasella(idCasella);
			filterFetch.setFilter(filtro);
			risu = daoEmail.search(filterFetch).getData();
		}
		return risu;
	}

	public static EmailBean eseguiLogicheRichiestaLettura(EmailBean bean, MessageInfos message, String idCasella) throws Exception {
		try {
			String idAccountCasella = bean.getMail().getIdCasella();
			String originalMsgID = message.getOriginalMessageId(idAccountCasella); // a questo punto so che è una notifica di lettura, verifico se esiste il
																					// relativo message id in database
			if (StringUtils.isNotEmpty(originalMsgID)) {
				// l'id deve essere associato ad una mail tramite id o id di trasporto
				logger.debug("il msgID della mail originale è il seguente: " + originalMsgID);
				DaoTEmailMgo daoEmail = (DaoTEmailMgo) DaoUtils.getDao(DaoTEmailMgo.class, idCasella);
				TFilterFetch<TEmailMgoBean> filterFetch = new TFilterFetch<TEmailMgoBean>();
				TEmailMgoBean filter = new TEmailMgoBean();
				filter.setMessageId(originalMsgID);
				filter.setIdCasella(idAccountCasella);
				filterFetch.setFilter(filter);
				List<TEmailMgoBean> listaRisultati = daoEmail.search(filterFetch).getData();
				// modifica 21/10/2014 cerco di fare l'associazione anche per
				// messageidTrasporto
				if (ListUtil.isEmpty(listaRisultati)) {
					filter = new TEmailMgoBean();
					filter.setIdCasella(idAccountCasella);
					filter.setMessageIdTrasporto(originalMsgID);
					filterFetch.setFilter(filter);
					listaRisultati = daoEmail.search(filterFetch).getData();
				}
				if (ListUtil.isEmpty(listaRisultati)) {
					bean.getMail().setFlgNoAssocAuto(true);
				} else {
					bean = new ReceiptManager().process(bean, listaRisultati.get(0), message, null, idCasella, false);
				}
				return bean;
			}
		} catch (Exception e) {
			logger.error("Impossibile portare a termine le logiche di richiesta lettura", e);
			throw e;
		}
		return bean;
	}

	/**
	 * ricava il valore corretto da settare
	 * 
	 * @param in
	 * @return
	 */
	private static String retrieveReceiptType(String in) {
		if (in.equals(TipoRicevuta.COMPLETA.getValue()))
			return Constants.TIPO_RICEVUTA_COMPLETA;
		if (in.equals(TipoRicevuta.BREVE.getValue()))
			return Constants.TIPO_RICEVUTA_BREVE;
		if (in.equals(TipoRicevuta.SINTETICA.getValue()))
			return Constants.TIPO_RICEVUTA_SINTETICA;
		return null;
	}

	/**
	 * verifica i parametri di configurazione delle folder specifiche
	 * 
	 * @param parameters
	 * @return
	 */
	private static Map<String, String> verificaParametriFolder(Map<String, TParametersBean> parameters) {
		Map<String, String> mittentiFolder = new HashMap<String, String>();
		Set<String> chiavi = parameters.keySet();
		Map<String, String> mittenti = new HashMap<String, String>();
		Map<String, String> folders = new HashMap<String, String>();
		for (String key : chiavi) {
			if (key.contains(PARAMETER_SPECIFIC_SENDER)) {
				TParametersBean parametro = parameters.get(key);
				mittenti.put(parametro.getParKey(), parametro.getStrValue());
			}
			if (key.contains(PARAMETER_SPECIFIC_FOLDER)) {
				TParametersBean parametro = parameters.get(key);
				folders.put(parametro.getParKey(), parametro.getStrValue());
			}
			for (String chiaveMittente : mittenti.keySet()) {
				String numeroMittente = chiaveMittente.substring(chiaveMittente.indexOf('#'));
				String folder = folders.get(PARAMETER_SPECIFIC_FOLDER + numeroMittente);
				if (folder != null) {
					mittentiFolder.put(mittenti.get(chiaveMittente), folder);
				} else {
					logger.warn("Nessuna folder relativa al mittente: " + chiaveMittente);
				}
			}
		}
		return mittentiFolder;
	}

	@SuppressWarnings("unchecked")
	public static EmailBean populateRelEmailFolderBean(MessageInfos message, String idAccount, EmailBean mail, Map<String, TParametersBean> parameters,
			String idCasella) throws Exception {
		TRelEmailFolderBean relEmailFolder = null;
		Map<String, String> folderSpeciali = verificaParametriFolder(parameters);
		Set<String> senders = folderSpeciali.keySet();
		String mittente = message.getMittenteWithPrincipalMail();
		// gestione folder speciali di reindirizzamento solo per mail che non
		// siano ricevute
		if (folderSpeciali.size() > 0 && !(message.isRicevuta())) {
			for (String sender : senders) {
				if (sender.contains(WILDCARD)) {
					if (sender.startsWith(WILDCARD)) {
						if (StringUtils.isNotBlank(mittente) && mittente.contains(sender.substring(1))) {
							String folder = folderSpeciali.get(sender);
							relEmailFolder = retrieveFolder(idAccount, mail.getMail(), folder, idCasella);
							break;
						}
					} else {
						if (sender.endsWith(WILDCARD)) {
							if (StringUtils.isNotBlank(mittente) && mittente.startsWith(sender.substring(0, sender.length() - 2))) {
								String folder = folderSpeciali.get(sender);
								relEmailFolder = retrieveFolder(idAccount, mail.getMail(), folder, idCasella);
								break;
							}
						} else {
							logger.warn(
									"Gestione wildcard(in mezzo ad un indirizzo email) non consentita, il carattere speciale deve essere inserito all'inizio o alla fine");
						}
					}
				} else {
					if (sender.equalsIgnoreCase(mittente)) {
						String folder = folderSpeciali.get(sender);
						relEmailFolder = retrieveFolder(idAccount, mail.getMail(), folder, idCasella);
						break;
					}
				}
			}
		}
		if (relEmailFolder == null) {
			// La mail e' PEC
			if (message.isPec()) {
				try {
					if (!isInteroperabile(message)) {
						relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_PEC, idCasella);
					} else {
						List<Object> interbeanlist = message.getOpResultWithStartName(Constants.OPERAZIONI_INTEROPERABILITA);
						AbstractInterbean interbean = (AbstractInterbean) interbeanlist.get(0);

						// in realta' non e' una mail interoperabile per cui la
						// inserisco nella folder di non trattabili
						if (!interbean.getIsInteroperabile()) {
							relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARRIVO_INTEROP_NON_CONF_NON_TRATT,
									idCasella);
						}
						if (interbean.getType() == InteroperabilitaType.SEGNATURA) {
							// si valutano tutte le condizioni che rendono la
							// mail interoperabile non conforme e non trattabile
							if (interbeanlist.size() > 1 || !interbean.getIsdtdValid() || interbean.getIsXmlDuplicate()
									|| (ListUtil.isNotEmpty(interbean.getErroriConformita())) || (ListUtil.isNotEmpty(interbean.getErroriXML()))) {
								relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARRIVO_INTEROP_NON_CONF_NON_TRATT,
										idCasella);
								// inserisco la parte di avvertimenti
								// interoperabili
								String avvertimentiInterop = "";
								if (interbeanlist.size() > 1) {
									avvertimentiInterop = avvertimentiInterop.concat(AbstractInterbean.ERRORE_XML_CONTRASTANTI);
								}
								if (!interbean.getIsdtdValid()) {
									avvertimentiInterop = avvertimentiInterop.concat(AbstractInterbean.ERRORE_DTD_NON_VALIDO);
								}
								if (interbean.getIsXmlDuplicate()) {
									avvertimentiInterop = avvertimentiInterop.concat(AbstractInterbean.ERRORE_XML_DUPLICATO);
								}
								if (ListUtil.isNotEmpty(interbean.getErroriConformita())) {
									for (String errore : interbean.getErroriConformita()) {
										avvertimentiInterop = avvertimentiInterop.concat(errore) + "\r\n";
									}
								}
								if (ListUtil.isNotEmpty(interbean.getErroriXML())) {
									for (String errore : interbean.getErroriXML()) {
										avvertimentiInterop = avvertimentiInterop.concat(errore);
									}
								}
								String avvertimenti = mail.getMail().getAvvertimenti();
								if (avvertimenti != null) {
									mail.getMail().setAvvertimenti(avvertimenti.concat(avvertimentiInterop));
								} else {
									mail.getMail().setAvvertimenti(avvertimentiInterop);
								}
							} else {
								// email da cercare nella segnatura
								String emailRicevente = getEmailFromAccount(idAccount, idCasella);
								Utilities util = new Utilities();
								TFilterFetch<TAnagFruitoriCaselleBean> ff = new TFilterFetch<TAnagFruitoriCaselleBean>();
								TAnagFruitoriCaselleBean fil = new TAnagFruitoriCaselleBean();
								fil.setIdFruitoreCasella(util.ricavaFruitoreCasella(idAccount));
								ff.setFilter(fil);
								List<TAnagFruitoriCaselleBean> fruitori = DaoUtils.getDao(DaoTAnagFruitoriCaselle.class, idCasella).search(ff).getData();
								String codCompleto = fruitori.get(0).getCodiceCompleto();
								// codici da cercare nella segnatura
								String codAOO = null;
								String codEnte = null;
								if (codCompleto != null && !"".equals(codCompleto)) {
									String[] codici = StringUtils.split(codCompleto, Constants.CODE_SEPARATOR);
									codAOO = codici[0];
									if(codici.length > 1) {
										codEnte = codici[1];
									}
								}
								InteropManager manager = new InteropManager(interbean, mail.getMail().getIdEmail(), mail.getMail().getAccountMittente());
								InteropVerificationData verificationData = manager.verificaSegnatura(emailRicevente, codAOO, codEnte);
								if (verificationData.getIsConforme()) {
									relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARRIVO_INTEROP_CONFORMI, idCasella);
								} else {
									// inserisco la parte di avvertimenti
									// interoperabili
									String avvertimenti = mail.getMail().getAvvertimenti();
									String avvertimentiInteroperabili = verificationData.getAvvertimento();
									if (avvertimenti != null) {
										mail.getMail().setAvvertimenti(avvertimenti.concat(avvertimentiInteroperabili));
									} else {
										mail.getMail().setAvvertimenti(avvertimentiInteroperabili);
									}
									relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARRIVO_INTEROP_NON_CONF_TRATT,
											idCasella);
								}
							}
						} else {
							if (mail.getInterOp() != null && ListUtil.isNotEmpty(mail.getInterOp().getMailAgganciate())) {
								relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARCHIVIATA_RICEVUTE, idCasella);
							} else {
								relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARRIVO_NOTIF_NON_ASSOCIATE, idCasella);
							}
							// inserisco la parte di avvertimenti
							// interoperabili
							String avvertimentiInterop = "";
							if (interbeanlist.size() > 1) {
								avvertimentiInterop = avvertimentiInterop.concat(AbstractInterbean.ERRORE_XML_CONTRASTANTI);
							}
							if (!interbean.getIsdtdValid()) {
								avvertimentiInterop = avvertimentiInterop.concat(AbstractInterbean.ERRORE_DTD_NON_VALIDO);
							}
							if (interbean.getIsXmlDuplicate()) {
								avvertimentiInterop = avvertimentiInterop.concat(AbstractInterbean.ERRORE_XML_DUPLICATO);
							}
							if (ListUtil.isNotEmpty(interbean.getErroriConformita())) {
								for (String errore : interbean.getErroriConformita()) {
									avvertimentiInterop = avvertimentiInterop.concat(errore);
								}
							}
							if (ListUtil.isNotEmpty(interbean.getErroriXML())) {
								for (String errore : interbean.getErroriXML()) {
									avvertimentiInterop = avvertimentiInterop.concat(errore);
								}
							}
							String avvertimenti = mail.getMail().getAvvertimenti();
							if (avvertimenti != null) {
								mail.getMail().setAvvertimenti(avvertimenti.concat(avvertimentiInterop));
							} else {
								mail.getMail().setAvvertimenti(avvertimentiInterop);
							}
						}
					}
				} catch (Exception e) {
					logger.error(ERRORE_CREAZIONE_BEAN + ": ", ExceptionUtils.getStackTrace(e));
					throw e;
				}

			}
			// La mail e' anomalia
			if (message.isAnomaliaPec()) {
				if (!message.isSpam()) {
					relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_PEO, idCasella);
				}
				// la mail e' anomalia e spam
				else {
					if (!verifyIfAlreadyExist(message, idAccount, idCasella)) {
						relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_PEO_SPAM, idCasella);
					}
				}
			}
			Categoria categoria = identificaCategoria(message);
			if (categoria == Categoria.ALTRO) {
				// le notifiche di cancellazione sono categorizzate come ALTRO
				if (message.isDispositionNotificationDelete()) {
					// notifica di cancellazione: va archiviata direttamente
					relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARCHIVIATA_ARRIVO, idCasella);
				} else {
					boolean isPec = false;
					try {
						Properties properties = SendUtils.getAccountProperties(idAccount);
						if (properties.getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false").equalsIgnoreCase("true")) {
							isPec = true;
						}
					} catch (Exception e) {
						logger.warn("impossibile leggere le configurazioni dell'account con id: " + idAccount);
					}
					if (isPec) {
						relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_MAIL_AUTOMATICHE, idCasella);
					} else {
						relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_PEO, idCasella);
					}
				}
			} else if (categoria == Categoria.CONFERMA_LETTURA || categoria == Categoria.CONFERMA_LETTURA_PEC) {

				String originalMsgID = message.getOriginalMessageId(idAccount);
				// a questo punto so che è una notifica di lettura, verifico se esiste il relativo message id in database
				// per scegliere la folder corretta
				if (StringUtils.isNotEmpty(originalMsgID)) {
					// archivio la notifica di lettura visto che è stata trovata la relazione
					relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARCHIVIATA_RICEVUTE, idCasella);
				} else {
					// nessuna associazione possibile, l'archiviazione dovreà essere fatta a mano
					relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARRIVO_NOTIF_NON_ASSOCIATE, idCasella);
				}

			}
			// DiegoL: se arrivo qui è una interop non conforme e quindi la metto nelle non conformi non trattabili
			if (categoria == Categoria.INTER_SEGN ||
					categoria == Categoria.INTER_AGG ||
					categoria == Categoria.INTER_ANN ||
					categoria == Categoria.INTER_CONF ||
					categoria == Categoria.INTER_ECC ) {
				relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARRIVO_INTEROP_NON_CONF_NON_TRATT, idCasella);
			}
			if (message.isRicevuta() || message.isDeliveryStatusNotification()) {
				if (mail.getMail().getFlgNoAssocAuto()) {
					relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARRIVO_NOTIF_NON_ASSOCIATE, idCasella);

				} else {
					relEmailFolder = retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_ARCHIVIATA_RICEVUTE, idCasella);

				}
			}			
		}
		List<TRelEmailFolderBean> listaRel = new ArrayList<TRelEmailFolderBean>();
		listaRel.add(relEmailFolder);
		mail.setFolders(listaRel);
		return mail;
	}

	/**
	 * crea il bean della folder
	 * 
	 * @param idAccount
	 * @param mail
	 * @param tipoFolder
	 * @return
	 * @throws MailServerException
	 */
	private static TRelEmailFolderBean retrieveFolder(String idAccount, TEmailMgoBean mail, FolderEmail tipoFolder, String idCasella) throws Exception {
		TRelEmailFolderBean relEmailFolder = new TRelEmailFolderBean();
		TFolderCaselleBean folder = ricavaEmailFolder(idAccount, tipoFolder, idCasella);
		relEmailFolder.setIdEmail(mail.getIdEmail());
		relEmailFolder.setIdFolderCasella(folder.getIdFolderCasella());
		return relEmailFolder;
	}

	private static TRelEmailFolderBean retrieveFolder(String idAccount, TEmailMgoBean mail, String tipoFolder, String idCasella) throws Exception {
		TRelEmailFolderBean relEmailFolder = new TRelEmailFolderBean();
		TFolderCaselleBean folder = ricavaEmailFolder(idAccount, tipoFolder, idCasella);
		relEmailFolder.setIdEmail(mail.getIdEmail());
		relEmailFolder.setIdFolderCasella(folder.getIdFolderCasella());
		return relEmailFolder;
	}

	/**
	 * restituisce la cartella in cui risiede una email
	 * 
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<TFolderCaselleBean> getFolderFromEmail(TEmailMgoBean mail, String idCasella) throws Exception {
		try {

			List<TRelEmailFolderBean> relazioni = getRelEmailFolderFromEmail(mail, idCasella);
			if (ListUtil.isEmpty(relazioni)) {
				throw new MailServerException("impossibile ricavare la folder dall'id email processato");
			}
			TRelEmailFolderBean rel = relazioni.get(0);
			TFilterFetch<TFolderCaselleBean> filter = new TFilterFetch<TFolderCaselleBean>();
			TFolderCaselleBean fil = new TFolderCaselleBean();
			fil.setIdFolderCasella(rel.getIdFolderCasella());
			filter.setFilter(fil);
			List<TFolderCaselleBean> cartelle = DaoUtils.getDao(DaoTFolderCaselle.class, idCasella).search(filter).getData();
			if (ListUtil.isEmpty(cartelle)) {
				throw new MailServerException("impossibile ricavare la folder dall'id email processato");
			}
			return cartelle;
		} catch (Exception e) {
			logger.error("impossibile ricavare la folder dall'id email processato", e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<TRelEmailFolderBean> getRelEmailFolderFromEmail(TEmailMgoBean mail, String idCasella) throws Exception {
		List<TRelEmailFolderBean> relazioni = null;
		try {
			TFilterFetch<TRelEmailFolderBean> ff = new TFilterFetch<TRelEmailFolderBean>();
			TRelEmailFolderBean filtro = new TRelEmailFolderBean();
			filtro.setIdEmail(mail.getIdEmail());
			ff.setFilter(filtro);
			relazioni = DaoUtils.getDao(DaoTRelEmailFolder.class, idCasella).search(ff).getData();
			if (ListUtil.isEmpty(relazioni)) {
				throw new MailServerException("impossibile ricavare la folder dall'id email processato");
			}
			return relazioni;
		} catch (Exception e) {
			logger.error("impossibile ricavare la folder dall'id email processato", e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<TDestinatariEmailMgoBean> getListaDestinatari(TEmailMgoBean bean, String idCasella) throws Exception {
		List<TDestinatariEmailMgoBean> ris = null;
		try {
			TFilterFetch<TDestinatariEmailMgoBean> ff = new TFilterFetch<TDestinatariEmailMgoBean>();
			TDestinatariEmailMgoBean destinatario = new TDestinatariEmailMgoBean();
			destinatario.setIdEmail(bean.getIdEmail());
			ff.setFilter(destinatario);
			ris = DaoUtils.getDao(DaoTDestinatariEmailMgo.class, idCasella).search(ff).getData();
		} catch (Exception e) {
			logger.error("impossibile ricavarei destinatari dall'id email processato", e);
			throw e;
		}
		return ris;
	}

	@SuppressWarnings("unchecked")
	public static List<TRubricaEmailBean> getVoceRubricaFromDestinatario(TDestinatariEmailMgoBean dest, String idCasella) throws Exception {
		List<TRubricaEmailBean> res = null;
		try {
			TFilterFetch<TRubricaEmailBean> fife = new TFilterFetch<TRubricaEmailBean>();
			TRubricaEmailBean ft = new TRubricaEmailBean();
			ft.setIdVoceRubricaEmail(dest.getIdVoceRubricaDest());
			fife.setFilter(ft);
			res = DaoUtils.getDao(DaoTRubricaEmail.class, idCasella).search(fife).getData();
		} catch (Exception e) {
			logger.error("impossibile ricavare le voci rubrica dal destinatario", e);
			throw e;
		}
		return res;
	}

	/**
	 * Metodo che popola le relazioni tra email basandosi sul fatto che la email appena arrivata sia una ricevuta di una PEC
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<TRelEmailMgoBean> populateRelEmail(EmailBean mailCompleta, TValDizionarioBean dizCategoria, TValDizionarioBean dizRuolo,
			String mittenteRicevuta, String idEmailOrig, String idCasella) throws Exception {
		List<TRelEmailMgoBean> ret = new ArrayList<TRelEmailMgoBean>();
		List<TDestinatariEmailMgoBean> tos = null;
		// se il mittente ricevuta è nullo non inserisco niente nel campo
		// idDestinatario della relazione
		if (mittenteRicevuta != null) {
			TFilterFetch<TDestinatariEmailMgoBean> ff = new TFilterFetch<TDestinatariEmailMgoBean>();
			TDestinatariEmailMgoBean filt = new TDestinatariEmailMgoBean();
			filt.setAccountDestinatario(mittenteRicevuta);
			filt.setIdEmail(idEmailOrig);
			ff.setFilter(filt);
			try {
				tos = DaoUtils.getDao(DaoTDestinatariEmailMgo.class, idCasella).search(ff).getData();
			} catch (Exception e) {
				logger.error("Impossibile trovare il destinatario relativo alla email: " + mailCompleta.getMail().getIdEmail(), e);
				throw e;
			}
			if (tos != null && tos.size() > 1) {
				logger.warn("Trovato più di un destinatario per una ricevuta PEC, situazione anomala");
			}
		}
		TRelEmailMgoBean relMail = new TRelEmailMgoBean();
		relMail.setIdRelEmail(KeyGenerator.gen());
		relMail.setIdEmail1(mailCompleta.getMail().getIdEmail());
		relMail.setFlgRelAutomatica(true);
		relMail.setIdEmail2(idEmailOrig);
		relMail.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
		relMail.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
		if (ListUtil.isNotEmpty(tos)) {
			relMail.setIdDestinatario2(tos.get(0).getIdDestinatarioEmail());
		}
		ret.add(relMail);
		return ret;
	}

	/**
	 * ricava la folder partendo dal nome della stessa e dall'idAccount
	 * 
	 * @param idAccount
	 * @param folder
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public static TFolderCaselleBean ricavaEmailFolder(String idAccount, FolderEmail folder, String idCasella) throws Exception {
		TFilterFetch<TFolderCaselleBean> filter = new TFilterFetch<TFolderCaselleBean>();
		TFolderCaselleBean filtro = new TFolderCaselleBean();
		filtro.setIdCasella(idAccount);
		filtro.setClassificazione(folder.getValue());
		filter.setFilter(filtro);
		TPagingList<TFolderCaselleBean> listaRis = null;
		try {
			listaRis = DaoUtils.getDao(DaoTFolderCaselle.class, idCasella).search(filter);
			if (ListUtil.isEmpty(listaRis.getData())) {
				throw new MailServerException("Nessuna directory prevista per la PEC per questa casella: " + idAccount);
			}
			if (listaRis.getData().size() > 1) {
				throw new MailServerException("Presenza di due folder con lo stesso nome per la casella: " + idAccount);
			}
		} catch (Exception e) {
			logger.error(ERRORE_CREAZIONE_BEAN + ": ", ExceptionUtils.getStackTrace(e));
			throw e;
		}
		return listaRis.getData().get(0);
	}

	/**
	 * ricava la folder partendo dal nome della stessa e dall'idAccount
	 * 
	 * @param idAccount
	 * @param folder
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public static TFolderCaselleBean ricavaEmailFolder(String idAccount, String folder, String idCasella) throws Exception {
		TFilterFetch<TFolderCaselleBean> filter = new TFilterFetch<TFolderCaselleBean>();
		TFolderCaselleBean filtro = new TFolderCaselleBean();
		filtro.setIdCasella(idAccount);
		filtro.setClassificazione(folder);
		filter.setFilter(filtro);
		TPagingList<TFolderCaselleBean> listaRis = null;
		try {
			listaRis = DaoUtils.getDao(DaoTFolderCaselle.class, idCasella).search(filter);
			if (ListUtil.isEmpty(listaRis.getData())) {
				logger.error("Nessuna directory " + folder + " prevista per la PEC per questa casella: " + idAccount);
				throw new MailServerException("Nessuna directory prevista per la PEC per questa casella: " + idAccount);
			}
			if (listaRis.getData().size() > 1) {
				logger.error("Presenza di due folder con lo stesso nome per la casella: " + idAccount);
				throw new MailServerException("Presenza di due folder " + folder + " con lo stesso nome per la casella: " + idAccount);
			}
		} catch (Exception e) {
			logger.error(ERRORE_CREAZIONE_BEAN + ": ", ExceptionUtils.getStackTrace(e));
			throw e;
		}
		return listaRis.getData().get(0);
	}

	public static EmailBean populateAttachEmailMgoBean(MessageInfos message, EmailBean bean) throws MailServerException {
		List<TAttachEmailMgoBean> attachsEmailList = new ArrayList<TAttachEmailMgoBean>();
		List<Object> signerinfo = message.getOpResultWithStartName(Constants.SIGNATURE_VERIFY_OPERATION);
		List<SignerAttachmentsBean> attachments = new ArrayList<SignerAttachmentsBean>();
		if (signerinfo != null && !signerinfo.isEmpty()) {
			SignerMailBean signerbean = (SignerMailBean) signerinfo.get(0);
			if (signerbean.getAttachments() != null) {
				attachments = signerbean.getAttachments();
				logger.debug("Numero attachments verificati: " + attachments.size());
			}
		}
		logger.debug("Devo recuperare gli attachments. In questo momento il numero è: " + attachments.size());
		logger.debug("I loro nomi e hash sono: ");
		for (SignerAttachmentsBean lSignerAttachmentsBean : attachments) {
			logger.debug("Nome file: " + lSignerAttachmentsBean.getFilename());
			if (StringUtils.isBlank(lSignerAttachmentsBean.getEncodedHash())) {
				logger.debug("Hash encoded non valorizzato");
			} else {
				logger.debug("Hash encoded: " + lSignerAttachmentsBean.getEncodedHash());
				logger.debug("Algoritmo: " + lSignerAttachmentsBean.getAlgoritmo());
				logger.debug("Encoding: " + lSignerAttachmentsBean.getEncoding());
			}

		}
		AttachmentVerifier attachmentVerifier = (AttachmentVerifier) MailServerSpringAppContext.getContext().getBean("attachmentVerifier");
		boolean firmaAttachOk = true;
		for (SignerAttachmentsBean attach : attachments) {
			TAttachEmailMgoBean attachMgoBean = new TAttachEmailMgoBean();
			if (attach.getIssigner() && !attach.getIssignervalid()) {
				firmaAttachOk = false;
			}
			attachMgoBean.setIdAttachEmail(KeyGenerator.gen());
			// salvo l'informazione se il file è firmato digitalmente e se la firma è valida
			attachMgoBean.setFlgFirmato(attach.getIssigner());
			attachMgoBean.setFlgFirmaValida(attach.getIssignervalid());
			attachMgoBean.setDimensione(attach.getSize());
			// VERIFICO SE C'E' IL FILE SENNO' PROVO A RECUPERARLO TRAMITE HASH, IL NOME INFATTI NON E' UNIVOCO VISTO CHE POTREBBERO ESISTERE ALLEGATI OMONIMI
			File fileAllegato = attach.getFile();
			logger.debug("Path del file allegato: " + fileAllegato != null ? fileAllegato.getAbsolutePath() : "null");
			if (!fileAllegato.exists()) {
				logger.debug("Il file allegato non esiste, provo a recuperarlo tramite hash");
				List<MailAttachmentsBean> mailAttachments = message.getDirectAttachments();
				MailAttachmentsBean attachTarget = null;
				for (MailAttachmentsBean mailAttach : mailAttachments) {
					logger.debug("Informazioni del mailAttach recuperato dal MessageInfos: " + mailAttach.getFilename());
					logger.debug("EncodedHash: " + mailAttach.getEncodedHash());
					logger.debug("Encoding: " + mailAttach.getEncoding());
					logger.debug("Algoritmo: " + mailAttach.getAlgoritmo());
					if (StringUtils.isNotBlank(mailAttach.getEncodedHash()) && StringUtils.isNotBlank(attach.getEncodedHash())
							&& StringUtils.isNotBlank(mailAttach.getEncoding()) && StringUtils.isNotBlank(attach.getEncoding())
							&& StringUtils.isNotBlank(mailAttach.getAlgoritmo()) && StringUtils.isNotBlank(attach.getAlgoritmo())
							&& mailAttach.getAlgoritmo().equalsIgnoreCase(attach.getAlgoritmo())
							&& mailAttach.getEncoding().equalsIgnoreCase(attach.getEncoding()) && mailAttach.getEncodedHash().equals(attach.getEncodedHash())) {
						attachTarget = mailAttach;
						logger.debug("File attach trovato tra gli allegati del MessageInfos");
						break;
					}
				}
				// mi interessa solo il file, quindi anche in caso di duplicati basta il primo che coincide con l'hash
				if (attachTarget != null) {
					attach.setFile(attachTarget.getFile());
				} else {
					throw new MailServerException("populateAttachEmailMgoBean - File non trovato");
				}
			}
			// EVENTUALI MAIL IN ALLEGATO SONO GIA' DECODIFICATE, SPECIALMENTE QUELLE IN BASE64
			// UNA VOLTA RECUPERATO IL FILE CHIAMO I SERVIZI DI FILEOPERATION
			logger.info("Invocazione FileOperation per calcolo impronta e recupero mimetype e filename per il file: " + attach.getFilename());
			Response risposta = attachmentVerifier.invocaFo(message, attach.getFile(), attach.getFilename());
			logger.debug("Verifica risposta FileOperation");
			if (risposta != null && risposta.getFileoperationResponse() != null && risposta.getFileoperationResponse().getFileOperationResults() != null
					&& risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {
				logger.info("Il servizio FileOperation ha dato risultati");
				for (AbstractResponseOperationType opResponse : risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult()) {
					if (opResponse instanceof ResponseFormatRecognitionType && opResponse.getVerificationStatus().equals(VerificationStatusType.OK)) {
						logger.debug("Il servizio di riconoscimento formato ha dato esito positivo");
						ResponseFormatRecognitionType formatResponse = (ResponseFormatRecognitionType) opResponse;
						attachMgoBean.setDisplayFilename(formatResponse.getNewFileName());
						attachMgoBean.setMimetype(formatResponse.getMimeType());
						logger.debug("Mimetype: " + formatResponse.getMimeType());
						logger.debug("New filename: " + formatResponse.getNewFileName());
					}
					if (opResponse instanceof ResponseFileDigestType && opResponse.getVerificationStatus().equals(VerificationStatusType.OK)) {
						logger.debug("Il servizio di generazione dell'impronta ha dato esito positivo");
						ResponseFileDigestType digestResponse = (ResponseFileDigestType) opResponse;
						attachMgoBean.setAlgoritmoImpronta(digestResponse.getDigestAlgId().value());
						attachMgoBean.setEncodingImpronta(digestResponse.getEncoding().value());
						attachMgoBean.setImpronta(digestResponse.getResult());
						logger.debug("Impronta: " + digestResponse.getResult());
						logger.debug("Algoritmo: " + digestResponse.getDigestAlgId().value());
						logger.debug("Encoding: " + digestResponse.getEncoding().value());
					}
				}
			}
			if (attachMgoBean.getDisplayFilename() == null) {
				attachMgoBean.setDisplayFilename(attach.getFilename());
			}
			if (attachMgoBean.getMimetype() == null) {
				logger.debug("Nessun formato conosciuto trovato - imposto \"sconosciuto\" come Mimetype");
				attachMgoBean.setMimetype("sconosciuto");
			}
			attachMgoBean.setNomeOriginale(attach.getFilename());
			attachMgoBean.setIdEmail(bean.getMail().getIdEmail());
			attachsEmailList.add(attachMgoBean);
		}
		// SE ESISTONO ATTACH NON FIRMATI CORRETTAMENTE INSERISCO UN
		// AVVERTIMENTO
		if (!firmaAttachOk)

		{
			String avvertimenti = bean.getMail().getAvvertimenti();
			if (avvertimenti != null) {
				bean.getMail().setAvvertimenti(avvertimenti.concat("#" + AVVERTIMENTO_ATTACHMENT_MALFIRMATI));
			} else {
				bean.getMail().setAvvertimenti(AVVERTIMENTO_ATTACHMENT_MALFIRMATI);
			}
		}
		bean.setAttachments(attachsEmailList);
		return bean;
	}

	public static short getNroAttachFirmati(MessageInfos message) {
		short nro = 0;
		List<Object> signerinfo = message.getOpResultWithStartName(Constants.SIGNATURE_VERIFY_OPERATION);
		if (signerinfo != null && !signerinfo.isEmpty()) {
			SignerMailBean signerbean = (SignerMailBean) signerinfo.get(0);
			if (signerbean.getAttachments() != null) {
				for (SignerAttachmentsBean attach : signerbean.getAttachments()) {
					if (attach.getIssigner()) {
						nro++;
					}
				}
			}
		}
		return nro;
	}

	public static List<TDestinatariEmailMgoBean> populateDestinatari(MessageInfos message, String idEmail) {
		List<TDestinatariEmailMgoBean> lista = new ArrayList<TDestinatariEmailMgoBean>();
		List<String> destinatariTo = Util.rimuoviDoppioniDaLista(message.getHeaderinfo().getDestinatarito());
		List<String> destinatariCC = Util.rimuoviDoppioniDaLista(message.getHeaderinfo().getDestinataricc());
		for (String destTo : destinatariTo) {
			TDestinatariEmailMgoBean destinatario = new TDestinatariEmailMgoBean();
			destinatario.setIdEmail(idEmail);
			destinatario.setIdDestinatarioEmail(KeyGenerator.gen());
			destinatario.setFlgDestOrigInvio(false);
			destinatario.setAccountDestinatario(destTo.trim());
			destinatario.setFlgTipoDestinatario(TipoDestinatario.TO.getValue());
			destinatario.setFlgDestEffettivo(true);
			destinatario.setFlgNotifInteropAgg(false);
			destinatario.setFlgNotifInteropAnn(false);
			destinatario.setFlgNotifInteropConf(false);
			destinatario.setFlgNotifInteropEcc(false);
			lista.add(destinatario);
		}
		for (String destCC : destinatariCC) {
			TDestinatariEmailMgoBean destinatario = new TDestinatariEmailMgoBean();
			destinatario.setIdEmail(idEmail);
			destinatario.setIdDestinatarioEmail(KeyGenerator.gen());
			destinatario.setFlgDestOrigInvio(false);
			destinatario.setAccountDestinatario(destCC.trim());
			destinatario.setFlgTipoDestinatario(TipoDestinatario.CC.getValue());
			destinatario.setFlgDestEffettivo(true);
			destinatario.setFlgNotifInteropAgg(false);
			destinatario.setFlgNotifInteropAnn(false);
			destinatario.setFlgNotifInteropConf(false);
			destinatario.setFlgNotifInteropEcc(false);
			lista.add(destinatario);
		}
		if (!message.isPec()) {
			List<String> destinatariBcc = Util.rimuoviDoppioniDaLista(message.getHeaderinfo().getDestinataribcc());
			for (String destBCC : destinatariBcc) {
				TDestinatariEmailMgoBean destinatario = new TDestinatariEmailMgoBean();
				destinatario.setIdEmail(idEmail);
				destinatario.setIdDestinatarioEmail(KeyGenerator.gen());
				destinatario.setFlgDestOrigInvio(false);
				destinatario.setAccountDestinatario(destBCC.trim());
				destinatario.setFlgTipoDestinatario(TipoDestinatario.BCC.getValue());
				destinatario.setFlgDestEffettivo(true);
				destinatario.setFlgNotifInteropAgg(false);
				destinatario.setFlgNotifInteropAnn(false);
				destinatario.setFlgNotifInteropConf(false);
				destinatario.setFlgNotifInteropEcc(false);
				lista.add(destinatario);
			}
		}
		return lista;
	}

	public static List<TRubricaEmailBean> populateRubricaDestinatari(List<TDestinatariEmailMgoBean> destinatari, String idFruitore) {
		List<TRubricaEmailBean> vociRubrica = new ArrayList<TRubricaEmailBean>();
		for (TDestinatariEmailMgoBean desti : destinatari) {
			TRubricaEmailBean voceRubrica = new TRubricaEmailBean();
			voceRubrica.setAccountEmail(desti.getAccountDestinatario().trim());
			voceRubrica.setIdVoceRubricaEmail(KeyGenerator.gen());
			voceRubrica.setIdFruitoreCasella(idFruitore);
			voceRubrica.setFlgMailingList(false);
			voceRubrica.setDescrizioneVoce(desti.getAccountDestinatario());
			voceRubrica.setFlgPecVerificata(false);
			voceRubrica.setFlgPresenteInIpa(false);
			voceRubrica.setFlgAnn(false);
			vociRubrica.add(voceRubrica);
		}
		return vociRubrica;
	}

	public static TRubricaEmailBean populateRubricaMittente(String accountMittente, String idFruitore, MessageInfos message) {
		TRubricaEmailBean voceRubrica = new TRubricaEmailBean();
		voceRubrica.setAccountEmail(accountMittente.trim());
		voceRubrica.setIdVoceRubricaEmail(KeyGenerator.gen());
		voceRubrica.setIdFruitoreCasella(idFruitore);
		voceRubrica.setFlgMailingList(false);
		voceRubrica.setDescrizioneVoce(accountMittente);
		if (message.isPec()) {
			voceRubrica.setFlgPecVerificata(true);
			voceRubrica.setTipoAccount(TipoAccount.PEC.getValue());
		} else {
			voceRubrica.setFlgPecVerificata(true);
			voceRubrica.setTipoAccount(TipoAccount.ORDINARIA.getValue());
		}
		voceRubrica.setFlgPresenteInIpa(false);
		voceRubrica.setFlgAnn(false);
		return voceRubrica;
	}

	/**
	 * Metodo che identifica la categoria della email
	 * 
	 * @param message
	 * @param idAccountCasella
	 * @return
	 */
	public static Categoria identificaCategoria(MessageInfos message) {
		List<Object> interbeanlist = message.getOpResultWithStartName(Constants.OPERAZIONI_INTEROPERABILITA);
		if (message.isDeliveryStatusNotification()) {
			return Categoria.DELIVERY_STATUS;
		}
		if (message.isRicevuta() && message.getHeaderinfo().getRicevuta() != null) {
			switch (message.getHeaderinfo().getRicevuta()) {
			case NON_ACCETTAZIONE:
				return Categoria.PEC_RIC_NO_ACC;
			case ACCETTAZIONE:
				return Categoria.PEC_RIC_ACC;
			case PREAVVISO_ERRORE_CONSEGNA:
				return Categoria.PEC_RIC_PREAVV_NO_CONS;
			case PRESA_IN_CARICO:
				return Categoria.PEC_RIC_PRESA_C;
			case RILEVAZIONE_VIRUS:
				return Categoria.PEC_RIC_NO_ACC;
			case ERRORE_CONSEGNA:
				return Categoria.PEC_RIC_NO_CONS;
			case AVVENUTA_CONSEGNA:
				return Categoria.PEC_RIC_CONS;
			}
		}
		Boolean isDispositionNotificationDelete = message.isDispositionNotificationDelete();
		Boolean isDispositionNotificationDisplayed = message.isDispositionNotificationDisplayed();
		if (message.isPec() && (!isInteroperabile(message)) && (!isDispositionNotificationDelete) && (!isDispositionNotificationDisplayed)) {
			return Categoria.PEC;
		} else {
			if (isInteroperabile(message)) {
				AbstractInterbean interbean = (AbstractInterbean) interbeanlist.get(0);
				if (interbean.getIsInteroperabile()) {
					switch (interbean.getType()) {
					case AGGIORNAMENTO_CONFERMA:
						return Categoria.INTER_AGG;
					case ANNULLAMENTO_PROTOCOLLAZIONE:
						return Categoria.INTER_ANN;
					case CONFERMA_RICEZIONE:
						return Categoria.INTER_CONF;
					case NOTIFICA_ECCEZIONE:
						return Categoria.INTER_ECC;
					case SEGNATURA:
						return Categoria.INTER_SEGN;
					}
				}
			}
		}
		if (isDispositionNotificationDelete) {
			return Categoria.ALTRO;
		}
		if (isDispositionNotificationDisplayed) {
			if (message.isPec()) {
				return Categoria.CONFERMA_LETTURA_PEC;
			} else {
				return Categoria.CONFERMA_LETTURA;
			}
		}
		if (message.isAnomaliaPec()) {
			return Categoria.ANOMALIA;
		}
		return Categoria.ALTRO;
	}

	/**
	 * verifica se il messaggio è già presente in banca dati ed è stato eventualmente sbloccato
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static boolean verifyIfAlreadyExist(MessageInfos message, String idAccount, String idCasella) throws Exception {
		boolean exist = false;
		TFilterFetch<TEmailMgoBean> filter = new TFilterFetch<TEmailMgoBean>();
		TEmailMgoBean filtro = new TEmailMgoBean();
		filtro.setMessageId(message.getHeaderinfo().getMessageid());
		filtro.setIdCasella(idAccount);
		filter.setFilter(filtro);
		try {
			List<TEmailMgoBean> listaRet = DaoUtils.getDao(DaoTEmailMgo.class, idCasella).search(filter).getData();
			if (listaRet != null && !(listaRet.isEmpty()) && listaRet.get(0).getFlgStatoSpam() != null
					&& listaRet.get(0).getFlgStatoSpam().equals(Constants.STATO_SPAM_SBLOCCATO)) {
				exist = true;
			}
		} catch (Exception e) {
			logger.error("Impossibile verificare la presenza nel db di una mail: ", e);
			throw e;
		}
		return exist;
	}

	/**
	 * ricava l'email dall'idaccount
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static String getEmailFromAccount(String idAccount, String idCasella) throws Exception {
		TFilterFetch<MailboxAccountBean> filterFetch = new TFilterFetch<MailboxAccountBean>();
		MailboxAccountBean filtro = new MailboxAccountBean();
		filtro.setIdAccount(idAccount);
		filterFetch.setFilter(filtro);
		List<MailboxAccountBean> res = null;
		try {
			res = DaoUtils.getDao(DaoMailboxAccount.class, idCasella).search(filterFetch).getData();
		} catch (Exception e) {
			logger.error("Impossibile ricavare le informazione d'account: ", e);
			throw e;
		}
		if (res == null || res.isEmpty()) {
			logger.error("Nessuna email collegata all'account con id account: " + idAccount);
			throw new MailServerException("Nessuna email collegata all'account con id account: " + idAccount);
		}
		return res.get(0).getAccount();
	}

	public static boolean isInteroperabile(MessageInfos message) {
		List<Object> interbeanlist = message.getOpResultWithStartName(Constants.OPERAZIONI_INTEROPERABILITA);
		if (interbeanlist != null && !interbeanlist.isEmpty()) {
			return (((AbstractInterbean) interbeanlist.get(0)).getIsInteroperabile());
		}
		return false;
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	public static boolean isNotificaInteroperabile(MessageInfos message) {
		if (isInteroperabile(message)) {
			List<Object> interbeanlist = message.getOpResultWithStartName(Constants.OPERAZIONI_INTEROPERABILITA);
			AbstractInterbean interbean = (AbstractInterbean) interbeanlist.get(0);
			return (interbean.getType() == (InteroperabilitaType.AGGIORNAMENTO_CONFERMA) || interbean.getType() == (InteroperabilitaType.CONFERMA_RICEZIONE)
					|| interbean.getType() == (InteroperabilitaType.NOTIFICA_ECCEZIONE)
					|| interbean.getType() == (InteroperabilitaType.ANNULLAMENTO_PROTOCOLLAZIONE) || interbean.getType() == (InteroperabilitaType.SEGNATURA));
		}
		return false;
	}
	
}
