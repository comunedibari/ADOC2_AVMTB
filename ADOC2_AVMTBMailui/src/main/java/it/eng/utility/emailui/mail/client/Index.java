package it.eng.utility.emailui.mail.client;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.emailui.core.client.callback.ServiceCallback;
import it.eng.utility.emailui.core.client.datasource.GWTRestDataSource;
import it.eng.utility.emailui.core.client.datasource.GWTRestService;
import it.eng.utility.emailui.core.client.util.JSONUtil;
import it.eng.utility.emailui.mail.shared.bean.MailCanStartBean;
import it.eng.utility.emailui.mail.shared.bean.MailconnectConfigBean;

public class Index extends VLayout {

	final GWTRestDataSource optionmailbox = new GWTRestDataSource("MailboxDataSource");

	final GWTRestService<MailconnectConfigBean, MailconnectConfigBean> mailconnectConfig = new GWTRestService<MailconnectConfigBean, MailconnectConfigBean>(
			"MailConnectConfigDatasource");

	final GWTRestService<MailCanStartBean, MailCanStartBean> mailCanStart = new GWTRestService<MailCanStartBean, MailCanStartBean>(
			"MailCanStartDatasource");

	SelectItem mailboxItem;
	String idMailconnect = "";
	String consoleType = "";
	String status = "";

	public Index() {

		MailCanStartBean lMailCanStartBean = new MailCanStartBean();
		mailCanStart.call(lMailCanStartBean, JSONUtil.MailCanStartBeanJsonWriter, JSONUtil.MailCanStartBeanJsonReader,
				new ServiceCallback<MailCanStartBean>() {

					@Override
					public void execute(MailCanStartBean pMailCanStartBean) {
						if (pMailCanStartBean.isCanStart()) {
							startIndex();
						} else {
							final Dialog lDialog = new Dialog();
							lDialog.setButtons(new Button[] {});
							lDialog.setIsModal(true);
							lDialog.setShowCloseButton(false);
							SC.warn("Attenzione",
									"Non è possibile avviare mailui: Non tutte le caselle hanno mail.mailbox.mailconnectid configurato",
									new BooleanCallback() {
										@Override
										public void execute(Boolean value) {

										}
									}, lDialog);
						}

					}
				});
	}

	protected void startIndex() {
		// inizializzo l'id mailbox
		MailconnectConfigBean mailconnectConfigBean = new MailconnectConfigBean();
		mailconnectConfig.call(mailconnectConfigBean, JSONUtil.MailconnectConfigBeanJsonWriter,
				JSONUtil.MailconnectConfigBeanJsonReader, new ServiceCallback<MailconnectConfigBean>() {
					@Override
					public void execute(MailconnectConfigBean bean) {
						idMailconnect = bean.getIdMailbox();
						consoleType = bean.getConsoleType();

					}
				});

		ToolStrip toolStrip = new ToolStrip();

		toolStrip.setWidth100();

		// Select di selezione delle mailbox
		mailboxItem = new SelectItem("mailbox", "Mailbox");
		mailboxItem.setWidth(500);
		mailboxItem.setShowTitle(true);

		PickerIcon refreshPicker = new PickerIcon(PickerIcon.REFRESH, new FormItemClickHandler() {
			public void onFormItemClick(FormItemIconClickEvent event) {
				mailboxItem.getOptionDataSource().invalidateCache();
			}
		});

		mailboxItem.setIcons(refreshPicker);
		mailboxItem.setDisplayField("name");
		mailboxItem.setValueField("idMailbox");
		mailboxItem.setOptionDataSource(optionmailbox);
		mailboxItem.setAllowEmptyValue(false);
		mailboxItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (event != null && event.getItem().getSelectedRecord() != null) {
					if (event.getItem().getSelectedRecord().getAttribute("idMailbox") != null) {

						// Carico il layout
						if (Index.this.getMembers().length >= 2) {
							Index.this.getMember(1).destroy();
						}
						Index.this.addMember(new Layout(event.getValue() != null ? event.getValue().toString() : " "),
								1);
					}
				}
				markForRedraw();

			}

		});

		toolStrip.addFormItem(mailboxItem);

		// Bottone di creazione nuova mailbox (Apre una window)
		ToolStripButton createmailbox = new ToolStripButton();
		createmailbox.setIcon("operation.png");
		createmailbox.setTitle("Configura nuova mailbox");

		createmailbox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Apro la finestra con i valori da inserire
				WindowAddMailbox addmailbox = new WindowAddMailbox();
				addmailbox.show();
			}
		});

		toolStrip.addButton(createmailbox);

		// Bottone per sapere quale MailConnect si sta usando (Apre una window)
		ToolStripButton mailconnectid = new ToolStripButton();
		mailconnectid.setIcon("question_mark.png");
		mailconnectid.setTitle("Info MailConnectId");

		mailconnectid.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Apro la finestra con il valore del MailConnectID
				SC.warn("MailConnectID configurato per questo server è: " + idMailconnect);
			}
		});

		toolStrip.addButton(mailconnectid);

		addMember(toolStrip);

		// Bottone per sapere quale MailConnect si sta usando (Apre una window)
		ToolStripButton consoletype = new ToolStripButton();
		consoletype.setIcon("consoletype.png");
		consoletype.setTitle("Vai alla console di gestione dei messaggi");

		consoletype.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Apro la finestra con il valore del consoleType
				com.google.gwt.user.client.Window.Location
						.assign(com.google.gwt.user.client.Window.Location.getHref() + "start.html");
			}
		});

		toolStrip.addButton(consoletype);
		addMember(toolStrip);

		setWidth100();
		setHeight100();
	}

}