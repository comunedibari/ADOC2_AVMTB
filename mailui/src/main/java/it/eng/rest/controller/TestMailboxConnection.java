package it.eng.rest.controller;

import org.apache.axis.encoding.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.eng.aurigamailbusiness.utility.MailboxUtil;
import it.eng.core.business.HibernateUtil;
import it.eng.utility.database.SubjectInitializer;

@RestController
@RequestMapping("/service/testmailboxconnection")
public class TestMailboxConnection {
	
	private static Logger mLogger = LogManager.getLogger(TestMailboxConnection.class);

	@RequestMapping(value = "/testconnection", method = RequestMethod.POST)
	public @ResponseBody boolean testconnection(@RequestParam("email") String email, @RequestParam("password") String password) {
		mLogger.debug("email: "+email);
		mLogger.debug("password: "+password);
		mLogger.debug("password.decode: "+ (new String(Base64.decode(password))));
		SubjectInitializer.init(null);
		Session hibernateSession = null;
		try {
			hibernateSession = HibernateUtil.begin();
			return testPassword(email, new String(Base64.decode(password)));
		} catch (Exception e) {
			mLogger.error("Exception: " + e.getLocalizedMessage(), e);
			return false;
		} finally {
			try {
				HibernateUtil.release(hibernateSession);
			} catch (Exception e) {
				mLogger.error("HibernateUtil.Exception: " + e.getLocalizedMessage(), e);
				return false;
			}
		}
	}

	private boolean testPassword(String email, String password) throws Exception {
		Boolean result = MailboxUtil.connectIMAPByEmail(email, password);
		mLogger.info("Risultato connessione IMAP per id account" + email + ": " + result);
		return true;
	}	
}
