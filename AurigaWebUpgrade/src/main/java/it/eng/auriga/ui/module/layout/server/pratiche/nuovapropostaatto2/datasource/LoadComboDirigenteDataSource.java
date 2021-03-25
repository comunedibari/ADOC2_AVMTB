package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ScrivaniaOrganigrammaBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.SelectDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "LoadComboDirigenteDataSource")
public class LoadComboDirigenteDataSource extends SelectDataSource<ScrivaniaOrganigrammaBean> {

	@Override
	public PaginatorBean<ScrivaniaOrganigrammaBean> realFetch(AdvancedCriteria criteria, Integer startRow,
			Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
		
		String idSv = StringUtils.isNotBlank(getExtraparams().get("idSv")) ? getExtraparams().get("idSv") : "";
		
		String descrizione = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("descrizione")) {
					descrizione = (String) criterion.getValue();
				}
			}
		}
		
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SCRIVANIE_ORGANIGRAMMA");
		
		if(StringUtils.isNotBlank(getExtraparams().get("altriParamLoadCombo"))) {	
			String altriParametri = getExtraparams().get("altriParamLoadCombo");
			altriParametri = altriParametri.replace("$ID_USER_LAVORO$", idUserLavoro);
			altriParametri = altriParametri.replace("$STR_IN_DES$", descrizione);
			altriParametri = altriParametri.replace("$CI_TO_ADD$", idSv);
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		} else {
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("DES_RUOLO|*|$DIRIGENTE$|*|STR_IN_DES|*|" + descrizione + "|*|CI_TO_ADD|*|" + idSv);						
		}
		
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				loginBean, lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<ScrivaniaOrganigrammaBean> lPaginatorBean = new PaginatorBean<ScrivaniaOrganigrammaBean>();

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<ScrivaniaOrganigrammaBean> lListResult = XmlListaUtility.recuperaLista(xmlLista, ScrivaniaOrganigrammaBean.class);
			lPaginatorBean = new PaginatorBean<ScrivaniaOrganigrammaBean>(lListResult);			
		}

		return lPaginatorBean;
	}

}
