package it.eng.auriga.database.store.dmpk_login.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkLoginResetpasswordnologinBean")
public class DmpkLoginResetpasswordnologinBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_LOGIN_RESETPASSWORDNOLOGIN";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String usernamein;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String emailutenteout;
	private java.lang.String newpasswordout;
	private java.lang.String emailmittenteout;
	private java.lang.String oggettomailout;
	private java.lang.String bodymailout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getEmailutenteout(){return emailutenteout;}
    public java.lang.String getNewpasswordout(){return newpasswordout;}
    public java.lang.String getEmailmittenteout(){return emailmittenteout;}
    public java.lang.String getOggettomailout(){return oggettomailout;}
    public java.lang.String getBodymailout(){return bodymailout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setEmailutenteout(java.lang.String value){this.emailutenteout=value;}
    public void setNewpasswordout(java.lang.String value){this.newpasswordout=value;}
    public void setEmailmittenteout(java.lang.String value){this.emailmittenteout=value;}
    public void setOggettomailout(java.lang.String value){this.oggettomailout=value;}
    public void setBodymailout(java.lang.String value){this.bodymailout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    