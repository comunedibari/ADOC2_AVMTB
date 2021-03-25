package it.eng.utility.ui.listener;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import it.eng.utility.performance.SessionListenerLogger;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
/**
 * 
 * @author DANCRIST
 *
 */

public class SessionListener implements HttpSessionListener { 
	
	private static SessionListenerLogger mLogger = new SessionListenerLogger();

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		
		SessionListenerBean sessionListenerBean = new SessionListenerBean();
		
		if(arg0.getSession().getAttribute("LOGIN_INFO") != null &&
				arg0.getSession().getAttribute("LOGIN_INFO") instanceof LoginBean){
			LoginBean lb = (LoginBean) arg0.getSession().getAttribute("LOGIN_INFO");
			sessionListenerBean.setDenominazione(lb.getDenominazione());
			sessionListenerBean.setUserid(lb.getUserid());	
		}
	
		sessionListenerBean.setIdSession(arg0.getSession().getId());
		sessionListenerBean.setCreationTime(new Date(arg0.getSession().getCreationTime()));
		sessionListenerBean.setSessionLastAccessedTime(new Date(arg0.getSession().getLastAccessedTime()));
		sessionListenerBean.setSessionMaxInactiveInterval((int) arg0.getSession().getMaxInactiveInterval());
		
		mLogger.createdSession(sessionListenerBean);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		
		SessionListenerBean sessionListenerBean = new SessionListenerBean();
		
		if(arg0.getSession().getAttribute("LOGIN_INFO") != null &&
				arg0.getSession().getAttribute("LOGIN_INFO") instanceof LoginBean){
			LoginBean lb = (LoginBean) arg0.getSession().getAttribute("LOGIN_INFO");
			sessionListenerBean.setDenominazione(lb.getDenominazione());
			sessionListenerBean.setUserid(lb.getUserid());	
		}
	
		sessionListenerBean.setIdSession(arg0.getSession().getId());
		sessionListenerBean.setCreationTime(new Date(arg0.getSession().getCreationTime()));
		sessionListenerBean.setSessionLastAccessedTime(new Date(arg0.getSession().getLastAccessedTime()));
		sessionListenerBean.setSessionMaxInactiveInterval((int) arg0.getSession().getMaxInactiveInterval());
		
		mLogger.destroySession(sessionListenerBean);
	}

}