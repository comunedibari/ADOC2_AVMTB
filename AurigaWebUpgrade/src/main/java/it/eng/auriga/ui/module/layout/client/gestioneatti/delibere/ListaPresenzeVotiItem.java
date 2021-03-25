package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.ChangedEvent;
import com.smartgwt.client.widgets.grid.events.ChangedHandler;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class ListaPresenzeVotiItem extends GridItem {
	
	protected ListGridField idUser;
	protected ListGridField denominazione;
	protected ListGridField incarico;
	protected ListGridField ruolo;
	protected ListGridField flgPresenza;
	protected ListGridField voto;

	public ListaPresenzeVotiItem(String name,String tipologiaSessione) {
		
		super(name, "lista_presenze_voti");
		
		setGridPkField("idUser");
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(true);  
		
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
		ruolo.setCanEdit(true);
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
		tipoRuoloItem.setAllowEmptyValue(true);
		ruolo.setEditorProperties(tipoRuoloItem); 
		ruolo.setEmptyCellValue("<i>Seleziona...</i>");
	
//		LinkedHashMap<String, String> flgPresenzaValueMap = new LinkedHashMap<String, String>();
//		flgPresenzaValueMap.put("1", "presente");
//		flgPresenzaValueMap.put("0", "assente");		
//		flgPresenza = new ListGridField("flgPresenza", "Presenza");
//		flgPresenza.setCanSort(true);
//		flgPresenza.setCanEdit(true);
//		flgPresenza.setValueMap(flgPresenzaValueMap);
//		RadioGroupItem radioPresenzaItem = new RadioGroupItem("radioPresenza");
//		radioPresenzaItem.setShowTitle(false);
//		radioPresenzaItem.setValueMap(flgPresenzaValueMap);
//		radioPresenzaItem.setDefaultValue("1");
//		radioPresenzaItem.setVertical(true);
//		radioPresenzaItem.setWrap(false);		
//		flgPresenza.setEditorProperties(radioPresenzaItem);

		flgPresenza = new ListGridField("flgPresenza", "Presenza");
		flgPresenza.setAttribute("custom", true);
		flgPresenza.setCanSort(true);
		flgPresenza.setCanEdit(true);
		flgPresenza.setCellFormatter(new CellFormatter() {
		
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				LinkedHashMap<String, String> flgPresenzaValueMap = getFlgPresenzaValueMap();
				if(flgPresenzaValueMap != null && flgPresenzaValueMap.containsKey(record.getAttribute("flgPresenza"))) {
					return flgPresenzaValueMap.get(record.getAttribute("flgPresenza"));
				}
				return null;								
			}
		});
		flgPresenza.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				Record currentRecord = grid.getRecord(event.getRowNum());
				if(event.getValue() != null && "0".equals(event.getValue())) {
					final Record newRecord = new Record(currentRecord.getJsObj());
					newRecord.setAttribute("voto", "senza_voto");
					updateData(newRecord, currentRecord);
					grid.refreshRow(event.getRowNum());
				}
			}
		});
		flgPresenza.setEmptyCellValue("<i>Seleziona...</i>");		
								
//		LinkedHashMap<String, String> votoValueMap = new LinkedHashMap<String, String>();
//		votoValueMap.put("astenuto", "astenuto");
//		votoValueMap.put("favorevole", "favorevole");
//		votoValueMap.put("contrario", "contrario");
//		votoValueMap.put("senzavoto", "senza voto");		
//		voto = new ListGridField("voto", "Voto");
//		voto.setCanSort(true);
//		voto.setCanEdit(true);
//		voto.setValueMap(votoValueMap);
//		RadioGroupItem radioVotoItem = new RadioGroupItem("radioVoto");
//		radioVotoItem.setShowTitle(false);
//		radioVotoItem.setValueMap(votoValueMap);
//		radioVotoItem.setDefaultValue("astenuto");
//		radioVotoItem.setVertical(true);
//		radioVotoItem.setWrap(false);
//		voto.setEditorProperties(radioVotoItem);
		
		voto = new ListGridField("voto", "Voto");
		voto.setAttribute("custom", true);
		voto.setCanSort(true);
		voto.setCanEdit(true);
		voto.setCellFormatter(new CellFormatter() {
		
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				LinkedHashMap<String, String> votoValueMap = getVotoValueMap();
				if(votoValueMap != null && votoValueMap.containsKey(record.getAttribute("voto"))) {
					return votoValueMap.get(record.getAttribute("voto"));
				}
				return null;							
			}
		});
		voto.setEmptyCellValue("<i>Seleziona...</i>");
		
		setGridFields(
				idUser,
				denominazione,
				incarico,
				ruolo,
				flgPresenza,
				voto
		);
	}
	
	public LinkedHashMap<String, String> getFlgPresenzaValueMap() {
		LinkedHashMap<String, String> flgPresenzaValueMap = new LinkedHashMap<String, String>();
		flgPresenzaValueMap.put("1", "presente");
		flgPresenzaValueMap.put("0", "assente");	
		return flgPresenzaValueMap;
	}
	
	public LinkedHashMap<String, String> getVotoValueMap() {
		LinkedHashMap<String, String> votoValueMap = new LinkedHashMap<String, String>();
		votoValueMap.put("astenuto", "astenuto");
		votoValueMap.put("favorevole", "favorevole");
		votoValueMap.put("contrario", "contrario");
		votoValueMap.put("senza_voto", "senza voto");
		return votoValueMap;
	}
	
	@Override
	public ListGrid buildGrid() {	
		
		ListGrid grid = super.buildGrid();		
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		grid.setEditByCell(true);
//		grid.setAlwaysShowEditors(true);  
//		grid.setAutoSaveEdits(true);
	    grid.setEditorCustomizer(new ListGridEditorCustomizer() {
			
			@Override
			public FormItem getEditor(ListGridEditorContext context) {
				if(context.getEditField().getName().equals("flgPresenza")) {
					RadioGroupItem radioPresenzaItem = new RadioGroupItem("radioPresenza");
					radioPresenzaItem.setShowTitle(false);
					radioPresenzaItem.setValueMap(getFlgPresenzaValueMap());
					radioPresenzaItem.setVertical(true);
					radioPresenzaItem.setWrap(false);
					radioPresenzaItem.setDefaultValue("0");
					return radioPresenzaItem; 
				} else if(context.getEditField().getName().equals("voto")) {
					RadioGroupItem radioVotoItem = new RadioGroupItem("radioVoto");
					radioVotoItem.setShowTitle(false);
					radioVotoItem.setVertical(true);
					radioVotoItem.setWrap(false);						
					String flgPresenza = context.getEditedRecord().getAttribute("flgPresenza");
					if(flgPresenza != null && "1".equalsIgnoreCase(flgPresenza)) {
						radioVotoItem.setValueMap(getVotoValueMap());
						radioVotoItem.setDefaultValue("astenuto");						
					} else {
						LinkedHashMap<String, String> votoValueMap = new LinkedHashMap<String, String>();
						votoValueMap.put("senza_voto", "senza voto");						
						radioVotoItem.setValueMap(votoValueMap);
						radioVotoItem.setDefaultValue("senza_voto");
					}
					return radioVotoItem; 
				}		
				return null;				
			}
		});	
		return grid;
	}
	
}