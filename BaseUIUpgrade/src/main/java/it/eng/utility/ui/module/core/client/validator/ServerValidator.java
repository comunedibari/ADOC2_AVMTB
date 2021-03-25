package it.eng.utility.ui.module.core.client.validator;

import com.smartgwt.client.types.ValidatorType;
import com.smartgwt.client.widgets.form.validator.Validator;

public class ServerValidator extends Validator{
	
	public ServerValidator() {
		setType(ValidatorType.SERVERCUSTOM);
		setClientOnly(false);
	}

	
}
