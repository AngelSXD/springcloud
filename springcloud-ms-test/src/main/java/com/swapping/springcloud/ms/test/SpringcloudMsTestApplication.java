package com.swapping.springcloud.ms.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudMsTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsTestApplication.class, args);
	}
}
