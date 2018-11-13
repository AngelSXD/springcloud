package com.swapping.springcloud.ms.hystrix.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 * IP:turbine服务所在服务器IP  localhost
 * port:turbine服务所配置的服务端口	10000
 * 监控项目访问： http://IP:port/turbine.stream
 * 展示信息：
 * ping:
 * {.....}
 *
 *
 *
 * 图形化监控页面：http://IP:port/hystrix
 *
 * 图形化监控页面使用说明：
 * 	1.在进入豪猪主页后，在输入框输入http://localhost:10000/turbine.stream，点击Monitor Stream按钮
 *	2.展示所有配置了Hystrix Dashboard仪表盘展示的 各个服务之间的feign调用情况
 */
@EnableTurbine//开启turbine
@EnableHystrixDashboard//开启仪表盘

@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudMsHystrixTurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsHystrixTurbineApplication.class, args);
	}
}
