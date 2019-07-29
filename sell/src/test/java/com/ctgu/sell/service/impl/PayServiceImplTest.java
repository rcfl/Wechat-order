package com.ctgu.sell.service.impl;

import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.service.OrderService;
import com.ctgu.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

	@Autowired
	private PayService payService;
	@Autowired
	private OrderService orderService;

	@Test
	public void create() {
		OrderDTO orderDTO = orderService.findByOrderId("1532785814571501917");
		payService.create(orderDTO);

	}
}