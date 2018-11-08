package com.swapping.springcloud.ms.member.feign.integral.client;

import com.swapping.springcloud.ms.core.exception.MyException;
import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.member.feign.integral.bean.Integral;
import org.springframework.stereotype.Component;

/**
 * 熔断器  断路器
 *
 * 1.作为fallBack,相当于是降级处理。
 * 对于操作请求，如果超时或处理失败，可以通过抛出异常来通知消费者服务，来进行补救处理。
 * 对于查询请求，如果超时或请求失败，可以通过返回默认值或查询缓存来返回。
 *
 * 2.熔断器使用说明参见：https://www.cnblogs.com/sxdcgaq8080/p/9921355.html
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
