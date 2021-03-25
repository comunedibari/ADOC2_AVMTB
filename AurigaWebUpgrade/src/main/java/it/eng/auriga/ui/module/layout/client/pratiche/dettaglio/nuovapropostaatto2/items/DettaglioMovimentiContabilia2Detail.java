package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DettaglioMovimentiContabilia2Detail extends CustomDetail {
	
	protected ListaMovimentiContabilia2Item gridItem;
	
	protected DynamicForm mDynamicForm;
	
	protected HiddenItem flgEntrataUscita;
	protected TextItem tipoMovimento;
	protected TextItem annoMovimento;
	protected TextItem numeroMovimento;
	protected TextAreaItem descrizioneMovimento;
	protected TextItem annoSub;
	protected TextItem numeroSub;
	protected TextItem descrizioneSub;
	protected TextItem annoModifica;	
	protected TextItem numeroModifica;	
	protected TextItem descrizioneModifica;	
	protected TextItem importoModifica;
	protected TextItem importoIniziale;
	protected TextItem importo;		
	protected TextItem numeroCapitolo;
	protected TextItem numeroArticolo;
	protected TextItem numeroUEB;
	protected TextItem descrizioneCapitolo;
	protected TextItem descrizioneArticolo;
	protected TextItem codiceCIG;
	protected TextItem motivoAssenzaCIG;
	protected TextItem codiceCUP;
	protected TextItem codiceSoggetto;	
	protected TextItem descrizioneSoggetto;
	protected TextItem codiceClasseSoggetto;
	protected TextItem descrizioneClasseSoggetto;	
	protected TextItem codicePdC;
	protected TextItem descrizionePdC;
	protected TextItem codiceCategoria;
	protected TextItem descrizioneCategoria;
	protected TextItem codiceCodUE;
	protected TextItem descrizioneCodUE;
	protected TextItem codiceCofog;
	protected TextItem descrizioneCofog;
	protected TextItem codiceGsa;
	protected TextItem descrizioneGsa;
	protected TextItem codiceMacroaggregato;
	protected TextItem descrizioneMacroaggregato;
	protected TextItem codiceMissione;
	protected TextItem descrizioneMissione;
	protected TextItem codiceNaturaRicorrente;
	protected TextItem descrizioneNaturaRicorrente;
	protected TextItem prenotazione;
	protected TextItem prenotazioneLiquidabile;
	protected TextItem codiceProgetto;
	protected TextItem descrizioneProgetto;						
	protected TextItem codiceProgramma;
	protected TextItem descrizioneProgramma;
	protected TextItem codiceTipoDebitoSiope;
	protected TextItem descrizioneTipoDebitoSiope;	
	protected TextItem codiceTipoFinanziamento;
	protected TextItem descrizioneTipoFinanziamento;
	protected TextItem codiceTipologia;
	protected TextItem descrizioneTipologia;
	protected TextItem codiceTitolo;
	protected TextItem descrizioneTitolo;
	
	public DettaglioMovimentiContabilia2Detail(String nomeEntita, ListaMovimentiContabilia2Item gridItem) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
		
		flgEntrataUscita = new HiddenItem("flgEntrataUscita");
		
		tipoMovimento = new TextItem("tipoMovimento", "Tipo movim.");
		tipoMovimento.setWidth(150);
		tipoMovimento.setColSpan(1);
		tipoMovimento.setStartRow(true);
			
		annoMovimento = new TextItem("annoMovimento", "Anno imp./acc.");
		annoMovimento.setWidth(150);
		annoMovimento.setColSpan(1);
		annoMovimento.setStartRow(true);
		
		numeroMovimento = new TextItem("numeroMovimento", "N° imp./acc.");
		numeroMovimento.setWidth(150);
		numeroMovimento.setColSpan(1);
		
		descrizioneMovimento = new TextAreaItem("descrizioneMovimento", "Descrizione imp./acc.");
		descrizioneMovimento.setWidth(630);
		descrizioneMovimento.setHeight(40);
		descrizioneMovimento.setColSpan(14);
		descrizioneMovimento.setStartRow(true);

		annoSub = new TextItem("annoSub", "Anno sub");
		annoSub.setWidth(150);
		annoSub.setColSpan(1);
		annoSub.setStartRow(true);
		annoSub.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		numeroSub = new TextItem("numeroSub", "N° sub");
		numeroSub.setWidth(150);
		numeroSub.setColSpan(1);
		numeroSub.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		descrizioneSub = new TextItem("descrizioneSub", "Descrizione sub");
		descrizioneSub.setWidth(630);
		descrizioneSub.setHeight(40);
		descrizioneSub.setColSpan(14);
		descrizioneSub.setStartRow(true);
		descrizioneSub.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		annoModifica = new TextItem("annoModifica", "Anno modifica");
		annoModifica.setWidth(150);
		annoModifica.setColSpan(1);
		annoModifica.setStartRow(true);
		annoModifica.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		numeroModifica = new TextItem("numeroModifica", "N° modifica");
		numeroModifica.setWidth(150);
		numeroModifica.setColSpan(1);
		numeroModifica.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		descrizioneModifica = new TextItem("descrizioneModifica", "Descr. modifica");
		descrizioneModifica.setWidth(630);
		descrizioneModifica.setHeight(40);
		descrizioneModifica.setColSpan(14);
		descrizioneModifica.setStartRow(true);
		descrizioneModifica.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
	
		importoModifica = new TextItem("importoModifica", "Importo aumento/riduzione imp./acc. (&euro;)");
		importoModifica.setWidth(150);
		importoModifica.setColSpan(1);
		importoModifica.setStartRow(true);
		importoModifica.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				importoModifica.setValue(NumberFormatUtility.getFormattedValue(importoModifica.getValueAsString()));
				return value != null && !"".equals(value);
			}
		});
		
		importoIniziale = new TextItem("importoIniziale", "Importo iniziale (&euro;)");
		importoIniziale.setWidth(150);
		importoIniziale.setColSpan(1);
		importoIniziale.setStartRow(true);
		importoIniziale.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				importoIniziale.setValue(NumberFormatUtility.getFormattedValue(importoIniziale.getValueAsString()));
				return value != null && !"".equals(value);
			}
		});
		
		importo = new TextItem("importo", "Importo attuale (&euro;)");
		importo.setWidth(150);
		importo.setColSpan(1);
		importo.setStartRow(true);
		importo.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				importo.setValue(NumberFormatUtility.getFormattedValue(importo.getValueAsString()));
				return true;
			}
		});
		
		numeroCapitolo = new TextItem("numeroCapitolo", "Cap.");
		numeroCapitolo.setColSpan(1);
		numeroCapitolo.setWidth(150);
		numeroCapitolo.setStartRow(true);
		
		numeroArticolo = new TextItem("numeroArticolo", "Art.");	
		numeroArticolo.setColSpan(1);
		numeroArticolo.setWidth(150);

		numeroUEB = new TextItem("numeroUEB", "UEB");
		numeroUEB.setColSpan(1);
		numeroUEB.setWidth(150);
				
		descrizioneCapitolo = new TextItem("descrizioneCapitolo", "Des. Cap.");
		descrizioneCapitolo.setWidth(630);
		descrizioneCapitolo.setColSpan(14);
		descrizioneCapitolo.setStartRow(true);
		
		descrizioneArticolo = new TextItem("descrizioneArticolo", "Des. Art.");
		descrizioneArticolo.setWidth(630);
		descrizioneArticolo.setColSpan(14);
		descrizioneArticolo.setStartRow(true);
	
		codiceCIG = new TextItem("codiceCIG", "CIG");
		codiceCIG.setWidth(150);
		codiceCIG.setColSpan(1);
		codiceCIG.setStartRow(true);
		
		motivoAssenzaCIG = new TextItem("motivoAssenzaCIG", "Motivo assenza CIG");
		motivoAssenzaCIG.setStartRow(true);
		motivoAssenzaCIG.setWidth(630);
		motivoAssenzaCIG.setColSpan(14);
		motivoAssenzaCIG.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});

		codiceCUP = new TextItem("codiceCUP", "CUP");
		codiceCUP.setWidth(150);
		codiceCUP.setColSpan(1);
		codiceCUP.setStartRow(true);
		
		codiceSoggetto = new TextItem("codiceSoggetto", "Soggetto");
		codiceSoggetto.setWidth(150);
		codiceSoggetto.setColSpan(1);
		codiceSoggetto.setStartRow(true);
		
		descrizioneSoggetto = new TextItem("descrizioneSoggetto");
		descrizioneSoggetto.setShowTitle(false);
		descrizioneSoggetto.setWidth(480);
		descrizioneSoggetto.setColSpan(13);
		
		codiceClasseSoggetto = new TextItem("codiceClasseSoggetto", "Classe soggetto");
		codiceClasseSoggetto.setWidth(150);
		codiceClasseSoggetto.setColSpan(1);
		codiceClasseSoggetto.setStartRow(true);
		
		descrizioneClasseSoggetto = new TextItem("descrizioneClasseSoggetto");
		descrizioneClasseSoggetto.setShowTitle(false);
		descrizioneClasseSoggetto.setWidth(480);
		descrizioneClasseSoggetto.setColSpan(13);
		
		codicePdC = new TextItem("codicePdC", "PdC finanz.");
		codicePdC.setWidth(150);
		codicePdC.setColSpan(1);
		codicePdC.setStartRow(true);
		
		descrizionePdC = new TextItem("descrizionePdC");
		descrizionePdC.setShowTitle(false);
		descrizionePdC.setWidth(480);
		descrizionePdC.setColSpan(13);
		
		codiceCategoria = new TextItem("codiceCategoria", "Categoria");
		codiceCategoria.setWidth(150);
		codiceCategoria.setColSpan(1);
		codiceCategoria.setStartRow(true);
		
		descrizioneCategoria = new TextItem("descrizioneCategoria");
		descrizioneCategoria.setShowTitle(false);
		descrizioneCategoria.setWidth(480);
		descrizioneCategoria.setColSpan(13);
		
		codiceCodUE = new TextItem("codiceCodUE", "Trans. UE");
		codiceCodUE.setWidth(150);
		codiceCodUE.setColSpan(1);
		codiceCodUE.setStartRow(true);
		
		descrizioneCodUE = new TextItem("descrizioneCodUE");
		descrizioneCodUE.setShowTitle(false);
		descrizioneCodUE.setWidth(480);
		descrizioneCodUE.setColSpan(13);
		
		codiceCofog = new TextItem("codiceCofog", "Cofog");
		codiceCofog.setWidth(150);
		codiceCofog.setColSpan(1);
		codiceCofog.setStartRow(true);
		
		descrizioneCofog = new TextItem("descrizioneCofog");
		descrizioneCofog.setShowTitle(false);
		descrizioneCofog.setWidth(480);
		descrizioneCofog.setColSpan(13);
		
		codiceGsa = new TextItem("codiceGsa", "Perim. sanitario");
		codiceGsa.setWidth(150);
		codiceGsa.setColSpan(1);
		codiceGsa.setStartRow(true);
		
		descrizioneGsa = new TextItem("descrizioneGsa");
		descrizioneGsa.setShowTitle(false);
		descrizioneGsa.setWidth(480);
		descrizioneGsa.setColSpan(13);
		
		codiceMacroaggregato = new TextItem("codiceMacroaggregato", "Macroaggregato");
		codiceMacroaggregato.setWidth(150);
		codiceMacroaggregato.setColSpan(1);
		codiceMacroaggregato.setStartRow(true);
		
		descrizioneMacroaggregato = new TextItem("descrizioneMacroaggregato");
		descrizioneMacroaggregato.setShowTitle(false);
		descrizioneMacroaggregato.setWidth(480);
		descrizioneMacroaggregato.setColSpan(13);
		
		codiceMissione = new TextItem("codiceMissione", "Missione");
		codiceMissione.setWidth(150);
		codiceMissione.setColSpan(1);
		codiceMissione.setStartRow(true);
		
		descrizioneMissione = new TextItem("descrizioneMissione");
		descrizioneMissione.setShowTitle(false);
		descrizioneMissione.setWidth(480);
		descrizioneMissione.setColSpan(13);
		
		codiceNaturaRicorrente = new TextItem("codiceNaturaRicorrente", "Natura ricorrente");
		codiceNaturaRicorrente.setWidth(150);
		codiceNaturaRicorrente.setColSpan(1);
		codiceNaturaRicorrente.setStartRow(true);
		
		descrizioneNaturaRicorrente = new TextItem("descrizioneNaturaRicorrente");
		descrizioneNaturaRicorrente.setShowTitle(false);
		descrizioneNaturaRicorrente.setWidth(480);
		descrizioneNaturaRicorrente.setColSpan(13);
		
		prenotazione = new TextItem("prenotazione", "Prenotazione");
		prenotazione.setWidth(150);
		prenotazione.setColSpan(1);
		prenotazione.setStartRow(true);
		
		prenotazioneLiquidabile = new TextItem("prenotazioneLiquidabile", "Prenot. liquidabile");
		prenotazioneLiquidabile.setWidth(150);
		prenotazioneLiquidabile.setColSpan(1);
		prenotazioneLiquidabile.setStartRow(true);
		
		codiceProgetto = new TextItem("codiceProgetto", "Progetto/iniziativa");
		codiceProgetto.setWidth(150);
		codiceProgetto.setColSpan(1);
		codiceProgetto.setStartRow(true);
		
		descrizioneProgetto = new TextItem("descrizioneProgetto");
		descrizioneProgetto.setShowTitle(false);
		descrizioneProgetto.setWidth(480);
		descrizioneProgetto.setColSpan(13);
		
		codiceProgramma = new TextItem("codiceProgramma", "Programma");
		codiceProgramma.setWidth(150);
		codiceProgramma.setColSpan(1);
		codiceProgramma.setStartRow(true);
		
		descrizioneProgramma = new TextItem("descrizioneProgramma");
		descrizioneProgramma.setShowTitle(false);
		descrizioneProgramma.setWidth(480);
		descrizioneProgramma.setColSpan(13);
		
		codiceTipoDebitoSiope = new TextItem("codiceTipoDebitoSiope", "Debito Siope");
		codiceTipoDebitoSiope.setWidth(150);
		codiceTipoDebitoSiope.setColSpan(1);
		codiceTipoDebitoSiope.setStartRow(true);
		
		descrizioneTipoDebitoSiope = new TextItem("descrizioneTipoDebitoSiope");
		descrizioneTipoDebitoSiope.setShowTitle(false);
		descrizioneTipoDebitoSiope.setWidth(480);
		descrizioneTipoDebitoSiope.setColSpan(13);
		
		codiceTipoFinanziamento = new TextItem("codiceTipoFinanziamento", "Tipo finanz.");
		codiceTipoFinanziamento.setWidth(150);
		codiceTipoFinanziamento.setColSpan(1);
		codiceTipoFinanziamento.setStartRow(true);
		
		descrizioneTipoFinanziamento = new TextItem("descrizioneTipoFinanziamento");
		descrizioneTipoFinanziamento.setShowTitle(false);
		descrizioneTipoFinanziamento.setWidth(480);
		descrizioneTipoFinanziamento.setColSpan(13);
		
		codiceTipologia = new TextItem("codiceTipologia", "Tipologia");
		codiceTipologia.setWidth(150);
		codiceTipologia.setColSpan(1);
		codiceTipologia.setStartRow(true);
		
		descrizioneTipologia = new TextItem("descrizioneTipologia");
		descrizioneTipologia.setShowTitle(false);
		descrizioneTipologia.setWidth(480);
		descrizioneTipologia.setColSpan(13);
		
		codiceTitolo = new TextItem("codiceTitolo", "Titolo");
		codiceTitolo.setWidth(150);
		codiceTitolo.setColSpan(1);
		codiceTitolo.setStartRow(true);
		
		descrizioneTitolo = new TextItem("descrizioneTitolo");
		descrizioneTitolo.setShowTitle(false);
		descrizioneTitolo.setWidth(480);
		descrizioneTitolo.setColSpan(13);
		
		mDynamicForm.setFields(
			flgEntrataUscita, 
			tipoMovimento,
			annoMovimento,
			numeroMovimento,
			descrizioneMovimento,
			annoSub,
			numeroSub,
			descrizioneSub,
			annoModifica,	
			numeroModifica,	
			descrizioneModifica,	
			importoModifica,
			importoIniziale,
			importo,		
			numeroCapitolo,
			numeroArticolo,
			numeroUEB,
			descrizioneCapitolo,
			descrizioneArticolo,
			codiceCIG, 
			motivoAssenzaCIG,
			codiceCUP,
			codiceSoggetto,	descrizioneSoggetto,
			codiceClasseSoggetto, descrizioneClasseSoggetto,
			codicePdC, descrizionePdC,
			codiceCategoria, descrizioneCategoria,
			codiceCodUE, descrizioneCodUE,
			codiceCofog, descrizioneCofog,
			codiceGsa, descrizioneGsa,
			codiceMacroaggregato, descrizioneMacroaggregato,
			codiceMissione, descrizioneMissione,
			codiceNaturaRicorrente, descrizioneNaturaRicorrente,
			prenotazione,
			prenotazioneLiquidabile,
			codiceProgetto, descrizioneProgetto,						
			codiceProgramma, descrizioneProgramma,
			codiceTipoDebitoSiope, descrizioneTipoDebitoSiope,	
			codiceTipoFinanziamento, descrizioneTipoFinanziamento,
			codiceTipologia, descrizioneTipologia,
			codiceTitolo, descrizioneTitolo
		);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(false);
		annoMovimento.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		annoMovimento.setTabIndex(-1);
		numeroMovimento.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		numeroMovimento.setTabIndex(-1);		
		annoSub.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		annoSub.setTabIndex(-1);
		numeroSub.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		numeroSub.setTabIndex(-1);		
		annoModifica.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		annoModifica.setTabIndex(-1);
		numeroModifica.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		numeroModifica.setTabIndex(-1);		
	}
	
}