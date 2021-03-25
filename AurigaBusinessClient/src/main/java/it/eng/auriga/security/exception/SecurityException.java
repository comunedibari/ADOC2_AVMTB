package it.eng.auriga.security.exception;

public class SecurityException extends java.lang.Exception 
{
    int iCodice=0;
    String iContext = "";


    /**
     * Creates a new instance of ObjectHandlerException
     * @param message   = messaggio
     */
    public SecurityException(String message) {
        super("[Security] " + message);
    }

    /**
     *
     * @param innerCode = custom Code
     * @param message   = messaggio
     */
    public SecurityException(int innerCode, String message) {
        this(message);
        this.iCodice = innerCode;
    }
    
    /**
    *
    * @param innerCode = custom Code
    * @param message   = messaggio
    */
   public SecurityException(int innerCode, String message, String context) {
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

