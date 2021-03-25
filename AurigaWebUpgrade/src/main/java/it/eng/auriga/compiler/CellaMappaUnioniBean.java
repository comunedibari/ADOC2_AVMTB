package it.eng.auriga.compiler;

public class CellaMappaUnioniBean {
	
	private int nroRaggruppamento;
	private boolean coveredCell;
	private int rowSpan;
	private int colSpan;
	
	public CellaMappaUnioniBean() {
		this.nroRaggruppamento = 0;
		this.coveredCell = true;
		this.rowSpan = 1;
		this.colSpan = 1;
	}
	public int getNroRaggruppamento() {
		return nroRaggruppamento;
	}
	public void setNroRaggruppamento(int nroRaggruppamento) {
		this.nroRaggruppamento = nroRaggruppamento;
	}
	public boolean isCoveredCell() {
		return coveredCell;
	}
	public void setCoveredCell(boolean coveredCell) {
		this.coveredCell = coveredCell;
	}
	public int getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nroRaggruppamento +" " + coveredCell + " " + rowSpan + " " + colSpan;
	}
}
