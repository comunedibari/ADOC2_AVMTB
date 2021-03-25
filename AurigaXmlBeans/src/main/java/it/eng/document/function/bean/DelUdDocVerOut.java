package it.eng.document.function.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DelUdDocVerOut implements Serializable{

	private String uriOut;
	private BigDecimal nroProgrVerOut;	
	private Integer errorCode;
	private String errorContext;
	private String defaultMessage;
	private boolean inError;
	public String getUriOut() {
		return uriOut;
	}

	public Integer getErrorCode() {
		return errorCode;
	}
	public String getErrorContext() {
		return errorContext;
	}
	public String getDefaultMessage() {
		return defaultMessage;
	}
	public boolean isInError() {
		return inError;
	}
	public void setUriOut(String uriOut) {
		this.uriOut = uriOut;
	}
	
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public void setErrorContext(String errorContext) {
		this.errorContext = errorContext;
	}
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	public void setInError(boolean inError) {
		this.inError = inError;
	}

	public BigDecimal getNroProgrVerOut() {
		return nroProgrVerOut;
	}

	public void setNroProgrVerOut(BigDecimal nroProgrVerOut) {
		this.nroProgrVerOut = nroProgrVerOut;
	}
	
	

}
