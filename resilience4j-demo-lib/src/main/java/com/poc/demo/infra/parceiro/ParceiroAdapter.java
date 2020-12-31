package com.poc.demo.infra.parceiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.poc.demo.domain.exception.BusinessException;
import com.poc.demo.domain.exception.FrutaInvalidException;
import com.poc.demo.domain.model.Cesta;

@Service
public class ParceiroAdapter {
	
	private Logger logger = LoggerFactory.getLogger(ParceiroAdapter.class);
	
	@Autowired
	private ParceiroFactory parceiroFactory;
	
	
	public Cesta getFrutas(String tipoParceiro) {
		Parceiro parceiro = parceiroFactory.get(tipoParceiro);
	    return parceiro.getFrutas();
	}

	public Cesta getCacheFrutas(Throwable throwable) throws Throwable {
		verify(throwable);		
		Parceiro parceiro = parceiroFactory.get("cache");
	    return parceiro.getFrutas();
	}

	private void verify(Throwable throwable) throws Throwable {
		if (throwable instanceof BusinessException || throwable instanceof FrutaInvalidException)
			throw throwable;
	}
	
}
