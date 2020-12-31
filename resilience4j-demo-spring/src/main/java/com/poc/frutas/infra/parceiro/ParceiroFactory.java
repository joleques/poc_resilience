package com.poc.frutas.infra.parceiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poc.frutas.domain.exception.BusinessException;

@Component
public class ParceiroFactory {

	@Autowired
	private CeasaAdapter ceasa;

	@Autowired
	private MercadoPublicoAdapter mercadoPublico;

	@Autowired
	private AtacadaoAdapter atacadaoAdapter;

	@Autowired
	private CacheAdapter cacheAdapter;
	
	
	public Parceiro get(String parceiro) {
		if ("ceasa".equalsIgnoreCase(parceiro))
			return ceasa;

		if ("mercadopublico".equalsIgnoreCase(parceiro))
			return mercadoPublico;

		if ("atacadao".equalsIgnoreCase(parceiro))
			return atacadaoAdapter;

		if ("cache".equalsIgnoreCase(parceiro))
			return cacheAdapter;
		
		throw new BusinessException("Tipo de parceiro invalido....");
	}
}
