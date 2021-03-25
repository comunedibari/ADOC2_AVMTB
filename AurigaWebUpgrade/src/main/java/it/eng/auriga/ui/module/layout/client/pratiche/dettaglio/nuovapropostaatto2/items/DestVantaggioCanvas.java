package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class DestVantaggioCanvas extends ReplicableCanvas {
	
	private RadioGroupItem tipoPersonaItem;
	private TextItem cognomeItem;
	private TextItem nomeItem;
	private TextItem ragioneSocialeItem;
	private TextItem codFiscalePIVAItem;
	private ExtendedNumericItem importoItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public DestVantaggioCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(10);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		tipoPersonaItem = new RadioGroupItem("tipoPersona", "Persona");
		tipoPersonaItem.setStartRow(true);
		tipoPersonaItem.setValueMap("fisica", "giuridica");
		tipoPersonaItem.setVertical(false);
		tipoPersonaItem.setWrap(false);
		tipoPersonaItem.setShowDisabled(false);
		tipoPersonaItem.setRequired(true);				
		tipoPersonaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredDestVantaggio()) {
					tipoPersonaItem.setAttribute("obbligatorio", true);
					tipoPersonaItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Persona"));
				} else {
					tipoPersonaItem.setAttribute("obbligatorio", false);
					tipoPersonaItem.setTitle("Persona");
				}
				return true;
			}
		});
		tipoPersonaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return ((DestVantaggioItem)getItem()).isRequiredDestVantaggio();
			}
		}));		
		tipoPersonaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		cognomeItem = new TextItem("cognome", "Cognome");
		cognomeItem.setWidth(150);
		cognomeItem.setColSpan(1);
		cognomeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredDestVantaggio()) {
					cognomeItem.setAttribute("obbligatorio", true);
					cognomeItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Cognome"));
				} else {					
					cognomeItem.setAttribute("obbligatorio", false);
					cognomeItem.setTitle("Cognome");					
				}
				boolean isPersonaFisica = tipoPersonaItem.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
				return isPersonaFisica;
			}
		});
		cognomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean isPersonaFisica = tipoPersonaItem.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
				return ((DestVantaggioItem)getItem()).isRequiredDestVantaggio() && isPersonaFisica;
			}
		}));
		
		nomeItem = new TextItem("nome", "Nome");
		nomeItem.setWidth(150);
		nomeItem.setColSpan(1);
		nomeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredDestVantaggio()) {
					nomeItem.setAttribute("obbligatorio", true);
					nomeItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Nome"));
				} else {					
					nomeItem.setAttribute("obbligatorio", false);
					nomeItem.setTitle("Nome");										
				}			
				boolean isPersonaFisica = tipoPersonaItem.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
				return isPersonaFisica;
			}
		});
		nomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean isPersonaFisica = tipoPersonaItem.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
				return ((DestVantaggioItem)getItem()).isRequiredDestVantaggio() && isPersonaFisica;
			}
		}));
		
		ragioneSocialeItem = new TextItem("ragioneSociale", "Ragione sociale");
		ragioneSocialeItem.setWidth(300);
		ragioneSocialeItem.setColSpan(2);
		ragioneSocialeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredDestVantaggio()) {
					ragioneSocialeItem.setAttribute("obbligatorio", true);
					ragioneSocialeItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Ragione sociale"));
				} else {
					ragioneSocialeItem.setAttribute("obbligatorio", false);
					ragioneSocialeItem.setTitle("Ragione sociale");					
				}
				boolean isPersonaGiuridica = tipoPersonaItem.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
				return isPersonaGiuridica;
			}
		});
		ragioneSocialeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean isPersonaGiuridica = tipoPersonaItem.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
				return ((DestVantaggioItem)getItem()).isRequiredDestVantaggio() && isPersonaGiuridica;
			}
		}));
		
		codFiscalePIVAItem = new TextItem("codFiscalePIVA", "C.F./P.I.");
		codFiscalePIVAItem.setWidth(150);
		codFiscalePIVAItem.setColSpan(1);
		codFiscalePIVAItem.setLength(16);
		codFiscalePIVAItem.setCharacterCasing(CharacterCasing.UPPER);
		codFiscalePIVAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isPersonaFisica = tipoPersonaItem.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
				if (isPersonaFisica) {
					if(((DestVantaggioItem)getItem()).isRequiredDestVantaggio()) {
						codFiscalePIVAItem.setAttribute("obbligatorio", true);
						codFiscalePIVAItem.setTitle(FrontendUtil.getRequiredFormItemTitle("C.F."));
					} else {
						codFiscalePIVAItem.setAttribute("obbligatorio", false);
						codFiscalePIVAItem.setTitle("C.F.");					
					}
				} else {
					if(((DestVantaggioItem)getItem()).isRequiredDestVantaggio()) {
						codFiscalePIVAItem.setAttribute("obbligatorio", true);
						codFiscalePIVAItem.setTitle(FrontendUtil.getRequiredFormItemTitle("C.F./P.I."));
					} else {
						codFiscalePIVAItem.setAttribute("obbligatorio", false);
						codFiscalePIVAItem.setTitle("C.F./P.I.");					
					}
				}				
				boolean isTipoPersonaValorizzato = tipoPersonaItem.getValueAsString() != null && !"".equals(tipoPersonaItem.getValueAsString());
				return isTipoPersonaValorizzato;
			}
		});
		codFiscalePIVAItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean isTipoPersonaValorizzato = tipoPersonaItem.getValueAsString() != null && !"".equals(tipoPersonaItem.getValueAsString());
				return ((DestVantaggioItem)getItem()).isRequiredDestVantaggio() && isTipoPersonaValorizzato;
			}
		}), new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(value == null || "".equals(value)) {
					return true;
				}
				boolean isPersonaFisica = tipoPersonaItem.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoPersonaItem.getValueAsString());
				if (isPersonaFisica) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					return regExp.test((String) value);
				} else {
					RegExp regExp = RegExp.compile(RegExpUtility.partitaIvaRegExp());
					return regExp.test((String) value);
				}
			}
		});	
		
		RegExpValidator importoPrecisionValidator = new RegExpValidator();
		importoPrecisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		importoPrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
		
		CustomValidator importoMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				// se l'importo è vuoto, ma non è obbligatorio, la validazione deve andare a buon fine
				if(value == null || "".equals(value)) {
					return true;
				}
				String pattern = "#,##0.00";
				double importo = 0;
				if(value != null && !"".equals(value)) {
					importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
				}
				return importo > 0;
			}
		};
		importoMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore di zero");
		
		importoItem = new ExtendedNumericItem("importo", "Importo (&euro;)"); 
		importoItem.setKeyPressFilter("[0-9.,]");
		importoItem.setColSpan(1);
		importoItem.setWidth(150);
		importoItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				importoItem.setValue(NumberFormatUtility.getFormattedValue((String) event.getValue()));
			}
		});		
		importoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(((DestVantaggioItem)getItem()).isRequiredDestVantaggio()) {
					importoItem.setAttribute("obbligatorio", true);
					importoItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Importo (&euro;)"));
				} else {
					importoItem.setAttribute("obbligatorio", false);
					importoItem.setTitle("Importo (&euro;)");					
				}
				importoItem.setValue(NumberFormatUtility.getFormattedValue(importoItem.getValueAsString()));			
				boolean isTipoPersonaValorizzato = tipoPersonaItem.getValueAsString() != null && !"".equals(tipoPersonaItem.getValueAsString());
				return isTipoPersonaValorizzato;
			}
		});
		importoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean isTipoPersonaValorizzato = tipoPersonaItem.getValueAsString() != null && !"".equals(tipoPersonaItem.getValueAsString());
				return ((DestVantaggioItem)getItem()).isRequiredDestVantaggio() && isTipoPersonaValorizzato;
			}
		}), importoPrecisionValidator, importoMaggioreDiZeroValidator);
				
		mDynamicForm.setFields(tipoPersonaItem, cognomeItem, nomeItem, ragioneSocialeItem, codFiscalePIVAItem, importoItem);	
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);				
	}
	
}
