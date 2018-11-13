package com.swapping.springcloud.ms.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCircuitBreaker    //Hystrix Dashboard必须加
@EnableHystrixDashboard//展示熔断器仪表盘

@EnableTransactionManagement
@EnableDiscoveryClient	//启动服务发现与注册
@EnableFeignClients({"com.swapping"})
@SpringBootApplication(scanBasePackages = "com.swapping")
public class SpringcloudMsGoodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsGoodsApplication.class, args);
	}
}
