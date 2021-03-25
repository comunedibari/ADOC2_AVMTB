package it.eng.utility.ui.module.core.server.datasource;

import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.StatusBean;

@Datasource(id="UploadStatusDataSource")
public class UploadStatusDataSource extends AbstractServiceDataSource<StatusBean, StatusBean>{

	@Override
	public StatusBean call(StatusBean bean) throws Exception {
		if (bean.isFinish()){
			getSession().setAttribute("caricato", 0);
			getSession().setAttribute("finito", false);
			return bean;
		}
		Integer lIntCaricato = getSession().getAttribute("caricato")!=null?(Integer)getSession().getAttribute("caricato"):0;
		Integer lIntNumero = getSession().getAttribute("numero")!=null?(Integer)getSession().getAttribute("numero"):0;
		boolean finito = getSession().getAttribute("finito")!=null?(Boolean)getSession().getAttribute("finito"):false;
		StatusBean lStatusBean = new StatusBean();
		lStatusBean.setCaricato(lIntCaricato);
		lStatusBean.setNumero(lIntNumero);
		lStatusBean.setFinito(finito);
		return lStatusBean;
	}

}
