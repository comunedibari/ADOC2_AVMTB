package it.eng.utility.email.database.script;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Classe di parsing degli script
 * @author michele
 */
public class ScriptParsing {
	
	/**
	 * Ritorna la lista degli script di creazione
	 * @return
	 * @throws Exception
	 */
	public static List<String> getScript() throws Exception{
		InputStream stream = ScriptParsing.class.getResourceAsStream("/oracle10g.sql");
		String scriptsource = IOUtils.toString(stream, "UTF-8");
		String[] arrayscript = StringUtils.split(scriptsource, ";");
		return Arrays.asList(arrayscript);
	}
	
}
