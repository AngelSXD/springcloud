package com.swapping.springcloud.ms.member.mapper;

import com.swapping.springcloud.ms.member.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    @Insert("insert into member" +
            "(create_date,update_date,update_id,create_id,uid,member_name,birthday,member_phone,password) " +
            "values (#{createDate},#{updateDate},#{updateId},#{createId},#{uid},#{memberName},#{birthday},#{memberPhone},#{password})")
    int save(Member member);

    Member findByUid(@Param("uid") String uid);
}
