package it.eng.utility.email.config;

import it.eng.utility.email.old.segnatura.AggiornamentoConferma;
import it.eng.utility.email.old.segnatura.AnnullamentoProtocollazione;
import it.eng.utility.email.old.segnatura.ConfermaRicezione;
import it.eng.utility.email.old.segnatura.NotificaEccezione;
import it.eng.utility.email.old.segnatura.Segnatura;
import it.eng.utility.email.util.xml.SegnaturaOldXmlValidationHandler;

import java.io.IOException;

public class OldVersionChecker extends VersionChecker {

	public OldVersionChecker() throws IOException {
		super(new SegnaturaOldXmlValidationHandler());
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
