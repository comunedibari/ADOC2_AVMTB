/**
 * ElencoAtti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server;

public class ElencoAtti  implements java.io.Serializable {
    private it.linksmt.avt.albopretorio.ws.server.ElencoAttiElencoAttiIn elencoAttiIn;

    public ElencoAtti() {
    }

    public ElencoAtti(
           it.linksmt.avt.albopretorio.ws.server.ElencoAttiElencoAttiIn elencoAttiIn) {
           this.elencoAttiIn = elencoAttiIn;
    }


    /**
     * Gets the elencoAttiIn value for this ElencoAtti.
     * 
     * @return elencoAttiIn
     */
    public it.linksmt.avt.albopretorio.ws.server.ElencoAttiElencoAttiIn getElencoAttiIn() {
        return elencoAttiIn;
    }


    /**
     * Sets the elencoAttiIn value for this ElencoAtti.
     * 
     * @param elencoAttiIn
     */
    public void setElencoAttiIn(it.linksmt.avt.albopretorio.ws.server.ElencoAttiElencoAttiIn elencoAttiIn) {
        this.elencoAttiIn = elencoAttiIn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ElencoAtti)) return false;
        ElencoAtti other = (ElencoAtti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.elencoAttiIn==null && other.getElencoAttiIn()==null) || 
             (this.elencoAttiIn!=null &&
              this.elencoAttiIn.equals(other.getElencoAttiIn())));
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
        if (getElencoAttiIn() != null) {
            _hashCode += getElencoAttiIn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ElencoAtti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "elencoAtti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoAttiIn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elencoAttiIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">elencoAtti>elencoAttiIn"));
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
