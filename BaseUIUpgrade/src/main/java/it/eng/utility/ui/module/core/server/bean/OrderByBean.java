package it.eng.utility.ui.module.core.server.bean;



/**
 * Bean contiene i valori per il sorting della tabella
 * @author michele
 *
 */
public class OrderByBean {

	private String columnname;
	private Direction direction;

	public OrderByBean(String columnname, Direction direction) {
		super();
		this.columnname = columnname;
		this.direction = direction;
	}
	
	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public enum Direction {
		ASC,
		DESC;
	}
	
}