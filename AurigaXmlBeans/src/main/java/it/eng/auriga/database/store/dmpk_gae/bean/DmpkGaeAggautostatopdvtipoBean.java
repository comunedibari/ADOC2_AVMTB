package it.eng.auriga.database.store.dmpk_gae.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkGaeAggautostatopdvtipoBean")
public class DmpkGaeAggautostatopdvtipoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_GAE_AGGAUTOSTATOPDVTIPO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	public Integer getParametro_1() { return 1; }
    
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    