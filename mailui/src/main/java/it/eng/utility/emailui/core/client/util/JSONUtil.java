package it.eng.utility.emailui.core.client.util;

import it.eng.utility.emailui.mail.shared.bean.MailCanStartBean;
import it.eng.utility.emailui.mail.shared.bean.MailconnectConfigBean;
import it.eng.utility.emailui.mail.shared.bean.OperationDatabaseBean; 
import it.eng.utility.emailui.mail.shared.bean.OperationTypeBean; 
import it.eng.utility.emailui.mail.shared.bean.MailboxBean; 
import it.eng.utility.emailui.mail.shared.bean.OperationBean; 
import it.eng.utility.emailui.core.shared.message.MessageBean; 
import it.eng.utility.emailui.core.shared.annotation.UserBean; 
import it.eng.utility.emailui.mail.shared.bean.ProcessFlowBean; 
import it.eng.utility.emailui.mail.shared.bean.OperationTypeConfigBean; 

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

import com.google.gwt.core.client.GWT;

/**
 * ATTENZIONE CLASSE AUTOGENERATA NON MODIFICARE MANUALMENTE
 * Classe di utilitÃ  per effettuare il json dei bean
 * @author michele
 *
 */
public class JSONUtil {

	
	public interface OperationDatabaseBeanJsonWriter extends JsonWriter<OperationDatabaseBean> {}
	public static final OperationDatabaseBeanJsonWriter OperationDatabaseBeanJsonWriter = GWT.create(OperationDatabaseBeanJsonWriter.class);

	public interface OperationDatabaseBeanJsonReader extends JsonReader<OperationDatabaseBean> {}
	public static final OperationDatabaseBeanJsonReader OperationDatabaseBeanJsonReader = GWT.create(OperationDatabaseBeanJsonReader.class);
	
	public interface OperationTypeBeanJsonWriter extends JsonWriter<OperationTypeBean> {}
	public static final OperationTypeBeanJsonWriter OperationTypeBeanJsonWriter = GWT.create(OperationTypeBeanJsonWriter.class);

	public interface OperationTypeBeanJsonReader extends JsonReader<OperationTypeBean> {}
	public static final OperationTypeBeanJsonReader OperationTypeBeanJsonReader = GWT.create(OperationTypeBeanJsonReader.class);
	
	public interface MailboxBeanJsonWriter extends JsonWriter<MailboxBean> {}
	public static final MailboxBeanJsonWriter MailboxBeanJsonWriter = GWT.create(MailboxBeanJsonWriter.class);

	public interface MailboxBeanJsonReader extends JsonReader<MailboxBean> {}
	public static final MailboxBeanJsonReader MailboxBeanJsonReader = GWT.create(MailboxBeanJsonReader.class);
	
	public interface OperationBeanJsonWriter extends JsonWriter<OperationBean> {}
	public static final OperationBeanJsonWriter OperationBeanJsonWriter = GWT.create(OperationBeanJsonWriter.class);

	public interface OperationBeanJsonReader extends JsonReader<OperationBean> {}
	public static final OperationBeanJsonReader OperationBeanJsonReader = GWT.create(OperationBeanJsonReader.class);
	
	public interface MessageBeanJsonWriter extends JsonWriter<MessageBean> {}
	public static final MessageBeanJsonWriter MessageBeanJsonWriter = GWT.create(MessageBeanJsonWriter.class);

	public interface MessageBeanJsonReader extends JsonReader<MessageBean> {}
	public static final MessageBeanJsonReader MessageBeanJsonReader = GWT.create(MessageBeanJsonReader.class);
	
	public interface UserBeanJsonWriter extends JsonWriter<UserBean> {}
	public static final UserBeanJsonWriter UserBeanJsonWriter = GWT.create(UserBeanJsonWriter.class);

	public interface UserBeanJsonReader extends JsonReader<UserBean> {}
	public static final UserBeanJsonReader UserBeanJsonReader = GWT.create(UserBeanJsonReader.class);
	
	public interface ProcessFlowBeanJsonWriter extends JsonWriter<ProcessFlowBean> {}
	public static final ProcessFlowBeanJsonWriter ProcessFlowBeanJsonWriter = GWT.create(ProcessFlowBeanJsonWriter.class);

	public interface ProcessFlowBeanJsonReader extends JsonReader<ProcessFlowBean> {}
	public static final ProcessFlowBeanJsonReader ProcessFlowBeanJsonReader = GWT.create(ProcessFlowBeanJsonReader.class);
	
	public interface OperationTypeConfigBeanJsonWriter extends JsonWriter<OperationTypeConfigBean> {}
	public static final OperationTypeConfigBeanJsonWriter OperationTypeConfigBeanJsonWriter = GWT.create(OperationTypeConfigBeanJsonWriter.class);

	public interface OperationTypeConfigBeanJsonReader extends JsonReader<OperationTypeConfigBean> {}
	public static final OperationTypeConfigBeanJsonReader OperationTypeConfigBeanJsonReader = GWT.create(OperationTypeConfigBeanJsonReader.class);
		
	public interface MailconnectConfigBeanJsonWriter extends JsonWriter<MailconnectConfigBean> {}
	public static final MailconnectConfigBeanJsonWriter MailconnectConfigBeanJsonWriter = GWT.create(MailconnectConfigBeanJsonWriter.class);

	public interface MailconnectConfigBeanJsonReader extends JsonReader<MailconnectConfigBean> {}
	public static final MailconnectConfigBeanJsonReader MailconnectConfigBeanJsonReader = GWT.create(MailconnectConfigBeanJsonReader.class);
	
	public interface MailCanStartBeanJsonWriter extends JsonWriter<MailCanStartBean> {}
	public static final MailCanStartBeanJsonWriter MailCanStartBeanJsonWriter = GWT.create(MailCanStartBeanJsonWriter.class);
	
	public interface MailCanStartBeanJsonReader extends JsonReader<MailCanStartBean> {}
	public static final MailCanStartBeanJsonReader MailCanStartBeanJsonReader = GWT.create(MailCanStartBeanJsonReader.class);
	
}	