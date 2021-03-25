package it.eng.areas.hybrid.module.cryptoLight.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.smartcardio.CardTerminal;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.cryptoLight.cnProvider.CommonNameProvider;
import it.eng.areas.hybrid.module.cryptoLight.exception.SmartCardException;
import it.eng.areas.hybrid.module.cryptoLight.outputFileProvider.FileOutputProvider;
import it.eng.areas.hybrid.module.cryptoLight.sign.FileBean;
import it.eng.areas.hybrid.module.cryptoLight.sign.HashFileBean;
import it.eng.areas.hybrid.module.cryptoLight.signers.FactorySigner;
import it.eng.areas.hybrid.module.cryptoLight.signers.ISigner;
import it.eng.areas.hybrid.module.cryptoLight.signers.PDFSigner;
import it.eng.areas.hybrid.module.cryptoLight.thread.CardPresentThread;
import it.eng.areas.hybrid.module.cryptoLight.thread.ConfigurationThread;
import it.eng.areas.hybrid.module.cryptoLight.thread.SignerThread;
import it.eng.areas.hybrid.module.cryptoLight.tools.Factory;
import it.eng.areas.hybrid.module.cryptoLight.util.FileUtility;
import it.eng.areas.hybrid.module.cryptoLight.util.MessageKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.Messages;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;
import it.eng.areas.hybrid.module.cryptoLight.util.TimeStamperUtility;
import it.eng.areas.hybrid.module.cryptoLight.util.WSClientUtils;
import it.eng.common.type.SignerType;
import it.eng.cryptoutil.verify.util.ResponseUtils;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.ResponseSigVerify;

