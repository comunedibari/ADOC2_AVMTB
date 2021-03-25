package it.eng.auriga.ui.module.layout.server.common;

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class OperazioneMassivaBean<T> extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
		private List<T> listaRecord;
		private HashMap<String, String> errorMessages;
		
		public List<T> getListaRecord() {
			return listaRecord;
		}
		public void setListaRecord(List<T> listaRecord) {
			this.listaRecord = listaRecord;
		}		
		public HashMap<String, String> getErrorMessages() {
			return errorMessages;
		}
		public void setErrorMessages(HashMap<String, String> errorMessages) {
			this.errorMessages = errorMessages;
		}
		
}
