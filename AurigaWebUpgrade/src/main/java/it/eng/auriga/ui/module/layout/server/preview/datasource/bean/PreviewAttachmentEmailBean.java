package it.eng.auriga.ui.module.layout.server.preview.datasource.bean;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class PreviewAttachmentEmailBean {

	private String nomeFile;
	private String displayFileName;
	private String uri;
	private MimeTypeFirmaBean infoFile;
	private Boolean flgFirmato;

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Boolean getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(Boolean flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	/**
	 * @return the displayFileName
	 */
	public String getDisplayFileName() {
		return displayFileName;
	}

	/**
	 * @param displayFileName the displayFileName to set
	 */
	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}
}
