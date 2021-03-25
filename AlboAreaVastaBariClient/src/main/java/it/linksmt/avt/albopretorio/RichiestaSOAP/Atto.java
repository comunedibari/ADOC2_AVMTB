/**
 * Atto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.linksmt.avt.albopretorio.RichiestaSOAP;

public class Atto  implements java.io.Serializable {
    private long idAtto;

    private long idEnte;

    private int anno;

    private int numeroRegistro;

    private java.lang.String numeroRegistroGenerale;

    private java.lang.String numeroRegistroDiArea;

    private java.lang.String nomeArea;

    private long idTipoAtto;

    private java.lang.String oggetto;

    private java.lang.String descrizione;

    private java.lang.String note;

    private java.lang.String enteRichiestaPubblicazione;

    private java.lang.String dataPubblicazione;

    private java.lang.String dataScadenza;

    private java.lang.String dataAdozione;

    private int durataPubblicazioneAtto;

    private int offsetDurataPubblicazione;

    private long idDocumentFolder;

    private long idMainDocument;

    private java.lang.String rppNome;

    private java.lang.String rppCognome;

    private java.lang.String rppEmail;

    private java.lang.String rppTelefono;

    private java.lang.String rppIndirizzo;

    public Atto() {
    }

    public Atto(
           long idAtto,
           long idEnte,
           int anno,
           int numeroRegistro,
           java.lang.String numeroRegistroGenerale,
           java.lang.String numeroRegistroDiArea,
           java.lang.String nomeArea,
           long idTipoAtto,
           java.lang.String oggetto,
           java.lang.String descrizione,
           java.lang.String note,
           java.lang.String enteRichiestaPubblicazione,
           java.lang.String dataPubblicazione,
           java.lang.String dataScadenza,
           java.lang.String dataAdozione,
           int durataPubblicazioneAtto,
           int offsetDurataPubblicazione,
           long idDocumentFolder,
           long idMainDocument,
           java.lang.String rppNome,
           java.lang.String rppCognome,
           java.lang.String rppEmail,
           java.lang.String rppTelefono,
           java.lang.String rppIndirizzo) {
           this.idAtto = idAtto;
           this.idEnte = idEnte;
           this.anno = anno;
           this.numeroRegistro = numeroRegistro;
           this.numeroRegistroGenerale = numeroRegistroGenerale;
           this.numeroRegistroDiArea = numeroRegistroDiArea;
           this.nomeArea = nomeArea;
           this.idTipoAtto = idTipoAtto;
           this.oggetto = oggetto;
           this.descrizione = descrizione;
           this.note = note;
           this.enteRichiestaPubblicazione = enteRichiestaPubblicazione;
           this.dataPubblicazione = dataPubblicazione;
           this.dataScadenza = dataScadenza;
           this.dataAdozione = dataAdozione;
           this.durataPubblicazioneAtto = durataPubblicazioneAtto;
           this.offsetDurataPubblicazione = offsetDurataPubblicazione;
           this.idDocumentFolder = idDocumentFolder;
           this.idMainDocument = idMainDocument;
           this.rppNome = rppNome;
           this.rppCognome = rppCognome;
           this.rppEmail = rppEmail;
           this.rppTelefono = rppTelefono;
           this.rppIndirizzo = rppIndirizzo;
    }


    /**
     * Gets the idAtto value for this Atto.
     * 
     * @return idAtto
     */
    public long getIdAtto() {
        return idAtto;
    }


    /**
     * Sets the idAtto value for this Atto.
     * 
     * @param idAtto
     */
    public void setIdAtto(long idAtto) {
        this.idAtto = idAtto;
    }


    /**
     * Gets the idEnte value for this Atto.
     * 
     * @return idEnte
     */
    public long getIdEnte() {
        return idEnte;
    }


    /**
     * Sets the idEnte value for this Atto.
     * 
     * @param idEnte
     */
    public void setIdEnte(long idEnte) {
        this.idEnte = idEnte;
    }


    /**
     * Gets the anno value for this Atto.
     * 
     * @return anno
     */
    public int getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this Atto.
     * 
     * @param anno
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }


    /**
     * Gets the numeroRegistro value for this Atto.
     * 
     * @return numeroRegistro
     */
    public int getNumeroRegistro() {
        return numeroRegistro;
    }


    /**
     * Sets the numeroRegistro value for this Atto.
     * 
     * @param numeroRegistro
     */
    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }


    /**
     * Gets the numeroRegistroGenerale value for this Atto.
     * 
     * @return numeroRegistroGenerale
     */
    public java.lang.String getNumeroRegistroGenerale() {
        return numeroRegistroGenerale;
    }


    /**
     * Sets the numeroRegistroGenerale value for this Atto.
     * 
     * @param numeroRegistroGenerale
     */
    public void setNumeroRegistroGenerale(java.lang.String numeroRegistroGenerale) {
        this.numeroRegistroGenerale = numeroRegistroGenerale;
    }


    /**
     * Gets the numeroRegistroDiArea value for this Atto.
     * 
     * @return numeroRegistroDiArea
     */
    public java.lang.String getNumeroRegistroDiArea() {
        return numeroRegistroDiArea;
    }


    /**
     * Sets the numeroRegistroDiArea value for this Atto.
     * 
     * @param numeroRegistroDiArea
     */
    public void setNumeroRegistroDiArea(java.lang.String numeroRegistroDiArea) {
        this.numeroRegistroDiArea = numeroRegistroDiArea;
    }


    /**
     * Gets the nomeArea value for this Atto.
     * 
     * @return nomeArea
     */
    public java.lang.String getNomeArea() {
        return nomeArea;
    }


    /**
     * Sets the nomeArea value for this Atto.
     * 
     * @param nomeArea
     */
    public void setNomeArea(java.lang.String nomeArea) {
        this.nomeArea = nomeArea;
    }


    /**
     * Gets the idTipoAtto value for this Atto.
     * 
     * @return idTipoAtto
     */
    public long getIdTipoAtto() {
        return idTipoAtto;
    }


    /**
     * Sets the idTipoAtto value for this Atto.
     * 
     * @param idTipoAtto
     */
    public void setIdTipoAtto(long idTipoAtto) {
        this.idTipoAtto = idTipoAtto;
    }


    /**
     * Gets the oggetto value for this Atto.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this Atto.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the descrizione value for this Atto.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this Atto.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the note value for this Atto.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this Atto.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the enteRichiestaPubblicazione value for this Atto.
     * 
     * @return enteRichiestaPubblicazione
     */
    public java.lang.String getEnteRichiestaPubblicazione() {
        return enteRichiestaPubblicazione;
    }


    /**
     * Sets the enteRichiestaPubblicazione value for this Atto.
     * 
     * @param enteRichiestaPubblicazione
     */
    public void setEnteRichiestaPubblicazione(java.lang.String enteRichiestaPubblicazione) {
        this.enteRichiestaPubblicazione = enteRichiestaPubblicazione;
    }


    /**
     * Gets the dataPubblicazione value for this Atto.
     * 
     * @return dataPubblicazione
     */
    public java.lang.String getDataPubblicazione() {
        return dataPubblicazione;
    }


    /**
     * Sets the dataPubblicazione value for this Atto.
     * 
     * @param dataPubblicazione
     */
    public void setDataPubblicazione(java.lang.String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }


    /**
     * Gets the dataScadenza value for this Atto.
     * 
     * @return dataScadenza
     */
    public java.lang.String getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this Atto.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.lang.String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the dataAdozione value for this Atto.
     * 
     * @return dataAdozione
     */
    public java.lang.String getDataAdozione() {
        return dataAdozione;
    }


    /**
     * Sets the dataAdozione value for this Atto.
     * 
     * @param dataAdozione
     */
    public void setDataAdozione(java.lang.String dataAdozione) {
        this.dataAdozione = dataAdozione;
    }


    /**
     * Gets the durataPubblicazioneAtto value for this Atto.
     * 
     * @return durataPubblicazioneAtto
     */
    public int getDurataPubblicazioneAtto() {
        return durataPubblicazioneAtto;
    }


    /**
     * Sets the durataPubblicazioneAtto value for this Atto.
     * 
     * @param durataPubblicazioneAtto
     */
    public void setDurataPubblicazioneAtto(int durataPubblicazioneAtto) {
        this.durataPubblicazioneAtto = durataPubblicazioneAtto;
    }


    /**
     * Gets the offsetDurataPubblicazione value for this Atto.
     * 
     * @return offsetDurataPubblicazione
     */
    public int getOffsetDurataPubblicazione() {
        return offsetDurataPubblicazione;
    }


    /**
     * Sets the offsetDurataPubblicazione value for this Atto.
     * 
     * @param offsetDurataPubblicazione
     */
    public void setOffsetDurataPubblicazione(int offsetDurataPubblicazione) {
        this.offsetDurataPubblicazione = offsetDurataPubblicazione;
    }


    /**
     * Gets the idDocumentFolder value for this Atto.
     * 
     * @return idDocumentFolder
     */
    public long getIdDocumentFolder() {
        return idDocumentFolder;
    }


    /**
     * Sets the idDocumentFolder value for this Atto.
     * 
     * @param idDocumentFolder
     */
    public void setIdDocumentFolder(long idDocumentFolder) {
        this.idDocumentFolder = idDocumentFolder;
    }


    /**
     * Gets the idMainDocument value for this Atto.
     * 
     * @return idMainDocument
     */
    public long getIdMainDocument() {
        return idMainDocument;
    }


    /**
     * Sets the idMainDocument value for this Atto.
     * 
     * @param idMainDocument
     */
    public void setIdMainDocument(long idMainDocument) {
        this.idMainDocument = idMainDocument;
    }


    /**
     * Gets the rppNome value for this Atto.
     * 
     * @return rppNome
     */
    public java.lang.String getRppNome() {
        return rppNome;
    }


    /**
     * Sets the rppNome value for this Atto.
     * 
     * @param rppNome
     */
    public void setRppNome(java.lang.String rppNome) {
        this.rppNome = rppNome;
    }


    /**
     * Gets the rppCognome value for this Atto.
     * 
     * @return rppCognome
     */
    public java.lang.String getRppCognome() {
        return rppCognome;
    }


    /**
     * Sets the rppCognome value for this Atto.
     * 
     * @param rppCognome
     */
    public void setRppCognome(java.lang.String rppCognome) {
        this.rppCognome = rppCognome;
    }


    /**
     * Gets the rppEmail value for this Atto.
     * 
     * @return rppEmail
     */
    public java.lang.String getRppEmail() {
        return rppEmail;
    }


    /**
     * Sets the rppEmail value for this Atto.
     * 
     * @param rppEmail
     */
    public void setRppEmail(java.lang.String rppEmail) {
        this.rppEmail = rppEmail;
    }


    /**
     * Gets the rppTelefono value for this Atto.
     * 
     * @return rppTelefono
     */
    public java.lang.String getRppTelefono() {
        return rppTelefono;
    }


    /**
     * Sets the rppTelefono value for this Atto.
     * 
     * @param rppTelefono
     */
    public void setRppTelefono(java.lang.String rppTelefono) {
        this.rppTelefono = rppTelefono;
    }


    /**
     * Gets the rppIndirizzo value for this Atto.
     * 
     * @return rppIndirizzo
     */
    public java.lang.String getRppIndirizzo() {
        return rppIndirizzo;
    }


    /**
     * Sets the rppIndirizzo value for this Atto.
     * 
     * @param rppIndirizzo
     */
    public void setRppIndirizzo(java.lang.String rppIndirizzo) {
        this.rppIndirizzo = rppIndirizzo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Atto)) return false;
        Atto other = (Atto) obj;
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
            this.anno == other.getAnno() &&
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
            this.idTipoAtto == other.getIdTipoAtto() &&
            ((this.oggetto==null && other.getOggetto()==null) || 
             (this.oggetto!=null &&
              this.oggetto.equals(other.getOggetto()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.enteRichiestaPubblicazione==null && other.getEnteRichiestaPubblicazione()==null) || 
             (this.enteRichiestaPubblicazione!=null &&
              this.enteRichiestaPubblicazione.equals(other.getEnteRichiestaPubblicazione()))) &&
            ((this.dataPubblicazione==null && other.getDataPubblicazione()==null) || 
             (this.dataPubblicazione!=null &&
              this.dataPubblicazione.equals(other.getDataPubblicazione()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.dataAdozione==null && other.getDataAdozione()==null) || 
             (this.dataAdozione!=null &&
              this.dataAdozione.equals(other.getDataAdozione()))) &&
            this.durataPubblicazioneAtto == other.getDurataPubblicazioneAtto() &&
            this.offsetDurataPubblicazione == other.getOffsetDurataPubblicazione() &&
            this.idDocumentFolder == other.getIdDocumentFolder() &&
            this.idMainDocument == other.getIdMainDocument() &&
            ((this.rppNome==null && other.getRppNome()==null) || 
             (this.rppNome!=null &&
              this.rppNome.equals(other.getRppNome()))) &&
            ((this.rppCognome==null && other.getRppCognome()==null) || 
             (this.rppCognome!=null &&
              this.rppCognome.equals(other.getRppCognome()))) &&
            ((this.rppEmail==null && other.getRppEmail()==null) || 
             (this.rppEmail!=null &&
              this.rppEmail.equals(other.getRppEmail()))) &&
            ((this.rppTelefono==null && other.getRppTelefono()==null) || 
             (this.rppTelefono!=null &&
              this.rppTelefono.equals(other.getRppTelefono()))) &&
            ((this.rppIndirizzo==null && other.getRppIndirizzo()==null) || 
             (this.rppIndirizzo!=null &&
              this.rppIndirizzo.equals(other.getRppIndirizzo())));
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
        _hashCode += getAnno();
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
        _hashCode += new Long(getIdTipoAtto()).hashCode();
        if (getOggetto() != null) {
            _hashCode += getOggetto().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getEnteRichiestaPubblicazione() != null) {
            _hashCode += getEnteRichiestaPubblicazione().hashCode();
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
        _hashCode += getDurataPubblicazioneAtto();
        _hashCode += getOffsetDurataPubblicazione();
        _hashCode += new Long(getIdDocumentFolder()).hashCode();
        _hashCode += new Long(getIdMainDocument()).hashCode();
        if (getRppNome() != null) {
            _hashCode += getRppNome().hashCode();
        }
        if (getRppCognome() != null) {
            _hashCode += getRppCognome().hashCode();
        }
        if (getRppEmail() != null) {
            _hashCode += getRppEmail().hashCode();
        }
        if (getRppTelefono() != null) {
            _hashCode += getRppTelefono().hashCode();
        }
        if (getRppIndirizzo() != null) {
            _hashCode += getRppIndirizzo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Atto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://albopretorio.avt.linksmt.it/RichiestaSOAP", "Atto"));
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
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("idTipoAtto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoAtto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enteRichiestaPubblicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enteRichiestaPubblicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataPubblicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataPubblicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("durataPubblicazioneAtto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataPubblicazioneAtto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offsetDurataPubblicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "offsetDurataPubblicazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDocumentFolder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumentFolder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMainDocument");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMainDocument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rppNome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rppNome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rppCognome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rppCognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rppEmail");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rppEmail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rppTelefono");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rppTelefono"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rppIndirizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rppIndirizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
