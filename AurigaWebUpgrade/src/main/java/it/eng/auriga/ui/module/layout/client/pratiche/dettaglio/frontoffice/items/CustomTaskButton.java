package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items;

import com.smartgwt.client.data.Record;

public class CustomTaskButton extends FrontendButton {

	public CustomTaskButton(String title) {
		super(title);
	}
	
	public CustomTaskButton(String title, String icon) {
		super(title, icon);
	}
	
	public boolean isToShow(Record recordEvento) {
		return true;	
	}
	
}
