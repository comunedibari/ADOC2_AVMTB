package it.eng.auriga.ui.module.layout.client;

import it.eng.utility.ui.module.core.client.EscapeHtmlClient;
import it.eng.utility.ui.module.core.client.SJCLClient;
import it.eng.utility.ui.module.core.client.ScriptCleanerClient;
import it.eng.utility.ui.module.core.client.util.ClientFactory;

public class ClientFactoryImpl implements ClientFactory {
	
	private final SJCLClient sjclClient = new SJCLClient();
	private final ScriptCleanerClient scriptCleanerClient = new ScriptCleanerClient();
	private final EscapeHtmlClient escapeHtmlClient = new EscapeHtmlClient();

	@Override
	public SJCLClient getSJCLClient() {
		return sjclClient;
	}
	
	@Override
	public ScriptCleanerClient getScriptCleanerClient() {
		return scriptCleanerClient;
	}
	
	@Override
	public EscapeHtmlClient getEcapeHtmlClient() {
		return escapeHtmlClient;
	}

}
