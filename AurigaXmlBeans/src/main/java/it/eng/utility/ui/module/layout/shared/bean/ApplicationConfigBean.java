package it.eng.utility.ui.module.layout.shared.bean;


public class ApplicationConfigBean {

	private String idApplicazione;
	private String descrizioneApplicazione;
	private String codApplicazioneInLoginDB;

	public void setIdApplicazione(String idApplicazione) {
		this.idApplicazione = idApplicazione;
	}

	public String getIdApplicazione() {
		return idApplicazione;
	}

	public String getDescrizioneApplicazione() {
		return descrizioneApplicazione;
	}

	public void setDescrizioneApplicazione(String descrizioneApplicazione) {
		this.descrizioneApplicazione = descrizioneApplicazione;
	}

	public String getCodApplicazioneInLoginDB() {
		return codApplicazioneInLoginDB;
	}

	public void setCodApplicazioneInLoginDB(String codApplicazioneInLoginDB) {
		this.codApplicazioneInLoginDB = codApplicazioneInLoginDB;
	}
	
}
