package com.ctgu.sell.repository;

import com.ctgu.sell.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

	/**
	 * 根据买家的openid 查询
	 * @param buyerOpenid
	 * @param pageable
	 * @return
	 */
	Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);


}
