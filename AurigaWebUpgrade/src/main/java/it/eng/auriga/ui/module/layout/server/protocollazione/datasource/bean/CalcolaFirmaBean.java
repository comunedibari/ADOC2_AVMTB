package it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.TipoFirmaHsm;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class CalcolaFirmaBean {

	private String uri;
	private String nomeFile;
	private String idFile;
	private MimeTypeFirmaBean infoFile;
	private TipoFirmaHsm tipoFirmaHsm;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getIdFile() {
		return idFile;
	}

	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	public TipoFirmaHsm getTipoFirmaHsm() {
		return tipoFirmaHsm;
	}

	public void setTipoFirmaHsm(TipoFirmaHsm tipoFirmaHsm) {
		this.tipoFirmaHsm = tipoFirmaHsm;
	}

}
