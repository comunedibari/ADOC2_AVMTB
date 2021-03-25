package it.eng.utility.milano.searchemail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcOdbcConnector {
	
	private static Logger mLogger = LogManager.getLogger(JdbcOdbcConnector.class);

	private static String odbcName;
	private String username;
	private String password;
	private static String OJDBC_STRING = "jdbc:odbc:";
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver") ;
        mLogger.debug("Try to open DB");
        Connection lConnection = DriverManager.getConnection(OJDBC_STRING + odbcName);
        mLogger.debug("Connection to " + OJDBC_STRING + odbcName + " opened");
        return lConnection;
	}
	public static String getOdbcName() {
		return odbcName;
	}
	public static void setOdbcName(String pOdbcName) {
		odbcName = pOdbcName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
