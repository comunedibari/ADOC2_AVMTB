package it.eng.utility.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.JDBCException;

import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.utility.email.process.exception.MailServerException;

public class LogUtility {

	static Logger log = LogManager.getLogger(LogUtility.class);

	/**
	 * Metodo per loggare informazioni aggiuntive sull'eccezione Hibernate
	 * 
	 * @param error
	 */

	public static void logHibernateException(Throwable error) {

		if (error != null) {

			Throwable errorException = error;

			if (error instanceof AurigaMailBusinessException || error instanceof MailServerException) {
				errorException = error.getCause();
			}
			if (errorException instanceof JDBCException) {
				log.error("Eccezione JDBCException Hibernate - dettaglio messagio: " + errorException.getMessage());
				log.error("Eccezione JDBCException Hibernate - error code: " + ((JDBCException) errorException).getErrorCode());
				log.error("Eccezione JDBCException Hibernate - SQLState: " + ((JDBCException) errorException).getSQLState());
				log.error("Eccezione JDBCException Hibernate - query in errore: " + ((JDBCException) errorException).getSQL());
			}
		}

	}

}
