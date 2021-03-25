
package it.itagile.firmaremota.ws;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="timestampDetachedReturn" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "timestampDetachedReturn"
})
@XmlRootElement(name = "timestampDetachedResponse")
public class TimestampDetachedResponse {

    @XmlElement(required = true)
    protected byte[] timestampDetachedReturn;

    /**
     * Recupera il valore della proprietÓ timestampDetachedReturn.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getTimestampDetachedReturn() {
        return timestampDetachedReturn;
    }

    /**
     * Imposta il valore della proprietÓ timestampDetachedReturn.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setTimestampDetachedReturn(byte[] value) {
        this.timestampDetachedReturn = value;
    }

}
