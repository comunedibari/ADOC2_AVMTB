package it.eng.auriga.ui.module.layout.server.tributi.datasource.bean;

import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.avvioProcedimento.bean.AvvioProcedimentoInBean;
import it.eng.auriga.ui.module.layout.server.istanzeportaleriscossione.datasource.bean.IstanzePortaleRiscossioneBean;

import java.util.List;

public class AvvioProcTributiInBean extends AvvioProcedimentoInBean {
	
	private List<IstanzePortaleRiscossioneBean> listaIstanze;

	public List<IstanzePortaleRiscossioneBean> getListaIstanze() {
		return listaIstanze;
	}

	public void setListaIstanze(List<IstanzePortaleRiscossioneBean> listaIstanze) {
		this.listaIstanze = listaIstanze;
	}
	
}
