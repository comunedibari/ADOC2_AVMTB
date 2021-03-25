package it.eng.areas.hybrid.module.cryptoLight;

import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.cryptoLight.resources.Resources;
import it.eng.areas.hybrid.module.util.HybridActivator;

/**
 * ATTENZIONE OSGi non riesce ad accedere alla classe del provider!
 * Questa libreria va caricata come jar NON OSGi
 * @author GioBo
 *
 */
public class Activator extends HybridActivator {
	
	protected void addModuleManager(IClientModuleManager moduleManager) {
		try {
			Resources.start(getBundleName());
			moduleManager.registerModule(new SignerMultiClientModule(getBundleName(), getBundleVersion()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
