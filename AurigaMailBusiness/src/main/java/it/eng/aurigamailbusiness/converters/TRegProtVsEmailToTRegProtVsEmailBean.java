package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;
import it.eng.aurigamailbusiness.database.mail.TRegProtVsEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TRegProtVsEmailToTRegProtVsEmailBean implements IBeanPopulate<TRegProtVsEmail, TRegProtVsEmailBean> {

	@Override
	public void populate(TRegProtVsEmail src, TRegProtVsEmailBean dest) throws Exception {
		if (src.getTEmailMgo() != null) {
			dest.setIdEmail(src.getTEmailMgo().getIdEmail());
		}
	}

	@Override
	public void populateForUpdate(TRegProtVsEmail src, TRegProtVsEmailBean dest) throws Exception {
		if (src.getTEmailMgo() != null) {
			dest.setIdEmail(src.getTEmailMgo().getIdEmail());
		}
	}

}