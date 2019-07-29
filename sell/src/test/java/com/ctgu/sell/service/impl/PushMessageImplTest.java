package com.ctgu.sell.service.impl;

import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.service.OrderService;
import com.ctgu.sell.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageImplTest {

	@Autowired
	private PushMessageService pushMessageService;
	@Autowired
	private OrderService orderService;

	@Test
	public void orderStatus() {
		OrderDTO orderDTO = orderService.findByOrderId("1563871041411681777");

		pushMessageService.orderStatus(orderDTO);

	}
}