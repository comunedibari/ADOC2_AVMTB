package it.eng.applet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import it.eng.applet.listener.PrinterSelectedActionListener;
import it.eng.applet.scanner.PrinterScanner;
import netscape.javascript.JSObject;

public class PrinterScannerApplet extends JApplet {

	private static final long serialVersionUID = 0x4319c65c6f295ca5L;
	private static String CALLBACK = "callbackSelectPrinter";
	private static String CALLBACKCANCELPRINTERSCANNER = "callbackCancelSelectPrinter";
	private final String ALTRA_STAMPANTE = "Altra stampante";
	private final String NOME_ALTRA_STAMPANTE = "Nome stampante: ";
	private ButtonGroup mButtonGroup;
	private JButton mSelezionaButton;
	private JPanel radioPanelPrinters;
	private JPanel mJPanelApplet;
	private JPanel buttonPanel;
	private JSObject jso;
	private String callbackToExecute;
	private String callbackCancel;
	private String stampanteSelezionata;
	private JTextField nomeAltraStampanteTextField;
	private JLabel nomeAltraStampanteLabel;
	private JPanel altraStampantePanel;
	private JLabel labelSelezionaStampante;	
	private JPanel panelLabel;
	private static Logger mLogger = Logger.getLogger(PrinterScannerApplet.class);
	private boolean selectPrinter;  // identifica se � stata selezionata una stampante o meno. Usata nella fase di detroy dell'applet.

	public PrinterScannerApplet() {
		setName("Seleziona la stampante");
	}


