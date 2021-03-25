package it.eng.utility.email.operation.impl.interoperation;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.utility.email.config.VersionCheckerUtil;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.operation.impl.interoperation.exception.InteropDuplicateException;
import it.eng.utility.email.util.segnatura.AggiornamentoConferma;
import it.eng.utility.email.util.segnatura.AnnullamentoProtocollazione;
import it.eng.utility.email.util.segnatura.ConfermaRicezione;
import it.eng.utility.email.util.segnatura.NotificaEccezione;
import it.eng.utility.email.util.segnatura.Segnatura;

/**
 * Operazioni di controllo per l'interoperabilità che gestisce vecchio e nuovo dtd
 * 
 * @author jravagnan
 * 
 */
@MessageOperation(description = "Controlla se i file in attachements contengono dei tipi interoperabili", name = "InterOperation")
public class InterOperation extends AbstractMessageOperation<AbstractInterbean> {

	@ConfigOperation(title = "Case sensitive", name = "interoperation.casesensitive", description = "Indica se i nomi dei file devono essere recuperati in modalità case sensitive o meno (Default false)")
	private Boolean iscasesensitive = false;
	public static final String UTF8_BOM = "\uFEFF";

	private static final Logger log = LogManager.getLogger(InterOperation.class);

	@SuppressWarnings("unchecked")
	@Override
	public AbstractInterbean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		log.debug("Invocata la InterOperation per il messaggio con id: " + message.getHeaderinfo().getMessageid());

