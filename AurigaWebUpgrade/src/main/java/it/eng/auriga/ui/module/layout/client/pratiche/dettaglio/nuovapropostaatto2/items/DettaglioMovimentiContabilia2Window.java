package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioMovimentiContabilia2Window extends ModalWindow {
	
	protected DettaglioMovimentiContabilia2Window window;
	protected DettaglioMovimentiContabilia2Detail detail;	
	protected ToolStrip detailToolStrip;
	
	public DettaglioMovimentiContabilia2Window(ListaMovimentiContabilia2Item gridItem, String nomeEntita, Record record) {
		
		super(nomeEntita, false);
		
		if(record != null) {
			setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " " + getTipoEstremiRecord(record));
		}
		
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(500);
		setWidth(900);
		setOverflow(Overflow.AUTO);    			
		
		detail = new DettaglioMovimentiContabilia2Detail(nomeEntita, gridItem);		
		detail.setHeight100();
		detail.setWidth100();
		
		setBody(detail);
		
		detail.editRecord(record);
		
		detail.setCanEdit(false);
		
		setIcon("blank.png");		
	}
	
	public String getTipoEstremiRecord(Record record) {
		String estremi = "";
		if(record.getAttribute("tipoMovimento") != null && !"".equals(record.getAttribute("tipoMovimento"))) {
			estremi += record.getAttribute("tipoMovimento") + " ";
		}
		if(record.getAttribute("annoMovimento") != null && !"".equals(record.getAttribute("annoMovimento"))) {
			estremi += record.getAttribute("annoMovimento") + " ";
		}	
		if(record.getAttribute("numeroMovimento") != null && !"".equals(record.getAttribute("numeroMovimento"))) {
			estremi += "N. " + record.getAttribute("numeroMovimento") + " ";
		}
		if(record.getAttribute("numeroSub") != null && !"".equals(record.getAttribute("numeroSub"))) {
			estremi += "Sub " + record.getAttribute("numeroSub") + " ";
		}
		if(record.getAttribute("numeroModifica") != null && !"".equals(record.getAttribute("numeroModifica"))) {
			estremi += "Modifica N. " + record.getAttribute("numeroModifica") + " ";
		}		
		return estremi;
	}
	
}
