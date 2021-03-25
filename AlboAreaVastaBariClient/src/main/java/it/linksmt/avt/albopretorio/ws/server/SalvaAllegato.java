/**
 * SalvaAllegato.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server;

public class SalvaAllegato  implements java.io.Serializable {
    private it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoSalvaAllegatoIn salvaAllegatoIn;

    public SalvaAllegato() {
    }

    public SalvaAllegato(
           it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoSalvaAllegatoIn salvaAllegatoIn) {
           this.salvaAllegatoIn = salvaAllegatoIn;
    }


    /**
     * Gets the salvaAllegatoIn value for this SalvaAllegato.
     * 
     * @return salvaAllegatoIn
     */
    public it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoSalvaAllegatoIn getSalvaAllegatoIn() {
        return salvaAllegatoIn;
    }


    /**
     * Sets the salvaAllegatoIn value for this SalvaAllegato.
     * 
     * @param salvaAllegatoIn
     */
    public void setSalvaAllegatoIn(it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoSalvaAllegatoIn salvaAllegatoIn) {
        this.salvaAllegatoIn = salvaAllegatoIn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SalvaAllegato)) return false;
        SalvaAllegato other = (SalvaAllegato) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.salvaAllegatoIn==null && other.getSalvaAllegatoIn()==null) || 
             (this.salvaAllegatoIn!=null &&
              this.salvaAllegatoIn.equals(other.getSalvaAllegatoIn())));
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
        if (getSalvaAllegatoIn() != null) {
            _hashCode += getSalvaAllegatoIn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SalvaAllegato.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "salvaAllegato"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salvaAllegatoIn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "salvaAllegatoIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">salvaAllegato>salvaAllegatoIn"));
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
