package it.eng.auriga.ui.module.layout.client.common.items;

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class SelectItemValoriDizionario extends SelectItem {
	
	public SelectItemValoriDizionario(String name, String title, String dictionaryEntry) {
		this(name, title, dictionaryEntry, false, false);
	}
	
	public SelectItemValoriDizionario(String name, String title, String dictionaryEntry, boolean multiple) {
		this(name, title, dictionaryEntry, multiple, false);
	}
	
	public SelectItemValoriDizionario(String name, String title, String dictionaryEntry, boolean multiple, boolean requiredStrInDes) {
		
		super(name, title);
		
		setOptionDataSource(getLoadComboValoriDizionarioDataSource(dictionaryEntry, multiple, requiredStrInDes));
		setValueField("key");
		setDisplayField("value");
		setMultiple(multiple);
		
		if(!multiple) {
			setDefaultToFirstOption(true);
		}	
	}
	
	public GWTRestDataSource getLoadComboValoriDizionarioDataSource(String dictionaryEntry, boolean multiple, boolean requiredStrInDes) {
		GWTRestDataSource lLoadComboValoriDizionarioDataSource = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		lLoadComboValoriDizionarioDataSource.addParam("dictionaryEntry", dictionaryEntry);
		lLoadComboValoriDizionarioDataSource.addParam("flgSelectMulti", multiple ? "1" : "");
		if(requiredStrInDes) {
			lLoadComboValoriDizionarioDataSource.addParam("requiredStrInDes", "true");
		}
		return lLoadComboValoriDizionarioDataSource;
	}
	
}