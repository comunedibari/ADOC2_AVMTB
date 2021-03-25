
package it.eng.hsm.client.pkbox.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="environment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="document" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="accessPermissions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="signerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fieldName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sigLayout" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contact" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="x" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="y" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="algorithm" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="encoding" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "environment",
    "document",
    "accessPermissions",
    "signerName",
    "fieldName",
    "sigLayout",
    "reason",
    "location",
    "contact",
    "date",
    "image",
    "page",
    "position",
    "x",
    "y",
    "algorithm",
    "encoding"
})
@XmlRootElement(name = "pdfdigest2")
public class Pdfdigest2 {

    @XmlElement(required = true, nillable = true)
    protected String environment;
    @XmlElement(required = true, nillable = true)
    protected byte[] document;
    protected int accessPermissions;
    @XmlElement(required = true, nillable = true)
    protected String signerName;
    @XmlElement(required = true, nillable = true)
    protected String fieldName;
    @XmlElement(required = true, nillable = true)
    protected String sigLayout;
    @XmlElement(required = true, nillable = true)
    protected String reason;
    @XmlElement(required = true, nillable = true)
    protected String location;
    @XmlElement(required = true, nillable = true)
    protected String contact;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(required = true, nillable = true)
    protected byte[] image;
    protected int page;
    protected int position;
    protected int x;
    protected int y;
    protected int algorithm;
    protected int encoding;

    /**
     * Recupera il valore della propriet� environment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Imposta il valore della propriet� environment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnvironment(String value) {
        this.environment = value;
    }

    /**
     * Recupera il valore della propriet� document.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocument() {
        return document;
    }

    /**
     * Imposta il valore della propriet� document.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocument(byte[] value) {
        this.document = value;
    }

    /**
     * Recupera il valore della propriet� accessPermissions.
     * 
     */
    public int getAccessPermissions() {
        return accessPermissions;
    }

    /**
     * Imposta il valore della propriet� accessPermissions.
     * 
     */
    public void setAccessPermissions(int value) {
        this.accessPermissions = value;
    }

    /**
     * Recupera il valore della propriet� signerName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignerName() {
        return signerName;
    }

    /**
     * Imposta il valore della propriet� signerName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignerName(String value) {
        this.signerName = value;
    }

    /**
     * Recupera il valore della propriet� fieldName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Imposta il valore della propriet� fieldName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Recupera il valore della propriet� sigLayout.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigLayout() {
        return sigLayout;
    }

    /**
     * Imposta il valore della propriet� sigLayout.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigLayout(String value) {
        this.sigLayout = value;
    }

    /**
     * Recupera il valore della propriet� reason.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Imposta il valore della propriet� reason.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Recupera il valore della propriet� location.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Imposta il valore della propriet� location.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Recupera il valore della propriet� contact.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * Imposta il valore della propriet� contact.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContact(String value) {
        this.contact = value;
    }

    /**
     * Recupera il valore della propriet� date.
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
     * Imposta il valore della propriet� date.
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
     * Recupera il valore della propriet� image.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Imposta il valore della propriet� image.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImage(byte[] value) {
        this.image = value;
    }

    /**
     * Recupera il valore della propriet� page.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Imposta il valore della propriet� page.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Recupera il valore della propriet� position.
     * 
     */
    public int getPosition() {
        return position;
    }

    /**
     * Imposta il valore della propriet� position.
     * 
     */
    public void setPosition(int value) {
        this.position = value;
    }

    /**
     * Recupera il valore della propriet� x.
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * Imposta il valore della propriet� x.
     * 
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * Recupera il valore della propriet� y.
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * Imposta il valore della propriet� y.
     * 
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * Recupera il valore della propriet� algorithm.
     * 
     */
    public int getAlgorithm() {
        return algorithm;
    }

    /**
     * Imposta il valore della propriet� algorithm.
     * 
     */
    public void setAlgorithm(int value) {
        this.algorithm = value;
    }

    /**
     * Recupera il valore della propriet� encoding.
     * 
     */
    public int getEncoding() {
        return encoding;
    }

    /**
     * Imposta il valore della propriet� encoding.
     * 
     */
    public void setEncoding(int value) {
        this.encoding = value;
    }

}
