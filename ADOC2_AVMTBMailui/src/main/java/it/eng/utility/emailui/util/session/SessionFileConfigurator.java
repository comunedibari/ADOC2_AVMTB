package it.eng.utility.emailui.util.session;

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
