package it.eng.utility.emailui.mail.server.datasource;

import it.eng.utility.email.database.business.FactoryMailBusiness;
import it.eng.utility.email.process.ProcessNode;
import it.eng.utility.email.process.ProcessNodeExpression;
import it.eng.utility.email.process.ProcessOperation;
import it.eng.utility.email.process.ProcessOperationFlow;
import it.eng.utility.emailui.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;
import it.eng.utility.emailui.mail.shared.bean.OperationBean;
import it.eng.utility.emailui.mail.shared.bean.ProcessFlowBean;

import java.util.ArrayList;
import java.util.List;

@Datasource(id="MailProcessorSave")
public class MailProcessorSave extends AbstractServiceDataSource<ProcessFlowBean,ProcessFlowBean>{

		
	@Override
	public ProcessFlowBean call(ProcessFlowBean bean) throws Exception {
		
		
		ProcessOperationFlow flow = new ProcessOperationFlow();
			
		//Creo l'xml dell'albero
		ProcessOperation start = new ProcessOperation();
		start.setOperationid("1");
			
		recursiveElaborateFlow(start, bean.getOperation());
		
		flow.setStart(start);
				
		//Creo il Flow
		FactoryMailBusiness.getInstance().saveProcessFlow(bean.getIdmailbox(), flow);
		return bean;
	}
	
	
	private void recursiveElaborateFlow(ProcessOperation operationroot, List<OperationBean> operationbeans) throws Exception {
		
		List<ProcessNodeExpression> processnodeexpression = new ArrayList<>();
 		for(OperationBean operationbean:operationbeans) {
			
			//Recupero i valori del root node passato in ingresso
			if(operationbean.getOperationnumparent().equals(operationroot.getOperationid())){
				//Creo un nuovo process node
				ProcessNodeExpression nodeexpression = new ProcessNodeExpression();
				nodeexpression.setExpression(operationbean.getExpression());
				
				ProcessOperation operation = new ProcessOperation();
				operation.setOperationid(operationbean.getOperationnum());
				nodeexpression.setOperation(operation);
				//Chimata ricorsiva
				recursiveElaborateFlow(operation, operationbeans);
				
				processnodeexpression.add(nodeexpression);
				
			}	
		}
		if(!processnodeexpression.isEmpty()){
			ProcessNode node = new ProcessNode();
			node.setNodeElaborate(processnodeexpression);
			operationroot.setNode(node);
		}
		
	}
}
