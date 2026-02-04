package com.mvv.saga_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class SagaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SagaServiceApplication.class, args);
	}

}
