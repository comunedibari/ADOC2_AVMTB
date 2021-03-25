package it.eng.util;

import it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleInput;
import it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleInput.FlagArrivoInvio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaoUtil {

	public static String[] getProfiliToSearch(ListaIdCaselleInput pListaIdCaselleInput){
		List<FlagArrivoInvio> lListFlags = pListaIdCaselleInput.getFlag();
		if (lListFlags == null) return new String[]{};
		List<String> lListProfili = new ArrayList<String>();
		for (FlagArrivoInvio lFlagArrivoInvio : lListFlags){
			lListProfili.addAll(Arrays.asList(lFlagArrivoInvio.getProfili()));
		}
		return lListProfili.toArray(new String[]{});
	}
}
