package it.eng.utility.emailui.mail.client;

import it.eng.utility.emailui.core.client.callback.ServiceCallback;
import it.eng.utility.emailui.core.client.datasource.GWTRestService;
import it.eng.utility.emailui.core.client.util.JSONUtil;
import it.eng.utility.emailui.mail.shared.bean.OperationBean;
import it.eng.utility.emailui.mail.shared.bean.OperationTypeBean;
import it.eng.utility.emailui.mail.shared.bean.OperationTypeConfigBean;

import java.util.List;

import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

public class WindowAddOperation extends Window {

	
	DynamicForm configform = null;
	
	public WindowAddOperation(final TreeNode rootnode,final TreeGrid tree,final List<OperationTypeBean> operationtypelist,final GWTRestService<OperationBean,OperationBean> service,final Layout layout,final String idmailbox) {
	
		setTitle("Nuova Operazione");
		setSize("500", "400");
		setCanDragResize(true);
		setShowModalMask(true);
		setIsModal(true);
		setAutoCenter(true);
			
		Button button = new Button("Salva");
		
		final SectionStack sectionStack = new SectionStack();  
		sectionStack.setWidth100();
		sectionStack.setHeight100();
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE); 
		
		SectionStackSection principali = new SectionStackSection("Operazione");  
		principali.setExpanded(true);  
    	final DynamicForm form = new DynamicForm();
    	principali.addItem(form);  
    	principali.setControls(button);
        sectionStack.addSection(principali);  
		
		SelectItem operationtype = new SelectItem("operationname","Tipo Operazione");
		//Recupero i valori del tipo        
        String[] str = new String[operationtypelist.size()];
        int y=0;
        for(OperationTypeBean type:operationtypelist) {
        	str[y++]=type.getName();
        }
		operationtype.setValueMap(str);
				
		TextItem expression = new TextItem("expression","Espressione");
		
		//Gestione delle configurazioni dinamiche
		final SectionStackSection configurazioni = new SectionStackSection("Configurazioni");  
		configurazioni.setExpanded(true);  

    	sectionStack.addSection(configurazioni);  
		
    	configurazioni.setExpanded(false);
    	
    	operationtype.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				
				Canvas[] itemscan = configurazioni.getItems();
				if(itemscan!=null){
					for(Canvas canvas:itemscan){
						canvas.destroy();
					}
				}
				
				configform = new DynamicForm();
				
				configform.setWrapItemTitles(false);
				
				//ciclo l'operazione de recupero il valore selezionato
				String operationname =  form.getValueAsString("operationname");
				configform.clear();
				for(OperationTypeBean type:operationtypelist) {
					if(type.getName().equals(operationname)){
						
						FormItem[] items = new FormItem[type.getConfigs().size()];
						int i=0;
						for(OperationTypeConfigBean conf:type.getConfigs()){
							
							FormItem item = new FormItem(conf.getKey());
							item.setType(conf.getType());
							item.setPrompt(conf.getDescription());
							item.setTitle(conf.getTitle());
							
							items[i++] = item;
							
							
						}
										
						configform.setFields(items);
														
						break;						
					}
				}	
				
				configurazioni.addItem(configform);
					
				
				configurazioni.setExpanded(true);
				
				//sectionStack.redraw();
				
				
			}
		});	
		
		//Gestione del bottone di salvataggio
		button.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//Recupero i dati e li inserisco nell'albero
				OperationBean bean = new OperationBean();
				bean.setExpression(form.getValueAsString("expression"));
				bean.setOperationname(form.getValueAsString("operationname"));
				bean.setIdmailbox(idmailbox);
				for(OperationTypeBean type:operationtypelist){
					if(type.getName().equals(bean.getOperationname())){
						bean.setOperationdescription(type.getDescription());
						
						//Recupero le configurazioni
						
						if(type!=null && type.getConfigs()!=null){
							for(OperationTypeConfigBean configbean:type.getConfigs()){
								configbean.setValue(configform.getValueAsString(configbean.getKey()));								
							}
						}				
						
						bean.setConfiguration(type.getConfigs());
						
						
						break;
					}
				}
				bean.setOperationnumparent(rootnode.getAttributeAsString("operationnum"));
				bean.setOperationnum(UUID.uuid());
				
				
				
				
							
				
				service.call(bean, JSONUtil.OperationBeanJsonWriter, JSONUtil.OperationBeanJsonReader, new ServiceCallback<OperationBean>() {
					
					@Override
					public void execute(OperationBean bean) {
						
						Layout.EmployeeTreeNode treenode = new Layout.EmployeeTreeNode(bean);
			
						tree.getData().add(treenode, rootnode);
						WindowAddOperation.this.markForDestroy();
						
						layout.saveflow(tree,idmailbox);
						
						
					}
				});
				

			}
		});
			
		form.setFields(operationtype,expression);
		addItem(sectionStack);
	}	
}