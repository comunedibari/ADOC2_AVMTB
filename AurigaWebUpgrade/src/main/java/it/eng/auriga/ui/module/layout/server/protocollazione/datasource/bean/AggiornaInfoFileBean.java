package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import java.util.HashMap;
import java.util.List;

public class AggiornaInfoFileBean {

	private List<CalcolaFirmaBean> fileVerPreFirma;
	private HashMap<String, CalcolaFirmaBean> fileFirmati;

	public List<CalcolaFirmaBean> getFileVerPreFirma() {
		return fileVerPreFirma;
	}

	public void setFileVerPreFirma(List<CalcolaFirmaBean> fileVerPreFirma) {
		this.fileVerPreFirma = fileVerPreFirma;
	}

	public HashMap<String, CalcolaFirmaBean> getFileFirmati() {
		return fileFirmati;
	}

	public void setFileFirmati(HashMap<String, CalcolaFirmaBean> fileFirmati) {
		this.fileFirmati = fileFirmati;
	}

}
