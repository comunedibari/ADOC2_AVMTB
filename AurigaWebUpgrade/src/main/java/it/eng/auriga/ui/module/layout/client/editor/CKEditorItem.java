package it.eng.auriga.ui.module.layout.client.editor;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.Styles;
import it.eng.utility.ui.module.layout.client.common.IDatiSensibiliItem;
import it.eng.utility.ui.module.layout.client.common.IEditorItem;

public class CKEditorItem extends CanvasItem implements IEditorItem, IDatiSensibiliItem {
	
	private CKEditor ckeCanvas;
	private Record paramsViewerCKEditor;
//	private String ckeTitle;
	
	public CKEditorItem(String name)  {
		this(name, -1, "STANDARD", 10, -1, "", false);
	}
	
	public CKEditorItem(String name, int numMaxCaratteri)  {
		this(name, numMaxCaratteri, "STANDARD", 10, -1, "", false);
	}
	
	public CKEditorItem(String name, int numMaxCaratteri, String tipoEditor)  {
		this(name, numMaxCaratteri, tipoEditor, 10, -1, "", false);
	}
	
	public CKEditorItem(String name, String tipoEditor)  {
		this(name, -1, tipoEditor, 10, -1, "", false);
	}
	
	public CKEditorItem(String name, int numMaxCaratteri, String tipoEditor, int altezzaInRighe)  {
		this(name, numMaxCaratteri, tipoEditor, altezzaInRighe, -1, "", false);
	}
	
	public CKEditorItem(String name, int numMaxCaratteri, String tipoEditor, int altezzaInRighe, int larghezza, String defaultValue) {
		this(name, numMaxCaratteri, tipoEditor, altezzaInRighe, larghezza, defaultValue, false);
	}
	
	public CKEditorItem(String name, final int numMaxCaratteri, final String tipoEditor, int altezzaInRighe, int larghezza, String defaultValue, boolean upperCase) {
		this(name, numMaxCaratteri, tipoEditor, (altezzaInRighe * 20) + "", larghezza, defaultValue, upperCase, false);
	}
	
	public CKEditorItem(String name, final int numMaxCaratteri, final String tipoEditor, int altezzaInRighe, int larghezza, String defaultValue, boolean upperCase, boolean hideBorder) {
		this(name, numMaxCaratteri, tipoEditor, (altezzaInRighe * 20) + "", larghezza, defaultValue, upperCase, hideBorder);
	}
	
	public CKEditorItem(String name, final int numMaxCaratteri, final String tipoEditor, String altezza, int larghezza, String defaultValue, boolean upperCase, boolean hideBorder) {
		
		super(name);
		
		// devo salvare i parametri per il viewer del CKEditor, per instanziarlo nello stesso modo rispetto all'item di partenza
		paramsViewerCKEditor = new Record();
		paramsViewerCKEditor.setAttribute("numMaxCaratteri", numMaxCaratteri);
		paramsViewerCKEditor.setAttribute("tipoEditor", tipoEditor);
		paramsViewerCKEditor.setAttribute("upperCase", upperCase);
		paramsViewerCKEditor.setAttribute("hideBorder", hideBorder);
		
		if (altezza == null || "".equalsIgnoreCase(altezza)) {
			// Metto una altezza di default, ovvero quello che corrisponde a 10 righe)
			altezza = "200";
		}
		String strLarghezza =  larghezza > 0 ? larghezza + "" : "100%";
		ckeCanvas = new CKEditor(name + "_" + SC.generateID(), numMaxCaratteri, tipoEditor, altezza, strLarghezza, defaultValue, upperCase, hideBorder);
		ckeCanvas.setKeepInParentRect(true);
		ckeCanvas.addHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				fireEvent(new ChangedEvent(getJsObj()));
			}
		}, ChangedEvent.getType());
		
		VLayout lVLayout = creaVLayout();
		lVLayout.setMembers(ckeCanvas);
		setCanvas(lVLayout);
		
		setOverflow(Overflow.VISIBLE);
		setWidth("100%");
		if (altezza.indexOf('%') != -1) {
			setHeight(altezza);
		} else {
			setHeight(numMaxCaratteri > -1 ? Integer.parseInt(altezza) + 70 : Integer.parseInt(altezza) + 58);
		}
		setShowTitle(false);
		setShowHint(false);
		setShowIcons(showViewer());		
		setColSpan(1);
