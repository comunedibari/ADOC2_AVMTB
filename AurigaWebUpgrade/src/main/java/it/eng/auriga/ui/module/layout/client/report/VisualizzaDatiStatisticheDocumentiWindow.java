package it.eng.auriga.ui.module.layout.client.report;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.SavePreferenceAction;
import it.eng.utility.ui.module.layout.client.common.SavePreferenceWindow;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class VisualizzaDatiStatisticheDocumentiWindow extends ModalWindow {
	
	protected GWTRestDataSource layoutListaDS;
	protected GWTRestDataSource layoutListaDefaultDS;

	protected ToolStrip topListToolStrip;
	protected SelectItem layoutListaSelectItem;
	protected ListGrid layoutListaPickListProperties;
	protected SavePreferenceWindow saveLayoutListaWindow;
	protected ToolStripButton saveLayoutListaButton;

	private VisualizzaDatiStatisticheDocumentiList list;
	
	protected  ToolStrip buttons;
	protected  DetailToolStripButton exportPdfButton;
	protected  DetailToolStripButton exportXLSButton;

	public VisualizzaDatiStatisticheDocumentiWindow(final Record pRecord) {
		
		super("datiStatisticheDocumenti", false);
		
		setTitle("Statistiche sui documenti");

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		setWidth(1300);
		setHeight(500);
		
		layoutListaDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutListaDS.addParam("prefKey", getPrefKeyPrefix() + "layout.grid");

		layoutListaDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutListaDefaultDS.addParam("userId", "DEFAULT");
		layoutListaDefaultDS.addParam("prefKey", getPrefKeyPrefix() + "layout.grid");
		layoutListaDefaultDS.addParam("prefName", "DEFAULT");

		saveLayoutListaWindow = new SavePreferenceWindow(I18NUtil.getMessages().saveLayoutButton_title(), layoutListaDS, true, new SavePreferenceAction() {

			@Override
			public void execute(final String value) {
				if (value != null && !value.equals("")) {
					AdvancedCriteria criteriaLayoutLista = new AdvancedCriteria();
					criteriaLayoutLista.addCriteria("prefName", OperatorId.EQUALS, value);
					layoutListaDS.fetchData(criteriaLayoutLista, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							// Record layoutLista = new Record();
							// layoutLista.setAttribute("viewState", list.getViewState());
							// layoutLista.setAttribute("detailAuto", getDetailAuto());
							if (data.length != 0) {
								Record record = data[0];
								// record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));
								record.setAttribute("value", list.getViewState());
								layoutListaDS.updateData(record);
							} else {
								Record record = new Record();
								record.setAttribute("prefName", value);
								// record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));
								record.setAttribute("value", list.getViewState());
								layoutListaDS.addData(record);
							}
							layoutListaSelectItem.setValue(value);
						}
					});
				}
			}
		});

		layoutListaSelectItem = new SelectItem("prefName");
		layoutListaSelectItem.setValueField("prefName");
		layoutListaSelectItem.setDisplayField("prefName");
		layoutListaSelectItem.setTitle(I18NUtil.getMessages().layoutSelectItem_title());
		layoutListaSelectItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return (String) layoutListaSelectItem.getValue();
			}
		});

		layoutListaPickListProperties = new ListGrid();
		layoutListaPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		layoutListaPickListProperties.setShowHeader(false);
//		layoutListaPickListProperties.setCanRemoveRecords(true);
		// apply the selected preference from the SelectItem
		layoutListaPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				
				boolean isRemoveField = isAbilToRemovePreference(event.getRecord()) && event.getColNum() == 0;
				if(!isRemoveField) {	
					String preferenceName = event.getRecord().getAttribute("prefName");
					if (preferenceName != null && !"".equals(preferenceName)) {
						AdvancedCriteria criteria = new AdvancedCriteria();
						criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
						layoutListaDS.fetchData(criteria, new DSCallback() {
	
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									Record record = data[0];
									list.setViewState(record.getAttributeAsString("value"));
								}
							}
						});
					} else {
						layoutListaDefaultDS.fetchData(null, new DSCallback() {
	
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									Record record = data[0];
									list.setViewState(record.getAttributeAsString("value"));
								}
							}
						});
					}
				}
			}
		});
		layoutListaSelectItem.setPickListProperties(layoutListaPickListProperties);

		layoutListaSelectItem.setOptionDataSource(layoutListaDS);
