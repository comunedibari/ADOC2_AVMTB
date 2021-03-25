/**
 * SalvaAllegatoSalvaAllegatoIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server;

public class SalvaAllegatoSalvaAllegatoIn  implements java.io.Serializable {
    private long idAtto;

    private boolean isMainDocument;

    private java.lang.String fileName;

    private java.lang.String mimeType;

    private byte[] fileContent;

    public SalvaAllegatoSalvaAllegatoIn() {
    }

    public SalvaAllegatoSalvaAllegatoIn(
           long idAtto,
           boolean isMainDocument,
           java.lang.String fileName,
           java.lang.String mimeType,
           byte[] fileContent) {
           this.idAtto = idAtto;
           this.isMainDocument = isMainDocument;
           this.fileName = fileName;
           this.mimeType = mimeType;
           this.fileContent = fileContent;
    }


    /**
     * Gets the idAtto value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @return idAtto
     */
    public long getIdAtto() {
        return idAtto;
    }


    /**
     * Sets the idAtto value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @param idAtto
     */
    public void setIdAtto(long idAtto) {
        this.idAtto = idAtto;
    }


    /**
     * Gets the isMainDocument value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @return isMainDocument
     */
    public boolean isIsMainDocument() {
        return isMainDocument;
    }


    /**
     * Sets the isMainDocument value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @param isMainDocument
     */
    public void setIsMainDocument(boolean isMainDocument) {
        this.isMainDocument = isMainDocument;
    }


    /**
     * Gets the fileName value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @return fileName
     */
    public java.lang.String getFileName() {
        return fileName;
    }


    /**
     * Sets the fileName value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @param fileName
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }


    /**
     * Gets the mimeType value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @return mimeType
     */
    public java.lang.String getMimeType() {
        return mimeType;
    }


    /**
     * Sets the mimeType value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @param mimeType
     */
    public void setMimeType(java.lang.String mimeType) {
        this.mimeType = mimeType;
    }


    /**
     * Gets the fileContent value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @return fileContent
     */
    public byte[] getFileContent() {
        return fileContent;
    }


    /**
     * Sets the fileContent value for this SalvaAllegatoSalvaAllegatoIn.
     * 
     * @param fileContent
     */
    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SalvaAllegatoSalvaAllegatoIn)) return false;
        SalvaAllegatoSalvaAllegatoIn other = (SalvaAllegatoSalvaAllegatoIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.idAtto == other.getIdAtto() &&
            this.isMainDocument == other.isIsMainDocument() &&
            ((this.fileName==null && other.getFileName()==null) || 
             (this.fileName!=null &&
              this.fileName.equals(other.getFileName()))) &&
            ((this.mimeType==null && other.getMimeType()==null) || 
             (this.mimeType!=null &&
              this.mimeType.equals(other.getMimeType()))) &&
            ((this.fileContent==null && other.getFileContent()==null) || 
             (this.fileContent!=null &&
              java.util.Arrays.equals(this.fileContent, other.getFileContent())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getIdAtto()).hashCode();
        _hashCode += (isIsMainDocument() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getFileName() != null) {
            _hashCode += getFileName().hashCode();
        }
        if (getMimeType() != null) {
            _hashCode += getMimeType().hashCode();
        }
        if (getFileContent() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFileContent());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFileContent(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SalvaAllegatoSalvaAllegatoIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">salvaAllegato>salvaAllegatoIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAtto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAtto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isMainDocument");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isMainDocument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mimeType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mimeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fileContent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fileContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
