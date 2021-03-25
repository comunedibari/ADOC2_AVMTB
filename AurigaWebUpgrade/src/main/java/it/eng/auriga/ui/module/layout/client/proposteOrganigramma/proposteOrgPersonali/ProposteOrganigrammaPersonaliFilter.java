package it.eng.auriga.ui.module.layout.client.proposteOrganigramma.proposteOrgPersonali;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FilterClause;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.SelectItemFiltrabile;
import it.eng.utility.ui.module.layout.client.common.filter.item.ScadenzaItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;

public class ProposteOrganigrammaPersonaliFilter extends ConfigurableFilter {

	private String lista;
	private Map<String, List<String>> toClear;

	public ProposteOrganigrammaPersonaliFilter(String lista) {
		super(lista);
		setWidth(750);
//		this.lista = lista;
//		toClear = new HashMap<String, List<String>>();
//		for (FilterFieldBean lFilterFieldBeanToFind : Layout.getFilterConfig(lista).getFields()){
//			List<String> lListClear = new ArrayList<String>();
//			for (FilterFieldBean lFilterFieldBean : Layout.getFilterConfig(lista).getFields()){
//				if (lFilterFieldBean.getDependsFrom()!=null && lFilterFieldBean.getDependsFrom().contains(lFilterFieldBeanToFind.getName())){
//					lListClear.add(lFilterFieldBean.getName());
//				}
//			};
//			if (lListClear!=null && lListClear.size()>0)
//				toClear.put(lFilterFieldBeanToFind.getName(), lListClear);
//		}
	}

	@Override
	public void createFilteredSelectItem(final FilterFieldBean pFilterFieldBean, DataSourceField pDataSourceField) {
		SelectItemFiltrabile lSelectItem = new SelectItemFiltrabile(filter, pFilterFieldBean, pDataSourceField);
		final List<String> lListClear =  new ArrayList<String>();
		for (FilterFieldBean lFilterFieldBean : Layout.getFilterConfig("proposte_organigramma_personali").getFields()){
			if (lFilterFieldBean.getDependsFrom()!=null && lFilterFieldBean.getDependsFrom().contains(pFilterFieldBean.getName())){
				lListClear.add(lFilterFieldBean.getName());
			}
		};
		if (lListClear!=null && lListClear.size()>0){
			lSelectItem.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					for (String lString : lListClear){
						int position = getClausePosition(lString);
						if (position != -1){
							FormItem lClauseValueItem = (FormItem) getClauseValueItem(position);										
							SelectItem lClauseValueSelectItem = (SelectItem) lClauseValueItem;
							lClauseValueSelectItem.clearValue();
						}
					}
					if (pFilterFieldBean.getName().equals("idProcessType")){
						int position = getClausePosition("scadenza");
						if (position != -1){
							FormItem lClauseValueItem = (FormItem) getClauseValueItem(position);
							ScadenzaItem lClauseValueSelectItem = (ScadenzaItem) lClauseValueItem;
							DynamicForm lDynamicForm = (DynamicForm) lClauseValueSelectItem.getCanvas();
							SelectItem lSelectItem = (SelectItem) lDynamicForm.getFields()[0];
							lSelectItem.clearValue();
						}
					}
				}
			});
		} 
		pDataSourceField.setEditorType(lSelectItem);
		pDataSourceField.setFilterEditorType(SelectItem.class);
		mappaSelects.put(pFilterFieldBean.getName(), lSelectItem);
	}

	protected void manageRemoveClick(ClickEvent event, FilterClause lFilterClause) {
		filter.removeClause(lFilterClause);
		int positionIdProcessType = getClausePosition("idProcessType");	
		int positionInFase = getClausePosition("inFase");	
		//		int positionEseguibileTask = getClausePosition("eseguibileTask");	
		//		if (positionIdProcessType == -1){
		//			if (positionInFase != -1)
		//				removeClause(getClause(positionInFase));
		//			if (positionEseguibileTask != -1)
		//				removeClause(getClause(positionEseguibileTask));
		//		}
		//		if (positionInFase == -1){
		//			if (positionEseguibileTask != -1)
		//				removeClause(getClause(positionEseguibileTask));
		//		}

		if (positionIdProcessType == -1){
			final List<String> lListClear =  new ArrayList<String>();
			for (FilterFieldBean lFilterFieldBean : Layout.getFilterConfig("proposte_organigramma_personali").getFields()){
				if (lFilterFieldBean.getDependsFrom()!=null && lFilterFieldBean.getDependsFrom().contains("idProcessType")){
					lListClear.add(lFilterFieldBean.getName());
				}
			};
			if (lListClear!=null && lListClear.size()>0){
				for (String lString : lListClear){
					int position = getClausePosition(lString);
					if (position != -1){
						FormItem lClauseValueItem = (FormItem) getClauseValueItem(position);										
						SelectItem lClauseValueSelectItem = (SelectItem) lClauseValueItem;
						lClauseValueSelectItem.clearValue();
						lClauseValueSelectItem.redraw();
					}
				}
			}
		}

		if (positionInFase == -1){
			final List<String> lListClear =  new ArrayList<String>();
			for (FilterFieldBean lFilterFieldBean : Layout.getFilterConfig("proposte_organigramma_personali").getFields()){
				if (lFilterFieldBean.getDependsFrom()!=null && lFilterFieldBean.getDependsFrom().contains("inFase")){
					lListClear.add(lFilterFieldBean.getName());
				}
			};
			if (lListClear!=null && lListClear.size()>0){
				for (String lString : lListClear){
					int position = getClausePosition(lString);
					if (position != -1){
						FormItem lClauseValueItem = (FormItem) getClauseValueItem(position);										
						SelectItem lClauseValueSelectItem = (SelectItem) lClauseValueItem;
						lClauseValueSelectItem.clearValue();
						lClauseValueSelectItem.redraw();
					}
				}
			}
		}

	}

}
