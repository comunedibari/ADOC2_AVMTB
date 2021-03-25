package it.eng.utility.ui.module.layout.client.common.file;

import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
/**
 * Canvas che disegna un input file mascherato secondo le regole del css per la classe "cabinet"
 * L'input file è disegnato all'interno di un form che per default ha action = "upload" 
 * e come target = "uploadTarget". All'interno del form viene anche inviato un input hidden
 * che contiene una proprietà custom che si chiama smartId
 * 
 * Sul BaseUi abbiamo di default una servlet che si occupa di gestinre l'upload di un file mappata su "upload".
 * 
 * Di default viene anche azionata una {@link ProgressBarWindow} che mostra lo stato del caricamento.
 * 
 * In fase di init, occorre specificare una {@link UploadItemCallBackHandler}. Tale callback viene automaticamente 
 * richiamata dalla funzione js nativa changeNameFileCallback relativa a questo Canvas. Per essere sicuri
 * che la funzione sia univoca, il nome viene generato in fase di init appendendo a 
 * changeNameFile_ e showProgressWindow_ lo smartId generato a runtime. 
 * Tale js nativo (changeNameFile) è invocato in risposta dalla servlet di
 * upload, dopo aver recuperato lo smartId dal form ed averlo agganciato al nome della function da invocare
 * 
 * 
 * @author Rametta
 *
 */
public class PrettyFileUploadInput extends Canvas {

	private String _target = "uploadTarget";
	private String _action = "springdispatcher/UploadServlet/";
	private UploadItemCallBackHandler _callback;
	private ProgressBarWindow _window;
	private boolean showProgressWindow = true;
	private String smartId;
	private String title;
	private CssAndDimensionFileInput mCssAndDimensionFileInput;
	private int indiceTab;

	/**
	 * Crea il canvas di default e chiama in callback il metodo
	 * uploadEnd della {@link UploadItemCallBackHandler} passata come parametro 
	 * @param pUploadItemCallBackHandler 
	 */
	public PrettyFileUploadInput(UploadItemCallBackHandler pUploadItemCallBackHandler){
		this(pUploadItemCallBackHandler, new CssAndDimensionFileInput());
	}

	/**
	 * Crea il canvas di default e chiama in callback il metodo
	 * uploadEnd della {@link UploadItemCallBackHandler} passata come parametro 
	 * @param pUploadItemCallBackHandler 
	 */
	public PrettyFileUploadInput(UploadItemCallBackHandler pUploadItemCallBackHandler, CssAndDimensionFileInput pCssAndDimensionFileInput){
		if (pCssAndDimensionFileInput == null){
			this.mCssAndDimensionFileInput = new CssAndDimensionFileInput();
		} else {
			this.mCssAndDimensionFileInput = pCssAndDimensionFileInput; 
		}
		_callback = pUploadItemCallBackHandler;
		smartId = SC.generateID();
		initChangeFileNameCallback(this, "changeNameFile_" + smartId, Layout.isExternalPortlet);
		initShowProgressWindow(this, "showProgressWindow_" + smartId, Layout.isExternalPortlet);
		initManageError(this, "manageError_" + smartId, Layout.isExternalPortlet);
		setHeight(mCssAndDimensionFileInput.getHeight());
		setWidth(mCssAndDimensionFileInput.getWidth());
		setOverflow(Overflow.HIDDEN);
		setAlign(Alignment.CENTER);		
		setOverflow(Overflow.VISIBLE);
		setCursor(mCssAndDimensionFileInput.getCursor());
		if(mCssAndDimensionFileInput.isShowHover()) {
			setPrompt(I18NUtil.getMessages().prettyFileUploadInput_title());
			setShowHover(true);
			setShowHoverComponents(true);
		} else {
			setPrompt(null);
			setShowHover(false);
			setShowHoverComponents(false);
		}
	}

	private native void initManageError(PrettyFileUploadInput pPrettyFileUploadInput, String functionName, boolean isExternalPortlet) /*-{
		if (isExternalPortlet){	
		   $doc[functionName] = function (value) {
		   	   pPrettyFileUploadInput.@it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadInput::manageErrorCallback(Ljava/lang/String;)(value);
		   };
		} else {
			$wnd[functionName] = function (value) {
		   	   pPrettyFileUploadInput.@it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadInput::manageErrorCallback(Ljava/lang/String;)(value);
		   };
		}
	}-*/;

