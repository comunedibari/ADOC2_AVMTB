package it.eng.hsm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.FileDaFirmare;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.TipoFirmaHsm;
import it.eng.auriga.ui.module.layout.shared.bean.WSEndPointArubaHsmBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.hsm.cades.FirmaCadesUtil;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.cert.CertBean;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.marca.MarcaResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.config.ArubaConfig;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.config.InfoCertConfig;
import it.eng.hsm.client.config.MedasConfig;
import it.eng.hsm.client.config.PkBoxConfig;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.option.SignOption;
import it.eng.hsm.pades.FirmaPadesUtil;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.tz.avtmb.integration.client.StampsignClientService;
import it.eng.tz.avtmb.integration.client.StampsignClientServiceHelper;
import it.eng.tz.avtmb.integration.client.StampsignClientServiceImpl;
import it.eng.tz.avtmb.integration.client.dto.SignType;
import it.eng.tz.avtmb.integration.client.dto.StampSignAuthRequest;
import it.eng.tz.avtmb.integration.client.dto.StampSignAuthResponse;
import it.eng.tz.avtmb.integration.client.dto.StampSignRequest;
import it.eng.tz.avtmb.integration.client.dto.StampSignResponse;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;


/**
 * 
 * @author Federico Cacco
 * 
 *         Classe di utility per firma HSM, ricavata dalla vecchia classe it.eng.arubaHsm.ArubaHsmClient
 *
 */
public class HsmBaseUtility {

	private static final Logger log = Logger.getLogger(HsmBaseUtility.class);

    private static StampsignClientServiceHelper helper;
    private static StampsignClientService stampsignClientService;
    private static Properties properties;
	
	public static CertResponseBean richiediListaCertificati(FirmaHsmBean bean, HttpSession session) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("richiediListaCertificati inizio " + formatter.format(dataCorrente));

		// Creo il client per il retrieve dei certificati
		Hsm hsmClient = HsmClientFactory.getHsmClient(session, bean);
		
		// Ottengo la lista dei certificati
		CertResponseBean certResponseBean = hsmClient.getUserCertificateList();
		MessageBean message = certResponseBean.getMessageBean();
		if ((message != null) && ((message.getStatus() != null) && (message.getStatus().equals(ResponseStatus.OK)))) {
			dataCorrente = new Date();
			log.debug("remoteOtpGenerator fine " + formatter.format(dataCorrente));
			return certResponseBean;
		}else{
			String errorMessage = "Errore nella richiesta dei certificati disponibili";
			if ((message != null) && (message.getDescription() != null) && !"errore sconosciuto".equalsIgnoreCase(message.getDescription())) {
				errorMessage += ". " + message.getDescription();
			} else {
				errorMessage += ". " + "Verificare i dati inseriti";
			}
			throw new HsmClientSignatureException(errorMessage);
		}
	}

