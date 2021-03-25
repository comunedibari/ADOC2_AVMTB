package it.eng.utility.email.bean;

import java.util.List;

public class MailConfigurator {

	private static List<String> caselle;

	public static void setCaselle(List<String> caselle) {
		MailConfigurator.caselle = caselle;
	}

	public static List<String> getCaselle() {
		return caselle;
	}
}
