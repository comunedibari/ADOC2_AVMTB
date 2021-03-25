package it.eng.auriga.database.store.dmpk_policy.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyGetiduoappuserBean")
public class DmpkPolicyGetiduoappuserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_GETIDUOAPPUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserin;
	private java.math.BigDecimal idspaooin;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    