package it.eng.aurigamailbusiness.config;

public class DebugInvioBean {

	private boolean invia;
	private boolean abilitaInvioMittente = false;

	public boolean isAbilitaInvioMittente() {
		return abilitaInvioMittente;
	}

	public void setAbilitaInvioMittente(boolean abilitaInvioMittente) {
		this.abilitaInvioMittente = abilitaInvioMittente;
	}

	public boolean isInvia() {
		return invia;
	}

	public void setInvia(boolean invia) {
		this.invia = invia;
	}
}
