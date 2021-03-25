package it.eng.utility.ui.module.layout.client.common;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.RecordComponentPoolingMode;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

public class GridItem extends CanvasItem {
	
	protected String nomeLista;
	
	protected VLayout layout;
	protected ListGrid grid; 
	protected String gridPkField; 
	protected String gridEmptyMessage; 
	
	protected ToolStrip toolStrip;
	protected ToolStripButton multiselectButton;	
	protected SelectItem layoutListaSelectItem;
	protected SavePreferenceWindow saveLayoutListaWindow;
	protected ToolStripButton saveLayoutListaButton;
	
	protected ToolStrip bottomListToolStrip;
	
	protected GWTRestDataSource layoutListaDS;
	protected GWTRestDataSource layoutListaDefaultDS;
	
	protected DataSource gridDataSource;	
	protected ListGridField[] gridFields;	
	protected String gridSortField;
	protected SortDirection gridSortDirection;	
	
	protected boolean canEdit = false;
	protected boolean expandable;
	protected boolean multiselect;
	
	protected boolean showPreference = true;
	protected boolean showEditButtons = true;	
	protected boolean showNewButton = true;
	protected boolean showModifyButton = true;
	protected boolean showDeleteButton = true;
	
	public GridItem(String name, String nomeLista) {	
		super(name);
		this.setNomeLista(nomeLista);
		createComponent();
	}
	public GridItem(String name, String nomeLista, boolean editable, boolean expandable) {	
		super(name);
		this.setNomeLista(nomeLista);
		this.setEditable(editable);		
		this.setExpandable(expandable); 
		createComponent();
	}
	
	public GridItem(String name, String nomeLista, boolean editable) {
		this(name, nomeLista);
		this.setNomeLista(nomeLista);
		this.setEditable(editable);		
		createComponent();
	}

