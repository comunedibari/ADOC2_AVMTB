package it.eng.workflow.service.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompletaTaskServiceInBean implements Serializable {

	private static final long serialVersionUID = 4783376823188276195L;
	private String idTask;

	public String getIdTask() {
		return idTask;
	}

	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
}
