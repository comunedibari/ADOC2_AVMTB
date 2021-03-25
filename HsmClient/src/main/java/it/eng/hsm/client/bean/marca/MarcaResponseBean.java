package it.eng.hsm.client.bean.marca;

import it.eng.hsm.client.bean.MessageBean;


public class MarcaResponseBean {
	
	private FileResponseBean fileResponseBean = new FileResponseBean();
	
	private MessageBean message;
	
	public FileResponseBean getFileResponseBean() {
		return fileResponseBean;
	}

	public void setFileResponseBean(FileResponseBean fileResponseBean) {
		this.fileResponseBean = fileResponseBean;
	}

	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}
	
}
