package it.eng.auriga.ui.module.layout.client.applet;

public class AppletOpener {
	
	public static native void open(String url, String features) /*-{
		$wnd.appletWindow = $wnd.showModalDialog(url, window.$wnd, features);
	}-*/;
	
	public static native void close() /*-{
	$wnd.appletWindow.close();
}-*/;
	
	public static native void reinitAppletFirma(String nuoviParametri)/*-{
		$wnd.appletFirma.reinitToSign(nuoviParametri);
	}-*/;
}
