package it.eng.utility.emailui.core.server.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaginatorBean<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<T> data = new ArrayList<T>();
	private Integer startRow;
	private Integer endRow;
	private Integer totalRows;
	
	public void addRecord(T record){
		data.add(record);
	}
	
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getEndRow() {
		return endRow;
	}
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
}