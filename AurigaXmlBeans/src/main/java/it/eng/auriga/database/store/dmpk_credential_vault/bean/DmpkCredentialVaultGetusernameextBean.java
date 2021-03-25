package it.eng.auriga.database.store.dmpk_credential_vault.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCredentialVaultGetusernameextBean")
public class DmpkCredentialVaultGetusernameextBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CREDENTIAL_VAULT_GETUSERNAMEEXT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserin;
	private java.lang.String usernamelocin;
	private java.lang.String codsistemaesternoin;
	private java.lang.String codistsistemaesternoin;
	private java.lang.String usernameextout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getUsernamelocin(){return usernamelocin;}
    public java.lang.String getCodsistemaesternoin(){return codsistemaesternoin;}
    public java.lang.String getCodistsistemaesternoin(){return codistsistemaesternoin;}
    public java.lang.String getUsernameextout(){return usernameextout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setUsernamelocin(java.lang.String value){this.usernamelocin=value;}
    public void setCodsistemaesternoin(java.lang.String value){this.codsistemaesternoin=value;}
    public void setCodistsistemaesternoin(java.lang.String value){this.codistsistemaesternoin=value;}
    public void setUsernameextout(java.lang.String value){this.usernameextout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    