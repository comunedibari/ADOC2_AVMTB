package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class LockEmailBean extends OperazioneMassivaPostaElettronicaBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> errorMessage;
	private String motivi;
	private String iduserlockfor;
	private boolean storeInError;

	public ArrayList<String> getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(ArrayList<String> errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getMotivi() {
		return motivi;
	}
	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}
	public String getIduserlockfor() {
		return iduserlockfor;
	}
	public void setIduserlockfor(String iduserlockfor) {
		this.iduserlockfor = iduserlockfor;
	}
	public boolean isStoreInError() {
		return storeInError;
	}
	public void setStoreInError(boolean storeInError) {
		this.storeInError = storeInError;
	}		
}
