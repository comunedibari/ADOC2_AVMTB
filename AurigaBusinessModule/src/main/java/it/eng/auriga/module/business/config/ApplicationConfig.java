package it.eng.auriga.module.business.config;

public class ApplicationConfig {

	private static String idApplication;
	
	private static int maxRecordInQuery = 100;

	public static void setIdApplication(String idApplication) {
		ApplicationConfig.idApplication = idApplication;
	}

	public static String getIdApplication() {
		return idApplication;
	}

	public static void setMaxRecordInQuery(int maxRecordInQuery) {
		ApplicationConfig.maxRecordInQuery = maxRecordInQuery;
	}

	public static int getMaxRecordInQuery() {
		return maxRecordInQuery;
	}
	
	
}
