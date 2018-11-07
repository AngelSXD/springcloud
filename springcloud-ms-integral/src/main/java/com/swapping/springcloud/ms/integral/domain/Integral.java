package com.swapping.springcloud.ms.integral.domain;

import com.swapping.springcloud.ms.core.base.BaseBean;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Entity;
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
        {
                @UniqueConstraint(columnNames = {"memberUid"}),
                @UniqueConstraint(columnNames = {"uid"})
        }
)
public class Integral extends BaseBean {

    private String memberUid;//会员的 业务主键

    private Long integralNum;//积分  [会员购买商品会 增加积分]


    public static Specification<Integral> where(Integral integral){

        return  new Specification<Integral>() {
            @Override
            public Predicate toPredicate(Root<Integral> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //创建查询列表
                List<Predicate> predicates = new ArrayList<>();

                //字段goodsPrice是否查询
                String memberUid = integral.getMemberUid();
                if (memberUid != null) {
                    predicates.add(criteriaBuilder.equal(root.get("memberUid"),memberUid));
                }

                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }

}
