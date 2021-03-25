/**
 * Applet che, a partire dall'impronta del pdf generata dopo l'esecuzione dell'applet che preleva il certificato, apre l'applet di firma in cui non viene
 * richiesto il pin e le informazioni del firmatario.
 */
package it.eng.auriga.ui.module.layout.client.firmamultiplahash;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DragResizeStopEvent;
import com.smartgwt.client.widgets.events.DragResizeStopHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.applet.WaitPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.AppletWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;

public abstract class FirmaCertificatoWindow extends AppletWindow {

	private AppletEng lFirmaCertificatoApplet;

	protected FirmaCertificatoWindow _window;

	private Map<String, Record> nomiUnivoci;
	private String smartId;
	private String commonName;
	private String pin;
	private String firmatario;
	private String alias;
	private String metodoFirma;
	private String appletTipoFirmaAtti;

	private boolean skipCtrlBustaFirm = false;

	public abstract void firmaCallBack(Map<String, Record> files, String commonName);
	
	public Record[] getFilesAndUd() {
		return null;
	}
	
	public void firmaCallBackWithErrors(final Map<String, Record> files, final String commonName) {
		FirmaUtility.manageFirmaCallBackWithErrors(files, getFilesAndUd(), new ServiceCallback<Map<String, Record>>() {
			
			@Override
			public void execute(Map<String, Record> files) {
				firmaCallBack(files, commonName);
			}
		});				
	}
	
//	public FirmaCertificatoWindow(Map<String, Record> files, String pin, String firmatario, String alias, String metodoFirma) {
//		this(null, false, files, false, pin, firmatario, alias, metodoFirma);
//	}

