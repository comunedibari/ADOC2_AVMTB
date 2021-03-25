package it.eng.utility.email.util.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class XmlValidationHandler implements ValidationEventHandler{
	
	private String xsd;

	public void addError(String errore){
		if (errori == null){
			errori = new ArrayList<String>();
		} 
		errori.add(errore);
	}
	
	private List<String> errori;

	public List<String> getErrori() {
		return errori;
	}

	public void setErrori(List<String> errori) {
		this.errori = errori;
	}

	public String getXsd() {
		return xsd;
	}

	public void setXsd(String xsd) {
		this.xsd = xsd;
	}

	@Override
	public boolean handleEvent(ValidationEvent paramValidationEvent) {
		addError(paramValidationEvent.getMessage());
		return true;
	}
}
