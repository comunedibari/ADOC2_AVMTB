package it.eng.gd.lucenemanager.document.reader.util;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;
import org.jpedal.grouping.PdfGroupingAlgorithms;
import org.jpedal.objects.PdfPageData;

import it.eng.gd.lucenemanager.exception.LuceneManagerException;

/**
 * Estrattore di testo da file pdf utilizzando jpedal
 * 
 * @author jravagnan
 * 
 */
public class ExtractTextAsWordlist {

	private String timeout;

	/** word count - used for debug */
	private int wordsExtracted = 0;

	/** the decoder object which decodes the pdf and returns a data object */
	private PdfDecoder decodePdf = null;

	private Logger logger = Logger.getLogger(ExtractTextAsWordlist.class.getName());

	public ExtractTextAsWordlist() {
	}

	public String extractTextFromPdf(String fileName) throws Exception {
		logger.info("extractTextFromPdf(String fileName)");
		logger.debug("file da elaborare: " + fileName);
		// se e' nullo lo setto ad un defualt
		if (timeout == null) {
			timeout = "120";
		}
		logger.debug("timeout = " + timeout);
		String finalText = "";
		Long toutMillis = (Long.parseLong(timeout)) * 1000;
		// PdfDecoder returns a PdfException if there is a problem
		try {
			decodePdf = new PdfDecoder(true);

			// increase fonts not embedded
			FontMappings.setFontReplacements();

			decodePdf.setExtractionMode(PdfDecoder.TEXT); // extract just text
			PdfDecoder.init(true);
			// make sure widths in data CRITICAL if we want to split lines correctly!!

			/**
			 * if you do not require XML content, pure text extraction is much faster.
			 */
			decodePdf.useTextExtraction();
			/**/

			// always reset to use unaltered co-ords - allow use of rotated or unrotated
			// co-ordinates on pages with rotation (used to be in PdfDecoder)
			PdfGroupingAlgorithms.useUnrotatedCoords = false;

			/**
			 * open the file (and read metadata including pages in file)
			 */
			decodePdf.openPdfFile(fileName);

		} catch (Exception e) {
			logger.error("Exception " + e + " in pdf code for wordlist" + fileName);
			throw new Exception("Impossibile trasformare il file pdf " + fileName + " in puro testo");
		}

		/**
		 * extract data from pdf (if allowed).
		 */
		if (!decodePdf.isExtractionAllowed()) {
			logger.warn("Text extraction not allowed");
		} else if (decodePdf.isEncrypted() && !decodePdf.isPasswordSupplied()) {
			logger.error("File protetto: necessaria password!");
		} else {
			// page range
			int start = 1, end = decodePdf.getPageCount();
			Long timeStart = System.currentTimeMillis();
			/**
			 * extract data from pdf
			 */
			try {
				for (int page = start; page < end + 1; page++) { // read pages
					logger.debug("elaborazione pagina numero: " + page);
					// decode the page
					decodePdf.decodePage(page);

					/** create a grouping object to apply grouping to data */
					PdfGroupingAlgorithms currentGrouping = decodePdf.getGroupingObject();

					/** use whole page size for demo - get data from PageData object */
					PdfPageData currentPageData = decodePdf.getPdfPageData();

					int x1 = currentPageData.getMediaBoxX(page);
					int x2 = currentPageData.getMediaBoxWidth(page) + x1;

					int y2 = currentPageData.getMediaBoxX(page);
					int y1 = currentPageData.getMediaBoxHeight(page) - y2;

					/** Co-ordinates are x1,y1 (top left hand corner), x2,y2(bottom right) */

					/** The call to extract the list */
					List<?> words = null;

					/** new 7th October 2003 - define punctuation */
					try {
						words = currentGrouping.extractTextAsWordlist(x1, y1, x2, y2, page, true, "&:=()!;.,\\/\"\"\'\'");
					} catch (PdfException e) {
						decodePdf.closePdfFile();
						logger.error("Exception= " + e + " in " + fileName);
					}

					if (words == null) {
						logger.info("No text found");
					} else {

						/** each word is stored as 5 consecutive values (word,x1,y1,x2,y2) */
						int wordCount = words.size() / 5;

						// update our count
						wordsExtracted = wordsExtracted + wordCount;

						logger.debug("Page contains " + wordCount + " words.");

						Iterator<?> wordIterator = words.iterator();
						while (wordIterator.hasNext()) {

							String currentWord = (String) wordIterator.next();
							finalText = finalText + " " + currentWord;

						}

					}

					// remove data once written out
					decodePdf.flushObjectValues(false);
					Long timeNow = System.currentTimeMillis();
					if ((timeNow - timeStart) > toutMillis) {
						logger.error("Tempo per l'elaborazione superiore al timeout previsto: " + timeout + " s");
						throw new LuceneManagerException("Tempo per l'elaborazione superiore al timeout previsto: " + timeout + " s");
					}

				}
			} catch (LuceneManagerException e) {
				logger.error("Exception " + e + " in " + fileName);
				throw new Exception(e.getMessage());
			} catch (Exception e) {
				decodePdf.closePdfFile();
				logger.error("Exception " + e + " in " + fileName);
				throw new Exception("Impossibile chiudere il file pdf");
			}

			/**
			 * flush data structures - not strictly required but included as example
			 */
			decodePdf.flushObjectValues(true); // flush any text data read

			logger.debug("Text read");

		}

		/** close the pdf file */
		decodePdf.closePdfFile();
		return finalText;

	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	/**
	 * return words extracted
	 */
	public int getWordsExtractedCount() {
		return wordsExtracted;
	}

}