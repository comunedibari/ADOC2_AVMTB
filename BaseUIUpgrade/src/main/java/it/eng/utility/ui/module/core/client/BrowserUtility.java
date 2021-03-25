package it.eng.utility.ui.module.core.client;

import com.google.gwt.user.client.Window.Navigator;

public class BrowserUtility {

	public static boolean detectIfIEBrowser() {
		String userAgent = Navigator.getUserAgent();
		if (userAgent != null) {
			return userAgent.toUpperCase().contains("MSIE") || userAgent.toUpperCase().contains("TRIDENT");
		} else {
			return false;
		}
	}
	
	public static boolean detectIfFireFox60Browser() {
		String userAgent = Navigator.getUserAgent();
		if (userAgent != null) {
			return userAgent.toUpperCase().contains("MOZILLA") && userAgent.toUpperCase().contains("FIREFOX/6");
		} else {
			return false;
		}
	}

}
