package com.swapping.springcloud.ms.member.controller;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.core.utils.SwappingUtils;
import com.swapping.springcloud.ms.member.domain.Member;
import com.swapping.springcloud.ms.member.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {


    @Autowired
    MemberService memberService;

    @RequestMapping(value = "/showMember")
    public String showMember(){
        System.out.println("请求到达！！！！");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "会员服务正常,会员服务是服务消费者,也就是服务调用者,会调用商品服务进行商品购买,减少库存,增加销量;\n\r同时调用积分服务,增加积分";
    }


    /**
     * 新增 会员信息
     *
     * ======业务验证=====
     * 必填：会员名称
     * 验证：会员名称是否重复
     * ===================
     *
     * ======业务操作=====
     * 1.保存会员信息
     * 2.为本会员新增 会员原始积分信息
     * 3.返回保存结果
     * ===================
     *
     * @param member
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public UniVerResponse<Member> save(@RequestBody Member member){
        UniVerResponse.checkField(member,"memberName");

        UniVerResponse<Member> res = new UniVerResponse<>();
        Member old = memberService.findByMemberName(member.getMemberName());
        if (old == null){
            member.initEntity();

            String password = member.getPassword();
            member.setPassword(StringUtils.isBlank(password) ? SwappingUtils.MD5("123456") : password);
            Member save = memberService.insert(member);
            if (save != null){
                res.beTrue(save);
            }else {
                res.beFalse("会员新增失败",UniVerResponse.ERROR_BUSINESS);
            }
        }else {
            res.beFalse("重复会员名称，请勿使用",UniVerResponse.ERROR_PARAMS);
        }

        return  res;

    }


    /**
     * 根据会员id  获取会员信息
     * @param memberUid
     * @return
     */
    @RequestMapping(value = "/findMemberByUid", method = RequestMethod.GET)
    public UniVerResponse<Member> findMemberByUid(String memberUid){

        UniVerResponse.simplCheckField(memberUid,"memberUid");
        UniVerResponse<Member> res = new UniVerResponse<>();
        res.beTrue(memberService.get(memberUid));
        return res;
    }



    /**
     * 保存会员信息
     *
     * 测试mybatis分布式事务
     * @param member
     * @return
     */
    @RequestMapping(value = "/saveByMybatis",method = RequestMethod.POST)
    public UniVerResponse<Member> saveByMybatis(@RequestBody Member member) {
        UniVerResponse.checkField(member, "memberName");
        UniVerResponse<Member> res = new UniVerResponse<>();
        Member old = memberService.findByMemberName(member.getMemberName());
        if (old == null){
            member.initEntity();

            String password = member.getPassword();
            member.setPassword(StringUtils.isBlank(password) ? SwappingUtils.MD5("123456") : password);
            Member save = memberService.saveByMabatis(member);
            if (save != null){
                res.beTrue(save);
            }else {
                res.beFalse("会员新增失败",UniVerResponse.ERROR_BUSINESS);
            }
        }else {
            res.beFalse("重复会员名称，请勿使用",UniVerResponse.ERROR_PARAMS);
        }

        return res;
    }


}
