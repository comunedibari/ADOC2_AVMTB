package it.eng.areas.hybrid.deploy.beans;

public class HybridPropertyConfigurator {

	private String hybridPort;
	private String hybridPortSSL;
	private String hybridWorkFolder;
	private Boolean hybridForceHttps;

	public String getHybridPort() {
		return hybridPort;
	}

	public void setHybridPort(String hybridPort) {
		this.hybridPort = hybridPort;
	}

	public String getHybridPortSSL() {
		return hybridPortSSL;
	}

	public void setHybridPortSSL(String hybridPortSSL) {
		this.hybridPortSSL = hybridPortSSL;
	}

	public String getHybridWorkFolder() {
		return hybridWorkFolder;
	}

	public void setHybridWorkFolder(String hybridWorkFolder) {
		this.hybridWorkFolder = hybridWorkFolder;
	}

	public Boolean getHybridForceHttps() {
		return hybridForceHttps;
	}

	public void setHybridForceHttps(Boolean hybridForceHttps) {
		this.hybridForceHttps = hybridForceHttps;
	}

}
