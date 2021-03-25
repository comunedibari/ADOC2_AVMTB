package it.eng.utility.cryptosigner.utils;
/**
 * marker interface per i codici dei messaggi
 * @author Russo
 *
 */
public interface MessageConstants {
	//GM general Message
	String GM_GENERIC_ERROR="GM.GENERIC_ERROR";
	String GM_UNEXPECTED_ERROR="GM.UNEXPECTED_ERROR";
	String GM_WSCALL_ERROR="GM.WSCALL_ERROR";
	String GM_FILE_INPUT_NULL="GM.FILE_INPUT_NULL";
	String GM_EXECUTION_CONTROLLER="GM.EXECUTION_CONTROLLER";
	
	String CD_MIMETYPE_NOTFOUND="CD.MIMETYPE_NOTFOUND";
	String CD_MIMETYPE_NOTVERIFIABLE="CD.MIMETYPE_NOTVERIFIABLE";
	
	String SIGN_FILE_NOTSIGNED="SIGN.FILE_NOTSIGNED";
	String SIGN_MARK_NOTVALID="SIGN.MARK_NOTVALID";
	String SIGN_MARKSIGN_NOTVALID="SIGN.MARKSIGN_NOTVALID";
	String SIGN_MARK_NOTVALID_TIME="SIGN.MARK_NOTVALID_TIME";
	String SIGN_CERTIFICATE_TSA_NOTVALID="SIGN.CERTIFICATE_TSA_NOTVALID";
	String SIGN_CERTIFICATE_NOTVALID="SIGN.CERTIFICATE_NOTVALID";
	String SIGN_TSA_ERROR_STORAGE="SIGN.TSA_ERROR_STORAGE";
	String SIGN_TSA_DATE_AFTER="SIGN.TSA_DATE_AFTER";
	String SIGN_TSA_DATE_BEFORE="SIGN.TSA_DATE_BEFORE";
	String SIGN_TSA_ISSUER_NOTVALID="SIGN.TSA_ISSUER_NOTVALID";
	String SIGN_TOKEN_WITHOUT_VALIDMARK="SIGN.TOKEN_WITHOUT_VALIDMARK";
	String SIGN_FORMAT_WITHOUT_MARK="SIGN.FORMAT_WITHOUT_MARK";
	String SIGN_MARK_VALIDATION_ERROR="SIGN.MARK_VALIDATION_ERROR";
	String SIGN_VALIDATION_ERROR="SIGN.VALIDATION_ERROR";
	String SIGN_DECIPHERING_ERROR="SIGN.DECIPHERING_ERROR";
	String SIGN_FILE_ASSOCIATION_ERROR="SIGN.FILE_ASSOCIATION_ERROR";
	String SIGN_ALGORITHM_NOTSUPPORTED="SIGN.ALGORITHM_NOTSUPPORTED";
	String SIGN_MARK_ALGORITHM_NOTSUPPORTED="SIGN.MARK_ALGORITHM_NOTSUPPORTED";
	String SIGN_MARKSIGN_ALGORITHM_NOTSUPPORTED="SIGN.MARKSIGN_ALGORITHM_NOTSUPPORTED";
	String SIGN_HASH_ERROR="SIGN.HASH_ERROR";
	String SIGN_VERIFICATION_ERROR="SIGN.VERIFICATION_ERROR";
	String SIGN_SIGNEDFILE_NOTFOUND="SIGN.SIGNEDFILE_NOTFOUND";
	String SIGN_MARKEDEDFILE_NOTFOUND="SIGN.MARKEDEDFILE_NOTFOUND";
	String SIGN_ALGORITHM_NOTFOUND="SIGN.ALGORITHM_NOTFOUND";
	String SIGN_MARK_EXTENDED_VALIDITY="SIGN.MARK_EXTENDED_VALIDITY";
	String SIGN_SIGNEDCONTENT_WARNING="SIGN.SIGNEDCONTENT_WARNING";
	String SIGN_MARK_EXTENSION="SIGN.MARK_EXTENSION";
		
	String FV_FORMAT_NONVERIFIABLE="FV.FORMAT_NONVERIFIABLE";
	String FV_FORMAT_EXIPERED_BEFORE="FV.FORMAT_EXIPERED_BEFORE";
	String FV_FILE_FORMAT_ERROR="FV.FILE_FORMAT_ERROR";
}
