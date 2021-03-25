package it.eng.areas.hybrid.module.cryptoLight.outputFileProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

public class AurigaMultiHashFileOutputProvider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger(AurigaMultiHashFileOutputProvider.class);

	private String outputUrl;
	private String idSelected;
	private String callBack;
	private String callBackStart;

	private boolean autoClosePostSign = false;
	private String callBackAskForClose = null;

	private String callBackResult = null;

	@Override
	public boolean saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, String... params) throws Exception {
		logger.info("Metodo saveOutputFile");
		callBackResult = null;
		boolean operationSuccessed = true;
		if (getOutputUrl() != null) {
			String jSessionId = PreferenceManager.getString(PreferenceKeys.PROPERTY_JSESSIONID);
			URI uri;
			String domain = "";
			try {
				String uploadUrl = PreferenceManager.getString(PreferenceKeys.PROPERTY_OUTPUTURL);
				uri = new URI(uploadUrl);
				domain = uri.getHost();
				domain =  domain.startsWith("www.") ? domain.substring(4) : domain;
			} catch (URISyntaxException e1) {
				logger.error("Errore nel recupero del dominio", e1);
			}
			if (getCallBackStart() != null) {
			}
			CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse(jSessionId, domain, "/");
			CloseableHttpResponse response = null;
			String lStringOriginalFileName = fileInputName;
			File lFileOut;

			try {
				HttpPost request = new HttpPost(getOutputUrl());
				RequestConfig config = RequestConfig.custom().build();
				lFileOut = File.createTempFile("fileTemp", ".tmp");
				logger.info("idDoc " + params[0]);

				List<NameValuePair> lListParames = new ArrayList<NameValuePair>();
				if (params[0] != null) {
					logger.info("idDoc " + params[0]);
					lListParames.add(new BasicNameValuePair("idDoc", params[0]));
				}
				if (params[1] != null) {
					lListParames.add(new BasicNameValuePair("signedBean", params[1]));
				}
				if (params[2] != null) {
					logger.info("firmatario " + params[2]);
					lListParames.add(new BasicNameValuePair("firmatario", params[2]));
				}
				if (params[3] != null) {
					logger.info("pathFileTemp " + params[3]);
					lListParames.add(new BasicNameValuePair("pathFileTemp", params[3]));
				}
				if (params[4] != null) {
					logger.info("versioneDoc " + params[4]);
					lListParames.add(new BasicNameValuePair("versioneDoc", params[4]));
				}
				if (params[5] != null) {
					logger.info("firmaPresente " + params[5]);
					lListParames.add(new BasicNameValuePair("firmaPresente", params[5]));
				}
				if (params[6] != null) {
					logger.info("firmaValida " + params[6]);
					lListParames.add(new BasicNameValuePair("firmaValida", params[6]));
				}
				if (params[7] != null) {
					logger.info("modalitaFirma " + params[7]);
					lListParames.add(new BasicNameValuePair("modalitaFirma", params[7]));
				}

				HttpUriRequest upload = RequestBuilder.post().setUri(new URI(getOutputUrl())).addParameters(lListParames.toArray(new NameValuePair[] {}))
						.build();

				String result = null;
				BufferedReader br = null;
				response = lDefaultHttpClient.execute(upload);
				logger.info(response.getStatusLine());
				StringBuilder sb = new StringBuilder();
				String line;
				br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				result = sb.toString();
				if (response.getStatusLine().getStatusCode() != 200) {
					operationSuccessed = false;
					throw new IOException("Il server ha risposto: " + response.getStatusLine() + ": " + result);
				}
				if (callBack != null && !callBack.equals("")) {
					callBackResult = result;
				}
			} catch (Exception e) {
				logger.error("Errore", e);
			} finally {
				if (response != null) {
					response.close();
				}
			}
		} else {
			operationSuccessed = false;
			logger.error("OutputUrl null");
		}
		return operationSuccessed;
	}

	@Override
	public void saveOutputParameter() throws Exception {
		String urlOutput = null;
		try {
			urlOutput = PreferenceManager.getString(PreferenceKeys.PROPERTY_OUTPUTURL);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl(urlOutput);

			idSelected = PreferenceManager.getString(PreferenceKeys.PROPERTY_IDSELECTED);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_IDSELECTED + ": " + idSelected);
			setIdSelected(idSelected);

			callBack = PreferenceManager.getString(PreferenceKeys.PROPERTY_CALLBACK);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACK + ": " + callBack);
			setCallBack(callBack);

			callBackStart = PreferenceManager.getString(PreferenceKeys.PROPERTY_CALLBACK_START);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACK_START + ": " + callBackStart);
			setCallBackStart(callBackStart);

			String autoClosePostSignString = PreferenceManager.getString(PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
			if (autoClosePostSignString != null)
				autoClosePostSign = Boolean.valueOf(autoClosePostSignString);

			callBackAskForClose = PreferenceManager.getString(PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public String getOutputUrl() {
		return outputUrl;
	}

	public void setOutputUrl(String outputUrl) {
		this.outputUrl = outputUrl;
	}

	public String getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(String idSelected) {
		this.idSelected = idSelected;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public void setCallBackStart(String callBackStart) {
		this.callBackStart = callBackStart;
	}

	public String getCallBackStart() {
		return callBackStart;
	}

	@Override
	public boolean getAutoClosePostSign() {
		return autoClosePostSign;
	}

	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}

	@Override
	public String getCallbackResult() {
		return callBackResult;
	}

	@Override
	public String getCallback() {
		return this.callBack;
	}

}
