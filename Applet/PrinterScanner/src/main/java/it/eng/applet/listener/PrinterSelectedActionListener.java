package it.eng.applet.listener;

import it.eng.applet.PrinterScannerApplet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import org.apache.log4j.Logger;

public class PrinterSelectedActionListener implements ActionListener {
	
	public enum Comandi {
		SELEZIONA("Seleziona"), PRINTER("PrinterSelected");
		
		private String value;
		
		public String getValue() {return value;}
		private Comandi(String pStrComando){
			value = pStrComando;
		}
		
		public static Comandi getComando(String value){
			for (Comandi lComandi : Comandi.values()){
				if (lComandi.getValue().equals(value)) return lComandi;
			}
			return null;
		}
	}
	
	private PrinterScannerApplet applet;
	private static Logger mLogger = Logger.getLogger(PrinterSelectedActionListener.class);
	
	public PrinterSelectedActionListener(
			PrinterScannerApplet printerScannerApplet) {
		applet = printerScannerApplet;
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		String lStrAction = paramActionEvent.getActionCommand();
		mLogger.info("Ricevuto comando " + lStrAction);
		Comandi lComando = Comandi.getComando(lStrAction);
		switch (lComando) {
		case SELEZIONA:
			applet.seleziona();
			break;
		case PRINTER:
			applet.enableSeleziona(((JRadioButton)paramActionEvent.getSource()).getName());
			break;
		default:
			
			break;
		}
	}

}