	protected void createComponent() {
		//setWidth("*");
		//setHeight("*");
		setColSpan("*");
		setEndRow(true);
		setStartRow(true);
		setShowTitle(false);
		setShowHint(false);
		setShowIcons(false);
		// this is going to be an editable data item             
		setShouldSaveValue(true);
		addShowValueHandler(new ShowValueHandler() {                 
			@Override                 
			public void onShowValue(ShowValueEvent event) {                     
				setData(event.getDataValueAsRecordList());
			}             
		}); 
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			@Override
			public void onInit(FormItem item) {
				init(item);
			}
		});
	}
	
	public void init(FormItem item) {
		grid = buildGrid();
		grid.setData((RecordList) item.getValue());
		
		// per evitare l'effetto blink degli scroll che compaiono e scompaiono
		grid.setSizeMayChangeOnRedraw(true);
		grid.setRedrawOnResize(true);
		grid.setLeaveScrollbarGap(true);
		grid.setBodyOverflow(Overflow.SCROLL);
//		grid.setShowCustomScrollbars(false);
		
		layoutListaDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutListaDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + getNomeLista() + ".layout.grid");		

		layoutListaDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		layoutListaDefaultDS.addParam("userId", "DEFAULT");
		layoutListaDefaultDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + getNomeLista() + ".layout.grid");			
		layoutListaDefaultDS.addParam("prefName", "DEFAULT");
		
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
//			                Record layoutLista = new Record();
//							layoutLista.setAttribute("viewState", list.getViewState());
//							layoutLista.setAttribute("detailAuto", getDetailAuto());																               
							if (data.length != 0) {   
								Record record = data[0];    			                	
//			                	record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));		
								record.setAttribute("value", grid.getViewState());
								layoutListaDS.updateData(record);			                    
							} else {
								Record record = new Record();   
								record.setAttribute("prefName", value); 
//			                    record.setAttribute("value", JSON.encode(layoutLista.getJsObj(), encoder));
								record.setAttribute("value", grid.getViewState());
								layoutListaDS.addData(record);			                              
							}			               			               
							layoutListaSelectItem.setValue(value);   			                
						}   
					});     					
				}   
			}
		});				

		final ListGrid layoutListaPickListProperties = new ListGrid();   
		layoutListaPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		layoutListaPickListProperties.setShowHeader(false);
		layoutListaPickListProperties.setCanRemoveRecords(true); 		 
		//apply the selected preference from the SelectItem   				
		layoutListaPickListProperties.addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
				String preferenceName = (String) event.getRecord().getAttribute("prefName");      
				if(preferenceName != null && !"".equals(preferenceName)) { 
					AdvancedCriteria criteria = new AdvancedCriteria();        
					criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName); 
					layoutListaDS.fetchData(criteria, new DSCallback() {   
						@Override  
						public void execute(DSResponse response, Object rawData, DSRequest request) {   
							Record[] data = response.getData();   
							if (data.length > 0 && data[0].getAttribute("value") != null) {  										    
//	                        	Record layoutLista = new Record(JSON.decode(data[0].getAttributeAsString("value")));                        	
//	                        	list.setViewState(layoutLista.getAttributeAsString("viewState"));
//	                        	setDetailAuto(layoutLista.getAttributeAsBoolean("detailAuto"));
								grid.setViewState(data[0].getAttributeAsString("value"));   
								redrawRecordButtons();
							}   
						}   
					});
				} else {
					layoutListaDefaultDS.fetchData(null, new DSCallback() {   
						@Override  
						public void execute(DSResponse response, Object rawData, DSRequest request) {   
							Record[] data = response.getData();   
							if (data.length > 0 && data[0].getAttribute("value") != null) {   										    
//	                        	Record layoutLista = new Record(JSON.decode(data[0].getAttributeAsString("value")));                        	
//	                        	list.setViewState(layoutLista.getAttributeAsString("viewState"));
//	                        	setDetailAuto(layoutLista.getAttributeAsBoolean("detailAuto"));
								grid.setViewState(data[0].getAttributeAsString("value"));  
								redrawRecordButtons();										
							}   
						}   
					});
				}
			}
		});
		layoutListaSelectItem.setPickListProperties(layoutListaPickListProperties);  
		layoutListaSelectItem.setOptionDataSource(layoutListaDS);      
		layoutListaSelectItem.setAllowEmptyValue(true);

		ListGridField layoutListaPrefNameField = new ListGridField("prefName");
		ListGridField layoutListaRemoveField = new ListGridField("remove");
		layoutListaRemoveField.setType(ListGridFieldType.ICON);
		layoutListaRemoveField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("prefName") == null || "".equals(record.getAttributeAsString("prefName"))) {
					return null;
				} else {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
			}
		});
		layoutListaRemoveField.setIsRemoveField(true);
		layoutListaSelectItem.setPickListFields(layoutListaRemoveField, layoutListaPrefNameField);
		
		saveLayoutListaButton = new ToolStripButton(I18NUtil.getMessages().saveLayoutButton_title());   
		saveLayoutListaButton.setIcon("buttons/save.png"); 
		saveLayoutListaButton.setIconSize(16); 
		saveLayoutListaButton.setAutoFit(true);   
		saveLayoutListaButton.setPrompt(I18NUtil.getMessages().saveLayoutListaButton_prompt());       		
		saveLayoutListaButton.addClickHandler(new ClickHandler() {   
			public void onClick(ClickEvent event) {   
				if ((!saveLayoutListaWindow.isDrawn()) || (!saveLayoutListaWindow.isVisible())) {
					saveLayoutListaWindow.getForm().clearValues();					
					saveLayoutListaWindow.getForm().setValue((String) layoutListaSelectItem.getValue());
					saveLayoutListaWindow.markForRedraw();
					saveLayoutListaWindow.show();
				}   					
			}   
		});
		
		List<ToolStripButton> customEditButtons = buildCustomEditButtons();
		List<Canvas> customEditCanvas = buildCustomEditCanvas();
		toolStrip = new ToolStrip();
		toolStrip.setBackgroundColor("transparent");
		toolStrip.setBackgroundImage("blank.png");
		toolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		toolStrip.setBorder("0px");
		toolStrip.setWidth100();           
		toolStrip.setHeight(30);
		
		if(customEditButtons != null) {
			for(ToolStripButton editButton : customEditButtons) {
				toolStrip.addButton(editButton);
			}
		}
		if(customEditCanvas != null) {
			for(Canvas editCanvas : customEditCanvas) {
				toolStrip.addMember(editCanvas);
			}
		}
		
		if (getGridMultiselectButtons() != null && getGridMultiselectButtons().length > 0) {			
			multiselectButton = new ToolStripButton();
			multiselectButton.setIcon("buttons/multiselect.png");
			multiselectButton.setIconSize(16);
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOnButton_prompt());
			multiselectButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					if (getMultiselect()) {
						setMultiselect(false);
					} else {
						setMultiselect(true);
					}
				}
			});
			toolStrip.addButton(multiselectButton);
		}
		
		toolStrip.addFill();
		toolStrip.addFormItem(layoutListaSelectItem);
		toolStrip.addButton(saveLayoutListaButton); 		
				
		layout = buildLayout();
		layout.setPadding(1); // aggiunto per evitare l'effetto blink degli scroll che compaiono e scompaiono
		if((customEditButtons != null && customEditButtons.size() > 0) || 
		   (customEditCanvas != null && customEditCanvas.size() > 0) || 
		   isShowPreference()) {
			layout.addMember(toolStrip);
		}
		layout.addMember(grid);			
		if (getGridMultiselectButtons() != null && getGridMultiselectButtons().length > 0) {
			bottomListToolStrip = new ToolStrip();
			bottomListToolStrip.setWidth100();
			bottomListToolStrip.setHeight(30);
			bottomListToolStrip.setBackgroundColor("transparent");
			bottomListToolStrip.setBackgroundImage("blank.png");
			bottomListToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
			bottomListToolStrip.setBorder("0px");
			for (GridMultiToolStripButton lToolStripButton : getGridMultiselectButtons()) {
				bottomListToolStrip.addButton(lToolStripButton);
			}
			bottomListToolStrip.addFill();
			layout.addMember(bottomListToolStrip);			
		}
		
		((CanvasItem)item).setCanvas(layout);			
		
		if (getGridMultiselectButtons() != null && getGridMultiselectButtons().length > 0) {
			setMultiselect(true);
		}
			
		if(isShowPreference()) {
			AdvancedCriteria criteriaLayoutLista = new AdvancedCriteria();        
			criteriaLayoutLista.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");	
			layoutListaDS.fetchData(criteriaLayoutLista, new DSCallback() {   
				@Override  
				public void execute(DSResponse response, Object rawData, DSRequest request) {   
					Record[] data = response.getData();   
					if (data.length != 0) {   
						Record record = data[0];         
						grid.setViewState(record.getAttributeAsString("value"));                	
						layoutListaSelectItem.setValue("DEFAULT");		
						redrawRecordButtons();
					} else {
						layoutListaDefaultDS.fetchData(null, new DSCallback() {      
							@Override  
							public void execute(DSResponse response, Object rawData, DSRequest request) {   
								Record[] data = response.getData();   
								if (data.length != 0) {   
									Record record = data[0];         
									grid.setViewState(record.getAttributeAsString("value"));    
									layoutListaSelectItem.setValue((String) null);		
									redrawRecordButtons();
								} 
							}   
						});                    
					}				
				}   
			});     
		}
	}
	
	protected GridMultiToolStripButton[] getGridMultiselectButtons() {
		return new GridMultiToolStripButton[] {};
	}
	
	public void setMultiselect(Boolean multiselect) {
		this.multiselect = multiselect;
		if (multiselect) {
			grid.deselectAllRecords();
			grid.setShowSelectedStyle(false);
			multiselectButton.setIcon("buttons/multiselect_off.png");
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOffButton_prompt());
			if (isDisableGridRecordComponent()) {
				grid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
				grid.setSelectionType(SelectionStyle.SIMPLE);
			} else {
				grid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
				grid.setSelectionType(SelectionStyle.NONE);
			}
		} else {
			grid.deselectAllRecords();
			grid.setShowSelectedStyle(true);
			multiselectButton.setIcon("buttons/multiselect.png");
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOnButton_prompt());
			grid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
			grid.setSelectionType(SelectionStyle.SINGLE);
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				redrawMultiselectButtons();
				grid.markForRedraw();
			}
		});
	}

	public void redrawMultiselectButtons() {
		if (multiselect) {
			for (MultiToolStripButton lToolStripButton : getGridMultiselectButtons()) {
				if (lToolStripButton.toShow()) {
					lToolStripButton.show();
				} else {
					lToolStripButton.hide();
				}
			}
		} else {
			for (MultiToolStripButton lToolStripButton : getGridMultiselectButtons()) {
				lToolStripButton.hide();
			}
		}
	}

	public Boolean getMultiselect() {
		return multiselect;
	}
	
	protected VLayout buildLayout() {
		return new VLayout();
	}
	
	public ListGrid buildGrid() {
		ListGrid grid = new ListGrid() {	
			
			@Override
			protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
				String baseStyle = getGridBaseStyle(record, rowNum, colNum);
				if(baseStyle != null) {
					return baseStyle;
				}
				return super.getBaseStyle(record, rowNum, colNum);
			}			
			
			@Override
			public boolean canExpandRecord(ListGridRecord record, int rowNum) {
				return canExpandGridRecord(record, rowNum, this);
			}
			
			@Override  
			protected Canvas getExpansionComponent(final ListGridRecord record) {  
				return createExpansionComponent(record);
			}
			
			@Override  
			protected Canvas createRecordComponent(ListGridRecord record, Integer colNum) {   						
				return createGridRecordComponent(this, record, colNum);
			}
			
//			@Override
//	        public Canvas updateRecordComponent(ListGridRecord record, Integer colNum, Canvas component,
//	                boolean recordChanged) {
//	            if(component != null && component instanceof UpdateableRecordComponent && recordChanged) {
//	            	((UpdateableRecordComponent) component).updateComponent(record);
//	                return component;
//	            }
//	            else {
//	                return super.updateRecordComponent(record, colNum, component, recordChanged);
//	            }
//	        }
		};		
		grid.setWidth100();				
		grid.setHeight100();
		grid.setDataPageSize(25);
		grid.setAlternateRecordStyles(true);		
		grid.setWrapCells(true);
		grid.setShowEmptyMessage(true);
		if(gridEmptyMessage != null && !"".equals(gridEmptyMessage)) {
			grid.setEmptyMessage(gridEmptyMessage);
		} else {
			grid.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		}
		grid.setLeaveScrollbarGap(true);	
		grid.setBodyOverflow(Overflow.SCROLL);
		grid.setFixedRecordHeights(false);
		grid.setCanReorderFields(true);
		grid.setCanResizeFields(true);
		grid.setCanReorderRecords(true);
		grid.setCanHover(true);		
		grid.setAutoFetchData(false);
		if (isDisableGridRecordComponent()){
			grid.setShowRecordComponents(false);
			grid.setShowRecordComponentsByCell(false);
			grid.setRecordComponentPoolingMode(null);
			grid.setPoolComponentsPerColumn(null);
		} else {
			grid.setShowRecordComponents(true);
			grid.setShowRecordComponentsByCell(true);
//			grid.setRecordComponentPoolingMode(RecordComponentPoolingMode.RECYCLE);
//			grid.setPoolComponentsPerColumn(true);
		}
		grid.setShowAllRecords(true);
