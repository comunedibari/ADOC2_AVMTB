package it.eng.utility.emailui.core.server.datasource;

import it.eng.utility.emailui.core.server.datasource.annotation.Datasource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

public class SingletonDataSource {

	private static SingletonDataSource instance = new SingletonDataSource();
	
	private SingletonDataSource(){}
	
	public static SingletonDataSource getInstance(){
		return instance;
	}
	
	public void initialize(){
		Reflections reflections = new Reflections("it.eng.utility.emailui");
		Set<Class<?>> datasourcesclasses = reflections.getTypesAnnotatedWith(Datasource.class);
		Iterator<Class<?>> iteratore = datasourcesclasses.iterator();
		while(iteratore.hasNext()){
			Class classe = iteratore.next();
			datasources.put(((Datasource)classe.getAnnotation(Datasource.class)).id(),classe);
		}
	}
	
	
	private Map<String,Class> datasources = new HashMap<String,Class>();

	public Map<String, Class> getDatasources() {
		
		//Se è vuoto lo inizializzo
		return datasources;
	}

	public void setDatasources(Map<String, Class> datasources) {
		this.datasources = datasources;
	}	
}