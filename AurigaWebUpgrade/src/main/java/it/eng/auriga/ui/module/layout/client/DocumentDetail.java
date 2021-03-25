package it.eng.auriga.ui.module.layout.client;

import java.util.ArrayList;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.archivio.CondivisionePopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

public abstract class DocumentDetail extends CustomDetail{
	
	public DocumentDetail(String nomeEntita) {
		super(nomeEntita);
	}

	public DocumentDetail(String pNomeEntita, ValuesManager pValuesManager) {
		super(pNomeEntita, pValuesManager);
	}
	
	/**
	 * Metodo che ricarica i dati di dettaglio del documento (se si è in
	 * inserimento i campi vengono ripuliti e riportati alla situazione
	 * iniziale)
	 * 
	 */
	public void reload(final DSCallback callback) {

		if (mode != null && mode.equals("new")) {
			vm.clearErrors(true);
			editNewRecord();
		} else {
			Record lRecordToLoad = new Record(vm.getValues());
			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						editRecord(record);
						if(callback != null){
							callback.execute(response, null, new DSRequest());
						}
						
					} else {
						Layout.addMessage(
								new MessageBean("Si è verificato un errore durante il caricamento del dettaglio", "",
										MessageType.ERROR));
					}
				}
			});
		}
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Invio per conoscenza"
	 */
	public void clickCondivisioneStandard(Record destinatariPreferiti) {
		
		final Record detailRecord = new Record(vm.getValues());

		RecordList listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_DOC.getValue()));
		RecordList listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_DOC.getValue()));
		final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare		
		
		if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
			listaPreferiti.addList(listaUOPreferiti.toArray());
		}
		
		if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
			listaPreferiti.addList(listaUtentiPreferiti.toArray());
		}	
	
		String title = I18NUtil.getMessages().condivisioneWindow_title() + " del documento " + detailRecord.getAttribute("segnatura");
		
		final CondivisionePopup condivisionePopup = new CondivisionePopup("U", detailRecord, title) {
			
			@Override
			public RecordList getListaPreferiti() {
				return listaPreferiti;
			}
	
			@Override
			public void onClickOkButton(Record record, final DSCallback callback) {
				
				clickOkCondivisioneButton(detailRecord, record, callback);
			}
		};
		condivisionePopup.show();
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Freccia invio per conoscenza"
	 */
	public void clickFrecciaCondivisione(Record destinatariPreferiti) {
		
		final Record detailRecord = new Record(vm.getValues());
		
		final Menu creaCondivisione = new Menu(); 
		
		RecordList listaUOPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUOPreferite").get(AzioniRapide.INVIO_CC_DOC.getValue()));
		RecordList listaUtentiPreferiti = AurigaLayout.buildRecordListFromArrayListOfMap((ArrayList<Map>) destinatariPreferiti.getAttributeAsMap("mappaUtentiPreferiti").get(AzioniRapide.INVIO_CC_DOC.getValue()));

		boolean noMenuRapido = true; // identifica la presenza o meno di valori da visualizzare nel menu rapido di condivisione
		final RecordList listaPreferiti = new RecordList(); // contiene tutti i preferiti da visualizzare per la condivisione
		
		if(listaUOPreferiti != null && !listaUOPreferiti.isEmpty()){
			listaPreferiti.addList(listaUOPreferiti.toArray());
			noMenuRapido = false;
		}
		
		if(listaUtentiPreferiti != null && !listaUtentiPreferiti.isEmpty()){
			listaPreferiti.addList(listaUtentiPreferiti.toArray());
			noMenuRapido = false;
		}
		
		// Invio per conoscenza Standard
		MenuItem condivisioneMenuStandardItem = new MenuItem("Standard");
		condivisioneMenuStandardItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				String title = I18NUtil.getMessages().condivisioneWindow_title() + " del documento " + detailRecord.getAttribute("segnatura");
				
				final CondivisionePopup condivisionePopup = new CondivisionePopup("U", detailRecord, title) {

					@Override
					public RecordList getListaPreferiti() {
						return listaPreferiti;
					}
					
					@Override
					public void onClickOkButton(Record record, final DSCallback callback) {
						
						clickOkCondivisioneButton(detailRecord, record, callback);
					}
				};
				condivisionePopup.show();
			}
		});
		creaCondivisione.addItem(condivisioneMenuStandardItem);
		
		// Invio per conoscenza Rapida
		MenuItem condivisioneMenuRapidoItem = new MenuItem("Rapida");
		
		Boolean success = destinatariPreferiti.getAttributeAsBoolean("success");
		
		if(success != null && success == true) {
			
			Menu scelteRapide = new Menu();					
			
			if(noMenuRapido){
				condivisioneMenuRapidoItem.setEnabled(false);
			} else {
				buildMenuRapidoCondivisione(detailRecord, "U", listaPreferiti, scelteRapide);
				condivisioneMenuRapidoItem.setSubmenu(scelteRapide);
			}
			
		} else {
			condivisioneMenuRapidoItem.setEnabled(false);
		}
		creaCondivisione.addItem(condivisioneMenuRapidoItem);
		creaCondivisione.showContextMenu();
	}	
	
	protected void buildMenuRapidoCondivisione(final Record detailRecord, final String flgUdFolder, RecordList listaPreferiti, Menu scelteRapide) {
		
		for(int i=0; i < listaPreferiti.getLength();i++){
				
			Record currentRecord = listaPreferiti.get(i);
			final String idDestinatarioPreferito = currentRecord.getAttribute("idDestinatarioPreferito");
			final String tipoDestinatarioPreferito = currentRecord.getAttribute("tipoDestinatarioPreferito");
			final String descrizioneDestinatarioPreferito = currentRecord.getAttribute("descrizioneDestinatarioPreferito");
			
			MenuItem currentRapidoItem = new MenuItem(descrizioneDestinatarioPreferito); 
			currentRapidoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
				@Override
				public void onClick(MenuItemClickEvent event) {
					
					final RecordList listaUdFolder = new RecordList();
					if(detailRecord.getAttributeAsString("idUdFolder") == null ||
							"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
						detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
						detailRecord.setAttribute("flgUdFolder", flgUdFolder);
					}
					listaUdFolder.add(detailRecord);
					RecordList listaDestInvioCC = new RecordList();
					Record recordAssegnazioni = new Record();
					recordAssegnazioni.setAttribute("idUo", idDestinatarioPreferito);
					recordAssegnazioni.setAttribute("typeNodo", tipoDestinatarioPreferito);
					listaDestInvioCC.add(recordAssegnazioni);
					
					Record record = new Record();
					record.setAttribute("flgUdFolder", flgUdFolder);
					record.setAttribute("listaRecord", listaUdFolder);
					record.setAttribute("listaDestInvioCC", listaDestInvioCC);
					
					Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
					try {
						lGwtRestDataSource.addData(record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								operationCallback(response, detailRecord, "idUdFolder", "Invio per conoscenza effettuato con successo",
										"Si è verificato un errore durante l'invio per conoscenza!", new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {	
										reload(null);
									}
								});
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
				}
			});
			scelteRapide.addItem(currentRapidoItem);			
		}
	}
	
	private void clickOkCondivisioneButton(final Record detailRecord, Record record, final DSCallback callback) {
		
		final RecordList listaUdFolder = new RecordList();
		if(detailRecord.getAttributeAsString("idUdFolder") == null ||
				"".equals(detailRecord.getAttributeAsString("idUdFolder"))){
			detailRecord.setAttribute("idUdFolder", detailRecord.getAttribute("idUd"));
			detailRecord.setAttribute("flgUdFolder", "U");
		}
		listaUdFolder.add(detailRecord);
	
		record.setAttribute("flgUdFolder", "U");
		record.setAttribute("listaRecord", listaUdFolder);
		
		Layout.showWaitPopup("Invio per conoscenza in corso: potrebbe richiedere qualche secondo. Attendere…");
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CondivisioneDataSource");
		try {
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					operationCallback(response, detailRecord, "idUdFolder", "Invio per conoscenza effettuato con successo",
							"Si è verificato un errore durante l'invio per conoscenza!", new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {														
							if(callback != null){
								callback.execute(response, rawData, request);
							}
							reload(null);
						}
					});
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	public void operationCallback(DSResponse response, Record record, String pkField, String successMessage, String errorMessage, DSCallback callback) {
		
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			if (errorMessages != null) {
				if (errorMessages.get(record.getAttribute(pkField)) != null) {
					errorMsg = (String) errorMessages.get(record.getAttribute(pkField));
				} else {
					errorMsg = errorMessage != null ? errorMessage : "Si è verificato un errore durante l'operazione!";
				}
			}
			Layout.hideWaitPopup();
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}
	}

}