		AbstractInterbean abstractInterBean = null;
		abstractInterBean = new AbstractInterbean();
		abstractInterBean.setIsdtdValid(false);
		abstractInterBean.setIsInteroperabile(false);
		abstractInterBean.setIsXmlDuplicate(false);
		// Recupero gli attach
		List<MailAttachmentsBean> attachments = message.getAttachmentsWithPrincipalMail();
		log.debug("Recupero gli attach");
		if (attachments != null) {
			try {
				for (MailAttachmentsBean attachment : attachments) {

					String name = attachment.getFilename();
					InteroperabilitaType type = InteroperabilitaType.getValueForFileName(name, iscasesensitive);
					log.debug("Tipo di interoperabilità: " + type);
					if (type != null) {
						abstractInterBean.setIsInteroperabile(true);
						controllDuplicate(abstractInterBean);
						abstractInterBean.setType(type);
						VersionCheckerUtil lVersionCheckerUtil = new VersionCheckerUtil();
						log.debug("Creato oggetto versionCheckerUtil");
						// String lStrXmlFile = FileUtils.readFileToString(attachment.getFile());
						byte[] bytes = FileUtils.readFileToByteArray(attachment.getFile());
						String lStrXmlFile = new String(bytes, StandardCharsets.UTF_8);
						// elimino eventualmente il BOM presente
						if (lStrXmlFile.startsWith(UTF8_BOM)) {
							lStrXmlFile = lStrXmlFile.substring(1);
						}
						log.debug("Letto l'xml dal file");
						// si verifica se è una nuova o una vecchia interoperabilità validando il file xml con entrambi i DTD
						// a parità di risultato si sceglie la versione nuova se è presente il codice registro
						boolean isNew = lVersionCheckerUtil.isNew(lStrXmlFile, type);
						log.debug("Nuova interoperabilità: " + isNew);
						abstractInterBean.setIsdtdValid(lVersionCheckerUtil.isDtdValid());
						log.debug("DTD valido: " + abstractInterBean.getIsdtdValid());
						if (isNew) {
							InterBean lInterBean = new InterBean();
							BeanUtilsBean2.getInstance().copyProperties(lInterBean, abstractInterBean);
							abstractInterBean = lInterBean;
							abstractInterBean.setIsNew(true);
						} else {
							InterBeanOld lInterBeanOld = new InterBeanOld();
							BeanUtilsBean2.getInstance().copyProperties(lInterBeanOld, abstractInterBean);
							abstractInterBean = lInterBeanOld;
							abstractInterBean.setIsNew(false);
						}
						if (lVersionCheckerUtil.isDtdValid()) {
							if (lVersionCheckerUtil.isXmlValido()) {
								log.debug("Xml valido");
								// popolo la corrispondente proprietà del bean in base al tipo di notifica
								popolaInteroperabilita(abstractInterBean, type, lVersionCheckerUtil, isNew);
								log.debug("Finito popola interoperabilità");
							} else {
								// xml non valido -> salvo gli errori della validazione XMl
								// non popolo proprietà di interoperbilità
								log.debug("Xml non valido");
								abstractInterBean.setErroriConformita(lVersionCheckerUtil.getErrori());
								log.debug("Settati gli errori");
							}
						}
					}
				}
			} catch (InteropDuplicateException e) {
				// Interbean duplicato messaggio non interoperabile
				log.error("Eccezione elaborate message: " + e.getMessage(), e);
				abstractInterBean.setIsXmlDuplicate(true);
			}
		}
		return abstractInterBean;
	}

	protected void popolaInteroperabilita(AbstractInterbean bean, InteroperabilitaType type, VersionCheckerUtil lVersionCheckerUtil, boolean isNew) {
		switch (type) {
		case SEGNATURA:
			if (isNew) {
				Segnatura lSegnatura = (Segnatura) lVersionCheckerUtil.getBean();
				((InterBean) bean).setSegnatura(lSegnatura);
			} else {
				it.eng.utility.email.old.segnatura.Segnatura lSegnatura = (it.eng.utility.email.old.segnatura.Segnatura) lVersionCheckerUtil.getBean();
				((InterBeanOld) bean).setSegnatura(lSegnatura);
			}
			break;
		case CONFERMA_RICEZIONE:
			if (isNew) {
				ConfermaRicezione lConfermaRicezione = (ConfermaRicezione) lVersionCheckerUtil.getBean();
				((InterBean) bean).setConfermaricezione(lConfermaRicezione);
			} else {
				it.eng.utility.email.old.segnatura.ConfermaRicezione lConfermaRicezione = (it.eng.utility.email.old.segnatura.ConfermaRicezione) lVersionCheckerUtil
						.getBean();
				((InterBeanOld) bean).setConfermaricezione(lConfermaRicezione);
			}
			break;
		case AGGIORNAMENTO_CONFERMA:
			if (isNew) {
				AggiornamentoConferma lAggiornamentoConferma = (AggiornamentoConferma) lVersionCheckerUtil.getBean();
				((InterBean) bean).setAggiornamentoconferma(lAggiornamentoConferma);
			} else {
				it.eng.utility.email.old.segnatura.AggiornamentoConferma lAggiornamentoConferma = (it.eng.utility.email.old.segnatura.AggiornamentoConferma) lVersionCheckerUtil
						.getBean();
				((InterBeanOld) bean).setAggiornamentoconferma(lAggiornamentoConferma);
			}
			break;
		case ANNULLAMENTO_PROTOCOLLAZIONE:
			if (isNew) {
				AnnullamentoProtocollazione lAnnullamentoProtocollazione = (AnnullamentoProtocollazione) lVersionCheckerUtil.getBean();
				((InterBean) bean).setAnnullamentoprotocollazione(lAnnullamentoProtocollazione);
			} else {
				it.eng.utility.email.old.segnatura.AnnullamentoProtocollazione lAnnullamentoProtocollazione = (it.eng.utility.email.old.segnatura.AnnullamentoProtocollazione) lVersionCheckerUtil
						.getBean();
				((InterBeanOld) bean).setAnnullamentoprotocollazione(lAnnullamentoProtocollazione);
			}
			break;
		case NOTIFICA_ECCEZIONE:
			if (isNew) {
				NotificaEccezione lNotificaEccezione = (NotificaEccezione) lVersionCheckerUtil.getBean();
				((InterBean) bean).setNotificaeccezione(lNotificaEccezione);
			} else {
				it.eng.utility.email.old.segnatura.NotificaEccezione lNotificaEccezione = (it.eng.utility.email.old.segnatura.NotificaEccezione) lVersionCheckerUtil
						.getBean();
				((InterBeanOld) bean).setNotificaeccezione(lNotificaEccezione);
			}
			break;
		}
	}

	/**
	 * Controllo se esite già un valore del messaggio interoperabile settato
	 * 
	 * @param bean
	 * @throws InteropDuplicateException
	 */
	private void controllDuplicate(AbstractInterbean bean) throws InteropDuplicateException {
		if (bean.getType() != null) {
			throw new InteropDuplicateException();
		}
	}

	public Boolean getIscasesensitive() {
		return iscasesensitive;
	}

	public void setIscasesensitive(Boolean iscasesensitive) {
		this.iscasesensitive = iscasesensitive;
	}

}
