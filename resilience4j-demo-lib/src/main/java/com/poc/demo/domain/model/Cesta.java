package com.poc.demo.domain.model;

import java.util.List;

import com.poc.demo.domain.exception.FrutaInvalidException;

public class Cesta {

	private String local;
	
	private List<Fruta> frutas;

	public Cesta(String local, List<Fruta> frutas) {
		this.local = local;
		this.frutas = frutas;
		verify();
	}

	private void verify() {
		if (this.local == null)
			throw new FrutaInvalidException("Local não pode ser nulo");
		

		if (this.frutas == null)
			throw new FrutaInvalidException("Frutas não pode ser nulo");
		
	}

	public String getLocal() {
		return local;
	}

	public List<Fruta> getFrutas() {
		return frutas;
	}
	
	
}
