package com.swapping.springcloud.ms.integral.controller;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.integral.domain.Integral;
import com.swapping.springcloud.ms.integral.feign.member.bean.Member;
import com.swapping.springcloud.ms.integral.feign.member.client.FeignMsMemberClient;
import com.swapping.springcloud.ms.integral.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integral")
public class IntegralController {

    @Autowired
    IntegralService integralService;

    @Autowired
    FeignMsMemberClient memberClient;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public UniVerResponse<Integral> save(@RequestBody Integral entity){
        UniVerResponse.checkField(entity,"memberUid");

        UniVerResponse<Integral> res = new UniVerResponse<>();

        entity.initEntity();
        entity.setIntegralNum(999999999L);
        Integral save = integralService.insert(entity);
        res.beTrue(entity);


        //验证会员存在性
//        UniVerResponse<Member> memberRes = memberClient.findMemberByUid(entity.getMemberUid());
//        Member verify = memberRes.getObj();
//        if (memberRes.isSuccess() && verify != null){
//            Integral old = integralService.findByMember(entity.getMemberUid());
//            if (old == null){
//                entity.initEntity();
//                entity.setIntegralNum(999999999L);
//                Integral save = integralService.insert(entity);
//                if (save != null){
//                    res.beTrue(entity);
//                }else {
//                    res.beFalse("会员新增积分记录失败",UniVerResponse.ERROR_BUSINESS);
//                }
//            }else {
//                res.beFalse2("本会员已有积分数据存在",UniVerResponse.ERROR_BUSINESS,entity);
//            }
//        }else {
//            res.beFalse("会员不存在",UniVerResponse.ERROR_BUSINESS);
//        }
        return  res;
    }
}
