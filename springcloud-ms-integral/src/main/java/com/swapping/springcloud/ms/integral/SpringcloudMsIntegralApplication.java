package com.swapping.springcloud.ms.integral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.swapping")
@EnableDiscoveryClient
@EnableFeignClients
public class SpringcloudMsIntegralApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsIntegralApplication.class, args);
	}
}
