package it.eng.auriga.ui.module.layout.client.configurazioneFlussi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.SelezionaUfficioItems;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public abstract class AltriDatiRuoliAmmPopup extends Window {
		
	protected AltriDatiRuoliAmmPopup _window;
	protected ValuesManager vm;
	
	protected DynamicForm unitaOrgForm;
	protected DynamicForm ruoliProcUoForm;
	protected DynamicForm ruoliProcSvForm;
	
	protected CheckboxItem flgUoRuoloAmmItem;
	protected SelezionaUfficioItems unitaOrgItems;
	protected CheckboxItem flgUoSubordinateItem;
	protected CheckboxItem flgRuoloProcUoRuoloAmmItem;
	protected SelectItem ruoliProcUoItem;
	protected CheckboxItem flgRuoloProcSvRuoloAmmItem;
	protected SelectItem ruoliProcSvItem;
	
	protected Button okButton;

	public AltriDatiRuoliAmmPopup(String title, boolean canEdit, String codTipoFlusso, Record record){
		
		_window = this;
		
		vm = new ValuesManager();
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		setTitle(title);
		setShowTitle(true);
		setHeaderIcon("buttons/altriDati.png");
		
		setAutoCenter(true);
		setWidth(840);
		setHeight(175);
		
		unitaOrgForm = new DynamicForm();	
		unitaOrgForm.setValuesManager(vm);
		unitaOrgForm.setKeepInParentRect(true);
		unitaOrgForm.setWidth100();
		unitaOrgForm.setHeight100();
		unitaOrgForm.setNumCols(8);
		unitaOrgForm.setColWidths(120,1,1,1,1,1,"*","*");		
		unitaOrgForm.setCellPadding(5);
		unitaOrgForm.setWrapItemTitles(false);
		
		flgUoRuoloAmmItem = new CheckboxItem("flgUoRuoloAmm", "nella UO");
		flgUoRuoloAmmItem.setValue(true);
		flgUoRuoloAmmItem.setStartRow(true);
		flgUoRuoloAmmItem.setColSpan(1);
		flgUoRuoloAmmItem.setWidth(100);
		flgUoRuoloAmmItem.setHeight(30);
		flgUoRuoloAmmItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				flgUoRuoloAmmItem.setValue(true);
				flgRuoloProcUoRuoloAmmItem.setValue(false);
				flgRuoloProcSvRuoloAmmItem.setValue(false);
				redraw();
			}
		});
		
		unitaOrgItems = new SelezionaUfficioItems(unitaOrgForm, "idUoRuoloAmm", "descrizioneUoRuoloAmm", "codiceUoRuoloAmm", "organigrammaUoRuoloAmm"){
			@Override
			protected boolean codRapidoItemShowIfCondition() {
				return flgUoRuoloAmmItem.getValueAsBoolean() != null && flgUoRuoloAmmItem.getValueAsBoolean();
			}
			
			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition() {
				return flgUoRuoloAmmItem.getValueAsBoolean() != null && flgUoRuoloAmmItem.getValueAsBoolean();
			}
			
			@Override
			protected boolean organigrammaItemShowIfCondition() {
				return flgUoRuoloAmmItem.getValueAsBoolean() != null && flgUoRuoloAmmItem.getValueAsBoolean();
			}
			
			@Override
			public String getFinalitaOrganigramma() {
				return "ACL";
			}
		};
		
		flgUoSubordinateItem = new CheckboxItem("flgUoSubordinateRuoloAmm", "incluse UO subordinate");
		flgUoSubordinateItem.setColSpan(1);
		flgUoSubordinateItem.setWidth(50);
		flgUoSubordinateItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgUoRuoloAmmItem.getValueAsBoolean() != null && flgUoRuoloAmmItem.getValueAsBoolean();
			}
		});

		List<FormItem> listaUnitaOrgItems = new ArrayList<FormItem>();
		listaUnitaOrgItems.add(flgUoRuoloAmmItem);
		listaUnitaOrgItems.addAll(unitaOrgItems);
		listaUnitaOrgItems.add(flgUoSubordinateItem);
		unitaOrgForm.setItems(listaUnitaOrgItems.toArray(new FormItem[listaUnitaOrgItems.size()]));
		
