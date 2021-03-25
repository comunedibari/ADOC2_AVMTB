package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class CIGCanvas extends ReplicableCanvas {
	
	private TextItem codiceCIGItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public CIGCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		CustomValidator codiceCIGLengthValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value != null && !"".equals((String) value)) {
					String valore = (String) value;
					return valore.length() == 10;
				}
				return true;
			}
		};
		codiceCIGLengthValidator.setErrorMessage("Il codice CIG, se indicato, deve essere di 10 caratteri");
		
		codiceCIGItem = new TextItem("codiceCIG");
		codiceCIGItem.setShowTitle(false);
		codiceCIGItem.setWidth(500);
		codiceCIGItem.setColSpan(3);
		codiceCIGItem.setStartRow(true);
		codiceCIGItem.setLength(10);
		codiceCIGItem.setValidators(codiceCIGLengthValidator);
					
		mDynamicForm.setFields(codiceCIGItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
}