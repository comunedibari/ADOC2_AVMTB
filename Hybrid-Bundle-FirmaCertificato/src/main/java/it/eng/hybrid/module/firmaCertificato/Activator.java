package it.eng.hybrid.module.firmaCertificato;

import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.util.HybridActivator;
import it.eng.hybrid.module.firmaCertificato.resources.Resources;


/**
 *
 */
public class Activator extends HybridActivator {
	
	protected void addModuleManager(IClientModuleManager moduleManager) {
		try {
			Resources.start(getBundleName());
			moduleManager.registerModule(new FirmaCertificatoModule(getBundleName(), getBundleVersion()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
