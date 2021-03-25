//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.04.04 at 10:03:09 AM CEST 
//


package it.eng.module.foutility.beans.generated;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * risposta all'operazione di verifica, lo stato e gli errori riportati sono complessivi di tutte el verifiche effettuate, i risultati delle singole verifiche sono riportati nel tag sigVerifyResult
 * 
 * <p>Java class for ResponseSigVerify complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseSigVerify">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType">
 *       &lt;sequence>
 *         &lt;element name="sigVerifyResult" type="{it.eng.fileoperation.ws}SigVerifyResultType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseSigVerify", propOrder = {
    "sigVerifyResult"
})
public class ResponseSigVerify
    extends AbstractResponseOperationType
    implements Serializable
{

    @XmlElement(required = true)
    protected SigVerifyResultType sigVerifyResult;

    /**
     * Gets the value of the sigVerifyResult property.
     * 
     * @return
     *     possible object is
     *     {@link SigVerifyResultType }
     *     
     */
    public SigVerifyResultType getSigVerifyResult() {
        return sigVerifyResult;
    }

    /**
     * Sets the value of the sigVerifyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SigVerifyResultType }
     *     
     */
    public void setSigVerifyResult(SigVerifyResultType value) {
        this.sigVerifyResult = value;
    }

}