//		layoutListaSelectItem.setAllowEmptyValue(true);

		ListGridField layoutListaPrefNameField = new ListGridField("prefName");
		ListGridField layoutListaRemoveField = new ListGridField("remove");
		layoutListaRemoveField.setType(ListGridFieldType.ICON);
		layoutListaRemoveField.setWidth(18);
		layoutListaRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if(isAbilToRemovePreference(record)) {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				} else {
					return null;
				}	
			}
		});
//		layoutListaRemoveField.setIsRemoveField(true);
		layoutListaRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				if(isAbilToRemovePreference(event.getRecord())) {
					final String prefName = event.getRecord().getAttribute("prefName");
					layoutListaDS.removeData(event.getRecord(), new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							String value = (String) layoutListaSelectItem.getValue();
							if (prefName != null && value != null && prefName.equals(value)) {
								layoutListaSelectItem.setValue((String) null);
							}
						}
					});
				}
			}
		});
		layoutListaSelectItem.setPickListFields(layoutListaRemoveField, layoutListaPrefNameField);

		// layoutListaSelectItem.addChangedHandler(new ChangedHandler() {
		// @Override
		// public void onChanged(ChangedEvent event) {
		// String preferenceName = (String) layoutListaSelectItem.getValue();
		// AdvancedCriteria criteria = new AdvancedCriteria();
		// if(preferenceName != null) { criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName); }
		// layoutListaDS.fetchData(criteria, new DSCallback() {
		// @Override
		// public void execute(DSResponse response, Object rawData, DSRequest request) {
		// Record[] data = response.getData();
		// if (data.length > 0 && data[0].getAttribute("value") != null) {
		// // Record layoutLista = new Record(JSON.decode(data[0].getAttributeAsString("value")));
		// // list.setViewState(layoutLista.getAttributeAsString("viewState"));
		// // setDetailAuto(layoutLista.getAttributeAsBoolean("detailAuto"));
		// list.setViewState(record.getAttributeAsString("value"));
		// }
		// }
		// });
		// }
		// });

		layoutListaSelectItem.setAutoFetchData(false);
		layoutListaSelectItem.setFetchMissingValues(true);

		saveLayoutListaButton = new ToolStripButton(I18NUtil.getMessages().saveLayoutButton_title());
		saveLayoutListaButton.setIcon("buttons/save.png");
		saveLayoutListaButton.setIconSize(16);
		saveLayoutListaButton.setAutoFit(true);
		saveLayoutListaButton.setPrompt(I18NUtil.getMessages().saveLayoutListaButton_prompt());
		saveLayoutListaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if ((!saveLayoutListaWindow.isDrawn()) || (!saveLayoutListaWindow.isVisible())) {
					saveLayoutListaWindow.getForm().clearValues();
					saveLayoutListaWindow.getForm().setValue((String) layoutListaSelectItem.getValue());
					saveLayoutListaWindow.redraw();
					saveLayoutListaWindow.show();
				}
				// SC.askforValue(I18NUtil.getMessages().saveLayoutButtonAskForValue_message() + ": ", new ValueCallback() {
				// @Override
				// public void execute(String value) {
				// if (value != null && !value.equals("")) {
				// Record record = new Record();
				// record.setAttribute("prefName", value);
				// record.setAttribute("value", list.getViewState());
				// layoutListaDS.addData(record);
				// layoutListaSelectItem.setValue(value);
				// }
				// }
				// });
			}
		});
		
		topListToolStrip = new ToolStrip();
		topListToolStrip.setWidth100();
		topListToolStrip.setHeight(30);
		topListToolStrip.addFill(); // push all buttons to the right
		topListToolStrip.addFormItem(layoutListaSelectItem);
		topListToolStrip.addButton(saveLayoutListaButton);
		
		list = new VisualizzaDatiStatisticheDocumentiList("datiStatisticheDocumenti", pRecord);
		
		exportPdfButton = new DetailToolStripButton(I18NUtil.getMessages().exportButton_prompt(), "export/pdf.png");
		exportPdfButton.addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {   
				exportPdf();
			}   
		}); 
		
		exportXLSButton = new DetailToolStripButton(I18NUtil.getMessages().exportButton_prompt(), "export/xls.png");
		exportXLSButton.addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {   
				exportXls(pRecord);
			}   
		}); 
		
		buttons = new ToolStrip();		
		buttons.setWidth100();       
		buttons.setHeight(30);
		buttons.addButton(exportPdfButton);
		buttons.addButton(exportXLSButton);  
		buttons.addFill(); //push all buttons to the right 
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(topListToolStrip);
		layout.addMember(list);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(buttons);
						
		setBody(portletLayout);
		
		setAttribute("edgeTop", 46, true);
		
		setIcon("menu/statisticheDocumenti.png");
		
		caricaPreferenceLista(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				buildWindowAndDataSet(pRecord);						
			}
		});
	}
	
	public boolean isAbilToRemovePreference(Record record) {
		final String prefName = record.getAttribute("prefName");
		return prefName != null && !"".equals(prefName) && !"DEFAULT".equalsIgnoreCase(prefName);					
	}
	
	protected void caricaPreferenceLista(final DSCallback callback) {
		AdvancedCriteria criteriaLayoutLista = new AdvancedCriteria();
		criteriaLayoutLista.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		layoutListaDS.fetchData(criteriaLayoutLista, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (data.length != 0) {
					Record record = data[0];
					list.setViewState(record.getAttributeAsString("value"));
					layoutListaSelectItem.setValue("DEFAULT");
					callback.execute(new DSResponse(), null, new DSRequest());
				} else {
					layoutListaDefaultDS.fetchData(null, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								Record record = data[0];
								list.setViewState(record.getAttributeAsString("value"));
								layoutListaSelectItem.setValue((String) null);
							} else {
								layoutListaSelectItem.setValue((String) null);
							}
							callback.execute(new DSResponse(), null, new DSRequest());
						}
					});
				}
			}
		});
	}
	
	public String getPrefKeyPrefix() {
		return Layout.getConfiguredPrefKeyPrefix() + this.nomeEntita + ".";
	}

	protected void exportXls(Record pRecord) {
		String formatoExport = "xls";
		export(formatoExport);			
	}

	protected void export(final String formatoExport) {
		Record record = new Record();
		record.setAttribute("nomeEntita", getTitle());
		record.setAttribute("formatoExport", formatoExport);
		record.setAttribute("csvSeparator", "|*|");
		record.setAttribute("criteria", new Criteria());
		LinkedHashMap<String, String> mappa = new LinkedHashMap<String, String>();					
		for (int i = 0; i < list.getFields().length; i++) {
			ListGridField field = list.getFields()[i];
			String fieldName = field.getName();
			if(fieldName.endsWith("XOrd")) {
				fieldName = fieldName.substring(0, fieldName.lastIndexOf("XOrd"));
			}
			String fieldTitle = field.getTitle();
			if(!list.getControlFields().contains(fieldName) && !"_checkboxField".equals(fieldName)) {						
				mappa.put(fieldName, fieldTitle);
			}
		}
		String[] fields = new String[mappa.keySet().size()];
		String[] headers = new String[mappa.keySet().size()];
		int n = 0;
		for(String key : mappa.keySet()) {
			fields[n] = key;						
			headers[n] = mappa.get(key);
			n++;
		}
		record.setAttribute("fields", fields);					
		record.setAttribute("headers", headers);										
		Record[] records = new Record[list.getRecords().length];						
		for (int i = 0; i < list.getRecords().length; i++) {	
			Record rec = new Record();
			for(String fieldName : fields) {
				rec.setAttribute(fieldName, list.getRecords()[i].getAttribute(fieldName));								
			}
			records[i] = rec;
		}			
		record.setAttribute("records", records);	
		GWTRestService<Record, Record> lReportDatasource = new GWTRestService("ReportDocAvanzatiDatasource");
		lReportDatasource.performCustomOperation("export", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String filename = "Results." + formatoExport;
				String uri = response.getData()[0].getAttribute("tempFileOut");
				
				 com.google.gwt.user.client.Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename) + "&url=" + URL.encode(uri));
			}
		}, new DSRequest());
	}

	protected void exportPdf() {
		String formatoExport = "pdf";
		export(formatoExport);
	}

	protected void buildWindowAndDataSet(Record lRecord) {
		setTitle(lRecord.getAttribute("title"));
		Record[] lRecords = lRecord.getAttributeAsRecordArray("dataset");		
		if(lRecords.length > 0) {
			list.setData(lRecords);
			show();
		} else {
			Layout.addMessage(new MessageBean("Nessun risultato trovato", "", MessageType.ERROR));
			markForDestroy();
		}
	}
	
	@Override
	protected void onDestroy() {		
		if(saveLayoutListaWindow != null) {
			saveLayoutListaWindow.destroy();
		}
		if(layoutListaDS != null) {
			layoutListaDS.destroy();
		}
		if(layoutListaDefaultDS != null) {
			layoutListaDefaultDS.destroy();
		}
		super.onDestroy();
	}
	
}