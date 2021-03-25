package it.eng.utility.email.process;

/**
 * Identifica l'espressione da utilizzare per lo swith all'operazione successiva
 * @author michele
 *
 */
public class ProcessNodeExpression {

	/**
	 * Espressione di uscita
	 */
	private String expression;
	
	/**
	 * Nodo di processo
	 */
	private ProcessOperation operation;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public ProcessOperation getOperation() {
		return operation;
	}

	public void setOperation(ProcessOperation operation) {
		this.operation = operation;
	}


}