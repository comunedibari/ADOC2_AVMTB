package it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author cristiano
 *
 */

public class ProcessTypesXMLBean {

	@NumeroColonna(numero = "1")
	private Integer id;

	@NumeroColonna(numero = "2")
	private String nome;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
