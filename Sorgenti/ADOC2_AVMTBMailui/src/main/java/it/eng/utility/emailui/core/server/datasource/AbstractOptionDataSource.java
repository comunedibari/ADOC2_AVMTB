package it.eng.utility.emailui.core.server.datasource;

import it.eng.utility.emailui.core.server.bean.AdvancedCriteria;
import it.eng.utility.emailui.core.server.bean.OrderByBean;
import it.eng.utility.emailui.core.server.bean.PaginatorBean;
import it.eng.utility.emailui.core.server.service.ErrorBean;

import java.util.List;

/**
 * Classe astratta che definisce i metodi da implementare per un datasource <b> 
 * da uasre da una combo o select per il caricamento delle option
 * @author michele
 *
 * @param <T>
 */
public abstract class AbstractOptionDataSource<T> extends AbstractDataSource<T,T> {
	
	public abstract PaginatorBean<T> fetch(AdvancedCriteria criteria,Integer startRow,Integer endRow,List<OrderByBean> orderby) throws Exception;
	
	@Override
	public T update(T bean,T oldvalue) throws Exception{
		return null;
	}

	@Override
	public java.util.Map<String,ErrorBean> validate(T bean) throws Exception {
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
		

	
}	