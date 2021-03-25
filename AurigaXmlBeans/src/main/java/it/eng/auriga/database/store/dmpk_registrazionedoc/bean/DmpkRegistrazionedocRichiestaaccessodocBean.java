package it.eng.auriga.database.store.dmpk_registrazionedoc.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRegistrazionedocRichiestaaccessodocBean")
public class DmpkRegistrazionedocRichiestaaccessodocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REGISTRAZIONEDOC_RICHIESTAACCESSODOC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String codcategoriaregin;
	private java.lang.String siglaregin;
	private java.lang.Integer annoregin;
	private java.math.BigDecimal numregin;
	private java.lang.String subnumregin;
	private java.lang.String tipoaccessoin;
	private java.lang.String motivoin;
	private java.math.BigDecimal idudout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCodcategoriaregin(){return codcategoriaregin;}
    public java.lang.String getSiglaregin(){return siglaregin;}
    public java.lang.Integer getAnnoregin(){return annoregin;}
    public java.math.BigDecimal getNumregin(){return numregin;}
    public java.lang.String getSubnumregin(){return subnumregin;}
    public java.lang.String getTipoaccessoin(){return tipoaccessoin;}
    public java.lang.String getMotivoin(){return motivoin;}
    public java.math.BigDecimal getIdudout(){return idudout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCodcategoriaregin(java.lang.String value){this.codcategoriaregin=value;}
    public void setSiglaregin(java.lang.String value){this.siglaregin=value;}
    public void setAnnoregin(java.lang.Integer value){this.annoregin=value;}
    public void setNumregin(java.math.BigDecimal value){this.numregin=value;}
    public void setSubnumregin(java.lang.String value){this.subnumregin=value;}
    public void setTipoaccessoin(java.lang.String value){this.tipoaccessoin=value;}
    public void setMotivoin(java.lang.String value){this.motivoin=value;}
    public void setIdudout(java.math.BigDecimal value){this.idudout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    