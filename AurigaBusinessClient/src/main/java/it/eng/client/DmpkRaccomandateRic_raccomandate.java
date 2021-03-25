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
public class DmpkRaccomandateRic_raccomandate {
		
	private static final String SERVICE_NAME = "DmpkRaccomandateRic_raccomandate";	
	private static Logger mLogger = Logger.getLogger(DmpkRaccomandateRic_raccomandate.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.auriga.database.store.result.bean.StoreResultBean 
  	 public it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.auriga.database.store.dmpk_raccomandate.bean.DmpkRaccomandateRic_raccomandateBean>  execute(Locale locale,it.eng.auriga.database.store.bean.SchemaBean var2,it.eng.auriga.database.store.dmpk_raccomandate.bean.DmpkRaccomandateRic_raccomandateBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.auriga.database.store.dmpk_raccomandate.bean.DmpkRaccomandateRic_raccomandateBean>>() {}.getType();
  	  	return (it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.auriga.database.store.dmpk_raccomandate.bean.DmpkRaccomandateRic_raccomandateBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.database.store.result.bean.StoreResultBean.class,outputType, SERVICE_NAME, "execute", var2,var3);
	 } 
}    