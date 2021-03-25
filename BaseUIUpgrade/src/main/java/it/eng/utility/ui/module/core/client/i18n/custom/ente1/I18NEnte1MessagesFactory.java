package it.eng.utility.ui.module.core.client.i18n.custom.ente1;

import it.eng.utility.ui.module.core.client.i18n.I18NMessagesFactory;
import it.eng.utility.ui.module.core.client.i18n.defaults.Messages;

import com.google.gwt.core.client.GWT;

public class I18NEnte1MessagesFactory implements I18NMessagesFactory {

	 private static final Ente1_Messages i18n = (Ente1_Messages) GWT.create(Ente1_Messages.class);
	
	@Override
	public Messages getMessages() {
		return i18n;
	}

}
