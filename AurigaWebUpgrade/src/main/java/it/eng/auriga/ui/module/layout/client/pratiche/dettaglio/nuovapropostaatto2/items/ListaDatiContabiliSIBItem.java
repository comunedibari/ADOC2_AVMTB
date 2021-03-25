package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
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

public class ListaDatiContabiliSIBItem extends GridItem {
	
	protected ListGridField flgEntrataUscita;
	protected ListGridField tipoDettaglio;
	protected ListGridField annoCompetenza;
	protected ListGridField numeroDettaglio;
	protected ListGridField subNumero;
	protected ListGridField annoCrono;
	protected ListGridField numeroCrono;
	protected ListGridField subCrono;
	protected ListGridField importo;
	protected ListGridField oggetto;
	protected ListGridField codiceCIG;
	protected ListGridField codiceCUP;
	protected ListGridField codiceGAMIPBM;
	protected ListGridField codUnitaOrgCdR;
	protected ListGridField desUnitaOrgCdR;	
	protected ListGridField capitolo;
	protected ListGridField articolo;
	protected ListGridField numero;
	protected ListGridField descrizioneCapitolo;
	protected ListGridField liv5PdC;
	protected ListGridField descrizionePdC;
	protected ListGridField annoEsigibilitaDebito;
	protected ListGridField dataEsigibilitaDa;
	protected ListGridField dataEsigibilitaA;
	protected ListGridField dataScadenzaEntrata;
	protected ListGridField dichiarazioneDL78;	
	protected ListGridField tipoFinanziamento;
	protected ListGridField codTipoFinanziamento; 
	protected ListGridField denominazioneSogg;
	protected ListGridField codFiscaleSogg;
	protected ListGridField codPIVASogg;
	protected ListGridField indirizzoSogg;
	protected ListGridField cap;
	protected ListGridField localita;
	protected ListGridField provincia;
	protected ListGridField flgValidato;
	protected ListGridField flgSoggDaPubblicare;
	protected ListGridField settore;
	protected ListGridField descrizioneSettore;
	
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	
	protected ToolStrip totaliToolStrip;
	protected HLayout totaleEntrateLayout;
	protected Label totaleEntrateLabel;
	protected HLayout totaleUsciteLayout;
	protected Label totaleUsciteLabel;

	public ListaDatiContabiliSIBItem(String name) {
		
		super(name, "listaDatiContabiliSIB");
		
		setShowPreference(true);
		setShowEditButtons(false);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
				
		flgEntrataUscita = new ListGridField("flgEntrataUscita");
		flgEntrataUscita.setHidden(true);
		flgEntrataUscita.setCanHide(false);
		  
		tipoDettaglio = new ListGridField("tipoDettaglio", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_title());
		LinkedHashMap<String, String> tipoDettaglioValueMap = new LinkedHashMap<String, String>();
		tipoDettaglioValueMap.put("IPG", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_IPG_value()); //impegno
		tipoDettaglioValueMap.put("ACC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_ACC_value()); //accertamento
		tipoDettaglioValueMap.put("VIP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VIP_value()); //variazione di impegno
		tipoDettaglioValueMap.put("VAC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VAC_value()); //variazione di accertamento
		tipoDettaglioValueMap.put("SIP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_SIP_value()); //subimpegno
		tipoDettaglioValueMap.put("SAC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_SAC_value()); //subaccertamento
		tipoDettaglioValueMap.put("VSI", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VSI_value()); //variazione di subimpegno
		tipoDettaglioValueMap.put("VSA", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_VSA_value()); //variazione di subaccertamento
		tipoDettaglioValueMap.put("COP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_COP_value()); //cronoprogramma
		tipoDettaglioValueMap.put("SCP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoDettaglio_SCP_value()); //subcronoprogramma
		tipoDettaglio.setValueMap(tipoDettaglioValueMap);
		
		annoCompetenza = new ListGridField("annoCompetenza", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_annoCompetenza_title());
		annoCompetenza.setType(ListGridFieldType.INTEGER);
		
		numeroDettaglio = new ListGridField("numeroDettaglio", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_numeroDettaglio_title());
		numeroDettaglio.setType(ListGridFieldType.INTEGER);
		
		subNumero = new ListGridField("subNumero", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_subNumero_title());
		subNumero.setType(ListGridFieldType.INTEGER);
		
		annoCrono = new ListGridField("annoCrono", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_annoCrono_title());
		annoCrono.setType(ListGridFieldType.INTEGER);
		
		numeroCrono = new ListGridField("numeroCrono", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_numeroCrono_title());
		numeroCrono.setType(ListGridFieldType.INTEGER);
			
		subCrono = new ListGridField("subCrono", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_subCrono_title());
		subCrono.setType(ListGridFieldType.INTEGER);
		
		importo = new ListGridField("importo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_importo_title());
		importo.setType(ListGridFieldType.FLOAT);
		importo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importo"));				
			}
		});
		
		oggetto = new ListGridField("oggetto", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_oggetto_title());
		
		codiceCIG = new ListGridField("codiceCIG", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codiceCIG_title());
		
		codiceCUP = new ListGridField("codiceCUP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codiceCUP_title());
		
		codiceGAMIPBM = new ListGridField("codiceGAMIPBM", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codiceGAMIPBM_title());
		
		codUnitaOrgCdR = new ListGridField("codUnitaOrgCdR");
		codUnitaOrgCdR.setType(ListGridFieldType.INTEGER);
		codUnitaOrgCdR.setHidden(true);
		codUnitaOrgCdR.setCanHide(false);
		
		desUnitaOrgCdR = new ListGridField("desUnitaOrgCdR", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_unitaOrgCdR_title());
		desUnitaOrgCdR.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				return record.getAttributeAsInt("codUnitaOrgCdR");
			}
		});
		desUnitaOrgCdR.setAttribute("custom", true);
		desUnitaOrgCdR.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("codUnitaOrgCdR") != null && !"".equals(record.getAttribute("codUnitaOrgCdR"))) {
					String desUnitaOrgCdR = record.getAttribute("codUnitaOrgCdR") + "";
					if(record.getAttribute("desUnitaOrgCdR")!= null && !"".equals(record.getAttribute("desUnitaOrgCdR"))) {
						desUnitaOrgCdR += " - " + record.getAttribute("desUnitaOrgCdR");													
					}
					return desUnitaOrgCdR;
				}	
				return null;
			}
		});
		
