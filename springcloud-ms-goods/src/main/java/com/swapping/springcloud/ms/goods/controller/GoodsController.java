package com.swapping.springcloud.ms.goods.controller;

import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.goods.domain.Goods;
import com.swapping.springcloud.ms.goods.domain.GoodsOrder;
import com.swapping.springcloud.ms.goods.feign.msMember.bean.Member;
import com.swapping.springcloud.ms.goods.feign.msMember.client.FeignMsMemberClient;
import com.swapping.springcloud.ms.goods.service.GoodsService;
import com.swapping.springcloud.ms.goods.utils.AsynFeignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/goods")
public class GoodsController {


    @Autowired
    GoodsService goodsService;

    @Autowired
    AsynFeignUtils asynFeignUtils;

    @RequestMapping(value = "showGoods")
    public String showGoods(){
        return "商品服务正常,商品服务是服务提供者,提供给会员服务调用,会进行购买动作,扣除库存的操作";
    }


    /**
     * 商品  新增接口
     * @param goods
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public UniVerResponse<Goods> save(@RequestBody Goods goods){
        UniVerResponse.checkField(goods,"goodsName","goodsPrice","stock");

        UniVerResponse<Goods> res = new UniVerResponse<>();
        String goodsName = goods.getGoodsName();
//        Goods old = goodsService.findByName(goodsName);
//        if (old == null){
            goods.initEntity();
            goods.setSaleNum(0L); //默认销量为0
            Goods save = goodsService.insert(goods);
            if (save != null){
                res.beTrue(goods);
            }else {
                res.beFalse2("保存商品失败",UniVerResponse.ERROR_BUSINESS,goods);
            }
//        }else {
//            res.beFalse("商品名称重复啦",UniVerResponse.ERROR_PARAMS);
//        }
        return res;
    }


    /**
     * 新增 订单记录
     *
     * 会员购买商品  记录
     * @param goodsOrder
     * @return
     */
    @RequestMapping("/insertOrder")
    public UniVerResponse<GoodsOrder> insertOrder(GoodsOrder goodsOrder){
        UniVerResponse.checkField(goodsOrder,"goodsUid","memberUid","buyNum","integral");

        UniVerResponse<GoodsOrder> res = new UniVerResponse<>();

        Map<String,Object> map = asynFeignUtils.getMemberAndGoods2Asyn(goodsOrder);

        Member verify1 = (Member) map.get("member");
        Goods  verify2 = (Goods) map.get("goods");
        if (map.size() == 2 ){
            goodsOrder.initEntity();

            goodsOrder.setGoodsName(verify2.getGoodsName());
            goodsOrder.setGoodsPrice(verify2.getGoodsPrice());
            goodsOrder.setMemberName(verify1.getMemberName());
            goodsOrder.setRemark(goodsOrder.getMemberName()+">>在"+goodsOrder.getCreateDate()+">>购买"+goodsOrder.getGoodsName()+"商品");

            GoodsOrder save = goodsService.insertOrder(goodsOrder);
            if (save != null){

            }else {
                res.beFalse2("订单保存失败",UniVerResponse.ERROR_BUSINESS,goodsOrder);
            }
        }else {
            String errMsg = "";

            if (verify1 == null){
                errMsg = "[会员]";
            }
            if (verify2 == null){
                errMsg += "[商品]";
            }
            res.beFalse(errMsg+="验证失败",UniVerResponse.ERROR_BUSINESS);
        }


        return res;
    }

    @RequestMapping(value = "/findGoodsByUid", method = RequestMethod.GET)
    public UniVerResponse<Goods> findGoodsByUid(String goodsUid){
        UniVerResponse.simplCheckField(goodsUid,"goodsUid");

        UniVerResponse<Goods> res = new UniVerResponse<>();
        res.beTrue(goodsService.get(goodsUid));
        return res;
    }

}
