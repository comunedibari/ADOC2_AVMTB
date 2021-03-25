package it.eng.auriga.module.business.dao.beansconverters;

import it.eng.auriga.module.business.dao.beans.RichEmailToSendBean;
import it.eng.auriga.module.business.entity.TRichEmailToSend;
import it.eng.core.business.converter.IBeanPopulate;

import org.hibernate.Session;

/**
 * @author Federico Cacco
 */

public class TRichEmailToSendToRichEmailToSendBeanConverter implements IBeanPopulate<TRichEmailToSend, RichEmailToSendBean> {

	@SuppressWarnings("unused")
	private Session session;

	public TRichEmailToSendToRichEmailToSendBeanConverter(Session session) {
		this.session = session;
	}

	@Override
	public void populate(TRichEmailToSend src, RichEmailToSendBean dest) throws Exception {
	}

	@Override
	public void populateForUpdate(TRichEmailToSend src, RichEmailToSendBean dest) throws Exception {
	}

}