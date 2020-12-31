package com.poc.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.poc.demo.domain.model.Cesta;
import com.poc.demo.infra.parceiro.ParceiroAdapter;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.decorators.Decorators.DecorateSupplier;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;

@Service
public class FrutasApplicationService {

    private static final String BACKEND_FRUTAS = "backendFrutas";
    
	private Logger logger = LoggerFactory.getLogger(ParceiroAdapter.class);
    
	private ParceiroAdapter parceiroAdapter;


    private CircuitBreaker circuitBreaker;

    private Bulkhead bulkhead;
	
    private Retry retry;

	private ThreadPoolBulkhead threadPoolBulkhead;

	private RateLimiter rateLimiter;

	private TimeLimiter timeLimiter;

	private ScheduledExecutorService scheduledExecutorService;
    
    
	
	public FrutasApplicationService(
			ParceiroAdapter parceiroAdapter,
            CircuitBreakerRegistry circuitBreakerRegistry,
            ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry,
            BulkheadRegistry bulkheadRegistry,
            RetryRegistry retryRegistry,
            RateLimiterRegistry rateLimiterRegistry,
            TimeLimiterRegistry timeLimiterRegistry){
        this.parceiroAdapter = parceiroAdapter;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker(BACKEND_FRUTAS);
        this.bulkhead = bulkheadRegistry.bulkhead(BACKEND_FRUTAS);
        this.threadPoolBulkhead = threadPoolBulkheadRegistry.bulkhead(BACKEND_FRUTAS);
        this.retry = retryRegistry.retry(BACKEND_FRUTAS);
        this.rateLimiter = rateLimiterRegistry.rateLimiter(BACKEND_FRUTAS);
        this.timeLimiter = timeLimiterRegistry.timeLimiter(BACKEND_FRUTAS);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(3);
	}

	public Cesta getFrutas(String tipoParceiro) {
		logger.info("Buscando as frutas do parceiro.....");
	    return execute(() -> parceiroAdapter.getFrutas(tipoParceiro), throwable -> {
			try {
				return parceiroAdapter.getCacheFrutas(throwable);
			} catch (Throwable e) {
				String message = String.format("Local Invalido [%s]", e.getMessage());
				logger.info(message);
				return new Cesta(message, List.of());
			}
		});
	}
	
	private Cesta execute(Supplier<Cesta> supplier, Function<Throwable, Cesta> fallback){
        DecorateSupplier<Cesta> supplier2 = Decorators.ofSupplier(supplier)
                .withCircuitBreaker(circuitBreaker)
                .withBulkhead(bulkhead)
                .withRetry(retry);
        if(fallback != null )
        	supplier2
            .withFallback(fallback);
            
		return supplier2
                .get();
    }

}
