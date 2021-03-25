package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

/**
 * 
 * @author cristiano
 *
 */

public class PostaElettronicaRetriewAttachBean {

	private MimeTypeFirmaBean mimeTypeFirmaBean;
	private String uriFile;

	/**
	 * @return the mimeTypeFirmaBean
	 */
	public MimeTypeFirmaBean getMimeTypeFirmaBean() {
		return mimeTypeFirmaBean;
	}

	/**
	 * @param mimeTypeFirmaBean
	 *            the mimeTypeFirmaBean to set
	 */
	public void setMimeTypeFirmaBean(MimeTypeFirmaBean mimeTypeFirmaBean) {
		this.mimeTypeFirmaBean = mimeTypeFirmaBean;
	}

	/**
	 * @return the uriFile
	 */
	public String getUriFile() {
		return uriFile;
	}

	/**
	 * @param uriFile
	 *            the uriFile to set
	 */
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

}
