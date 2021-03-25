package it.eng.utility.authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.apache.log4j.Logger;

public class LdapAuth {

	private String hostLDAPServer;
	private String portLDAPServer;
	private String domain;
	private String dn;
	private String adminPassword;
	private String adminUser;
	private String timeout;
	private String attributiRiferimento;

	private final String DEFAULT_ATTRIBUTO_RIFERIMENTO = "sAMAccountName";
	private final String FILTER_PERSON 				   = "person";
	private final String FILTER_USER 				   = "user";
	private final String ATTRIBUTO_EMAIL               = "mail";
	private List<String> returnedAttsList = new ArrayList<String>();
	private boolean ssl;
	private String searchFilterBase;
	private String filter;

	private static Logger mLogger = Logger.getLogger(LdapAuth.class);

	public boolean authenticate(String username, String password) {
		try {
			Attributes att = authenticateUser(username, password);
			if (att == null) {
				mLogger.info("Login non effettuato");
				mLogger.info("Verificare le credenzali");
				return false;
			} else {
				mLogger.info("Login effettuato");
				try {
					if (att.get("givenName") != null) {
						String s = att.get("givenName").get().toString();
						mLogger.info("GIVEN NAME:" + s);
					}
					if (att.get("description") != null) {
						String s = att.get("description").get().toString();
						mLogger.info("description:" + s);
					}
				} catch (NamingException e) {
					mLogger.error("ERRORE LDAP authentication - Naming");
					return false;
				}
				return true;
			}
		} catch (Throwable e) {
			mLogger.error("ERRORE LDAP authentication");
			return false;
		}
	}

	public Attributes authenticateUser(String username, String password) {
		mLogger.info("authenticateUser");
		if (password == null || "".equalsIgnoreCase(password)) {
			return null;
		}
		Hashtable<String, String> environment = new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		if (ssl) {
			mLogger.info("ssl enabled");
			environment.put(Context.SECURITY_PROTOCOL, "ssl");
		} else {
			mLogger.info("ssl NOT enabled");
		}
		String[] token;
		if (hostLDAPServer.indexOf(",") != -1) {
			token = hostLDAPServer.split(",");
		} else {
			token = new String[1];
			token[0] = hostLDAPServer;
		}

		mLogger.info("Connessione al server ldap " + hostLDAPServer + " su porta " + portLDAPServer);
		String urlWithFailover = "";
		for (int i = 0; i < token.length; i++) {
			if (urlWithFailover.length() > 0) {
				urlWithFailover += " ";
			}
			if(ssl) {
				urlWithFailover += "ldaps://" + token[i] + ":" + portLDAPServer;
			} else {
				urlWithFailover += "ldap://" + token[i] + ":" + portLDAPServer;
			}
			
		}

		environment.put(Context.PROVIDER_URL, urlWithFailover);
		if(adminUser!=null && !"".equals(adminPassword)) {
			environment.put(Context.SECURITY_AUTHENTICATION, "simple");
		} else {
			environment.put(Context.SECURITY_AUTHENTICATION, "none");
		}
		if (timeout != null && !"".equals(timeout)) {
			environment.put("com.sun.jndi.ldap.connect.timeout", timeout);
		}
		String[] attributiLogin = new String[] { DEFAULT_ATTRIBUTO_RIFERIMENTO };
		if (attributiRiferimento != null && !"".equalsIgnoreCase(attributiRiferimento)) {
			attributiLogin = attributiRiferimento.split(",");
		}

		int i = 0;
		Attributes attributiReturn = null;
		while ((i < attributiLogin.length) && (attributiReturn == null)) {
			String attributoLogin = attributiLogin[i];
			int indexDomain = attributoLogin.indexOf("_");
			String domain = null;
			String attributo = null;
			if (indexDomain > -1) {
				String[] attribdom = attributoLogin.split("_");
				attributo = attribdom[0];
				domain = attribdom[1];
			} else {
				attributo = attributoLogin;
			}
			attributiReturn = login(environment, attributo, domain, username, password);
			i++;
		}
		return attributiReturn;
	}

