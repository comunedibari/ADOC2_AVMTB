package it.eng.aurigamailbusiness.database.utility.bean.search;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchOut implements Serializable{

	private static final long serialVersionUID = 3838293268565241122L;
	
	private List<EmailResultBean> emails;

	public void setEmails(List<EmailResultBean> emails) {
		this.emails = emails;
	}

	public List<EmailResultBean> getEmails() {
		return emails;
	}

}
