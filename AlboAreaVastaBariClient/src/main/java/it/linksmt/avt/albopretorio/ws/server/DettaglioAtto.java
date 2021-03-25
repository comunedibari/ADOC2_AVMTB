/**
 * DettaglioAtto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server;

public class DettaglioAtto  implements java.io.Serializable {
    private it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoIn dettaglioAttoIn;

    public DettaglioAtto() {
    }

    public DettaglioAtto(
           it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoIn dettaglioAttoIn) {
           this.dettaglioAttoIn = dettaglioAttoIn;
    }


    /**
     * Gets the dettaglioAttoIn value for this DettaglioAtto.
     * 
     * @return dettaglioAttoIn
     */
    public it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoIn getDettaglioAttoIn() {
        return dettaglioAttoIn;
    }


    /**
     * Sets the dettaglioAttoIn value for this DettaglioAtto.
     * 
     * @param dettaglioAttoIn
     */
    public void setDettaglioAttoIn(it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoIn dettaglioAttoIn) {
        this.dettaglioAttoIn = dettaglioAttoIn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DettaglioAtto)) return false;
        DettaglioAtto other = (DettaglioAtto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dettaglioAttoIn==null && other.getDettaglioAttoIn()==null) || 
             (this.dettaglioAttoIn!=null &&
              this.dettaglioAttoIn.equals(other.getDettaglioAttoIn())));
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
        if (getDettaglioAttoIn() != null) {
            _hashCode += getDettaglioAttoIn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DettaglioAtto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", "dettaglioAtto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dettaglioAttoIn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dettaglioAttoIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "DettaglioAttoIn"));
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
