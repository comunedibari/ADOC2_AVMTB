package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2;

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.pratiche.AvvioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.PropostaAttoInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.CIGItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

public class AvvioNuovaPropostaAtto2CompletaDetail extends NuovaPropostaAtto2CompletaDetail implements PropostaAttoInterface {

	protected AvvioNuovaPropostaAtto2CompletaDetail instance;

	protected Record recordAvvio;
	protected Record recordEvento;
	protected Record recordInitialValues;
	
	protected String idTipoProc;
	protected String nome;
	protected String activityName;
	
	protected Boolean flgNumPropostaDiffXStruttura;

	protected AvvioPraticaLayout avvioPraticaLayout;

	public AvvioNuovaPropostaAtto2CompletaDetail(String nomeEntita, String idTipoProc, Record lRecordAvvio, Record lRecordEvento,
			AvvioPraticaLayout avvioPraticaLayout) {

		//TODO controllare se i tab dinamici caricati all'avvio vengono gestiti correttamente nei modelli
		super(nomeEntita, getAttributAddDocTabsAvvio(lRecordAvvio), getTipoDocumento(lRecordAvvio));
//		super(nomeEntita, null, getTipoDocumento(lRecordAvvio));
		
		instance = this;

		this.idTipoProc = idTipoProc;

		this.recordAvvio = lRecordAvvio;
		this.recordEvento = lRecordEvento;
		
		if (lRecordAvvio != null && lRecordAvvio.getAttribute("nuovoComeCopia") != null && lRecordAvvio.getAttributeAsBoolean("nuovoComeCopia")) {
			Map<String, Object> values = lRecordAvvio.getAttribute("recordInitialValues") != null ? lRecordAvvio.getAttributeAsRecord("recordInitialValues").toMap() : null;
			if (values != null ) {
				removeValuesToSkipInNuovoComeCopia(values);
				this.recordInitialValues = new Record(values);
			}
		} else {
			this.recordInitialValues = lRecordAvvio != null ? lRecordAvvio.getAttributeAsRecord("recordInitialValues") : null;
		}
		
		this.recordParametriTipoAtto = lRecordAvvio != null ? lRecordAvvio.getAttributeAsRecord("parametriTipoAtto") : null;
		this.flgPubblicazioneAllegatiUguale = lRecordAvvio != null ? lRecordAvvio.getAttributeAsBoolean("flgPubblicazioneAllegatiUguale") : null;
		this.flgAvvioLiquidazioneContabilia = lRecordAvvio != null ? lRecordAvvio.getAttributeAsBoolean("flgAvvioLiquidazioneContabilia") : null;
		this.flgNumPropostaDiffXStruttura = lRecordAvvio != null ? lRecordAvvio.getAttributeAsBoolean("flgNumPropostaDiffXStruttura") : null;
		
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;

		this.avvioPraticaLayout = avvioPraticaLayout;
		
		build();
		
		if(recordInitialValues != null) {
			editNewRecord(recordInitialValues.toMap());
			if(recordInitialValues.getAttributeAsMap("valori") != null) {
				setAttributiDinamiciDocDaCopiare(recordInitialValues.getAttributeAsMap("valori")); //TODO non funziona, non carica correttamente gli attributi dinamici a maschera
			}			
			if (isAbilToSelUffPropEsteso()) {	
				//TODO se il valore non è più selezionabile nella select lo sbianco
			} else {
				if(getUfficioProponenteValueMap().size() == 1) {
					String key = getUfficioProponenteValueMap().keySet().toArray(new String[1])[0];
					String value = getUfficioProponenteValueMap().get(key);
					ufficioProponenteItem.setValue((key != null && key.startsWith("UO")) ? key.substring(2) : key);
					if(value != null && !"".equals(value)) {
						codUfficioProponenteItem.setValue(value.substring(0, value.indexOf(" - ")));
						desUfficioProponenteItem.setValue(value.substring(value.indexOf(" - ") + 3));
					}			
					afterSelezioneUoProponente();
				} else if (recordInitialValues.getAttribute("ufficioProponente") != null && !getUfficioProponenteValueMap().containsKey(recordInitialValues.getAttribute("ufficioProponente"))) {			
					ufficioProponenteItem.setValue("");
					codUfficioProponenteItem.setValue("");
					desUfficioProponenteItem.setValue("");
					afterSelezioneUoProponente();
				}
			}
		} else {
			editNewRecord();
		}

		caricaAttributiDinamiciDoc(null, null, null, tipoDocumento, null);
	}
	
	public boolean isAvvioEmendamentoFromAttoRif() {
		return recordInitialValues != null && recordInitialValues.getAttribute("tipoAttoEmendamento") != null && !"".equals(recordInitialValues.getAttribute("tipoAttoEmendamento"));
	}
	
