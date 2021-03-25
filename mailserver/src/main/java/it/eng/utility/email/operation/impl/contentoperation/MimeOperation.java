package it.eng.utility.email.operation.impl.contentoperation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.module.foutility.beans.ResponseUtils;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;
import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.InputFile;
import it.eng.module.foutility.beans.generated.InputFileOperationType;
import it.eng.module.foutility.beans.generated.InputFormatRecognitionType;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.module.foutility.beans.generated.Response;
import it.eng.module.foutility.beans.generated.ResponseFormatRecognitionType;
import it.eng.module.foutility.fo.FOImpl;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;

@MessageOperation(description = "Controlla il mimetype degli attachments", name = "MimeOperation")
public class MimeOperation extends AbstractMessageOperation<MimeBean> {

	@ConfigOperation(title = "WS Endpoint", name = "mimeOperation.wsEndpoint", description = "Endpoint del WS di fileOperation")
	private String wsEndpoint = "";

	@Override
	public MimeBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		MimeBean mimebean = new MimeBean();

		List<MailAttachmentsBean> attachments = message.getDirectAttachments();

		if (attachments != null) {
			mimebean.setMimes(new ArrayList<MimeAttachmentBean>());
			for (MailAttachmentsBean attachment : attachments) {

				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
					throw new InterruptedException();
				}

				MimeAttachmentBean mimeattachment = new MimeAttachmentBean();

				String mime = getFormato(wsEndpoint, attachment.getFile());
				mimeattachment.setMimetype(mime);

				mimeattachment.setMessageid(attachment.getMessageid());
				mimebean.getMimes().add(mimeattachment);
			}
		}

		return mimebean;
	}

	private String getFormato(String wsEndpoint, File file) throws Exception {

		try {

			FileOperation request = new FileOperation();

			Operations operations = new Operations();
			InputFormatRecognitionType input = new InputFormatRecognitionType();
			operations.getOperation().add(input);
			request.setOperations(operations);

			InputFile inputFile = new InputFile();
			inputFile.setFileUrl(file.toURI().toString());
			InputFileOperationType fileOp = new InputFileOperationType();
			fileOp.setInputType(inputFile);
			fileOp.setOriginalName(file.getName());
			request.setFileOperationInput(fileOp);
			FOImpl lFoImpl = new FOImpl();
			Response risposta = lFoImpl.execute(request);

			if (risposta != null && risposta.getFileoperationResponse() != null
					&& ResponseUtils.isResponseOK(risposta.getFileoperationResponse().getFileOperationResults())) {
				if (risposta.getFileoperationResponse().getFileOperationResults() != null
						&& risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult() != null) {
					for (AbstractResponseOperationType opResponse : risposta.getFileoperationResponse().getFileOperationResults().getFileOperationResult()) {
						if (opResponse instanceof ResponseFormatRecognitionType) {
							ResponseFormatRecognitionType formatResponse = (ResponseFormatRecognitionType) opResponse;
							return formatResponse.getMimeType();
						}
					}
				}
			}

		} catch (Throwable e) {
			throw new Exception("Impossibile riconoscere il formato del file", e);
		}

		return null;
	}

	public String getWsEndpoint() {
		return wsEndpoint;
	}

	public void setWsEndpoint(String wsEndpoint) {
		this.wsEndpoint = wsEndpoint;
	}
}