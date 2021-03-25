package it.eng.hybrid.module.scan.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.media.jai.JAI;
import javax.media.jai.NullOpImage;
import javax.media.jai.OpImage;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFEncodeParam;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.hybrid.module.scan.ScanClientModule;
import it.eng.hybrid.module.scan.messages.MessageKeys;
import it.eng.hybrid.module.scan.messages.Messages;
import it.eng.hybrid.module.scan.preferences.PreferenceKeys;
import it.eng.hybrid.module.scan.preferences.PreferenceManager;
import it.eng.hybrid.module.scan.util.TipoScansioneEnum;
import it.eng.proxyselector.frame.ProxyFrame;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.twain.TwainBufferedImage;
import uk.co.mmscomputing.device.twain.TwainConstants;
import uk.co.mmscomputing.device.twain.TwainIOMetadata;
import uk.co.mmscomputing.device.twain.TwainImageInfo;
import uk.co.mmscomputing.device.twain.TwainSource;

public class ScanApplication extends JFrame implements ActionListener, ScannerListener {

	public final static Logger logger = Logger.getLogger(ScanApplication.class);

	private ScanClientModule module;

	private String callBackAskForClose;
	private String callBackRichiestaChiusura;
	private String callBackAnnullaChiusura;

	private String servletUpload = "";

	private String contextPath = "";
	private String callBack = "";
	private String idSelected = "";

	private String risoluzioneDefault;
	private boolean abilitaPDFA;
	private boolean abilitaPannImpEmbedded = false;

	public static boolean DEBUGENABLED = false;

	private String filenameCompl;
	private String filenameComplFinal;

	private Properties scanningInfo;
	private String fileNameTemp;

	private String tempFolder = System.getProperty("user.home");
	private boolean correctExecution;

	private Vector imgX = null;
	private Vector imgY = null;

	private boolean started = false;
	private boolean imageSaved = true;

	public String prefix_nomefile = "scans_temp_";
	public int len_prefix_nomefile = 11;

	public int max_connectiont_imeout = 0; // Disabilitato
	private int numImage = 0;

	private Scanner scanner;
	private ScannerDevice device;

	private final String SELECTSCANNER = "selectScanner";
	private final String STARTSCANNER = "startScanner";
	private final String STOPSCANNER = "stopScanner";
	private final String DELETETEMP = "deleteTemp";
	private final String SHOWTEMP = "showTemp";
	private final String SELECTFOLDER = "selectFolder";
	private final String SELECTRESOLUTION = "selectResolution";

	private JMenu preferenceMenu;
	private JMenu invioLogMenu;
	private JTextArea ta;
	private JScrollPane sp;
	private JLabel tempFolderLabel;
	private JProgressBar aJProgressBar;
	private JButton acquireButton;
	private JButton closeSaveButton;
	private JButton deleteTempButton;
	private JButton showTempButton;

	private JScrollPane jScrollPane = null;
	private JPanel jContentPane = null;
	private JPanel menuPanel = null;
	private JPanel mainPanel = null;

	private boolean errorOnUpload;

	private final String CALLBACK = "callBack";
	private final String CALLBACKASKFORCLOSE = "callBackAskForClose";
	private final String CALLBACKRICHIESTACHIUSURA = "callBackRichiestaChiusura";
	private final String CALLBACKANNULLACHIUSURA = "callBackAnnullaChiusura";

	private int numberTempWindowOpened = 0;

	public ScanApplication(ScanClientModule module, JSONArray parameters) {
		// super(owner);
		this.module = module;

		// JSONObject options = parameters.getJSONObject(0);
		// Iterator optionsItr = options.keys();
		// List<String> optionNames = new ArrayList<String>();
		// while( optionsItr.hasNext() ){
		// optionNames.add((String) optionsItr.next());
		//
		// }
		// Map<String, String> props = new HashMap<String, String>();
		// for(int i=0;i<options.length();i++){
		// props.put(optionNames.get(i),options.getString(optionNames.get(i)));
		// logger.info("Proprieta' " + optionNames.get(i) + "=" + options.getString(optionNames.get(i)));
		// }

		Messages.setBundle(ResourceBundle.getBundle("messages"));

		PreferenceManager.initConfig(parameters);

		try {
			DEBUGENABLED = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_DEBUGENABLED);
			logger.info("Proprieta' " + PreferenceKeys.PROPERTY_DEBUGENABLED + ": " + DEBUGENABLED);
		} catch (Exception e) {
			logger.info("Errore nel recupero della propriet� " + PreferenceKeys.PROPERTY_DEBUGENABLED);
		}

		// Parametri in ingresso
		servletUpload = PreferenceManager.getString("servletUpload") == null ? "" : PreferenceManager.getString("servletUpload");
		logger.info("servletUpload: " + servletUpload);
		contextPath = PreferenceManager.getString("contextPath") == null ? "" : PreferenceManager.getString("contextPath");
		logger.info("contextPath: " + contextPath);

		idSelected = PreferenceManager.getString("idSelected") == null ? "" : PreferenceManager.getString("idSelected");

		callBack = PreferenceManager.getString(CALLBACK) == null ? "" : PreferenceManager.getString(CALLBACK);
		logger.info("callBack: " + callBack);
		callBackAskForClose = PreferenceManager.getString(CALLBACKASKFORCLOSE) == null ? "" : PreferenceManager.getString(CALLBACKASKFORCLOSE);
		logger.info("callBackAskForClose: " + callBackAskForClose);
		callBackRichiestaChiusura = PreferenceManager.getString(CALLBACKRICHIESTACHIUSURA) == null ? ""
				: PreferenceManager.getString(CALLBACKRICHIESTACHIUSURA);
		logger.info("callBackRichiestaChiusura: " + callBackRichiestaChiusura);
		callBackAnnullaChiusura = PreferenceManager.getString(CALLBACKANNULLACHIUSURA) == null ? "" : PreferenceManager.getString(CALLBACKANNULLACHIUSURA);
		logger.info("callBackAnnullaChiusura: " + callBackAnnullaChiusura);

