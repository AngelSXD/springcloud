package com.swapping.springcloud.ms.integral.service.impl;

import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.swapping.springcloud.ms.integral.dao.IntegralDao;
import com.swapping.springcloud.ms.integral.domain.Integral;
import com.swapping.springcloud.ms.integral.mapper.IntegralMapper;
import com.swapping.springcloud.ms.integral.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class IntegralServiceImpl implements IntegralService,ITxTransaction{


    @Autowired
    IntegralDao integralDao;

    @Autowired
    IntegralMapper integralMapper;

    /**
     * 事务参与方,被调用方
     *
     * 1.需要注解@TxTransaction 标明是事务参与方  或者 本service直接实现接口ITxTransaction
     *
     * 2.需要显示注解@Transactional
     *
     * 3.需要TxManagerTxUrlServiceImpl获取@Value("${tm.manager.url}")
     *
     * 4.需要TxManagerHttpRequestServiceImpl
     *
     *
     * @param entity
     * @return
     */
    @Override
    @TxTransaction
    @Transactional
    public Integral insert(Integral entity){
        Integral save = integralDao.save(entity);
//        int a = 1/0;
        return save;
    }

    @Override
    @Transactional
    @TxTransaction
    public int saveByMybatis(Integral integral) {
        int a = integralMapper.insertIntegral(integral);
//        int b = 1/0;
        return a;
    }

    @Override
    public Integral update(Integral entity){
        return null;
    }

    @Override
    public boolean delete(Integral entity){
        return false;
    }

    @Override
    public Page<Integral> pageFind(Integral entity){
        return null;
    }

    @Override
    public Page<Integral> pageSortFind(Integral entity, Map<String, Sort.Direction> map){
        return null;
    }

    @Override
    public List<Integral> find(Integral entity){
        return null;
    }

    @Override
    public List<Integral> findAll(){
        return null;
    }


    @Override
    @Transactional
    @TxTransaction
    public Integral findByMember(String memberUid) {
        return integralDao.findByMemberUid(memberUid);
    }
}
