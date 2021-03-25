package it.eng.bean;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contiene le informazioni relative al risultato del caricamento del file, con
 * eventuale messaggio informativo
 * 
 * @author massimo malvestio
 *
 */
public class ExecutionResultBean<T> {

	private boolean successful;

	private String message;

	protected Map<String, Object> additionalInformations;

	public ExecutionResultBean() {
		additionalInformations = new LinkedHashMap<String, Object>();
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void addAdditionalInformation(String key, Object value) {
		additionalInformations.put(key, value);
	}

	public Object getAdditionalInformation(String key) {
		return additionalInformations.get(key);
	}

	public Map<String, Object> getAdditionalInformations() {
		return additionalInformations;
	}

	public void setAdditionalInformations(Map<String, Object> additionalInformations) {
		this.additionalInformations = additionalInformations;
	}

	public void copyProperties(ExecutionResultBean source) {

		setSuccessful(source.isSuccessful());
		setMessage(source.getMessage());
		additionalInformations.putAll(source.getAdditionalInformations());

	}

	/**
	 * Metodo di utilità per salvare i risultati dell'elaborazione, quando si ha
	 * un unico risultato da ritornare
	 */
	public void setResult(Object result) {
		additionalInformations.put("result", result);
	}

	/**
	 * Metodo di utilità per recuperare i risultati dell'elaborazione, quando si
	 * ha un unico risultato da ritornare
	 */
	public T getResult() {
		return (T) additionalInformations.get("result");
	}
}
