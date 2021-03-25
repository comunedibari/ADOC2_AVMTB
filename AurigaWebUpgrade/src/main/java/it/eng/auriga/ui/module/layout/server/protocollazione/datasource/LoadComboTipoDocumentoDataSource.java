package it.eng.auriga.ui.module.layout.server.protocollazione.datasource;

import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TipoDocumentoBean;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "LoadComboTipoDocumentoDataSource")
public class LoadComboTipoDocumentoDataSource extends AbstractFetchDataSource<TipoDocumentoBean> {

	@Override
	public PaginatorBean<TipoDocumentoBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
		
		String idTipoDocumento = StringUtils.isNotBlank(getExtraparams().get("idTipoDocumento")) ? getExtraparams().get("idTipoDocumento") : "";
		String categoriaReg = StringUtils.isNotBlank(getExtraparams().get("categoriaReg")) ? getExtraparams().get("categoriaReg") : "";
		String siglaReg = StringUtils.isNotBlank(getExtraparams().get("siglaReg")) ? getExtraparams().get("siglaReg") : "";
		boolean showErrorMsg = StringUtils.isNotBlank(getExtraparams().get("showErrorMsg")) && "true".equalsIgnoreCase(getExtraparams().get("showErrorMsg"));
		boolean isFromFilter = StringUtils.isNotBlank(getExtraparams().get("isFromFilter")) && "true".equalsIgnoreCase(getExtraparams().get("isFromFilter"));
		boolean isCompilazioneModulo = StringUtils.isNotBlank(getExtraparams().get("isCompilazioneModulo")) && "true".equalsIgnoreCase(getExtraparams().get("isCompilazioneModulo"));
		boolean isSoloDaPubblicare = StringUtils.isNotBlank(getExtraparams().get("isSoloDaPubblicare")) && "true".equalsIgnoreCase(getExtraparams().get("isSoloDaPubblicare"));
		
		String descrizione = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("descTipoDocumento")) {
					descrizione = (String) criterion.getValue();
				}
			}
		}

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_DOC");

		String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|FLG_ALLEGATO_PRIMARIO|*|P";
		// Differenziare per i filtri dove non va passato FLG_SOLO_ASSEGNABILI a 1 in AltriParametriIn
		if (!isFromFilter) {
			altriParametri += "|*|FLG_SOLO_ASSEGNABILI|*|1";
		}
		// Se l'utente ha digitato un filtro, il CI_TO_ADD non deve essere passato
		if (StringUtils.isNotBlank(descrizione)) {
			altriParametri += "|*|STR_IN_DES|*|" + descrizione;
		} else {
			altriParametri += "|*|CI_TO_ADD|*|" + idTipoDocumento;
		}
		if (StringUtils.isNotBlank(categoriaReg)) {
			altriParametri += "|*|CATEGORIA_REG|*|" + categoriaReg;
		}
		if (StringUtils.isNotBlank(siglaReg)) {
			altriParametri += "|*|SIGLA_REG|*|" + siglaReg;
		}
		if (isCompilazioneModulo) {
			altriParametri += "|*|X_COMPILAZIONE_MODULO|*|1";
		}
		if(isSoloDaPubblicare) {
			altriParametri += "|*|FLG_SOLO_DA_PUBBLICARE|*|1";
		}
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);

		// Differenziare per i filtri dove non va passato FlgSoloVldIn a 1
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(!isFromFilter ? new BigDecimal(1) : null);
		lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		PaginatorBean<TipoDocumentoBean> lPaginatorBean = new PaginatorBean<TipoDocumentoBean>();

		if (lStoreResultBean.getDefaultMessage() != null) {
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(0);
			lPaginatorBean.setTotalRows(0);
			if(showErrorMsg) {
				addMessage("Nessuna tipologia trovata: " + lStoreResultBean.getDefaultMessage(), "", MessageType.ERROR);
			}
		} 
		else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<TipoDocumentoBean> data = XmlListaUtility.recuperaLista(xmlLista, TipoDocumentoBean.class);

			lPaginatorBean.setData(data);
			lPaginatorBean.setStartRow(0);
			lPaginatorBean.setEndRow(data.size());
			lPaginatorBean.setTotalRows(data.size());
		}

		return lPaginatorBean;

	}

}
