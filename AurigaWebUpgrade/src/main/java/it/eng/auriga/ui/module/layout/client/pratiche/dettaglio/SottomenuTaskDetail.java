package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio;

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout.GetListaTaskProcCallback;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class SottomenuTaskDetail extends CustomDetail {
	
	protected String idProcess;
	protected String idTipoProc;
	
	protected String idTipoEvento;
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;
	
	public SottomenuTaskDetail(String nomeEntita, String idProcess, String idTipoProc, String idTipoEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita);
	
		this.idProcess = idProcess;
		this.idTipoProc = idTipoProc;
		this.idTipoEvento = idTipoEvento;
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
				
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();
		
		VLayout lVLayoutTask = new VLayout();
		lVLayoutTask.setMembersMargin(10);
		
		buildSottomenu(lVLayoutTask);
		
		lVLayout.addMember(lVLayoutTask);
				
		VLayout spacer = new VLayout();
		spacer.setHeight100();
		spacer.setWidth100();						
		lVLayout.addMember(spacer);		
		
		addMember(lVLayout);			
	}
	
	protected void buildListaTaskPratica(RecordList listaTask, VLayout lVLayout) {
		
		for(int i = 0; i<listaTask.getLength();i++)
		{
			final Record recordTask = listaTask.get(i);
				
			final String nome = recordTask.getAttribute("nome");
			String displayName = dettaglioPraticaLayout.getDisplayNameEvento(nome);
			
			dettaglioPraticaLayout.getMappaMenu().put(nome, recordTask);			
			
			final String dettagli = recordTask.getAttribute("dettagli");
			final String flgFatta = recordTask.getAttribute("flgFatta");
			final String flgEsito = recordTask.getAttribute("flgEsito");
			final String flgDatiVisibili = recordTask.getAttribute("flgDatiVisibili");			
			final String flgToDo = recordTask.getAttribute("flgToDo");
			final String icona = recordTask.getAttribute("icona");
			
			boolean isEseguibile = true;
			if(recordTask != null && recordTask.getAttribute("flgEseguibile") != null) {
				isEseguibile = "1".equals(recordTask.getAttribute("flgEseguibile"));	
			}	
      
			Label taskLabel = new TaskLabel(displayName, dettagli, (isEseguibile ? null : recordTask.getAttribute("motiviNonEseg")), icona, flgFatta, flgEsito, flgDatiVisibili, flgToDo, dettaglioPraticaLayout);
				
			lVLayout.addMember(taskLabel);
							
			if(flgDatiVisibili != null && "1".equals(flgDatiVisibili)) {
				taskLabel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						dettaglioPraticaLayout.onClickVoceMenu(recordTask);
					}
				});			
			}
		}
	}
	
	public void buildSottomenu(final VLayout lVLayout) {
		dettaglioPraticaLayout.getListaTaskProc(idProcess, idTipoProc, idTipoEvento, null, new GetListaTaskProcCallback() {			
			@Override
			public void execute(RecordList data) {
				
				buildListaTaskPratica(data, lVLayout);
			}
		});							
	}

}
