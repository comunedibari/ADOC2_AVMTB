package it.eng.utility.email.operation.impl.archiveoperation.utils;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.module.foutility.beans.generated.DigestAlgID;
import it.eng.module.foutility.beans.generated.DigestEncID;
import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.InputDigestType;
import it.eng.module.foutility.beans.generated.InputFile;
import it.eng.module.foutility.beans.generated.InputFileOperationType;
import it.eng.module.foutility.beans.generated.InputFormatRecognitionType;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.module.foutility.beans.generated.Response;
import it.eng.module.foutility.beans.generated.ResponseFormatRecognitionType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.fo.FOImpl;
import it.eng.utility.email.operation.impl.interoperation.InteroperabilitaType;

public class AttachmentVerifier {

	private String wsEndpoint;
	private String digestAlgorithm;
	private String digestEncoding;

	private static Logger log = LogManager.getLogger(AttachmentVerifier.class);

	public Response invocaFo(MessageInfos message, File file, String fileName) {

		try {
			FileOperation request = new FileOperation();

			Operations operations = new Operations();

			Boolean fileInteroperabilita = false;

			if (BeanUtils.isInteroperabile(message)) {
				InteroperabilitaType type = InteroperabilitaType.getValueForFileName(fileName, false);
				if (type != null) {
					// identificato un file interoperabile
					fileInteroperabilita = true;
				}
			}

			if (!(fileName.equalsIgnoreCase("daticert.xml") || fileInteroperabilita)) {
				// in questo caso sono già sicuro del tipo di file e del mimetype, evito di fare un'operazione inutile con FileOperations
				// tra l'altro alcuni file xml potrebbero contenere link esterni a dtd da validare, che potrebbero causare dei timeout
				InputFormatRecognitionType inputFormatRecognition = new InputFormatRecognitionType();
				operations.getOperation().add(inputFormatRecognition);
			} else {
				log.info("Imposto mimetype 'application/xml' senza invocare fileOperation per il file: " + fileName);
			}

			InputDigestType inputDigest = new InputDigestType();
			inputDigest.setDigestAlgId(DigestAlgID.fromValue(digestAlgorithm));
			inputDigest.setEncoding(DigestEncID.fromValue(digestEncoding));
			operations.getOperation().add(inputDigest);
			request.setOperations(operations);

			InputFile inputFile = new InputFile();
			inputFile.setFileUrl(file.toURI().toString());
			InputFileOperationType fileOp = new InputFileOperationType();
			fileOp.setOriginalName(fileName);
			fileOp.setInputType(inputFile);
			request.setFileOperationInput(fileOp);
			FOImpl lFoImpl = new FOImpl();
			log.debug("Analisi del File:" + fileName +" per il calcolo del mimetype");
			log.debug("Percorso del file: " + file.toURI().toString());
			Response risposta = lFoImpl.execute(request);
			log.debug("Risposta da parte di FileOp:");
			if(risposta.getGenericError() != null && risposta.getGenericError().getErrorMessage() != null 
					&& !risposta.getGenericError().getErrorMessage().isEmpty()) {
				for(String error : risposta.getGenericError().getErrorMessage()) {
					log.debug(error);
				}
			}

			if ((fileName.equalsIgnoreCase("daticert.xml") || fileInteroperabilita) && risposta != null && risposta.getFileoperationResponse() != null
					&& risposta.getFileoperationResponse().getFileOperationResults() != null
					&& risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {
				ResponseFormatRecognitionType formatRecognitionType = new ResponseFormatRecognitionType();
				formatRecognitionType.setVerificationStatus(VerificationStatusType.OK);
				formatRecognitionType.setMimeType("application/xml");
				formatRecognitionType.setNewFileName(fileName);
				risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult().add(formatRecognitionType);
			}
			
			return risposta;

		} catch (Exception e) {
			log.error("Eccezione invocaFo: ", e);
		}

		return null;
	}

	public String getWsEndpoint() {
		return wsEndpoint;
	}

	public void setWsEndpoint(String wsEndpoint) {
		this.wsEndpoint = wsEndpoint;
	}

	public String getDigestAlgorithm() {
		return digestAlgorithm;
	}

	public void setDigestAlgorithm(String digestAlgorithm) {
		this.digestAlgorithm = digestAlgorithm;
	}

	public String getDigestEncoding() {
		return digestEncoding;
	}

	public void setDigestEncoding(String digestEncoding) {
		this.digestEncoding = digestEncoding;
	}

}
