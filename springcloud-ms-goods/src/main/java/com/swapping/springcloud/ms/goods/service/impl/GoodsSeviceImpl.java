package com.swapping.springcloud.ms.goods.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.swapping.springcloud.ms.goods.dao.GoodsDao;
import com.swapping.springcloud.ms.goods.dao.GoodsOrderDao;
import com.swapping.springcloud.ms.goods.domain.Goods;
import com.swapping.springcloud.ms.goods.domain.GoodsOrder;
import com.swapping.springcloud.ms.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class GoodsSeviceImpl  implements GoodsService{

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    GoodsOrderDao goodsOrderDao;

    @Override
    @TxTransaction
    @Transactional
    public Goods insert(Goods entity){
        Goods save = goodsDao.save(entity);
//        int a = 1/0;
        return save;
    }

    @Override
    public Goods update(Goods entity){
        return null;
    }

    @Override
    public boolean delete(Goods entity){
        return false;
    }

    @Override
    public Page<Goods> pageFind(Goods entity){
        return null;
    }

    @Override
    public Page<Goods> pageSortFind(Goods entity, Map<String, Sort.Direction> map){
        return null;
    }

    @Override
    public List<Goods> find(Goods entity){
        return null;
    }

    @Override
    public List<Goods> findAll(){
        return null;
    }

    @Override
    public Goods findByName(String goodsName) {
        return goodsDao.findByGoodsName(goodsName);
    }

    @Override
    public GoodsOrder insertOrder(GoodsOrder goodsOrder) {
        return goodsOrderDao.save(goodsOrder);
    }

    @Override
    public Goods get(String uid) {
        return goodsDao.findByUid(uid);
    }
}
