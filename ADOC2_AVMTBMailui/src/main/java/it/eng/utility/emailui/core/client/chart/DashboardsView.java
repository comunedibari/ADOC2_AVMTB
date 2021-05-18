package it.eng.utility.emailui.core.client.chart;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DashboardsView extends VLayout {

private static final String CONTEXT_AREA_WIDTH = "*";

private VLayout panel;

	public DashboardsView() {
	  super();
		  
	  setStyleName("crm-ContextArea");
	  setWidth(CONTEXT_AREA_WIDTH);
	  
	  setOverflow(Overflow.AUTO);
	  
	  drawFusionCharts(this);
	  setWidth100();
	  setHeight100();
	  draw();
	}

	private void drawFusionCharts(VLayout panel) {
	  
	  FusionChart chart1 = new FusionChart("FCF_StackedColumn2D.swf", "400", "350", "StCol2D.xml"); 
	     
	  FusionChart chart2 = new FusionChart("FCF_StackedBar2D.swf", "400", "350", "StBar2D.xml");  
		
	  FusionChart chart3 = new FusionChart("FCF_Doughnut2D.swf", "400", "350", "Doughnut2D.xml"); 
	  
	  FusionChart chart4 = new FusionChart("FCF_Funnel.swf", "350", "300", "Funnel.xml"); 
	  
	  //chart4.
	  
	  
	  HLayout northLayout = new HLayout();
	  northLayout.setHeight("50%");
	  northLayout.setBackgroundColor("#FFFFFF");
	  
	  northLayout.addMember(chart1);
	  northLayout.addMember(chart2);    
	  
	  HLayout southLayout = new HLayout();
	  southLayout.setHeight("50%");    
	  southLayout.setBackgroundColor("#FFFFFF");
	
	  southLayout.addMember(chart3);
	  southLayout.addMember(chart4);
	  
	  panel.addMember(northLayout);
	  panel.addMember(southLayout);
	}

	@Override
	public Widget asWidget() {
	  return panel;
	} 


}
