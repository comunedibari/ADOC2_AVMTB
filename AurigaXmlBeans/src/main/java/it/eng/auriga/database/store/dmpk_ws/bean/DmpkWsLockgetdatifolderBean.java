package it.eng.auriga.database.store.dmpk_ws.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWsLockgetdatifolderBean")
public class DmpkWsLockgetdatifolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WS_LOCKGETDATIFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String flgtipowsin;
	private java.lang.String xmlin;
	private java.math.BigDecimal idfolderout;
	private java.lang.String xmlout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getFlgtipowsin(){return flgtipowsin;}
    public java.lang.String getXmlin(){return xmlin;}
    public java.math.BigDecimal getIdfolderout(){return idfolderout;}
    public java.lang.String getXmlout(){return xmlout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setFlgtipowsin(java.lang.String value){this.flgtipowsin=value;}
    public void setXmlin(java.lang.String value){this.xmlin=value;}
    public void setIdfolderout(java.math.BigDecimal value){this.idfolderout=value;}
    public void setXmlout(java.lang.String value){this.xmlout=value;}
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