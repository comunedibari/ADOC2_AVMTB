
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
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="dataURI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="algorithm" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hashProtected" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mediaType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signerPin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerinfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "data",
    "dataURI",
    "algorithm",
    "hashProtected",
    "fileName",
    "mediaType",
    "pin",
    "signerPin",
    "customerinfo",
    "encoding"
})
@XmlRootElement(name = "createTimeStampedData")
public class CreateTimeStampedData {

    @XmlElement(required = true, nillable = true)
    protected String environment;
    @XmlElement(required = true, nillable = true)
    protected byte[] data;
    @XmlElement(required = true, nillable = true)
    protected String dataURI;
    protected int algorithm;
    protected boolean hashProtected;
    @XmlElement(required = true, nillable = true)
    protected String fileName;
    @XmlElement(required = true, nillable = true)
    protected String mediaType;
    @XmlElement(required = true, nillable = true)
    protected String pin;
    @XmlElement(required = true, nillable = true)
    protected String signerPin;
    @XmlElement(required = true, nillable = true)
    protected String customerinfo;
    protected int encoding;

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
     * Recupera il valore della propriet� data.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Imposta il valore della propriet� data.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setData(byte[] value) {
        this.data = value;
    }

    /**
     * Recupera il valore della propriet� dataURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataURI() {
        return dataURI;
    }

    /**
     * Imposta il valore della propriet� dataURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataURI(String value) {
        this.dataURI = value;
    }

    /**
     * Recupera il valore della propriet� algorithm.
     * 
     */
    public int getAlgorithm() {
        return algorithm;
    }

    /**
     * Imposta il valore della propriet� algorithm.
     * 
     */
    public void setAlgorithm(int value) {
        this.algorithm = value;
    }

    /**
     * Recupera il valore della propriet� hashProtected.
     * 
     */
    public boolean isHashProtected() {
        return hashProtected;
    }

    /**
     * Imposta il valore della propriet� hashProtected.
     * 
     */
    public void setHashProtected(boolean value) {
        this.hashProtected = value;
    }

    /**
     * Recupera il valore della propriet� fileName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Imposta il valore della propriet� fileName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Recupera il valore della propriet� mediaType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Imposta il valore della propriet� mediaType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaType(String value) {
        this.mediaType = value;
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
     * Recupera il valore della propriet� signerPin.
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
     * Imposta il valore della propriet� signerPin.
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

    /**
     * Recupera il valore della propriet� encoding.
     * 
     */
    public int getEncoding() {
        return encoding;
    }

    /**
     * Imposta il valore della propriet� encoding.
     * 
     */
    public void setEncoding(int value) {
        this.encoding = value;
    }

}
