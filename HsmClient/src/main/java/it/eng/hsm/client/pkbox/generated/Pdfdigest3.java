
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
 *         &lt;element name="cx" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cy" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "cx",
    "cy",
    "algorithm",
    "encoding"
})
@XmlRootElement(name = "pdfdigest3")
public class Pdfdigest3 {

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
    protected int cx;
    protected int cy;
    protected int algorithm;
    protected int encoding;

    /**
     * Recupera il valore della proprietÓ environment.
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
     * Imposta il valore della proprietÓ environment.
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
     * Recupera il valore della proprietÓ document.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocument() {
        return document;
    }

    /**
     * Imposta il valore della proprietÓ document.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocument(byte[] value) {
        this.document = value;
    }

    /**
     * Recupera il valore della proprietÓ accessPermissions.
     * 
     */
    public int getAccessPermissions() {
        return accessPermissions;
    }

    /**
     * Imposta il valore della proprietÓ accessPermissions.
     * 
     */
    public void setAccessPermissions(int value) {
        this.accessPermissions = value;
    }

    /**
     * Recupera il valore della proprietÓ signerName.
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
     * Imposta il valore della proprietÓ signerName.
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
     * Recupera il valore della proprietÓ fieldName.
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
     * Imposta il valore della proprietÓ fieldName.
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
     * Recupera il valore della proprietÓ sigLayout.
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
     * Imposta il valore della proprietÓ sigLayout.
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
     * Recupera il valore della proprietÓ reason.
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
     * Imposta il valore della proprietÓ reason.
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
     * Recupera il valore della proprietÓ location.
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
     * Imposta il valore della proprietÓ location.
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
     * Recupera il valore della proprietÓ contact.
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
     * Imposta il valore della proprietÓ contact.
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
     * Recupera il valore della proprietÓ date.
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
     * Imposta il valore della proprietÓ date.
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
     * Recupera il valore della proprietÓ image.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Imposta il valore della proprietÓ image.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImage(byte[] value) {
        this.image = value;
    }

    /**
     * Recupera il valore della proprietÓ page.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Imposta il valore della proprietÓ page.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Recupera il valore della proprietÓ position.
     * 
     */
    public int getPosition() {
        return position;
    }

    /**
     * Imposta il valore della proprietÓ position.
     * 
     */
    public void setPosition(int value) {
        this.position = value;
    }

    /**
     * Recupera il valore della proprietÓ x.
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * Imposta il valore della proprietÓ x.
     * 
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * Recupera il valore della proprietÓ y.
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * Imposta il valore della proprietÓ y.
     * 
     */
    public void setY(int value) {
        this.y = value;
    }

    /**
     * Recupera il valore della proprietÓ cx.
     * 
     */
    public int getCx() {
        return cx;
    }

    /**
     * Imposta il valore della proprietÓ cx.
     * 
     */
    public void setCx(int value) {
        this.cx = value;
    }

    /**
     * Recupera il valore della proprietÓ cy.
     * 
     */
    public int getCy() {
        return cy;
    }

    /**
     * Imposta il valore della proprietÓ cy.
     * 
     */
    public void setCy(int value) {
        this.cy = value;
    }

    /**
     * Recupera il valore della proprietÓ algorithm.
     * 
     */
    public int getAlgorithm() {
        return algorithm;
    }

    /**
     * Imposta il valore della proprietÓ algorithm.
     * 
     */
    public void setAlgorithm(int value) {
        this.algorithm = value;
    }

    /**
     * Recupera il valore della proprietÓ encoding.
     * 
     */
    public int getEncoding() {
        return encoding;
    }

    /**
     * Imposta il valore della proprietÓ encoding.
     * 
     */
    public void setEncoding(int value) {
        this.encoding = value;
    }

}
