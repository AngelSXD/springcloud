package com.swapping.springcloud.ms.goods.domain;

import com.swapping.springcloud.ms.core.base.BaseBean;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(uniqueConstraints =
                {@UniqueConstraint(columnNames = {"uid"})},
        indexes =
                {@Index(columnList = "goodsName,goodsPrice")}
)
public class Goods extends BaseBean {

    private String goodsName;//商品名称

    private Long goodsPrice;//商品价格  以分存储

    private Long stock;//商品库存

    private Long saleNum;//商品销量

    public static Specification<Goods> where(Goods goods){

        return  new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //创建查询列表
                List<Predicate> predicates = new ArrayList<>();

                //字段goodsPrice是否查询
                String goodsName = goods.getGoodsName();
                if (StringUtils.isNotBlank(goodsName)){
                    predicates.add(criteriaBuilder.like(root.get("goodsName"),"%"+goodsName+"%"));
                }

                //字段goodsPrice是否查询
                Long goodsPrice = goods.getGoodsPrice();
                if (goodsPrice != null) {
                    predicates.add(criteriaBuilder.equal(root.get("goodsPrice"),goodsPrice));
                }


                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }
}
