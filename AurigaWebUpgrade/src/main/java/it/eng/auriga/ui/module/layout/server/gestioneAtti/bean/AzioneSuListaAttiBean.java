package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import java.io.Serializable;

public class AzioneSuListaAttiBean extends OperazioneMassivaAttiBean implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		private String azione;
		private String motivoOsservazioni;
		private String eventoAMC;
		
		public String getAzione() {
			return azione;
		}

		public void setAzione(String azione) {
			this.azione = azione;
		}

		public String getMotivoOsservazioni() {
			return motivoOsservazioni;
		}

		public void setMotivoOsservazioni(String motivoOsservazioni) {
			this.motivoOsservazioni = motivoOsservazioni;
		}

		public String getEventoAMC() {
			return eventoAMC;
		}

		public void setEventoAMC(String eventoAMC) {
			this.eventoAMC = eventoAMC;
		}
		
}
