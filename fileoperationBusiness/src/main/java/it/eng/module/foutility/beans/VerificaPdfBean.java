package it.eng.module.foutility.beans;

/**
 * 
 * @author DANCRIST
 *
 */

public class VerificaPdfBean {
	
	/**
	 * Attiva = true/false verifica
	 */
	private String attivaVerificaEditabili = "false";
	
	private String attivaVerificaCommenti = "false";
	

	public String getAttivaVerificaEditabili() {
		return attivaVerificaEditabili;
	}
	public void setAttivaVerificaEditabili(String attivaVerificaEditabili) {
		this.attivaVerificaEditabili = attivaVerificaEditabili;
	}
	
	public String getAttivaVerificaCommenti() {
		return attivaVerificaCommenti;
	}
	public void setAttivaVerificaCommenti(String attivaVerificaCommenti) {
		this.attivaVerificaCommenti = attivaVerificaCommenti;
	}
	
}