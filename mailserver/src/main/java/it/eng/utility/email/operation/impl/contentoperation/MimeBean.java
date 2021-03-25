package it.eng.utility.email.operation.impl.contentoperation;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;



/**
 * Bean contenenente il mimetype 
 * @author michele
 *
 */

@XmlRootElement
public class MimeBean {
	
	private List<MimeAttachmentBean> mimes;

	public List<MimeAttachmentBean> getMimes() {
		return mimes;
	}

	public void setMimes(List<MimeAttachmentBean> mimes) {
		this.mimes = mimes;
	}

	
	
}