package it.eng.auriga.repository2.jaxws.webservices.operazioniaurigaFeedbackElabDocs;

public enum OperazioniAurigaLottiDocFaultCode {

	INVALID_REQUEST(1, "Request formalmente non corretta (non valida rispetto xsd) o non valorizzata"), INVALID_CREDENTIAL(4,
			"Credenziali non valide"), GENERIC_ERROR(999, "Errore generico non previsto");

	private final Integer code;
	private final String msg;

	OperazioniAurigaLottiDocFaultCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}
}