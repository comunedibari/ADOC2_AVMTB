package it.eng.dm.engine.manage;

import it.eng.auriga.module.config.SessionFile;

import java.util.List;

public class ActivitiSessionFileConfigurator {

	private List<SessionFile> sessions;

	public void setSessions(List<SessionFile> sessions) {
		this.sessions = sessions;
	}

	public List<SessionFile> getSessions() {
		return sessions;
	}
}
