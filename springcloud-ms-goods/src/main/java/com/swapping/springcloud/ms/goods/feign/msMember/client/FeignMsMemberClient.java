package com.swapping.springcloud.ms.goods.feign.msMember.client;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.goods.feign.msMember.bean.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * 服务提供者：ms-member服务提供的feign
 * 服务调用者：ms-goods调用feign
 * @FeignClient(name = "springcloud-ms-member") name与ms-member服务配置文件中 spring.application.name=springcloud-ms-member 一致
 *
 */
@FeignClient(name = "springcloud-ms-member")
@RequestMapping(value = "/member")
public interface FeignMsMemberClient {


    /**
     * 根据会员uid 查询会员信息
     *
     * 1.@RequestMapping  需要对应服务提供方的接口地址
     *
     * 2.注意get方法 的请求参数需要使用注解 @RequestParam 一一对应服务提供方的接口中 各个参数
     *
     * 3.Member实体 是服务调用方  自己定义的member,只需要返回的实体字段与服务提供方本接口的返回实体字段 相对应 即可拿到接口返回结果
     * @param memberUid
     * @return
     */
    @RequestMapping(value = "/findMemberByUid", method = RequestMethod.GET)
    public UniVerResponse<Member> findMemberByUid(@RequestParam(value = "memberUid") String memberUid);


}
