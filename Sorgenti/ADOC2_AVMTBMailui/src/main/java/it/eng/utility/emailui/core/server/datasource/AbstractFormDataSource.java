package it.eng.utility.emailui.core.server.datasource;

import java.util.List;

import it.eng.utility.emailui.core.server.bean.AdvancedCriteria;
import it.eng.utility.emailui.core.server.bean.OrderByBean;
import it.eng.utility.emailui.core.server.bean.PaginatorBean;
import it.eng.utility.emailui.core.server.service.ErrorBean;

/**
 * Classe astratta che definisce i metodi da implementare per un datasource da usabile da un DynamicForm
 * 
 * @author michele
 *
 * @param <T>
 */
public abstract class AbstractFormDataSource<T> extends AbstractDataSource<T, T> {

	public abstract T update(T bean, T oldvalue) throws Exception;

	public abstract java.util.Map<String, ErrorBean> validate(T bean) throws Exception;

	@Override
	public T add(T bean) throws Exception {
		return update(bean, bean);
	}

	@Override
	public T remove(T bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginatorBean<T> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println(criteria);
		return null;
	}
}