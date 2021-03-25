package it.eng.auriga.ui.module.layout.client;

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SaveImpostazioniFirmaWindow extends ModalWindow {

	private static final String TIPO_FIRMA_CLIENT = "C";
	private static final String TIPO_FIRMA_REMOTA =  "R";
	private static final String TIPO_FIRMA_REMOTA_WS =  "W";
	private static final String TIPO_FIRMA_REMOTA_AUTOMATICA = "A";
	
	private String pwdDB = null;
//	private String pwdDBRichOtp = null;

	private HiddenItem attivaFirmaInDelegaItem;
	private HiddenItem canSendOtpViaCallItem;
	private HiddenItem canSendOtpViaSmsItem;
	
	private ValuesManager vm;
	private DynamicForm mDynamicForm;
	private SelectItem tipoFirmaSelectItem;
	private SelectItem providerFirmaRemotaItem;
	private TextItem userIdItem;
	private TextItem firmaInDelegaItem;
	private FormItem passwordItem;
	private FormItem confermaPasswordItem;
	private ImgButtonItem cambiaPasswordButton;
	
	private TextItem usernameRichOtpItem;
//	private FormItem passwordRichOtpItem;
//	private FormItem confermaPasswordRichOtpItem;
//	private ImgButtonItem cambiaPasswordRichOtpButton;
	
	private CheckboxItem showSendOtpViaSmsItem;
	private CheckboxItem showSendOtpViaCallItem;
	
	private Button saveButton;
	private Map providerValueMap;

	public SaveImpostazioniFirmaWindow(final Map providerValueMap) {
		
		super("config_utente_impostazioniFirma", true);

		setTitle(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_title());
		setIcon("file/mini_sign.png");

		this.vm = new ValuesManager();
		
		setAutoCenter(true);
		setWidth(600);
		setHeight(200);

		this.providerValueMap = providerValueMap;
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		mDynamicForm = new DynamicForm();
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(5);
		mDynamicForm.setColWidths(10, 10, 10, 10, "*");
		mDynamicForm.setCellPadding(7);
		mDynamicForm.setCanSubmit(true);
		mDynamicForm.setAlign(Alignment.LEFT);
		mDynamicForm.setTop(50);
		mDynamicForm.setValuesManager(vm);
		
		attivaFirmaInDelegaItem = new HiddenItem("attivaFirmaInDelega");
		canSendOtpViaSmsItem = new HiddenItem("canSendOtpViaSms");
		canSendOtpViaCallItem = new HiddenItem("canSendOtpViaCall");

		LinkedHashMap<String, String> tipoFirmaMap = new LinkedHashMap<String, String>();
		tipoFirmaMap.put(TIPO_FIRMA_CLIENT, I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_client_value());
		tipoFirmaMap.put(TIPO_FIRMA_REMOTA, I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_remota_value());
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_FIRMA_ESTERNA")) {
			tipoFirmaMap.put(TIPO_FIRMA_REMOTA_WS, "Remota (con inserimento OTP)");	
		}
		tipoFirmaMap.put(TIPO_FIRMA_REMOTA_AUTOMATICA, I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_remotaAutomatica_value());
		
		tipoFirmaSelectItem = new SelectItem();
		tipoFirmaSelectItem.setName("tipoFirma");
		tipoFirmaSelectItem.setTitle(setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_metodoFirma_title()));
		tipoFirmaSelectItem.setShowTitle(true);
		tipoFirmaSelectItem.setColSpan(1);
		tipoFirmaSelectItem.setWidth(300);
		tipoFirmaSelectItem.setAlign(Alignment.CENTER);
		tipoFirmaSelectItem.setValueMap(tipoFirmaMap);
		tipoFirmaSelectItem.setAllowEmptyValue(false);
		tipoFirmaSelectItem.setRequired(true);
		tipoFirmaSelectItem.setDefaultValue(getDefaultFirmaValue());
		tipoFirmaSelectItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				setValues(vm.getValuesAsRecord());
				mDynamicForm.markForRedraw();
			}
		});
		
		providerFirmaRemotaItem = new SelectItem("provider_firma_remota", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_providerFirmaHsmSelect());
		providerFirmaRemotaItem.setValueMap(providerValueMap);
		providerFirmaRemotaItem.setAllowEmptyValue(false);
		providerFirmaRemotaItem.setWidth(300);
		providerFirmaRemotaItem.setStartRow(true);
		providerFirmaRemotaItem.setDefaultToFirstOption(true);
		providerFirmaRemotaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (TIPO_FIRMA_REMOTA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString()) ||
						   TIPO_FIRMA_REMOTA_AUTOMATICA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString())) && providerValueMap.size() > 1;
			}
		});
		
		providerFirmaRemotaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				setValues(vm.getValuesAsRecord());
				mDynamicForm.markForRedraw();
			}
		});
		
		userIdItem = new TextItem("userId",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_userId()));
		userIdItem.setColSpan(1);
		userIdItem.setWidth(300);
		userIdItem.setStartRow(true);
		userIdItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return TIPO_FIRMA_REMOTA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString()) ||
				   TIPO_FIRMA_REMOTA_AUTOMATICA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString());
			}
		});
		userIdItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return  TIPO_FIRMA_REMOTA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString()) ||
						   TIPO_FIRMA_REMOTA_AUTOMATICA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString());
			}
		}));
		userIdItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		firmaInDelegaItem = new TextItem("firmaInDelega",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_firmaInDelega()));
		firmaInDelegaItem.setColSpan(1);
		firmaInDelegaItem.setWidth(300);
		firmaInDelegaItem.setStartRow(true);
		firmaInDelegaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return showFirmaInDelega();
			}
		});
		firmaInDelegaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		passwordItem = new PasswordItem("password", setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_password()));
		passwordItem.setAttribute("obbligatorio", true);
		passwordItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		passwordItem.setColSpan(1);
		passwordItem.setWidth(300);
		passwordItem.setStartRow(true);
		passwordItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue());
			}
		});
		RequiredIfValidator reqIfValPwd = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue());
			}
		});
		passwordItem.setValidators(reqIfValPwd);
		passwordItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		confermaPasswordItem = new PasswordItem("confermaPassword",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_confermaPassword()));
		confermaPasswordItem.setAttribute("obbligatorio", true);
		confermaPasswordItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		confermaPasswordItem.setColSpan(1);
		confermaPasswordItem.setWidth(300);
		confermaPasswordItem.setStartRow(true);
		confermaPasswordItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equalsIgnoreCase(tipoFirmaSelectItem.getValueAsString()) &&
					   (pwdDB == null || "".equalsIgnoreCase(pwdDB));
			}
		});
		RequiredIfValidator reqIfValConfermaPwd = new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue());
			}
		});
		CustomValidator validatorConfermaPwd = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				
				boolean isVerify = true;
				if(TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue())) {
					String pwd = passwordItem.getValue() != null ? (String)passwordItem.getValue() : "";
					String confermaPwd = confermaPasswordItem.getValue() != null ? (String) confermaPasswordItem.getValue() : "";
					if(!"".equalsIgnoreCase(confermaPwd) && !pwd.equalsIgnoreCase(confermaPwd)) {
						isVerify = false;
					}
				} 
				return isVerify;
			}
		};
		validatorConfermaPwd.setErrorMessage("Le password non coincidono!");
		confermaPasswordItem.setValidators(reqIfValConfermaPwd,validatorConfermaPwd);
		confermaPasswordItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		cambiaPasswordButton = new ImgButtonItem("cambiaPassword", "buttons/reset_pwd.png", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_cambiaPassword());
		cambiaPasswordButton.setAlwaysEnabled(true);
		cambiaPasswordButton.setColSpan(1);
		cambiaPasswordButton.setStartRow(false);
		cambiaPasswordButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue()) &&
					   (pwdDB != null && !"".equalsIgnoreCase(pwdDB));
			}
		});
		cambiaPasswordButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				
				passwordItem.setCanEdit(true);
				pwdDB = null;
			}
		});
		cambiaPasswordButton.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				
				mDynamicForm.markForRedraw();
			}
		});
		
		usernameRichOtpItem = new TextItem("usernameRichOtp",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_usernameRichOtp()));
		usernameRichOtpItem.setColSpan(1);
		usernameRichOtpItem.setWidth(300);
		usernameRichOtpItem.setStartRow(true);
		usernameRichOtpItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return showCredenzialiOtp();
			}
		});
		usernameRichOtpItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
