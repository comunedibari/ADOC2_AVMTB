package it.eng.utility.email.process;

import java.util.Properties;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Operation da eseguire
 * @author michele
 *
 */
public class ProcessOperation {

	/**
	 * Nome dell'operazione
	 */
	@XmlTransient
	private String operationame;
	
	/**
	 * Operation id
	 */
	private String operationid;
		
	/**
	 * Configurazioni delle operazioni
	 */
	@XmlTransient
	private Properties configurations;
	
	/**
	 * Nodo di collegamento in uscita
	 * Ne posso mettere solo uno
	 */
	private ProcessNode node;
	
	
	
	public String getOperationid() {
		return operationid;
	}

	public void setOperationid(String operationid) {
		this.operationid = operationid;
	}

	public ProcessNode getNode() {
		return node;
	}

	public void setNode(ProcessNode node) {
		this.node = node;
	}

	public String getOperationame() {
		return operationame;
	}

	public void setOperationame(String operationame) {
		this.operationame = operationame;
	}

	public Properties getConfigurations() {
		return configurations;
	}

	public void setConfigurations(Properties configurations) {
		this.configurations = configurations;
	}
}