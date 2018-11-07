package com.swapping.springcloud.ms.goods.utils;

import com.swapping.springcloud.ms.core.exception.MyException;
import com.swapping.springcloud.ms.core.response.UniVerResponse;
import com.swapping.springcloud.ms.goods.domain.Goods;
import com.swapping.springcloud.ms.goods.domain.GoodsOrder;
import com.swapping.springcloud.ms.goods.feign.msMember.bean.Member;
import com.swapping.springcloud.ms.goods.feign.msMember.client.FeignMsMemberClient;
import com.swapping.springcloud.ms.goods.service.GoodsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 异步feign调用 工具类
 *
 *
 * 本类中 仅作一个示例使用
 * 目的：
 *      告诉使用者，在spring cloud微服务的架构中，如果需要从不同的微服务中获取的不同信息  没有先后的必然关系的话，
 *      可以使用这种方式，进行异步的调用，异步分别获取不同ms的接口，以求在最短的时间内 【并行】得到各个接口的数据。
 *      而不是使用 【串行】的方式 去一步一步的获取结果！！！！
 * 参考：
 *      https://www.cnblogs.com/sxdcgaq8080/p/9560260.html
 */
@Component
public class AsynFeignUtils {

    @Autowired
    FeignMsMemberClient memberClient;

    @Autowired
    GoodsService goodsService;

    // 尽量少的使用System.out.print() 参考地址：https://www.cnblogs.com/sxdcgaq8080/p/9646802.html
    static Logger asyn_log4j = Logger.getRootLogger();

    public Map<String,Object> getMemberAndGoods2Asyn(GoodsOrder goodsOrder){


        //===============基础信息====================
        String memberUid = goodsOrder.getMemberUid();
        String goodsUid = goodsOrder.getGoodsUid();



        //================异步1 获取会员信息==================
        CompletableFuture<Member> cf1 = new CompletableFuture<>();
        new Thread(() -> {

            asyn_log4j.info("异步获取---->根据uid获取会员信息");
            Member member = null;
            try {
                UniVerResponse<Member> res = memberClient.findMemberByUid(memberUid);
                member = res.getObj();
            } catch (Exception e) {
                asyn_log4j.info("会员信息获取失败");
                throw new MyException("会员信息获取失败",UniVerResponse.ERROR_BUSINESS,AsynFeignUtils.class);
            }
            // 告诉completableFuture任务已经完成
            cf1.complete(member);
        }).start();


        //==================异步2  获取商品信息
        CompletableFuture<Goods> cf2 = new CompletableFuture<>();
        new Thread(() -> {

            asyn_log4j.info("异步获取商品信息---->");
            Goods goods = null;
            try {
                goods = goodsService.get(goodsUid);
            } catch (Exception e) {
                asyn_log4j.info("商品信息获取失败");
                throw new MyException("商品信息获取失败",UniVerResponse.ERROR_BUSINESS,AsynFeignUtils.class);
            }
            // 告诉completableFuture任务已经完成
            cf2.complete(goods);
        }).start();

        Map<String ,Object> res = new HashMap<String, Object>();

        try {

            Member member =  cf1.get();
            Goods goods = cf2.get();

            if(member != null && goods != null){
                res.put("member", member);
                res.put("goods", goods);
            }
        } catch (Exception e) {
            throw new MyException("AsynFeignUtils异步操作获取失败",UniVerResponse.ERROR_BUSINESS,AsynFeignUtils.class);
        }
        return res;

    }

}
