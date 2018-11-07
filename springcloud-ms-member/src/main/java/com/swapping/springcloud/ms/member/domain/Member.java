package com.swapping.springcloud.ms.member.domain;

import com.swapping.springcloud.ms.core.base.BaseBean;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Table
public class Member extends BaseBean {

    @Column(nullable = false)
    private String memberName;//会员名称

    private Date birthday;//生日

    private String memberPhone;//会员电话

    @Column(nullable = false)
    private String password;//密码


    public static Specification<Member> where(Member member){

        return  new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //创建查询列表
                List<Predicate> predicates = new ArrayList<>();

                //字段memberName是否查询
                String memberName = member.getMemberName();
                if (StringUtils.isNotBlank(memberName)){
                    predicates.add(criteriaBuilder.like(root.get("memberName"),"%"+memberName+"%"));
                }

                //字段memberPhone是否查询
                String memberPhone = member.getMemberPhone();
                if (StringUtils.isNotBlank(memberPhone)) {
                    predicates.add(criteriaBuilder.equal(root.get("memberPhone"),memberPhone));
                }

                //字段password是否查询
                String password = member.getPassword();
                if (StringUtils.isNotBlank(password)) {
                    predicates.add(criteriaBuilder.equal(root.get("password"),password));
                }

                //生日 时间查询
                Date birthday = member.getBirthday();
                if (birthday != null){
                    LocalDate localDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    localDate = localDate.plusDays(1);
                    Date endDate = java.sql.Date.valueOf(localDate);
                    predicates.add(criteriaBuilder.between(root.get("birthday"),birthday,endDate));
                }



                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
    }


}
