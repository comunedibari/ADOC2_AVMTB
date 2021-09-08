package it.eng.utility.emailui.mail.client;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

import it.eng.utility.emailui.core.client.callback.ServiceCallback;
import it.eng.utility.emailui.core.client.datasource.GWTRestService;
import it.eng.utility.emailui.core.client.util.JSONUtil;
import it.eng.utility.emailui.mail.shared.bean.OperationBean;
import it.eng.utility.emailui.mail.shared.bean.OperationTypeBean;
import it.eng.utility.emailui.mail.shared.bean.ProcessFlowBean;

public class Layout extends VLayout {

	final GWTRestService<ProcessFlowBean, ProcessFlowBean> mailProcessorservice = new GWTRestService<ProcessFlowBean, ProcessFlowBean>(
			"MailProcessorDataSource");

	final GWTRestService<OperationBean, OperationBean> mailoperationsave = new GWTRestService<OperationBean, OperationBean>("OperationSave");

	final GWTRestService<OperationBean, OperationBean> mailoperationdelete = new GWTRestService<OperationBean, OperationBean>("OperationDelete");

	final GWTRestService<ProcessFlowBean, ProcessFlowBean> mailProcessorservicesave = new GWTRestService<ProcessFlowBean, ProcessFlowBean>("MailProcessorSave");

	private List<OperationTypeBean> operationtype;

