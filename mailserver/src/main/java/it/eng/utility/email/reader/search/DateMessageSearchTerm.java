package it.eng.utility.email.reader.search;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

/**
 * Classe di ricerca che identifica se messaggio dall'id
 * @author michele
 *
 */
public class DateMessageSearchTerm extends SearchTerm{

	private static final long serialVersionUID = 1L;

	private Date after;
	private Date before;
	
	public DateMessageSearchTerm(Date after,Date before) {
		this.after = after;
		this.before = before;
	}
	
	@Override
	public boolean match(Message msg) {
		try{
			GregorianCalendar actual = new GregorianCalendar();
			actual.setTime(msg.getSentDate());
	
			boolean afterok = true;
			boolean beforeok = true;
			
			if(after!=null){
				GregorianCalendar calendarafter = new GregorianCalendar();
				calendarafter.setTime(after);
				
				afterok = actual.after(calendarafter);
			}
			
			if(before!=null){
				GregorianCalendar calendarbefore = new GregorianCalendar();
				calendarbefore.setTime(before);
				
				beforeok = actual.before(calendarbefore);
			}
			
			return afterok && beforeok;				
			
		}catch(Exception e){
			return false;
		}
		
	}
	
}
