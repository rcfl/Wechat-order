package com.ctgu.sell.controller;

import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
public class PayController {

	@Autowired
	private OrderService orderService;

	public void create(@RequestParam("orderId") String orderId,
	                   @RequestParam("returnUrl") String returnUrl) {

		OrderDTO orderDTO = orderService.findByOrderId(orderId);

	}

}
