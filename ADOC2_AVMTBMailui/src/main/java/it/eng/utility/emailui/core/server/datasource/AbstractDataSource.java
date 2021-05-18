package it.eng.utility.emailui.core.server.datasource;

import it.eng.utility.emailui.core.server.bean.AdvancedCriteria;
import it.eng.utility.emailui.core.server.bean.OrderByBean;
import it.eng.utility.emailui.core.server.bean.PaginatorBean;
import it.eng.utility.emailui.core.server.service.ErrorBean;
import it.eng.utility.emailui.core.shared.message.MessageBean;
import it.eng.utility.emailui.core.shared.message.MessageType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractDataSource<T,E> {
	
	public static final String KEY_FIELD_NAME = "keyproperty";
	
	private List<MessageBean> messages = null;
	
	/**
	 * Dati di contesto
	 */
	private Properties context;
	
	private HttpSession session;
	
	private HttpServletRequest request;

	private Map<String,String> extraparams;
	
	public abstract E add(T bean) throws Exception;
	
	public abstract E remove(T bean) throws Exception;
	
	public abstract E update(T bean,T oldvalue) throws Exception;
	
	public abstract PaginatorBean<E> fetch(AdvancedCriteria criteria,Integer startRow,Integer endRow,List<OrderByBean> orderby) throws Exception;
	
	public abstract Map<String,ErrorBean> validate(T bean) throws Exception;		
	
	public List<MessageBean> getMessages() {
		return messages;
	}
	
	
	
	public void setContext(Properties context) {
		this.context = context;
	}

	public Properties getContext() {
		return context;
	}

	public void addMessage(String message,String detail,MessageType type){
		
		if(messages==null){
			messages = new ArrayList<MessageBean>();
		}
		messages.add(new MessageBean(message, detail, type));
	}
	
	public void setExtraparams(Map<String, String> extraparams) {
		this.extraparams = extraparams;
	}

	public Map<String, String> getExtraparams() {
		return extraparams;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Retituisce la classe del bean generics
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class getBeanClassIn(){
		Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType)type;
                if(paramType.getActualTypeArguments()[0] instanceof ParameterizedType){
                	return (Class<T>) ((ParameterizedType)paramType.getActualTypeArguments()[0]).getRawType();
                }else{
                	return (Class<T>) paramType.getActualTypeArguments()[0];
                }	
        }
        return null;
	}
	
	/**
	 * Retituisce la classe del bean generics
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class getBeanClassOut(){
		Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType)type;
                return (Class<T>) paramType.getActualTypeArguments()[0];
        }
        return null;
	}
	
	

}
