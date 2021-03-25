
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per signerInformationType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="signerInformationType">
 *   &lt;complexContent>
 *     &lt;extension base="{it.eng.fileoperation.ws}SigVerifyResultType">
 *       &lt;sequence>
 *         &lt;element name="signingTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="certificato">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *                   &lt;element name="dataDecorrenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="subject" type="{it.eng.fileoperation.ws}dnType" minOccurs="0"/>
 *                   &lt;element name="issuer" type="{it.eng.fileoperation.ws}dnType" minOccurs="0"/>
 *                   &lt;element name="qcStatements" type="{it.eng.fileoperation.ws}qcStatements" minOccurs="0"/>
 *                   &lt;element name="keyUsages" type="{it.eng.fileoperation.ws}keyUsages" minOccurs="0"/>
 *                   &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="marca" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType">
 *                 &lt;sequence>
 *                   &lt;element name="contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *                   &lt;element name="tsaName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="policy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="controFirma" type="{it.eng.fileoperation.ws}signerInformationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "signerInformationType", propOrder = {
    "signingTime",
    "certificato",
    "marca",
    "controFirma"
})
public class SignerInformationType
    extends SigVerifyResultType
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar signingTime;
    @XmlElement(required = true)
    protected SignerInformationType.Certificato certificato;
    protected SignerInformationType.Marca marca;
    protected SignerInformationType controFirma;

    /**
     * Recupera il valore della proprietà signingTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSigningTime() {
        return signingTime;
    }

    /**
     * Imposta il valore della proprietà signingTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSigningTime(XMLGregorianCalendar value) {
        this.signingTime = value;
    }

    /**
     * Recupera il valore della proprietà certificato.
     * 
     * @return
     *     possible object is
     *     {@link SignerInformationType.Certificato }
     *     
     */
    public SignerInformationType.Certificato getCertificato() {
        return certificato;
    }

    /**
     * Imposta il valore della proprietà certificato.
     * 
     * @param value
     *     allowed object is
     *     {@link SignerInformationType.Certificato }
     *     
     */
    public void setCertificato(SignerInformationType.Certificato value) {
        this.certificato = value;
    }

    /**
     * Recupera il valore della proprietà marca.
     * 
     * @return
     *     possible object is
     *     {@link SignerInformationType.Marca }
     *     
     */
    public SignerInformationType.Marca getMarca() {
        return marca;
    }

    /**
     * Imposta il valore della proprietà marca.
     * 
     * @param value
     *     allowed object is
     *     {@link SignerInformationType.Marca }
     *     
     */
    public void setMarca(SignerInformationType.Marca value) {
        this.marca = value;
    }

    /**
     * Recupera il valore della proprietà controFirma.
     * 
     * @return
     *     possible object is
     *     {@link SignerInformationType }
     *     
     */
    public SignerInformationType getControFirma() {
        return controFirma;
    }

    /**
     * Imposta il valore della proprietà controFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link SignerInformationType }
     *     
     */
    public void setControFirma(SignerInformationType value) {
        this.controFirma = value;
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
     *         &lt;element name="contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
     *         &lt;element name="dataDecorrenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="subject" type="{it.eng.fileoperation.ws}dnType" minOccurs="0"/>
     *         &lt;element name="issuer" type="{it.eng.fileoperation.ws}dnType" minOccurs="0"/>
     *         &lt;element name="qcStatements" type="{it.eng.fileoperation.ws}qcStatements" minOccurs="0"/>
     *         &lt;element name="keyUsages" type="{it.eng.fileoperation.ws}keyUsages" minOccurs="0"/>
     *         &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "contenuto",
        "dataDecorrenza",
        "dataScadenza",
        "subject",
        "issuer",
        "qcStatements",
        "keyUsages",
        "serialNumber"
    })
    public static class Certificato {

        @XmlElement(required = true)
        protected byte[] contenuto;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataDecorrenza;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataScadenza;
        protected DnType subject;
        protected DnType issuer;
        protected QcStatements qcStatements;
        protected KeyUsages keyUsages;
        protected String serialNumber;

        /**
         * Recupera il valore della proprietà contenuto.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getContenuto() {
            return contenuto;
        }

        /**
         * Imposta il valore della proprietà contenuto.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setContenuto(byte[] value) {
            this.contenuto = value;
        }

        /**
         * Recupera il valore della proprietà dataDecorrenza.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataDecorrenza() {
            return dataDecorrenza;
        }

        /**
         * Imposta il valore della proprietà dataDecorrenza.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataDecorrenza(XMLGregorianCalendar value) {
            this.dataDecorrenza = value;
        }

        /**
         * Recupera il valore della proprietà dataScadenza.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataScadenza() {
            return dataScadenza;
        }

        /**
         * Imposta il valore della proprietà dataScadenza.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataScadenza(XMLGregorianCalendar value) {
            this.dataScadenza = value;
        }

        /**
         * Recupera il valore della proprietà subject.
         * 
         * @return
         *     possible object is
         *     {@link DnType }
         *     
         */
        public DnType getSubject() {
            return subject;
        }

        /**
         * Imposta il valore della proprietà subject.
         * 
         * @param value
         *     allowed object is
         *     {@link DnType }
         *     
         */
        public void setSubject(DnType value) {
            this.subject = value;
        }

        /**
         * Recupera il valore della proprietà issuer.
         * 
         * @return
         *     possible object is
         *     {@link DnType }
         *     
         */
        public DnType getIssuer() {
            return issuer;
        }

        /**
         * Imposta il valore della proprietà issuer.
         * 
         * @param value
         *     allowed object is
         *     {@link DnType }
         *     
         */
        public void setIssuer(DnType value) {
            this.issuer = value;
        }

        /**
         * Recupera il valore della proprietà qcStatements.
         * 
         * @return
         *     possible object is
         *     {@link QcStatements }
         *     
         */
        public QcStatements getQcStatements() {
            return qcStatements;
        }

        /**
         * Imposta il valore della proprietà qcStatements.
         * 
         * @param value
         *     allowed object is
         *     {@link QcStatements }
         *     
         */
        public void setQcStatements(QcStatements value) {
            this.qcStatements = value;
        }

        /**
         * Recupera il valore della proprietà keyUsages.
         * 
         * @return
         *     possible object is
         *     {@link KeyUsages }
         *     
         */
        public KeyUsages getKeyUsages() {
            return keyUsages;
        }

        /**
         * Imposta il valore della proprietà keyUsages.
         * 
         * @param value
         *     allowed object is
         *     {@link KeyUsages }
         *     
         */
        public void setKeyUsages(KeyUsages value) {
            this.keyUsages = value;
        }

        /**
         * Recupera il valore della proprietà serialNumber.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSerialNumber() {
            return serialNumber;
        }

        /**
         * Imposta il valore della proprietà serialNumber.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSerialNumber(String value) {
            this.serialNumber = value;
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
     *     &lt;extension base="{it.eng.fileoperation.ws.base}AbstractResponseOperationType">
     *       &lt;sequence>
     *         &lt;element name="contenuto" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
     *         &lt;element name="tsaName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="policy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "contenuto",
        "tsaName",
        "serialNumber",
        "date",
        "policy"
    })
    public static class Marca
        extends AbstractResponseOperationType
    {

        @XmlElement(required = true)
        protected byte[] contenuto;
        protected String tsaName;
        protected String serialNumber;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar date;
        protected String policy;

        /**
         * Recupera il valore della proprietà contenuto.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getContenuto() {
            return contenuto;
        }

        /**
         * Imposta il valore della proprietà contenuto.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setContenuto(byte[] value) {
            this.contenuto = value;
        }

        /**
         * Recupera il valore della proprietà tsaName.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTsaName() {
            return tsaName;
        }

        /**
         * Imposta il valore della proprietà tsaName.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTsaName(String value) {
            this.tsaName = value;
        }

        /**
         * Recupera il valore della proprietà serialNumber.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSerialNumber() {
            return serialNumber;
        }

        /**
         * Imposta il valore della proprietà serialNumber.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSerialNumber(String value) {
            this.serialNumber = value;
        }

        /**
         * Recupera il valore della proprietà date.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDate() {
            return date;
        }

        /**
         * Imposta il valore della proprietà date.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDate(XMLGregorianCalendar value) {
            this.date = value;
        }

        /**
         * Recupera il valore della proprietà policy.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPolicy() {
            return policy;
        }

        /**
         * Imposta il valore della proprietà policy.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPolicy(String value) {
            this.policy = value;
        }

    }

}
