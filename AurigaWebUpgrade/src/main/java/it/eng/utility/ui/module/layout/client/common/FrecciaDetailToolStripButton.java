package it.eng.utility.ui.module.layout.client.common;

public class FrecciaDetailToolStripButton extends DetailToolStripButton {

	public FrecciaDetailToolStripButton() {
		this(true);
	}
	
	public FrecciaDetailToolStripButton(boolean showBorder) {
		super("", "menu/menu.png", false);
		setIconWidth(12);
		setIconHeight(16);
		if(showBorder) {
			setBaseStyle(it.eng.utility.Styles.frecciaDetailButton);
		} else {
			setBaseStyle(it.eng.utility.Styles.frecciaDetailButtonNoBorder);
		}		
	}

}