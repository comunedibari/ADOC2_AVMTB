package it.eng.utility.ui.module.core.client.util;

import it.eng.utility.ui.module.core.client.EscapeHtmlClient;
import it.eng.utility.ui.module.core.client.SJCLClient;
import it.eng.utility.ui.module.core.client.ScriptCleanerClient;

public interface ClientFactory {
	
	SJCLClient getSJCLClient();
	ScriptCleanerClient getScriptCleanerClient();
	EscapeHtmlClient getEcapeHtmlClient();

}
