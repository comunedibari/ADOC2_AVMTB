package it.eng.auriga.database.store.dmpk_core_2.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2ToglidaBean")
public class DmpkCore2ToglidaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_TOGLIDA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String flgtpobjinnerin;
	private java.math.BigDecimal idobjinnerin;
	private java.lang.String nomeobjinnerin;
	private java.lang.String flgtpobjouterin;
	private java.math.BigDecimal idobjouterin;
	private java.lang.String pathnomeobjouterin;
	private java.math.BigDecimal idlibraryin;
	private java.lang.String nomelibraryin;
	private java.lang.String urixmlout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getFlgtpobjinnerin(){return flgtpobjinnerin;}
    public java.math.BigDecimal getIdobjinnerin(){return idobjinnerin;}
    public java.lang.String getNomeobjinnerin(){return nomeobjinnerin;}
    public java.lang.String getFlgtpobjouterin(){return flgtpobjouterin;}
    public java.math.BigDecimal getIdobjouterin(){return idobjouterin;}
    public java.lang.String getPathnomeobjouterin(){return pathnomeobjouterin;}
    public java.math.BigDecimal getIdlibraryin(){return idlibraryin;}
    public java.lang.String getNomelibraryin(){return nomelibraryin;}
    public java.lang.String getUrixmlout(){return urixmlout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgtpobjinnerin(java.lang.String value){this.flgtpobjinnerin=value;}
    public void setIdobjinnerin(java.math.BigDecimal value){this.idobjinnerin=value;}
    public void setNomeobjinnerin(java.lang.String value){this.nomeobjinnerin=value;}
    public void setFlgtpobjouterin(java.lang.String value){this.flgtpobjouterin=value;}
    public void setIdobjouterin(java.math.BigDecimal value){this.idobjouterin=value;}
    public void setPathnomeobjouterin(java.lang.String value){this.pathnomeobjouterin=value;}
    public void setIdlibraryin(java.math.BigDecimal value){this.idlibraryin=value;}
    public void setNomelibraryin(java.lang.String value){this.nomelibraryin=value;}
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