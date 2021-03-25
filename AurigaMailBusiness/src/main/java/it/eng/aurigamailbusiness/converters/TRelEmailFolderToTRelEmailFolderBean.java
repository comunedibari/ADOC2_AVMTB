package it.eng.aurigamailbusiness.converters;

import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.database.mail.TRelEmailFolder;
import it.eng.core.business.converter.IBeanPopulate;

public class TRelEmailFolderToTRelEmailFolderBean implements IBeanPopulate<TRelEmailFolder, TRelEmailFolderBean> {

	@Override
	public void populate(TRelEmailFolder src, TRelEmailFolderBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setIdFolderCasella(src.getId().getIdFolderCasella());
			dest.setIdEmail(src.getId().getIdEmail());
		}
	}

	@Override
	public void populateForUpdate(TRelEmailFolder src, TRelEmailFolderBean dest) throws Exception {
		if (src.getId() != null) {
			dest.setIdFolderCasella(src.getId().getIdFolderCasella());
			dest.setIdEmail(src.getId().getIdEmail());
		}
	}

}