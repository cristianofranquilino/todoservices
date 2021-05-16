package br.com.todoservices.configuration;

import org.glassfish.jersey.server.ResourceConfig;

import br.com.todoservices.interceptor.ApiInterceptor;

public class ApiConfig extends ResourceConfig {
	public ApiConfig() {
		packages("br.com.todoservices.resource");
        register(ApiInterceptor.class);
	}
}