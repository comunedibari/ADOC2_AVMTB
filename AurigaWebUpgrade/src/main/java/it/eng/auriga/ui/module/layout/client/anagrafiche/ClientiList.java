package it.eng.auriga.ui.module.layout.client.anagrafiche;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import java.util.HashMap;
import java.util.Map;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class ClientiList extends CustomList {
	
	private ListGridField idSoggettoField;
	private ListGridField idClienteField;
	private ListGridField idUoUtSvField;	
	private ListGridField denominazioneField;
	private ListGridField codiceFiscaleField;
	private ListGridField partitaIvaField;	
	private ListGridField dataNascitaIstituzioneField;
	private ListGridField dataCessazioneField;	
	private ListGridField flgPersFisicaField;	
	private ListGridField cognomeField;
	private ListGridField nomeField;
	private ListGridField titoloField;	
	//private ListGridField tipoField;	
	//private ListGridField sottotipoField;	
	private ListGridField condizioneGiuridicaField;	
	private ListGridField causaleCessazioneField;	
	private ListGridField comuneNascitaIstituzioneField;	
	private ListGridField statoNascitaIstituzioneField;	
	private ListGridField cittadinanzaField;	
	private ListGridField flgAnnField;	
	private ListGridField altreDenominazioniField;	
	private ListGridField vecchieDenominazioniField;	
	private ListGridField indirizzoField;	
	private ListGridField flgCertificatoField;	
	private ListGridField estremiCertificazioneField;	
	private ListGridField codiceOrigineField;	
	private ListGridField codiceIpaField;
	private ListGridField tsInsField;	
	private ListGridField uteInsField;	
	private ListGridField tsLastUpdField;
	private ListGridField uteLastUpdField;	
	private ListGridField flgDiSistemaField;
	private ListGridField flgValidoField;
	private ListGridField codiceRapidoField;
	private ListGridField acronimoField;
	private ListGridField emailField;
	private ListGridField emailPecField;
	private ListGridField flgEmailPecPeoField;
	private ListGridField telField;
	private ListGridField faxField;
	private ListGridField codTipoSoggIntField;
	private ListGridField flgInOrganigrammaField;
	private ListGridField flgSelXFinalitaField;
	private ListGridField scoreField;

	public ClientiList(String nomeEntita) {
		
		super(nomeEntita);
			
		// Colonne visibili
		denominazioneField            = new ListGridField("denominazione", I18NUtil.getMessages().clienti_list_denominazioneField_title()); denominazioneField.setAttribute("custom", true);
		codiceFiscaleField            = new ListGridField("codiceFiscale", I18NUtil.getMessages().clienti_list_codiceFiscaleField_title());
		partitaIvaField               = new ListGridField("partitaIva", I18NUtil.getMessages().clienti_list_partitaIvaField_title());
		dataNascitaIstituzioneField   = new ListGridField("dataNascitaIstituzione", I18NUtil.getMessages().clienti_list_dataNascitaIstituzioneField_title()); dataNascitaIstituzioneField.setType(ListGridFieldType.DATE);dataNascitaIstituzioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataCessazioneField           = new ListGridField("dataCessazione", I18NUtil.getMessages().clienti_list_dataCessazioneField_title()); dataCessazioneField.setType(ListGridFieldType.DATE); dataCessazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		cognomeField                  = new ListGridField("cognome", I18NUtil.getMessages().clienti_list_cognomeField_title());
		nomeField                     = new ListGridField("nome", I18NUtil.getMessages().clienti_list_nomeField_title());
		titoloField                   = new ListGridField("titolo", I18NUtil.getMessages().clienti_list_titoloField_title());	
		//sottotipoField                = new ListGridField("sottotipo", I18NUtil.getMessages().clienti_list_sottotipoField_title());
		condizioneGiuridicaField      = new ListGridField("condizioneGiuridica", I18NUtil.getMessages().clienti_list_condizioneGiuridicaField_title());	
		causaleCessazioneField        = new ListGridField("causaleCessazione", I18NUtil.getMessages().clienti_list_causaleCessazioneField_title());	
		comuneNascitaIstituzioneField = new ListGridField("comuneNascitaIstituzione", I18NUtil.getMessages().clienti_list_comuneNascitaIstituzioneField_title());	
		statoNascitaIstituzioneField  = new ListGridField("statoNascitaIstituzione", I18NUtil.getMessages().clienti_list_statoNascitaIstituzioneField_title());	
		cittadinanzaField             = new ListGridField("cittadinanza", I18NUtil.getMessages().clienti_list_cittadinanzaField_title());	
		altreDenominazioniField       = new ListGridField("altreDenominazioni", I18NUtil.getMessages().clienti_list_altreDenominazioniField_title());
		vecchieDenominazioniField     = new ListGridField("vecchieDenominazioni", I18NUtil.getMessages().clienti_list_vecchieDenominazioniField_title());	
		indirizzoField                = new ListGridField("indirizzo", I18NUtil.getMessages().clienti_list_indirizzoField_title());
		codiceIpaField                = new ListGridField("codiceIpa", I18NUtil.getMessages().clienti_list_codiceIpaField_title());
		tsInsField                    = new ListGridField("tsIns", I18NUtil.getMessages().clienti_list_tsInsField_title());	 tsInsField.setType(ListGridFieldType.DATE); tsInsField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		uteInsField                   = new ListGridField("uteIns", I18NUtil.getMessages().clienti_list_uteInsField_title());	
		tsLastUpdField                = new ListGridField("tsLastUpd", I18NUtil.getMessages().clienti_list_tsLastUpdField_title());	 tsLastUpdField.setType(ListGridFieldType.DATE); tsLastUpdField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		uteLastUpdField               = new ListGridField("uteLastUpd", I18NUtil.getMessages().clienti_list_uteLastUpdField_title());	
		codiceRapidoField             = new ListGridField("codiceRapido", I18NUtil.getMessages().clienti_list_codiceRapidoField_title());
		acronimoField                 = new ListGridField("acronimo", I18NUtil.getMessages().clienti_list_acronimoField_title());	
		emailField                    = new ListGridField("email", I18NUtil.getMessages().clienti_list_emailField_title());	
		emailPecField                 = new ListGridField("emailPec", I18NUtil.getMessages().clienti_list_emailPecField_title());	
		telField                      = new ListGridField("tel", I18NUtil.getMessages().clienti_list_telField_title());	
		faxField                      = new ListGridField("fax", I18NUtil.getMessages().clienti_list_faxField_title());
		
		flgPersFisicaField = new ListGridField("flgPersFisica", I18NUtil.getMessages().clienti_list_flgPersFisicaField_title()); flgPersFisicaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton); flgPersFisicaField.setType(ListGridFieldType.ICON); flgPersFisicaField.setWidth(30); flgPersFisicaField.setIconWidth(16); flgPersFisicaField.setIconHeight(16);
		Map<String, String> flgPersFisicaValueIcons = new HashMap<String, String>();
		flgPersFisicaValueIcons.put("1", "anagrafiche/soggetti/flgPersFisica/persona_fisica.png");
		flgPersFisicaValueIcons.put("0", "anagrafiche/soggetti/flgPersFisica/persona_giuridica.png");
		flgPersFisicaValueIcons.put("" , "warning.png");
		flgPersFisicaField.setValueIcons(flgPersFisicaValueIcons);
		Map<String, String> flgPersFisicaValueHovers = new HashMap<String, String>();
		flgPersFisicaValueHovers.put("1", I18NUtil.getMessages().clienti_flgPersFisica_1_value());
		flgPersFisicaValueHovers.put("0", I18NUtil.getMessages().clienti_flgPersFisica_0_value());
		flgPersFisicaValueHovers.put("", I18NUtil.getMessages().clienti_flgPersFisica_NULL_value());
		flgPersFisicaField.setAttribute("valueHovers", flgPersFisicaValueHovers);
		
		/*
		tipoField = new ListGridField("tipo", I18NUtil.getMessages().clienti_list_tipoField_title()); tipoField.setAttribute("custom", true);			
		tipoField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				
				if(value != null) {
					if("PA+".equals(value)) return I18NUtil.getMessages().clienti_tipo_APA_value();
					if("#IAMM".equals(value)) return I18NUtil.getMessages().clienti_tipo_IAMM_value();					
					if("UO;UOI".equals(value)) return I18NUtil.getMessages().clienti_tipo_UOUOI_value();
					if("UP".equals(value)) return I18NUtil.getMessages().clienti_tipo_UP_value();
					if("#AF".equals(value)) return I18NUtil.getMessages().clienti_tipo_AF_value();
					if("#AG".equals(value)) return I18NUtil.getMessages().clienti_tipo_AG_value();	
				}
				return null;
			}
		});
		*/
		
		flgAnnField = new ListGridField("flgAnn", I18NUtil.getMessages().clienti_list_flgAnnField_title(), 50); flgAnnField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton); flgAnnField.setType(ListGridFieldType.ICON); flgAnnField.setWidth(30); flgAnnField.setIconWidth(16); flgAnnField.setIconHeight(16);
		Map<String, String> flgAnnValueIcons = new HashMap<String, String>();		
		flgAnnValueIcons.put("1", "ko.png");
		flgAnnValueIcons.put("0", "blank.png");
		flgAnnValueIcons.put("", "blank.png");
		flgAnnField.setValueIcons(flgAnnValueIcons);
		flgAnnField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgAnn"))) {
					return I18NUtil.getMessages().clienti_flgAnn_1_value();
				}				
				return null;
			}
		});
		
		flgValidoField = new ListGridField("flgValido", I18NUtil.getMessages().clienti_list_flgValidoField_title());	 flgValidoField.setType(ListGridFieldType.ICON); flgValidoField.setWidth(30); flgValidoField.setIconWidth(16); flgValidoField.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();		
		flgValidoValueIcons.put("1", "ok.png");
		flgValidoValueIcons.put("0", "blank.png");
		flgValidoValueIcons.put("", "blank.png");
		flgValidoField.setValueIcons(flgValidoValueIcons);		
		flgValidoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgValido"))) {
					return I18NUtil.getMessages().clienti_flgValido_1_value();
				}				
				return null;
			}
		});		
		
		flgEmailPecPeoField = new ListGridField("flgEmailPecPeo", I18NUtil.getMessages().clienti_list_flgEmailPecPeoField_title()); flgEmailPecPeoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton); flgEmailPecPeoField.setType(ListGridFieldType.ICON); flgEmailPecPeoField.setWidth(30); flgEmailPecPeoField.setIconWidth(16); flgEmailPecPeoField.setIconHeight(16);
		Map<String, String> flgEmailPecPeoValueIcons = new HashMap<String, String>();		
		flgEmailPecPeoValueIcons.put("PEC", "anagrafiche/soggetti/flgEmailPecPeo/PEC.png");
		flgEmailPecPeoValueIcons.put("PEO", "anagrafiche/soggetti/flgEmailPecPeo/PEO.png");
		flgEmailPecPeoValueIcons.put("", "blank.png");
		flgEmailPecPeoField.setValueIcons(flgEmailPecPeoValueIcons);
		Map<String, String> flgEmailPecPeoValueHovers = new HashMap<String, String>();
		flgEmailPecPeoValueHovers.put("PEC", I18NUtil.getMessages().clienti_flgEmailPecPeo_PEC_value());
		flgEmailPecPeoValueHovers.put("PEO", I18NUtil.getMessages().clienti_flgEmailPecPeo_PEO_value());
		flgEmailPecPeoField.setAttribute("valueHovers", flgEmailPecPeoValueHovers);	
		
		
		scoreField = new ListGridField("score", I18NUtil.getMessages().clienti_list_scoreField_title());	 scoreField.setType(ListGridFieldType.INTEGER); scoreField.setSortByDisplayField(false);
		scoreField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				
				Integer score = value != null && !"".equals(String.valueOf(value)) ? new Integer(String.valueOf(value)) : null;
				if(score != null) {
					String res = "";
					for(int i = 0; i < score; i++) {
						res += "<img src=\"images/score.png\" size=\"10\"/>";
					}
					return res;
				}
				return null;
			}
		});		
		        
        // Colonne disattivate	
 
		// Colonne hidden
		idSoggettoField               = new ListGridField("idSoggetto", I18NUtil.getMessages().clienti_list_idSoggettoField_title());               idSoggettoField.setHidden(true);		idSoggettoField.setCanHide(false);             idSoggettoField.setCanGroupBy(false);             idSoggettoField.setCanReorder(false);              idSoggettoField.setCanSort(false);              idSoggettoField.setCanFreeze(false);		        idSoggettoField.setCanExport(false);            idSoggettoField.setShowHover(false);
		idClienteField                = new ListGridField("idCliente", I18NUtil.getMessages().clienti_list_idClienteField_title());                 idClienteField.setHidden(true);         idClienteField.setCanHide(false);              idClienteField.setCanGroupBy(false);              idClienteField.setCanReorder(false);               idClienteField.setCanSort(false);               idClienteField.setCanFreeze(false);		            idClienteField.setCanExport(false);             idClienteField.setShowHover(false);
		codiceOrigineField            = new ListGridField("codiceOrigine", I18NUtil.getMessages().clienti_list_codiceOrigineField_title());			 codiceOrigineField.setHidden(true);              codiceOrigineField.setCanHide(false);             codiceOrigineField.setCanGroupBy(false);             codiceOrigineField.setCanReorder(false);              codiceOrigineField.setCanSort(false);              codiceOrigineField.setCanFreeze(false);		            codiceOrigineField.setCanExport(false);             codiceOrigineField.setShowHover(false);
		idUoUtSvField                 = new ListGridField("idUoUtSv"); idUoUtSvField.setHidden(true);		 idUoUtSvField.setCanHide(false); idUoUtSvField.setCanGroupBy(false);             idUoUtSvField.setCanReorder(false);              idUoUtSvField.setCanSort(false);              idUoUtSvField.setCanFreeze(false);		            idUoUtSvField.setCanExport(false);             idUoUtSvField.setShowHover(false);		
		estremiCertificazioneField    = new ListGridField("estremiCertificazione", I18NUtil.getMessages().clienti_list_estremiCertificazioneField_title());	 estremiCertificazioneField.setHidden(true); estremiCertificazioneField.setCanHide(false);  estremiCertificazioneField.setCanGroupBy(false);             estremiCertificazioneField.setCanReorder(false);              estremiCertificazioneField.setCanSort(false);              estremiCertificazioneField.setCanFreeze(false);		            estremiCertificazioneField.setCanExport(false);             estremiCertificazioneField.setShowHover(false);
		flgSelXFinalitaField          = new ListGridField("flgSelXFinalita"); flgSelXFinalitaField.setHidden(true); flgSelXFinalitaField.setCanHide(false);flgSelXFinalitaField.setCanGroupBy(false);             flgSelXFinalitaField.setCanReorder(false);              flgSelXFinalitaField.setCanSort(false);              flgSelXFinalitaField.setCanFreeze(false);		            flgSelXFinalitaField.setCanExport(false);             flgSelXFinalitaField.setShowHover(false);		
		
		codTipoSoggIntField = new ListGridField("codTipoSoggInt", I18NUtil.getMessages().clienti_list_codTipoSoggIntField_title());	    codTipoSoggIntField.setHidden(true);              codTipoSoggIntField.setCanHide(false);             codTipoSoggIntField.setCanGroupBy(false);             codTipoSoggIntField.setCanReorder(false);              codTipoSoggIntField.setCanSort(false);              codTipoSoggIntField.setCanFreeze(false);		            codTipoSoggIntField.setCanExport(false);             codTipoSoggIntField.setShowHover(false); codTipoSoggIntField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton); codTipoSoggIntField.setType(ListGridFieldType.ICON); codTipoSoggIntField.setWidth(30); codTipoSoggIntField.setIconWidth(16); codTipoSoggIntField.setIconHeight(16);
		Map<String, String> codTipoSoggIntValueIcons = new HashMap<String, String>();		
		codTipoSoggIntValueIcons.put("UP", "anagrafiche/soggetti/codTipoSoggInt/UP.png");
		codTipoSoggIntValueIcons.put("AOOI", "anagrafiche/soggetti/codTipoSoggInt/AOOI.png");
		codTipoSoggIntValueIcons.put("UOI", "anagrafiche/soggetti/codTipoSoggInt/UOI.png");
		codTipoSoggIntField.setValueIcons(codTipoSoggIntValueIcons);
		Map<String, String> codTipoSoggIntValueHovers = new HashMap<String, String>();
		codTipoSoggIntValueHovers.put("UP", I18NUtil.getMessages().clienti_codTipoSoggInt_UP_value());
		codTipoSoggIntValueHovers.put("AOOI", I18NUtil.getMessages().clienti_codTipoSoggInt_AOOI_value());
		codTipoSoggIntValueHovers.put("UOI", I18NUtil.getMessages().clienti_codTipoSoggInt_UOI_value());
		codTipoSoggIntField.setAttribute("valueHovers", codTipoSoggIntValueHovers);	
		
		flgInOrganigrammaField = new ListGridField("flgInOrganigramma", I18NUtil.getMessages().clienti_list_flgInOrganigrammaField_title());    flgInOrganigrammaField.setHidden(true);              flgInOrganigrammaField.setCanHide(false);             flgInOrganigrammaField.setCanGroupBy(false);             flgInOrganigrammaField.setCanReorder(false);              flgInOrganigrammaField.setCanSort(false);              flgInOrganigrammaField.setCanFreeze(false);		            flgInOrganigrammaField.setCanExport(false);             flgInOrganigrammaField.setShowHover(false); flgInOrganigrammaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton); flgInOrganigrammaField.setType(ListGridFieldType.ICON); flgInOrganigrammaField.setWidth(30); flgInOrganigrammaField.setIconWidth(16); flgInOrganigrammaField.setIconHeight(16);
		Map<String, String> flgInOrganigrammaValueIcons = new HashMap<String, String>();		
		flgInOrganigrammaValueIcons.put("1", "anagrafiche/soggetti/soggInOrg.png");
		flgInOrganigrammaValueIcons.put("0", "blank.png");
		flgInOrganigrammaValueIcons.put("", "blank.png");
		flgInOrganigrammaField.setValueIcons(flgInOrganigrammaValueIcons);		
		flgInOrganigrammaField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgInOrganigramma"))) {
					return I18NUtil.getMessages().clienti_list_flgInOrganigrammaField_title();
				}				
				return null;
			}
		});		

		flgCertificatoField = new ListGridField("flgCertificato", I18NUtil.getMessages().clienti_list_flgCertificatoField_title());    flgCertificatoField.setHidden(true);              flgCertificatoField.setCanHide(false);             flgCertificatoField.setCanGroupBy(false);             flgCertificatoField.setCanReorder(false);              flgCertificatoField.setCanSort(false);              flgCertificatoField.setCanFreeze(false);		            flgCertificatoField.setCanExport(false);             flgCertificatoField.setShowHover(false); flgCertificatoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton); flgCertificatoField.setType(ListGridFieldType.ICON); flgCertificatoField.setWidth(30); flgCertificatoField.setIconWidth(16); flgCertificatoField.setIconHeight(16);
		Map<String, String> flgCertificatoValueIcons = new HashMap<String, String>();
		flgCertificatoValueIcons.put("1", "coccarda.png");
		flgCertificatoValueIcons.put("0", "blank.png");
		flgCertificatoValueIcons.put("", "blank.png");
		flgCertificatoField.setValueIcons(flgCertificatoValueIcons);
		flgCertificatoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgCertificato"))) {
					return I18NUtil.getMessages().clienti_flgCertificato_1_value() + ": " + record.getAttributeAsString("estremiCertificazione");
				}				
				return null;
			}
		});
				
		flgDiSistemaField = new ListGridField("flgDiSistema", I18NUtil.getMessages().clienti_list_flgDiSistemaField_title());                flgDiSistemaField.setHidden(true);              flgDiSistemaField.setCanHide(false);             flgDiSistemaField.setCanGroupBy(false);             flgDiSistemaField.setCanReorder(false);              flgDiSistemaField.setCanSort(false);              flgDiSistemaField.setCanFreeze(false);		            flgDiSistemaField.setCanExport(false);             flgDiSistemaField.setShowHover(false);		 flgDiSistemaField.setType(ListGridFieldType.ICON); flgDiSistemaField.setWidth(30); flgDiSistemaField.setIconWidth(16); flgDiSistemaField.setIconHeight(16);
		Map<String, String> flgDiSistemaValueIcons = new HashMap<String, String>();		
		flgDiSistemaValueIcons.put("1", "lock.png");
		flgDiSistemaValueIcons.put("0", "blank.png");
		flgDiSistemaValueIcons.put("", "blank.png");
		flgDiSistemaField.setValueIcons(flgDiSistemaValueIcons);
		flgDiSistemaField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgDiSistema"))) {
					return I18NUtil.getMessages().clienti_flgDiSistema_1_value();
				}				
				return null;
			}
		});

		setFields(new ListGridField[] {
		                   		        idSoggettoField, 
		                   		        idClienteField,
		                   		        denominazioneField, 
		                   		        codiceFiscaleField, 
		                   		        partitaIvaField, 
		                   		        dataNascitaIstituzioneField,
		                   		        dataCessazioneField,
		                   		        flgPersFisicaField, 
		                   		        cognomeField, 
		                   		        nomeField, 				
		                   		        titoloField,
		                   		        //tipoField,
		                   		     //sottotipoField,
		                   		        condizioneGiuridicaField,
		                   		        causaleCessazioneField,
		                   		        comuneNascitaIstituzioneField,
		                   		        statoNascitaIstituzioneField,
		                   		        cittadinanzaField,
		                   		        flgAnnField,
		                   		        altreDenominazioniField,
		                   		        vecchieDenominazioniField,
		                   		        indirizzoField,
		                   		        flgCertificatoField,
		                   		        estremiCertificazioneField,
		                   		        codiceOrigineField,
		                   		        codiceIpaField,
		                   		        tsInsField,
		                   		        uteInsField,
		                   		        tsLastUpdField,
		                   		        uteLastUpdField,	
		                   		        flgDiSistemaField,
		                   		        flgValidoField,
		                   		        codiceRapidoField,
		                   		        acronimoField,
		                   		        emailField,
		                   		        emailPecField,
		                   		        flgEmailPecPeoField,
		                   		        telField,
		                   		        faxField,
		                   		        codTipoSoggIntField,
		                   		        flgInOrganigrammaField,
		                   		        flgSelXFinalitaField,
		                   		        scoreField
		});  
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}
	
	@Override
	public void reloadFieldsFromCriteria(AdvancedCriteria criteria) {
		boolean isFulltextSearch = false;
		if(criteria != null && criteria.getCriteria() != null) {
			for(Criterion crit : criteria.getCriteria()) {
				if("searchFulltext".equals(crit.getFieldName())) {
					Map value = JSOHelper.getAttributeAsMap(crit.getJsObj(), "value");					
					String parole = (String) value.get("parole");
					if(parole != null && !"".equals(parole)) {
						isFulltextSearch = true;
					}
				}
			}
		}
		if(isFulltextSearch) {
			scoreField.setHidden(false);
		} else {
			scoreField.setHidden(true);
		}		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {		
				try {
					refreshFields();
				} catch(Exception e) {}
				markForRedraw();
			}
		});	
	}
		
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);	
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				} 				
			}
		}, new DSRequest());	
	}
	
	@Override  
    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if(layout.isLookup() && record != null) {			
			if(record.getAttributeAsBoolean("flgSelXFinalita")) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}			        
		} 
		return super.getCellCSSText(record, rowNum, colNum);
    } 
	
	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {  
		Canvas lCanvasReturn = null;
		if (fieldName.equals("buttons")) {	
			ImgButton detailButton = buildDetailButton(record);  
			ImgButton modifyButton = buildModifyButton(record);  
			ImgButton deleteButton = buildDeleteButton(record);  			
			ImgButton lookupButton = buildLookupButton(record);			
						
			if(!isRecordAbilToMod(record)) {	
				modifyButton.disable();			
			}			
			
			if(!isRecordAbilToDel(record)) {	
				deleteButton.disable();			
			}
			
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
				
			recordCanvas.addMember(detailButton);			
			recordCanvas.addMember(modifyButton);
			recordCanvas.addMember(deleteButton);
			
			if(layout.isLookup()) {
				if(!isRecordSelezionabileForLookup(record)) {
					lookupButton.disable();
				}
				recordCanvas.addMember(lookupButton);		// aggiungo il bottone SELEZIONA				
			}			
			lCanvasReturn = recordCanvas;					
		}					
		return lCanvasReturn;
	}	
	
	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		SC.ask(flgDiSistema ? I18NUtil.getMessages().clienti_annullamentoLogicoAsk_message() : I18NUtil.getMessages().clienti_eliminazioneFisicaAsk_message(), new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {
				
				if(value) {		
					removeData(record, new DSCallback() {																
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "", MessageType.INFO));
								layout.hideDetail(true);																						
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout.getTipoEstremiRecord(record)), "", MessageType.ERROR));										
							}		
						}
					});													
				}
			}
		});         
	}
	
	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return record.getAttributeAsBoolean("flgSelXFinalita");
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		return ClientiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return ClientiLayout.isAbilToDel();
	}	
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		return ClientiLayout.isRecordAbilToMod(flgDiSistema);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		return ClientiLayout.isRecordAbilToDel(flgValido, flgDiSistema);	
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}