//		DetailSection detailSectionUnitaOrg = new DetailSection("Nella UO", false, true, false, unitaOrgForm);
			
		ruoliProcUoForm = new DynamicForm();			
		ruoliProcUoForm.setValuesManager(vm);
		ruoliProcUoForm.setKeepInParentRect(true);
		ruoliProcUoForm.setWidth100();
		ruoliProcUoForm.setHeight100();
		ruoliProcUoForm.setNumCols(8);
		ruoliProcUoForm.setColWidths(120,1,1,1,1,1,"*","*");		
		ruoliProcUoForm.setCellPadding(5);
		ruoliProcUoForm.setWrapItemTitles(false);
		
		flgRuoloProcUoRuoloAmmItem = new CheckboxItem("flgRuoloProcUoRuoloAmm", "nella UO che ricopre il ruolo di processo");
		flgRuoloProcUoRuoloAmmItem.setValue(true);
		flgRuoloProcUoRuoloAmmItem.setStartRow(true);
		flgRuoloProcUoRuoloAmmItem.setColSpan(1);
		flgRuoloProcUoRuoloAmmItem.setWidth(100);
		flgRuoloProcUoRuoloAmmItem.setHeight(30);
		flgRuoloProcUoRuoloAmmItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				flgUoRuoloAmmItem.setValue(false);
				flgRuoloProcUoRuoloAmmItem.setValue(true);
				flgRuoloProcSvRuoloAmmItem.setValue(false);
				redraw();
			}
		});
		
		GWTRestDataSource ruoliProcUoDS = new GWTRestDataSource("LoadComboRuoliAttSoggIntProcDataSource");
		ruoliProcUoDS.addParam("codTipoFlusso", codTipoFlusso);
		
		ruoliProcUoItem = new SelectItem("idRuoloProcUoRuoloAmm");		
		ruoliProcUoItem.setValueField("key");
		ruoliProcUoItem.setDisplayField("value");
		ruoliProcUoItem.setOptionDataSource(ruoliProcUoDS);
		ruoliProcUoItem.setAutoFetchData(false);
		ruoliProcUoItem.setAlwaysFetchMissingValues(true);
		ruoliProcUoItem.setFetchMissingValues(true);
		ruoliProcUoItem.setShowTitle(false);
		ruoliProcUoItem.setClearable(true);
		ruoliProcUoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgRuoloProcUoRuoloAmmItem.getValueAsBoolean() != null && flgRuoloProcUoRuoloAmmItem.getValueAsBoolean();
			}
		});

		ruoliProcUoForm.setItems(flgRuoloProcUoRuoloAmmItem, ruoliProcUoItem);
		
//		DetailSection detailSectionRuoliProcUo = new DetailSection("Nella UO che ricopre il ruolo di processo", false, true, false, ruoliProcUoForm);
		
		ruoliProcSvForm = new DynamicForm();	
		ruoliProcSvForm.setValuesManager(vm);
		ruoliProcSvForm.setKeepInParentRect(true);
		ruoliProcSvForm.setWidth100();
		ruoliProcSvForm.setHeight100();
		ruoliProcSvForm.setNumCols(8);
		ruoliProcSvForm.setColWidths(120,1,1,1,1,1,"*","*");		
		ruoliProcSvForm.setCellPadding(5);
		ruoliProcSvForm.setWrapItemTitles(false);
		
		flgRuoloProcSvRuoloAmmItem = new CheckboxItem("flgRuoloProcSvRuoloAmm", "nella UO di colui che ricopre il ruolo di processo");
		flgRuoloProcSvRuoloAmmItem.setValue(true);
		flgRuoloProcSvRuoloAmmItem.setStartRow(true);
		flgRuoloProcSvRuoloAmmItem.setColSpan(1);
		flgRuoloProcSvRuoloAmmItem.setWidth(100);
		flgRuoloProcSvRuoloAmmItem.setHeight(30);
		flgRuoloProcSvRuoloAmmItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				flgUoRuoloAmmItem.setValue(false);
				flgRuoloProcUoRuoloAmmItem.setValue(false);
				flgRuoloProcSvRuoloAmmItem.setValue(true);
				redraw();
			}
		});
		
		GWTRestDataSource ruoliProcSvDS = new GWTRestDataSource("LoadComboRuoliAttSoggIntProcDataSource");
		ruoliProcSvDS.addParam("codTipoFlusso", codTipoFlusso);
		
		ruoliProcSvItem = new SelectItem("idRuoloProcSvRuoloAmm");		
		ruoliProcSvItem.setValueField("key");
		ruoliProcSvItem.setDisplayField("value");
		ruoliProcSvItem.setOptionDataSource(ruoliProcSvDS);
		ruoliProcSvItem.setAutoFetchData(false);
		ruoliProcSvItem.setAlwaysFetchMissingValues(true);
		ruoliProcSvItem.setFetchMissingValues(true);
		ruoliProcSvItem.setShowTitle(false);
		ruoliProcSvItem.setClearable(true);
		ruoliProcSvItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgRuoloProcSvRuoloAmmItem.getValueAsBoolean() != null && flgRuoloProcSvRuoloAmmItem.getValueAsBoolean();
			}
		});

		
		ruoliProcSvForm.setItems(flgRuoloProcSvRuoloAmmItem, ruoliProcSvItem);
		
