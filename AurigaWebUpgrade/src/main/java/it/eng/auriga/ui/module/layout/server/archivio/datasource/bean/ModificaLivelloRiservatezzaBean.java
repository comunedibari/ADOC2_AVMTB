package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import java.io.Serializable;

public class ModificaLivelloRiservatezzaBean extends OperazioneMassivaArchivioBean implements Serializable {
			
		private static final long serialVersionUID = 1L;
	
		private String livelloRiservatezza;
				
		public String getLivelloRiservatezza() {
			return livelloRiservatezza;
		}
		public void setLivelloRiservatezza(String livelloRiservatezza) {
			this.livelloRiservatezza = livelloRiservatezza;
		}
		
}
