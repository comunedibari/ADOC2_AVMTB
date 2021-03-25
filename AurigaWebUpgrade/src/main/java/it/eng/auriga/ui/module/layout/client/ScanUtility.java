package it.eng.auriga.ui.module.layout.client;

import it.eng.auriga.ui.module.layout.client.protocollazione.AcquisisciDaScannerHybridWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.AcquisisciDaScannerWindow;
import it.eng.utility.ui.module.core.client.BrowserUtility;

public class ScanUtility {

	public interface ScanCallback {

		void execute(String filePdf, final String uriPdf, String fileTif, String uriTif, String record);

	}

	public static void openScan(ScanCallback callback) {

		String modalitaScansione = AurigaLayout.getParametroDB("MODALITA_SCANSIONE");
		if (modalitaScansione == null || "".equalsIgnoreCase(modalitaScansione) || "APPLET".equalsIgnoreCase(modalitaScansione)
				|| BrowserUtility.detectIfIEBrowser()) {
			AcquisisciDaScannerWindow lAcquisisciDaScannerWindow = new AcquisisciDaScannerWindow(callback);
			lAcquisisciDaScannerWindow.show();
		} else {
			new AcquisisciDaScannerHybridWindow(callback);
			// Non serve fare lo show come nelle applet
		}
	}

}
