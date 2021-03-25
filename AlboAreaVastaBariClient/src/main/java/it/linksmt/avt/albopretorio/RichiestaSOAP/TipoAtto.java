/**
 * TipoAtto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.RichiestaSOAP;

public class TipoAtto  implements java.io.Serializable {
    private long idTipoAtto;

    private java.lang.String nome;

    private java.lang.String descrizione;

    private int durataPubblicazioneLegale;

    public TipoAtto() {
    }

    public TipoAtto(
           long idTipoAtto,
           java.lang.String nome,
           java.lang.String descrizione,
           int durataPubblicazioneLegale) {
           this.idTipoAtto = idTipoAtto;
           this.nome = nome;
           this.descrizione = descrizione;
           this.durataPubblicazioneLegale = durataPubblicazioneLegale;
    }


    /**
     * Gets the idTipoAtto value for this TipoAtto.
     * 
     * @return idTipoAtto
     */
    public long getIdTipoAtto() {
        return idTipoAtto;
    }


    /**
     * Sets the idTipoAtto value for this TipoAtto.
     * 
     * @param idTipoAtto
     */
    public void setIdTipoAtto(long idTipoAtto) {
        this.idTipoAtto = idTipoAtto;
    }


    /**
     * Gets the nome value for this TipoAtto.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this TipoAtto.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the descrizione value for this TipoAtto.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this TipoAtto.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the durataPubblicazioneLegale value for this TipoAtto.
     * 
     * @return durataPubblicazioneLegale
     */
    public int getDurataPubblicazioneLegale() {
        return durataPubblicazioneLegale;
    }


    /**
     * Sets the durataPubblicazioneLegale value for this TipoAtto.
     * 
     * @param durataPubblicazioneLegale
     */
    public void setDurataPubblicazioneLegale(int durataPubblicazioneLegale) {
        this.durataPubblicazioneLegale = durataPubblicazioneLegale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoAtto)) return false;
        TipoAtto other = (TipoAtto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.idTipoAtto == other.getIdTipoAtto() &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            this.durataPubblicazioneLegale == other.getDurataPubblicazioneLegale();
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
        _hashCode += new Long(getIdTipoAtto()).hashCode();
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        _hashCode += getDurataPubblicazioneLegale();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoAtto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "TipoAtto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoAtto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoAtto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durataPubblicazioneLegale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataPubblicazioneLegale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
