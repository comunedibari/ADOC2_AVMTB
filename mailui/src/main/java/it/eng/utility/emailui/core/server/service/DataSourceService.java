package it.eng.utility.emailui.core.server.service;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.emailui.core.server.bean.AdvancedCriteria;
import it.eng.utility.emailui.core.server.bean.Criterion;
import it.eng.utility.emailui.core.server.bean.OrderByBean;
import it.eng.utility.emailui.core.server.bean.OrderByBean.Direction;
import it.eng.utility.emailui.core.server.bean.PaginatorBean;
import it.eng.utility.emailui.core.server.datasource.AbstractDataSource;
import it.eng.utility.emailui.core.server.datasource.SingletonDataSource;
import it.eng.utility.emailui.core.server.service.json.DateSerializer;
import it.eng.utility.emailui.core.shared.message.MessageType;

/**
 * Implementa la comunincazione tra client e server tramite l'utilizzo dei
 * Datasource di smartgwt
 * 
 * @author michele
 *
 */
@Controller
@RequestMapping("/datasourceservice")
public class DataSourceService {

	public static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

	public static final SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");

	public static final SimpleDateFormat datetime_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private static Logger logger = LogManager.getLogger(DataSourceService.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/all", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	@ResponseBody
	public String all(@RequestBody String json, @RequestParam(value = "sourceidobject") String sourceid,
			HttpSession session, HttpServletRequest servletrequest) {

		// Salvo i parametri extra
		Map<String, String> extraparams = new HashMap<String, String>();
		Enumeration<String> paramsname = servletrequest.getParameterNames();
		while (paramsname.hasMoreElements()) {
			String name = paramsname.nextElement();
			extraparams.put(name, servletrequest.getParameter(name));
		}

		Gson gson = new Gson();

		ResponseBean response = new ResponseBean();

		AbstractDataSource datasource = null;
		try {

			// Converto il json in RequestBean
			RequestBean request = gson.fromJson(json, RequestBean.class);

			// Recupero gli eventuali fileld type
			LinkedHashMap<String, String> fieldtype = (LinkedHashMap<String, String>) request.getData()
					.remove("FIELD_TYPE");

			// Recupero il datasource server
			datasource = (AbstractDataSource) SingletonDataSource.getInstance().getDatasources().get(sourceid)
					.newInstance();
			datasource.setExtraparams(extraparams);
			datasource.setSession(session);
			datasource.setRequest(servletrequest);
			DSOperationType type = getDSOperation(request.getOperationType());
			List datalist = new ArrayList();
			Object objret = null;
			response.setStatus(DSResponse.STATUS_SUCCESS);
			switch (type) {

			case VALIDATE:
				Map<String, ErrorBean> mappingerror = datasource.validate(beanMapping(datasource, request.getData()));
				if (mappingerror != null && !mappingerror.isEmpty()) {
					response.setStatus(DSResponse.STATUS_VALIDATION_ERROR);
					// response.setErrors(mappingerror);
				}
				break;

			case FETCH:
				if (fieldtype == null) {
					fieldtype = new LinkedHashMap<String, String>();
				}
				// Per evitare che il range arrivi a null
				if (request.getStartRow() == null) {
					request.setStartRow(0);
				}

				if (request.getEndRow() == null) {
					request.setEndRow(75);
				}

				PaginatorBean<?> paginator = datasource.fetch(criteriaMapping(request.getData(), fieldtype),
						request.getStartRow(), request.getEndRow(), getOrderBy(request));
				if (paginator == null) {
					paginator = new PaginatorBean();
					paginator.setStartRow(0);
					paginator.setEndRow(0);
					paginator.setTotalRows(0);

				}
				response.setData(paginator.getData());
				response.setStartRow(paginator.getStartRow());
				response.setEndRow(paginator.getEndRow());
				response.setTotalRows(paginator.getTotalRows());
				break;

			case ADD:
				objret = datasource.add(beanMapping(datasource, request.getData()));
				datalist.add(objret);
				response.setEndRow(1);
				response.setStartRow(0);
				response.setTotalRows(1);
				response.setData(datalist);
				break;

			case REMOVE:
				objret = datasource.remove(beanMapping(datasource, request.getData()));
				datalist.add(objret);
				response.setEndRow(1);
				response.setStartRow(0);
				response.setTotalRows(1);
				response.setData(datalist);
				break;

			case UPDATE:
				objret = datasource.update(beanMapping(datasource, request.getData()),
						beanMapping(datasource, request.getOldValues()));
				datalist.add(objret);
				response.setEndRow(1);
				response.setStartRow(0);
				response.setTotalRows(1);
				response.setData(datalist);
				break;

			case CUSTOM:
				// Da implementare se necessario
				// datasource.validate(beanMapping(datasource,
				// request.getData()));
				break;
			}
		} catch (Exception e) {
			logger.error(e);
			response.setStatus(DSResponse.STATUS_FAILURE);
			// Aggiungo il messaggio di errore
			datasource.addMessage(e.getMessage(), "", MessageType.ERROR);

		}
		DSResponseJson dsrespjson = new DSResponseJson();
		response.setMessages(datasource.getMessages());
		// Salvo i dati di contesto

		dsrespjson.setResponse(response);

		StringWriter writer = new StringWriter();

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new DateSerializer());
		builder.registerTypeAdapter(Timestamp.class, new DateSerializer());

		builder.create().toJson(dsrespjson, DSResponseJson.class, new JsonEscapeWriter(writer));

		String jsonresp = writer.toString();

		return jsonresp;
	}

