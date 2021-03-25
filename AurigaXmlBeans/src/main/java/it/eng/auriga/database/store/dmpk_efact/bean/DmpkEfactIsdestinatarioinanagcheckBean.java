package it.eng.auriga.database.store.dmpk_efact.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactIsdestinatarioinanagcheckBean")
public class DmpkEfactIsdestinatarioinanagcheckBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_ISDESTINATARIOINANAGCHECK";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String iddestinatarioin;
	private java.lang.String cfpivadestinatarioin;
	private java.lang.Integer trovatoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIddestinatarioin(){return iddestinatarioin;}
    public java.lang.String getCfpivadestinatarioin(){return cfpivadestinatarioin;}
    public java.lang.Integer getTrovatoout(){return trovatoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIddestinatarioin(java.lang.String value){this.iddestinatarioin=value;}
    public void setCfpivadestinatarioin(java.lang.String value){this.cfpivadestinatarioin=value;}
    public void setTrovatoout(java.lang.Integer value){this.trovatoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    