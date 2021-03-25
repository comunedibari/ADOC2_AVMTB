package it.eng.hsm.client.impl;

import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.InfoCertConfig;
import it.eng.hsm.client.config.PadesConfig;
import it.eng.hsm.client.config.RestConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.option.SignOption;
import it.eng.hsm.client.util.ConnectionFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.impl.provider.entity.InputStreamProvider;
import com.sun.jersey.core.impl.provider.entity.StringProvider;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;


public class HsmInfoCert extends HsmImpl {

	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public SignResponseBean firmaCades(byte[] bytesFileDaFirmare, SignOption infoCertOption) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione"  + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfoCertConfig)) {
			logger.error("Non e' stata specificata la configurazione per InfoCert" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione InfoCert non specificata");
		}
		
		InfoCertConfig infoCertConfig = (InfoCertConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = infoCertConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di firma non specificata");
		}
		
		String urlEndpoint = restConfig.getUrlEndpoint();
		String alias = infoCertConfig.getAlias();
		if ( urlEndpoint == null || alias==null ) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}
		
		DefaultClientConfig cc = new DefaultClientConfig();
		
		Client client = null;
		if(infoCertConfig.getProxyConfig()!=null ){
			URLConnectionClientHandler ch  = new URLConnectionClientHandler(new ConnectionFactory(infoCertConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}
		
		String path = "";
		if( infoCertConfig.isAuto() ){
			path = "/auto/sign/cades/" + alias ;	
		}else {
			path = "/remote/sign/cades/" + alias ;
		}
		logger.debug("Endpoint " + urlEndpoint + path );
		
		WebResource webResource = client.resource( urlEndpoint  ).path( path );
		
		FormDataMultiPart multi = new FormDataMultiPart();
		multi = multi.field("pin", infoCertConfig.getPin() );
		if( !infoCertConfig.isAuto() ){
			multi = multi.field("otp", infoCertConfig.getOtp() );
		}
		
		FormDataBodyPart bodyPart = new FormDataBodyPart("contentToSign-0",
			    new ByteArrayInputStream(bytesFileDaFirmare),
			    MediaType.APPLICATION_OCTET_STREAM_TYPE);
//		FileDataBodyPart bodyPart = new FileDataBodyPart("contentToSign-0",
//                file,
//                MediaType.APPLICATION_OCTET_STREAM_TYPE);
		bodyPart.setContentDisposition(
                FormDataContentDisposition.name("contentToSign-0")
                        .fileName("contentToSign-0").build());
		
		if( bodyPart!=null )
			multi.bodyPart( bodyPart);
		
		Builder restBuilder = webResource.acceptLanguage(Locale.ITALIAN).type(MediaType.MULTIPART_FORM_DATA_TYPE);
		ClientResponse response = restBuilder.post(ClientResponse.class, multi);
		logger.info("response.getStatus() " + response.getStatus());
		
		if (Response.Status.OK.getStatusCode() != response.getStatus()) {
			String messageString = response.getEntity(String.class);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma cades - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			signResponseBean.getFileResponseBean().setMessageBean(messageBean);
		} else {
			InputStream output = response.getEntity(InputStream.class);
			MessageBean messageBean = new MessageBean();
			try {
				messageBean.setStatus(ResponseStatus.OK);
				signResponseBean.getFileResponseBean().setMessageBean(messageBean);
				signResponseBean.getFileResponseBean().setFileFirmato(IOUtils.toByteArray(output));
			} catch (IOException e) {
				messageBean.setDescription(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signResponseBean.getFileResponseBean().setMessageBean(messageBean);
			}
		}

		return signResponseBean;
		//throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SignResponseBean firmaPades(byte[] bytesFileDaFirmare, SignOption infoCertOption)
		throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException  {
		logger.debug("Metodo di firma file pades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfoCertConfig)) {
			logger.error("Non e' stata specificata la configurazione per InfoCert" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione InfoCert non specificata");
		}
		
		InfoCertConfig infoCertConfig = (InfoCertConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = infoCertConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di firma non specificata");
		}
		
		String urlEndpoint = restConfig.getUrlEndpoint();
		String alias = infoCertConfig.getAlias();
		logger.debug("urlEndpoint: " + urlEndpoint + " alias: " + alias );
		if ( urlEndpoint == null || alias==null ) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}
		
		PadesConfig padesConfig = infoCertConfig.getPadesConfig();
		if (padesConfig == null) {
			logger.error("Non e' stata specificata la configurazione per la firma pades" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Pades non specificata");
		}
		
		DefaultClientConfig cc = new DefaultClientConfig();
//		cc.getClasses().add(StringProvider.class);///////// 
//		cc.getClasses().add(InputStreamProvider.class);////
//		cc.getClasses().add(MultiPartWriter.class);////////
		Client client = null;
		if(infoCertConfig.getProxyConfig()!=null ){
			URLConnectionClientHandler ch  = new URLConnectionClientHandler(new ConnectionFactory(infoCertConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}
		
		String path = "";
		if( infoCertConfig.isAuto() ){
			path = "/auto/sign/pades/" + alias ;	
		}else {
			path = "/remote/sign/pades/" + alias ;
		}
		logger.debug("Endpoint " + urlEndpoint + path );
		
		WebResource webResource = client.resource( urlEndpoint  ).path( path );
		
		FormDataMultiPart multi = new FormDataMultiPart();
		multi = multi.field("pin", infoCertConfig.getPin() );
		if( !infoCertConfig.isAuto() ){
			multi = multi.field("otp", infoCertConfig.getOtp() );
		}
		
		FormDataBodyPart bodyPart = new FormDataBodyPart("contentToSign-0",
			    new ByteArrayInputStream(bytesFileDaFirmare),
			    MediaType.APPLICATION_OCTET_STREAM_TYPE);
//		FileDataBodyPart bodyPart = new FileDataBodyPart("contentToSign-0",
//                file,
//                MediaType.APPLICATION_OCTET_STREAM_TYPE);
		bodyPart.setContentDisposition(
                FormDataContentDisposition.name("contentToSign-0")
                        .fileName("contentToSign-0").build());
		
		multi = multi.field("box_signature_page", padesConfig.getNumPagina() );
		multi = multi.field("box_signature_llx", padesConfig.getLeftX() );
		multi = multi.field("box_signature_lly", padesConfig.getLeftY() );
		multi = multi.field("box_signature_urx", padesConfig.getRightX() );
		multi = multi.field("box_signature_ury", padesConfig.getRightY() );
		multi = multi.field("box_signature_reason", padesConfig.getReason() );
		multi = multi.field("box_signature_lbl_reason", padesConfig.getReason() );
		
		if( bodyPart!=null ){
			multi.bodyPart( bodyPart);
		}
		Builder restBuilder = webResource.acceptLanguage(Locale.ITALIAN).
			type(MediaType.MULTIPART_FORM_DATA);
		ClientResponse response = restBuilder.post(ClientResponse.class, multi);
		
		logger.info("response.getStatus() " + response.getStatus());
		if (Response.Status.OK.getStatusCode() != response.getStatus()) {
			String messageString = response.getEntity(String.class);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma pades - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			signResponseBean.getFileResponseBean().setMessageBean(messageBean);
		} else {
			InputStream output = response.getEntity(InputStream.class);
			MessageBean messageBean = new MessageBean();
			try {
				messageBean.setStatus(ResponseStatus.OK);
				signResponseBean.getFileResponseBean().setMessageBean(messageBean);
				signResponseBean.getFileResponseBean().setFileFirmato(IOUtils.toByteArray(output));
			} catch (IOException e) {
				messageBean.setDescription(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma pades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signResponseBean.getFileResponseBean().setMessageBean(messageBean);
			}
		}

		return signResponseBean;
	}

	@Override
	public SignResponseBean firmaCadesParallela(byte[] bytesFileDaFirmare, SignOption infoCertOption) 
		throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}
	
	@Override
	public SignResponseBean firmaXades(byte[] bytesFileDaFirmare, SignOption infoCertOption)
		throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file xades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfoCertConfig)) {
			logger.error("Non e' stata specificata la configurazione per InfoCert" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione InfoCert non specificata");
		}
		
		InfoCertConfig infoCertConfig = (InfoCertConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = infoCertConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di firma non specificata");
		}
		
		String urlEndpoint = restConfig.getUrlEndpoint();
		String alias = infoCertConfig.getAlias();
		logger.debug("urlEndpoint: " + urlEndpoint + " alias: " + alias );
		if ( urlEndpoint == null || alias==null ) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}
		
		DefaultClientConfig cc = new DefaultClientConfig();
//		cc.getClasses().add(StringProvider.class);///////// 
//		cc.getClasses().add(InputStreamProvider.class);////
//		cc.getClasses().add(MultiPartWriter.class);////////
		Client client = null;
		if(infoCertConfig.getProxyConfig()!=null ){
			URLConnectionClientHandler ch  = new URLConnectionClientHandler(new ConnectionFactory(infoCertConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}
		
		String path = "";
		if( infoCertConfig.isAuto() ){
			path = "/auto/sign/xades-enveloped/" + alias ;	
		}else {
			path = "/remote/sign/xades-enveloped/" + alias ;
		}
		logger.debug("Endpoint " + urlEndpoint + path );
		
		WebResource webResource = client.resource( urlEndpoint  ).path( path );
		
		FormDataMultiPart multi = new FormDataMultiPart();
		multi = multi.field("pin", infoCertConfig.getPin() );
		if( !infoCertConfig.isAuto() ){
			multi = multi.field("otp", infoCertConfig.getOtp() );
		}
		
		FormDataBodyPart bodyPart = new FormDataBodyPart("contentToSign-0",
			    new ByteArrayInputStream(bytesFileDaFirmare),
			    MediaType.APPLICATION_OCTET_STREAM_TYPE);
//		FileDataBodyPart bodyPart = new FileDataBodyPart("contentToSign-0",
//                file,
//                MediaType.APPLICATION_OCTET_STREAM_TYPE);
		bodyPart.setContentDisposition(
                FormDataContentDisposition.name("contentToSign-0")
                        .fileName("contentToSign-0").build());
		
		if( bodyPart!=null ){
			multi.bodyPart( bodyPart);
		}
		Builder restBuilder = webResource.acceptLanguage(Locale.ITALIAN).
			type(MediaType.MULTIPART_FORM_DATA);
		ClientResponse response = restBuilder.post(ClientResponse.class, multi);
		
		logger.info("response.getStatus() " + response.getStatus());
		
		if (Response.Status.OK.getStatusCode() != response.getStatus()) {
			String messageString = response.getEntity(String.class);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma xades - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			signResponseBean.getFileResponseBean().setMessageBean(messageBean);
		} else {
			InputStream output = response.getEntity(InputStream.class);
			MessageBean messageBean = new MessageBean();
			try {
				messageBean.setStatus(ResponseStatus.OK);
				signResponseBean.getFileResponseBean().setMessageBean(messageBean);
				signResponseBean.getFileResponseBean().setFileFirmato(IOUtils.toByteArray(output));
			} catch (IOException e) {
				messageBean.setDescription(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma xades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signResponseBean.getFileResponseBean().setMessageBean(messageBean);
			}
		}

		return signResponseBean;
		//throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SignResponseBean firmaMultiplaHash( List<HashRequestBean> listaHashDaFirmare )
		throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public OtpResponseBean richiediOTP() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades - INIZIO");
		OtpResponseBean otpResponseBean = new OtpResponseBean();
		
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof InfoCertConfig)) {
			logger.error("Non e' stata specificata la configurazione per InfoCert" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione InfoCert non specificata");
		}
		
		InfoCertConfig infoCertConfig = (InfoCertConfig) hsmConfig.getClientConfig();
		RestConfig restConfig = infoCertConfig.getRestConfig();
		if (restConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il servizio di richiesta otp" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio di richiesta otp non specificata");
		}
		
		String urlEndpoint = restConfig.getUrlEndpoint();
		String alias = infoCertConfig.getAlias();
		logger.debug("urlEndpoint: " + urlEndpoint + " alias: " + alias );
		if ( urlEndpoint == null || alias==null ) {
			logger.error("Configurazione servizio incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione servizio incompleta");
		}
		
		DefaultClientConfig cc = new DefaultClientConfig();
		
		Client client = null;
		if(infoCertConfig.getProxyConfig()!=null ){
			URLConnectionClientHandler ch  = new URLConnectionClientHandler(new ConnectionFactory(infoCertConfig.getProxyConfig()));
			client = new Client(ch);
		} else {
			client = Client.create(cc);
		}
		logger.debug("Endpoint " + urlEndpoint + "/request-otp/" + alias );
		
		WebResource webResource = client.resource( urlEndpoint  ).path( "/request-otp/" + alias );
		
		ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class);
		if (Response.Status.OK.getStatusCode() != response.getStatus()) {
			String messageString = response.getEntity(String.class);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di richiesta otp - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			otpResponseBean.setMessage(messageBean);
		} else {
			logger.debug("Otp innviato");
			MessageBean messageBean = new MessageBean();
			messageBean.setStatus(ResponseStatus.OK);
			otpResponseBean.setMessage(messageBean);
		}
		
		return otpResponseBean;
	}

	@Override
	public CertResponseBean getUserCertificateList() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SessionResponseBean apriSessioneFirmaMultipla() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public SignResponseBean firmaMultiplaHashInSession(
			List<HashRequestBean> listaHashDaFirmare, String sessionId) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}

	@Override
	public MessageBean chiudiSessioneFirmaMultipla( String sessionId )
		throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalita' non supportata");
	}
}
