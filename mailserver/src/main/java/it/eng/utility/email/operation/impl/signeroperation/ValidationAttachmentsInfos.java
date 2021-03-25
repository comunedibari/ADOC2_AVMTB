package it.eng.utility.email.operation.impl.signeroperation;



import it.eng.module.foutility.beans.generated.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean contenenti informazioni sull'esito di una verifica, in termini di 
 * errori e avvisi che sono stati generati
 * @author Administrator
 *
 */
public class ValidationAttachmentsInfos {

	String[] warnings = null;
	List<MessageType> warningsBean = new ArrayList<MessageType>();
	
	String[] errors = null;
	List<MessageType> errorsBean = new ArrayList<MessageType>();
	
	private static final String WARNINGS_CODE = "warnings";
	private static final String ERRORS_CODE = "errors";
	private static final String ERRORSBEAN_CODE = "errorsBean";
	private static final String WARNINGSBEAN_CODE = "warningsBean";
	private static final String ISVALID_CODE = "isValid";
	private boolean cannotEecute;//proprieta' che indica che il controllo non può essere eseguito per qualche errore
	
	/**
	 * Abbreviazione per errors.isEmpty()
	 * @return boolean
	 */
	public boolean isValid() {
		return ((errors==null || errors.length==0) && errorsBean.size()==0);
	}
	
	/**
	 * Verifica la presenza di errori e avvisi
	 * @param strict verifica anche la presenza di avvisi
	 * @return true se non sono presenti erorri ed eventualmente avvisi
	 */
	public boolean isValid(boolean strict) {
		return strict ? isValid() && (warnings==null || warnings.length==0) : isValid();
	}
	
	/**
	 * Aggiunge un nuovo avviso a quelli presenti
	 * @param message
	 */
	public void addWarning(String message) {
		int length = warnings==null? 0 : warnings.length;
		String[] temp = new String[length+1];
		if (warnings!=null)
			System.arraycopy(warnings,0,temp,0,length);
		temp[length] = message;
		warnings = temp;
	}
	public void addWarningWithCode(String code, String message) {
		MessageType messageType = new MessageType();
		messageType.setCode( code );
		messageType.setDescription( message );
		warningsBean.add( messageType);
	}
	
	/**
	 * Aggiunge una lista di avvisi a quelli presenti
	 * @param newWarnings
	 */
	public void addWarnings(String[] newWarnings) {
		if (newWarnings==null || newWarnings.length == 0)
			return;
		int length = warnings==null? 0 : warnings.length;
		String[] temp = new String[length+newWarnings.length];
		if (warnings!=null)
			System.arraycopy(warnings,0,temp,0,length);	
		for (int i=0;i<newWarnings.length; i++)
			temp[length + i] = newWarnings[i];
		warnings = temp;
	}
	
	/**
	 * Aggiunge un errore a quelli presenti
	 * @param error
	 */
	public void addError(String error) {
		int length = errors==null? 0 : errors.length;
		String[] temp = new String[length+1];
		if (errors!=null)
			System.arraycopy(errors, 0, temp, 0, length);
		temp[length] = error;
		errors = temp;
	}
	public void addError(String error,boolean cannotExecute) {
		addError(error);
		//memorizzo che il controllo non può essere eseguito
		if(cannotExecute){
			this.cannotEecute=true;
		}
	}
	public void addErrorWithCode(String errorCode, String error) {
		MessageType messageType = new MessageType();
		messageType.setCode( errorCode );
		messageType.setDescription( error );
		errorsBean.add( messageType);
	}
	public void addErrorWithCode(String errorCode, String error, boolean cannotExecute) {
		addErrorWithCode(errorCode, error);
		//memorizzo che il controllo non può essere eseguito
		if(cannotExecute){
			this.cannotEecute=true;
		}
	}
	/**
	 * Aggiunge una lista di errori a quelli presenti
	 * @param newErrors
	 */
	public void addErrors(String[] newErrors) {
		if (newErrors==null || newErrors.length == 0)
			return;
		int length = errors==null? 0 : errors.length;
		String[] temp = new String[length+newErrors.length];
		if (errors!=null)
			System.arraycopy(errors,0,temp,0,length);
		for (int i=0;i<newErrors.length; i++)
			temp[length + i] = newErrors[i];
		errors = temp;
	}
	
