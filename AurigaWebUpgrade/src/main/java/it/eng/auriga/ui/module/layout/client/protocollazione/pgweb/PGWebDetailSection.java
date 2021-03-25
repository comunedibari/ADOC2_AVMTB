package it.eng.auriga.ui.module.layout.client.protocollazione.pgweb;

import it.eng.utility.ui.module.layout.client.common.DetailSection;

import com.smartgwt.client.widgets.form.DynamicForm;

public class PGWebDetailSection extends DetailSection {
	
	public PGWebDetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
		super(pTitle, pCanCollapse, pShowOpen, pIsRequired, forms);
	}

	public PGWebDetailSection(String pTitle, final Integer  pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
		super(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, forms);
	}
		
	public PGWebDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final String pBackgroundColor, final DynamicForm... forms) {
		super(pTitle,  pHeight, pCanCollapse, pShowOpen, pIsRequired, pBackgroundColor, forms);
	}
	
	@Override
	public boolean showFirstCanvasWhenEmptyAfterOpen() {
		
		return true;
	}
		
 }