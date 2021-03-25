package it.eng.module.foutility.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class CheckPdfUtil {
	
	static Logger logger = Logger.getLogger(CheckPdfUtil.class);

	@Test
	public void testCheckPdfUtil() {
		File fileLayer = new File ("C:\\Users\\Peluso Antonio\\Desktop\\new_Anna_layer.pdf");
		String mimeTypeFile = "application/pdf";
		
		File fileWithoutLayer = manageMultiLayerPdf(fileLayer.getAbsolutePath(), mimeTypeFile);
		
		System.out.println("il File con i layer appiattiti si trova al seguente path: " + fileWithoutLayer.getAbsolutePath());
		
	}
	
	
	
	public static File manageMultiLayerPdf(String pathFile, String mimeTypeFile) {
		
		File fileWithoutLayer = new File(pathFile);
		
		try {
			if ("application/pdf".equalsIgnoreCase(mimeTypeFile) && checkPdfMultiLayer(pathFile)) {
				logger.debug("Il pdf e' multiLayer");
				logger.debug("--- GESTIONE PDF MULTILAYER ---");
				fileWithoutLayer = copyFileInAnotherFile(pathFile, fileWithoutLayer);
				
			}
		} catch (Exception e) {
			logger.error("Errore durante la gestione dei pdf multiLayer: " + e.getMessage(), e);
		}
		
		return fileWithoutLayer;
		
	}
	
	public static boolean checkPdfA(String pathFile) throws Exception {
		try {
			PdfReader reader = new PdfReader(pathFile);
			return reader.getMetadata() != null && (new String(reader.getMetadata())).contains("pdfaid:conformance");
		} catch (Exception e) {
			logger.error("Errore nella verifica del pdfA: " + e.getMessage() + ", ritorno false", e);
			return false;
		}
	}
	
	private static File copyFileInAnotherFile(String pathFile, File fileWithoutLayer) throws Exception {
		File destTempFile = File.createTempFile("tempFile", ".pdf", fileWithoutLayer.getParentFile());
		FileOutputStream os = new FileOutputStream(destTempFile);

		Document document = new Document();
		PdfWriter writer;

		try {
			// il file generato dovrà essere un PDFA se quello di origine era un PDFA, altrimenti no.
			if (checkPdfA(pathFile)) {
				writer = PdfAWriter.getInstance(document, os, PdfAConformanceLevel.PDF_A_3U);
			} else {
				writer = PdfWriter.getInstance(document, os);
			}

			writer.setTagged();
			writer.setLanguage("it");
			writer.setLinearPageMode();
			writer.createXmpMetadata();

			document.open();

			PdfContentByte cb = writer.getDirectContent();
			
			PdfReader reader = new PdfReader(pathFile);
			// Questo parametro mi fa bypassare la password in modifica presente sul file e quindi mi permette di leggerlo con PdfReader
			reader.unethicalreading = true;
			
			// Scorro le pagine da copiare
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				addPageToDocument(document, writer, cb, reader, i, false);
			}

			os.flush();
			document.close();
			os.close();

			return destTempFile;
		} catch (Exception e) {
			throw new Exception("Errore durante la copia del file protetto con i permessi: " + e.getMessage(), e);
		}

	}
	
	private static void addPageToDocument(Document document, PdfWriter writer, PdfContentByte cb, PdfReader reader,
			int pageNumber, boolean forceA4) {
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);

		// Verifico la rotazione della pagina corrente
		Rectangle psize = reader.getPageSizeWithRotation(pageNumber);

		// Imposto il document in ladscape o portrait, a seconda della pagina
		if (psize.getWidth() > psize.getHeight()) {
			if (forceA4) {
				document.setPageSize(PageSize.A4.rotate());
			} else {
				document.setPageSize(psize);
			}
		} else {
			if (forceA4) {
				document.setPageSize(PageSize.A4);
			} else {
				document.setPageSize(psize);
			}
		}

		// Creo una nuova pagina nel document in cui copiare la pagina corrente
		document.newPage();
		// Raddrizzo l'immagine a seconda della rotazione
		switch (psize.getRotation()) {
		case 0:
			cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
			break;
		case 90:
			cb.addTemplate(page, 0, -1f, 1f, 0, 0, psize.getHeight());
			break;
		case 180:
			cb.addTemplate(page, -1f, 0, 0, -1f, psize.getWidth(), psize.getHeight());
			break;
		case 270:
			cb.addTemplate(page, 0, 1f, -1f, 0, psize.getWidth(), 0);
			break;
		default:
			break;
		}
	}
	
	public static boolean checkPdfMultiLayer(String realFilePath) {
		try {
			PdfReader reader = new PdfReader(realFilePath);
			PdfDictionary catalog = reader.getCatalog();
			if(catalog.get(PdfName.OCPROPERTIES)!=null){
				return true;
			}else {
				 return false;
			}
		}catch(Exception ex) {
			logger.error("Errore durante il controllo dei pdf MultiLayer: " + ex.getMessage());
		}
		return false;
	}

}
