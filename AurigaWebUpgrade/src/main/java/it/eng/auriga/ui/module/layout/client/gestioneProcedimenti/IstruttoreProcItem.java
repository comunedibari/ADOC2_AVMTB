package it.eng.auriga.ui.module.layout.client.gestioneProcedimenti;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

import java.util.HashMap;

public class IstruttoreProcItem extends ReplicableItem {
	
	private String idProcedimento;
	
	public IstruttoreProcItem(String idProcedimento) {
		this.idProcedimento = idProcedimento;
	}
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("idProcedimento", idProcedimento);
		IstruttoreProcCanvas lIstruttoreProcCanvas = new IstruttoreProcCanvas(this, params);		
		return lIstruttoreProcCanvas;
	}
	
}
