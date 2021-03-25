package it.eng.utility.email.process;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che processa i nodi del flusso
 * @author michele
 *
 */
public class ProcessNode {

	/**
	 * Espressione da controllare
	 */
	private List<ProcessNodeExpression> nodeElaborate;

	public List<ProcessNodeExpression> getNodeElaborate() {
		return nodeElaborate;
	}

	public void setNodeElaborate(List<ProcessNodeExpression> nodeElaborate) {
		this.nodeElaborate = nodeElaborate;
	}
	
	public void addProcessNodeElaborate(ProcessNodeExpression nodeelaborate){
		if(this.nodeElaborate==null){
			this.nodeElaborate = new ArrayList<ProcessNodeExpression>();
		}
		this.nodeElaborate.add(nodeelaborate);
	}
	
}