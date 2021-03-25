package it.eng.workflow.service;

public enum TaskDaCompletare {
	COMPILAISTANZA("compila_istanza"), 
	ALLEGADOCUMENTAZIONE("allega_documentazione"),
	PRESENTAISTANZA("presenta_istanza");
	
	private String nomeTask;
	
	private TaskDaCompletare(String nomeTask){
		this.nomeTask = nomeTask;
	}

	public String getNomeTask() {
		return nomeTask;
	}

	public void setNomeTask(String nomeTask) {
		this.nomeTask = nomeTask;
	}
	
	
}