	public static LinkedHashMap<String, String> getAttributAddDocTabsAvvio(Record lRecordAvvio) {
		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		RecordList attributiAddDocTabs = lRecordAvvio != null ? lRecordAvvio.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				tabs.put(tab.getAttribute("codice"), tab.getAttribute("titolo"));
			}
		}
		return tabs;
	}
	
	@Override
	public boolean skipSuperBuild() {
		return true; // evito di fare la build nel costruttore della superclasse, in modo da farla poi alla fine, dopo aver inizializzato recordAvvio e le altre variabili che mi servono nella build
	}
	
	@Override
	public boolean isAvvioPropostaAtto() {
		return true;
	}	
	
	@Override
	protected String getIdProcessTypeTask() {
		return idTipoProc;
	}
	
	public static String getTipoDocumento(Record lRecordAvvio) {
		return lRecordAvvio != null ? lRecordAvvio.getAttribute("idTipoDocProposta") : null;
	}
	
	@Override
	public void editNewRecordFromModello(Map valuesFromModello) {
		if(recordInitialValues != null) {			
			Map initialValues = recordInitialValues.toMap();
			initialValues.putAll(valuesFromModello);
			editNewRecord(initialValues);
		} else {
			super.editNewRecordFromModello(valuesFromModello);
		}
	}
	
	@Override
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();
		lRecordToSave.setAttribute("tipoDocumento", recordAvvio.getAttribute("idTipoDocProposta"));
		lRecordToSave.setAttribute("categoriaRegAvvio", recordAvvio.getAttribute("categoriaProposta"));					
		lRecordToSave.setAttribute("siglaRegAvvio", recordAvvio.getAttribute("siglaProposta"));		
		return lRecordToSave;
	}
	
	@Override
	public Record getRecordEvento() {
		return null;
	}
	
	@Override
	public void loadDati() {

	}

	@Override
	public void salvaDatiProvvisorio() {

	}

	@Override
	public void salvaDatiDefinitivo() {
		if (validate()) {
			//TODO gestire nuovi campi flgOpCommerciale, flgEscludiCIG e motivoEsclusioneCIG
			if(showAttributoCustomCablato("DATI_CONTABILI") && showDetailSectionCIG() && listaCIGItem != null && listaCIGItem.getEditing()
			   && (isDeterminaConSpesa() || isDeterminaConSpesaSenzaImpegni() || isDeterminaAggiudicaProceduraGara())) {
				boolean isListaCIGEmpty = false;
				if(listaCIGItem != null) {
					RecordList listaCIG = CIGForm.getValueAsRecordList("listaCIG");
					isListaCIGEmpty = true;
					for(int i=0; i < listaCIG.getLength(); i++) {
						if(listaCIG.get(i).getAttribute("codiceCIG") != null &&
								!"".equals(listaCIG.get(i).getAttribute("codiceCIG"))) {
							isListaCIGEmpty = false;
							break;
						}
					}
				}
				if (!isEsclusoCIG() && isListaCIGEmpty) {
					SC.ask("CIG non valorizzato. Vuoi procedere comunque?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if (value) {
								continuaSalvaDatiDefinitivo();
							} else {
								avvioPraticaLayout.manageOnErrorAvvioIterAtti(null);								
							}
						}
					});
				} else {
					continuaSalvaDatiDefinitivo();
				}
			} else {
				continuaSalvaDatiDefinitivo();
			}			
		} else {
			avvioPraticaLayout.manageOnErrorAvvioIterAtti(null);
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}
	
	private void continuaSalvaDatiDefinitivo() {
		Record lRecordToSave = getRecordToSave();
		final String ufficioProponente = lRecordToSave.getAttribute("ufficioProponente");
		final String oggetto = lRecordToSave.getAttribute("oggetto");
//		final String idFolder = getIdFolderForAvvio(lRecordToSave);						
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		if(flgAvvioLiquidazioneContabilia != null && flgAvvioLiquidazioneContabilia) {
			lNuovaPropostaAtto2CompletaDataSource.addParam("isAvvioLiquidazioneContabilia", "true");
		}
		if(flgNumPropostaDiffXStruttura != null && flgNumPropostaDiffXStruttura) {
			lNuovaPropostaAtto2CompletaDataSource.addParam("isNumPropostaDiffXStruttura", "true");
		}
		Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
		lNuovaPropostaAtto2CompletaDataSource.addData(lRecordToSave, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordSaved = response.getData()[0];
					final String idUd = lRecordSaved.getAttribute("idUd");
					final String rowidDoc = lRecordSaved.getAttribute("rowidDoc");
					final String idDocPrimario = lRecordSaved.getAttribute("idDocPrimario");
					avvioPraticaLayout.avvioIterAtti(idUd, null, idDocPrimario, ufficioProponente, oggetto);						
				} else {
					avvioPraticaLayout.manageOnErrorAvvioIterAtti(null);
				}
			}
		});
	}
	
	public String getIdFolderForAvvio(Record lRecord) {
		String idFolder = null;
//		RecordList listaClassFasc = lRecord.getAttributeAsRecordList("listaClassFasc");
//		if (listaClassFasc != null && listaClassFasc.getLength() > 0) {
//			Record lFirstRecord = listaClassFasc.get(0);
//			if (lFirstRecord.getAttribute("idFolderFascicolo") != null && !"".equals(lFirstRecord.getAttribute("idFolderFascicolo"))) {
//				idFolder = lFirstRecord.getAttribute("idFolderFascicolo");
//			}
//		}
//		if (idFolder == null) {
//			RecordList listaFolderCustom = lRecord.getAttributeAsRecordList("listaFolderCustom");
//			if (listaFolderCustom != null && listaFolderCustom.getLength() > 0) {
//				Record lFirstRecord = listaFolderCustom.get(0);
//				if (lFirstRecord.getAttribute("id") != null && !"".equals(lFirstRecord.getAttribute("id"))) {
//					idFolder = lFirstRecord.getAttribute("id");
//				}
//			}
//		}
		return idFolder;
	}

	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}

	@Override
	public String getNomeTastoSalvaProvvisorio() {
		return null;
	}

	@Override
	public String getNomeTastoSalvaDefinitivo() {
		return "Avanti";
	}

	@Override
	public void back() {
		avvioPraticaLayout.caricaDettaglioEventoApp(nome);
	}

	@Override
	public boolean hasDocumento() {
		return false;
	}

	@Override
	public boolean showSalvaModello() {
		return true;
	}
	
	@Override
	public String getPrefKeyModelliDSprefix() {
		return "evento" + avvioPraticaLayout.getIdTipoProc();
	}
}