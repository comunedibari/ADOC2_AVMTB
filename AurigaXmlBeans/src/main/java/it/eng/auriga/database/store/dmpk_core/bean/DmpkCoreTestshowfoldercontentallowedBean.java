package it.eng.auriga.database.store.dmpk_core.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreTestshowfoldercontentallowedBean")
public class DmpkCoreTestshowfoldercontentallowedBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_TESTSHOWFOLDERCONTENTALLOWED";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idfolderin;
	private java.lang.String flgfolderudin;
	private java.lang.Integer flgshowcontentallowedout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdfolderin(){return idfolderin;}
    public java.lang.String getFlgfolderudin(){return flgfolderudin;}
    public java.lang.Integer getFlgshowcontentallowedout(){return flgshowcontentallowedout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdfolderin(java.math.BigDecimal value){this.idfolderin=value;}
    public void setFlgfolderudin(java.lang.String value){this.flgfolderudin=value;}
    public void setFlgshowcontentallowedout(java.lang.Integer value){this.flgshowcontentallowedout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    