package com.ctgu.sell.service.impl;

import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.enums.ResultEnum;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.repository.OrderMasterRepository;
import com.ctgu.sell.service.BuyerService;
import com.ctgu.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 买家
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private OrderMasterRepository orderMasterRepository;
	@Autowired
	private OrderService orderService;

	@Override
	public OrderDTO findOrderOne(String openid, String orderId) {
		OrderDTO orderDTO = checkOrderOwner(openid, orderId);
		if (orderDTO == null) {
			log.error("【订单详情】 查不到该订单， orderId={}", orderId);
			throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
		}

		return orderDTO;
	}

	@Override
	public OrderDTO cancelOrdel(String openid, String orderId) {
		OrderDTO orderDTO = checkOrderOwner(openid, orderId);
		if (orderDTO == null) {
			log.error("【取消订单】 查不到该订单， orderId={}", orderId);
			throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
		}
		return orderService.cancel(orderDTO);
	}

	private OrderDTO checkOrderOwner(String openid, String orderId) {
		OrderDTO orderDTO = orderService.findByOrderId(orderId);

		if (orderDTO == null) {
			//log.error("【订单详情】");
			//throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
			return null;
		}

		if (orderDTO.getBuyerOpenid().equalsIgnoreCase(openid) ) {
			log.error("【订单详情】 openid不一致。 openid={}, orderDTO={}", openid, orderDTO);
			throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
		}

		return orderDTO;
	}

}
