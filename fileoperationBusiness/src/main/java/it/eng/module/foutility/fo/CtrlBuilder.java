package it.eng.module.foutility.fo;

import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.AbstractResponseOperationType;

public interface CtrlBuilder {

	public IFileController build(AbstractInputOperationType input);
	public IFileController getCTRLFromResponse(AbstractResponseOperationType response);
}
