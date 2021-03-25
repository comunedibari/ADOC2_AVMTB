
package it.eng.hsm.client.pkbox.generated;

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
 *         &lt;element name="digest" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="digestAlgorithm" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="customerinfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="keyID" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="signer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signerPin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="algorithm" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="encoding" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "digest",
    "digestAlgorithm",
    "customerinfo",
    "keyID",
    "signer",
    "pin",
    "signerPin",
    "algorithm",
    "encoding"
})
@XmlRootElement(name = "rawsign2")
public class Rawsign2 {

    @XmlElement(required = true, nillable = true)
    protected String environment;
    @XmlElement(required = true, nillable = true)
    protected byte[] digest;
    protected int digestAlgorithm;
    @XmlElement(required = true, nillable = true)
    protected String customerinfo;
    @XmlElement(required = true, nillable = true)
    protected byte[] keyID;
    @XmlElement(required = true, nillable = true)
    protected String signer;
    @XmlElement(required = true, nillable = true)
    protected String pin;
    @XmlElement(required = true, nillable = true)
    protected String signerPin;
    protected int algorithm;
    protected int encoding;

    /**
     * Recupera il valore della proprietÓ environment.
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
     * Imposta il valore della proprietÓ environment.
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
     * Recupera il valore della proprietÓ digest.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDigest() {
        return digest;
    }

    /**
     * Imposta il valore della proprietÓ digest.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDigest(byte[] value) {
        this.digest = value;
    }

    /**
     * Recupera il valore della proprietÓ digestAlgorithm.
     * 
     */
    public int getDigestAlgorithm() {
        return digestAlgorithm;
    }

    /**
     * Imposta il valore della proprietÓ digestAlgorithm.
     * 
     */
    public void setDigestAlgorithm(int value) {
        this.digestAlgorithm = value;
    }

    /**
     * Recupera il valore della proprietÓ customerinfo.
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
     * Imposta il valore della proprietÓ customerinfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerinfo(String value) {
        this.customerinfo = value;
    }

    /**
     * Recupera il valore della proprietÓ keyID.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getKeyID() {
        return keyID;
    }

    /**
     * Imposta il valore della proprietÓ keyID.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setKeyID(byte[] value) {
        this.keyID = value;
    }

    /**
     * Recupera il valore della proprietÓ signer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigner() {
        return signer;
    }

    /**
     * Imposta il valore della proprietÓ signer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigner(String value) {
        this.signer = value;
    }

    /**
     * Recupera il valore della proprietÓ pin.
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
     * Imposta il valore della proprietÓ pin.
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
     * Recupera il valore della proprietÓ signerPin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignerPin() {
        return signerPin;
    }

    /**
     * Imposta il valore della proprietÓ signerPin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignerPin(String value) {
        this.signerPin = value;
    }

    /**
     * Recupera il valore della proprietÓ algorithm.
     * 
     */
    public int getAlgorithm() {
        return algorithm;
    }

    /**
     * Imposta il valore della proprietÓ algorithm.
     * 
     */
    public void setAlgorithm(int value) {
        this.algorithm = value;
    }

    /**
     * Recupera il valore della proprietÓ encoding.
     * 
     */
    public int getEncoding() {
        return encoding;
    }

    /**
     * Imposta il valore della proprietÓ encoding.
     * 
     */
    public void setEncoding(int value) {
        this.encoding = value;
    }

}
