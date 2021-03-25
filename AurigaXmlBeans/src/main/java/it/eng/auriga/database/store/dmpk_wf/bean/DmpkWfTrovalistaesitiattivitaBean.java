package it.eng.auriga.database.store.dmpk_wf.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfTrovalistaesitiattivitaBean")
public class DmpkWfTrovalistaesitiattivitaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_TROVALISTAESITIATTIVITA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String citypeflussowfio;
	private java.math.BigDecimal idprocessin;
	private java.lang.String activitynamein;
	private java.lang.String listaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCitypeflussowfio(){return citypeflussowfio;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getActivitynamein(){return activitynamein;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCitypeflussowfio(java.lang.String value){this.citypeflussowfio=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setActivitynamein(java.lang.String value){this.activitynamein=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    