package it.eng.auriga.ui.module.layout.client.gestioneatti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.FolderProcedimentoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.osservazioniNotifiche.OsservazioniNotificheWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.MostraIterSvoltoModalWindows;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.CustomTaskButton;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/**
 * Accentra il codice comune tra "Atti in lavorazione" ed "Atti personali"
 * 
 * @author massimo malvestio
 *
 */
public class AttiList extends CustomList {

	protected ControlListGridField folderButtonField;
	protected ControlListGridField folderDetailButtonField;
	protected ControlListGridField iterSvoltoButtonField;
	protected ControlListGridField anteprimaPropostaButtonField;
	protected ControlListGridField anteprimaVRCButtonField;
	protected ControlListGridField anteprimaAppendiceContabileButtonField;
	protected ControlListGridField riepilogoFirmeVistiButtonField;
	protected ControlListGridField anteprimaNoteCommentiButtonField;
	
	public AttiList(String nomeEntita) {
		super(nomeEntita);
	}

	protected void folderButtonClick(Record record) {
		String idFolder = record.getAttribute("idUdFolder");
		if (idFolder != null && !idFolder.equalsIgnoreCase("")) {
			record.setAttribute("estremiProcedimento", record.getAttribute("numeroProposta"));
			FolderProcedimentoPopup folderProcedimentoPopup = new FolderProcedimentoPopup(record);
			folderProcedimentoPopup.show();
		}
	}

	protected void folderDetailButtonClick(final Record record) {
		
		List<CustomTaskButton> customButtons = buildCustomButtonsDettaglioPratica(record);

		AurigaLayout.apriDettaglioPratica(record.getAttribute("idProcedimento"), record.getAttribute("numeroProposta"), customButtons, new BooleanCallback() {

			@Override
			public void execute(Boolean isSaved) {
				if (getLayout() != null) {
					if(isSaved != null && isSaved) {
						getLayout().reloadListAndSetCurrentRecord(record);
					}
				}
			}
		});
	}