	private Attributes login(Hashtable<String, String> environment, String attributo, String domain, String username, String password) {

		String usernameDomain = username;
		// controllo hce non ci sia gia'  il dominio
		if (usernameDomain.indexOf("@") == -1 && domain != null) {
			usernameDomain = username + "@" + domain;
		}
		// se cisono le credenziali admin uso quelle per entrare
		if (adminUser != null && !"".equals(adminUser) && adminPassword != null && !"".equals(adminPassword)) {
			environment.put(Context.SECURITY_PRINCIPAL, adminUser);
			environment.put(Context.SECURITY_CREDENTIALS, adminPassword);
		} else {
			environment.put(Context.SECURITY_PRINCIPAL, usernameDomain);
			environment.put(Context.SECURITY_CREDENTIALS, password);
		}
		LdapContext ctxGC = null;
		try {
			ctxGC = new InitialLdapContext(environment, null);
			mLogger.info("Contesto ottenuto");
			String returnedAtts[] = returnedAttsList.toArray(new String[returnedAttsList.size()]);
			String searchFilter = null;
			if (searchFilterBase!=null && searchFilterBase.equals(FILTER_PERSON)) {
				searchFilter = "(&(objectClass=person)(" + attributo + "=" + usernameDomain + "))";
				if(filter!=null && !"".equals(filter)) {
					searchFilter=searchFilter.concat(filter);
				}
			} 
			if(searchFilterBase!=null && searchFilterBase.equals(FILTER_USER)) {
				searchFilter = "(&(objectClass=user)(" + attributo + "=" + usernameDomain + "))";
				if(filter!=null && !"".equals(filter)) {
					searchFilter=searchFilter.concat(filter);
				}
			}	

			SearchControls searchCtls = new SearchControls();
			searchCtls.setReturningAttributes(returnedAtts);

			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchBase = dn;

			NamingEnumeration<SearchResult> answer = ctxGC.search(searchBase, searchFilter, searchCtls);
			String dn = null;
			while (answer.hasMoreElements()) {
				SearchResult sr = answer.next();
				Attributes attrs = sr.getAttributes();
				dn = sr.getNameInNamespace();
				mLogger.info("DINSTIGUISHED NAME(dn): " + dn);
				if (attrs != null && dn != null && !"".equals(dn)) {
					environment.put(Context.SECURITY_PRINCIPAL, dn);
					environment.put(Context.SECURITY_CREDENTIALS, password);
					ctxGC = new InitialLdapContext(environment, null);
					mLogger.info("Contesto utente ottenuto");
					return attrs;
				}
			}

		} catch (NamingException e) {
			mLogger.warn(e);
		}
		return null;
	}
	
		
	public Map<String, String> searchUserPropertiesByUsername(String user, List<String> returnedAttsListIn ) {

		Hashtable<String, String> environment = new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		if (ssl) {
			mLogger.info("ssl enabled");
			environment.put(Context.SECURITY_PROTOCOL, "ssl");
		} else {
			mLogger.info("ssl NOT enabled");
		}
		String[] token;
		if (hostLDAPServer.indexOf(",") != -1) {
			token = hostLDAPServer.split(",");
		} else {
			token = new String[1];
			token[0] = hostLDAPServer;
		}

		mLogger.info("Connessione al server ldap " + hostLDAPServer + " su porta " + portLDAPServer);
		String urlWithFailover = "";
		for (int i = 0; i < token.length; i++) {
			if (urlWithFailover.length() > 0) {
				urlWithFailover += " ";
			}
			urlWithFailover += "ldap://" + token[i] + ":" + portLDAPServer;
		}

		environment.put(Context.PROVIDER_URL, urlWithFailover);

		environment.put(Context.SECURITY_AUTHENTICATION, "simple");

		if (timeout != null && !"".equals(timeout)) {
			environment.put("com.sun.jndi.ldap.connect.timeout", timeout);
		}
		
		// se cisono le credenziali admin uso quelle per entrare
		if (adminUser != null && !"".equals(adminUser) && adminPassword != null && !"".equals(adminPassword)) {
			environment.put(Context.SECURITY_PRINCIPAL, adminUser);
			environment.put(Context.SECURITY_CREDENTIALS, adminPassword);
		} 
		LdapContext ctxGC = null;
				
		String[] attributiLogin = new String[] { DEFAULT_ATTRIBUTO_RIFERIMENTO };

		int i = 0;
		String domain = null;
		String attributo = null;
		
		Map<String, String> attributiUtente = new HashMap<String, String>();
		String usernameDomain = user;
				
		while ((i < attributiLogin.length) ) {
			
			String attributoLogin = attributiLogin[i];
			int indexDomain = attributoLogin.indexOf("_");
			if (indexDomain > -1) {
				String[] attribdom = attributoLogin.split("_");
				attributo = attribdom[0];
				domain = attribdom[1];
			} else {
				attributo = attributoLogin;
			}
		
			// controllo hce non ci sia gia'  il dominio
			if (usernameDomain.indexOf("@") == -1 && domain != null) {
				usernameDomain = user + "@" + domain;
			}
			
			try {
				ctxGC = new InitialLdapContext(environment, null);
				mLogger.info("Contesto ottenuto");
				String returnedAtts[] = returnedAttsListIn.toArray(new String[returnedAttsListIn.size()]);
				
				String searchFilter = "(&(objectClass=user)(" + attributo + "=" + usernameDomain + "))";
				//String searchFilter = "(&(objectClass=*)(| (cn=*passala*) (dc=*passala*) (description=*passala*) (displayName=*passala*) (dmdName=*passala*) (fullName=*passala*) (gecos=*passala*) (givenName=*passala*) (group=*passala*) (initials=*passala*) (labeledURI=*passala*) (name=*passala*) (o=*passala*) (ou=*passala*) (sAMAccountName=*passala*) (sn=*passala*) (uid=*passala*) ) )";
				
				SearchControls searchCtls = new SearchControls();
				searchCtls.setReturningAttributes(returnedAtts);

				searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				String searchBase = dn;

				NamingEnumeration<SearchResult> answer = ctxGC.search(searchBase, searchFilter, searchCtls);
				String dn = null;
				while (answer.hasMoreElements()) {
					SearchResult sr = answer.next();
					Attributes attrs = sr.getAttributes();
					if (attrs != null ) {
						NamingEnumeration<? extends Attribute> attrList = attrs.getAll();
						while( attrList.hasMore()){
							Attribute attrElement = attrList.next();
							attributiUtente.put(attrElement.getID(), (String)attrElement.get());
						}

						return attributiUtente;
					}
				}

			i++;
				
			} catch (NamingException e) {
				mLogger.warn(e);
			}
		}
		
		return null;
	}

