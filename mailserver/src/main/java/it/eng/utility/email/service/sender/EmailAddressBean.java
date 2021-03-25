package it.eng.utility.email.service.sender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;


/**
 * Indirizzi di invio della mail 
 * @author michele
 *
 */
public class EmailAddressBean {

	private List<String> addressto = new ArrayList<String>(); 
	private List<String> addresscc = new ArrayList<String>();
	private List<String> addressbcc = new ArrayList<String>();
	
	private List<String> alladdress = new ArrayList<String>();
	
	public List<String> getAddressto() {
		return addressto;
	}
	public void setAddressto(List<String> addressto) {
		this.addressto = addressto;
	}
	public List<String> getAddresscc() {
		return addresscc;
	}
	public void setAddresscc(List<String> addresscc) {
		this.addresscc = addresscc;
	}
	public List<String> getAddressbcc() {
		return addressbcc;
	}
	public void setAddressbcc(List<String> addressbcc) {
		this.addressbcc = addressbcc;
	}
	
	/**
	 * Recupero il numero totale di righe
	 * @return
	 */
	public Integer getAddressSize(){
		return addressto.size()+addresscc.size()+addressbcc.size();
	}
	
	/**
	 * Recupero tutti i gruppi
	 */
	public List<String> getAddressForGroup(int group, int maxsize){
		if(alladdress.isEmpty()){
			alladdress.addAll(addressto);
			alladdress.addAll(addresscc);
			alladdress.addAll(addressbcc);
		}			
		return Arrays.asList(ArrayUtils.subarray(alladdress.toArray(new String[0]), maxsize*group, (maxsize*(group+1)))); 	
	}
	
	
	/**
	 * Restituisce il numero totale di gruppi
	 * @return
	 */
	public Integer getgroup(Integer maxsize) {
		int group = getAddressSize().intValue()/maxsize.intValue();
		if(getAddressSize().intValue()%maxsize.intValue()!=0){
			group++;
		}	
		return group;
	}
}