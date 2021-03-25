package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2;

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
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

public class AvvioRevocaNuovaPropostaAtto2Detail extends RevocaNuovaPropostaAtto2Detail implements PropostaAttoInterface {

	protected AvvioRevocaNuovaPropostaAtto2Detail instance;

	protected Record recordAvvio;
	protected Record recordEvento;
	protected Record recordInitialValues;

	protected String idTipoProc;
	protected String nome;
	protected String activityName;

	protected AvvioPraticaLayout avvioPraticaLayout;

	public AvvioRevocaNuovaPropostaAtto2Detail(String nomeEntita, String idTipoProc, Record lRecordAvvio, Record lRecordEvento,
			AvvioPraticaLayout avvioPraticaLayout) {

		super(nomeEntita, null, getTipoDocumento(lRecordAvvio));
		
		instance = this;

		this.idTipoProc = idTipoProc;

		this.recordAvvio = lRecordAvvio;
		this.recordEvento = lRecordEvento;

		this.recordInitialValues = lRecordAvvio != null ? lRecordAvvio.getAttributeAsRecord("recordInitialValues") : null;
		
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;

		this.avvioPraticaLayout = avvioPraticaLayout;
		
		build();
		
		if(recordInitialValues != null) {
			editNewRecord(recordInitialValues.toMap());
		} else {
			editNewRecord();
		}

		caricaAttributiDinamiciDoc(null, null, null, tipoDocumento, null);
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
	public void removeValuesToSkipInModello(Map<String, Object> values) {
		super.removeValuesToSkipInModello(values);
		//tolgo tutti i valori preimpostati in avvio e che dipendono in qualche modo dall'atto da annullare, quelli non vanno mai sovrascritti
		values.remove("idTipoProc");
		values.remove("nomeTipoProc");
		values.remove("idTipoFlussoActiviti");
		values.remove("idTipoDocProposta");
		values.remove("nomeTipoDocProposta");
		values.remove("siglaProposta");		
		values.remove("listaUfficioProponente");
		values.remove("ufficioProponente");
		values.remove("codUfficioProponente");
		values.remove("desUfficioProponente");
		values.remove("listaAdottante"); //anche questo non lo salvo nel modello perchè è specifico di quella revoca	
		values.remove("listaRdP");
		values.remove("listaRUP");
		values.remove("listaDirigentiConcerto");		
		values.remove("flgSpesa");		
		values.remove("idUdAttoDeterminaAContrarre");
		values.remove("categoriaRegAttoDeterminaAContrarre");
		values.remove("siglaAttoDeterminaAContrarre");
		values.remove("numeroAttoDeterminaAContrarre");
		values.remove("annoAttoDeterminaAContrarre");		
		values.remove("oggetto"); //anche questo non lo salvo nel modello perchè è specifico di quella revoca	
		values.remove("oggettoHtml"); //anche questo non lo salvo nel modello perchè è specifico di quella revoca				
	}
	
	@Override
	public void editNewRecordFromModello(Map valuesFromModello) {
		if(recordInitialValues != null) {			
			Map initialValues = recordInitialValues.toMap();
			initialValues.putAll(valuesFromModello);
			initialValues.put("listaAdottante", ruoliForm != null ? ruoliForm.getValueAsRecordList("listaAdottante") : null); //tengo quello che c'è a maschera
			initialValues.put("oggetto", oggettoItem != null ? (String) oggettoItem.getValue() : null); //tengo quello che c'è a maschera
			initialValues.put("oggettoHtml", oggettoHtmlItem != null ? oggettoHtmlItem.getValue() : null); //tengo quello che c'è a maschera
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
		lRecordToSave.setAttribute("flgDelibera", (recordAvvio.getAttribute("flgDelibera") != null && "1".equals(recordAvvio.getAttribute("flgDelibera"))) ? true : false);
		lRecordToSave.setAttribute("showDirigentiProponenti", recordAvvio.getAttribute("showDirigentiProponenti"));
		lRecordToSave.setAttribute("showAssessori", recordAvvio.getAttribute("showAssessori"));
		lRecordToSave.setAttribute("showConsiglieri", recordAvvio.getAttribute("showConsiglieri"));		
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
			if(showDetailSectionCIG() && listaCIGItem != null && listaCIGItem.getEditing()
				&& (!_FLG_SPESA_NO.equalsIgnoreCase(getValueAsString("flgSpesa")) || getValueAsBoolean("flgDeterminaAggiudicaProceduraGara"))) {
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
				if (isListaCIGEmpty) {
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
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
		lNuovaPropostaAtto2DataSource.addData(lRecordToSave, new DSCallback() {

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

}
