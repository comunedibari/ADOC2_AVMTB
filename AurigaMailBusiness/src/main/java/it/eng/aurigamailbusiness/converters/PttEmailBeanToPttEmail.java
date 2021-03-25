package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.PttEmailBean;
import it.eng.aurigamailbusiness.database.egr.PttEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class PttEmailBeanToPttEmail implements IBeanPopulate<PttEmailBean, PttEmail> {

	@Override
	public void populate(PttEmailBean src, PttEmail dest) throws Exception {
		// TODO inserire elementi che servono per il popolamento
	}

	@Override
	public void populateForUpdate(PttEmailBean src, PttEmail dest) throws Exception {
		// TODO inserire elementi che servono per il popolamento dell'update...
	}

}