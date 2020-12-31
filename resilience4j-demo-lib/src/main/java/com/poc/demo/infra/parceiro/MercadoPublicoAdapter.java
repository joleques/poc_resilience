package com.poc.demo.infra.parceiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.poc.demo.domain.exception.ApiParceiroException;
import com.poc.demo.domain.model.Cesta;

@Service
class MercadoPublicoAdapter implements Parceiro{

	private Logger logger = LoggerFactory.getLogger(MercadoPublicoAdapter.class);
	
	@Override
	public Cesta getFrutas() {
		String message = "Serviço mercado publico indisponivel.....";
		logger.info(message);
		throw new ApiParceiroException(message);
	}

}
