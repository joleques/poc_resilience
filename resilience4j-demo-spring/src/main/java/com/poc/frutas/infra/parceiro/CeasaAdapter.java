package com.poc.frutas.infra.parceiro;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.poc.frutas.domain.model.Cesta;
import com.poc.frutas.domain.model.Fruta;

@Service
class CeasaAdapter implements Parceiro{

	private Logger logger = LoggerFactory.getLogger(CeasaAdapter.class);
	
	@Override
	public Cesta getFrutas() {
		logger.info("Frutas da ceasa.....");
		
		List<Fruta> frutas = new ArrayList<Fruta>();
		frutas.add(new Fruta(1L, "Banana"));
		frutas.add(new Fruta(2L, "Abacate"));
		frutas.add(new Fruta(3L, "Laranja"));
		frutas.add(new Fruta(4L, "Ameixa"));
		frutas.add(new Fruta(5L, "Melão"));
		frutas.add(new Fruta(6L, "Mamão"));		
		return new Cesta("Ceasa", frutas);
	}

}
