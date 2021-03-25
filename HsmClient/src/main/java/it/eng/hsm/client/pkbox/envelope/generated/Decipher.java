
package it.eng.hsm.client.pkbox.envelope.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="environment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="envelope" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="decryptKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="keyPin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerinfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "environment",
    "envelope",
    "decryptKey",
    "pin",
    "keyPin",
    "customerinfo"
})
@XmlRootElement(name = "decipher")
public class Decipher {

    @XmlElement(required = true, nillable = true)
    protected String environment;
    @XmlElement(required = true, nillable = true)
    protected byte[] envelope;
    @XmlElement(required = true, nillable = true)
    protected String decryptKey;
    @XmlElement(required = true, nillable = true)
    protected String pin;
    @XmlElement(required = true, nillable = true)
    protected String keyPin;
    @XmlElement(required = true, nillable = true)
    protected String customerinfo;

    /**
     * Recupera il valore della propriet� environment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Imposta il valore della propriet� environment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnvironment(String value) {
        this.environment = value;
    }

    /**
     * Recupera il valore della propriet� envelope.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEnvelope() {
        return envelope;
    }

    /**
     * Imposta il valore della propriet� envelope.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEnvelope(byte[] value) {
        this.envelope = value;
    }

    /**
     * Recupera il valore della propriet� decryptKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecryptKey() {
        return decryptKey;
    }

    /**
     * Imposta il valore della propriet� decryptKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecryptKey(String value) {
        this.decryptKey = value;
    }

    /**
     * Recupera il valore della propriet� pin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPin() {
        return pin;
    }

    /**
     * Imposta il valore della propriet� pin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPin(String value) {
        this.pin = value;
    }

    /**
     * Recupera il valore della propriet� keyPin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyPin() {
        return keyPin;
    }

    /**
     * Imposta il valore della propriet� keyPin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyPin(String value) {
        this.keyPin = value;
    }

    /**
     * Recupera il valore della propriet� customerinfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerinfo() {
        return customerinfo;
    }

    /**
     * Imposta il valore della propriet� customerinfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerinfo(String value) {
        this.customerinfo = value;
    }

}
