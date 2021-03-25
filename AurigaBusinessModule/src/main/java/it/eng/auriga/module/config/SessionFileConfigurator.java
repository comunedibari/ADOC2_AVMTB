package it.eng.auriga.module.config;

import java.util.List;

public class SessionFileConfigurator {

	private List<SessionFile> sessions;

	public void setSessions(List<SessionFile> sessions) {
		this.sessions = sessions;
	}

	public List<SessionFile> getSessions() {
		return sessions;
	}
}
