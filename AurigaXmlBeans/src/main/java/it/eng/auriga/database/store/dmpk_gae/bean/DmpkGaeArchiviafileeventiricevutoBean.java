package it.eng.auriga.database.store.dmpk_gae.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkGaeArchiviafileeventiricevutoBean")
public class DmpkGaeArchiviafileeventiricevutoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_GAE_ARCHIVIAFILEEVENTIRICEVUTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String xmlrequestin;
	private java.lang.String xmldatifilericevutoin;
	private java.lang.String contenutofileeventiin;
	private java.lang.String idfileout;
	private java.lang.String contfileeventielabout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getXmlrequestin(){return xmlrequestin;}
    public java.lang.String getXmldatifilericevutoin(){return xmldatifilericevutoin;}
    public java.lang.String getContenutofileeventiin(){return contenutofileeventiin;}
    public java.lang.String getIdfileout(){return idfileout;}
    public java.lang.String getContfileeventielabout(){return contfileeventielabout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setXmlrequestin(java.lang.String value){this.xmlrequestin=value;}
    public void setXmldatifilericevutoin(java.lang.String value){this.xmldatifilericevutoin=value;}
    public void setContenutofileeventiin(java.lang.String value){this.contenutofileeventiin=value;}
    public void setIdfileout(java.lang.String value){this.idfileout=value;}
    public void setContfileeventielabout(java.lang.String value){this.contfileeventielabout=value;}
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