package com.swapping.springcloud.ms.sleuth.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.internal.EnableZipkinServer;

//启用Zipkin服务
@EnableZipkinServer

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.swapping"})
public class SpringcloudMsSleuthZipkinApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsSleuthZipkinApp.class, args);
	}
}
