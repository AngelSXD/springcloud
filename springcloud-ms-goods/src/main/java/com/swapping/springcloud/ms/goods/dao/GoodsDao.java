package com.swapping.springcloud.ms.goods.dao;

import com.swapping.springcloud.ms.goods.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsDao extends JpaRepository<Goods, Long>,JpaSpecificationExecutor<Goods> {

    Goods findByGoodsName(String goodsName);

    Goods findByUid(String uid);
}
