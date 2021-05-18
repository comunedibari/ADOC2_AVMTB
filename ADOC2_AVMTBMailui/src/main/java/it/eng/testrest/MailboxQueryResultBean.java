package it.eng.testrest;

import java.util.List;

public class MailboxQueryResultBean extends GenericOperationBean {
	private List<MailboxRestBean> data;
	private int start;
	private int end;
	
	public MailboxQueryResultBean(List<MailboxRestBean> pListData){
		data = pListData;
		start = 0;
		end = pListData.size();
	}
	
	public MailboxQueryResultBean() {
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

	public List<MailboxRestBean> getData() {
		return data;
	}

	public void setData(List<MailboxRestBean> data) {
		this.data = data;
	}

}
