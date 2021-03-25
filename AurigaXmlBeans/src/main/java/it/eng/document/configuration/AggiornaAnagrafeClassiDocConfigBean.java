package it.eng.document.configuration;

/**
 * Permette di definire valori di configurazione necessari a invocare i servizi per l'aggiornamento dei dati delle classi documentali
 * @author Mattia Zanetti
 *
 */

public class AggiornaAnagrafeClassiDocConfigBean {
	
	private String defaultSchema;

	public String getDefaultSchema() {
		return defaultSchema;
	}

	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}

}