	/**
	 * Recupera il tipo di operazione
	 * 
	 * @param value
	 * @return
	 */
	private DSOperationType getDSOperation(String value) {
		DSOperationType[] types = DSOperationType.values();
		for (DSOperationType type : types) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Mapping della mappa sul bean
	 * 
	 * @param source
	 * @param map
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private Object beanMapping(AbstractDataSource<?, ?> source, Map map) throws Exception {
		Object instance = source.getBeanClassIn().newInstance();
		if (instance instanceof Map) {
			instance = map;
		} else {

			BeanUtilsBean beanutils = BeanUtilsBean2.getInstance();
			// ciclo la mappa e rimuovo gli oggetti map
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object obj = map.get(key);
				if (obj instanceof Map) {
					Field field = instance.getClass().getDeclaredField(key);
					if (field != null) {
						Object subinstance = field.getType().newInstance();
						beanutils.populate(subinstance, (Map) obj);

						beanutils.getPropertyUtils().setProperty(instance, key, subinstance);
					}
				} else if (obj instanceof List) {

					Field field = instance.getClass().getDeclaredField(key);
					if (field != null) {
						List objlist = new ArrayList();
						ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
						Class<?> genericclass = (Class<?>) stringListType.getActualTypeArguments()[0];
						List<LinkedHashMap> submap = (List<LinkedHashMap>) obj;
						for (LinkedHashMap linksubmap : submap) {
							Object subinstance = genericclass.newInstance();
							beanutils.populate(subinstance, linksubmap);
							objlist.add(subinstance);
						}

						beanutils.getPropertyUtils().setProperty(instance, key, objlist);
					}
				} else {
					try {
						beanutils.copyProperty(instance, key, obj);
					} catch (Exception e) {
						// Log.warn("Proprietà "+key+" non settabile!");
					}
				}
			}
		}
		return instance;
	}

