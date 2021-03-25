package it.eng.utility;

public class SignatureVerifyOptionBean {

	private boolean detectCode;
	private boolean crlReliability;
	private boolean caReliability;
	private boolean tsaReliability;
	private boolean recursive;
	private boolean childValidation;
	
	public boolean isDetectCode() {
		return detectCode;
	}
	public void setDetectCode(boolean detectCode) {
		this.detectCode = detectCode;
	}
	public boolean isCrlReliability() {
		return crlReliability;
	}
	public void setCrlReliability(boolean crlReliability) {
		this.crlReliability = crlReliability;
	}
	public boolean isCaReliability() {
		return caReliability;
	}
	public void setCaReliability(boolean caReliability) {
		this.caReliability = caReliability;
	}
	public boolean isTsaReliability() {
		return tsaReliability;
	}
	public void setTsaReliability(boolean tsaReliability) {
		this.tsaReliability = tsaReliability;
	}
	public boolean isRecursive() {
		return recursive;
	}
	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}
	public boolean isChildValidation() {
		return childValidation;
	}
	public void setChildValidation(boolean childValidation) {
		this.childValidation = childValidation;
	}
	@Override
	public String toString() {
		return "SignatureVerifyOptionBean [detectCode=" + detectCode + ", crlReliability=" + crlReliability + ", caReliability=" + caReliability
				+ ", tsaReliability=" + tsaReliability + ", recursive=" + recursive + ", childValidation=" + childValidation + "]";	
	}
}
