/**
 * ElencoTipiAttoResponseReturn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.ws.server;

public class ElencoTipiAttoResponseReturn  implements java.io.Serializable {
    private it.linksmt.avt.albopretorio.RichiestaSOAP.TipoAtto[] elencoTipiAtto;

    private it.linksmt.avt.albopretorio.RichiestaSOAP.GestioneAttoError error;

    public ElencoTipiAttoResponseReturn() {
    }

    public ElencoTipiAttoResponseReturn(
           it.linksmt.avt.albopretorio.RichiestaSOAP.TipoAtto[] elencoTipiAtto,
           it.linksmt.avt.albopretorio.RichiestaSOAP.GestioneAttoError error) {
           this.elencoTipiAtto = elencoTipiAtto;
           this.error = error;
    }


    /**
     * Gets the elencoTipiAtto value for this ElencoTipiAttoResponseReturn.
     * 
     * @return elencoTipiAtto
     */
    public it.linksmt.avt.albopretorio.RichiestaSOAP.TipoAtto[] getElencoTipiAtto() {
        return elencoTipiAtto;
    }


    /**
     * Sets the elencoTipiAtto value for this ElencoTipiAttoResponseReturn.
     * 
     * @param elencoTipiAtto
     */
    public void setElencoTipiAtto(it.linksmt.avt.albopretorio.RichiestaSOAP.TipoAtto[] elencoTipiAtto) {
        this.elencoTipiAtto = elencoTipiAtto;
    }

    public it.linksmt.avt.albopretorio.RichiestaSOAP.TipoAtto getElencoTipiAtto(int i) {
        return this.elencoTipiAtto[i];
    }

    public void setElencoTipiAtto(int i, it.linksmt.avt.albopretorio.RichiestaSOAP.TipoAtto _value) {
        this.elencoTipiAtto[i] = _value;
    }


    /**
     * Gets the error value for this ElencoTipiAttoResponseReturn.
     * 
     * @return error
     */
    public it.linksmt.avt.albopretorio.RichiestaSOAP.GestioneAttoError getError() {
        return error;
    }


    /**
     * Sets the error value for this ElencoTipiAttoResponseReturn.
     * 
     * @param error
     */
    public void setError(it.linksmt.avt.albopretorio.RichiestaSOAP.GestioneAttoError error) {
        this.error = error;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ElencoTipiAttoResponseReturn)) return false;
        ElencoTipiAttoResponseReturn other = (ElencoTipiAttoResponseReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.elencoTipiAtto==null && other.getElencoTipiAtto()==null) || 
             (this.elencoTipiAtto!=null &&
              java.util.Arrays.equals(this.elencoTipiAtto, other.getElencoTipiAtto()))) &&
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
        if (getElencoTipiAtto() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElencoTipiAtto());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElencoTipiAtto(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ElencoTipiAttoResponseReturn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.albopretorio.avt.linksmt.it/", ">elencoTipiAttoResponse>return"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoTipiAtto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elencoTipiAtto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "TipoAtto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