//		grid.setAutoFitFieldWidths(true);
//		grid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
//		grid.setAutoFitMaxWidth(100);		
//		grid.setAutoFitMaxWidth(250);	
		if (isEditable()){
			grid.setCanEdit(true);
			grid.setEditEvent(ListGridEditEvent.CLICK);
//			grid.setEditByCell(true);					
		}
		if (isExpandable()){
			grid.setCanExpandRecords(true);
		}	
//		grid.setRowEndEditAction(com.smartgwt.client.types.ListGridEditEvent.CLICK); 
		grid.setCanAutoFitFields(false);		
//		grid.setNoDoubleClicks(true);							
		grid.setCanDragRecordsOut(true);   				
//		grid.setShowHoverComponents(true);
		grid.setShowHeaderContextMenu(true);					
		grid.setDateFormatter( DateDisplayFormat.TOEUROPEANSHORTDATE); 
		grid.setDatetimeFormatter( DateDisplayFormat.TOEUROPEANSHORTDATETIME );								
		grid.setTop(10);	
		
		if (gridDataSource != null)
			grid.setDataSource(gridDataSource);				
		if (gridFields != null)
			grid.setFields(gridFields);
		if (gridSortField != null)
			grid.setSortField(gridSortField);
		if(gridSortDirection != null) {
			grid.setSortDirection(gridSortDirection);
		} else {
			grid.setSortDirection(SortDirection.ASCENDING);
		}
		grid.setValidateOnChange(true);				
		grid.addEditCompleteHandler(new EditCompleteHandler() {
			@Override
			public void onEditComplete(EditCompleteEvent event) {
				manageControlloEditComplete(event);
			}
		});
		return grid;
	}
	
	public void setGridEmptyMessage(String emptyMessage) {
		this.gridEmptyMessage = emptyMessage;	
		if(getGrid() != null) {
			if(gridEmptyMessage != null && !"".equals(gridEmptyMessage)) {
				getGrid().setEmptyMessage(gridEmptyMessage);
			} else {
				getGrid().setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
			}
		}
	}
	
	public boolean canExpandGridRecord(ListGridRecord record, int rowNum, ListGrid listGrid) {
		return listGrid.canExpandRecord(record, rowNum);
	}
	
	public void manageControlloEditComplete(EditCompleteEvent event) {
		
	}
	
