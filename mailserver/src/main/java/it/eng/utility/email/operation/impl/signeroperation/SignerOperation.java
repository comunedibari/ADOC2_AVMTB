package it.eng.utility.email.operation.impl.signeroperation;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.cryptosigner.controller.ISignerController;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.impl.signature.FormatValidity;
import it.eng.utility.cryptosigner.manager.SignatureManager;
import it.eng.utility.cryptosigner.manager.factory.SignatureManagerFactory;
import it.eng.utility.cryptosigner.utils.CAUtility;
import it.eng.utility.cryptosigner.utils.OutputAnalyzer;
import it.eng.utility.cryptosigner.utils.OutputAnalyzerFilter;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;

/**
 * 10.12.2014 ripristinata la versione cryptosigner per RER 20.02.2015 unificate le versioni cryptosigner con FileOp Operazione del controllo se gli attachment
 * sono firmati con cryptosigner
 * 
 * @author diego
 *
 */
@MessageOperation(description = "Controlla se i file in attachements sono firmati e ne restituisce i parametri usando il cryptosigner o FileOp in base al parametro di config", name = "SignerOperation")
public class SignerOperation extends AbstractMessageOperation<SignerMailBean> {

	Logger log = LogManager.getLogger(SignerOperation.class);

	@ConfigOperation(title = "Controllo ricorsivo", name = "signeroperation.recursive", description = "Indica se il controllo deve essere fatto ricorsivamente all'interno della mail o solo al primo livello (Default false)")
	private Boolean isrecusive = false;

	@ConfigOperation(title = "Directory contenente le CRL", name = "signeroperation.crldirectory", description = "Directory contenente le CRL")
	private String crlDirectory = "";

	@ConfigOperation(title = "Directory contenente le CA abilitate", name = "signeroperation.cadirectory", description = "Directory contenente le CA abilitate")
	private String caDirectory = "";

	@ConfigOperation(title = "Directory contenente le configurazioni", name = "signeroperation.configdirectory", description = "Directory contenente le configurazioni")
	private String configDirectory = "";

	@ConfigOperation(title = "Indica se usare cryptosigner o FileOp (valori true o false)", name = "signeroperation.configcryptosigner", description = "Indica se usare cryptosigner o FileOp (valori true o false)")
	private String configCryptoSigner = "";

	// 20.02.2015 Diego: unisco le proprietà di FileOp
	@ConfigOperation(title = "Controllo ricorsivo nelle firme: indica se il controllo delle firme deve essere fatto ricorsivamente (Default false)", name = "signeroperation.recursiveCheck", description = "Indica se il controllo delle firme deve essere fatto ricorsivamente (Default false)")
	private Boolean recursiveCheck = false;

	@ConfigOperation(title = "CA Reliability: indica se deve essere effettuato o meno il controllo sulle CA (Default false)", name = "signeroperation.caReliability", description = "Indica se deve essere effettuato o meno il controllo sulle CA (Default false)")
	private Boolean caReliability = false;

	@ConfigOperation(title = "CRL Check: indica se deve essere effettuato o meno il controllo sulle CRL (Default false)", name = "signeroperation.crlCheck", description = "Indica se deve essere effettuato o meno il controllo sulle CRL (Default false)")
	private Boolean crlCheck = false;

	@ConfigOperation(title = "detectCode: indica se deve essere effettuato o meno il controllo (Default false)", name = "signeroperation.detectCode", description = "Indica se deve essere effettuato o meno il controllo (Default false)")
	private Boolean detectCode = false;

	@ConfigOperation(title = "Proxy Host", name = "signeroperation.proxyhost", description = "Proxy Host")
	private String proxyhost = "";

	@ConfigOperation(title = "Proxy Port", name = "signeroperation.proxyport", description = "Proxy Port")
	private String proxyport = "";

	@ConfigOperation(title = "Proxy User", name = "signeroperation.proxyuser", description = "Proxy User")
	private String proxyuser = "";

	@ConfigOperation(title = "Proxy Password", name = "signeroperation.proxypassword", description = "Proxy Password")
	private String proxypassword = "";

	@ConfigOperation(title = "Endpoint del WS di fileOperation", name = "signeroperation.wsendpoint", description = "Endpoint del WS di fileOperation")
	private String wsEndpoint = "";

	@ConfigOperation(title = "ChildValidation", name = "signeroperation.childvalidation", description = "Abilita la validazione ricorsiva delle firme con fileOperation (Defaul: false)")
	private String childValidation = "false";

	/**
	 * Signature Manager Factory
	 */
	private static SignatureManagerFactory signerfactory;

