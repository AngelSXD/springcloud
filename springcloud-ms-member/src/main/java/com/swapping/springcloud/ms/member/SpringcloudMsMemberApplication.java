package com.swapping.springcloud.ms.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.swapping")
@EnableDiscoveryClient	//启动服务发现与注册
@EnableFeignClients({"com.swapping"})		//启用feign进行服务之间的远程调用  服务提供者需要这个注解,而服务调用者则不需要这个注解
public class SpringcloudMsMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsMemberApplication.class, args);
	}
}
