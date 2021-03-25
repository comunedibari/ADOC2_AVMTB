
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
    "envelope",
    "encoding"
})
@XmlRootElement(name = "detach")
public class Detach {

    @XmlElement(required = true, nillable = true)
    protected String environment;
    @XmlElement(required = true, nillable = true)
    protected byte[] envelope;
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
     * Recupera il valore della proprietÓ envelope.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEnvelope() {
        return envelope;
    }

    /**
     * Imposta il valore della proprietÓ envelope.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEnvelope(byte[] value) {
        this.envelope = value;
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
