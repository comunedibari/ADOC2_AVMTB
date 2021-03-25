package it.eng.auriga.database.store.dmpk_core.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreAggiornaprogressivoudBean")
public class DmpkCoreAggiornaprogressivoudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_AGGIORNAPROGRESSIVOUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idudin;
	public Integer getParametro_1() { return 1; }
    public java.math.BigDecimal getIdudin(){return idudin;}
    
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    