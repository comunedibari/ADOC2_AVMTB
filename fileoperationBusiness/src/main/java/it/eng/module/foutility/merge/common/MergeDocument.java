package it.eng.module.foutility.merge.common;

import it.eng.utility.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;

public class MergeDocument {

	private MergeDocument() {
	}

	public static MergeDocument newInstance() {
		MergeDocument instance = new MergeDocument();
		return instance;
	}
	
	public void mergeDocument(File[] mergeFiles, OutputStream output) throws Exception {
		//File[] mergeFiles = new File[mergeFiles.length];
		try {
			// Effettuo il merge dei documenti PDF
			concatPDFs(mergeFiles, output);
		} finally {
			// Elimino tutti i file temporanei anche in caso di errore
			for (int i = 0; i < mergeFiles.length; i++) {
				if (mergeFiles[i] != null) {
					FileUtil.deleteFile(mergeFiles[i]);
				}
			}
		}
	}

	private void concatPDFs(File[] files, OutputStream output) throws Exception {
		PdfCopyFields copy = new PdfCopyFields(output);

		// Apro il documento
		copy.open();
		try {
			// Inserisco i documenti pdf in ordine
			for (int i = 0; i < files.length; i++) {
				// Creo un PDFReader
				FileInputStream stream = new FileInputStream(files[i]);
				PdfReader reader = new PdfReader(stream);
				copy.addDocument(reader);
			}
		} finally {
			// Chiudo il documento
			copy.close();
		}
	}
}