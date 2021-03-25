package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public class DirigenteRespRegTecnicaCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public DirigenteRespRegTecnicaCompletaItem() {
		super("dirigenteRespRegTecnica", "dirigenteRespRegTecnicaFromLoadDett", "codUoDirigenteRespRegTecnica", "desDirigenteRespRegTecnica");
	}
	
	@Override
	public List<FormItem> getCustomItems() {
		
		List<FormItem> customItems = new ArrayList<FormItem>();
		
		final CheckboxItem flgDirRespRegTecnicaAncheRdPItem = new CheckboxItem("flgDirRespRegTecnicaAncheRdP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheRdP_title());
		flgDirRespRegTecnicaAncheRdPItem.setDefaultValue(false);
		flgDirRespRegTecnicaAncheRdPItem.setColSpan(1);
		flgDirRespRegTecnicaAncheRdPItem.setWidth("*");
		flgDirRespRegTecnicaAncheRdPItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				manageOnChangedFlgDirRespRegTecnicaAncheRdP(value != null && value);
			}
		});
		flgDirRespRegTecnicaAncheRdPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("FORZA_RDP_UGUALE_DIR")) {
					flgDirRespRegTecnicaAncheRdPItem.setValue(true);
					flgDirRespRegTecnicaAncheRdPItem.setCanEdit(false);
				}
				return showFlgAncheRdP() && getIdUdAttoDaAnn() == null;
			}
		});
		customItems.add(flgDirRespRegTecnicaAncheRdPItem);
		
		final CheckboxItem flgDirRespRegTecnicaAncheRUPItem = new CheckboxItem("flgDirRespRegTecnicaAncheRUP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheRUP_title());
		flgDirRespRegTecnicaAncheRUPItem.setDefaultValue(false);
		flgDirRespRegTecnicaAncheRUPItem.setColSpan(1);
		flgDirRespRegTecnicaAncheRUPItem.setWidth("*");
		flgDirRespRegTecnicaAncheRUPItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				manageOnChangedFlgDirRespRegTecnicaAncheRUP(value != null && value);
			}
		});
		flgDirRespRegTecnicaAncheRUPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("FORZA_RUP_UGUALE_DIR")) {
					flgDirRespRegTecnicaAncheRUPItem.setValue(true);
					flgDirRespRegTecnicaAncheRUPItem.setCanEdit(false);
				}
				return showFlgAncheRUP() && getIdUdAttoDaAnn() == null;
			}
		});
		customItems.add(flgDirRespRegTecnicaAncheRUPItem);
		
		return customItems;
	}
	
	public void manageOnChangedFlgDirRespRegTecnicaAncheRdP(boolean value) {
		
	}

	public void manageOnChangedFlgDirRespRegTecnicaAncheRUP(boolean value) {
		
	}
		
	public boolean showFlgAncheRdP() {
		return true;
	}
	
	public boolean showFlgAncheRUP() {
		return true;
	}
	
	public void clearFlgDirRespRegTecnicaAncheRUP() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			lReplicableCanvas.getForm()[0].setValue("flgDirRespRegTecnicaAncheRUP", false);
		}	
	}

	public String getIdUdAttoDaAnn() {
		return null;
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
}