		risoluzioneDefault = PreferenceManager.getString("risoluzioneScanner") == null ? "200" : PreferenceManager.getString("risoluzioneScanner");
		logger.info("Risoluzione default: " + risoluzioneDefault);
		abilitaPDFA = PreferenceManager.getString("abilitaPdfA") == null ? false : PreferenceManager.getBoolean("abilitaPdfA");
		logger.info("PDFA abilitato: " + abilitaPDFA);
		abilitaPannImpEmbedded = PreferenceManager.getString("abilitaPannImpEmbedded") == null ? false : PreferenceManager.getBoolean("abilitaPannImpEmbedded");
		logger.info("Pannello impostazioni embedded abilitato: " + abilitaPannImpEmbedded);
		try {
			PreferenceManager.saveProp(PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_DEFAULT, risoluzioneDefault);
			PreferenceManager.reinitConfig();
			logger.info("Risoluzione di default impostata a " + risoluzioneDefault + " dpi");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String tempFolder = PreferenceManager.getString(PreferenceKeys.PROPERTY_TEMPFOLDER);
		if (tempFolder == null || tempFolder.trim().equals("")) {
			tempFolder = System.getProperty("user.home");
		}
		logger.info("Proprieta' " + PreferenceKeys.PROPERTY_TEMPFOLDER + ": " + tempFolder);

		// Leggo il parametro nel file di configurazione che controlla e il programma era stato chiuso in modo corretto in precedenza
		String correctExecutionString = PreferenceManager.getString(PreferenceKeys.PROPERTY_CORRECTEXECUTION);

		if (correctExecutionString == null || correctExecutionString.trim().equals("")) {
			correctExecution = false; // Non presente il parametro e quindi avvio in modalit� sicura
		} else {
			// Prelevo il valore memorizzato nel file di configurazione
			correctExecution = Boolean.valueOf(correctExecutionString);
		}
		logger.info("L'esecuzione precedente e' andata a buon fine? " + correctExecution);

		fileNameTemp = tempFolder + File.separator + prefix_nomefile + getRandomFileNameNoExt();
		logger.info("Nome file temporaneo: " + fileNameTemp);

		scanningInfo = new Properties();

		imgX = new Vector();
		imgY = new Vector();

		// Verifica sulla correttezza del contextPath passato
		try {
			new URL(contextPath);
		} catch (MalformedURLException e) {
			logger.info("Il parametro 'contextPath' " + contextPath + " non e' corretto!", e);
		}

		int mainPanelWidth = PreferenceManager.getInt(PreferenceKeys.MAINPANEL_WIDTH, 400);
		int mainPanelHeight = PreferenceManager.getInt(PreferenceKeys.MAINPANEL_HEIGHT, 400);
		logger.info("mainPanelWidth=" + mainPanelWidth + " mainPanelHeight=" + mainPanelHeight);
		this.setSize(mainPanelWidth, mainPanelHeight);

		setContentPane(getJContentPane());
		tempFolderLabel.setText(Messages.getMessage(MessageKeys.MSG_TEMPFOLDER) + ": " + tempFolder);

		// pingIsClosed();

		// Se c'e' uno scanner di default, parte automaticamente
		try {
			scanner = Scanner.getDevice();

			if (scanner != null) {
				scanner.addListener(this);
			}

			logger.info("Scanner: " + scanner);
			logger.info("Le API del device sono installate?: " + scanner.isAPIInstalled());

			if (correctExecution) {
				// Allora l'esecuzione precedente � andata a buon fine e quindi si riavvia con la config precedente
				/*
				 * Imposto il flag che indica la corretta esecuzione a false in modo tale che, se non viene modificato alla fine si � a conoscenza di un errore
				 * o crash
				 */
				PreferenceManager.saveProp(PreferenceKeys.PROPERTY_CORRECTEXECUTION, false);

				if (scanner != null) {
					logger.info("Avvio scansione in corso, attendere....." + scanner.getSelectedDeviceName());
					ta.append("Avvio scansione in corso, attendere.....\n");

					scanner.acquire();
					logger.info("Dopo l'acquire iniziale");
				}

			} else {
				// L'esecuzione precedente non � andata a buon fine quindi non avvio automaticamente la scansione
				ta.append("La precedente acquisizione non e' andata a buon fine.\nSelezionare lo scanner da utilizzare\n");
			}
		} catch (Exception ex) {
			logger.info("ERRORE - " + ex.getMessage());
			ex.printStackTrace();
		}

	}

	private JScrollPane getJContentPane() {
		if (jContentPane == null) {
			GridBagLayout gridLayout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();

			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jScrollPane = new JScrollPane(jContentPane);

			c.gridy = 0;
			c.gridx = 0;
			c.anchor = GridBagConstraints.LINE_START;
			c.gridx = 0;
			c.weightx = 1.0;
			c.insets = new Insets(2, 10, 2, 10);
			JPanel menu = getMenuPanel();
			jContentPane.add(menu, c);

			c.gridy = 1;
			c.insets = new Insets(2, 10, 2, 10);
			c.anchor = GridBagConstraints.LINE_START;
			JPanel main = getMainPanel();
			jContentPane.add(main, c);
		}
		return jScrollPane;
	}

