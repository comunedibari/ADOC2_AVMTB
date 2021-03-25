package it.eng.auriga.ui.module.layout.client.sub_profili;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.anagrafeProcedimenti.LookupProcedimentiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;


public class ListaTipoProcessoProcedimentoItem extends GridItem {
	
	protected ListGridField flgSelectedField;
	protected ListGridField idOggettoPrivField;
	protected ListGridField tipoOggettoPrivField;
	protected ListGridField codiceOggettoPrivField;
	protected ListGridField denominazioneOggettoPrivField;
	protected ListGridField listaPrivilegiOggettoPrivField;
	protected ListaTipoProcessoProcedimentoItem instance = this;	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	protected GridMultiToolStripButton cambiaPrivilegiMultiButton;
	protected GridMultiToolStripButton cancellaPrivilegiMultiButton;
	protected LinkedHashMap<String, String> listaPrivilegiValueMap = new LinkedHashMap<String, String>();		

	public ListaTipoProcessoProcedimentoItem(String name) {
		
		super(name, "sub_profili_lista_tipo_processo_procedimento");
		
		setGridPkField("idOggettoPriv");
		
		// Nascoste
		flgSelectedField        = new ListGridField("flgSelected");       flgSelectedField.setHidden(true);        flgSelectedField.setCanHide(false);          flgSelectedField.setCanSort(true);        flgSelectedField.setCanEdit(false);												
		idOggettoPrivField      = new ListGridField("idOggettoPriv");     idOggettoPrivField.setHidden(true);      idOggettoPrivField.setCanHide(false);        idOggettoPrivField.setCanSort(true);      idOggettoPrivField.setCanEdit(false);						
		tipoOggettoPrivField    = new ListGridField("tipoOggettoPriv");   tipoOggettoPrivField.setHidden(true);    tipoOggettoPrivField.setCanHide(false);      tipoOggettoPrivField.setCanSort(true);    tipoOggettoPrivField.setCanEdit(false);
		codiceOggettoPrivField  = new ListGridField("codiceOggettoPriv"); codiceOggettoPrivField.setHidden(true);  codiceOggettoPrivField.setCanHide(false);    codiceOggettoPrivField.setCanSort(true);  codiceOggettoPrivField.setCanEdit(false);		
		
		// Visibili
		denominazioneOggettoPrivField   = new ListGridField("denominazioneOggettoPriv",  "Denominazione");   denominazioneOggettoPrivField.setCanSort(true);    denominazioneOggettoPrivField.setCanEdit(false);		
		listaPrivilegiOggettoPrivField  = new ListGridField("listaPrivilegiOggettoPriv", "Privilegi");       listaPrivilegiOggettoPrivField.setCanSort(true);   listaPrivilegiOggettoPrivField.setCanEdit(isEditable());
		listaPrivilegiOggettoPrivField.setAttribute("custom", true);
		listaPrivilegiOggettoPrivField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return trasformaLista(record);
			}
		});
		
		// Opzioni Abilitazioni a tipi processi/iter atti
		listaPrivilegiValueMap.put("A", I18NUtil.getMessages().sub_profili_abilitazioni_TP_A_value());
		
		GWTRestDataSource listaPrivilegiValueMapDS = getLoadComboPrivilegiDatasource();
		SelectItem privilegiFunzioneItem = new SelectItem("privilegiFunzione");		
		privilegiFunzioneItem.setValueField("key");
		privilegiFunzioneItem.setDisplayField("value");
		privilegiFunzioneItem.setOptionDataSource(listaPrivilegiValueMapDS);
		privilegiFunzioneItem.setAutoFetchData(false);
		privilegiFunzioneItem.setAlwaysFetchMissingValues(true);
		privilegiFunzioneItem.setFetchMissingValues(true);
		privilegiFunzioneItem.setWidth(200);
		privilegiFunzioneItem.setMultiple(true);
		listaPrivilegiOggettoPrivField.setEditorProperties(privilegiFunzioneItem); 
		
		setGridFields(  // Nascoste
				        flgSelectedField,
		                idOggettoPrivField,
				        tipoOggettoPrivField,
				        codiceOggettoPrivField,
				        
				        // Visibili
				        denominazioneOggettoPrivField,
				        listaPrivilegiOggettoPrivField
		             );
	}
	
	@Override
	protected GridMultiToolStripButton[] getGridMultiselectButtons() {
		List<GridMultiToolStripButton> gridMultiselectButtons = new ArrayList<GridMultiToolStripButton>();

		if (cambiaPrivilegiMultiButton == null) {
			cambiaPrivilegiMultiButton = new GridMultiToolStripButton("archivio/archiviaConcludi.png", grid, "Cambia privilegi", false) {
					
					@Override
					public boolean toShow() {
						return true;
					}

					@Override
					public void doSomething() {
						apriCambiaPrivilegiSubProfiliPopup("cambiaPrivilegiMultiButton");
					}
				};
		}
		gridMultiselectButtons.add(cambiaPrivilegiMultiButton);
		
		if (cancellaPrivilegiMultiButton == null) {
			cancellaPrivilegiMultiButton = new GridMultiToolStripButton("buttons/delete2.png", grid, "Cancella privilegi", false) {
					
					@Override
					public boolean toShow() {
						return true;
					}

					@Override
					public void doSomething() {
						cancellaPrivilegiPrivilegiMulti();
					}
				};
		}
		gridMultiselectButtons.add(cancellaPrivilegiMultiButton);
		
		return gridMultiselectButtons.toArray(new GridMultiToolStripButton[gridMultiselectButtons.size()]);
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		return grid;
	}

	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();	
		ToolStripButton addButton = new ToolStripButton();   
		addButton.setIcon("buttons/add.png");   
		addButton.setIconSize(16);
		addButton.setPrefix("Aggiungi processo/procedimento");
		addButton.setPrompt("Aggiungi processo/procedimento");
		
		final String finalita = "sub_profili_lista_tipo_registrazione";
		final boolean flgSelezioneSingola = false;
		
		addButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickAddButton(finalita, flgSelezioneSingola);     	
			}   
		});  
		buttons.add(addButton);

		return buttons;
	}
	
	public void onClickAddButton(String finalita, Boolean flgSelezioneSingola) {
		LookupProcedimenti lookupProcedimenti = new LookupProcedimenti(finalita, flgSelezioneSingola);
		lookupProcedimenti.show();
	}
	
	
	public class LookupProcedimenti extends LookupProcedimentiPopup {

		public LookupProcedimenti(String finalita, Boolean flgSelezioneSingola) {
			super(finalita, flgSelezioneSingola);
		}

		@Override
		public void manageLookupBack(Record record) {
			setValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			setValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

		@Override
		public boolean  getShowNewButton() {
           return false;       
		}
		
		@Override
		public void afterManageOnCloseClick(boolean flgApriCambiaPrivilegiSubProfiliPopup) {
			if (flgApriCambiaPrivilegiSubProfiliPopup)
			    apriCambiaPrivilegiSubProfiliPopup("afterManageOnCloseClick");
		}
	}

	public void apriCambiaPrivilegiSubProfiliPopup(final String callFrom){
		
	    GWTRestDataSource listaPrivilegiValueMapDS = getLoadComboPrivilegiDatasource(); 
	
	    // Apro la popup per selezionare i privilegi
		new CambiaPrivilegiSubProfiliPopup("Cambia privilegi", listaPrivilegiValueMapDS) {
			
			@Override
			public void onClickOkButton(Record object, final DSCallback callback) {
				String listaPrivilegi = object.getAttribute("privilegiFunzione");	
				// Se provengo dalla lista con gli oggetti da importare 
				if (callFrom.equalsIgnoreCase("afterManageOnCloseClick")){
					// Aggiorno i privilegi su tutti i nuovi oggetti importati
					for (ListGridRecord record : grid.getRecords()) {
						String flgSelected = record.getAttribute("flgSelected");
						// Se e' un oggetto nuovo importato
						if (flgSelected !=null && flgSelected.equalsIgnoreCase("1")){
							if(listaPrivilegi != null && !"".equals(listaPrivilegi)) {
								record.setAttribute("listaPrivilegiOggettoPriv", ((String) listaPrivilegi).split(","));			
							} else {
								record.setAttribute("listaPrivilegiOggettoPriv",  new String[0]);	
							}	
						}
						grid.refreshRow(grid.getRecordIndex(record));
					}
					grid.deselectAllRecords();
				}
				
				// Se provengo dal click del bottone "cambiaPrivilegiMultiButton"
				if (callFrom.equalsIgnoreCase("cambiaPrivilegiMultiButton")){
					// Aggiorno i privilegi su tutti i record selezionati
					for (int i = 0; i < grid.getSelectedRecords().length; i++) {
						Record recordSelected = new Record();
						recordSelected = grid.getSelectedRecords()[i];
						if(listaPrivilegi != null && !"".equals(listaPrivilegi)) {
							recordSelected.setAttribute("listaPrivilegiOggettoPriv", ((String) listaPrivilegi).split(","));			
						} else {
							recordSelected.setAttribute("listaPrivilegiOggettoPriv",  new String[0]);	
						}
						grid.refreshRow(grid.getRecordIndex(recordSelected));
					}
					grid.deselectAllRecords();
				}					
				this.markForDestroy();
			}
		};
	}

	public void setValuesFromRecord(Record record) {
		
		// Prendo i dati restituiti dalla lookup
		Record recordToInsert = new Record();
		recordToInsert.setAttribute("flgSelected",              "1");
		recordToInsert.setAttribute("tipoOggettoPriv",          getTipoOggettoPriv());
		recordToInsert.setAttribute("idOggettoPriv",            record.getAttribute("id"));				
		recordToInsert.setAttribute("codiceOggettoPriv",        record.getAttribute("id"));
		recordToInsert.setAttribute("denominazioneOggettoPriv", record.getAttribute("nome"));
								
		// Aggiungo i dati a quelli gia' presenti
		instance.addData(recordToInsert);	
	}

	public String getFinalitaForLookupOrganigramma() {
		return null;
	}
	
	
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					
		if(isShowEditButtons()) {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
			if(isShowModifyButton()) {
				if(modifyButtonField == null) {
					modifyButtonField = buildModifyButtonField();					
				}
				buttonsList.add(modifyButtonField);
			}
			if(isShowDeleteButton()) {
				if(deleteButtonField == null) {
					deleteButtonField = buildDeleteButtonField();					
				}
				buttonsList.add(deleteButtonField);
			}
		} else {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
		}
		return buttonsList;
	}
	
	protected ControlListGridField buildDetailButtonField() {
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(!isShowEditButtons() || !isEditable()) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(!isShowEditButtons() || !isEditable()) {			
					return I18NUtil.getMessages().detailButton_prompt();
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(!isShowEditButtons() || !isEditable()) {
					event.cancel();
					onClickDetailButton(event.getRecord());		
				}
			}
		});		
		return detailButtonField;
	}
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/modify.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().modifyButton_prompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickModifyButton(event.getRecord());
				}
			}
		});		
		return modifyButtonField;
	}
	
	public ControlListGridField buildDeleteButtonField() {
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/delete.png");
				}
				return null;
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().deleteButton_prompt();	
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickDeleteButton(event.getRecord());
				}
			}
		});			 
		return deleteButtonField;
	}
	
	protected String buildImgButtonHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}

	protected String buildIconHtml(String src) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}
	
	public void onClickDetailButton(final ListGridRecord record) {		
	}
	
	public void onClickModifyButton(final ListGridRecord record) {		
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {
		int length = fields.length;
		length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons					
		ListaBean configLista = Layout.getListConfig(getNomeLista());	
		formattedFields = new ListGridField[length];
		controlFields = new ArrayList<String>();
		int count = 0;
		for (final ListGridField field : fields){	
			String fieldName = field.getName();			
			boolean skip = false;
			for(ControlListGridField buttonField : getButtonsFields()) {
				if(fieldName.equals(buttonField.getName())) {
					skip = true;
					break;
				}
			}						
			if(!skip) {
				try {				
					
					if("listaPrivilegiOggettoPriv".equalsIgnoreCase(field.getName())) {
						field.setCanEdit(isEditable());
					} else {
						field.setCanEdit(false);
					}
					
					if(field instanceof ControlListGridField) {
						controlFields.add(fieldName);
					}
					field.setAlign(Alignment.CENTER);					
					if(field.getTitle() != null && !"".equals(field.getTitle().trim())) {
						field.setPrompt(field.getTitle());					
					}							
					if(configLista.getColonneOrdinabili() != null) {
						if(configLista.getColonneOrdinabili().contains(fieldName)) {
							field.setCanSort(true);
						} else {
							field.setCanSort(false);
						}		
					}
					//Recupero il tipo relativo
					ListGridFieldType fieldType = field.getType();
					if (fieldType == null || fieldType.equals(ListGridFieldType.TEXT)) {
						if(field.getAttribute("custom") == null || !field.getAttributeAsBoolean("custom")) {
							field.setCellAlign(Alignment.LEFT);	
						}
					} else if (fieldType.equals(ListGridFieldType.INTEGER) 
							|| fieldType.equals(ListGridFieldType.BINARY) 
							|| fieldType.equals(ListGridFieldType.FLOAT)) {
						field.setCellAlign(Alignment.RIGHT);
					} else if (fieldType.equals(ListGridFieldType.DATE) 
							|| fieldType.equals(ListGridFieldType.TIME)) {
						field.setCellAlign(Alignment.CENTER);										
					} else if (fieldType.equals(ListGridFieldType.IMAGE) 
							|| fieldType.equals(ListGridFieldType.IMAGEFILE) 
							|| fieldType.equals(ListGridFieldType.LINK) 
							|| fieldType.equals(ListGridFieldType.ICON)) {
						field.setCellAlign(Alignment.CENTER);
					} else {
						field.setCellAlign(Alignment.LEFT);
					}
					if (fieldType == null || fieldType.equals(ListGridFieldType.TEXT)) {
						if(field.getAttribute("custom") == null || !field.getAttributeAsBoolean("custom")) {
							field.setCellFormatter(new CellFormatter() {							
								@Override
								public String format(Object value, ListGridRecord record, int rowNum,
										int colNum) {
									if(record != null) {
										record.setAttribute("realValue"+colNum, value);
									}
									if (value==null) return null;
									String lStringValue = value.toString();
									if (lStringValue.length()>Layout.getGenericConfig().getMaxValueLength()){
										return lStringValue.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
									} else return lStringValue;
								}
							});
							field.setHoverCustomizer(new HoverCustomizer() {
								@Override
								public String hoverHTML(Object value, ListGridRecord record, int rowNum,
										int colNum) {
									Object realValue = record != null ? record.getAttribute("realValue"+colNum) : null;								
									return (realValue != null) ? (String) realValue : (String) value;
								}
							});			
						}			
					} else if (fieldType.equals(ListGridFieldType.INTEGER)) {	
						field.setSortNormalizer(new SortNormalizer() {			
							@Override
							public Object normalize(ListGridRecord record, String fieldName) {
								String value = record.getAttribute(fieldName);
								return value != null && !"".equals(value) ? Long.parseLong(value.replace(".", "")) : 0;																						
							}
						});		
					} else if (fieldType.equals(ListGridFieldType.DATE)) {			
						LinkedHashMap<String, String> groupingModes = new LinkedHashMap<String, String>();
						groupingModes.put("date", I18NUtil.getMessages().groupingModePerGiorno_title()); 
						groupingModes.put("month", I18NUtil.getMessages().groupingModePerMese_title());
						groupingModes.put("year", I18NUtil.getMessages().groupingModePerAnno_title());         
						field.setGroupingModes(groupingModes);  															
						field.setGroupValueFunction(new GroupValueFunction() {             
							public Object getGroupValue(Object value, ListGridRecord record, ListGridField field, String fieldName, ListGrid grid) { 
								Date date = record != null ? DateUtil.parseInput(record.getAttributeAsString(fieldName)) : null;                  								
								DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
								if (date == null) return " ";								
								if(field.getGroupingMode() != null) {
									DateTimeFormat dateFormatYear = DateTimeFormat.getFormat("yyyy");	
									String year = dateFormatYear.format(date);
									if(field.getGroupingMode().equals("year")) {
										return year;
									} else if(field.getGroupingMode().equals("month")) {
										DateTimeFormat dateFormatMonth = DateTimeFormat.getFormat("MM");
										switch(Integer.parseInt(dateFormatMonth.format(date))) {
										case 1: return I18NUtil.getMessages().decodificaMese_1() + " " + year;
										case 2: return I18NUtil.getMessages().decodificaMese_2() + " " + year;
										case 3: return I18NUtil.getMessages().decodificaMese_3() + " " + year;
										case 4: return I18NUtil.getMessages().decodificaMese_4() + " " + year;
										case 5: return I18NUtil.getMessages().decodificaMese_5() + " " + year;
										case 6: return I18NUtil.getMessages().decodificaMese_6() + " " + year;
										case 7: return I18NUtil.getMessages().decodificaMese_7() + " " + year;
										case 8: return I18NUtil.getMessages().decodificaMese_8() + " " + year;
										case 9: return I18NUtil.getMessages().decodificaMese_9() + " " + year;
										case 10: return I18NUtil.getMessages().decodificaMese_10() + " " + year;
										case 11: return I18NUtil.getMessages().decodificaMese_11() + " " + year;
										case 12: return I18NUtil.getMessages().decodificaMese_12() + " " + year;
										}										
									} 	
								}
								return dateFormat.format(date); 								      																																			
							}
						}); 	
						field.setSortNormalizer(new SortNormalizer() {			
							@Override
							public Object normalize(ListGridRecord record, String fieldName) {
								return formatDateForSorting(record, fieldName);																									
							}
						});		
						field.setCellFormatter(new CellFormatter() {	
							@Override
							public String format(Object value, ListGridRecord record, int rowNum,
									int colNum) {
								return manageDateCellFormat(value, field, record);
							}
						});																										
					} else if (fieldType.equals(ListGridFieldType.ICON)){						
						final Map<String, String> valueHovers = field.getAttributeAsMap("valueHovers");
						if (valueHovers != null) {
							final ListGridField iconfield = field;
							field.setHoverCustomizer(new HoverCustomizer() {
								@Override
								public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
									if(record != null) return valueHovers.get(record.getAttribute(iconfield.getName()));
									else return (String) value;
								}
							});
						}
					}
					formattedFields[count] = field;					
				} catch (Exception e) {
					formattedFields[count] = field;
				}			
				count++;
			}
		}						
		if(getButtonsFields() != null) {
			for(int i = 0; i < getButtonsFields().size(); i++) {
				controlFields.add(getButtonsFields().get(i).getName());
				formattedFields[count] = getButtonsFields().get(i);
				count++;
			}					
		}						
		super.setGridFields(formattedFields);
	}	
	
	protected Object formatDateForSorting(ListGridRecord record, String fieldName) {
		String value = record != null ? record.getAttributeAsString(fieldName) : null;
		return value != null && !"".equals(value) ? DateUtil.parseInput(value) : null;
	}
	
	public String manageDateCellFormat(Object value, ListGridField field, ListGridRecord record) {
		if (value==null) return null;
		String lStringValue = value.toString();	
		if(field.getDateFormatter() != null && field.getDateFormatter().equals(DateDisplayFormat.TOEUROPEANSHORTDATE)) {									
			lStringValue = (lStringValue != null && !"".equals(lStringValue)) ? lStringValue.substring(0, 10) : "";
		}
		return lStringValue;
	}

	protected String getTipoOggettoPriv(){
		return "TP";
	}	
	
	protected static GWTRestDataSource getLoadComboPrivilegiDatasource() {
		GWTRestDataSource loadComboPrivilegiDS = new GWTRestDataSource("LoadComboPrivilegiTipoProcessoProcedimentoDataSource");
		return loadComboPrivilegiDS;
	}
	
	protected String trasformaLista(ListGridRecord record) {
		String ret = "";
		if (record.getAttribute("listaPrivilegiOggettoPriv") != null && !record.getAttribute("listaPrivilegiOggettoPriv").equalsIgnoreCase("")){
			String[] listaValoriString = record.getAttribute("listaPrivilegiOggettoPriv").split(",");
			List<String> listaValoriList  = Arrays.asList(listaValoriString);
			StringBuffer lStringBuffer = new StringBuffer();
			for (int i = 0; i < listaValoriList.size(); i++) {
				String codice = listaValoriList.get(i).trim();
				String desc = (String) listaPrivilegiValueMap.get(codice);			     
				if (i > 0)
					lStringBuffer.append(", ");
				
				lStringBuffer.append(desc);				
			}
			ret = lStringBuffer.toString();
		}
		return ret;
	}
	
	protected void cancellaPrivilegiPrivilegiMulti() {
		
		SC.ask("Vuoi cancellare i privilegi selezionati ? ", new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {				
				if(value) {
					RecordList listaSelectedRecords = new RecordList();
					for (int i = 0; i < grid.getSelectedRecords().length; i++) {
						listaSelectedRecords.add(grid.getSelectedRecords()[i]);
					}					
					// Cancello tutti i record selezionati
					for (int i = 0; i < listaSelectedRecords.getLength(); i++) {
						Record recordSelected = listaSelectedRecords.get(i);
						removeData(recordSelected);
					}
					grid.deselectAllRecords();												
				}
			}
		}); 
	}
}