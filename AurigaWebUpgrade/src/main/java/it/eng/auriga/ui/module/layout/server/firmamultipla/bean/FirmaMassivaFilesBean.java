package it.eng.auriga.ui.module.layout.server.firmamultipla.bean;

import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;

import java.util.List;

public class FirmaMassivaFilesBean {

	private List<FileDaFirmareBean> files;
	private List<IdFileBean> daNonTrasmettere;
	private String commonName;
	private Boolean documentoPrincipaleNonFirmato;

	public List<FileDaFirmareBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileDaFirmareBean> files) {
		this.files = files;
	}

	public List<IdFileBean> getDaNonTrasmettere() {
		return daNonTrasmettere;
	}

	public void setDaNonTrasmettere(List<IdFileBean> daNonTrasmettere) {
		this.daNonTrasmettere = daNonTrasmettere;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public Boolean getDocumentoPrincipaleNonFirmato() {
		return documentoPrincipaleNonFirmato;
	}

	public void setDocumentoPrincipaleNonFirmato(
			Boolean documentoPrincipaleNonFirmato) {
		this.documentoPrincipaleNonFirmato = documentoPrincipaleNonFirmato;
	}
	
}
