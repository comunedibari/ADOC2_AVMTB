package it.eng.auriga.ui.module.layout.client.tipologieDocumentali;

import java.util.ArrayList;
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
import com.smartgwt.client.util.DateUtil;
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

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.auriga.ui.module.layout.client.sub_profili.SubProfiliPopup;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public class ListaUoGpPrivAbilitatiPubblicazioneItem extends GridItem {
	
	protected ListGridField idUoGpPrivField;
	protected ListGridField tipoField;
	protected ListGridField descTipoField;
	protected ListGridField nroLivelliUoField;
	protected ListGridField denominazioneUoGpPrivField;
	protected ListaUoGpPrivAbilitatiPubblicazioneItem instance = this;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	
	
	public ListaUoGpPrivAbilitatiPubblicazioneItem(String name) {
		
		super(name, "lista_uo_gp_abilitati_pubblicazione");
		
		setGridPkField("idUoGpPriv");
		
		// Nascoste
		idUoGpPrivField = new ListGridField("idUoGpPriv"); idUoGpPrivField.setHidden(true);idUoGpPrivField.setCanHide(false);idUoGpPrivField.setCanSort(true);idUoGpPrivField.setCanEdit(false);
		tipoField       = new ListGridField("tipo");       tipoField.setHidden(true);      tipoField.setCanHide(false);      tipoField.setCanSort(true);      tipoField.setCanEdit(false);
		
		// Visibili
		descTipoField              = new ListGridField("descTipo", "Tipo");                            descTipoField.setCanSort(true);              descTipoField.setCanEdit(false);
		nroLivelliUoField          = new ListGridField("nroLivelliUo", "Cod.");                        nroLivelliUoField.setCanSort(true);          nroLivelliUoField.setCanEdit(false);
		denominazioneUoGpPrivField = new ListGridField("denominazioneUoGpPriv", "Nome/Denominazione"); denominazioneUoGpPrivField.setCanSort(true); denominazioneUoGpPrivField.setCanEdit(false);
		
		setGridFields(  // Nascoste
				        idUoGpPrivField,
				        tipoField,
				        
				        // Visibili
				        descTipoField,
				        nroLivelliUoField,
				        denominazioneUoGpPrivField
		             );
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
		ToolStripButton addUoButton = new ToolStripButton();   
		addUoButton.setIcon("lookup/organigramma.png");   
		addUoButton.setIconSize(16);
		addUoButton.setPrefix("Aggiungi U.O.");
		addUoButton.setPrompt("Aggiungi U.O.");
		addUoButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickAddUoButton();     	
			}   
		});  
		buttons.add(addUoButton);
		
		ToolStripButton addGpPrivButton = new ToolStripButton();   
		addGpPrivButton.setIcon("buttons/sub_profili.png");   
		addGpPrivButton.setIconSize(16);
		addGpPrivButton.setPrefix("Aggiungi sub-profili");
		addGpPrivButton.setPrompt("Aggiungi sub-profili:");
		addGpPrivButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickAddGpPrivButton();
			}   
		});  
		buttons.add(addGpPrivButton);
			
		return buttons;
	}
	
	public void onClickAddUoButton() {
		AssegnazioneLookupOrganigramma lookupOrganigrammaPopup = new AssegnazioneLookupOrganigramma(null);
		lookupOrganigrammaPopup.show();
	}
	
	public void onClickAddGpPrivButton() {
		
		final SubProfiliPopup subProfiliPopup = new SubProfiliPopup() {
			@Override
			public void onClickOkButton(RecordList data, final DSCallback callback) {
				if (data!= null && data.getLength() > 0) {				
					for (int i = 0; i < data.getLength(); i++) {
						String key = data.get(i).getAttribute("key");
						String value = data.get(i).getAttribute("value");
						Record recordSubProfilo = new Record();						
						recordSubProfilo.setAttribute("idSubProfilo",         key);
						recordSubProfilo.setAttribute("denominazioneUoGpPriv", value);
						setSubProfiloValuesFromRecord(recordSubProfilo);						
					}
				}
				this.markForDestroy();
			}
		};
		subProfiliPopup.show();
	}
	
	public class AssegnazioneLookupOrganigramma extends LookupOrganigrammaPopup {

		public AssegnazioneLookupOrganigramma(Record record) {
			super(record, false, 0);
		}

		@Override
		public void manageLookupBack(Record record) {
			setUoValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			setUoValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

		@Override
		public String getFinalita() {
			return getFinalitaForLookupOrganigramma();
		}				
	}

	public void setSubProfiloValuesFromRecord(Record record) {
		// Prendo i dati restituiti dalla lookup
		Record recordToInsert = new Record();
		recordToInsert.setAttribute("idUoGpPriv", record.getAttribute("idSubProfilo"));
		String tipo = "GP";
		String descTipo = "Sub-profilo";
		recordToInsert.setAttribute("tipo",tipo);
		recordToInsert.setAttribute("descTipo",descTipo);
		recordToInsert.setAttribute("nroLivelliUo", "");
		recordToInsert.setAttribute("denominazioneUoGpPriv", record.getAttribute("denominazioneUoGpPriv"));
		
		// Aggiungo i dati a quelli gia' presenti
		instance.addData(recordToInsert);
	}
	
	public void setUoValuesFromRecord(Record record) {
		
		// Prendo i dati restituiti dalla lookup
		Record recordToInsert = new Record();
		recordToInsert.setAttribute("idUoGpPriv", record.getAttribute("idUo"));
		
		String tipo = record.getAttribute("tipo");
		int pos = tipo.indexOf("_");
		if (pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		
		String descTipo = "";
		if (tipo!=null && !tipo.equalsIgnoreCase("")){
			if (tipo.equalsIgnoreCase("GP")){
				descTipo = "Sub-profilo";
			}
			else if (tipo.equalsIgnoreCase("UO")){
				descTipo = "U.O.";
			}			
		}			
		recordToInsert.setAttribute("tipo",tipo);
		recordToInsert.setAttribute("descTipo",descTipo);
		recordToInsert.setAttribute("nroLivelliUo", record.getAttribute("codRapidoUo"));
		recordToInsert.setAttribute("denominazioneUoGpPriv", record.getAttribute("nome"));
		
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
//					field.setCanEdit(false);
					if(field instanceof ControlListGridField) {
						controlFields.add(fieldName);
					}
					field.setAlign(Alignment.CENTER);					
					if(field.getTitle() != null && !"".equals(field.getTitle().trim())) {
						field.setPrompt(field.getTitle());					
					}							
					if(configLista.getColonneOrdinabili() != null) {
//						if(configLista.getColonneDefault().contains(fieldName)) {
//							field.setHidden(false);
//						} else {
//							field.setHidden(true);
//						}
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
	
}