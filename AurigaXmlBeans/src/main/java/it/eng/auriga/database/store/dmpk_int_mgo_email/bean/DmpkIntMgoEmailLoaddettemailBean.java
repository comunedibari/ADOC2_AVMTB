package it.eng.auriga.database.store.dmpk_int_mgo_email.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailLoaddettemailBean")
public class DmpkIntMgoEmailLoaddettemailBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_LOADDETTEMAIL";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idemailin;
	private java.lang.Integer flgsoloabilitazioniin;
	private java.lang.String xmldatiemailout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String idemailprecin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdemailin(){return idemailin;}
    public java.lang.Integer getFlgsoloabilitazioniin(){return flgsoloabilitazioniin;}
    public java.lang.String getXmldatiemailout(){return xmldatiemailout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getIdemailprecin(){return idemailprecin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdemailin(java.lang.String value){this.idemailin=value;}
    public void setFlgsoloabilitazioniin(java.lang.Integer value){this.flgsoloabilitazioniin=value;}
    public void setXmldatiemailout(java.lang.String value){this.xmldatiemailout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIdemailprecin(java.lang.String value){this.idemailprecin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    