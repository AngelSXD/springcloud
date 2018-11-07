package com.swapping.springcloud.ms.member.feign.integral.bean;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Integral {

    private Date createDate;// 创建时间

    private Date updateDate;// 修改时间

    private String updateId; // 修改人

    private String createId; // 创建人

    private String uid;     //业务主键

    private String memberUid;//会员的 业务主键

    private Long integralNum;//积分  [会员购买商品会 增加积分]

}
