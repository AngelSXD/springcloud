package com.swapping.springcloud.ms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 * 为了保证Zuul的高可用性，前端可以同时启动多个Zuul实例进行负载，在Zuul的前端使用Nginx或者F5进行负载转发以达到高可用性。
 *
 * zuul三大功能：
 *
 * 1.路由拦截 filter
 * 2.路由熔断 fallback
 * 3.路由重试 retry
 *
 *
 *
 */
@EnableCircuitBreaker    //Hystrix Dashboard必须加
@EnableHystrixDashboard//展示熔断器仪表盘

@EnableZuulProxy	//网关映射 注解
@EnableFeignClients	//feign调用注解
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.swapping")
public class SpringcloudMsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsGatewayApplication.class, args);
	}
}
