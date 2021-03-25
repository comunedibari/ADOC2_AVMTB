package it.eng.auriga.database.store.dmpk_processes.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesSetattrnomestprocxindexerBean")
public class DmpkProcessesSetattrnomestprocxindexerBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_SETATTRNOMESTPROCXINDEXER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idprocessin;
	public Integer getParametro_1() { return 1; }
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    