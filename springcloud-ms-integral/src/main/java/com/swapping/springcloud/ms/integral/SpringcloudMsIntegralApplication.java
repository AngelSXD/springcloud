package com.swapping.springcloud.ms.integral;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCircuitBreaker	//Hystrix Dashboard必须加
@EnableHystrixDashboard//展示熔断器仪表盘

@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients({"com.swapping"})
@MapperScan("com.swapping.springcloud.ms.integral.mapper")
@SpringBootApplication(scanBasePackages = "com.swapping")
public class SpringcloudMsIntegralApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsIntegralApplication.class, args);
	}
}
