package it.eng.auriga.ui.module.layout.server.registroDocumenti.bean;

public class VisualizzaDocumentoBean {

	private String uriFile;
	private Boolean remoteUri;
	private String estremi;
	private String html;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getEstremi() {
		return estremi;
	}

	public void setEstremi(String estremi) {
		this.estremi = estremi;
	}

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public Boolean getRemoteUri() {
		return remoteUri;
	}

	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}
			
	
}
