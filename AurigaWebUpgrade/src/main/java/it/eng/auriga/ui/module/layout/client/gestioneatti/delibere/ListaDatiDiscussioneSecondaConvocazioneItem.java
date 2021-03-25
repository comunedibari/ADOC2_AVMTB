package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class ListaDatiDiscussioneSecondaConvocazioneItem extends GridItem {
	
	protected ListGridField idUser;
	protected ListGridField denominazione;
	protected ListGridField incarico;
	protected ListGridField ruolo;
	protected ListGridField flgPresenza;
	
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	protected ControlListGridField detailButtonField;
	protected ControlListGridField previewButtonField;
	protected ControlListGridField downloadButtonField;

	public ListaDatiDiscussioneSecondaConvocazioneItem(String name,String tipologiaSessione, Boolean isEditabile) {
		super(name, "lista_dati_discussione_seconda_convocazione");
		
		setGridPkField("idUser");
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setEditable(isEditabile != null && isEditabile);		
		setCanEdit(isEditable());  
		
		idUser = new ListGridField("idUser");
		idUser.setHidden(true);
		idUser.setCanHide(false);
		idUser.setCanSort(true);
		idUser.setCanEdit(false);
		
		denominazione = new ListGridField("denominazione", "Cognome e nome");
		denominazione.setCanSort(true);
		denominazione.setCanEdit(false);
		
		incarico = new ListGridField("incarico", "Incarico");
		incarico.setCanSort(true);
		incarico.setCanEdit(false);
		
		ruolo = new ListGridField("ruolo", "Ruolo in seduta");
		ruolo.setCanSort(true);
		GWTRestDataSource tipoRuoloDS = new GWTRestDataSource("LoadComboTipoRuoloSedutaDataSource");
		tipoRuoloDS.addParam("tipo_sessione", tipologiaSessione);
		
		SelectItem tipoRuoloItem = new SelectItem("tipoRuolo");
		tipoRuoloItem.setValueField("key");
		tipoRuoloItem.setDisplayField("value");
		tipoRuoloItem.setOptionDataSource(tipoRuoloDS);
		tipoRuoloItem.setAutoFetchData(false);
		tipoRuoloItem.setAlwaysFetchMissingValues(true);
		tipoRuoloItem.setFetchMissingValues(true);
		tipoRuoloItem.setStartRow(false);
		tipoRuoloItem.setColSpan(7);
		tipoRuoloItem.setWidth(200);
		
		ruolo.setEditorType(tipoRuoloItem); 
		ruolo.setCanEdit(isEditable());
		
		RadioGroupItem radioPresenzaItem = new RadioGroupItem("radioPresenza");
		radioPresenzaItem.setShowTitle(false);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("1", "presente");
		lLinkedHashMap.put("0", "assente");
		radioPresenzaItem.setValueMap(lLinkedHashMap);
		radioPresenzaItem.setDefaultValue("1");
		radioPresenzaItem.setVertical(false);
		radioPresenzaItem.setWrap(false);
		
		flgPresenza = new ListGridField("flgPresenza", "Presenza");
		flgPresenza.setCanSort(true);
		flgPresenza.setEditorType(radioPresenzaItem);
		flgPresenza.setCanEdit(isEditable());
		flgPresenza.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgPresenza") != null && "1".equals(record.getAttribute("flgPresenza"))) {
					return "presente";
				} else {
					return "assente";
				}
			}
		});
		
		setGridFields(
				idUser,
				denominazione,
				incarico,
				ruolo,
				flgPresenza
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
	public void setGridFields(ListGridField... fields) {

		int length = fields.length;
		length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons			
		
		ListaBean configLista = Layout.getListConfig("lista_dati_discussione_seconda_convocazione");
		
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
					
					if("ruolo".equalsIgnoreCase(field.getName()) || "flgPresenza".equalsIgnoreCase(field.getName())) {
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
					
					//fare un controllo di non nullità sul bean non ha senso, Layout ritorna sempre un bean
					if(configLista.getColonneOrdinabili() != null) {
						//					if(configLista.getColonneDefault().contains(fieldName)) {
						//						field.setHidden(false);
						//					} else {
						//						field.setHidden(true);
						//					}
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
	
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					

		if(detailButtonField == null) {
			detailButtonField = buildDetailButtonField();					
		}
		if(previewButtonField == null) {
			previewButtonField = buildPreviewButtonField();					
		}
		if(downloadButtonField == null) {
			downloadButtonField = buildDownloadButtonField();					
		}
		buttonsList.add(detailButtonField);
		buttonsList.add(previewButtonField);
		buttonsList.add(downloadButtonField);
		
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
				
				return buildImgButtonHtml("buttons/detail.png");
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
		
				return I18NUtil.getMessages().detailButton_prompt();
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {

				onClickDetailButton(event.getRecord());		
			}
		});		
		return detailButtonField;
	}
	
	protected ControlListGridField buildPreviewButtonField() {
		
		ControlListGridField previewButtonField = new ControlListGridField("previewButton");  
		previewButtonField.setAttribute("custom", true);	
		previewButtonField.setShowHover(true);		
		previewButtonField.setCanReorder(false);
		previewButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				
				return buildImgButtonHtml("file/preview.png");
			}
		});
		previewButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
		
				return I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(); // probabilmente da cambiare
			}
		});		
		previewButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {

				onClickPreviewButton(event.getRecord());		
			}
		});		
		return previewButtonField;
	}

	protected ControlListGridField buildDownloadButtonField() {
	
		ControlListGridField downloadButtonField = new ControlListGridField("downloadButton");  
		downloadButtonField.setAttribute("custom", true);	
		downloadButtonField.setShowHover(true);		
		downloadButtonField.setCanReorder(false);
		downloadButtonField.setCellFormatter(new CellFormatter() {
		
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				
				return buildImgButtonHtml("file/download_manager.png");
			
			}
		});
		downloadButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				return I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(); // probabilmente da cambiare
			
			}
		});		
		downloadButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				onClickDownloadButton(event.getRecord());		
			
			}
		});		
		return downloadButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord record) {

	}
	
	public void onClickPreviewButton(final ListGridRecord record) {

	}
	
	public void onClickDownloadButton(final ListGridRecord record) {

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
	
	protected String buildImgButtonHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}
	
	protected String buildIconHtml(String src) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}

}