package it.eng.auriga.database.store.dmpk_int_mgo_email.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailCollegaregtoemailBean")
public class DmpkIntMgoEmailCollegaregtoemailBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_COLLEGAREGTOEMAIL";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idemailin;
	private java.math.BigDecimal idudin;
	private java.lang.String categoriaregin;
	private java.lang.String siglaregistroin;
	private java.math.BigDecimal annoregin;
	private java.math.BigDecimal numeroregi;
	private java.lang.String impronteallegatiin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdemailin(){return idemailin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getCategoriaregin(){return categoriaregin;}
    public java.lang.String getSiglaregistroin(){return siglaregistroin;}
    public java.math.BigDecimal getAnnoregin(){return annoregin;}
    public java.math.BigDecimal getNumeroregi(){return numeroregi;}
    public java.lang.String getImpronteallegatiin(){return impronteallegatiin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdemailin(java.lang.String value){this.idemailin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setCategoriaregin(java.lang.String value){this.categoriaregin=value;}
    public void setSiglaregistroin(java.lang.String value){this.siglaregistroin=value;}
    public void setAnnoregin(java.math.BigDecimal value){this.annoregin=value;}
    public void setNumeroregi(java.math.BigDecimal value){this.numeroregi=value;}
    public void setImpronteallegatiin(java.lang.String value){this.impronteallegatiin=value;}
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