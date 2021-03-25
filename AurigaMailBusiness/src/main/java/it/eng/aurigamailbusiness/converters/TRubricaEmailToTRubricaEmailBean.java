package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.database.mail.TRubricaEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TRubricaEmailToTRubricaEmailBean implements IBeanPopulate<TRubricaEmail, TRubricaEmailBean> {

	@Override
	public void populate(TRubricaEmail src, TRubricaEmailBean dest) throws Exception {
		if (src.getTAnagFruitoriCaselle() != null) {
			dest.setIdFruitoreCasella(src.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
	}

	@Override
	public void populateForUpdate(TRubricaEmail src, TRubricaEmailBean dest) throws Exception {
		if (src.getTAnagFruitoriCaselle() != null) {
			dest.setIdFruitoreCasella(src.getTAnagFruitoriCaselle().getIdFruitoreCasella());
		}
	}

}