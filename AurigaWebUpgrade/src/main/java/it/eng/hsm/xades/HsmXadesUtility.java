package it.eng.hsm.xades;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.hsm.HsmBaseUtility;
import it.eng.utility.storageutil.exception.StorageException;

public class HsmXadesUtility extends HsmBaseUtility {

	public FirmaHsmBean firmaXades(FirmaHsmBean bean) throws StorageException {
		// try {
		// URL url = new URL(wsdlEndpoint);
		// QName qname = new QName(serviceNS, serviceName);
		// Service service = Service.create(url, qname);
		// ArubaSignService arubaService = service.getPort(ArubaSignService.class);
		// SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();
		//
		// SignRequestV2 signRequest = new SignRequestV2();
		// signRequest.setCertID("AS0");
		//
		// signRequest.setTransport( TypeTransport.BYNARYNET );
		//
		// StorageService storageService = StorageImplementation.getStorage();
		// File fileDaFirmare = storageService.getRealFile(bean.getListaFileDaFirmare().get(0).getUri());
		// byte[] bytesFileDaFirmare = getFileBytes( fileDaFirmare );
		//
		// signRequest.setBinaryinput( bytesFileDaFirmare );
		// signRequest.setRequiredmark(false);
		//
		// Auth identity = new Auth();
		// identity.setUser( getUser() );
		// //identity.setUserPWD( userPassword );
		//
		// identity.setDelegatedUser( getDelegatedUser() );
		// identity.setDelegatedPassword( getDelegatedPassword() );
		// identity.setDelegatedDomain("engineering");
		//
		// //identity.setTypeHSM("COSIGN");
		// identity.setTypeOtpAuth("engineering");
		// identity.setOtpPwd("dsign");
		// signRequest.setIdentity( identity );
		//
		// XmlSignatureParameter parameter = new XmlSignatureParameter();
		// parameter.setCanonicalizedType( CanonicalizedType.ALGO_ID_C_14_N_11_WITH_COMMENTS);
		// parameter.setType( XmlSignatureType.XMLENVELOPED );
		// SignReturnV2 response = arubaService.xmlsignature(signRequest, parameter );
		//
		// if( response!=null ){
		// if( response.getStatus()!=null && response.getStatus().equalsIgnoreCase("KO") ){
		//
		// HashMap<String , String> error = new HashMap<String, String>();
		// error.put(bean.getListaFileDaFirmare().get(0).getRealName(), response.getDescription());
		// bean.setErrorMessages(error);
		//
		// }
		//
		// }
		//
		// } catch (TypeOfTransportNotImplemented_Exception e) {
		// } catch (MalformedURLException e) {
		// }
		return bean;
	}

	public FirmaHsmBean firmaXadesMultipla(FirmaHsmBean bean) throws StorageException {
		// try {
		// URL url = new URL(wsdlEndpoint);
		// QName qname = new QName(serviceNS, serviceName);
		// Service service = Service.create(url, qname);
		// ArubaSignService arubaService = service.getPort(ArubaSignService.class);
		// SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();
		//
		// SignRequestV2 signRequest = new SignRequestV2();
		// signRequest.setCertID("AS0");
		//
		// signRequest.setTransport( TypeTransport.BYNARYNET );
		//
		// StorageService storageService = StorageImplementation.getStorage();
		//
		// signRequest.setRequiredmark(false);
		//
		// Auth identity = new Auth();
		// identity.setUser( getUser() );
		// //identity.setUserPWD( userPassword );
		//
		// identity.setDelegatedUser( getDelegatedUser() );
		// identity.setDelegatedPassword( getDelegatedPassword() );
		// identity.setDelegatedDomain("engineering");
		//
		// //identity.setTypeHSM("COSIGN");
		// identity.setTypeOtpAuth("engineering");
		// identity.setOtpPwd("dsign");
		// signRequest.setIdentity( identity );
		//
		// HashMap<String , String> error = new HashMap<String, String>();
		// List<String> fileFirmati = new ArrayList<String>();
		//
		// XmlSignatureParameter parameter = new XmlSignatureParameter();
		// parameter.setCanonicalizedType( CanonicalizedType.ALGO_ID_C_14_N_11_WITH_COMMENTS);
		// parameter.setType( XmlSignatureType.XMLENVELOPED );
		//
		// for (int i=0; i < bean.getListaFileDaFirmare().size(); i++ ) {
		//
		// String uriFile = bean.getListaFileDaFirmare().get(i).getUri();
		//
		// File fileDaFirmare = storageService.getRealFile(uriFile);
		// byte[] bytesFileDaFirmare = getFileBytes( fileDaFirmare );
		//
		// signRequest.setBinaryinput(bytesFileDaFirmare);
		//
		// SignReturnV2 response = arubaService.xmlsignature(signRequest, parameter );
		//
		// if( response!=null ){
		// if( response.getStatus()!=null && response.getStatus().equalsIgnoreCase("KO") )
		//
		// error.put(bean.getListaFileDaFirmare().get(i).getRealName(), response.getDescription());
		//
		// else
		// fileFirmati.add(bean.getListaFileDaFirmare().get(i).getRealName());
		//
		// }
		//
		// }
		//
		// bean.setErrorMessages(error);
		// bean.setFileFirmati(fileFirmati);
		//
		// }
		// catch (TypeOfTransportNotImplemented_Exception e) {
		// }
		// catch (MalformedURLException e) {
		// }
		return bean;
	}

}
