package it.eng.utility.email.process;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Definisce il flusso del processo
 * @author michele
 *
 */
@XmlRootElement
public class ProcessOperationFlow {
	
	/**
	 * Operazione di start
	 */
	private ProcessOperation start;

	public ProcessOperation getStart() {
		return start;
	}

	public void setStart(ProcessOperation start) {
		this.start = start;
	}
}