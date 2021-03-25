package it.eng.auriga.compiler;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.CSS;
import javax.swing.text.html.StyleSheet;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.doc.table.OdfTableColumn;
import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeBodyElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeMasterStylesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleParagraphPropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTableCellPropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTextPropertiesElement;
import org.odftoolkit.odfdom.dom.element.text.TextLineBreakElement;
import org.odftoolkit.odfdom.dom.element.text.TextSElement;
import org.odftoolkit.odfdom.dom.element.text.TextSectionElement;
import org.odftoolkit.odfdom.dom.element.text.TextTabElement;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.odftoolkit.odfdom.dom.style.props.OdfParagraphProperties;
import org.odftoolkit.odfdom.dom.style.props.OdfStylePropertiesSet;
import org.odftoolkit.odfdom.dom.style.props.OdfStyleProperty;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextSpan;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfName;
import org.odftoolkit.odfdom.pkg.OdfNamespace;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.style.StyleTypeDefinitions.SupportedLinearMeasure;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.Section;
import org.odftoolkit.simple.text.list.CircleDecorator;
import org.odftoolkit.simple.text.list.DiscDecorator;
import org.odftoolkit.simple.text.list.ListDecorator.ListType;
import org.odftoolkit.simple.text.list.ListItem;
import org.odftoolkit.simple.text.list.NumberDecorator;
import org.odftoolkit.simple.text.list.NumberedAlphaLowerDecorator;
import org.odftoolkit.simple.text.list.NumberedAlphaUpperDecorator;
import org.odftoolkit.simple.text.list.NumberedGreekLowerDecorator;
import org.odftoolkit.simple.text.list.NumberedRomanLowerDecorator;
import org.odftoolkit.simple.text.list.NumberedRomanUpperDecorator;
import org.odftoolkit.simple.text.list.SquareDecorator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Utilities;
import com.itextpdf.tool.xml.css.CSS.Value;

import fr.opensagres.xdocreport.core.document.SyntaxKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import it.eng.auriga.compiler.exeption.FreeMarkerCreateDocumentException;
import it.eng.auriga.compiler.exeption.FreeMarkerFixMergedCellException;
import it.eng.auriga.compiler.exeption.FreeMarkerMergeHtmlSectionsException;
import it.eng.auriga.compiler.exeption.FreeMarkerRetriveStyleException;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.ModelliDocDatasource;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.AssociazioniAttributiCustomBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocBean;
import it.eng.utility.PdfUtility;
import it.eng.utility.barcode.BarcodeUtility;
import it.eng.utility.barcode.ImpostazioniBarcodeBean;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.user.ParametriDBUtil;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import net.sf.jooreports.templates.image.RenderedImageSource;

/**
 * Centralizza la generazione di template con dati iniettati
 * 
 * @author mattia zanin
 * 
 *         IMPORTANTE: SE LA CLASSE VIENE MODIFICATA VERIFICARE SE DEVE ESSERE MODIFICATA ANCHE L'ANALOGA CLASSE IN AURIGADOCUMENT
 *
 */
public class FreeMarkerModelliUtil {
	
	private static Logger logger = Logger.getLogger(FreeMarkerModelliUtil.class);
	
	public static final char NUL = (char) 0; // Codice ASCII NUL (Nullo)
	public static final char EOT = (char) 4; // Codice ASCII EOT (End of transmission)
	public static final char ENQ = (char) 5; // Codice ASCII ENQ (Enquiry)	 
	
	public static final String FREEMARKER_INIZIO = "${";
	public static final String FREEMARKER_FINE = "}";
	public static final String FREEMARKER_IMAGE_INIZIO = "JOOSCRIPT.IMAGE(";
	public static final String FREEMARKER_IMAGE_FINE = ")";
	public static final String TAG_APERTURA_SCRIPT_INIZIO = "<SCRIPT";
	public static final String TAG_APERTURA_SCRIPT_FINE = ">";
	public static final String TAG_CHIUSURA_SCRIPT = "</SCRIPT>";
	public static final String TAG_APERTURA_IMAGE_INIZIO = "<IMG";
	public static final String TAG_APERTURA_IMAGE_FINE = ">";
	
	public static final char CHECK = '\u2611'; // Codice ASCII CHECK
	public static final char NOT_CHECK = '\u2610'; // Codice ASCII NO CHECK	
	public static final String ESCAPE_BR = "" + '\u200E'; // Codice ASCII ESCAPEBR
	
	public static final String HTML_VALUE_PREFIX = "|*|HTML|*|";
	public static final String TAG_APERTURA_DATI_SENSIBILI = "<s>";
	public static final String TAG_CHIUSURA_DATI_SENSIBILI = "</s>";
	public static final String OMISSIS = "<i>omissis</i>";
	public static final String HTML_SPACE = "&nbsp;";
	public static String VARIABILE_FONT_NAME;
	public static String VARIABILE_FONT_SIZE;
	
	public static TemplateWithValuesBean createTemplateWithValues(File templateOdt, ModelliDocBean bean, HttpSession session) throws Exception  {

		// Devo togliere tutti i numeri colonna e sostituirli con col<n>
		Map<String, Object> model;
		if ((bean.getFlgProfCompleta() != null && bean.getFlgProfCompleta())) {
			model = createMapToFillTemplate(bean, session);
		} else {
			// Se il modello non è profilato lancio una eccezione
			logger.error("Impossibile generare il file da modello. La profilatura del modello non è completa per il modello con id: " + bean.getIdModello());
			throw new Exception("Impossibile generare il file da modello. La profilatura del modello non è completa");
		}
		
		/**/ 		
//		File templateHtml = File.createTempFile("temp", ".html");						
//		OpenOfficeConfiguration config = (OpenOfficeConfiguration)SpringAppContext.getContext().getBean("officemanager");	
//		OpenOfficeConverter.configure(config);												
//		OpenOfficeConverter.newInstance().convert(templateOdt, templateHtml);		
//		String html = FileUtils.readFileToString(templateHtml);
//		
//		Document htmlDocument = Jsoup.parse(html);
//		String charset = getCharsetFromHtmlDocument(htmlDocument);		
//		
//		Map<String, Object> htmlSectionsModel = new HashMap<String, Object>();
//		Elements divElements = htmlDocument.getElementsByTag("div");
//		for(Element div : divElements){				
//			if(div.attr("id").toUpperCase().startsWith(FREEMARKER_INIZIO)) {
//				String nomeVariabile = div.attr("id").substring(FREEMARKER_INIZIO.length(), div.attr("id").indexOf(FREEMARKER_FINE, 0)).trim();				
//				div.append(StringEscapeUtils.unescapeHtml((String) model.get(nomeVariabile)));
//				htmlSectionsModel.put(nomeVariabile, StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml((String) model.get(nomeVariabile)).replaceAll("\n", "").replaceAll("\t", "")));
//				model.put(nomeVariabile, nomeVariabile);				
//			}
//		}		
//		
//		html = htmlDocument.html();		
//		FileUtils.writeStringToFile(templateHtml, html, charset); //TODO bisogna mettere il charset corretto
//		
//		File templateOdtFromHtml = File.createTempFile("temp", ".odt");
//		OpenOfficeConverter.newInstance().convert(templateHtml, templateOdtFromHtml);
		/**/
		
		//Gestisco gli omisses
		Map<String, Object> htmlSectionsModel = new HashMap<String, Object>();
		for(String nomeVariabile : model.keySet()) {
			if(model.get(nomeVariabile) instanceof String) {
				String value = (String) model.get(nomeVariabile);
				String html = "";
				String regex = "<([A-Za-z][A-Za-z0-9]*)\\b[^>]*>(.*?)</\\1>";
				boolean isHtml = false;
				if(value != null && value.startsWith(HTML_VALUE_PREFIX)) {
					html = value.substring(HTML_VALUE_PREFIX.length());
					isHtml = true;
				} else if (StringUtils.isNotBlank(value) && value.replaceAll("\n", "").matches(regex)) {
					html = value;
					isHtml = true;
				}
				if (isHtml) {
					//TODO Perchè viene fatto l'unescapeHtml? 
					// intanto lo commento e faccio solo i replaceAll, perchè mi crea problemi quando ho dei caratteri speciali: &amp; ecc...					
//					html = StringEscapeUtils.unescapeHtml(StringEscapeUtils.unescapeHtml(html).replaceAll("\n", "").replaceAll("\t", ""));
					html = html.replaceAll("\n", "").replaceAll("\t", "");
					// sostituisco tutti i dati sensibili (quelli tra <s> e </s>)
					html = html.replaceAll("<br />", ESCAPE_BR);
					html = html.replaceAll("<br/>", ESCAPE_BR);
					html = html.replaceAll("<br>", ESCAPE_BR);	
					if(bean.getFlgMostraDatiSensibili() == null || bean.getFlgMostraDatiSensibili()) {
						String htmlFull = "" + html; 
						if(bean.getFlgMostraOmissisBarrati() == null || !bean.getFlgMostraOmissisBarrati()) {
							htmlFull = htmlFull.replaceAll(TAG_APERTURA_DATI_SENSIBILI, "");
							htmlFull = htmlFull.replaceAll(TAG_CHIUSURA_DATI_SENSIBILI, "");
						}
						htmlFull = htmlFull.replaceAll(HTML_SPACE, "&#160;");
						// htmlFull = htmlFull.replaceAll(HTML_SPACE, "&#13;");
						htmlSectionsModel.put(nomeVariabile, htmlFull);
					} else {
						String htmlOmissis = replaceOmissisInHtml(html);
						htmlSectionsModel.put(nomeVariabile, htmlOmissis);
					}					
					model.put(nomeVariabile, "");	
				}
			}
		}
		
		// font e size di default del modello nel caso in cui sia fatto male
		VARIABILE_FONT_SIZE = ParametriDBUtil.getParametroDB(session, "MODELLO_FONT_SIZE");
		VARIABILE_FONT_NAME = ParametriDBUtil.getParametroDB(session, "MODELLO_FONT_NAME");
		
		// Inietto i valori nel modello.
		// Questa iniezione non consente una corretta gestione dei valori ckeditor, che andranno quiandi iniettati in un secondo momento
		DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory(); 
		DocumentTemplate template = documentTemplateFactory.getTemplate(templateOdt);    
       
		File templateOdtWithValues = File.createTempFile("temp", ".odt");
		FileOutputStream odtOutputStream = new FileOutputStream(templateOdtWithValues);
		try {
			template.createDocument(model, odtOutputStream);
		} catch (Exception e) {
			logger.error("Errore nell'esecuzione del metodo createDocument: " + e.getMessage());
			throw new FreeMarkerCreateDocumentException(e.getMessage(), e);
		}
		
		// Ho terminato la prima iniezione, ora gestisco i valori ckeditor
		
		TemplateWithValuesBean beanToReturn = new TemplateWithValuesBean();

		// Inietto l'html dei campi CKEditor nelle sezioni del modello odt (prima bisogna aggiungere le dipendenze di xdocreport nel pom)
		if(htmlSectionsModel != null && !htmlSectionsModel.isEmpty()) {
			// Creo una mappa dove salvo l'eventuale testo statico presente nelle sezioni html priam e dopo il placeholder
			// Map<String, String[]> mappaTestoStaticoHtmlSection = new HashMap<>();
			// Ripristino le section destinate a contenere i valori ckeditor, rimettendo nel file ottenuto dalla prima iniezione quelle presenti nel modello.
			// Lo devo fare perchè la prima iniezione ha iniettato anche i valori ckeditor, ma dovendo rifare l'inizione devo ripristinare le section del modello
			for(String nomeVariabile : htmlSectionsModel.keySet()) {
				mergeHtmlSections(nomeVariabile, templateOdt, templateOdtWithValues);
			}	
			
			// 5) Gestione tabelle con celle unite
			// Non è supportata l'inizione html di tabelle con celle unite, devo quandi seguire questi passaggi per ogni tabella presente:
			// 1- Trasformare la tabella html con celle unite in una tabella senza celle unite
			// 2- Iniettare la tabella senza celle unite
			// 3- Generare il file odt
			// 4- Per ogni tabella nel file odt eseguore i raggruppamenti delle celle per ripristinare lo stato originale della tabella
			Map<String, TableStyleBean> mappaTablesStyle = fixMergedCell(htmlSectionsModel);

			//TODO PER AGGIUNGERE IL CONTENUTO NELLE SEZIONI HTML
			// 1) Load ODT file by filling Velocity template engine and cache
			// it to the registry
			FileInputStream fisReport = new FileInputStream(templateOdtWithValues);
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(fisReport, TemplateEngineKind.Freemarker);
			
			// 2) Create fields metadata to manage text styling
			FieldsMetadata metadata = report.createFieldsMetadata();
			for(String nomeVariabile : htmlSectionsModel.keySet()) {
//				if (mappaTestoStaticoHtmlSection.containsKey(nomeVariabile)) {
//					String testoHtml = (String) htmlSectionsModel.get(nomeVariabile);
//					if (testoHtml != null && testoHtml.startsWith("<p")) {
//						int endTag = testoHtml.indexOf(">");
//						testoHtml = testoHtml.substring(0, endTag + 1) + mappaTestoStaticoHtmlSection.get(nomeVariabile)[0] + testoHtml.substring(endTag + 1);
//					}
//					if (testoHtml != null && testoHtml.endsWith("</p>")) {
//						testoHtml = testoHtml.substring(0, testoHtml.length() - 4) + mappaTestoStaticoHtmlSection.get(nomeVariabile)[1] + "</p>";
//					}
//					htmlSectionsModel.put(nomeVariabile, testoHtml);
//					
//				}
				metadata.addFieldAsTextStyling(nomeVariabile, SyntaxKind.Html, true);		
			}
			
			// 3) Create context Java model
			IContext context = report.createContext();
			context.putMap(htmlSectionsModel);
			
			// 4) Generate report by merging Java model with the ODT
			odtOutputStream = new FileOutputStream(templateOdtWithValues);
			
			Map<String, Object> listsStyle = null;
			
			try {
				// 6) Retrive tables style
				mappaTablesStyle = retriveCellsTableStyle(htmlSectionsModel, mappaTablesStyle);
				
				// 7) Retrive list style
				listsStyle = retriveListsStyle(htmlSectionsModel);
				
			} catch (Exception e) {
				logger.error("Errore nel retrive degli stili: " + e.getMessage());
				throw new FreeMarkerRetriveStyleException(e.getMessage(), e);
			}
		
			report.process(context, odtOutputStream);
			
			//Funzione che prende i caratteri delle sezioni dal modello ODT e li applica sull ODT generato che dovrà  essere convertito
			// TODO controllare il cambio caratteri, per ora gestisco gli errori con una eccezione
			try {
				templateOdtWithValues = cambiaCarattereSezioni(templateOdtWithValues, templateOdt, mappaTablesStyle, listsStyle, bean, htmlSectionsModel);
				if (mappaTablesStyle != null && !mappaTablesStyle.isEmpty()) { 
					templateOdtWithValues = applicaStiliTabelle(templateOdtWithValues, mappaTablesStyle);
					// Riprisitino l'unione solo se si sono tabelle con celle unite
					if (isPresentiTabelleConCelleUnite(mappaTablesStyle)) {
						templateOdtWithValues = applicaUnioniCelle(templateOdtWithValues, mappaTablesStyle);
					}
				}
			} catch (StoreException e) {
				//errori gestiti
				logger.error(e.getMessage(), e);
				// per gestire eventualmente un warning specifico in futuro
				beanToReturn.setInError(true);
				beanToReturn.setErrorMessage(e.getMessage());
			} catch (Exception e) {
				// errore non gestito durante l'applicazione degli stili o l'unione delle celle nelle tabelle 
				logger.error(e.getMessage(), e);
				// per gestire eventualmente un warning specifico in futuro
				beanToReturn.setInError(true);
				beanToReturn.setErrorMessage("");
			} finally {
				if(fisReport != null) {
					try {
						fisReport.close(); 
					} catch (Exception e) {}
				}
				if(odtOutputStream != null) {
					try {
					    odtOutputStream.close();
					} catch (Exception e) {}
				}
			}
		}
		
		beanToReturn.setFileOdtGenerato(templateOdtWithValues);
		if(bean.getFlgGeneraPdf()) {
			beanToReturn.setFileGenerato(createPdfFromOdt(templateOdtWithValues, session));
		} else {
			beanToReturn.setFileGenerato(ModelliUtil.convertToDoc(templateOdtWithValues));
		}
		return beanToReturn;
	}
	
