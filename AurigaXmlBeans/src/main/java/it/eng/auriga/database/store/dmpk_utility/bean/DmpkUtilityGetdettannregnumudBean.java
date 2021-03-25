package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetdettannregnumudBean")
public class DmpkUtilityGetdettannregnumudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETDETTANNREGNUMUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object estremiregnumtabin;
	private java.lang.String codcategoriaregin;
	private java.lang.String siglaregin;
	private java.util.Date tslastupdstatoin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Object getEstremiregnumtabin(){return estremiregnumtabin;}
    public java.lang.String getCodcategoriaregin(){return codcategoriaregin;}
    public java.lang.String getSiglaregin(){return siglaregin;}
    public java.util.Date getTslastupdstatoin(){return tslastupdstatoin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setEstremiregnumtabin(java.lang.Object value){this.estremiregnumtabin=value;}
    public void setCodcategoriaregin(java.lang.String value){this.codcategoriaregin=value;}
    public void setSiglaregin(java.lang.String value){this.siglaregin=value;}
    public void setTslastupdstatoin(java.util.Date value){this.tslastupdstatoin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    