
package it.eng.hsm.client.pkbox.envelope.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="customerinfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="signerPin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="encoding" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
    "customerinfo",
    "signer",
    "pin",
    "signerPin",
    "mode",
    "encoding",
    "date"
})
@XmlRootElement(name = "sign")
public class Sign {

    @XmlElement(required = true, nillable = true)
    protected String environment;
    @XmlElement(required = true, nillable = true)
    protected byte[] data;
    @XmlElement(required = true, nillable = true)
    protected String customerinfo;
    @XmlElement(required = true, nillable = true)
    protected String signer;
    @XmlElement(required = true, nillable = true)
    protected String pin;
    @XmlElement(required = true, nillable = true)
    protected String signerPin;
    protected int mode;
    protected int encoding;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;

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
     * Recupera il valore della propriet� signer.
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
     * Imposta il valore della propriet� signer.
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
     * Recupera il valore della propriet� mode.
     * 
     */
    public int getMode() {
        return mode;
    }

    /**
     * Imposta il valore della propriet� mode.
     * 
     */
    public void setMode(int value) {
        this.mode = value;
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

    /**
     * Recupera il valore della propriet� date.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Imposta il valore della propriet� date.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

}