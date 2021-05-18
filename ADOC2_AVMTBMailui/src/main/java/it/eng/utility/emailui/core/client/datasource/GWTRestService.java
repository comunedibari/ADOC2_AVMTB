package it.eng.utility.emailui.core.client.datasource;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.JSON;

import it.eng.utility.emailui.core.client.callback.ServiceCallback;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

public class GWTRestService<E, T> extends GWTRestDataSource {

	public GWTRestService(String serverid) {
		super(serverid, "key", FieldType.TEXT);
	}

	public void call(E object, JsonWriter<E> jsonizer) {
		Record record = new Record(JSON.decode(jsonizer.toJson(object)));
		this.addData(record);
	}

	public void call(E object, JsonWriter<E> jsonizerWriter, final JsonReader<T> jsonizerReader, final ServiceCallback<T> callback) {
		Record record = new Record(JSON.decode(jsonizerWriter.toJson(object)));
		this.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				// Recupero il dato
				try {
					Record record = response.getData()[0];
					// System.out.println(JSON.encode((JavaScriptObject)rawData));
					T ret = jsonizerReader.read(JSON.encode(record.getJsObj()));
					callback.execute(ret);
				} catch (Exception e) {
					// callback.execute(null);
				}
			}
		});
	}
}