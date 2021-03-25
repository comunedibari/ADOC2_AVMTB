package it.eng.utility.ui.module.layout.server.common;

import javax.servlet.http.HttpSession;


public interface UserPrivilegiUtil {

	public String[] getPrivilegi(HttpSession lSession);
}
