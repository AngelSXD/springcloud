package com.swapping.springcloud.ms.goods.service;

import com.swapping.springcloud.ms.goods.domain.Goods;
import com.swapping.springcloud.ms.goods.domain.GoodsOrder;

public interface GoodsService extends ICRUDService<Goods> {

    Goods findByName(String goodsName);

    GoodsOrder insertOrder(GoodsOrder goodsOrder);

    Goods get(String uid);
}
