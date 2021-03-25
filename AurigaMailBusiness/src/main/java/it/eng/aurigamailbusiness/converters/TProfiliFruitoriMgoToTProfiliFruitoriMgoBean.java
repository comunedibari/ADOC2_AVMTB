package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.TProfiliFruitoriMgoBean;
import it.eng.aurigamailbusiness.database.mail.TProfiliFruitoriMgo;
import it.eng.core.business.converter.IBeanPopulate;

public class TProfiliFruitoriMgoToTProfiliFruitoriMgoBean implements IBeanPopulate<TProfiliFruitoriMgo, TProfiliFruitoriMgoBean> {

	@Override
	public void populate(TProfiliFruitoriMgo src, TProfiliFruitoriMgoBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setProfilo(src.getId().getProfilo());
			dest.setIdRelFruitoreCasella(src.getId().getIdRelFruitoreCasella());
		}

	}

	@Override
	public void populateForUpdate(TProfiliFruitoriMgo src, TProfiliFruitoriMgoBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setProfilo(src.getId().getProfilo());
			dest.setIdRelFruitoreCasella(src.getId().getIdRelFruitoreCasella());
		}

	}

}