	public void initPanel(java.util.List<String> printersAvailable, PrinterSelectedActionListener lPrinterSelectedActionListener, String stampanteDefault) {
	radioPanelPrinters = new JPanel(new GridLayout(0, 1));
	mButtonGroup = new ButtonGroup();
	JRadioButton lJRadioButton;
	boolean stampanteSelezionata = false;
	for (Iterator<String> i$ = printersAvailable.iterator(); i$.hasNext(); radioPanelPrinters.add(lJRadioButton)) {
		String lStrPrinter = (String) i$.next();
		lJRadioButton = new JRadioButton(lStrPrinter, false);
		lJRadioButton.setName(lStrPrinter);
		lJRadioButton.setActionCommand(it.eng.applet.listener.PrinterSelectedActionListener.Comandi.PRINTER.getValue());
		lJRadioButton.addActionListener(lPrinterSelectedActionListener);
		if ((stampanteDefault != null) && stampanteDefault.equals(lStrPrinter)) {
			lJRadioButton.setSelected(true);
			setStampanteSelezionata(stampanteDefault);
			stampanteSelezionata = true;
		}
		mButtonGroup.add(lJRadioButton);
	}
	// Aggiungo radio per altra stampante (commentato perche' non piu' utilizzato)
//	JRadioButton lJRadioButtonAltraStampante = new JRadioButton(ALTRA_STAMPANTE, false);
//	lJRadioButtonAltraStampante.setName(ALTRA_STAMPANTE);
//	lJRadioButtonAltraStampante.setActionCommand(it.eng.applet.listener.PrinterSelectedActionListener.Comandi.PRINTER.getValue());
//	lJRadioButtonAltraStampante.addActionListener(lPrinterSelectedActionListener);
//	radioPanelPrinters.add(lJRadioButtonAltraStampante);
//	mButtonGroup.add(lJRadioButtonAltraStampante);

	int maxLength = Math.max(findMax(printersAvailable), 50);
	radioPanelPrinters.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
	mJPanelApplet = new JPanel(new BorderLayout());
	JPanel panel = new JPanel(new BorderLayout());
	
	
	//Aggiunta della label "Seleziona stampante desiderata"
	labelSelezionaStampante = new JLabel();
	labelSelezionaStampante.setText("Seleziona la stampante desiderata: ");
	//Creazione del pannello per la label
	panelLabel = new JPanel(new BorderLayout());
	panelLabel.add(labelSelezionaStampante); //Aggiunta della label al pannello
	mJPanelApplet.add(panelLabel, BorderLayout.NORTH);
	
	//Pannello per avere una ScrollBar per i RadioButton
	JScrollPane scrollPane = new JScrollPane(radioPanelPrinters);
	panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
	panel.add(scrollPane, BorderLayout.CENTER);
	//commentato perche' non piu' utilizzato
	//panel.add(radioPanelPrinters, BorderLayout.CENTER);
	//panel.add(creaPanelNomeAltraStampante(), BorderLayout.SOUTH); 
	mJPanelApplet.add(panel, BorderLayout.CENTER);

	
//	if ((!stampanteSelezionata) && (stampanteDefault != null) && (!stampanteDefault.equalsIgnoreCase(""))) {
//		lJRadioButtonAltraStampante.setSelected(true);
//		nomeAltraStampanteTextField.setText(stampanteDefault);
//		setStampanteSelezionata(stampanteDefault);
//		mostraAltraStampante();
//		stampanteSelezionata = true;
//	} else {
//		nascondiAltraStampante();
//	}
	
	
	mJPanelApplet.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, 1));
	mSelezionaButton = new JButton("Seleziona");
	if (stampanteSelezionata) {
		mSelezionaButton.setEnabled(true);
	} else {
		mSelezionaButton.setEnabled(false);
	}
	mSelezionaButton.setAlignmentX(0.5F);
	mSelezionaButton.setActionCommand(it.eng.applet.listener.PrinterSelectedActionListener.Comandi.SELEZIONA.getValue());
	mSelezionaButton.addActionListener(lPrinterSelectedActionListener);
	buttonPanel.add(mSelezionaButton);
	mJPanelApplet.add(buttonPanel, BorderLayout.SOUTH);
	setContentPane(mJPanelApplet);
	setSize(new Dimension(maxLength * 7 + 60, printersAvailable.size() * 30 + 80));
	radioPanelPrinters.setSize(radioPanelPrinters.getSize());
	validate();
}
	
	
	private int findMax(java.util.List<String> printersAvailable) {
		int max = 0;
		Iterator<String> i$ = printersAvailable.iterator();
		do {
			if (!i$.hasNext()) {
				break;
			}
			String lStrPrinter = (String) i$.next();
			if (lStrPrinter.length() > max) {
				max = lStrPrinter.length();
			}
		} while (true);
		return max;
	}

	@Override
	public void init() {
		PrinterSelectedActionListener lPrinterSelectedActionListener = new PrinterSelectedActionListener(this);
		mLogger.info("Listener pulsanti radio creato");
		List<String> printersAvailable = (new PrinterScanner()).printerAvailable();
		mLogger.info("Inizializza il pannello");
		initPanel(printersAvailable, lPrinterSelectedActionListener, getStampanteDefault());
		selectPrinter = false;
		recuperoBrowser();
		recuperaCallback();
	}

	@Override
	public void destroy(){
		// Blocco IF  eseguito solo nel caso in cui si esce dall'applet senza aver selezionato una stampante 
		if(!selectPrinter){
			// Non ho selezionato la stampante, chiamo la callback di annullamento
			if (callbackCancel != null && !"".equalsIgnoreCase(callbackCancel)) {
				JSObject.getWindow(this).eval(callbackCancel + "(\"\")");
			}
		}
	}
	
	
	private void recuperaCallback() {
		callbackToExecute = getParameter(CALLBACK);
		callbackCancel = getParameter(CALLBACKCANCELPRINTERSCANNER);
		mLogger.info(callbackToExecute);
		mLogger.info(callbackCancel);
	}

	private String getStampanteDefault() {
		String stampanteDefault = getParameter("stampanteSelezionata");
		mLogger.info("Stampante predefinita: " + stampanteDefault);
		return stampanteDefault;
	}

	
	// Metodo Vecchio 
