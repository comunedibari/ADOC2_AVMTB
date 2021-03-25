package it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean;

public class AttachmentUDBean {

	private String fileNameAttach;
	private String uriAttach;
	private Integer nroAttach;
	private Boolean remoteUri;
	private Boolean firmato;
	private Boolean flgFirmaValida;
	private String mimetype;
	
	public Boolean getFlgFirmaValida() {
		return flgFirmaValida;
	}

	public void setFlgFirmaValida(Boolean flgFirmaValida) {
		this.flgFirmaValida = flgFirmaValida;
	}
	public String getFileNameAttach() {
		return fileNameAttach;
	}

	public void setFileNameAttach(String fileNameAttach) {
		this.fileNameAttach = fileNameAttach;
	}

	public String getUriAttach() {
		return uriAttach;
	}

	public void setUriAttach(String uriAttach) {
		this.uriAttach = uriAttach;
	}

	public void setNroAttach(Integer nroAttach) {
		this.nroAttach = nroAttach;
	}

	public Integer getNroAttach() {
		return nroAttach;
	}

	public Boolean getRemoteUri() {
		return remoteUri;
	}

	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}

	public Boolean getFirmato() {
		return firmato;
	}

	public void setFirmato(Boolean firmato) {
		this.firmato = firmato;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
}
