package it.eng.auriga.database.store.dmpk_raccomandate.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRaccomandateRic_raccomandateBean")
public class DmpkRaccomandateRic_raccomandateBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_RACCOMANDATE_RIC_RACCOMANDATE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String v_ric;
	private java.lang.String resultout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getV_ric(){return v_ric;}
    public java.lang.String getResultout(){return resultout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setV_ric(java.lang.String value){this.v_ric=value;}
    public void setResultout(java.lang.String value){this.resultout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    