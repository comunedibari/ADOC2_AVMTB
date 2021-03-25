package it.eng.auriga.ui.module.layout.client;

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaAppletWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaHybridWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaPdf;
import it.eng.utility.ui.module.core.client.BrowserUtility;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;

public class StampaEtichettaUtility {

	public interface StampaEtichettaCallback {

		void execute();

	}

	public static void stampaEtichetta(String title, String appletJarName, String nomeStampante, Record[] pRecordsEtichette, String numeroCopie,
			StampaEtichettaCallback callback) {
		if (isStampaEtichetteHybridDisattiva()) {
			stampaEtichettaInPdf(pRecordsEtichette, numeroCopie, callback);
		} else if (nomeStampante != null && !"".equals(nomeStampante)) {
			if (numeroCopie != null && !"".equals(numeroCopie) && !"0".equals(numeroCopie)) {
				String modalitaFirma = AurigaLayout.getParametroDB("MODALITA_STAMPA_ETICHETTA");
				if (modalitaFirma == null || "".equalsIgnoreCase(modalitaFirma) || "APPLET".equalsIgnoreCase(modalitaFirma)
						|| BrowserUtility.detectIfIEBrowser()) {
					StampaEtichettaAppletWindow lStampaEtichettaAppletWindow = new StampaEtichettaAppletWindow(title, appletJarName, nomeStampante,
							pRecordsEtichette, numeroCopie, callback);
					lStampaEtichettaAppletWindow.show();
				} else {
					new StampaEtichettaHybridWindow(title, appletJarName, nomeStampante, pRecordsEtichette, numeroCopie, callback);
					// Non serve fare lo show come nelle applet
				}
			} else if (callback != null) {
				callback.execute();
			}
		} else {
			AurigaLayout
					.addMessage(new MessageBean("Selezionare una stampante o configurare una stampante di default per le etichette", "", MessageType.ERROR));
		}
	}
	
	public static void stampaEtichettaInPdf (Record[] pRecordsEtichette, String numeroCopie, StampaEtichettaCallback callback) {
		if (numeroCopie != null && !"".equals(numeroCopie) && !"0".equals(numeroCopie)) {
			StampaEtichettaPdf.stampa(pRecordsEtichette, numeroCopie);			
		} else if (callback != null) {
			callback.execute();
		}
	}
	
	private static boolean isStampaEtichetteHybridDisattiva () {
		return AurigaLayout.isBottoniStampaEtichetteHybridDisattivi();
	}
}
