/**
 * DettaglioAttoIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.RichiestaSOAP;

public class DettaglioAttoIn  implements java.io.Serializable {
    private long idAtto;

    private long idEnte;

    public DettaglioAttoIn() {
    }

    public DettaglioAttoIn(
           long idAtto,
           long idEnte) {
           this.idAtto = idAtto;
           this.idEnte = idEnte;
    }


    /**
     * Gets the idAtto value for this DettaglioAttoIn.
     * 
     * @return idAtto
     */
    public long getIdAtto() {
        return idAtto;
    }


    /**
     * Sets the idAtto value for this DettaglioAttoIn.
     * 
     * @param idAtto
     */
    public void setIdAtto(long idAtto) {
        this.idAtto = idAtto;
    }


    /**
     * Gets the idEnte value for this DettaglioAttoIn.
     * 
     * @return idEnte
     */
    public long getIdEnte() {
        return idEnte;
    }


    /**
     * Sets the idEnte value for this DettaglioAttoIn.
     * 
     * @param idEnte
     */
    public void setIdEnte(long idEnte) {
        this.idEnte = idEnte;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DettaglioAttoIn)) return false;
        DettaglioAttoIn other = (DettaglioAttoIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.idAtto == other.getIdAtto() &&
            this.idEnte == other.getIdEnte();
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
        _hashCode += new Long(getIdEnte()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DettaglioAttoIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "DettaglioAttoIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAtto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAtto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
