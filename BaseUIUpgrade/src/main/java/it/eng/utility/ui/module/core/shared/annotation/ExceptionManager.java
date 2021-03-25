package it.eng.utility.ui.module.core.shared.annotation;

import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.service.ResponseBean;

public abstract class ExceptionManager {

	public abstract void manageException(Throwable e, AbstractDataSource<?, ?> datasource, ResponseBean response);

}
