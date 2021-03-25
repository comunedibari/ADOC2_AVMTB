package it.eng.utility.email.operation.impl.archiveoperation.utils;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.MailboxBean;
import it.eng.aurigamailbusiness.database.dao.DaoMailbox;
import it.eng.core.business.TFilterFetch;

/**
 * utility per la gestione degli account
 * 
 * @author jravagnan
 *
 */
public class AccountUtils {

	private AccountUtils() {
		throw new IllegalStateException("Classe di utilità");
	}

	private static final Logger log = LogManager.getLogger(AccountUtils.class);

	/**
	 * ricava l'idAccount dall'idMailbox
	 * 
	 * @param idCasella
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String retrieveIdAccount(String idMailbox) throws Exception {
		TFilterFetch<MailboxBean> filter = new TFilterFetch<MailboxBean>();
		MailboxBean filtro = new MailboxBean();
		filtro.setIdMailbox(idMailbox);
		filter.setFilter(filtro);
		List<MailboxBean> listaRis;
		try {
			listaRis = DaoUtils.getDao(DaoMailbox.class, idMailbox).search(filter).getData();
			if (listaRis != null && !listaRis.isEmpty()) {
				return listaRis.get(0).getIdAccount();
			}
		} catch (Exception e) {
			log.error("Impossibile ricavare l'account per l'idMailbox: " + idMailbox);
			throw e;
		}
		return null;
	}

}
