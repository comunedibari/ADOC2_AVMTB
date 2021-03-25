package it.eng.auriga.ui.module.layout.client.oggettario;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupOggettarioPopup extends ModalWindow {

	private LookupOggettarioPopup _window;
	
	private OggettarioLayout portletLayout;

	protected ToolStrip detailToolStrip;
	
	public LookupOggettarioPopup(String flgTipoProv) {
		
		super("oggettario", true);
		
		setTitle(I18NUtil.getMessages().oggettario_lookupOggettarioPopup_title());  	
		
		_window = this;
			
		portletLayout = new OggettarioLayout(flgTipoProv, true) {
			@Override
			public void lookupBack(Record selectedRecord) {
				
				manageLookupBack(selectedRecord);
				_window.markForDestroy();	
			}
			
			@Override
			public void multiLookupBack(Record record) {
				
				manageMultiLookupBack(record);
			}
			
			@Override
			public void multiLookupUndo(Record record) {
								
				manageMultiLookupUndo(record);
			}
			
			@Override
			public void showDetail() {
				
				super.showDetail();
				if(fullScreenDetail) {	
					String title = "";
					if(mode != null) {
						if(mode.equals("new")) {				
							title = getNewDetailTitle();
						} else if(mode.equals("edit")) {
							title = getEditDetailTitle();		
						} else if(mode.equals("view")) {
							title = getViewDetailTitle();
						}
					}
					_window.setTitle(title);											
				}
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle(I18NUtil.getMessages().oggettario_lookupOggettarioPopup_title()); 
				} 	
			}						
		};
		
		portletLayout.setLookup(true);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			addBackButton();
			portletLayout.setHeight(450);
			portletLayout.addMember(detailToolStrip);
		} 
		
		setBody(portletLayout);
       
        setIcon("menu/oggettario.png");
                
	}

	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);
	
	private void addBackButton() {
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right
		
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_window.close();
			}
		});
		
		detailToolStrip.addButton(closeModalWindow);
	}
	
}
