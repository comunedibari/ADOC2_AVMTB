package it.eng.auriga.repository2.jaxws.webservices.addunitadoc;

public class WSAddUdConfig {

	private boolean useUniqueSession = true;
	private String visureBPMProcessDefName;
	private String visureTipoProcesso;
	private String pathAttach;
	private String flgNuovaGestione;
	private String flgCallFileOpMultiThread;
	private String maxSizePoolThread;
	
	public boolean isUseUniqueSession() {
		return useUniqueSession;
	}
	public void setUseUniqueSession(boolean useUniqueSession) {
		this.useUniqueSession = useUniqueSession;
	}
	
	public String getVisureBPMProcessDefName() {
		return visureBPMProcessDefName;
	}
	public void setVisureBPMProcessDefName(String visureBPMProcessDefName) {
		this.visureBPMProcessDefName = visureBPMProcessDefName;
	}
	public String getVisureTipoProcesso() {
		return visureTipoProcesso;
	}
	public void setVisureTipoProcesso(String visureTipoProcesso) {
		this.visureTipoProcesso = visureTipoProcesso;
	}
	public String getPathAttach() {
		return pathAttach;
	}
	public void setPathAttach(String pathAttach) {
		this.pathAttach = pathAttach;
	}
	public String getFlgNuovaGestione() {
		return flgNuovaGestione;
	}
	public void setFlgNuovaGestione(String flgNuovaGestione) {
		this.flgNuovaGestione = flgNuovaGestione;
	}
	public String getFlgCallFileOpMultiThread() {
		return flgCallFileOpMultiThread;
	}
	public void setFlgCallFileOpMultiThread(String flgCallFileOpMultiThread) {
		this.flgCallFileOpMultiThread = flgCallFileOpMultiThread;
	}
	public String getMaxSizePoolThread() {
		return maxSizePoolThread;
	}
	public void setMaxSizePoolThread(String maxSizePoolThread) {
		this.maxSizePoolThread = maxSizePoolThread;
	}
		
}