	protected List<CustomTaskButton> buildCustomButtonsDettaglioPratica(final Record record) {
		List<CustomTaskButton> customButtons = new ArrayList<CustomTaskButton>();
		if(AttiLayout.isAttivoSmistamentoAtti()) {		
			CustomTaskButton buttonSmistaAtto = new CustomTaskButton("Smista", "pratiche/task/buttons/smista_atto.png") {

				public boolean isToShow(Record recordEvento) {
					Boolean flgAttivaSmistamento = recordEvento != null ? recordEvento.getAttributeAsBoolean("flgAttivaSmistamento") : null;
					return flgAttivaSmistamento != null && flgAttivaSmistamento;
				}
			};
			buttonSmistaAtto.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					smistaPropAttoButtonClick(record);
				}
			});
			customButtons.add(buttonSmistaAtto);
		}
		return customButtons;
	}

	@Override
	public void manageContextClick(final Record record) {
		if (record != null) {
			showRowContextMenu(getRecord(getRecordIndex(record)), null);
		}
	}

	public void showRowContextMenu(final ListGridRecord record, final Menu navigationContextMenu) {
		
		final Menu contextMenu = new Menu();
		
		// Crea la voce APRI FOLDER
		MenuItem folderMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " cartella", "archivio/flgUdFolder/F.png");
		folderMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				folderButtonClick(record);
			}
		});
		contextMenu.addItem(folderMenuItem);

		// Crea la voce DETTAGLIO FOLDER
		MenuItem folderDetailMenuItem = new MenuItem("Visualizza dettaglio proposta", "buttons/detail.png");
		folderDetailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				folderDetailButtonClick(record);
			}
		});
		contextMenu.addItem(folderDetailMenuItem);

		// Crea la voce ITER SVOLTO
		MenuItem mostraIterSvoltoMenuItem = new MenuItem(I18NUtil.getMessages().atti_inlavorazione_iter_svolto_menu_title(), "buttons/iter_svolto.png");
		mostraIterSvoltoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				mostraIterSvoltoClick(record);
			}
		});
		contextMenu.addItem(mostraIterSvoltoMenuItem);	
				
		if (record.getAttribute("unitaDocumentariaId") != null && !"".equals(record.getAttribute("unitaDocumentariaId"))) {
			GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", record.getAttribute("unitaDocumentariaId"));
			lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						final Record detailRecord = response.getData()[0];						
						final Menu altreOpMenu = createUdAltreOpMenu(record, detailRecord);
						if(altreOpMenu.getItems() != null) {
							int initialPos = contextMenu.getItems() != null ? contextMenu.getItems().length : 0;
							for (int i = 0; i < altreOpMenu.getItems().length; i++) {
								contextMenu.addItem(altreOpMenu.getItems()[i], i + initialPos);
							}	
						}
						contextMenu.showContextMenu();
					}
				}
			});
		} else {
			contextMenu.showContextMenu();
		}
	}
		
	public Menu createUdAltreOpMenu(final ListGridRecord listRecord, final Record detailRecord) {	
		
		final Menu contextMenu = new Menu();
		
		MenuItem visualizzaFileMenuItem = buildVisualizzaFileMenuItem(detailRecord);
		if(visualizzaFileMenuItem != null) {
			contextMenu.addItem(visualizzaFileMenuItem);
		}
		
		MenuItem scaricaFileCompletiXAttiMenuItem = buildScaricaFileCompletiXAttiMenuItem(listRecord, detailRecord);
		if(scaricaFileCompletiXAttiMenuItem != null) {
			contextMenu.addItem(scaricaFileCompletiXAttiMenuItem);
		}
		
		MenuItem smistaPropAttoMenuItem = new MenuItem("Smista", "archivio/assegna.png");
		smistaPropAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				smistaPropAttoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToSmistaPropAtto(detailRecord)) {			
			contextMenu.addItem(smistaPropAttoMenuItem);
		}
		
		MenuItem rilascioVistoMenuItem = new MenuItem("Rilascia visto", "attiInLavorazione/azioni/rilascioVisto.png");
		rilascioVistoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				rilascioVistoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToRilascioVisto(detailRecord)) {			
			contextMenu.addItem(rilascioVistoMenuItem);
		}
		
		MenuItem rifiutoVisto = new MenuItem("Rifiuta visto", "attiInLavorazione/azioni/rifiutoVisto.png");
		rifiutoVisto.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				rifiutoVistoButtonClick(listRecord);
			}
		});
		if(isRecordAbilToRifiutoVisto(detailRecord)) {			
			contextMenu.addItem(rifiutoVisto);
		}
		
		MenuItem mandaLibroFirmaMenuItem = new MenuItem("Manda al libro firma", "attiInLavorazione/mandaLibroFirma.png");
		mandaLibroFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				mandaLibroFirmaMenuItemButtonClick(listRecord);
			}
		});
		if(isRecordAbilInviaALibroFirmaVistoRegCont(detailRecord)) {
			contextMenu.addItem(mandaLibroFirmaMenuItem);	
		}
		
		MenuItem togliLibroFirmaMenuItem = new MenuItem("Togli dal libro firma", "attiInLavorazione/togliLibroFirma.png");
		togliLibroFirmaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				togliLibroFirmaButtonClick(listRecord);
			}
		});
		if(isRecordAbilTogliDaLibroFirmaVistoRegCont(detailRecord)) {
			contextMenu.addItem(togliLibroFirmaMenuItem);	
		}
		
		MenuItem segnaRicontrollareMenuItem = new MenuItem("Segna da ricontrollare", "archivio/flgPresaInCarico/da_fare.png");
		segnaRicontrollareMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				segnaRicontrollareButtonClick(listRecord);
			}
		});
		if(isRecordAbilImpostaStatoAlVistoRegCont(detailRecord)) {
			contextMenu.addItem(segnaRicontrollareMenuItem);	
		}
		
		MenuItem osservazioniNotificheMenuItem = new MenuItem(I18NUtil.getMessages().osservazioniNotifiche_menu_apri_title(), "osservazioni_notifiche.png");
		osservazioniNotificheMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				osservazioniNotificheButtonClick(listRecord);
			}
		});
		if(isRecordAbilToOsservazioniNotifiche(detailRecord)) {
			contextMenu.addItem(osservazioniNotificheMenuItem);	
		}
		
		String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");				
		MenuItem eventoAMCMenuItem = new MenuItem(lSistAMC != null && !"".equals(lSistAMC) ? "Evento " + lSistAMC : "Evento AMC", "buttons/gear.png");
		eventoAMCMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				eventoAMCButtonClick(listRecord);
			}
		});
		if(isRecordAbilToEventoAMC(detailRecord)) {
			contextMenu.addItem(eventoAMCMenuItem);
		}
							
		return contextMenu;
	}
	
	protected MenuItem buildVisualizzaFileMenuItem(final Record detailRecord) {
		
		Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
		
		Menu fileAssociatiSubMenu = new Menu();
		fileAssociatiSubMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");
		
		//File primario senza omissis
		if ((detailRecord.getAttributeAsString("uriFilePrimario") != null
				&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") == null) || "".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioIntegraleMenuItem, true);
			fileAssociatiSubMenu.addItem(filePrimarioIntegraleMenuItem);			
		}
			
		//File primario con versione omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario") != null
				&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			//File primario integrale
			MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario (vers. integrale) - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioIntegraleMenuItem, true);
			fileAssociatiSubMenu.addItem(filePrimarioIntegraleMenuItem);
			
			//File primario omissis
			MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " +filePrimarioOmissis.getAttributeAsString("nomeFile"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioOmissisMenuItem, false);
			fileAssociatiSubMenu.addItem(filePrimarioOmissisMenuItem);
		}
		
		//File primario solo omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario") == null
				|| "".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " + filePrimarioOmissis.getAttributeAsString("nomeFile"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioOmissisMenuItem, false);
			fileAssociatiSubMenu.addItem(filePrimarioOmissisMenuItem);
		}
		
		//Aggiungo al menu gli allegati
		RecordList listaAllegati = detailRecord.getAttributeAsRecordList("listaAllegati");
		
		if (listaAllegati != null) {
			for (int n = 0; n < listaAllegati.getLength(); n++) {
				final int nroAllegato = n;
				final Record allegatoRecord = listaAllegati.get(n);
				
				boolean flgParteDispositivo = allegatoRecord.getAttribute("flgParteDispositivo") != null && "true".equals(allegatoRecord.getAttribute("flgParteDispositivo"));					
				
				//Allegato senza omissis
				if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					if(flgParteDispositivo) {
						fileAllegatoIntegraleMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato, true);
					fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
				}
				
				//Entrambi versioni di allegati
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					//Allegato integrale
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. integrale) - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					if(flgParteDispositivo) {
						fileAllegatoIntegraleMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato,true);
					fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
					
					//Alegato omissis
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					if(flgParteDispositivo) {
						fileAllegatoOmissisMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
					fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
				}
				
				//Allegato solo omissis
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					if(flgParteDispositivo) {
						fileAllegatoOmissisMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
					fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
				}
			}
		}
		
		if(fileAssociatiSubMenu != null && fileAssociatiSubMenu.getItems() != null && fileAssociatiSubMenu.getItems().length > 0) {
			MenuItem visualizzaFileMenuItem = new MenuItem("File", "archivio/file.png");		
			visualizzaFileMenuItem.setSubmenu(fileAssociatiSubMenu);
			return visualizzaFileMenuItem;
		}
		
		return null;
	}
	
	private void buildFilePrimarioMenuItem(final Record detailRecord, MenuItem filePrimarioMenuItem, final boolean fileIntegrale) {
		
		Menu operazioniFilePrimarioSubmenu = new Menu();
		
		InfoFileRecord lInfoFileRecord;
		//file primario integrale
		if(fileIntegrale){
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFile"));
		}
		//file primario omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsRecord("filePrimarioOmissis").getAttributeAsObject("infoFile"));
		}
		
		MenuItem visualizzaFilePrimarioMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				if(fileIntegrale) {
					String idUd = detailRecord.getAttributeAsString("idUd");
					String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
					String display = detailRecord.getAttributeAsString("nomeFilePrimario");
					String uri = detailRecord.getAttributeAsString("uriFilePrimario");
					String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
					Object infoFile = detailRecord.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
				else {
					String idUd = detailRecord.getAttributeAsString("idUd");
					final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
					String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
					String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
					String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
					String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
					Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
			}
		});
		visualizzaFilePrimarioMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFilePrimarioSubmenu.addItem(visualizzaFilePrimarioMenuItem);
		
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFilePrimarioMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(),
					"file/previewEdit.png");
			visualizzaEditFilePrimarioMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							if(fileIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
								String display = detailRecord.getAttributeAsString("nomeFilePrimario");
								String uri = detailRecord.getAttributeAsString("uriFilePrimario");
								String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
								Object infoFile = detailRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
								String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
								String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
								String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
								String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
								Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFilePrimarioMenuItem
					.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFilePrimarioSubmenu.addItem(visualizzaEditFilePrimarioMenuItem);
		}
		
		MenuItem scaricaFilePrimarioMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFilePrimarioSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						Object infoFile = detailRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFilePrimarioSubMenu.setItems(firmato, sbustato);
			scaricaFilePrimarioMenuItem.setSubmenu(scaricaFilePrimarioSubMenu);
		} else {
			scaricaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
		}
		operazioniFilePrimarioSubmenu.addItem(scaricaFilePrimarioMenuItem);

		filePrimarioMenuItem.setSubmenu(operazioniFilePrimarioSubmenu);
	}
		
	private void buildAllegatiMenuItem(final Record detailRecord, final Record allegatoRecord, MenuItem fileAllegatoMenuItem,final int nroAllegato, final boolean allegatoIntegrale) {
		
		Menu operazioniFileAllegatoSubmenu = new Menu();
		
		InfoFileRecord lInfoFileRecord;
		//se è un allegato integrale
		if(allegatoIntegrale) {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
		}
		//versione con omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
		}
		
		MenuItem visualizzaFileAllegatoMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFileAllegatoMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						//se è un allegato integrale
						if(allegatoIntegrale) {
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
							String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
							String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
						//versione con omissis
						else{
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
							String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
							String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
					}
				});
		visualizzaFileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFileAllegatoSubmenu.addItem(visualizzaFileAllegatoMenuItem);
		
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFileAllegatoMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(), "file/previewEdit.png");
			visualizzaEditFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							// se è un allegato integrale
							if (allegatoIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							// versione con omissis
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFileAllegatoSubmenu.addItem(visualizzaEditFileAllegatoMenuItem);
		}
		
		MenuItem scaricaFileAllegatoMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFileAllegatoSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFileAllegatoSubMenu.setItems(firmato, sbustato);
			scaricaFileAllegatoMenuItem.setSubmenu(scaricaFileAllegatoSubMenu);
		} else {
			scaricaFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							if(allegatoIntegrale) {
								//se è un allegato integrale
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
							//versione con omissis
							else{
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
						}
					});
		}
		operazioniFileAllegatoSubmenu.addItem(scaricaFileAllegatoMenuItem);

		fileAllegatoMenuItem.setSubmenu(operazioniFileAllegatoSubmenu);
	}
		
	protected MenuItem buildScaricaFileCompletiXAttiMenuItem(final ListGridRecord listRecord, final Record detailRecord) {
		
		if(AurigaLayout.isPrivilegioAttivo("ATT/SDC")) {
			RecordList listaFileCompletiXAtti = detailRecord.getAttributeAsRecordList("listaFileCompletiXAtti");
			if(listaFileCompletiXAtti != null && !listaFileCompletiXAtti.isEmpty()) {
				
				MenuItem scaricaFileCompletiXAttiMenuItem = new MenuItem("Scarica documentazione completa", "buttons/export.png");
				scaricaFileCompletiXAttiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
					@Override
					public void onClick(MenuItemClickEvent event) {
						
						// devo fare uno zip con tutti i file: testo, allegati, foglio riepilogo, emandamenti e pareri emedamento
						final GWTRestDataSource datasource = new GWTRestDataSource("ProtocolloDataSource");
						if(detailRecord.getAttribute("segnatura") != null && !"".equals(detailRecord.getAttribute("segnatura"))) {
							datasource.extraparam.put("segnatura", detailRecord.getAttribute("segnatura"));
						} else {
							datasource.extraparam.put("segnatura", listRecord.getAttribute("numeroProposta"));
						}
						datasource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
						datasource.extraparam.put("operazione", "scaricaCompletiXAtti");
						datasource.setForceToShowPrompt(false);
						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_list_downloadDocsZip_wait(), "", MessageType.WARNING));
						datasource.executecustom("generateDocsZip", detailRecord, new DSCallback() {
		
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {		
									Record result = response.getData()[0];
									String message = result.getAttribute("message");
									if (message != null) {
										Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
									} else if (result.getAttribute("errorCode") == null || result.getAttribute("errorCode").isEmpty()) {
										String zipUri = response.getData()[0].getAttribute("storageZipRemoteUri");
										String zipName = response.getData()[0].getAttribute("zipName");							
										scaricaFile(listRecord.getAttribute("idUdFolder"), "", zipName, zipUri, zipUri);															
									}
								}
							}
						});
					}
				});
				return scaricaFileCompletiXAttiMenuItem;
			}
		}		
		return null;
	}
	
	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
				}
			});
		}
	}

	public void visualizzaFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		PreviewControl.switchPreview(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
	}

	public void visualizzaEditFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd
				,rotazioneTimbroPref,posizioneTimbroPref);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lOpzioniTimbro) {
				boolean timbroEnabled = detailRecord != null && detailRecord.getAttribute("flgTipoProv") != null
						&& !"".equals(detailRecord.getAttribute("flgTipoProv")) && detailRecord.getAttribute("idUd") != null
						&& !"".equals(detailRecord.getAttribute("idUd"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, Boolean.valueOf(remoteUri), timbroEnabled, lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {

					}
				};
				previewDocWindow.show();
			}
		});
	}

	public void scaricaFile(String idUd, String idDoc, String display, String uri, String remoteUri) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void scaricaFileSbustato(String idUd, String idDoc, String display, String uri, String remoteUri, Object infoFile) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	}
	
	protected boolean isRecordAbilToAnteprimaVRC(ListGridRecord record) {		
		String uriVistoRegContabile = record.getAttribute("uriVistoRegContabile");
		return AurigaLayout.isAttivaNuovaPropostaAtto2() && (uriVistoRegContabile != null && !"".equals(uriVistoRegContabile));		
	}
	
	protected boolean isRecordAbilToAnteprimaProposta(ListGridRecord record) {		
		String uriDispositivoAtto = record.getAttribute("uriDispositivoAtto");
		String idModDispositivo = record.getAttribute("idModDispositivo");
		return (AurigaLayout.isAttivaNuovaPropostaAtto2() && idModDispositivo != null && !"".equalsIgnoreCase(idModDispositivo)) || (uriDispositivoAtto != null && !"".equals(uriDispositivoAtto));
	}
	
	protected boolean isRecordAbilToAnteprimaAppendiceContabile(ListGridRecord record) {	
		String idModContabile = record.getAttribute("idModContabile");
		return AurigaLayout.isAttivaNuovaPropostaAtto2() && idModContabile != null && !"".equalsIgnoreCase(idModContabile);
	}
	
	protected boolean isRecordAbilToRiepilogoFirmeVisti(ListGridRecord record) {
		if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
			return (record.getAttributeAsString("uriRiepilogoFirmeVisti") != null && !"".equals(record.getAttributeAsString("uriRiepilogoFirmeVisti"))) ||
					(record.getAttributeAsString("idDocRiepilogoFirmeVisti") != null && !"".equals(record.getAttributeAsString("idDocRiepilogoFirmeVisti")));			
		}
		return false;
	}
	
	protected boolean isRecordAbilToAnteprimaNoteCommenti(ListGridRecord record) {
		return false; //TODO
	}
			
	protected boolean isRecordAbilToSmistaPropAtto(Record record) {		
		return record.getAttributeAsBoolean("abilSmistaPropAtto");		
	}
	
	protected boolean isRecordAbilToOsservazioniNotifiche(Record record) {
		return record.getAttributeAsBoolean("abilOsservazioniNotifiche");
	}
	
	protected boolean isRecordAbilInviaALibroFirmaVistoRegCont(Record record) {
		return record.getAttributeAsBoolean("abilInviaALibroFirmaVistoRegCont");
	}
	
	protected boolean isRecordAbilTogliDaLibroFirmaVistoRegCont(Record record) {
		return record.getAttributeAsBoolean("abilTogliaDaLibroFirmaVistoRegCont");
	}
	
	protected boolean isRecordAbilImpostaStatoAlVistoRegCont(Record record) {
		return record.getAttributeAsBoolean("abilImpostaStatoAlVistoRegCont");
	}
		
	protected boolean isRecordAbilToRilascioVisto(Record record) {
		return record.getAttributeAsBoolean("abilRilascioVisto");
	}
	
	protected boolean isRecordAbilToRifiutoVisto(Record record) {
		return record.getAttributeAsBoolean("abilRifiutoVisto");
	}
	
	protected boolean isRecordAbilToEventoAMC(Record record) {
		String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");		
		return lSistAMC != null && !"".equals(lSistAMC) && AurigaLayout.isPrivilegioAttivo("ATT/AMC");
	}
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {

		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();

		if (!isDisableRecordComponent()) {

			if (buttonsField == null) {

				buttonsField = new ControlListGridField("buttons");
				buttonsField.setWidth(getButtonsFieldWidth());
				buttonsField.setCanReorder(Boolean.FALSE);
				buttonsField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {

						String rowClickEventSource = event.getRecord().getAttribute("rowClickEventSource");
						if (rowClickEventSource == null || "".equals(rowClickEventSource)) {
							event.cancel();
						}
					}
				});
			}
			buttonsList.add(buttonsField);
			
		} else {
			
			if (folderButtonField == null) {
				folderButtonField = new ControlListGridField("folderButton");
				folderButtonField.setAttribute("custom", true);
				folderButtonField.setShowHover(true);
				folderButtonField.setCanReorder(false);
				folderButtonField.setCellFormatter(new CellFormatter() {

					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
						return buildImgButtonHtml("archivio/flgUdFolder/F.png");
					}
				});
				folderButtonField.setHoverCustomizer(new HoverCustomizer() {

					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						return I18NUtil.getMessages().archivio_list_iconaFolderButton_prompt() + " cartella";
					}
				});
				folderButtonField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						folderButtonClick(record);
					}
				});
			}
			
			buttonsList.add(folderButtonField);
			
			if (folderDetailButtonField == null) {
				folderDetailButtonField = new ControlListGridField("folderDetailButton");
				folderDetailButtonField.setAttribute("custom", Boolean.TRUE);
				folderDetailButtonField.setShowHover(Boolean.TRUE);
				folderDetailButtonField.setCanReorder(Boolean.FALSE);
				folderDetailButtonField.setCellFormatter(new CellFormatter() {

					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
						return buildImgButtonHtml("buttons/detail.png");
					}
				});
				folderDetailButtonField.setHoverCustomizer(new HoverCustomizer() {

					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						return "Visualizza dettaglio proposta";
					}
				});
				folderDetailButtonField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						folderDetailButtonClick(record);
					}
				});
			}
			
			buttonsList.add(folderDetailButtonField);

			if (iterSvoltoButtonField == null) {
				iterSvoltoButtonField = new ControlListGridField("iterSvoltoButton");
				iterSvoltoButtonField.setAttribute("custom", Boolean.TRUE);
				iterSvoltoButtonField.setShowHover(Boolean.TRUE);
				iterSvoltoButtonField.setCanReorder(Boolean.FALSE);
				iterSvoltoButtonField.setCellFormatter(new CellFormatter() {

					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
						return buildImgButtonHtml("buttons/iter_svolto.png");
					}
				});
				iterSvoltoButtonField.setHoverCustomizer(new HoverCustomizer() {

					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						return I18NUtil.getMessages().atti_inlavorazione_iter_svolto_menu_title();
					}
				});
				iterSvoltoButtonField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						mostraIterSvoltoClick(record);
					}
				});
			}
			
			buttonsList.add(iterSvoltoButtonField);						
			
			if (anteprimaPropostaButtonField == null) {
				anteprimaPropostaButtonField = new ControlListGridField("anteprimaPropostaButton");
				anteprimaPropostaButtonField.setAttribute("custom", Boolean.TRUE);
				anteprimaPropostaButtonField.setShowHover(Boolean.TRUE);
				anteprimaPropostaButtonField.setCanReorder(Boolean.FALSE);
				anteprimaPropostaButtonField.setCellFormatter(new CellFormatter() {

					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
						if(isRecordAbilToAnteprimaProposta(record)) {
							return buildImgButtonHtml("file/preview.png");
						} 
						return null;
					}
				});
				anteprimaPropostaButtonField.setHoverCustomizer(new HoverCustomizer() {

					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						if(isRecordAbilToAnteprimaProposta(record)) {
							if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {								
								return "Anteprima provvedimento";							
							} else {
								return "Anteprima proposta";
							}
						}
						return null;
					}
				});
				anteprimaPropostaButtonField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						if(isRecordAbilToAnteprimaProposta(record)) {						
							anteprimaPropostaClick(record);
						}
					}
				});
			}
			
			buttonsList.add(anteprimaPropostaButtonField);	
			
			if (anteprimaVRCButtonField == null) {

				anteprimaVRCButtonField = new ControlListGridField("anteprimaVRCButton");
				anteprimaVRCButtonField.setAttribute("custom", Boolean.TRUE);
				anteprimaVRCButtonField.setShowHover(Boolean.TRUE);
				anteprimaVRCButtonField.setCanReorder(Boolean.FALSE);
				anteprimaVRCButtonField.setCellFormatter(new CellFormatter() {

					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
						if(isRecordAbilToAnteprimaVRC(record)) {
							return buildImgButtonHtml("file/previewVRC.png");
						} 
						return null;
					}
				});
				anteprimaVRCButtonField.setHoverCustomizer(new HoverCustomizer() {

					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						if(isRecordAbilToAnteprimaVRC(record)) {
							return "Anteprima visto regolarità contabile";							
						}
						return null;
					}
				});
				anteprimaVRCButtonField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						if(isRecordAbilToAnteprimaVRC(record)) {						
							anteprimaVRCClick(record);
						}
					}
				});
			}
			buttonsList.add(anteprimaVRCButtonField);	

			if (anteprimaAppendiceContabileButtonField == null) {
				anteprimaAppendiceContabileButtonField = new ControlListGridField("anteprimaAppendiceContabileButton");
				anteprimaAppendiceContabileButtonField.setAttribute("custom", Boolean.TRUE);
				anteprimaAppendiceContabileButtonField.setShowHover(Boolean.TRUE);
				anteprimaAppendiceContabileButtonField.setCanReorder(Boolean.FALSE);
				anteprimaAppendiceContabileButtonField.setCellFormatter(new CellFormatter() {

					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
						if(isRecordAbilToAnteprimaAppendiceContabile(record)) {
							return buildImgButtonHtml("buttons/modello.png");
						} 
						return null;
					}
				});
				anteprimaAppendiceContabileButtonField.setHoverCustomizer(new HoverCustomizer() {

					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						if(isRecordAbilToAnteprimaAppendiceContabile(record)) {
							return AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "Visualizza appendice movimenti contabili" : "Visualizza appendice dati di spesa";
						}
						return null;
					}
				});
				anteprimaAppendiceContabileButtonField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						if(isRecordAbilToAnteprimaAppendiceContabile(record)) {						
							anteprimaAppendiceContabileClick(record);
						}
					}
				});
			}
			
			buttonsList.add(anteprimaAppendiceContabileButtonField);
			
			if (riepilogoFirmeVistiButtonField == null) {
				riepilogoFirmeVistiButtonField = new ControlListGridField("riepilogoFirmeVistiButton");
				riepilogoFirmeVistiButtonField.setAttribute("custom", Boolean.TRUE);
				riepilogoFirmeVistiButtonField.setShowHover(Boolean.TRUE);
				riepilogoFirmeVistiButtonField.setCanReorder(Boolean.FALSE);
				riepilogoFirmeVistiButtonField.setCellFormatter(new CellFormatter() {

					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
						if(isRecordAbilToRiepilogoFirmeVisti(record)) {
							return buildImgButtonHtml("file/attestato.png");
						} 
						return null;
					}
				});
				riepilogoFirmeVistiButtonField.setHoverCustomizer(new HoverCustomizer() {

					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {						
						if(isRecordAbilToRiepilogoFirmeVisti(record)) {
							return "Foglio riepilogo";
						} 
						return null;
					}
				});
				riepilogoFirmeVistiButtonField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());									
						if(isRecordAbilToRiepilogoFirmeVisti(record)) {
							Menu menu = buildRiepilogoFirmeVistiMenu(record);
							menu.showContextMenu();
						}
					}
				});
			}
			
			buttonsList.add(riepilogoFirmeVistiButtonField);
			
			if (anteprimaNoteCommentiButtonField == null) {
				anteprimaNoteCommentiButtonField = new ControlListGridField("anteprimaNoteCommentiButton");
				anteprimaNoteCommentiButtonField.setAttribute("custom", Boolean.TRUE);
				anteprimaNoteCommentiButtonField.setShowHover(Boolean.TRUE);
				anteprimaNoteCommentiButtonField.setCanReorder(Boolean.FALSE);
				anteprimaNoteCommentiButtonField.setCellFormatter(new CellFormatter() {

					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
						if(isRecordAbilToAnteprimaNoteCommenti(record)) {
							return buildImgButtonHtml("postaElettronica/apposizione_tag_commenti.png");
						} 
						return null;
					}
				});
				anteprimaNoteCommentiButtonField.setHoverCustomizer(new HoverCustomizer() {

					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {						
						if(isRecordAbilToAnteprimaNoteCommenti(record)) {
							return "Visualizza note & commenti";
						} 
						return null;
					}
				});
				anteprimaNoteCommentiButtonField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());									
						if(isRecordAbilToAnteprimaNoteCommenti(record)) {
							anteprimaNoteCommentiClick(record);	
						}
					}
				});
			}
			
			buttonsList.add(anteprimaNoteCommentiButtonField);
		}
		
		return buttonsList;
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
	protected void mostraIterSvoltoClick(Record record) {
		String idPratica = record.getAttribute("idProcedimento");
		String estremi = record.getAttribute("numeroProposta");
		MostraIterSvoltoModalWindows windows = new MostraIterSvoltoModalWindows(idPratica, estremi);
		windows.show();
	}
	
	protected void  anteprimaVRCClick(final Record record) {
		final String uriFileVRC = record.getAttribute("uriVistoRegContabile");
		final String nomeFileVRC = record.getAttribute("displayFilenameVistoRegContabile");
		Record lRecordFileVRC = new Record();
		lRecordFileVRC.setAttribute("uri", uriFileVRC);
		lRecordFileVRC.setAttribute("nomeFile", nomeFileVRC);
		new OneCallGWTRestDataSource("ProtocolloDataSource").executecustom("getInfoFromFile", lRecordFileVRC, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = dsResponse.getData()[0];
					InfoFileRecord infoFileVRC = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
					PreviewControl.switchPreview(uriFileVRC, false, infoFileVRC, "FileToExtractBean", nomeFileVRC);					
				}
			}
		});
	}
	
	protected void anteprimaPropostaClick(final Record record) {
		if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {
			if(record.getAttribute("uriDispositivoAtto") != null && !"".equals(record.getAttribute("uriDispositivoAtto"))) {
				//TODO devo calcolarmi l'infoFile e fare la preview del file primario
				Record modelloRecord = new Record();
				modelloRecord.setAttribute("processId", record.getAttribute("idProcedimento"));
				modelloRecord.setAttribute("uri", record.getAttribute("uriDispositivoAtto"));
				modelloRecord.setAttribute("nomeFile", record.getAttribute("nomeFileDispositivoAtto"));			
				//richiamo lo stesso metodo usato per gli altri atti, ma senza mettere la copertina
				new GWTRestService<Record, Record>("CompilaModelloDatasource").executecustom("compilaDispositivoAttoConCopertina", modelloRecord, new DSCallback() {
		
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];
							String nomeFilePdf = result.getAttribute("nomeFile");
							String uriFilePdf = result.getAttribute("uri");
							InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
							PreviewControl.switchPreview(uriFilePdf, false, infoFilePdf, "FileToExtractBean", nomeFilePdf);
						}
					}
				});
			} else {
				//TODO devo richiamare l'anteprima del dispositivo della proposta		
				final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
				lNuovaPropostaAtto2DataSource.addParam("idProcess", record.getAttribute("idProcedimento"));
				lNuovaPropostaAtto2DataSource.addParam("taskName", null); // se non lo passo in teoria carica l'ultimo task
				String idUd = record.getAttribute("unitaDocumentariaId");
				if (idUd != null && !idUd.equalsIgnoreCase("")) {
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", idUd); //TODO o devo fare così in discussione seduta?
					lNuovaPropostaAtto2DataSource.getData(lRecordToLoad, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								final Record detailRecord = response.getData()[0];
								boolean hasDatiSensibili = detailRecord.getAttributeAsBoolean("flgDatiSensibili") != null && detailRecord.getAttributeAsBoolean("flgDatiSensibili");
								detailRecord.setAttribute("idModello", record.getAttribute("idModDispositivo") != null ? record.getAttribute("idModDispositivo") : "");
								detailRecord.setAttribute("nomeModello", record.getAttribute("nomeModDispositivo") != null ? record.getAttribute("nomeModDispositivo") : "");
								detailRecord.setAttribute("displayFilenameModello", record.getAttribute("displayFilenameModDispositivo") != null ? record.getAttribute("displayFilenameModDispositivo") : "");
								if(hasDatiSensibili) {
									generaDispositivoDaModelloVersIntegrale(detailRecord, new ServiceCallback<Record>() {
										
										@Override
										public void execute(final Record recordPreview) {
											generaDispositivoDaModelloVersConOmissis(detailRecord, new ServiceCallback<Record>() {
												
												@Override
												public void execute(final Record recordPreviewOmissis) {
													previewWithCallback(recordPreview, new ServiceCallback<Record>() {
												
														@Override
														public void execute(Record object) {
															preview(recordPreviewOmissis);
														}
													});
												}
											});
										}
									});
								} else {
									generaDispositivoDaModelloVersIntegrale(detailRecord, new ServiceCallback<Record>() {
										
										@Override
										public void execute(Record recordPreview) {
											preview(recordPreview);
										}
									});
								}
							}
						}
					});				
				}
			}
		} else {
			Record modelloRecord = new Record();
			modelloRecord.setAttribute("processId", record.getAttribute("idProcedimento"));
			modelloRecord.setAttribute("uriModCopertina", record.getAttribute("uriModCopertina")); //TODO Da recuperare dalla lista (diverso per delibere e determine)
			modelloRecord.setAttribute("tipoModCopertina", record.getAttribute("tipoModCopertina")); //TODO Da recuperare dalla lista (diverso per delibere e determine)
			modelloRecord.setAttribute("uri", record.getAttribute("uriDispositivoAtto"));
			modelloRecord.setAttribute("nomeFile", record.getAttribute("nomeFileDispositivoAtto"));			
			new GWTRestService<Record, Record>("CompilaModelloDatasource").executecustom("compilaDispositivoAttoConCopertina", modelloRecord, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						String nomeFilePdf = result.getAttribute("nomeFile");
						String uriFilePdf = result.getAttribute("uri");
						InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
						PreviewControl.switchPreview(uriFilePdf, false, infoFilePdf, "FileToExtractBean", nomeFilePdf);
					}
				}
			});
		}
	}
	
	protected void anteprimaAppendiceContabileClick(final Record record) {
		if(AurigaLayout.isAttivaNuovaPropostaAtto2()) {
			final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
			lNuovaPropostaAtto2DataSource.addParam("idProcess", record.getAttribute("idProcedimento"));
			lNuovaPropostaAtto2DataSource.addParam("taskName", null); // se non lo passo in teoria carica l'ultimo task
			String idUd = record.getAttribute("unitaDocumentariaId");
			if (idUd != null && !idUd.equalsIgnoreCase("")) {
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idUd", idUd);
				lNuovaPropostaAtto2DataSource.getData(lRecordToLoad, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							final Record detailRecord = response.getData()[0];
							detailRecord.setAttribute("idModello", record.getAttribute("idModContabile") != null ? record.getAttribute("idModContabile") : "");
							detailRecord.setAttribute("nomeModello", record.getAttribute("nomeModContabile") != null ? record.getAttribute("nomeModContabile") : "");
							detailRecord.setAttribute("displayFilenameModello", record.getAttribute("displayFilenameModContabile") != null ? record.getAttribute("displayFilenameModContabile") : "");
							generaDatiSpesaDaModello(detailRecord, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record recordPreview) {
									preview(recordPreview);
								}
							});
						}
					}
				});				
			}
		}
	}
	
	public Menu buildRiepilogoFirmeVistiMenu(final Record record) {
		final Menu riepilogoFirmeVistiMenu = new Menu();
		if (record.getAttribute("uriRiepilogoFirmeVisti") != null && !"".equals(record.getAttribute("uriRiepilogoFirmeVisti"))) {
			MenuItem visualizzaRiepilogoFirmeVistiMenuItem = new MenuItem("Visualizza", "file/preview.png");
			visualizzaRiepilogoFirmeVistiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {					
					final String uriRiepilogoFirmeVisti = record.getAttribute("uriRiepilogoFirmeVisti");
					final String nomeFileRiepilogoFirmeVisti = record.getAttribute("nomeFileRiepilogoFirmeVisti");					
					Record lRecordFileRiepilogoFirmeVisti = new Record();
					lRecordFileRiepilogoFirmeVisti.setAttribute("uri", uriRiepilogoFirmeVisti);
					lRecordFileRiepilogoFirmeVisti.setAttribute("nomeFile", nomeFileRiepilogoFirmeVisti);
					new OneCallGWTRestDataSource("ProtocolloDataSource").executecustom("getInfoFromFile", lRecordFileRiepilogoFirmeVisti, new DSCallback() {
						
						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record result = dsResponse.getData()[0];
								InfoFileRecord infoFileRiepilogoFirmeVisti = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
								PreviewControl.switchPreview(uriRiepilogoFirmeVisti, false, infoFileRiepilogoFirmeVisti, "FileToExtractBean", nomeFileRiepilogoFirmeVisti);					
							}
						}
					});		
				}
			});
			riepilogoFirmeVistiMenu.addItem(visualizzaRiepilogoFirmeVistiMenuItem);
		}		
		if (record.getAttribute("idDocRiepilogoFirmeVisti") != null && !"".equals(record.getAttribute("idDocRiepilogoFirmeVisti"))) {
			MenuItem rigeneraRiepilogoFirmeVistiMenuItem = new MenuItem("Rigenera", "protocollazione/generaDaModello.png");
			rigeneraRiepilogoFirmeVistiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String idUd = record.getAttribute("unitaDocumentariaId");
					final String idDocRiepilogoFirmeVisti = record.getAttribute("idDocRiepilogoFirmeVisti");
					final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
					lNuovaPropostaAtto2CompletaDataSource.addParam("idProcess", record.getAttribute("idProcedimento"));
					lNuovaPropostaAtto2CompletaDataSource.addParam("taskName", null); // se non lo passo in teoria carica l'ultimo task
					if (idUd != null && !idUd.equalsIgnoreCase("")) {
						Record lRecordToLoad = new Record();
						lRecordToLoad.setAttribute("idUd", idUd);
						lNuovaPropostaAtto2CompletaDataSource.getData(lRecordToLoad, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									final Record detailRecord = response.getData()[0];
									detailRecord.setAttribute("idModello", record.getAttribute("idModRiepilogoFirmeVisti") != null ? record.getAttribute("idModRiepilogoFirmeVisti") : "");
									detailRecord.setAttribute("nomeModello", record.getAttribute("nomeModRiepilogoFirmeVisti") != null ? record.getAttribute("nomeModRiepilogoFirmeVisti") : "");
									detailRecord.setAttribute("displayFilenameModello", record.getAttribute("displayFilenameModRiepilogoFirmeVisti") != null ? record.getAttribute("displayFilenameModRiepilogoFirmeVisti") : "");
									generaRiepilogoFirmeVistiDaModello(detailRecord, new ServiceCallback<Record>() {
										
										@Override
										public void execute(final Record recordPreview) {
											previewWithCallback(recordPreview, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record object) {													
													Record recordToVers = new Record();
													recordToVers.setAttribute("idFile", idDocRiepilogoFirmeVisti);
													recordToVers.setAttribute("uri", recordPreview.getAttributeAsString("uri"));
													recordToVers.setAttribute("nomeFile", recordPreview.getAttributeAsString("nomeFile"));		
													recordToVers.setAttribute("infoFile", recordPreview.getAttributeAsRecord("infoFile"));		
													Layout.showWaitPopup("Salvataggio file in corso...");				
													lNuovaPropostaAtto2CompletaDataSource.executecustom("versionaDocumento", recordToVers, new DSCallback() {
														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															Layout.hideWaitPopup();
															if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																if(layout != null) {
																	layout.doSearch();
																}
															}									
														}		
													});
												}
											});
											
											
										}
									});
								}
							}
						});				
					}					
				}
			});
			riepilogoFirmeVistiMenu.addItem(rigeneraRiepilogoFirmeVistiMenuItem);
		}		
		return riepilogoFirmeVistiMenu;
	}
	
	protected void anteprimaNoteCommentiClick(final Record record) {
		//TODO
	}
	
	public void generaDispositivoDaModelloVersIntegrale(Record record) {
		generaDispositivoDaModelloVersIntegrale(record, null);
	}
	
	public void generaDispositivoDaModelloVersIntegrale(Record record, final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(record, true, callback);
	}
	
	public void generaDispositivoDaModelloVersConOmissis(Record record) {
		generaDispositivoDaModelloVersConOmissis(record, null);
	}
	
	public void generaDispositivoDaModelloVersConOmissis(Record record, final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(record, false, callback);
	}
	
	public void generaDispositivoDaModello(Record record, boolean flgMostraDatiSensibili, final ServiceCallback<Record> callback) {
		
		record.setAttribute("flgMostraDatiSensibili", flgMostraDatiSensibili);
		
		Layout.showWaitPopup("Generazione anteprima provvedimento in corso...");				
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.executecustom("generaDispositivoDaModello", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					if(callback != null) {
						callback.execute(recordPreview);
					}
				}				
			}		
		});
	}
	
	public void generaDatiSpesaDaModello(Record record, final ServiceCallback<Record> callback) {

		Layout.showWaitPopup(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "Generazione anteprima movimenti contabili in corso..." : "Generazione anteprima dati di spesa in corso...");				
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.executecustom("generaDatiSpesaDaModello", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					if(callback != null) {
						callback.execute(recordPreview);
					}
				}				
			}		
		});
	}
	
	public void generaRiepilogoFirmeVistiDaModello(Record record, final ServiceCallback<Record> callback) {

		Layout.showWaitPopup("Generazione riepilogo firme e visti in corso...");				
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("generaRiepilogoFirmeVistiDaModello", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					if(callback != null) {
						callback.execute(recordPreview);
					}
				}				
			}		
		});
	}
		
	public void preview(final Record recordPreview) {
		previewWithCallback(recordPreview, null);
	}
	
	public void previewWithCallback(final Record recordPreview, final ServiceCallback<Record> closeCallback) {
		if (recordPreview.getAttribute("nomeFile") != null && recordPreview.getAttribute("nomeFile").endsWith(".pdf")) {
			new PreviewWindow(recordPreview.getAttribute("uri"), false, new InfoFileRecord(recordPreview.getAttributeAsRecord("infoFile")), "FileToExtractBean",	recordPreview.getAttribute("nomeFile")) {
			
				@Override
				public void manageCloseClick() {
					super.manageCloseClick();
					if(closeCallback != null) {
						closeCallback.execute(recordPreview);
					}
				};
			};
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", recordPreview.getAttribute("nomeFile"));
			lRecord.setAttribute("uri", recordPreview.getAttribute("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", "false");
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
			if(closeCallback != null) {
				closeCallback.execute(recordPreview);
			}
		}
	}
	
	public void smistaPropAttoButtonClick(final Record record) {
		final RecordList listaRecord = new RecordList();
		listaRecord.add(record);		
		new SmistamentoAttiPopup((listaRecord.getLength() == 1) ? listaRecord.get(0) : null) {

			@Override
			public void onClickOkButton(final DSCallback callback) {
				final Record recordToSave = new Record();
				recordToSave.setAttribute("listaRecord", listaRecord);
				if(AurigaLayout.isAttivoClienteCOTO()) {
					recordToSave.setAttribute("listaUfficioLiquidatore", _form.getValueAsRecordList("listaUfficioLiquidatore"));
				}				
				recordToSave.setAttribute("listaSmistamento", _form.getValueAsRecordList("listaSmistamento"));
				Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("SmistamentoAttiDataSource");
				try {
					lGwtRestDataSource.addData(recordToSave, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {							
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
								Record data = response.getData()[0];
								Map errorMessages = data.getAttributeAsMap("errorMessages");
								if (errorMessages != null && errorMessages.size() == 1) {
									String errorMsg = (String) errorMessages.get(record.getAttribute("idProcedimento"));
									Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
								} else {
									if(layout != null) {
										layout.doSearch();
									}
									Layout.addMessage(new MessageBean("Smistamento effettuato con successo", "", MessageType.INFO));
									callback.execute(response, rawData, request);
								}
							} 
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
			}
		};
	}	
	
	public void rilascioVistoButtonClick(final Record record) {
		final RecordList listaRecord = new RecordList();
		final Record recordAtto = new Record();
		// questa operazione deve funzionare sia da lista atti completi che in quella vecchia degli atti
		// quindi passo un record con solo le property unitaDocumentariaId e idProcedimento (che si trovano in entrambi i bean)
		recordAtto.setAttribute("unitaDocumentariaId", record.getAttribute("unitaDocumentariaId"));
		recordAtto.setAttribute("idProcedimento", record.getAttribute("idProcedimento"));										
		listaRecord.add(recordAtto);			
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);	
		recordToSave.setAttribute("azione", AttiLayout._RILASCIO_VISTO);
		MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Osservazioni/note a corredo del visto", recordToSave, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				Layout.showWaitPopup("Rilascio visto in corso: potrebbe richiedere qualche secondo. Attendere…");
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "AzioneSuListaDocAttiCompletiDataSource" : "AzioneSuListaDocAttiDataSource");
				try {
					lGwtRestDataSource.addData(object, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {					
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
								Record data = response.getData()[0];
								Map errorMessages = data.getAttributeAsMap("errorMessages");
								if (errorMessages != null && errorMessages.size() == 1) {
									String errorMsg = (String) errorMessages.get(record.getAttribute("unitaDocumentariaId"));
									Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
								} else {
									if(layout != null) {
										layout.doSearch();
									}
									Layout.addMessage(new MessageBean("Rilascio visto effettuato con successo", "", MessageType.INFO));							
								}
							} 
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}	
			}
		});
		lMotivoOsservazioniAzioneSuListaAttiPopup.show();			
	}
	
	public void rifiutoVistoButtonClick(final Record record) {
		final RecordList listaRecord = new RecordList();
		final Record recordAtto = new Record();
		// questa operazione deve funzionare sia da lista atti completi che in quella vecchia degli atti
		// quindi passo un record con solo le property unitaDocumentariaId e idProcedimento (che si trovano in entrambi i bean)
		recordAtto.setAttribute("unitaDocumentariaId", record.getAttribute("unitaDocumentariaId"));
		recordAtto.setAttribute("idProcedimento", record.getAttribute("idProcedimento"));										
		listaRecord.add(recordAtto);		
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);	
		recordToSave.setAttribute("azione", AttiLayout._RIFIUTO_VISTO);
		MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Compilazione motivo rifiuto", true, recordToSave, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				Layout.showWaitPopup("Rifiuto visto in corso: potrebbe richiedere qualche secondo. Attendere…");
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "AzioneSuListaDocAttiCompletiDataSource" : "AzioneSuListaDocAttiDataSource");
				try {
					lGwtRestDataSource.addData(object, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {					
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
								Record data = response.getData()[0];
								Map errorMessages = data.getAttributeAsMap("errorMessages");
								if (errorMessages != null && errorMessages.size() == 1) {
									String errorMsg = (String) errorMessages.get(record.getAttribute("unitaDocumentariaId"));
									Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
								} else {
									if(layout != null) {
										layout.doSearch();
									}
									Layout.addMessage(new MessageBean("Rifiuto visto effettuato con successo", "", MessageType.INFO));							
								}
							} 
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}		
			}
		});
		lMotivoOsservazioniAzioneSuListaAttiPopup.show();				
	}
	
	protected void mandaLibroFirmaMenuItemButtonClick(final Record record) {
		final RecordList listaRecord = new RecordList();
		listaRecord.add(record);
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
		lGwtRestDataSource.executecustom("mandaALibroFirma", recordToSave, new DSCallback() {

			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map errorMessages = data.getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() >= 1) {
						List<String> listKey = new ArrayList<String>(errorMessages.keySet());
						String errorMsg = (String) errorMessages.get(listKey.get(0));
						Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
					} else {
						if (layout != null) {
							layout.doSearch();
						}
						Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));
					}
				}
			}
		});
	}
	
	protected void togliLibroFirmaButtonClick(final Record record) {
		final RecordList listaRecord = new RecordList();
		listaRecord.add(record);
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
		lGwtRestDataSource.executecustom("togliDaLibroFirma", recordToSave, new DSCallback() {

			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map errorMessages = data.getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() >= 1) {
						List<String> listKey = new ArrayList<String>(errorMessages.keySet());
						String errorMsg = (String) errorMessages.get(listKey.get(0));
						Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
					} else {
						if (layout != null) {
							layout.doSearch();
						}
						Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));
					}
				}
			
			}
		});
	}

	protected void segnaRicontrollareButtonClick(final Record record) {

		final RecordList listaRecord = new RecordList();
		listaRecord.add(record);
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
		lGwtRestDataSource.executecustom("segnaDaRicontrollare", recordToSave, new DSCallback() {

			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Map errorMessages = data.getAttributeAsMap("errorMessages");
					if (errorMessages != null && errorMessages.size() >= 1) {
						List<String> listKey = new ArrayList<String>(errorMessages.keySet());
						String errorMsg = (String) errorMessages.get(listKey.get(0));
						Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
					} else {
						if (layout != null) {
							layout.doSearch();
						}
						Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));
					}
				}
			
			}
		});

	}
		
	public void osservazioniNotificheButtonClick(Record record) {
		String unitaDocumentariaId = record.getAttribute("unitaDocumentariaId");
		String title = "Osservazioni e notifiche " +  record.getAttribute("numeroProposta");
		OsservazioniNotificheWindow osservazioniNotifiche = new OsservazioniNotificheWindow(unitaDocumentariaId, "U", title);
		osservazioniNotifiche.show();		
	}	
	
	public void eventoAMCButtonClick(final Record record) {
		final RecordList listaRecord = new RecordList();
		final Record recordAtto = new Record();
		// questa operazione deve funzionare sia da lista atti completi che in quella vecchia degli atti
		// quindi passo un record con solo la property unitaDocumentariaId (che si trova in entrambi i bean)
		recordAtto.setAttribute("unitaDocumentariaId", record.getAttribute("unitaDocumentariaId"));
		recordAtto.setAttribute("flgRilevanzaContabile", "1"); // lo setto sempre a 1 altrimenti poi non mi esegue l'evento (tanto poi carica il dettaglio e riverifica se ha rilevanza contabile oppure no)									
		listaRecord.add(recordAtto);	
		final Record recordToSave = new Record();
		recordToSave.setAttribute("listaRecord", listaRecord);
		recordToSave.setAttribute("azione", AttiLayout._EVENTO_AMC);						
		final String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
		EventoAMCPopup lEventoAMCPopup = new EventoAMCPopup("Evento " + lSistAMC, recordToSave, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				Layout.showWaitPopup("Evento " + lSistAMC + " in corso: potrebbe richiedere qualche secondo. Attendere…");
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "AzioneSuListaDocAttiCompletiDataSource" : "AzioneSuListaDocAttiDataSource");
				try {
					lGwtRestDataSource.addData(object, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {	
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {							
								Record data = response.getData()[0];
								Map errorMessages = data.getAttributeAsMap("errorMessages");
								if (errorMessages != null && errorMessages.size() == 1) {
									String errorMsg = (String) errorMessages.get(record.getAttribute("unitaDocumentariaId"));
									Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
								} else {
									if(layout != null) {
										layout.doSearch();
									}
									Layout.addMessage(new MessageBean("Evento " + lSistAMC + " effettuato con successo", "", MessageType.INFO));							
								}
							} 									
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}			
			}
		});
		lEventoAMCPopup.show();					
	}	
	
	public static boolean isAttivaRespConcertoInIterAtti() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_RESP_CONCERTO_IN_ITER_ATTI");
	}
	
	public static boolean isAttivaCIGInIterAtti() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CIG_IN_ITER_ATTI");
	}
	
	public static boolean isAttivaEstensoriAtti() {
		String value = AurigaLayout.getParametroDB("ESTENSORI_ITER_ATTI");
		return value != null && !"".equals(value);
	}
	
	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if (getFieldName(colNum).equals("ordinamentoNumeroProposta")) {
				if (record.getAttribute("ordinamentoNumeroProposta") != null &&
						!"".equalsIgnoreCase(record.getAttribute("ordinamentoNumeroProposta"))) {
									return it.eng.utility.Styles.cellTextBlueClickable;
				}
			} else {
				return super.getBaseStyle(record, rowNum, colNum);
			}
		} catch (Exception e) {
			return super.getBaseStyle(record, rowNum, colNum);
		}
		return super.getBaseStyle(record, rowNum, colNum);
	}
	
}