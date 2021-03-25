package it.eng.hsm.client.config;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class ItagileConfig extends ClientConfig {

	@XmlVariabile(nome = "wsSignConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsSignConfig;
	@XmlVariabile(nome = "wsCertConfig", tipo = TipoVariabile.NESTED)
	private WSConfig wsCertConfig;
	@XmlVariabile(nome = "user", tipo = TipoVariabile.SEMPLICE)
	private String user;
	@XmlVariabile(nome = "password", tipo = TipoVariabile.SEMPLICE)
	private String password;
	@XmlVariabile(nome = "typeOtpAuth", tipo = TipoVariabile.SEMPLICE)
	private String typeOtpAuth;
	@XmlVariabile(nome = "otpPwd", tipo = TipoVariabile.SEMPLICE)
	private String otpPwd;
	@XmlVariabile(nome = "padesConfig", tipo = TipoVariabile.NESTED)
	private PadesConfig padesConfig;

	public WSConfig getWsSignConfig() {
		return wsSignConfig;
	}

	public void setWsSignConfig(WSConfig wsSignConfig) {
		this.wsSignConfig = wsSignConfig;
	}

	public WSConfig getWsCertConfig() {
		return wsCertConfig;
	}

	public void setWsCertConfig(WSConfig wsCertConfig) {
		this.wsCertConfig = wsCertConfig;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTypeOtpAuth() {
		return typeOtpAuth;
	}

	public void setTypeOtpAuth(String typeOtpAuth) {
		this.typeOtpAuth = typeOtpAuth;
	}

	public String getOtpPwd() {
		return otpPwd;
	}

	public void setOtpPwd(String otpPwd) {
		this.otpPwd = otpPwd;
	}

	public PadesConfig getPadesConfig() {
		return padesConfig;
	}

	public void setPadesConfig(PadesConfig padesConfig) {
		this.padesConfig = padesConfig;
	}

}
