package com.mvv.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudgatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {

		return builder.
				routes()
				.route(r -> r.path("/card/**").uri("lb://cards-service"))
				.route(r -> r.path("/card-types/**").uri("lb://cards-service"))
				.route(r -> r.path("/products/**").uri("lb://products-service"))
				.route(r -> r.path("/orders/**").uri("lb://orders-service"))
				.build();

	}



}
