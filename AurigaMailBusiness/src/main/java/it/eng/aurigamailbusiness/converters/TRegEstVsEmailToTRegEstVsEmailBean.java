package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.TRegEstVsEmailBean;
import it.eng.aurigamailbusiness.database.mail.TRegEstVsEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TRegEstVsEmailToTRegEstVsEmailBean implements IBeanPopulate<TRegEstVsEmail, TRegEstVsEmailBean> {

	@Override
	public void populate(TRegEstVsEmail src, TRegEstVsEmailBean dest) throws Exception {
		if (src.getTDestinatariEmailMgo() != null) {
			dest.setIdDestinatarioEmail(src.getTDestinatariEmailMgo().getIdDestinatarioEmail());
		}
		if (src.getTEmailMgoByIdEmailRicevuta() != null) {
			dest.setIdEmailRicevuta(src.getTEmailMgoByIdEmailRicevuta().getIdEmail());
		}
		if (src.getTEmailMgoByIdEmailInviata() != null) {
			dest.setIdEmailInviata(src.getTEmailMgoByIdEmailInviata().getIdEmail());
		}
	}

	@Override
	public void populateForUpdate(TRegEstVsEmail src, TRegEstVsEmailBean dest) throws Exception {
		if (src.getTDestinatariEmailMgo() != null) {
			dest.setIdDestinatarioEmail(src.getTDestinatariEmailMgo().getIdDestinatarioEmail());
		}
		if (src.getTEmailMgoByIdEmailRicevuta() != null) {
			dest.setIdEmailRicevuta(src.getTEmailMgoByIdEmailRicevuta().getIdEmail());
		}
		if (src.getTEmailMgoByIdEmailInviata() != null) {
			dest.setIdEmailInviata(src.getTEmailMgoByIdEmailInviata().getIdEmail());
		}

	}

}