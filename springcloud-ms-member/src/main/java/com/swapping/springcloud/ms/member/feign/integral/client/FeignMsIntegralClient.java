package com.swapping.springcloud.ms.member.feign.integral.client;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.member.feign.integral.bean.Integral;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "springcloud-ms-integral",fallback = FeignMsIntegralClientHystric.class)
public interface FeignMsIntegralClient {

    /**
     * 增加会员积分信息
     * @param entity
     * @return
     */
    @RequestMapping(value = "/integral/save", method = RequestMethod.POST)
    public UniVerResponse<Integral> save(@RequestBody Integral entity);

}
