package com.ctgu.sell.repository;

import com.ctgu.sell.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String > {


	List<OrderDetail> findByOrderId(String orderId);
}
