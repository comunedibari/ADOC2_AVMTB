
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InputUnpackType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputUnpackType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType">
 *       &lt;sequence>
 *         &lt;element name="abilitaNomeSbustato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputUnpackType", propOrder = {
    "abilitaNomeSbustato"
})
public class InputUnpackType
    extends AbstractInputOperationType
{

    @XmlElement(defaultValue = "false")
    protected Boolean abilitaNomeSbustato;

    /**
     * Recupera il valore della proprietà abilitaNomeSbustato.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAbilitaNomeSbustato() {
        return abilitaNomeSbustato;
    }

    /**
     * Imposta il valore della proprietà abilitaNomeSbustato.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAbilitaNomeSbustato(Boolean value) {
        this.abilitaNomeSbustato = value;
    }

}
