package com.swapping.springcloud.ms.member.feign.goods.bean;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class GoodsOrder {

    private Long id;// 主键 自增

    private Date createDate;// 创建时间

    private Date updateDate;// 修改时间

    private String updateId; // 修改人

    private String createId; // 创建人

    private String uid;     //业务主键

    private String goodsUid;//商品Uid

    private String memberUid;//会员Uid

    private Long buyNum;//购买数量

    private Long integral;//增加积分量  【积分量 = 单个商品积分量*购买数量】   【单个商品积分量 = 商品价格*10】

    private String remark;//订单备注


}
