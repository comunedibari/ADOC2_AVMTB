package it.eng.hybrid.module.selectCertificati;

import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.util.HybridActivator;
import it.eng.hybrid.module.selectCertificati.resources.Resources;


/**
 *
 */
public class Activator extends HybridActivator {
	
	protected void addModuleManager(IClientModuleManager moduleManager) {
		try {
			Resources.start(getBundleName());
			moduleManager.registerModule(new SelectCertificatiModule(getBundleName(), getBundleVersion()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
