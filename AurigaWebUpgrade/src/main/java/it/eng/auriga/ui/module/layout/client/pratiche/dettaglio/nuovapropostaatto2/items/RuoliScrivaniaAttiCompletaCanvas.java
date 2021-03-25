package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

public class RuoliScrivaniaAttiCompletaCanvas extends ReplicableCanvas {
	
	private HiddenItem codUoHiddenItem;
	private ExtendedTextItem codUoItem;
	private FilteredSelectItemWithDisplay idSvItem;
	private HiddenItem idSvFromLoadDettHiddenItem;
	private HiddenItem descrizioneHiddenItem;
	private CheckboxItem flgFirmatarioItem;
	private CheckboxItem flgRiacqVistoInRitornoIterItem;
	private TextAreaItem motiviItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public RuoliScrivaniaAttiCompletaCanvas(ReplicableItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		List<FormItem> listaItems = new ArrayList<FormItem>();
		
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo() && isAltriParamWithNriLivelliUo()) {
			codUoItem = new ExtendedTextItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
			codUoItem.setWidth(80);
			codUoItem.setColSpan(1);
			codUoItem.addChangedBlurHandler(new ChangedHandler() {
	
				@Override
				public void onChanged(ChangedEvent event) {
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), (String) null);
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), (String) null);
//					mDynamicForm.clearFieldErrors(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), true);
					final String value = codUoItem.getValueAsString();
					if (value != null && !"".equals(value)) {
						idSvItem.fetchData(new DSCallback() {
	
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
//								if (data.getLength() == 0) {
//									mDynamicForm.setFieldErrors(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "Valore non valido");
//								}
								if (data.getLength() == 1) {
									if (value.equals(data.get(0).getAttribute("codUo"))) {
										mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), data.get(0).getAttribute("idSv"));
										mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), data.get(0).getAttribute("descrizione"));
									}
								}
								/*
								if (data.getLength() > 0) {
									for (int i = 0; i < data.getLength(); i++) {
										if (value.equals(data.get(i).getAttribute("codUo"))) {
											mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), data.get(i).getAttribute("idSv"));
											mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), data.get(i).getAttribute("descrizione"));
											break;
										}
									}
								}*/
								((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();								
							}
						});
					} else {
						idSvItem.fetchData();
						((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
					}
				}
			});			
			listaItems.add(codUoItem);		
		} else {
			codUoHiddenItem = new HiddenItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName());
			listaItems.add(codUoHiddenItem);
		}
		
		SelectGWTRestDataSource ruoliScrivaniaAttiDS = new SelectGWTRestDataSource("LoadComboRuoliScrivaniaAttiDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdUdAttoDaAnn() != null) {
			ruoliScrivaniaAttiDS.addParam("idUdAttoDaAnn", ((RuoliScrivaniaAttiCompletaItem)getItem()).getIdUdAttoDaAnn());
		}
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo() != null) {
			ruoliScrivaniaAttiDS.addParam("altriParamLoadCombo", ((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo());
		}		
		idSvItem = new FilteredSelectItemWithDisplay(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), ruoliScrivaniaAttiDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid idSvPickListProperties = super.builPickListProperties();	
				if(!isCodUoFieldWithFilter() && !isDescrizioneFieldWithFilter()) {
					idSvPickListProperties.setShowFilterEditor(false); 
				}
				idSvPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource ruoliScrivaniaAttiDS = (GWTRestDataSource) idSvItem.getOptionDataSource();		
						boolean isRequiredFilterCodUo = AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo() && isAltriParamWithNriLivelliUo();
						if(isRequiredFilterCodUo) {
							ruoliScrivaniaAttiDS.addParam("codUo", codUoItem.getValueAsString());
						}
						ruoliScrivaniaAttiDS.addParam("idSv", (String) idSvFromLoadDettHiddenItem.getValue());
						ruoliScrivaniaAttiDS.addParam("uoProponente", ((RuoliScrivaniaAttiCompletaItem)getItem()).getUoProponenteCorrente());
						Boolean flgAttoMeroIndirizzo = ((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgAttoMeroIndirizzo();
						ruoliScrivaniaAttiDS.addParam("flgAttoMeroIndirizzo", flgAttoMeroIndirizzo != null && flgAttoMeroIndirizzo ? "1" : "");					
						idSvItem.setOptionDataSource(ruoliScrivaniaAttiDS);
						idSvItem.invalidateDisplayValueCache();
					}
				});
				return idSvPickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				mDynamicForm.clearErrors(true);				
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), record.getAttributeAsString("codUo"));
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), record.getAttributeAsString("descrizione"));
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.clearErrors(true);				
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), "");
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "");
				mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), "");
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.clearErrors(true);				
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), "");
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "");
					mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), "");
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
						}
					});
				}
			}
		};
		idSvItem.setShowTitle(false);
		idSvItem.setWidth(669); // setto la larghezza a 650 + 19 o rimane disallineato rispetto alle motivazioni sotto
		idSvItem.setValueField("idSv");
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(80);		
		codUoField.setCanFilter(isCodUoFieldWithFilter());				
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		descrizioneField.setWidth("*");
		descrizioneField.setCanFilter(isDescrizioneFieldWithFilter());		
		idSvItem.setPickListFields(codUoField, descrizioneField);		
		boolean isRequiredFilterCodUo = AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo() && isAltriParamWithNriLivelliUo();
		boolean isRequiredFilterDescrizione = !isRequiredFilterCodUo && AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo() && isAltriParamWithStrInDes();						
		if (isRequiredFilterCodUo) {
			idSvItem.setEmptyPickListMessage("Nessun record trovato o filtri incompleti o poco restrittivi: digitare il cod. rapido");
		} else if (isRequiredFilterDescrizione) {
			idSvItem.setEmptyPickListMessage("Nessun record trovato o filtri incompleti o poco restrittivi: filtrare per descrizione (almeno 3 caratteri)");
		} else {
			idSvItem.setEmptyPickListMessage("Nessun record trovato");		
		}
		idSvItem.setFilterLocally(true);
		idSvItem.setAllowEmptyValue(false);
		idSvItem.setAutoFetchData(false);
		idSvItem.setAlwaysFetchMissingValues(true);
		idSvItem.setFetchMissingValues(true);
		idSvItem.setClearable(true);
		idSvItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				return !notReplicable || obbligatorio;
			}
		}));
		listaItems.add(idSvItem);
				
		idSvFromLoadDettHiddenItem = new HiddenItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFromLoadDettFieldName());
		listaItems.add(idSvFromLoadDettHiddenItem);
		
		descrizioneHiddenItem = new HiddenItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName());
		listaItems.add(descrizioneHiddenItem);
		
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgFirmatarioFieldName() != null && !"".equals(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgFirmatarioFieldName())) {
			flgFirmatarioItem = new CheckboxItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgFirmatarioFieldName(), I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgFirmatario_title());
			flgFirmatarioItem.setDefaultValue(false);
			flgFirmatarioItem.setColSpan(1);
			flgFirmatarioItem.setWidth("*");
			flgFirmatarioItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return ((RuoliScrivaniaAttiCompletaItem)getItem()).showFlgFirmatario();
				}
			});
			listaItems.add(flgFirmatarioItem);			
		}
		
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgRiacqVistoInRitornoIterFieldName() != null && !"".equals(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgRiacqVistoInRitornoIterFieldName())) {
			flgRiacqVistoInRitornoIterItem = new CheckboxItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getFlgRiacqVistoInRitornoIterFieldName(), "visto da ri-acquisire in caso di ritorno indietro nell'iter");
			flgRiacqVistoInRitornoIterItem.setDefaultValue(false);
			flgRiacqVistoInRitornoIterItem.setColSpan(1);
			flgRiacqVistoInRitornoIterItem.setWidth("*");
			flgRiacqVistoInRitornoIterItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return ((RuoliScrivaniaAttiCompletaItem)getItem()).showFlgRiacqVistoInRitornoIter();
				}
			});
			listaItems.add(flgRiacqVistoInRitornoIterItem);			
		}
		
		if(((RuoliScrivaniaAttiCompletaItem)getItem()).getMotiviFieldName() != null && !"".equals(((RuoliScrivaniaAttiCompletaItem)getItem()).getMotiviFieldName())) {			
			motiviItem = new TextAreaItem(((RuoliScrivaniaAttiCompletaItem)getItem()).getMotiviFieldName());
			motiviItem.setShowTitle(false);
			motiviItem.setHint("Motivo/i");
			motiviItem.setShowHintInField(true);
			motiviItem.setColSpan(20);
			motiviItem.setStartRow(true);
			motiviItem.setLength(4000);
			motiviItem.setHeight(40);
			motiviItem.setWidth(650);
			if(((RuoliScrivaniaAttiCompletaItem)getItem()).isRequiredMotivi()) {
				motiviItem.setRequired(true);
			}
			motiviItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return ((RuoliScrivaniaAttiCompletaItem)getItem()).showMotivi();
				}
			});
			listaItems.add(motiviItem);	
		}
		
		listaItems.addAll(((RuoliScrivaniaAttiCompletaItem)getItem()).getCustomItems());
		
		mDynamicForm.setFields(listaItems.toArray(new FormItem[listaItems.size()]));
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	public boolean isCodUoFieldWithFilter() {
		if(!isAltriParamWithNriLivelliUo()) {
			return false;		
		} else if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && !isAltriParamWithDesRuoloOrIdUo()) {
			return false;			
		}
		return true;
		
	}
	
	public boolean isDescrizioneFieldWithFilter() {
		return isAltriParamWithStrInDes();
	}
	
	public boolean isAltriParamWithNriLivelliUo() {
		String altriParamLoadCombo = ((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo();
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("NRI_LIVELLI_UO|*|") != -1;
	}
	
	public boolean isAltriParamWithStrInDes() {
		String altriParamLoadCombo = ((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo();
		return altriParamLoadCombo != null && altriParamLoadCombo.indexOf("STR_IN_DES|*|") != -1;
	}
	
	public boolean isAltriParamWithDesRuoloOrIdUo() {
		String altriParamLoadCombo = ((RuoliScrivaniaAttiCompletaItem)getItem()).getAltriParamLoadCombo();
		return altriParamLoadCombo != null && (altriParamLoadCombo.indexOf("DES_RUOLO|*|") != -1 || altriParamLoadCombo.indexOf("ID_UO|*|") != -1);
	}
	
	public void resetAfterChangedParams() {
		final String value = idSvItem.getValueAsString();
		idSvItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				RecordList data = response.getDataAsRecordList();
				if(((RuoliScrivaniaAttiCompletaItem)getItem()).selectUniqueValueAfterChangedParams() && data.getLength() == 1) {
					if(value == null || "".equals(value) || !value.equals(data.get(0).getAttributeAsString("idSv"))) {	
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), data.get(0).getAttributeAsString("idSv"));
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), data.get(0).getAttributeAsString("codUo"));
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), data.get(0).getAttributeAsString("descrizione"));
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
							@Override
							public void execute() {
								((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
							}
						});
					}
				} else if(value != null && !"".equals(value)) {					
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String idSv = data.get(i).getAttribute("idSv");
							if (value.equals(idSv)) {
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), "");
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), "");
						mDynamicForm.setValue(((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName(), "");
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
							@Override
							public void execute() {
								((RuoliScrivaniaAttiCompletaItem)getItem()).manageChangedScrivaniaSelezionata();
							}
						});
					}
				}
			}
		});
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, idSvItem, ((RuoliScrivaniaAttiCompletaItem)getItem()).getIdSvFieldName(), new String[]{((RuoliScrivaniaAttiCompletaItem)getItem()).getCodUoFieldName(), ((RuoliScrivaniaAttiCompletaItem)getItem()).getDescrizioneFieldName()}, "idSv");
		super.editRecord(record);					
	}	
	
}
