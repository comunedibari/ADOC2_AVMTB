//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.04.04 at 10:03:09 AM CEST 
//


package it.eng.module.foutility.beans.generated;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * risposta per l'operazione di sbustamento
 * 
 * <p>Java class for ResponseUnpackType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
    implements Serializable
{

    protected String nomeFileSbustato;

    /**
     * Gets the value of the nomeFileSbustato property.
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
     * Sets the value of the nomeFileSbustato property.
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