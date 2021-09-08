package it.eng.utility.emailui.core.server.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.emailui.core.server.service.DataSourceService;

public class Criterion implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object start;
	private Object end;
	private String fieldName;
	private String operator;
	private Object value;
	private FieldType type;
	private List<Criterion> criteria;

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public Integer getValueAsInteger() throws Exception {
		if (value != null) {
			if (value instanceof Integer) {
				return (Integer) value;
			} else if (value instanceof Number) {
				return ((Number) value).intValue();
			} else {
				try {
					return Integer.decode(value.toString());
				} catch (Exception e) {
					throw new Exception("Impossibile convertire il valore " + value + " in Integer");
				}
			}
		} else {
			return null;
		}
	}

	public Long getValueAsLong() throws Exception {
		if (value != null) {
			if (value instanceof Long) {
				return (Long) value;
			} else if (value instanceof Number) {
				return ((Number) value).longValue();
			} else {
				try {
					return Long.decode(value.toString());
				} catch (Exception e) {
					throw new Exception("Impossibile convertire il valore " + value + " in Long");
				}
			}
		} else {
			return null;
		}
	}

	public Date getValueAsDate() throws Exception {
		if (value != null) {
			if (value instanceof Date) {
				return (Date) value;
			} else {
				// Tento di convetite nei vari formati
				try {
					return DataSourceService.date_format.parse(value.toString());
				} catch (Exception e) {
					try {
						return DataSourceService.datetime_format.parse(value.toString());
					} catch (Exception e1) {
						try {
							return DataSourceService.time_format.parse(value.toString());
						} catch (Exception e2) {
							throw new Exception("Impossibile convertire il valore " + value + " in Date");
						}
					}
				}
			}
		} else {
			return null;
		}
	}

	public Boolean getValueAsBoolean() throws Exception {
		if (value != null) {
			if (value instanceof Boolean) {
				return (Boolean) value;
			} else if (value instanceof String) {
				try {
					return Boolean.valueOf((String) value);
				} catch (Exception e) {
					throw new Exception("Impossibile convertire il valore " + value + " in Boolean");
				}
			} else {
				throw new Exception("Impossibile convertire il valore " + value + " in Boolean");
			}
		} else {
			return null;
		}
	}

	public BigDecimal getValueAsBigDecimal() throws Exception {
		if (value != null) {
			if (value instanceof BigDecimal) {
				return (BigDecimal) value;
			} else if (value instanceof Number) {
				return new BigDecimal(((Number) value).longValue());
			} else {
				try {
					return new BigDecimal(value.toString());
				} catch (Exception e) {
					throw new Exception("Impossibile convertire il valore " + value + " in BigDecimal");
				}
			}
		} else {
			return null;
		}
	}

	public String getValueAsString() {
		return value.toString();
	}

	/**
	 * Aggiunge un criteria alla lista
	 * 
	 * @param criterion
	 */
	public void addCriterion(Criterion criterion) {
		if (criteria == null) {
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
