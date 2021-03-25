
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ResponseUnpackType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponseUnpackType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType">
 *       &lt;sequence>
 *         &lt;element name="nomeFileSbustato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseUnpackType", propOrder = {
    "nomeFileSbustato"
})
public class ResponseUnpackType
    extends AbstractResponseOperationType
{

    protected String nomeFileSbustato;

    /**
     * Recupera il valore della proprietà nomeFileSbustato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFileSbustato() {
        return nomeFileSbustato;
    }

    /**
     * Imposta il valore della proprietà nomeFileSbustato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFileSbustato(String value) {
        this.nomeFileSbustato = value;
    }

}
