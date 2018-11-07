package com.swapping.springcloud.ms.member.dao;

import com.swapping.springcloud.ms.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberDao extends JpaRepository<Member, Long>,JpaSpecificationExecutor<Member> {

    Member findByMemberName(String memberName);

    Member findByUid(String uid);
}
