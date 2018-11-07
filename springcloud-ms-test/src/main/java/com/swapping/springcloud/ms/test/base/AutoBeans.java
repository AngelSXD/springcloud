package com.swapping.springcloud.ms.test.base;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AutoBeans {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
