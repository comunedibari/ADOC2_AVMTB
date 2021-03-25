package it.eng.utility.email.database.script;

import java.io.InputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.hibernate.jdbc.Work;

/**
 * Esegue lo script di installazione della tabelle
 * @author michele
 *
 */
public class ExecuteScriptWork implements Work {

	@Override
	public void execute(Connection connection) throws SQLException {
		String actualscript = null;
		
		try{
			ScriptRunner runner = new ScriptRunner(connection, true, false);
			InputStream stream = ScriptParsing.class.getResourceAsStream("/oracle10g.sql");
			runner.runScript(new StringReader(IOUtils.toString(stream)));
		}catch(Throwable e){
			throw new SQLException("SQL COMMAND:"+actualscript,e);
		}
	}
}