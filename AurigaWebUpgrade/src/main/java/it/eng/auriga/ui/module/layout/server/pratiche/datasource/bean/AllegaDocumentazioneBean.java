package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

import java.util.List;

public class AllegaDocumentazioneBean extends EventoCustomBean{
	
	private String idTipoDocumento;
	private List<AllegatoProcBean> listaAllegatiProcToSave;
	private List<AllegatoProcBean> listaAllegatiProcSaved;
	
	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public List<AllegatoProcBean> getListaAllegatiProcToSave() {
		return listaAllegatiProcToSave;
	}

	public void setListaAllegatiProcToSave(List<AllegatoProcBean> listaAllegatiProcToSave) {
		this.listaAllegatiProcToSave = listaAllegatiProcToSave;
	}

	public List<AllegatoProcBean> getListaAllegatiProcSaved() {
		return listaAllegatiProcSaved;
	}

	public void setListaAllegatiProcSaved(List<AllegatoProcBean> listaAllegatiProcSaved) {
		this.listaAllegatiProcSaved = listaAllegatiProcSaved;
	}	
	
}
