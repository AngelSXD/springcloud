package com.swapping.springcloud.ms.member.feign.goods.client;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.member.feign.goods.bean.Goods;
import com.swapping.springcloud.ms.member.feign.goods.bean.GoodsOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 服务调用方  发起
 *
 * 这个feign接口  是需要指定  服务提供方的 spring.application.name 指定的服务提供方的名字
 *
 * 注意feign上不要使用@RequestMapping,因为当有Accept header,会出现404
 *
 */
@FeignClient(name = "springcloud-ms-goods",fallback = FeignMsGoodsClientFallback.class)
public interface FeignMsGoodsClient {

    /**
     * feign  post 新增 会员购买商品订单
     * @param goodsOrder
     * @return
     */
    @RequestMapping(value = "/goods/insertOrder", method = RequestMethod.POST)
    public UniVerResponse<GoodsOrder> insertOrder(@RequestBody GoodsOrder goodsOrder);

    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    public UniVerResponse<Goods> save(@RequestBody Goods goods);

}
