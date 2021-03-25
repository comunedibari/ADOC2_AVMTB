package it.eng.utility.email.operation.impl.signeroperation.constants;

public class SignerConstants {

	public static final class TimeStampInfo {
		
		/**
		 * OID dell'algoritmo di digest del timestamp
		 */
		public static final String PROP_HASH_ALGORITHM = "Hash Algorithm";
		
		/**
		 * somma della data di riferimento temporale e il massimo scostamento in millisecondi contenuto nella marca
		 */
		public static final String PROP_MILLISECS = "Temporal reference";
		
		/**
		 * abbreviazione per Date(PROP_MILLISECS)
		 */
		public static final String PROP_DATE = "Date reference";
		
		/**
		 * formato della busta contenente il timestamp
		 */
		public static final String PROP_TIMESTAMP_FORMAT = "Timestamp Format";
		
		/**
		 * true se il certificato e contenuto nella lista di certificate accreditati
		 */
		public static final String PROP_RECOGNIZED_CERTIFICATE = "TSA Recognized Certificate";
		
		/**
		 * nome della TSA
		 */
		public static final String PROP_SID = "Signer";
		
	}
	
	
	public static final class SignerInfo {
		
		/**
		 *  Tipo: String - formato della busta
		 */
		public static final String ENVELOPE_FORMAT_PROPERTY			= "Envelope Format";	
		
		/**
		 * Tipo: List<ISignature> - lista delle firme
		 */
		public static final String SIGNATURE_PROPERTY				= "Signatures";
		
		/**
		 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla validita di ciascuna firma
		 */
		public static final String SIGNATURE_VALIDATION_PROPERTY	= "Signature Validation Infos";
		
		/**
		 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla validita di ciascuna firma
		 */
		public static final String CERTIFICATE_VALIDATION_PROPERTY	= "Certficate Validation Infos";
		
		/**
		 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla scadenza dei certificati di ciascuna firma
		 */
		public static final String CERTIFICATE_EXPIRATION_PROPERTY	= "Certificate Expiration Infos";
		
		/**
		 *  Tipo: Map<ISignature, ValidationInfos> - informazioni sulla revoca dei certificati
		 */
		public static final String CRL_VALIDATION_PROPERTY			= "CRL Validation Infos";
		
		/**
		 *  Tipo:List<ISignature> - lista delle firme con certificato non riconosciuto 
		 */
		public static final String CERTIFICATE_UNQUALIFIED_PROPERTY = "Unqualified certificates";
		
		/**
		 *  Tipo: ValidationInfos - informazioni sulla validita del formato rispetto al periodo di validita associato
		 */
		public static final String FORMAT_VALIDITY_PROPERTY 		= "FormatCheckValidity";
		
		/**
		 *  Tipo: List<DocumentAndTimeStampInfo> 
		 */
		public static final String TIME_STAMP_INFO_PROPERTY			= "TimeStampProperty";
		
		/**
		 *  Tipo: Map<ISignature, X509Certificate> - corrispondenza tra firma e certificato di certificazione accreditato
		 */
		public static final String CERTIFICATE_RELIABILITY_PROPERTY = "CertificateReliabilityProperty";
		
		/**
		 *  Tipo: String - cone dell'eventuale controller che a andato in errore
		 */
		public static final String MASTER_SIGNER_EXCEPTION_PROPERTY = "MasterSignerExceptionProperty"; 
		
	}
	
	
	
	
	
}