//		passwordRichOtpItem = new PasswordItem("passwordRichOtp", setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_passwordRichOtp()));
//		passwordRichOtpItem.setAttribute("obbligatorio", true);
//		passwordRichOtpItem.setType(FormItemType.PASSWORD_ITEM.getValue());
//		passwordRichOtpItem.setColSpan(1);
//		passwordRichOtpItem.setWidth(300);
//		passwordRichOtpItem.setStartRow(true);
//		passwordRichOtpItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				
//				return showCredenzialiOtp();
//			}
//		});
//		RequiredIfValidator reqIfValPwdRichOtp = new RequiredIfValidator(new RequiredIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem formItem, Object value) {
//				
//				return showCredenzialiOtp();
//			}
//		});
//		passwordRichOtpItem.setValidators(reqIfValPwdRichOtp);
//		passwordRichOtpItem.addChangedHandler(new ChangedHandler() {
//			
//			@Override
//			public void onChanged(ChangedEvent event) {
//				mDynamicForm.markForRedraw();
//			}
//		});
		
//		confermaPasswordRichOtpItem = new PasswordItem("confermaPasswordRichOtp",setTitleAlign(I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_confermaPasswordRichOtp()));
//		confermaPasswordRichOtpItem.setAttribute("obbligatorio", true);
//		confermaPasswordRichOtpItem.setType(FormItemType.PASSWORD_ITEM.getValue());
//		confermaPasswordRichOtpItem.setColSpan(1);
//		confermaPasswordRichOtpItem.setWidth(300);
//		confermaPasswordRichOtpItem.setStartRow(true);
//		confermaPasswordRichOtpItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				
//				return (showCredenzialiOtp() && (pwdDBRichOtp == null || "".equalsIgnoreCase(pwdDBRichOtp)));
//			}
//		});
		
