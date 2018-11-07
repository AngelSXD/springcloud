package com.swapping.springcloud.ms.member.service;

import com.swapping.springcloud.ms.member.domain.Member;

public interface MemberService extends ICRUDService<Member> {

    Member findByMemberName(String memberName);

    Member get(String memberUid);
}
