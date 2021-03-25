package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.TFolderCaselleBean;
import it.eng.aurigamailbusiness.database.mail.TFolderCaselle;
import it.eng.core.business.converter.IBeanPopulate;

public class TFolderCaselleToTFolderCaselleBean implements IBeanPopulate<TFolderCaselle, TFolderCaselleBean> {

	@Override
	public void populate(TFolderCaselle src, TFolderCaselleBean dest) throws Exception {
		if (src.getMailboxAccount() != null) {
			dest.setIdCasella(src.getMailboxAccount().getIdAccount());
		}
		if (src.getTFolderCaselle() != null) {
			dest.setIdFolderSup(src.getTFolderCaselle().getIdFolderCasella());
		}
	}

	@Override
	public void populateForUpdate(TFolderCaselle src, TFolderCaselleBean dest) throws Exception {
		if (src.getMailboxAccount() != null) {
			dest.setIdCasella(src.getMailboxAccount().getIdAccount());
		}
		if (src.getTFolderCaselle() != null) {
			dest.setIdFolderSup(src.getTFolderCaselle().getIdFolderCasella());
		}
	}

}