//		RequiredIfValidator reqIfValConfermaPwdRichOtp = new RequiredIfValidator(new RequiredIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem formItem, Object value) {
//				
//				return showCredenzialiOtp();
//			}
//		});
//		CustomValidator validatorConfermaPwdRichOtp = new CustomValidator() {
//			
//			@Override
//			protected boolean condition(Object value) {
//				
//				boolean isVerify = true;
//				if(TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue())) {
//					String pwdOtp = passwordRichOtpItem.getValue() != null ? (String)passwordRichOtpItem.getValue() : "";
//					String confermaPwdOtp = confermaPasswordRichOtpItem.getValue() != null ? (String) confermaPasswordRichOtpItem.getValue() : "";
//					if(!"".equalsIgnoreCase(confermaPwdOtp) && !pwdOtp.equalsIgnoreCase(confermaPwdOtp)) {
//						isVerify = false;
//					}
//				} 
//				return isVerify;
//			}
//		};
//		validatorConfermaPwdRichOtp.setErrorMessage("Le password non coincidono!");
//		confermaPasswordRichOtpItem.setValidators(reqIfValConfermaPwdRichOtp,validatorConfermaPwdRichOtp);
//		confermaPasswordRichOtpItem.addChangedHandler(new ChangedHandler() {
//			
//			@Override
//			public void onChanged(ChangedEvent event) {
//				mDynamicForm.markForRedraw();
//			}
//		});
		
//		cambiaPasswordRichOtpButton = new ImgButtonItem("cambiaPasswordRichOtp", "buttons/reset_pwd.png", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_cambiaPasswordRichOtp());
//		cambiaPasswordRichOtpButton.setAlwaysEnabled(true);
//		cambiaPasswordRichOtpButton.setColSpan(1);
//		cambiaPasswordRichOtpButton.setStartRow(false);
//		cambiaPasswordRichOtpButton.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				
//				return TIPO_FIRMA_REMOTA_AUTOMATICA.equals((String)tipoFirmaSelectItem.getValue()) &&
//					   (pwdDBRichOtp != null && !"".equalsIgnoreCase(pwdDBRichOtp));
//			}
//		});
//		cambiaPasswordRichOtpButton.addIconClickHandler(new IconClickHandler() {
//			
//			@Override
//			public void onIconClick(IconClickEvent event) {
//				
//				passwordRichOtpItem.setCanEdit(true);
//				pwdDBRichOtp = null;
//			}
//		});
//		cambiaPasswordRichOtpButton.addChangedHandler(new ChangedHandler() {
//			
//			@Override
//			public void onChanged(ChangedEvent event) {
//				
//				mDynamicForm.markForRedraw();
//			}
//		});
		
		SpacerItem spacerShowSendOtpViaSmsItem = new SpacerItem();
		spacerShowSendOtpViaSmsItem.setColSpan(1);
		spacerShowSendOtpViaSmsItem.setStartRow(true);
		spacerShowSendOtpViaSmsItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return showSendOtpViaSms();	
			}
		});
		
		showSendOtpViaSmsItem = new CheckboxItem("showSendOtpViaSms", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_showGeneraOtpViaSms());
		showSendOtpViaSmsItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSendOtpViaSms();	
			}
		});
		
		SpacerItem spacerShowSendOtpViaCallItem = new SpacerItem();
		spacerShowSendOtpViaCallItem.setColSpan(1);
		spacerShowSendOtpViaCallItem.setStartRow(true);
		spacerShowSendOtpViaCallItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return showSendOtpViaCall();	
			}
		});
		
		showSendOtpViaCallItem = new CheckboxItem("showSendOtpViaCall", I18NUtil.getMessages().configUtenteMenuImpostazioniFirma_showGeneraOtpViaCall());
		showSendOtpViaCallItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSendOtpViaCall();
			}
		});
		
