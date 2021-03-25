package it.eng.auriga.database.store.dmpk_attributi_dinamici.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAttributiDinamiciComponicriterisuattrdinBean")
public class DmpkAttributiDinamiciComponicriterisuattrdinBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ATTRIBUTI_DINAMICI_COMPONICRITERISUATTRDIN";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String aliastabellain;
	private java.lang.String pktabellain;
	private java.lang.Object criterisuattrdinin;
	private java.lang.String whereconditiontoaddout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getAliastabellain(){return aliastabellain;}
    public java.lang.String getPktabellain(){return pktabellain;}
    public java.lang.Object getCriterisuattrdinin(){return criterisuattrdinin;}
    public java.lang.String getWhereconditiontoaddout(){return whereconditiontoaddout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setAliastabellain(java.lang.String value){this.aliastabellain=value;}
    public void setPktabellain(java.lang.String value){this.pktabellain=value;}
    public void setCriterisuattrdinin(java.lang.Object value){this.criterisuattrdinin=value;}
    public void setWhereconditiontoaddout(java.lang.String value){this.whereconditiontoaddout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    