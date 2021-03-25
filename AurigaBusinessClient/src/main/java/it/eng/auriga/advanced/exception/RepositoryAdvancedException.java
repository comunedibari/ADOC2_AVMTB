package it.eng.auriga.advanced.exception;

public class RepositoryAdvancedException extends java.lang.Exception 
{
    int iCodice=0;
    String iContext = "";


    /**
     * Creates a new instance of ObjectHandlerException
     * @param message   = messaggio
     */
    public RepositoryAdvancedException(String message) {
        super("[RepositoryAdvanced] " + message);
    }

    /**
     *
     * @param innerCode = custom Code
     * @param message   = messaggio
     */
    public RepositoryAdvancedException(int innerCode, String message) {
        this(message);
        this.iCodice = innerCode;
    }
    
    /**
    *
    * @param innerCode = custom Code
    * @param message   = messaggio
    */
   public RepositoryAdvancedException(int innerCode, String message, String context) {
       this(message);
       this.iCodice = innerCode;
       this.iContext = context;
   }

    public int getCodice(){
        return this.iCodice;
    }
    
    public String getContext(){
        return this.iContext;
    }

}

