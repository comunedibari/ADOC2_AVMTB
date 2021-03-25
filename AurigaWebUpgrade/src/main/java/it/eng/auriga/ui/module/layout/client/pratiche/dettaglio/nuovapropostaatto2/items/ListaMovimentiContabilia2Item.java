package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public class ListaMovimentiContabilia2Item extends GridItem {
	
	protected ListGridField flgEntrataUscita;
	protected ListGridField tipoMovimento;
	protected ListGridField annoMovimento;
	protected ListGridField numeroMovimento;
	protected ListGridField descrizioneMovimento;
	protected ListGridField annoSub;
	protected ListGridField numeroSub;
	protected ListGridField descrizioneSub;	
	protected ListGridField annoModifica;	
	protected ListGridField numeroModifica;	
	protected ListGridField descrizioneModifica;	
	protected ListGridField importoModifica;
	protected ListGridField importoIniziale;
	protected ListGridField importo;		
	protected ListGridField numeroCapitolo;
	protected ListGridField numeroArticolo;
	protected ListGridField numeroUEB;
	protected ListGridField descrizioneCapitolo;
	protected ListGridField descrizioneArticolo;
	protected ListGridField codiceCIG;
	protected ListGridField motivoAssenzaCIG;
	protected ListGridField codiceCUP;
	protected ListGridField codiceSoggetto;	
	protected ListGridField descrizioneSoggetto;
	protected ListGridField codiceClasseSoggetto;
	protected ListGridField descrizioneClasseSoggetto;	
	protected ListGridField codicePdC;
	protected ListGridField descrizionePdC;
	protected ListGridField codiceCategoria;
	protected ListGridField descrizioneCategoria;
	protected ListGridField codiceCodUE;
	protected ListGridField descrizioneCodUE;
	protected ListGridField codiceCofog;
	protected ListGridField descrizioneCofog;
	protected ListGridField codiceGsa;
	protected ListGridField descrizioneGsa;
	protected ListGridField codiceMacroaggregato;
	protected ListGridField descrizioneMacroaggregato;
	protected ListGridField codiceMissione;
	protected ListGridField descrizioneMissione;
	protected ListGridField codiceNaturaRicorrente;
	protected ListGridField descrizioneNaturaRicorrente;
	protected ListGridField prenotazione;
	protected ListGridField prenotazioneLiquidabile;
	protected ListGridField codiceProgetto;
	protected ListGridField descrizioneProgetto;						
	protected ListGridField codiceProgramma;
	protected ListGridField descrizioneProgramma;
	protected ListGridField codiceTipoDebitoSiope;
	protected ListGridField descrizioneTipoDebitoSiope;	
	protected ListGridField codiceTipoFinanziamento;
	protected ListGridField descrizioneTipoFinanziamento;
	protected ListGridField codiceTipologia;
	protected ListGridField descrizioneTipologia;
	protected ListGridField codiceTitolo;
	protected ListGridField descrizioneTitolo;
	
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	protected ControlListGridField detailButtonField;
	
	protected ToolStrip totaliToolStrip;
	protected HLayout totaleEntrateLayout;
	protected Label totaleEntrateLabel;
	protected HLayout totaleUsciteLayout;
	protected Label totaleUsciteLabel;

	public ListaMovimentiContabilia2Item(String name) {
		
		super(name, "listaMovimentiContabilia2");
		
		setShowPreference(true);
		setShowEditButtons(false);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
				
		flgEntrataUscita = new ListGridField("flgEntrataUscita");
		flgEntrataUscita.setHidden(true);
		flgEntrataUscita.setCanHide(false);
		
		tipoMovimento = new ListGridField("tipoMovimento", "Tipo movim.");
		
		annoMovimento = new ListGridField("annoMovimento", "Anno imp./acc.");
		annoMovimento.setType(ListGridFieldType.INTEGER);
		
		numeroMovimento = new ListGridField("numeroMovimento", "N° imp./acc.");
		numeroMovimento.setType(ListGridFieldType.INTEGER);
		
		descrizioneMovimento = new ListGridField("descrizioneMovimento", "Descrizione imp./acc.");
		
		annoSub = new ListGridField("annoSub", "Anno sub");
		annoSub.setType(ListGridFieldType.INTEGER);
		
		numeroSub = new ListGridField("numeroSub", "N° sub");
		numeroSub.setType(ListGridFieldType.INTEGER);
		
		descrizioneSub = new ListGridField("descrizioneSub", "Descrizione sub");
		
		annoModifica = new ListGridField("annoModifica", "Anno modifica");
		annoModifica.setType(ListGridFieldType.INTEGER);
		
		numeroModifica = new ListGridField("numeroModifica", "N° modifica");
		numeroModifica.setType(ListGridFieldType.INTEGER);
		
		descrizioneModifica = new ListGridField("descrizioneModifica", "Descr. modifica");
		
		importoModifica = new ListGridField("importoModifica", "Importo aumento/riduzione imp./acc. (&euro;)");
		importoModifica.setType(ListGridFieldType.FLOAT);
		importoModifica.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importoModifica"));				
			}
		});
			
		importoIniziale = new ListGridField("importoIniziale", "Importo iniziale (&euro;)");
		importoIniziale.setType(ListGridFieldType.FLOAT);
		importoIniziale.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importoIniziale"));				
			}
		});
		
		importo = new ListGridField("importo", "Importo attuale (&euro;)");
		importo.setType(ListGridFieldType.FLOAT);
		importo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importo"));				
			}
		});
		
		numeroCapitolo = new ListGridField("numeroCapitolo", "Cap.");
		numeroCapitolo.setType(ListGridFieldType.INTEGER);
		
		numeroArticolo = new ListGridField("numeroArticolo", "Art.");
		numeroArticolo.setType(ListGridFieldType.INTEGER);
		
		numeroUEB = new ListGridField("numeroUEB", "UEB");
		numeroUEB.setType(ListGridFieldType.INTEGER);
		
		descrizioneCapitolo = new ListGridField("descrizioneCapitolo", "Des. Cap.");
		
		descrizioneArticolo = new ListGridField("descrizioneArticolo", "Des. Art.");
		
		codiceCIG = new ListGridField("codiceCIG", "CIG");
		
		motivoAssenzaCIG = new ListGridField("motivoAssenzaCIG", "Motivo assenza CIG");
		
		codiceCUP = new ListGridField("codiceCUP", "CUP");
		
		codiceSoggetto = new ListGridField("codiceSoggetto", "Cod. soggetto");
		codiceSoggetto.setHidden(true);
		codiceSoggetto.setCanHide(false);
		
		descrizioneSoggetto = new ListGridField("descrizioneSoggetto", "Soggetto");
		descrizioneSoggetto.setAttribute("custom", true);
		descrizioneSoggetto.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceSoggetto", "descrizioneSoggetto");				
			}
		});		
		
		codiceClasseSoggetto = new ListGridField("codiceClasseSoggetto", "Cod. classe soggetto");
		codiceClasseSoggetto.setHidden(true);
		codiceClasseSoggetto.setCanHide(false);
		
		descrizioneClasseSoggetto = new ListGridField("descrizioneClasseSoggetto", "Classe soggetto");
		descrizioneClasseSoggetto.setAttribute("custom", true);
		descrizioneClasseSoggetto.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceClasseSoggetto", "descrizioneClasseSoggetto");				
			}
		});		
		
		codicePdC = new ListGridField("codicePdC", "Cod. PdC finanz.");
		codicePdC.setHidden(true);
		codicePdC.setCanHide(false);
		
		descrizionePdC = new ListGridField("descrizionePdC", "PdC finanz.");
		descrizionePdC.setAttribute("custom", true);
		descrizionePdC.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codicePdC", "descrizionePdC");				
			}
		});		
		
		codiceCategoria = new ListGridField("codiceCategoria", "Cod. categoria");
		codiceCategoria.setHidden(true);
		codiceCategoria.setCanHide(false);
		
		descrizioneCategoria = new ListGridField("descrizioneCategoria", "Categoria");
		descrizioneCategoria.setAttribute("custom", true);
		descrizioneCategoria.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceCategoria", "descrizioneCategoria");
			}
		});		
		
		codiceCodUE = new ListGridField("codiceCodUE", "Cod. trans. UE");
		codiceCodUE.setHidden(true);
		codiceCodUE.setCanHide(false);
		
		descrizioneCodUE = new ListGridField("descrizioneCodUE", "Trans. UE");
		descrizioneCodUE.setAttribute("custom", true);
		descrizioneCodUE.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceCodUE", "descrizioneCodUE");
			}
		});		
		
		codiceCofog = new ListGridField("codiceCofog", "Cod. Cofog");
		codiceCofog.setHidden(true);
		codiceCofog.setCanHide(false);
		
		descrizioneCofog = new ListGridField("descrizioneCofog", "Cofog");
		descrizioneCofog.setAttribute("custom", true);
		descrizioneCofog.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceCofog", "descrizioneCofog");
			}
		});		
		
		codiceGsa = new ListGridField("codiceGsa", "Cod. perim. sanitario");
		codiceGsa.setHidden(true);
		codiceGsa.setCanHide(false);
		
		descrizioneGsa = new ListGridField("descrizioneGsa", "Perim. sanitario");
		descrizioneGsa.setAttribute("custom", true);
		descrizioneGsa.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceGsa", "descrizioneGsa");
			}
		});		
		
		codiceMacroaggregato = new ListGridField("codiceMacroaggregato", "Cod. macroaggregato");
		codiceMacroaggregato.setHidden(true);
		codiceMacroaggregato.setCanHide(false);
		
		descrizioneMacroaggregato = new ListGridField("descrizioneMacroaggregato", "Macroaggregato");
		descrizioneMacroaggregato.setAttribute("custom", true);
		descrizioneMacroaggregato.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceMacroaggregato", "descrizioneMacroaggregato");
			}
		});	
		
		codiceMissione = new ListGridField("codiceMissione", "Cod. missione");
		codiceMissione.setHidden(true);
		codiceMissione.setCanHide(false);
		
		descrizioneMissione = new ListGridField("descrizioneMissione", "Missione");
		descrizioneMissione.setAttribute("custom", true);
		descrizioneMissione.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceMissione", "descrizioneMissione");
			}
		});		
		
		codiceNaturaRicorrente = new ListGridField("codiceNaturaRicorrente", "Cod. natura ricorrente");
		codiceNaturaRicorrente.setHidden(true);
		codiceNaturaRicorrente.setCanHide(false);
		
		descrizioneNaturaRicorrente = new ListGridField("descrizioneNaturaRicorrente", "Natura ricorrente");
		descrizioneNaturaRicorrente.setAttribute("custom", true);
		descrizioneNaturaRicorrente.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceNaturaRicorrente", "descrizioneNaturaRicorrente");
			}
		});
		
		prenotazione = new ListGridField("prenotazione", "Prenotazione");
		
		prenotazioneLiquidabile = new ListGridField("prenotazioneLiquidabile", "Prenot. liquidabile");
		
		codiceProgetto = new ListGridField("codiceProgetto", "Cod. progetto/iniziativa");
		codiceProgetto.setHidden(true);
		codiceProgetto.setCanHide(false);
		
		descrizioneProgetto = new ListGridField("descrizioneProgetto", "Progetto/iniziativa");
		descrizioneProgetto.setAttribute("custom", true);
		descrizioneProgetto.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceProgetto", "descrizioneProgetto");				
			}
		});	
		
		codiceProgramma = new ListGridField("codiceProgramma", "Cod. programma");
		codiceProgramma.setHidden(true);
		codiceProgramma.setCanHide(false);
		
		descrizioneProgramma = new ListGridField("descrizioneProgramma", "Programma");
		descrizioneProgramma.setAttribute("custom", true);
		descrizioneProgramma.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceProgramma", "descrizioneProgramma");
			}
		});	
		
		codiceTipoDebitoSiope = new ListGridField("codiceTipoDebitoSiope", "Cod. debito Siope");
		codiceTipoDebitoSiope.setHidden(true);
		codiceTipoDebitoSiope.setCanHide(false);
		
		descrizioneTipoDebitoSiope = new ListGridField("descrizioneTipoDebitoSiope", "Debito Siope");
		descrizioneTipoDebitoSiope.setAttribute("custom", true);
		descrizioneTipoDebitoSiope.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceTipoDebitoSiope", "descrizioneTipoDebitoSiope");				
			}
		});	
		
		codiceTipoFinanziamento = new ListGridField("codiceTipoFinanziamento", "Cod. tipo finanz.");
		codiceTipoFinanziamento.setHidden(true);
		codiceTipoFinanziamento.setCanHide(false);
		
		descrizioneTipoFinanziamento = new ListGridField("descrizioneTipoFinanziamento", "Tipo finanz.");
		descrizioneTipoFinanziamento.setAttribute("custom", true);
		descrizioneTipoFinanziamento.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceTipoFinanziamento", "descrizioneTipoFinanziamento");
			}
		});		
		
		codiceTipologia = new ListGridField("codiceTipologia", "Cod. tipologia");
		codiceTipologia.setHidden(true);
		codiceTipologia.setCanHide(false);
		
		descrizioneTipologia = new ListGridField("descrizioneTipologia", "Tipologia");
		descrizioneTipologia.setAttribute("custom", true);
		descrizioneTipologia.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceTipologia", "descrizioneTipologia");
			}
		});		
		
		codiceTitolo = new ListGridField("codiceTitolo", "Cod. titolo");
		codiceTitolo.setHidden(true);
		codiceTitolo.setCanHide(false);
		
		descrizioneTitolo = new ListGridField("descrizioneTitolo", "Titolo");
		descrizioneTitolo.setAttribute("custom", true);
		descrizioneTitolo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceTitolo", "descrizioneTitolo");
			}
		});		
		
		setGridFields(
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
			codiceSoggetto,
			descrizioneSoggetto,
			codiceClasseSoggetto,
			descrizioneClasseSoggetto,
			codicePdC,
			descrizionePdC,
			codiceCategoria,
			descrizioneCategoria,
			codiceCodUE,
			descrizioneCodUE,
			codiceCofog,
			descrizioneCofog,
			codiceGsa,
			descrizioneGsa,
			codiceMacroaggregato,
			descrizioneMacroaggregato,
			codiceMissione,
			descrizioneMissione,
			codiceNaturaRicorrente,
			descrizioneNaturaRicorrente,
			prenotazione,
			prenotazioneLiquidabile,
			codiceProgetto, 
			descrizioneProgetto,						
			codiceProgramma,
			descrizioneProgramma,
			codiceTipoDebitoSiope, 
			descrizioneTipoDebitoSiope,	
			codiceTipoFinanziamento,
			descrizioneTipoFinanziamento,
			codiceTipologia,
			descrizioneTipologia,
			codiceTitolo,
			descrizioneTitolo
		);				
	}
	
	public String getDescrizioneWithCodice(Record record, String codiceFieldName, String descrizioneFieldName) {
		String codice = record.getAttribute(codiceFieldName);
		String descrizione = record.getAttribute(descrizioneFieldName);
		if(codice != null && !"".equals(codice) && descrizione != null && !"".equals(descrizione)) {
			return codice + " - " + descrizione;
		} else if(descrizione!= null && !"".equals(descrizione)) {
			return descrizione;													
		} else if(codice != null && !"".equals(codice)) {
			return codice;
		}	
		return null;
	}
	
	@Override
	public void init(FormItem item) {
		
		super.init(item);
		
		totaliToolStrip = new ToolStrip();
		totaliToolStrip.setBackgroundColor("transparent");
		totaliToolStrip.setBackgroundImage("blank.png");
		totaliToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		totaliToolStrip.setBorder("0px");
		totaliToolStrip.setWidth100();           
		totaliToolStrip.setHeight(30);
		
		totaleEntrateLayout = new HLayout();
		totaleEntrateLayout.setOverflow(Overflow.VISIBLE);
		totaleEntrateLayout.setWidth(5);
	
		totaleEntrateLabel = new Label();
		totaleEntrateLabel.setAlign(Alignment.CENTER);
		totaleEntrateLabel.setValign(VerticalAlignment.CENTER);
		totaleEntrateLabel.setWrap(false);
		
		totaleEntrateLayout.setMembers(totaleEntrateLabel);
		
		totaleUsciteLayout = new HLayout();
		totaleUsciteLayout.setOverflow(Overflow.VISIBLE);
		totaleUsciteLayout.setWidth(5);
	
		totaleUsciteLabel = new Label();
		totaleUsciteLabel.setAlign(Alignment.CENTER);
		totaleUsciteLabel.setValign(VerticalAlignment.CENTER);
		totaleUsciteLabel.setWrap(false);
		
		totaleUsciteLayout.setMembers(totaleUsciteLabel);
		
		totaliToolStrip.addMember(totaleEntrateLayout);
		totaliToolStrip.addFill();
		totaliToolStrip.addMember(totaleUsciteLayout);
		
		layout.addMember(totaliToolStrip);			
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali
		grid.addSort(new SortSpecifier("flgEntrataUscita", SortDirection.DESCENDING));
		grid.addSort(new SortSpecifier("annoMovimento", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroMovimento", SortDirection.ASCENDING));	
		grid.addSort(new SortSpecifier("annoSub", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroSub", SortDirection.ASCENDING));	
		grid.addSort(new SortSpecifier("annoModifica", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroModifica", SortDirection.ASCENDING));			
		grid.addSort(new SortSpecifier("descrizioneMovimento", SortDirection.ASCENDING));
		return grid;		
	}
	
	@Override
	protected void updateGridItemValue() {
		super.updateGridItemValue();
		aggiornaTotali();
	}
	
	public void aggiornaTotali() {
		/*
		if(grid.getRecords().length > 0) {			
			String pattern = "#,##0.00";
			float totaleEntrate = 0;
			float totaleUscite = 0;
			for(int i = 0; i < grid.getRecords().length; i++) {
				Record record = grid.getRecords()[i];
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				String tipoMovimento = record.getAttribute("tipoMovimento") != null ? record.getAttribute("tipoMovimento") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				//TODO correggere il calcolo dei totali considerando gli importi solo degli impegni/accertamenti padre, e gli importi dei sub e delle modifiche solo se presenti singolarmente (senza padre)
				if("E".equals(flgEntrataUscita) && "Accertamento".equalsIgnoreCase(tipoMovimento)) {
					totaleEntrate += importo;
				} else if("U".equals(flgEntrataUscita) && "Impegno".equalsIgnoreCase(tipoMovimento)) {
					totaleUscite += importo;
				}
			}
			totaleEntrateLabel.setContents("<span style=\"color:green\"><b>Totale entrate " + NumberFormat.getFormat(pattern).format(totaleEntrate) + " euro</b></span>");
			totaleUsciteLabel.setContents("<span style=\"color:#37505f\"><b>Totale uscite " + NumberFormat.getFormat(pattern).format(totaleUscite) + " euro</b></span>");
			totaliToolStrip.show();
		} else {		
			totaleEntrateLabel.setContents("");
			totaleUsciteLabel.setContents("");
			totaliToolStrip.hide();
		}
		*/
		totaleEntrateLabel.setContents("");
		totaleUsciteLabel.setContents("");
		totaliToolStrip.hide();
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();				
		ToolStripButton refreshListButton = new ToolStripButton();   
		refreshListButton.setIcon("buttons/refreshList.png");   
		refreshListButton.setIconSize(16);
		refreshListButton.setPrefix("refreshListButton");
		refreshListButton.setPrompt(I18NUtil.getMessages().refreshListButton_prompt());
		refreshListButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickRefreshListButton();   	
			}   
		});  
		if (isShowRefreshListButton()) {
			buttons.add(refreshListButton);
		}		
		ToolStripButton exportXlsButton = new ToolStripButton();   
		exportXlsButton.setIcon("export/xls.png"); 
		exportXlsButton.setIconSize(16);
		exportXlsButton.setPrefix("exportXlsButton");
		exportXlsButton.setPrompt("Esporta in formato xls");
		exportXlsButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickExportXlsButton();   	
			}   
		});  
		if (isShowExportXlsButton()) {
			buttons.add(exportXlsButton);
		}
		return buttons;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {	
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {					
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("refreshListButton"))
					{
						if (isShowRefreshListButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
					}	
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("exportXlsButton"))
					{
						if (isShowExportXlsButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
					}
				}
			}	
			layoutListaSelectItem.show();
			saveLayoutListaButton.show();
			getGrid().setCanReorderRecords(canEdit);
			redrawRecordButtons();
		}
	}	
	
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();			
		if(detailButtonField == null) {
			detailButtonField = buildDetailButtonField();					
		}
		buttonsList.add(detailButtonField);
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
				event.cancel();
				onClickDetailButton(event.getRecord());				
			}
		});		
		return detailButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		final DettaglioMovimentiContabilia2Window lDettaglioMovimentiContabilia2Window = new DettaglioMovimentiContabilia2Window(this, "dettaglioMovimentiContabilia2Window", record);
		lDettaglioMovimentiContabilia2Window.show();
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {

		int length = fields.length;
		length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons			
		
		ListaBean configLista = Layout.getListConfig("listaMovimentiContabilia2");
		
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
					
					field.setCanEdit(false);

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
	
	protected String buildImgButtonHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}

	protected String buildIconHtml(String src) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
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
	
	public boolean isShowDatiStoriciButton() {
		return false;
	}
	
	public void onClickDatiStoriciButton() {
		
	}
	
	public boolean isShowRefreshListButton() {
		return true;
	}
	
	public void onClickRefreshListButton() {
		
	}
	
	public boolean isShowExportXlsButton() {
		return true;
	}
	
	public void onClickExportXlsButton() {		

		if (getGrid().getDataAsRecordList() != null && getGrid().getDataAsRecordList().getLength() <= 0) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando la lista è vuota", "", MessageType.ERROR));
			return;
		}
		
		if (getGrid().isGrouped()) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando c'è un raggruppamento attivo sulla lista", "", MessageType.ERROR));
			return;
		} 
		
		LinkedHashMap<String, String> mappa = createFieldsMap(true);
		String[] fields = new String[mappa.keySet().size()];
		String[] headers = new String[mappa.keySet().size()];
		int n = 0;
		for (String key : mappa.keySet()) {
			fields[n] = key;
			headers[n] = mappa.get(key);
			n++;
		}
		
		final Record record = new Record();
		record.setAttribute("nomeEntita", "listaMovimentiContabilia2");
		record.setAttribute("formatoExport", "xls");
		record.setAttribute("criteria", (String) null);
		record.setAttribute("fields", fields);
		record.setAttribute("headers", headers);
		record.setAttribute("records", extractRecords(fields));
		record.setAttribute("overflow", false);
		
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.performCustomOperation("export", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					String filename = "Results." + record.getAttribute("formatoExport");
					String url = response.getData()[0].getAttribute("tempFileOut");
					// se l'esportazione ha restituito un uri allora lancio il download del documento generato, altrimenti
					// vuol dire che è abilitato per questo datasource l'esportazione asincrona e quindi la generazione è stata schedulata
					if (url != null) {
						Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename)
								+ "&url=" + URL.encode(url));
					}
				}
				/*
				 * else { Layout.addMessage(new MessageBean("Si è verificato un errore durante l'esportazione della lista", "", MessageType.ERROR)); }
				 */
			}
		}, new DSRequest());
	}	
	
	protected LinkedHashMap<String, String> createFieldsMap(Boolean includeXord) {
		LinkedHashMap<String, String> mappa = new LinkedHashMap<String, String>();

		for (int i = 0; i < getGrid().getFields().length; i++) {

			ListGridField field = getGrid().getFields()[i];
			String fieldName = field.getName();

			if (fieldName.endsWith("XOrd") && includeXord) {

				String fieldTitle = field.getTitle() + " (Ordinamento)";

				if (!(getGrid().getFieldByName(fieldName) instanceof ControlListGridField)  && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
					mappa.put(fieldName, fieldTitle);
				}
			}

			if (fieldName.endsWith("XOrd")) {
				fieldName = fieldName.substring(0, fieldName.lastIndexOf("XOrd"));
			}
			String fieldTitle = field.getTitle();

			/* ho messo dopo la modifica dei fieldName che finiscono in XOrd, perchè non voglio che nn siano cambiati */
			if (field.getDisplayField() != null)
				fieldName = field.getDisplayField();

			if (!(getGrid().getFieldByName(fieldName) instanceof ControlListGridField) && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
				mappa.put(fieldName, fieldTitle);
			}
		}
		return mappa;
	}
	
	protected Record[] extractRecords(String[] fields) {
		Record[] records = new Record[getGrid().getRecords().length];
		for (int i = 0; i < getGrid().getRecords().length; i++) {
			Record rec = new Record();
			for (String fieldName : fields) {
				rec.setAttribute(fieldName, getGrid().getRecords()[i].getAttribute(fieldName));
			}
			// Devo gestire nell'esportazione le colonne che hanno un CellFormatter settato
			rec.setAttribute("descrizioneSoggetto", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceSoggetto", "descrizioneSoggetto"));
			rec.setAttribute("descrizioneClasseSoggetto", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceClasseSoggetto", "descrizioneClasseSoggetto"));
			rec.setAttribute("descrizionePdC", getDescrizioneWithCodice(getGrid().getRecords()[i], "codicePdC", "descrizionePdC"));
			rec.setAttribute("descrizioneCategoria", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceCategoria", "descrizioneCategoria"));
			rec.setAttribute("descrizioneCodUE", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceCodUE", "descrizioneCodUE"));
			rec.setAttribute("descrizioneCofog", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceCofog", "descrizioneCofog"));
			rec.setAttribute("descrizioneGsa", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceGsa", "descrizioneGsa"));
			rec.setAttribute("descrizioneMacroaggregato", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceMacroaggregato", "descrizioneMacroaggregato"));
			rec.setAttribute("descrizioneMissione", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceMissione", "descrizioneMissione"));
			rec.setAttribute("descrizioneNaturaRicorrente", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceNaturaRicorrente", "descrizioneNaturaRicorrente"));
			rec.setAttribute("descrizioneProgetto", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceProgetto", "descrizioneProgetto"));
			rec.setAttribute("descrizioneProgramma", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceProgramma", "descrizioneProgramma"));
			rec.setAttribute("descrizioneTipoDebitoSiope", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceTipoDebitoSiope", "descrizioneTipoDebitoSiope"));
			rec.setAttribute("descrizioneTipoFinanziamento", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceTipoFinanziamento", "descrizioneTipoFinanziamento"));
			rec.setAttribute("descrizioneTipologia", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceTipologia", "descrizioneTipologia"));
			rec.setAttribute("descrizioneTitolo", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceTitolo", "descrizioneTitolo"));			
			records[i] = rec;
		}
		return records;
	}
	
}