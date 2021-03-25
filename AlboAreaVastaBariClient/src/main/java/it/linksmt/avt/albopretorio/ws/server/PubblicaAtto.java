/**
 * PubblicaAtto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server;

public class PubblicaAtto  implements java.io.Serializable {
    private it.linksmt.avt.albopretorio.ws.server.PubblicaAttoPubblicaAttoIn pubblicaAttoIn;

    public PubblicaAtto() {
    }

    public PubblicaAtto(
           it.linksmt.avt.albopretorio.ws.server.PubblicaAttoPubblicaAttoIn pubblicaAttoIn) {
           this.pubblicaAttoIn = pubblicaAttoIn;
    }


    /**
     * Gets the pubblicaAttoIn value for this PubblicaAtto.
     * 
     * @return pubblicaAttoIn
     */
    public it.linksmt.avt.albopretorio.ws.server.PubblicaAttoPubblicaAttoIn getPubblicaAttoIn() {
        return pubblicaAttoIn;
    }


    /**
     * Sets the pubblicaAttoIn value for this PubblicaAtto.
     * 
     * @param pubblicaAttoIn
     */
    public void setPubblicaAttoIn(it.linksmt.avt.albopretorio.ws.server.PubblicaAttoPubblicaAttoIn pubblicaAttoIn) {
        this.pubblicaAttoIn = pubblicaAttoIn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PubblicaAtto)) return false;
        PubblicaAtto other = (PubblicaAtto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pubblicaAttoIn==null && other.getPubblicaAttoIn()==null) || 
             (this.pubblicaAttoIn!=null &&
              this.pubblicaAttoIn.equals(other.getPubblicaAttoIn())));
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
        if (getPubblicaAttoIn() != null) {
            _hashCode += getPubblicaAttoIn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PubblicaAtto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "pubblicaAtto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pubblicaAttoIn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pubblicaAttoIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAtto>pubblicaAttoIn"));
        elemField.setMinOccurs(0);
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
