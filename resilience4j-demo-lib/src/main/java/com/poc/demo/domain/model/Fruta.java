package com.poc.demo.domain.model;

import com.poc.demo.domain.exception.FrutaInvalidException;

public class Fruta {

	private Long id;
	
	private String descricao;

	public Fruta(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
		verify();
	}

	private void verify() {
		if (this.id == null)
			throw new FrutaInvalidException("Codigo da fruta não pode ser nulo");
		

		if (this.descricao == null)
			throw new FrutaInvalidException("Descrição da fruta não pode ser nulo");
		
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	
	
}
