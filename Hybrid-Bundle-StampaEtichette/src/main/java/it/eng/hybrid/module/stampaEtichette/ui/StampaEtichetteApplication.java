package it.eng.hybrid.module.stampaEtichette.ui;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.stampaEtichette.StampaEtichetteClientModule;
import it.eng.hybrid.module.stampaEtichette.bean.ParameterBean;
import it.eng.hybrid.module.stampaEtichette.bean.PdfPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.bean.StampaEtichettaPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.bean.TestoBarcodeBean;
import it.eng.hybrid.module.stampaEtichette.config.ManagerConfiguration;
import it.eng.hybrid.module.stampaEtichette.config.ParameterConfigurator;
import it.eng.hybrid.module.stampaEtichette.exception.InitException;
import it.eng.hybrid.module.stampaEtichette.util.EtichetteWriter;
import it.eng.hybrid.module.stampaEtichette.util.PdfWriter;
import it.eng.hybrid.module.stampaEtichette.util.PrintUtil;

public class StampaEtichetteApplication extends JFrame {

	public final static Logger logger = Logger.getLogger(StampaEtichetteApplication.class);

	public static boolean DEBUGENABLED = false;
	public static String VERSIONE = "3";

	private StampaEtichetteClientModule module;

	private JPanel pane = null;
	private JScrollPane scrolling = null;
	private JTextPane fileBox = null;

	private ParameterBean parameter;
	private StampaEtichettaPropertiesBean properties;
	private PdfPropertiesBean pdfProperties;

	private Map<String, String> props = new HashMap<String, String>();

	private boolean errore = false;

