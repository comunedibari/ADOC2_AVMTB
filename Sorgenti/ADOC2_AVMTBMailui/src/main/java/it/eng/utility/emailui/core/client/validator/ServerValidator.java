package it.eng.utility.emailui.core.client.validator;

import com.smartgwt.client.types.ValidatorType;
import com.smartgwt.client.widgets.form.validator.Validator;

public class ServerValidator extends Validator{
	
	public ServerValidator() {
		setType(ValidatorType.SERVERCUSTOM);
		setClientOnly(false);
	}

	
}
