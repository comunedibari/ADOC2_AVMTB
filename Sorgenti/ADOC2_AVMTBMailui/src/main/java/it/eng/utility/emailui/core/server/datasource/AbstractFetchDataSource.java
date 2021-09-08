package it.eng.utility.emailui.core.server.datasource;

import it.eng.utility.emailui.core.server.bean.AdvancedCriteria;
import it.eng.utility.emailui.core.server.bean.OrderByBean;
import it.eng.utility.emailui.core.server.bean.PaginatorBean;
import it.eng.utility.emailui.core.server.service.ErrorBean;

import java.util.List;

/**
 * Classe astratta che definisce i metodi da implementare per un datasource da usabile da un DynamicForm
 * @author michele
 *
 * @param <T>
 */
public abstract class AbstractFetchDataSource<T> extends AbstractDataSource<T,T> {

	public T update(T bean,T oldvalue) throws Exception{
		return null;
	}

	public java.util.Map<String,ErrorBean> validate(T bean) throws Exception{
		return null;
	}
	
	@Override
	public T add(T bean) throws Exception {
		return null;
	}

	@Override
	public T remove(T bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
		
	@Override
	public abstract PaginatorBean<T> fetch(AdvancedCriteria criteria,Integer startRow,Integer endRow,List<OrderByBean> orderby) throws Exception;
	
}