	/**
	 * Creo l'AdvancedCriteria
	 * 
	 * @param source
	 * @param map
	 * @return
	 */
	private AdvancedCriteria criteriaMapping(HashMap<String, Object> map, LinkedHashMap<String, String> fileldtypemap)
			throws Exception {
		AdvancedCriteria instance = new AdvancedCriteria();
		BeanUtilsBean utils = BeanUtilsBean2.getInstance();
		if (map != null && map.keySet() != null && !map.keySet().isEmpty()) {

			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object linkmap = map.get(key);
				if (linkmap instanceof LinkedHashMap) {
					Criterion criterion = new Criterion();
					if (fileldtypemap.containsKey(((LinkedHashMap) linkmap).get("name"))) {
						FieldType type = getType(
								(String) (((LinkedHashMap) fileldtypemap).get(((LinkedHashMap) linkmap).get("name"))));
						((LinkedHashMap) linkmap).put(((LinkedHashMap) linkmap).get("name"),
								reconvertValue(((LinkedHashMap) linkmap).get("value"), type));
					}
					utils.populate(criterion, (LinkedHashMap<?, ?>) linkmap);
					if (fileldtypemap.containsKey(((LinkedHashMap) linkmap).get("name"))) {
						criterion.setType(getType(
								(String) (((LinkedHashMap) fileldtypemap).get(((LinkedHashMap) linkmap).get("name")))));
					}
					// Controllo ricorivamente
					criterionpopulate(criterion, fileldtypemap);
					instance.addCriterion(criterion);
				} else if (linkmap instanceof List) {
					List<LinkedHashMap> submap = (List<LinkedHashMap>) linkmap;
					for (LinkedHashMap linksubmap : submap) {
						Criterion criterion = new Criterion();
						utils.populate(criterion, (LinkedHashMap<?, ?>) linksubmap);
						if (fileldtypemap.containsKey(criterion.getFieldName())) {
							criterion.setType(getType(fileldtypemap.get((String) (criterion.getFieldName()))));
						}
						// Controllo ricorivamente
						criterionpopulate(criterion, fileldtypemap);
						instance.addCriterion(criterion);
					}
				} else if (linkmap instanceof String) {
					Criterion crit = new Criterion();
					crit.setFieldName(key);
					crit.setValue((String) map.get(key));
					if (fileldtypemap.containsKey(crit.getFieldName())) {
						crit.setType(getType(fileldtypemap.get((String) (crit.getFieldName()))));
					}
					instance.addCriterion(crit);
				}
			}
		}
		return instance;
	}

	/**
	 * Popola ricorsivamente il Criterion
	 * 
	 * @param instance
	 * @param map
	 * @param fileldtypemap
	 * @throws Exception
	 */
	private void criterionpopulate(Criterion instance, LinkedHashMap<String, String> fileldtypemap) throws Exception {
		BeanUtilsBean utils = BeanUtilsBean2.getInstance();
		if (instance != null && instance.getCriteria() != null) {

			for (Object linkmap : instance.getCriteria()) {
				if (linkmap instanceof LinkedHashMap) {
					Criterion criterion = new Criterion();
					if (fileldtypemap.containsKey(((LinkedHashMap) linkmap).get("name"))) {
						FieldType type = getType(
								(String) (((LinkedHashMap) fileldtypemap).get(((LinkedHashMap) linkmap).get("name"))));
						((LinkedHashMap) linkmap).put(((LinkedHashMap) linkmap).get("name"),
								reconvertValue(((LinkedHashMap) linkmap).get("value"), type));
					}
					utils.populate(criterion, (LinkedHashMap<?, ?>) linkmap);
					// Controllo ricorivamente
					criterionpopulate(criterion, fileldtypemap);
					instance.addCriterion(criterion);
				} else if (linkmap instanceof List) {
					List<LinkedHashMap> submap = (List<LinkedHashMap>) linkmap;
					for (LinkedHashMap linksubmap : submap) {
						Criterion criterion = new Criterion();
						utils.populate(criterion, (LinkedHashMap<?, ?>) linksubmap);
						// Controllo ricorivamente
						criterionpopulate(criterion, fileldtypemap);
						instance.addCriterion(criterion);
					}
				}
			}
		}
	}

	private Object reconvertValue(Object obj, FieldType type) throws Exception {

		if (obj != null) {
			switch (type) {
			case BOOLEAN:
				return new Boolean(obj.toString());

			case DATE:
				return date_format.parse(obj.toString());

			case DATETIME:
				return datetime_format.parse(obj.toString());

			case TIME:
				return time_format.parseObject(obj.toString());

			case INTEGER:
				return new Integer(obj.toString());

			case FLOAT:
				return new BigDecimal(obj.toString());

			default:
				return obj.toString();
			}

		} else {
			return null;
		}

	}

	private FieldType getType(String typevalue) throws Exception {
		FieldType[] types = FieldType.values();
		for (FieldType type : types) {
			if (type.getValue().equalsIgnoreCase(typevalue)) {
				return type;
			}
		}
		throw new Exception("Nessun type trovato!");
	}

	private List<OrderByBean> getOrderBy(RequestBean bean) {
		List<OrderByBean> orders = null;
		if (bean.getSortBy() != null) {
			orders = new ArrayList<OrderByBean>();
			for (String order : bean.getSortBy()) {
				if (order.startsWith("-")) {
					orders.add(new OrderByBean(order.substring(1), Direction.DESC));
				} else {
					orders.add(new OrderByBean(order, Direction.ASC));
				}
			}
		}
		return orders;
	}

	/**
	 * Bean di Response
	 * 
	 * @author michele
	 *
	 */
	private class DSResponseJson {

		private ResponseBean response;

		public ResponseBean getResponse() {
			return response;
		}

		public void setResponse(ResponseBean response) {
			this.response = response;
		}
	}
}