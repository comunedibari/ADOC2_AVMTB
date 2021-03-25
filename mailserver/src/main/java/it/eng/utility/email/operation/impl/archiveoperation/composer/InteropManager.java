package it.eng.utility.email.operation.impl.archiveoperation.composer;

import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.utility.email.operation.impl.interoperation.AbstractInterbean;
import it.eng.utility.email.operation.impl.interoperation.InterBean;
import it.eng.utility.email.operation.impl.interoperation.InteroperabilitaType;
import it.eng.utility.email.process.exception.MailServerException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * classe per la verifica degli xml della interoperabilità
 * 
 * @author jravagnan
 * 
 */
public class InteropManager {

	private Logger log = LogManager.getLogger(InteropManager.class);

	private AbstractInterbean busta;

	private String idEmail;

	private String accountDestinatarioEmail;

	boolean isNew = false;

	public InteropManager(AbstractInterbean interbean, String idEmail, String accountDesy) {
		this.busta = interbean;
		this.idEmail = idEmail;
		this.accountDestinatarioEmail = accountDesy;
		if (interbean instanceof InterBean) {
			isNew = true;
		} else
			isNew = false;
	}

	/**
	 * fa una verifica complessiva del file segnatura
	 * 
	 * @param email
	 * @param codAOO
	 * @param codEnte
	 * @return
	 * @throws MailServerException
	 */
	public InteropVerificationData verificaSegnatura(String email, String codAOO, String codEnte) throws MailServerException {
		if (isNew) {
			return new NewSegnaturaVerifier().verify(busta, email, codAOO, codEnte);
		} else {
			return new OldSegnaturaVerifier().verify(busta, email, codAOO, codEnte);
		}
	}

	public EmailBean gestisciNotificheInteroperabili(EmailBean bean,String idCasella) throws MailServerException {
		// per comodità si aggiunge anche la gestione della segnatura qui, anche se non è una notifica interoperabile
		if (busta.getType() == (InteroperabilitaType.AGGIORNAMENTO_CONFERMA)
				|| busta.getType() == (InteroperabilitaType.CONFERMA_RICEZIONE)
				|| busta.getType() == (InteroperabilitaType.NOTIFICA_ECCEZIONE)
				|| busta.getType() == (InteroperabilitaType.ANNULLAMENTO_PROTOCOLLAZIONE)
				|| busta.getType() == (InteroperabilitaType.SEGNATURA)) {
			if (isNew) {
				return new NewInteropNotifManager().notifInteropManage(busta, bean, idEmail, accountDestinatarioEmail,idCasella);
			} else {
				return new OldInteropNotifManager().notifInteropManage(busta, bean, idEmail, accountDestinatarioEmail,idCasella);
			}
		} else {
			log.error("Il tipo di xml non è AGGIORNAMENTO_CONFERMA,CONFERMA_RICEZIONE,NOTIFICA_ECCEZIONE o ANNULLAMENTO_PROTOCOLLAZIONE per cui non processabile");
			throw new MailServerException(
					"Il tipo di xml non è AGGIORNAMENTO_CONFERMA,CONFERMA_RICEZIONE,NOTIFICA_ECCEZIONE o ANNULLAMENTO_PROTOCOLLAZIONE per cui non processabile");
		}

	}

	public AbstractInterbean getBusta() {
		return busta;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public String getIdDestinatarioEmail() {
		return accountDestinatarioEmail;
	}

}