	public Map<String, String> searchUserPropertiesByEmail(String email, List<String> returnedAttsListIn ) {

		Hashtable<String, String> environment = new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		if (ssl) {
			mLogger.info("ssl enabled");
			environment.put(Context.SECURITY_PROTOCOL, "ssl");
		} else {
			mLogger.info("ssl NOT enabled");
		}
		String[] token;
		if (hostLDAPServer.indexOf(",") != -1) {
			token = hostLDAPServer.split(",");
		} else {
			token = new String[1];
			token[0] = hostLDAPServer;
		}

		mLogger.info("Connessione al server ldap " + hostLDAPServer + " su porta " + portLDAPServer);
		String urlWithFailover = "";
		for (int i = 0; i < token.length; i++) {
			if (urlWithFailover.length() > 0) {
				urlWithFailover += " ";
			}
			urlWithFailover += "ldap://" + token[i] + ":" + portLDAPServer;
		}

		environment.put(Context.PROVIDER_URL, urlWithFailover);

		environment.put(Context.SECURITY_AUTHENTICATION, "simple");

		if (timeout != null && !"".equals(timeout)) {
			environment.put("com.sun.jndi.ldap.connect.timeout", timeout);
		}
		
		// se cisono le credenziali admin uso quelle per entrare
		if (adminUser != null && !"".equals(adminUser) && adminPassword != null && !"".equals(adminPassword)) {
			environment.put(Context.SECURITY_PRINCIPAL, adminUser);
			environment.put(Context.SECURITY_CREDENTIALS, adminPassword);
		} 
		LdapContext ctxGC = null;
				
		String[] attributiLogin = new String[] { ATTRIBUTO_EMAIL };

		int i = 0;
		String domain = null;
		String attributo = null;
		
		Map<String, String> attributiUtente = new HashMap<String, String>();
		String emailDomain = email;
		
		while ((i < attributiLogin.length) ) {
			
			String attributoLogin = attributiLogin[i];
			int indexDomain = attributoLogin.indexOf("_");
			if (indexDomain > -1) {
				String[] attribdom = attributoLogin.split("_");
				attributo = attribdom[0];
				domain = attribdom[1];
			} else {
				attributo = attributoLogin;
			}
						
			try {
				ctxGC = new InitialLdapContext(environment, null);
				mLogger.info("Contesto ottenuto");
				String returnedAtts[] = returnedAttsListIn .toArray(new String[returnedAttsListIn .size()]);
				
				String searchFilter = "(&(objectClass=user)(" + attributo + "=" + emailDomain + "))";				
				//searchFilter = "(&(objectClass=*)(| (cn=*passala*) (dc=*passala*) (description=*passala*) (displayName=*passala*) (dmdName=*passala*) (fullName=*passala*) (gecos=*passala*) (givenName=*passala*) (group=*passala*) (initials=*passala*) (labeledURI=*passala*) (name=*passala*) (o=*passala*) (ou=*passala*) (sAMAccountName=*passala*) (sn=*passala*) (uid=*passala*) ) )";
					
				SearchControls searchCtls = new SearchControls();
				searchCtls.setReturningAttributes(returnedAtts);

				searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				String searchBase = dn;

				NamingEnumeration<SearchResult> answer = ctxGC.search(searchBase, searchFilter, searchCtls);
				String dn = null;
				while (answer.hasMoreElements()) {
					SearchResult sr = answer.next();
					Attributes attrs = sr.getAttributes();
					if (attrs != null ) {
						NamingEnumeration<? extends Attribute> attrList = attrs.getAll();
						while( attrList.hasMore()){
							Attribute attrElement = attrList.next();
							attributiUtente.put(attrElement.getID(), (String)attrElement.get());
						}
						return attributiUtente;
					}
				}

			i++;
				
			} catch (NamingException e) {
				mLogger.warn(e);
			}
		}
		
		return null;
	}

	public String getHostLDAPServer() {
		return hostLDAPServer;
	}

	public void setHostLDAPServer(String hostLDAPServer) {
		this.hostLDAPServer = hostLDAPServer;
	}

	public String getPortLDAPServer() {
		return portLDAPServer;
	}

	public void setPortLDAPServer(String portLDAPServer) {
		this.portLDAPServer = portLDAPServer;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public List<String> getReturnedAttsList() {
		return returnedAttsList;
	}

	public void setReturnedAttsList(List<String> returnedAttsList) {
		this.returnedAttsList = returnedAttsList;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getAttributiRiferimento() {
		return attributiRiferimento;
	}

	public void setAttributiRiferimento(String attributiRiferimento) {
		this.attributiRiferimento = attributiRiferimento;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getSearchFilterBase() {
		return searchFilterBase;
	}

	public void setSearchFilterBase(String searchFilterBase) {
		this.searchFilterBase = searchFilterBase;
	}
}
