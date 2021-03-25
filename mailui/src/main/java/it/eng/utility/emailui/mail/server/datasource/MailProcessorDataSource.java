package it.eng.utility.emailui.mail.server.datasource;

import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.operation.annotation.ConfigOperation;
import it.eng.utility.email.operation.annotation.MessageOperation;
import it.eng.utility.email.process.ProcessNodeExpression;
import it.eng.utility.email.process.ProcessOperation;
import it.eng.utility.email.process.ProcessOperationFlow;
import it.eng.utility.emailui.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;
import it.eng.utility.emailui.mail.shared.bean.OperationBean;
import it.eng.utility.emailui.mail.shared.bean.OperationTypeBean;
import it.eng.utility.emailui.mail.shared.bean.OperationTypeConfigBean;
import it.eng.utility.emailui.mail.shared.bean.ProcessFlowBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.google.common.base.Predicate;
import com.smartgwt.client.types.FieldType;

@Datasource(id="MailProcessorDataSource")
public class MailProcessorDataSource extends AbstractServiceDataSource<ProcessFlowBean,ProcessFlowBean>{

	private HashMap<String,String> operations  = new HashMap<>();
	
	private HashMap<String,List<OperationTypeConfigBean>> operationsconfig  = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public ProcessFlowBean call(ProcessFlowBean bean) throws Exception {
		
		Reflections reflection = new Reflections("it.eng.utility.email", new TypeAnnotationsScanner().filterResultsBy(new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				if(StringUtils.equalsIgnoreCase(input, MessageOperation.class.getName())){
					return true;
				}else{
					return false;	
				}
			}
		}));	
				
		Set<Class<?>> operationset = reflection.getTypesAnnotatedWith(MessageOperation.class);
			
		
		for(Class<?> classe:operationset){
			MessageOperation operation =  classe.getAnnotation(MessageOperation.class);
			operations.put(operation.name(), operation.description());
			

			
		
			//Recupero le configurazioni della classe
			Field[] fields = classe.getDeclaredFields();	
			
			if(fields!=null){
				List<OperationTypeConfigBean> operationtypelist = new ArrayList<>();
				for(Field field:fields){
					if(field.isAnnotationPresent(ConfigOperation.class)){
						
						String name = field.getAnnotation(ConfigOperation.class).name();
						String description = field.getAnnotation(ConfigOperation.class).description();
						String title = field.getAnnotation(ConfigOperation.class).title();
						
						Class typeclass = field.getType();
					
						String type = FieldType.TEXT.name(); 
						if(typeclass.isAssignableFrom(Number.class)){
							type = FieldType.INTEGER.name();
						} else if(typeclass.isAssignableFrom(Boolean.class)){
							type = FieldType.BOOLEAN.name();
						}
						OperationTypeConfigBean operationtype = new OperationTypeConfigBean();
									
						operationtype.setDescription(description);
						operationtype.setKey(name);
						operationtype.setTitle(title);
						operationtype.setType(type);
										
						
						operationtypelist.add(operationtype);
								
					}			
				}
							
				operationsconfig.put(operation.name(),operationtypelist);
				
			}
		}		
		
		//Recupero il flow
		ProcessOperationFlow flow = FactoryMailBusiness.getInstance().getProcessFlow(bean.getIdmailbox());
		
		//Elaboro il flow
		ProcessOperation operation = flow.getStart();
		
		OperationBean operationbean = new OperationBean();
		
		String operationname = FactoryMailBusiness.getInstance().getOperationName(operation.getOperationid());
		operationbean.setOperationdescription(operations.get(operationname));
		operationbean.setOperationname(operationname);
		operationbean.setConfiguration(operationsconfig.get(operationname));
		
		operationbean.setOperationnum(operation.getOperationid());
		operationbean.setOperationnumparent("0");	
		List<OperationBean> listbean = new ArrayList<>();		
		listbean.add(operationbean);
				
		ProcessFlowBean process = new ProcessFlowBean();
		
		recursiveElaborateFlow(operation,listbean);
		
		process.setOperation(listbean);
		
		
		//Recupero i tipi di operazione
		Set<String> setoperation = operations.keySet();
		List<OperationTypeBean> operationstype = new ArrayList<>();
		for(String op:setoperation){
			OperationTypeBean type = new OperationTypeBean();
			type.setName(op);
			type.setDescription(operations.get(op));
					
			
			type.setConfigs(operationsconfig.get(op));
				
			operationstype.add(type);
		}
		
		process.setOperationtype(operationstype);
		return process;
	}
	
	
	private void recursiveElaborateFlow(ProcessOperation processoperation,List<OperationBean> operationbean) throws Exception{
		
		if(processoperation.getNode()!=null && processoperation.getNode().getNodeElaborate()!=null){
			for(ProcessNodeExpression node:processoperation.getNode().getNodeElaborate()){
							
				OperationBean bean = new OperationBean();
				bean.setExpression(node.getExpression());
				
				String operationname = FactoryMailBusiness.getInstance().getOperationName(node.getOperation().getOperationid());
								
				bean.setOperationdescription(operations.get(operationname));
				bean.setOperationname(operationname);
				bean.setConfiguration(operationsconfig.get(operationname));
				
				//Ciclo le configurazioni e recupero il valore
				if(bean.getConfiguration()!=null) {
					for(OperationTypeConfigBean configbean:bean.getConfiguration()){
						
						configbean.setValue(FactoryMailBusiness.getInstance().getMessageOperationConfigValue(node.getOperation().getOperationid(), configbean.getKey()));
					}
				}	
				
				bean.setOperationnum(node.getOperation().getOperationid());
				bean.setOperationnumparent(processoperation.getOperationid());	
				recursiveElaborateFlow(node.getOperation(), operationbean);
				operationbean.add(bean);
			}
		}
	}
}
