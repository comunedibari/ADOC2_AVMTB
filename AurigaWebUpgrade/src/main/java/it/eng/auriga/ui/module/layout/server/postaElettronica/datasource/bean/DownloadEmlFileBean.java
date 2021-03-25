package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

/**
 * 
 * @author DANCRIST
 *
 */

public class DownloadEmlFileBean {
	
	private String idEmail;
	private String uriFile;

	public String getUriFile() {
		return uriFile;
	}

	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

}