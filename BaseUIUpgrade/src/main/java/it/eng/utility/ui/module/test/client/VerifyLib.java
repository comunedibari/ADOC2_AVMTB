package it.eng.utility.ui.module.test.client;

import java.io.File;

public class VerifyLib {

	public static void main(String[] args){
		File lFile = new File("D:\\lib\\");
		String[] attuali = lFile.list();
		File lFilePreviste = new File("D:\\Versione corretta\\");
		String[] previste = lFilePreviste.list();
		for (String lString: attuali){
			if (!isPresent(lString, previste)){
//				System.out.println(lString);
			}
		}
	}

	private static boolean isPresent(String lString, String[] previste) {
		for (String previsto : previste){
			if (lString.equals(previsto)){
				return true;
			}
		}
		return false;
	}
}
