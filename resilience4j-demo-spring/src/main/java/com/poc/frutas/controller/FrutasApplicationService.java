package com.poc.frutas.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import com.poc.frutas.domain.model.Cesta;
import com.poc.frutas.domain.model.Fruta;
import com.poc.frutas.infra.parceiro.ParceiroAdapter;

@Service
public class FrutasApplicationService {

	@Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

	@Autowired
	private ParceiroAdapter parceiroAdapter;

	private Logger logger = LoggerFactory.getLogger(ParceiroAdapter.class);
	
	
	public Cesta getFrutas(String tipoParceiro) {
	    CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
	    
	    return circuitBreaker.run(() -> parceiroAdapter.getFrutas(tipoParceiro), throwable -> {
			try {
				return parceiroAdapter.getCacheFrutas(throwable);
			} catch (Throwable e) {
				String message = String.format("Local Invalido [%s]", e.getMessage());
				logger.info(message);
				return new Cesta(message, new ArrayList<Fruta>());
			}
		});
	}

}
