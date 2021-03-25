package it.eng.utility.ui.module.layout.client.error;

import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;

public class StackTraceDetail extends Dialog{
	
	private StackTraceDetail stackTraceDetail;
	private HTMLPane errorWindowStackTrace;
	
	public StackTraceDetail(){
		
		setWidth(600);
		setHeight(400);
		setTitle("StackTrace");
		setAutoDraw(false);
		setAutoCenter(true);
		setAutoSize(true);
		setShowFooter(false);
		setShowToolbar(false);
		setShowStatusBar(false);
		stackTraceDetail = this;
		addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				stackTraceDetail.hide();
			}
		});
		errorWindowStackTrace = new HTMLPane();
		errorWindowStackTrace.setWidth100();
		errorWindowStackTrace.setHeight(400);
		errorWindowStackTrace.setContents("");
		addItem(errorWindowStackTrace);
	}

	public HTMLPane getErrorWindowStackTrace() {
		return errorWindowStackTrace;
	}

	public void setErrorWindowStackTrace(HTMLPane errorWindowStackTrace) {
		this.errorWindowStackTrace = errorWindowStackTrace;
	}

}
