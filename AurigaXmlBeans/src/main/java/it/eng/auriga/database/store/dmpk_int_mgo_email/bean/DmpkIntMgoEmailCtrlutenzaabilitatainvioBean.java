package it.eng.auriga.database.store.dmpk_int_mgo_email.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailCtrlutenzaabilitatainvioBean")
public class DmpkIntMgoEmailCtrlutenzaabilitatainvioBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_CTRLUTENZAABILITATAINVIO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String accountmittentein;
	private java.lang.Integer flgabilinvioout;
	private java.math.BigDecimal iduserout;
	private java.lang.String idutentepecout;
	private java.lang.String idaccountout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getAccountmittentein(){return accountmittentein;}
    public java.lang.Integer getFlgabilinvioout(){return flgabilinvioout;}
    public java.math.BigDecimal getIduserout(){return iduserout;}
    public java.lang.String getIdutentepecout(){return idutentepecout;}
    public java.lang.String getIdaccountout(){return idaccountout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setAccountmittentein(java.lang.String value){this.accountmittentein=value;}
    public void setFlgabilinvioout(java.lang.Integer value){this.flgabilinvioout=value;}
    public void setIduserout(java.math.BigDecimal value){this.iduserout=value;}
    public void setIdutentepecout(java.lang.String value){this.idutentepecout=value;}
    public void setIdaccountout(java.lang.String value){this.idaccountout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    