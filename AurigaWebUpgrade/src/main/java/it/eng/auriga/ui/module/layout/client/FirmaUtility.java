package it.eng.auriga.ui.module.layout.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;

import it.eng.auriga.ui.module.layout.client.firma.HsmCredenzialiOTPWindows;
import it.eng.auriga.ui.module.layout.client.firma.HsmCredenzialiWindows;
import it.eng.auriga.ui.module.layout.client.firmamultipla.FirmaMultiplaWindow;
import it.eng.auriga.ui.module.layout.client.firmamultiplahash.FirmaCertificatoHybridWindow;
import it.eng.auriga.ui.module.layout.client.firmamultiplahash.FirmaCertificatoWindow;
import it.eng.auriga.ui.module.layout.client.firmamultiplahash.FirmaMultiplaHashHybridWindow;
import it.eng.auriga.ui.module.layout.client.firmamultiplahash.FirmaMultiplaHashWindow;
import it.eng.auriga.ui.module.layout.client.firmamultiplahash.SelectCertificatoFirmaHybridWindow;
import it.eng.auriga.ui.module.layout.client.firmamultiplahash.SelectCertificatoFirmaWindow;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.BrowserUtility;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class FirmaUtility {
	
	public static final String FIRMA_NON_VALIDA_ERROR = "firma non valida"; 
	public static final String FIRMA_NON_ESEGUITA_ERROR = "firma non eseguita";
	
	public static void firmaMultipla(final Record[] filesAndUd, final FirmaMultiplaCallback callback) {
		firmaMultipla(null, null, false, filesAndUd, callback);
	}

	public static void firmaMultipla(String uri, String display, InfoFileRecord infoFile, final FirmaCallback callback) {
		firmaMultipla(null, null, uri, display, infoFile, callback);
	}

	public static void firmaMultipla(final String appletTipoFirmaForzato, final String hsmTipoFirmaForzato, String uri, String display, InfoFileRecord infoFile, final FirmaCallback callback) {

		Record lRecordFile = new Record();
		lRecordFile.setAttribute("idFile", uri);
		lRecordFile.setAttribute("uri", uri);
		lRecordFile.setAttribute("nomeFile", display);
		lRecordFile.setAttribute("infoFile", infoFile);
		FirmaMultiplaCallback firmaMultiplaCallback = null;
		if (callback != null) {
			firmaMultiplaCallback = new FirmaMultiplaCallback() {

				@Override
				public void execute(Map<String, Record> files, Record[] filesAndUd) {
					Record lRecordFileFirmato = files.values().iterator().next();
					String nomeFileFirmato = lRecordFileFirmato.getAttribute("nomeFile");
					String uriFileFirmato = lRecordFileFirmato.getAttribute("uri");
					String record = JSON.encode(lRecordFileFirmato.getAttributeAsRecord("infoFile").getJsObj());
					InfoFileRecord infoFileFirmato = InfoFileRecord.buildInfoFileString(record);
					callback.execute(nomeFileFirmato, uriFileFirmato, infoFileFirmato);
				}
			};
		}
				
		Record[] filesAndUd = new Record[1];
		filesAndUd[0] = lRecordFile;
		firmaMultipla(appletTipoFirmaForzato, hsmTipoFirmaForzato, false, filesAndUd, firmaMultiplaCallback);
	}
	
//	public static void firmaMultipla(final String appletTipoFirmaForzato, final String hsmTipoFirmaForzato, final Record lRecordFile, final FirmaMultiplaCallback callback) {
//		Record[] filesAndUd = new Record[1];
//		filesAndUd[0] = lRecordFile;
//		firmaMultipla(appletTipoFirmaForzato, hsmTipoFirmaForzato, filesAndUd, callback);
//	}

//	public static void firmaMultipla(final Record lRecordFile, final FirmaMultiplaCallback callback) {
//		Record[] filesAndUd = new Record[1];
//		filesAndUd[0] = lRecordFile;
//		firmaMultipla(null, null, filesAndUd, callback);
//	}

//	public static void firmaMultipla(final Record lRecordFile, final FirmaMultiplaCommonNameCallback callback) {
//		Record[] filesAndUd = new Record[1];
//		filesAndUd[0] = lRecordFile;
//		firmaMultiplaCommonName(null, null, filesAndUd, callback);
//	}

//	public static void firmaMultipla(final String appletTipoFirmaForzato, final String hsmTipofirmaForzato, final Record lRecordFile, final FirmaMultiplaCommonNameCallback callback) {
//		Record[] filesAndUd = new Record[1];
//		filesAndUd[0] = lRecordFile;
//		firmaMultiplaCommonName(appletTipoFirmaForzato, hsmTipofirmaForzato, filesAndUd, callback);
//	}
	
	public static void firmaMultipla(final String appletTipoFirmaForzato, final String hsmTipofirmaForzato, final Record[] filesAndUd, final FirmaMultiplaCallback callback) {
		firmaMultipla(appletTipoFirmaForzato, hsmTipofirmaForzato, false, filesAndUd, callback);
	}

	public static void firmaMultipla(final String appletTipoFirmaForzato, final String hsmTipofirmaForzato, final boolean abilGestFirmaAllegatiFirmatiPIAtto, final Record[] filesAndUd, final FirmaMultiplaCallback callback) {

		if (AurigaLayout.getParametroDBAsBoolean("CONV_PDF_PRE_FIRMA")) {
			Layout.showWaitPopup("Preparazione file in corso...");
			Record lRecordFiles = new Record();
			lRecordFiles.setAttribute("files", new RecordList(filesAndUd));
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("ConversionePdfDataSource");
			lGwtRestService.addParam("SCOPO", "FIRMA");

			// Eseguo la chiamata al datasource
			lGwtRestService.call(lRecordFiles, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					Layout.hideWaitPopup();
					manageFirmaMultipla(appletTipoFirmaForzato, hsmTipofirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, object.getAttributeAsRecordList("files").toArray(), callback);
				}
			});
		} else {
			manageFirmaMultipla(appletTipoFirmaForzato, hsmTipofirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, callback);
		}

	}

	public static void firmaMultiplaCommonName(final Record[] filesAndUd, final FirmaMultiplaCommonNameCallback callback) {
		firmaMultiplaCommonName(null, null, false, filesAndUd, callback);
	}
	
	public static void firmaMultiplaCommonName(final String appletTipoFirmaForzato, final String hsmTipoFirmaForzato, final Record[] filesAndUd, final FirmaMultiplaCommonNameCallback callback) {
		firmaMultiplaCommonName(appletTipoFirmaForzato, hsmTipoFirmaForzato, false, filesAndUd, callback);
	}

	public static void firmaMultiplaCommonName(final String appletTipoFirmaForzato, final String hsmTipoFirmaForzato, final boolean abilGestFirmaAllegatiFirmatiPIAtto, final Record[] filesAndUd, final FirmaMultiplaCommonNameCallback callback) {
		if (AurigaLayout.getParametroDBAsBoolean("CONV_PDF_PRE_FIRMA")) {
			Layout.showWaitPopup("Conversione pdf in corso...");
			Record lRecordFiles = new Record();
			lRecordFiles.setAttribute("files", new RecordList(filesAndUd));
			new GWTRestService<Record, Record>("ConversionePdfDataSource").call(lRecordFiles, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					Layout.hideWaitPopup();
					manageFirmaMultiplaCommonName(appletTipoFirmaForzato, hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, object.getAttributeAsRecordList("files").toArray(), callback);
				}
			});
		} else {
			manageFirmaMultiplaCommonName(appletTipoFirmaForzato, hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, callback);
		}
	}

	/**********************************************************************************************
	 * FINE METODI PUBBLICI *
	 **********************************************************************************************/

	private static void manageFirmaMultipla(final String appletTipoFirmaForzato, final String hsmTipoFirmaForzato, boolean abilGestFirmaAllegatiFirmatiPIAtto, final Record[] filesAndUd, final FirmaMultiplaCallback callback) {
		String nomeFirmatario = "";
		boolean skipCtrlBustaFirm = false;
		sceltaTipofirmaCentralized(appletTipoFirmaForzato, hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, skipCtrlBustaFirm, nomeFirmatario, false, callback);
	}

	private static void manageFirmaMultiplaCommonName(final String appletTipoFirmaForzato, final String hsmTipoFirmaForzato, boolean abilGestFirmaAllegatiFirmatiPIAtto, final Record[] filesAndUd, final FirmaMultiplaCommonNameCallback callback) {
		String nomeFirmatario = AurigaLayout.getParametroDB("HSM_CN_FIRMATARIO");
		boolean skipCtrlBustaFirm = AurigaLayout.getParametroDBAsBoolean("SKIP_CTRL_BUSTA_FIRM");
		sceltaTipofirmaCentralized(appletTipoFirmaForzato, hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, skipCtrlBustaFirm, nomeFirmatario, true, callback);
	}

	private static void sceltaTipofirmaCentralized(final String appletTipoFirmaForzato, final String hsmTipoFirmaForzato, final boolean abilGestFirmaAllegatiFirmatiPIAtto, final Record[] filesAndUd,
			final boolean skipCtrlBustaFirm, final String nomeFirmatario, final boolean flagCommonName, final Object callbackFirma) {

		if (filesAndUd.length > 0) { // Se ho file da firmare
			final String preferenceTipoFirma = AurigaLayout.getImpostazioneFirma("tipoFirma");
			final String tipoFirmaUtilizzato;
			if (preferenceTipoFirma != null && !"".equalsIgnoreCase(preferenceTipoFirma)) {
				// Il tipo di firma da utilizzare è salvato in DB
				if ("R".equalsIgnoreCase(preferenceTipoFirma) || "A".equalsIgnoreCase(preferenceTipoFirma) || "W".equalsIgnoreCase(preferenceTipoFirma)) {
					// Se R (Remota) o A (Automatica) entro nel caso della firma HSM
					tipoFirmaUtilizzato = "HSM";
				} else {
					// Ho impostato al firma client, vedo in DB se usare APPLET o JNLP
					// Non è detto che in DB ci sia l'impo0stazione di una firma client, potrebbe esserci HSM e in quel caso lo ignoro
					if (AurigaLayout.getParametroDB("MODALITA_FIRMA").equalsIgnoreCase("APPLET")){
						tipoFirmaUtilizzato = "APPLET";
					} else {
						tipoFirmaUtilizzato = "JNLP";
					}
				}
			} else {
				tipoFirmaUtilizzato = AurigaLayout.getParametroDB("MODALITA_FIRMA");
			}
			
			
			/**
				tipoFirmaUtilizzato può essere HSM, APPLET o JNLP
				devo valorizzare appletTipoFirmaForzato e hsmTipoFirmaForzato
				i metodi standeard di firma li vedo dai parametri DB HSM_TIPO_FIRMA e APPLET_TIPO_FIRMA
				mostro la finestra se: 
				1 - se con firma applet o jnlp appletTipoFirmaForzato e APPLET_TIPO_FIRMA sono vuoti o ""
				2 - se con firma hsm hsmTipoFirmaForzato e HSM_TIPO_FIRMA sono vuoti o ""
			*/
			boolean isTipoFirmaSettato = false;
			
			if ("HSM".equalsIgnoreCase(tipoFirmaUtilizzato)){
				String hsmTipoFirmaDB = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA");
//				TC 26/05/2020 Gestione nuovo tipo di firma Are Vasta con Auriga Applicazione esterna
//				if("W".equals(preferenceTipoFirma) && !"".equalsIgnoreCase(AurigaLayout.getParametroDB("TIPO_FIRMA_ESTERNA"))) {
//					hsmTipoFirmaDB = AurigaLayout.getParametroDB("TIPO_FIRMA_ESTERNA");
//				}
				if ((hsmTipoFirmaForzato!=null && !"".equals(hsmTipoFirmaForzato)) || (hsmTipoFirmaDB!=null && !"".equals(hsmTipoFirmaDB))) {
					isTipoFirmaSettato = true;
				}
			} else if ("APPLET".equalsIgnoreCase(tipoFirmaUtilizzato) || "JNLP".equalsIgnoreCase(tipoFirmaUtilizzato)){
				String appletTipoFirmaDB = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA");
				if ((appletTipoFirmaForzato!=null && !"".equals(appletTipoFirmaForzato)) || (appletTipoFirmaDB!=null && !"".equals(appletTipoFirmaDB))) {
					isTipoFirmaSettato = true;
				}
			}
			
			boolean mostraPopupTipoFirma = true;
			if (!isTipoFirmaSettato) {
				// Se ho più di un file mostro la popup tipo firma
				if (filesAndUd.length <= 1) {
					Record recordFileDaFirmare = filesAndUd[0];
					String nomeFile = recordFileDaFirmare.getAttribute("nomeFile");
	
					if (nomeFile != null && nomeFile.toLowerCase().endsWith(".pdf")) {
						// Se il file è un pdf marcato non devo mostrare la popup tipo firma, 
						// in quanto verrà forzata una CAdES verticale						
						if (recordFileDaFirmare.getAttributeAsRecord("infoFile") != null){
							InfoFileRecord lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(recordFileDaFirmare.getAttributeAsObject("infoFile"));
							if(lInfoFileRecord != null) {
								if (lInfoFileRecord.getInfoFirmaMarca()  != null && lInfoFileRecord.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
									// Il file è marcato, non mostro la popup tipo firma
									mostraPopupTipoFirma = false;
								} else if (lInfoFileRecord.isFirmato() && !lInfoFileRecord.getInfoFirmaMarca().isBustaCrittograficaFattaDaAuriga()) {
									// La busta crittografica non è fatta da Auriga, non mostro la popup tipo firma
									mostraPopupTipoFirma = false;
								}
							}
						} 
					} else {
						// Non è un pdf, non mostro la popup tipo firma
						mostraPopupTipoFirma = false;
					}
				}
			} else {
				// Il tipo firma è forzato, non mostro la popup tipo firma
				mostraPopupTipoFirma = false;
			}
					
			if (!mostraPopupTipoFirma) {
				if("W".equals(preferenceTipoFirma)) {
					manageFirmaMultiplaWithExternalWSCentralized(appletTipoFirmaForzato, hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, true, nomeFirmatario, flagCommonName, callbackFirma, tipoFirmaUtilizzato);
				} else {
					manageFirmaMultiplaCentralized(appletTipoFirmaForzato, hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, skipCtrlBustaFirm, nomeFirmatario, flagCommonName, callbackFirma, tipoFirmaUtilizzato);
				}
			} else {
				// Leggo il tipo firma da gui
				
				//Prelevo da DB il tipo di default da settare nella popup
				String defaultTipoFirmaPopup = AurigaLayout.getParametroDB("DEFAULT_OPT_SEL_TIPO_FIRMA");
				
				SceltaTipoFirmaPopup sceltaTipoFirmaPopup = new SceltaTipoFirmaPopup(defaultTipoFirmaPopup, new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecordTipoFirma) {

						String appletTipoFirmaForzato = lRecordTipoFirma.getAttribute("tipologiaFirma");
						String hsmTipoFirmaForzato = lRecordTipoFirma.getAttribute("tipologiaFirma");
						if("W".equals(preferenceTipoFirma)) {
							manageFirmaMultiplaWithExternalWSCentralized(appletTipoFirmaForzato, hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, true, nomeFirmatario, flagCommonName, callbackFirma, tipoFirmaUtilizzato);
						} else {
							manageFirmaMultiplaCentralized(appletTipoFirmaForzato, hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, skipCtrlBustaFirm, nomeFirmatario, flagCommonName, callbackFirma, tipoFirmaUtilizzato);
						}	
					}
				});
			}
		}

	}
	
	private static void manageFirmaMultiplaWithExternalWSCentralized(final String appletTipoFirmaForzato, final String hsmTipoFirmaForzato, final boolean abilGestFirmaAllegatiFirmatiPIAtto, final Record[] filesAndUd,
			final boolean skipCtrlBustaFirm, final String nomeFirmatario, boolean flagCommonName, final Object callbackFirma, final String tipoFirmaUtilizzato) {

		final String tipoHsmFinal;
		String tipoHsm;
		final String providerHsmFromPreference = AurigaLayout.getImpostazioneFirma("provider_firma_remota");
		tipoHsm = getValoreVariabileHsmParamsAsString("hsmType", providerHsmFromPreference);
		if (tipoHsm == null || "".equalsIgnoreCase(tipoHsm)) {
			tipoHsm = AurigaLayout.getParametroDB("TIPO_HSM");
		} 
		tipoHsmFinal = tipoHsm;

		// Leggo le preferenze
		Record preimpostazioniHsmCredenzialiWindows = new Record();
		preimpostazioniHsmCredenzialiWindows.setAttribute("username", AurigaLayout.getImpostazioneFirma("userId"));
		preimpostazioniHsmCredenzialiWindows.setAttribute("usernameDelegante", AurigaLayout.getImpostazioneFirma("firmaInDelega"));
		new HsmCredenzialiWindows(preimpostazioniHsmCredenzialiWindows, true) {

			@Override
			public void onClickOkButton() {
				String username = getUsername();
				String usernameDelegante = getUsernameDelegante();
				String password = getPassword();
				String codiceOtp = getCodiceOtp();
				String certId = getCertId();
				String poterDiFirma = "";
				if(!(tipoHsmFinal != null && tipoHsmFinal.equalsIgnoreCase( "ARUBA" ))){
					poterDiFirma = getPotereDiFirma();
				}
				firmaHsmHashMultiplaWithExternalWS(hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, nomeFirmatario, skipCtrlBustaFirm, username, usernameDelegante, password, codiceOtp, certId, poterDiFirma, true, callbackFirma, this);
				markForDestroy();
			}

			@Override
			public void onClickAnnullaButton() {
				this.markForDestroy();
			}

			@Override
			public void generazioneRemotaOtpButton(String typeOpt) {
				boolean richiesteCredenzialiSeparatePerOtp =  getValoreVariabileHsmParamsAsBoolean("useDifferentCredentialForOtpRequest", providerHsmFromPreference);
				if (!richiesteCredenzialiSeparatePerOtp) {
					String username = getUsername();
					String password = getPassword();
					String certId = getCertId();
					String potereDiFirma = getPotereDiFirma();
					callRemoteOtpGenerator(username, password, certId, potereDiFirma, typeOpt);
				} else {
					Record preimpostazioniHsmCredenzialiOTPWindows = new Record();
					preimpostazioniHsmCredenzialiOTPWindows.setAttribute("otpUsername", AurigaLayout.getImpostazioneFirma("usernameRichOtp"));
					// preimpostazioniHsmCredenzialiOTPWindows.setAttribute("usernameDelegante", AurigaLayout.getImpostazioneFirma(""));
					new HsmCredenzialiOTPWindows(preimpostazioniHsmCredenzialiOTPWindows) {

						@Override
						public void generazioneRemotaOtpButton(String typeOpt) {
							String otpUsername = getOtpUsername();
							String otpPassword = getOtpPassword();
							callRemoteOtpGenerator(otpUsername, otpPassword, null, null, typeOpt);
							markForDestroy();
						}

						@Override
						public void onClickAnnullaButton() {
							this.markForDestroy();
						}
						
					};
				}
			}
			
			@Override
			public boolean nascondiListaCertificati() {
				return false;
			}
			
			@Override
			public boolean mostraUsernameDelegante() {
				return false;
			}
			
			@Override
			public boolean mostraGenerazioneRemotaOtpSms(boolean creazioneWindow) {
				return false;
			}
			
			@Override
			public boolean mostraGenerazioneRemotaOtpCall(boolean creazioneWindow) {
				return false;
			}

		};
	}

	private static void manageFirmaMultiplaCentralized(final String appletTipoFirmaForzato, final String hsmTipoFirmaForzato, final boolean abilGestFirmaAllegatiFirmatiPIAtto, final Record[] filesAndUd,
			final boolean skipCtrlBustaFirm, final String nomeFirmatario, boolean flagCommonName, final Object callbackFirma, final String tipoFirmaUtilizzato) {
		
		if (tipoFirmaUtilizzato.equalsIgnoreCase("HSM")) {

			final String tipoHsmFinal;
			String tipoHsm;
			final String providerHsmFromPreference = AurigaLayout.getImpostazioneFirma("provider_firma_remota");
			tipoHsm = getValoreVariabileHsmParamsAsString("hsmType", providerHsmFromPreference);
			if (tipoHsm == null || "".equalsIgnoreCase(tipoHsm)) {
				tipoHsm = AurigaLayout.getParametroDB("TIPO_HSM");
			} 
			tipoHsmFinal = tipoHsm;
			
			if (!isFirmaHsmAutomatica(providerHsmFromPreference)) {
				// Leggo le preferenze
				Record preimpostazioniHsmCredenzialiWindows = new Record();
				preimpostazioniHsmCredenzialiWindows.setAttribute("username", AurigaLayout.getImpostazioneFirma("userId"));
				preimpostazioniHsmCredenzialiWindows.setAttribute("usernameDelegante", AurigaLayout.getImpostazioneFirma("firmaInDelega"));
				new HsmCredenzialiWindows(preimpostazioniHsmCredenzialiWindows) {
					
					@Override
					public void onClickOkButton() {
						String username = getUsername();
						String usernameDelegante = getUsernameDelegante();
						String password = getPassword();
						String codiceOtp = getCodiceOtp();
						String certId = getCertId();
						String poterDiFirma = null;
						if(!(tipoHsmFinal != null && tipoHsmFinal.equalsIgnoreCase( "ARUBA" ))){
							poterDiFirma = getPotereDiFirma();
						}
						firmaHsmHashMultipla(hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, nomeFirmatario, skipCtrlBustaFirm, username, usernameDelegante, password, codiceOtp, certId, poterDiFirma, true, callbackFirma, this);
						markForDestroy();
					}

					@Override
					public void onClickAnnullaButton() {
						this.markForDestroy();
					}

					@Override
					public void generazioneRemotaOtpButton(String typeOpt) {
						boolean richiesteCredenzialiSeparatePerOtp =  getValoreVariabileHsmParamsAsBoolean("useDifferentCredentialForOtpRequest", providerHsmFromPreference);
						if (!richiesteCredenzialiSeparatePerOtp) {
							String username = getUsername();
							String password = getPassword();
							String certId = getCertId();
							String potereDiFirma = getPotereDiFirma();
							callRemoteOtpGenerator(username, password, certId, potereDiFirma, typeOpt);
						} else {
							Record preimpostazioniHsmCredenzialiOTPWindows = new Record();
							preimpostazioniHsmCredenzialiOTPWindows.setAttribute("otpUsername", AurigaLayout.getImpostazioneFirma("usernameRichOtp"));
							// preimpostazioniHsmCredenzialiOTPWindows.setAttribute("usernameDelegante", AurigaLayout.getImpostazioneFirma(""));
							new HsmCredenzialiOTPWindows(preimpostazioniHsmCredenzialiOTPWindows) {

								@Override
								public void generazioneRemotaOtpButton(String typeOpt) {
									String otpUsername = getOtpUsername();
									String otpPassword = getOtpPassword();
									callRemoteOtpGenerator(otpUsername, otpPassword, null, null, typeOpt);
									markForDestroy();
								}

								@Override
								public void onClickAnnullaButton() {
									this.markForDestroy();
								}
								
							};
						}
					}
					
					@Override
					public boolean nascondiListaCertificati() {
						if(tipoHsmFinal != null && tipoHsmFinal.equalsIgnoreCase("MEDAS") ){
							return false;
						} else {
							return true;
						}
					}
					
					@Override
					public boolean mostraUsernameDelegante() {
						return getValoreVariabileHsmParamsAsBoolean("attivaFirmaInDelega", providerHsmFromPreference);
					}
					
					@Override
					public boolean mostraGenerazioneRemotaOtpSms(boolean creazioneWindow) {
						if(creazioneWindow && tipoHsmFinal != null && tipoHsmFinal.equalsIgnoreCase("MEDAS") ){
							// Il tasto viene mostrato solamente una volta scelto il certificato di firma
							return false;
						} else {
							String value = AurigaLayout.getImpostazioneFirma("showSendOtpViaSms");
							return (value != null && "true".equalsIgnoreCase(value));
						}
					}
					
					@Override
					public boolean mostraGenerazioneRemotaOtpCall(boolean creazioneWindow) {
						if(creazioneWindow && tipoHsmFinal != null && tipoHsmFinal.equalsIgnoreCase("MEDAS") ){
							// Il tasto viene mostrato solamente una volta scelto il certificato di firma
							return false;
						} else {
							String value = AurigaLayout.getImpostazioneFirma("showSendOtpViaCall");
							return (value != null && "true".equalsIgnoreCase(value));
						}
					}

				};
			} else {
				// Non ho bisogno di nome utente, password e codice otp
				String username = AurigaLayout.getImpostazioneFirma("userId");
				String usernameDelegante = AurigaLayout.getImpostazioneFirma("firmaInDelega");
				String password =  AurigaLayout.getImpostazioneFirma("password");
				// Verifico se ho delle impostazioni salvate lato gui
				boolean parametriHSMFromGui = ((username != null && !"".equalsIgnoreCase(username)) || (usernameDelegante != null && !"".equalsIgnoreCase(usernameDelegante)) || (password != null && !"".equalsIgnoreCase(password)));
				firmaHsmHashMultipla(hsmTipoFirmaForzato, abilGestFirmaAllegatiFirmatiPIAtto, filesAndUd, nomeFirmatario, skipCtrlBustaFirm, username, usernameDelegante, password, null, null, null, parametriHSMFromGui, callbackFirma, null);
			}

		} else if ((!flagCommonName) && (tipoFirmaUtilizzato.equalsIgnoreCase("GRAFOMETRICA"))) {
			// Modalità di firma grafometrica della società Plug-in
			// Si tratta solo di una demo quindi non è necessario parametrizzare i parametri in un file di configurazione o in database
			// settiamo tutti i parametri necessari direttamente a codice
			Layout.showWaitPopup("Invio file da firmare in corso...");
			// invocazione del datasource per recupare il file dallo storage
			new GWTRestService<Record, Record>("FirmaGrafometricaDataSource").call(filesAndUd[0], new ServiceCallback<Record>() {

				@Override
				public void execute(final Record objectBeanDatasource) {

					JSONObject requestData = new JSONObject();

					String title = "Raccolta dati anagrafici del " + DateTimeFormat.getFormat("dd-MM-yyyy HH-mm").format(new Date());

					JSONArray arrayFirmatari = new JSONArray();
					arrayFirmatari.set(0, new JSONString("e62e8ecd-3848-4008-86fa-5ff1a538bdef"));

					requestData.put("FileName", new JSONString(objectBeanDatasource.getAttribute("nomeFileDaFirmare"))); // nome file da firmare
					requestData.put("UserGuid", new JSONString("10e0ebfc-b8f1-47be-8bb2-48f1449dc019")); // identificativo dell'utente che predispone la
																											// firma
					requestData.put("Title", new JSONString(title));
					requestData.put("Description", new JSONString("Raccolta dati anagrafici"));
					requestData.put("CaptureBiometricData", new JSONNumber(0));
					requestData.put("DocumentTypeGuid", new JSONString("c7fa832a-7933-4845-8274-c55f8a17e5e8")); // identificativo del tipo di documento
					requestData.put("UsePenForSignature", new JSONString("true"));
					requestData.put("ValidFor", new JSONNumber(2)); // modalità di firma tramite popup
					requestData.put("SignersGuid", arrayFirmatari); // array degli identificativi dei firmatari
					requestData.put("Buffer", new JSONString(objectBeanDatasource.getAttribute("base64EncodeFile"))); // stringa base64 del file

					RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, "http://161.27.146.23:4080/api/documents/pushdocument");
					requestBuilder.setHeader("Content-Type", "application/json");
					requestBuilder.setHeader("Accept", "application/json");

					try {
						// invocazione della chiamata REST passando il file e i parametri di configurazione
						requestBuilder.sendRequest(requestData.toString(), new RequestCallback() {

							@Override
							public void onResponseReceived(Request request, Response response) {

								if (response.getStatusCode() == 200) {
									final Record recordResponse = new Record(JSON.decode(response.getText()));
									// dalla response si recupera il workstepid
									Layout.hideWaitPopup();
									Record recordData = recordResponse.getAttributeAsRecord("data");
									String workStepId = recordData.getAttribute("WorkstepId");
									// in mancanza di errori
									// la risposta del metodo conterrà il workstepid che si utilizzerà per l'apertura del popup da firmare
									// apertura del popup da firmare
									HtmlFlowWindow lHtmlFlowWindow = new HtmlFlowWindow("Firma grafometrica ", "blank.png",
											"http://161.27.146.23:4082/Sign.aspx?WorkstepId=" + workStepId + "&label="
													+ objectBeanDatasource.getAttribute("base64EncodeLabel"));
									lHtmlFlowWindow.show();
									// il file firmato sarà poi inviato ai nostri web service come nuova unità documentale
									// al momento non è necessario effettuare alcun aggancio fra il file inviato e quello restituito firmato
									// nè si tiene traccia dello stato attuale del file inviato
									// al momento è sufficiente visualizzare solo la finestra per la firma
								} else {
									AurigaLayout.addMessage(new MessageBean(
											"Si &egrave; verificato un errore nella firma grafometrica del file: " + response.getStatusText(), "",
											MessageType.ERROR));
								}
							}

							@Override
							public void onError(Request request, Throwable exception) {
								// gestione degli errori ed eventuali risposte negative
								AurigaLayout.addMessage(
										new MessageBean("Si &egrave; verificato un errore nella firma grafometrica del file: " + exception.getMessage(), "",
												MessageType.ERROR));
							}
						});
					} catch (Exception exception) {
						// gestione degli errori ed eventuali risposte negative
						AurigaLayout.addMessage(new MessageBean(
								"Si &egrave; verificato un errore nella firma grafometrica del file: " + exception.getMessage(), "", MessageType.ERROR));
					}
				}
			});

		} else {
			// Sono nella firma con applet
			// Se provengo da una chiamata che ha forzato il tipo di firma uso il parametro forzato, altrimenti se esso è null o "" uso
			// il parametro in APPLET_TIPO_FIRMA
			final String appletTipoFirma = (appletTipoFirmaForzato != null && !"".equalsIgnoreCase(appletTipoFirmaForzato)) ? appletTipoFirmaForzato
					: AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA");

			String appletName = "";
			String jnplName = "";
			String appletJarName = "";
			String appletClass = "";

			// Leggo il parametro APPLET_FIRMA_SPEC
			appletName = AurigaLayout.getParametroDB("APPLET_FIRMA_SPEC");

			if (appletName != null && !"".equals(appletName)) {
				jnplName = appletName + ".jnlp";
				appletJarName = appletName + ".jar";
				appletClass = "it.eng.client.applet.SmartCardApplet";
			}

			// Se il parametro APPLET_FIRMA_SPEC NON E' valorizzato lancio l’applet MULTI HASH
			if (appletName == null || "".equals(appletName) || "SignerAppletMulti".equals(appletName)) {
				Record lRecordFiles = new Record();
				lRecordFiles.setAttribute("files", new RecordList(filesAndUd));

				// Prelevo il parametro dal database che indica se si vuole una firma che deve apparire graficamente (Pades)
				String showGraphicSignature = AurigaLayout.getParametroDB("SHOW_GRAPHIC_SIGNATURE_IN_PADES");

				/*
				 * Se il valore di showGraphicSignature è true allora devono essere aperte le due applet per eseguire la firma Pades; in caso contrario deve
				 * essere aperta l'applet normale di firma
				 */
				if ("TRUE".equalsIgnoreCase(showGraphicSignature) && "pades".equalsIgnoreCase(appletTipoFirma)) {

					// Avvio hybrid o l'applet a seconda del parametro DB
					String modalitaFirma = tipoFirmaUtilizzato;
					if (modalitaFirma == null || "".equalsIgnoreCase(modalitaFirma) || "APPLET".equalsIgnoreCase(modalitaFirma)
							|| BrowserUtility.detectIfIEBrowser()) {
						// La modalità scelta è quella dell'applet

						// Avvio della window per aprire l'applet di selezione del certificato
						SelectCertificatoFirmaWindow lSelectCertificatoFirmaWindow = new SelectCertificatoFirmaWindow() {

							/**
							 * Definizione della callback che verrà richiamata una volta terminata l'esecuzione dell'applet di selezione del certificato
							 */
							@Override
							public void letturaCertificatoCallBack(final String pin, final String firmatario, final String alias, final String metodoFirma) {

								// Senza il destroy in questa posizione non si riuscirebbe ad aprire la nuova applet per la firma
								this.destroy();

								// Setto il record che, nel datasource, verrà mappato al bean ConversionePdfConRettangoloBean
								final Record lRecordFiles = new Record();
								lRecordFiles.setAttribute("files", new RecordList(filesAndUd));

								// Setto i parametri per l'eventuale firma Pades visibile
								setParametriRettangoloFirmaPades(lRecordFiles, firmatario);

								/*
								 * Chiamata del datasource per la creazione dell'impronta del file pdf con integrato il rettangolo contenente la firma
								 */
								new GWTRestService<Record, Record>("CalcolaImpronteService").executecustom("calcolaImprontaFirmaPades", lRecordFiles,
										new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {

												/*
												 * Necessario, altrimenti lMap riferirebbe ancora all'impronta del file originale e non a quello modificato
												 */
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													Record object = response.getData()[0];
													Map<String, Record> lMap = new HashMap<String, Record>();
													for (Record lRecordFile : object.getAttributeAsRecordList("files").toArray()) {
														lMap.put(lRecordFile.getAttribute("idFile"), lRecordFile);
													}

													/*
													 * Avvio della schermata per aprire l'applet che esegue la firma a partire dall'impronta contente il
													 * riquadro della firma generata in precedenza
													 * 
													 * I parametri pin, firmatario e alias sono stati ricavati dall'esecuzione della prima applet (di
													 * selezione del certificato) e restituiti tramite la callback
													 */
													FirmaCertificatoWindow lFirmaCertificatoWindow = new FirmaCertificatoWindow(appletTipoFirma, abilGestFirmaAllegatiFirmatiPIAtto, lMap,
															skipCtrlBustaFirm, pin, firmatario, alias, metodoFirma) {

														@Override
														public void firmaCallBack(Map<String, Record> files, String commonName) {
															// Ritorno alla callback chiamante
															manageFirmaCallback(files, filesAndUd, commonName, callbackFirma);																
														}		
														
														@Override
														public Record[] getFilesAndUd() {
															return filesAndUd;
														}
													};
													lFirmaCertificatoWindow.show();
												}
											}
										});

							}
						};
						lSelectCertificatoFirmaWindow.show();

					} else {
						// CASO IN CUI SI VOGLIANO APRIRE LE RISPETTIVE FINESTRE HYBRID

						new SelectCertificatoFirmaHybridWindow() {

							@Override
							public void letturaCertificatoCallBack(final String pin, final String firmatario, final String alias, final String metodoFirma) {

								// Setto il record che, nel datasource, verrà mappato al bean ConversionePdfConRettangoloBean
								final Record lRecordFiles = new Record();
								lRecordFiles.setAttribute("files", new RecordList(filesAndUd));

								// Setto i parametri per l'eventuale firma Pades visibile
								setParametriRettangoloFirmaPades(lRecordFiles, firmatario);

								/*
								 * Chiamata del datasource per la creazione dell'impronta del file pdf con integrato il rettangolo contenente la firma
								 */
								new GWTRestService<Record, Record>("CalcolaImpronteService").executecustom("calcolaImprontaFirmaPades", lRecordFiles, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {

										/*
										 * Necessario, altrimenti lMap riferirebbe ancora all'impronta del file originale e non a quello modificato
										 */
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Record object = response.getData()[0];
											Map<String, Record> lMap = new HashMap<String, Record>();
											for (Record lRecordFile : object.getAttributeAsRecordList("files").toArray()) {
												lMap.put(lRecordFile.getAttribute("idFile"), lRecordFile);
											}

											/*
											 * Avvio della schermata per aprire il modulo che esegue la firma a partire dall'impronta contente il
											 * riquadro della firma generata in precedenza
											 * 
											 * I parametri pin, firmatario e alias sono stati ricavati dall'esecuzione del primo modulo (di
											 * selezione del certificato) e restituiti tramite la callback
											 */
											new FirmaCertificatoHybridWindow(appletTipoFirma, abilGestFirmaAllegatiFirmatiPIAtto, lMap, skipCtrlBustaFirm, pin, firmatario, alias, metodoFirma
													,object.getAttributeAsString("jSessionId")) {

												@Override
												public void firmaCallBack(Map<String, Record> files, String commonName) {
													// Ritorno alla callback chiamante
													manageFirmaCallback(files, filesAndUd, commonName, callbackFirma);																
												}
												
												@Override
												public Record[] getFilesAndUd() {
													return filesAndUd;
												}
											};
										}
									}
								});
							}
						};
					}

				} else {

					/*
					 * In questo caso il valore nel database di SHOW_GRAPHIC_SIGNATURE_IN_PADES è diverso da true, oppure la firma da utilizzare è la CAdES.
					 * Questo comporta che dev'essere visualizzato il modulo normale di firma, ovvero quello ad un solo passo
					 */

					new GWTRestService<Record, Record>("CalcolaImpronteService").call(lRecordFiles, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							Map<String, Record> lMap = new HashMap<String, Record>();
							for (Record lRecordFile : object.getAttributeAsRecordList("files").toArray()) {
								lMap.put(lRecordFile.getAttribute("idFile"), lRecordFile);
							}
							// Avvio hybrid o l'applet a seconda del parametro DB
							String modalitaFirma = tipoFirmaUtilizzato;
							if (modalitaFirma == null || "".equalsIgnoreCase(modalitaFirma) || "APPLET".equalsIgnoreCase(modalitaFirma)
									|| BrowserUtility.detectIfIEBrowser()) {

								FirmaMultiplaHashWindow lFirmaMultiplaHashWindow = new FirmaMultiplaHashWindow(appletTipoFirma, abilGestFirmaAllegatiFirmatiPIAtto, lMap, skipCtrlBustaFirm) {

									@Override
									public void firmaCallBack(Map<String, Record> files, String commonName) {
										manageFirmaCallback(files, filesAndUd, commonName, callbackFirma);											
									}
									
									@Override
									public Record[] getFilesAndUd() {
										return filesAndUd;
									}
								};
								lFirmaMultiplaHashWindow.show();

							} else {

								new FirmaMultiplaHashHybridWindow(appletTipoFirma, abilGestFirmaAllegatiFirmatiPIAtto, lMap, skipCtrlBustaFirm, object.getAttributeAsString("jSessionId")) {

									@Override
									public void firmaCallBack(Map<String, Record> files, String commonName) {
										manageFirmaCallback(files, filesAndUd, commonName, callbackFirma);											
									}
									
									@Override
									public Record[] getFilesAndUd() {
										return filesAndUd;
									}
								};

							}
						}
					});
				}
			}
			// Altrimenti si chiama l'applet MULTI STANDARD e passo i riferimenti dell'applet da usare ( appletName,jnplName, appletJarName,appletClass )
			else {
				Map<String, Record> lMap = new HashMap<String, Record>();
				for (Record lRecordFile : filesAndUd) {
					lMap.put(lRecordFile.getAttribute("idFile"), lRecordFile);
				}
				FirmaMultiplaWindow lFirmaMultiplaWindow = new FirmaMultiplaWindow(lMap, appletName, jnplName, appletJarName, appletClass) {

					@Override
					public void firmaCallBack(Map<String, Record> files, String commonName) {
						manageFirmaCallback(files, filesAndUd, commonName, callbackFirma);							
					}
					
					@Override
					public Record[] getFilesAndUd() {
						return filesAndUd;
					}
				};
				lFirmaMultiplaWindow.show();
			}
		}
	}

	public interface FirmaCallback {

		void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info);

	}

	public interface FirmaMultiplaCallback {

		void execute(Map<String, Record> files, Record[] filesAndUd);

	}

	public interface FirmaMultiplaCommonNameCallback {

		void execute(Map<String, Record> files, Record[] filesAndUd, String commonName);

	}

	private static void callRemoteOtpGenerator(String username, String password, String certId, String potereDiFirma, String typeOtp) {
		Record recordDaPassare = new Record();
		recordDaPassare.setAttribute("username", username);
		recordDaPassare.setAttribute("password", password);
		recordDaPassare.setAttribute("certId", certId);
		recordDaPassare.setAttribute("potereDiFirma", potereDiFirma);
		recordDaPassare.setAttribute("typeOtp", typeOtp);
		recordDaPassare.setAttribute("parametriHSMFromGui", true);
		recordDaPassare.setAttribute("providerHsmFromPreference", AurigaLayout.getImpostazioneFirma("provider_firma_remota"));
		
		Layout.showWaitPopup(I18NUtil.getMessages().firmaUtility_richiestaCodiceOtp());
		GWTRestDataSource dataSource = new GWTRestDataSource("FirmaHsmDataSource");
		dataSource.executecustom("richiediCodiceOtp", recordDaPassare, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Layout.hideWaitPopup();

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

					Record data = response.getData()[0];

					if (data.getAttributeAsBoolean("esitoOk")) {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firmaUtility_richiestaCodiceOtpOk(), "", MessageType.INFO));
					} else {
						String messaggio = data.getAttribute("errorMessage");
						Layout.addMessage(new MessageBean(messaggio, "", MessageType.ERROR));
					}
				}
			}
		});
	}
	
	private static void firmaHsmHashMultiplaWithExternalWS(String hsmTipoFirmaForzato, boolean abilGestFirmaAllegatiFirmatiPIAtto, final Record[] filesAndUd, final String nomeFirmatario, boolean skipControlloFirmaBusta, String username, String usernameDelegante, String password, String codiceOtp, String certId, String potereDiFirma, boolean parametriHSMFromGui, final Object callbackFirma, final Window finestraAutenticazione) {
		
		boolean successo = false;

		RecordList recordList = new RecordList();

		for (Record lRecordFile : filesAndUd) {
			recordList.add(lRecordFile);
		}

		Record recordDaPassare = new Record();
		recordDaPassare.setAttribute("listaFileDaFirmare", recordList);
		String tipoMarca = "";
		if (AurigaLayout.getParametroDBAsBoolean("HSM_MARCA_SIGNATURE")){
			tipoMarca = "HSM";
		} else {
			tipoMarca = AurigaLayout.getParametroDB("HSM_MARCA_SIGNATURE");
		}
		recordDaPassare.setAttribute("fileDaMarcare", tipoMarca );// AurigaLayout.getParametroDBAsBoolean("HSM_MARCA_SIGNATURE"));
		recordDaPassare.setAttribute("username", username);
		recordDaPassare.setAttribute("usernameDelegante", usernameDelegante);
		recordDaPassare.setAttribute("password", password);
		recordDaPassare.setAttribute("codiceOtp", codiceOtp);
		recordDaPassare.setAttribute("certId", certId);
		recordDaPassare.setAttribute("potereDiFirma", potereDiFirma);
		recordDaPassare.setAttribute("parametriHSMFromGui", parametriHSMFromGui);
		recordDaPassare.setAttribute("providerHsmFromPreference", AurigaLayout.getImpostazioneFirma("provider_firma_remota"));
		recordDaPassare.setAttribute("useExternalWS", true);
		recordDaPassare.setAttribute("tipofirma", AurigaLayout.getParametroDB("TIPO_FIRMA_ESTERNA"));

		// Ricavo gli eventuali parametri del posizionamento della firma Pades
		setParametriRettangoloFirmaPades(recordDaPassare, "");

		Layout.showWaitPopup("Firma file in corso...");
		GWTRestDataSource dataSource = new GWTRestDataSource("FirmaHsmDataSource");
		dataSource.addParam("skipControlloFirmaBusta", "" + skipControlloFirmaBusta);

		// Inserisco il parametro di forzatura dato dagli atti
		dataSource.addParam("HSM_TIPO_FIRMA_FORZATO", hsmTipoFirmaForzato);
		// Inserisco il parametro per la gestione alllegati firmati parte integrante negli atti
		dataSource.addParam("GESTIONE_FIRMA_ALLEGATI_FIRMATI_PI_ATTO", abilGestFirmaAllegatiFirmatiPIAtto + "");

		// Chiamata del metodo del datasource
		dataSource.executecustom("firmaHsmHashMultipla", recordDaPassare, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Layout.hideWaitPopup();

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

					Record data = response.getData()[0];

					if (data.getAttributeAsBoolean("esitoOk")) {
						final Record[] fileFirmati = data.getAttributeAsRecordArray("fileFirmati");
						if (fileFirmati.length > 0) {

							final Map<String, Record> files = new HashMap<String, Record>();
							for (Record lRecordFile : fileFirmati) {
								lRecordFile.setAttribute("firmaEseguita", true);
								files.put(lRecordFile.getAttribute("idFile"), lRecordFile);
							}

							manageFirmaCallback(files, filesAndUd, nomeFirmatario, callbackFirma);
							
							if (finestraAutenticazione != null){
								// La finestra potrebbe esere già stata chiusa
								try {
									finestraAutenticazione.markForDestroy();
								}catch (Exception e) {
								}
							}
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().firmaUtility_fileFirmatoOk(), "", MessageType.INFO));
						}

					} else {
						String messaggio = data.getAttribute("errorMessage");
						Layout.addMessage(new MessageBean(messaggio, "", MessageType.ERROR));
					}
				}
			}
		});
	}

	private static void firmaHsmHashMultipla(String hsmTipoFirmaForzato, boolean abilGestFirmaAllegatiFirmatiPIAtto, final Record[] filesAndUd, final String nomeFirmatario, boolean skipControlloFirmaBusta, String username, String usernameDelegante, String password, String codiceOtp, String certId, String potereDiFirma, boolean parametriHSMFromGui, final Object callbackFirma, final Window finestraAutenticazione) {
		
		boolean successo = false;

		RecordList recordList = new RecordList();

		for (Record lRecordFile : filesAndUd) {
			recordList.add(lRecordFile);
		}

		Record recordDaPassare = new Record();
		recordDaPassare.setAttribute("listaFileDaFirmare", recordList);
		String tipoMarca = "";
		if (AurigaLayout.getParametroDBAsBoolean("HSM_MARCA_SIGNATURE")){
			tipoMarca = "HSM";
		} else {
			tipoMarca = AurigaLayout.getParametroDB("HSM_MARCA_SIGNATURE");
		}
		recordDaPassare.setAttribute("fileDaMarcare", tipoMarca );// AurigaLayout.getParametroDBAsBoolean("HSM_MARCA_SIGNATURE"));
		recordDaPassare.setAttribute("username", username);
		recordDaPassare.setAttribute("usernameDelegante", usernameDelegante);
		recordDaPassare.setAttribute("password", password);
		recordDaPassare.setAttribute("codiceOtp", codiceOtp);
		recordDaPassare.setAttribute("certId", certId);
		recordDaPassare.setAttribute("potereDiFirma", potereDiFirma);
		recordDaPassare.setAttribute("parametriHSMFromGui", parametriHSMFromGui);
		recordDaPassare.setAttribute("providerHsmFromPreference", AurigaLayout.getImpostazioneFirma("provider_firma_remota"));

		// Ricavo gli eventuali parametri del posizionamento della firma Pades
		setParametriRettangoloFirmaPades(recordDaPassare, "");

		Layout.showWaitPopup("Firma file in corso...");
		GWTRestDataSource dataSource = new GWTRestDataSource("FirmaHsmDataSource");
		dataSource.addParam("skipControlloFirmaBusta", "" + skipControlloFirmaBusta);

		// Inserisco il parametro di forzatura dato dagli atti
		dataSource.addParam("HSM_TIPO_FIRMA_FORZATO", hsmTipoFirmaForzato);
		// Inserisco il parametro per la gestione alllegati firmati parte integrante negli atti
		dataSource.addParam("GESTIONE_FIRMA_ALLEGATI_FIRMATI_PI_ATTO", abilGestFirmaAllegatiFirmatiPIAtto + "");

		// Chiamata del metodo del datasource
		dataSource.executecustom("firmaHsmHashMultipla", recordDaPassare, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Layout.hideWaitPopup();

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

					Record data = response.getData()[0];

					if (data.getAttributeAsBoolean("esitoOk")) {
						final Record[] fileFirmati = data.getAttributeAsRecordArray("fileFirmati");
						if (fileFirmati.length > 0) {

							final Map<String, Record> files = new HashMap<String, Record>();
							for (Record lRecordFile : fileFirmati) {
								lRecordFile.setAttribute("firmaEseguita", true);
								files.put(lRecordFile.getAttribute("idFile"), lRecordFile);
							}

							manageFirmaCallback(files, filesAndUd, nomeFirmatario, callbackFirma);
							
							if (finestraAutenticazione != null){
								// La finestra potrebbe esere già stata chiusa
								try {
									finestraAutenticazione.markForDestroy();
								}catch (Exception e) {
								}
							}
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().firmaUtility_fileFirmatoOk(), "", MessageType.INFO));
						}

					} else {
						String messaggio = data.getAttribute("errorMessage");
						Layout.addMessage(new MessageBean(messaggio, "", MessageType.ERROR));
						SC.warn(messaggio);
					}
				}
			}
		});
	}
	
	// Metodo centralizzato per la callback della firma
	public static void manageFirmaCallback(final Map<String, Record> files, final Record[] filesAndUd, final String commonName, final Object callback) {
		RecordList filesAndUdRecordList = new RecordList();
		for (Record lRecordFile : filesAndUd) {
			filesAndUdRecordList.add(lRecordFile);
		}
		
		Record recordDaPassare = new Record();
		recordDaPassare.setAttribute("fileVerPreFirma", filesAndUdRecordList);
		recordDaPassare.setAttribute("fileFirmati", files);
		
		GWTRestDataSource dataSource = new GWTRestDataSource("CalcolaFirmaDatasource");
		dataSource.executecustom("aggiornaInfoFileFirmati", recordDaPassare, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					Record fileFirmatiAggiornati = data.getAttributeAsRecord("fileFirmati");
					// Riverso gli info file di fileFirmatiAggiornati su files
					Set<String> setIdFile = files.keySet();
					for (String idFile : setIdFile) {
						Record fileFirmatoAggiornato = fileFirmatiAggiornati.getAttributeAsRecord(idFile);
						files.get(idFile).setAttribute("infoFile", fileFirmatoAggiornato.getAttributeAsRecord("infoFile"));
					} 
					
					if (callback != null) {
						if (callback instanceof FirmaMultiplaCommonNameCallback) {
							((FirmaMultiplaCommonNameCallback) callback).execute(files, filesAndUd, commonName);
						} else if (callback instanceof FirmaMultiplaCallback) {
							((FirmaMultiplaCallback) callback).execute(files, filesAndUd);
						}
					}
				}
			}
		});	
		
	}
	
	// Metodo centralizzato per la gestione degli errori nella callback della firma
	public static void manageFirmaCallBackWithErrors(final Map<String, Record> files, Record[] filesAndUd, final ServiceCallback<Map<String, Record>> callback) {
		Map<String, String> errors = new HashMap<String, String>();
		for(String key : files.keySet()) {
			Record lRecordFile = files.get(key);
			boolean firmaEseguita = lRecordFile.getAttributeAsBoolean("firmaEseguita") != null && lRecordFile.getAttributeAsBoolean("firmaEseguita");
			Record infoFile = lRecordFile.getAttributeAsRecord("infoFile");
			if(infoFile != null) {
				boolean firmato = infoFile.getAttribute("firmato") != null && infoFile.getAttribute("firmato").equalsIgnoreCase("true");
				boolean firmaValida = infoFile.getAttribute("firmaValida") != null && infoFile.getAttribute("firmaValida").equalsIgnoreCase("true");
				if (!firmaEseguita || !firmato){							
					errors.put(lRecordFile.getAttribute("idFile"), FIRMA_NON_ESEGUITA_ERROR);
				} else if (!firmaValida){							
					errors.put(lRecordFile.getAttribute("idFile"), FIRMA_NON_VALIDA_ERROR);
				}
			} else if (!firmaEseguita){							
				errors.put(lRecordFile.getAttribute("idFile"), FIRMA_NON_ESEGUITA_ERROR);							
			}			
		}
		// Se va in errore l'upload di uno o più file dopo la firma, l'applet da' comunque il messaggio che tutti i file sono stati firmati con successo
		// Se poi qualcuno di questi è andato a buon fine arriva fino a qui, altrimenti chiude l'applet e non fa niente senza dare nessun errore 
		if(files.size() == 1) {
			if(errors.size() == 0) {
				if(callback != null) {
					callback.execute(files);
				}				
			} else if(errors.size() == 1){
				Layout.addMessage(new MessageBean("Il file è andato in errore durante la firma o la firma risulta non valida", "", MessageType.ERROR));				
			} 
		} else {
			if(errors.size() == 0) {
				if(callback != null) {
					callback.execute(files);
				}
			} else if(errors.size() == files.size()){
				Layout.addMessage(new MessageBean("Tutti i file sono andati in errore durante la firma o la firma risulta non valida", "", MessageType.ERROR));				
			} else {			
//				Layout.addMessage(new MessageBean(errors.size() + " file su " + files.size() + " sono andati in errore durante la firma o la firma risulta non valida", "", MessageType.WARNING));
				String askMessage = errors.size() + " file su " + files.size() + " sono andati in errore durante la firma o la firma risulta non valida. Vuoi continuare?";
				SC.ask("ATTENZIONE", askMessage, new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {
						if(value) {
							if(callback != null) {
								callback.execute(files);
							}
						}
					}
				});				
			}
		}	
	}	
	
	private static boolean isFirmaHsmAutomatica(String providerHsm) {
		// Controllo se ho le preferenze di firma
		String preferenceTipoFirma = AurigaLayout.getImpostazioneFirma("tipoFirma");
		if (preferenceTipoFirma != null && "A".equalsIgnoreCase(preferenceTipoFirma)) {
			return true;
		}else if (preferenceTipoFirma != null && ("R".equalsIgnoreCase(preferenceTipoFirma)|| "W".equalsIgnoreCase(preferenceTipoFirma))) {
			return false;
		}else {
			return !getValoreVariabileHsmParamsAsBoolean("requireAuthentication", providerHsm);
		}
	}
	
	/**
	 * 
	 * Metodo che estrae dai parametri XML il valore del nodo ValoreSemplice legato al nodo con nome specificato da parametro in ingresso
	 * 
	 * Il file XML ha la seguente struttura
	 * 
	 * <?xml version="1.0" encoding="ISO-8859-1"?>
	 * 
	 * <SezioneCache>
	 * 
	 * <Variabile> ... </Variabile>
	 * 
	 * <Variabile>
	 * 
	 * <Nome>nomeVariabile</Nome>
	 * 
	 * <ValoreSemplice>stringa</ValoreSemplice>
	 * 
	 * </Variabile>
	 * 
	 * ...
	 * @param nomeVariabile Il nome del nodo da cui ricavarne il valore
	 * @return il valore della variabile
	 */
	public static String getValoreVariabileHsmParamsAsString(String nomeVariabile, String providerHsm) {
		
		String parametriHsmXml;
		if (providerHsm != null && !"".equalsIgnoreCase(providerHsm)){
			parametriHsmXml = AurigaLayout.getParametroDB("HSM_PARAMETERS_" + providerHsm);
		} else {
			parametriHsmXml = AurigaLayout.getParametroDB("HSM_PARAMETERS");
		}

		// Faccio il parsing delle impostazioni
		Document messageDom = XMLParser.parse(parametriHsmXml);
		// Ottengo la lista dei nodi del documento XML
		NodeList listaNodiNome = messageDom.getElementsByTagName("Nome");
		String returnValue = "false";
		// Scorro la lista dei nodi, per cercare quello con il nome desiderato
		for (int i = 0; i < listaNodiNome.getLength(); i++) {
			Node nodo = listaNodiNome.item(i);
			if (nodo.getFirstChild().getNodeValue().equalsIgnoreCase(nomeVariabile)) {
				// Ho trovato il nodo, vado nel nodo padre per prendere il figlio ValoreSemplice
				Node nodoPadre = nodo.getParentNode();
				// Scorro tutti i figli del padre, per cercare il ValoreSemplice associato
				for (int j = 0; j < nodoPadre.getChildNodes().getLength(); j++) {
					String nodeValue = nodoPadre.getChildNodes().item(j).getNodeName();
					if (nodeValue.equalsIgnoreCase("ValoreSemplice")) {
						// Estraggo il valore del nodo ValoreSemplice
						returnValue = nodoPadre.getChildNodes().item(j).getFirstChild().getNodeValue();
					}
				}
			}
		}
		return returnValue;
	}
	
	/**
	 * 
	 * Metodo che estrae dai parametri XML il valore del nodo ValoreSemplice legato al nodo con nome specificato da parametro in ingresso
	 * 
	 * Il file XML ha la seguente struttura
	 * 
	 * <?xml version="1.0" encoding="ISO-8859-1"?>
	 * 
	 * <SezioneCache>
	 * 
	 * <Variabile> ... </Variabile>
	 * 
	 * <Variabile>
	 * 
	 * <Nome>nomeVariabile</Nome>
	 * 
	 * <ValoreSemplice>booleano</ValoreSemplice>
	 * 
	 * </Variabile>
	 * 
	 * ...
	 * @param nomeVariabile Il nome del nodo da cui ricavarne il valore
	 * @return il valore booleano della variabile
	 */
	public static boolean getValoreVariabileHsmParamsAsBoolean(String nomeVariabile, String providerHsm) {
	
		return Boolean.valueOf(getValoreVariabileHsmParamsAsString(nomeVariabile, providerHsm));
	}

	private static void setParametriRettangoloFirmaPades(Record recordDaPassare, String firmatario) {
		String parametriRettangoloFirmaJson = AurigaLayout.getParametroDB("POSITION_GRAPHIC_SIGNATURE_IN_PADES");
		if ((parametriRettangoloFirmaJson != null) && (!"".equalsIgnoreCase(parametriRettangoloFirmaJson.trim()))) {
			Record[] recordArray = Record.convertToRecordArray(JSON.decode(parametriRettangoloFirmaJson));
			if ((recordArray != null) && (recordArray.length > 0)) {
				Record parametriRettangoloFirmaPades = recordArray[0];
				parametriRettangoloFirmaPades.setAttribute("firmatario", firmatario);
				recordDaPassare.setAttribute("rettangoloFirmaPades", parametriRettangoloFirmaPades);
			}
		}
	}

}
