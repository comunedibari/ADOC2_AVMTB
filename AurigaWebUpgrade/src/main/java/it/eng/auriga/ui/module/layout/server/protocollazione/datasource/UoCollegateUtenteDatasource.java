package it.eng.auriga.ui.module.layout.server.protocollazione.datasource;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoProtocollanteBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.util.List;

@Datasource(id = "UoCollegateUtenteDatasource")
public class UoCollegateUtenteDatasource extends AbstractFetchDataSource<UoProtocollanteBean> {

	@Override
	public PaginatorBean<UoProtocollanteBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro() != null ? lAurigaLoginBean.getIdUserLavoro() : "";
		DmpkLoadComboDmfn_load_comboBean bean = new DmpkLoadComboDmfn_load_comboBean();
		bean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		bean.setTipocomboin("SOGG_INT_COLLEGATI_UTENTE");
		String altriParametri = "TIPI_SOGG_INT|*|UO|*|FLG_ANCHE_CON_DELEGA|*|1|*|ID_USER_LAVORO|*|" + idUserLavoro;
		altriParametri += "|*|FLG_INCL_SOTTOUO_SV|*|1";
//		if(isSelezioneUoLavoro) altriParametri += "|*|FLG_ANCHE_REL_TIPO_L|*|1"; // andrebbe passato solo in caso di selezione della UO di lavoro, ma per il momento lo lasciamo commentato
		bean.setAltriparametriin(altriParametri);
		DmpkLoadComboDmfn_load_combo store = new DmpkLoadComboDmfn_load_combo();
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResult = store.execute(getLocale(), lAurigaLoginBean, bean);
		if (storeResult.isInError()) {
			throw new StoreException(storeResult);
		}
		bean = storeResult.getResultBean();
		String lStrXml = bean.getListaxmlout();
		List<UoProtocollanteBean> lListResult = XmlListaUtility.recuperaLista(lStrXml, UoProtocollanteBean.class);
		PaginatorBean<UoProtocollanteBean> result = new PaginatorBean<UoProtocollanteBean>(lListResult);
		return result;
	}

}
