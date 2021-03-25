package it.eng.utility.emailui.core.client.util;

import com.smartgwt.client.util.Offline;

import it.eng.utility.emailui.core.shared.annotation.UserBean;

/**
 * Classe di utilità per la gestione dell'utente loggato
 * 
 * @author michele
 *
 */
public class UserUtil {

	private static final String USER_BEAN = "USERBEAN";

	public static final String USER_CONTEXT = "CONTEXT";

	/**
	 * Elimina l'utente dal cookie
	 */
	public static void removeContext() {
		try {
			Offline.remove(USER_CONTEXT);
		} catch (Exception e) {
			// Ho tentanto di rimuove l'utente non loggato
		}
	}

	/**
	 * Salva l''utente nel cookie
	 * 
	 * @param user
	 */
	public static void setJsonContext(String context) {
		Offline.put(USER_CONTEXT, context);
	}

	/**
	 * Salva l''utente nel cookie
	 * 
	 * @param user
	 */
	public static String getContextToJson() {
		try {
			String context = (String) Offline.get(USER_CONTEXT);
			if (context != null) {
				return context;
			}
			return null;
		} catch (Exception e) {
			// Contesto non trovato
			return null;
		}
	}

	/**
	 * Salva l''utente nel cookie
	 * 
	 * @param user
	 */
	public static void setUser(UserBean user) {
		Offline.put(USER_BEAN, JSONUtil.UserBeanJsonWriter.toJson(user));
	}

	/**
	 * recupera l'utente dal cookie
	 * 
	 * @param user
	 */
	public static UserBean getUser() {
		try {
			String userstr = (String) Offline.get(USER_BEAN);
			// System.out.println(userstr);
			if (userstr != null) {
				return (UserBean) JSONUtil.UserBeanJsonReader.read(userstr);
			}
			return null;
		} catch (Exception e) {
			// Utente non trovato
			return null;
		}
	}

	public static String getUserToJson() {
		try {
			String userstr = (String) Offline.get(USER_BEAN);
			if (userstr != null) {
				return userstr;
			}
			return null;
		} catch (Exception e) {
			// Utente non trovato
			return null;
		}
	}

	/**
	 * Elimina l'utente dal cookie
	 */
	public static void removeUser() {
		try {
			Offline.remove(USER_BEAN);
		} catch (Exception e) {
			// Ho tentanto di rimuove l'utente non loggato
		}
	}

}
