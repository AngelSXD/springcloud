package com.swapping.springcloud.ms.integral.dao;

import com.swapping.springcloud.ms.integral.domain.Integral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IntegralDao extends JpaRepository<Integral, Long>,JpaSpecificationExecutor<Integral> {

    Integral findByMemberUid(String memberUid);
}