//	public void refreshGridRecordComponents() {
//		for (ListGridRecord record : grid.getRecords()) {
//			grid.refreshRecordComponent(grid.getRecordIndex(record));
//		}
//	}
	
	public void refreshRows() {
		for (ListGridRecord record : grid.getRecords()) {
			grid.refreshRow(grid.getRecordIndex(record));
		}
	}
	
	public void setData(RecordList data) {
		grid.deselectAllRecords();
		if (data != null) {
			grid.setData(data);				
		} else {
			grid.setData(new RecordList());
		}
		updateGridItemValue();  
	}
	
	public RecordList getData() {
		return grid.getDataAsRecordList();				
	}
	
	public void addData(Record record) {
		grid.addData(record);	
		grid.validateRow(grid.getDataAsRecordList().getLength()-1);
		updateGridItemValue();
	}
	
	public void addDataAndRefresh(final Record record) {
		addData(record);	
		grid.refreshRow(grid.getRecordIndex(record));
//		grid.refreshRecordComponent(grid.getRecordIndex(record));
//		grid.invalidateRecordComponents(); 
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
    		
			@Override
			public void execute() {
				grid.selectSingleRecord(record);
			}
    	});	
	}
	
	public void updateData(Record record, Record oldRecord) {		
		RecordList data = grid.getDataAsRecordList();		
		int index = data.findIndex(getGridPkField(), oldRecord.getAttribute(getGridPkField()));		
		ListGridRecord newRecord = new ListGridRecord(record.getJsObj());
		if (record.getAttributeAsBoolean("_canEdit")) 
			newRecord.setAttribute("_canEdit", true);
		data.set(index, newRecord);
		setData(data);											
	}
	
	public void updateDataAndRefresh(final Record record, Record oldRecord) {
		updateData(record, oldRecord);
		grid.refreshRow(grid.getRecordIndex(oldRecord));
//		grid.refreshRecordComponent(grid.getRecordIndex(oldRecord));
//		grid.invalidateRecordComponents();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
    		
			@Override
			public void execute() {
				grid.selectSingleRecord(record);
			}
    	});		
	}
	
	public void clearValue() {
		grid.setData(new Record[0]);
		updateGridItemValue();
	}
	
	protected void updateGridItemValue() {
//		setHeight(((grid.getDataAsRecordList().getLength()+1)*30)+50); 
		redrawRecordButtons();
		RecordList data = grid.getDataAsRecordList();   
		CanvasItem item = layout.getCanvasItem();
		if (data != null) { 
			item.storeValue(data);                             
		} else {                                 
			item.storeValue(new Record[0]);                             
		}       
	}
	
	public ListGrid getGrid() {
		return grid;
	}

	public void setGrid(ListGrid grid) {
		this.grid = grid;
	}
	
	public String getGridPkField() {
		return gridPkField;
	}

	public void setGridPkField(String gridPkField) {
		this.gridPkField = gridPkField;
	}

	public DataSource getGridDataSource() {
		return this.gridDataSource;
	}

	public void setGridDataSource(DataSource gridDataSource) {
		this.gridDataSource = gridDataSource;
	}
	
	public ListGridField[] getGridFields() {
		return gridFields;
	}

	public void setGridFields(ListGridField... gridFields) {
		this.gridFields = gridFields;
	}
	
	public String getGridSortField() {
		return gridSortField;
	}

	public void setGridSortField(String gridSortField) {
		this.gridSortField = gridSortField;
	}
	
	public SortDirection getGridSortDirection() {
		return gridSortDirection;
	}

	public void setGridSortDirection(SortDirection gridSortDirection) {
		this.gridSortDirection = gridSortDirection;
	}
	
	public String getGridBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		return null;
	}

	public boolean isDisableGridRecordComponent() {
		return true;
	}

	public Canvas createGridRecordComponent(ListGrid grid, ListGridRecord record, Integer colNum) {
		return null;
	}

	protected Canvas createExpansionComponent(final ListGridRecord record) {  
		return null;
	}
	
	public void redrawRecordButtons() {				
		try { grid.hideField("buttons"); } catch(Exception e) {}
		try { grid.refreshFields(); } catch(Exception e) {}					
		try { grid.showField("buttons"); } catch(Exception e) {}		
		grid.markForRedraw();	
	}
	
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();		
		ToolStripButton newButton = new ToolStripButton();   
		newButton.setIcon("buttons/new.png");   
		newButton.setIconSize(16);
		newButton.setPrefix("newButton");
		newButton.setPrompt(I18NUtil.getMessages().newButton_prompt());
		newButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickNewButton();   	
			}   
		});  
		if (isShowNewButton())
			buttons.add(newButton);
		
		
		ToolStripButton modifyButton = new ToolStripButton();   
		modifyButton.setIcon("buttons/modify.png");   
		modifyButton.setIconSize(16);
		modifyButton.setPrefix("modifyButton");
		modifyButton.setPrompt(I18NUtil.getMessages().modifyButton_prompt());
		modifyButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickModifyButton();   	
			}   
		});  
		if (isShowModifyButton())			
			buttons.add(modifyButton);
		
		return buttons;
	}
	
	public List<Canvas> buildCustomEditCanvas() {
		return null;
	}
	
	public void onClickNewButton() {};
	public void onClickModifyButton() {};
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {
					if(canEdit ) { 
						if(isShowEditButtons()) { 
							member.show();						
							if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("newButton"))
								{
									if (isShowNewButton())								
											((ToolStripButton) member).show();
									else
										((ToolStripButton) member).hide();
								}							
							if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("modifyButton"))
								{
									if (isShowModifyButton())								
										((ToolStripButton) member).show();
									else
										((ToolStripButton) member).hide();
								}							
						} else {
							member.hide();
						}					
					}else {
						member.hide();
					}
				}
			}	
			if(isShowPreference()) {
				layoutListaSelectItem.show();
				saveLayoutListaButton.show();
			}
			grid.setCanReorderRecords(canEdit);			
			redrawRecordButtons();