public class PanelSign extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(PanelSign.class);

	private SignApplication signAppl;
	private JPanel lJPanelPrincipal = null;

	// bottoni
	private JPanel bottonieraPanel;
	private JButton jButtonSigner = null;
	private JButton jButtonSignAndMark = null;
	private JButton jButtonMark = null;
	private JButton jButtonCounterSign = null;
	private JButton jButtonFirmaManuale;

	private JPasswordField jPin = null;
	private JComboBox jcombocardreader = null;
	private JProgressBar jProgressBar = null;

	private JLabel labelOS = null;

	private JLabel labelreader = null;
	private JLabel labelcard = null;

	private JLabel labelModalitaFirma = null;
	private JComboBox modalitaFirma = null;
	private JComboBox tipoFirma = null;
	private JCheckBox marcaSoloNonMarcate = null;

	private CardPresentThread cardPresentThread;

	// per test inserisco la seleziona manuale del file
	private JPanel panelFile = null;
	private JPanel signFilesPanel = null;
	private JScrollPane scrollFilePanel = null;

	private JLabel labelFile = null;
	private JLabel labelSelectFile = null;
	private JButton btnSelectFile = null;

	private JTextArea textArea;

	private final String SELECTFILE = "selectFile";
	private final static String VERIFICAFIRME = "verificaFirme";
	private final String FIRMA = "firma";
	private final String CONTROFIRMA = "controfirma";
	private final String FIRMAEMARCA = "firmaEmarca";
	private final String MARCA = "marca";

	private List<FileBean> listFilesToSign;

	private List<String> commonNameArgs;

	private List<String> callBackArgs;// = new ArrayList<String>();
	private String callBack;

	private SignerThread signThread;

	public void setThreadControl(CardPresentThread thread) {
		cardPresentThread = thread;
	}

	/**
	 * This is the default constructor
	 */
	public PanelSign(SignApplication signAppl) {
		super(new BorderLayout());
		this.signAppl = signAppl;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		logger.info("Panel Sign intialize START");

		lJPanelPrincipal = new JPanel();
		lJPanelPrincipal.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTH;
		// marina inizio
		c.fill = GridBagConstraints.BOTH;
		// marina fine
		c.gridx = 0;
		c.weightx = 1.0;
		c.insets = new Insets(2, 10, 2, 10);
		int y = 0;

		c.gridy = y++;
		lJPanelPrincipal.add(createSOPanel(), c);

		c.gridy = y++;
		lJPanelPrincipal.add(createFilePanel(), c);

		c.gridy = y++;
		c.insets = new Insets(5, 10, 2, 10);
		lJPanelPrincipal.add(createConfigPropertyPanel(), c);

		c.gridy = y++;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 10, 10, 10);
		lJPanelPrincipal.add(createFirmaPanel(), c);

		c.gridy = y++;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 10, 10, 10);
		lJPanelPrincipal.add(createPanelloAttivita(), c);

		this.add(lJPanelPrincipal, BorderLayout.NORTH);

		// Carico i provider e controllo le carte e il sistema operativo presente
		ConfigurationThread confThread = new ConfigurationThread(getJProgressBar(), (SignApplication) signAppl, getJPin(), getJButtonSigner(), this);
		confThread.start();

		((SignApplication) signAppl).getJTabbedPane().setEnabled(false);

		logger.info("Panel Sign intialize END");
	}

	private JPanel createSOPanel() {
		JPanel labelPanelLeftOS = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10, 0, 0, 0);
		labelOS = new JLabel();
		Font newLabelFont = new Font(labelOS.getFont().getName(), Font.BOLD, labelOS.getFont().getSize());
		labelOS.setFont(newLabelFont);
		labelOS.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_SO));
		c.gridx = 0;
		c.gridy = 0;
		labelPanelLeftOS.add(labelOS, c);
		return labelPanelLeftOS;
	}

	public void setSO(String osname) {
		labelOS.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_SO) + " " + osname);
	}

	private JPanel createConfigPropertyPanel() {
		JPanel lJPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;

		JPanel panel1 = new JPanel(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.insets = new Insets(2, 0, 2, 10);
		c1.anchor = GridBagConstraints.LINE_START;
		JLabel labelTipoFirma = new JLabel(Messages.getMessage(MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENVELOPETYPE));
		c1.gridx = 0;
		c1.gridy = 0;
		panel1.add(labelTipoFirma, c1);
		String[] firmeDisponibili = PreferenceManager.getStringArray(PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE_OPTIONS);
		tipoFirma = new JComboBox(firmeDisponibili);
		String tipoFirmaDefault = PreferenceManager.getString(PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE, "");
		logger.info("tipoFirma=" + tipoFirmaDefault);
		int indiceSelezionato = 0;
		for (int i = 0; i < firmeDisponibili.length; i++) {
			if (firmeDisponibili[i].equalsIgnoreCase(tipoFirmaDefault))
				indiceSelezionato = i;
		}
		tipoFirma.setSelectedIndex(indiceSelezionato);
		tipoFirma.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (((String) tipoFirma.getSelectedItem()).equalsIgnoreCase(SignerType.PDF.name())
						|| ((String) tipoFirma.getSelectedItem()).equalsIgnoreCase(SignerType.XAdES.name())) {
					modalitaFirma.setVisible(false);
					labelModalitaFirma.setVisible(false);
				} else {
					modalitaFirma.setVisible(true);
					labelModalitaFirma.setVisible(true);
				}

				if (signAppl.getFileBeanList() != null && signAppl.getFileBeanList().size() > 0) {
					Iterator<FileBean> itr = signAppl.getFileBeanList().iterator();
					while (itr.hasNext()) {
						FileBean bean = itr.next();
						String beanFileName = bean.getFileName();
						logger.info("Elaborazione file " + bean.getFile());
						bean.setRootFileDirectory(bean.getFile().getParentFile());

						if (FileUtility.isPdf(bean.getFile())) {
							logger.info("Il file da firmare � PDF");
							bean.setIsPdf(true);
						} else {
							logger.info("Il file da firmare non � PDF");
							bean.setIsPdf(false);
						}

						try {
							bean.setTipoBusta(Factory.isSigned(bean.getFile()));
							logger.info("Tipo di busta del file: " + bean.getTipoBusta());
						} catch (Exception e1) {
							logger.info("Errore " + e1.getMessage());
							/// this.file = null;
						}

						// verifico se va fatta la conversione in pdf
						String tipoBustaSelezionata = (String) tipoFirma.getSelectedItem();
						logger.info("Tipo di firma selezionata: " + tipoBustaSelezionata);

						bean = FileUtility.valorizzaFile(tipoBustaSelezionata, bean.getTipoBusta() != null, bean);
						if (bean != null) {
							if (bean.getIsFirmato())
								signAppl.setFileFirmatoPresente(true);
							signAppl.setFileTuttiFirmati(bean.getIsFirmato() && signAppl.isFileTuttiFirmati());
							// fileBeanList.add( bean );

						} else {
							itr.remove();
							textArea.append(beanFileName + " ignorato a causa di un errore nell'acquisizione\n");
						}

					}
				}

				showFiles();
			}
		});

		c1.gridx = 1;
		c1.gridy = 0;
		panel1.add(tipoFirma, c1);

		labelModalitaFirma = new JLabel(Messages.getMessage(MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENVELOPEMERGE));
		labelModalitaFirma.setVisible(false);
		c1.gridx = 0;
		c1.gridy = 1;
		panel1.add(labelModalitaFirma, c1);
		String[] modalitaDisponibili = PreferenceManager.getStringArray(PreferenceKeys.PROPERTY_SIGN_ENVELOPE_MERGE_OPTIONS);
		modalitaFirma = new JComboBox(modalitaDisponibili);
		String modalitaDefault = PreferenceManager.getString(PreferenceKeys.PROPERTY_SIGN_ENVELOPE_MERGE, "");
		logger.info("modalitaFirma=" + modalitaDefault);
		indiceSelezionato = 0;
		for (int i = 0; i < modalitaDisponibili.length; i++) {
			if (modalitaDisponibili[i].equalsIgnoreCase(modalitaDefault))
				indiceSelezionato = i;
		}
		modalitaFirma.setSelectedIndex(indiceSelezionato);
		c1.gridx = 1;
		c1.gridy = 1;
		panel1.add(modalitaFirma, c1);

		if (((String) tipoFirma.getSelectedItem()).equalsIgnoreCase(SignerType.PDF.name())) {
			modalitaFirma.setVisible(false);
			labelModalitaFirma.setVisible(false);
		} else {
			modalitaFirma.setVisible(true);
			labelModalitaFirma.setVisible(true);
		}

		JPanel panel2 = new JPanel(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		JLabel labelFirmaSolo = new JLabel(Messages.getMessage(MessageKeys.PANEL_PREFERENCE_FIRMAMARCA_FIELD_MARKONLYNOTTIMESTAMPED));
		labelFirmaSolo.setVisible(false);
		c2.gridx = 0;
		c2.gridy = 0;
		panel2.add(labelFirmaSolo, c2);
		marcaSoloNonMarcate = new JCheckBox();
		boolean firmaSoloDefault = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SIGN_MARCATURA_SOLO_NON_MARCATE);
		marcaSoloNonMarcate.setSelected(firmaSoloDefault);
		marcaSoloNonMarcate.setVisible(false);
		c2.gridx = 1;
		c2.gridy = 0;
		panel2.add(marcaSoloNonMarcate, c2);

		c.gridx = 0;
		c.gridy = 0;
		lJPanel.add(panel1, c);
		c.gridx = 0;
		c.gridy = 1;
		lJPanel.add(panel2, c);

		// lJPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );

		return lJPanel;
	}

	private JPanel createFirmaPanel() {
		JPanel firmaPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 2, 10);
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		firmaPanel.add(createEmulationPanel(), c);

		c.insets = new Insets(2, 5, 2, 5);
		c.gridy = 1;
		firmaPanel.add(createPinPanel(), c);

		c.gridy = 2;
		firmaPanel.add(createCardReaderPanel(), c);

		c.gridy = 3;
		c.insets = new Insets(10, 5, 10, 5);
		firmaPanel.add(createBottonieraPanel(), c);

		firmaPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		return firmaPanel;
	}

	private JPanel createPanelloAttivita() {
		JPanel attivitaPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 2, 10);
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;

		textArea = new JTextArea(5, 25);
		// textArea.setVisible(false);
		boolean activityPanelEnabled = PreferenceManager.enabled(PreferenceKeys.PREFERENCE_ACTIVITYPANEL_ENABLED);
		if (activityPanelEnabled)
			attivitaPanel.add(textArea, c);

		return attivitaPanel;
	}

	private JPanel createPinPanel() {
		JPanel lJPanelPin = new JPanel();
		JLabel lJLabelPin = new JLabel(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_PIN));
		lJLabelPin.setLabelFor(jPin);
		lJPanelPin.add(lJLabelPin);
		lJPanelPin.add(getJPin());
		jPin.setVisible(true);
		jPin.setColumns(10);
		return lJPanelPin;
	}

	private JPanel createEmulationPanel() {
		JPanel labelPanelLeftEmulation = new JPanel(new GridLayout(1, 1));
		JLabel labelEmulation = new JLabel();
		labelEmulation.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_EMULATIONENABLED));
		if (PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SIGN_EMULATIONMODE))
			labelEmulation.setVisible(true);
		else
			labelEmulation.setVisible(false);
		labelPanelLeftEmulation.add(labelEmulation);
		return labelPanelLeftEmulation;
	}

	private JPanel createCardReaderPanel() {
		JPanel lJPanelCardReader = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 0, 2, 0);
		c.gridx = 0;
		c.gridy = 0;
		labelcard = new JLabel();
		labelcard.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_CARDBUSY));
		labelcard.setLabelFor(getJcombocardreader());
		lJPanelCardReader.add(labelcard, c);
		labelcard.setVisible(false);
		c.gridx = 0;
		c.gridy = 1;
		lJPanelCardReader.add(getJcombocardreader(), c);
		jcombocardreader.setVisible(false);
		labelreader = new JLabel();
		labelreader.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_NOSMARTCARDDETECTED));
		c.gridx = 0;
		c.gridy = 2;
		lJPanelCardReader.add(labelreader, c);
		return lJPanelCardReader;
	}

	private JPanel createBottonieraPanel() {
		// pannello per la bottoniera
		bottonieraPanel = new JPanel();

		bottonieraPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bottonieraPanel.add(getJButtonSigner(), BorderLayout.CENTER);

		Boolean markEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_MARK_ENABLED);
		if (markEnabled) {
			bottonieraPanel.add(getJButtonSignAndMark(), BorderLayout.CENTER);
			bottonieraPanel.add(getJButtonMarca(), BorderLayout.CENTER);
		}
		Boolean counterSignEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_COUNTERSIGN_ENABLED);
		if (counterSignEnabled) {
			bottonieraPanel.add(getJButtonCounterSign(), BorderLayout.CENTER);
		}
		bottonieraPanel.add(getLblFirmaManuale(), BorderLayout.CENTER);
		bottonieraPanel.add(getJProgressBar());

		jButtonSigner.setVisible(false);
		jButtonSigner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonFirmaManuale.setVisible(false);

		return bottonieraPanel;
	}

	private JPanel createFilePanel() {
		panelFile = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(5, 0, 0, 0);

		signFilesPanel = new JPanel(new GridBagLayout());
		signFilesPanel.setBackground(Color.WHITE);

		scrollFilePanel = new JScrollPane(signFilesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollFilePanel.setMinimumSize(new Dimension(300, 70));
		scrollFilePanel.setPreferredSize(new Dimension(300, 70));

		c.insets = new Insets(10, 0, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		panelFile.add(scrollFilePanel, c);

		labelFile = new JLabel();
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 0, 5, 10);
		c.gridwidth = 2;
		panelFile.add(labelFile, c);

		labelSelectFile = new JLabel(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_SCEGLIFILE));
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 0, 5, 10);
		c.gridwidth = 1;
		panelFile.add(labelSelectFile, c);

		btnSelectFile = new JButton(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_SCEGLIFILE));
		btnSelectFile.setActionCommand(SELECTFILE);
		btnSelectFile.addActionListener(this);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		// c.insets = new Insets(0, 0, 0, 10);
		panelFile.add(btnSelectFile, c);

		return panelFile;
	}

	public JLabel getLabelCard() {
		return labelcard;
	}

	public void setCardPresent(boolean ispresent, boolean emulation) {
		if (ispresent || emulation) {
			showEnabledButtons(true);
			jPin.setVisible(true);
			labelcard.setVisible(false);
			jButtonFirmaManuale.setVisible(false);
		} else {
			showEnabledButtons(false);
			jPin.setVisible(true);
			labelcard.setVisible(true);
			// jButtonFirmaManuale.setVisible(true);
		}

	}

	/**
	 * Questo metodo crea il pulsante per eseguire la firma del file in questione e setta le rispettive propriet�
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonSigner() {
		if (jButtonSigner == null) {
			URL picURL = getClass().getResource("buttonsigner.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonSigner = new JButton(cup);
			jButtonSigner.setToolTipText(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_SIGN));
			jButtonSigner.setActionCommand(FIRMA);
			jButtonSigner.addActionListener(this);
		}
		return jButtonSigner;
	}

	public JButton getJButtonSignAndMark() {
		if (jButtonSignAndMark == null) {
			URL picURL = getClass().getResource("buttonSignEMarca.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonSignAndMark = new JButton(cup);
			jButtonSignAndMark.setToolTipText(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_SIGNMARK));
			jButtonSignAndMark.setActionCommand(FIRMAEMARCA);
			jButtonSignAndMark.addActionListener(this);
		}
		return jButtonSignAndMark;
	}

	public JButton getJButtonMarca() {
		if (jButtonMark == null) {
			URL picURL = getClass().getResource("buttonMarca.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonMark = new JButton(cup);
			jButtonMark.setToolTipText(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_MARK));
			jButtonMark.setActionCommand(MARCA);
			jButtonMark.addActionListener(this);
		}
		return jButtonMark;
	}

	public JButton getJButtonCounterSign() {
		if (jButtonCounterSign == null) {
			URL picURL = getClass().getResource("buttoncountersigner.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonCounterSign = new JButton(cup);
			jButtonCounterSign.setToolTipText(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_COUNTERSIGN));
			jButtonCounterSign.setActionCommand(CONTROFIRMA);
			jButtonCounterSign.addActionListener(this);
		}
		return jButtonCounterSign;
	}

	/**
	 * This method initializes jPin
	 * 
	 * @return javax.swing.JPasswordField
	 */
	public JPasswordField getJPin() {
		if (jPin == null) {
			jPin = new JPasswordField();
		}
		return jPin;
	}

	/**
	 * This method initializes jProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setVisible(true);
		}
		return jProgressBar;
	}

	/**
	 * This method initializes jcombocardreader
	 * 
	 * @return javax.swing.JComboBox
	 */
	public JComboBox getJcombocardreader() {
		if (jcombocardreader == null) {
			jcombocardreader = new JComboBox();
			jcombocardreader.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cardPresentThread.setCardName(jcombocardreader.getSelectedItem().toString());
				}
			});
		}
		return jcombocardreader;
	}

	public void clearReader() {
		try {
			jcombocardreader.removeAllItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Nascondo la combo e visualizzo il messaggio di nessun lettore presente
		labelreader.setVisible(true);
		jcombocardreader.setVisible(false);

		// Visualizzo il bottone di firma manuale
		// jButtonFirmaManuale.setVisible(true);

	}

	public void settingReader(List<CardTerminal> terminallist) {
		if (jcombocardreader.getItemCount() != terminallist.size()) {
			labelreader.setVisible(false);
			jcombocardreader.setVisible(true);
			// Carico i valori
			jcombocardreader.removeAllItems();
			for (CardTerminal terminal : terminallist) {
				jcombocardreader.addItem(terminal.getName());
				jcombocardreader.setSelectedIndex(0);
			}
		}

	}

	private JButton getLblFirmaManuale() {
		if (jButtonFirmaManuale == null) {
			jButtonFirmaManuale = new JButton();
			jButtonFirmaManuale.setToolTipText(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_FIRMAOFFLINE));
			jButtonFirmaManuale.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent evt) {
					// ManualFile mf = new ManualFile(card.getBaseurl(), card.getCookie(), card.getFile());
					// mf.setVisible(true);

				}
			});
			jButtonFirmaManuale.setIcon(new ImageIcon(PanelSign.class.getResource("buttonsignermanual.png")));
			jButtonFirmaManuale.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		return jButtonFirmaManuale;
	}

	public void endSignerUpload() {
		// JSObject.getWindow((SmartCard)card).eval("endObjectSigner();");
	}

	public void showManualSign() {
		jButtonFirmaManuale.setVisible(true);
	}

	public void showFiles() {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 10, 3, 10);
		if (signAppl.getFileBeanList() != null && !signAppl.getFileBeanList().isEmpty()) {
			signFilesPanel.removeAll();
			for (int i = 0; i < signAppl.getFileBeanList().size(); i++) {
				FileBean bean = signAppl.getFileBeanList().get(i);
				if (bean != null) {
					JLabel labelNomeFile = new JLabel();
					if (bean.getFileNameConvertito() != null)
						labelNomeFile.setText(bean.getFileNameConvertito());
					else
						labelNomeFile.setText(bean.getFileName());

					c.gridx = 0;
					c.gridy = i;
					c.weightx = 1.0;
					c.weighty = 1.0;
					c.anchor = GridBagConstraints.LINE_START;
					signFilesPanel.add(labelNomeFile, c);

					boolean verificaFirmeEnabled = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SIGN_VERIFICAFIRME_ENABLED);
					if (verificaFirmeEnabled) {
						JButton jButtonVerificaFirme = new JButton(Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_VERIFICAFIRME));
						jButtonVerificaFirme.setActionCommand(VERIFICAFIRME + i);
						jButtonVerificaFirme.addActionListener(this);
						if (bean.getIsFirmato())
							jButtonVerificaFirme.setVisible(true);
						else
							jButtonVerificaFirme.setVisible(false);
						c.gridx = 1;
						c.gridy = i;
						signFilesPanel.add(jButtonVerificaFirme, c);
					}
				}
			}
		} else if (!signAppl.getHashfilebean().isEmpty()) {
			labelFile.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_HASHFILES));
		} else {
			labelFile.setText(Messages.getMessage(MessageKeys.PANEL_SIGN_LABEL_FILESELECTION_KO));
		}

		if (signAppl.getFileBeanList() != null && !signAppl.getFileBeanList().isEmpty()) {
			btnSelectFile.setVisible(false);
			labelSelectFile.setVisible(false);
			labelFile.setVisible(false);
			scrollFilePanel.setVisible(true);
			signFilesPanel.setVisible(true);
			bottonieraPanel.setVisible(true);
		} else if (signAppl.getHashfilebean() != null && !signAppl.getHashfilebean().isEmpty()) {
			btnSelectFile.setVisible(false);
			labelSelectFile.setVisible(false);
			labelFile.setVisible(true);
			scrollFilePanel.setVisible(false);
			signFilesPanel.setVisible(true);
			bottonieraPanel.setVisible(true);
		} else {
			if (PreferenceManager.enabled(PreferenceKeys.PROPERTY_SIGN_FILE_SELECTION_ENABLED)) {
				labelSelectFile.setVisible(true);
				btnSelectFile.setVisible(true);
			}
			labelFile.setVisible(true);
			scrollFilePanel.setVisible(false);
			bottonieraPanel.setVisible(false);
		}

		boolean showFileList = false;
		try {
			showFileList = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SIGN_SHOWLISTFILE);
			logger.info("Propriet� " + PreferenceKeys.PROPERTY_SIGN_SHOWLISTFILE + ": " + showFileList);
		} catch (Exception e) {
			logger.info("Errore nel recupero della propriet� " + PreferenceKeys.PROPERTY_LOGFILEENABLED);
		}
		if (!showFileList) {
			scrollFilePanel.setVisible(false);
		}
	}

	/**
	 * ascolta i bottoni di firma abilitati
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			String command = e.getActionCommand();
			logger.info("command:" + command);
			// verifico se hai premuto il bottone seleziona file
			if (command.equals(SELECTFILE)) {
				JFileChooser filechooser = new JFileChooser();
				int returnVal = filechooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = filechooser.getSelectedFile();
					if (file != null && file.exists()) {
						FileBean bean = new FileBean();
						bean.setFile(file);
						bean.setFileName(file.getName());
						bean.setRootFileDirectory(file.getParentFile());
						if (FileUtility.isPdf(file)) {
							logger.info("Il file da firmare è PDF");
							bean.setIsPdf(true);
						} else {
							logger.info("Il file da firmare non è PDF");
							bean.setIsPdf(false);
						}

						try {
							bean.setTipoBusta(Factory.isSigned(bean.getFile()));
							logger.info("Tipo di busta del file: " + bean.getTipoBusta());
						} catch (Exception e1) {
							logger.info("Errore " + e1.getMessage());
							/// this.file = null;
						}

						// verifico se va fatta la conversione in pdf
						String tipoBustaSelezionata = (String) tipoFirma.getSelectedItem();
						logger.info("Tipo di firma selezionata: " + tipoBustaSelezionata);

						bean = FileUtility.valorizzaFile(tipoBustaSelezionata, bean.getTipoBusta() != null, bean);
						if (bean != null) {
							if (bean.getIsFirmato())
								signAppl.setFileFirmatoPresente(true);
							signAppl.setFileTuttiFirmati(bean.getIsFirmato() && signAppl.isFileTuttiFirmati());
							signAppl.getFileBeanList().add(bean);
						}
					}
					showFiles();
				}
				return;
			}

			if ((signAppl.getFileBeanList() == null || signAppl.getFileBeanList().isEmpty()) && signAppl.getHashfilebean().isEmpty()) {
				JOptionPane.showMessageDialog(signAppl.getJTabbedPane(), "seleziona un file", "", JOptionPane.ERROR_MESSAGE);
				return;
			}

			textArea.setVisible(true);

			boolean timemark = false;
			boolean counterSign = false;
			if (command.startsWith(VERIFICAFIRME)) {

				boolean caCheckFirme = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SIGN_CACHECKENABLED);
				boolean crlCheckFirme = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SIGN_CRLCHECKENABLED);
				Date dataRif = null;
				try {
					String dataRifString = PreferenceManager.getString(PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA);
					logger.info("Propriet� " + PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA + ": " + dataRifString);
					String dataRifFormato = PreferenceManager.getString(PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA_FORMATO);
					logger.info("Propriet� " + PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA_FORMATO + ": " + dataRifFormato);
					if (dataRifFormato != null && dataRifString != null) {
						SimpleDateFormat sdf = new SimpleDateFormat(dataRifFormato);
						dataRif = sdf.parse(dataRifString);
					}
				} catch (Exception e1) {
					logger.info("Errore nel recupero delle propriet� " + PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA, e1);
				}
				String indiceFile = command.substring(VERIFICAFIRME.length());
				File fileDaVerificare = signAppl.getFileBeanList().get(Integer.parseInt(indiceFile)).getFile();
				try {
					FileOperationResponse risposta = WSClientUtils.verificaFirme(caCheckFirme, crlCheckFirme, dataRif, fileDaVerificare);
					if (!ResponseUtils.isResponseOK(risposta)) {
						// prendo la risposta alla verifica
						if (risposta.getGenericError() != null) {
							throw new Exception("si sono verificati errori nell'invocazione del servizio di verifica:" + risposta.getGenericError());
						}
						if (risposta.getFileoperationResponse() != null && risposta.getFileoperationResponse().getFileOperationResults() != null) {
							it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults foresults = new it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults();
							foresults.getFileOperationResult().addAll(risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult());
							ResponseSigVerify resp = ResponseUtils.getResponse(foresults, ResponseSigVerify.class);
							if (resp == null) {
								JOptionPane.showMessageDialog(signAppl.getJTabbedPane(), Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC),
										Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC), JOptionPane.ERROR_MESSAGE);
								return;
							}
							String[] options = new String[] { Messages.getMessage(MessageKeys.PANEL_BUTTON_CKECKRESULT) };
							int choice = OptionSignDialog.createDialog(signAppl.getPanelsign(), WSClientUtils.processHierarchy(resp.getSigVerifyResult()),
									Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_VERIFICAFIRMA_SIGNATURENOTVALID),
									Messages.getMessage(MessageKeys.PANEL_SIGN_BUTTON_VERIFICAFIRME), JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE,
									null, options, options[0]);
							if (choice == JOptionPane.CLOSED_OPTION) {
								return;
							}
						}
					}
				} catch (Exception e1) {
					logger.info("Errore nella verifica firme " + PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA, e1);
					JOptionPane.showMessageDialog(signAppl.getJTabbedPane(), Messages.getMessage(e1.getMessage()), Messages.getMessage(MessageKeys.MSG_ERROR),
							JOptionPane.ERROR_MESSAGE);
				}

				return;
			} else if (command.equals(FIRMAEMARCA)) {
				timemark = true;
			} else if (command.equals(MARCA)) {
				for (FileBean bean : signAppl.getFileBeanList()) {
					if (!bean.getIsFirmato()) {
						logger.info("Marca sul file " + bean.getFileName() + " non consentita - file non firmato.");
						textArea.append("Marca sul file " + bean.getFileName() + " non consentita - file non firmato.\n");
					} else {
						// if( bean.getIsPdf() ){
						ISigner signer = FactorySigner.getSigner(bean.getTipoBusta());
						if (signer instanceof PDFSigner /* || signer instanceof P7MSigner */) {
							logger.info("Marca sul file " + bean.getFileName() + " non consentita - file pdf.");
							textArea.append("Marca sul file " + bean.getFileName() + " non consentita - file pdf.\n");
						} else {
							boolean esitoMarca = TimeStamperUtility.addMarca(bean.getFile(), marcaSoloNonMarcate.isSelected());
							if (esitoMarca) {
								logger.info("Marca sul file " + bean.getFileName() + " aggiunta con successo.");
								textArea.append("Marca sul file " + bean.getFileName() + " aggiunta con successo.\n");
							} else {
								logger.info("Marca sul file " + bean.getFileName() + " non aggiunta a causa di errori.");
								textArea.append("Marca sul file " + bean.getFileName() + " non aggiunta a causa di errori.\n");
							}
						}
					}
				}
				JOptionPane.showMessageDialog(signAppl.getJTabbedPane(), Messages.getMessage(MessageKeys.MSG_OPSUCCESS), "", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if (command.equals(CONTROFIRMA)) {
				counterSign = true;
			}

			if (jPin.getPassword() == null || new String(jPin.getPassword()).length() == 0) {
				JOptionPane.showMessageDialog(signAppl.getJTabbedPane(), Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PINNULL),
						Messages.getMessage(MessageKeys.MSG_ERROR), JOptionPane.ERROR_MESSAGE);
				return;
			}

			// clono le liste per passarle (dopo aver eventualmente tolto oggetti) al processo di firma
			listFilesToSign = new ArrayList<FileBean>();

			// verifico se posso fare la firma congiunta
			String envMerge = null;
			if (((String) tipoFirma.getSelectedItem()).equalsIgnoreCase(SignerType.PDF.name())) {
				envMerge = SignatureMerge.CONGIUNTA.value();
			} else {
				envMerge = (String) modalitaFirma.getSelectedItem();
			}

			// caso file
			Iterator<FileBean> i = signAppl.getFileBeanList().iterator();
			while (i.hasNext()) {
				FileBean bean = i.next();

				boolean congiunta = bean.getTipoBusta() != null && envMerge.equalsIgnoreCase(SignatureMerge.CONGIUNTA.value());
				logger.info("Il file è firmato ed e' stata richiesta la modalità congiunta?  " + congiunta);

				if (counterSign) {
					// if( !bean.getIsFirmato() ){
					// textArea.append("Controfirma sul file " + bean.getFileName() + " non consentita - file non firmato.\n");
					// } else {
					// ISigner signer = FactorySigner.getSigner( bean.getTipoBusta());
					// if( signer instanceof PDFSigner ){
					// textArea.append("Controfirma sul file " + bean.getFileName() + " non consentita - controfirma pdf non supportata.\n");
					// } else {
					// listFilesToSign.add( bean );
					// }
					// }
				} else {
					// caso firma congiunta
					if (congiunta) {
						bean.setFirmaCongiuntaRequired(true);

						if (bean.getIsFirmaPresente() != null && bean.getIsFirmaPresente().equalsIgnoreCase("true") && bean.getIsFirmaValida() != null
								&& bean.getIsFirmaValida().equalsIgnoreCase("false")) {
							logger.info("Imposto la modalità firma verticale");
							textArea.append("Il file " + bean.getFileName() + " presenta firme non valide, si procede alla firma verticale\n");
							congiunta = false;
							bean.setFirmaCongiuntaRequired(false);
							listFilesToSign.add(bean);
						}
						if (bean.getIsFirmaPresente() == null && bean.getIsFirmaValida() != null && bean.getIsFirmaValida().equalsIgnoreCase("false")) {
							logger.info("Imposto la modalità firma verticale");
							textArea.append("Il file " + bean.getFileName() + " presenta firme non valide, si procede alla firma verticale\n");
							congiunta = true;
							bean.setFirmaCongiuntaRequired(true);
							listFilesToSign.add(bean);
						}

						// if( bean.getIsFirmato() ) {
						if ( /* bean.getIsFirmaPresente()==null && */bean.getIsFirmaValida() == null && bean.getIsFirmato()) {
							logger.info("firma congiunta verifica firme attuali attiva invio il file al WS");
							// verifica se le firme sono valide chiamando il ws altrimenti chiedi all'utente
							// che occorre fare una firma verticale
							boolean caCheckFirme = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SIGN_CACHECKENABLED);
							boolean crlCheckFirme = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_SIGN_CRLCHECKENABLED);
							Date dataRif = null;
							try {
								String dataRifString = PreferenceManager.getString(PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA);
								logger.info("dataRifString: " + dataRifString);
								String dataRifFormato = PreferenceManager.getString(PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA_FORMATO);
								logger.info("dataRifFormato: " + dataRifFormato);
								if (dataRifFormato != null && dataRifString != null) {
									SimpleDateFormat sdf = new SimpleDateFormat(dataRifFormato);
									dataRif = sdf.parse(dataRifString);
								}

							} catch (Exception e1) {
								logger.info("Errore nel recupero delle proprietà " + PreferenceKeys.PROPERTY_SIGN_DATARIFERIMENTOVERIFICA, e1);
							}

							try {
								FileOperationResponse risposta = WSClientUtils.verificaFirme(caCheckFirme, crlCheckFirme, dataRif, bean.getFile());
								if (risposta != null && !ResponseUtils.isResponseOK(risposta)) {
									// prendo la risposta alla verifica
									if (risposta.getGenericError() != null) {
										throw new Exception(
												"si sono verificati errori nell'invocazione del servizio di verifica:" + risposta.getGenericError());
									}
									if (risposta.getFileoperationResponse() != null && risposta.getFileoperationResponse().getFileOperationResults() != null) {
										it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults foresults = new it.eng.fileOperation.clientws.Response.FileoperationResponse.FileOperationResults();
										foresults.getFileOperationResult()
												.addAll(risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult());
										ResponseSigVerify resp = ResponseUtils.getResponse(foresults, ResponseSigVerify.class);
										if (resp == null) {
											JOptionPane.showMessageDialog(signAppl.getJTabbedPane(),
													Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC),
													Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC), JOptionPane.ERROR_MESSAGE);
											return;
										}
										// String[] options = new String[] { Messages.getMessage( MessageKeys.PANEL_BUTTON_SI ), Messages.getMessage(
										// MessageKeys.PANEL_BUTTON_NO ), Messages.getMessage( MessageKeys.PANEL_BUTTON_CKECKRESULT ) };
										// int choice=OptionSignDialog.createDialog( card.getPanelsign(),
										// WSClientUtils.processHierarchy(resp.getSigVerifyResult()),
										// Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_JOINTSIGNNOTPOSSIBLE, null, bean.getFileName() ),
										// Messages.getMessage( MessageKeys.PANEL_BUTTON_CKECKRESULT ),
										// JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE,null, options, options[1]);
										// if(choice==JOptionPane.OK_OPTION){
										logger.info("Imposto la modalità firma verticale");
										textArea.append("Il file " + bean.getFileName() + " presenta firme non valide, si procede alla firma verticale\n");
										// congiunta = true;
										// bean.setFirmaCongiuntaRequired( true );
										congiunta = false;
										bean.setFirmaCongiuntaRequired(false);
										listFilesToSign.add(bean);
										// }else{
										// logger.info("Firma annullata");
										// //return;
										// textArea.append("Firma annullata per il file " + bean.getFileName() + "\n");
										//
										// }
									}
								}
								if (risposta != null && ResponseUtils.isResponseOK(risposta)) {
									bean.setFirmaCongiuntaRequired(true);
									listFilesToSign.add(bean);
								}
							} catch (Exception e1) {
								logger.info("Errore", e1);
								textArea.append("Firma non eseguita per il file " + bean.getFileName() + " a causa di errori\n");
								i.remove();
							}
						} else {
							logger.info("Il file non è firmato, procedo con la firma verticale");
							bean.setFirmaCongiuntaRequired(false);
							listFilesToSign.add(bean);
						}
					} else {
						bean.setFirmaCongiuntaRequired(false);
						listFilesToSign.add(bean);
					}
				}
			}

			// caso impronta
			Iterator<HashFileBean> i1 = signAppl.getHashfilebean().iterator();
			while (i1.hasNext()) {
				HashFileBean bean = i1.next();

				boolean congiunta = envMerge.equalsIgnoreCase(SignatureMerge.CONGIUNTA.value());
				logger.info("E' stata richiesta la modalita' congiunta?  " + congiunta);
				if (congiunta) {

					if (bean.getIsFirmaPresente() != null && bean.getIsFirmaPresente().equalsIgnoreCase("true") && bean.getIsFirmaValida() != null
							&& bean.getIsFirmaValida().equalsIgnoreCase("false")) {
						logger.info("Imposto la modalità firma verticale");
						textArea.append("Il file " + bean.getFileName() + " presenta firme non valide, si procede alla firma verticale\n");
					}
					if (bean.getIsFirmaPresente() == null && bean.getIsFirmaValida() != null && bean.getIsFirmaValida().equalsIgnoreCase("false")) {
						logger.info("Imposto la modalità firma verticale");
						textArea.append("Il file " + bean.getFileName() + " presenta firme non valide, si procede alla firma verticale\n");
					}
				}
			}

			// Recupero lo slot selezionato
			int slot = jcombocardreader.getSelectedIndex();

			logger.info("Password passata a SignThread " + jPin.getPassword().toString());
			signThread = new SignerThread(this, signAppl.getHashfilebean(), listFilesToSign, SignerType.valueOf((String) tipoFirma.getSelectedItem()),
					(String) modalitaFirma.getSelectedItem(), jPin.getPassword(), jProgressBar, slot, timemark, counterSign, signAppl.getFileInputProvider());

			// Avvio il thread di firma
			signThread.start();

		} catch (Exception er) {
			er.printStackTrace();
			// JOptionPane.showMessageDialog(card.getJTabbedPane(),
			// Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_GENERIC ) +er.getMessage(),
			// Messages.getMessage(MessageKeys.MSG_ERROR), JOptionPane.ERROR_MESSAGE);
		}

	}

	public void signStarted() {
		logger.info("Entro in signStarted ");
		showEnabledButtons(false);
		jPin.setVisible(false);

		// Stoppo il thread di controllo dei lettori
		cardPresentThread.stopThread();

		// Disabilito la combo
		jcombocardreader.setEnabled(false);

		signAppl.getJTabbedPane().setEnabled(false);

		if (signAppl.getPreferenceMenu() != null)
			signAppl.getPreferenceMenu().setEnabled(false);
		if (signAppl.getInvioLogMenu() != null)
			signAppl.getInvioLogMenu().setEnabled(false);
	}

	/**
	 * Metodo che viene chiamato quando SignerThread termina la lavorazione.
	 * Viene chiamato sia nel caso sia andata a buon fine che meno. 
	 * @param esitoFirma true se � andata a buon fine, false altrimenti
	 */
	public void signStopped(boolean esitoFirma, boolean esitoUploadFile) {		
		
		logger.info("Entro in signStopped con esitoFirma " + esitoFirma);
		jProgressBar.setVisible(false);  	//Disabilito la progress bar
		showEnabledButtons(true);  		 	//Abilito i pulsanti
		jPin.setVisible(true);			 	//Abilito la text relativa al pin
		jcombocardreader.setEnabled(true);  //Abilito la combo reader

		signAppl.getJTabbedPane().setEnabled(true);
		signAppl.getPreferenceMenu().setEnabled(true);
		signAppl.getInvioLogMenu().setEnabled(true);

		if (esitoFirma) {
			logger.info("Ritorno a SignerApplication per chiudere il frame");
			signAppl.closeFrame(esitoUploadFile);
		}
	}

	public void showMessageDialog(String message, String title, int messageType) {
		JOptionPane.showMessageDialog(signAppl.getJTabbedPane(), message, title, messageType);
	}

	public void gestisciEccezione(String errorMessage) {
		// showManualSign();
		if( errorMessage==null || errorMessage.equalsIgnoreCase(""))
			errorMessage = Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_NOPROVIDER );
		JOptionPane.showMessageDialog( signAppl.getJTabbedPane(),/*"Errore di firma! "+*/ errorMessage, "Errore", JOptionPane.ERROR_MESSAGE);
		jProgressBar.setVisible(false);
		jProgressBar.setValue(0);
		jProgressBar.setString("");
		signAppl.getJTabbedPane().setEnabled(true);
		signAppl.getPanelsign().showEnabledButtons(true);
		signAppl.getPanelsign().getJPin().setVisible(true);
	}

	public void sendCommonName(String pStrCommonNameToSend) throws SmartCardException {
		CommonNameProvider lCommonNameProvider = signAppl.getCommonNameProvider();
		if (lCommonNameProvider != null) {
			commonNameArgs = lCommonNameProvider.sendCommonName(pStrCommonNameToSend);
			if (commonNameArgs == null) {
				throw new SmartCardException(Messages.getMessage(MessageKeys.MSG_UNABLE_TO_SEND_CN));
			}
		}
	}

	public boolean saveOutputFiles() {
		FileOutputProvider fop = signAppl.getFileOutputProvider();
		logger.info("Vado a chiamare il file output " + fop);
		boolean esitoUploadFileComplessivo = true;
		if (fop != null) {
			// caso files
			if (listFilesToSign != null && !listFilesToSign.isEmpty()) {
				for (FileBean bean : listFilesToSign) {
					File fileInput = bean.getFile();
					InputStream in;
					try {
						in = new FileInputStream(bean.getOutputFile());
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
						return false;
					}
					logger.info("Effettuo la chiamata");
					try {
						if (bean.getFileNameConvertito() != null) {
							logger.info("Utilizzo il nome convertito " + bean.getFileNameConvertito());
							fop.saveOutputFile(bean.getIdFile(), in, bean.getFileNameConvertito(), (String) getTipoFirma().getSelectedItem());
						} else if (PreferenceManager.getString(PreferenceKeys.PROPERTY_FILENAME) != null) {
							logger.info("Utilizzo il nome dalla preference " + PreferenceManager.getString(PreferenceKeys.PROPERTY_FILENAME));
							fop.saveOutputFile(bean.getIdFile(), in, PreferenceManager.getString(PreferenceKeys.PROPERTY_FILENAME),
									(String) getTipoFirma().getSelectedItem());
						} else if (bean.getFileName() != null) {
							logger.info("Utilizzo il nome del file " + bean.getFileName());
							fop.saveOutputFile(bean.getIdFile(), in, bean.getFileName(), (String) getTipoFirma().getSelectedItem());
						} else {
							logger.info("Utilizzo il nome del file " + fileInput.getName());
							fop.saveOutputFile(bean.getIdFile(), in, fileInput.getName(), (String) getTipoFirma().getSelectedItem());
						}

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						logger.info("Errore - " + e1.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// caso impronte
			else {
				List<HashFileBean> hashfilebean = signAppl.getHashfilebean();
				if (hashfilebean != null && !hashfilebean.isEmpty()) {
					int numOK = 0;
					int numKO = 0;
					boolean esito = false;
					callBack = fop.getCallback();
					callBackArgs = new ArrayList<String>();
					for (HashFileBean hash : hashfilebean) {
						try {
							esito = fop.saveOutputFile(null, null, hash.getFileName(), (String) getTipoFirma().getSelectedItem(), hash.getId(),
									hash.getSignedBean(), hash.getFirmatario(), hash.getTempFilePath(), hash.getVersione(), hash.getIsFirmaPresente(),
									hash.getIsFirmaValida(), (String) getModalitaFirma().getSelectedItem());
							esitoUploadFileComplessivo = esitoUploadFileComplessivo && esito;
							
							String callBackResult = fop.getCallbackResult();
							if (callBackResult != null)
								callBackArgs.add(callBackResult);

							if (esito) {
								numOK++;
							} else {
								numKO++;
							}
						} catch (Exception e) {
							e.printStackTrace();
							numKO++;
						}
					}

					String messaggio = getTextArea().getText();
					if (numKO > 0) {
						showMessageDialog(Messages.getMessage(MessageKeys.MSG_END_ERROR), "", JOptionPane.ERROR_MESSAGE);
					} else if (messaggio != null && messaggio.equalsIgnoreCase("si procede alla firma verticale")) {
						// showMessageDialog( Messages.getMessage( MessageKeys.MSG_OPFINISH, numOK, numKO ) +
						showMessageDialog(Messages.getMessage(MessageKeys.MSG_OPSUCCESS) + " " + Messages.getMessage(MessageKeys.MSG_END_WARNING), "",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						// showMessageDialog( Messages.getMessage( MessageKeys.MSG_OPFINISH, numOK, numKO ),"", JOptionPane.INFORMATION_MESSAGE );
						showMessageDialog(Messages.getMessage(MessageKeys.MSG_OPSUCCESS), "", JOptionPane.INFORMATION_MESSAGE);
					}

					// JOptionPane.showMessageDialog( card.getJTabbedPane(),Messages.getMessage( MessageKeys.MSG_OPFINISH, numOK, numKO ),"",
					// JOptionPane.INFORMATION_MESSAGE);

				}
			}
		}
		
		logger.info("Terminato il caricamento con il server");
		return 	esitoUploadFileComplessivo;
		
	}

	public void showEnabledButtons(boolean visible) {
		jButtonSigner.setVisible(visible);
		if (jButtonSignAndMark != null)
			jButtonSignAndMark.setVisible(visible);
		if (signAppl.getFileBeanList() != null && !signAppl.getFileBeanList().isEmpty()) {
			if (signAppl.isFileFirmatoPresente() && !((String) tipoFirma.getSelectedItem()).equals(SignerType.PDF.name())) {
				if (jButtonMark != null)
					jButtonMark.setVisible(visible);
				if (jButtonCounterSign != null)
					jButtonCounterSign.setVisible(visible);
			} else {
				if (jButtonMark != null)
					jButtonMark.setVisible(false);
				if (jButtonCounterSign != null)
					jButtonCounterSign.setVisible(false);
			}
		} else if (signAppl.getHashfilebean() != null && !signAppl.getHashfilebean().isEmpty()) {
			if (jButtonMark != null)
				jButtonMark.setVisible(false);
			if (jButtonCounterSign != null)
				jButtonCounterSign.setVisible(false);
		}
	}

	public void showConfigData() {
		// modalita impronta
		if (!signAppl.getHashfilebean().isEmpty()) {
			if (((String) tipoFirma.getSelectedItem()).equalsIgnoreCase(SignerType.XAdES.name())) {
				logger.info("Firma impronta Xades non consentita, si imposta Cades");
				tipoFirma.setSelectedItem(SignerType.CAdES_BES.name());
			}
			tipoFirma.setEnabled(false);

			// modalitaFirma.setSelectedItem( SignatureMerge.VERTICALE.value() );
			modalitaFirma.setEnabled(false);
		}
		// modalita file
		else {
			modalitaFirma.setEnabled(true);
		}
	}

	public JComboBox getModalitaFirma() {
		return modalitaFirma;
	}

	public void setModalitaFirma(JComboBox modalitaFirma) {
		this.modalitaFirma = modalitaFirma;
	}

	public JComboBox getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(JComboBox tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public JCheckBox getMarcaSoloNonMarcate() {
		return marcaSoloNonMarcate;
	}

	public void setMarcaSoloNonMarcate(JCheckBox marcaSoloNonMarcate) {
		this.marcaSoloNonMarcate = marcaSoloNonMarcate;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public SignApplication getSignApplication() {
		return signAppl;
	}

	public List<String> getCommonNameArgs() {
		return commonNameArgs;
	}

	public void setCommonNameArgs(List<String> commonNameArgs) {
		this.commonNameArgs = commonNameArgs;
	}

	public List<String> getCallBackArgs() {
		return callBackArgs;
	}

	public void setCallBackArgs(List<String> callBackArgs) {
		this.callBackArgs = callBackArgs;
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

}