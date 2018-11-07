package com.swapping.springcloud.ms.member.feign.goods.client;

import com.swapping.springcloud.ms.core.exception.MyException;
import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.member.feign.goods.bean.Goods;
import com.swapping.springcloud.ms.member.feign.goods.bean.GoodsOrder;
import org.springframework.stereotype.Component;


/**
 * 断路器
 */
@Component
public class FeignMsGoodsClientFallback implements FeignMsGoodsClient{

    @Override
    public UniVerResponse<GoodsOrder> insertOrder(GoodsOrder goodsOrder) {
        System.out.println("进入断路器-goods-insertorder-");
        throw new MyException("insertOrder执行失败");
    }

    @Override
    public UniVerResponse<Goods> save(Goods goods) {
        System.out.println("进入断路器-goods-save-");
        throw new MyException("save执行失败");
    }
}
