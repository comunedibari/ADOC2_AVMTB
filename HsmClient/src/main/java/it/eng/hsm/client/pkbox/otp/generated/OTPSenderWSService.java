
package it.eng.hsm.client.pkbox.otp.generated;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "OTPSenderWSService", targetNamespace = "http://otp.webservice.ncfr.infocert.it/", wsdlLocation = "https://ncfr.infocert.it/ncfr-webservice/OTPSender?wsdl")
public class OTPSenderWSService
    extends Service
{

    private final static URL OTPSENDERWSSERVICE_WSDL_LOCATION;
    private final static WebServiceException OTPSENDERWSSERVICE_EXCEPTION;
    private final static QName OTPSENDERWSSERVICE_QNAME = new QName("http://otp.webservice.ncfr.infocert.it/", "OTPSenderWSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://ncfr.infocert.it/ncfr-webservice/OTPSender?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        OTPSENDERWSSERVICE_WSDL_LOCATION = url;
        OTPSENDERWSSERVICE_EXCEPTION = e;
    }

    public OTPSenderWSService() {
        super(__getWsdlLocation(), OTPSENDERWSSERVICE_QNAME);
    }

    public OTPSenderWSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), OTPSENDERWSSERVICE_QNAME, features);
    }

    public OTPSenderWSService(URL wsdlLocation) {
        super(wsdlLocation, OTPSENDERWSSERVICE_QNAME);
    }

    public OTPSenderWSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, OTPSENDERWSSERVICE_QNAME, features);
    }

    public OTPSenderWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OTPSenderWSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns OTPSenderWS
     */
    @WebEndpoint(name = "OTPSenderWSPort")
    public OTPSenderWS getOTPSenderWSPort() {
        return super.getPort(new QName("http://otp.webservice.ncfr.infocert.it/", "OTPSenderWSPort"), OTPSenderWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OTPSenderWS
     */
    @WebEndpoint(name = "OTPSenderWSPort")
    public OTPSenderWS getOTPSenderWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://otp.webservice.ncfr.infocert.it/", "OTPSenderWSPort"), OTPSenderWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (OTPSENDERWSSERVICE_EXCEPTION!= null) {
            throw OTPSENDERWSSERVICE_EXCEPTION;
        }
        return OTPSENDERWSSERVICE_WSDL_LOCATION;
    }

}
