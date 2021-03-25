package it.eng.auriga.ui.module.layout.client.protocollazione;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class AllegatoWindow extends ModalWindow {
	
	protected AllegatoWindow window;
	protected AllegatoDetail detail;	
	protected ToolStrip detailToolStrip;
	
	protected boolean canEdit;
	protected boolean isNew;
	
	public AllegatoWindow(AllegatiGridItem gridItem, String nomeEntita, final boolean isNew, Record record, boolean canEdit) {
		
		super(nomeEntita, false);
		
		setTitle("Aggiungi allegati");	
		if(record != null) {
			if(canEdit) {
				setTitle(I18NUtil.getMessages().editDetail_titlePrefix() + " allegato N° " + getTipoEstremiRecord(record));				
			} else {
				setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " allegato N° " + getTipoEstremiRecord(record));	
			}			
		}
		
		window = this;
		this.isNew = isNew;
		this.canEdit = canEdit;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(500);
		setWidth(900);
		setOverflow(Overflow.AUTO);    			
		
		detail = new AllegatoDetail(nomeEntita, gridItem, record, canEdit);		
		detail.setHeight100();
		detail.setWidth100();
		
		if(canEdit) {
			
			final DetailToolStripButton saveButton = new DetailToolStripButton(I18NUtil.getMessages().formChooser_ok(), "ok.png");
			saveButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					saveButton.focusAfterGroup();
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			    		
						@Override
						public void execute() {
							manageOnSaveButtonClick();					
						}
			    	});
				}
			});
			
			DetailToolStripButton annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
			annullaButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {				
					manageOnAnnullaButtonClick();
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
		
		setIcon("blank.png");		
	}
	
	public void manageOnSaveButtonClick() {
		focusAfterGroup();
		if(detail.validate()) {
			final Record lRecord = detail.getRecordToSave();
			RecordList listaAllegati = lRecord.getAttributeAsRecordList("listaAllegati");
			if(isNew) {
				addAllegati(listaAllegati);
			} else {
				updateAllegato(listaAllegati.get(0));
			}
			window.markForDestroy();						
		}
	}
	
	public void manageOnAnnullaButtonClick() {
		window.markForDestroy();
	}
	
	@Override
	public void manageOnCloseClick() {
		if (getCanEdit()) {
			SC.ask("Vuoi mantenere eventuali dati/file aggiunti o modificati?", new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					if(value) {
						manageOnSaveButtonClick();
					} else {
						manageOnAnnullaButtonClick();
					}				
				}
			});
		} else {
			manageOnAnnullaButtonClick();
		}
	}
	
	private boolean getCanEdit() {
		return canEdit;
	}

	public void addAllegati(RecordList listaRecord) {
		
	}
	
	public void updateAllegato(Record record) {
		
	}
	
	public String getTipoEstremiRecord(Record record) {	
		return record.getAttribute("numeroProgrAllegato");
	}
	
}