	public FirmaCertificatoWindow(String appletTipoFirmaAtti, boolean abilGestFirmaAllegatiFirmatiPIAtto, Map<String, Record> files, boolean pSkipCtrlBustaFirm, String pin, String firmatario,
			String alias, String metodoFirma) {

		super(I18NUtil.getMessages().protocollazione_firmaWindow_title(), "FirmaCertificatoApplet" + getFirmaCertificatoAppletJarVersion() + ".jar");

		this.pin = pin;
		this.firmatario = firmatario;
		this.appletTipoFirmaAtti = appletTipoFirmaAtti;
		this.metodoFirma = metodoFirma;
		

		_window = this;
		nomiUnivoci = files;
		smartId = SC.generateID();
		skipCtrlBustaFirm = pSkipCtrlBustaFirm;

		setID(smartId);

		/**
		 * Inizializzazione delle funzioni di callback
		 */
		String idCreated = "appletEnd" + smartId + new Date().getTime();
		initCommonNameFunction(this, idCreated + "commonNameFunction");
		initCallBackAskForCloseFunction(this, idCreated + "callBackAskForCloseFunction");

		// Mappa dei parametri da passare
		Map<String, String> lMapParams = new HashMap<String, String>();

		lMapParams.put("pin", pin); // Pin della chiavetta
		lMapParams.put("readOnly", "false");
		lMapParams.put("sign.markEnabled", String.valueOf(isAppletSignMarkEnabled()));
		lMapParams.put("fileInputProvider", "it.eng.client.applet.fileProvider.MultiHashFileInputProvider");
		lMapParams.put("autoClosePostSign", "true"); // Flag che, posto a true, implica la chiusura dell'applet una volta eseguita la firma
		lMapParams.put("callBackAskForClose", idCreated + "callBackAskForCloseFunction");
		
		//Inserisco il metodo di firma che arriva dall'applet di selezione del certificato
		lMapParams.put("metodoFirma", metodoFirma);

		String tipoFirma = getAppletTipoFirma();
		String signEnvelopeType = null;
		if (tipoFirma == null || tipoFirma.equals("") || tipoFirma.equalsIgnoreCase("CAdES")) {
			signEnvelopeType = "CAdES_BES";
		} else if (tipoFirma.equalsIgnoreCase("PAdES")) {
			signEnvelopeType = "PDF";
		} else if (tipoFirma.equalsIgnoreCase("XAdES")) {
			signEnvelopeType = "XAdES";
		}

		if (isActivityPanelHidden()) {
			lMapParams.put("preference.activityPanel.enabled", "false");
		}

		boolean firmaCongiunta = isFirmaCongiunta();
		boolean firmaSingola = files.keySet().size() == 1;

		int count = 0;
		for (String lStrId : files.keySet()) {
			String idFile = files.get(lStrId).getAttribute("idFile");
			String uri = URL.encode(files.get(lStrId).getAttribute("uri"));
			String nomeFile = files.get(lStrId).getAttribute("nomeFile");
			Record infoFile = files.get(lStrId).getAttributeAsRecord("infoFile");
			String impronta = infoFile.getAttribute("impronta");
			/**
			 * improntaPdf è il valore di encodedHash che viene settato nel datasource chiamante
			 */
			String improntaPdf = infoFile.getAttribute("improntaPdf");
			// String improntaXml = infoFile.getAtrtribute("improntaXml");
			String mimetype = infoFile.getAttribute("mimetype");
			String firmaPresente = infoFile.getAttribute("firmato");
			// Leggo firma valida dalle informazioni sull'ultima busta, altrimenti prendo quella generica.
			// Se è presente una marca temporale valida o non valida o se è un pdf protetto o se la busta crittografica esterna non è fatta da Auriga
			// metto firmaValida a false in modo da forzare la cades verticale
			String firmaValida = infoFile.getAttribute("firmaValida");
			if (infoFile.getAttributeAsRecord("infoFirmaMarca") != null) {
				Record infoFirmaMarca = infoFile.getAttributeAsRecord("infoFirmaMarca");
				if (infoFile.getAttributeAsBoolean("firmato") && !infoFirmaMarca.getAttributeAsBoolean("bustaCrittograficaFattaDaAuriga")) {
					firmaValida = "false";
				}else if (infoFirmaMarca.getAttribute("dataOraMarcaTemporale") != null && !"".equalsIgnoreCase(infoFirmaMarca.getAttribute("dataOraMarcaTemporale"))) {
					firmaValida = "false";
				} else {
					boolean firmeNonValideBustaCrittografica = infoFile.getAttributeAsBoolean("firmato") && infoFile.getAttributeAsRecord("infoFirmaMarca").getAttributeAsBoolean("firmeNonValideBustaCrittografica");
					firmaValida = (!firmeNonValideBustaCrittografica) + "";
				}
			} 
			if (infoFile.getAttributeAsBoolean("pdfProtetto")){
				firmaValida = "false";
			}
			boolean isFirmaPAdES = (tipoFirma != null && tipoFirma.equalsIgnoreCase("PAdES")) && (mimetype != null && "application/pdf".equals(mimetype));
			boolean isFirmaXAdES = (tipoFirma != null && tipoFirma.equalsIgnoreCase("XAdES")) && (mimetype != null && "application/xml".equals(mimetype));
			// se non ho una firma PAdES ma il file non è un pdf o XAdES e il file non è un xml allora farò una firma CAdES
			boolean isFirmaCAdES = (tipoFirma != null && tipoFirma.equalsIgnoreCase("CAdES")) || (!isFirmaPAdES && !isFirmaXAdES);
			// se devo fare una firma PAdES e il file è già firmato
			if (isFirmaPAdES && firmaPresente != null && firmaPresente.equalsIgnoreCase("true")) {
				boolean isFirmatoPAdES = infoFile.getAttribute("tipoFirma") != null && ( infoFile.getAttribute("tipoFirma").equalsIgnoreCase("PAdES") || infoFile.getAttribute("tipoFirma").equalsIgnoreCase("PDF") );
				// se il file è già firmato ma non in PaDES o è già firmato in PAdES ma non è una firma valida allora dovrò fare una firma CAdES
				if (!isFirmatoPAdES || !(firmaValida != null && firmaValida.equalsIgnoreCase("true"))) {
					isFirmaPAdES = false;
					isFirmaCAdES = true;
				}
			}
			// se devo fare una firma XAdES e il file è già firmato
			if (isFirmaXAdES && firmaPresente != null && firmaPresente.equalsIgnoreCase("true")) {
				boolean isFirmatoXAdES = infoFile.getAttribute("tipoFirma") != null && infoFile.getAttribute("tipoFirma").equalsIgnoreCase("XAdES");
				// se il file è già firmato ma non in XaDES o è già firmato in XAdES ma non è una firma valida allora dovrò fare una firma CAdES
				if (!isFirmatoXAdES || !(firmaValida != null && firmaValida.equalsIgnoreCase("true"))) {
					isFirmaXAdES = false;
					isFirmaCAdES = true;
				}
			}
			// se devo fare una firma CAdES congiunta e il file è già firmato e con una firma valida
			if (isFirmaCAdES && firmaCongiunta && firmaPresente != null && firmaPresente.equalsIgnoreCase("true") && firmaValida != null
					&& firmaValida.equalsIgnoreCase("true")) {
				boolean isFirmatoCAdES = infoFile.getAttribute("tipoFirma") != null && infoFile.getAttribute("tipoFirma").startsWith("CAdES");
				// se il file è già firmato in CAdES allora dovrò usare l'impronta dello sbustato per fare una firma orrizzontale
				if (isFirmatoCAdES) {
					impronta = infoFile.getAttribute("improntaSbustato");
				}
				// se il file è già firmato ma non in CAdES allora in firmaValida passerò false in modo da forzare la firma verticale
				// l'impronta da usare sarà ovviamente quella del file con la busta
				else {
					firmaValida = "false";
				}
			}

			String hash = impronta;
			// se devo fare una firma PAdES allora dovro usare l'impronta pdf del file
			if (isFirmaPAdES) {
				hash = improntaPdf;
			}
			// e se devo fare una firma XML?
			else if (isFirmaXAdES) {
				// TODO
				// hash = improntaXml;
			}

			String fileType = "CAdES_BES";
			if (isFirmaPAdES) {
				fileType = "PDF";
			} else if (isFirmaXAdES) {
				fileType = "XAdES";
			}

			// se ho solo un file in firma come tipo di busta passerò quella corretta per il singolo file
			if (firmaSingola) {
				signEnvelopeType = fileType;
				// se il file non era firmato o aveva una firma non valida (o di tipo diverso da quella con cui devo firmare) farò sempre una firma verticale
				if (!(firmaPresente != null && firmaPresente.equalsIgnoreCase("true")) || !(firmaValida != null && firmaValida.equalsIgnoreCase("true"))) {
					firmaCongiunta = false;
				}
			}

			lMapParams.put("idFile" + count, idFile + "#" + uri + "#" + nomeFile);
			lMapParams.put("hash" + count, hash);
			lMapParams.put("fileName" + count, nomeFile);
			lMapParams.put("firmatario" + count, firmatario); // Passaggio del firmatario
			lMapParams.put("alias", alias); // Passaggio dell'alias
			lMapParams.put("versione" + count, "versione");
			lMapParams.put("fileType" + count, fileType);
			lMapParams.put("firmaPresente" + count, firmaPresente);
			lMapParams.put("firmaValida" + count, firmaValida);
			count++;
		}

		lMapParams.put("numFiles", files.size() + "");
		lMapParams.put("fileOutputProvider", "it.eng.client.applet.fileProvider.AurigaMultiHashFileOutputProvider");
		lMapParams.put("sign.envelope.type", signEnvelopeType);
		lMapParams.put("sign.envelope.type.options", "CAdES_BES,PDF,XAdES");

		if (firmaCongiunta) {
			lMapParams.put("sign.envelope.merge", "congiunta");
			lMapParams.put("sign.envelope.merge.options", "congiunta");
		}

		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		lMapParams.put("outputUrl", GWT.getHostPageBaseURL() + "springdispatcher/UploadMultiHashSignerApplet/");
		lMapParams.put("commonNameCallback", idCreated + "commonNameFunction");
		lMapParams.put("commonNameProvider", "it.eng.client.applet.cnProvider.AurigaProxyCommonNameProvider");

		this.setRedrawOnResize(true);

		/**
		 * Chiamata della nuova applet con i parametri che sono stati settati
		 */
		lFirmaCertificatoApplet = new AppletEng("FirmaCertificatoApplet", "FirmaCertificatoApplet.jnlp", lMapParams, 367, 530, true, true, this,
				new String[] {appletJarName}, "it.eng.client.applet.SmartCardApplet") {

			@Override
			public void uploadFromServlet(String file) {
				uploadFirmaEndCallback(file);
			}

			@Override
			protected void uploadAfterDatasourceCall(Record object) {
				RecordList lRecordListFile = object.getAttributeAsRecordList("files");
				Map<String, Record> files = new HashMap<String, Record>();
				if (lRecordListFile != null) {
					for (int i = 0; i < lRecordListFile.getLength(); i++) {
						Record lRecordFile = lRecordListFile.get(i);						
						files.put(lRecordFile.getAttribute("idFile"), lRecordFile);
					}
				}
				firmaCallBackWithErrors(files, commonName);
			}

			@Override
			protected Record getRecordForAppletDataSource() {
				return getRecordFirmaForAppletDataSource();
			}

			/**
			 * Funzione che viene chiamata una volta terminata l'esecuzione dell'applet per eseguire il trasferimento del file e il ritorno al chiamante
			 */
			@Override
			public void realCloseClick(Window pWindow) {
				pWindow.markForDestroy();
				if (uploadDone) {
					final WaitPopup loadWindow = new WaitPopup();
					loadWindow.setZIndex(getZIndex());
					loadWindow.show("Trasferimento file in corso");
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AppletMultiplaDatasource");
					loadWindow.setZIndex(getZIndex());
					loadWindow.show("Trasferimento file in corso");
					Record lRecord = getRecordForAppletDataSource();
					if (skipCtrlBustaFirm) {
						uploadAfterDatasourceCall(lRecord);
						loadWindow.hideFinal();
					} else {
						lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								uploadAfterDatasourceCall(object);
								loadWindow.hideFinal(); // Nascondo il popup generato
							}

							public void manageError() {
								loadWindow.hideFinal(); // Nascondo il popup generato
							};
						});
					}
				}

			}
		};

		/*
		 * Aggiungo il contenuto dell'applet all'interno della finestra che è stata creata. Se questa riga non fosse presente l'applet non avrebbe un
		 * contenitore
		 */
		addItem(lFirmaCertificatoApplet);

		// adatta l'applet alla finestra che la contiene quando viene ridimensionata la finestra
		addDragResizeStopHandler(new DragResizeStopHandler() {

			@Override
			public void onDragResizeStop(DragResizeStopEvent event) {
				markForRedraw();
				lFirmaCertificatoApplet.setWidth100();
				lFirmaCertificatoApplet.setHeight100();
				lFirmaCertificatoApplet.markForRedraw();
				setAutoSize(true);
			}
		});
	}

	public String getAppletTipoFirma() {
		return appletTipoFirmaAtti;
	}

	public boolean isFirmaCongiunta() {
		return AurigaLayout.getParametroDBAsBoolean("FIRMA_CONGIUNTA");
	}

	public boolean isAppletSignMarkEnabled() {
		return AurigaLayout.getParametroDBAsBoolean("APPLET_SIGN_MARK_ENABLED");
	}

	public boolean isActivityPanelHidden() {
		return AurigaLayout.getParametroDBAsBoolean("NASCONDI_PANNELLO_FIRMA");
	}

	private native void initCommonNameFunction(FirmaCertificatoWindow firmaCertificatoWindow, String functionName) /*-{
		$wnd[functionName] = function(value) {
			firmaCertificatoWindow.@it.eng.auriga.ui.module.layout.client.firmamultiplahash.FirmaCertificatoWindow::commonNameFunction(Ljava/lang/String;)(value);
		};
	}-*/;

	public void commonNameFunction(String commonName) {
		this.commonName = commonName;
	}

	private native void initCallBackAskForCloseFunction(FirmaCertificatoWindow firmaCertificatoWindow, String functionName) /*-{
		$wnd[functionName] = function() {
			firmaCertificatoWindow.@it.eng.auriga.ui.module.layout.client.firmamultiplahash.FirmaCertificatoWindow::callBackAskForCloseFunction()();
		};
	}-*/;

	/**
	 * Passa in questa callback solamente nel caso sia stato inserito il pin e si stia cercando di firmare il file
	 */
	public void callBackAskForCloseFunction() {
		lFirmaCertificatoApplet.realCloseClick(this);
	}

	public void uploadFirmaEndCallback(String file) {
		// Estraggo il nome + uri del file Firmato
		String[] result = file.split("#");
		String nome = URL.decodeQueryString(result[0]);
		String uri = result[1];
		String idFile = result[2];
		Record lRecord = new Record();
		lRecord.setAttribute("firmaEseguita", true);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("nomeFile", nome);
		lRecord.setAttribute("idFile", idFile);
		nomiUnivoci.put(idFile, lRecord);
	}

	protected Record getRecordFirmaForAppletDataSource() {
		RecordList files = new RecordList();
		if (nomiUnivoci != null) {
			for (String key : nomiUnivoci.keySet()) {
				files.add(nomiUnivoci.get(key));
			}
		}
		Record lRecord = new Record();
		lRecord.setAttribute("files", files);
		lRecord.setAttribute("provenienza", appletJarName);
		return lRecord;
	}
}
