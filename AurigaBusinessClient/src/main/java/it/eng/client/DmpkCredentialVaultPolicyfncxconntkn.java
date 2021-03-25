package it.eng.client;
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author ServiceClient generator 0.0.4-SNAPSHOT
 */
public class DmpkCredentialVaultPolicyfncxconntkn {
		
	private static final String SERVICE_NAME = "DmpkCredentialVaultPolicyfncxconntkn";	
	private static Logger mLogger = Logger.getLogger(DmpkCredentialVaultPolicyfncxconntkn.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.auriga.database.store.result.bean.StoreResultBean 
  	 public it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.auriga.database.store.dmpk_credential_vault.bean.DmpkCredentialVaultPolicyfncxconntknBean>  execute(Locale locale,it.eng.auriga.database.store.bean.SchemaBean var2,it.eng.auriga.database.store.dmpk_credential_vault.bean.DmpkCredentialVaultPolicyfncxconntknBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.auriga.database.store.dmpk_credential_vault.bean.DmpkCredentialVaultPolicyfncxconntknBean>>() {}.getType();
  	  	return (it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.auriga.database.store.dmpk_credential_vault.bean.DmpkCredentialVaultPolicyfncxconntknBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.database.store.result.bean.StoreResultBean.class,outputType, SERVICE_NAME, "execute", var2,var3);
	 } 
}    