package it.eng.testrest;

public class GenericOperationBean {

	private RestResult result;
	private Exception exception;
	private String message;
	public RestResult getResult() {
		return result;
	}
	public void setResult(RestResult result) {
		this.result = result;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception pException) {
		this.exception = pException;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	} 
}