//			refreshGridRecordComponents();
//			for(ListGridRecord record : grid.getRecords()) {   
//				record.setCustomStyle(canEdit ? it.eng.utility.Styles.cell : it.eng.utility.Styles.cellDisabled);
//	        }  
//			grid.markForRedraw();
		}
	}

	public void removeData(Record record) {
		grid.removeData(record);	
		updateGridItemValue();
	}
	
	public RecordList getValueAsRecordList() {
		return getData();
	}
	
	public String getNomeLista() {
		return nomeLista;
	}

	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}
	
	public boolean isEditable() {
		return canEdit;
	}

	public void setEditable(boolean canEdit) {
		this.canEdit = canEdit;
	}
	
	public boolean isExpandable() {
		return expandable;
	}

	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}
	
	public boolean isShowPreference() {
		return showPreference;
	}
	
	public void setShowPreference(boolean showPreference) {
		this.showPreference = showPreference;
	}
	
	public boolean isShowEditButtons() {
		return showEditButtons;
	}

	public void setShowEditButtons(boolean showEditButtons) {
		this.showEditButtons = showEditButtons;
	}
	
	public boolean isShowNewButton() {
		return showNewButton;
	}
	
	public void setShowNewButton(boolean showNewButton) {
		this.showNewButton = showNewButton;
	}
	
	public boolean isShowModifyButton() {
		return showModifyButton;
	}
	
	public void setShowModifyButton(boolean showModifyButton) {
		this.showModifyButton = showModifyButton;
	}
	
	public boolean isShowDeleteButton() {
		return showDeleteButton;
	}
	
	public void setShowDeleteButton(boolean showDeleteButton) {
		this.showDeleteButton = showDeleteButton;
	}
	
	public void manageOnDestroy() {
		if(saveLayoutListaWindow != null) {
			saveLayoutListaWindow.destroy();
		}
		if(layoutListaDS != null) {
			layoutListaDS.destroy();
		}
		if(layoutListaDefaultDS != null) {
			layoutListaDefaultDS.destroy();
		}
	} 
	
	public abstract class GridMultiToolStripButton extends MultiToolStripButton {

		private ListGrid list;
		
		public GridMultiToolStripButton(String pIcon, ListGrid pList, String pTitle) {
			this(pIcon, pList, pTitle, false);
		}
		
		public GridMultiToolStripButton(String pIcon, ListGrid pList, String pTitle, boolean pToShowTitle) {			
			super(pIcon, null, pTitle, pToShowTitle);
			this.list = pList;
		}
		
		@Override
		public ListGrid getList() {
			return list;
		}
	}
	
};
