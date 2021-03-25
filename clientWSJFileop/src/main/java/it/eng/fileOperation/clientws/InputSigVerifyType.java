
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per InputSigVerifyType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InputSigVerifyType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractInputOperationType">
 *       &lt;sequence>
 *         &lt;element name="dataRif" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="recursive" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="childValidation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="signatureVerify" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CRLCheck" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="CAReliability" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="detectCode" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TimestampVerifiy" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TSAReliability" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputSigVerifyType", propOrder = {
    "dataRif",
    "recursive",
    "childValidation",
    "signatureVerify",
    "timestampVerifiy"
})
public class InputSigVerifyType
    extends AbstractInputOperationType
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRif;
    @XmlElement(defaultValue = "true")
    protected Boolean recursive;
    @XmlElement(defaultValue = "false")
    protected Boolean childValidation;
    protected InputSigVerifyType.SignatureVerify signatureVerify;
    @XmlElement(name = "TimestampVerifiy")
    protected InputSigVerifyType.TimestampVerifiy timestampVerifiy;

    /**
     * Recupera il valore della proprietà dataRif.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRif() {
        return dataRif;
    }

    /**
     * Imposta il valore della proprietà dataRif.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRif(XMLGregorianCalendar value) {
        this.dataRif = value;
    }

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
     * Recupera il valore della proprietà childValidation.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isChildValidation() {
        return childValidation;
    }

    /**
     * Imposta il valore della proprietà childValidation.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setChildValidation(Boolean value) {
        this.childValidation = value;
    }

    /**
     * Recupera il valore della proprietà signatureVerify.
     * 
     * @return
     *     possible object is
     *     {@link InputSigVerifyType.SignatureVerify }
     *     
     */
    public InputSigVerifyType.SignatureVerify getSignatureVerify() {
        return signatureVerify;
    }

    /**
     * Imposta il valore della proprietà signatureVerify.
     * 
     * @param value
     *     allowed object is
     *     {@link InputSigVerifyType.SignatureVerify }
     *     
     */
    public void setSignatureVerify(InputSigVerifyType.SignatureVerify value) {
        this.signatureVerify = value;
    }

    /**
     * Recupera il valore della proprietà timestampVerifiy.
     * 
     * @return
     *     possible object is
     *     {@link InputSigVerifyType.TimestampVerifiy }
     *     
     */
    public InputSigVerifyType.TimestampVerifiy getTimestampVerifiy() {
        return timestampVerifiy;
    }

    /**
     * Imposta il valore della proprietà timestampVerifiy.
     * 
     * @param value
     *     allowed object is
     *     {@link InputSigVerifyType.TimestampVerifiy }
     *     
     */
    public void setTimestampVerifiy(InputSigVerifyType.TimestampVerifiy value) {
        this.timestampVerifiy = value;
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
     *         &lt;element name="CRLCheck" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="CAReliability" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="detectCode" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        "crlCheck",
        "caReliability",
        "detectCode"
    })
    public static class SignatureVerify {

        @XmlElement(name = "CRLCheck", defaultValue = "true")
        protected boolean crlCheck;
        @XmlElement(name = "CAReliability", defaultValue = "true")
        protected boolean caReliability;
        protected boolean detectCode;

        /**
         * Recupera il valore della proprietà crlCheck.
         * 
         */
        public boolean isCRLCheck() {
            return crlCheck;
        }

        /**
         * Imposta il valore della proprietà crlCheck.
         * 
         */
        public void setCRLCheck(boolean value) {
            this.crlCheck = value;
        }

        /**
         * Recupera il valore della proprietà caReliability.
         * 
         */
        public boolean isCAReliability() {
            return caReliability;
        }

        /**
         * Imposta il valore della proprietà caReliability.
         * 
         */
        public void setCAReliability(boolean value) {
            this.caReliability = value;
        }

        /**
         * Recupera il valore della proprietà detectCode.
         * 
         */
        public boolean isDetectCode() {
            return detectCode;
        }

        /**
         * Imposta il valore della proprietà detectCode.
         * 
         */
        public void setDetectCode(boolean value) {
            this.detectCode = value;
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
     *         &lt;element name="TSAReliability" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        "tsaReliability"
    })
    public static class TimestampVerifiy {

        @XmlElement(name = "TSAReliability")
        protected boolean tsaReliability;

        /**
         * Recupera il valore della proprietà tsaReliability.
         * 
         */
        public boolean isTSAReliability() {
            return tsaReliability;
        }

        /**
         * Imposta il valore della proprietà tsaReliability.
         * 
         */
        public void setTSAReliability(boolean value) {
            this.tsaReliability = value;
        }

    }

}
