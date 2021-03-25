package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;

public class FileResponseBean {

	private MessageBean messageBean;
	private byte[] fileFirmato;
	
	public MessageBean getMessageBean() {
		return messageBean;
	}
	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}
	public byte[] getFileFirmato() {
		return fileFirmato;
	}
	public void setFileFirmato(byte[] fileFirmato) {
		this.fileFirmato = fileFirmato;
	}
	
}
