
package it.eng.hsm.client.pkbox.generated;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "PKBoxException", targetNamespace = "http://soap.remote.pkserver.it")
public class PKBoxException_Exception
    extends java.lang.Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private PKBoxException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public PKBoxException_Exception(String message, PKBoxException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public PKBoxException_Exception(String message, PKBoxException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: it.eng.hsm.client.pkbox.generated.PKBoxException
     */
    public PKBoxException getFaultInfo() {
        return faultInfo;
    }

}
