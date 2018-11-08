package com.swapping.springcloud.ms.integral.service;

import com.swapping.springcloud.ms.integral.domain.Integral;

public interface IntegralService extends ICRUDService<Integral> {

    Integral findByMember(String memberUid);

    int saveByMybatis(Integral integral);

}
