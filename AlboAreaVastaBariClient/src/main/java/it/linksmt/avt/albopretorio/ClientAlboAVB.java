package it.linksmt.avt.albopretorio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisProperties;
import org.apache.log4j.Logger;

import it.linksmt.avt.albopretorio.util.Util;
import it.linksmt.avt.albopretorio.RichiestaSOAP.DettaglioAttoIn;
import it.linksmt.avt.albopretorio.data.Output;
import it.linksmt.avt.albopretorio.ws.server.DettaglioAttoResponseReturn;
import it.linksmt.avt.albopretorio.ws.server.ElencoAttiElencoAttiIn;
import it.linksmt.avt.albopretorio.ws.server.ElencoAttiResponseReturn;
import it.linksmt.avt.albopretorio.ws.server.ElencoTipiAttoResponseReturn;
import it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEntePubblicaAttoByEnteIn;
import it.linksmt.avt.albopretorio.ws.server.PubblicaAttoByEnteResponseReturn;
import it.linksmt.avt.albopretorio.ws.server.PubblicaAttoPubblicaAttoIn;
import it.linksmt.avt.albopretorio.ws.server.PubblicaAttoResponseReturn;
import it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoResponseReturn;
import it.linksmt.avt.albopretorio.ws.server.SalvaAllegatoSalvaAllegatoIn;
import it.linksmt.avt.albopretorio.ws.server.impl.GestioneAttoServerImplPortBindingStub;
import it.linksmt.avt.albopretorio.ws.server.impl.GestioneAttoServerImplServiceLocator;

public class ClientAlboAVB {
	
	private static final Logger logger = Logger.getLogger(ClientAlboAVB.class);
	private static final int DEFAULT_TIMEOUT = 60000;
	private static final String CONFIG_FILE = "client-alboreggio.properties";
	private static final String KEY_ENDPOINT = "alboavb.endpoint";
	private static final String KEY_TIMEOUT = "alboavb.timeout";
	private static final String KEY_USERNAME = "alboavb.username";
	private static final String KEY_PASSWORD = "alboavb.password";
	private static final String KEY_PROXY_SERVER = "alboavb.proxy.server";
	private static final String KEY_PROXY_PORT = "alboavb.proxy.port";
	private static final String LOG_ERROR_MESSAGE = "Errore di comunicazione";
	private static String username;
	private static String password;
	private GestioneAttoServerImplServiceLocator service;
	private Properties properties;
	private int timeout = DEFAULT_TIMEOUT;

	public void init() throws IOException {
		properties = retrieveProperties(CONFIG_FILE);
		if (logger.isDebugEnabled())
			logger.debug("CLIENT CONFIGURATION\n" + properties);
		service = createService(properties.getProperty(KEY_ENDPOINT));
//		setProxy(properties.getProperty(KEY_PROXY_SERVER), properties.getProperty(KEY_PROXY_PORT));
		username = properties.getProperty(KEY_USERNAME);
		password = properties.getProperty(KEY_PASSWORD);
		timeout = getTimeout();
	}
	
