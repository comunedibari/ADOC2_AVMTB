package it.eng.gd.lucenemanager.config;

/**
 * gestione destinazione del writer
 * 
 * @author jravagnan
 * 
 */
public class WriterDestinationConfig {

	public static final String CONFIG_NAME = "writerDestination";

	public static final String DB_VALUE = "db";

	public static final String FILE_VALUE = "file";

	private String destination = FILE_VALUE;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
