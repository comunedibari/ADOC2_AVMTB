package it.eng.auriga.database.store.dmpk_core.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreGetidlibraryBean")
public class DmpkCoreGetidlibraryBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_GETIDLIBRARY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String flgtipoobjin;
	private java.math.BigDecimal idobjin;
	private java.math.BigDecimal idlibraryout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getFlgtipoobjin(){return flgtipoobjin;}
    public java.math.BigDecimal getIdobjin(){return idobjin;}
    public java.math.BigDecimal getIdlibraryout(){return idlibraryout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgtipoobjin(java.lang.String value){this.flgtipoobjin=value;}
    public void setIdobjin(java.math.BigDecimal value){this.idobjin=value;}
    public void setIdlibraryout(java.math.BigDecimal value){this.idlibraryout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    