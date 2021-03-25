package it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean;

import java.io.Serializable;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.IstruttoreProcBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class AssegnazioneVisureBean extends OperazioneMassivaVisureBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<IstruttoreProcBean> listaAssegnazione;

	public List<IstruttoreProcBean> getListaAssegnazione() {
		return listaAssegnazione;
	}

	public void setListaAssegnazione(List<IstruttoreProcBean> listaAssegnazione) {
		this.listaAssegnazione = listaAssegnazione;
	}
}