package it.eng.auriga.ui.module.layout.server.archivio.datasource;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ResponsabileProcedimentoPubblicazioneBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;

@Datasource(id = "LoadComboResponsabileProcedimentoPubblicazioneDataSource")
public class LoadComboResponsabileProcedimentoPubblicazioneDataSource extends AbstractFetchDataSource<ResponsabileProcedimentoPubblicazioneBean> {
	@Override
	public PaginatorBean<ResponsabileProcedimentoPubblicazioneBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
				
		String descrizioneResponsabileProcedimentoPubblicazione = "";
		String usernameResponsabileProcedimentoPubblicazione = "";
		String idResponsabileProcedimentoPubblicazione = "";
		
		if (criteria!=null && criteria.getCriteria()!=null){			
			for (Criterion criterion : criteria.getCriteria()){
				if(criterion.getFieldName().equals("descrizioneResponsabileProcedimentoPubblicazione")) {
					descrizioneResponsabileProcedimentoPubblicazione = (String) criterion.getValue();					
				} else if(criterion.getFieldName().equals("usernameResponsabileProcedimentoPubblicazione")) {
					usernameResponsabileProcedimentoPubblicazione = (String) criterion.getValue();					
				} else if (criterion.getFieldName().equals("idResponsabileProcedimentoPubblicazione")){
					idResponsabileProcedimentoPubblicazione = (String) criterion.getValue();
					
				}
			}
		}	
		
		idResponsabileProcedimentoPubblicazione = StringUtils.isNotBlank(getExtraparams().get("idResponsabileProcedimentoPubblicazione")) ? getExtraparams().get("idResponsabileProcedimentoPubblicazione") : "";
		
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);		
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("UTENTI");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_ACCRED_IN_DOMINIO|*|1|*|STR_IN_USERNAME|*|" + usernameResponsabileProcedimentoPubblicazione + "|*|STR_IN_DES|*|" + descrizioneResponsabileProcedimentoPubblicazione +  "|*|CI_TO_ADD|*|" +  idResponsabileProcedimentoPubblicazione);
		
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
				
		PaginatorBean<ResponsabileProcedimentoPubblicazioneBean> lPaginatorBean = new PaginatorBean<ResponsabileProcedimentoPubblicazioneBean>();		
		
		if(lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);			
		} 
		else 
		{
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<ResponsabileProcedimentoPubblicazioneBean> lista = XmlListaUtility.recuperaLista(xmlLista, ResponsabileProcedimentoPubblicazioneBean.class);
			lPaginatorBean.setData(lista);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(lista.size());
			lPaginatorBean.setTotalRows(lista.size());				
		} 
		return lPaginatorBean;
	}
}