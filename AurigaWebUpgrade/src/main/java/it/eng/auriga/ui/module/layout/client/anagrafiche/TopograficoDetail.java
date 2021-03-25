package it.eng.auriga.ui.module.layout.client.anagrafiche;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;

public class TopograficoDetail extends CustomDetail {
		
	protected TopograficoForm _form;
	
	public TopograficoDetail(String nomeEntita) {		
		
		super(nomeEntita);
		
		_form = new TopograficoForm();
		
		addSubForm(_form);
		
	}
	
	public void setCanEdit(boolean canEdit) {
		_form.setCanEdit(canEdit);	
	}
	
	public TopograficoForm getForm() {
		return _form;
	}


}
