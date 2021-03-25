package it.eng.utility.email.config;

import java.util.List;

import it.eng.utility.email.config.VersionChecker.Risultato;

public interface VersionCheckerInterface {
	
	public Risultato checkSegnatura(String lStrXml);

	public Risultato checkConfermaRicezione(String lStrXml);
	
	public Risultato checkAggiornamentoConferma(String lStrXml);
	
	public Risultato checkAnnullamentoProtocollazione(String lStrXml);
	
	public Risultato checkNotificaEccezione(String lStrXml);
	
	public Object getBean();
	
	public List<String> getErrori();
	
}
