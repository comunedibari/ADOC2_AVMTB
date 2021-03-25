package it.eng.auriga.database.store.dmo_coordinate.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmoCoordinateSet_yBean")
public class DmoCoordinateSet_yBean extends StoreBean implements Serializable{

	private static final String storeName = "DMO_COORDINATE_SET_Y";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Object self;
	private java.math.BigDecimal y;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Object getSelf(){return self;}
    public java.math.BigDecimal getY(){return y;}
    
    public void setSelf(java.lang.Object value){this.self=value;}
    public void setY(java.math.BigDecimal value){this.y=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    