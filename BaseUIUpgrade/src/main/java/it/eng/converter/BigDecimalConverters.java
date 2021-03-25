package it.eng.converter;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.beanutils.locale.converters.BigDecimalLocaleConverter;

public class BigDecimalConverters extends BigDecimalLocaleConverter{

	@Override
	public Object convert(Class arg0, Object arg1) {
		if (arg1 instanceof String){
			if (StringUtils.isEmpty((String)arg1)){
				return null;
			} else return super.convert(arg1);
		} else return super.convert(arg1);
	}

}
