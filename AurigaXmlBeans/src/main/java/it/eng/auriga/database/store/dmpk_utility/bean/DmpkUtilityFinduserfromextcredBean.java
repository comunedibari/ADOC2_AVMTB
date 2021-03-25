package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFinduserfromextcredBean")
public class DmpkUtilityFinduserfromextcredBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDUSERFROMEXTCRED";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String codapplesternain;
	private java.lang.String codistanzaapplestin;
	private java.lang.String usernameestin;
	private java.lang.String passwordestin;
	private java.lang.Integer flgsolovldin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getCodapplesternain(){return codapplesternain;}
    public java.lang.String getCodistanzaapplestin(){return codistanzaapplestin;}
    public java.lang.String getUsernameestin(){return usernameestin;}
    public java.lang.String getPasswordestin(){return passwordestin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setCodapplesternain(java.lang.String value){this.codapplesternain=value;}
    public void setCodistanzaapplestin(java.lang.String value){this.codistanzaapplestin=value;}
    public void setUsernameestin(java.lang.String value){this.usernameestin=value;}
    public void setPasswordestin(java.lang.String value){this.passwordestin=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    