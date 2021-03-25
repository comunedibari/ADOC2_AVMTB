package it.eng.auriga.ui.module.layout.client.protocollazione;

/**
 * Valida l'AllegatoCanvas cui viene passato
 * @author massimo malvestio
 *
 */
public interface AllegatoValidator {

	/**
	 * Valida i contenuti dell'AllegatoItem
	 * @return
	 */
	public boolean validate();
	
	/**
	 * @return ritorna l'eventuale messagio impostato
	 */
	public String getMessage();
	
}
