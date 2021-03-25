package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityTo_date_datetimexmltagBean")
public class DmpkUtilityTo_date_datetimexmltagBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_TO_DATE_DATETIMEXMLTAG";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String tagvaluein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getTagvaluein(){return tagvaluein;}
    
    public void setTagvaluein(java.lang.String value){this.tagvaluein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    