package it.eng.utility.milano.searchemail;

public class DbQueryConfig {
	private static String query;
	private static String odbcName;
	public static String getQuery() {
		return query;
	}
	public static void setQuery(String query) {
		DbQueryConfig.query = query;
	}
	public static String getOdbcName() {
		return odbcName;
	}
	public static void setOdbcName(String odbcName) {
		DbQueryConfig.odbcName = odbcName;
	}
	
}
