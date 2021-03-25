package it.eng.aurigamailbusiness.exception;

public class ConstraintException extends Exception {

	private static final long serialVersionUID = -9194255608381738447L;

	public ConstraintException(String property, Class beanClass){
		super("L'oggetto di tipo " + beanClass.getSimpleName() + " non può avere la proprietà " + property + " nulla");
	}

	public ConstraintException(Class<? extends Object> beanClass) {
		super("L'oggetto di tipo " + beanClass.getSimpleName() + " non può essere nullo");
	}
}
