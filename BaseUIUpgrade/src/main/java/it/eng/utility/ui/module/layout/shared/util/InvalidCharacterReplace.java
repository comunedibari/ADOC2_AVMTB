package it.eng.utility.ui.module.layout.shared.util;

/**
 * 
 * @author FEBUONO
 *
 */

public class InvalidCharacterReplace {	
		
	public static String replaceAllInvalidCharacters(String value) {
		value = value.replace("‘", "'");
		value = value.replace("“", "\"");
		value = value.replace("—", "-");
		value = value.replace("’", "'");
		value = value.replace("”", "\"");
		return value;
	}	
	
}