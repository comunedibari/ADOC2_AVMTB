
package it.eng.certverify.clientws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "CertificateVerifierImplService", targetNamespace = "verify.cryptoutil.eng.it", wsdlLocation = "http://192.168.11.52:8080/FileopWar/business/soap/crypto?wsdl")
public class CertificateVerifierImplService
    extends Service
{

    private final static URL CERTIFICATEVERIFIERIMPLSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(it.eng.certverify.clientws.CertificateVerifierImplService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = it.eng.certverify.clientws.CertificateVerifierImplService.class.getResource(".");
            url = new URL(baseUrl, "http://192.168.11.52:8080/FileopWar/business/soap/crypto?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://192.168.11.52:8080/FileopWar/business/soap/crypto?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        CERTIFICATEVERIFIERIMPLSERVICE_WSDL_LOCATION = url;
    }

    public CertificateVerifierImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CertificateVerifierImplService() {
        super(CERTIFICATEVERIFIERIMPLSERVICE_WSDL_LOCATION, new QName("verify.cryptoutil.eng.it", "CertificateVerifierImplService"));
    }

    /**
     * 
     * @return
     *     returns CertificateVerifier
     */
    @WebEndpoint(name = "CertificateVerifierImplPort")
    public CertificateVerifier getCertificateVerifierImplPort() {
        return super.getPort(new QName("verify.cryptoutil.eng.it", "CertificateVerifierImplPort"), CertificateVerifier.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CertificateVerifier
     */
    @WebEndpoint(name = "CertificateVerifierImplPort")
    public CertificateVerifier getCertificateVerifierImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("verify.cryptoutil.eng.it", "CertificateVerifierImplPort"), CertificateVerifier.class, features);
    }

}
