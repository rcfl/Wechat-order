package com.ctgu.sell.service.impl;

import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.service.PayService;
import com.ctgu.sell.utils.JsonUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

	private static final String ORDER_NAME = "微信点餐订单";

	@Autowired
	private BestPayServiceImpl bestPayService;

	@Override
	public void create(OrderDTO orderDTO) {
		PayRequest payRequest = new PayRequest();
		payRequest.setOpenid(orderDTO.getBuyerOpenid());
		payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
		payRequest.setOrderId(orderDTO.getOrderId());
		payRequest.setOrderName(ORDER_NAME);
		payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
		log.info("【微信支付】 request={}", JsonUtil.toJson(payRequest));

		PayResponse payResponse = bestPayService.pay(payRequest);
		log.info("【微信字符】 response={}", JsonUtil.toJson(payResponse));
	}
}
