package it.eng.utility.email.process.exception;

/**
 * lancio questa eccezione fittizia nel caso mi imbatta in una operazione asincrona, in questo modo si riesce a gestire il flusso dell'operazione
 * 
 * @author mzanetti
 *
 */

public class PlannedOperation extends Exception {

	public PlannedOperation() {
	}
}
