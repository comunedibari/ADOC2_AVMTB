package it.eng.auriga.module.business.dao.beansconverters;

import org.apache.commons.lang.StringUtils;

public class BeanConverterUtils {

	public static String getIfNotNull(String lString){
		return (StringUtils.isNotEmpty(lString)?lString+" ":"");
	}
	
	public static String getIfNotNull(String lString, String alt){
		return (StringUtils.isNotEmpty(lString)?alt+" ":"");
	}
	
	public static String getIfNotNull(String lString, String pre, String post){
		return (StringUtils.isNotEmpty(lString)?pre + lString + post:"");
	}
	
	public static String getIfNotNullOnlyPre(String lString, String pre){
		return (StringUtils.isNotEmpty(lString)?pre + lString:"");
	}
}
