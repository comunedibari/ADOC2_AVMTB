
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InputConversionType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputConversionType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType">
 *       &lt;sequence>
 *         &lt;element name="pdfA" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="signConversion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputConversionType", propOrder = {
    "pdfA",
    "signConversion"
})
public class InputConversionType
    extends AbstractInputOperationType
{

    @XmlElement(defaultValue = "false")
    protected Boolean pdfA;
    @XmlElement(defaultValue = "false")
    protected Boolean signConversion;

    /**
     * Recupera il valore della proprietà pdfA.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPdfA() {
        return pdfA;
    }

    /**
     * Imposta il valore della proprietà pdfA.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPdfA(Boolean value) {
        this.pdfA = value;
    }

    /**
     * Recupera il valore della proprietà signConversion.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSignConversion() {
        return signConversion;
    }

    /**
     * Imposta il valore della proprietà signConversion.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSignConversion(Boolean value) {
        this.signConversion = value;
    }

}
