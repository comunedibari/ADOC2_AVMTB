package it.eng.aurigamailbusiness.bean.restrepresentation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.MailLoginBean;
import it.eng.aurigamailbusiness.bean.SenderBean;

@XmlRootElement(name="mailLoginSenderBean")
public class MailLoginSenderBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SenderBean senderBean;
	private MailLoginBean mailLoginBean;
	
	public SenderBean getSenderBean() {
		return senderBean;
	}
	public void setSenderBean(SenderBean senderBean) {
		this.senderBean = senderBean;
	}
	public MailLoginBean getMailLoginBean() {
		return mailLoginBean;
	}
	public void setMailLoginBean(MailLoginBean mailLoginBean) {
		this.mailLoginBean = mailLoginBean;
	}
	
}
