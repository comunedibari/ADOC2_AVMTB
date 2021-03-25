package it.eng.utility.email.operation.impl.wserviceoperation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di definizione dei metodi del webservice
 * @author michele
 *
 */
@XmlRootElement
public class WSServiceBean {

	private Boolean isok;
	private String wsresult;

	public Boolean getIsok() {
		return isok;
	}

	public void setIsok(Boolean isok) {
		this.isok = isok;
	}

	public String getWsresult() {
		return wsresult;
	}

	public void setWsresult(String wsresult) {
		this.wsresult = wsresult;
	}	
}