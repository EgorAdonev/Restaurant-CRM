package com.adonev.waurma.crm.data.repository;

import com.adonev.waurma.crm.data.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
