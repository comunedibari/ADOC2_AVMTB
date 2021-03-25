package it.eng.client;

import java.util.Locale;

/**
 * @author WorkflowService generator 0.0.1-SNAPSHOT
 */
public class WorkflowService {
		   	
	/**
	 * Servizio AvvioProcedimentoService
	 */ 	
	public static AvvioProcedimentoService getAvvioProcedimentoService(){
		return new AvvioProcedimentoService();
	}
	 
	
	/**
	 * Servizio CompletaTaskService
	 */ 	
	public static CompletaTaskService getCompletaTaskService(){
		return new CompletaTaskService();
	}
	 
	
}    