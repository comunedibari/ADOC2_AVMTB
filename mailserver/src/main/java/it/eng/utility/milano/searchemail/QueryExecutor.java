package it.eng.utility.milano.searchemail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryExecutor {

	private static Logger mLogger = LogManager.getLogger(QueryExecutor.class);
	private String idMessage;
	private String account;
	
	public QueryExecutor(String pIdMessage, String pAccount){
		idMessage = pIdMessage;
		account = pAccount;
	}
	
	public boolean executeQuery() throws Exception{
		JdbcOdbcConnector lJdbcOdbcConnector = new JdbcOdbcConnector();
		Connection pConnection = null;
		PreparedStatement lPreparedStatement = null;
		ResultSet lResultSet = null;
		try {
			pConnection = lJdbcOdbcConnector.getConnection();
			if (pConnection == null) {
				throw new Exception("Impossibile stabilire una connessione con il db");
			}
			lPreparedStatement = pConnection.prepareStatement(DbQueryConfig.getQuery());
			lPreparedStatement.setString(1, idMessage);
			lPreparedStatement.setString(2, account);
			lResultSet = lPreparedStatement.executeQuery();
			//Parto dal presupposto che ho sempre e comunque un risultato
			while (lResultSet.next()){
				int lInt = lResultSet.getInt(1);
				if (lInt == 0) {
					mLogger.debug("BAD! Non ho trovato nessuna corrispondenza per il messaggio " + idMessage + " per l'account " + account);
					return false;
				} else if (lInt==1) {
					mLogger.debug("OK! Trovata corrispondenza per il messaggio " + idMessage + " per l'account " + account);
					return true;
				} else {
					mLogger.debug("BAD! Ho trovato pi� di una corrispondenza per il messaggio " + idMessage + " per l'account " + account);
					return false;
				}
			}
			return false;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (lResultSet!=null) lResultSet.close();
				if (lPreparedStatement!=null) lPreparedStatement.close();
				if (pConnection!=null && !pConnection.isClosed()){
					pConnection.close();
				}
			} catch (Exception e){
				mLogger.error("Errore chiusura connessione: " + e.getMessage(), e);
			}
		}
	}

}
