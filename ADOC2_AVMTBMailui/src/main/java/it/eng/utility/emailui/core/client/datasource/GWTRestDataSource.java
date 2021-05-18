package it.eng.utility.emailui.core.client.datasource;


import it.eng.utility.emailui.core.client.util.UserUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.JSON;

public class GWTRestDataSource extends RestDataSource {
	
//	public static void settingMessageBox(MessageBox messagebo){
//		messagebox = messagebo;
//	}
//	
//	private static MessageBox messagebox = null;
	
	private LinkedHashMap<String, String> extratypeparam = new LinkedHashMap<String, String>();

	public GWTRestDataSource(String serverid) {
		init(serverid,null,FieldType.TEXT);		
	}
	
	/**
	 * Effettua l'ovveride di un tipo definito nel datasource
	 * @param nama
	 * @param type
	 */
	public void addFieldType(String name,String type){
		extratypeparam.put(name, type);
	}
	
	@Override
	protected Object transformRequest(DSRequest dsRequest) {
		dsRequest.setContentType("application/json; charset=utf-8");
		
		final Map<String, String> headers = new HashMap<String, String> ();
	    headers.put ("Accept", "application/json");
	    headers.put ("Accept-Charset", "utf-8");
		
	    dsRequest.setHttpHeaders(headers);
	    
	    
		//Setto i dati di contesto
		JSOHelper.setAttribute(dsRequest.getData(), "CONTEXT", UserUtil.getContextToJson());
		
		//Recupero gli attributi definiti
		DataSourceField[] fields = getFields();
		if(fields!=null){
			for(DataSourceField field:fields){
				if(!extratypeparam.containsKey(field.getName())){
					extratypeparam.put(field.getName(), field.getType().getValue());
				}
			}
		}
		
		if(!extratypeparam.isEmpty()){
			//Setto gli attributi
			JSOHelper.setAttribute(dsRequest.getData(), "FIELD_TYPE", extratypeparam);
		}
		
		return super.transformRequest(dsRequest);
	}

	@Override
	protected void transformResponse(DSResponse response, DSRequest request, Object data) {
			
		JavaScriptObject dataobj = (JavaScriptObject)data;
		JavaScriptObject responseobj = JSOHelper.getAttributeAsJavaScriptObject(dataobj, "response");
		
		//Setto i dati del contesto
		String json = JSON.encode(JSOHelper.getAttributeAsJavaScriptObject(responseobj, "context"));
		if(json!=null){
			UserUtil.setJsonContext(json);
		}
		//Setto i messsaggi di errore
		JavaScriptObject[] messagesobj = JSOHelper.getAttributeAsJavaScriptObjectArray(responseobj, "messages");
		if(messagesobj!=null){
			//Converto e salvo i valori sulla messagebox;
//			List<MessageBean> messages = new ArrayList<MessageBean>();
//			for(JavaScriptObject messageobj:messagesobj){
//				MessageBean bean = JSONUtil.MessageBeanJsonReader.read(JSON.encode(messageobj));
//				messages.add(bean);				
//			}
//			messagebox.addMessages(messages);
			
		}
		super.transformResponse(response, request, data);
	}
	
	
	public GWTRestDataSource(String serverid,String keyproperty,FieldType keytype) {
		init(serverid,keyproperty,keytype);		
	}
	
	public GWTRestDataSource(String serverid,String keyproperty,FieldType keytype,List<DataSourceField> fields) {
		init(serverid,keyproperty,keytype);		
		setFields(fields.toArray(new DataSourceField[0]));
	}

	public GWTRestDataSource(String serverid,List<DataSourceField> fields) {
		init(serverid,null,null);		
		setFields(fields.toArray(new DataSourceField[0]));
	}
	
	public GWTRestDataSource(String serverid,List<DataSourceField> fields,String id) {
		setID(id);
		init(serverid,null,null);		
		setFields(fields.toArray(new DataSourceField[0]));
	}
	
	@SuppressWarnings("unchecked")
	public void addParam(String key,String value){
		Map mappa = getDefaultParams();
		mappa.put(key, value);
		setDefaultParams(mappa);
	}
	
	private void init(String serverid,String keyproperty,FieldType keytype){
		 OperationBinding fetch = new OperationBinding();  
	     fetch.setOperationType(DSOperationType.FETCH);  
	     fetch.setDataProtocol(DSProtocol.POSTMESSAGE);  
	     OperationBinding add = new OperationBinding();  
	     add.setOperationType(DSOperationType.ADD);  
	     add.setDataProtocol(DSProtocol.POSTMESSAGE);  
	     OperationBinding update = new OperationBinding();  
	     update.setOperationType(DSOperationType.UPDATE);  
	     update.setDataProtocol(DSProtocol.POSTMESSAGE);  
	     OperationBinding remove = new OperationBinding();  
	     remove.setOperationType(DSOperationType.REMOVE);  
	     remove.setDataProtocol(DSProtocol.POSTMESSAGE);  
	     OperationBinding validate = new OperationBinding();  
	     validate.setOperationType(DSOperationType.VALIDATE);  
	     validate.setDataProtocol(DSProtocol.POSTMESSAGE); 
	     setOperationBindings(fetch, add, update, remove,validate);
	     setDataFormat(DSDataFormat.JSON);
	     setDropExtraFields(false);
	     setDataProtocol(DSProtocol.POSTPARAMS);
	     setDisableQueuing(true);
	     
	     
	     
	     setDataURL("restdispatcher/datasourceservice/all");
	     Map<String, String> map = new HashMap<String,String>();
	     map.put("sourceidobject", serverid);
	     if(keyproperty!=null){
	    	 map.put("keyproperty",keyproperty);
	     }
	     setDefaultParams(map);
	          
	     if(keyproperty!=null){
	    	 DataSourceField field = new DataSourceField(keyproperty,keytype);
	    	 field.setPrimaryKey(true);
	     
	     	addField(field);
	     }
	}
}