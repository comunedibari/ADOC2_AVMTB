package com.albopretorio.business.servlet;

import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.nio.file.Files;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.albopretorio.business.bean.AlboPretorioServletConfig;
import com.albopretorio.business.bean.AurigaLoginBean;

/**
 * @author Antonio Peluso
 * 
 *         SERVLET CHE CENTRALIZZA LE CHIAMATE AI SERVIZI DELL ALBO PRETORIO
 *         PRESENTI SU AURIGABUSINESS (AurigaDocument)
 */

@Controller
@RequestMapping("/alboPretorioServlet")
public class AlboPretorioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private WebApplicationContext springContext;

	private static final Logger logger = Logger.getLogger(AlboPretorioServlet.class);

	/** HEADER */
	private static final String TOKEN_AURIGALOGIN = "token";
	private static final String SCHEMA_AURIGALOGIN = "schema";
	private static final String ID_USER_LAVORO_AURIGALOGIN = "idUserLavoro";

	/** INPUT PARAMETERS */
	private static final String NAME_SERVICE = "nameService";
	private static final String DATI_INPUT_XML = "dataInputXml";

	@RequestMapping(value = "invoke", method = { RequestMethod.POST })
	@ResponseBody
	HttpEntity<String> invoke(HttpServletRequest servletrequest, HttpServletResponse servletresponse) {

		logger.debug("--- START --- AlboPretorioServlet");

		// Recupero il nome del servizio della Business da chiamare
		String nameService = StringUtils.isNotBlank(servletrequest.getParameter(NAME_SERVICE))
				? servletrequest.getParameter(NAME_SERVICE)
				: "";
		// Recupero i parametri che vengono dati in ingresso al servizio della Business
		String dataInputXml = StringUtils.isNotBlank(servletrequest.getParameter(DATI_INPUT_XML))
				? servletrequest.getParameter(DATI_INPUT_XML)
				: "";

		AlboPretorioServletConfig alboPretorioServletConfig = (AlboPretorioServletConfig) springContext
				.getBean("AlboPretorioServletConfig");
		String errorMessageTimeout = StringUtils.isNotBlank(alboPretorioServletConfig.getErrorMessageTimeout())
				? alboPretorioServletConfig.getErrorMessageTimeout()
				: "Impossibile evadere la richiesta nel tempo stabilito. Riprovare più tardi";

		// Costruisco il path della chiamata alla business prendendo dal file di
		// configurazione
		// l'endpoint dei servizi dell'albo nella business concatenando il nome del
		// servizio da richiamare
		String pathBusinessService = alboPretorioServletConfig.getPathWSAlboPretorio() + nameService;

		// Recupero i parametri per l'autenticazione
		AurigaLoginBean loginBean = getCredentials(alboPretorioServletConfig, servletrequest);

		// Client client = Client.create(new DefaultClientConfig());
		// client.setReadTimeout(alboPretorioServletConfig.getReadTimeout());
		ClientConfig configuration = new ClientConfig();
		configuration = configuration.property(ClientProperties.READ_TIMEOUT,
				alboPretorioServletConfig.getReadTimeout());
		configuration.register(MultiPartFeature.class);
		Client client = ClientBuilder.newClient(configuration);

		WebTarget webResource = client.target(pathBusinessService);
		FormDataMultiPart multiForm = new FormDataMultiPart();
		multiForm.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
		multiForm.field("dataInput", dataInputXml, MediaType.APPLICATION_ATOM_XML_TYPE);

		logger.debug("Chiamo la business - service: " + nameService + " dataInputXml: " + dataInputXml);
		
		/*CONTROLLO SE E' UN SERVIZIO CHE GESTISCE FILE, QUINDI CAMBIO IL TIPO DI RITORNO*/
		boolean isServiceFie = nameService.equalsIgnoreCase("sbusta") || nameService.equalsIgnoreCase("scaricaZip")
				|| nameService.equalsIgnoreCase("recuperaFile") || nameService.equalsIgnoreCase("checkFirma");
		

		/* Performance logger */
		final long t0 = System.currentTimeMillis();
		try {
			
			String mediaTypeService = isServiceFie ? MediaType.APPLICATION_OCTET_STREAM : MediaType.APPLICATION_JSON;
			
			Invocation.Builder invocationBuilder = webResource.request(mediaTypeService)
					.header(TOKEN_AURIGALOGIN, loginBean.getToken()).header(SCHEMA_AURIGALOGIN, loginBean.getSchema())
					.header(ID_USER_LAVORO_AURIGALOGIN, loginBean.getIdUserLavoro());

			Response response = invocationBuilder.post(Entity.entity(multiForm, (MediaType.MULTIPART_FORM_DATA)),
					Response.class);

			/*
			 * ClientResponse response = webResource .accept(MediaType.APPLICATION_JSON)
			 * .type(MediaType.MULTIPART_FORM_DATA) .header(TOKEN_AURIGALOGIN,
			 * loginBean.getToken()) .header(SCHEMA_AURIGALOGIN, loginBean.getSchema())
			 * .header(ID_USER_LAVORO_AURIGALOGIN, loginBean.getIdUserLavoro())
			 * .post(ClientResponse.class, multiForm);
			 */

			/* Performance logger */
			final long t1 = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nameService + " timeOperation: " + ((t1 - t0) / 1000) + " s");

			// Servizi che ritornano file
			if (isServiceFie) {

				if (Response.Status.OK.getStatusCode() == response.getStatus()) {
					InputStream is = response.readEntity(InputStream.class);

					servletresponse.setContentType("application/octet-stream;charset=UTF-8");
//					servletresponse.setHeader("Content-Disposition", "attachment; filename=\"somefile.pdf\""); 

					IOUtils.copy(is, servletresponse.getOutputStream());
					servletresponse.flushBuffer();
					
					return null;
				} else {

					String errorMessage = response.readEntity(String.class);

					logger.error("La Business ha risposto  - service: " + nameService + "\n errore: " + errorMessage
							+ "\n dataInputXml: " + dataInputXml);

					HttpHeaders responseHeaders = new HttpHeaders();
					responseHeaders.add("storeException", response.getHeaderString("storeException"));
					return new ResponseEntity<String>(errorMessage, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} else {
				HttpHeaders responseHeaders = new HttpHeaders();

				String contentType = "application/json;charset=UTF-8";
				responseHeaders.add("Content-Type", contentType);

				// String responseString = response.getEntity(String.class);
				String responseString = response.readEntity(String.class);

				if (Response.Status.OK.getStatusCode() == response.getStatus()) {
					if (responseString.contains("\"status\":\"500\"")) {
						JSONObject jsonObject = new JSONObject(responseString);
						String errorMessage = jsonObject.getString("errorMessage");

						logger.error("La Business ha risposto  - service: " + nameService + "\n errore: " + errorMessage
								+ "\n dataInputXml: " + dataInputXml + "\n Response: " + responseString);

						responseHeaders.add("storeException", "false");
						return new ResponseEntity<String>(errorMessage, responseHeaders,
								HttpStatus.INTERNAL_SERVER_ERROR);

					} else if (responseString.contains("\"status\":\"599\"")) {
						JSONObject jsonObject = new JSONObject(responseString);
						String errorMessage = jsonObject.getString("errorMessage");

						logger.error("La Business ha risposto  - service: " + nameService + "\n errore: " + errorMessage
								+ "\n dataInputXml: " + dataInputXml + "\n Response: " + responseString);

						responseHeaders.add("storeException", "true");
						return new ResponseEntity<String>(errorMessage, responseHeaders,
								HttpStatus.INTERNAL_SERVER_ERROR);
					}

					logger.debug("Call Business OK - service: " + nameService);
					return new ResponseEntity<String>(responseString, responseHeaders, HttpStatus.OK);

				} else {
					logger.error("C'e' stato un errore inaspettato - service: " + nameService + "\n dataInputXml: "
							+ dataInputXml + "\n Response: " + responseString);

					responseHeaders.add("storeException", "false");
					return new ResponseEntity<String>(responseString, responseHeaders,
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		} catch (Exception e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("storeException", "true");

			if (e.getCause() != null && e.getCause() instanceof SocketTimeoutException) {
				logger.error("AlboPretorioServlet - Timeout nella chiamata al servizio " + nameService + ": "
						+ e.getMessage(), e);
				return new ResponseEntity<String>(errorMessageTimeout, responseHeaders,
						HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				logger.error("AlboPretorioServlet - Errore inaspettato nella chiamata al servizio " + nameService + ": "
						+ e.getMessage(), e);
				return new ResponseEntity<String>(errorMessageTimeout, responseHeaders,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

	}

	/**
	 * Metodo per il recupero dei parametri di autenticazione da passare al servizio
	 * della Business
	 * 
	 * 
	 * @param alboPretorioServletConfig
	 * @param request
	 * @return
	 */
	private AurigaLoginBean getCredentials(AlboPretorioServletConfig alboPretorioServletConfig,
			HttpServletRequest request) {

		AurigaLoginBean loginBean = new AurigaLoginBean();

		// Controllo se ho passato le credenziali di login alla chiamata tramite header
		// (CHIAMATA DA AURIGA), altrimenti prendo quelle di Default da file di
		// configurazione
		if (StringUtils.isNotBlank(request.getHeader(TOKEN_AURIGALOGIN))
				&& StringUtils.isNotBlank(request.getHeader(SCHEMA_AURIGALOGIN))) {
			loginBean.setToken(request.getHeader(TOKEN_AURIGALOGIN));
			loginBean.setSchema(request.getHeader(SCHEMA_AURIGALOGIN));
			loginBean.setIdUserLavoro(StringUtils.isNotBlank(request.getHeader(ID_USER_LAVORO_AURIGALOGIN))
					? request.getHeader(ID_USER_LAVORO_AURIGALOGIN)
					: null);

		} else {
			// Se non ho le credenziali nell header della chiamata (CHIAMATA DA ESTERNO) li
			// prendo da file di configurazione
			if (StringUtils.isNotBlank(request.getHeader("isStorico"))
					&& Boolean.parseBoolean(request.getHeader("isStorico"))) {
				loginBean.setToken(alboPretorioServletConfig.getTokenStorico());
			} else if (StringUtils.isNotBlank(request.getHeader("isStoricoAut"))
					&& Boolean.parseBoolean(request.getHeader("isStoricoAut"))) {
				loginBean.setToken(alboPretorioServletConfig.getTokenStoricoAut());
			} else {
				loginBean.setToken(alboPretorioServletConfig.getToken());
			}
			loginBean.setSchema(alboPretorioServletConfig.getSchema());
			loginBean.setIdUserLavoro(null);
		}

		logger.debug("Parametri di autenticazione da passare al servizio Token: " + loginBean.getToken() + "; Schema: "
				+ loginBean.getSchema() + "; IdUserLavoro: " + loginBean.getIdUserLavoro());

		return loginBean;

	}

	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
	}
}
