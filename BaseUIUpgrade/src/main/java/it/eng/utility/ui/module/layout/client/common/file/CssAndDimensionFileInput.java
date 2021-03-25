package it.eng.utility.ui.module.layout.client.common.file;

import com.smartgwt.client.types.Cursor;

public class CssAndDimensionFileInput {

	private String cssClass = "cabinet";
	private int height = 16;
	private int width = 16;
	private Cursor cursor = Cursor.POINTER;
	private boolean showHover = true;
	
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Cursor getCursor() {
		return cursor;
	}
	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	public boolean isShowHover() {
		return showHover;
	}
	public void setShowHover(boolean showHover) {
		this.showHover = showHover;
	}
	
}
