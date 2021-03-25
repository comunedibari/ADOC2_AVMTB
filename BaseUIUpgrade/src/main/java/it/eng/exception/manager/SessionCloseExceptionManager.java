package it.eng.exception.manager;

import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.service.ResponseBean;
import it.eng.utility.ui.module.core.shared.annotation.ExceptionManager;
import it.eng.utility.ui.module.core.shared.message.MessageType;

public class SessionCloseExceptionManager extends ExceptionManager {
	@Override
	public void manageException(Throwable e,
			AbstractDataSource<?, ?> datasource, ResponseBean response) {
		datasource.addMessage("Ricerca interrotta dall'utente", "Ricerca interrotta dall'utente", MessageType.WARNING);

	}
}
