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


    /**
     * 事务发起方
     *
     * 1.需要注解
     * @TxTransaction(isStart=true)     标明是事务发起方
     *
     * 2.需要注解
     * @Transactional       需要事务注解
     *
     * 3.需要TxManagerTxUrlServiceImpl 获取@Value("${tm.manager.url}")
     *
     *
     * @param entity
     * @return
     */
    @Override
    @TxTransaction(isStart=true)
    @Transactional
    public Member insert(Member entity){
        Member save = null;
        Integral integral = new Integral();
        integral.setMemberUid(entity.getUid());

        save = memberDao.save(entity);

        /**
         * 因为 做了异常统一拦截，所以integralClient 并不会最后返回的是抛出的异常
         * 而是 异常统一拦截器【com.swapping.springcloud.ms.core.exception.MyControllerAdvice】处理之后的结果
         * 故而需要自己 判断返回结果，自己抛出异常进行事务回滚，而【有了异常统一拦截之后的熔断器就不会起作用了】
         *
         */
        UniVerResponse<Integral> integralRes = integralClient.save(integral);
        if (!integralRes.isSuccess()){
            throw new MyException("系统异常，请稍后重试");
        }
        Goods goods = new Goods();
        goods.setGoodsName(entity.getMemberName());
        goods.setGoodsPrice(11L);
        goods.setStock(200L);
        UniVerResponse<Goods> goodsRes = goodsClient.save(goods);
//        if (integralRes.isSuccess()){
//            save = memberDao.save(entity);
//        }
//        int a = 1/0;


        return save;
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
