package it.eng.utility.email.operation.impl.archiveoperation.composer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import it.eng.aurigamailbusiness.bean.Dizionario;
import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.InterOpBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRegEstVsEmailBean;
import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TValDizionarioBean;
import it.eng.aurigamailbusiness.database.dao.DaoTEmailMgo;
import it.eng.aurigamailbusiness.database.dao.DaoTRegEstVsEmail;
import it.eng.aurigamailbusiness.database.mail.TDestinatariEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TRubricaEmail;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.utility.XmlFieldUtil;
import it.eng.core.business.KeyGenerator;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.utility.database.SubjectInitializer;
import it.eng.utility.email.operation.impl.archiveoperation.utils.AccountUtils;
import it.eng.utility.email.operation.impl.archiveoperation.utils.Utilities;
import it.eng.utility.email.operation.impl.interoperation.AbstractInterbean;
import it.eng.utility.email.operation.impl.interoperation.InterBeanOld;
import it.eng.utility.email.operation.impl.interoperation.InteroperabilitaType;
import it.eng.utility.email.process.exception.MailServerException;

public class OldInteropNotifManager extends AbstractInteropNotifManager {

	private Logger log = LogManager.getLogger(NewInteropNotifManager.class);

	/**
	 * Metodo di gestione di una mail interoperabile, vecchia versione
	 * 
	 * @busta = bean che raggruppa tutti gli attributri comuni all'interoperabilità, vecchia e nuova
	 * @emailBean = bean della mail che si sta processando
	 * @accountDestinatarioEmail = mittente della mail che si sta processando
	 * @idEmailInProcess = id univoco generato per la mail che si sta processando
	 * @idMailBox = id della mailbox che si sta processando
	 */

