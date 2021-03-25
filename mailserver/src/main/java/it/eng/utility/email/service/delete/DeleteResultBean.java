package it.eng.utility.email.service.delete;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteResultBean {

	private String error;
	private Boolean result;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}	
}