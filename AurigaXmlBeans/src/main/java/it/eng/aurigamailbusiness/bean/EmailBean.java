package it.eng.aurigamailbusiness.bean;

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmailBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1232421174234898174L;

	private TEmailMgoBean mail;

	private TRubricaEmailBean voceRubricaMittente;

	private List<TRelEmailMgoBean> relMail;

	private List<TRelEmailFolderBean> folders;

	private List<TDestinatariEmailMgoBean> destinatari;

	private List<TRubricaEmailBean> vociRubricaDestinatari;

	private List<TAttachEmailMgoBean> attachments;
	
	private TRegProtVsEmailBean registrazioneProtocollo;

	private ReceiptBean receipt;

	private InterOpBean interOp;

	public TEmailMgoBean getMail() {
		return mail;
	}

	public void setMail(TEmailMgoBean mail) {
		this.mail = mail;
	}

	public TRubricaEmailBean getVoceRubricaMittente() {
		return voceRubricaMittente;
	}

	public void setVoceRubricaMittente(TRubricaEmailBean voceRubricaMittente) {
		this.voceRubricaMittente = voceRubricaMittente;
	}

	public List<TRelEmailFolderBean> getFolders() {
		return folders;
	}

	public void setFolders(List<TRelEmailFolderBean> folders) {
		this.folders = folders;
	}

	public List<TDestinatariEmailMgoBean> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<TDestinatariEmailMgoBean> destinatari) {
		this.destinatari = destinatari;
	}

	public List<TRubricaEmailBean> getVociRubricaDestinatari() {
		return vociRubricaDestinatari;
	}

	public void setVociRubricaDestinatari(List<TRubricaEmailBean> vociRubricaDestinatari) {
		this.vociRubricaDestinatari = vociRubricaDestinatari;
	}

	public List<TAttachEmailMgoBean> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<TAttachEmailMgoBean> attachments) {
		this.attachments = attachments;
	}

	public ReceiptBean getReceipt() {
		return receipt;
	}

	public void setReceipt(ReceiptBean receipt) {
		this.receipt = receipt;
	}

	public InterOpBean getInterOp() {
		return interOp;
	}

	public void setInterOp(InterOpBean interOp) {
		this.interOp = interOp;
	}

	public List<TRelEmailMgoBean> getRelMail() {
		return relMail;
	}

	public void setRelMail(List<TRelEmailMgoBean> relMail) {
		this.relMail = relMail;
	}

	public TRegProtVsEmailBean getRegistrazioneProtocollo() {
		return registrazioneProtocollo;
	}

	public void setRegistrazioneProtocollo(TRegProtVsEmailBean registrazioneProtocollo) {
		this.registrazioneProtocollo = registrazioneProtocollo;
	}

}
