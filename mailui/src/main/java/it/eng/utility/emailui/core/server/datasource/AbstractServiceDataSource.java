package it.eng.utility.emailui.core.server.datasource;

import java.util.List;

import it.eng.utility.emailui.core.server.bean.AdvancedCriteria;
import it.eng.utility.emailui.core.server.bean.OrderByBean;
import it.eng.utility.emailui.core.server.bean.PaginatorBean;
import it.eng.utility.emailui.core.server.service.ErrorBean;

/**
 * Classe astratta che definisce i metodi da implementare per un datasource di tipo call
 * 
 * @author michele
 *
 * @param <T>
 */
public abstract class AbstractServiceDataSource<IN, OUT> extends AbstractDataSource<IN, OUT> {

	public abstract OUT call(IN bean) throws Exception;

	public java.util.Map<String, ErrorBean> validate(IN bean) throws Exception {
		return null;
	}

	public OUT update(IN bean, IN oldvalue) throws Exception {
		return add(bean);
	};

	@Override
	public OUT add(IN bean) throws Exception {
		return call(bean);
	}

	@Override
	public OUT remove(IN bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginatorBean<OUT> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println(criteria);
		return null;
	}
}