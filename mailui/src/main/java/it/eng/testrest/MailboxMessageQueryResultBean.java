package it.eng.testrest;

import java.util.List;

public class MailboxMessageQueryResultBean extends GenericOperationBean {

	private List<MailboxMessageRestBean> data;
	private int start;
	private int end;
	
	public MailboxMessageQueryResultBean(List<MailboxMessageRestBean> pListData){
		data = pListData;
		start = 0;
		end = pListData.size();
	}
	
	public MailboxMessageQueryResultBean() {
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}

	public List<MailboxMessageRestBean> getData() {
		return data;
	}

	public void setData(List<MailboxMessageRestBean> data) {
		this.data = data;
	}
}
