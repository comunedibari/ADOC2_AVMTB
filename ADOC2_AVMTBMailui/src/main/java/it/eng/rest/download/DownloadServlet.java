package it.eng.rest.download;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.eng.aurigamailbusiness.sender.storage.StorageCenter;
import it.eng.utility.storageutil.StorageService;

@RestController
@RequestMapping("/service/mailboxmessage")
public class DownloadServlet {

	private static Logger mLogger = LogManager.getLogger(DownloadServlet.class);
	@RequestMapping(value="/download", method=RequestMethod.GET)
	@ResponseBody
	public HttpEntity download(@RequestParam String urlMime, @RequestParam String idMailbox, @RequestParam String messageId, HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.TEXT_HTML);
		String filename = messageId + ".eml";
		byte[] documentBody = null;
		
		StorageCenter lStorageCenter = new StorageCenter();
		StorageService storage = lStorageCenter.getGlobalStorage(idMailbox);

		try {
			documentBody = IOUtils.toByteArray(storage.extract(urlMime));
			File lFile = storage.extractFile(urlMime);
			mLogger.debug(lFile.getPath());
			header.set("Content-Disposition",
					"attachment; filename=" + filename);
			header.setContentLength(documentBody.length);
			return new HttpEntity<byte[]>(documentBody, header);
		} catch (Exception e) {
			mLogger.error("Errore download " + e.getMessage(), e);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.TEXT_HTML);
			responseHeaders.setCacheControl("private, no-store, no-cache, must-revalidate");
			responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
			responseHeaders.add("Cache-Control", "private, no-store, no-cache, must-revalidate");
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("<html>");
			lStringBuffer.append("<head>");
			lStringBuffer.append("<body>");
			lStringBuffer.append("<script>");
			lStringBuffer.append("try {window.top.errorCallback('Impossibile effettuare il download del file'); } " +
			"catch(err) {for (var p in err) alert(err[p])}");
			lStringBuffer.append("</script>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		}
	}
}
