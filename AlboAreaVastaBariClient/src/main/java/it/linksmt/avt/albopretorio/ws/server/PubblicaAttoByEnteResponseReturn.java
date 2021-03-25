/**
 * PubblicaAttoByEnteResponseReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server;

public class PubblicaAttoByEnteResponseReturn  implements java.io.Serializable {
    private it.linksmt.avt.albopretorio.RichiestaSOAP.Atto atto;

    private it.linksmt.avt.albopretorio.RichiestaSOAP.GestioneAttoError error;

    public PubblicaAttoByEnteResponseReturn() {
    }

    public PubblicaAttoByEnteResponseReturn(
           it.linksmt.avt.albopretorio.RichiestaSOAP.Atto atto,
           it.linksmt.avt.albopretorio.RichiestaSOAP.GestioneAttoError error) {
           this.atto = atto;
           this.error = error;
    }


    /**
     * Gets the atto value for this PubblicaAttoByEnteResponseReturn.
     * 
     * @return atto
     */
    public it.linksmt.avt.albopretorio.RichiestaSOAP.Atto getAtto() {
        return atto;
    }


    /**
     * Sets the atto value for this PubblicaAttoByEnteResponseReturn.
     * 
     * @param atto
     */
    public void setAtto(it.linksmt.avt.albopretorio.RichiestaSOAP.Atto atto) {
        this.atto = atto;
    }


    /**
     * Gets the error value for this PubblicaAttoByEnteResponseReturn.
     * 
     * @return error
     */
    public it.linksmt.avt.albopretorio.RichiestaSOAP.GestioneAttoError getError() {
        return error;
    }


    /**
     * Sets the error value for this PubblicaAttoByEnteResponseReturn.
     * 
     * @param error
     */
    public void setError(it.linksmt.avt.albopretorio.RichiestaSOAP.GestioneAttoError error) {
        this.error = error;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PubblicaAttoByEnteResponseReturn)) return false;
        PubblicaAttoByEnteResponseReturn other = (PubblicaAttoByEnteResponseReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.atto==null && other.getAtto()==null) || 
             (this.atto!=null &&
              this.atto.equals(other.getAtto()))) &&
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError())));
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
        if (getAtto() != null) {
            _hashCode += getAtto().hashCode();
        }
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PubblicaAttoByEnteResponseReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">pubblicaAttoByEnteResponse>return"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "atto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "Atto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "GestioneAttoError"));
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
