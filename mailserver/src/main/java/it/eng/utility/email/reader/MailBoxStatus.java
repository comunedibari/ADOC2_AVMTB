package it.eng.utility.email.reader;

public enum MailBoxStatus {

	MAILBOX_ACTIVE("active"),
	MAILBOX_OFF("off"),
	MAILBOX_DISABLED("disabled");
	
	private String status;
	
	private MailBoxStatus(String status){
		this.status = status;
	}
	
	public String status(){
		return this.status;
	}
	
	public static MailBoxStatus getForValue(String value){
		for(MailBoxStatus mess:MailBoxStatus.values()){
			if(mess.status.equals(value)){
				return mess;
			}
		}
		return null;
	}
	
}
