
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AbstractInputOperationType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AbstractInputOperationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="breakOnError" type="{http://www.w3.org/2001/XMLSchema}boolean" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractInputOperationType", namespace = "it.eng.fileoperation.ws.base", propOrder = {
    "breakOnError"
})
@XmlSeeAlso({
    InputUnpackType.class,
    InputConversionType.class,
    InputFormatRecognitionType.class,
    InputSigVerifyType.class,
    InputUnpackMultipartType.class,
    InputCodeDetectorType.class,
    InputDigestType.class,
    InputFileCompressType.class,
    InputTimbroType.class
})
public class AbstractInputOperationType {

    @XmlElement(defaultValue = "false")
    protected boolean breakOnError;

    /**
     * Recupera il valore della proprietà breakOnError.
     * 
     */
    public boolean isBreakOnError() {
        return breakOnError;
    }

    /**
     * Imposta il valore della proprietà breakOnError.
     * 
     */
    public void setBreakOnError(boolean value) {
        this.breakOnError = value;
    }

}
