package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import java.io.Serializable;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;

public class SmistamentoAttiBean extends OperazioneMassivaAttiBean implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		private List<UfficioLiquidatoreBean> listaUfficioLiquidatore;
		private List<AssegnazioneBean> listaSmistamento;
		
		public List<UfficioLiquidatoreBean> getListaUfficioLiquidatore() {
			return listaUfficioLiquidatore;
		}
		public void setListaUfficioLiquidatore(List<UfficioLiquidatoreBean> listaUfficioLiquidatore) {
			this.listaUfficioLiquidatore = listaUfficioLiquidatore;
		}
		public List<AssegnazioneBean> getListaSmistamento() {
			return listaSmistamento;
		}
		public void setListaSmistamento(List<AssegnazioneBean> listaSmistamento) {
			this.listaSmistamento = listaSmistamento;
		}
		
}
