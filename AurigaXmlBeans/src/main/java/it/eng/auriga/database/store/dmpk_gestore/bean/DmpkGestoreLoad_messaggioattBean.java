package it.eng.auriga.database.store.dmpk_gestore.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkGestoreLoad_messaggioattBean")
public class DmpkGestoreLoad_messaggioattBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_GESTORE_LOAD_MESSAGGIOATT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String displaynameattout;
	private java.lang.String msgattout;
	private java.lang.String noteprocout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getDisplaynameattout(){return displaynameattout;}
    public java.lang.String getMsgattout(){return msgattout;}
    public java.lang.String getNoteprocout(){return noteprocout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setDisplaynameattout(java.lang.String value){this.displaynameattout=value;}
    public void setMsgattout(java.lang.String value){this.msgattout=value;}
    public void setNoteprocout(java.lang.String value){this.noteprocout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    