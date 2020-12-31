package com.poc.frutas.infra.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {

	@Value(value="${app.timeout.connect}")
	private Long connectTimeoutOfSeconds;
	@Value(value="${app.timeout.read}")
	private Long readTimeoutOfSeconds;
	
	@Bean
	public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(getConnectTimeout()))
                .setReadTimeout(Duration.ofSeconds(getReadTimeout()))
                .build();
    }
	

	private Long getReadTimeout() {
		return readTimeoutOfSeconds != null ? readTimeoutOfSeconds: 3L;
	}

	private Long getConnectTimeout() {
		return connectTimeoutOfSeconds != null ? connectTimeoutOfSeconds : 3L;
	}


}
