
package it.eng.fileOperation.clientws;

import java.util.ArrayList;
import java.util.List;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="genericError" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="fileoperationResponse" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="fileOperationResults">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="fileOperationResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="fileResult" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "genericError",
    "fileoperationResponse"
})
@XmlRootElement(name = "response")
public class Response {

    protected Response.GenericError genericError;
    protected Response.FileoperationResponse fileoperationResponse;

    /**
     * Recupera il valore della proprietÓ genericError.
     * 
     * @return
     *     possible object is
     *     {@link Response.GenericError }
     *     
     */
    public Response.GenericError getGenericError() {
        return genericError;
    }

    /**
     * Imposta il valore della proprietÓ genericError.
     * 
     * @param value
     *     allowed object is
     *     {@link Response.GenericError }
     *     
     */
    public void setGenericError(Response.GenericError value) {
        this.genericError = value;
    }

    /**
     * Recupera il valore della proprietÓ fileoperationResponse.
     * 
     * @return
     *     possible object is
     *     {@link Response.FileoperationResponse }
     *     
     */
    public Response.FileoperationResponse getFileoperationResponse() {
        return fileoperationResponse;
    }

    /**
     * Imposta il valore della proprietÓ fileoperationResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link Response.FileoperationResponse }
     *     
     */
    public void setFileoperationResponse(Response.FileoperationResponse value) {
        this.fileoperationResponse = value;
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
     *         &lt;element name="fileOperationResults">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="fileOperationResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="fileResult" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
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
        "fileOperationResults",
        "fileResult"
    })
    public static class FileoperationResponse {

        @XmlElement(required = true)
        protected Response.FileoperationResponse.FileOperationResults fileOperationResults;
        @XmlMimeType("application/octet-stream")
        protected DataHandler fileResult;

        /**
         * Recupera il valore della proprietÓ fileOperationResults.
         * 
         * @return
         *     possible object is
         *     {@link Response.FileoperationResponse.FileOperationResults }
         *     
         */
        public Response.FileoperationResponse.FileOperationResults getFileOperationResults() {
            return fileOperationResults;
        }

        /**
         * Imposta il valore della proprietÓ fileOperationResults.
         * 
         * @param value
         *     allowed object is
         *     {@link Response.FileoperationResponse.FileOperationResults }
         *     
         */
        public void setFileOperationResults(Response.FileoperationResponse.FileOperationResults value) {
            this.fileOperationResults = value;
        }

        /**
         * Recupera il valore della proprietÓ fileResult.
         * 
         * @return
         *     possible object is
         *     {@link DataHandler }
         *     
         */
        public DataHandler getFileResult() {
            return fileResult;
        }

        /**
         * Imposta il valore della proprietÓ fileResult.
         * 
         * @param value
         *     allowed object is
         *     {@link DataHandler }
         *     
         */
        public void setFileResult(DataHandler value) {
            this.fileResult = value;
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
         *         &lt;element name="fileOperationResult" type="{it.eng.fileoperation.ws.base}AbstractResponseOperationType" maxOccurs="unbounded"/>
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
            "fileOperationResult"
        })
        public static class FileOperationResults {

            @XmlElement(required = true)
            protected List<AbstractResponseOperationType> fileOperationResult;

            /**
             * Gets the value of the fileOperationResult property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the fileOperationResult property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getFileOperationResult().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AbstractResponseOperationType }
             * 
             * 
             */
            public List<AbstractResponseOperationType> getFileOperationResult() {
                if (fileOperationResult == null) {
                    fileOperationResult = new ArrayList<AbstractResponseOperationType>();
                }
                return this.fileOperationResult;
            }

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
     *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "errorMessage"
    })
    public static class GenericError {

        @XmlElement(required = true)
        protected List<String> errorMessage;

        /**
         * Gets the value of the errorMessage property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the errorMessage property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getErrorMessage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getErrorMessage() {
            if (errorMessage == null) {
                errorMessage = new ArrayList<String>();
            }
            return this.errorMessage;
        }

    }

}