	@Override
	public SignerMailBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		log.debug("Invocata la SignerOperation per il messaggio con id: " + message.getHeaderinfo().getMessageid());
		// 20.02.2015 Diego: gestisco come default il FileOp, altrimenti uso cryptosigner...
		if (configCryptoSigner != null && (configCryptoSigner.equalsIgnoreCase("") || configCryptoSigner.equalsIgnoreCase("false"))) {
			SignerOperationFO signerOperationFO = new SignerOperationFO();
			signerOperationFO.setCaDirectory(caDirectory);
			signerOperationFO.setCaReliability(caReliability);
			signerOperationFO.setConfigDirectory(configDirectory);
			signerOperationFO.setCrlCheck(crlCheck);
			signerOperationFO.setCrlDirectory(crlDirectory);
			signerOperationFO.setDetectCode(detectCode);
			signerOperationFO.setIsrecusive(isrecusive);
			signerOperationFO.setProxyhost(proxyhost);
			signerOperationFO.setProxypassword(proxypassword);
			signerOperationFO.setProxyport(proxyport);
			signerOperationFO.setRecursiveCheck(recursiveCheck);
			signerOperationFO.setWsEndpoint(wsEndpoint);
			signerOperationFO.setChildValidation(childValidation);
			return signerOperationFO.elaborate(message);
		}
		// Istanzio il signature manager come variabile statica
		if (signerfactory == null) {
			signerfactory = SignatureManagerFactory.newInstance(new File(caDirectory), new File(crlDirectory), new File(configDirectory));

			File caxml = new File(caDirectory, "IT_TSL_signed.xml");
			// Controllo se esite il file sulla CA Directory
			if (!caxml.exists()) {
				FileOutputStream output = null;
				try {
					output = new FileOutputStream(caxml);
					IOUtils.copy(this.getClass().getResourceAsStream("/IT_TSL_signed.xml"), output);
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (Exception e) {
							log.warn("Errore nella chiusura dello stream", e);
						}
					}
				}
			}
			CAUtility.updateCAFromXml(caxml, signerfactory.getCaStorage());
		}

		// Creo il nuovo bean
		SignerMailBean bean = new SignerMailBean();
		bean.setAttachments(new ArrayList<SignerAttachmentsBean>());

		// Verifico i dati degli attachments
		attachmentsVerify(message, bean);

		return bean;
	}

	/**
	 * Recupera tutti gli attachment dalla mail passata in ingresso
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private void attachmentsVerify(MessageInfos info, SignerMailBean bean) throws Exception {
		if (info != null) {
			List<MailAttachmentsBean> attachments = null;
			if (isrecusive) {
				// Prendo tutti gli attachments
				attachments = info.getDirectAttachments();
			} else {
				// Prendo i soli attachments della mail principale
				attachments = info.getAttachmentsWithPrincipalMail();
			}

			if (attachments != null) {
				for (MailAttachmentsBean attachment : attachments) {
					
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
						throw new InterruptedException();
					}
					log.debug("### SignerOperation: inizio verifica file " + attachment.getFilename());
					long startTime = System.currentTimeMillis(); // Tempo di inizio verifica Firma.
					SignerAttachmentsBean attachmentbean = new SignerAttachmentsBean();
					attachmentbean.setFilename(attachment.getFilename());
					attachmentbean.setMessageid(attachment.getMessageid());
					attachmentbean.setAlgoritmo(attachment.getAlgoritmo());
					attachmentbean.setEncoding(attachment.getEncoding());
					attachmentbean.setEncodedHash(attachment.getEncodedHash());
					attachmentbean.setIssigner(false);
					attachmentbean.setIssignervalid(false);
					// Verifico il file
					try {
						// 21.10.2016
						// Diego: prendo il bean ...
						SignatureManager manager = (SignatureManager) SpringAppContext.getContext().getBean("SignatureManager");
						List<ISignerController> controllers = manager.getMasterSignerController().getControllers();
						for (ISignerController controller : controllers) {
							if (controller instanceof FormatValidity) {
								// TODO Cablato a codice eventualmente da inserire come configurazione esterna o dell'operazione
								Properties prop = new Properties();
								prop.put("P7M", "30/06/2010");
								prop.put("M7M", "30/06/2010");
								((FormatValidity) controller).setValidityProperties(prop);
								break;
							}
						}

						OutputSignerBean signer = manager.executeEmbedded(attachment.getFile());
						// Setto i valori di output
						if (signer != null) {
							attachmentbean.setIssigner(true);
							HashMap<SignerResultType, ValidationAttachmentsInfos> signeroutput = new HashMap<SignerResultType, ValidationAttachmentsInfos>();
							attachmentbean.setResultoperation(signeroutput);

							// Analizzatore dei risultati della firma
							OutputAnalyzer analyzer = new OutputAnalyzer(signer);

							try {
								ValidationAttachmentsInfos infoscertificate = new ValidationAttachmentsInfos();
								BeanUtilsBean2.getInstance().copyProperties(infoscertificate, analyzer.getCertificateValidityInfos());
								attachmentbean.getResultoperation().put(SignerResultType.CertificateValidity, infoscertificate);
							} catch (Exception e) {
								// WARNING DI PASSAGGIO DATI
								log.warn("Warning analyzer attachmentsVerify", e);
							}

							try {
								ValidationAttachmentsInfos infoscrl = new ValidationAttachmentsInfos();
								BeanUtilsBean2.getInstance().copyProperties(infoscrl, analyzer.getCRLInfos());
								attachmentbean.getResultoperation().put(SignerResultType.CRL, infoscrl);
							} catch (Exception e) {
								// WARNING DI PASSAGGIO DATI
								log.warn("Warning analyzer attachmentsVerify", e);
							}

							try {
								ValidationAttachmentsInfos qualifiedcertificate = new ValidationAttachmentsInfos();
								BeanUtilsBean2.getInstance().copyProperties(qualifiedcertificate, analyzer.getQualifiedCertificateInfos());
								attachmentbean.getResultoperation().put(SignerResultType.QualifiedCertificate, qualifiedcertificate);
							} catch (Exception e) {
								// WARNING DI PASSAGGIO DATI
								log.warn("Warning analyzer", e);
							}

							try {
								ValidationAttachmentsInfos signaturevalidation = new ValidationAttachmentsInfos();
								BeanUtilsBean2.getInstance().copyProperties(signaturevalidation, analyzer.getSignatureValidationInfos());
								attachmentbean.getResultoperation().put(SignerResultType.SignatureValidation, signaturevalidation);
							} catch (Exception e) {
								// WARNING DI PASSAGGIO DATI
								log.warn("Warning analyzer", e);
							}

							try {
								ValidationAttachmentsInfos formatcheckvalidation = new ValidationAttachmentsInfos();
								OutputAnalyzerFilter filter = new OutputAnalyzerFilter();
								filter.acceptOutput(OutputSignerBean.FORMAT_VALIDITY_PROPERTY);
								BeanUtilsBean2.getInstance().copyProperties(formatcheckvalidation, analyzer.getValidationResults(filter));
								attachmentbean.getResultoperation().put(SignerResultType.FormatCheckValidity, formatcheckvalidation);
							} catch (Exception e) {
								// WARNING DI PASSAGGIO DATI
								log.warn("Warning analyzer", e);
							}

							try {
								// Effettuo una validazione della firma
								attachmentbean.setIssignervalid(analyzer.getValidationResults().isValid(false));
							} catch (Exception e) {
								attachmentbean.setIssignervalid(false);
								log.warn("Warning analyzer", e);
							}
						}
					} catch (Exception e) {
						// File non firmato
						throw e;
					} finally {
						bean.getAttachments().add(attachmentbean);
					}
					long endTime = System.currentTimeMillis();
					long duration = (endTime - startTime);
					log.debug("###  Fine processamento firma file " + attachment.getFilename() + " in " + duration + " millisecondi");
				}
			}
		}
	}

	public Boolean getIsrecusive() {
		return isrecusive;
	}

	public void setIsrecusive(Boolean isrecusive) {
		this.isrecusive = isrecusive;
	}

	public String getCrlDirectory() {
		return crlDirectory;
	}

	public void setCrlDirectory(String crlDirectory) {
		this.crlDirectory = crlDirectory;
	}

	public String getCaDirectory() {
		return caDirectory;
	}

	public void setCaDirectory(String caDirectory) {
		this.caDirectory = caDirectory;
	}

	public String getConfigDirectory() {
		return configDirectory;
	}

	public void setConfigDirectory(String configDirectory) {
		this.configDirectory = configDirectory;
	}

	public String getConfigCryptoSigner() {
		return configCryptoSigner;
	}

	public void setConfigCryptoSigner(String configCryptoSigner) {
		this.configCryptoSigner = configCryptoSigner;
	}

	public Boolean getRecursiveCheck() {
		return recursiveCheck;
	}

	public void setRecursiveCheck(Boolean recursiveCheck) {
		this.recursiveCheck = recursiveCheck;
	}

	public Boolean getCaReliability() {
		return caReliability;
	}

	public void setCaReliability(Boolean caReliability) {
		this.caReliability = caReliability;
	}

	public Boolean getCrlCheck() {
		return crlCheck;
	}

	public void setCrlCheck(Boolean crlCheck) {
		this.crlCheck = crlCheck;
	}

	public Boolean getDetectCode() {
		return detectCode;
	}

	public void setDetectCode(Boolean detectCode) {
		this.detectCode = detectCode;
	}

	public String getProxyhost() {
		return proxyhost;
	}

	public void setProxyhost(String proxyhost) {
		this.proxyhost = proxyhost;
	}

	public String getProxyport() {
		return proxyport;
	}

	public void setProxyport(String proxyport) {
		this.proxyport = proxyport;
	}

	public String getProxyuser() {
		return proxyuser;
	}

	public void setProxyuser(String proxyuser) {
		this.proxyuser = proxyuser;
	}

	public String getProxypassword() {
		return proxypassword;
	}

	public void setProxypassword(String proxypassword) {
		this.proxypassword = proxypassword;
	}

	public String getWsEndpoint() {
		return wsEndpoint;
	}

	public void setWsEndpoint(String wsEndpoint) {
		this.wsEndpoint = wsEndpoint;
	}

	public String getChildValidation() {
		return childValidation;
	}

	public void setChildValidation(String childValidation) {
		this.childValidation = childValidation;
	}

}