	private static boolean isPresentiTabelleConCelleUnite(Map<String, TableStyleBean> mappaTablesStyle) {
		if (mappaTablesStyle != null) {
			Set<String> keySet = mappaTablesStyle.keySet();
			for (String key : keySet) {
				TableStyleBean lTableStyleBean = mappaTablesStyle.get(key);
				if (lTableStyleBean != null && isTabellaConCelleUnite(lTableStyleBean)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isTabellaConCelleUnite(TableStyleBean lTableStyleBean) {
		CellaMappaUnioniBean[][] lCellaMappaUnioniBean = lTableStyleBean.getMappaUnioni();
		if (lCellaMappaUnioniBean != null){
			for (int i = 0; i < lCellaMappaUnioniBean.length; i++) {
				for (int j = 0; j < lCellaMappaUnioniBean[i].length; j++) {
					if (lCellaMappaUnioniBean[i][j] != null && lCellaMappaUnioniBean[i][j].getNroRaggruppamento() > 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static File applicaUnioniCelle(File templateOdtWithValues, Map<String, TableStyleBean> mappaTablesStyle) throws Exception {

		TextDocument odtFinale = null;
		try {
			odtFinale = TextDocument.loadDocument(templateOdtWithValues.getPath());
	
			//Prendo le varie sezioni dal modello ODT
			Iterator<Section> iteratorSezioni = odtFinale.getSectionIterator();
			while(iteratorSezioni.hasNext()) {
				String nomeSection = iteratorSezioni.next().getName();
				Section section = odtFinale.getSectionByName(nomeSection);
				Iterator<Table> iteratorTabelle = section.getTableList().iterator();
				// Ciclo sulle tabelle
				while (iteratorTabelle.hasNext()) {
					Table tabella = iteratorTabelle.next();
					String idTable = tabella.getOdfElement().getAttribute("idTableCKEDITOR");
					if (StringUtils.isNotBlank(idTable) && mappaTablesStyle.containsKey(idTable) && isTabellaConCelleUnite(mappaTablesStyle.get(idTable))) {
						// Ciclo sulle celle
						for (int posRow = tabella.getRowCount() - 1; posRow >= 0; posRow --) {
							for (int posCol = tabella.getColumnCount() - 1; posCol >= 0 ; posCol--) {
								CellaMappaUnioniBean currentCell = mappaTablesStyle.get(idTable).getMappaUnioni()[posRow][posCol];
								Integer nroRagg = currentCell.getNroRaggruppamento();
								boolean coveredCell = currentCell.isCoveredCell();
								if (!coveredCell && nroRagg > 0) {
									// E' una cella unita di partenza (quindi non è la covered)
									Integer colToAdd = currentCell.getColSpan() ;
									Integer rowToAdd = currentCell.getRowSpan() ;
									Integer startRow = posRow;
									Integer endRow = posRow + rowToAdd -1 ;
									Integer startCol = posCol;
									Integer endCol = posCol + colToAdd -1 ;
	
									tabella.getCellRangeByPosition(startCol, startRow, endCol, endRow).merge();
								} 							
							}
						}
					}
				}
			}
	
			File templateOdtToconvert = File.createTempFile("temp", ".odt");
	
			odtFinale.save(templateOdtToconvert);
	
			return templateOdtToconvert;
			
		} finally {
			if(odtFinale != null) {
				try {
					odtFinale.close();
				} catch (Exception e) {}
			}
		}
	}

	private static void getWidthCols(TableStyleBean tableStyleBean) {
		
		CellaMappaUnioniBean[][] mappaUnioni = tableStyleBean.getMappaUnioni();
		Float[] listaWidthCols = new Float[mappaUnioni[0].length];

		for (int i = 0; i < mappaUnioni.length; i++) {
			for (int j = 0; j < mappaUnioni[0].length; j++) {

				CellaMappaUnioniBean currentCell = mappaUnioni[i][j];

				Integer colspan = currentCell.getColSpan();
				if (colspan == 1) {
					AttributeSet cellStyle = tableStyleBean.getListaStiliRow().get(i).get(j);
					String colWidthUnit = cellStyle.getAttribute(CSS.Attribute.WIDTH) != null ? cellStyle.getAttribute(CSS.Attribute.WIDTH).toString() : null;
					if (StringUtils.isNotBlank(colWidthUnit) && colWidthUnit.length() >= 2 && !colWidthUnit.toLowerCase().contains("nan") ) {
						if (listaWidthCols[j] == null ) {
							Float colWidthFloat = convertMeasure(colWidthUnit);
							listaWidthCols[j] = colWidthFloat;
						}
					}
				}
			}
		}
		
		ArrayList<Float> listaWidths = new ArrayList<>();
		for (int i = 0; i < listaWidthCols.length; i++) {
			listaWidths.add(listaWidthCols[i]);
		}
		
		tableStyleBean.setListaWidthColonne(listaWidths);
	}

	private static boolean isTrue(Boolean value) {
		return value != null && value;
	}
	
	public static String replaceOmissisInHtml(String html) {
		
		String htmlOmissis = ""; 
		int pos = 0;
		while(pos < html.length()) {
			int startDatiSensibili = html.indexOf(TAG_APERTURA_DATI_SENSIBILI, pos);
			if(startDatiSensibili == -1) {
				htmlOmissis += html.substring(pos);
				break;
			}
			htmlOmissis += html.substring(pos, startDatiSensibili);
			int endDatiSensibili = html.indexOf(TAG_CHIUSURA_DATI_SENSIBILI, startDatiSensibili);
			htmlOmissis += OMISSIS;	
			if(endDatiSensibili != -1) {
				pos = endDatiSensibili + TAG_CHIUSURA_DATI_SENSIBILI.length();
			} else {
				pos = html.length();
			}
		}
		
		htmlOmissis = htmlOmissis.replaceAll(HTML_SPACE, "&#160;");
		return htmlOmissis;
	}

	/**
	 * Metodo che per ogni tabella assegnna un bean TableStyleBean contenente le informazioni su:
	 * - lista degli stili applicati su ogni cella
	 * - larghezza di ogni colonna
	 * - mappa dei raggruppamenti (ad ogni cella unita è assegnato il numero di raggruppamento di appartenenza)
	 * - coordinnate di raggruppamenti (per ogni raggruppamento si indica la cella di inizio in alto a sinistra e quella di fine in basso a destra)
	 * 
	 * @param htmlSectionsModel Mappa delle sezioni nel documento
	 * @return mappa contenete lo TableStyleBean di ogni tabella
	 * @throws FreeMarkerFixMergedCellException 
	 */
	private static Map<String, TableStyleBean> fixMergedCell(Map<String, Object> htmlSectionsModel) throws FreeMarkerFixMergedCellException {
		
		try {
			Map<String, TableStyleBean> tablesStyle = new HashMap<String, TableStyleBean>();
			
			// ciclo su tutte le section
			for (String htmlSectionName : htmlSectionsModel.keySet()) {
				Document doc = Jsoup.parse((String) htmlSectionsModel.get(htmlSectionName));
				doc.outputSettings().indentAmount(0).prettyPrint(false);
				int nTab = 0;
				Elements tables = doc.select("table");
				// ciclo su tutte le tabelle sdella section
				for (Element table : tables) {
					nTab++;
					Elements rows = table.select("tr");
					TableStyleBean tableStyle = new TableStyleBean();
					
					// Ricavo il numero massimo di righe e colonne, ovvero la dimensione della tabella con tutte le celle divise
					int numMaxRows = rows.size();
					int numMaxCols = 0;
					for (int posRow = 0; posRow < rows.size(); posRow++) {
						Element row = rows.get(posRow);
						Elements cols = row.select("td");
						int sumColCurrentRow = 0;
						for (int posCol = 0; posCol < cols.size(); posCol++) {
							Element currentCol = cols.get(posCol);
							int colSpanSet = StringUtils.isNotBlank(currentCol.attr("colspan")) ? Integer.parseInt(currentCol.attr("colspan")) : 1;
							sumColCurrentRow += colSpanSet;
						}
						if (sumColCurrentRow > numMaxCols) {
							numMaxCols = sumColCurrentRow;
						}
					}
					
					// Inizializzo la mappa delle celle unite
					CellaMappaUnioniBean[][] mappaUnioni = new CellaMappaUnioniBean[numMaxRows][numMaxCols];
					for (int i = 0; i < numMaxRows; i++) {
						for (int j = 0; j < numMaxCols; j++) {
							mappaUnioni[i][j] = new CellaMappaUnioniBean();
						}
					}
					
					int numeroRaggruppamento = 0;
	
					// Scorro tutte le celle per ricavare la mappa delle unioni
					for (int posRow = 0; posRow < rows.size(); posRow++) {
						Element row = rows.get(posRow);
						Elements cols = row.select("td");
						// CellaMappaUnioniBean ha la stessa dimensione della tabella, ma non ci sarà una corrispondenza 1 a 1 delle celle dato che la tabella può contenere celle unite
						// Ad esempio se la tabella ha le prime due celle della prima riga unite la cella 0,0 corrisponde alla cella 0,0 di CellaMappaUnioniBean, ma la cella 0,1 corrisponde alla
						// cella 0,2 della CellaMappaUnioniBean, in quanto in CellaMappaUnioniBean la cella 0,1 è segnata come covered
						// colCorrenteMappa serve appunto come indice per tenere conto di questa mancata corrispondenza
						int colCorrenteMappa = initColCorrenteMappa(mappaUnioni, posRow, 0);
						for (int posCol = 0; posCol < cols.size(); posCol++) {
	
							Element col = cols.get(posCol);
							int colSpanSet = StringUtils.isNotBlank(col.attr("colspan")) ? Integer.parseInt(col.attr("colspan")) : 0;
							int rowSpanSet = StringUtils.isNotBlank(col.attr("rowspan")) ? Integer.parseInt(col.attr("rowspan")) : 0;
							if (colSpanSet > 0 && rowSpanSet > 0) {
								// La cella è formata dall'unione di celle sia in verticale che orizzontale
								numeroRaggruppamento ++;
								mappaUnioni[posRow][colCorrenteMappa].setCoveredCell(false);
								mappaUnioni[posRow][colCorrenteMappa].setRowSpan(rowSpanSet);
								mappaUnioni[posRow][colCorrenteMappa].setColSpan(colSpanSet);
								// Per ogni riga trovo le celle che appartengono a quel raggruppamento
								int startColGroup = colCorrenteMappa;
								for (int  i = 0; i < rowSpanSet; i++) {
									colCorrenteMappa = startColGroup;
									for (int j = 0 ; j < colSpanSet; j++) {
										mappaUnioni[posRow + i][colCorrenteMappa].setNroRaggruppamento(numeroRaggruppamento);
										colCorrenteMappa = avanzaColCorrenteMappa(mappaUnioni, posRow + i, colCorrenteMappa);
									}
								}
							} else if (colSpanSet > 0 && rowSpanSet == 0) {
								// La cella è formata solo dall'unione di celle in orizzontale
								numeroRaggruppamento ++;
								mappaUnioni[posRow][colCorrenteMappa].setCoveredCell(false);
								mappaUnioni[posRow][colCorrenteMappa].setColSpan(colSpanSet);
								for (int i = 0 ; i < colSpanSet; i++) {
									mappaUnioni[posRow][colCorrenteMappa].setNroRaggruppamento(numeroRaggruppamento);
									colCorrenteMappa = avanzaColCorrenteMappa(mappaUnioni, posRow, colCorrenteMappa);
								}
							} else if (colSpanSet == 0 && rowSpanSet > 0) {
								// La cella è formata solo dall'unione di celle in verticale
								numeroRaggruppamento ++;
								mappaUnioni[posRow][colCorrenteMappa].setCoveredCell(false);
								mappaUnioni[posRow][colCorrenteMappa].setRowSpan(rowSpanSet);
								for (int i = 0; i < rowSpanSet; i++) {
									mappaUnioni[posRow + i][colCorrenteMappa].setNroRaggruppamento(numeroRaggruppamento);
								}
								colCorrenteMappa = avanzaColCorrenteMappa(mappaUnioni, posRow, colCorrenteMappa);
							} else if  (colSpanSet == 0 && rowSpanSet == 0) {
								// Nessun raggruppamento sulla cella
								colCorrenteMappa = avanzaColCorrenteMappa(mappaUnioni, posRow, colCorrenteMappa);
							}
						}
					}
					
					tableStyle.setMappaUnioni(mappaUnioni);
					tablesStyle.put(htmlSectionName + "_Tab" + nTab, tableStyle);
					
					// Aggiungo tutte le celle per fare in modo che la tabella diventi senz raggruppamenti
					for (int i = 0; i < mappaUnioni.length; i++) {
						int lastNotCovered = 0;
						System.out.println(mappaUnioni[i].toString());
	
						for (int j = 0; j < mappaUnioni[0].length; j++) {
							Element row = rows.get(i);
							Elements cols = row.getElementsByTag("td");
							if (mappaUnioni[i][j].isCoveredCell() && mappaUnioni[i][j].getNroRaggruppamento() > 0 ) {
								if ( j == 0 ) {
									cols.get(lastNotCovered).before("<td></td>");				
								} else {
									cols.get(lastNotCovered).after("<td></td>");				
								}
							} else if (!mappaUnioni[i][j].isCoveredCell() || mappaUnioni[i][j].getNroRaggruppamento() == 0){
								lastNotCovered = j;
							}
						}
					}
				}
				htmlSectionsModel.put(htmlSectionName, doc.select("body").html());
			}
			return tablesStyle;
		} catch (Exception e) {
			logger.error("Errore nell'esecuzione del metodo fixMergedCell: " + e.getMessage());
			throw new FreeMarkerFixMergedCellException(e.getMessage(), e);
		}
	}
	
	// Si posiziona nel primo elemento non covered della riga attuale nella mappaUnioni
	private static int initColCorrenteMappa(CellaMappaUnioniBean[][] mappaUnioni, int posRow, int posCol) {
		CellaMappaUnioniBean[] rigaUnione = mappaUnioni[posRow];
		// Devo considerare che potrebbero esserci dei rowspan
		// ad esempio se in una tabella 3x3 la cella in posizione 0,0 ha un rowspan di 2, la prima celle della riga 1 corrisponde
		/// all'elemento in posizione 1 di rigaUnione in quanto l'lemento in posizione 0 è una cella covered.
		for (int i = posCol; i < rigaUnione.length; i++) {
			if (rigaUnione[i] == null || rigaUnione[i].getNroRaggruppamento() == 0) {
				return i;
			}	
		}
		
		return -1;
	}
	
	// Si posiziona nel prossimo elemento non covered della riga attuale nella mappaUnioni
	private static int avanzaColCorrenteMappa(CellaMappaUnioniBean[][] mappaUnioni, int posRow, int colCorrenteMappa) {
		CellaMappaUnioniBean[] rigaUnione = mappaUnioni[posRow];
		
		for (int i = 1; (colCorrenteMappa + i) < rigaUnione.length; i++) {
			if (rigaUnione[colCorrenteMappa + i] == null || rigaUnione[colCorrenteMappa + i].getNroRaggruppamento() == 0) {
				return colCorrenteMappa + i;
			}	
		}
		
		return -1;
	}
	
	private static Map<String, TableStyleBean> retriveCellsTableStyle(Map<String, Object> htmlSectionsModel, Map<String, TableStyleBean> tablesStyle) {
				
		for (String htmlSectionName : htmlSectionsModel.keySet()) {
			Document doc = Jsoup.parse((String) htmlSectionsModel.get(htmlSectionName));
			int nTab = 0;
			Elements tables = doc.select("table");
			for (Element table : tables) {
				nTab++;
				Elements rows = table.select("tr");
				ArrayList<ArrayList<AttributeSet>> listaStiliRow = new ArrayList<ArrayList<AttributeSet>>();
				for (int posRow = 0; posRow < rows.size(); posRow++) {
					Element row = rows.get(posRow);
					Elements cols = row.select("td");
					ArrayList<AttributeSet> listaStiliCol = new ArrayList<AttributeSet>();
					for (int posCol = 0; posCol < cols.size(); posCol++) {
						Element col = cols.get(posCol);
						StyleSheet styleSheet = new StyleSheet();
						AttributeSet styleSet = styleSheet.getDeclaration(col.attr("style"));
						listaStiliCol.add(posCol, styleSet);
					}
					listaStiliRow.add(posRow, listaStiliCol);
				}
				
				TableStyleBean tableStyle = tablesStyle.get(htmlSectionName + "_Tab" + nTab) != null ? tablesStyle.get(htmlSectionName + "_Tab" + nTab) : new TableStyleBean();
				tableStyle.setListaStiliRow(listaStiliRow);
				tablesStyle.put(htmlSectionName + "_Tab" + nTab, tableStyle);
			}
		}

		return tablesStyle;
	}

	private static Map<String, Object> retriveListsStyle(Map<String, Object> htmlSectionsModel) {
		Map<String, Object> listsStyle = new HashMap<String, Object>();

		for (String htmlSectionName : htmlSectionsModel.keySet()) {
			Document doc = Jsoup.parse((String) htmlSectionsModel.get(htmlSectionName));
			int nItem = 0;
			Elements listItems = doc.select("li");
			for (Element listItem : listItems) {
				nItem++;
				StyleSheet styleSheet = new StyleSheet();
				AttributeSet styleSet = styleSheet.getDeclaration(listItem.attr("style"));
				listsStyle.put("%#ListItem" + nItem + "#%sect_"+ htmlSectionName, styleSet);
			}
			
			int nOlist = 0;
			Elements oLists = doc.select("ol"); 
			for (Element oList : oLists) {
				nOlist++;
				StyleSheet styleSheet = new StyleSheet();
				AttributeSet styleSet = styleSheet.getDeclaration(oList.attr("style"));
				listsStyle.put("%#oListStyle" + nOlist + "#%sect_"+ htmlSectionName, styleSet);
				
				Integer startSet = StringUtils.isNotBlank(oList.attr("start")) ? Integer.parseInt(oList.attr("start")) : null;
				listsStyle.put("%#oListStart" + nOlist + "#%sect_"+ htmlSectionName, startSet);
			}
			
			int nUlist = 0;
			Elements uLists = doc.select("ul"); 
			for (Element uList : uLists) {
				nUlist++;
				StyleSheet styleSheet = new StyleSheet();
				AttributeSet styleSet = styleSheet.getDeclaration(uList.attr("style"));
				listsStyle.put("%#uListStyle" + nUlist + "#%sect_"+ htmlSectionName, styleSet);
			}
		}

		return listsStyle;
	}
	
	private static File applicaStiliTabelle(File templateOdtWithValues, Map<String, TableStyleBean> mappaTablesStyle) throws Exception {
		
		OdfDocument odtFinale = null;
		try {
		    odtFinale = OdfDocument.loadDocument(templateOdtWithValues);
			for (OdfTable odfTable : odtFinale.getTableList()) {
				String idTable =  odfTable.getOdfElement().getAttribute("idTableCKEDITOR");
				if (StringUtils.isNotBlank(idTable) && mappaTablesStyle.containsKey(idTable)) {
//					odfTable.getOdfElement().setProperty(StyleTablePropertiesElement.MayBreakBetweenRows, "true");
//					odfTable.getOdfElement().setProperty(StyleTablePropertiesElement.KeepWithNext, "auto");
					for (int posCol = 0; posCol < odfTable.getColumnCount(); posCol++) {
						OdfTableColumn colonna = odfTable.getColumnByIndex(posCol);
						for (int posRow = 0; posRow < colonna.getCellCount(); posRow ++) {
							AttributeSet cellStyle = mappaTablesStyle.get(idTable).getListaStiliRow().get(posRow).get(posCol);
	
							// setto la larghezza della colonna posCol
							colonna.setUseOptimalWidth(false);
							
							getWidthCols(mappaTablesStyle.get(idTable));	
							String colWidthUnit = cellStyle.getAttribute(CSS.Attribute.WIDTH) != null ? cellStyle.getAttribute(CSS.Attribute.WIDTH).toString() : null;
							ArrayList<Float> colsWidth = mappaTablesStyle.get(idTable).getListaWidthColonne();
	
							if (StringUtils.isNotBlank(colWidthUnit) && colWidthUnit.length() >=2 && !colWidthUnit.toLowerCase().contains("nan")) {
	//							Float colWidthFloat = convertMeasure(colWidthUnit);
								if (colsWidth.get(posCol) != null) {
									colonna.setWidth(colsWidth.get(posCol).longValue());//colWidthFloat.longValue());
								}
							}
	
							OdfTableCell cella = odfTable.getCellByPosition(posCol, posRow);
							cella.getOdfElement().setProperty(StyleTableCellPropertiesElement.Padding, "0.15cm");
	
							if (cellStyle.getAttribute(CSS.Attribute.TEXT_ALIGN) != null) {
								cella.setHorizontalAlignment(cellStyle.getAttribute(CSS.Attribute.TEXT_ALIGN).toString());
							} else {
								cella.setHorizontalAlignment("justify");
							}
							
							if (cellStyle.getAttribute(CSS.Attribute.VERTICAL_ALIGN) != null) {
								cella.setVerticalAlignment(cellStyle.getAttribute(CSS.Attribute.VERTICAL_ALIGN).toString());
							} else {
								cella.setVerticalAlignment("middle");
							}
							
							if (cellStyle.getAttribute(CSS.Attribute.BACKGROUND_COLOR) != null ) {
								StyleSheet styleSheet = new StyleSheet();
								if (styleSheet.stringToColor(cellStyle.getAttribute(CSS.Attribute.BACKGROUND_COLOR).toString()) != null) {
									Color backgroundColor = new Color(styleSheet.stringToColor(cellStyle.getAttribute(CSS.Attribute.BACKGROUND_COLOR).toString()));
									cella.setCellBackgroundColor(backgroundColor);
								}
							}
							cella.getOdfElement().setProperty(StyleParagraphPropertiesElement.KeepTogether, "auto");
							cella.getOdfElement().setProperty(StyleParagraphPropertiesElement.KeepWithNext, "auto");
							cella.getOdfElement().setProperty(StyleParagraphPropertiesElement.LineSpacing, "1");
						}
					}
				}
			}
	
			File templateOdtToconvert = File.createTempFile("temp", ".odt");
	
			odtFinale.save(templateOdtToconvert);
			
			return templateOdtToconvert;
		} finally {
			if(odtFinale != null) {
				try {
					odtFinale.close();
				} catch (Exception e) {}
			}
		}
	}

	private static Float convertMeasure(String measureWithUnit) {
		
		if (StringUtils.isNotBlank(measureWithUnit) && measureWithUnit.length() >= 2) {
			String unit;
			String measure;
			if (measureWithUnit.contains("%")) {
				unit = measureWithUnit.substring(measureWithUnit.length() - 1, measureWithUnit.length());
				measure = measureWithUnit.substring(0, measureWithUnit.length() - 1);
			} else {
				unit = measureWithUnit.substring(measureWithUnit.length() - 2, measureWithUnit.length());
				measure = measureWithUnit.substring(0, measureWithUnit.length() - 2);
			}
			
			switch (unit) {
			case "pt":
				Float measurePtFloat = Utilities.pointsToMillimeters(Float.parseFloat(measure));
				return measurePtFloat;
			case "in":
				Float measureInFloat = Utilities.inchesToMillimeters(Float.parseFloat(measure));
				return measureInFloat;
			case "cm":
				Float measureCmFloat = Float.parseFloat(measure)*10;
				return measureCmFloat;
			case "px":
				int fattoreConversionePxMm = 4;
				Float measurePxFloat = Float.parseFloat(measure)/fattoreConversionePxMm;
				return measurePxFloat;
			case "%":
				Float measurePercFloat = Float.parseFloat(measure);
				return measurePercFloat;
			default:
				Float measureDefaultFloat = Utilities.pointsToMillimeters(Float.parseFloat(measure));
				return measureDefaultFloat;
			}
		}
		return null;
	}

	private static File cambiaCarattereSezioni(File templateOdtWithValues,File templateOdt, Map<String, TableStyleBean> mappaTablesStyle, Map<String, Object> listsStyle, ModelliDocBean modelliDocBean, Map<String, Object> htmlSectionsModel) throws Exception {
		
		TextDocument odtModello = null;
		TextDocument odtFinale = null;
		try {
			odtModello = TextDocument.loadDocument(templateOdt.getPath());
			odtFinale = TextDocument.loadDocument(templateOdtWithValues.getPath());
		
			//Prendo le varie sezioni dal modello ODT
			Iterator<Section> iteratorSezioni=odtModello.getSectionIterator();
			while(iteratorSezioni.hasNext()) {
				String sezione = iteratorSezioni.next().getName();
				// Devo elaborare solamente le sezioni associate ad una iniezione html
				if (htmlSectionsModel != null && htmlSectionsModel.containsKey(sezione)) {
					// Elimino il primo paragrafo vuoto da ogni sezione
					eliminaPrimoParagrafoVuoto(odtModello, odtFinale, sezione, mappaTablesStyle, listsStyle);
					// Cambio i caratteri della sezione
					cambiaCarattereSezione(odtModello, odtFinale, sezione, mappaTablesStyle, listsStyle, modelliDocBean);
				}
			}
	
			File templateOdtToconvert = File.createTempFile("temp", ".odt");
			
			odtFinale.save(templateOdtToconvert);
				
			return templateOdtToconvert;
		} finally {
			if(odtFinale != null) {
				try {
					odtFinale.close();
				} catch (Exception e) {}
			}
			if(odtModello != null) {
				try {
					odtModello.close();
				} catch (Exception e) {}
			}
		}
	}
	
	private static void eliminaPrimoParagrafoVuoto(TextDocument odtModello, TextDocument odtFinale, String nomeSection, Map<String, TableStyleBean> mappaTablesStyle, Map<String, Object> listsStyle) throws Exception {
		Section section = odtFinale.getSectionByName(nomeSection);
		Iterator<Paragraph> iterator = section.getParagraphIterator();
		if (iterator!= null && iterator.hasNext()) {
			Paragraph par = iterator.next();
			if (par != null){
			String content = par.getTextContent();
				if (StringUtils.isBlank(content)) {
					// Decommentare per rimuovere  il primo paragrafo vuoto che viene inserito nell'inizezione
					//par.remove();
				}
			}
		}
	}
	
	private static void cambiaCarattereSezione(TextDocument odtModello, TextDocument odtFinale, String nomeSection, Map<String, TableStyleBean> mappaTablesStyle, Map<String, Object> listsStyle, ModelliDocBean modelliDocBean) throws Exception {
		
		String variabileFontName = null;
		String variabileFontSize = null;
		String variabileStyleName = null;
				
		Section section = odtModello.getSectionByName(nomeSection);
		Iterator<Paragraph> iterator = section.getParagraphIterator();
		if (iterator.hasNext()) {
			Paragraph par = iterator.next();
			// Recupero lo span contenete la variabile su cui iniettare il testo
			StyleTextPropertiesElement styleText = null;
			
			if (par.getFrameContainerElement().getFirstChild() != null && par.getFrameContainerElement().getFirstChild() instanceof OdfTextSpan) {
				try {
					OdfTextSpan spam = (OdfTextSpan) par.getFrameContainerElement().getFirstChild();
					NodeList listaNodi = spam.getAutomaticStyle().getChildNodes();
					if (listaNodi != null) {
						 for (int i = 0; i < listaNodi.getLength(); i++) {
							 if (listaNodi.item(i) instanceof StyleTextPropertiesElement) {
								 styleText = (StyleTextPropertiesElement) listaNodi.item(i);
								 variabileFontName = styleText.getStyleFontNameAttribute();
								 variabileFontSize = styleText.getFoFontSizeAttribute();
								 variabileStyleName = spam.getAutomaticStyle().getStyleNameAttribute();
							 }
						 }
					}
				} catch (Exception e) {
					logger.error("Errore nel recupero dello style nella section " + nomeSection, e);
				}
			} else if (par.getOdfElement() != null && par.getOdfElement().getAutomaticStyle() != null) {
				try {
					NodeList listaNodi = par.getOdfElement().getAutomaticStyle().getChildNodes();
					if (listaNodi != null) {
						for (int i = 0; i < listaNodi.getLength(); i++) {
							if (listaNodi.item(i) instanceof StyleTextPropertiesElement) {
								styleText = (StyleTextPropertiesElement) listaNodi.item(i);
								variabileFontName = styleText.getStyleFontNameAttribute();
								variabileFontSize = styleText.getFoFontSizeAttribute();
								variabileStyleName = par.getOdfElement().getAutomaticStyle().getStyleNameAttribute();
							}
						 }
					}
				} catch (Exception e) {
					logger.error("Errore nel recupero dello style nella section " + nomeSection, e);
				}
			}
			
			if (StringUtils.isBlank(variabileFontName)) {
				try {
					variabileFontName = par.getFont().getFamilyName();
				} catch (Exception e1) {
					// Se non sono ancora riuscito a recuperare il fontName vuol dire che viene usato quello predefinito del documento
					// Tento di recuperarlo
					try {
						OdfNamespace fontNameNameSpace = OdfNamespace.newNamespace("fo", "urn:oasis:names:tc:opendocument:xmlns:style:1.0");
			            OdfName fontNameOdfName = OdfName.newName(fontNameNameSpace, "font-name");
			            OdfStyleProperty  fontNameProp = OdfStyleProperty.get(OdfStylePropertiesSet.TextProperties, fontNameOdfName);
			            String styleFamily = par.getOdfElement().getAutomaticStyle().getStyleFamilyAttribute();
						OdfStyleFamily odfStyleFamily = OdfStyleFamily.getByName(styleFamily);
						variabileFontName = odtModello.getDocumentStyles().getDefaultStyle(odfStyleFamily).getProperty(fontNameProp);
					} catch (Exception e2) {
						logger.error("Non sono riuscito a recuperare il fontName della section" + nomeSection + " nel modello " + modelliDocBean.getIdModello() + " " + modelliDocBean.getNomeModello() + " " + modelliDocBean.getNomeFileModello());
					}
				}
			}
			//In acuni casi mi restituisce gli apici e vanno tolti
			if (variabileFontName != null && variabileFontName.contains("'")) {
				try{
					variabileFontName = variabileFontName.substring(1,(variabileFontName.length() - 1));
				} catch (Exception e) {
					logger.error("Errore nel recupero di variabileFontName");
				}
			}
			//In alcuni casi mi restituisce 1 o 2 alla fine del nome del carattere e vanno tolti
			if (variabileFontName != null && (((variabileFontName.charAt(variabileFontName.length()-1)=='1') || (variabileFontName.charAt(variabileFontName.length()-1)=='2')) && (variabileFontName.charAt(variabileFontName.length()-2)!=' '))) {
				try {
					variabileFontName = variabileFontName.substring(0,(variabileFontName.length() - 1));
				} catch (Exception e) {
					logger.error("Errore nel recupero di variabileFontName");
				}
			}
			
			if (StringUtils.isBlank(variabileFontSize)) {
				try {
					variabileFontSize = par.getFont().getSize() + "pt";
				} catch (Exception e1) {
					// Se non sono ancora riuscito a recuperare il fontSize vuol dire che viene usato quello predefinito del documento
					// Tento di recuperarlo
					try {
						OdfNamespace fontSizeNameSpace = OdfNamespace.newNamespace("fo", "urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0");
						OdfName fontSizeOdfName = OdfName.newName(fontSizeNameSpace, "font-size");
						OdfStyleProperty  fontSizeProp = OdfStyleProperty.get(OdfStylePropertiesSet.TextProperties, fontSizeOdfName);
						String styleFamily = par.getOdfElement().getAutomaticStyle().getStyleFamilyAttribute();
						OdfStyleFamily odfStyleFamily = OdfStyleFamily.getByName(styleFamily);
						variabileFontSize = odtModello.getDocumentStyles().getDefaultStyle(odfStyleFamily).getProperty(fontSizeProp);
					} catch (Exception e2) {
						logger.error("Non sono riuscito a recuperare il fontSize della section" + nomeSection + " nel modello " + modelliDocBean.getIdModello() + " " + modelliDocBean.getNomeModello() + " " + modelliDocBean.getNomeFileModello());
					}
				}
				
			}

			// Se non ho ancora recuperato il font metto quello salvato in DB o quello cablato
			if (StringUtils.isBlank(variabileFontName)) {
				variabileFontName = StringUtils.isNotBlank(VARIABILE_FONT_NAME) ? VARIABILE_FONT_NAME : "Book Antiqua";	
			}
			if (StringUtils.isBlank(variabileFontSize)) {
				variabileFontSize = StringUtils.isNotBlank(VARIABILE_FONT_SIZE) ? VARIABILE_FONT_SIZE : "11.0pt";
			}
			
			if (variabileStyleName == null) {
				variabileStyleName = par.getStyleName();
			}
		}

		Section sectionFinale = odtFinale.getSectionByName(nomeSection);

		// FIXME Se section finale è vuota cosa devo fare? Una volta è capitato ed è andato in errore per null pointer
		settaFontSection(sectionFinale, variabileFontName, variabileFontSize, variabileStyleName, mappaTablesStyle, listsStyle);
	}
	
	private static void settaFontSection( Section sectionFinale, String variabileFontName, String variabileFontSize, String variabileStyleName, Map<String, TableStyleBean> mappaTablesStyle, Map<String, Object> listsStyle) {
		Iterator<Paragraph> iteratorParagrafi = sectionFinale.getParagraphIterator();
		while (iteratorParagrafi.hasNext()) {
			Paragraph par = iteratorParagrafi.next();
			//Controllo se il paragrafo non è una riga vuota
			String textContent = par.getTextContent();
			if (!textContent.trim().isEmpty()) {
				
				checkReplaceBR(par.getOdfElement());
				Font font = par.getFont();
//				try {
//					font = par.getFont();
//				} catch (Exception e) {
//					font = new Font(variabileFontName, FontStyle.REGULAR , Double.parseDouble(variabileFontSize.replace("pt", "")));
//				}
				font.setFamilyName(variabileFontName);
				font.setSize(Double.parseDouble(variabileFontSize.replace("pt", "")));
				par.setFont(font);
				
				OdfStylableElement odfParagraph = (OdfStylableElement) par.getOdfElement();
				odfParagraph.setProperty(StyleTextPropertiesElement.FontName, variabileFontName);
				odfParagraph.setProperty(StyleTextPropertiesElement.FontNameAsian, variabileFontName);
				odfParagraph.setProperty(StyleTextPropertiesElement.FontNameComplex, variabileFontName);
				odfParagraph.setProperty(StyleTextPropertiesElement.FontSize, variabileFontSize + "pt");
				odfParagraph.setProperty(StyleTextPropertiesElement.FontSizeAsian, variabileFontSize + "pt");
				odfParagraph.setProperty(StyleTextPropertiesElement.FontSizeComplex, variabileFontSize + "pt");
				odfParagraph.setProperty(StyleParagraphPropertiesElement.KeepTogether, "auto");
				odfParagraph.setProperty(StyleParagraphPropertiesElement.KeepWithNext, "auto");
				odfParagraph.setProperty(StyleParagraphPropertiesElement.LineSpacing, "1");
				
				if (odfParagraph.getProperty(OdfParagraphProperties.TextAlign) == null) {
					odfParagraph.setProperty(OdfParagraphProperties.TextAlign, "justify");
				}
				
				if (par.getOdfElement().getFirstChild() != null) {
					try {
						odfParagraph = (OdfStylableElement) par.getOdfElement().getFirstChild();
					} catch (Exception e) {
						logger.debug("SEZIONE DEL MODELLO DA NON INIETTARE" + sectionFinale.getName(), e);
						return;
					}
					odfParagraph.setProperty(StyleTextPropertiesElement.FontName, variabileFontName);
					odfParagraph.setProperty(StyleTextPropertiesElement.FontNameAsian, variabileFontName);
					odfParagraph.setProperty(StyleTextPropertiesElement.FontNameComplex, variabileFontName);
					odfParagraph.setProperty(StyleTextPropertiesElement.FontSize, variabileFontSize + "pt");
					odfParagraph.setProperty(StyleTextPropertiesElement.FontSizeAsian, variabileFontSize + "pt");
					odfParagraph.setProperty(StyleTextPropertiesElement.FontSizeComplex, variabileFontSize + "pt");
					odfParagraph.setProperty(StyleParagraphPropertiesElement.KeepTogether, "auto");
					odfParagraph.setProperty(StyleParagraphPropertiesElement.KeepWithNext, "auto");
					odfParagraph.setProperty(StyleParagraphPropertiesElement.LineSpacing, "1");
				}
			}
		}
		
		// setto l'id alle tabelle della sezione, mi serve per applicare gli stili alle tabelle
		Iterator<Table> iteratorTabelle = sectionFinale.getTableList().iterator();
		int count = 0;
		while (iteratorTabelle.hasNext()) {
			count++;
			Table tabella = iteratorTabelle.next();
			String idTable = sectionFinale.getName() + "_Tab" + count;
			tabella.getOdfElement().setAttribute("idTableCKEDITOR", idTable);
			if (StringUtils.isNotBlank(idTable) && mappaTablesStyle != null && !mappaTablesStyle.isEmpty() && mappaTablesStyle.containsKey(idTable) && mappaTablesStyle.get(idTable).getListaStiliRow() != null && !mappaTablesStyle.get(idTable).getListaStiliRow().isEmpty()) {

				for (int rowPos = 0; rowPos < tabella.getRowCount(); rowPos++) {
					AttributeSet cellStyle = mappaTablesStyle.get(idTable).getListaStiliRow().get(rowPos).get(0);

					// setto l'altezza della riga posRow
					String rowHeightUnit = cellStyle.getAttribute(CSS.Attribute.HEIGHT) != null ? cellStyle.getAttribute(CSS.Attribute.HEIGHT).toString() : null;
					if (StringUtils.isNotBlank(rowHeightUnit) && rowHeightUnit.length() >= 2) { 
						Float rowHeightFloat = convertMeasure(rowHeightUnit);
						Row riga = tabella.getRowByIndex(rowPos);
						riga.setHeight(rowHeightFloat.doubleValue(), true);
					}
					
					// setto qui un bordo di default TODO: vedere se si riesce a recuperare da cellStyle
					for (int colPos = 0; colPos < tabella.getColumnCount(); colPos++) {	
						Cell cella = tabella.getCellByPosition(colPos, rowPos);
						if (cella != null) {
							cella.setBorders(CellBordersType.ALL_FOUR, new Border(Color.BLACK, 0.5, SupportedLinearMeasure.PT));
							
							for (int i = 0; i < cella.getOdfElement().getFirstChild().getChildNodes().getLength(); i++) {
								checkReplaceBR((OdfElement)cella.getOdfElement().getFirstChild().getChildNodes().item(i));
							}
							
							Font font = cella.getFont();
							font.setFamilyName(variabileFontName);
							font.setSize(Double.parseDouble(variabileFontSize.replace("pt", "")));
							cella.setFont(font);
						}
					}
				}
				
			}
		}
		
//		int nOrderedListsModello = 0;
//		int nUnorderedListsModello = 0;
		
		/** FIXBUG: Antonio 10/03/2021
		 * 
		 * Bean contenente i due contatori per le liste ordinate <ol> e le non ordinate <ul>
		 * 
		 * è stato modificato come bean perche prima sfalsavano le chiavi delle liste nelle funzioni ricorsive perche passate come valore e l'incremento andava perso
		 * 
		 * */
		CountersListsBean counterListBean = new CountersListsBean();
		
		int nItemListsModello = 0;
		
		Iterator<org.odftoolkit.simple.text.list.List> iteratorListe = sectionFinale.getListIterator();
		while (iteratorListe.hasNext()) {
	    	org.odftoolkit.simple.text.list.List lista = iteratorListe.next();
	    	if (!lista.getItems().isEmpty()) {
	    		if (lista.getType().equals(ListType.NUMBER)) {
	    			counterListBean.incrementNOrderedListsModello();
	    			Integer oListStart = (Integer) listsStyle.get("%#oListStart" + counterListBean.getnOrderedListsModello() + "#%sect_"+ sectionFinale.getName());
	    			if (oListStart != null && lista.getItem(0) != null) {
	    				lista.getItem(0).setStartNumber(oListStart);
	    			}
	    			AttributeSet style = (AttributeSet) listsStyle.get("%#oListStyle" + counterListBean.getnOrderedListsModello() + "#%sect_"+ sectionFinale.getName());

	    			setStileLista(lista, style, sectionFinale.getOwnerDocument());
	    		} else {
	    			counterListBean.incrementNUnorderedListsModello();
	    			AttributeSet style = (AttributeSet) listsStyle.get("%#uListStyle" + counterListBean.getnUnorderedListsModello() + "#%sect_"+ sectionFinale.getName());

	    			setStileLista(lista, style, sectionFinale.getOwnerDocument());
	    		}
	    	}
	    	
	    	java.util.List<ListItem> listaItem = lista.getItems();
	    	for (ListItem listItem : listaItem) {
	    		nItemListsModello++;
	    		setFontListInSection(sectionFinale, listItem, variabileFontName, variabileFontSize, listsStyle, counterListBean, nItemListsModello);
			}
	    }
	}
	
	//Funzione che applica il carattere alle liste delle liste ricorsivamente
	private static void setFontListInSection(Section sectionFinale, ListItem listItem, String variabileFontName, String variabileFontSize, Map<String, Object> listsStyle, CountersListsBean counterListBean, int nItemListsModello) {

		if (listItem != null && listItem.getOdfElement() != null && listItem.getOdfElement().getFirstChild() != null 
				&& listItem.getOdfElement().getFirstChild() instanceof OdfTextParagraph) {
			
			for (int i = 0; i < listItem.getOdfElement().getFirstChild().getChildNodes().getLength(); i++) {
				checkReplaceBR((OdfElement)listItem.getOdfElement().getFirstChild().getChildNodes().item(i));
			}
			
			OdfTextParagraph listTextParagraph = (OdfTextParagraph) listItem.getOdfElement().getFirstChild();

			AttributeSet itemStyle = (AttributeSet)listsStyle.get("%#ListItem" + nItemListsModello + "#%sect_"+ sectionFinale.getName());
			if (itemStyle.getAttribute(CSS.Attribute.TEXT_ALIGN) != null) {
				listTextParagraph.setProperty(OdfParagraphProperties.TextAlign, itemStyle.getAttribute(CSS.Attribute.TEXT_ALIGN).toString());
			} else {
				listTextParagraph.setProperty(OdfParagraphProperties.TextAlign, "justify");
			}
			
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontName, variabileFontName);
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontNameAsian, variabileFontName);
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontNameComplex, variabileFontName);
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontSize, variabileFontSize + "pt");
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontSizeAsian, variabileFontSize + "pt");
			listTextParagraph.setProperty(StyleTextPropertiesElement.FontSizeComplex, variabileFontSize + "pt");
			listTextParagraph.setProperty(StyleParagraphPropertiesElement.KeepTogether, "auto");
			listTextParagraph.setProperty(StyleParagraphPropertiesElement.KeepWithNext, "auto");
			listTextParagraph.setProperty(StyleParagraphPropertiesElement.LineSpacing, "1");

			Iterator<org.odftoolkit.simple.text.list.List> iteratorListe = listItem.getListIterator();
			while (iteratorListe.hasNext()) {
				org.odftoolkit.simple.text.list.List lista = iteratorListe.next();
				if (lista.getType().equals(ListType.NUMBER)) {
					counterListBean.incrementNOrderedListsModello();
					Integer oListStart = (Integer) listsStyle.get("%#oListStart" + counterListBean.getnOrderedListsModello() + "#%sect_"+ sectionFinale.getName());
					if (oListStart != null && lista.getItem(0) != null) {
						lista.getItem(0).setStartNumber(oListStart);
					}
					AttributeSet style = (AttributeSet) listsStyle.get("%#oListStyle" + counterListBean.getnOrderedListsModello() + "#%sect_"+ sectionFinale.getName());

					setStileLista(lista, style, sectionFinale.getOwnerDocument());
				} else {
					counterListBean.incrementNUnorderedListsModello();
					AttributeSet style = (AttributeSet) listsStyle.get("%#uListStyle" + counterListBean.getnUnorderedListsModello() + "#%sect_"+ sectionFinale.getName());

					setStileLista(lista, style, sectionFinale.getOwnerDocument());
				}

				java.util.List<ListItem> listaItem = lista.getItems();
				for (ListItem subListItem : listaItem) {
					nItemListsModello++;
					setFontListInSection(sectionFinale, subListItem, variabileFontName, variabileFontSize, listsStyle, counterListBean, nItemListsModello);
				}
			}
		}
	}
	
	private static void setStileLista(org.odftoolkit.simple.text.list.List lista, AttributeSet styleLista, org.odftoolkit.simple.Document document) {
		String style = styleLista.getAttribute(CSS.Attribute.LIST_STYLE_TYPE) != null ? styleLista.getAttribute(CSS.Attribute.LIST_STYLE_TYPE).toString() : "";
		switch (style) {
		case Value.UPPER_ROMAN:
			lista.setDecorator(new NumberedRomanUpperDecorator(document));	
			break;
		case Value.LOWER_ROMAN:
			lista.setDecorator(new NumberedRomanLowerDecorator(document));	
			break;
		case Value.LOWER_ALPHA:
			lista.setDecorator(new NumberedAlphaLowerDecorator(document));	
			break;
		case Value.UPPER_ALPHA:
			lista.setDecorator(new NumberedAlphaUpperDecorator(document));	
			break;
		case Value.LOWER_GREEK:
			lista.setDecorator(new NumberedGreekLowerDecorator(document));	
			break;
		case Value.DISC:
			lista.setDecorator(new DiscDecorator(document));	
			break;
		case Value.CIRCLE:
			lista.setDecorator(new CircleDecorator(document));	
			break;
		case Value.SQUARE:
			lista.setDecorator(new SquareDecorator(document));	
			break;
		default:
			if (lista.getType().equals(ListType.NUMBER)) {
				lista.setDecorator(new NumberDecorator(document));
			} else {
				switch (lista.getLevel()) {
				case 1:
					lista.setDecorator(new DiscDecorator(document));
					break;
				case 2:
					lista.setDecorator(new CircleDecorator(document));
					break;
				case 3:
				default:
					lista.setDecorator(new SquareDecorator(document));
					break;
				}
			}
			break;
		}
	}
	
	public static void mergeHtmlSections(String nomeVariabile, File fileOdt, File fileOdtWithValues) throws FreeMarkerMergeHtmlSectionsException {
		OdfPackage odfPackage = null;
		OdfDocument odfDocument = null;			
		TextSectionElement sectionOrig = null;
		try {	        	
        	odfPackage = OdfPackage.loadPackage(fileOdt);  
        	odfDocument = OdfTextDocument.loadDocument(odfPackage);
        	if (getSectionByName(odfDocument, nomeVariabile) == null) {
        		// Probabilmente ho profilato per errore una variabile come testo html, e nel modello non è presente una section con quel nome
        		logger.error("Errore durante l'esecuzione del metodo mergeHtmlSections: Sezione " + nomeVariabile + " non presente nel modello");
        		throw new Exception("La variabile " + nomeVariabile + " è profilata come testo html, ma nel modello non è presente la relativa sezione");
        	}
        	sectionOrig = (TextSectionElement) getSectionByName(odfDocument, nomeVariabile).cloneNode(true);
        } catch(Exception e){
        	logger.error("Errore durante l'esecuzione del metodo mergeHtmlSections: " + e.getMessage(), e);
        	throw new FreeMarkerMergeHtmlSectionsException(e.getMessage(), e);
        } finally {
        	if(odfDocument != null) {
        		try {
        			odfDocument.close();
        		} catch (Exception e) {}
        	}        	
        	if(odfPackage != null) {
        		try {
        			odfPackage.close();
        		} catch (Exception e) {}    		
        	}        	
        }			
		try {	        
			odfPackage = OdfPackage.loadPackage(fileOdtWithValues);  
        	odfDocument = OdfTextDocument.loadDocument(odfPackage);		
        	TextSectionElement section = getSectionByName(odfDocument, nomeVariabile);
        	// Se section è null vuol dire che nel primo step di iniezione è stata tolta (ad esempio si trova dentro un blocco if che non
        	// deve essere visualizzato)
        	if (section != null) {
        		Node importedNode = odfDocument.getContentDom().importNode(sectionOrig, true);
        		section.getParentNode().replaceChild(importedNode, section);
        	}
			odfDocument.save(fileOdtWithValues);
        } catch(Exception e){
        	logger.error("Errore durante l'esecuzione del metodo mergeHtmlSections: " + e.getMessage(), e);
        	throw new FreeMarkerMergeHtmlSectionsException(e.getMessage(), e);
        } finally {
        	if(odfDocument != null) {
        		try {
        			odfDocument.close();
        		} catch (Exception e) {}    		
        	}        	
        	if(odfPackage != null) {
        		try {
        			odfPackage.close();
        		} catch (Exception e) {}         		
        	}        	
        }		
	}
	
	public static TextSectionElement getSectionByName(OdfDocument odfDocument, String name) {
	    TextSectionElement element;
	    try {
	      OdfElement root = odfDocument.getContentDom().getRootElement();
	      OfficeBodyElement officeBody = OdfElement.findFirstChildNode(OfficeBodyElement.class, root);
	      XPath xpath = odfDocument.getContentDom().getXPath();
	      String xpathValue = ".//text:section[@text:name=\"" + name + "\"]";
	      element = (TextSectionElement) xpath.evaluate(xpathValue, officeBody, XPathConstants.NODE);
	      if (element != null) {
	    	  return element;
	      }
	      root = odfDocument.getStylesDom().getRootElement();
	      OfficeMasterStylesElement masterStyle = OdfElement.findFirstChildNode(OfficeMasterStylesElement.class, root);
	      xpath = odfDocument.getStylesDom().getXPath();
	      element = (TextSectionElement) xpath.evaluate(".//text:section[@text:name=\"" + name + "\"]", masterStyle, XPathConstants.NODE);
	      if (element != null) {
	    	  return element;
	      }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
	}
	
	private static File createPdfFromOdt(File templateOdtWithValues, HttpSession session) throws Exception {
		
		File templatePdfWithValues = File.createTempFile("temp", ".pdf");
		
		boolean generaPdfa = ParametriDBUtil.getParametroDBAsBoolean(session, "GENERAZIONE_DA_MODELLO_ABILITA_PDFA");

		try {
			OpenOfficeConverter.newInstance().convertByOutExt(templateOdtWithValues, "application/vnd.oasis.opendocument.text", templatePdfWithValues, "pdf");
		} catch (Exception e) {
			logger.error("Errore durante la conversione con OpenOffice", e);
 			throw new StoreException("Il servizio di conversione in pdf del testo è momentaneamente non disponibile. Se il problema persiste contattare l'assistenza");
		}
		
		if (generaPdfa) {
            templatePdfWithValues = PdfUtility.convertiPdfToPdfA3U(templatePdfWithValues);
        }

		return templatePdfWithValues;
	}
	
//	public static File convertToPdfA1(File in) throws DocumentException, IOException, URISyntaxException {
//
//		File outputPdf1A = File.createTempFile("conv1A", ".pdf");
//
//		com.itextpdf.text.Document document = new com.itextpdf.text.Document();
//
//		URL url = FreeMarkerModelliUtil.class.getResource("/srgb_color_space_profile.icm");
//		System.out.println(url);
//		InputStream iccFile = FreeMarkerModelliUtil.class.getResourceAsStream("/srgb_color_space_profile.icm");
//
//		ICC_Profile icc = ICC_Profile.getInstance(iccFile);
//
//		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputPdf1A));
//
//		PdfAWriter writer = PdfAWriter.getInstance(document, output, PdfAConformanceLevel.PDF_A_1A);
//		//PdfAWriter writer = PdfAWriter.getInstance(document, output, PdfAConformanceLevel.PDF_A_3U);
//		writer.createXmpMetadata();
//		writer.setTagged();
//		writer.setLanguage("it");
//		writer.setLinearPageMode();
//
//		document.open();
//
//		writer.setOutputIntents("Custom", "", "http://www.color.org", "adobe rgb", icc);
//
//		PdfContentByte cb = writer.getDirectContent();
//
//		// Collego il reader al file
//		PdfReader reader = new PdfReader(in.getAbsolutePath());
//
//		// Scorro le pagine da copiare
//		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
//			addPageToDocument(document, writer, cb, reader, i, false);
//
//		}
//
//		document.close();
//		output.flush();
//		output.close();
//		reader.close();
//
//		return outputPdf1A;
//	}
	
	public static Map<String, Object> createMapToFillTemplate(ModelliDocBean bean, HttpSession session) throws BadElementException, ParseException, IOException{
		
		LinkedHashMap<String, AssociazioniAttributiCustomBean> associazioniAttributiCustomMap = getAssociazioniAttributiCustomMap(bean);
		
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new LinkedHashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new LinkedHashMap<String, String>();
		Map<String, String> colonneListe = bean.getColonneListe() != null ? bean.getColonneListe() : new LinkedHashMap<String, String>();
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		List<String> nomiSottoVariabiliLista = new ArrayList<String>();
		
		for (String nomeVariabile : associazioniAttributiCustomMap.keySet()) {
			AssociazioniAttributiCustomBean associazione = associazioniAttributiCustomMap.get(nomeVariabile);
			String nomeAttributoCustom = associazione != null && StringUtils.isNotBlank(associazione.getNomeAttributoCustom()) && "attributoCustom".equalsIgnoreCase(associazione.getTipoAssociazioneVariabileModello()) ? associazioniAttributiCustomMap.get(nomeVariabile).getNomeAttributoCustom() : nomeVariabile;
			String tipo = (String) tipiValori.get(nomeAttributoCustom);
			if ("LISTA".equals(tipo) || (associazione.getFlgRipetibile() != null && Boolean.valueOf(associazione.getFlgRipetibile()))) {
				List<Map> valoreLista = valori.get(nomeAttributoCustom) != null ? (List<Map>) valori.get(nomeAttributoCustom) : new ArrayList<Map>();
				String prefix = nomeAttributoCustom + ".";				
				if(isTrue(associazioniAttributiCustomMap.get(nomeVariabile).getFlgComplex())) {
					List<Map<String, Object>> valoreListaModel = new ArrayList<Map<String, Object>>();
					for(int r = 0; r < valoreLista.size(); r++) {
						Map<String, Object> valoriColonneRigaModel = new LinkedHashMap<String, Object>();
						java.util.HashSet<Object> cols = new java.util.HashSet<Object>();
						for(String col : colonneListe.keySet()) {
							if(col.startsWith(prefix)) {
								cols.add(col.substring(prefix.length()));
							}
						}						
						for(Object c : cols) {
							String numeroColonna = (String) c;
							String nomeAttributoCustomColonna = colonneListe.get(nomeAttributoCustom + "." + numeroColonna);
							List<String> listaNomiVariabiliColonna = getListaNomiVariabiliFromNomeAttributoCustom(nomeAttributoCustomColonna, bean, associazione.getAliasVariabileModello());	
							if(listaNomiVariabiliColonna != null) {
								for (int n = 0; n < listaNomiVariabiliColonna.size(); n++) {
									String nomeVariabileColonna = listaNomiVariabiliColonna.get(n);
									Object valoreVariabileColonna = null; 
									String tipoColonna = (String) tipiValori.get(nomeAttributoCustom + "." + numeroColonna);
									if(valoreLista.get(r).get(c) != null) {	
										if ("DATE".equals(tipoColonna)) {
											valoreVariabileColonna = getDateModelValue(valoreLista.get(r).get(c));
										} else if ("DATETIME".equals(tipoColonna)) {
											valoreVariabileColonna = getDateTimeModelValue(valoreLista.get(r).get(c));
										} else if ("CHECK".equals(tipoColonna)) {
											valoreVariabileColonna = getCheckModelValue(valoreLista.get(r).get(c));
										} else if ("CKEDITOR".equals(tipoColonna)) {
											String htmlFixedTable = fixHTMLCKEDITOR(valoreLista.get(r).get(c), session);
											valoreVariabileColonna = getHtmlModelValue(htmlFixedTable);
//											valoreVariabileColonna = getHtmlModelValue(valoreLista.get(r).get(c));
										} else {
											valoreVariabileColonna = getTextModelValue(valoreLista.get(r).get(c));
										}
									} else {
										valoreVariabileColonna = "";
									}
									
									if(valoreVariabileColonna != null && !"".equals(valoreVariabileColonna)) {					
										Boolean flgBarcodeColonna = associazioniAttributiCustomMap.get(nomeVariabileColonna) != null ? associazioniAttributiCustomMap.get(nomeVariabileColonna).getFlgBarcode() : null;	
										Boolean flgImageColonna = associazioniAttributiCustomMap.get(nomeVariabileColonna) != null ? associazioniAttributiCustomMap.get(nomeVariabileColonna).getFlgImage() : null;
										if(flgBarcodeColonna != null && flgBarcodeColonna) {
											String tipoBarcode = associazioniAttributiCustomMap.get(nomeVariabileColonna) != null ? associazioniAttributiCustomMap.get(nomeVariabileColonna).getTipoBarcode() : null;						
											String strBarcode = (String) valoreVariabileColonna;
											ImpostazioniBarcodeBean impostazioniBarcodeBean = getImpostazioniImmagineBarCode(tipoBarcode);
											valoreVariabileColonna = BarcodeUtility.getImageProvider(strBarcode, impostazioniBarcodeBean);
										} else if (flgImageColonna != null && flgImageColonna){
											valoreVariabileColonna = getImageModelValue(valoreVariabileColonna, session);
										}
									}
									valoriColonneRigaModel.put(nomeVariabileColonna.substring(nomeVariabileColonna.indexOf(".") + 1), valoreVariabileColonna);
									
									if(!nomiSottoVariabiliLista.contains(nomeVariabileColonna)) {
										nomiSottoVariabiliLista.add(nomeVariabileColonna);
									}
								}
							}
							
						}					
						valoreListaModel.add(valoriColonneRigaModel);
					}
					model.put(nomeVariabile, valoreListaModel);
				} else {
					List<Object> valoreListaModel = new ArrayList<Object>();
					for(int r = 0; r < valoreLista.size(); r++) {
						Object valoreVariabileColonna = null; 
						String tipoColonna = (String) tipiValori.get(nomeAttributoCustom);						
						if(valoreLista.get(r).get("1") != null) {	
							if ("DATE".equals(tipoColonna)) {
								valoreVariabileColonna = getDateModelValue(valoreLista.get(r).get("1"));
							} else if ("DATETIME".equals(tipoColonna)) {
								valoreVariabileColonna = getDateTimeModelValue(valoreLista.get(r).get("1"));
							} else if ("CHECK".equals(tipoColonna)) {
								valoreVariabileColonna = getCheckModelValue(valoreLista.get(r).get("1"));
							} else if ("CKEDITOR".equals(tipoColonna)) {
								String htmlFixedTable = fixHTMLCKEDITOR(valoreLista.get(r).get("1"), session);
								valoreVariabileColonna = getHtmlModelValue(htmlFixedTable);
//								valoreVariabileColonna = getHtmlModelValue(valoreLista.get(r).get("1"));
							} else {
								valoreVariabileColonna = getTextModelValue(valoreLista.get(r).get("1"));
							}
						} else {
							valoreVariabileColonna = "";
						}
						valoreListaModel.add(valoreVariabileColonna);
					}
					model.put(nomeVariabile, valoreListaModel);
				}
			} else {
				Object valoreVariabile = null;
				if(valori.get(nomeAttributoCustom) != null) {			
					if ("DATE".equals(tipo)) {
						valoreVariabile = getDateModelValue(valori.get(nomeAttributoCustom));
					} else if ("DATETIME".equals(tipo)) {	
						valoreVariabile = getDateTimeModelValue(valori.get(nomeAttributoCustom));
					} else if ("CHECK".equals(tipo)) {
						valoreVariabile = getCheckModelValue(valori.get(nomeAttributoCustom));
					} else if ("CKEDITOR".equals(tipo)) {
						String htmlFixedTable = fixHTMLCKEDITOR(valori.get(nomeAttributoCustom), session);
						valoreVariabile = getHtmlModelValue(htmlFixedTable);
					} else {
						valoreVariabile = getTextModelValue(valori.get(nomeAttributoCustom));
					}
				} else {
					valoreVariabile = "";
				}
				Boolean flgBarcode = associazioniAttributiCustomMap.get(nomeVariabile) != null ? associazioniAttributiCustomMap.get(nomeVariabile).getFlgBarcode() : null;
				Boolean flgImage = associazioniAttributiCustomMap.get(nomeVariabile) != null ? associazioniAttributiCustomMap.get(nomeVariabile).getFlgImage() : null;
				if(flgBarcode != null && flgBarcode && valoreVariabile != null && StringUtils.isNotBlank(valoreVariabile.toString())) {
					String tipoBarcode = associazioniAttributiCustomMap.get(nomeVariabile) != null ? associazioniAttributiCustomMap.get(nomeVariabile).getTipoBarcode() : null;						;
					String strBarcode = (String) valoreVariabile;
					ImpostazioniBarcodeBean impostazioniBarcodeBean = getImpostazioniImmagineBarCode(tipoBarcode);
					valoreVariabile = BarcodeUtility.getImageProvider(strBarcode, impostazioniBarcodeBean);
				} else if (flgImage != null && flgImage){
					valoreVariabile = getImageModelValue(valoreVariabile, session);
				}
				model.put(nomeVariabile, valoreVariabile);
			}
		}	
		
		for(String nomeSottoVariabileLista : nomiSottoVariabiliLista) {
			model.remove(nomeSottoVariabileLista);
		}
		
		return model;
	}
	
	public static String fixHTMLCKEDITOR(Object stringHtml, HttpSession session) {
		
		// L'indentazione deve essere fatta con tag e non con spazi
		// Sostituisco tutti gli spazi usati per indentare i caratteri < con tab
		Object html_backup = stringHtml;
		try {
			// Scorro tutta la stringa html
			for (int i = 0; i < stringHtml.toString().length(); i++) {
				// Vedo se sono a fine riga
				if (stringHtml.toString().charAt(i) == '\n') {
					// Salvo la posizione del carattere a capo
					int posReturn = i;
					// Sostituisco tutti gli spazi dopo il carattere a capo con tab
					for (int j = posReturn + 1; (j < stringHtml.toString().length()) && (stringHtml.toString().charAt(j) == ' '); j++) {
						if ((j + 1 < stringHtml.toString().length()) && (stringHtml.toString().charAt(j + 1) == '<')) {
							int posOpenAngular = j + 1;
							for (int k = posReturn + 1; k < posOpenAngular; k++) {
								String stringToReplace = stringHtml.toString().substring(0, k) + '\t' + stringHtml.toString().substring(k + 1);
								stringHtml = stringToReplace;
							}
						}
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("Errore durante la formattazione dell'html dei CKEditor: " + e.getMessage(), e);
			stringHtml = html_backup;
		}
				
		Document doc = Jsoup.parse(stringHtml.toString());
		doc.outputSettings().indentAmount(0).prettyPrint(false);

		/** unwrap degli item che hanno style none e che quindi non devono essere mostrati */
		Elements listItems = doc.select("li");
		for (Element listItem : listItems) {
			StyleSheet styleSheet = new StyleSheet();
			AttributeSet styleSet = styleSheet.getDeclaration(listItem.attr("style"));
			if (styleSet.getAttribute(CSS.Attribute.LIST_STYLE_TYPE) != null && styleSet.getAttribute(CSS.Attribute.LIST_STYLE_TYPE).toString().equals(Value.NONE) && isBlankListItem(listItem)) {
				listItem.parent().unwrap();
				listItem.unwrap();
			}
		}
		
		/** elimino tutti i thead*/
		Elements theadItems = doc.select("thead");
		for (Element theadItem : theadItems) {
			theadItem.unwrap();
		}
		
		/** elimino tutti i tbody*/
		Elements tbodyItems = doc.select("tbody");
		for (Element tbodyItem : tbodyItems) {
			tbodyItem.unwrap();
		}
		
		/** sostituisco tutti i th con elementi td, setto lo stile centrato e grassetto */
		Elements thItems = doc.select("th");
		for (Element thItem : thItems) {
			thItem.tagName("td");
			thItem.attributes().remove("scope");
			thItem.attr("style", "text-align:center");
			String text = thItem.text();
			thItem.empty();
			thItem.appendElement("strong");
			thItem.child(0).text(text);
		}
		
		Elements tables = doc.select("table");
		if (!tables.isEmpty()) {
			Elements cellTableElements = tables.select("td");

			for (Element cell : cellTableElements) {
				Elements pCellElements = cell.select("p");
				if (!pCellElements.isEmpty()) {
					String styleP = pCellElements.get(0).attr("style");
					if (StringUtils.isNotBlank(styleP)) {
						if ( pCellElements.get(0).parent() != null) {
							pCellElements.get(0).parent().attr("style", StringUtils.isNotBlank( pCellElements.get(0).parent().attr("style")) ?  pCellElements.get(0).parent().attr("style") + "; " + styleP : styleP);
						}
					}

					for (int pPos = 0; pPos < pCellElements.size(); pPos++) {
						Element pElement = pCellElements.get(pPos);
						if (pPos != pCellElements.size() - 1) {
							String innerHtml = "";
//							if(ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_SOST_BR_HTML_MODELLI")) {
								innerHtml = pElement.html() + "<br/>";
//							} else {
//								innerHtml = pElement.html() + " ";
//							}
							pElement.html(innerHtml);
						}
						pElement.unwrap(); 
					}
				}

			}
		}

		return doc.select("body").html();
	}
	
	private static boolean isBlankListItem(Element listItem) {
		for (TextNode textNode : listItem.textNodes()) {
			if (StringUtils.isNotBlank(textNode.getWholeText())) {
				return false;
			}
		}
		return true;
	}

	// Verifica se nel modello ci sono associazioni con attributi liberi
	public static boolean contieneSoloAssociazioniAttributiCustom(ModelliDocBean modello) throws Exception {
		boolean soloAttributiCustom = true;
		if(modello.getListaAssociazioniAttributiCustom() != null) {
			// Le associazioni dei sotto attributi sono dello stesso tipo di quella del padre, non serve verificarle
			for(AssociazioniAttributiCustomBean associazioneAttributiCustomBean : modello.getListaAssociazioniAttributiCustom()) {
				if (!"attributoCustom".equalsIgnoreCase(associazioneAttributiCustomBean.getTipoAssociazioneVariabileModello())){
					soloAttributiCustom = false;
				}
			}
		}
		return soloAttributiCustom;	
	}
	
	private static LinkedHashMap<String, AssociazioniAttributiCustomBean> getAssociazioniAttributiCustomMap(ModelliDocBean bean) {
		
		LinkedHashMap<String, AssociazioniAttributiCustomBean> associazioniAttributiCustomMap = new LinkedHashMap<String, AssociazioniAttributiCustomBean>();
		
		if(bean.getListaAssociazioniAttributiCustom() != null && bean.getListaAssociazioniAttributiCustom().size() > 0) {
			for(AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean : bean.getListaAssociazioniAttributiCustom()) {
				if(StringUtils.isNotBlank(lAssociazioniAttributiCustomBean.getNomeVariabileModello())) {
					associazioniAttributiCustomMap.put(lAssociazioniAttributiCustomBean.getNomeVariabileModello(), lAssociazioniAttributiCustomBean);
				}
				if(lAssociazioniAttributiCustomBean.getFlgComplex() != null && lAssociazioniAttributiCustomBean.getFlgComplex()) {
					for(AssociazioniAttributiCustomBean lAssociazioniSottoAttributiComplexBean : lAssociazioniAttributiCustomBean.getListaAssociazioniSottoAttributiComplex()) {
						associazioniAttributiCustomMap.put(lAssociazioniSottoAttributiComplexBean.getNomeVariabileModello(), lAssociazioniSottoAttributiComplexBean);
					}
				}
			}
		}
		
		return associazioniAttributiCustomMap;
	}
	
	private static List<String> getListaNomiVariabiliFromNomeAttributoCustom(String nomeAttributoCustom, ModelliDocBean bean, String nomeVariabileLista) {
		
		List<String> listaNomiVariabili = new ArrayList<String>();
		
		if(StringUtils.isNotBlank(nomeAttributoCustom)) {
			if(bean.getListaAssociazioniAttributiCustom() != null && bean.getListaAssociazioniAttributiCustom().size() > 0) {
				for(AssociazioniAttributiCustomBean lAssociazioniAttributiCustomBean : bean.getListaAssociazioniAttributiCustom()) {
					if(StringUtils.isNotBlank(lAssociazioniAttributiCustomBean.getNomeAttributoCustom()) && nomeAttributoCustom.equalsIgnoreCase(lAssociazioniAttributiCustomBean.getNomeAttributoCustom())) {
						listaNomiVariabili.add(lAssociazioniAttributiCustomBean.getNomeVariabileModello());
					}
					if(lAssociazioniAttributiCustomBean.getFlgComplex() != null && lAssociazioniAttributiCustomBean.getFlgComplex()) {
						for(AssociazioniAttributiCustomBean lAssociazioniSottoAttributiComplexBean : lAssociazioniAttributiCustomBean.getListaAssociazioniSottoAttributiComplex()) {
							boolean controlloVariabileListaAttrLibero = lAssociazioniSottoAttributiComplexBean.getNomeVariabileModello().startsWith(nomeVariabileLista + ".");
							if(StringUtils.isNotBlank(lAssociazioniSottoAttributiComplexBean.getNomeAttributoCustom()) && nomeAttributoCustom.equalsIgnoreCase(lAssociazioniSottoAttributiComplexBean.getNomeAttributoCustom())) {
								listaNomiVariabili.add(lAssociazioniSottoAttributiComplexBean.getNomeVariabileModello());
							} else if(StringUtils.isNotBlank(lAssociazioniSottoAttributiComplexBean.getNomeAttributoLibero()) && nomeAttributoCustom.equalsIgnoreCase(lAssociazioniSottoAttributiComplexBean.getNomeAttributoLibero()) && controlloVariabileListaAttrLibero) {
								listaNomiVariabili.add(lAssociazioniSottoAttributiComplexBean.getNomeVariabileModello());
							}
						}
					}
				}
			}
		}
		
		return listaNomiVariabili;
	}
	
	private static ImpostazioniBarcodeBean getImpostazioniImmagineBarCode(String tipoBarcode) {
		
		if (StringUtils.isBlank(tipoBarcode)) {
			tipoBarcode = "CODE128";
		}
		
		ImpostazioniBarcodeBean impostazioniBarcodeBean = new ImpostazioniBarcodeBean();
		impostazioniBarcodeBean.setBarcodeEncoding(tipoBarcode);
		return impostazioniBarcodeBean;
	}

	public static String getTextModelValue(Object value) {
		return value != null ? String.valueOf(value) : " ";
	}
	
	public static String getDateModelValue(Object value) {		
		Date date = getDateValueFromObject(value);
		return date != null ? formatDate(date) : " ";			
	}
	
	public static String getDateTimeModelValue(Object value) {		
		Date date = getDateValueFromObject(value);
		return date != null ? formatDateTime(date) : " ";			
	}	
	
	public static String getCheckModelValue(Object value) {
		if (value != null && value instanceof String && (((String) value).equals(CHECK + "") || ((String) value).equals(NOT_CHECK + ""))){
			return (String) value;
		} else {
			boolean checked = getBooleanValueFromObject(value);
			return checked ? CHECK + "" : NOT_CHECK + "";
		}
	}
	
	public static String getHtmlModelValue(Object value) {
		return value != null ? ModelliDocDatasource.HTML_VALUE_PREFIX + String.valueOf(value) : "";
	}
	
	private static RenderedImageSource getImageModelValue(Object value, HttpSession session) throws IOException {
		String path = "/images/pratiche/icone/";
		String nomeImmagineLogo = "blank.png";
		try {
			if (value instanceof String && StringUtils.isNotBlank((String) value)) {
				// FIXME Da cambiare quando valentina passarà tutto il path e non solo il nome file
				path = "/images/loghiXTemplateDoc/";
				nomeImmagineLogo = (String) value;
			}		
			BufferedImage imgLogo = ImageIO.read(new File(session.getServletContext().getRealPath(path + nomeImmagineLogo)));
			BufferedImage joinedImg = joinBufferedImage(session, 16.51f, imgLogo);
			// construct the buffered image
			// obtain it's graphics
			Graphics2D bImageGraphics = joinedImg.createGraphics();
			// draw the Image (image) into the BufferedImage (bImage)
			bImageGraphics.drawImage(joinedImg, null, null);
			// cast it to rendered image
			RenderedImage rImage = (RenderedImage) joinedImg;
			return new RenderedImageSource(rImage);
		} catch (IOException e) {
			throw new IOException("Impossibile leggere il file " + path + nomeImmagineLogo, e);
		}
	}
	
	private static BufferedImage joinBufferedImage(HttpSession session, float aspectRatio, BufferedImage... imgs) throws IOException {

		// do some calculate first
		int totalWidth =  Math.round(100 * aspectRatio);
		int wid = 0;
		int height =  0;
		for (BufferedImage bufferedImage : imgs) {
			wid += bufferedImage.getWidth();
			height = Math.max(height, bufferedImage.getHeight());
		}
		
		BufferedImage blankImg = ImageIO.read(new File(session.getServletContext().getRealPath("/images/pratiche/icone/blank.png")));
		Image tmp = blankImg.getScaledInstance((totalWidth - wid) / 2, height, Image.SCALE_SMOOTH);
	    BufferedImage scaledBalnkImage = new BufferedImage((totalWidth - wid) / 2, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = scaledBalnkImage.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	     
		BufferedImage newImage = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		java.awt.Color oldColor = g2.getColor();
		// fill background
		g2.fillRect(0, 0, totalWidth, height);
		// draw image
		g2.setColor(oldColor);
		int x = 0;
		g2.drawImage(scaledBalnkImage, null, x, 0);
		x += scaledBalnkImage.getWidth();
		for (BufferedImage bufferedImage : imgs) {
			g2.drawImage(bufferedImage, null, x, 0);
			x += bufferedImage.getWidth();
		}
		g2.drawImage(scaledBalnkImage, null, x, 0);
		x += scaledBalnkImage.getWidth();
		g2.dispose();
		return newImage;
	}
	
	private static String formatDate(Date date) {
		return new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).format(date);
	}
	
	private static String formatDateTime(Date date) {
		return new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP).format(date);
	}
	
	private static boolean getBooleanValueFromObject(Object value) {
		if (value != null) {
			if (value instanceof Boolean) {
				return ((Boolean) value).booleanValue();
			} else if (value instanceof String) {
				String strValue = (String) value;
				return "true".equalsIgnoreCase(strValue) || "1".equalsIgnoreCase(strValue) || "si".equalsIgnoreCase(strValue);
			}
		} 
		return false;
	}
	
	private static Date getDateValueFromObject(Object value) {
		if (value != null) {
			if (value instanceof Date) {
				return (Date) value;
			} else {				
				String valueStr = String.valueOf(value);
				if (StringUtils.isNotBlank(valueStr)) {
					try {
						return new SimpleDateFormat(AbstractDataSource.DATETIME_ATTR_FORMAT).parse(valueStr);
					}catch (Exception e1) {
						try {
							return new SimpleDateFormat(AbstractDataSource.DATE_ATTR_FORMAT).parse(valueStr);
						}catch (Exception e2) {
							try {
								return new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP_WITH_SEC).parse(valueStr);
							}catch (Exception e3) {
								try {
									return new SimpleDateFormat(AbstractDataSource.FMT_STD_TIMESTAMP).parse(valueStr);
								}catch (Exception e4) {
									try {
										return new SimpleDateFormat(AbstractDataSource.FMT_STD_DATA).parse(valueStr);
									}catch (Exception e5) {
									}
								}
							}						
						}
					}
				}
			}
		} 
		return null;
	}
	
	private static void checkReplaceBR(OdfElement odfElement) {
		
		String textContent = odfElement.getTextContent();
		if(StringUtils.isNotBlank(textContent) && textContent.contains(ESCAPE_BR)) {
			String newTextContent = textContent.replaceAll(ESCAPE_BR, "\n");
			removeTextContentBeforeAppend(odfElement);
			appendTextElement(odfElement, newTextContent);
		}
	}
	
	private static void removeTextContentBeforeAppend(OdfElement ownerElement) {
		NodeList nodeList = ownerElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node;
			node = nodeList.item(i);
			if (node.getNodeType() == Node.TEXT_NODE) {
				ownerElement.removeChild(node);
				// element removed need reset index.
				i--;
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				String nodename = node.getNodeName();
				if (nodename.equals("text:s") || nodename.equals("text:tab") || nodename.equals("text:line-break")) {
					ownerElement.removeChild(node);
					// element removed need reset index.
					i--;
				} else if (nodename.equals("text:a") || nodename.equals("text:span") || nodename.equals("text:list-item") || nodename.equals("text:p") )
					removeTextContentBeforeAppend((OdfElement) node);
			}
		}
	}
	
	private static void appendTextElement(OdfElement parentElement, String content) {
		OdfFileDom ownerDocument = (OdfFileDom) parentElement.getOwnerDocument();
		Node ownerElement = parentElement.getLastChild() != null ? parentElement.getLastChild() : parentElement;
		if (ownerElement != null) {
			int i = 0, length = content.length();
			String str = "";
			while (i < length) {
				char ch = content.charAt(i);
				if (ch == ' ') {
					int j = 1;
					i++;
					while ((i < length) && (content.charAt(i) == ' ')) {
						j++;
						i++;
					}
					if (j == 1) {
						str += ' ';
					} else {
						str += ' ';
						Text textnode = ownerDocument.createTextNode(str);
						ownerElement.appendChild(textnode);
						str = "";
						TextSElement spaceElement = ownerDocument.newOdfElement(TextSElement.class);
						ownerElement.appendChild(spaceElement);
						spaceElement.setTextCAttribute(j - 1);
					}
				} else if (ch == '\n') {
					if (str.length() > 0) {
						Text textnode = ownerDocument.createTextNode(str);
						ownerElement.appendChild(textnode);
						str = "";
					}
					TextLineBreakElement lineBreakElement = ownerDocument.newOdfElement(TextLineBreakElement.class);
					ownerElement.appendChild(lineBreakElement);
					i++;
				} else if (ch == '\t') {
					if (str.length() > 0) {
						Text textnode = ownerElement.getOwnerDocument().createTextNode(str);
						ownerElement.appendChild(textnode);
						str = "";
					}
					TextTabElement tabElement = ownerDocument.newOdfElement(TextTabElement.class);
					ownerElement.appendChild(tabElement);
					i++;
				} else if (ch == '\r') {
					i++;
				} else {
					str += ch;
					i++;
				}
			}
			if (str.length() > 0) {
				Text textnode = ownerDocument.createTextNode(str);
				ownerElement.appendChild(textnode);
			}
		}
	}

}