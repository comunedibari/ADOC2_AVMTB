package it.eng.utility.email.operation.impl.signeroperation;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Bean contenente tutte le verifiche della firma fatte sui file
 * @author michele
 *
 */
@XmlRootElement
public class SignerMailBean {

	private List<SignerAttachmentsBean> attachments;

	public List<SignerAttachmentsBean> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<SignerAttachmentsBean> attachments) {
		this.attachments = attachments;
	}
	
	/**
	 * Controlla se almeno uno degli attachemts è firmato
	 * @return
	 */
	public boolean isOneSigner(){
		boolean valid = false;
		if(attachments !=null){
			for(SignerAttachmentsBean bean:attachments){
				if(bean.getIssigner()){
					valid = true;
					break;
				}
			}
		}
		return valid;	
	}
	
	/**
	 * Controlla se almeno uno degli attachemts è firmato e la firma è valida
	 * @return
	 */
	public boolean isOneSignerValid(){
		boolean valid = false;
		if(attachments !=null){
			for(SignerAttachmentsBean bean:attachments){
				if(bean.getIssigner()) {
					if(bean.getIssignervalid()){
						valid = true;
						break;	
					}
				}
			}
		}
		return valid;	
	}
	
	
	/**
	 * Controlla se gli attachment sono tutti firmati
	 * @return
	 */
	public boolean isAllSigner(){
		boolean valid = true;
		if(attachments !=null){
		for(SignerAttachmentsBean bean:attachments){
			if(!bean.getIssigner()){
				valid = false;
				break;
			}
		}
		}
		return valid;	
	}
	
	/**
	 * Controlla se gli attachment sono tutti firmati e validi
	 * @return
	 */
	public boolean isAllSignerValid(){
		boolean valid = true;
		if(attachments !=null){
		for(SignerAttachmentsBean bean:attachments){
			if(!bean.getIssigner()) {
				valid = false;
				break;
			}else{
				if(!bean.getIssignervalid()){
					valid = false;
					break;	
				}
			}
		}
		}
		return valid;	
	}
	
	/**
	 * Controlla se tutti gli attachments non sono firmati
	 * @return
	 */
	public boolean isAllNoSigner(){
		boolean valid = true;
		if(attachments !=null){
		for(SignerAttachmentsBean bean:attachments){
			if(bean.getIssigner()){
				valid = false;
				break;
			}
		}
		}
		return valid;	
	}
	
}