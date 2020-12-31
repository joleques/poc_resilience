package com.poc.demo.infra.parceiro;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.poc.demo.domain.model.Cesta;
import com.poc.demo.domain.model.Fruta;

@Service
public class CacheAdapter implements Parceiro {

	private Logger logger = LoggerFactory.getLogger(CacheAdapter.class);
	
	@Override
	public Cesta getFrutas() {
		logger.info("Frutas do cache.....");
		List<Fruta> frutas = new ArrayList<Fruta>();
		frutas.add(new Fruta(1L, "Manga"));
		frutas.add(new Fruta(2L, "Uva"));
		frutas.add(new Fruta(3L, "Cereja"));
		return new Cesta("Cache", frutas);
	}

}
