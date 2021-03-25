package it.eng.auriga.ui.module.layout.client.gestioneatti;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.organigramma.LegendaDinamicaPanel;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class SmistamentoAttiPopup extends ModalWindow {

	protected SmistamentoAttiPopup _window;
	protected DynamicForm _form;
	private DynamicForm formImage;

	public DynamicForm getForm() {
		return _form;
	}

	protected UfficioLiquidatoreItem ufficioLiquidatoreItem;
	protected SmistamentoAttiItem smistamentoItem;

	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;

	public SmistamentoAttiPopup(Record pListRecord) {

		super("smistamento_atto", true);

		_window = this;

		String idProcedimento = pListRecord != null ? pListRecord.getAttribute("idProcedimento") : null;
		String estremiProcedimento = (pListRecord != null && pListRecord.getAttribute("numeroProposta") != null) ? pListRecord.getAttribute("numeroProposta") : "";
		
		String title = null;
		if (!"".equals(estremiProcedimento)) {
			title = "Compila dati smistamento atto " + estremiProcedimento;
		} else {
			title = "Compila dati smistamento atti";
		}

		setTitle(title);

		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		_form = new DynamicForm();
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(5);
		_form.setColWidths(120, "*", "*", "*", "*");
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);
		
		smistamentoItem = new SmistamentoAttiItem(idProcedimento);
		smistamentoItem.setName("listaSmistamento");
		smistamentoItem.setTitle("Assegnatario");
		smistamentoItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		smistamentoItem.setCanEdit(true);
		smistamentoItem.setColSpan(5);
		smistamentoItem.setNotReplicable(true);
		
		if(AurigaLayout.isAttivoClienteCOTO()) {
			
			ufficioLiquidatoreItem = new UfficioLiquidatoreItem();
			ufficioLiquidatoreItem.setName("listaUfficioLiquidatore");
			ufficioLiquidatoreItem.setTitle("Ufficio liquidatore");
			ufficioLiquidatoreItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
			ufficioLiquidatoreItem.setCanEdit(true);
			ufficioLiquidatoreItem.setColSpan(5);
			ufficioLiquidatoreItem.setNotReplicable(true);
			
			_form.setFields(new FormItem[] { ufficioLiquidatoreItem, smistamentoItem });
			
		} else {
			_form.setFields(new FormItem[] { smistamentoItem });
		}

		Button confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if (validate()) {
					onClickOkButton(new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							_window.markForDestroy();
						}
					});
				}
			}
		});

		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				_window.markForDestroy();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_LEGENDA_DIN_TIPO_UO")) {
				LegendaDinamicaPanel leg = new LegendaDinamicaPanel();
				layout.addMember(leg);
			} else {
				buildLegendImageUO();
				layout.addMember(formImage);
			}
		}

		layout.addMember(_form);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/assegna.png");
		
		if(pListRecord != null) {
			GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lProtocolloDataSource.addParam("flgSoloAbilAzioni", "1");
			lProtocolloDataSource.addParam("idProcess", idProcedimento);
			lProtocolloDataSource.addParam("taskName", "#SMISTAMENTO");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", pListRecord.getAttribute("unitaDocumentariaId"));
			lProtocolloDataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record detailRecord = response.getData()[0];
						_window.editRecordPerModificaInvio(prepareRecordSmistamentoAtti(detailRecord));		
						_window.show();
					}
				}
			});
		} else {
			_window.editRecordPerModificaInvio(prepareRecordSmistamentoAtti(null));		
			_window.show();
		}
	}
	
	private Record prepareRecordSmistamentoAtti(Record detailRecord) {
		// Preparo il record per settare i valori a maschera
		Record lRecordSmistamentoAtti = new Record();				
		if(AurigaLayout.isAttivoClienteCOTO()) {
			/*
			#IdGruppoLiquidatori (id gruppo interno)
			#NomeGruppoLiquidatori
			#CodGruppoLiquidatori
			*/
			RecordList lRecordListUfficioLiquidatore = new RecordList();
			Record lRecordUfficioLiquidatore = new Record();
			if(detailRecord != null) {
				// se sono nello smistamento di un singolo atto
				if(detailRecord.getAttribute("idGruppoLiquidatori") != null && !"".equals(detailRecord.getAttribute("idGruppoLiquidatori"))) {
					lRecordUfficioLiquidatore.setAttribute("ufficioLiquidatore", detailRecord.getAttribute("idGruppoLiquidatori"));
					lRecordUfficioLiquidatore.setAttribute("nomeUfficioLiquidatore", detailRecord.getAttribute("nomeGruppoLiquidatori"));
					lRecordUfficioLiquidatore.setAttribute("codRapidoUfficioLiquidatore", detailRecord.getAttribute("codGruppoLiquidatori"));
				}
			} else {
				// se sono nello smistamento massivo
			}
			lRecordListUfficioLiquidatore.add(lRecordUfficioLiquidatore);
			lRecordSmistamentoAtti.setAttribute("listaUfficioLiquidatore", lRecordListUfficioLiquidatore);			
		}
		/*
		#IdAssegnatarioProcesso (SV o UO + id -> UO134, SV145 ecc)
		#DesAssegnatarioProcesso                        
		#NriLivelliAssegatarioProcesso (codice rapido)
		 */
		RecordList lRecordListSmistamento = new RecordList();
		Record lRecordSmistamento = new Record();
		if(detailRecord != null) {
			// se sono nello smistamento di un singolo atto
			if(detailRecord.getAttribute("idAssegnatarioProcesso") != null && !"".equals(detailRecord.getAttribute("idAssegnatarioProcesso"))) {
				lRecordSmistamento.setAttribute("codRapido", detailRecord.getAttribute("nriLivelliAssegatarioProcesso"));
				lRecordSmistamento.setAttribute("descrizione", detailRecord.getAttribute("desAssegnatarioProcesso"));
				lRecordSmistamento.setAttribute("organigramma", detailRecord.getAttribute("idAssegnatarioProcesso"));
				if (detailRecord.getAttribute("idAssegnatarioProcesso").startsWith("UO")) {
					lRecordSmistamento.setAttribute("typeNodo", "UO");
					lRecordSmistamento.setAttribute("idUo", detailRecord.getAttribute("idAssegnatarioProcesso").substring(2));
				} else if (detailRecord.getAttribute("idAssegnatarioProcesso").startsWith("SV")){
					lRecordSmistamento.setAttribute("typeNodo", "SV");
					lRecordSmistamento.setAttribute("idUo", detailRecord.getAttribute("idAssegnatarioProcesso").substring(2));
				}
			} else if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				lRecordSmistamento.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
			}
		} else {
			// se sono nello smistamento massivo
			if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				lRecordSmistamento.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
			}
		}
		lRecordListSmistamento.add(lRecordSmistamento);
		lRecordSmistamentoAtti.setAttribute("listaSmistamento", lRecordListSmistamento);			
		return lRecordSmistamentoAtti;
	}
	
	public boolean validate() {
		if(AurigaLayout.isAttivoClienteCOTO()) {
			Boolean valid = _form.validate();
			valid = ufficioLiquidatoreItem.validate() && valid;
			valid = smistamentoItem.validate() && valid;
			return valid;
		} else {
			return smistamentoItem.validate();
		}
	}

	private void buildLegendImageUO() {
		formImage = new DynamicForm();
		formImage.setKeepInParentRect(true);
		formImage.setCellPadding(5);
		formImage.setWrapItemTitles(false);

		StaticTextItem tipoUOImage = new StaticTextItem("iconaStatoConsolidamento");
		tipoUOImage.setShowValueIconOnly(true);
		tipoUOImage.setShowTitle(false);
		tipoUOImage.setValueIconWidth(600);
		tipoUOImage.setValueIconHeight(60);
		tipoUOImage.setAlign(Alignment.LEFT);
		Map<String, String> valueIcons = new HashMap<String, String>();
		valueIcons.put("1", "organigramma/legenda_uo.png");
		tipoUOImage.setValueIcons(valueIcons);
		tipoUOImage.setDefaultValue("1");
		tipoUOImage.setDefaultIconSrc("organigramma/legenda_uo.png");

		formImage.setItems(tipoUOImage);
	}

	public abstract void onClickOkButton(DSCallback callback);

	public void editRecordPerModificaInvio(Record record) {
		_form.editRecord(record);
		markForRedraw();
	}

}
