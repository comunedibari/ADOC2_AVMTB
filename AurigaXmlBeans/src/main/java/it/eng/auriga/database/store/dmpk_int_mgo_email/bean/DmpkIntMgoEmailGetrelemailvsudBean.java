package it.eng.auriga.database.store.dmpk_int_mgo_email.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailGetrelemailvsudBean")
public class DmpkIntMgoEmailGetrelemailvsudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_GETRELEMAILVSUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idudin;
	private java.lang.String idemailarrivoout;
	private java.lang.Integer nroemailpecinviateout;
	private java.lang.Integer nroemailpeoinviateout;
	private java.lang.Integer flgricevutenotconfout;
	private java.lang.Integer flgricevutenoteccout;
	private java.lang.Integer flgricevutenotaggout;
	private java.lang.Integer flgricevutenotannout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getIdemailarrivoout(){return idemailarrivoout;}
    public java.lang.Integer getNroemailpecinviateout(){return nroemailpecinviateout;}
    public java.lang.Integer getNroemailpeoinviateout(){return nroemailpeoinviateout;}
    public java.lang.Integer getFlgricevutenotconfout(){return flgricevutenotconfout;}
    public java.lang.Integer getFlgricevutenoteccout(){return flgricevutenoteccout;}
    public java.lang.Integer getFlgricevutenotaggout(){return flgricevutenotaggout;}
    public java.lang.Integer getFlgricevutenotannout(){return flgricevutenotannout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setIdemailarrivoout(java.lang.String value){this.idemailarrivoout=value;}
    public void setNroemailpecinviateout(java.lang.Integer value){this.nroemailpecinviateout=value;}
    public void setNroemailpeoinviateout(java.lang.Integer value){this.nroemailpeoinviateout=value;}
    public void setFlgricevutenotconfout(java.lang.Integer value){this.flgricevutenotconfout=value;}
    public void setFlgricevutenoteccout(java.lang.Integer value){this.flgricevutenoteccout=value;}
    public void setFlgricevutenotaggout(java.lang.Integer value){this.flgricevutenotaggout=value;}
    public void setFlgricevutenotannout(java.lang.Integer value){this.flgricevutenotannout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    