package com.swapping.springcloud.ms.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.swapping")
@EnableDiscoveryClient
@EnableFeignClients
public class SpringcloudMsGoodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMsGoodsApplication.class, args);
	}
}