	/**
	 * @param owner
	 */
	public StampaEtichetteApplication(StampaEtichetteClientModule module, JSONArray parameters) {
		// super(owner);
		this.module = module;

		JSONObject options = parameters.getJSONObject(0);
		Iterator optionsItr = options.keys();
		List<String> optionNames = new ArrayList<String>();
		while (optionsItr.hasNext()) {
			optionNames.add((String) optionsItr.next());
		}

		for (int i = 0; i < options.length(); i++) {

			props.put(optionNames.get(i), options.getString(optionNames.get(i)));
			logger.info("Proprieta' " + optionNames.get(i) + "=" + options.getString(optionNames.get(i)));
		}

		try {
			initPanel();
		} catch (Exception e) {
			logger.error("Errore");
			JOptionPane.showMessageDialog(null, new String(e.getMessage()), "Attenzione", JOptionPane.ERROR_MESSAGE);
			closeFrame();
		}
		try {
			logger.info(XMLResourceDescriptor.getCSSParserClassName());
		} catch (Throwable e) {
			logger.error("Errore ", e);

			JOptionPane.showMessageDialog(null, new String(e.getMessage()), "Attenzione", JOptionPane.ERROR_MESSAGE);
			closeFrame();
		}

		// init parametri
		inizializzoParametri(parameters);
		// init properties
		inizializzoProperties();

		// Recuper l'istanza del browser
		// recuperoBrowser();

		try {
			PrintUtil.checkPrinterSelected(parameter);
			logger.info("tipo stampa: " + parameter.getTipoStampa());
			switch (parameter.getTipoStampa()) {
			case PRN:
				gestisciPRN();
				break;
			case PDF:
				gestisciPDF();
				break;
			default:
				logger.info("Tipo di stampa non inizializzato");
				break;
			}
			logger.info("Chiamo il closeFrame");
			closeFrame();

		} catch (Throwable e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, new String(e.getMessage()), "Attenzione", JOptionPane.ERROR_MESSAGE);
			closeFrame();
		}

	}

	public void closeFrame() {
		logger.info("AutoClosePostSign: " + props.get("callbackClose"));
		if (props.get("callbackClose") != null) {

			// Setto la callback di cui il module si trova in attesa del settaggio
			module.setResult(props.get("callbackClose"));
		}
	}

	/*
	 * Il ciclo di vita dell'applicativo � cos� rapido che termina prima di aver visualizzato la schermata informativa. Per questo motivo � stato introdotto un
	 * ciclo nel modulo che attende qualche secondo e poi controlla, tramite questo metodo, se la finestra � stata visualizzata. Nel caso la finestra sia stata
	 * visualizzata il metodo la termina e rilascia le risorse.
	 */
	public boolean checkAndClose() {
		if (!errore) {
			if (this.isVisible()) {
				logger.info("Prima del dispose");
				this.dispose();
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/*
	 * Definizione di un metodo per definire il comportamento in caso di click sulla x della finestra di Windows
	 */
	public void forcedClose() {
		logger.info("Chiusura forzata");
		logger.info("Riassunto props : " + props.toString());

		if (props.get("callBackCancel") != null) {

			logger.info("props.get(callBackCancel): " + props.get("callBackCancel"));

			// Settaggio della callback in caso di cancellazione
			module.setResult(props.get("callBackCancel"));

		} else

		{
			logger.info("props.get(callBackCancel) e NULL");
		}
		this.dispose();
	}

	/**
	 * inizializza l'interfaccia utente non interattiva dell'applet
	 * 
	 * @throws Exception
	 */
	private void initPanel() throws Exception {
		pane = new JPanel();
		pane.setBounds(new Rectangle(0, 0, 500, 100));
		pane.setLayout(null);
		fileBox = new JTextPane();
		fileBox.setText(fileBox.getText() + "La stampa e' in corso...");
		fileBox.setEditable(false);
		scrolling = new JScrollPane(fileBox);
		scrolling.setBounds(new Rectangle(16, 20, 312, 80));
		pane.add(scrolling);
		this.setContentPane(pane);
	}

	protected void inizializzoParametri(JSONArray parameters) {
		try {
			parameter = ParameterConfigurator.getInstance(props).getParams();
			logger.info("getCallbackCancel in inizializzoParametri: " + parameter.getCallbackCancel());

		} catch (InitException e1) {
			e1.printStackTrace();
		}
	}

	protected void inizializzoProperties() {
		try {
			ManagerConfiguration.init(parameter);
			properties = ManagerConfiguration.getProperties();
			pdfProperties = ManagerConfiguration.getPdfProperties();
			logger.info("pdfProperties " + pdfProperties);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void gestisciPDF() throws Exception {

		// int numeroCopie = parameter.getNumeroCopie();

		//for (int i = 0; i < numeroCopie; i++) {
			try {
				printPdfLabel(pdfProperties, parameter);
			} catch (Exception e) {
				logger.error("Problema di comunicazione con la stampante: " + e.getMessage(), e);
				fileBox.setText(fileBox.getText() + "Selezionare la stampante desiderata");
				errore = true;
			//}

		}
		// AdobeUtil.cleanAdobe(properties.getRunningExe());
	}

	private void gestisciPRN() throws Exception {

		fileBox.setText(fileBox.getText() + "Inizio operazione stampa");
		boolean esito = EtichetteWriter.print(properties, parameter);

		if (esito)
			fileBox.setText(fileBox.getText() + "\nStampa completata.");
		else {
			fileBox.setText(fileBox.getText() + "\nStampa non completata,\n si e' verificato un errore.");
			errore = true;
		}

	}

	public void printPdfLabel(PdfPropertiesBean pdfPropertiesBean, ParameterBean pParameterBean) throws Exception {
		logger.debug("Entro in printPdfLabel di StampaEtichetteApplication");
		String pdfFile = PdfWriter.writePdf(pdfPropertiesBean, parameter);
		logger.debug("Stringa pdfFile: " + pdfFile);
		File lFile = new File(pdfFile);
		logger.debug("Path del file generato: " + lFile.getPath());
		List<TestoBarcodeBean> listaTesti = parameter.getTesto();
		for (TestoBarcodeBean testoBarcodeBean : listaTesti) {
			logger.debug("Conter" + testoBarcodeBean.getCounter() + ": " + testoBarcodeBean.getCounter());
			logger.debug("Testo" + testoBarcodeBean.getCounter() + ": " + testoBarcodeBean.getTesto());
			logger.debug("BarCode" + testoBarcodeBean.getCounter() + ": " + testoBarcodeBean.getBarcode());
		}
		PrintUtil.print(lFile, pdfPropertiesBean, parameter);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
