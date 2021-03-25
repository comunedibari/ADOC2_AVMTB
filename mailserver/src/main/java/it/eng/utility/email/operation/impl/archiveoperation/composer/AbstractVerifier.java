package it.eng.utility.email.operation.impl.archiveoperation.composer;

import it.eng.utility.email.operation.impl.interoperation.AbstractInterbean;

public abstract class AbstractVerifier {
	
	public static final String DATI_OBBLIGATORI_KO = "Impossibile trovare i dati obbligatori della segnatura\r\n";

	public static final String PRESENZA_EMAIL_AOO_KO = "Impossibile trovare email o CodAOO nella segnatura\r\n";

	public abstract InteropVerificationData verify(AbstractInterbean busta, String email, String codAOO, String codEnte);
}
