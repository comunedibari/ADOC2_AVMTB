package it.eng.auriga.database.store.dmpk_credential_vault.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCredentialVaultInituserprofctxBean")
public class DmpkCredentialVaultInituserprofctxBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CREDENTIAL_VAULT_INITUSERPROFCTX";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	public Integer getParametro_1() { return 1; }
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    