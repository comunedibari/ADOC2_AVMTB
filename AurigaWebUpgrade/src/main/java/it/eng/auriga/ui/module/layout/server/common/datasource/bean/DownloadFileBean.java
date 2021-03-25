package it.eng.auriga.ui.module.layout.server.common.datasource.bean;

/**
 * Contiene l'uri e il nome del file
 * @author ottavio passalacqua
 *
 */
public class DownloadFileBean {
	
	private String nomeTabella;
	private String nomeColonna;
	private String rowIdRecord;
	
	private String uriFile;
	private String nomeFile;
	private String extFile;
	private String message;
	
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNomeTabella() {
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}
	public String getNomeColonna() {
		return nomeColonna;
	}
	public void setNomeColonna(String nomeColonna) {
		this.nomeColonna = nomeColonna;
	}
	public String getRowIdRecord() {
		return rowIdRecord;
	}
	public void setRowIdRecord(String rowIdRecord) {
		this.rowIdRecord = rowIdRecord;
	}
	public String getExtFile() {
		return extFile;
	}
	public void setExtFile(String extFile) {
		this.extFile = extFile;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

}
