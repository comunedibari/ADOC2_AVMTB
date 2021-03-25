package it.eng.auriga.ui.module.layout.server.caricamentorubriche.datasource.bean;

import java.util.List;

public class CaricamentoRubricaMappingsBean {

	private List<CaricamentoRubricaMappingBean> mappings;

	private String errorMessage;
	
	private boolean successful;
	
	public List<CaricamentoRubricaMappingBean> getMappings() {
		return mappings;
	}

	public void setMappings(List<CaricamentoRubricaMappingBean> mappings) {
		this.mappings = mappings;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
}
