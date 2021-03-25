package it.eng.utility.ocr;

import it.eng.utility.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import net.sourceforge.tess4j.Tesseract;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.PdfReader;

//import com.lowagie.text.pdf.PdfReader;

public class OCRApi {
	
	private static Logger aLogger = Logger.getLogger(OCRApi.class.getName());	

    public OCRApi() { 
    	
    }
    
    public InputStream getText(File file) throws Throwable {
    	Tesseract tesseract = Tesseract.getInstance();
    	tesseract.setLanguage("ita");	
    	String ocrText = "";
    	try {
    		aLogger.debug("Inizio getText del file " + file.getAbsolutePath() + "");
    		if (file.getName().toLowerCase().endsWith(".pdf")) {
    			 try {    		     
    				 	PdfReader reader = new PdfReader(file.getAbsolutePath());
    		            int n = reader.getNumberOfPages();
    		            aLogger.debug("Number of pages : " + n);
    		            if(n > 1) {
	    		            int i = 1;      		            
	    		            while ( i <= n ) {
	    		            	File outFile = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().toLowerCase().indexOf(".pdf")) + 
	    		            	String.format("%03d", i) + ".pdf");
	    		            	//PdfUtilities.splitPdf(file.getAbsolutePath(), outFile.getAbsolutePath(), "" + i, "" + i);
	    		            	aLogger.debug(">>> OCR OF PAGE  " + i + "/" + n + " <<<");
	    		            	ocrText += "\n" + tesseract.doOCR(outFile);    
	    		            	FileUtil.deleteFile(outFile);
	    		            	i++;
	    		            }
    		            } else {
    		            	ocrText = tesseract.doOCR(file);
    		            }
    		      } 
    		      catch (Exception e) {
    		    	  throw e;
    		      }    		            			
        	} else {    			    	    	
        		ocrText = tesseract.doOCR(file);
        	}	    	
    		aLogger.debug("Tutto OK");
    	} catch(Throwable e) {    		
            aLogger.error("getText: " + e.getMessage(), e);
            throw e;              	        	
        } finally {
        	aLogger.debug("Fine getText");
        }
    	return IOUtils.toInputStream(ocrText);
    }
    
    public static void main(String args[]) throws Throwable {       	
    	OCRApi ocrapi = new OCRApi();
        File file = null;
		InputStream is = null;    	
        FileOutputStream fos = null;
        
        file = new File("C:/OCR/pippo.tif");
    	if(file.exists()) {
	        try {	        	
	        	fos = new FileOutputStream("C:/OCR/pippo_tif.txt");             
	        	is = ocrapi.getText(file);
	        	fos.write(IOUtils.toString(is).getBytes());        	
	        } catch (Throwable t) {
	        	t.printStackTrace();
	        } finally {
	        	try { is.close(); } catch (Exception e) {}
	        	try { fos.close(); } catch (Exception e) {}
	        }    
    	}
    	
    	file = new File("C:/OCR/pippo.pdf");	
    	if(file.exists()) {
	        try {        	
	        	fos = new FileOutputStream("C:/OCR/pippo_pdf.txt");
	        	is = ocrapi.getText(file);
	        	fos.write(IOUtils.toString(is).getBytes());        	
	        } catch (Throwable t) {
	        	t.printStackTrace();   
	        	return;
	        } finally {
	        	try { is.close(); } catch (Exception e) {}
	        	try { fos.close(); } catch (Exception e) {}
	        }     
    	}
    	
    	file = new File("C:/OCR/pippo.jpg");	
    	if(file.exists()) {
	        try {        	
	        	fos = new FileOutputStream("C:/OCR/pippo_jpg.txt");
	        	is = ocrapi.getText(file);
	        	fos.write(IOUtils.toString(is).getBytes());        	
	        } catch (Throwable t) {
	        	t.printStackTrace();   
	        	return;
	        } finally {
	        	try { is.close(); } catch (Exception e) {}
	        	try { fos.close(); } catch (Exception e) {}
	        }     
    	}
    }
    
}
