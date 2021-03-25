package it.eng.auriga.ui.module.layout.client.attributiDinamici;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;

public class ListaAttributoDinamicoListaDetail extends CustomDetail {
	
	protected ListaAttributoDinamicoListaItem gridItem;
	
	protected DynamicForm mDynamicForm;
	protected AttributoListaItem listaAttributoDinamicoListaItem;
	
	public ListaAttributoDinamicoListaDetail(String nomeEntita, final ListaAttributoDinamicoListaItem gridItem) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
		
		listaAttributoDinamicoListaItem = new AttributoListaItem(null, gridItem.getDatiDettLista()) {
			
			@Override
			public boolean showCanvasSection() {
				return false;
			}
		};
		listaAttributoDinamicoListaItem.setName("listaAttributoDinamicoLista");
		listaAttributoDinamicoListaItem.setShowTitle(false);
		listaAttributoDinamicoListaItem.setNotReplicable(true);
		listaAttributoDinamicoListaItem.setShowDuplicaRigaButton(false);
		listaAttributoDinamicoListaItem.setAttribute("obbligatorio", true);
		
		mDynamicForm.setFields(listaAttributoDinamicoListaItem);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}
		
	@Override
	public void editRecord(Record record) {
		RecordList lRecordList = new RecordList();
		lRecordList.add(record);
		listaAttributoDinamicoListaItem.drawAndSetValue(lRecordList);		
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = mDynamicForm.getValueAsRecordList("listaAttributoDinamicoLista").get(0);
		return lRecordToSave;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		listaAttributoDinamicoListaItem.setCanEdit(canEdit);
	}
	
	@Override
	public boolean customValidate() {
		listaAttributoDinamicoListaItem.removeEmptyCanvas();
		return super.customValidate();
	}
	
}