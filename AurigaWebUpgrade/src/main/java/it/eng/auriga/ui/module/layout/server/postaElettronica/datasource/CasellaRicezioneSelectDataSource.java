package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.common.InfoCasellaBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "CasellaRicezioneSelectDataSource")
public class CasellaRicezioneSelectDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {
	
	private static Logger mLogger = Logger.getLogger(CasellaRicezioneSelectDataSource.class);

	// private static Logger mLogger = Logger.getLogger(CasellaRicezioneSelectDataSource.class);

	// @Override
	// public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria,
	// Integer startRow, Integer endRow, List<OrderByBean> orderby)
	// throws Exception {
	//
	// String idUtenteModPec = StringUtils.isNotBlank(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdUtenteModPec()) ?
	// AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdUtenteModPec() : "";
	//
	// List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
	// if(StringUtils.isNotBlank(idUtenteModPec)) {
	// CasellaUtility lCasellaUtility = new CasellaUtility();
	// ListaIdCaselleInput lListaIdCaselleInput = new ListaIdCaselleInput();
	// List<FlagArrivoInvio> lList = new ArrayList<ListaIdCaselleInput.FlagArrivoInvio>();
	// String classifica = getExtraparams().get("classifica");
	// mLogger.debug("Classifica vale " + classifica);
	// if (StringUtils.isNotEmpty(classifica)){
	// if (classifica.startsWith("standard.arrivo") || classifica.startsWith("standard.archiviata.arrivo") ){
	// lList.add(FlagArrivoInvio.IN);
	// mLogger.debug("Cerco per Flag IN");
	// }
	// else if (classifica.startsWith("standard.invio") || classifica.startsWith("standard.archiviata.invio") || classifica.startsWith("standard.bozze")){
	// lList.add(FlagArrivoInvio.OUT);
	// mLogger.debug("Cerco per Flag OUT");
	// }
	// }
	//
	// lListaIdCaselleInput.setFlag(lList);
	// lListaIdCaselleInput.setIdUtente(idUtenteModPec);
	// lListaIdCaselleInput.setIdEnteAOO(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio()+"");
	// ListaCaselleUtenteWithAccountOutput lListaIdCaselleOutput = lCasellaUtility.getlistacaselleutentewithaccount(getLocale(), lListaIdCaselleInput);
	//
	// Map<String, String> caselle = lListaIdCaselleOutput.getAccounts();
	// for (String casella : caselle.keySet()){
	// SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
	// lSimpleKeyValueBean.setValue(caselle.get(casella));
	// lSimpleKeyValueBean.setKey(casella);
	// lListResult.add(lSimpleKeyValueBean);
	// }
	// }
	//
	// PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
	// lPaginatorBean.setData(lListResult);
	// lPaginatorBean.setStartRow(0);
	// lPaginatorBean.setEndRow(lListResult.size());
	// lPaginatorBean.setTotalRows(lListResult.size());
	//
	// return lPaginatorBean;
	// }

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro()
				: "";

		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();
		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("CASELLE_INV_RIC");
		String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro;

		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(null);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		if (StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			try {
				for (InfoCasellaBean lInfoCasellaBean : XmlListaUtility.recuperaLista(xmlLista, InfoCasellaBean.class)) {
					SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
					lSimpleKeyValueBean.setKey(lInfoCasellaBean.getIdAccount());
					lSimpleKeyValueBean.setValue(preparaDescrizioneMail(lInfoCasellaBean.getTipoMail(), lInfoCasellaBean.getAccount()));
					lListResult.add(lSimpleKeyValueBean);
				}
			} catch (Exception e) {
				mLogger.warn(e);
			}
		}

		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

	private String preparaDescrizioneMail(String tipoMail, String descrizione) {
		Map<String, String> flagsMap = getFlag();
		if (flagsMap.containsKey(tipoMail)) {
			return "<div><img src=\"images/" + flagsMap.get(tipoMail) + "\" width=\"14\" height=\"14\" style=\"vertical-align:middle;\" /> " + descrizione + "</div>";
		} else {
			return "<div><img src=\"images/" + "blank.png" + "\" width=\"14\" height=\"14\" style=\"vertical-align:middle;\" /> " + descrizione + "</div>";
		}
	}

	private Map<String, String> getFlag() {
		Map<String, String> flagsMap = new HashMap<String, String>();
		flagsMap.put("PEC", "mail/PEC.png");
		flagsMap.put("PEO", "mail/PEO.png");
		return flagsMap;
	}

}
