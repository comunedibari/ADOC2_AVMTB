package it.eng.auriga.database.store.dmpk_login.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkLoginContadominiaccredutenteBean")
public class DmpkLoginContadominiaccredutenteBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_LOGIN_CONTADOMINIACCREDUTENTE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String usernamein;
	private java.lang.Integer nrodominiaccredout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.Integer getNrodominiaccredout(){return nrodominiaccredout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setNrodominiaccredout(java.lang.Integer value){this.nrodominiaccredout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    