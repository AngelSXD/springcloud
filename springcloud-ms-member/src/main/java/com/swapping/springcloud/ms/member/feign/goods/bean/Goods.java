package com.swapping.springcloud.ms.member.feign.goods.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Goods {

    private String uid;//商品业务主键

    private String goodsName;//商品名称

    private Long goodsPrice;//商品价格  以分存储

    private Long stock;//商品库存

    private Long saleNum;//商品销量
}
