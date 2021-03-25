package it.eng.utility.email.operation.impl.notificationoperation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean che rappresenta il risultato dell'elaborazione della AssignmentNotificationOperation
 * @author Roberto
 *
 */
@XmlRootElement
public class AssignmentNotificationBean 
{
	private Boolean isok;
	private String resultMessage;
	
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public Boolean getIsok() {
		return isok;
	}
	public void setIsok(Boolean isok) {
		this.isok = isok;
	}
}
