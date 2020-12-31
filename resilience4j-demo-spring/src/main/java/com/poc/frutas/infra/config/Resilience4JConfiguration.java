package com.poc.frutas.infra.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

import com.poc.frutas.domain.exception.ApiParceiroException;
import com.poc.frutas.domain.exception.BusinessException;
import com.poc.frutas.domain.exception.FrutaInvalidException;
import com.poc.frutas.domain.exception.FrutasIndisponiveisException;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class Resilience4JConfiguration {

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(buildTimeLimiterConfig())
				.circuitBreakerConfig(buildCircuitBreakerConfig()).build());
	}

	@Bean
	public Customizer<SpringRetryCircuitBreakerFactory> retryCustomizer() {
		return factory -> factory.configureDefault(id -> new SpringRetryConfigBuilder(id)
				.retryPolicy(new SimpleRetryPolicy(5))
				.backOffPolicy(new ExponentialBackOffPolicy()).build());

	}

	private CircuitBreakerConfig buildCircuitBreakerConfig() {
		return CircuitBreakerConfig.custom()
				.slidingWindowSize(5)
				.minimumNumberOfCalls(5)
				.permittedNumberOfCallsInHalfOpenState(3)
				.automaticTransitionFromOpenToHalfOpenEnabled(true)
				.waitDurationInOpenState(Duration.ofMillis(5000))
				.failureRateThreshold(50)
				.slidingWindowType(SlidingWindowType.COUNT_BASED)
				.recordExceptions(ApiParceiroException.class, FrutasIndisponiveisException.class)
				.build();
	}

	private TimeLimiterConfig buildTimeLimiterConfig() {
		return TimeLimiterConfig.custom()
				.timeoutDuration(Duration.ofSeconds(1))
				.build();
	}

}
