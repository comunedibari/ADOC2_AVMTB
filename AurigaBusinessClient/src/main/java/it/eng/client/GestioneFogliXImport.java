package it.eng.client;
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author ServiceClient generator 1.0.4
 */
public class GestioneFogliXImport {
		
	private static final String SERVICE_NAME = "GestioneFogliXImport";	
	private static Logger mLogger = Logger.getLogger(GestioneFogliXImport.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.CreaFoglioXImportOutBean 
  	 public it.eng.document.function.bean.CreaFoglioXImportOutBean  creafoglioximport(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.CreaFoglioXImportInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaFoglioXImportOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaFoglioXImportOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaFoglioXImportOutBean.class,outputType, SERVICE_NAME, "creaFoglioXImport", var2,var3);
	 } 
}    