package com.swapping.springcloud.ms.member.feign.integral.client;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.member.feign.integral.bean.Integral;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "springcloud-ms-integral")
public interface FeignMsIntegralClient {

    /**
     * 增加会员积分信息
     * @param entity
     * @return
     */
    @RequestMapping(value = "/integral/save", method = RequestMethod.POST)
    public UniVerResponse<Integral> save(@RequestBody Integral entity);

    /**
     * 增加会员积分信息
     *
     * 测试mybatis分布式事务
     *
     * 由于已经有了统一的异常拦截处理
     * 故而 本方法不用在熔断器中设定熔断方法
     *
     * 但是熔断器类必须存在，因为启动需要查找熔断器类，否则报错
     * @param integral
     * @return
     */
    @RequestMapping(value = "/integral/saveByMybatis", method = RequestMethod.POST)
    public UniVerResponse<Integer> saveByMybatis(@RequestBody Integral integral);
}
