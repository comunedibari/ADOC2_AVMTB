package it.eng.utility.ui.module.core.server.service;

import java.util.Date;

import com.google.gson.GsonBuilder;

public class GsonBuilderFactory {
	
	public static GsonBuilder getIstance() {
		
		GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter());
		
		return builder;
	}
	
}