//		DetailSection detailSectionRuoliProcSv = new DetailSection("Nella UO di colui che ricopre il ruolo di processo", false, true, false, ruoliProcSvForm);
		
		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				onClickOkButton(new Record(vm.getValues()));
				_window.markForDestroy();
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(okButton);
		_buttons.setAutoDraw(false);
	
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.setPadding(5);
		lVLayout.setMembers(unitaOrgForm, ruoliProcUoForm, ruoliProcSvForm, _buttons);

		addItem(lVLayout);

		draw();
					
		editRecord(record);
		
		setCanEdit(canEdit);	
	}
	
	public void editRecord(Record record) {
		
		vm.setValue("flgUoRuoloAmm", record.getAttribute("flgUoRuoloAmm") != null && "true".equalsIgnoreCase(record.getAttribute("flgUoRuoloAmm")));	
		vm.setValue("idUoRuoloAmm", record.getAttribute("idUoRuoloAmm"));	
		vm.setValue("descrizioneUoRuoloAmm", record.getAttribute("descrizioneUoRuoloAmm"));	
		vm.setValue("codiceUoRuoloAmm", record.getAttribute("codiceUoRuoloAmm"));	
		vm.setValue("organigrammaUoRuoloAmm", record.getAttribute("organigrammaUoRuoloAmm"));	
		vm.setValue("flgUoSubordinateRuoloAmm", record.getAttribute("flgUoSubordinateRuoloAmm") != null && "true".equalsIgnoreCase(record.getAttribute("flgUoSubordinateRuoloAmm")));				
		vm.setValue("flgRuoloProcUoRuoloAmm", record.getAttribute("flgRuoloProcUoRuoloAmm") != null && "true".equalsIgnoreCase(record.getAttribute("flgRuoloProcUoRuoloAmm")));	
		vm.setValue("idRuoloProcUoRuoloAmm", record.getAttribute("idRuoloProcUoRuoloAmm"));
		vm.setValue("descrRuoloProcUoRuoloAmm", record.getAttribute("descrRuoloProcUoRuoloAmm"));
		vm.setValue("flgRuoloProcSvRuoloAmm", record.getAttribute("flgRuoloProcSvRuoloAmm") != null && "true".equalsIgnoreCase(record.getAttribute("flgRuoloProcSvRuoloAmm")));	
		vm.setValue("idRuoloProcSvRuoloAmm", record.getAttribute("idRuoloProcSvRuoloAmm"));
		vm.setValue("descrRuoloProcSvRuoloAmm", record.getAttribute("descrRuoloProcSvRuoloAmm"));
		
		if (record.getAttribute("idRuoloProcUoRuoloAmm") != null && !"".equals(record.getAttributeAsString("idRuoloProcUoRuoloAmm"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idRuoloProcUoRuoloAmm"), record.getAttribute("descrRuoloProcUoRuoloAmm"));
			ruoliProcUoItem.setValueMap(valueMap);
		}
		
		if (record.getAttribute("idRuoloProcSvRuoloAmm") != null && !"".equals(record.getAttributeAsString("idRuoloProcSvRuoloAmm"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("idRuoloProcSvRuoloAmm"), record.getAttribute("descrRuoloProcSvRuoloAmm"));
			ruoliProcSvItem.setValueMap(valueMap);
		}
		
		unitaOrgItems.afterEditRecord(record);
	}
	
	public void redraw() {
		
		for (DynamicForm form : vm.getMembers()) {
			form.redraw();
		}
	}
	
	public void setCanEdit(Boolean canEdit) {
		if(canEdit) {
			okButton.show();
		} else {
			okButton.hide();
		}
		for (DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
	}
	
	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}

	public abstract void onClickOkButton(Record record);
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
	
}
