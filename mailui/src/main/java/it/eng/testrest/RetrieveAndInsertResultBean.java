package it.eng.testrest;

import java.util.List;

public class RetrieveAndInsertResultBean {

	private List<String> mailInserite;
	private List<String> mailNonInserite;
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<String> getMailNonInserite() {
		return mailNonInserite;
	}

	public void setMailNonInserite(List<String> mailNonInserite) {
		this.mailNonInserite = mailNonInserite;
	}

	public List<String> getMailInserite() {
		return mailInserite;
	}

	public void setMailInserite(List<String> mailInserite) {
		this.mailInserite = mailInserite;
	}
}
