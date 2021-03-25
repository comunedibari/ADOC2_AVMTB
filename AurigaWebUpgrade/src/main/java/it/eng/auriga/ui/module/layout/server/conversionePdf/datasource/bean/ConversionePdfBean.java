package it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean;

import java.util.List;

public class ConversionePdfBean {

	private RettangoloFirmaPadesBean rettangoloFirmaPades;
	private List<FileDaConvertireBean> files;
	private String jSessionId;

	public List<FileDaConvertireBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileDaConvertireBean> files) {
		this.files = files;
	}

	public RettangoloFirmaPadesBean getRettangoloFirmaPades() {
		return rettangoloFirmaPades;
	}

	public void setRettangoloFirmaPades(RettangoloFirmaPadesBean rettangoloFirmaPades) {
		this.rettangoloFirmaPades = rettangoloFirmaPades;
	}

	public String getjSessionId() {
		return jSessionId;
	}

	public void setjSessionId(String jSessionId) {
		this.jSessionId = jSessionId;
	}

}