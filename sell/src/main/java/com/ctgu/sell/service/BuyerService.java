package com.ctgu.sell.service;

import com.ctgu.sell.dto.OrderDTO;

public interface BuyerService {

	//查询一个订单
	OrderDTO findOrderOne(String openid, String orderId);

	//取消订单
	OrderDTO cancelOrdel(String openid, String orderId);
}
