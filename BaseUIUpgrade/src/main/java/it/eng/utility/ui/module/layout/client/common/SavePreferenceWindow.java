package it.eng.utility.ui.module.layout.client.common;

import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Window;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;

public class SavePreferenceWindow extends Window {

	private SavePreferenceForm form;
	private SavePreferenceAction savePreferenceAction;

	public SavePreferenceWindow(String title, GWTRestDataSource datasource, boolean defaultValue, SavePreferenceAction action) {
		this.savePreferenceAction = action;
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setTitle(title);
		setWidth(350);
		setHeight(100);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		form = new SavePreferenceForm(this, datasource, defaultValue);
		addItem(form);
		setShowTitle(true);
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
	}
	
	public void savePreferenceAction(String value) {
		savePreferenceAction.execute(value);
		hide();
	}

	public void manageOnCloseClick() {
		hide();
	}

	public SavePreferenceForm getForm() {
		return form;
	}

	public void setForm(SavePreferenceForm form) {
		this.form = form;
	}

}
