package it.eng.utility.email.config;

import it.eng.utility.email.util.segnatura.AggiornamentoConferma;
import it.eng.utility.email.util.segnatura.AnnullamentoProtocollazione;
import it.eng.utility.email.util.segnatura.ConfermaRicezione;
import it.eng.utility.email.util.segnatura.NotificaEccezione;
import it.eng.utility.email.util.segnatura.Segnatura;
import it.eng.utility.email.util.xml.SegnaturaNewXmlValidationHandler;

import java.io.IOException;

public class NewVersionChecker extends VersionChecker {

	public NewVersionChecker() throws IOException {
		super(new SegnaturaNewXmlValidationHandler());
	}
	
	@Override
	public Risultato checkSegnatura(String lStrXml) {
		return checkVersion(Segnatura.class, lStrXml);
	}

	@Override
	public Risultato checkConfermaRicezione(String lStrXml) {
		return checkVersion(ConfermaRicezione.class, lStrXml);
	}

	@Override
	public Risultato checkAggiornamentoConferma(String lStrXml) {
		return checkVersion(AggiornamentoConferma.class, lStrXml);
	}

	@Override
	public Risultato checkAnnullamentoProtocollazione(String lStrXml) {
		return checkVersion(AnnullamentoProtocollazione.class, lStrXml);
	}

	@Override
	public Risultato checkNotificaEccezione(String lStrXml) {
		return checkVersion(NotificaEccezione.class, lStrXml);
	}

}
