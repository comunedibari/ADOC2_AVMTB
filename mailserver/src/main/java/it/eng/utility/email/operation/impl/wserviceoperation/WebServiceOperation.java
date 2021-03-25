package it.eng.utility.email.operation.impl.wserviceoperation;

import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;

import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.XmlUtil;
import it.eng.utility.email.operation.AbstractMessageOperation;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.storage.StorageCenter;

/**
 * Operazione di chiamata al webservice configurato
 * 
 * @author michele
 */
@MessageOperation(description = "Effettua una chiamata ad un servizio esterno", name = "WebServiceOperation")
public class WebServiceOperation extends AbstractMessageOperation<WSServiceBean> {

	@ConfigOperation(title = "Url", name = "wsserviceoperation.serviceurl", description = "Indica l'url del servizio da chiamare")
	private String serviceurl = null;

	@ConfigOperation(title = "Nome operazione", name = "wsserviceoperation.opeartionname", description = "Indica l'operazione configurata nel sistema di destinazione")
	private String operationname = null;

	@Override
	public WSServiceBean elaborate(MessageInfos message) throws Exception {

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		if (serviceurl == null) {
			throw new Exception("URL del servizio non configurato!");
		}

		// Chiamata al metodo REST
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		MailWSOperationBean mailfile = new MailWSOperationBean();
		mailfile.setOperationname(operationname);
		if (message.isSpam() == null) {
			mailfile.setIsSpam(false);
		} else {
			mailfile.setIsSpam(message.isSpam());
		}

		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione corrente
			throw new InterruptedException();
		}

		// Converto le operazioni in stringhe
		HashMap<String, Object> opresult = message.getOpresult();

		HashMap<String, String> opresultstring = new HashMap<String, String>();
		if (opresult != null) {

			Set<String> keys = opresult.keySet();
			for (String key : keys) {
				opresultstring.put(key, XmlUtil.objectToXml(opresult.get(key)));
			}

		}
		mailfile.setResultoperation(opresultstring);

		mailfile.setMailboxproperties((getMailreceiver() != null? getMailreceiver().getConfigbean().getMailconfig() : getMailreceiverPop3().getConfigbean().getMailconfig()));

		FormDataMultiPart multi = new FormDataMultiPart();
		StorageCenter lStorageCenter = new StorageCenter();
		InputStream lInputStream;
		try {
			lInputStream = lStorageCenter.extract((getMailreceiver() != null ? getMailreceiver().getConfigbean().getMailboxid() : getMailreceiverPop3().getConfigbean().getMailboxid()), message.getUri());
		} catch (Throwable e) {
			throw new Exception(e);
		}
		multi = multi.field("mimemessage", lInputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
		multi = multi.field("operation", mailfile, MediaType.APPLICATION_XML_TYPE);

		WSServiceBean wsresult = service.path("service").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(WSServiceBean.class, multi);

		if (!wsresult.getIsok()) {
			throw new Exception(wsresult.getWsresult());
		}

		return wsresult;
	}

	private URI getBaseURI() {
		return UriBuilder.fromUri(serviceurl).build();
	}

	public String getServiceurl() {
		return serviceurl;
	}

	public void setServiceurl(String serviceurl) {
		this.serviceurl = serviceurl;
	}

	public String getOperationname() {
		return operationname;
	}

	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}

}