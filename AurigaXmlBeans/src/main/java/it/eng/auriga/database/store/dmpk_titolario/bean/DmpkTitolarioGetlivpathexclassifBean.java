package it.eng.auriga.database.store.dmpk_titolario.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTitolarioGetlivpathexclassifBean")
public class DmpkTitolarioGetlivpathexclassifBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TITOLARIO_GETLIVPATHEXCLASSIF";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idclassificazionein;
	private java.lang.String percorsoxmlout;
	private java.lang.Integer flgshowcontentallowedout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.math.BigDecimal idpianoclassifin;
	private java.lang.String tsrifin;
	private java.lang.Integer flgsoloattivein;
	private java.lang.String finalitain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdclassificazionein(){return idclassificazionein;}
    public java.lang.String getPercorsoxmlout(){return percorsoxmlout;}
    public java.lang.Integer getFlgshowcontentallowedout(){return flgshowcontentallowedout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.math.BigDecimal getIdpianoclassifin(){return idpianoclassifin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.Integer getFlgsoloattivein(){return flgsoloattivein;}
    public java.lang.String getFinalitain(){return finalitain;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdclassificazionein(java.math.BigDecimal value){this.idclassificazionein=value;}
    public void setPercorsoxmlout(java.lang.String value){this.percorsoxmlout=value;}
    public void setFlgshowcontentallowedout(java.lang.Integer value){this.flgshowcontentallowedout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIdpianoclassifin(java.math.BigDecimal value){this.idpianoclassifin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setFlgsoloattivein(java.lang.Integer value){this.flgsoloattivein=value;}
    public void setFinalitain(java.lang.String value){this.finalitain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    