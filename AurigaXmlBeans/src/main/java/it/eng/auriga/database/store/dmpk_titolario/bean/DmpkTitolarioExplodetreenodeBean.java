package it.eng.auriga.database.store.dmpk_titolario.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkTitolarioExplodetreenodeBean")
public class DmpkTitolarioExplodetreenodeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_TITOLARIO_EXPLODETREENODE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String tsrifin;
	private java.lang.Integer flgsoloattivein;
	private java.math.BigDecimal idpianoclassifin;
	private java.lang.String idnodetoexplodein;
	private java.lang.String treexmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String finalitain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.Integer getFlgsoloattivein(){return flgsoloattivein;}
    public java.math.BigDecimal getIdpianoclassifin(){return idpianoclassifin;}
    public java.lang.String getIdnodetoexplodein(){return idnodetoexplodein;}
    public java.lang.String getTreexmlout(){return treexmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getFinalitain(){return finalitain;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setFlgsoloattivein(java.lang.Integer value){this.flgsoloattivein=value;}
    public void setIdpianoclassifin(java.math.BigDecimal value){this.idpianoclassifin=value;}
    public void setIdnodetoexplodein(java.lang.String value){this.idnodetoexplodein=value;}
    public void setTreexmlout(java.lang.String value){this.treexmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFinalitain(java.lang.String value){this.finalitain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    