//		setValidators(new CustomValidator() {
//			
//			@Override
//			protected boolean condition(Object value) {
//				if (!AurigaLayout.getParametroDBAsBoolean("ATTIVA_SOST_BR_HTML_MODELLI") && ckeCanvas != null && ckeCanvas.htmlContainsBrTag(ckeCanvas.getValue())) {
//					// se il testo contiene il tag br richiamo il blur che pulisce 
//					// il testo e blocco l'azione
//					ckeCanvas.onBlur();
//					return false;
//				}
//				return true;
//			}
//		});

		buildViewer(this);
		
		addShowValueHandler(new ShowValueHandler() {
			
			@Override
			public void onShowValue(ShowValueEvent event) {
				String value = (String) event.getDataValue();
				if(ckeCanvas != null) {
					ckeCanvas.setValue(value);			
				}
			}
		});
	}
	
	protected void buildViewer(final CKEditorItem item) {
    	if(showViewer()) {
    		FormItemIcon viewerIcon = new FormItemIcon();
    		viewerIcon.setHeight(16);
    		viewerIcon.setWidth(16);
//    		viewerIcon.setInline(true);       
    		viewerIcon.setNeverDisable(true);
    		viewerIcon.setSrc("buttons/view.png");
    		viewerIcon.setPrompt("Visualizza contenuti");
    		viewerIcon.setCursor(Cursor.POINTER);
    		viewerIcon.setAttribute("cellStyle", Styles.formCellClickable);
    		viewerIcon.addFormItemClickHandler(new FormItemClickHandler() {

    			@Override
    			public void onFormItemClick(FormItemIconClickEvent event) {
    				manageOnViewerClick(item);    				
    			}
    		});
    			
    		List<FormItemIcon> icons = new ArrayList<FormItemIcon>();
    		icons.add(viewerIcon);
    		item.setIcons(icons.toArray(new FormItemIcon[icons.size()]));
    		item.setIconVAlign(VerticalAlignment.CENTER);
    	}
    }
	
	public void manageOnViewerClick(final CKEditorItem item) {
    	ViewerCKEditorValueWindow lViewerCKEditorValueWindow = new ViewerCKEditorValueWindow(item);
    	lViewerCKEditorValueWindow.show();
    }
    
    public boolean showViewer() {
    	return true;
    }
	
//  @Override
//	public void setTitle(String title) {
//		ckeTitle = title;
//		VLayout lVLayout = (VLayout) getCanvas();
//		if(lVLayout != null && lVLayout.getIsGroup()) {
//			lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + title + "</span>");
//		}
//	}
//	
//	@Override
//	public String getTitle() {
//		return ckeTitle;
//	}
	
	protected VLayout creaVLayout() {
		VLayout lVLayout = new VLayout();
		lVLayout.setAlign(VerticalAlignment.CENTER);		
		return lVLayout;
	}

	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		if(ckeCanvas != null) {
			ckeCanvas.setWidth(width);
		}
	}
	
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		if(ckeCanvas != null) {
			ckeCanvas.setWidth(width);
		}
	}
	
	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		if(ckeCanvas != null) {
			ckeCanvas.setHeight(height);
		}
	}
	
	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		if(ckeCanvas != null) {
			ckeCanvas.setHeight(height);
		}
	}
	
	@Override
	public void clearValue() {
		setValue("");
	}
	
	@Override
	public String getValue() {
		if(ckeCanvas != null) {
			// la pulizia dei caratteri dell'html che danno problemi con Shibboleth viene fatta lato DB quindi commento
//			return cleanTestoHtml(ckeCanvas.getValue());
			return ckeCanvas.getValue();
		}
		return null;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		if(ckeCanvas != null) {
			ckeCanvas.setReadOnly(!canEdit);	
			setAttribute("readOnly", !canEdit);
		}
	}
	
	@Override
	public Boolean getCanEdit() {
		boolean readOnly = getAttributeAsBoolean("readOnly") != null ? getAttributeAsBoolean("readOnly") : false;
		return !readOnly;
	}
	
	@Override
	public void setRequired(Boolean required) {
		setAttribute("obbligatorio", required);
	}
	
	@Override
	public Boolean getRequired() {
		return getAttributeAsBoolean("obbligatorio") != null ? getAttributeAsBoolean("obbligatorio") : false;
	}
	
	@Override
	public Boolean validate() {		
		boolean valid = !getRequired() || (getValue() != null && !"".equals(getValue()));
		if(getForm() != null) {
			getForm().clearFieldErrors(getName(), true);
			if(!valid) {
				getForm().setFieldErrors(getName(), "Campo obbligatorio");
			}
		}
		return valid;
	}
	
	@Override
	public boolean hasDatiSensibili() {
		return getValue() != null && getValue().contains("<s>") && getValue().contains("</s>"); 		
	}
	
	@Override
	public boolean isCKEditor() {
		return true;
	}
	
	@Override
	public void manageOnDestroy() {
		if(ckeCanvas != null) {
			ckeCanvas.close();
			ckeCanvas.markForDestroy();
		}
	}
	
	@Override
	public void setVisible(Boolean visible) {
		super.setVisible(visible);
		if(ckeCanvas != null) {
			ckeCanvas.setVisible(visible);
		}
	}		
	
	private String cleanTestoHtml(String html) {
		if (html != null && !"".equals(html)) {
			String cleanedHtml = html.replaceAll("&nbsp;", "&#160;")
					.replaceAll("&ndash;", "&#8211;")
					.replaceAll("&ldquo;", "&#8220;")
					.replaceAll("&rdquo;", "&#8221;")
					.replaceAll("&rsquo;", "&#8217;")
					.replaceAll("&lsquo;", "&#8216;")
					.replaceAll("&sbquo;", "&#8218;")
					.replaceAll("&bdquo;", "&#8222;");
			
			return cleanedHtml;
		} else {
			return html;
		}
	}

	public Record getParamsViewerCKEditor() {
		return paramsViewerCKEditor;
	}
	
}