//	public void enableSeleziona(String selected) {
//		mLogger.info((new StringBuilder()).append("Selezionata la stampante ").append(selected).toString());
//		if (selected.equals(ALTRA_STAMPANTE)) {
//			//mostraAltraStampante(); 
//			setStampanteSelezionata(nomeAltraStampanteTextField.getText());
//			mSelezionaButton.setEnabled(true);
//		} else {
//			mLogger.info((new StringBuilder()).append("Selezionata la stampante ").append(selected).toString());
//   			//nascondiAltraStampante(); 
//			setStampanteSelezionata(selected);
//			mSelezionaButton.setEnabled(true);
//		}
//	}
	
	
	
	public void enableSeleziona(String selected) {
		mLogger.info((new StringBuilder()).append("Selezionata la stampante ").append(selected).toString());
		setStampanteSelezionata(selected);
		mSelezionaButton.setEnabled(true);
	}

	public void seleziona() {
		mLogger.info((new StringBuilder()).append("Setto sull'applicativo la stampante ").append(stampanteSelezionata).toString());
		selectPrinter = true;
		if (jso != null && !callbackToExecute.equals("")) {
			mLogger.info((new StringBuilder()).append("JsonObject non vuoto"));
			try {
				// mLogger.info((new StringBuilder()).append("Eseguo la chiamata").append(callbackToExecute).toString());
				mLogger.info((new StringBuilder()).append("Stampante selezionata: ").append(stampanteSelezionata).toString());
				mLogger.info((new StringBuilder()).append("Chiamata: ").append(callbackToExecute + "(" + stampanteSelezionata + ")").toString());
				// JSObject.getWindow(this).call(callbackToExecute, new Object[]{stampanteSelezionata});
				
				
				/*
				 * Inserito il replace in modo tale che, in caso di stampanti di rete, venga ritornato
				 * al chiamante il nome corretto.
				 */
				String stampante = stampanteSelezionata.replace("\\", "\\\\");
				JSObject.getWindow(this).eval(callbackToExecute + "(\"" + stampante + "\")");
				// mLogger.info((new StringBuilder()).append("Chiamata eseguita"));
			} catch (Exception ex) {
				mLogger.error((new StringBuilder()).append("Errore in esecuzione: ").append(ex.getMessage()).toString(), ex);
			}
		} else {
			mLogger.info((new StringBuilder()).append("JsonObject vuoto!!!"));
		}
		stop();
		destroy();
	}

	public String getStampanteSelezionata() {
		return stampanteSelezionata;
	}

	public void setStampanteSelezionata(String stampanteSelezionata) {
		this.stampanteSelezionata = stampanteSelezionata;
	}

	protected void recuperoBrowser() {
		try {
			jso = JSObject.getWindow(this);
			mLogger.info("Acquisito il controllo del JavaScript Object, questa applet \350 in esecuzione in un browser.");
		} catch (Throwable e) {
			mLogger.error("Esecuzione fuori dal browser... forse sta usando un visualizzatore di applet.", e);
		}
	}

	private JPanel creaPanelNomeAltraStampante() {
		nomeAltraStampanteLabel = new JLabel(NOME_ALTRA_STAMPANTE, JLabel.LEFT);
		nomeAltraStampanteLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 0, 0));
		nomeAltraStampanteTextField = new JTextField();
		// Listen for changes in the text
		nomeAltraStampanteTextField.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				select();
			}

			public void removeUpdate(DocumentEvent e) {
				select();
			}

			public void insertUpdate(DocumentEvent e) {
				select();
			}

			public void select() {
				setStampanteSelezionata(nomeAltraStampanteTextField.getText());
			}
		});
		altraStampantePanel = new JPanel();
		altraStampantePanel.setLayout(new BorderLayout());
		altraStampantePanel.add(nomeAltraStampanteLabel, BorderLayout.WEST);
		altraStampantePanel.add(nomeAltraStampanteTextField, BorderLayout.CENTER);
		return altraStampantePanel;
	}

	private void nascondiAltraStampante() {
		nomeAltraStampanteTextField.setVisible(false);
		nomeAltraStampanteLabel.setText(" ");
		// altraStampantePanel.setVisible(false);
	}

	private void mostraAltraStampante() {
		// altraStampantePanel.setEnabled(true);
		nomeAltraStampanteTextField.setVisible(true);
		nomeAltraStampanteLabel.setText(NOME_ALTRA_STAMPANTE);
	}

}
