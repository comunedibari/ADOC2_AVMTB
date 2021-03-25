package it.eng.hsm.pades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;

import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.RettangoloFirmaPadesBean;
import it.eng.hsm.HsmSignOptionFactory;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.utility.ui.sign.FileElaborate;
import it.eng.utility.ui.sign.SignerHashUtil;

public class FirmaPadesUtil {

	private static Logger log = Logger.getLogger(FirmaPadesUtil.class);

	public static byte[] generate(byte[] data, X509Certificate userCert, Hsm client, boolean visualizzaFirmaPades,
			RettangoloFirmaPadesBean rettangoloFirmaPadesBean) throws HsmClientSignatureException {
		try {
			PDFServerSigner signer = new PDFServerSigner();

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			IOUtils.copy(new ByteArrayInputStream(data), buffer);
			signer.setDocument(buffer.toByteArray());

			ByteArrayOutputStream response = null;
			if(client.getHsmConfig().getHsmType().equals(HsmType.PKBOX)){
				FileElaborate fileElaborate = signer.calcolaHashPdf(data, userCert, client, visualizzaFirmaPades, rettangoloFirmaPadesBean);
				List<HashRequestBean> listaHashDaFirmare = new ArrayList<HashRequestBean>();
				HashRequestBean hashRequestBean = new HashRequestBean();
				hashRequestBean.setHash(Base64.encodeBase64String(fileElaborate.getHash()));
				hashRequestBean.setSignOption(HsmSignOptionFactory.getSignOption(client.getHsmConfig().getHsmType()));
				listaHashDaFirmare.add(hashRequestBean);
				
				SignResponseBean responseHsm;
				try {
					responseHsm = client.firmaMultiplaHash(listaHashDaFirmare);
				
					MessageBean message = responseHsm.getMessage();
					if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
						log.error("Errore: - " + message.getCode() + " " + message.getDescription());
					}
					List<HashResponseBean> listHashResponseBean = responseHsm.getHashResponseBean();
					if ((listHashResponseBean != null) && (listHashResponseBean.size() >= 1) && (listHashResponseBean.get(0).getHashFirmata() != null)) {
						byte[] hashFirmata = listHashResponseBean.get(0).getHashFirmata();
						log.debug("hashFirmata " + new String(hashFirmata));
						
						byte[] dataBytes =Base64.decodeBase64(hashFirmata);
						log.debug("hashFirmata decodificata " + new String(dataBytes));
						
						CMSSignedData s = new CMSSignedData(dataBytes);
						File lFileFirmato = File.createTempFile("sign", "");
						fileElaborate.setSigned(lFileFirmato);
						
						SignerHashUtil.attachPdf(s.getEncoded(), fileElaborate);;
						
						ByteArrayOutputStream outputStream = fileElaborate.getOutStream();
						byte[] bytesfirmati = outputStream.toByteArray();
						log.debug("bytesfirmati  " + new String(bytesfirmati));
						
						return bytesfirmati;
					}
				} catch (UnsupportedOperationException e) {
					log.error("Errore nella firma remota", e);
					throw new HsmClientSignatureException(e.getMessage(), e);
				} catch (HsmClientConfigException e) {
					log.error("Errore nella firma remota", e);
					throw new HsmClientSignatureException(e.getMessage(), e);
				} catch (CMSException e) {
					log.error("Errore nella firma remota", e);
					throw new HsmClientSignatureException(e.getMessage(), e);
				} catch (Exception e) {
					log.error("Errore nella firma remota", e);
					throw new HsmClientSignatureException(e.getMessage(), e);
				}
				
						
			} else {
				response = signer.generaPdf(data, userCert, client, visualizzaFirmaPades, rettangoloFirmaPadesBean);
			}
			
			
			
			return response.toByteArray();
		} catch (IOException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		}

	}
	
	public static List<byte[]> generate(List<byte[]> listaFileDaFirmare, X509Certificate userCert, Hsm client, boolean visualizzaFirmaPades,
			RettangoloFirmaPadesBean rettangoloFirmaPadesBean) throws HsmClientSignatureException {
		try {
			PDFServerSigner signer = new PDFServerSigner();

			List<FileElaborate> fileElaborateList = new ArrayList<FileElaborate>();
			String sigName = "Test";
			String reason = "reason";
			String location = "location";
			String contact = "contact";
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			List<XMLGregorianCalendar> dataFirmaList = new ArrayList<XMLGregorianCalendar>();
			 
			for(byte[] data : listaFileDaFirmare){
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				IOUtils.copy(new ByteArrayInputStream(data), buffer);
				signer.setDocument(buffer.toByteArray());
			
				//FileElaborate fileElaborate = signer.calcolaHashPdf(data, userCert, client, visualizzaFirmaPades, rettangoloFirmaPadesBean);
				FileElaborate fileElaborate = new FileElaborate();
				
				Date date = new Date();
		        String dataString = formatter.format(date);
		        System.out.println(dataString);
				 
				XMLGregorianCalendar xmlDate = null;
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(date);
				 
				try{
				     xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
				}catch(Exception e){
				      e.printStackTrace();
				}
				dataFirmaList.add(xmlDate);
				byte[] hash = digestPdf(data, "2", xmlDate, sigName, reason, location, contact);
				fileElaborate.setHash(hash);
				fileElaborateList.add(fileElaborate);
				
			}
				
			List<HashRequestBean> listaHashDaFirmare = new ArrayList<HashRequestBean>();
			for(FileElaborate fileElaborate : fileElaborateList){
				HashRequestBean hashRequestBean = new HashRequestBean();
				hashRequestBean.setHash(Base64.encodeBase64String(fileElaborate.getHash()));
				hashRequestBean.setSignOption(HsmSignOptionFactory.getSignOption(client.getHsmConfig().getHsmType()));
				listaHashDaFirmare.add(hashRequestBean);
			}
				
			SignResponseBean responseHsm = client.firmaMultiplaHash(listaHashDaFirmare);
				
			MessageBean message = responseHsm.getMessage();
			if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
				log.error("Errore: - " + message.getCode() + " " + message.getDescription());
				throw new HsmClientSignatureException("Errore nella firma: " + message.getDescription());
			}
					
			List<HashResponseBean> listHashResponseBean = responseHsm.getHashResponseBean();
			List<byte[]> listaBytesfirmati = new ArrayList<byte[]>();
			if ( (listHashResponseBean != null) && (listHashResponseBean.size() >= 1) ) {
				for (int i = 0; i < listHashResponseBean.size(); i++) {
					
					try {
						log.debug("dimensione listHashResponseBean: " + listHashResponseBean.size());
						log.debug("i: " + i);
						log.debug("listHashResponseBean.get(i): " + listHashResponseBean.get(i));
						log.debug("listHashResponseBean.get(i).getMessage(): " + listHashResponseBean.get(i).getMessage());
						log.debug("listHashResponseBean.get(i).getMessage().getStatus(): " + listHashResponseBean.get(i).getMessage().getStatus());
						
						if (listHashResponseBean.get(i).getMessage().getStatus().equals(ResponseStatus.OK)) {
							byte[] hashFirmata = listHashResponseBean.get(i).getHashFirmata();
							log.debug("hashFirmata " + new String(hashFirmata));
						
							byte[] dataBytes =Base64.decodeBase64(hashFirmata);
							log.debug("hashFirmata decodificata " + new String(dataBytes));
						
							/*CMSSignedData s = new CMSSignedData(dataBytes);
							File lFileFirmato = File.createTempFile("sign", "");
							fileElaborateList.get(i).setSigned(lFileFirmato);
						
							SignerHashUtil.attachPdf(s.getEncoded(), fileElaborateList.get(i));;
						
							ByteArrayOutputStream outputStream = fileElaborateList.get(i).getOutStream();
							byte[] bytesfirmati = outputStream.toByteArray();*/
							
							XMLGregorianCalendar xmlDate = dataFirmaList.get(i);
							byte[] fileDaFirmare = listaFileDaFirmare.get(i); 
							byte[] bytesfirmati = mergeSignatureFile(fileDaFirmare, dataBytes/*, signatureEncoding*/, xmlDate, sigName, reason, location, contact);
							
							log.debug("bytesfirmati  " + new String(bytesfirmati));
							listaBytesfirmati.add(bytesfirmati);
						} else {
							log.error("Errore nella firma per il file numero " + i + " Errore: " + listHashResponseBean.get(i).getMessage().getDescription());
							throw new HsmClientSignatureException("Errore nella firma: " + listHashResponseBean.get(i).getMessage().getDescription());
						}
					} catch (Exception e) {
						log.error("Errore nella firma", e);
						throw new HsmClientSignatureException(e.getMessage(), e);
					}
				}		
						
			}
			
			return listaBytesfirmati;
		} catch (IOException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (UnsupportedOperationException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (HsmClientConfigException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		}

	}
	
	public static byte[] digestPdf( byte[] data, String encoding, XMLGregorianCalendar xmlDate, String sigName, String reason, String location, String contact) {
		
		URL url;
		try {
			url = new URL("https://pkboxrmt.csi.it:8443/pkserver/service/default/Utils?wsdl");
			QName qname = new QName("http://soap.remote.pkserver.it", "Utils");
			Service service = Service.create(url, qname);
			
			it.eng.hsm.client.pkbox.generated.UtilsPortType pkboxService = service.getPort(it.eng.hsm.client.pkbox.generated.UtilsPortType.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) pkboxService).getBinding();
			BindingProvider bindingProvider = (BindingProvider) pkboxService;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://pkboxrmt.csi.it:8443/pkserver/services/Utils?wsdl");
			
			
			int algorithm = 5;
			String environment = "default";
			
			System.out.println("---- Invoco il servizio digest con parametri ");
			System.out.println("environment " + environment );
			System.out.println("data " + data);
		
			//System.out.println("algorithm " + algorithm);
			System.out.println("encoding " + encoding);
			byte[] digest = null;
			boolean esito = false;
			int digestEncoding = Integer.parseInt(encoding);
			try {
				
				digest = pkboxService.pdfdigest(environment, data, sigName, null, reason, location, contact, xmlDate, null, 1, 1, 0, 0, algorithm, digestEncoding);
				esito = true;
				System.out.println("Servizio invocato -  " + new String(digest)+" FINE");
			}/*catch (PKBoxException_Exception e) {
				System.out.println("ERRORE PKBoxException_Exception");
				e.printStackTrace();
			}*/ catch (Exception e) {
				System.out.println("ERRORE Exception");
				e.printStackTrace();
			}
			
			return digest;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		return null;
	}

	public static byte[] mergeSignatureFile(byte[] originalData, byte[] signedData, /*String signatureEncoding1,*/ XMLGregorianCalendar xmlDate, String sigName, String reason, String location, String contact) {
		
		URL url;
		try {
			url = new URL("https://pkboxrmt.csi.it:8443/pkserver/service/default/Envelope?wsdl");
			QName qname = new QName("http://soap.remote.pkserver.it", "Envelope");
			Service service = Service.create(url, qname);
			
			it.eng.hsm.client.pkbox.envelope.generated.EnvelopePortType pkboxService = service.getPort(it.eng.hsm.client.pkbox.envelope.generated.EnvelopePortType.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) pkboxService).getBinding();
			BindingProvider bindingProvider = (BindingProvider) pkboxService;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "https://pkboxrmt.csi.it:8443/pkserver/services/Envelope?wsdl");
			
			//int encoding1 = Integer.parseInt(signatureEncoding);
			String environment = "default";
			
			System.out.println("---- Invoco il servizio merge con parametri ");
			System.out.println("environment " + environment );
			//System.out.println("signatureEncoding " + encoding);
			byte[] fileSigned = pkboxService.pdfmerge(environment, originalData, signedData, 
					sigName, null, reason, location, contact, xmlDate, null, 1, 1, 0, 0);
			
			return fileSigned;
		} catch (MalformedURLException e) {
			System.out.println("ERRORE  MalformedURLException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.eng.hsm.client.pkbox.envelope.generated.PKBoxException_Exception e) {
			System.out.println("ERRORE  PKBoxException_Exception");
			e.printStackTrace();
		} catch (Throwable e){
			System.out.println(" Throwable ");
			e.printStackTrace();
		}
		
		
		
		return null;
	}

}
