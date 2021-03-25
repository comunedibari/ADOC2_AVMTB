package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ListaDatiContabiliDinamicaWindow extends ModalWindow {
	
	protected ListaDatiContabiliDinamicaWindow window;
	protected ListaDatiContabiliDinamicaDetail detail;	
	protected ToolStrip detailToolStrip;
	
	public ListaDatiContabiliDinamicaWindow(ListaDatiContabiliDinamicaItem gridItem, String nomeEntita, Record record, boolean canEdit) {
		
		super(nomeEntita, false);
		
		final boolean isNew = (record == null);
		
		setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_title());	
		if(record != null) {
			if(canEdit) {
				setTitle(I18NUtil.getMessages().editDetail_titlePrefix() + " " + getTipoEstremiRecord(record));				
			} else {
				setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " " + getTipoEstremiRecord(record));	
			}			
		}
		
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(500);
		setWidth(900);
		setOverflow(Overflow.AUTO);    			
		
		detail = new ListaDatiContabiliDinamicaDetail(nomeEntita, gridItem);		
		detail.setHeight100();
		detail.setWidth100();
		
		if(canEdit) {
			
			DetailToolStripButton saveButton = new DetailToolStripButton(I18NUtil.getMessages().formChooser_ok(), "ok.png");
			saveButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					focusAfterGroup();
					if(detail.validate()) {
						final Record lRecord = detail.getRecordToSave();
						saveData(lRecord);		
						if(isNew) {
							AfterSaveDatiSpesaDialogBox afterSaveDatiSpesaDialogBox = new AfterSaveDatiSpesaDialogBox("Vuoi inserire un nuovo dettaglio di spesa?") {
	
								@Override
								public void onClickButton(String scelta) {
									if(scelta != null && scelta.equals("SI")) {
										detail.editNewRecord();
									} else if(scelta != null && scelta.equals("CC")) {
										// per la copia lascio i dati così come sono invariati a maschera, perchè se richiamo l'editRecord mi perdo il valore di alcuni campi									
//										detail.editRecord(new Record(detail.getValuesManager().getValues()));
									} else {
										window.manageOnCloseClick();
									}
								}
							};	
							afterSaveDatiSpesaDialogBox.show();
						} else {
							window.manageOnCloseClick();
						}
					}
				}
			});
			
			DetailToolStripButton annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
			annullaButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {				
					window.manageOnCloseClick();
				}
			});
			
			ToolStrip detailToolStrip = new ToolStrip();
			detailToolStrip.setWidth100();
			detailToolStrip.setHeight(30);
			detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
			detailToolStrip.addFill(); // push all buttons to the right		
			detailToolStrip.addButton(saveButton);
			detailToolStrip.addButton(annullaButton);
			
			VLayout detailLayout = new VLayout();  
			detailLayout.setOverflow(Overflow.HIDDEN);		
			detailLayout.setHeight100();
			detailLayout.setWidth100();		
			detailLayout.setMembers(detail, detailToolStrip);	
			
			setBody(detailLayout);
		} else {
			setBody(detail);
		}
		
		if(record != null) {
			detail.editRecord(record);
		} else {
			detail.editNewRecord();
		}
		
		detail.setCanEdit(canEdit);
		
		setIcon("blank.png");		
	}
	
	public void saveData(Record record) {
		
	}
	
	public String getTipoEstremiRecord(Record record) {
		String estremi = "";
		if(record.getAttribute("flgEntrataUscita") != null && !"".equals(record.getAttribute("flgEntrataUscita"))) {
			estremi += InvioDatiSpesaDetail.buildFlgEntrataUscitaValueMap().get(record.getAttribute("flgEntrataUscita")) + " ";
		}
		if(record.getAttribute("annoEsercizio") != null && !"".equals(record.getAttribute("annoEsercizio"))) {
			estremi += " " + record.getAttribute("annoEsercizio") + " ";
		}
		if(record.getAttribute("oggetto") != null && !"".equals(record.getAttribute("oggetto"))) {			
			if (record.getAttribute("oggetto").length() > 30){
				estremi += " - " + record.getAttribute("oggetto").substring(0, 30) + "...";
			} else {
				estremi += " - " + record.getAttribute("oggetto");		
			}
		}		
		return estremi;
	}
	
}
