package it.eng.utility.ui.module.layout.client.common.items;

import java.util.Date;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

/**
 * @author Cristiano Daniele.
 * Componente FilterDateItem di tipo DateItem da utilizzare solo nei filtri di ricerca delle portlet layout.
 * Può essere utilizzato sia con l'ausilio del componente Gwt che manualmente.
 */

public class FilterDateItem extends com.smartgwt.client.widgets.form.fields.DateItem {

	public FilterDateItem() {
		// this.setHeight(20);
		this.setWidth(120);
		this.setAttribute("allowRelativeDates", false);
		this.setUseTextField(true);
		this.setEnforceDate(true);
		this.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		this.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		final TextItem textFieldProperties = new TextItem();
		setDefaultDataRange();
		textFieldProperties.setChangeOnKeypress(true);
		textFieldProperties.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {

				String value = event.getValue() != null ? (String) event.getValue() : null;
				if (value != null && !"".equals(value)) {
					DateUtil.setDateInputFormat("DMY");
					Date date = DateUtil.parseInput(value);
					if (date != null) {
						event.getForm().setValue(getName(), (String) value);
						textFieldProperties.setValue((String) value);
					} else {
						event.getForm().setValue(getName(), (String) null);
						textFieldProperties.setValue((String) null);
					}
				} else {
					textFieldProperties.setValue((String) null);
					event.getForm().setValue(getName(), (String) null);
				}
			}
		});
		textFieldProperties.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		setTextFieldProperties(textFieldProperties);
	}

	public FilterDateItem(String name) {
		this();
		setName(name);
	}

	public FilterDateItem(String name, String title) {
		this();
		setName(name);
		setTitle(title);
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
    	setCanFocus(canEdit ? true : false);
	}

    protected void setDefaultDataRange() {
    	GenericConfigBean config = Layout.getGenericConfig();
    	if(config!=null) {
    		if(config.getMinAnno()!=null && !"".equals(config.getMinAnno())) {
    			int minAnno = new Integer(config.getMinAnno()) - 1900;
    			Date startDate = new Date(minAnno, 11, 31);
    			this.setStartDate(startDate);
    		}
    		if(config.getMaxAnno()!=null && !"".equals(config.getMaxAnno())) {
    			int maxAnno = new Integer(config.getMaxAnno()) - 1900;
    			Date endDate = new Date(maxAnno, 11, 31);
    			this.setEndDate(endDate);
    		}
    	}

    }
    
}