	@Override
	public EmailBean notifInteropManage(AbstractInterbean busta, EmailBean emailBean, String idEmailInProcess, String accountDestinatarioEmailInProcess,
			String idMailBox) {

		try {

			InterOpBean interopBean = new InterOpBean();
			String numeroRegistrazioneInvioS = null;
			String dataRegistrazioneInvioS = null;
			String annoRegistrazioneInvioS = null;
			String numeroRegistrazioneRicezioneS = null;
			String dataRegistrazioneRicezioneS = null;
			String annoRegistrazioneRicezioneS = null;
			String codiceAOOInvio = null;
			String codiceAOORicezione = null;
			String codiceAmministrazioneInvio = null;
			String codiceAmministrazioneRicezione = null;
			String oggettoRegistrazioneRicezione = null;

			Date dataRegistrazioneInvio = null;
			Integer numeroRegistrazioneInvio = null;
			Short annoRegistrazioneInvio = null;

			Date dataRegistrazioneRicezione = null;
			Integer numeroRegistrazioneRicezione = null;
			Short annoRegistrazioneRicezione = null;

			// ricavo l'id_account
			String idAccount = AccountUtils.retrieveIdAccount(idMailBox);

			// procedo con l'inserimento delle relazioni solo se non si hanno
			// errori in conformità
			// in caso di errori infatti le proprietà associate alle varie
			// tipologie di notifiche sono sicuramente NULL
			// si estraggono tutti i dati dagli elementi dell'XML, registrando
			// eventuali errori nella relativa lista degli errori XML

			if (((InterBeanOld) busta).getIsdtdValid()
					&& (((InterBeanOld) busta).getErroriConformita() == null || ((InterBeanOld) busta).getErroriConformita().size() == 0)) {

				log.debug("Avvio gestione relazioni notifiche interoperabili");

				switch (((InterBeanOld) busta).getType()) {
				case CONFERMA_RICEZIONE:
					if (((InterBeanOld) busta).getConfermaricezione() != null && ((InterBeanOld) busta).getConfermaricezione().getMessaggioRicevuto() != null
							&& ((InterBeanOld) busta).getConfermaricezione().getMessaggioRicevuto().getIdentificatore() != null) {
						// si ricavano gli attributi dall'elemento messaggio
						// ricevuto per identificare il protocollo inviato
						numeroRegistrazioneInvioS = XmlFieldUtil.cleanField(
								((InterBeanOld) busta).getConfermaricezione().getMessaggioRicevuto().getIdentificatore().getNumeroRegistrazione().getContent());
						dataRegistrazioneInvioS = XmlFieldUtil.cleanField(
								((InterBeanOld) busta).getConfermaricezione().getMessaggioRicevuto().getIdentificatore().getDataRegistrazione().getContent());
						codiceAOOInvio = XmlFieldUtil.cleanField(
								((InterBeanOld) busta).getConfermaricezione().getMessaggioRicevuto().getIdentificatore().getCodiceAOO().getContent());
						codiceAmministrazioneInvio = XmlFieldUtil.cleanField(((InterBeanOld) busta).getConfermaricezione().getMessaggioRicevuto()
								.getIdentificatore().getCodiceAmministrazione().getContent());
					} else {
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_MESSAGGIO_RICEVUTO);
					}
					if (((InterBeanOld) busta).getConfermaricezione() != null && ((InterBeanOld) busta).getConfermaricezione().getIdentificatore() != null) {
						// identificatore del protocollo ricevuto
						numeroRegistrazioneRicezioneS = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getConfermaricezione().getIdentificatore().getNumeroRegistrazione().getContent());
						dataRegistrazioneRicezioneS = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getConfermaricezione().getIdentificatore().getDataRegistrazione().getContent());
						codiceAOORicezione = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getConfermaricezione().getIdentificatore().getCodiceAOO().getContent());
						codiceAmministrazioneRicezione = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getConfermaricezione().getIdentificatore().getCodiceAmministrazione().getContent());
					} else {
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_IDENTIFICATORE);
					}
					break;
				case AGGIORNAMENTO_CONFERMA:
					if (((InterBeanOld) busta).getAggiornamentoconferma() != null
							&& ((InterBeanOld) busta).getAggiornamentoconferma().getMessaggioRicevuto() != null
							&& ((InterBeanOld) busta).getAggiornamentoconferma().getMessaggioRicevuto().getIdentificatore() != null) {
						// si ricavano gli attributi dall'elemento messaggio
						// ricevuto per identificare il protocollo inviato
						numeroRegistrazioneInvioS = XmlFieldUtil.cleanField(((InterBeanOld) busta).getAggiornamentoconferma().getMessaggioRicevuto()
								.getIdentificatore().getNumeroRegistrazione().getContent());
						dataRegistrazioneInvioS = XmlFieldUtil.cleanField(((InterBeanOld) busta).getAggiornamentoconferma().getMessaggioRicevuto()
								.getIdentificatore().getDataRegistrazione().getContent());
						codiceAOOInvio = XmlFieldUtil.cleanField(
								((InterBeanOld) busta).getAggiornamentoconferma().getMessaggioRicevuto().getIdentificatore().getCodiceAOO().getContent());
						codiceAmministrazioneInvio = XmlFieldUtil.cleanField(((InterBeanOld) busta).getAggiornamentoconferma().getMessaggioRicevuto()
								.getIdentificatore().getCodiceAmministrazione().getContent());
					} else {
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_MESSAGGIO_RICEVUTO);
					}
					if (((InterBeanOld) busta).getAggiornamentoconferma() != null
							&& ((InterBeanOld) busta).getAggiornamentoconferma().getIdentificatore() != null) {
						// identificatore del protocollo ricevuto
						numeroRegistrazioneRicezioneS = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getAggiornamentoconferma().getIdentificatore().getNumeroRegistrazione().getContent());
						dataRegistrazioneRicezioneS = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getAggiornamentoconferma().getIdentificatore().getDataRegistrazione().getContent());
						codiceAOORicezione = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getAggiornamentoconferma().getIdentificatore().getCodiceAOO().getContent());
						codiceAmministrazioneRicezione = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getAggiornamentoconferma().getIdentificatore().getCodiceAmministrazione().getContent());
					} else {
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_IDENTIFICATORE);
					}
					break;
				case NOTIFICA_ECCEZIONE:
					if (((InterBeanOld) busta).getNotificaeccezione() != null && ((InterBeanOld) busta).getNotificaeccezione().getMessaggioRicevuto() != null
							&& ((InterBeanOld) busta).getNotificaeccezione().getMessaggioRicevuto().getIdentificatore() != null) {
						// si ricavano gli attributi dall'elemento messaggio
						// ricevuto per identificare il protocollo inviato
						numeroRegistrazioneInvioS = XmlFieldUtil.cleanField(
								((InterBeanOld) busta).getNotificaeccezione().getMessaggioRicevuto().getIdentificatore().getNumeroRegistrazione().getContent());
						dataRegistrazioneInvioS = XmlFieldUtil.cleanField(
								((InterBeanOld) busta).getNotificaeccezione().getMessaggioRicevuto().getIdentificatore().getDataRegistrazione().getContent());
						codiceAOOInvio = XmlFieldUtil.cleanField(
								((InterBeanOld) busta).getNotificaeccezione().getMessaggioRicevuto().getIdentificatore().getCodiceAOO().getContent());
						codiceAmministrazioneInvio = XmlFieldUtil.cleanField(((InterBeanOld) busta).getNotificaeccezione().getMessaggioRicevuto()
								.getIdentificatore().getCodiceAmministrazione().getContent());
					} else {
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_MESSAGGIO_RICEVUTO);
					}
					// non occorre leggere l'identificatore visto che la maggior
					// parte delle volte non è compilato correttamente
					// e non è significativo visto che il protocollo originale è
					// andato in errore
					if (((InterBeanOld) busta).getNotificaeccezione() != null && ((InterBeanOld) busta).getNotificaeccezione().getMotivo() != null) {
						// si registra il motivo di eccezione
						String motivoEccezione = XmlFieldUtil.cleanField(((InterBeanOld) busta).getNotificaeccezione().getMotivo().getContent());
						emailBean.getMail().setMotivoEccezione(motivoEccezione);
					} else {
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_MOTIVO_ECCEZIONE);
					}
					break;
				case ANNULLAMENTO_PROTOCOLLAZIONE:
					// sono presenti solo gli attributi per identificare il
					// protocollo ricevuto
					if (((InterBeanOld) busta).getAnnullamentoprotocollazione() != null
							&& ((InterBeanOld) busta).getAnnullamentoprotocollazione().getIdentificatore() != null) {
						numeroRegistrazioneRicezioneS = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getAnnullamentoprotocollazione().getIdentificatore().getNumeroRegistrazione().getContent());
						dataRegistrazioneRicezioneS = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getAnnullamentoprotocollazione().getIdentificatore().getDataRegistrazione().getContent());
						codiceAOORicezione = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getAnnullamentoprotocollazione().getIdentificatore().getCodiceAOO().getContent());
						codiceAmministrazioneRicezione = XmlFieldUtil.cleanField(
								((InterBeanOld) busta).getAnnullamentoprotocollazione().getIdentificatore().getCodiceAmministrazione().getContent());
					} else {
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_IDENTIFICATORE);
					}
					break;
				case SEGNATURA:
					// ricevo un nuovo protocollo interoperabile
					if (((InterBeanOld) busta).getSegnatura() != null && ((InterBeanOld) busta).getSegnatura().getIntestazione() != null
							&& ((InterBeanOld) busta).getSegnatura().getIntestazione().getIdentificatore() != null) {
						// identificatore del protocollo
						numeroRegistrazioneRicezioneS = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getSegnatura().getIntestazione().getIdentificatore().getNumeroRegistrazione().getContent());
						dataRegistrazioneRicezioneS = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getSegnatura().getIntestazione().getIdentificatore().getDataRegistrazione().getContent());
						codiceAOORicezione = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getSegnatura().getIntestazione().getIdentificatore().getCodiceAOO().getContent());
						codiceAmministrazioneRicezione = XmlFieldUtil.cleanField(
								((InterBeanOld) busta).getSegnatura().getIntestazione().getIdentificatore().getCodiceAmministrazione().getContent());
						oggettoRegistrazioneRicezione = XmlFieldUtil
								.cleanField(((InterBeanOld) busta).getSegnatura().getIntestazione().getOggetto().getContent());
					} else {
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_IDENTIFICATORE);
					}
					break;
				}
				// fine switch

				if (dataRegistrazioneInvioS != null && !StringUtils.isEmpty(dataRegistrazioneInvioS)) {
					try {
						// Provo ad ottenere una data valida e poi un anno
						dataRegistrazioneInvio = new SimpleDateFormat("yyyy-MM-dd").parse(dataRegistrazioneInvioS);
						// Sono sicuro che le prime 4 cirfre indicano l'anno
						annoRegistrazioneInvioS = new SimpleDateFormat("yyyy").format(dataRegistrazioneInvio);
						annoRegistrazioneInvio = Short.parseShort(annoRegistrazioneInvioS);
					} catch (Exception e) {
						log.warn("Notifica Interoperabile - Impossibile convertire la data registrazione del protocollo inviato: " + dataRegistrazioneInvioS
								+ " - Formato non valido");
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_DATA_REGISTRAZIONE_INVIO);
					}
				} else {
					if (((InterBeanOld) busta).getType() != InteroperabilitaType.SEGNATURA
							&& ((InterBeanOld) busta).getType() != InteroperabilitaType.ANNULLAMENTO_PROTOCOLLAZIONE) {
						// Segnatura e annullamento non hanno il campo e quindi
						// l'errore non va registrato
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_DATA_REGISTRAZIONE_INVIO);
					}
				}

				if (numeroRegistrazioneInvioS != null && !StringUtils.isEmpty(numeroRegistrazioneInvioS)
						&& numeroRegistrazioneInvioS.length() <= NUMERO_REGISTRAZIONE_MAX_LENGTH) {
					try {
						numeroRegistrazioneInvio = Integer.parseInt(numeroRegistrazioneInvioS);
					} catch (Exception e) {
						log.warn("Notifica Interoperabile - Impossibile convertire il numero di registazione del protocollo inviato: "
								+ numeroRegistrazioneInvioS + " - Formato non valido");
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_NUMERO_REGISTRAZIONE_INVIO);
					}
				} else {
					if (((InterBeanOld) busta).getType() != InteroperabilitaType.SEGNATURA
							&& ((InterBeanOld) busta).getType() != InteroperabilitaType.ANNULLAMENTO_PROTOCOLLAZIONE) {
						// Segnatura e annullamento non hanno il campo e quindi
						// l'errore non va registrato
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_NUMERO_REGISTRAZIONE_INVIO);
					}
				}

				if (codiceAOOInvio == null || StringUtils.isEmpty(codiceAOOInvio) /* || !codiceAOOInvio.matches(CODICE_AOO_PATTERN) */
						|| codiceAOOInvio.length() > COD_AOO_MAX_LENGTH) {
					codiceAOOInvio = null;
					if (((InterBeanOld) busta).getType() != InteroperabilitaType.SEGNATURA
							&& ((InterBeanOld) busta).getType() != InteroperabilitaType.ANNULLAMENTO_PROTOCOLLAZIONE) {
						// Segnatura e annullamento non hanno il campo e quindi
						// l'errore non va registrato
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_COD_AOO_INVIO);
					}
				}

				if (codiceAmministrazioneInvio == null || StringUtils.isEmpty(codiceAmministrazioneInvio)
				/* || !codiceAmministrazioneInvio.matches(CODICE_AMMINISTRAZIONE_PATTERN) */
						|| codiceAmministrazioneInvio.length() > COD_AMMINISTRAZIONE_MAX_LENGTH) {
					codiceAmministrazioneInvio = null;
					if (((InterBeanOld) busta).getType() != InteroperabilitaType.SEGNATURA
							&& ((InterBeanOld) busta).getType() != InteroperabilitaType.ANNULLAMENTO_PROTOCOLLAZIONE) {
						// Segnatura e annullamento non hanno il campo e quindi
						// l'errore non va registrato
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_COD_AMMINISTRAZIONE_INVIO);
					}
				}

				if (dataRegistrazioneRicezioneS != null && !StringUtils.isEmpty(dataRegistrazioneRicezioneS)) {
					try {
						// Provo ad ottenere una data
						dataRegistrazioneRicezione = new SimpleDateFormat("yyyy-MM-dd").parse(dataRegistrazioneRicezioneS);
						// Sono sicuro che le prime 4 cirfre indicano l'anno
						annoRegistrazioneRicezioneS = new SimpleDateFormat("yyyy").format(dataRegistrazioneRicezione);
						annoRegistrazioneRicezione = Short.parseShort(annoRegistrazioneRicezioneS);
					} catch (Exception e) {
						log.warn("Notifica Interoperabile - Impossibile convertire la data registrazione del protocollo ricevuto: "
								+ dataRegistrazioneRicezioneS + " - Formato non valido");
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_DATA_REGISTRAZIONE_RICEZIONE);
					}
				} else {
					if (((InterBeanOld) busta).getType() != InteroperabilitaType.NOTIFICA_ECCEZIONE) {
						// Eccezione non ha il campo e quindi l'errore non va
						// registrato
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_DATA_REGISTRAZIONE_RICEZIONE);
					}
				}

				if (numeroRegistrazioneRicezioneS != null && !StringUtils.isEmpty(numeroRegistrazioneRicezioneS)
						&& numeroRegistrazioneRicezioneS.length() <= NUMERO_REGISTRAZIONE_MAX_LENGTH) {
					try {
						numeroRegistrazioneRicezione = Integer.parseInt(numeroRegistrazioneRicezioneS);
					} catch (Exception e) {
						log.warn("Notifica Interoperabile - Impossibile convertire il numero di registazione del protocollo ricevuto: "
								+ numeroRegistrazioneRicezione + " - Formato non valido");
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_NUMERO_REGISTRAZIONE_RICEZIONE);
					}
				} else {
					if (((InterBeanOld) busta).getType() != InteroperabilitaType.NOTIFICA_ECCEZIONE) {
						// Eccezione non ha il campo e quindi l'errore non va
						// registrato
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_NUMERO_REGISTRAZIONE_RICEZIONE);
					}
				}

				if (codiceAOORicezione == null || StringUtils.isEmpty(codiceAOORicezione) /* || !codiceAOORicezione.matches(CODICE_AOO_PATTERN) */
						|| codiceAOORicezione.length() > COD_AOO_MAX_LENGTH) {
					codiceAOORicezione = null;
					if (((InterBeanOld) busta).getType() != InteroperabilitaType.NOTIFICA_ECCEZIONE) {
						// Eccezione non ha il campo e quindi l'errore non va
						// registrato
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_COD_AOO_RICEZIONE);
					}
				}

				if (codiceAmministrazioneRicezione == null || StringUtils.isEmpty(codiceAmministrazioneRicezione)
				/* || !codiceAmministrazioneRicezione.matches(CODICE_AMMINISTRAZIONE_PATTERN) */
						|| codiceAmministrazioneRicezione.length() > COD_AMMINISTRAZIONE_MAX_LENGTH) {
					codiceAmministrazioneRicezione = null;
					if (((InterBeanOld) busta).getType() != InteroperabilitaType.NOTIFICA_ECCEZIONE) {
						// Eccezione non ha il campo e quindi l'errore non va
						// registrato
						busta.addErroreXML(AbstractInterbean.ERRORE_XML_COD_AMMINISTRAZIONE_RICEZIONE);
					}
				}

				if (((InterBeanOld) busta).getType() == InteroperabilitaType.SEGNATURA) {

					// nuova mail: salvo i dati in arrivo ricavati
					// dall'identificatore della segnatura
					if (dataRegistrazioneRicezione != null) {
						emailBean.getMail().setTsRegMitt(dataRegistrazioneRicezione);
					}
					if (annoRegistrazioneRicezione != null) {
						emailBean.getMail().setAnnoRegMitt(annoRegistrazioneRicezione);
					}
					if (numeroRegistrazioneRicezione != null) {
						emailBean.getMail().setNumRegMitt(numeroRegistrazioneRicezione);
					}
					if (oggettoRegistrazioneRicezione != null && !StringUtils.isEmpty(oggettoRegistrazioneRicezione)) {
						emailBean.getMail().setOggettoRegMitt(oggettoRegistrazioneRicezione);
					}

				} else {

					List<TRelEmailMgoBean> listaRelazioni = new ArrayList<TRelEmailMgoBean>();
					List<TRegEstVsEmailBean> listaRegistrazioniEsterneToInsert = new ArrayList<TRegEstVsEmailBean>();
					List<TRegEstVsEmailBean> listaRegistrazioniEsterneToUpdate = new ArrayList<TRegEstVsEmailBean>();

					// operazioni per la gestione delle notifiche interoperabili

					// cerco nelle tabelle del registro se email fanno
					// riferimento a questa protocollazione
					if (((InterBeanOld) busta).getType() != InteroperabilitaType.ANNULLAMENTO_PROTOCOLLAZIONE) {

						// ricavo eventuali id mail associate ai protocolli
						// inviati
						if (annoRegistrazioneInvio != null && numeroRegistrazioneInvio != null) {

							TValDizionarioBean dizCategoria = util.ricavaDizionarioDaValore(Dizionario.CAUSALE);
							TValDizionarioBean dizRuolo = util.ricavaDizionarioDaValore(Dizionario.NOTIFICA_INTEROPERABILE);

							List<TEmailMgo> listaMail = getListaMailProtocolloInviato(idMailBox, idAccount, numeroRegistrazioneInvio, annoRegistrazioneInvio);

							if (listaMail != null && listaMail.size() > 0) {

								// almeno una mail protocollata trovata
								// se è solo una la relazione è sicura
								// altrimenti se ci sono più mail candidate
								// per confermare la relazione è obbligatorio
								// trovare un destinatario associato

								for (TEmailMgo mailProtocollo : listaMail) {

									String idEmailProtocollo = mailProtocollo.getIdEmail();
									String idDestinatario = null;

									// blocco della mail protocollata

									try {

										// blocco creazione record in
										// T_REL_EMAIL_MGO

										try {
											try {
												idDestinatario = getDestinatarioProtocolloInviato(idMailBox, idEmailProtocollo,
														accountDestinatarioEmailInProcess, codiceAOOInvio, codiceAmministrazioneInvio);
											} catch (Exception exc) {
												// salvo comunque la relazione
												// anche senza il destinatario
												// se la mail trovata è l'unica
												if (listaMail.size() != 1) {
													throw exc;
												}
											}
											if (listaMail.size() == 1 || (idDestinatario != null)) {
												// aggiungo la relazione se ho
												// un destinatario o se la mail
												// trovata è l'unica
												listaRelazioni.add(creaRelazione(dizCategoria, dizRuolo, idEmailProtocollo, idEmailInProcess, idDestinatario));
											} else {
												log.warn("Non è possibile creare una relazione in T_REL_EMAIL_MGO");
												log.warn("Id mail ricevuta: " + idEmailInProcess);
												log.warn("Id mail candidata: " + idEmailProtocollo);
												log.warn("Id destinatario: " + idDestinatario);
											}
										} catch (Exception exc) {
											// interrompo e provo con la mail
											// successiva
											log.warn("Errore nella creazione della relazione in T_REL_EMAIL_MGO");
											log.warn("Id mail ricevuta: " + idEmailInProcess);
											log.warn("Id mail candidata: " + idEmailProtocollo);
											log.warn("Id destinatario: " + idDestinatario);
											throw exc;
										}

										// blocco creazione record in
										// T_REG_EST_VS_EMAIL

										try {

											// salvo l'eventuale registrazione
											// esterna
											// solo se ho tutti i dati a
											// disposizione
											if (((InterBeanOld) busta).getType() != InteroperabilitaType.NOTIFICA_ECCEZIONE && idDestinatario != null
													&& numeroRegistrazioneRicezione != null && annoRegistrazioneRicezione != null
													&& dataRegistrazioneRicezione != null && codiceAmministrazioneRicezione != null
													&& codiceAOORicezione != null) {

												// verifico se aggiornare o
												// inserire
												// una
												// registrazione esterna
												// verifico se esiste già un
												// record con il destinatario
												TFilterFetch<TRegEstVsEmailBean> filterFetchRegEsterne = new TFilterFetch<TRegEstVsEmailBean>();
												TRegEstVsEmailBean regEst = new TRegEstVsEmailBean();
												regEst.setIdDestinatarioEmail(idDestinatario);
												filterFetchRegEsterne.setFilter(regEst);
												List<TRegEstVsEmailBean> listRegExt = null;
												DaoTRegEstVsEmail daoRegEst = (DaoTRegEstVsEmail) DaoFactory.getDao(DaoTRegEstVsEmail.class);
												// si cerca fra le relazioni
												// esterne
												// esistenti
												listRegExt = daoRegEst.search(filterFetchRegEsterne).getData();

												if (listRegExt == null || listRegExt.size() == 0) {
													// nessuna relazione
													// presente,
													// ne inserisco una nuova
													listaRegistrazioniEsterneToInsert.add(creaRegistrazioneEsterna(numeroRegistrazioneRicezione,
															dataRegistrazioneRicezione, annoRegistrazioneRicezione, codiceAOORicezione,
															codiceAmministrazioneRicezione, idEmailInProcess, idDestinatario, idEmailProtocollo));
												} else {
													// si aggiorna la precedente
													// relazione: codice
													// amministrazione e data
													// registrazione
													// questo caso si può
													// verificare nel caso
													// riceva un successivo
													// aggiornamento o una nuova
													// conferma alla stessa mail
													// protocollata
													TRegEstVsEmailBean regEsterna = listRegExt.get(0);
													listaRegistrazioniEsterneToUpdate.add(updateRegistrazioneEsterna(regEsterna, numeroRegistrazioneRicezione,
															dataRegistrazioneRicezione, annoRegistrazioneRicezione, codiceAOORicezione,
															codiceAmministrazioneRicezione, idEmailInProcess, idDestinatario, idEmailProtocollo));
												}
											} else {
												if (((InterBeanOld) busta).getType() != InteroperabilitaType.NOTIFICA_ECCEZIONE) {
													log.warn("Informazioni mancanti per creare la registrazione esterna");
													log.warn("Id destinatario: " + idDestinatario);
													log.warn("Numero Registrazione protocollo ricevuto: " + numeroRegistrazioneRicezione);
													log.warn("Data Registrazione protocollo ricevuto: " + dataRegistrazioneRicezione);
													log.warn("Codice AOO protocollo ricevuto: " + codiceAOORicezione);
													log.warn("Codice Amministrazione protocollo ricevuto: " + codiceAmministrazioneRicezione);
													log.warn("Tipologia notifica interoperabile: " + ((InterBeanOld) busta).getType());
												} else {
													log.warn(
															"Non creo una registrazione esterna visto che la notifica è un'eccezione e i dati del prototocollo ricevuti non sono significativi");
												}
											}

										} catch (Exception exc) {
											log.warn("Errore nella creazione o aggiornamento della relazione in T_REG_EST_VS_EMAIL");
											log.warn("Numero Registrazione protocollo ricevuto: " + numeroRegistrazioneRicezione);
											log.warn("Data Registrazione protocollo ricevuto: " + dataRegistrazioneRicezione);
											log.warn("Codice AOO protocollo ricevuto: " + codiceAOORicezione);
											log.warn("Codice Amministrazione protocollo ricevuto: " + codiceAmministrazioneRicezione);
											log.warn("Tipologia notifica interoperabile: " + ((InterBeanOld) busta).getType());
											throw exc;
										}

									} catch (Exception exc) {
										// interrompo e provo con la mail
										// successiva
										log.warn("Si è verificato un errore nella creazione delle relazioni fra: ");
										log.warn("Id mail ricevuta: " + idEmailInProcess);
										log.warn("Id mail candidata: " + idEmailProtocollo);
										log.warn("Id destinatario: " + idDestinatario);
										log.warn("Tipologia notifica interoperabile: " + ((InterBeanOld) busta).getType());
										log.warn(exc);
									}

								}

							} else {
								log.warn("Nessuno protocollo trovato");
								log.warn("Numero Registrazione protocollo inviato: " + numeroRegistrazioneInvio);
								log.warn("Data Registrazione protocollo inviato: " + dataRegistrazioneInvio);
								log.warn("Codice AOO protocollo inviato: " + codiceAOOInvio);
								log.warn("Codice Amministrazione protocollo inviato: " + codiceAmministrazioneInvio);
								log.warn("Tipologia notifica interoperabile: " + ((InterBeanOld) busta).getType());
							}

						} else {
							log.warn("Informazioni mancanti per ricavare la mail originale con il protocollo di riferimento");
							log.warn("Numero Registrazione protocollo inviato: " + numeroRegistrazioneInvio);
							log.warn("Data Registrazione protocollo inviato: " + dataRegistrazioneInvio);
							log.warn("Codice AOO protocollo inviato: " + codiceAOOInvio);
							log.warn("Codice Amministrazione protocollo inviato: " + codiceAmministrazioneInvio);
							log.warn("Tipologia notifica interoperabile: " + ((InterBeanOld) busta).getType());
						}

					}

					// logiche per annullamento protocollo: in questo caso si
					// presume che ci sia già una registrazione esterna salvata
					// nella conferma o nell'aggiornamento
					// si recupera quindi quella mail relativa al protocollo e
					// si crea una nuova relazione esterna
					else {
						List<TRegEstVsEmailBean> listRegExt = null;
						if (annoRegistrazioneRicezione != null && numeroRegistrazioneRicezione != null && codiceAOORicezione != null
								&& codiceAmministrazioneRicezione != null && dataRegistrazioneRicezione != null) {
							DaoTRegEstVsEmail daoRegEst = (DaoTRegEstVsEmail) DaoFactory.getDao(DaoTRegEstVsEmail.class);
							TFilterFetch<TRegEstVsEmailBean> filterFetchRegEsterne = new TFilterFetch<TRegEstVsEmailBean>();
							TRegEstVsEmailBean regEsterna = new TRegEstVsEmailBean();
							regEsterna.setAnnoReg(annoRegistrazioneRicezione);
							regEsterna.setNumReg(numeroRegistrazioneRicezione);
							regEsterna.setCodAoo(codiceAOORicezione);
							regEsterna.setCodAmministrazione(codiceAmministrazioneRicezione);
							regEsterna.setTsReg(dataRegistrazioneRicezione);
							// ordinamento per data di inserimento
							List<TOrderBy> orderList = new ArrayList<TOrderBy>();
							TOrderBy orderBy = new TOrderBy();
							orderBy.setType(OrderByType.DESCENDING);
							orderBy.setPropname("tsIns");
							orderList.add(orderBy);
							filterFetchRegEsterne.setOrders(orderList);
							filterFetchRegEsterne.setFilter(regEsterna);
							listRegExt = daoRegEst.search(filterFetchRegEsterne).getData();
							if (listRegExt.size() > 0) {
								// se per caso sono arrivate
								// due
								// conferme e
								// quindi ci sono due relazioni esterne riferite
								// alla stessa email con il protocollo inviato
								// si crea una relazione esterna solo con
								// l'ultima inserita
								listaRelazioni.add(creaRelazioneAnnullamento(listRegExt.get(0), idEmailInProcess));
							} else {
								log.warn("Nessuna registrazione esterna trovata per le informazioni di protocollo");
								log.warn("Numero Registrazione protocollo ricevuto: " + numeroRegistrazioneRicezione);
								log.warn("Data Registrazione protocollo ricevuto: " + dataRegistrazioneRicezione);
								log.warn("Codice AOO protocollo ricevuto: " + codiceAOORicezione);
								log.warn("Codice Amministrazione protocollo ricevuto: " + codiceAmministrazioneRicezione);
								log.warn("Tipologia notifica interoperabile: " + ((InterBeanOld) busta).getType());
							}
						} else {
							log.warn("Informazioni mancanti per creare la registrazione esterna");
							log.warn("Numero Registrazione protocollo ricevuto: " + numeroRegistrazioneRicezione);
							log.warn("Data Registrazione protocollo ricevuto: " + dataRegistrazioneRicezione);
							log.warn("Codice AOO protocollo ricevuto: " + codiceAOORicezione);
							log.warn("Codice Amministrazione protocollo ricevuto: " + codiceAmministrazioneRicezione);
							log.warn("Tipologia notifica interoperabile: " + ((InterBeanOld) busta).getType());
						}
					}
					interopBean.setRelazioniEsterneToInsert(listaRegistrazioniEsterneToInsert);
					interopBean.setRelazioniEsterneToUpdate(listaRegistrazioniEsterneToUpdate);
					emailBean.setRelMail(listaRelazioni);
					// aggiorno i flag delle varie mail aggangiate, in modo da
					// evidenziare che esistono le relative mail di notifica
					interopBean.setMailAgganciate(restituisciMailAgganciateAggiornate(emailBean.getRelMail(), ((InterBeanOld) busta).getType()));
					// logiche per il cambio di folder
					interopBean = eseguiLogicheCambioFolder(interopBean, ((InterBeanOld) busta).getType(), emailBean.getMail().getIdCasella(), idMailBox);
					// setto nel bean della mail l'oggetto interopBean con le
					// relazioni create
					emailBean.setInterOp(interopBean);
				}
			} else {
				// nessuna operazione da fare visto che sono presenti degli
				// errori nell'XML
				emailBean.setInterOp(null);
			}

		} catch (Exception exc) {
			// eventuali eccezioni non devono causare l'annullamento
			// dell'archiviazione della mail
			// ma solo il salvataggio di eventuali avvertimenti
			log.warn("Eccezione InteropManagaer ", exc);
			busta.addErroreXML("Eccezione InteropManagaer: " + exc.getMessage() + "\r\n");
			emailBean.setInterOp(null);
		}

		return emailBean;
	}

	/**
	 * Metodo per recuperare le mail con il protocollo inviato a partire dalle informazioni ricevute da una conferma, un'eccezione o una conferma interoperabile
	 * <br>
	 * Si verifica se la mail è effettivamente associata alla mailbox che si sta processando <br>
	 * I risultati possono essere molteplici visto che uno stesso protocollo potrebbe essere stato mandato più volte in differenti mail
	 *
	 * @param idAccount
	 * @param accountDestinatarioEmail
	 * @param codiceAOO
	 * @param codiceAmministrazione
	 * @param numeroRegistrazione
	 * @param annoRegistrazione
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	private List<TEmailMgo> getListaMailProtocolloInviato(String idMailbox, String idAccount, Integer numeroRegistrazione, Short annoRegistrazione)
			throws Exception {

		Session session = null;
		Transaction transaction = null;

		List<TEmailMgo> listaMail = null;

		try {

			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();

			Criteria criteriaMailProtocollo = session.createCriteria(TEmailMgo.class, "mail").createAlias("TRegProtVsEmails", "prot")
					.add(Restrictions.eq("mail.mailboxAccount.idAccount", idAccount));

			criteriaMailProtocollo.add(Restrictions.eq("prot.annoReg", annoRegistrazione))
					.add(Restrictions.eq("prot.numReg", new BigDecimal(numeroRegistrazione)));

			listaMail = criteriaMailProtocollo.list();

		} catch (Exception exc) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.warn("Errore nel recupero della mail con il protocollo inviato", exc);
			throw exc;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return listaMail;

	}

	/**
	 * Metodo per recuperare il destinatario della mail con il protocollo inviato La prima ricerca verifica eventuali registrazioni di protocollo inviate a
	 * destinatari che coincidono con l'account della mail che si sta processando In mancanza di risultati si ricercano eventuali voci di rubrica associate a
	 * codice di amministrazione e codice AOO, visto che la mail di protocollazione potrebbe essere stata spedita a un indirizzo differente
	 * 
	 * @param idMailBox
	 * @param idMailProtocollo
	 * @param accountDestinatarioEmail
	 * @param codiceAOO
	 * @param codiceAmministrazione
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	private String getDestinatarioProtocolloInviato(String idMailbox, String idMailProtocollo, String accountDestinatarioEmail, String codiceAOO,
			String codiceAmministrazione) throws Exception {

		Session session = null;
		Transaction transaction = null;

		String idDestinatario = null;

		try {

			session = SubjectInitializer.getSession(idMailbox);
			transaction = session.beginTransaction();

			Criteria criteriaDestinatarioProtocollo = session.createCriteria(TDestinatariEmailMgo.class, "destinatari")
					.add(Restrictions.eq("TEmailMgo.idEmail", idMailProtocollo))
					.add(Restrictions.eq("destinatari.accountDestinatario", accountDestinatarioEmail));

			List<TDestinatariEmailMgo> listaDestinatari = criteriaDestinatarioProtocollo.list();
			if (listaDestinatari.size() == 0 && codiceAmministrazione != null && codiceAOO != null) {
				// ricerco in rubrica eventuali destinatari associati al codice
				// amministrazione e al codice AOO
				// possono essercene diversi in rubrica associati a codice AOO e
				// codice Amministrazione
				// in questo caso prendo il più recente

				DetachedCriteria detachedCriteriaRubrica = DetachedCriteria.forClass(TRubricaEmail.class, "rubrica");
				detachedCriteriaRubrica.add(Property.forName("codAmministrazione").eq(codiceAmministrazione.toLowerCase()).ignoreCase())
						.add(Property.forName("codAoo").eq(codiceAOO.toLowerCase()).ignoreCase());

				Criteria criteriaRubricaDestinatari = session.createCriteria(TDestinatariEmailMgo.class, "destinatari")
						.add(Restrictions.eq("TEmailMgo.idEmail", idMailProtocollo))
						.add(Subqueries.propertyIn("accountDestinatario", detachedCriteriaRubrica.setProjection(Projections.property("accountEmail"))))
						.addOrder(Property.forName("tsIns").desc()).setMaxResults(1);
				listaDestinatari = criteriaRubricaDestinatari.list();
			}

			if (listaDestinatari.size() != 0) {
				idDestinatario = listaDestinatari.get(0).getIdDestinatarioEmail();
			}

		} catch (Exception exc) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.warn("Errore nel recupero del destinatario mail del protocollo inviato - id mail: " + idMailProtocollo, exc);
			throw exc;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return idDestinatario;

	}

	/**
	 * Salvataggio di una nuova relazione fra la mail della registrazione esterna e la mail processata
	 * 
	 * @param regEsterna
	 * @param idEmailInProcess
	 * @return
	 * @throws MailServerException
	 */

	private TRelEmailMgoBean creaRelazioneAnnullamento(TRegEstVsEmailBean regEsterna, String idEmailInProcess) throws Exception {
		util = new Utilities();
		TValDizionarioBean dizCategoria = util.ricavaDizionarioDaValore(Dizionario.CAUSALE);
		TValDizionarioBean dizRuolo = util.ricavaDizionarioDaValore(Dizionario.NOTIFICA_INTEROPERABILE);
		TRelEmailMgoBean rel = new TRelEmailMgoBean();
		rel.setIdRelEmail(KeyGenerator.gen());
		rel.setIdEmail1(idEmailInProcess);
		rel.setIdEmail2(regEsterna.getIdEmailInviata());
		rel.setIdDestinatario2(regEsterna.getIdDestinatarioEmail());
		rel.setFlgRelAutomatica(true);
		rel.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
		rel.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
		return rel;
	}

	/**
	 * Creazione di un nuovo oggetto TRegEstVsEmailBean per il salvataggio di una registrazione esterna
	 * 
	 * @param numeroRegistrazioneRicezione
	 * @param dataRegistrazioneRicezione
	 * @param annoRegistrazioneRicezione
	 * @param AOORicezione
	 * @param amministrazione
	 * @param idEmailRicevuta
	 * @param idDestinatarioMail
	 * @param idEmailInviata
	 * @return
	 * @throws Exception
	 */

	private TRegEstVsEmailBean creaRegistrazioneEsterna(Integer numeroRegistrazioneRicezione, Date dataRegistrazioneRicezione, Short annoRegistrazioneRicezione,
			String AOORicezione, String amministrazione, String idEmailRicevuta, String idDestinatarioMail, String idEmailInviata) throws Exception {
		TRegEstVsEmailBean nuovaReg = new TRegEstVsEmailBean();
		nuovaReg = new TRegEstVsEmailBean();
		nuovaReg.setIdRegEstEmail(KeyGenerator.gen());
		nuovaReg.setAnnoReg(annoRegistrazioneRicezione);
		nuovaReg.setNumReg(numeroRegistrazioneRicezione);
		nuovaReg.setCodAoo(AOORicezione);
		nuovaReg.setCodAmministrazione(amministrazione);
		nuovaReg.setTsReg(dataRegistrazioneRicezione);
		nuovaReg.setIdEmailRicevuta(idEmailRicevuta); // mail che si sta
														// processando che
														// contiene la notifica
		nuovaReg.setIdEmailInviata(idEmailInviata); // mail che si è inviata
													// contenente il protocollo
		nuovaReg.setIdDestinatarioEmail(idDestinatarioMail); // id del
																// destinatario
																// della mail
		return nuovaReg;
	}

	/**
	 * Metodo di aggiornamento della relazione esterna Si aggiornano tutti i campi, a parte l'id della registrazione
	 * 
	 * @param regEsterna
	 * @param numeroRegistrazioneRicezione
	 * @param dataRegistrazioneRicezione
	 * @param annoRegistrazioneRicezione
	 * @param AOORicezione
	 * @param amministrazione
	 * @param idEmailRicevuta
	 * @param idDestinatarioMail
	 * @param idEmailInviata
	 * @return
	 * @throws Exception
	 */

	private TRegEstVsEmailBean updateRegistrazioneEsterna(TRegEstVsEmailBean regEsterna, Integer numeroRegistrazioneRicezione, Date dataRegistrazioneRicezione,
			Short annoRegistrazioneRicezione, String AOORicezione, String amministrazione, String idEmailRicevuta, String idDestinatarioMail,
			String idEmailInviata) throws Exception {
		regEsterna.setAnnoReg(annoRegistrazioneRicezione);
		regEsterna.setNumReg(numeroRegistrazioneRicezione);
		regEsterna.setCodAoo(AOORicezione);
		regEsterna.setCodAmministrazione(amministrazione);
		regEsterna.setTsReg(dataRegistrazioneRicezione);
		regEsterna.setIdEmailRicevuta(idEmailRicevuta); // mail che si sta
														// processando che
														// contiene la notifica
		regEsterna.setIdEmailInviata(idEmailInviata); // mail che si è inviata
														// contenente il
														// protocollo
		regEsterna.setIdDestinatarioEmail(idDestinatarioMail);
		return regEsterna;
	}

	/**
	 * Metodo di creazione di un nuovo oggetto TRelEmailMgoBean per il salvataggio della mail originale contenente il protocollo e la notifica ricevuta
	 * 
	 * @param dizCategoria
	 * @param dizRuolo
	 * @param protocollo
	 * @param idEmailInProcess
	 * @param idDestinatario
	 * @return
	 * @throws Exception
	 */

	private TRelEmailMgoBean creaRelazione(TValDizionarioBean dizCategoria, TValDizionarioBean dizRuolo, String idEmailProtocollo, String idEmailInProcess,
			String idDestinatario) throws Exception {
		TRelEmailMgoBean relEmail = new TRelEmailMgoBean();
		relEmail.setIdRelEmail(KeyGenerator.gen()); // genero un id della
													// relazione
		relEmail.setIdEmail1(idEmailInProcess);
		relEmail.setIdEmail2(idEmailProtocollo);
		relEmail.setIdDestinatario2(idDestinatario);
		relEmail.setFlgRelAutomatica(true);
		relEmail.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
		relEmail.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
		return relEmail;
	}

	/**
	 * Metodo di aggiornamento dei flag delle notifiche interoperabili per le relazioni fra mail protocollate
	 * 
	 * @param listaRelazioni
	 * @param tipo
	 * @return
	 * @throws Exception
	 */

	private List<TEmailMgoBean> restituisciMailAgganciateAggiornate(List<TRelEmailMgoBean> listaRelazioni, InteroperabilitaType tipo) throws Exception {
		List<TEmailMgoBean> ret = new ArrayList<TEmailMgoBean>();
		if (listaRelazioni != null && listaRelazioni.size() != 0) {
			DaoTEmailMgo daoTEmailMgo = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			for (TRelEmailMgoBean rel : listaRelazioni) {
				TFilterFetch<TEmailMgoBean> ff = new TFilterFetch<TEmailMgoBean>();
				TEmailMgoBean filtro = new TEmailMgoBean();
				filtro.setIdEmail(rel.getIdEmail2());
				ff.setFilter(filtro);
				List<TEmailMgoBean> mails = null;
				try {
					mails = daoTEmailMgo.search(ff).getData();
					TEmailMgoBean bean = null;
					if (mails != null && mails.size() != 0) {
						bean = mails.get(0);
						switch (tipo) {
						case AGGIORNAMENTO_CONFERMA:
							bean.setFlgNotifInteropAgg(true);
							break;
						case NOTIFICA_ECCEZIONE:
							bean.setFlgNotifInteropEcc(true);
							break;
						case ANNULLAMENTO_PROTOCOLLAZIONE:
							bean.setFlgNotifInteropAnn(true);
							break;
						case CONFERMA_RICEZIONE:
							bean.setFlgNotifInteropConf(true);
							break;
						}
					}
					ret.add(bean);
				} catch (Exception e) {
					log.error("Impossibile aggiornare i flag interoperabili per la mail con id " + rel.getIdEmail2(), e);
					throw e;
				}
			}
		}
		return ret;
	}

}
