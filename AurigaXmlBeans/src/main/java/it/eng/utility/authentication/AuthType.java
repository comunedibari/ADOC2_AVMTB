package it.eng.utility.authentication;

public enum AuthType {

	DB("db"),
	LDAP("ldap");

	private String value;

	AuthType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
