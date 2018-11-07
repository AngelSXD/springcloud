package com.swapping.springcloud.ms.goods.domain;

import com.swapping.springcloud.ms.core.base.BaseBean;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员购买商品 订单记录表
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints =
        {@UniqueConstraint(columnNames = {"uid"})},
        indexes =
                {@Index(columnList = "goodsUid,memberUid")}
)
public class GoodsOrder extends BaseBean {

    @Column(nullable = false)//此列 在数据库中不能为null
    private String goodsUid;//商品Uid

    @Column(nullable = false)
    private String memberUid;//会员Uid

    @Column(nullable = false)
    private Long buyNum;//购买数量

    @Column(nullable = false)
    private Long integral;//增加积分量  【积分量 = 单个商品积分量*购买数量】   【单个商品积分量 = 商品价格*10】

    private String remark;//订单备注


    @Transient//加上这个注解  此列不会在数据库中出现
    private String memberName;//会员名称    展示使用字段

    @Transient
    private String goodsName;//商品名称     展示使用字段

    @Transient
    private Long goodsPrice;//商品价格    展示使用字段


    public static Specification<GoodsOrder> where(GoodsOrder goodsOrder){

        return  new Specification<GoodsOrder>() {
            @Override
            public Predicate toPredicate(Root<GoodsOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //创建查询列表
                List<Predicate> predicates = new ArrayList<>();

                //字段goodsUid是否查询
                String goodsUid = goodsOrder.getGoodsUid();
                if (StringUtils.isNotBlank(goodsUid)){
                    predicates.add(criteriaBuilder.equal(root.get("goodsUid"),goodsUid));
                }

                //字段memberUid是否查询
                String memberUid = goodsOrder.getMemberUid();
                if (memberUid != null) {
                    predicates.add(criteriaBuilder.equal(root.get("memberUid"),memberUid));
                }

                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }



}
