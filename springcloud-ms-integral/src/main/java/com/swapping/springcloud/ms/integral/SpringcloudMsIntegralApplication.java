package com.swapping.springcloud.ms.integral;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.swapping")
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.swapping.springcloud.ms.integral.mapper")
public class SpringcloudMsIntegralApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsIntegralApplication.class, args);
	}
}
