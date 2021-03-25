package it.eng.utility.email.config;

import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import it.eng.utility.email.util.xml.XmlValidationHandler;

public class VersionValidator {

	private static Logger mLogger = LogManager.getLogger(VersionValidator.class);

	private static Map<Class<?>, JAXBContext> mapContext;
	private static VersionValidator instance;
	private Class<it.eng.utility.email.util.segnatura.Segnatura> SegnaturaNew = it.eng.utility.email.util.segnatura.Segnatura.class;
	private Class<it.eng.utility.email.old.segnatura.Segnatura> SegnaturaOld = it.eng.utility.email.old.segnatura.Segnatura.class;
	private static SAXParserFactory mParserFactory = null;
	private static XMLReader reader;
	private static final String EXTERNAL_DTD_LOADING_FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

	public static synchronized VersionValidator getInstance() throws JAXBException, ParserConfigurationException, SAXException {
		if (instance == null) {
			mLogger.debug("Istanza nulla, inizializzo");
			instance = new VersionValidator();
		}
		return instance;
	}

	private VersionValidator() throws JAXBException, ParserConfigurationException, SAXException {
		if (mapContext == null) {
			initMapContext();
		}
		if (mParserFactory == null) {
			initParserFactory();
		}
		if (reader == null) {
			initReader();
		}
	}

	private void initReader() throws ParserConfigurationException, SAXException {
		SAXParser lSaxParser = mParserFactory.newSAXParser();
		reader = lSaxParser.getXMLReader();
		reader.setFeature(EXTERNAL_DTD_LOADING_FEATURE, false);
	}

	@SuppressWarnings("unchecked")
	public <T> T xmlToObject(String xml, XmlValidationHandler pXmlValidationHandler, Class<T> T, Class<?> E) throws JAXBException {

		SAXSource lSource = new SAXSource(reader, new InputSource(new StringReader(xml)));
		JAXBContext context = mapContext.get(E);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setEventHandler(pXmlValidationHandler);
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema schema = sf.newSchema(new StreamSource(new StringReader(pXmlValidationHandler.getXsd())));
			unmarshaller.setSchema(schema);
		} catch (SAXException e) {
			mLogger.error("Eccezione xmlToObject: " + e.getMessage(), e);
		}
		Object lObject = unmarshaller.unmarshal(lSource);
		JAXBElement<T> lJAXBElement = (JAXBElement<T>) lObject;
		return lJAXBElement.getValue();
	}

	private void initParserFactory() {
		mParserFactory = SAXParserFactory.newInstance();
		mParserFactory.setNamespaceAware(true);
		mParserFactory.setValidating(false);
	}

	private void initMapContext() throws JAXBException {
		mapContext = new ConcurrentHashMap<Class<?>, JAXBContext>();
		mLogger.debug("Creo contextNew");
		JAXBContext contextNew = JAXBContext.newInstance(SegnaturaNew);
		mLogger.debug("Creo contextOld");
		JAXBContext contextOld = JAXBContext.newInstance(SegnaturaOld);
		mapContext.put(SegnaturaNew, contextNew);
		mapContext.put(SegnaturaOld, contextOld);
	}
}
