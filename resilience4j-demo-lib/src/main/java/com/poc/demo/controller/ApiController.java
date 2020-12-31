package com.poc.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.poc.demo.domain.model.Cesta;

@RestController
public class ApiController {

    @Autowired
    private FrutasApplicationService service;
    
    @GetMapping("/frutas/{local}")
    public Cesta frutas(@PathVariable String local) {
        return service.getFrutas(local);
    }

}