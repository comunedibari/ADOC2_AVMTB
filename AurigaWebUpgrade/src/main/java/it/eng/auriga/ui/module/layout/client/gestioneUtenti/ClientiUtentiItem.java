package it.eng.auriga.ui.module.layout.client.gestioneUtenti;

import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupClientiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import java.util.ArrayList;
import java.util.List;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class ClientiUtentiItem extends ReplicableItem {
	
	private String nomeEntita;
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		ClientiUtentiCanvas lClientiUtentiCanvas = new ClientiUtentiCanvas();		
		return lClientiUtentiCanvas;
	}
	
	public String getNomeEntita() {
		return null;
	}

	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}

	@Override
	public ImgButton[] createAddButtons() {
		
		ImgButton[] addButtons = new ImgButton[1];
		
		addButtons[0] = new ImgButton();   
		addButtons[0].setSrc("lookup/rubricamulti.png");   
		addButtons[0].setShowDown(false);   
		addButtons[0].setShowRollOver(false);      
		addButtons[0].setSize(16); 
		addButtons[0].setPrompt(I18NUtil.getMessages().gruppisoggetti_detail_multilookupRubricaButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				String nomeEntita = getNomeEntita();
				ClientiUtentiMultiLookupRubrica lookupRubricaPopup = new ClientiUtentiMultiLookupRubrica(nomeEntita, null);				
				lookupRubricaPopup.show(); 	
			}   
		});				
		return addButtons;		
	}
	
	// Lookup per chiamare la RUBRICA
	public class ClientiUtentiMultiLookupRubrica extends LookupClientiPopup {

		private List<ClientiUtentiCanvas> multiLookupListRubrica = new ArrayList<ClientiUtentiCanvas>(); 
		
		public ClientiUtentiMultiLookupRubrica(String nomeEntita, Record record) {
			super(nomeEntita, record, null, null, false);			
		}

		@Override
		public void manageLookupBack(Record record) {						
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			ClientiUtentiCanvas lastCanvas = (ClientiUtentiCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupListRubrica.add(lastCanvas);
			} else {
				ClientiUtentiCanvas canvas = (ClientiUtentiCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupListRubrica.add(canvas);
			}
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(ClientiUtentiCanvas canvas : multiLookupListRubrica) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idSoggetto").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
	}
}