	/**
	 * Recupera gli avvisi presenti
	 * @return string
	 */
	public String[] getWarnings() {
		return warnings;
	}

	/**
	 * Recupera gli errori presenti
	 * @return string 
	 */
	public String[] getErrors() {
		return errors;
	}
	
	/**
	 * Recupera gli errori presenti
	 * @return errorsBean 
	 */
	public List<MessageType> getErrorsBean() {
		return errorsBean;
	}
	
	public List<MessageType> getWarningsBean() {
		return warningsBean;
	}
	
	public String getErrorsString() {
		StringBuffer errorsBuf = new StringBuffer();
		if (errors!=null) {
			for (int i=0; i<errors.length; i++) {
				errorsBuf.append(errors[i]);
				if (i != errors.length - 1)
					errorsBuf.append(", ");
			}
		}
		return errorsBuf.toString();
	}
	
	public String getErrorsBeanString() {
		StringBuffer errorsBuf = new StringBuffer();
		if (errors!=null) {
			for (String error : errors) {
				errorsBuf.append(error+"\n");
			}
			for (int i=0; i<errorsBean.size(); i++) {
				errorsBuf.append(errorsBean.get(i).getDescription());
				if (i != errorsBean.size() - 1)
					errorsBuf.append(", ");
			}
		}
		return errorsBuf.toString();
	}
	
	public String getWarningsString() {
		StringBuffer warningsBuf = new StringBuffer();
		if (warnings!=null) {
			for (int i=0; i<warnings.length; i++) {
				warningsBuf.append(warnings[i]);
				if (i != warnings.length - 1)
					warningsBuf.append(", ");
			}
		}
		return warningsBuf.toString();
	}
	
	public String toString() {
		StringBuffer warningsBuf = new StringBuffer("[");
		if (warnings!=null)
			for (int i=0;i <warnings.length; i++)
				if (i != warnings.length - 1)
					warningsBuf.append(warnings[i] + ", ");
				else
					warningsBuf.append(warnings[i] + "]");
		else 
			warningsBuf.append("]");
		StringBuffer errorsBuf = new StringBuffer("[");
		if (errors!=null)
			for (int i=0;i <errors.length; i++) {
				if (i != errors.length - 1)
					errorsBuf.append(errors[i] + ", ");
				else
					errorsBuf.append(errors[i] + "]");
			}
		else 
			errorsBuf.append("]");
		
		StringBuffer errorsBeanBuf = new StringBuffer("[");
		for (int i=0;i <errorsBean.size(); i++) {
			errorsBeanBuf.append( errorsBean.get( i ) );
		}
		errorsBeanBuf.append("]");
		
		StringBuffer warningsBeanBuf = new StringBuffer("[");
		for (int i=0;i <warningsBean.size(); i++) {
			warningsBeanBuf.append( warningsBean.get( i ) );
		}
		warningsBeanBuf.append("]");
		
		return WARNINGS_CODE + ": " + warningsBuf + ", " + ERRORS_CODE + ": " + errorsBuf +", " + ERRORSBEAN_CODE + ": " + errorsBeanBuf +", " + WARNINGSBEAN_CODE + ": " + warningsBeanBuf +", " + ISVALID_CODE + ": " + isValid();
	}

	
	
	public void setWarnings(String[] warnings) {
		this.warnings = warnings;
	}

	public void setErrors(String[] errors) {
		this.errors = errors;
	}

	public boolean isCannotEecute() {
		return cannotEecute;
	}

	public void setErrorsBean(List<MessageType> errorsBean) {
		this.errorsBean = errorsBean;
	}
	

}