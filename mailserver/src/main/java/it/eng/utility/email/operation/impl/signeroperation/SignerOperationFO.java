package it.eng.utility.email.operation.impl.signeroperation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;
import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.InputFile;
import it.eng.module.foutility.beans.generated.InputFileOperationType;
import it.eng.module.foutility.beans.generated.InputSigVerifyType;
import it.eng.module.foutility.beans.generated.InputSigVerifyType.SignatureVerify;
import it.eng.module.foutility.beans.generated.InputSigVerifyType.TimestampVerifiy;
import it.eng.module.foutility.beans.generated.MessageType;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.module.foutility.beans.generated.Response;
import it.eng.module.foutility.beans.generated.ResponseSigVerify;
import it.eng.module.foutility.beans.generated.SigVerifyResultType;
import it.eng.module.foutility.beans.generated.SignerInformationType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.fo.FOImpl;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;

/**
 * Operazione del controllo se gli attachment sono firmati o meno usando FileOperation
 * 
 * @author michele
 * 
 */
@MessageOperation(description = "Controlla se i file in attachements sono firmati e ne restituisce i parametri", name = "SignerOperationFO")
public class SignerOperationFO extends AbstractMessageOperation<SignerMailBean> {

	Logger log = LogManager.getLogger(SignerOperationFO.class);

	@ConfigOperation(title = "Controllo ricorsivo", name = "signeroperation.recursive", description = "Indica se il controllo deve essere fatto ricorsivamente all'interno della mail o solo al primo livello (Default false)")
	private Boolean isrecusive = false;

	@ConfigOperation(title = "Controllo ricorsivo nelle firme", name = "signeroperation.recursiveCheck", description = "Indica se il controllo delle firme deve essere fatto ricorsivamente (Default false)")
	private Boolean recursiveCheck = false;

	@ConfigOperation(title = "CA Reliability", name = "signeroperation.caReliability", description = "Indica se deve essere effettuato o meno il controllo sulle CA (Default false)")
	private Boolean caReliability = false;

	@ConfigOperation(title = "CRL Check", name = "signeroperation.crlCheck", description = "Indica se deve essere effettuato o meno il controllo sulle CRL (Default false)")
	private Boolean crlCheck = false;

	@ConfigOperation(title = "detectCode", name = "signeroperation.detectCode", description = "Indica se deve essere effettuato o meno il controllo (Default false)")
	private Boolean detectCode = false;

	@ConfigOperation(title = "Directory contenente le CRL", name = "signeroperation.crldirectory", description = "Directory contenente le CRL")
	private String crlDirectory = "";

	@ConfigOperation(title = "Directory contenente le CA abilitate", name = "signeroperation.cadirectory", description = "Directory contenente le CA abilitate")
	private String caDirectory = "";

	@ConfigOperation(title = "Directory contenente le configurazioni", name = "signeroperation.configdirectory", description = "Directory contenente le configurazioni")
	private String configDirectory = "";

	@ConfigOperation(title = "Proxy Host", name = "signeroperation.proxyhost", description = "Proxy Host")
	private String proxyhost = "";

	@ConfigOperation(title = "Proxy Port", name = "signeroperation.proxyport", description = "Proxy Port")
	private String proxyport = "";

	@ConfigOperation(title = "Proxy User", name = "signeroperation.proxyuser", description = "Proxy User")
	private String proxyuser = "";

	@ConfigOperation(title = "Proxy Password", name = "signeroperation.proxypassword", description = "Proxy Password")
	private String proxypassword = "";

	@ConfigOperation(title = "WS Endpoint", name = "signeroperation.wsendpoint", description = "Endpoint del WS di fileOperation")
	private String wsEndpoint = "";

	@ConfigOperation(title = "ChildValidation", name = "signeroperation.childvalidation", description = "Abilita la validazione ricorsiva delle firme con fileOperation")
	private String childValidation = "";

	// Diego: per mancata verifica annidata dei certificati (figli e fratelli) inserisco codici generici di errore
	private final String errorCode = "999999";
	private final String errorMessage = " ErroreGenerico di verifica sulla validità dei certifiicati.]";

	@Override
	public SignerMailBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		log.debug("Metodo elaborate della classe SignerOperationFO");

		// Creo il nuovo bean
		SignerMailBean bean = new SignerMailBean();

		valorizzaListaAttachments(message, bean);

		for (SignerAttachmentsBean attach : bean.getAttachments()) {
			invocaWS(attach);
		}

