package com.swapping.springcloud.ms.member.service.impl;


import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.swapping.springcloud.ms.core.exception.MyException;
import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.member.dao.MemberDao;
import com.swapping.springcloud.ms.member.domain.Member;
import com.swapping.springcloud.ms.member.feign.goods.bean.Goods;
import com.swapping.springcloud.ms.member.feign.goods.client.FeignMsGoodsClient;
import com.swapping.springcloud.ms.member.feign.integral.bean.Integral;
import com.swapping.springcloud.ms.member.feign.integral.client.FeignMsIntegralClient;
import com.swapping.springcloud.ms.member.mapper.MemberMapper;
import com.swapping.springcloud.ms.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService,ITxTransaction{

    @Autowired
    MemberDao memberDao;

    @Autowired
    FeignMsIntegralClient integralClient;

    @Autowired
    FeignMsGoodsClient goodsClient;

    @Autowired
    MemberMapper memberMapper;


    /**
     * 事务发起方
     *
     * 1.需要注解
     * @TxTransaction(isStart=true)     标明是事务发起方
     *
     * 2.需要注解
     * @Transactional       需要显示事务注解
     *
     * 3.需要TxManagerTxUrlServiceImpl 获取@Value("${tm.manager.url}")
     *
     * 4.需要TxManagerHttpRequestServiceImpl
     *
     * 5.需要被调用方  feign 设置 fallback熔断器【如果有统一异常处理，则被调用方抛异常不会进入熔断器】【如果没有统一异常处理，则熔断器必不可少】
     *
     * 统一异常拦截器【com.swapping.springcloud.ms.core.exception.MyControllerAdvice】
     *
     *
     * 【注意】同一个事务中，不能同时使用jpa和mybatis进行操作。
     *
     * @param entity
     * @return
     */
    @Override
    @TxTransaction(isStart=true)
    @Transactional
    public Member insert(Member entity){
        Member save = memberDao.save(entity);

        //为本会员设置原始积分
        Integral integral = new Integral();
        integral.setIntegralNum(0L);
        integral.setMemberUid(entity.getUid());
        UniVerResponse<Integral> integralRes = integralClient.save(integral);

        Goods  goods = new Goods();
        goods.setGoodsName(save.getMemberName());
        goods.setGoodsPrice(12L);
        goods.setStock(998L);
        UniVerResponse<Goods> goodsRes = goodsClient.save(goods);
        if (!integralRes.isSuccess() || !goodsRes.isSuccess()){
            throw new MyException("服务异常，请稍后重试");
        }

//        int a = 1/0;
        return save;
    }


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Member saveByMabatis(Member member) {
        String uid = member.getUid();
        int num = memberMapper.save(member);
        Integral integral = new Integral();
        integral.setMemberUid(uid);
        UniVerResponse<Integer> integralRes = integralClient.saveByMybatis(integral);
        if (!integralRes.isSuccess()){
            throw  new MyException("积分保存异常");
        }
//        int a = 1/0;
        return num>0 ? memberMapper.findByUid(uid) : null;
    }

    @Override
    public Member update(Member entity){
        return null;
    }

    @Override
    public boolean delete(Member entity){
        return false;
    }

    @Override
    public Page<Member> pageFind(Member entity){
        return null;
    }

    @Override
    public Page<Member> pageSortFind(Member entity, Map<String, Sort.Direction> map){
        return null;
    }

    @Override
    public List<Member> find(Member entity){
        return null;
    }

    @Override
    public List<Member> findAll(){
        return null;
    }

    @Override
    public Member findByMemberName(String memberName) {
        return memberDao.findByMemberName(memberName);
    }

    @Override
    public Member get(String memberUid) {
        return memberDao.findByUid(memberUid);
    }
}
