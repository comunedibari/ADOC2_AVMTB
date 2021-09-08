package it.eng.testrest;

import java.util.List;

public class RestResultBean {

	private List<UserBean> data;
	private int start;
	private int end;
	
	public List<UserBean> getData() {
		return data;
	}
	public void setData(List<UserBean> data) {
		this.data = data;
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
	
}
