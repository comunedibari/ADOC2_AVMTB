package it.eng.utility.ui.module.layout.client.common.items;

public class TitleItem extends com.smartgwt.client.widgets.form.fields.StaticTextItem {

    public TitleItem(String title) {
    	this(title, true);
    }
    
    public TitleItem(String title, boolean startRow) {
//    	this.setHeight(20);
		this.setWidth(120);
		this.setType("text");				
		setTitle(title);
		setColSpan(1);
		setTitleColSpan(1);	
		setStartRow(startRow);
		setWrapTitle(false);
		setWidth(1);
		setCanFocus(false);
		setTabIndex(-1);
    }

	
}
