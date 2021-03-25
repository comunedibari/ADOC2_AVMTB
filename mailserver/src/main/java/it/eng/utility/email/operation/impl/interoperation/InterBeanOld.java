package it.eng.utility.email.operation.impl.interoperation;



import it.eng.utility.email.old.segnatura.AnnullamentoProtocollazione;
import it.eng.utility.email.old.segnatura.ConfermaRicezione;
import it.eng.utility.email.old.segnatura.NotificaEccezione;
import it.eng.utility.email.old.segnatura.Segnatura;
import it.eng.utility.email.old.segnatura.AggiornamentoConferma;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean che incapsula la segnatura con vecchio xsd
 * @author jravagnan
 */
@XmlRootElement
public class InterBeanOld extends AbstractInterbean{

	
	private Segnatura segnatura;
	private ConfermaRicezione confermaricezione;
	private AggiornamentoConferma aggiornamentoconferma;
	private NotificaEccezione notificaeccezione;
	private AnnullamentoProtocollazione annullamentoprotocollazione;


	public Segnatura getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(Segnatura segnatura) {
		this.segnatura = segnatura;
	}

	public ConfermaRicezione getConfermaricezione() {
		return confermaricezione;
	}

	public void setConfermaricezione(ConfermaRicezione confermaricezione) {
		this.confermaricezione = confermaricezione;
	}

	public AggiornamentoConferma getAggiornamentoconferma() {
		return aggiornamentoconferma;
	}

	public void setAggiornamentoconferma(AggiornamentoConferma aggiornamentoconferma) {
		this.aggiornamentoconferma = aggiornamentoconferma;
	}

	public NotificaEccezione getNotificaeccezione() {
		return notificaeccezione;
	}

	public void setNotificaeccezione(NotificaEccezione notificaeccezione) {
		this.notificaeccezione = notificaeccezione;
	}

	public AnnullamentoProtocollazione getAnnullamentoprotocollazione() {
		return annullamentoprotocollazione;
	}

	public void setAnnullamentoprotocollazione(
			AnnullamentoProtocollazione annullamentoprotocollazione) {
		this.annullamentoprotocollazione = annullamentoprotocollazione;
	}



	
}