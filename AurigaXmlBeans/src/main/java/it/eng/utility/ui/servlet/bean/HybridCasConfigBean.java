package it.eng.utility.ui.servlet.bean;

public class HybridCasConfigBean {
	 
	private String accessControlAllowOriginValue;
	private String accessControlAllowMethodsValue;
	private String accessControlAllowHeadersValue;
	private String accessControlMaxAgeValue;	
	private String baseUrl;
	private boolean attivaHybridCasConfigBean;
	
	
	public String getAccessControlAllowOriginValue() {
		return accessControlAllowOriginValue;
	}
	public void setAccessControlAllowOriginValue(String accessControlAllowOriginValue) {
		this.accessControlAllowOriginValue = accessControlAllowOriginValue;
	}
	public String getAccessControlAllowMethodsValue() {
		return accessControlAllowMethodsValue;
	}
	public void setAccessControlAllowMethodsValue(String accessControlAllowMethodsValue) {
		this.accessControlAllowMethodsValue = accessControlAllowMethodsValue;
	}
	public String getAccessControlAllowHeadersValue() {
		return accessControlAllowHeadersValue;
	}
	public void setAccessControlAllowHeadersValue(String accessControlAllowHeadersValue) {
		this.accessControlAllowHeadersValue = accessControlAllowHeadersValue;
	}
	public String getAccessControlMaxAgeValue() {
		return accessControlMaxAgeValue;
	}
	public void setAccessControlMaxAgeValue(String accessControlMaxAgeValue) {
		this.accessControlMaxAgeValue = accessControlMaxAgeValue;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public boolean isAttivaHybridCasConfigBean() {
		return attivaHybridCasConfigBean;
	}
	public void setAttivaHybridCasConfigBean(boolean attivaHybridCasConfigBean) {
		this.attivaHybridCasConfigBean = attivaHybridCasConfigBean;
	}
}