//	public static OtpResponseBean richiediCodiceOTP(FirmaHsmBean bean, HttpSession session) throws Exception {
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		Date dataCorrente = null;
//		dataCorrente = new Date();
//		log.debug("remoteOtpGenerator inizio " + formatter.format(dataCorrente));
//
//		// Creo il client per il retrieve dei certificati
//		HsmType hsmType = HsmType.valueOf(bean.getHsmType());
//		String username = bean.getUsername();
//		String password = bean.getPassword();
//		String certId = bean.getCertId();
//		String potereDiFirma = bean.getPotereDiFirma();
//		String strTypeOpt = bean.getTypeOtp();
//						
//		// Ottengo la lista dei certificati		
//		List<CertBean> certBeanList = null;
//		CertResponseBean certResponseBean = null;
//		Hsm hsmClient = null;
//		if (certId != null && !"".equalsIgnoreCase(certId)){
//			// Ho già selezionato il certificato con cui effettuare la firma, lo devo solamente recuperare			
//			hsmClient = HsmClientFactory.getHsmClient(hsmType, session, username, password, null, null,  certId, potereDiFirma, strTypeOpt);
//			certResponseBean = hsmClient.getUserCertificateById();
//			certBeanList = certResponseBean.getCertList();
//		}else{
//			hsmClient = HsmClientFactory.getHsmClient(hsmType, session, username, password);
//			// Ottengo la lista dei certificati
//			certResponseBean = hsmClient.getUserCertificateList();
//			certBeanList = certResponseBean.getCertList();
//		}
//		
//		MessageBean message = certResponseBean.getMessageBean();
//		if ((message != null) && ((message.getStatus() != null) && (message.getStatus().equals(ResponseStatus.OK)))) {
//			// Invio la richiesta di codice OTP
//			if (certBeanList != null && certBeanList.size() > 0) {
//				// Estraggo il certificato di firma
//				CertBean certBean = certBeanList.get(0);
//				String certificateSerialNumber = certBean.getSerialNumber();
//				
//				// Creo il client per la firma HSM
//				hsmClient = HsmClientFactory.getHsmClient(hsmType, session, username, password, null, certificateSerialNumber, certId, potereDiFirma, strTypeOpt);
//			
//				// Invio la richiesta del codice OTP
//				OtpResponseBean otpResponseBean = hsmClient.richiediOTP();
//				message = otpResponseBean.getMessage();
//				if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
//					log.error("Errore: - " + message.getCode() + " " + message.getDescription());
//					throw new HsmClientSignatureException(message.getDescription());
//				}
//				dataCorrente = new Date();
//				log.debug("remoteOtpGenerator fine " + formatter.format(dataCorrente));
//				return otpResponseBean;
//			} else {
//				log.error("Non è stato trovato nessun certificato valido per la richiesta del codice OTP");
//				throw new HsmClientSignatureException("Non è stato trovato nessun certificato valido per la richiesta del codice OTP");
//			}
//		}else{
//			String errorMessage = "Errore nella richiesta del codice OTP";
//			if ((message != null) && (message.getDescription() != null) && !"errore sconosciuto".equalsIgnoreCase(message.getDescription())) {
//				errorMessage += ". " + message.getDescription();
//			} else {
//				errorMessage += ". " + "Verificare i dati inseriti";
//			}
//			throw new HsmClientSignatureException(errorMessage);
//		}
//	}
	
	public static OtpResponseBean richiediCodiceOTP(FirmaHsmBean bean, HttpSession session) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("remoteOtpGenerator inizio " + formatter.format(dataCorrente));

		// Creo il client per il retrieve dei certificati		
		Hsm hsmClient = HsmClientFactory.getHsmClient(session, bean);
	
		// Invio la richiesta del codice OTP
		OtpResponseBean otpResponseBean = hsmClient.richiediOTP();
		MessageBean message = otpResponseBean.getMessage();
		if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
			log.error("Errore: - " + message.getCode() + " " + message.getDescription());
			throw new HsmClientSignatureException(message.getDescription());
		}
		dataCorrente = new Date();
		log.debug("remoteOtpGenerator fine " + formatter.format(dataCorrente));
		return otpResponseBean;
	}

	public static FirmaHsmBean firmaHashMultipla(FirmaHsmBean bean, HttpSession session) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("firmaCadesHashMultipla inizio " + formatter.format(dataCorrente));

		if(bean.getUseExternalWS()) {
//		TC 26/05/2020 Gestione chiamata ai servizi di Area vasta Bari che a sua volta effettua la firma ARUBA
			StorageService storageService = StorageImplementation.getStorage();
			if(properties == null) {
				try {
					loadProperties();				}
				catch (Exception e) {
					throw new HsmClientSignatureException("Firma non eseguita. Verificare le configurazioni per la firma");
				}				
			}
			List<FileDaFirmare> fileFirmati = new ArrayList<FileDaFirmare>();
			try {
				String sessionId = null;
				boolean openSession = false;
				boolean closeSession = false;

				helper = new StampsignClientServiceHelper(properties);
				stampsignClientService = new StampsignClientServiceImpl(properties);
				
				StampSignAuthRequest authRequest = helper.generateStampSignAuthRequest(properties.getProperty("area.vasta.stampsign.aoo"));
		        StampSignAuthResponse authResponse = stampsignClientService.stampSignAuth(authRequest);
		        if (authResponse.getErrore() != null){
		        	throw new HsmClientSignatureException(" " + authResponse.getErrore().getMessaggio());
		        }
		        String accessToken = authResponse.getToken().getAccessToken().toString();
		        int i=0;
		        String otpCode = (bean.getCodiceOtp() == null) ? "" : bean.getCodiceOtp();
		        
				for (FileDaFirmare fileDaFirmareDaLista : bean.getListaFileDaFirmare()) {
					i++;
					if(bean.getListaFileDaFirmare().size() > 1) {
						openSession = (i == 1) ? true : false;
						closeSession = (bean.getListaFileDaFirmare().size() == i) ? true : false;
						if(sessionId != null) {
							otpCode = "";
						}
					}
//					String uriFileTemp;
//					if(i==1) {
//						uriFileTemp = "C:\\Test.pdf";
//					}
//					else {
//						uriFileTemp = "C:\\Test PDF_A.pdf";
//					}
//					File fileDaFirmare = new File(uriFileTemp);
					
					String uriFile = fileDaFirmareDaLista.getUri();						
					File fileDaFirmare = storageService.getRealFile(uriFile);
					dataCorrente = new Date();
					log.debug("Recupero i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
	
					byte[] bytesFileDaFirmare = getFileBytes(fileDaFirmare);
					dataCorrente = new Date();
					log.debug("Ho recuperato i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
	
					byte[] bytesFileFirmato = null;
			        UUID uuidRequest = UUID.randomUUID();
			        String stampsignSigner = properties.getProperty("area.vasta.stampsign.stampsignSigner");
//			        StampSignRequest signRequest = helper.fillSignRequest(bytesFileDaFirmare, uuidRequest, stampsignSigner, otpCode, SignType.fromString("PADES"));
			        StampSignRequest signRequest = helper.fillSignRequest(bytesFileDaFirmare, uuidRequest, stampsignSigner, otpCode,
			        			SignType.fromString(bean.getTipofirma().toUpperCase()), 
			        			sessionId, openSession, closeSession);
			        
			        StampSignResponse response = stampsignClientService.invokeStampSign(accessToken,signRequest);
			        if (response.getErrore() != null){
			            throw new HsmClientSignatureException(" " + response.getErrore().getMessaggio());
			        }
			        if(sessionId == null && response.getRisultato().getSessionId() != null) {
			        	sessionId = response.getRisultato().getSessionId().toString();
			        }
			        bytesFileFirmato = response.getRisultato().getDownloadFileContent();
//			        String signedPath = uriFileTemp.replace(".pdf", "-SIGNED.pdf").replace("C:", "D:");
//			        try (
//			        		FileOutputStream fos = new FileOutputStream(signedPath)) {
//			        	fos.write(bytesFileFirmato);
//			        }

			        dataCorrente = new Date();
					log.debug("Ho recuperato i bytes del file firmato - " + formatter.format(dataCorrente));

					if (bytesFileFirmato != null) {
						dataCorrente = new Date();
						log.debug("Creo il file firmato - " + formatter.format(dataCorrente));
						fileFirmati.add(creaFileFirmato(null, fileDaFirmareDaLista, bytesFileFirmato, null,
								false, fileDaFirmareDaLista.getTipoFirmaHsm().getDescrizione()));
						dataCorrente = new Date();
						log.debug("Ho creato il file firmato - " + formatter.format(dataCorrente));
					}
				}
	
				bean.setFileFirmati(fileFirmati);	
			} 
			catch (HsmClientSignatureException e) {
				log.debug("Errore nella firma remota", e);
				throw e;
			}
			catch (Exception e) {
				log.debug("Errore nella firma remota", e);
				throw new HsmClientSignatureException("Firma non eseguita. Verificare i dati inseriti");
			}
		}
		else {
		
			// Verifico se le eventuali firme Pades devono essere visualizzate
			boolean visualizzaFirmaPades = ParametriDBUtil.getParametroDBAsBoolean(session, "SHOW_GRAPHIC_SIGNATURE_IN_PADES");
	
			String certId = bean.getCertId();
			
			CertResponseBean certResponseBean = null;
			Hsm hsmClient = null;
			// Creo il client per il retrieve dei certificati
			hsmClient = HsmClientFactory.getHsmClient(session, bean);
			if (certId != null && !"".equalsIgnoreCase(certId)){
				// Ho già selezionato il certificato con cui effettuare la firma, lo devo solamente recuperare			
				certResponseBean = hsmClient.getUserCertificateById();
			}else{
				// Ottengo la lista dei certificati
				certResponseBean = hsmClient.getUserCertificateList();
			}
			MessageBean messageBean = certResponseBean.getMessageBean();
	
			if ((messageBean != null) && (messageBean.getStatus() != null) && (messageBean.getStatus().equals(ResponseStatus.OK))){
				List<CertBean> certBeanList = certResponseBean.getCertList();
				if (certBeanList != null && certBeanList.size() > 0) {
					// Estraggo il certificato di firma
					CertBean certBean = certBeanList.get(0);
					String certificateSerialNumber = certBean.getSerialNumber();
					bean.setCertSerialNumber(certificateSerialNumber);
					X509Certificate cert;
					
					try {
						// Creo il client per la firma HSM
						hsmClient = HsmClientFactory.getHsmClient(session, bean);
						if (hsmClient.getHsmConfig().getClientConfig().isRequireSignatureInSession()) {
							// Se richiesto dal provider, devo eseguire tutte le firme nella stesxsza sessione. Ne apro una e salvo il suo id
							try {
								SessionResponseBean sessioneResponseBean = hsmClient.apriSessioneFirmaMultipla();
								MessageBean message = sessioneResponseBean.getMessage();
								if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
									log.error("Errore nell'apertura della sessione di firma: " + message.getCode() + " - " + message.getDescription()  + " " + getHsmUserDescription(hsmClient, session));
									String errorMessage = "Errore nell'apertura della sessione di firma";
									if ((message != null) && (message.getDescription() != null) && !"errore sconosciuto".equalsIgnoreCase(message.getDescription())) {
										errorMessage += ". " + message.getDescription();
									} else {
										errorMessage += ". " + "Verificare i dati inseriti";
									}
									throw new HsmClientSignatureException(errorMessage);
								}
								hsmClient.getHsmConfig().getClientConfig().setIdSession(sessioneResponseBean.getSessionId());
		
							} catch (UnsupportedOperationException e) {
								log.debug("Il client non supporta l'apertura della sessione. L'operazione verrà ignorata", e);
							}
						}
		
						dataCorrente = new Date();
						log.debug("Recupero il certificato con id " + certBean.getCertId() + " - " + formatter.format(dataCorrente));
						cert = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(certBean.getCertValue()));
		
						StorageService storageService = StorageImplementation.getStorage();
		
						List<FileDaFirmare> fileFirmati = new ArrayList<FileDaFirmare>();
						
						if(!hsmClient.getHsmConfig().getHsmType().equals(HsmType.PKBOX)) {
		
							for (FileDaFirmare fileDaFirmareDaLista : bean.getListaFileDaFirmare()) {
			
								String uriFile = fileDaFirmareDaLista.getUri();
			
								File fileDaFirmare = storageService.getRealFile(uriFile);
			
								dataCorrente = new Date();
								log.debug("Recupero i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
			
								byte[] bytesFileDaFirmare = getFileBytes(fileDaFirmare);
								dataCorrente = new Date();
								log.debug("Ho recuperato i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
			
								byte[] bytesFileFirmato = null;
			
								if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_PADES)) {
									bytesFileFirmato = FirmaPadesUtil.generate(bytesFileDaFirmare, cert, hsmClient, visualizzaFirmaPades, bean.getRettangoloFirmaPades());
								} else if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_CONGIUNTA)) {
									bytesFileFirmato = FirmaCadesUtil.generaP7mCongiunta(bytesFileDaFirmare, cert, hsmClient);
								} else if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_VERTICALE)) {
									bytesFileFirmato = FirmaCadesUtil.generaP7mVerticale(bytesFileDaFirmare, cert, hsmClient);
								}
								dataCorrente = new Date();
								log.debug("Ho recuperato i bytes del file firmato - " + formatter.format(dataCorrente));
			
								if (bytesFileFirmato != null) {
									dataCorrente = new Date();
									log.debug("Creo il file firmato - " + formatter.format(dataCorrente));
									fileFirmati.add(creaFileFirmato(hsmClient, fileDaFirmareDaLista, bytesFileFirmato, bean.getFileDaMarcare(),
											bean.isSkipControlloFirmaBusta(), fileDaFirmareDaLista.getTipoFirmaHsm().getDescrizione()));
									dataCorrente = new Date();
									log.debug("Ho creato il file firmato - " + formatter.format(dataCorrente));
								}
							}
			
							bean.setFileFirmati(fileFirmati);
							
						} else {
				
							Map<Integer, Integer> mappaPosFilePades = new HashMap<Integer, Integer>();
							Map<Integer, Integer> mappaPosFileCadesVerticale = new HashMap<Integer, Integer>();
							Map<Integer, Integer> mappaPosFileCadesCongiunta = new HashMap<Integer, Integer>();
							
							int posFilePades = 0;
							int posFileCadesVerticale = 0;
							int posFileCadesCongiunta = 0;
							
							List<byte[]> fileDaFirmarePades = new ArrayList<>();
							List<byte[]> fileDaFirmareCadesCongiunta = new ArrayList<>();
							List<byte[]> fileDaFirmareCadesVerticale = new ArrayList<>();
							
							List<byte[]> fileFirmatiPades = new ArrayList<>();
							List<byte[]> fileFirmatiCadesCongiunta = new ArrayList<>();
							List<byte[]> fileFirmatiCadesVerticale = new ArrayList<>();
							
							for (int pos = 0; pos < bean.getListaFileDaFirmare().size(); pos++) {
								FileDaFirmare fileDaFirmareDaLista = bean.getListaFileDaFirmare().get(pos);
								
								String uriFile = fileDaFirmareDaLista.getUri();
			
								File fileDaFirmare = storageService.getRealFile(uriFile);
			
								dataCorrente = new Date();
								log.debug("Recupero i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
			
								byte[] bytesFileDaFirmare = getFileBytes(fileDaFirmare);
								dataCorrente = new Date();
								log.debug("Ho recuperato i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
								
								if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_PADES)) {
									log.debug("file " + pos + " aggiunto a fileDaFirmarePades");
									fileDaFirmarePades.add(bytesFileDaFirmare);
									mappaPosFilePades.put(pos, posFilePades);
									posFilePades ++;
								} else if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_CONGIUNTA)) {
									log.debug("file " + pos + " aggiunto a fileDaFirmareCadesCongiunta");
									fileDaFirmareCadesCongiunta.add(bytesFileDaFirmare);
									mappaPosFileCadesCongiunta.put(pos, posFileCadesCongiunta);
									posFileCadesCongiunta ++;
								} else if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_VERTICALE)) {
									log.debug("file " + pos + " aggiunto a fileDaFirmareCadesVerticale");
									fileDaFirmareCadesVerticale.add(bytesFileDaFirmare);
									mappaPosFileCadesVerticale.put(pos, posFileCadesVerticale);
									posFileCadesVerticale ++;
								}
								
								byte[] bytesFileFirmato = null;
							}
			
							if (!fileDaFirmarePades.isEmpty()) {
								log.debug("faccio le firme Pades");
								fileFirmatiPades = FirmaPadesUtil.generate(fileDaFirmarePades, cert, hsmClient, visualizzaFirmaPades, bean.getRettangoloFirmaPades());
							} 
							if (!fileDaFirmareCadesCongiunta.isEmpty()) {
								log.debug("faccio le firme Cades congiunte");
								fileFirmatiCadesCongiunta = FirmaCadesUtil.generaP7mCongiuntaPkBox(fileDaFirmareCadesCongiunta, cert, hsmClient);
							} 
							if (!fileDaFirmareCadesVerticale.isEmpty()) {
								log.debug("faccio le firme Cades verticali");
								fileFirmatiCadesVerticale = FirmaCadesUtil.generaP7mVerticalePkBox(fileDaFirmareCadesVerticale, cert, hsmClient);
							}
							dataCorrente = new Date();
							log.debug("Ho terminato la firma dei file, costruisco l'outpunt - " + formatter.format(dataCorrente));
							
							for (int pos = 0; pos < bean.getListaFileDaFirmare().size(); pos++) {
								FileDaFirmare fileDaFirmareDaLista = bean.getListaFileDaFirmare().get(pos);
								byte[] bytesFileFirmato = null;
								if (mappaPosFilePades.containsKey(pos)) {
									log.debug("Il file in posizione " + pos + " lo recupero da fileFirmatiPades alla posizione " + mappaPosFilePades.get(pos));
									bytesFileFirmato = fileFirmatiPades.get(mappaPosFilePades.get(pos));
								} else if (mappaPosFileCadesCongiunta.containsKey(pos)) {
									log.debug("Il file in posizione " + pos + " lo recupero da fileFirmatiCadesCongiunta alla posizione " + mappaPosFileCadesCongiunta.get(pos));
									bytesFileFirmato = fileFirmatiCadesCongiunta.get(mappaPosFileCadesCongiunta.get(pos));
								} else if (mappaPosFileCadesVerticale.containsKey(pos)) {
									log.debug("Il file in posizione " + pos + " lo recupero da fileFirmatiCadesVerticale alla posizione " + mappaPosFileCadesVerticale.get(pos));
									bytesFileFirmato = fileFirmatiCadesVerticale.get(mappaPosFileCadesVerticale.get(pos));
								}
		
								if (bytesFileFirmato != null) {
									dataCorrente = new Date();
									log.debug("Creo il file firmato - " + formatter.format(dataCorrente));
									fileFirmati.add(creaFileFirmato(hsmClient, fileDaFirmareDaLista, bytesFileFirmato, bean.getFileDaMarcare(),
											bean.isSkipControlloFirmaBusta(), fileDaFirmareDaLista.getTipoFirmaHsm().getDescrizione()));
									dataCorrente = new Date();
									log.debug("Ho creato il file firmato - " + formatter.format(dataCorrente));
								} else {
									throw new HsmClientSignatureException("Non sono stati trovati bytesFileFirmato per la posizione " + pos);
								}
							}
						}
			
						bean.setFileFirmati(fileFirmati);
	
		
						if (hsmClient.getHsmConfig().getClientConfig().isRequireSignatureInSession()) {
							// Se avevo aperto una sessione di firma, la devo chiudere
							try {
								MessageBean message = hsmClient.chiudiSessioneFirmaMultipla(hsmClient.getHsmConfig().getClientConfig().getIdSession());
								if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
									log.error("Errore nella chiusura della sessione di firma: " + message.getCode() + " - " + message.getDescription() + " " + getHsmUserDescription(hsmClient, session));
								}
							} catch (UnsupportedOperationException e) {
								log.debug("Il client non supporta la chiusura della sessione. L'operazione verrà ignorata", e);
							}
						}
		
					} catch (CertificateException e) {
						log.error("Errore nella firma multipla hash", e);
					}
				} else {
					log.error("Non è stato trovato nessun certificato valido per la firma");
					throw new HsmClientSignatureException("Non è stato trovato nessun certificato valido per la firma");
				}
			}else{
				String errorMessage = "Firma non eseguita";
				if ((messageBean != null) && (messageBean.getDescription() != null) && !"errore sconosciuto".equalsIgnoreCase(messageBean.getDescription())) {
					errorMessage += ". " + messageBean.getDescription();
				} else {
					errorMessage += ". " + "Verificare i dati inseriti";
				}
				throw new HsmClientSignatureException(errorMessage);
			}

		}

		dataCorrente = new Date();
		log.debug("firmaCadesHashMultipla fine " + formatter.format(dataCorrente));
		return bean;

	}
	
	private static void loadProperties() throws Exception{
		InputStream stream = HsmBaseUtility.class.getClassLoader().getResourceAsStream("application.properties");
		if(stream != null) {
			try {
				if(properties==null) {
					properties = new Properties();
				}
				properties.load(stream);
			}
			catch(IOException e) {
				log.error("loadProperties errore durante il caricamento di application.properties");
				throw new Exception("loadProperties errore durante il caricamento di application.properties", e);
			}
			catch(Exception e) {
				log.error("loadProperties errore durante il caricamento di application.properties");
				throw new Exception("loadProperties errore durante il caricamento di application.properties", e);
			}
		}
		return;
	}

	private static byte[] getBytesDocumento(String s)  {
	        Path path = Paths.get(s);
	        byte[] data = new byte[0];
	        try {
	            data = Files.readAllBytes(path);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return data;
	    }
	
	protected static FileDaFirmare creaFileFirmato(Hsm hsmClient, FileDaFirmare bean, byte[] bytesFileFirmato, String tipoMarca, boolean skipControlloFirmaBusta, String tipofirma) throws Exception {

		String nomeFileFirmato = "";

		nomeFileFirmato = bean.getNomeFile();

		if (tipofirma.toLowerCase().indexOf("cades") != -1) {
			// Sono in firma Cades, devo eventualmente cambiare il nome del file
			if (!nomeFileFirmato.toLowerCase().endsWith(".p7m")) {
				nomeFileFirmato = bean.getNomeFile() + ".p7m";
			}
		}

		FileDaFirmare fileFirmatoBean = new FirmaHsmBean().new FileDaFirmare();
		String uriFileFirmato = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(bytesFileFirmato));

		// MARCA
		boolean isFileDaMarcare = false;
		if(tipoMarca!=null && (tipoMarca.equalsIgnoreCase("HSM") || tipoMarca.equalsIgnoreCase("SERVER"))){
			isFileDaMarcare = true;
		}
		if (isFileDaMarcare) {

			byte[] byteFileMarcato = null;
			// Hsm hsmClient = HsmClientFactory.getHsmClient(hsmType);
			File fileFirmato = StorageImplementation.getStorage().extractFile(uriFileFirmato);
						
			if(tipoMarca.equalsIgnoreCase("HSM")){
				MarcaResponseBean marcaResponseBean = hsmClient.aggiungiMarca(fileFirmato);
	
				if ((marcaResponseBean != null) && (marcaResponseBean.getMessage() != null)) {
					log.debug("Response Status: " + marcaResponseBean.getMessage().getStatus());
					log.debug("Response Return Code: " + marcaResponseBean.getMessage().getCode());
					if (marcaResponseBean.getMessage().getDescription() != null) {
						log.debug("Response Description: " + marcaResponseBean.getMessage().getDescription());
					}
					if (marcaResponseBean.getMessage().getStatus().equals(ResponseStatus.OK)) {
						byteFileMarcato = marcaResponseBean.getFileResponseBean().getFileMarcato();
					}
				}
			} else if(tipoMarca.equalsIgnoreCase("SERVER")){
				WSEndPointArubaHsmBean wsEndPointArubaHsmBean = (WSEndPointArubaHsmBean) SpringAppContext.getContext().getBean("WSEndPointArubaHsm");
				
				boolean useAuth = false;
				if(wsEndPointArubaHsmBean!=null && wsEndPointArubaHsmBean.getMarcaServiceUid()!=null && 
						!wsEndPointArubaHsmBean.getMarcaServiceUid().equalsIgnoreCase("")){
					useAuth = true;
				}
				
				TsrGenerator tsr = new TsrGenerator(wsEndPointArubaHsmBean.getMarcaServiceUrl(), wsEndPointArubaHsmBean.getMarcaServiceUid(), wsEndPointArubaHsmBean.getMarcaServicePwd(), useAuth); 
				if (tipofirma.toLowerCase().indexOf("cades") != -1) {
					byteFileMarcato = tsr.addMarca(fileFirmato);
				} else {
					byteFileMarcato = tsr.addMarcaPdf(fileFirmato);
				} 
			}
			uriFileFirmato = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(byteFileMarcato));
			// Fine marca
		}

		fileFirmatoBean.setNomeFile(nomeFileFirmato);
		fileFirmatoBean.setUri(uriFileFirmato);
		fileFirmatoBean.setIdFile(bean.getIdFile());

		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uriFileFirmato));

		MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();

		if (!skipControlloFirmaBusta) {
			infoFile = lInfoFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
			fileFirmatoBean.setInfoFile(infoFile);
		}

		return fileFirmatoBean;

	}

	protected static byte[] getFileBytes(File file) {
		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		} catch (Exception e) {
			log.error("Errore in getFileBytes", e);
		}
		return bFile;
	}

	protected static SignOption getSignOption() {
		return null;
	}
	
	public static String getHsmUserDescription(Hsm bean, HttpSession session) {
		ClientConfig clientConfig = (bean != null && bean.getHsmConfig() != null && bean.getHsmConfig().getClientConfig() != null) ? bean.getHsmConfig().getClientConfig() : null;
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		String userDescription = "(UtenteLoggato: " + loginBean.getDenominazione() + ", UtenteDelega: " + loginBean.getDelegaDenominazione();
		if (clientConfig != null && clientConfig instanceof ArubaConfig) {
			ArubaConfig arubaClientConfig = (ArubaConfig)clientConfig;
			String username = arubaClientConfig.getUser();
			String password = StringUtils.isNotBlank(arubaClientConfig.getPassword()) ?arubaClientConfig.getPassword().replaceAll(".", "*") : "<vuota>";
			String usernameDelegato = arubaClientConfig.getDelegatedUser();
			String passwordDelegato = StringUtils.isNotBlank(arubaClientConfig.getDelegatedPassword()) ? arubaClientConfig.getDelegatedPassword().replaceAll(".", "*") : "<vuota>";
			String dominioDelegato = arubaClientConfig.getDelegatedDomain();
			String isInDelega = arubaClientConfig.isUseDelegate() + "";
			userDescription += ", HsmUsername: " + username + ", HsmPassword: " + password + ", HsmUsernameDelegato: " + usernameDelegato + ", HsmPasswordDelegato: " + passwordDelegato + ", HsmDominio: " + dominioDelegato + ", HsmInDelega: " + isInDelega;
		}else if (clientConfig != null && clientConfig instanceof MedasConfig) {
			MedasConfig medasClientConfig = (MedasConfig)clientConfig;
			String username = medasClientConfig.getUser();
			String codiceFiscale = medasClientConfig.getCodiceFiscale();
			String password = StringUtils.isNotBlank(medasClientConfig.getPassword()) ? medasClientConfig.getPassword().replaceAll(".", "*") : "<vuota>";
			userDescription += ", HsmUsername: " + username + ", HsmCodiceFiscale: " + codiceFiscale + ", HsmPassword: " + password;
		}else if (clientConfig != null && clientConfig instanceof PkBoxConfig) {
			PkBoxConfig pkBoxClientConfig = (PkBoxConfig)clientConfig;
			String user = pkBoxClientConfig.getUser();
			String password = StringUtils.isNotBlank(pkBoxClientConfig.getPassword()) ? pkBoxClientConfig.getPassword().replaceAll(".", "*") : "<vuota>";
			String alias = pkBoxClientConfig.getAlias();
			String pin = StringUtils.isNotBlank(pkBoxClientConfig.getPin()) ? pkBoxClientConfig.getPin().replaceAll(".", "*") : "<vuota>";
			userDescription += ", HsmUser: " + user + ", HsmPassword: " + password + ", HsmAlias: " + alias + ", HsmPin: " + pin;
		}else if (clientConfig != null && clientConfig instanceof InfoCertConfig) {
			InfoCertConfig infocertClientConfig = (InfoCertConfig)clientConfig;
			String alias = infocertClientConfig.getAlias();
			userDescription += ", Hsmalias: " + alias;
		}
		userDescription += ")";
		return userDescription;
	}

}
