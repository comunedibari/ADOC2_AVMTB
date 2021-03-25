/*
 * Autore: Antonio Vendramini
 * data: 20/10/2006
 * 
 * Il connection Token e' un parametro obbligatorio per l'invocazione delle stored procedure del progetto
 * commercio.
 * 
 * Il token viene memorizzato in sessione durante la fase di login (eng.commercio.servlet.Login)
 * 
 * Questa classe reperisce dalla sessione il token e lo restituisce come parametro Types.VARCHAR
 * al TableMAnagerProgress.makeProperty. 
 * 
 * In questa maniera si evita di aggiungere nel codice
 * HTML della pagina generata (e quindi in chiaro) il token per l'invocazione delle stored
 * rendendo il processo piu' sicuro.
 * 
 */

package eng.storefunction;

import  eng.database.exception.EngException;
import eng.util.Logger;
import  java.sql.Types;

public class ConnectionToken extends HttpPrimitive
{
	public ConnectionToken(int position) throws EngException
	{
		this(position, null);
	}

	public ConnectionToken(int position, String name) throws EngException
	{
		super(position, Types.VARCHAR, name);
		setModeIn();
	}

	public void makeValue(Object connectionToken)  throws EngException
	{
		setValuePrimitive(((String)connectionToken));
	}

	public  String  toString()
	{
		StringBuffer  sb = new StringBuffer("(ConnectionToken :");
		sb.append(super.toString());
		sb.append(")");
		return  sb.toString();
	}

	public Parameter cloneMe()  throws eng.database.exception.EngException 
	{
		ConnectionToken p = new ConnectionToken(this.getPosition(), this.getName());
		if (this.isModeIn()) p.setModeIn();
		else if (this.isModeOut()) p.setModeOut();
		else if (this.isModeInOut()) p.setModeInOut();
		return p;
	}
}