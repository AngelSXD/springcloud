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
 * 1.注意：feign接口上 尽量不要使用@RequestMapping()注解
 *      因为若请求中含有Accept header,会出现404
 *
 * 2.@FeignClient(name = "springcloud-ms-integral")
 * 其中的name代表这个feignClient指向的服务名称，
 * 也就是ms-integral服务中的application.prperties中定义的【spring.application.name=springcloud-ms-integral】
 *
 * 3.如果被调用方，也就是服务提供者在服务注册中心有多个实例，则调用方的多次请求会自动轮询到每个服务提供方来处理，
 * 这就是注册中心的负载均衡的功能
 */
@FeignClient(name = "springcloud-ms-integral")
public interface FeignMsIntegralClient {

    /**
     * 为会员添加原始积分记录
     * jpa分布式事务
     *
     *
     * 1.【注意】
     *  接口的地址需要完整对应到 被调用方接口的完整地址
     *  因为feignClient上不推荐直接使用@RequestMapping，
     *  所以将@RequestMapping("/integral")和@RequestMapping("/save")合并到具体接口上的@RequestMapping("/integral/save")
     *
     * 2.接口的方法名要和被调用接口的方法名一致！！
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
