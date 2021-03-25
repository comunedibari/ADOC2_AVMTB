package it.eng.utility.database;

// Classe che contiene costanti da utilizzare per i parametri di configurazione della mailbox

public class MailboxConfigDefaultValue {

	public static final Integer DEFAULT_FETCH = 20; // numero massimo di messaggi da scaricare dalla mailbox ad ogni connessione IMAP

	public static final Integer DEFAULT_MAX_NUM_MESSAGE_ERROR_TO_PROCESS = 10; // numero massimo di messaggi da ri-processare ad ogni schedulazione della
																				// casella

	public static final Integer DEFAULT_DELAY = 10000; // attesa fra le connessioni successive a IMAP

	public static final Integer DEFAULT_MAX_TRY_NUM_OPERATION = 10; // numero massimo di tentativi per l'esecuzione di un'operazione

	public static final Boolean DEFAULT_DELETE_SENT_PEC = false;
	
	public static final Boolean DEFAULT_AUTO_PROTOCOL = false;

}
