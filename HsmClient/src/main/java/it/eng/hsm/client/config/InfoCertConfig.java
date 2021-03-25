package it.eng.hsm.client.config;

public class InfoCertConfig extends ClientConfig {

	private RestConfig restConfig;
	
	private String alias;
	private String otp;
	private String pin;
	private boolean auto = true;
	
	private PadesConfig padesConfig;
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public PadesConfig getPadesConfig() {
		return padesConfig;
	}
	public void setPadesConfig(PadesConfig padesConfig) {
		this.padesConfig = padesConfig;
	}
	public RestConfig getRestConfig() {
		return restConfig;
	}
	public void setRestConfig(RestConfig restConfig) {
		this.restConfig = restConfig;
	}
	public boolean isAuto() {
		return auto;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	
}
