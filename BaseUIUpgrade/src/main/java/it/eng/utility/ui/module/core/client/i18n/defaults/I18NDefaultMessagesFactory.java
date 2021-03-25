package it.eng.utility.ui.module.core.client.i18n.defaults;

import it.eng.utility.ui.module.core.client.i18n.I18NMessagesFactory;

import com.google.gwt.core.client.GWT;

public class I18NDefaultMessagesFactory implements I18NMessagesFactory {

	 private static final Messages i18n = (Messages) GWT.create(Messages.class);
	
	@Override
	public Messages getMessages() {
		return i18n;
	}

}