	public Layout(final String idmailbox) {

		setLayoutMargin(0);
		setMargin(0);
		setPadding(0);
		setLeft(0);
		setTop(0);

		setWidth100();
		setHeight100();

		ProcessFlowBean bean = new ProcessFlowBean();
		bean.setIdmailbox(idmailbox);

		final TreeGrid employeeTreeGrid = new TreeGrid();

		mailProcessorservice.call(bean, JSONUtil.ProcessFlowBeanJsonWriter, JSONUtil.ProcessFlowBeanJsonReader, new ServiceCallback<ProcessFlowBean>() {

			@Override
			public void execute(ProcessFlowBean object) {

				employeeTreeGrid.setWidth("99%");
				employeeTreeGrid.setHeight("99%");
				employeeTreeGrid.setShowOpenIcons(false);
				employeeTreeGrid.setCanEdit(true);
				employeeTreeGrid.setShowDropIcons(false);
				employeeTreeGrid.setNodeIcon("operation.png");
				employeeTreeGrid.setFolderIcon("operation.png");
				employeeTreeGrid.setClosedIconSuffix("");
				employeeTreeGrid.setCanReorderRecords(true);
				employeeTreeGrid.setCanAcceptDroppedRecords(true);
				employeeTreeGrid.setCanDragRecordsOut(true);
				employeeTreeGrid.setDragDataAction(DragDataAction.MOVE);
				employeeTreeGrid.setShowConnectors(true);

				TreeGridField name = new TreeGridField("opearationname", "Name");
				TreeGridField description = new TreeGridField("opearationdescription", "Descrizione");
				TreeGridField expression = new TreeGridField("expression", "Espressione");

				// Descrizione non editabile
				description.setCanEdit(false);
				name.setCanEdit(false);

				employeeTreeGrid.setFields(name, description, expression);

				employeeTreeGrid.addEditCompleteHandler(new EditCompleteHandler() {

					@Override
					public void onEditComplete(EditCompleteEvent event) {
						saveflow(employeeTreeGrid, idmailbox);
					}
				});

				addMember(employeeTreeGrid);

				Tree employeeTree = new Tree();
				employeeTree.setModelType(TreeModelType.PARENT);
				employeeTree.setIdField("operationnum");
				employeeTree.setParentIdField("operationnumparent");
				employeeTree.setNameProperty("opearationname");
				employeeTree.setRootValue("1");

				TreeNode[] nodes = new TreeNode[object.getOperation().size()];

				int i = 0;
				for (OperationBean bean : object.getOperation()) {
					nodes[i++] = new EmployeeTreeNode(bean);
				}

				Layout.this.operationtype = object.getOperationtype();

				employeeTree.setData(nodes);
				employeeTree.setRootValue("0");
				employeeTreeGrid.setData(employeeTree);

				Menu context = new Menu();

				MenuItem insert = new MenuItem("Aggiungi Operazione");
				insert.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						ListGridRecord select = employeeTreeGrid.getSelectedRecord();

						WindowAddOperation window = new WindowAddOperation(new TreeNode(select.getJsObj()), employeeTreeGrid, Layout.this.operationtype,
								mailoperationsave, Layout.this, idmailbox);

						window.show();
					}
				});

				context.addItem(insert);

				MenuItem cancel = new MenuItem("Cancella Operazione");
				cancel.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						// System.out.println(event.getTarget());

						OperationBean operationbean = (OperationBean) employeeTreeGrid.getSelectedRecord().getAttributeAsObject("bean");

						mailoperationdelete.call(operationbean, JSONUtil.OperationBeanJsonWriter, JSONUtil.OperationBeanJsonReader,
								new ServiceCallback<OperationBean>() {

									@Override
									public void execute(OperationBean object) {
										employeeTreeGrid.getData().remove(new TreeNode(employeeTreeGrid.getSelectedRecord().getJsObj()));
										saveflow(employeeTreeGrid, idmailbox);
									}
								});

					}
				});
				context.addItem(cancel);

				MenuItem modify = new MenuItem("Modifica Configurazione");
				modify.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						OperationBean operationbean = (OperationBean) employeeTreeGrid.getSelectedRecord().getAttributeAsObject("bean");

						// Dall'operazione recupero il type
						OperationTypeBean actual = null;
						for (OperationTypeBean type : operationtype) {
							if (type.getName().equals(operationbean.getOperationname())) {
								actual = type;
								break;
							}
						}

						WindowModifyConfiguration conf = new WindowModifyConfiguration(actual, operationbean, operationtype, idmailbox);

						conf.show();
					}

				});

				context.addItem(modify);

				employeeTreeGrid.setSelectionType(SelectionStyle.SINGLE);

				employeeTreeGrid.setContextMenu(context);

				employeeTreeGrid.getData().openAll();
			}

		});
	}

	public void saveflow(TreeGrid employeeTreeGrid, String idmailbox) {
		// Salvo il flow in database
		ProcessFlowBean flow = new ProcessFlowBean();
		List<OperationBean> operations = new ArrayList<OperationBean>();
		TreeNode[] nodesall = employeeTreeGrid.getData().getAllNodes();
		for (TreeNode node : nodesall) {
			OperationBean bean = new OperationBean();

			bean.setExpression(node.getAttributeAsString("expression"));
			bean.setOperationdescription(node.getAttributeAsString("opearationdescription"));
			bean.setOperationname(node.getAttributeAsString("opearationname"));
			bean.setOperationnumparent(node.getAttributeAsString("operationnumparent"));
			bean.setOperationnum(node.getAttributeAsString("operationnum"));

			operations.add(bean);
		}
		flow.setOperation(operations);
		flow.setIdmailbox(idmailbox);

		mailProcessorservicesave.call(flow, JSONUtil.ProcessFlowBeanJsonWriter, JSONUtil.ProcessFlowBeanJsonReader, new ServiceCallback<ProcessFlowBean>() {

			@Override
			public void execute(ProcessFlowBean object) {
				// SC.warn("Salvataggio avvenuto con successo");
			}
		});
	}

	public static class EmployeeTreeNode extends TreeNode {

		public EmployeeTreeNode(OperationBean operation) {
			setAttribute("operationnum", operation.getOperationnum());
			setAttribute("operationnumparent", operation.getOperationnumparent());
			setAttribute("opearationname", operation.getOperationname());
			setAttribute("opearationdescription", operation.getOperationdescription());
			setAttribute("expression", operation.getExpression());
			setAttribute("bean", operation);
		}
	}

}
