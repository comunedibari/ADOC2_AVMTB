package it.eng.wordOpener.fileProvider.impl;

import it.eng.proxyselector.http.ProxyDefaultHttpClient;
import it.eng.wordOpener.configuration.WordOpenerConfigManager;
import it.eng.wordOpener.exception.InitException;
import it.eng.wordOpener.exception.UnableToRetrieveFileException;
import it.eng.wordOpener.exception.UnableToSaveFileException;
import it.eng.wordOpener.fileProvider.FileProvider;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JApplet;

import netscape.javascript.JSObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

public class AurigaFileProvider implements FileProvider{

	private static Logger mLogger = Logger.getLogger(AurigaFileProvider.class);
	private JApplet applet;
	@Override
	public File getFile() throws UnableToRetrieveFileException {
		String lStringFileUrl = null;
		try {
			lStringFileUrl = WordOpenerConfigManager.getFileUrl();
			String lStrFilename = WordOpenerConfigManager.getFileName();
			mLogger.debug("Il file ha nome " + lStrFilename);
			URL lUrlServlet = new URL(lStringFileUrl);

			URLConnection urlConnection = lUrlServlet.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);	
			byte[] lBytesFile =  IOUtils.toByteArray(urlConnection.getInputStream() );	//leggo lo stream di bytes dalla servlet
			File duplicateFile = File.createTempFile(lStrFilename, "." + WordOpenerConfigManager.getTipoFile().getEstensione());

			FileOutputStream out = FileUtils.openOutputStream(duplicateFile);
			IOUtils.copy(new ByteArrayInputStream(lBytesFile), out);
			out.flush();
			out.close();
			IOUtils.closeQuietly(out);
			return duplicateFile;
		} catch (MalformedURLException e){
			mLogger.error("Errore: " + e.getMessage(), e);
			throw new UnableToRetrieveFileException("Host di recupero " + lStringFileUrl +" non valido", e);
		} catch (InitException e) {
			throw new UnableToRetrieveFileException("Parametro non valorizzato: " + e.getMessage() , e);
		} catch (IOException e) {
			throw new UnableToRetrieveFileException("Errore di IO verso la servlet di recupero: " + e.getMessage(), e);
		}
	}
	
	@Override
	public void saveFileToServer(File pFile) throws UnableToSaveFileException {
		mLogger.debug("Salvo il file " + pFile.getPath()+ " sul server");
		String lStrFilename;
		String estensione;
		CloseableHttpResponse response = null;
		CloseableHttpClient lDefaultHttpClient = null;
		try {
			lStrFilename = WordOpenerConfigManager.getFileName();
			estensione = WordOpenerConfigManager.getTipoFile().getEstensione();
			mLogger.debug("Il file da inviare ha il nome " + lStrFilename);
			URI outputUrl = WordOpenerConfigManager.getOutputURL();
			mLogger.debug("Invio il file a " + outputUrl);
			String callback = WordOpenerConfigManager.getCallback();
			lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
			FileBody lFileBody = new FileBody(pFile);
			StringBody lStringBodyIdSelected = new StringBody(lStrFilename!=null?lStrFilename:"", ContentType.TEXT_PLAIN);
			StringBody lStringBodyEstensione = new StringBody(estensione!=null?estensione:"", ContentType.TEXT_PLAIN);
			RequestConfig config = RequestConfig.custom().build();
			HttpEntity reqEntity = MultipartEntityBuilder.create()
			.addPart("userfile", lFileBody)
			.addPart("idSelected", lStringBodyIdSelected)
			.addPart("estensione", lStringBodyEstensione).build();
			mLogger.debug("idSelected = " + lStrFilename);
			mLogger.debug("estensione = " + estensione);
			HttpPost request = new HttpPost(outputUrl);
			request.setEntity(reqEntity);
			request.setConfig(config);
			String result = null;
			BufferedReader br = null;
			response = lDefaultHttpClient.execute(request);
			System.out.println(response.getStatusLine());
			StringBuilder sb = new StringBuilder();
			String line;
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				if (callback!=null && !callback.equals("")){
					String lStrToInvoke = "javascript:" + callback + "('" + result +"');";
					try {
						JSObject.getWindow(applet).eval( lStrToInvoke );
					} catch (Exception e) {
						throw new UnableToSaveFileException("Browser non supportato: " + e.getMessage(), e);
					}
				}	
			} else {
				throw new UnableToSaveFileException("Errore Fallito: La servlet ha risposto con " + status);
			}
		} catch (InitException e) {
			throw new UnableToSaveFileException("Errore " + e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			throw new UnableToSaveFileException("Errore " + e.getMessage(), e);
		} catch (MalformedURLException e) {
			throw new UnableToSaveFileException("Errore " + e.getMessage(), e);
		} catch (URISyntaxException e) {
			throw new UnableToSaveFileException("Errore " + e.getMessage(), e);
		} catch (ClientProtocolException e) {
			throw new UnableToSaveFileException("Errore " + e.getMessage(), e);
		} catch (IOException e) {
			throw new UnableToSaveFileException("Errore " + e.getMessage(), e);
		} catch (Throwable e) {
			throw new UnableToSaveFileException("Errore " + e.getMessage(), e);	
		} finally {
			try {
				if (response!=null)
					response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void setApplet(JApplet applet) {
		// TODO Auto-generated method stub

	}
}
