package it.eng.utility.email.operation.impl.interoperation;

import it.eng.utility.email.util.segnatura.AggiornamentoConferma;
import it.eng.utility.email.util.segnatura.AnnullamentoProtocollazione;
import it.eng.utility.email.util.segnatura.ConfermaRicezione;
import it.eng.utility.email.util.segnatura.NotificaEccezione;
import it.eng.utility.email.util.segnatura.Segnatura;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean che incapsula la segnatura nuovo xsd
 * @author jravagnan
 */
@XmlRootElement
public class InterBean extends AbstractInterbean{
	
	public static final String ERRORE_XML_CODICE_REGISTRO_RICEZIONE = "Codice registro del protocollo ricevuto non presente o non valido\r\n";
	
	public static final String ERRORE_XML_CODICE_REGISTRO_INVIO = "Codice registro del protocollo inviato non presente o non valido\r\n";
	
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