		capitolo = new ListGridField("capitolo");
		capitolo.setType(ListGridFieldType.INTEGER);
		capitolo.setHidden(true);
		capitolo.setCanHide(false);
		
		articolo = new ListGridField("articolo");
		articolo.setType(ListGridFieldType.INTEGER);
		articolo.setHidden(true);
		articolo.setCanHide(false);
		
		numero = new ListGridField("numero");
		numero.setType(ListGridFieldType.INTEGER);
		numero.setHidden(true);
		numero.setCanHide(false);
		
		descrizioneCapitolo = new ListGridField("descrizioneCapitolo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_descrizioneCapitolo_title());
		descrizioneCapitolo.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				return (record.getAttributeAsInt("capitolo") * 1000000) +
					   (record.getAttributeAsInt("articolo") * 1000) +
					   (record.getAttributeAsInt("numero"));
			}
		});
		
		liv5PdC = new ListGridField("liv5PdC");
		liv5PdC.setType(ListGridFieldType.INTEGER);
		liv5PdC.setHidden(true);
		liv5PdC.setCanHide(false);
		
		descrizionePdC = new ListGridField("descrizionePdC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_liv5PdC_title());
		descrizionePdC.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				return record.getAttributeAsInt("liv5PdC");
			}
		});
		descrizionePdC.setAttribute("custom", true);
		descrizionePdC.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("liv5PdC") != null && !"".equals(record.getAttribute("liv5PdC"))) {
					String descrizionePdC = record.getAttribute("liv5PdC") + "";
					if(record.getAttribute("descrizionePdC")!= null && !"".equals(record.getAttribute("descrizionePdC"))) {
						descrizionePdC += " - " + record.getAttribute("descrizionePdC");													
					}
					return descrizionePdC;
				}	
				return null;
			}
		});
		
		annoEsigibilitaDebito = new ListGridField("annoEsigibilitaDebito", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_annoEsigibilitaDebito_title());
		
		dataEsigibilitaDa = new ListGridField("dataEsigibilitaDa", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_dataEsigibilitaDa_title());
		dataEsigibilitaDa.setType(ListGridFieldType.DATE);
		dataEsigibilitaDa.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		if(isListaDatiContabiliSIBContoCapitale()) {
			dataEsigibilitaDa.setHidden(true);
			dataEsigibilitaDa.setCanHide(false);
		}
		
		dataEsigibilitaA = new ListGridField("dataEsigibilitaA", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_dataEsigibilitaA_title());
		dataEsigibilitaA.setType(ListGridFieldType.DATE);
		dataEsigibilitaA.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		if(isListaDatiContabiliSIBContoCapitale()) {
			dataEsigibilitaA.setHidden(true);
			dataEsigibilitaA.setCanHide(false);
		}
		
		dataScadenzaEntrata = new ListGridField("dataScadenzaEntrata", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_dataScadenzaEntrata_title());
		dataScadenzaEntrata.setType(ListGridFieldType.DATE);
		dataScadenzaEntrata.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		dichiarazioneDL78 = new ListGridField("dichiarazioneDL78", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_dichiarazioneDL78_title());
		dichiarazioneDL78.setValueMap("SI", "NO");
		
		tipoFinanziamento = new ListGridField("tipoFinanziamento", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoFinanziamento_title());
		
		codTipoFinanziamento = new ListGridField("codTipoFinanziamento");
		codTipoFinanziamento.setType(ListGridFieldType.INTEGER);
		codTipoFinanziamento.setHidden(true);
		codTipoFinanziamento.setCanHide(false);
		
		denominazioneSogg = new ListGridField("denominazioneSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_denominazioneSogg_title());
		
		codFiscaleSogg = new ListGridField("codFiscaleSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codFiscaleSogg_title());
		
		codPIVASogg = new ListGridField("codPIVASogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codPIVASogg_title());
		
		indirizzoSogg = new ListGridField("indirizzoSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_indirizzoSogg_title());
		
		cap = new ListGridField("cap", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_cap_title());
		
		localita = new ListGridField("localita", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_localita_title());
		
		provincia = new ListGridField("provincia", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_provincia_title());
		
		flgValidato = new ListGridField("flgValidato", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgValidato_title());
		flgValidato.setType(ListGridFieldType.ICON);
		flgValidato.setWidth(30);
		flgValidato.setIconWidth(16);
		flgValidato.setIconHeight(16);
		Map<String, String> flgValidatoIcons = new HashMap<String, String>();
		flgValidatoIcons.put("1", "ok.png");
		flgValidatoIcons.put("0", "blank.png");
		flgValidatoIcons.put("", "blank.png");
		flgValidato.setValueIcons(flgValidatoIcons);
		flgValidato.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if ("1".equals(record.getAttribute("flgValidato"))) {
					return I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgValidato_title();
				}
				return null;
			}
		});
				
		flgSoggDaPubblicare = new ListGridField("flgSoggDaPubblicare");
		flgSoggDaPubblicare.setHidden(true);
		flgSoggDaPubblicare.setCanHide(false);		
		
		settore = new ListGridField("settore");
		settore.setType(ListGridFieldType.INTEGER);
		settore.setHidden(true);
		settore.setCanHide(false);
		
		descrizioneSettore = new ListGridField("descrizioneSettore", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_settore_title());
		descrizioneSettore.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				return record.getAttributeAsInt("settore");
			}
		});
		descrizioneSettore.setAttribute("custom", true);
		descrizioneSettore.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("settore") != null && !"".equals(record.getAttribute("settore"))) {
					String descrizioneSettore = record.getAttribute("settore") + "";
					if(record.getAttribute("descrizioneSettore")!= null && !"".equals(record.getAttribute("descrizioneSettore"))) {
						descrizioneSettore += " - " + record.getAttribute("descrizioneSettore");													
					}
					return descrizioneSettore;
				}	
				return null;
			}
		});
		
		setGridFields(
			flgEntrataUscita,
			tipoDettaglio,
			annoCompetenza,
			numeroDettaglio,
			subNumero,
			annoCrono,
			numeroCrono,
			subCrono,
			importo,
			oggetto,
			codiceCIG,
			codiceCUP,
			codiceGAMIPBM,
			codUnitaOrgCdR,
			desUnitaOrgCdR,			
			capitolo,
			articolo,
			numero,
			descrizioneCapitolo,
			liv5PdC,
			descrizionePdC,			
			annoEsigibilitaDebito,
			dataEsigibilitaDa,
			dataEsigibilitaA,
			dataScadenzaEntrata,
			dichiarazioneDL78,
			tipoFinanziamento,
			codTipoFinanziamento,
			denominazioneSogg,
			codFiscaleSogg,
			codPIVASogg,
			indirizzoSogg,
			cap,
			localita,
			provincia,
			flgValidato,
			flgSoggDaPubblicare,
			settore,
			descrizioneSettore
		);				
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
		grid.addSort(new SortSpecifier("annoCrono", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroCrono", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("subCrono", SortDirection.ASCENDING));		
		grid.addSort(new SortSpecifier("descrizioneCapitolo", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("annoCompetenza", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroDettaglio", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("subNumero", SortDirection.ASCENDING));
		return grid;		
	}
	
	@Override
	protected void updateGridItemValue() {
		super.updateGridItemValue();
		aggiornaTotali();
	}
	
	public void aggiornaTotali() {
		if(grid.getRecords().length > 0) {			
			String pattern = "#,##0.00";
			float totaleEntrate = 0;
			float totaleUscite = 0;
			for(int i = 0; i < grid.getRecords().length; i++) {
				Record record = grid.getRecords()[i];
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrate += importo;
				} else if("U".equals(flgEntrataUscita)) {
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
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();		
		ToolStripButton datiStoriciButton = new ToolStripButton();   
		datiStoriciButton.setIcon("pratiche/task/buttons/datiStorici.png");   
		datiStoriciButton.setIconSize(16);
		datiStoriciButton.setPrefix("datiStoriciButton");
		datiStoriciButton.setPrompt(I18NUtil.getMessages().nuovaPropostaAtto2_detail_datiStoriciWindow_title());  
		datiStoriciButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickDatiStoriciButton();   	
			}   
		});  
//		if (isShowDatiStoriciButton()) {
			buttons.add(datiStoriciButton);
//		}				
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
	
	public void setData(RecordList data) {
		super.setData(data);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("datiStoriciButton"))
					{
						if (isShowDatiStoriciButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
					}
				}
			}	
		}
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {	
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("datiStoriciButton"))
					{
						if (isShowDatiStoriciButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
					}						
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
	
	@Override
	public void setGridFields(ListGridField... fields) {

		int length = fields.length;
		length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons			
		
		ListaBean configLista = Layout.getListConfig("listaDatiContabiliSIB");
		
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
	
	public void onClickDetailButton(final ListGridRecord record) {
		final DettaglioDatiContabiliSIBWindow lDettaglioDatiContabiliSIBWindow = new DettaglioDatiContabiliSIBWindow(this, "dettaglioDatiContabiliSIBWindow", record);
		lDettaglioDatiContabiliSIBWindow.show();
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
		record.setAttribute("nomeEntita", "listaDatiContabiliSIB");
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
			//TODO Devo gestire nell'esportazione le colonne che hanno un CellFormatter settato
			String codUnitaOrgCdR = getGrid().getRecords()[i].getAttribute("codUnitaOrgCdR");
			String desUnitaOrgCdR = getGrid().getRecords()[i].getAttribute("desUnitaOrgCdR");
			if(codUnitaOrgCdR != null && !"".equals(codUnitaOrgCdR)) {
				if(desUnitaOrgCdR != null && !"".equals(desUnitaOrgCdR)) {
					rec.setAttribute("desUnitaOrgCdR", codUnitaOrgCdR + " - " + desUnitaOrgCdR);
				} else {
					rec.setAttribute("desUnitaOrgCdR", codUnitaOrgCdR);
				}				
			}	
			String liv5PdC = getGrid().getRecords()[i].getAttribute("liv5PdC");
			String descrizionePdC = getGrid().getRecords()[i].getAttribute("descrizionePdC");	
			if(liv5PdC != null && !"".equals(liv5PdC)) {
				if(descrizionePdC != null && !"".equals(descrizionePdC)) {
					rec.setAttribute("descrizionePdC", liv5PdC + " - " + descrizionePdC);
				} else {
					rec.setAttribute("descrizionePdC", liv5PdC);
				}	
			}	
			String settore = getGrid().getRecords()[i].getAttribute("settore");
			String descrizioneSettore = getGrid().getRecords()[i].getAttribute("descrizioneSettore");
			if(settore != null && !"".equals(settore)) {
				if(descrizioneSettore != null && !"".equals(descrizioneSettore)) {
					rec.setAttribute("descrizioneSettore", settore + " - " + descrizioneSettore);
				} else {
					rec.setAttribute("descrizioneSettore", settore);
				}	
			}	
			records[i] = rec;
		}
		return records;
	}
	
	public boolean isListaDatiContabiliSIBCorrente() {
		return getName() != null && "listaDatiContabiliSIBCorrente".equals(getName());
	}
	
	public boolean isListaDatiContabiliSIBContoCapitale() {
		return getName() != null && "listaDatiContabiliSIBContoCapitale".equals(getName());
	}
	
}
