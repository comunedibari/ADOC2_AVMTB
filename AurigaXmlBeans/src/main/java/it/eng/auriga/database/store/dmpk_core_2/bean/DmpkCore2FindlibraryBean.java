package it.eng.auriga.database.store.dmpk_core_2.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2FindlibraryBean")
public class DmpkCore2FindlibraryBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_FINDLIBRARY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String nomelibraryin;
	private java.math.BigDecimal idlibraryin;
	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String accesstypein;
	private java.math.BigDecimal idlibraryout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getNomelibraryin(){return nomelibraryin;}
    public java.math.BigDecimal getIdlibraryin(){return idlibraryin;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getAccesstypein(){return accesstypein;}
    public java.math.BigDecimal getIdlibraryout(){return idlibraryout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setNomelibraryin(java.lang.String value){this.nomelibraryin=value;}
    public void setIdlibraryin(java.math.BigDecimal value){this.idlibraryin=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setAccesstypein(java.lang.String value){this.accesstypein=value;}
    public void setIdlibraryout(java.math.BigDecimal value){this.idlibraryout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    