	@Override
	public String getInnerHTML() { 
	 	if (UserInterfaceFactory.isAttivaAccessibilita()){
			setIndiceTab(getTabIndex().intValue());
			return "<form NAME=\"form1\" action=\"" + _action + "\" STYLE=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + "; margin: 0px; padding: 0px;\" ENCTYPE=\"multipart/form-data\" method=\"post\" target=\"" + _target + "\" >" +
				   "<label style=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + ";\" class=\"" + mCssAndDimensionFileInput.getCssClass() + "\" >" +
				   "<input name=\"smartId\" id=\"smartId\" type=\"hidden\" style=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + ";\" value = \"" + smartId + "\" /> "+	
				   "<input name=\"isExternalPortlet\" id=\"isExternalPortlet\" type=\"hidden\" style=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + ";\" value = \"" + Layout.isExternalPortlet + "\" /> "+	
				   "<input name=\"fileUploadAttr\" id=\"filePath"+indiceTab+"\" type=\"file\" class=\"file\" style=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + ";\" onchange=\"if (this.value!=null && this.value != '') {" + (Layout.isExternalPortlet?"document":"window.top")+ ".showProgressWindow_" + smartId + "('test');this.form.submit();}\"/>" +
				   "</label>" +
				   "</form>";
		} else {
			return "<form NAME=\"form1\" action=\"" + _action + "\" STYLE=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + "; margin: 0px; padding: 0px;\" ENCTYPE=\"multipart/form-data\" method=\"post\" target=\"" + _target + "\">" +
				   "<label style=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + ";\" class=\"" + mCssAndDimensionFileInput.getCssClass() + "\" tabIndex=\"-1\">" +
				   "<input name=\"smartId\" id=\"smartId\" type=\"hidden\" style=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + ";\" value=\"" + smartId + "\" tabIndex=\"-1\"/> "+	
				   "<input name=\"isExternalPortlet\" id=\"isExternalPortlet\" type=\"hidden\" style=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + ";\" value=\"" + Layout.isExternalPortlet + "\" tabIndex=\"-1\"/> "+	
				   "<input name=\"fileUploadAttr\" id=\"filePath\" type=\"file\" class=\"file\" style=\"cursor: " + mCssAndDimensionFileInput.getCursor().getValue() + ";\" onchange=\"if(this.value != null && this.value != '') { " + (Layout.isExternalPortlet?"document":"window.top")+ ".showProgressWindow_" + smartId + "('test'); this.form.submit(); }; this.value=null; return false;\" tabIndex=\"-1\"/>" +
				   "</label>" +
				   "</form>";
		}
	}

	private native void initChangeFileNameCallback(PrettyFileUploadInput pPrettyFileUploadInput, String functionName, boolean isExternalPortlet) /*-{
	   if (isExternalPortlet){	
		   $doc[functionName] = function (value) {
		       pPrettyFileUploadInput.@it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadInput::changeNameFileCallback(Ljava/lang/String;)(value);
		   };
	   } else {
	   	   $wnd[functionName] = function (value) {
		       pPrettyFileUploadInput.@it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadInput::changeNameFileCallback(Ljava/lang/String;)(value);
		   };
	   }
	}-*/;

	private native void initShowProgressWindow(PrettyFileUploadInput pPrettyFileUploadInput, String functionName, boolean isExternalPortlet) /*-{
	 if (isExternalPortlet){	
	   $doc[functionName] = function (value) {
    		pPrettyFileUploadInput.@it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadInput::showProgressWindow(Ljava/lang/String;)(value);
		};
	 } else {
	   $wnd[functionName] = function (value) {
    		pPrettyFileUploadInput.@it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadInput::showProgressWindow(Ljava/lang/String;)(value);
		};
	 }	
	}-*/;

	/**
	 * Invocata dalla servlet mediante $wnd.changeNameFileCallback,
	 * splitta il nome del file, verifica se l'upload non è stato bloccato,
	 * chiude la progresswindo se presente ed invoca il metodo uploadEnd della
	 * callback
	 * @param file
	 */
	public void changeNameFileCallback(String file){
		String[] result = file.split("#");		
		String displayFilename = result[0];
		String uri = result[1];
		boolean cancelUpload = false;
		if (showProgressWindow){
			cancelUpload = _window.cancelUpload;
			_window.destroy();
		}
		if (!cancelUpload)
			_callback.uploadEnd(displayFilename, uri);
	}

	public void manageErrorCallback(String error){
		boolean cancelUpload = false;
		if (showProgressWindow){
			cancelUpload = _window.cancelUpload;
			_window.destroy();
		}
		if (!cancelUpload)
			_callback.manageError(error);
	}

	/**
	 * Mostra la progressBar
	 * @param file
	 */
	public void showProgressWindow(String file){
		if (showProgressWindow){
			_window = new ProgressBarWindow();
			_window.show();
		}	
	}

	/**
	 * Cambia il target del form generato
	 * @param _target
	 */
	public void setTarget(String _target) {
		this._target = _target;
	}

	/**
	 * Cambia la action del form generato
	 * @param _action
	 */
	public void setAction(String _action) {
		this._action = _action;
	}

	/**
	 * Mostra o nasconde la progress bar alla fine della 
	 * selezione del file
	 * @param showProgressWindow
	 */
	public void setShowProgressWindow(boolean showProgressWindow) {
		this.showProgressWindow = showProgressWindow;
	}

	public String getSmartId() {
		return smartId;
	}

	public void setSmartId(String smartId) {
		this.smartId = smartId;
	}
	
	
	public int getIndiceTab() {
		return indiceTab;
	}

	
	public void setIndiceTab(int indiceTab) {
		this.indiceTab = indiceTab;
	}
	
	public native void focusOnInputFile (int tabIndex)/*-{
	$doc.getElementById("filePath"+tabIndex).click();
    
	}-*/;
	
//	public native void focusOnInputFile (int tabIndex)/*-{
//	var event = new MouseEvent('click', {
//    view: window,
//    bubbles: true,
//    cancelable: true
//  	});
//    $doc.getElementById("filePath"+tabIndex).dispatchEvent(event);
//	}-*/;

}