//		mDynamicForm.setItems(attivaFirmaInDelegaItem, canSendOtpViaSmsItem, canSendOtpViaCallItem, tipoFirmaSelectItem, providerFirmaRemotaItem, userIdItem, firmaInDelegaItem, passwordItem, cambiaPasswordButton, confermaPasswordItem, usernameRichOtpItem, passwordRichOtpItem, confermaPasswordRichOtpItem, cambiaPasswordRichOtpButton, spacerShowSendOtpViaSmsItem, showSendOtpViaSmsItem, spacerShowSendOtpViaCallItem, showSendOtpViaCallItem);
		mDynamicForm.setItems(attivaFirmaInDelegaItem, canSendOtpViaSmsItem, canSendOtpViaCallItem, tipoFirmaSelectItem, providerFirmaRemotaItem, userIdItem, firmaInDelegaItem, passwordItem, cambiaPasswordButton, confermaPasswordItem, usernameRichOtpItem, spacerShowSendOtpViaSmsItem, showSendOtpViaSmsItem, spacerShowSendOtpViaCallItem, showSendOtpViaCallItem);

		saveButton = new Button("Salva");
		saveButton.setIcon("ok.png");
		saveButton.setIconSize(16);
		saveButton.setAutoFit(false);
		saveButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (vm.validate()) {
					// Devo azzerare i valori delle impostazioni non abilitate
					String attivaFirmaInDelega = mDynamicForm.getValueAsString("attivaFirmaInDelega");
					if (attivaFirmaInDelega == null || "".equalsIgnoreCase(attivaFirmaInDelega) || "false".equalsIgnoreCase(attivaFirmaInDelega)) {
						mDynamicForm.setValue("firmaInDelega", "");
					}
					String canSendOtpViaSms = mDynamicForm.getValueAsString("canSendOtpViaSms");
					if (canSendOtpViaSms == null || "".equalsIgnoreCase(canSendOtpViaSms) || "false".equalsIgnoreCase(canSendOtpViaSms)) {
						mDynamicForm.setValue("showSendOtpViaSms", false);
					}
					String canSendOtpViaCall = mDynamicForm.getValueAsString("canSendOtpViaCall");
					if (canSendOtpViaCall == null || "".equalsIgnoreCase(canSendOtpViaCall) || "false".equalsIgnoreCase(canSendOtpViaCall)) {
						mDynamicForm.setValue("showSendOtpViaCall", false);
					}
		
					Map mapToSave = mDynamicForm.getValuesAsRecord().toMap();
					mapToSave.remove("canSendOtpViaSms");
					mapToSave.remove("canSendOtpViaCall");
					Record prefToSave = new Record(mapToSave);
					manageOnOkButtonClick(prefToSave);
					markForDestroy();
				}
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(saveButton);
		
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
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.addMember(mDynamicForm);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);
	}

	protected boolean showFirmaInDelega() {
		String attivaFirmaInDelega = mDynamicForm.getValueAsString("attivaFirmaInDelega");
		if (attivaFirmaInDelega != null && "true".equalsIgnoreCase(attivaFirmaInDelega) && (TIPO_FIRMA_REMOTA.equals(mDynamicForm.getValueAsString("tipoFirma")) || TIPO_FIRMA_REMOTA_AUTOMATICA.equals(mDynamicForm.getValueAsString("tipoFirma")))) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean showCredenzialiOtp() {
		String providerFirma = mDynamicForm.getValueAsString("provider_firma_remota");
		boolean mostraCredenzialiOtp = FirmaUtility.getValoreVariabileHsmParamsAsBoolean("useDifferentCredentialForOtpRequest", providerFirma);
		if (mostraCredenzialiOtp && TIPO_FIRMA_REMOTA.equals(mDynamicForm.getValueAsString("tipoFirma"))) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean showSendOtpViaSms() {
		String canSendOtpViaSms = mDynamicForm.getValueAsString("canSendOtpViaSms");
		if (canSendOtpViaSms != null && "true".equalsIgnoreCase(canSendOtpViaSms) && TIPO_FIRMA_REMOTA.equals(mDynamicForm.getValueAsString("tipoFirma"))) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean showSendOtpViaCall() {
		String canSendOtpViaCall = mDynamicForm.getValueAsString("canSendOtpViaCall");
		if (canSendOtpViaCall != null && "true".equalsIgnoreCase(canSendOtpViaCall) && TIPO_FIRMA_REMOTA.equals(mDynamicForm.getValueAsString("tipoFirma"))) {
			return true;
		} else {
			return false;
		}
	}

	public void clearValues() {
		mDynamicForm.clearValues();
	}

	public void setValues(Record values) {
		if (values != null) {
			if(values.getAttributeAsString("password") != null && !"".equals(values.getAttributeAsString("password"))){
				pwdDB = values.getAttributeAsString("password");
				passwordItem.setCanEdit(false);
			}
			
//			if(values.getAttributeAsString("passwordRichOtp") != null && !"".equals(values.getAttributeAsString("passwordRichOt"))){
//				pwdDBRichOtp = values.getAttributeAsString("passwordRichOtp");
//				passwordRichOtpItem.setCanEdit(false);
//			}
			
			Boolean attivaFirmaInDelega = false;
			Boolean canSendOtpViaSms = false;
			Boolean canSendOtpViaCall = false;

			if (values.getAttribute("tipoFirma") != null && (TIPO_FIRMA_REMOTA.equals(values.getAttributeAsString("tipoFirma")) || TIPO_FIRMA_REMOTA_AUTOMATICA.equals(values.getAttributeAsString("tipoFirma")))) {
				String providerFirma = null;
				if (values.getAttribute("provider_firma_remota") != null && !"".equals(values.getAttributeAsString("provider_firma_remota"))) {
					providerFirma = values.getAttributeAsString("provider_firma_remota");
				} else if (providerValueMap.size() == 1){
					providerFirma = (String) providerValueMap.get(providerValueMap.keySet().toArray()[0]);
				} else {
					providerFirma = null;
				}
				
				Map providerFirmaRemotaItemValueMap = providerFirmaRemotaItem.getValueMap();
				if (providerFirmaRemotaItemValueMap != null && providerFirmaRemotaItemValueMap.size() > 0 && !providerFirmaRemotaItemValueMap.containsKey(providerFirma)) {
					String firstValue = (String) providerValueMap.get(providerValueMap.keySet().toArray()[0]);
					providerFirma = firstValue;
					values.setAttribute("provider_firma_remota", firstValue);
				}
							
				attivaFirmaInDelega = FirmaUtility.getValoreVariabileHsmParamsAsBoolean("attivaFirmaInDelega", providerFirma);
				canSendOtpViaCall = FirmaUtility.getValoreVariabileHsmParamsAsBoolean("canSendOtpViaCall", providerFirma);
				canSendOtpViaSms =  FirmaUtility.getValoreVariabileHsmParamsAsBoolean("canSendOtpViaSms", providerFirma);
			}
			
			values.setAttribute("attivaFirmaInDelega", attivaFirmaInDelega);
			values.setAttribute("canSendOtpViaCall", canSendOtpViaCall);
			values.setAttribute("canSendOtpViaSms", canSendOtpViaSms);
			mDynamicForm.editRecord(values);
		} else {
			mDynamicForm.editNewRecord();
		}
		mDynamicForm.clearErrors(true);
	}

	public void manageOnOkButtonClick(Record values) {

	}
	
	public static String getDefaultFirmaValue() {

		if("JNLP".equals(AurigaLayout.getParametroDB("MODALITA_FIRMA"))
			|| "APPLET".equals(AurigaLayout.getParametroDB("MODALITA_FIRMA"))) {
				return TIPO_FIRMA_CLIENT;
		} else if("HSM".equals(AurigaLayout.getParametroDB("MODALITA_FIRMA")) &&
				!AurigaLayout.getParametroDBAsBoolean("FIRMA_AUTOMATICA")) {
				return TIPO_FIRMA_REMOTA;
		} else if("HSM".equals(AurigaLayout.getParametroDB("MODALITA_FIRMA")) &&
				AurigaLayout.getParametroDBAsBoolean("FIRMA_AUTOMATICA")) {
				return TIPO_FIRMA_REMOTA_AUTOMATICA;
		} else 
		      return "";
	}

	private String setTitleAlign(String title) {
		return "<span style=\"width: 220px; display: inline-block;\">" + title + "</span>";
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}

}