package it.eng.auriga.ui.module.layout.client.gestioneUtenti;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.IndirizziSoggettoItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class GestioneUtentiDetail extends CustomDetail {

	protected GestioneUtentiDetail _instance;

	protected DynamicForm form;
	protected DynamicForm societaForm;
	protected DynamicForm areeCommercialiForm;
	protected DynamicForm gruppoClientiForm;
	protected DynamicForm indirizziForm;
	protected DynamicForm uoAssociateUtenteForm;
	protected DynamicForm visualizzaDocumentiFascicoliStrutturaForm;
	protected DynamicForm modificaDocumentiFascicoliStrutturaForm;
	protected DynamicForm applEstAccreditateForm;
	protected DynamicForm visibEmailTransCaselleAssociateUOForm;
	
	protected HiddenItem idUser;
	protected HiddenItem flgDiSistema;
	protected HiddenItem flgValido;
	protected HiddenItem flgIgnoreWarning;
	protected HiddenItem idSoggRubrica;
	protected HiddenItem nomeLogoItem;
	protected HiddenItem descrizioneLinguaItem;
	protected HiddenItem descrizioneMansioneItem;

	protected RadioGroupItem flgUtenteInternoEsternoItem;
	
	protected TextItem cognome;
	protected TextItem nome;
	protected TextItem username;
	protected TextItem codFiscale;
	protected TextItem titolo;
	protected TextItem nroMatricola;
	protected TextItem qualifica;
	protected TextItem email;
	protected TextItem codiceUtenteMansioneItem;
	
	protected NumericItem ordFirmatariFFDGItem;

	protected DateItem dtIniVld;
	protected DateItem dtFineVld;

	protected FormItem password;
	protected FormItem confermaPassword;
	protected FilteredSelectItem idProfilo;
	protected SelectItem idSubProfiloItem;

	protected SelectItemWithDisplay idLogoItem;
	protected SelectItemWithDisplay idLinguaItem;
	protected SelectItemWithDisplay idMansioneItem;

	protected ImgButtonItem resetPasswordButton;
	protected ImgButtonItem dettaglioProfiloButton;
	protected ImgButtonItem modificaProfiloButton;
	protected ImgButtonItem eliminaProfiloButton;
	protected ImgButtonItem nuovoProfiloButton;
	protected ImgButtonItem importaDaLDAPusernameButton;
	protected ImgButtonItem importaDaLDAPemailButton;

	protected DetailSection societaSection;
	protected DetailSection clientiSection;
	protected DetailSection indirizziSection;
	protected DetailSection gruppoClientiSection;
	protected DetailSection areeCommercialiSection;
	protected DetailSection uoAssociateUtenteSection;
	//CR Visualizzazione di documenti e fascicoli assegnati/inviati in copia alla struttura 13/10/2019
	protected DetailSection visualizzaDocumentiFascicoliStrutturaSection;
	protected DetailSection modificaDocumentiFascicoliStrutturaSection;
	protected DetailSection applEstAccreditateSection;
	protected DetailSection visibEmailTransCaselleAssociateUOSection;

	protected SocietaUtentiItem societaUtentiItem;
	protected IndirizziSoggettoItem indirizziItem;
	protected GruppoClientiItem gruppoClientiItem;
	protected AreeCommercialiItem areeCommercialiItem;
	protected UoAssociateUtenteItem uoAssociateUtenteItem;
	protected VisualizzaDocumentiFascicoliStrutturaItem visualizzaDocumentiFascicoliStrutturaItem;
	protected ModificaDocumentiFascicoliStrutturaItem modificaDocumentiFascicoliStrutturaItem;
	protected ApplEstAccreditateItem applEstAccreditateItem; 
	protected VisibEmailTransCaselleAssociateUOItem visibEmailTransCaselleAssociateUOItem; 
	
	private TabSet tabSet;

	private Tab tabMain;
	private Tab tabClienti;

	private VLayout lVLayoutMain;
	private VLayout lVLayoutDetail;

	protected ListGridField idSocietaField;
	protected ListGridField idSoggettoGruppoField;
	protected ListGridField denominazioneSocietaField;
	protected ListGridField denominazioneSoggettoField;
	protected ListGridField codfiscaleSoggettoField;
	protected ListGridField cidField;
	protected ListGridField gruppoDiRiferimentoField;

	protected String applicationName;
	protected boolean eFactPersonalizzazioni;
	protected boolean gestioneClienti;
	protected boolean gestioneSocieta;
	protected boolean gestioneMansione;
	protected boolean gestioneAreaCommerciale;	
	protected boolean visibilitaEmailCaselleAssociateUO;
	protected boolean inviaEmailPwdCreaUtente;

	public GestioneUtentiDetail(String nomeEntita) {

		super(nomeEntita);

		_instance = this;

		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		// creo tab set
		buildTabSet();

		// creo layout del TAB MAIN
		createLayoutMain();

		// Aggiungo info
		tabSet.addTab(tabMain);
		if (isGestioneClienti()) {
			tabSet.addTab(tabClienti);
		}
		lVLayoutDetail = new VLayout();
		lVLayoutDetail.setWidth100();
		lVLayoutDetail.addMember(tabSet);
		addMember(lVLayoutDetail);
	}

	private void buildMainInfoSection() {
		
		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWidth("100%");
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setNumCols(11);
		form.setColWidths(120, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		idUser = new HiddenItem("idUser");
		flgDiSistema = new HiddenItem("flgDiSistema");
		flgValido = new HiddenItem("flgValido");
		idSoggRubrica = new HiddenItem("idSoggRubrica");
		flgIgnoreWarning = new HiddenItem("flgIgnoreWarning");
		flgIgnoreWarning.setDefaultValue(0);

		cognome = new TextItem("cognome", I18NUtil.getMessages().gestioneutenti_cognome());
		cognome.setRequired(true);
		cognome.setWidth(180);
		cognome.setStartRow(true);

		if (isEfactPersonalizzazioni()) {
			cognome.setValidators(new CustomValidator() {
				@Override
				protected boolean condition(Object value) {
					String lValue = null;
					if (value != null)
						lValue = (String) value.toString().trim();

					if (lValue == null || "".equals((String) lValue) || lValue.length() < 2)
						return false;
					else
						return true;
				}
			});
		}

		nome = new TextItem("nome", I18NUtil.getMessages().gestioneutenti_nome());
		nome.setRequired(true);
		nome.setWidth(185);
		if (isEfactPersonalizzazioni()) {
			nome.setValidators(new CustomValidator() {
				@Override
				protected boolean condition(Object value) {
					String lValue = null;
					if (value != null)
						lValue = (String) value.toString().trim();

					if (lValue == null || "".equals((String) lValue) || lValue.length() < 2)
						return false;
					else
						return true;
				}
			});
		}
		
		username = new TextItem("username", I18NUtil.getMessages().gestioneutenti_username());
		username.setRequired(true);
		username.setWidth(420);
		username.setColSpan(4);
		username.setStartRow(true);
		if (isEfactPersonalizzazioni()) {
			username.setValidators(new CustomValidator() {
				@Override
				protected boolean condition(Object value) {
					String lValue = null;
					if (value != null)
						lValue = (String) value.toString().trim();

					if (lValue == null || "".equals((String) lValue) || lValue.length() < 2)
						return false;
					else
						return true;
				}
			});
		}

		importaDaLDAPusernameButton = new ImgButtonItem("importaDaLDAPusername", "buttons/server_ldap.png", I18NUtil.getMessages().gestioneutenti_importaDaLDAP());
		importaDaLDAPusernameButton.setAlwaysEnabled(true);
		importaDaLDAPusernameButton.setColSpan(1);
		importaDaLDAPusernameButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				String usernameIn = (String) vm.getValue("username");
				if(usernameIn !=null && !usernameIn.equalsIgnoreCase("")){
					Record record = new Record();
					record.setAttribute("username", usernameIn);					
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
					lGwtRestDataSource.executecustom("leggiInfoLDAPusername", record, new DSCallback() {
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS){
								if (response.getData() != null) {
									Record lRecordDb    = response.getData()[0];
									String lcognome     = lRecordDb.getAttribute("cognome");
									String lnome        = lRecordDb.getAttribute("nome");
									String lusername    = lRecordDb.getAttribute("username");
									String lcodFiscale  = lRecordDb.getAttribute("codFiscale");									
									String ltitolo      = lRecordDb.getAttribute("titolo");
									String lmatricola   = lRecordDb.getAttribute("nroMatricola");
									String lqualifica   = lRecordDb.getAttribute("qualifica");
									String lemail       = lRecordDb.getAttribute("email");
									form.setValue("cognome",      lcognome);   	cognome.setValue(lcognome);									
									form.setValue("nome",         lnome);      	nome.setValue(lnome);
									form.setValue("username",     lusername);  	username.setValue(lusername);
									form.setValue("codFiscale",   lcodFiscale); codFiscale.setValue(lcodFiscale);									
									form.setValue("titolo",       ltitolo);    	titolo.setValue(ltitolo);									
								    form.setValue("nroMatricola", lmatricola); 	nroMatricola.setValue(lmatricola);									
									form.setValue("qualifica",    lqualifica); 	qualifica.setValue(lqualifica);									
									form.setValue("email",        lemail);     	email.setValue(lemail);								
								}
							}
						}						
					});
				}
				else{
					SC.warn(I18NUtil.getMessages().gestioneutenti_importaDaLDAPusernameError_message());
				}
			}
		});
		
		codFiscale = new TextItem("codFiscale", I18NUtil.getMessages().gestioneutenti_codFiscale());
		codFiscale.setStartRow(true);
		codFiscale.setWidth(150);
		codFiscale.setLength(16);
		codFiscale.setValidators(new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals(value)) {
					return true;
				}
				RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
				return regExp.test((String) value);
			}
		});
		
		titolo = new TextItem("titolo", I18NUtil.getMessages().gestioneutenti_titolo());
		titolo.setWidth(420);
		titolo.setColSpan(4);
		titolo.setStartRow(true);
		titolo.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!getApplicationName().equalsIgnoreCase("EIIFact"));
			}
		});

		nroMatricola = new TextItem("nroMatricola", I18NUtil.getMessages().gestioneutenti_nroMatricola());
		nroMatricola.setWidth(120);
		nroMatricola.setStartRow(true);
		nroMatricola.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (!getApplicationName().equalsIgnoreCase("EIIFact"));
			}
		});

		qualifica = new TextItem("qualifica", I18NUtil.getMessages().gestioneutenti_qualifica());
		qualifica.setWidth(420);
		qualifica.setColSpan(4);
		qualifica.setStartRow(true);
		qualifica.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (isQualificaAttiva());
			}
		});

		email = new TextItem("email", I18NUtil.getMessages().gestioneutenti_email());
		email.setWidth(420);
		email.setColSpan(4);
		email.setStartRow(true);
		
		email.setValidators(new CustomValidator(){
			@Override
			protected boolean condition(Object value) {
				if (value == null || value.equals("")) return true; 
				RegExp regExp = RegExp.compile("^([_.\\]*[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z-_])*@(([0-9a-zA-Z])+([-\\w]*[0-9a-zA-Z])*\\.)+[a-zA-Z]{2,9})$");
				String lString = (String)value;
				String[] values = lString.split(";");
				boolean res = true;
				for (String val : values){
					if (val == null || val.equals("")) res = res && true;
					else res = res && regExp.test(val);
				}
				return res;
			}
		});

		importaDaLDAPemailButton = new ImgButtonItem("importaDaLDAPemail", "buttons/server_ldap.png", I18NUtil.getMessages().gestioneutenti_importaDaLDAP());
		importaDaLDAPemailButton.setAlwaysEnabled(true);
		importaDaLDAPemailButton.setColSpan(1);
		importaDaLDAPemailButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				String emailIn = (String) vm.getValue("email");
				if(emailIn!=null && !emailIn.equalsIgnoreCase("")){
					Record record = new Record();
					record.setAttribute("email", emailIn);
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
					lGwtRestDataSource.executecustom("leggiInfoLDAPemail", record, new DSCallback() {
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS){
								if (response.getData() != null) {
									Record lRecordDb    = response.getData()[0];
									String lcognome     = lRecordDb.getAttribute("cognome");
									String lnome        = lRecordDb.getAttribute("nome");									
									String lusername    = lRecordDb.getAttribute("username");
									String lcodFiscale  = lRecordDb.getAttribute("codFiscale");								
									String ltitolo      = lRecordDb.getAttribute("titolo");
									String lmatricola   = lRecordDb.getAttribute("nroMatricola");
									String lqualifica   = lRecordDb.getAttribute("qualifica");
									String lemail       = lRecordDb.getAttribute("email");
									form.setValue("cognome",      lcognome);   	cognome.setValue(lcognome);									
									form.setValue("nome",         lnome);      	nome.setValue(lnome);																	
									form.setValue("username",     lusername);  	username.setValue(lusername);
									form.setValue("codFiscale",   lcodFiscale); codFiscale.setValue(lcodFiscale);									
									form.setValue("titolo",       ltitolo);    	titolo.setValue(ltitolo);									
									form.setValue("nroMatricola", lmatricola); 	nroMatricola.setValue(lmatricola);									
									form.setValue("qualifica",    lqualifica); 	qualifica.setValue(lqualifica);									
									form.setValue("email",        lemail);     	email.setValue(lemail);
								}
							}
						}						
					});					
				}
				else{					
					SC.warn(I18NUtil.getMessages().gestioneutenti_importaDaLDAPemailError_message());
				}
			}
		});
		
		if (isInviaEmailPwdCreaUtente()) {
			email.setRequired(true);
			email.setValidators(new CustomValidator() {
				@Override
				protected boolean condition(Object value) {
					String lValue = null;
					if (value != null)
						lValue = (String) value.toString().trim();

					if (lValue == null || "".equals((String) lValue))
						return false;
					else
						return true;
				}
			});
		}

		dtIniVld = new DateItem("dtIniVld", I18NUtil.getMessages().gestioneutenti_dtIniVld());
		dtIniVld.setStartRow(true);
		dtIniVld.setRequired(true);

		dtFineVld = new DateItem("dtFineVld", I18NUtil.getMessages().gestioneutenti_dtFineVldDetail());

		if ((AurigaLayout.getParametroDB("AUTHENTICATION_TYPE") != null && AurigaLayout.getParametroDB("AUTHENTICATION_TYPE").equalsIgnoreCase("LDAP"))) {
			password = new HiddenItem("password");
			password.setDefaultValue("QQaattyy13625");
			confermaPassword = new HiddenItem("confermaPassword");
			confermaPassword.setDefaultValue("QQaattyy13625");
		} else {
			if (isInviaEmailPwdCreaUtente() == true) {
				password = new HiddenItem("password");
				password.setDefaultValue("QQaattyy13625");
				confermaPassword = new HiddenItem("confermaPassword");
				confermaPassword.setDefaultValue("QQaattyy13625");
			} else {
				password = new PasswordItem("password", I18NUtil.getMessages().gestioneutenti_password());
				password.setAttribute("obbligatorio", true);
				password.setType(FormItemType.PASSWORD_ITEM.getValue());
				password.setWidth(420);
				password.setColSpan(4);
				password.setStartRow(true);
				password.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return (getLayout().getMode() != null && "new".equals(getLayout().getMode()));
					}
				});

				password.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
					@Override
					public boolean execute(FormItem formItem, Object value) {
						return (getLayout().getMode() != null && "new".equals(getLayout().getMode()));
					}
				}));

				confermaPassword = new PasswordItem("confermaPassword", I18NUtil.getMessages().gestioneutenti_confermaPassword());
				confermaPassword.setAttribute("obbligatorio", true);
				confermaPassword.setType(FormItemType.PASSWORD_ITEM.getValue());
				confermaPassword.setWidth(420);
				confermaPassword.setColSpan(4);
				confermaPassword.setStartRow(true);
				confermaPassword.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return (getLayout().getMode() != null && "new".equals(getLayout().getMode()));
					}
				});

				confermaPassword.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
					@Override
					public boolean execute(FormItem formItem, Object value) {
						return (getLayout().getMode() != null && "new".equals(getLayout().getMode()));
					}
				}));

			}

			resetPasswordButton = new ImgButtonItem("resetPassword", "buttons/reset_pwd.png", "Reset Password");
			resetPasswordButton.setAlwaysEnabled(true);
			resetPasswordButton.setColSpan(1);
			resetPasswordButton.addIconClickHandler(new IconClickHandler() {
				@Override
				public void onIconClick(IconClickEvent event) {
					resetPassword();
				}
			});
			resetPasswordButton.setShowIfCondition(new FormItemIfFunction() {
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return (getLayout().getMode() != null && "edit".equals(getLayout().getMode()));
				}
			});
		}

		descrizioneMansioneItem = new HiddenItem("descrizioneMansione");
		idMansioneItem = createSelectItem("idMansione", 
				                           I18NUtil.getMessages().gestioneutenti_combo_mansione_label(), 
				                           "LoadComboMansioniDataSource",
				                           new String[] { "descrizioneMansione" }, 
				                           new String[] { "idMansione" }, 
				                           new String[] { I18NUtil.getMessages().gestioneutenti_combo_mansione_descrizioneMansioneField() }, 
				                           new Object[] { 250 }, 
				                           400, 
				                           "idMansione", 
				                           "descrizioneMansione", 
				                           true);

		idMansioneItem.setAllowEmptyValue(true);
		idMansioneItem.setStartRow(true);
		idMansioneItem.setFilterLocally(false);
		idMansioneItem.setColSpan(7);
		idMansioneItem.setVisible(isMansioneAttiva());
		
		codiceUtenteMansioneItem = new TextItem("codiceUtenteMansione", I18NUtil.getMessages().gestioneutenti_codiceUtenteMansione());
		codiceUtenteMansioneItem.setWidth(170);
		codiceUtenteMansioneItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (isCodiceUtenteMansioneAttiva());
			}
		});

		final GWTRestDataSource profiliUtenteDS = new GWTRestDataSource("LoadComboProfiliUtenteDataSource", "idProfilo", FieldType.TEXT, true);
		idProfilo = new FilteredSelectItem("idProfilo", I18NUtil.getMessages().gestioneutenti_idProfilo()) {
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				form.markForRedraw();
			}
			@Override
			public void setValue(String value) {
				super.setValue(value);
				form.markForRedraw();
			}
			@Override
			protected void clearSelect() {
				super.clearSelect();
				form.markForRedraw();
			}
		};
		ListGridField idProfiloField = new ListGridField("idProfilo");
		idProfiloField.setHidden(true);
		ListGridField nomeField = new ListGridField("nome", I18NUtil.getMessages().gestioneutenti_idProfilo());
		idProfilo.setPickListFields(idProfiloField, nomeField);
		idProfilo.setAddUnknownValues(false);
		idProfilo.setFilterLocally(true);
		idProfilo.setValueField("idProfilo");
		idProfilo.setDisplayField("nome");
		idProfilo.setOptionDataSource(profiliUtenteDS);
		idProfilo.setWidth(420);
		idProfilo.setClearable(true);
		idProfilo.setShowIcons(true);
		idProfilo.setAutoFetchData(false);
		idProfilo.setCachePickListResults(false);
		idProfilo.setStartRow(true);
		idProfilo.setEndRow(false);
		idProfilo.setColSpan(4);
		if (isEfactPersonalizzazioni()) {
			idProfilo.setRequired(true);
		}

		dettaglioProfiloButton = new ImgButtonItem("dettaglioProfilo", "buttons/detail.png", "Dettaglio profilo");
		dettaglioProfiloButton.setAlwaysEnabled(true);
		dettaglioProfiloButton.setColSpan(1);
		dettaglioProfiloButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				final String idProfiloSelezionato = idProfilo.getValueAsString();
				Record recordToLoad = new Record();
				recordToLoad.setAttribute("idProfilo", idProfiloSelezionato);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaDettaglioProfiloDataSource", "idProfilo", FieldType.TEXT);
				lGwtRestDataSource.getData(recordToLoad, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						final Record record = response.getData()[0];
						DettaglioProfiloPopup profiloPopup = new DettaglioProfiloPopup(editing) {
							@Override
							public void afterManageOnCloseClick(String value) {
								idProfilo.invalidateDisplayValueCache();
								idProfilo.setValue(value);
								idProfilo.fetchData();		
							}
						};
						profiloPopup.visualizzaDettaglio(record);
						profiloPopup.show();
					}
				});
			}
		});
		dettaglioProfiloButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return idProfilo.getValueAsString() != null && !"".equals(idProfilo.getValueAsString());
			}
		});

		modificaProfiloButton = new ImgButtonItem("modificaProfilo", "buttons/modify.png", "Modifica profilo");
		modificaProfiloButton.setAlwaysEnabled(true);
		modificaProfiloButton.setColSpan(1);
		modificaProfiloButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				final String idProfiloSelezionato = idProfilo.getValueAsString();
				Record recordToLoad = new Record();
				recordToLoad.setAttribute("idProfilo", idProfiloSelezionato);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaDettaglioProfiloDataSource", "idProfilo", FieldType.TEXT);
				lGwtRestDataSource.getData(recordToLoad, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						final Record record = response.getData()[0];
						DettaglioProfiloPopup profiloPopup = new DettaglioProfiloPopup() {
							@Override
							public void afterManageOnCloseClick(String value) {
								idProfilo.invalidateDisplayValueCache();
								idProfilo.setValue(value);
								idProfilo.fetchData();								
							}
						};
						profiloPopup.modificaDettaglio(record);
						profiloPopup.show();
					}
				});
			}
		});
		modificaProfiloButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return editing && idProfilo.getValueAsString() != null && !"".equals(idProfilo.getValueAsString()) && Layout.isPrivilegioAttivo("SIC/PR;M");
			}
		});
		
		eliminaProfiloButton = new ImgButtonItem("eliminaProfilo", "buttons/delete.png", "Elimina profilo");
		eliminaProfiloButton.setAlwaysEnabled(true);
		eliminaProfiloButton.setColSpan(1);
		eliminaProfiloButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				
				final String idProfiloSelezionato = idProfilo.getValueAsString();
				Record recordToLoad = new Record();
				recordToLoad.setAttribute("idProfilo", idProfiloSelezionato);
				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaDettaglioProfiloDataSource", "idProfilo", FieldType.TEXT);
				lGwtRestDataSource.getData(recordToLoad, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						final Record record = response.getData()[0];						
						lGwtRestDataSource.removeData(record, new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS){
									final Record record = response.getData()[0];
									if(record.getAttribute("warning") != null && !"".equals(record.getAttribute("warning"))) {
										SC.ask(record.getAttribute("warning"), new BooleanCallback() {
											@Override
											public void execute(Boolean value) {
												if(value != null && value) {
													lGwtRestDataSource.addParam("flgIgnoreWarning", "1");
													lGwtRestDataSource.removeData(record, new DSCallback() {
														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															if (response.getStatus() == DSResponse.STATUS_SUCCESS){
																idProfilo.setValue((String) null);																
																idProfilo.fetchData();													
															}
														}													
													});
												}
											}
										});
									} else {
										idProfilo.setValue((String) null);
										idProfilo.fetchData();
									}
								}
							}
						});	
					}
				});				
			}
		});
		eliminaProfiloButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return editing && idProfilo.getValueAsString() != null && !"".equals(idProfilo.getValueAsString()) && Layout.isPrivilegioAttivo("SIC/PR;FC");		
			}
		});

		nuovoProfiloButton = new ImgButtonItem("nuovoProfilo", "buttons/new.png", "Nuovo profilo");
		nuovoProfiloButton.setAlwaysEnabled(true);
		nuovoProfiloButton.setColSpan(1);
		nuovoProfiloButton.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				DettaglioProfiloPopup profiloPopup = new DettaglioProfiloPopup() {
					@Override
					public void afterManageOnCloseClick(String value) {
						idProfilo.setValue(value);
						idProfilo.fetchData();		
					}
				};
				profiloPopup.nuovoDettaglio();
				profiloPopup.show();
			}
		});
		nuovoProfiloButton.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return editing && Layout.isPrivilegioAttivo("SIC/PR;I");
			}
		});
		
		GWTRestDataSource subProfiliUtenteDS = new GWTRestDataSource("LoadComboSubProfiloUtenteDataSource", "key", FieldType.TEXT);
		idSubProfiloItem = new SelectItem("idSubProfilo", I18NUtil.getMessages().gestioneutenti_subprofili_title());
		idSubProfiloItem.setMultiple(true);
		idSubProfiloItem.setOptionDataSource(subProfiliUtenteDS);  
		idSubProfiloItem.setAutoFetchData(true);
		idSubProfiloItem.setDisplayField("value");
		idSubProfiloItem.setValueField("key");			
		idSubProfiloItem.setWrapTitle(false);
		idSubProfiloItem.setCachePickListResults(false);
		idSubProfiloItem.setStartRow(true);
		idSubProfiloItem.setAllowEmptyValue(true);
		idSubProfiloItem.setAlwaysFetchMissingValues(true);
		idSubProfiloItem.setClearable(false);
		idSubProfiloItem.setRedrawOnChange(true);
		idSubProfiloItem.setWidth(600);   		
		idSubProfiloItem.setColSpan(10);
		idSubProfiloItem.setStartRow(true);
		
		ordFirmatariFFDGItem = new NumericItem("ordFirmatariFFDG",I18NUtil.getMessages().gestioneutenti_ordFirmatariFFDG_title());
		ordFirmatariFFDGItem.setColSpan(1);
		ordFirmatariFFDGItem.setWidth(50);
		ordFirmatariFFDGItem.setStartRow(true);
		ordFirmatariFFDGItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("SHOW_ORD_FIRMATARI_FF_DG");
			}
		});
		
		nomeLogoItem = new HiddenItem("nomeLogo");
		idLogoItem = createSelectItem("idLogo", 
				                      I18NUtil.getMessages().gestioneutenti_combo_loghiDominio_label(), 
				                      "LoadComboLoghiDominiDataSource",
				                      new String[] { "nomeLogo", "descrizioneLogo" }, 
				                      new String[] { "idLogo" }, 
				                      new String[] { I18NUtil.getMessages().gestioneutenti_combo_loghiDominio_nomeLogoField(),
						                             I18NUtil.getMessages().gestioneutenti_combo_loghiDominio_descrizioneLogoField() 
						                           }, 
						              new Object[] { 150, 250 }, 
						              400, 
						              "idLogo",
				                      "descrizioneLogo", 
				                      true);
		idLogoItem.setAllowEmptyValue(true);
		idLogoItem.setStartRow(true);
		idLogoItem.setFilterLocally(false);
		idLogoItem.setColSpan(9);
		idLogoItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
			       return GestioneUtentiLayout.isGestioneLogo();
			}
		});

		descrizioneLinguaItem = new HiddenItem("descrizioneLingua");
		idLinguaItem = createSelectItem("idLingua", 
				                        I18NUtil.getMessages().gestioneutenti_combo_lingua_label(), "LoadComboLinguaApplicazioneDataSource",
				                        new String[] { "descrizioneLingua" }, 
				                        new String[] { "idLingua" }, 
				                        new String[] { I18NUtil.getMessages().gestioneutenti_combo_lingua_descrizioneLinguaField() }, 
				                        new Object[] { 250 }, 
				                        400, 
				                        "idLingua", 
				                        "descrizioneLingua", 
				                        true);

		idLinguaItem.setAllowEmptyValue(true);
		idLinguaItem.setStartRow(true);
		idLinguaItem.setFilterLocally(false);
		idLinguaItem.setColSpan(9);
		idLinguaItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
			       return GestioneUtentiLayout.isGestioneLingua();
			}
		});

		// flag utente interno/esterno
		LinkedHashMap<String, String> flgUtenteInternoEsternoMap = new LinkedHashMap<String, String>();  
		flgUtenteInternoEsternoMap.put("I", "Interno");  
		flgUtenteInternoEsternoMap.put("E", "Esterno");  
		flgUtenteInternoEsternoItem = new RadioGroupItem("flgUtenteInternoEsterno"); 
		flgUtenteInternoEsternoItem.setTitle(I18NUtil.getMessages().gestioneutenti_flgUtenteInternoEsterno());
		flgUtenteInternoEsternoItem.setValueMap(flgUtenteInternoEsternoMap); 
		flgUtenteInternoEsternoItem.setVertical(false);
		flgUtenteInternoEsternoItem.setWrap(false);	
		flgUtenteInternoEsternoItem.setTitleOrientation(TitleOrientation.LEFT);
		flgUtenteInternoEsternoItem.setWidth(120);
		flgUtenteInternoEsternoItem.setColSpan(9);
		flgUtenteInternoEsternoItem.setAttribute("obbligatorio", true);
		flgUtenteInternoEsternoItem.setStartRow(true);
		flgUtenteInternoEsternoItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (GestioneUtentiLayout.isGestioneUtenteInterno());
			}
		});
		flgUtenteInternoEsternoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return (GestioneUtentiLayout.isGestioneUtenteInterno());
			}
		}));
		
		flgUtenteInternoEsternoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				uoAssociateUtenteSection.setVisible(isAbilToInsUoAssociateUtente());
				visualizzaDocumentiFascicoliStrutturaSection.setVisible(isDbAttivaEstensioniPermessiDoc());
				modificaDocumentiFascicoliStrutturaSection.setVisible(isDbAttivaEstensioniPermessiDoc());
				markForRedraw();
			}
		});
		
		if (AurigaLayout.getParametroDB("AUTHENTICATION_TYPE") != null && AurigaLayout.getParametroDB("AUTHENTICATION_TYPE").equalsIgnoreCase("LDAP")) {
			form.setItems( 
					      cognome,  nome, 
					      username,  importaDaLDAPusernameButton, 
					      password, 
					      confermaPassword,
					      codFiscale,
					      titolo, 
					      nroMatricola, 
					      qualifica, 
					      email,     importaDaLDAPemailButton, 
					      dtIniVld, dtFineVld,
					      idMansioneItem, 
					      codiceUtenteMansioneItem, 
					      idProfilo, dettaglioProfiloButton, modificaProfiloButton, eliminaProfiloButton, nuovoProfiloButton, 
					      idSubProfiloItem,
					      ordFirmatariFFDGItem,
					      idLogoItem, 					      
					      idLinguaItem, 
					      flgUtenteInternoEsternoItem,
					      
					      // hidden
					      idSoggRubrica,					      
					      idUser,
					      nomeLogoItem,
					      descrizioneLinguaItem,
					      descrizioneMansioneItem
			);
		} else {
			form.setItems( 
					      cognome, nome, 
					      username, 
					      password, 
					      confermaPassword, resetPasswordButton, 
					      codFiscale,
					      titolo, 
					      nroMatricola, 
					      qualifica, 
					      email, 
					      dtIniVld, dtFineVld, 
					      idMansioneItem, 
					      codiceUtenteMansioneItem, 
					      idProfilo, dettaglioProfiloButton, modificaProfiloButton, eliminaProfiloButton, nuovoProfiloButton,
					      idSubProfiloItem,
					      ordFirmatariFFDGItem,
					      idLogoItem, 
					      idLinguaItem,
					      flgUtenteInternoEsternoItem,
					      
					      // hidden
					      idSoggRubrica,
					      idUser,					      
					      nomeLogoItem, 					      
					      descrizioneLinguaItem,
					      descrizioneMansioneItem
					      );
		}
		lVLayoutMain.addMember(form);
	}

	private void buildAreeCommercialiSection() {
		areeCommercialiForm = new DynamicForm();
		areeCommercialiForm.setValuesManager(vm);
		areeCommercialiForm.setWidth("100%");
		areeCommercialiForm.setHeight("5");
		areeCommercialiForm.setPadding(5);
		areeCommercialiForm.setNumCols(1);
		areeCommercialiForm.setWrapItemTitles(false);
		areeCommercialiItem = new AreeCommercialiItem();
		areeCommercialiItem = new AreeCommercialiItem();
		areeCommercialiItem.setName("listaAreeCommerciali");
		areeCommercialiItem.setTitle(I18NUtil.getMessages().gestioneutenti_areeCommerciali_title());
		areeCommercialiItem.setStartRow(true);
		areeCommercialiItem.setEndRow(true);
		areeCommercialiItem.setColSpan(10);
		areeCommercialiItem.setCanEdit(true);
		areeCommercialiItem.setShowTitle(false);
		areeCommercialiItem.setAttribute("obbligatorio", false);
		areeCommercialiForm.setFields(areeCommercialiItem);
		areeCommercialiSection = new DetailSection(I18NUtil.getMessages().gestioneutenti_areeCommercialiSection_title(), true, true, false, areeCommercialiForm);
		areeCommercialiSection.setVisible(isAreaCommercialeAttiva());
		lVLayoutMain.addMember(areeCommercialiSection);
	}

	private void buildSocietaSection() {
		societaForm = new DynamicForm();
		societaForm.setValuesManager(vm);
		societaForm.setWidth("100%");
		societaForm.setHeight("5");
		societaForm.setPadding(5);
		societaForm.setNumCols(1);
		societaForm.setWrapItemTitles(false);
		societaUtentiItem = new SocietaUtentiItem();
		societaUtentiItem.setName("listaSocietaUtenti");
		societaUtentiItem.setTitle(I18NUtil.getMessages().gestioneutenti_societa_title());
		societaUtentiItem.setStartRow(true);
		societaUtentiItem.setEndRow(true);
		societaUtentiItem.setColSpan(10);
		societaUtentiItem.setCanEdit(true);
		societaUtentiItem.setShowTitle(false);
		societaUtentiItem.setAttribute("obbligatorio", false);
		societaForm.setFields(societaUtentiItem);
		societaSection = new DetailSection(I18NUtil.getMessages().gestioneutenti_societaSection_title(), true, true, false, societaForm);
		societaSection.setVisible(isSocietaAttiva());
		lVLayoutMain.addMember(societaSection);
	}

	private void buildGrupppClientiSection() {
		gruppoClientiForm = new DynamicForm();
		gruppoClientiForm.setValuesManager(vm);
		gruppoClientiForm.setWidth("100%");
		gruppoClientiForm.setHeight("5");
		gruppoClientiForm.setPadding(5);
		gruppoClientiForm.setNumCols(1);
		gruppoClientiForm.setWrapItemTitles(false);
		gruppoClientiItem = new GruppoClientiItem();
		gruppoClientiItem.setName("listaGruppoClientiUtenti");
		gruppoClientiItem.setTitle(I18NUtil.getMessages().gestioneutenti_gruppoClienti_title());
		gruppoClientiItem.setStartRow(true);
		gruppoClientiItem.setEndRow(true);
		gruppoClientiItem.setColSpan(10);
		gruppoClientiItem.setCanEdit(true);
		gruppoClientiItem.setShowTitle(false);
		gruppoClientiItem.setAttribute("obbligatorio", false);
		gruppoClientiForm.setFields(gruppoClientiItem);
		gruppoClientiSection = new DetailSection(I18NUtil.getMessages().gestioneutenti_gruppoClientiSection_title(), true, true, false, gruppoClientiForm);
		gruppoClientiSection.setVisible(isGestioneClienti());
		lVLayoutMain.addMember(gruppoClientiSection);
	}


	private void buildIndirizziSection() {
		
		indirizziForm = new DynamicForm();
		indirizziForm.setValuesManager(vm);
		indirizziForm.setWidth("100%");
		indirizziForm.setHeight("5");
		indirizziForm.setPadding(5);
		indirizziForm.setWrapItemTitles(false);
		indirizziItem = new IndirizziSoggettoItem() {
			@Override
			public String getFlgPersonaFisica() {
				return _instance.isPersonaFisica() ? "1" : "";
			}
		};
		indirizziItem.setName("listaIndirizzi");
		indirizziItem.setShowTitle(false);
		indirizziForm.setFields(indirizziItem);
		indirizziSection = new DetailSection(I18NUtil.getMessages().gestioneutenti_indirizziSection_title(), true, true, false, indirizziForm);
		indirizziSection.setVisible(isEfactPersonalizzazioni());
		lVLayoutMain.addMember(indirizziSection);
	}
	
	private void buildUoAssociateUtenteSection() {
		
		uoAssociateUtenteForm = new DynamicForm();
		uoAssociateUtenteForm.setValuesManager(vm);
		uoAssociateUtenteForm.setWidth("100%");
		uoAssociateUtenteForm.setHeight("5");
		uoAssociateUtenteForm.setPadding(5);
		uoAssociateUtenteForm.setWrapItemTitles(false);
		uoAssociateUtenteForm.setNumCols(10);
		uoAssociateUtenteForm.setColWidths(10,10,10,10,10,10,10,10,10,10);

		uoAssociateUtenteItem = new UoAssociateUtenteItem(this) {
			
			@Override
			public boolean isViewMode() {
				return ((getLayout().getMode() != null && "view".equals(getLayout().getMode())));
			}
			
			@Override
			public boolean isEditMode() {
				return ((getLayout().getMode() != null && "edit".equals(getLayout().getMode())));
			}
			
			@Override
			public boolean isNewMode() {
				return (getLayout() == null || (getLayout().getMode() != null && "new".equals(getLayout().getMode())));
			}
			
			@Override
			public String getMode() {			
				return getLayout().getMode();
			}
			
			@Override
			public boolean isVersioneNestle() {
				return (GestioneUtentiLayout.isVersioneNESTLE());
			}
			
		};
		uoAssociateUtenteItem.setName("listaUoAssociateUtente");
		uoAssociateUtenteItem.setShowTitle(false);
		uoAssociateUtenteItem.setCanEdit(true);
		uoAssociateUtenteItem.setShowTitle(false);
		uoAssociateUtenteItem.setAttribute("obbligatorio", false);
		uoAssociateUtenteForm.setFields(uoAssociateUtenteItem);
		uoAssociateUtenteSection = new DetailSection(I18NUtil.getMessages().gestioneutenti_uoAssociateUtenteSection_title(), true, true, false, uoAssociateUtenteForm);
		lVLayoutMain.addMember(uoAssociateUtenteSection);
	}
	
	private void buildVisualizzaDocumentiFascicoliStruttura() {
		
	    visualizzaDocumentiFascicoliStrutturaForm = new DynamicForm();
	    visualizzaDocumentiFascicoliStrutturaForm.setValuesManager(vm);
	    visualizzaDocumentiFascicoliStrutturaForm.setWidth("100%");
	    visualizzaDocumentiFascicoliStrutturaForm.setHeight("5");
	    visualizzaDocumentiFascicoliStrutturaForm.setPadding(5);
		visualizzaDocumentiFascicoliStrutturaForm.setWrapItemTitles(false);
		visualizzaDocumentiFascicoliStrutturaForm.setNumCols(10);
		visualizzaDocumentiFascicoliStrutturaForm.setColWidths(10,10,10,10,10,10,10,10,10,10);

		visualizzaDocumentiFascicoliStrutturaItem = new VisualizzaDocumentiFascicoliStrutturaItem(this) {
			
			@Override
			public boolean isViewMode() {
				return ((getLayout().getMode() != null && "view".equals(getLayout().getMode())));
			}
			
			@Override
			public boolean isEditMode() {
				return ((getLayout().getMode() != null && "edit".equals(getLayout().getMode())));
			}
			
			@Override
			public boolean isNewMode() {
				return (getLayout() == null || (getLayout().getMode() != null && "new".equals(getLayout().getMode())));
			}
			
			@Override
			public String getMode() {	
				return getLayout().getMode();
			}
			
		};
		visualizzaDocumentiFascicoliStrutturaItem.setName("listaVisualizzaDocumentiFascicoliStruttura");
		visualizzaDocumentiFascicoliStrutturaItem.setShowTitle(false);
		visualizzaDocumentiFascicoliStrutturaItem.setCanEdit(true);
		visualizzaDocumentiFascicoliStrutturaItem.setAttribute("obbligatorio", false);
		
		visualizzaDocumentiFascicoliStrutturaForm.setFields(visualizzaDocumentiFascicoliStrutturaItem);
		
		visualizzaDocumentiFascicoliStrutturaSection = new DetailSection(I18NUtil.getMessages().visualizzaDocumentiFascicoliStruttura_title(), true, true, false, visualizzaDocumentiFascicoliStrutturaForm);
		lVLayoutMain.addMember(visualizzaDocumentiFascicoliStrutturaSection);
	}
	
	private void buildModificaDocumentiFascicoliStruttura() {
	
	    modificaDocumentiFascicoliStrutturaForm = new DynamicForm();
	    modificaDocumentiFascicoliStrutturaForm.setValuesManager(vm);
	    modificaDocumentiFascicoliStrutturaForm.setWidth("100%");
	    modificaDocumentiFascicoliStrutturaForm.setHeight("5");
	    modificaDocumentiFascicoliStrutturaForm.setPadding(5);
	    modificaDocumentiFascicoliStrutturaForm.setWrapItemTitles(false);
	    modificaDocumentiFascicoliStrutturaForm.setNumCols(10);
	    modificaDocumentiFascicoliStrutturaForm.setColWidths(10,10,10,10,10,10,10,10,10,10);
	
		modificaDocumentiFascicoliStrutturaItem = new ModificaDocumentiFascicoliStrutturaItem(this) {
			
			@Override
			public boolean isViewMode() {
				return ((getLayout().getMode() != null && "view".equals(getLayout().getMode())));
			}
			
			@Override
			public boolean isEditMode() {
				return ((getLayout().getMode() != null && "edit".equals(getLayout().getMode())));
			}
			
			@Override
			public boolean isNewMode() {
				return (getLayout() == null || (getLayout().getMode() != null && "new".equals(getLayout().getMode())));
			}
			
			@Override
			public String getMode() {
				return getLayout().getMode();
			}
			
		};
		modificaDocumentiFascicoliStrutturaItem.setName("listaModificaDocumentiFascicoliStruttura");
		modificaDocumentiFascicoliStrutturaItem.setShowTitle(false);
		modificaDocumentiFascicoliStrutturaItem.setCanEdit(true);
		modificaDocumentiFascicoliStrutturaItem.setAttribute("obbligatorio", false);
		
		modificaDocumentiFascicoliStrutturaForm.setFields(modificaDocumentiFascicoliStrutturaItem);
		
		modificaDocumentiFascicoliStrutturaSection = new DetailSection(I18NUtil.getMessages().modificaDocumentiFascicoliStruttura_title(), true, true, false, modificaDocumentiFascicoliStrutturaForm);
		lVLayoutMain.addMember(modificaDocumentiFascicoliStrutturaSection);
	}
	
	private void buildVisibilitaEmailTransitateCaselleAssociateUO() {
		
		visibEmailTransCaselleAssociateUOForm = new DynamicForm();
		visibEmailTransCaselleAssociateUOForm.setValuesManager(vm);
		visibEmailTransCaselleAssociateUOForm.setWidth("100%");
		visibEmailTransCaselleAssociateUOForm.setHeight("5");
		visibEmailTransCaselleAssociateUOForm.setPadding(5);
		visibEmailTransCaselleAssociateUOForm.setWrapItemTitles(false);
		visibEmailTransCaselleAssociateUOForm.setNumCols(10);
		visibEmailTransCaselleAssociateUOForm.setColWidths(10,10,10,10,10,10,10,10,10,10);
		visibEmailTransCaselleAssociateUOItem = new VisibEmailTransCaselleAssociateUOItem();
		visibEmailTransCaselleAssociateUOItem.setName("listaVisibEmailTransCaselleAssociateUO");
		visibEmailTransCaselleAssociateUOItem.setStartRow(true);
		visibEmailTransCaselleAssociateUOItem.setEndRow(true);
		visibEmailTransCaselleAssociateUOItem.setColSpan(10);
		visibEmailTransCaselleAssociateUOItem.setCanEdit(true);
		visibEmailTransCaselleAssociateUOItem.setShowTitle(false);
		visibEmailTransCaselleAssociateUOItem.setAttribute("obbligatorio", false);
		visibEmailTransCaselleAssociateUOForm.setFields(visibEmailTransCaselleAssociateUOItem);
		
		visibEmailTransCaselleAssociateUOSection = new DetailSection(I18NUtil.getMessages().gestioneutenti_visibEmailTransCaselleAssociateUOSection_title(), true, true, false, visibEmailTransCaselleAssociateUOForm);
		visibEmailTransCaselleAssociateUOSection.setVisible(isVisibilitaEmailCaselleAssociateUO());
		lVLayoutMain.addMember(visibEmailTransCaselleAssociateUOSection);
	}
	
	private void buildApplEstAccreditateSection() {
		
		applEstAccreditateForm = new DynamicForm();
		applEstAccreditateForm.setValuesManager(vm);
		applEstAccreditateForm.setWidth("100%");
		applEstAccreditateForm.setHeight("5");
		applEstAccreditateForm.setPadding(5);
		applEstAccreditateForm.setWrapItemTitles(false);
		applEstAccreditateForm.setNumCols(10);
		applEstAccreditateForm.setColWidths(10,10,10,10,10,10,10,10,10,10);
		
		applEstAccreditateItem = new ApplEstAccreditateItem(this){
			
			@Override
			public boolean isViewMode() {
				return ((getLayout().getMode() != null && "view".equals(getLayout().getMode())));
			}
			
			@Override
			public boolean isEditMode() {
				return ((getLayout().getMode() != null && "edit".equals(getLayout().getMode())));
			}
			
			@Override
			public boolean isNewMode() {
				return (getLayout() == null || (getLayout().getMode() != null && "new".equals(getLayout().getMode())));
			}
			
			@Override
			public String getMode() {
				return getLayout().getMode();
			}			
		};
		
		applEstAccreditateItem.setName("listaApplEstAccreditate");
		applEstAccreditateItem.setShowTitle(false);
		applEstAccreditateItem.setCanEdit(true);
		applEstAccreditateItem.setShowTitle(false);
		applEstAccreditateItem.setAttribute("obbligatorio", false);
		applEstAccreditateForm.setFields(applEstAccreditateItem);
		
		applEstAccreditateSection = new DetailSection(I18NUtil.getMessages().gestioneutenti_applEstAccreditateSection_title(), true, true, false, applEstAccreditateForm);
		lVLayoutMain.addMember(applEstAccreditateSection);
	}
	
	@Override
	public void editRecord(Record record) {
		GWTRestDataSource profiliUtenteDS = (GWTRestDataSource) idProfilo.getOptionDataSource();
		if (record.getAttribute("idProfilo") != null && !"".equals(record.getAttributeAsString("idProfilo"))) {
			profiliUtenteDS.addParam("idProfilo", record.getAttributeAsString("idProfilo"));
		} else {
			profiliUtenteDS.addParam("idProfilo", null);
		}
		idProfilo.setOptionDataSource(profiliUtenteDS);
		idProfilo.fetchData();
		
		super.editRecord(record);

		if (isEfactPersonalizzazioni()) {
			username.setDisabled(true);
		}

		// mostro o nascondo la sezione della societa'
		societaSection.setVisible(isSocietaAttiva());

		// mostro o nascondo la sezione delle aree commerciali
		areeCommercialiSection.setVisible(isAreaCommercialeAttiva());
		
		uoAssociateUtenteSection.setVisible(isAbilToInsUoAssociateUtente());
		visualizzaDocumentiFascicoliStrutturaSection.setVisible(isDbAttivaEstensioniPermessiDoc());
		modificaDocumentiFascicoliStrutturaSection.setVisible(isDbAttivaEstensioniPermessiDoc());
		
		applEstAccreditateSection.setVisible(isAbilToInsApplEstAccred());
	}

	@Override
	public void editNewRecord() {
		super.editNewRecord();
		username.setDisabled(false);

		// mostro o nascondo la sezione della societa'
		societaSection.setVisible(isSocietaAttiva());

		// mostro o nascondo la sezione delle aree commerciali
		areeCommercialiSection.setVisible(isAreaCommercialeAttiva());
		uoAssociateUtenteSection.setVisible(isAbilToInsUoAssociateUtente());
		visualizzaDocumentiFascicoliStrutturaSection.setVisible(isDbAttivaEstensioniPermessiDoc());
		modificaDocumentiFascicoliStrutturaSection.setVisible(isDbAttivaEstensioniPermessiDoc());
		applEstAccreditateSection.setVisible(false);
	}
	
	private void resetPassword() {
		String username = (String) vm.getValue("username");
		String email = (String) vm.getValue("email");
		String message = I18NUtil.getMessages().resetPasswordEsitoOK_message();
		((GestioneUtentiLayout) getLayout()).resetPassword(username, email, message, true);
	}

	private void manageOnOptionClick(String name, Record record) {

		if (name.equals("idLogo")) {
			String idLogo = record.getAttributeAsString("idLogo");
			String nomeLogo = record.getAttributeAsString("nomeLogo");
			form.setValue("idLogo", idLogo);
			form.setValue("nomeLogo", nomeLogo);
			idLogoItem.setValue(record.getAttribute("idLogo"));
			nomeLogoItem.setValue(record.getAttribute("nomeLogo"));
		}

		if (name.equals("idLingua")) {
			String idLingua = record.getAttributeAsString("idLingua");
			String descrizioneLingua = record.getAttributeAsString("descrizioneLingua");
			form.setValue("idLingua", idLingua);
			form.setValue("descrizioneLingua", descrizioneLingua);
			idLinguaItem.setValue(record.getAttribute("idLingua"));
			descrizioneLinguaItem.setValue(record.getAttribute("descrizioneLingua"));
		}

		if (name.equals("idMansione")) {
			String idMansione = record.getAttributeAsString("idMansione");
			String descrizioneMansione = record.getAttributeAsString("descrizioneMansione");
			form.setValue("idMansione", idMansione);
			form.setValue("descrizioneMansione", descrizioneMansione);
			idMansioneItem.setValue(record.getAttribute("idMansione"));
			descrizioneMansioneItem.setValue(record.getAttribute("descrizioneMansione"));

			// Lo salvo anche nel campo QUALIFICA perche' il servizio di ADD/UPDATE gestisece solo la QUALIFICA
			form.setValue("qualifica", idMansione);
			qualifica.setValue(record.getAttribute("idMansione"));

			// mostro o nascondo la sezione della societa'
			if (isSocietaAttiva()) {
				societaSection.setVisible(true);
			} else {
				societaUtentiItem.clearValue();
				societaSection.setVisible(false);
			}

			// mostro o nascondo la sezione delle aree commerciali
			if (isAreaCommercialeAttiva()) {
				areeCommercialiSection.setVisible(true);
			} else {
				areeCommercialiItem.clearValue();
				areeCommercialiSection.setVisible(false);
			}

			// mostro o nascondo il campo CodiceUtenteMansione
			String codiceUtenteMansione = null;
			form.setValue("codiceUtenteMansione", codiceUtenteMansione);
			codiceUtenteMansioneItem.setValue(codiceUtenteMansione);
			codiceUtenteMansioneItem.clearValue();
			form.redraw();
		}
	}

	private void manageClearSelect(String name) {
		if (name.equals("idLogo")) {
			String idLogo = null;
			String nomeLogo = null;
			form.setValue("idLogo", idLogo);
			form.setValue("nomeLogo", nomeLogo);
			nomeLogoItem.setValue(nomeLogo);
			idLogoItem.setValue(idLogo);
			idLogoItem.clearValue();
		}
		if (name.equals("idLingua")) {
			String idLingua = null;
			String descrizioneLingua = null;
			form.setValue("idLingua", idLingua);
			form.setValue("descrizioneLingua", descrizioneLingua);
			descrizioneLinguaItem.setValue(descrizioneLingua);
			idLinguaItem.setValue(idLingua);
			idLinguaItem.clearValue();
		}

		if (name.equals("idMansione")) {
			String idMansione = null;
			String descrizioneMansione = null;

			form.setValue("idMansione", idMansione);
			form.setValue("descrizioneMansione", descrizioneMansione);
			descrizioneMansioneItem.setValue(descrizioneMansione);
			idMansioneItem.setValue(idMansione);
			idMansioneItem.clearValue();

			// Lo cancello anche nel campo QUALIFICA perche' il servizio di ADD/UPDATE gestisce solo la QUALIFICA
			qualifica.setValue(idMansione);
			qualifica.clearValue();

			// mostro o nascondo la sezione della societa'
			if (isSocietaAttiva()) {
				societaSection.setVisible(true);
			} else {
				societaUtentiItem.clearValue();
				societaSection.setVisible(false);
			}

			// pulisco e nascondo la sezione delle aree commerciali
			areeCommercialiItem.clearValue();
			areeCommercialiSection.setVisible(false);

			String codiceUtenteMansione = null;
			form.setValue("codiceUtenteMansione", codiceUtenteMansione);
			codiceUtenteMansioneItem.setValue(codiceUtenteMansione);
			codiceUtenteMansioneItem.clearValue();
			form.redraw();

		}
	}

	protected void buildTabSet() {
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		setPaddingAsLayoutMargin(false);
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);
	}

	private void createLayoutMain() {
		
		lVLayoutMain = new VLayout();
		lVLayoutMain.setWidth100();
		lVLayoutMain.setOverflow(Overflow.AUTO);
		buildMainInfoSection();
		buildIndirizziSection();
		buildUoAssociateUtenteSection();
		//CR Visualizzazione di documenti e fascicoli assegnati/inviati in copia alla struttura 13/10/2019
		buildVisualizzaDocumentiFascicoliStruttura();
		buildModificaDocumentiFascicoliStruttura();
		buildVisibilitaEmailTransitateCaselleAssociateUO();
		buildApplEstAccreditateSection();
		buildAreeCommercialiSection();
		buildSocietaSection();
		buildGrupppClientiSection();
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();
		lVLayoutMain.addMember(lVLayoutSpacer);
		tabMain = new Tab("main");
		tabMain.setPrompt("Dati generali");
		tabMain.setTitle("Dati generali");
		tabMain.setPane(lVLayoutMain);
	}

	private SelectItemWithDisplay createSelectItem(String name, String title, String datasourceName, String[] campiVisibili, String[] campiHidden,
			String[] descrizioni, Object[] width, int widthTotale, String idField, String displayField, boolean isClearable) {
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(datasourceName, idField, FieldType.TEXT, campiVisibili, true);
		SelectItemWithDisplay lSelectItemWithDisplay = new SelectItemWithDisplay(name, title, lGwtRestDataSource) {

			@Override
			public void onOptionClick(Record record) {
				manageOnOptionClick(getName(), record);
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				manageClearSelect(getName());
			}
		};
		int i = 0;
		List<ListGridField> lList = new ArrayList<ListGridField>();
		for (String lString : campiVisibili) {
			ListGridField field = new ListGridField(lString, descrizioni[i]);
			if (width[i] instanceof String) {
				field.setWidth((String) width[i]);
			} else
				field.setWidth((Integer) width[i]);

			i++;
			lList.add(field);
		}
		for (String lString : campiHidden) {
			ListGridField field = new ListGridField(lString, lString);
			field.setHidden(true);
			lList.add(field);
		}
		lSelectItemWithDisplay.setPickListFields(lList.toArray(new ListGridField[] {}));
		lSelectItemWithDisplay.setFilterLocally(true);
		lSelectItemWithDisplay.setValueField(idField);
		lSelectItemWithDisplay.setOptionDataSource(lGwtRestDataSource);
		lSelectItemWithDisplay.setWidth(widthTotale);
		lSelectItemWithDisplay.setRequired(false);
		lSelectItemWithDisplay.setClearable(isClearable);
		lSelectItemWithDisplay.setShowIcons(isClearable);
		lSelectItemWithDisplay.setCachePickListResults(false);
		lSelectItemWithDisplay.setDisplayField(displayField);
		return lSelectItemWithDisplay;
	}

	public boolean isMansioneAttiva() {

		boolean ret = false;
		// Se l'applicazione e' EIIFACT verifico se il parametro ATTIVA_GEST_MANSIONE = true/false
		if (getApplicationName() != null && (getApplicationName().equalsIgnoreCase("EIIFact"))) {
			// Mostro/Nascondo la colonna MANSIONE se il parametro ATTIVA_GEST_MANSIONE = true/false
			ret = (isGestioneMansione());
		}
		return ret;
	}

	public boolean isQualificaAttiva() {

		boolean ret = true;

		// Se l'applicazione e' EIIFACT verifico se il parametro ATTIVA_GEST_MANSIONE = true/false
		if (getApplicationName() != null && (getApplicationName().equalsIgnoreCase("EIIFact"))) {

			// Mostro la colonna QUALIFICA se il parametro ATTIVA_GEST_MANSIONE = false
			if (isGestioneMansione()) {
				ret = false;
			}
			// Nascondo la colonna QUALIFICA se il parametro ATTIVA_GEST_MANSIONE = true
			else {
				ret = true;
			}
		}
		return ret;
	}

	// Il campo codiceUtenteMansione deve essere visibile se la gestione della mansione e' attiva e la mansione e stata selezionata
	public boolean isCodiceUtenteMansioneAttiva() {
		boolean ret = true;

		ret = isMansioneAttiva();

		// Se la gestione della mansione e' attiva, verifico se il campo codiceUtenteMansione devo mostrarlo o nasconderlo
		if (ret == true) {

			// verifico se e' stata selezionata una mansione
			String mansione = (String) vm.getValue("qualifica");

			if (mansione == null)
				mansione = "";

			// Se la mansione e' valorizza allora mostro il campo codiceUtenteMansione
			if (!mansione.equalsIgnoreCase("")) {
				ret = true;
			}
			// Altrimenti lo nascondo
			else {
				ret = false;
			}
		}
		return ret;
	}

	// La sezione AREA COMMERCIALE deve essere visibile se la gestione e' attiva e la mansione selezionata E' "commerciale".
	public boolean isAreaCommercialeAttiva() {
		boolean ret = true;

		ret = (isGestioneAreaCommerciale());

		// Se la gestione dell'area commerciale e' attiva, verifico se devo mostrarla o nasconderla
		if (ret == true) {

			// verifico se e' stata selezionata la mansione "commerciale"
			String mansione = (String) vm.getValue("qualifica");

			if (mansione == null)
				mansione = "";

			// Se la mansione e' valorizza verifico il suo valore
			if (!mansione.equalsIgnoreCase("")) {

				// Se la mansione NON E' "commerciale" allora la sezione la nascondo
				if (!mansione.toLowerCase().equalsIgnoreCase("commerciale")) {
					ret = false;
				}
			}
			// Altrimenti la nascondo
			else {
				ret = false;
			}
		}
		return ret;
	}

	// La sezione SOCIETA' deve essere visibile se la gestione e' attiva e la mansione selezionata NON E' NE "agente" NE "commerciale".
	// La sezione SOCIETA' deve essere nascosta se la gestione e' disattivata oppure
	// la combo delle mansioni e' attiva ed e' stata selezionata una voce "agente" oppure "commerciale"
	public boolean isSocietaAttiva() {
		boolean ret = true;

		ret = (isGestioneSocieta());

		// Se la gestione della societa' e' attiva, verifico se devo mostrarla o nasconderla
		if (ret == true) {

			// Se e' attiva la gestione della mansione allora verifico se e' stata selezionata la mansione "agente" o "commerciale" o "venditore" o "coordinatore"  o "alg_admin" 
			if (isMansioneAttiva()) {

				String mansione = (String) vm.getValue("qualifica");

				if (mansione == null)
					mansione = "";

				// Se la mansione e' valorizza verifico il suo valore
				if (!mansione.equalsIgnoreCase("")) {

					// Se la mansione E' "agente" o "commerciale" o "venditore" o "coordinatore"  o "alg_admin" allora nascondo la societa'
					if (mansione.toLowerCase().equalsIgnoreCase("agente")       || 
						mansione.toLowerCase().equalsIgnoreCase("commerciale")  ||
						mansione.toLowerCase().equalsIgnoreCase("venditore")    ||
						mansione.toLowerCase().equalsIgnoreCase("coordinatore") ||
						mansione.toLowerCase().equalsIgnoreCase("alg_admin"))						
					{
						ret = false;
					}
				}
			}
		}
		return ret;
	}

	public String getApplicationName() {
		String applicationName = null;
		try {
			Dictionary dictionary = Dictionary.getDictionary("params");
			applicationName = dictionary != null && dictionary.get("applicationName") != null ? dictionary.get("applicationName") : "";
		} catch (Exception e) {
			applicationName = "";
		}
		return applicationName;
	}

	public boolean isEfactPersonalizzazioni() {
		return AurigaLayout.getParametroDBAsBoolean("EFACT_PERSONALIZZAZIONI");
	}

	public boolean isPersonaFisica() {
		return true;
	}

	public boolean isGestioneClienti() {
		return AurigaLayout.getParametroDBAsBoolean("GEST_USER_CLIENTI");
	}

	public boolean isGestioneSocieta() {
		return AurigaLayout.getParametroDBAsBoolean("GEST_USER_SOCIETA");
	}

	public boolean isGestioneMansione() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_MANSIONE");
	}

	public boolean isGestioneAreaCommerciale() {
		return false; //AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_MANSIONE");
	}

	public boolean isAbilToInsUoAssociateUtente() {
		
		if(Layout.isPrivilegioAttivo("SIC/SO;I") ){
			
			// Se e' AURIGA mostro la sezione
			if (!getApplicationName().equalsIgnoreCase("EIIFact")){
					return true;					
			}
			// Se e' DIGIDOC mostro la sezione solo se l'utente e' INTERNO 
			else if (getApplicationName().equalsIgnoreCase("EIIFact")){
				String flgUtente = vm.getValueAsString("flgUtenteInternoEsterno");
				if(flgUtente!=null && !flgUtente.equalsIgnoreCase("") && flgUtente.equalsIgnoreCase("I"))	{
			    	return true;
			    }
			}		
		}
		return false;
	}
	
	public boolean isAbilToInsApplEstAccred() {
		return (!getApplicationName().equalsIgnoreCase("EIIFact") && Layout.isPrivilegioAttivo("SIC/UT;I")    );
	}
	
	public boolean isVisibilitaEmailCaselleAssociateUO() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_VISIB_EMAIL_CASELLE_UO");
	}
	
	public boolean isDbAttivaEstensioniPermessiDoc() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESTENSIONI_PERMESSI_DOC");
	}

	public boolean isInviaEmailPwdCreaUtente() {
		return AurigaLayout.getParametroDBAsBoolean("INVIA_EMAIL_PWD_CREA_UTENTE");
	}
}