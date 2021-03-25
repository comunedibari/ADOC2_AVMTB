package it.eng.module.foutility.fo;

import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;
import it.eng.module.foutility.beans.generated.InputCodeDetectorType;
import it.eng.module.foutility.beans.generated.InputConversionType;
import it.eng.module.foutility.beans.generated.InputDigestType;
import it.eng.module.foutility.beans.generated.InputFileCompressType;
import it.eng.module.foutility.beans.generated.InputFormatRecognitionType;
import it.eng.module.foutility.beans.generated.InputSigVerifyType;
import it.eng.module.foutility.beans.generated.InputTimbroType;
import it.eng.module.foutility.beans.generated.InputUnpackDigestType;
import it.eng.module.foutility.beans.generated.InputUnpackMultipartType;
import it.eng.module.foutility.beans.generated.InputUnpackType;
import it.eng.module.foutility.beans.generated.ResponseFileCompressType;
import it.eng.module.foutility.beans.generated.ResponseFileDigestType;
import it.eng.module.foutility.beans.generated.ResponseFileDigestUnpackType;
import it.eng.module.foutility.beans.generated.ResponseFormatRecognitionType;
import it.eng.module.foutility.beans.generated.ResponsePdfConvResultType;
import it.eng.module.foutility.beans.generated.ResponseSigVerify;
import it.eng.module.foutility.beans.generated.ResponseTimbroType;
import it.eng.module.foutility.beans.generated.ResponseUnpackType;
import it.eng.module.foutility.util.FileoperationContextProvider;

import org.springframework.context.ApplicationContext;

/**
 * mapping fra i controller e i relativi input Type
 * ogni inputtype è mappato ad un controller configurato in spring
 *
 *  
 */
public class SpringCtrlBuilder implements CtrlBuilder {
	public    IFileController build(AbstractInputOperationType input){
		IFileController ret=null;
		ApplicationContext context=FileoperationContextProvider.getApplicationContext();
		if(input instanceof InputFormatRecognitionType){
			ret=context.getBean(FormatRecognitionCtrl.class);
		}else  if(input instanceof InputUnpackDigestType){
			ret=(IFileController)context.getBean("DigestUnpackCtrl");
			//l'override dei par di conf viene fatto nell'esecuzione dei controller
		}else  if(input instanceof InputDigestType){
			ret=(IFileController)context.getBean("DigestInputCtrl");
			//l'override dei par di conf viene fatto nell'esecuzione dei controller
		}else if(input instanceof InputUnpackType){
			ret=context.getBean(UnpackCtrl.class);
		}else if(input instanceof InputConversionType){
			ret=context.getBean(PdfConvCtrl.class);
		}else if (input instanceof InputSigVerifyType){
			ret=context.getBean(SigVerifyCtrl.class);
		} else if(input instanceof InputTimbroType){
			ret=context.getBean(TimbroCtrl.class);
		}else if(input instanceof InputCodeDetectorType){
			ret=context.getBean(CodeDetectorCtrl.class);
		}else if(input instanceof InputFileCompressType){
			ret=context.getBean(CompressCtrl.class);
		}else if(input instanceof InputUnpackMultipartType){
			ret=context.getBean(UnpackMultipartCtrl.class);
		}
		return ret;
	}

	public IFileController getCTRLFromResponse(AbstractResponseOperationType response){
		IFileController ret=null;
		ApplicationContext context=FileoperationContextProvider.getApplicationContext();
		if(response instanceof ResponseFormatRecognitionType){
			ret=context.getBean(FormatRecognitionCtrl.class); 
		} else  if(response instanceof ResponseFileDigestUnpackType){
			ret=(IFileController)context.getBean("DigestUnpackCtrl");
			//l'override dei par di conf viene fatto nell'esecuzione dei controller
		} else  if(response instanceof ResponseFileDigestType){
			ret=(IFileController)context.getBean("DigestInputCtrl");
			//l'override dei par di conf viene fatto nell'esecuzione dei controller
		} else if(response instanceof ResponseUnpackType){
			ret=context.getBean(UnpackCtrl.class);
		} else if(response instanceof ResponsePdfConvResultType){
			ret=context.getBean(PdfConvCtrl.class);
		} else if (response instanceof ResponseSigVerify){
			ret=context.getBean(SigVerifyCtrl.class);
		} else if(response instanceof ResponseTimbroType){
			ret=context.getBean(TimbroCtrl.class);
		} else if(response instanceof ResponseFileCompressType){
			ret=context.getBean(CompressCtrl.class);
		}
		return ret;
	}



}
