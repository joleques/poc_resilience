package com.poc.demo.infra.parceiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.poc.demo.domain.exception.FrutaInvalidException;
import com.poc.demo.domain.model.Cesta;

@Service
class AtacadaoAdapter implements Parceiro {

	private Logger logger = LoggerFactory.getLogger(AtacadaoAdapter.class);
	
	@Override
	public Cesta getFrutas() {
		String message = "Atacadão não possui frutas no memoneto.....";
		logger.info(message);
		throw new FrutaInvalidException(message);
	}
}
