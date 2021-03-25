
package it.eng.fileOperation.clientws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ResponseFormatRecognitionType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponseFormatRecognitionType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType">
 *       &lt;sequence>
 *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datiFormato" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="idFormato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="datiFormatiInterni" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="newFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pdfConversion" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "ResponseFormatRecognitionType", propOrder = {
    "mimeType",
    "datiFormato",
    "datiFormatiInterni",
    "newFileName",
    "pdfConversion",
    "signConversion"
})
public class ResponseFormatRecognitionType
    extends AbstractResponseOperationType
{

    protected String mimeType;
    protected ResponseFormatRecognitionType.DatiFormato datiFormato;
    protected ResponseFormatRecognitionType.DatiFormatiInterni datiFormatiInterni;
    protected String newFileName;
    protected Boolean pdfConversion;
    protected Boolean signConversion;

    /**
     * Recupera il valore della proprietà mimeType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Imposta il valore della proprietà mimeType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
    }

    /**
     * Recupera il valore della proprietà datiFormato.
     * 
     * @return
     *     possible object is
     *     {@link ResponseFormatRecognitionType.DatiFormato }
     *     
     */
    public ResponseFormatRecognitionType.DatiFormato getDatiFormato() {
        return datiFormato;
    }

    /**
     * Imposta il valore della proprietà datiFormato.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseFormatRecognitionType.DatiFormato }
     *     
     */
    public void setDatiFormato(ResponseFormatRecognitionType.DatiFormato value) {
        this.datiFormato = value;
    }

    /**
     * Recupera il valore della proprietà datiFormatiInterni.
     * 
     * @return
     *     possible object is
     *     {@link ResponseFormatRecognitionType.DatiFormatiInterni }
     *     
     */
    public ResponseFormatRecognitionType.DatiFormatiInterni getDatiFormatiInterni() {
        return datiFormatiInterni;
    }

    /**
     * Imposta il valore della proprietà datiFormatiInterni.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseFormatRecognitionType.DatiFormatiInterni }
     *     
     */
    public void setDatiFormatiInterni(ResponseFormatRecognitionType.DatiFormatiInterni value) {
        this.datiFormatiInterni = value;
    }

    /**
     * Recupera il valore della proprietà newFileName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewFileName() {
        return newFileName;
    }

    /**
     * Imposta il valore della proprietà newFileName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewFileName(String value) {
        this.newFileName = value;
    }

    /**
     * Recupera il valore della proprietà pdfConversion.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPdfConversion() {
        return pdfConversion;
    }

    /**
     * Imposta il valore della proprietà pdfConversion.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPdfConversion(Boolean value) {
        this.pdfConversion = value;
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
     *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "mimeType"
    })
    public static class DatiFormatiInterni {

        @XmlElement(required = true)
        protected List<String> mimeType;

        /**
         * Gets the value of the mimeType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the mimeType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMimeType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getMimeType() {
            if (mimeType == null) {
                mimeType = new ArrayList<String>();
            }
            return this.mimeType;
        }

    }


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
     *         &lt;element name="idFormato" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "idFormato"
    })
    public static class DatiFormato {

        @XmlElement(required = true)
        protected String idFormato;

        /**
         * Recupera il valore della proprietà idFormato.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdFormato() {
            return idFormato;
        }

        /**
         * Imposta il valore della proprietà idFormato.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdFormato(String value) {
            this.idFormato = value;
        }

    }

}
