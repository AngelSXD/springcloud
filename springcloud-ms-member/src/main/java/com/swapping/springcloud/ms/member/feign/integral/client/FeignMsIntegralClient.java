package com.swapping.springcloud.ms.member.feign.integral.client;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.member.feign.integral.bean.Integral;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * feignClient 接口
 *
 * 注意：feign接口上 尽量不要使用@RequestMapping()注解
 *      因为若请求中含有Accept header,会出现404
 */
@FeignClient(name = "springcloud-ms-integral")
public interface FeignMsIntegralClient {

    /**
     * 为会员添加原始积分记录
     * jpa分布式事务
     *
     *
     * 【注意】接口的地址需要完整对应到 被调用方接口的完整地址
     *  因为feignClient上不推荐直接使用@RequestMapping，
     *  所以将@RequestMapping("/integral")和@RequestMapping("/save")合并到具体接口上的@RequestMapping("/integral/save")
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/integral/save", method = RequestMethod.POST)
    public UniVerResponse<Integral> save(@RequestBody Integral entity);






    /**
     * 为会员添加原始积分记录
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
