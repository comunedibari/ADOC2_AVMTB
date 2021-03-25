package it.eng.utility.emailui.core.server.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdvancedCriteria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Object start;
	private Object end;
	private String fieldName;
	private String operator;
	private Object value;
	private List<Criterion> criteria;
		
	public String[] getFieldNames(){
		List<String> fields = new ArrayList<String>();
		if(criteria!=null){
			for(Criterion criterion:criteria){
				if(!fields.contains(criterion.getFieldName())){
					fields.add(criterion.getFieldName());				
				}
			}
		}
		return fields.toArray(new String[0]);
	}
	
	
	/**
	 * Ritorna il criteria in base al nome del Field
	 * @param fieldName
	 * @return
	 */
	public Criterion[] getCriterionByFieldName(String fieldName) {
		List<Criterion> criterion = null;
		if(criteria!=null){
			for(Criterion criteriaone:criteria){
				if(criteriaone.getFieldName()!=null){
					if(criteriaone.getFieldName().equals(fieldName)){
						if(criterion==null){
							criterion = new ArrayList<Criterion>();
						}
						criterion.add(criteriaone);
					}
				}
			}
		}
		
		if(criterion!=null){
			return criterion.toArray(new Criterion[0]);
		}else{
			return null;
		}
	}
	
	
	
	
	/**
	 * Aggiunge un criteria alla lista
	 * @param criterion
	 */
	public void addCriterion(Criterion criterion){
		if(criteria==null){
			criteria = new ArrayList<Criterion>();
		}
		criteria.add(criterion);
	}

	public Object getStart() {
		return start;
	}

	public void setStart(Object start) {
		this.start = start;
	}

	public Object getEnd() {
		return end;
	}

	public void setEnd(Object end) {
		this.end = end;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public List<Criterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<Criterion> criteria) {
		this.criteria = criteria;
	}	
	
	
	
}
