package it.eng.aurigamailbusiness.database.utility.bean.lucene;

public class LuceneBeanResult {

	private String idEmail;

	private Float score;

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Float getScore() {
		return score;
	}
}
