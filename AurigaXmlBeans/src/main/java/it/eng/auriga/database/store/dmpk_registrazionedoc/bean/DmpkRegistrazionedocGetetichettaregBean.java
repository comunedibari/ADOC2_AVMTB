package it.eng.auriga.database.store.dmpk_registrazionedoc.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRegistrazionedocGetetichettaregBean")
public class DmpkRegistrazionedocGetetichettaregBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REGISTRAZIONEDOC_GETETICHETTAREG";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idudio;
	private java.lang.String codcategoriaregin;
	private java.lang.String siglaregin;
	private java.lang.Integer annoregin;
	private java.lang.Integer numregin;
	private java.lang.Integer nroallegatoin;
	private java.lang.String xmlopzionistampain;
	private java.lang.String contenutobarcodeout;
	private java.lang.String testoinchiaroout;
	private java.lang.String testoinchiarobarcodeout;
	private java.lang.String testoinchiaro2aregout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdudio(){return idudio;}
    public java.lang.String getCodcategoriaregin(){return codcategoriaregin;}
    public java.lang.String getSiglaregin(){return siglaregin;}
    public java.lang.Integer getAnnoregin(){return annoregin;}
    public java.lang.Integer getNumregin(){return numregin;}
    public java.lang.Integer getNroallegatoin(){return nroallegatoin;}
    public java.lang.String getXmlopzionistampain(){return xmlopzionistampain;}
    public java.lang.String getContenutobarcodeout(){return contenutobarcodeout;}
    public java.lang.String getTestoinchiaroout(){return testoinchiaroout;}
    public java.lang.String getTestoinchiarobarcodeout(){return testoinchiarobarcodeout;}
    public java.lang.String getTestoinchiaro2aregout(){return testoinchiaro2aregout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdudio(java.math.BigDecimal value){this.idudio=value;}
    public void setCodcategoriaregin(java.lang.String value){this.codcategoriaregin=value;}
    public void setSiglaregin(java.lang.String value){this.siglaregin=value;}
    public void setAnnoregin(java.lang.Integer value){this.annoregin=value;}
    public void setNumregin(java.lang.Integer value){this.numregin=value;}
    public void setNroallegatoin(java.lang.Integer value){this.nroallegatoin=value;}
    public void setXmlopzionistampain(java.lang.String value){this.xmlopzionistampain=value;}
    public void setContenutobarcodeout(java.lang.String value){this.contenutobarcodeout=value;}
    public void setTestoinchiaroout(java.lang.String value){this.testoinchiaroout=value;}
    public void setTestoinchiarobarcodeout(java.lang.String value){this.testoinchiarobarcodeout=value;}
    public void setTestoinchiaro2aregout(java.lang.String value){this.testoinchiaro2aregout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    