package com.swapping.springcloud.ms.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringcloudMsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsEurekaApplication.class, args);
	}
}
