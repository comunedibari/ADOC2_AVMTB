package it.eng.utility.emailui.mail.shared.bean;

import it.eng.utility.emailui.core.shared.annotation.JSONBean;

/**
 * Classe che verifica che mailui è in grado di partire in base
 * alle configurazioni sul mailconnectid per ogni casella
 * 
 * @author Rametta
 *
 */
@JSONBean
public class MailCanStartBean {

	private boolean canStart;

	public boolean isCanStart() {
		return canStart;
	}

	public void setCanStart(boolean canStart) {
		this.canStart = canStart;
	}
}