	public Output<SalvaAllegatoResponseReturn> salvaAllegato (SalvaAllegatoSalvaAllegatoIn salvaAllegatoIn) {
		logger.info("salvaAllegato() start.");
		final long t0 = System.currentTimeMillis();
		final Output<SalvaAllegatoResponseReturn> output = new Output<>();
		SalvaAllegatoResponseReturn resp = null;
		try {
			resp = getStub().salvaAllegato(salvaAllegatoIn);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setResult(resp);
		// Capire quando la chiamata è ok
		if(resp.getError().getIdAtto()!=null && "".equalsIgnoreCase(resp.getError().getIdAtto())) {
			output.setOk(true);
		}
		// output.setOk(true);
		final long t1 = System.currentTimeMillis();
		logger.info("salvaAllegato() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<PubblicaAttoResponseReturn> pubblicaAtto(PubblicaAttoPubblicaAttoIn pubblicaAttoIn) {
		logger.info("pubblicaAtto() start.");
		final long t0 = System.currentTimeMillis();
		final Output<PubblicaAttoResponseReturn> output = new Output<>();
		PubblicaAttoResponseReturn resp = null;
		try {
			resp = getStub().pubblicaAtto(pubblicaAttoIn);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setResult(resp);
		// Capire quando la chiamata è ok
		if(resp.getError().getIdAtto()!=null && "".equalsIgnoreCase(resp.getError().getIdAtto())) {
			output.setOk(true);
		}
		// output.setOk(true);
		final long t1 = System.currentTimeMillis();
		logger.info("pubblicaAtto() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<ElencoTipiAttoResponseReturn> elencoTipiAtto() {
		logger.info("elencoTipiAtto() start.");
		final long t0 = System.currentTimeMillis();
		final Output<ElencoTipiAttoResponseReturn> output = new Output<>();
		ElencoTipiAttoResponseReturn resp = null;
		try {
			resp = getStub().elencoTipiAtto();
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setResult(resp);
		// Capire quando la chiamata è ok
		if(resp.getError().getIdAtto()!=null && "".equalsIgnoreCase(resp.getError().getIdAtto())) {
			output.setOk(true);
		}
		// output.setOk(true);
		final long t1 = System.currentTimeMillis();
		logger.info("elencoTipiAtto() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<PubblicaAttoByEnteResponseReturn> pubblicaAttoByEnte(PubblicaAttoByEntePubblicaAttoByEnteIn pubblicaAttoByEnteIn) {
		logger.info("pubblicaAttoByEnte() start.");
		final long t0 = System.currentTimeMillis();
		final Output<PubblicaAttoByEnteResponseReturn> output = new Output<>();
		PubblicaAttoByEnteResponseReturn resp = null;
		try {
			resp = getStub().pubblicaAttoByEnte(pubblicaAttoByEnteIn);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setResult(resp);
		// Capire quando la chiamata è ok
		if(resp.getError().getIdAtto()!=null && "".equalsIgnoreCase(resp.getError().getIdAtto())) {
			output.setOk(true);
		}
		// output.setOk(true);
		final long t1 = System.currentTimeMillis();
		logger.info("pubblicaAttoByEnte() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<DettaglioAttoResponseReturn> dettaglioAtto(DettaglioAttoIn dettaglioAttoIn) {
		logger.info("dettaglioAtto() start.");
		final long t0 = System.currentTimeMillis();
		final Output<DettaglioAttoResponseReturn> output = new Output<>();
		DettaglioAttoResponseReturn resp = null;
		try {
			resp = getStub().dettaglioAtto(dettaglioAttoIn);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setResult(resp);
		// Capire quando la chiamata è ok
		if(resp.getError().getIdAtto()!=null && "".equalsIgnoreCase(resp.getError().getIdAtto())) {
			output.setOk(true);
		}
		// output.setOk(true);
		final long t1 = System.currentTimeMillis();
		logger.info("dettaglioAtto() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<ElencoAttiResponseReturn> elencoAtti(ElencoAttiElencoAttiIn elencoAttiIn) {
		logger.info("elencoAtti() start.");
		final long t0 = System.currentTimeMillis();
		final Output<ElencoAttiResponseReturn> output = new Output<>();
		ElencoAttiResponseReturn resp = null;
		try {
			resp = getStub().elencoAtti(elencoAttiIn);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setResult(resp);
		// Capire quando la chiamata è ok
		if(resp.getError().getIdAtto()!=null && "".equalsIgnoreCase(resp.getError().getIdAtto())) {
			output.setOk(true);
		}
		// output.setOk(true);
		final long t1 = System.currentTimeMillis();
		logger.info("elencoAtti() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	private Properties retrieveProperties(String fileName) throws IOException {
		// try (InputStream input = new FileInputStream("application.properties")) {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
			if (input == null) {
				throw new FileNotFoundException("File di configurazione '" + fileName + "' non recuperato.");
			}
			Properties properties = new Properties();
			properties.load(input);
			return properties;
		}
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setProxy(String server, String port) {
		AxisProperties.setProperty("http.proxyHost", server);
		AxisProperties.setProperty("http.proxyPort", port);
	}

	private GestioneAttoServerImplServiceLocator createService(String address) {
		final GestioneAttoServerImplServiceLocator sl = new GestioneAttoServerImplServiceLocator();
		sl.setGestioneAttoServerImplPortEndpointAddress(address);
		// sl.setProtocolloWebServiceWSDDServiceName(name);
		return sl;
	}
	
	private GestioneAttoServerImplPortBindingStub getStub() throws ServiceException {
		final GestioneAttoServerImplPortBindingStub stub = (GestioneAttoServerImplPortBindingStub) service.getGestioneAttoServerImplPort();
		stub.setTimeout(timeout);
		return stub;
	}
	
	private int getTimeout() {
		int timeout = DEFAULT_TIMEOUT;
		if (properties != null) {
			timeout = Integer.parseInt(properties.getProperty(KEY_TIMEOUT, String.valueOf(DEFAULT_TIMEOUT)));
		}
		return timeout;
	}
}
