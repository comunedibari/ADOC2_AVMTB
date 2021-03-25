package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilitySplitstringinvarrayBean")
public class DmpkUtilitySplitstringinvarrayBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_SPLITSTRINGINVARRAY";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String stringtosplitin;
	private java.lang.String separatorein;
	private java.lang.Integer sepdopoultimoelemin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getStringtosplitin(){return stringtosplitin;}
    public java.lang.String getSeparatorein(){return separatorein;}
    public java.lang.Integer getSepdopoultimoelemin(){return sepdopoultimoelemin;}
    
    public void setStringtosplitin(java.lang.String value){this.stringtosplitin=value;}
    public void setSeparatorein(java.lang.String value){this.separatorein=value;}
    public void setSepdopoultimoelemin(java.lang.Integer value){this.sepdopoultimoelemin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    