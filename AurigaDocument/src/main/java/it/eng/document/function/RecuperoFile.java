package it.eng.document.function;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.Extractverdoc;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.ExtractVerDocOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.storage.DocumentStorage;

@Service(name = "RecuperoFile")
public class RecuperoFile {

	@Operation(name = "extractFile")
	public FileExtractedOut extractFile(AurigaLoginBean pAurigaLoginBean, FileExtractedIn pFileExtractedIn){
		FileExtractedOut lFileExtractedOut = new FileExtractedOut();
		try {
			lFileExtractedOut.setExtracted(DocumentStorage.extract(pFileExtractedIn.getUri(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio()));
		}
		catch (Exception e) {
			lFileExtractedOut.setErrorInExtract(e.getMessage());
		}
		return lFileExtractedOut;
	}
	
	
	@Operation(name = "extractFileByIdDoc")
	public FileExtractedOut extractFileByIdDoc(AurigaLoginBean pAurigaLoginBean, BigDecimal idDocIn, BigDecimal nroProgrVerIn){
		
		FileExtractedOut lFileExtractedOut = new FileExtractedOut();
		
		try {
			
			// Chiamo il "DMPK_CORE.ExtractVerDoc" per leggere le informazioni di un documento
			ExtractVerDocOutBean lExtractVerDocOutBean = new ExtractVerDocOutBean();
			lExtractVerDocOutBean = extractVerDoc(pAurigaLoginBean, idDocIn, nroProgrVerIn);
			
			// Chiamo la extractFile per avere il il file
			FileExtractedIn pFileExtractedIn = new FileExtractedIn();
			pFileExtractedIn.setUri(lExtractVerDocOutBean.getUriOut());
			lFileExtractedOut = extractFile(pAurigaLoginBean, pFileExtractedIn);
		}
		
		catch (Exception e) {
			lFileExtractedOut.setErrorInExtract(e.getMessage());
		}
				
		return lFileExtractedOut;
		
	}
	
	// restituisce le info di una versione di un documento
	@Operation(name = "extractVerDoc")
	public ExtractVerDocOutBean extractVerDoc(AurigaLoginBean pAurigaLoginBean, BigDecimal idDocIn, BigDecimal nroProgrVerIn) throws Exception {
		try {
			
			DmpkCoreExtractverdocBean lDmpkCoreExtractverdocBean = new DmpkCoreExtractverdocBean();
			lDmpkCoreExtractverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());			
			lDmpkCoreExtractverdocBean.setIduserlavoroin(StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null);
			lDmpkCoreExtractverdocBean.setIddocin(idDocIn);
			lDmpkCoreExtractverdocBean.setNroprogrverio(nroProgrVerIn);
			
			// eseguo il servizio
			Extractverdoc lExtractverdoc = new Extractverdoc();
			
			StoreResultBean<DmpkCoreExtractverdocBean> lStoreResultBean = lExtractverdoc.execute(pAurigaLoginBean, lDmpkCoreExtractverdocBean);
			if (lStoreResultBean.isInError()){
				throw new StoreException(lStoreResultBean);
			}
			
			// leggo output
			ExtractVerDocOutBean lExtractVerDocOutBean = new ExtractVerDocOutBean(); 	

			// NroProgrVerIO
			if(lStoreResultBean.getResultBean().getNroprogrverio()!=null)
				lExtractVerDocOutBean.setNroProgrVerOut(lStoreResultBean.getResultBean().getNroprogrverio());

			// DisplayFilenameVerOut
			if(lStoreResultBean.getResultBean().getDisplayfilenameverout()!=null)
				lExtractVerDocOutBean.setDisplayFilenameVerOut(lStoreResultBean.getResultBean().getDisplayfilenameverout());

			// URIVerOut
			if(lStoreResultBean.getResultBean().getUriverout()!=null)
				lExtractVerDocOutBean.setUriOut(lStoreResultBean.getResultBean().getUriverout());
			
			// ImprontaVerOut
			if(lStoreResultBean.getResultBean().getImprontaverout()!=null)
				lExtractVerDocOutBean.setImprontaVerOut(lStoreResultBean.getResultBean().getImprontaverout());
			
			// AlgoritmoImprontaVerOut
			if(lStoreResultBean.getResultBean().getAlgoritmoimprontaverout()!=null)
				lExtractVerDocOutBean.setAlgoritmoImprontaVerOut(lStoreResultBean.getResultBean().getAlgoritmoimprontaverout());
			
			// EncodingImprontaVerOut
			if(lStoreResultBean.getResultBean().getEncodingimprontaverout()!=null)
				lExtractVerDocOutBean.setEncodingImprontaVerOut(lStoreResultBean.getResultBean().getEncodingimprontaverout());
			
			// DimensioneVerOut
			if(lStoreResultBean.getResultBean().getDimensioneverout()!=null)
				lExtractVerDocOutBean.setDimensioneVerOut(lStoreResultBean.getResultBean().getDimensioneverout());
				
			// MimetypeVerOut
			if(lStoreResultBean.getResultBean().getMimetypeverout()!=null)
				lExtractVerDocOutBean.setMimetypeVerOut(lStoreResultBean.getResultBean().getMimetypeverout());
			
			// FlgFirmataVerOut
			if(lStoreResultBean.getResultBean().getFlgfirmataverout()!=null)
				lExtractVerDocOutBean.setFlgFirmataVerOut(lStoreResultBean.getResultBean().getFlgfirmataverout());
			
			return lExtractVerDocOutBean;
			
		} catch (Exception e){
			throw e;
		}
	}
}
