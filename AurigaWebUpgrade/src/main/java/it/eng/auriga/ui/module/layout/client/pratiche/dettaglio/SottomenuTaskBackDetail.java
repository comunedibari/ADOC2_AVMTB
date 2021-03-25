package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio;

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout.GetListaTaskProcCallback;

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public abstract class SottomenuTaskBackDetail extends SottomenuTaskDetail implements BackDetailInterface {
	
	public SottomenuTaskBackDetail(String nomeEntita, String idProcess, String idTipoProc, String idTipoEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		super(nomeEntita, idProcess, idTipoProc, idTipoEvento, dettaglioPraticaLayout);
	}
	
	@Override
	public void buildSottomenu(final VLayout lVLayout) {
		dettaglioPraticaLayout.getListaTaskProc(idProcess, idTipoProc, null, idTipoEvento, new GetListaTaskProcCallback() {			
			@Override
			public void execute(RecordList data) {
				
				buildListaTaskPratica(data, lVLayout);
			}
		});					
	}
	
}
