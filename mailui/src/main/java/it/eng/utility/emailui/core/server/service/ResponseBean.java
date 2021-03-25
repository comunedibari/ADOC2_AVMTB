package it.eng.utility.emailui.core.server.service;

import it.eng.utility.emailui.core.shared.message.MessageBean;

import java.util.List;

public class ResponseBean {

	private Integer status;
    private Integer startRow;
    private Integer endRow;
    private Integer totalRows;
    private List<?> data;
    private List<MessageBean> messages;
//    private ContextBean context;
//    	
//	public ContextBean getContext() {
//		return context;
//	}
//	public void setContext(ContextBean context) {
//		this.context = context;
//	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public List<MessageBean> getMessages() {
		return messages;
	}
	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}
}