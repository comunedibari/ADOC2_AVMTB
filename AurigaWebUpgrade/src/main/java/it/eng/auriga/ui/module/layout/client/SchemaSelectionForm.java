package it.eng.auriga.ui.module.layout.client;

import java.util.LinkedHashMap;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class SchemaSelectionForm extends DynamicForm {

	// private static Logger logger = java.util.logging.Logger.getLogger("SchemaSelectionForm");
	private SchemaSelectionWindow selectionWindow;
	private DynamicForm form;

	public SchemaSelectionForm(SchemaSelectionWindow pSchemaSelectionWindow) {

		form = this;
		selectionWindow = pSchemaSelectionWindow;

		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths("*", "*");
		setCellPadding(5);

		SelectItem lSelectSchema = new SelectItem();
		lSelectSchema.setName("schema");
		lSelectSchema.setRequired(true);
		lSelectSchema.setShowTitle(false);
		lSelectSchema.setColSpan(2);
		lSelectSchema.setAlign(Alignment.CENTER);
		lSelectSchema.setWidth(300);
		LinkedHashMap<String, String> lHashMap = new LinkedHashMap<String, String>();
		lSelectSchema.setValueMap(lHashMap);

		ButtonItem lButtonItem = new ButtonItem();
		lButtonItem.setTitle("Ok");
		lButtonItem.setIcon("ok.png");
		lButtonItem.setIconHeight(16);
		lButtonItem.setIconWidth(16);
		lButtonItem.setColSpan(2);
		lButtonItem.setWidth(100);
		lButtonItem.setTop(20);
		lButtonItem.setAlign(Alignment.CENTER);

		lButtonItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				selectionWindow.proceedAsUsual(form.getValueAsString("schema"));
			}
		});

		setFields(lSelectSchema, new RowSpacerItem(), lButtonItem);
		setAlign(Alignment.CENTER);
		setTop(50);
	}
}
