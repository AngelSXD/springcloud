package com.swapping.springcloud.ms.integral.controller;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.integral.domain.Integral;
import com.swapping.springcloud.ms.integral.feign.member.bean.Member;
import com.swapping.springcloud.ms.integral.feign.member.client.FeignMsMemberClient;
import com.swapping.springcloud.ms.integral.service.IntegralService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/integral")
public class IntegralController {

    @Autowired
    IntegralService integralService;

    @Autowired
    FeignMsMemberClient memberClient;


    @RequestMapping(value = "/showIntegral")
    public String showIntegral(){
        System.out.println("积分服务到达");
        return "积分服务调用正常";
    }

    /**
     * 会员积分记录
     *
     * 新增或更新接口【接口应保证幂等性 故而一种接口两种功能应该尽量避免（虽然本接口的结果是幂等的）】
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public UniVerResponse<Integral> save(@RequestBody Integral entity){
        UniVerResponse.checkField(entity,"memberUid");

        UniVerResponse<Integral> res = new UniVerResponse<>();
        String memberUid = entity.getMemberUid();

        //验证每个会员积分记录的唯一性
        /**
         * 注意，虽然这里的查询方法在业务上并不参与原子性
         *
         * 但是既然本controller被调用，那么作为被feign调用的这一方，即便是这个查询方法，也需要作为事务的参与者，再查询方法上添加注解
         *  @Transactional
         *  @TxTransaction
         *
         */
        Integral old = integralService.findByMember(memberUid);
        if (old == null){
            old = new Integral();
            old.initEntity();
            old.setMemberUid(memberUid);
        }else {
            old.setUpdateDate(new Date());
        }
        old.setIntegralNum(entity.getIntegralNum());

        Integral save = integralService.insert(old);
        if (save != null){
            res.beTrue(entity);
        }else {
            res.beFalse("会员新增积分记录失败",UniVerResponse.ERROR_BUSINESS);
        }

        return  res;
    }

    /**
     * 测试 mybatis的分布式事务
     * @return
     */
    @RequestMapping(value = "/saveByMybatis", method = RequestMethod.POST)
    public UniVerResponse<Integer> saveByMybatis(@RequestBody Integral integral){
        UniVerResponse.checkField(integral,"memberUid");
        UniVerResponse<Integer> res = new UniVerResponse<>();
        integral.initEntity();
        integral.setIntegralNum(888888L);
        int num = integralService.saveByMybatis(integral);
        res.beTrue(num);
        return res;
    }
}
