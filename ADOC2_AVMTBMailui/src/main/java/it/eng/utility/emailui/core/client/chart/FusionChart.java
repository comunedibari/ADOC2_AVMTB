package it.eng.utility.emailui.core.client.chart;

import java.util.HashMap;

import com.smartgwt.client.widgets.ViewLoader;

public class FusionChart extends ViewLoader {
  
  private static int count = 0;
  
  private String swfId;

  public FusionChart(String src, String width, String height, String dataUrl) {
    super();

    //setCodeBase("http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0");
    //setClassID("clsid:d27cdb6e-ae6d-11cf-96b8-444553540000");
    //setPluginsPage("http://www.macromedia.com/go/getflashplayer");    
    
    swfId = "fusionChartId_" + count;
    ++count;
    
    setID(swfId);
    //setName(swfId);
    //setSrc("charts/fusioncharts/flash/" + src);
    setSize(width, height);
    
    HashMap<String, String> hashMap = new HashMap<String, String>();
    
    hashMap.put("id", swfId);
    hashMap.put("flashvars", "&id=" + swfId + "&chartWidth=" + width + "&chartHeight=" + height + 
        "&registerWithJS=1" + "&debugMode=0" + "&dataURL=" + "charts/fusioncharts/data/" + dataUrl);
    
    
    
    
    // hashMap.put("allowscriptaccess", "always");
    // hashMap.put("bgcolor", "#ffffff");
	// hashMap.put("quality", "high");
    
    // If you embed the chart into your web page, and the page has layers such as drop-down menus or 
    // drop-down forms, and you want them appear above the chart, you need to add the following  line 
    // to your code. 
    // hashMap.put("wmode", "opaque" ); 
    
    //setParams(hashMap);
    
    // setCanSelectText(true);
  }
}