	private JPanel getMenuPanel() {
		if (menuPanel == null) {
			menuPanel = new JPanel();
			JMenuBar menuBar = new JMenuBar();
			preferenceMenu = new JMenu(Messages.getMessage(MessageKeys.MENU_PREFERENCE_TITLE));

			JMenuItem selezionaScanner = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTSCANNER_TITLE));
			selezionaScanner.setToolTipText(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTSCANNER_TOOLTIP));
			selezionaScanner.setActionCommand(SELECTSCANNER);
			selezionaScanner.addActionListener(this);
			preferenceMenu.add(selezionaScanner);

			if (abilitaPannImpEmbedded) {
				JMenuItem selezionaRisoluzione = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTRESOLUTION_TITLE));
				selezionaRisoluzione.setToolTipText(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTRESOLUTION_TOOLTIP));
				selezionaRisoluzione.setActionCommand(SELECTRESOLUTION);
				selezionaRisoluzione.addActionListener(this);
				preferenceMenu.add(selezionaRisoluzione);
			}

			JMenuItem tempFolder = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTFOLDER_TITLE));
			tempFolder.setActionCommand(SELECTFOLDER);
			tempFolder.setToolTipText(Messages.getMessage(MessageKeys.MENU_PREFERENCE_SELECTFOLDER_TOOLTIP));
			tempFolder.addActionListener(this);
			preferenceMenu.add(tempFolder);

			JMenuItem log = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_LOG_TITLE));
			log.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int preferenceInvioLogPanelWidth = PreferenceManager.getInt(PreferenceKeys.PREFERENCEINVIOLOGPANEL_WIDTH, 400);
					int preferenceInvioLogPanelHeight = PreferenceManager.getInt(PreferenceKeys.PREFERENCEINVIOLOGPANEL_HEIGHT, 400);
					new PanelPreferenceLog(preferenceInvioLogPanelWidth, preferenceInvioLogPanelHeight).show();
				}
			});
			preferenceMenu.add(log);

			JMenuItem rete = new JMenuItem(Messages.getMessage(MessageKeys.MENU_PREFERENCE_RETE_TITLE));
			rete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String testUrl = PreferenceManager.getString("testUrl");
					new ProxyFrame(testUrl).setVisible(true);
				}
			});
			preferenceMenu.add(rete);
			menuBar.add(preferenceMenu);

			invioLogMenu = new JMenu(Messages.getMessage(MessageKeys.MENU_INVIOLOG_TITLE));
			JMenuItem invioLog = new JMenuItem(Messages.getMessage(MessageKeys.MENU_INVIOLOG_TITLE));
			invioLog.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int invioLogPanelWidth = PreferenceManager.getInt(PreferenceKeys.INVIOLOGPANEL_WIDTH, 400);
					int invioLogPanelHeight = PreferenceManager.getInt(PreferenceKeys.INVIOLOGPANEL_HEIGHT, 400);
					new PanelLog(invioLogPanelWidth, invioLogPanelHeight).show();
				}
			});
			invioLogMenu.add(invioLog);
			menuBar.add(invioLogMenu);

			menuPanel.add(menuBar);
		}
		return menuPanel;
	}

	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(5, 0, 0, 0);
			c.gridy = 0;
			mainPanel.add(getButtonPanel(), c);

			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 10, 0, 0);
			c.gridy = 1;
			aJProgressBar = new JProgressBar();
			aJProgressBar.setStringPainted(false);
			aJProgressBar.setIndeterminate(true);
			aJProgressBar.setVisible(false);
			aJProgressBar.setOpaque(true);
			aJProgressBar.setForeground(Color.GREEN);
			Border border = BorderFactory.createTitledBorder("Salvataggio in corso...");
			aJProgressBar.setBorder(border);
			// mainPanel.add(aJProgressBar, c);

			c.gridy = 2;
			ta = new JTextArea(11, 40);
			ta.setVisible(true);
			ta.setForeground(Color.BLACK);
			ta.setBackground(Color.WHITE);
			sp = new JScrollPane(ta);
			// Altrimenti la finestra si ridimensiona quando compare la scrollbar
			sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			mainPanel.add(sp, c);

			c.anchor = GridBagConstraints.LAST_LINE_START;
			c.insets = new Insets(10, 10, 0, 0);
			c.gridy = 3;
			tempFolderLabel = new JLabel();
			mainPanel.add(tempFolderLabel, c);

		}
		return mainPanel;
	}

	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();

		URL imageScannerURL = getClass().getResource("scanner.png");
		ImageIcon imageScanner = new ImageIcon(imageScannerURL);
		acquireButton = new JButton(imageScanner);
		acquireButton.addActionListener(this);
		acquireButton.setActionCommand(STARTSCANNER);
		if (!correctExecution) {
			acquireButton.setEnabled(false);
		}
		acquireButton.setToolTipText(Messages.getMessage(MessageKeys.BUTTON_STARTSCANNER_TOOLTIP));
		buttonPanel.add(acquireButton);

		URL imageStopScannerURL = getClass().getResource("ok.png");
		ImageIcon imageStopScanner = new ImageIcon(imageStopScannerURL);
		closeSaveButton = new JButton(imageStopScanner);
		closeSaveButton.addActionListener(this);
		closeSaveButton.setActionCommand(STOPSCANNER);
		closeSaveButton.setEnabled(false);
		closeSaveButton.setToolTipText(Messages.getMessage(MessageKeys.BUTTON_STOPSCANNER_TOOLTIP));
		buttonPanel.add(closeSaveButton);

		URL imageDeleteTempURL = getClass().getResource("close.png");
		ImageIcon imageDeleteTemp = new ImageIcon(imageDeleteTempURL);
		deleteTempButton = new JButton(imageDeleteTemp);
		deleteTempButton.addActionListener(this);
		deleteTempButton.setActionCommand(DELETETEMP);
		deleteTempButton.setEnabled(false);
		deleteTempButton.setToolTipText(Messages.getMessage(MessageKeys.BUTTON_DELETETEMP_TOOLTIP));
		buttonPanel.add(deleteTempButton);

		URL imageShowTempURL = getClass().getResource("show.png");
		ImageIcon imageShowTemp = new ImageIcon(imageShowTempURL);
		java.awt.Image image = imageShowTemp.getImage(); // transform it
		java.awt.Image newimg = image.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageShowTemp = new ImageIcon(newimg);
		showTempButton = new JButton(imageShowTemp);
		showTempButton.addActionListener(this);
		showTempButton.setActionCommand(SHOWTEMP);
		showTempButton.setEnabled(false);
		showTempButton.setToolTipText(Messages.getMessage(MessageKeys.BUTTON_SHOWTEMP_TOOLTIP));
		buttonPanel.add(showTempButton);

		return buttonPanel;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {

		String command = evt.getActionCommand();

		if (command.equalsIgnoreCase(STARTSCANNER)) {
			logger.info("Le API del device sono installate?: " + scanner.isAPIInstalled());
			try {
				// Impostazione del flag a false per controllare l'avvenuta acquisizione correttamente
				PreferenceManager.saveProp(PreferenceKeys.PROPERTY_CORRECTEXECUTION, false);
				logger.info("Avvio scansione in corso, attendere.....");
				ta.append("Avvio scansione in corso, attendere.....\n");
				scanner.acquire();
				logger.info("Dopo l'acquire cliccato");
			} catch (Exception e) {
				logger.info("error in acquire: " + e.getMessage());
				ta.append("Un'acquisizione e' gia' in corso\n");
			}

			// Eseguo il repaint del frame
			ScanApplication.this.invalidate();
			ScanApplication.this.validate();
			ScanApplication.this.repaint();

		} else if (command.equalsIgnoreCase(SELECTSCANNER)) {
			try {
				scanner.select();
				acquireButton.setEnabled(true);
			} catch (Exception e) {
				logger.info("error in select: " + e.getMessage());
				ta.append("Un'acquisizione e' gia' in corso: \nper selezionare un nuovo dispositivo chiudere la schermata precedente.\n");
			}
		} else if (command.equalsIgnoreCase(DELETETEMP)) {
			int result = JOptionPane.showConfirmDialog(this, Messages.getMessage(MessageKeys.MSG_CONFIRM_DELETE_FILES), "", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				deleteAllTempFile();
				closeSaveButton.setEnabled(false);
				showTempButton.setEnabled(false);
				numImage = 0;
			}
		} else if (command.equalsIgnoreCase(STOPSCANNER)) {
			Worker lWorker = new Worker();
			lWorker.start();
			try {
				logger.info("Aspetto che finisca");
				lWorker.join();
			} catch (InterruptedException e) {

			}
			manageExit();
		} else if (command.equalsIgnoreCase(SELECTFOLDER)) {
			JFileChooser chooser = new JFileChooser();

			if (tempFolder != null) {
				chooser.setCurrentDirectory(new java.io.File(tempFolder));
			} else {
				chooser.setCurrentDirectory(new java.io.File("."));
			}

			chooser.setDialogTitle(Messages.getMessage(MessageKeys.MSG_TEMPFOLDER));
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.addActionListener(this);
			chooser.setAcceptAllFileFilterUsed(false);
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				tempFolder = chooser.getSelectedFile().getAbsolutePath();
				// updateScannerSettings("tempFolder", tempFolder);

				try {
					PreferenceManager.saveProp(PreferenceKeys.PROPERTY_TEMPFOLDER, tempFolder);
					PreferenceManager.reinitConfig();
				} catch (Exception e) {
					e.printStackTrace();
				}

				tempFolderLabel.setText(Messages.getMessage(MessageKeys.MSG_TEMPFOLDER) + ": " + tempFolder);
			} else {
				logger.info("No Selection ");
			}
		} else if (command.equalsIgnoreCase(SHOWTEMP)) {

			if (numberTempWindowOpened == 0) {
				numberTempWindowOpened++;
				ArrayList<String> fileTemp = new ArrayList<String>();

				// Inserisco nella lista i vari nominativi delle immagini
				for (int indexPage = 0; indexPage < numImage; indexPage++) {

					fileTemp.add(getFilenameTemp(indexPage));
				}

				final ImagePreviewFrame lImagePreviewFrame = new ImagePreviewFrame(fileTemp, 500, 687, 0);
				lImagePreviewFrame.addWindowListener(new WindowAdapter() {

					public void windowClosing(WindowEvent e) {
						lImagePreviewFrame.forcedClose();
						logger.info("Chiudo la finestra");
						numberTempWindowOpened--;
					}
				});

				try {
					lImagePreviewFrame.showImage();
				} catch (IOException e) {
					logger.info("Errore nella lettura dell'immagine " + fileTemp + ": " + e.getMessage(), e);
				}
			} else {
				ta.append("C'e' gia' una finestra di anteprima aperta\n");
			}
		} else if (command.equalsIgnoreCase(SELECTRESOLUTION)) {
			new PanelResolution(400, 200).show(null);
		}
	}

	private void deleteAllTempFile() {
		String tempFolder = PreferenceManager.getString(PreferenceKeys.PROPERTY_TEMPFOLDER);
		if (tempFolder == null || tempFolder.trim().equals("")) {
			tempFolder = System.getProperty("user.home");
		}
		logger.info("Propriet� " + PreferenceKeys.PROPERTY_TEMPFOLDER + ": " + tempFolder);
		File dirtempFolder = new File(tempFolder);

		if (dirtempFolder.exists()) {

			logger.info("Inizio cancellazione file temporanei nella cartella   " + tempFolder);
			ta.append("Inizio cancellazione file temporanei nella cartella   " + tempFolder + "\n");

			File[] files = dirtempFolder.listFiles();

			for (int i = 0; i < files.length; i++) {

				String NomeFile = files[i].getName();

				// write("File trovato nella cartella : " + NomeFile , true);

				if (!files[i].isDirectory() && (NomeFile.length() > 10)) {
					if (NomeFile.substring(0, len_prefix_nomefile).equals(prefix_nomefile)) {

						// logger.info("Inizio Cancellazione file " + NomeFile, ta );

						if (files[i].delete()) {
							// delete file OK
							logger.info("Cancellazione file " + NomeFile + " eseguita con successo! ");
							ta.append("Cancellazione file " + NomeFile + " eseguita con successo! \n");
						} else {
							// Failed to delete file
							logger.info("Cancellazione file " + NomeFile + " FALLITA !! ");
							ta.append("Cancellazione file " + NomeFile + " FALLITA !! \n");
						}
					}
					// else
					// {
					// write("File da non cancellare : " + NomeFile , true);
					// }
				}
			}
		} else {
			logger.info("Cartella temporanea inesistente : " + tempFolder);
		}

	}

	// TODO INUTILE?
	// public void pingIsClosed() {
	// logger.info("comincio il ping verso l'applicativo");
	// // callBackIsClosed
	//
	// // TODO COMMENTATO
	// // Timer lTimer = new Timer();
	// // lTimer.schedule(new PingTimer(), 0, 500);
	//
	// }
	//
	// class PingTimer extends TimerTask {
	//
	// public PingTimer() {
	// }
	//
	// @Override
	// public void run() {
	// logger.info("faccio il ping");
	// if (callBackRichiestaChiusura != null && !callBackRichiestaChiusura.trim().equals("")) {
	// logger.info("Chiamo " + callBackRichiestaChiusura);
	//
	// Boolean richiestaChiusura = false;
	// try {
	// // JSObject window = JSObject.getWindow(this);
	// // richiestaChiusura = (Boolean) window.eval(callBackRichiestaChiusura + "()");
	// logger.info("risultato " + richiestaChiusura);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// if (richiestaChiusura) {
	// logger.info("ImageSaved settata a " + imageSaved);
	// if (!imageSaved) {
	// logger.info("Hai richiesto la chiusura ma l'immagine non e' salvata");
	// cancel();
	// askForClose();
	// } else {
	// cancel();
	// String lStrToInvoke = "javascript:" + callBackAskForClose + "();";
	// logger.info("Invoco " + lStrToInvoke);
	// if (callBackAskForClose != null && !callBackAskForClose.equals("")) {
	// module.setCallBackChiusura(callBackAskForClose);
	// // try {
	// // getAppletContext().showDocument(new URL(lStrToInvoke));
	// // } catch (MalformedURLException me) {
	// // StringWriter writer = new StringWriter();
	// // PrintWriter out = new PrintWriter(writer);
	// // me.printStackTrace(out);
	// // logger.info("errore : " + writer.toString());
	// // }
	// }
	// }
	// }
	// }
	// }
	//
	// }

	private String getRandomFileNameNoExt() {
		String filename = "abcde12345";

		try {
			Calendar cal = new GregorianCalendar();
			String anno = String.valueOf(cal.get(Calendar.YEAR));
			String mese = String.valueOf(cal.get(Calendar.MONTH) + 1);

			if (mese.length() < 2) {
				mese = "0" + mese;
			}

			String giorno = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

			if (giorno.length() < 2) {
				giorno = "0" + giorno;
			}

			String ore = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));

			if (ore.length() < 2) {
				ore = "0" + ore;
			}

			String minuti = String.valueOf(cal.get(Calendar.MINUTE));

			if (minuti.length() < 2) {
				minuti = "0" + minuti;
			}

			String secondi = String.valueOf(cal.get(Calendar.SECOND));

			if (secondi.length() < 2) {
				secondi = "0" + secondi;
			}

			filename = anno + mese + giorno + ore + minuti + secondi + "_" + new Random().nextInt(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filename;
	}

	private String getFilenameTemp(int numImage) {

		return (fileNameTemp + "_" + numImage + ".tif");
	}

	private void manageExit() {
		logger.info("Entro in manageExit");
		stop(); // Controllo che lo scanner abbia terminato

		/*
		 * Rimuoviamo il listener dello scanner e inizializziamo l'oggetto a null in modo tale
		 * che, al successivo riavvio del modulo, la connessione sia stata eliminata e non dia
		 * problemi di stato non corretto.
		 * Senza queste istruzioni non riuscir� ad avviare alla seconda volta il programma di gestione dello scanner
		 */
		scanner.removeListener(this);
		scanner = null;

		if (!errorOnUpload) {
			String lStrToInvoke = "javascript:" + callBackAskForClose + "();";
			logger.info("Invoco " + lStrToInvoke);
			if (callBackAskForClose != null && !callBackAskForClose.equals("")) {
				// try {
				// getAppletContext().showDocument(new URL(lStrToInvoke));
				// } catch (MalformedURLException me) {
				// StringWriter writer = new StringWriter();
				// PrintWriter out = new PrintWriter(writer);
				// me.printStackTrace(out);
				// logger.info("errore : " + writer.toString());
				// } catch (Throwable me) {
				// StringWriter writer = new StringWriter();
				// PrintWriter out = new PrintWriter(writer);
				// me.printStackTrace(out);
				// logger.info("errore : " + writer.toString());
				// }
				logger.info("Invoco il dispose in manageExit");

				module.setCallBackChiusura(callBackAskForClose);

				this.dispose();
			}
		}

	}

	public void forcedClose() {
		if (!imageSaved) {
			askForClose();
		} else {
			module.setCallBackCancel("callBackCancel");

			logger.info("Entro in forcedClose");

			logger.info("Invoco il dispose in forcedClose");
			this.dispose();
		}
	}

	public void stop() { // execute before System.exit
		if (scanner != null) { // make sure user waits for scanner to finish!
			scanner.waitToExit();
		}
	}

	private class Worker extends Thread {

		public void run() {
			startProgressBar();
			saveFile();
			stopProgressBar();
		}
	}

	private void startProgressBar() {
		closeSaveButton.setEnabled(false);
		preferenceMenu.setEnabled(false);
		invioLogMenu.setEnabled(false);
		aJProgressBar.setVisible(true);
	}

	private void stopProgressBar() {
		aJProgressBar.setVisible(false);
		preferenceMenu.setEnabled(true);
		invioLogMenu.setEnabled(true);
	}

	public boolean showConfirmOnClose() {
		logger.info("showConfirmOnClose.  imageSaved " + imageSaved);
		logger.info("ImageSaved vale " + imageSaved);
		return imageSaved;
	}

	public void askForClose() {

		logger.info("Entro in askForClose");

		int value = JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(this),
				"Nessuna immagine e' stata salvata sull'applicazione. Chiudendo perderai l'eventuale immagine acquisita. Chiudere?", "Attenzione",
				JOptionPane.YES_NO_OPTION);
		if (value == 0) {
			// Chiudo
			String lStrToInvoke = "javascript:" + callBackAskForClose + "();";
			logger.info("Invoco " + lStrToInvoke);
			if (callBackAskForClose != null && !callBackAskForClose.equals(""))

				this.dispose(); // Aggiunto per la chiusura della finestra e il rilascio delle risorse

			// try {
			// getAppletContext().showDocument(new URL(lStrToInvoke));
			// } catch (MalformedURLException me) {
			// StringWriter writer = new StringWriter();
			// PrintWriter out = new PrintWriter(writer);
			// me.printStackTrace(out);
			// logger.info("errore : " + writer.toString());
			// }
			logger.info("Setto il callBackAskForClose in askForClose");
			module.setCallBackChiusura(callBackAskForClose);
		}
		// TODO CANCELLARE
		// else {
		// // JSObject window = JSObject.getWindow(this);
		// // Boolean annulla = (Boolean) window.eval(callBackAnnullaChiusura + "()");
		// // Boolean annulla = (Boolean) window.call(callBackAnnullaChiusura, new Object[]{});
		// // logger.info("annulla : " + annulla);
		// // module.setCallBackChiusura(callBackAnnullaChiusura);
		// pingIsClosed();
		// }
	}

	private void saveFile() {
		float dimImgX = 0;
		float dimImgY = 0;

		logger.info("Salvataggio file...");
		acquireButton.setEnabled(false);
		deleteTempButton.setEnabled(false);

		Document document = new Document();
		document.addAuthor("Author");
		document.addSubject("Subject");
		document.addLanguage("nl-nl");
		document.addCreationDate();

		int pages = 0;
		String fileTemp = null;

		try {
			// Salvataggio del file
			if (numImage > 0) {
				filenameCompl = fileNameTemp + ".pdf";
				logger.info("Salvataggio file " + filenameCompl);
				ta.append("Salvataggio file " + filenameCompl + "\n");

				Image img = null;

				try {
					PdfContentByte cb = null;
					if (abilitaPDFA) {
						PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream(filenameCompl), PdfAConformanceLevel.PDF_A_1B);
						writer.setTagged();
						writer.createXmpMetadata();
						writer.setCompressionLevel(9);
						dimImgX = inchToPoints(imgX.get(0));
						dimImgY = inchToPoints(imgY.get(0));
						document.setPageSize(new Rectangle(dimImgX, dimImgY));
						document.open();
						cb = writer.getDirectContent();
					} else {
						PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filenameCompl));
						dimImgX = inchToPoints(imgX.get(0));
						dimImgY = inchToPoints(imgY.get(0));
						document.setPageSize(new Rectangle(dimImgX, dimImgY));
						document.open();
						cb = writer.getDirectContent();
					}

					scanningInfo.setProperty("NUMPAGES", String.valueOf(numImage));

					for (int k = 0; k < numImage; k++) {

						fileTemp = getFilenameTemp(k);
						img = Image.getInstance(fileTemp);

						if (img != null) {
							if (img.getScaledWidth() > dimImgX || img.getScaledHeight() > dimImgY) {
								img.scaleToFit(dimImgX, dimImgY);
							}

							img.setAbsolutePosition(0, 0);

							try {
								scanningInfo.setProperty("IMG." + k + ".H", "" + img.getHeight());
								scanningInfo.setProperty("IMG." + k + ".W", "" + img.getWidth());
							} catch (Throwable e) {
							}

							cb.addImage(img);
							pages++;

							if (k < numImage - 1) {
								dimImgX = inchToPoints(imgX.get(k + 1));
								dimImgY = inchToPoints(imgY.get(k + 1));
								document.setPageSize(new Rectangle(inchToPoints(imgX.get(k + 1)), inchToPoints(imgY.get(k + 1))));
								document.newPage();
							}
						}
					}

					if (document.isOpen() && (pages > 0)) {
						document.close();
					}

					// converto i file TIFF in BufferedImage
					BufferedImage image[] = new BufferedImage[numImage];

					for (int i = 0; i < numImage; i++) {
						String fileTempTiff = getFilenameTemp(i);
						SeekableStream ss = new FileSeekableStream(fileTempTiff);

						ImageDecoder decoder = ImageCodec.createImageDecoder("tiff", ss, null);
						PlanarImage op = new NullOpImage(decoder.decodeAsRenderedImage(0), null, OpImage.OP_IO_BOUND, null);
						image[i] = op.getAsBufferedImage();
						ss.close();
					}

					// Converto il BufferedImage array nel multiple TIFF image
					filenameComplFinal = (fileNameTemp + ".tif");

					TIFFEncodeParam param = new TIFFEncodeParam();
					OutputStream out = new FileOutputStream(filenameComplFinal);
					ImageEncoder encoder = ImageCodec.createImageEncoder("tiff", out, param);

					Vector vector = new Vector();

					for (int k = 1; k < image.length; k++) {
						vector.add(image[k]);
					}

					// Carico i file successivi al primo
					param.setExtraImages(vector.iterator());

					// Creo il multiple TIFF image
					encoder.encode(image[0]);
					out.close();

				} catch (Exception ex) {
					logger.info("ERRORE=" + ex.getMessage());

					throw ex;
				} finally {
					if (document.isOpen() && (pages > 0)) {
						document.close();
					}

					img = null;
				}
				logger.info("Trasferimento del file sul server...");
				ta.append("Trasferimento del file sul server...\n");

				// Trasferimento del file sul server
				String url = (contextPath.endsWith("/") ? contextPath : contextPath + "/")
						+ (servletUpload.startsWith("/") ? servletUpload.substring(1) : servletUpload);
				logger.info("Inizio invio file a " + contextPath + servletUpload);
				upload(url, filenameCompl);
				logger.info("Dopo upload");
			} else {
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			logger.info("ERRORE - " + e.getMessage());
			e.printStackTrace();
		}
		if (!errorOnUpload) {
			imageSaved = true;
			logger.info("ImageSaved settata a true");
			deleteAllTempFile();
			started = false;
			closeSaveButton.setEnabled(false);
			acquireButton.setEnabled(false);
			deleteTempButton.setEnabled(false);
			numImage = 0;
			logger.info("Fine salvataggio. Trasferimento del file sul server completato");
			ta.append("Fine salvataggio. Trasferimento del file sul server completato\n");
		} else {
			imageSaved = false;
			closeSaveButton.setEnabled(true);
			acquireButton.setEnabled(true);
			deleteTempButton.setEnabled(true);
			logger.info("Errore nel trasferimento del file sul server");
			ta.append("Errore nel trasferimento del file sul server\n");
		}

		// logger.info("Fine salvataggio.", ta);
	}

	private float inchToPoints(Object dim) {
		Double dblDim = new Double(((Double) dim).doubleValue() * 72);

		return dblDim.floatValue();
	}

	private void getInfoImage(ScannerIOMetadata metadata) {
		logger.info("Entro in getInfoImage");
		int risX = 0;
		int risY = 0;
		int widX = 0;
		int heiY = 0;

		if (metadata instanceof TwainIOMetadata) {
			logger.info("Entro in if (metadata instanceof TwainIOMetadata)");
			try {
				((TwainIOMetadata) metadata).getSource();

				risX = Integer.parseInt("" + ((TwainBufferedImage) metadata.getImage()).getProperty("resolution"));
				risY = Integer.parseInt("" + ((TwainBufferedImage) metadata.getImage()).getProperty("resolution"));
				widX = metadata.getImage().getWidth();
				heiY = metadata.getImage().getHeight();

				logger.info("risX: " + risX);
				logger.info("risY: " + risY);

				imgX.add(new Double((new Double(widX)).doubleValue() / (new Double(risX)).doubleValue()));
				imgY.add(new Double((new Double(heiY)).doubleValue() / (new Double(risY)).doubleValue()));
			} catch (Exception e) {
				logger.info("Exception: " + e.getMessage());
			}
		}
	}

	@Override
	public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {
		logger.info("Update - type " + type.toString());

		logger.info("metadata: " + metadata);
		// logger.info("((TwainIOMetadata) metadata).getSource(): " + ((TwainIOMetadata) metadata).getSource());
		//
		// TwainSource source = ((TwainIOMetadata) metadata).getSource();

		getTypeEnum(type, metadata);
		// Man mano che acquisisco le immagini, le aggiungo al vettore vImages
		if (type.equals(ScannerIOMetadata.ACQUIRED)) {
			try {
				logger.info("Faccio l'update per operation ACQUIRED");

				getInfoImage(metadata);
				BufferedImage image = metadata.getImage();
				if (abilitaPannImpEmbedded) {
					// Controllo se nelle impostazioni ho settato l'immagine in bianco e nero
					String scannerMode = PreferenceManager.getString(PreferenceKeys.PROPERTY_SCANNER_TIPOSCANSIONE_USER);
					if ((scannerMode != null) && (scannerMode.equalsIgnoreCase(TipoScansioneEnum.BLACKWHITE.getTipoScansioneCode()))) {
						logger.info("L'immagine viene salvata in bianco e nero");
						// Converto l'immagine in bianco e nero
						BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
						Graphics g = result.getGraphics();
						g.drawImage(image, 0, 0, null);
						g.dispose();
						image = result;
					}
				}
				
				createFileTemp(image, numImage);
				numImage++;

				logger.info("Aggiunta immagine N." + numImage);
				ta.append("Aggiunta immagine N." + numImage + "\n");

				closeSaveButton.setEnabled(true);
				deleteTempButton.setEnabled(true);
				showTempButton.setEnabled(true);
				//Arrivati a questo punto, l'interfaccia grafica dell'applicazione di scansione deve essere chiusa se il flag relativo permette il suo utilizzo
				if (!abilitaPannImpEmbedded) {
					device.setShowUserInterface(false);
				}
				imageSaved = false;
				// salva su file le properties
				String resolution_string = (String) ((TwainBufferedImage) metadata.getImage()).getProperty("resolution");
				// updateScannerSettings("resolution", resolution_string);
				PreferenceManager.saveProp(PreferenceKeys.PROPERTY_RESOLUTION, resolution_string);
				PreferenceManager.reinitConfig();
				scanningInfo.setProperty("IMG." + (numImage - 1) + ".RESOL", resolution_string);
				logger.info("resolution: " + resolution_string);
			} catch (Exception ex) {
				logger.info("Impossibile aggiungere immagine N." + numImage + ": " + ex.getMessage());
				ta.append("Impossibile aggiungere immagine N." + numImage + ": " + ex.getMessage() + "\n");
			}

		} else if (metadata.isState(TwainConstants.STATE_TRANSFERREADY)) {
			logger.info("Faccio l'update per operation STATE_TRANSFERREADY");
			try {
				TwainSource source;
				TwainImageInfo imageInfo = null;
				try {
					// get pixelType
					source = ((TwainIOMetadata) metadata).getSource();
					imageInfo = new TwainImageInfo(source);
				} catch (Throwable t) {
					logger.info("Throwable error: " + t.getMessage());
				}

				imageInfo.get();
				System.out.println("pixel type = " + imageInfo.getPixelType());
				System.out.println("image info: " + imageInfo.toString());
				String pixelType_string = "" + imageInfo.getPixelType();
				// updateScannerSettings("pixelType", pixelType_string);
				PreferenceManager.saveProp(PreferenceKeys.PROPERTY_PIXELTYPE, pixelType_string);
				PreferenceManager.reinitConfig();

				scanningInfo.setProperty("IMG." + numImage + ".DPIX", "" + imageInfo.getXResolution());
				scanningInfo.setProperty("IMG." + numImage + ".DPIY", "" + imageInfo.getYResolution());
				scanningInfo.setProperty("IMG." + numImage + ".W", "" + imageInfo.getWidth());
				scanningInfo.setProperty("IMG." + numImage + ".H", "" + imageInfo.getHeight());

			} catch (Exception e) {
				System.out.println("3\b" + getClass().getName() + ".update:\n\tCannot retrieve image information.\n\t" + e);
			}
		} else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
			logger.info("Faccio l'update per operation NEGOTIATE");
			try {
				device = metadata.getDevice();
				if (abilitaPannImpEmbedded) {
					device.setShowUserInterface(false);
					device.setShowProgressBar(true);
					String resolution_string = PreferenceManager.getString(PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_USER);
					logger.info("Risoluzione impostata dall'utente: " + resolution_string);
					if (resolution_string != null) {
						device.setResolution(Double.parseDouble(resolution_string));
						logger.info("Risoluzione settata a " + resolution_string + " dpi");
					} else {
						logger.info("Uso la risoluzione di default");
						Double defaultResolution = PreferenceManager.getString(PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_DEFAULT) == null ? 200
								: Double.parseDouble(PreferenceManager.getString((PreferenceKeys.PROPERTY_SCANNER_RESOLUTION_DEFAULT)));
						device.setResolution(defaultResolution);
						logger.info("Risoluzione settata a " + defaultResolution + " dpi");
					}
				} else {
					device.setShowUserInterface(true); // deve essere mostrata l'interfaccia dell'applicazione di scansione
					String resolution_string = PreferenceManager.getString(PreferenceKeys.PROPERTY_RESOLUTION);
					if (resolution_string != null) {
						device.setResolution(Double.parseDouble(resolution_string));
					}
				}
				String pixelType_string = PreferenceManager.getString(PreferenceKeys.PROPERTY_PIXELTYPE);
				// if (pixelType_string != null) {
				// System.out.println("state: " + source.getState());
				// source.setCapability(TwainConstants.ICAP_PIXELTYPE, Integer.parseInt(pixelType_string));
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
			logger.info("Faccio l'update per operation EXCEPTION");
			ta.append("Errore generico di comunicazione con il dispositivo\n");
			metadata.getException().printStackTrace();
		} else if (type.equals(ScannerIOMetadata.INFO)) {
			logger.info("Faccio l'update per operation INFO");
			logger.info("INFO=" + metadata.getInfo());
		} else {
			logger.info("Nessuna condizione if rispettata da type: " + type.toString());

		}

		if (!started) {
			started = true;
			logger.info("ImageSaved settata a false");
		}
		// acquireButton.setEnabled( false );

	}

	public void getTypeEnum(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {
		if (type.equals(ScannerIOMetadata.ACQUIRED)) {
			logger.info("type � di tipo ACQUIRE");
		} else if (metadata.isState(TwainConstants.STATE_TRANSFERREADY)) {
			logger.info("metadata � di tipo STATE_TRANSFERREADY");
		} else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
			logger.info("type � di tipo NEGOTIATE");
		} else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
			logger.info("type � di tipo EXCEPTION");
		} else if (type.equals(ScannerIOMetadata.INFO)) {
			logger.info("type � di tipo INFO");
		} else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
			logger.info("type � di tipo NEGOTIATE");
		}
	}

	private String createFileTemp(RenderedImage riImage, int numImage) throws Exception {
		String ris = "";
		String filenameTemp = "";

		try {

			filenameTemp = getFilenameTemp(numImage);

			TIFFEncodeParam param = new TIFFEncodeParam();
			param.setCompression(TIFFEncodeParam.COMPRESSION_NONE);

			ParameterBlock pb = new ParameterBlock();
			pb.addSource(riImage);
			pb.add(filenameTemp);
			pb.add("TIFF");
			pb.add(param);
			RenderedOp op = JAI.create("filestore", pb);
			op.dispose();
			op = null;
			ris = filenameTemp;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("ERRORE nella creazione del file temporaneo " + filenameTemp);
		}
		return ris;
	}

	private void upload(String url, String fileName) {
		logger.info("Entro in upload");
		CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
		CloseableHttpResponse response = null;
		File transferfile = new File(fileName);
		try {
			HttpPost request = new HttpPost(url);
			RequestConfig config = RequestConfig.custom().setExpectContinueEnabled(true).build();
			FileBody lFileBody = new FileBody(transferfile);
			FileBody tranferFileBodyTif = new FileBody(transferfile);
			StringBody lStringBodyIdSelected = new StringBody(idSelected != null ? idSelected : "", ContentType.TEXT_PLAIN);
			HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("userfile", lFileBody).addPart("userfiletif", tranferFileBodyTif)
					.addPart("idSelected", lStringBodyIdSelected).build();
			logger.info("idSelected = " + idSelected);
			ta.append("\nidSelected = " + idSelected);
			ta.append("\nreqEntity = " + reqEntity);
			request.setEntity(reqEntity);
			request.setConfig(config);
			String result = null;
			BufferedReader br = null;

			HttpUriRequest request2 = RequestBuilder.post().setUri(url).setEntity(reqEntity).build();

			response = lDefaultHttpClient.execute(request2);
			System.out.println(response.getStatusLine());
			StringBuilder sb = new StringBuilder();
			String line;
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
			logger.info(result);
			ta.append(result);
			if (response.getStatusLine().getStatusCode() != 200) {
				ta.append("\nIl server ha risposto: " + response.getStatusLine() + ": " + result);
				logger.info("fileName: " + fileName);
				throw new IOException("Il server ha risposto: " + response.getStatusLine() + ": " + result);
			}
			if (callBack != null && !callBack.equals("")) {
				// String lStrToInvoke = "javascript:" + callBack + "('" + result + "');";
				// getAppletContext().showDocument(new URL(lStrToInvoke));
				module.setCallBackChiusura(callBack);
				module.setCallBackChiusuraArg(result);
			}
			errorOnUpload = false;
			logger.info("Fine upload");
			ta.append("\nFine upload");
		} catch (Exception e) {
			errorOnUpload = true;
			logger.info("Errore", e);
			showError(e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void showError(final String error) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, error, "Errore", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
