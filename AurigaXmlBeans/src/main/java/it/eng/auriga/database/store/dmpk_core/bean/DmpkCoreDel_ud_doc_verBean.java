package it.eng.auriga.database.store.dmpk_core.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreDel_ud_doc_verBean")
public class DmpkCoreDel_ud_doc_verBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_DEL_UD_DOC_VER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String flgtipotargetin;
	private java.math.BigDecimal iduddocin;
	private java.math.BigDecimal nroprogrverio;
	private java.lang.Integer flgcancfisicain;
	private java.lang.String urixmlout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getFlgtipotargetin(){return flgtipotargetin;}
    public java.math.BigDecimal getIduddocin(){return iduddocin;}
    public java.math.BigDecimal getNroprogrverio(){return nroprogrverio;}
    public java.lang.Integer getFlgcancfisicain(){return flgcancfisicain;}
    public java.lang.String getUrixmlout(){return urixmlout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgtipotargetin(java.lang.String value){this.flgtipotargetin=value;}
    public void setIduddocin(java.math.BigDecimal value){this.iduddocin=value;}
    public void setNroprogrverio(java.math.BigDecimal value){this.nroprogrverio=value;}
    public void setFlgcancfisicain(java.lang.Integer value){this.flgcancfisicain=value;}
    public void setUrixmlout(java.lang.String value){this.urixmlout=value;}
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