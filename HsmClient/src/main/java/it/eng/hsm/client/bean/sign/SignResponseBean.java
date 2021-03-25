package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;


public class SignResponseBean {
	
	private FileResponseBean fileResponseBean = new FileResponseBean();
	
	private MessageBean message;
	private List<HashResponseBean> hashResponseBean = new ArrayList<HashResponseBean>();

	public FileResponseBean getFileResponseBean() {
		return fileResponseBean;
	}

	public void setFileResponseBean(FileResponseBean fileResponseBean) {
		this.fileResponseBean = fileResponseBean;
	}

	public List<HashResponseBean> getHashResponseBean() {
		return hashResponseBean;
	}

	public void setHashResponseBean(List<HashResponseBean> hashResponseBean) {
		this.hashResponseBean = hashResponseBean;
	}

	public MessageBean getMessage() {
		return message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}
	
}
