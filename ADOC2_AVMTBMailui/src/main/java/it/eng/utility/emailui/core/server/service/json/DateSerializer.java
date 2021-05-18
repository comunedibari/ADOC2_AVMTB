package it.eng.utility.emailui.core.server.service.json;

import it.eng.utility.emailui.core.server.service.DataSourceService;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateSerializer implements JsonSerializer<Date> {

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		if(src!=null){
			return new JsonPrimitive(DataSourceService.datetime_format.format(src));
		}else{
			return null;
		}
	}
	
}
