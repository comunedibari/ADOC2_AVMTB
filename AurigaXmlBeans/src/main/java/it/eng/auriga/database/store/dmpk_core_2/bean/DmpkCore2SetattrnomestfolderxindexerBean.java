package it.eng.auriga.database.store.dmpk_core_2.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2SetattrnomestfolderxindexerBean")
public class DmpkCore2SetattrnomestfolderxindexerBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_SETATTRNOMESTFOLDERXINDEXER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idfolderin;
	public Integer getParametro_1() { return 1; }
    public java.math.BigDecimal getIdfolderin(){return idfolderin;}
    
    public void setIdfolderin(java.math.BigDecimal value){this.idfolderin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    