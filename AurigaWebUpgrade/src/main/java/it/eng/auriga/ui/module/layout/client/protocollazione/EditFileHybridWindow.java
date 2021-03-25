package it.eng.auriga.ui.module.layout.client.protocollazione;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSON;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.applet.HybridEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

public class EditFileHybridWindow extends HybridWindow {

	protected EditFileHybridWindow _window;

	// public abstract void firmaCallBack(String nomeFileFirmato, String uriFileFirmato, String record);

	private String nomeFile;
	private String uriFile;
	private HybridEng mHybridEng;

	private OpenEditorCallback returnCallback;

	public EditFileHybridWindow(String display, String uri, Boolean remoteUri, String estensione, String impronta, OpenEditorCallback returnCallback) {
		super(I18NUtil.getMessages().protocollazione_editWindow_title(), "EditApplet.jar");
		this.returnCallback = returnCallback;
		_window = this;
		Map<String, String> lMapParams = new HashMap<String, String>();
		lMapParams.put("fileName", display);
		String url = null;
		if (remoteUri != null && remoteUri) {
			url = "fromRecord=false&recordType=" + DownloadFile.encodeURL("RemoteExtractor") + "&url=" + URL.encode(uri);
		} else {
			url = "fromRecord=false&url=" + URL.encode(uri);
		}
		lMapParams.put("fileUrl", "http://localhost:8080/SignerMulti-HybridProject/index.html");
		lMapParams.put("fileUrl", GWT.getHostPageBaseURL() + "springdispatcher/download?" + url);
		lMapParams.put("outputUrl", GWT.getHostPageBaseURL() + "springdispatcher/UploadEditApplet/");
		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		lMapParams.put("tipoFile", estensione);
		lMapParams.put("impronta", impronta);
		lMapParams.put("tipoImpronta", AurigaLayout.getAlgoritmoImpronta());
		lMapParams.put("tipoEncoding", AurigaLayout.getEncoding());

		final HybridEng lHybridEng = new HybridEng("EditApplet", "SignerApplet.jnlp", lMapParams, 200, 100, false, false, this,
				new String[] { "EditApplet.jar" }, "it.eng.wordOpener.applet.WordOpenerApplet") {

			@Override
			public void uploadFromServlet(String file) {
				uploadFirmaEndCallback(file);
			}

			@Override
			protected void uploadAfterDatasourceCall(Record object) {
				Record lRecord = object.getAttributeAsRecord("mimeTypeFirmaBean");
				String record = JSON.encode(lRecord.getJsObj());
				firmaCallBack(nomeFile, uriFile, record);
			}

			@Override
			protected Record getRecordForAppletDataSource() {
				return getRecordFirmaForAppletDataSource();
			}

			@Override
			public void cancelUpload() {
				super.cancelUpload();
				nothingToSave();
			}

			@Override
			protected void startHybrid(JavaScriptObject jsParams) {
				callHybrid(jsParams);
			}
		};
		mHybridEng = lHybridEng;
	}

	public static native void callHybrid(JavaScriptObject lMapParams) /*-{
		$wnd.doWordOpener(lMapParams);
	}-*/;

	protected void nothingToSave() {
		mHybridEng.realCloseClick(this);
	}

	public void uploadFirmaEndCallback(String file) {
		// Estraggo il nome + uri del file Firmato
		String[] result = file.split("#");
		nomeFile = result[0];
		uriFile = result[1];
		mHybridEng.uploadDone = true;
		mHybridEng.realCloseClick(this);
	}

	protected Record getRecordFirmaForAppletDataSource() {
		Record lRecord = new Record();
		lRecord.setAttribute("uri", uriFile);
		lRecord.setAttribute("fileName", nomeFile);
		lRecord.setAttribute("provenienza", appletJarName);
		return lRecord;
	}

	public void firmaCallBack(String nomeFileFirmato, String uriFileFirmato, String record) {
		if (returnCallback != null) {
			returnCallback.execute(nomeFileFirmato, uriFileFirmato, record);
		}
	}
}
