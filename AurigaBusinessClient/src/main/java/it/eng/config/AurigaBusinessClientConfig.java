package it.eng.config;

import it.eng.core.service.client.FactoryBusiness.BusinessType;

/**
 * @author ServiceClientConfig generator 1.0.4
 */
public class AurigaBusinessClientConfig {

	private String url;
	private BusinessType businesstype = BusinessType.REST;
	private static AurigaBusinessClientConfig instance;
	
	public static synchronized AurigaBusinessClientConfig getInstance() {
		if(instance==null){
			instance = new AurigaBusinessClientConfig();
			
		}
		return instance;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	public BusinessType getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(BusinessType businesstype) {
		this.businesstype = businesstype;
	}
}