package com.swapping.springcloud.ms.goods.feign.msMember.bean;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Member {



    private Date createDate;// 创建时间

    private String uid;     //业务主键

    private String memberName;//会员名称

    private Date birthday;//生日

    private String memberPhone;//会员电话

    private String password;//密码
}
