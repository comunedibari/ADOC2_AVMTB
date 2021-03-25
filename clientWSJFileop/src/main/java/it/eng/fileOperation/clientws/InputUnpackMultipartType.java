
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InputUnpackMultipartType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputUnpackMultipartType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType">
 *       &lt;sequence>
 *         &lt;element name="recursive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="abilitaSbustamento" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputUnpackMultipartType", propOrder = {
    "recursive",
    "abilitaSbustamento"
})
public class InputUnpackMultipartType
    extends AbstractInputOperationType
{

    @XmlElement(defaultValue = "true")
    protected Boolean recursive;
    @XmlElement(defaultValue = "true")
    protected Boolean abilitaSbustamento;

    /**
     * Recupera il valore della proprietà recursive.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRecursive() {
        return recursive;
    }

    /**
     * Imposta il valore della proprietà recursive.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRecursive(Boolean value) {
        this.recursive = value;
    }

    /**
     * Recupera il valore della proprietà abilitaSbustamento.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAbilitaSbustamento() {
        return abilitaSbustamento;
    }

    /**
     * Imposta il valore della proprietà abilitaSbustamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAbilitaSbustamento(Boolean value) {
        this.abilitaSbustamento = value;
    }

}
