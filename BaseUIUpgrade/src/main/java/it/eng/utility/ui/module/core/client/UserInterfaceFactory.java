package it.eng.utility.ui.module.core.client;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.FormItem;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.util.ClientFactory;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterLookupType;
import it.eng.utility.ui.module.layout.shared.bean.GetListaDefPrefsBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;

public class UserInterfaceFactory {

	private static UserInterfaceConfig config;
	
	public static void configure(UserInterfaceConfig config) {
		UserInterfaceFactory.config = config;
	}
			
	public static Canvas getPortletLayout(String nomeEntita, HashMap<String, String> params) {
		return config.getPortletLayout(nomeEntita, params);			
	}
	
	public static String getPrefKeyPrefix() {
		return config.getPrefKeyPrefix();	
	}
	
	public static GWTRestDataSource getPreferenceDataSource() {
		return config.getPreferenceDataSource();	
	}
	
	public static GWTRestService<GetListaDefPrefsBean, GetListaDefPrefsBean> getListaDefPrefsService() {
		return config.getListaDefPrefsService();	
	}
	
	public static GWTRestService<LoginBean, MenuBean> getServiceRestMenu() {
		return config.getServiceRestMenu();	
	}

	public static void onClickLookupFilterLookupButton(FilterLookupType type, ServiceCallback<Record> callback) {
		config.onClickLookupFilterLookupButton(type, callback);	
	}
	
	public static void onClickLookupButtonWithFilters(FilterLookupType type, Record filters, Map<String, Object> extraparams, ServiceCallback<Record> callback) {
		config.onClickLookupButtonWithFilters(type, filters, extraparams, callback);	
	}
	
	public static void onClickLookupFilterDetailButton(FilterLookupType type, String id, String descrizione) {
		config.onClickLookupFilterDetailButton(type, id, descrizione);	
	}
		
	public static FormItem buildCustomFilterEditorType(FilterFieldBean filterFieldBean) {
		return config.buildCustomFilterEditorType(filterFieldBean);	
	}
	
	public static String getParametroDB(String paramName) {
		return config.getParametroDB(paramName);	
	}
	
	public static Boolean getParametroDBAsBoolean(String paramName) {
		return config.getParametroDBAsBoolean(paramName);	
	}
	
	public static Boolean isAbilToExportList() {
		return config.isAbilToExportList();	
	}
	
	public static ClientFactory getClientFactory() {
		return config.getClientFactory();
	}
	
	public static boolean isAttivaAccessibilita() {
//		return config.isAttivaAccessibilita();
		
		return config!=null && config.isAttivaAccessibilita();
	}
	
	public static void initIsAttivaAccessibilita() {
		config.initIsAttivaAccessibilita();
	}
		
}
