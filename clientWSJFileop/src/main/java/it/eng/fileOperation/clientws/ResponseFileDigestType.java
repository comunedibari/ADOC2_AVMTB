
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ResponseFileDigestType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponseFileDigestType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType">
 *       &lt;sequence>
 *         &lt;element name="digestAlgId" type="{it.eng.fileoperation.ws}DigestAlgID" minOccurs="0"/>
 *         &lt;element name="encoding" type="{it.eng.fileoperation.ws}DigestEncID" minOccurs="0"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseFileDigestType", propOrder = {
    "digestAlgId",
    "encoding",
    "result"
})
@XmlSeeAlso({
    ResponseFileDigestUnpackType.class
})
public class ResponseFileDigestType
    extends AbstractResponseOperationType
{

    protected DigestAlgID digestAlgId;
    protected DigestEncID encoding;
    protected String result;

    /**
     * Recupera il valore della proprietà digestAlgId.
     * 
     * @return
     *     possible object is
     *     {@link DigestAlgID }
     *     
     */
    public DigestAlgID getDigestAlgId() {
        return digestAlgId;
    }

    /**
     * Imposta il valore della proprietà digestAlgId.
     * 
     * @param value
     *     allowed object is
     *     {@link DigestAlgID }
     *     
     */
    public void setDigestAlgId(DigestAlgID value) {
        this.digestAlgId = value;
    }

    /**
     * Recupera il valore della proprietà encoding.
     * 
     * @return
     *     possible object is
     *     {@link DigestEncID }
     *     
     */
    public DigestEncID getEncoding() {
        return encoding;
    }

    /**
     * Imposta il valore della proprietà encoding.
     * 
     * @param value
     *     allowed object is
     *     {@link DigestEncID }
     *     
     */
    public void setEncoding(DigestEncID value) {
        this.encoding = value;
    }

    /**
     * Recupera il valore della proprietà result.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Imposta il valore della proprietà result.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

}
