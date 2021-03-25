package it.eng.utility.ui.servlet.fileExtractor;

import java.io.File;
import java.io.InputStream;

public interface FileExtractor {

	public String getFileName() throws Exception;
	public File getFile() throws Exception;
	public InputStream getStream() throws Exception;
}
