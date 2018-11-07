package com.swapping.springcloud.ms.goods.dao;


import com.swapping.springcloud.ms.goods.domain.GoodsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsOrderDao extends JpaRepository<GoodsOrder, Long>,JpaSpecificationExecutor<GoodsOrder> {

}
