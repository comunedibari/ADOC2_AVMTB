/**
 * DettaglioAttoDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.RichiestaSOAP;

public class DettaglioAttoDto  implements java.io.Serializable {
    private long idAtto;

    private long idEnte;

    private int numeroRegistro;

    private java.lang.String numeroRegistroGenerale;

    private java.lang.String numeroRegistroDiArea;

    private java.lang.String nomeArea;

    private java.lang.String oggetto;

    private java.lang.String dataPubblicazione;

    private java.lang.String dataScadenza;

    private java.lang.String dataAdozione;

    private java.lang.String ultimoGiornoDiPubblicazione;

    private it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO documento;

    private it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO[] allegati;

    public DettaglioAttoDto() {
    }

    public DettaglioAttoDto(
           long idAtto,
           long idEnte,
           int numeroRegistro,
           java.lang.String numeroRegistroGenerale,
           java.lang.String numeroRegistroDiArea,
           java.lang.String nomeArea,
           java.lang.String oggetto,
           java.lang.String dataPubblicazione,
           java.lang.String dataScadenza,
           java.lang.String dataAdozione,
           java.lang.String ultimoGiornoDiPubblicazione,
           it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO documento,
           it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO[] allegati) {
           this.idAtto = idAtto;
           this.idEnte = idEnte;
           this.numeroRegistro = numeroRegistro;
           this.numeroRegistroGenerale = numeroRegistroGenerale;
           this.numeroRegistroDiArea = numeroRegistroDiArea;
           this.nomeArea = nomeArea;
           this.oggetto = oggetto;
           this.dataPubblicazione = dataPubblicazione;
           this.dataScadenza = dataScadenza;
           this.dataAdozione = dataAdozione;
           this.ultimoGiornoDiPubblicazione = ultimoGiornoDiPubblicazione;
           this.documento = documento;
           this.allegati = allegati;
    }


    /**
     * Gets the idAtto value for this DettaglioAttoDto.
     * 
     * @return idAtto
     */
    public long getIdAtto() {
        return idAtto;
    }


    /**
     * Sets the idAtto value for this DettaglioAttoDto.
     * 
     * @param idAtto
     */
    public void setIdAtto(long idAtto) {
        this.idAtto = idAtto;
    }


    /**
     * Gets the idEnte value for this DettaglioAttoDto.
     * 
     * @return idEnte
     */
    public long getIdEnte() {
        return idEnte;
    }


    /**
     * Sets the idEnte value for this DettaglioAttoDto.
     * 
     * @param idEnte
     */
    public void setIdEnte(long idEnte) {
        this.idEnte = idEnte;
    }


    /**
     * Gets the numeroRegistro value for this DettaglioAttoDto.
     * 
     * @return numeroRegistro
     */
    public int getNumeroRegistro() {
        return numeroRegistro;
    }


    /**
     * Sets the numeroRegistro value for this DettaglioAttoDto.
     * 
     * @param numeroRegistro
     */
    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }


    /**
     * Gets the numeroRegistroGenerale value for this DettaglioAttoDto.
     * 
     * @return numeroRegistroGenerale
     */
    public java.lang.String getNumeroRegistroGenerale() {
        return numeroRegistroGenerale;
    }


    /**
     * Sets the numeroRegistroGenerale value for this DettaglioAttoDto.
     * 
     * @param numeroRegistroGenerale
     */
    public void setNumeroRegistroGenerale(java.lang.String numeroRegistroGenerale) {
        this.numeroRegistroGenerale = numeroRegistroGenerale;
    }


    /**
     * Gets the numeroRegistroDiArea value for this DettaglioAttoDto.
     * 
     * @return numeroRegistroDiArea
     */
    public java.lang.String getNumeroRegistroDiArea() {
        return numeroRegistroDiArea;
    }


    /**
     * Sets the numeroRegistroDiArea value for this DettaglioAttoDto.
     * 
     * @param numeroRegistroDiArea
     */
    public void setNumeroRegistroDiArea(java.lang.String numeroRegistroDiArea) {
        this.numeroRegistroDiArea = numeroRegistroDiArea;
    }


    /**
     * Gets the nomeArea value for this DettaglioAttoDto.
     * 
     * @return nomeArea
     */
    public java.lang.String getNomeArea() {
        return nomeArea;
    }


    /**
     * Sets the nomeArea value for this DettaglioAttoDto.
     * 
     * @param nomeArea
     */
    public void setNomeArea(java.lang.String nomeArea) {
        this.nomeArea = nomeArea;
    }


    /**
     * Gets the oggetto value for this DettaglioAttoDto.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this DettaglioAttoDto.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the dataPubblicazione value for this DettaglioAttoDto.
     * 
     * @return dataPubblicazione
     */
    public java.lang.String getDataPubblicazione() {
        return dataPubblicazione;
    }


    /**
     * Sets the dataPubblicazione value for this DettaglioAttoDto.
     * 
     * @param dataPubblicazione
     */
    public void setDataPubblicazione(java.lang.String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }


    /**
     * Gets the dataScadenza value for this DettaglioAttoDto.
     * 
     * @return dataScadenza
     */
    public java.lang.String getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this DettaglioAttoDto.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.lang.String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the dataAdozione value for this DettaglioAttoDto.
     * 
     * @return dataAdozione
     */
    public java.lang.String getDataAdozione() {
        return dataAdozione;
    }


    /**
     * Sets the dataAdozione value for this DettaglioAttoDto.
     * 
     * @param dataAdozione
     */
    public void setDataAdozione(java.lang.String dataAdozione) {
        this.dataAdozione = dataAdozione;
    }


    /**
     * Gets the ultimoGiornoDiPubblicazione value for this DettaglioAttoDto.
     * 
     * @return ultimoGiornoDiPubblicazione
     */
    public java.lang.String getUltimoGiornoDiPubblicazione() {
        return ultimoGiornoDiPubblicazione;
    }


    /**
     * Sets the ultimoGiornoDiPubblicazione value for this DettaglioAttoDto.
     * 
     * @param ultimoGiornoDiPubblicazione
     */
    public void setUltimoGiornoDiPubblicazione(java.lang.String ultimoGiornoDiPubblicazione) {
        this.ultimoGiornoDiPubblicazione = ultimoGiornoDiPubblicazione;
    }


    /**
     * Gets the documento value for this DettaglioAttoDto.
     * 
     * @return documento
     */
    public it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO getDocumento() {
        return documento;
    }


    /**
     * Sets the documento value for this DettaglioAttoDto.
     * 
     * @param documento
     */
    public void setDocumento(it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO documento) {
        this.documento = documento;
    }


    /**
     * Gets the allegati value for this DettaglioAttoDto.
     * 
     * @return allegati
     */
    public it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO[] getAllegati() {
        return allegati;
    }


    /**
     * Sets the allegati value for this DettaglioAttoDto.
     * 
     * @param allegati
     */
    public void setAllegati(it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO[] allegati) {
        this.allegati = allegati;
    }

    public it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO getAllegati(int i) {
        return this.allegati[i];
    }

    public void setAllegati(int i, it.linksmt.avt.albopretorio.RichiestaSOAP.DocumentoWSDTO _value) {
        this.allegati[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DettaglioAttoDto)) return false;
        DettaglioAttoDto other = (DettaglioAttoDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.idAtto == other.getIdAtto() &&
            this.idEnte == other.getIdEnte() &&
            this.numeroRegistro == other.getNumeroRegistro() &&
            ((this.numeroRegistroGenerale==null && other.getNumeroRegistroGenerale()==null) || 
             (this.numeroRegistroGenerale!=null &&
              this.numeroRegistroGenerale.equals(other.getNumeroRegistroGenerale()))) &&
            ((this.numeroRegistroDiArea==null && other.getNumeroRegistroDiArea()==null) || 
             (this.numeroRegistroDiArea!=null &&
              this.numeroRegistroDiArea.equals(other.getNumeroRegistroDiArea()))) &&
            ((this.nomeArea==null && other.getNomeArea()==null) || 
             (this.nomeArea!=null &&
              this.nomeArea.equals(other.getNomeArea()))) &&
            ((this.oggetto==null && other.getOggetto()==null) || 
             (this.oggetto!=null &&
              this.oggetto.equals(other.getOggetto()))) &&
            ((this.dataPubblicazione==null && other.getDataPubblicazione()==null) || 
             (this.dataPubblicazione!=null &&
              this.dataPubblicazione.equals(other.getDataPubblicazione()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.dataAdozione==null && other.getDataAdozione()==null) || 
             (this.dataAdozione!=null &&
              this.dataAdozione.equals(other.getDataAdozione()))) &&
            ((this.ultimoGiornoDiPubblicazione==null && other.getUltimoGiornoDiPubblicazione()==null) || 
             (this.ultimoGiornoDiPubblicazione!=null &&
              this.ultimoGiornoDiPubblicazione.equals(other.getUltimoGiornoDiPubblicazione()))) &&
            ((this.documento==null && other.getDocumento()==null) || 
             (this.documento!=null &&
              this.documento.equals(other.getDocumento()))) &&
            ((this.allegati==null && other.getAllegati()==null) || 
             (this.allegati!=null &&
              java.util.Arrays.equals(this.allegati, other.getAllegati())));
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
        _hashCode += getNumeroRegistro();
        if (getNumeroRegistroGenerale() != null) {
            _hashCode += getNumeroRegistroGenerale().hashCode();
        }
        if (getNumeroRegistroDiArea() != null) {
            _hashCode += getNumeroRegistroDiArea().hashCode();
        }
        if (getNomeArea() != null) {
            _hashCode += getNomeArea().hashCode();
        }
        if (getOggetto() != null) {
            _hashCode += getOggetto().hashCode();
        }
        if (getDataPubblicazione() != null) {
            _hashCode += getDataPubblicazione().hashCode();
        }
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getDataAdozione() != null) {
            _hashCode += getDataAdozione().hashCode();
        }
        if (getUltimoGiornoDiPubblicazione() != null) {
            _hashCode += getUltimoGiornoDiPubblicazione().hashCode();
        }
        if (getDocumento() != null) {
            _hashCode += getDocumento().hashCode();
        }
        if (getAllegati() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllegati());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllegati(), i);
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
        new org.apache.axis.description.TypeDesc(DettaglioAttoDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "DettaglioAttoDto"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroRegistroGenerale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroRegistroGenerale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroRegistroDiArea");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroRegistroDiArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeArea");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataPubblicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataPubblicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataAdozione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataAdozione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ultimoGiornoDiPubblicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ultimoGiornoDiPubblicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "documento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "DocumentoWSDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allegati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "DocumentoWSDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
