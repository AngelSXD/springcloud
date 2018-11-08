package com.swapping.springcloud.ms.member.feign.integral.client;

import com.swapping.springcloud.ms.core.exception.MyException;
import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.member.feign.integral.bean.Integral;
import org.springframework.stereotype.Component;

/**
 * 熔断器  断路器
 *
 */
@Component
public class FeignMsIntegralClientHystric implements FeignMsIntegralClient {


    @Override
    public UniVerResponse<Integral> save(Integral entity) {
        System.out.println("进入断路器-integral-save。。。");
        throw new MyException("save 保存失败.");
    }

    @Override
    public UniVerResponse<Integer> saveByMybatis(Integral integral) {
        System.out.println("进入断路器-integral-save-mybatis。。。");
        throw new MyException("save mybatis保存失败.");
    }
}
