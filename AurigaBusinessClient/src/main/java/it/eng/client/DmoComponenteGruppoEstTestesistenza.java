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
public class DmoComponenteGruppoEstTestesistenza {
		
	private static final String SERVICE_NAME = "DmoComponenteGruppoEstTestesistenza";	
	private static Logger mLogger = Logger.getLogger(DmoComponenteGruppoEstTestesistenza.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.auriga.database.store.result.bean.StoreResultBean 
  	 public it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.auriga.database.store.dmo_componente_gruppo_est.bean.DmoComponenteGruppoEstTestesistenzaBean>  execute(Locale locale,it.eng.auriga.database.store.bean.SchemaBean var2,it.eng.auriga.database.store.dmo_componente_gruppo_est.bean.DmoComponenteGruppoEstTestesistenzaBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.auriga.database.store.dmo_componente_gruppo_est.bean.DmoComponenteGruppoEstTestesistenzaBean>>() {}.getType();
  	  	return (it.eng.auriga.database.store.result.bean.StoreResultBean<it.eng.auriga.database.store.dmo_componente_gruppo_est.bean.DmoComponenteGruppoEstTestesistenzaBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.database.store.result.bean.StoreResultBean.class,outputType, SERVICE_NAME, "execute", var2,var3);
	 } 
}    