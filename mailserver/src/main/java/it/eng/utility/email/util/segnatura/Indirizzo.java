//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.12 at 12:00:07 PM CEST 
//


package it.eng.utility.email.util.segnatura;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Indirizzo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Indirizzo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}Toponimo"/>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}Civico"/>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}CAP"/>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}Comune"/>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}Provincia"/>
 *         &lt;element ref="{http://www.digitPa.gov.it/protocollo/}Nazione" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Indirizzo", propOrder = {
    "toponimo",
    "civico",
    "cap",
    "comune",
    "provincia",
    "nazione"
})
public class Indirizzo {

    @XmlElement(name = "Toponimo", required = true)
    protected Toponimo toponimo;
    @XmlElement(name = "Civico", required = true)
    protected Civico civico;
    @XmlElement(name = "CAP", required = true)
    protected CAP cap;
    @XmlElement(name = "Comune", required = true)
    protected Comune comune;
    @XmlElement(name = "Provincia", required = true)
    protected Provincia provincia;
    @XmlElement(name = "Nazione")
    protected Nazione nazione;

    /**
     * Gets the value of the toponimo property.
     * 
     * @return
     *     possible object is
     *     {@link Toponimo }
     *     
     */
    public Toponimo getToponimo() {
        return toponimo;
    }

    /**
     * Sets the value of the toponimo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Toponimo }
     *     
     */
    public void setToponimo(Toponimo value) {
        this.toponimo = value;
    }

    /**
     * Gets the value of the civico property.
     * 
     * @return
     *     possible object is
     *     {@link Civico }
     *     
     */
    public Civico getCivico() {
        return civico;
    }

    /**
     * Sets the value of the civico property.
     * 
     * @param value
     *     allowed object is
     *     {@link Civico }
     *     
     */
    public void setCivico(Civico value) {
        this.civico = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link CAP }
     *     
     */
    public CAP getCAP() {
        return cap;
    }

    /**
     * Sets the value of the cap property.
     * 
     * @param value
     *     allowed object is
     *     {@link CAP }
     *     
     */
    public void setCAP(CAP value) {
        this.cap = value;
    }

    /**
     * Gets the value of the comune property.
     * 
     * @return
     *     possible object is
     *     {@link Comune }
     *     
     */
    public Comune getComune() {
        return comune;
    }

    /**
     * Sets the value of the comune property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comune }
     *     
     */
    public void setComune(Comune value) {
        this.comune = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link Provincia }
     *     
     */
    public Provincia getProvincia() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Provincia }
     *     
     */
    public void setProvincia(Provincia value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the nazione property.
     * 
     * @return
     *     possible object is
     *     {@link Nazione }
     *     
     */
    public Nazione getNazione() {
        return nazione;
    }

    /**
     * Sets the value of the nazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link Nazione }
     *     
     */
    public void setNazione(Nazione value) {
        this.nazione = value;
    }

}
