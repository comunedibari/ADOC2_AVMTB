package it.eng.auriga.database.store.dmpk_processes.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesSetsogginterniBean")
public class DmpkProcessesSetsogginterniBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_SETSOGGINTERNI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String ruolosoggettiin;
	private java.lang.Integer flgappendrecin;
	private java.lang.Integer nroposfirstrecin;
	private java.lang.String listaxmlin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getRuolosoggettiin(){return ruolosoggettiin;}
    public java.lang.Integer getFlgappendrecin(){return flgappendrecin;}
    public java.lang.Integer getNroposfirstrecin(){return nroposfirstrecin;}
    public java.lang.String getListaxmlin(){return listaxmlin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setRuolosoggettiin(java.lang.String value){this.ruolosoggettiin=value;}
    public void setFlgappendrecin(java.lang.Integer value){this.flgappendrecin=value;}
    public void setNroposfirstrecin(java.lang.Integer value){this.nroposfirstrecin=value;}
    public void setListaxmlin(java.lang.String value){this.listaxmlin=value;}
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