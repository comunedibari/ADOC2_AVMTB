package it.eng.auriga.database.store.dmpk_int_mgo_email.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailGetsoggconprofilivscasellaBean")
public class DmpkIntMgoEmailGetsoggconprofilivscasellaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_GETSOGGCONPROFILIVSCASELLA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idcasellain;
	private java.lang.String idfruitoreenteaooout;
	private java.lang.Integer identeaooout;
	private java.lang.String desenteaooout;
	private java.lang.String xmlfruitoriprofout;
	private java.lang.String xmlutentiprofout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdcasellain(){return idcasellain;}
    public java.lang.String getIdfruitoreenteaooout(){return idfruitoreenteaooout;}
    public java.lang.Integer getIdenteaooout(){return identeaooout;}
    public java.lang.String getDesenteaooout(){return desenteaooout;}
    public java.lang.String getXmlfruitoriprofout(){return xmlfruitoriprofout;}
    public java.lang.String getXmlutentiprofout(){return xmlutentiprofout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdcasellain(java.lang.String value){this.idcasellain=value;}
    public void setIdfruitoreenteaooout(java.lang.String value){this.idfruitoreenteaooout=value;}
    public void setIdenteaooout(java.lang.Integer value){this.identeaooout=value;}
    public void setDesenteaooout(java.lang.String value){this.desenteaooout=value;}
    public void setXmlfruitoriprofout(java.lang.String value){this.xmlfruitoriprofout=value;}
    public void setXmlutentiprofout(java.lang.String value){this.xmlutentiprofout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    