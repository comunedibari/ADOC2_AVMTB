package it.eng.auriga.database.store.dmpk_utility.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetconnschemaBean")
public class DmpkUtilityGetconnschemaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETCONNSCHEMA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String schemaout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getSchemaout(){return schemaout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setSchemaout(java.lang.String value){this.schemaout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    