		return bean;
	}

	private void valorizzaListaAttachments(MessageInfos info, SignerMailBean bean) throws Exception {
		bean.setAttachments(new ArrayList<SignerAttachmentsBean>());
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
					SignerAttachmentsBean attachmentbean = new SignerAttachmentsBean();
					attachmentbean.setFilename(attachment.getFilename());
					attachmentbean.setMessageid(attachment.getMessageid());
					attachmentbean.setSize(attachment.getSize());
					attachmentbean.setMimetype(attachment.getMimetype());
					attachmentbean.setIssigner(false);
					attachmentbean.setIssignervalid(false);
					attachmentbean.setFile(attachment.getFile());
					attachmentbean.setAlgoritmo(attachment.getAlgoritmo());
					attachmentbean.setEncoding(attachment.getEncoding());
					attachmentbean.setEncodedHash(attachment.getEncodedHash());
					bean.getAttachments().add(attachmentbean);
				}
			}
		}
	}

	/**
	 * Recupera tutti gli attachment dalla mail passata in ingresso
	 * 
	 * @param message
	 * @return
	 * @throws InterruptedException
	 * @throws Exception
	 */

	private void invocaWS(SignerAttachmentsBean attachBean) throws InterruptedException {
		try {

			if (Thread.currentThread().isInterrupted()) {
				// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
				throw new InterruptedException();
			}

			log.info("Inizio analisi dell'attachment di nome " + attachBean.getFilename());
			long startTime = System.currentTimeMillis(); // Tempo di inizio verifica Firma.
			
			FileOperation request = new FileOperation();
			Operations operations = new Operations();
			// Unica operazione è la verifica della firma in un allegato, compresa la verifica di eventuali firme a cascata
			InputSigVerifyType input = new InputSigVerifyType();
			input.setRecursive(getRecursiveCheck());
			input.setChildValidation(Boolean.parseBoolean(getChildValidation()));
			SignatureVerify signatureVerify = new SignatureVerify();
			signatureVerify.setCAReliability(getCaReliability());
			signatureVerify.setCRLCheck(getCrlCheck());
			signatureVerify.setDetectCode(getDetectCode());
			input.setSignatureVerify(signatureVerify);
			TimestampVerifiy timeStampVerify = new TimestampVerifiy();
			timeStampVerify.setTSAReliability(false);
			input.setTimestampVerifiy( timeStampVerify );
			operations.getOperation().add(input);
			request.setOperations(operations);

			InputFile lInputFile = new InputFile();
			lInputFile.setFileUrl(attachBean.getFile().toURI().toString());
			InputFileOperationType lInputFileOperationType = new InputFileOperationType();
			lInputFileOperationType.setOriginalName(attachBean.getFile().getName());
			lInputFileOperationType.setInputType(lInputFile);
			request.setFileOperationInput(lInputFileOperationType);
			Response risposta = new FOImpl().execute(request);

			attachBean.setResultoperation(new HashMap<SignerResultType, ValidationAttachmentsInfos>());

			if (risposta == null) {
				// C'è stato un timeout
				throw new IOException("Raggiunto il timeout");
			}

			if (risposta != null && risposta.getGenericError() != null) {
				log.info("L'attach di nome " + attachBean.getFilename() + " risulta non firmato con errore");
				for (String lStrError : risposta.getGenericError().getErrorMessage()) {
					log.info("Errore attach non firmato con errore: " + lStrError);
				}
				attachBean.setIssignervalid(false);
				attachBean.setIssigner(false);
			} else if (risposta != null && risposta.getFileoperationResponse() != null && risposta.getFileoperationResponse().getFileOperationResults() != null
					&& risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {
				List<AbstractResponseOperationType> results = risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult();
				for (AbstractResponseOperationType opResult : results) {

					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
						throw new InterruptedException();
					}

					if (opResult instanceof ResponseSigVerify) {

						ResponseSigVerify reponseSigVerify = (ResponseSigVerify) opResult;

						// se non è firmato allora lo status è sicuramente NULL
						if (reponseSigVerify.getVerificationStatus() == null) {
							log.info("L'attach di nome " + attachBean.getFilename() + " risulta non firmato");
							attachBean.setIssignervalid(false);
							attachBean.setIssigner(false);
							if (reponseSigVerify.getSigVerifyResult().getMessage() != null) {
								log.info("Messaggio: " + reponseSigVerify.getSigVerifyResult().getMessage());
							}
							return;
						} else {

							boolean isOk = !(opResult.getVerificationStatus().equals(VerificationStatusType.ERROR)
									|| opResult.getVerificationStatus().equals(VerificationStatusType.KO));

							log.info("L'attach di nome " + attachBean.getFilename() + " risulta firmato ");
							attachBean.setIssigner(true);
							if (reponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null
									&& reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult() != null) {
								ValidationAttachmentsInfos infosVal = addValidationAttachmentsInfos(
										reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignatureValResult());
								log.info("infosVal " + infosVal);
								attachBean.getResultoperation().put(SignerResultType.SignatureValidation, infosVal);
							}
							if (reponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null
									&& reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCertExpirationResult() != null) {
								ValidationAttachmentsInfos infosCert = addValidationAttachmentsInfos(
										reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCertExpirationResult());
								log.info("infosCert " + infosCert);
								attachBean.getResultoperation().put(SignerResultType.CertificateValidity, infosCert);
							}
							if (reponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null
									&& reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCAReliabilityResult() != null) {
								ValidationAttachmentsInfos infosCA = addValidationAttachmentsInfos(
										reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCAReliabilityResult());
								log.info("infosCA " + infosCA);
								attachBean.getResultoperation().put(SignerResultType.QualifiedCertificate, infosCA);
							}
							if (reponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null
									&& reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCRLResult() != null) {
								ValidationAttachmentsInfos infosCRL = addValidationAttachmentsInfos(
										reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getCRLResult());
								log.info("infosCRL " + infosCRL);
								attachBean.getResultoperation().put(SignerResultType.CRL, infosCRL);
							}
							if (reponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null
									&& reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getDetectionCodeResult() != null) {
								ValidationAttachmentsInfos infosDetectCode = addValidationAttachmentsInfos(
										reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getDetectionCodeResult());
								log.info("infosDetectCode " + infosDetectCode);
								attachBean.getResultoperation().put(SignerResultType.DETECTCODE, infosDetectCode);
							}
							if (reponseSigVerify.getSigVerifyResult().getSigVerifyResult() != null
									&& reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getFormatResult() != null) {
								ValidationAttachmentsInfos infosFormat = addValidationAttachmentsInfos(
										reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getFormatResult());
								log.info("infosFormat " + infosFormat);
								attachBean.getResultoperation().put(SignerResultType.FormatCheckValidity, infosFormat);
							}

							List<SignerInformationType> signerInformation = reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getSignerInformations()
									.getSignerInformation();

							for (int j = 0; j < signerInformation.size(); j++) {

								// controllo che i certificati siano validi
								boolean risultatoVerificaFirma = signerInformation.get(j).getVerificationStatus().equals(VerificationStatusType.OK);
								attachBean.setIssignervalid(risultatoVerificaFirma);

								if (!risultatoVerificaFirma) {
									return;
								}

							}

							if (Thread.currentThread().isInterrupted()) {
								// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
								throw new InterruptedException();
							}

							// segnalo che la verifica ha qualche problema....
							// controllo che non sia a false e allora inserisco una messaggi generale di errore
							if (!isOk) {
								// aggiungo il messaggio che indica che la verifica non è andata bene.
								ValidationAttachmentsInfos infos = new ValidationAttachmentsInfos();
								infos.addErrorWithCode(errorCode, "[" + attachBean.getFilename() + ":" + errorMessage);
								infos.addError(errorCode);
								attachBean.getResultoperation().put(SignerResultType.SignatureValidation, infos);
							}

							// indaghiamo i child
							SigVerifyResultType signVerifyResult = reponseSigVerify.getSigVerifyResult().getSigVerifyResult().getChild();

							while (signVerifyResult != null) {
								signerInformation = signVerifyResult.getSigVerifyResult().getSignerInformations().getSignerInformation();

								for (int j = 0; j < signerInformation.size(); j++) {
									// Controllo che i certificati dei child
									// siano validi
									boolean risultatoVerificaFirma = signerInformation.get(j).getVerificationStatus().equals(VerificationStatusType.OK);
									if (!risultatoVerificaFirma) {
										attachBean.setIssignervalid(false);
										return;
									}

								}

								signVerifyResult = signVerifyResult.getSigVerifyResult().getChild();
							}

							attachBean.setIssignervalid(isOk);
						}
					}
				}
			} else {
				log.error("Nessuna risposta da FileOperations");
			}
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			log.debug("Fine analisi del file " + attachBean.getFilename() + " in " + duration + " millisecondi");
		} catch (InterruptedException e) {
			throw e;
		}
		catch (Throwable e) {
			log.error("Eccezione invocaWS riguardo l'attachment di nome " + attachBean.getFilename() + ": " + e.getMessage(), e);
		}
		
	}

	private ValidationAttachmentsInfos addValidationAttachmentsInfos(AbstractResponseOperationType resp)
			throws IllegalAccessException, InvocationTargetException {
		ValidationAttachmentsInfos infos = new ValidationAttachmentsInfos();
		if (resp.getErrorsMessage() != null) {
			List<MessageType> errMessageList = resp.getErrorsMessage().getErrMessage();
			for (MessageType error : errMessageList) {
				infos.addErrorWithCode(error.getCode(), error.getDescription());
				infos.addError(error.getCode());
			}
		}
		if (resp.getWarnings() != null) {
			List<MessageType> warningMessageList = resp.getWarnings().getWarnMessage();
			for (MessageType warning : warningMessageList) {
				infos.addErrorWithCode(warning.getCode(), warning.getDescription());
			}
		}
		return infos;
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

	public Boolean getRecursiveCheck() {
		return recursiveCheck;
	}

	public void setRecursiveCheck(Boolean recursiveCheck) {
		this.recursiveCheck = recursiveCheck;
	}

	public String getChildValidation() {
		return childValidation;
	}

	public void setChildValidation(String childValidation) {
		this.childValidation = childValidation;
	}

}
