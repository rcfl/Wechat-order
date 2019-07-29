package com.ctgu.sell.service.impl;

import com.ctgu.sell.domain.OrderDetail;
import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.enums.OrderStatusEnum;
import com.ctgu.sell.enums.PayStatusEnum;
import com.ctgu.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

	@Autowired
	private OrderService orderService;

	private final String BUYER_OPENID = "1101110";

	private final String ORDER_ID = "1563713207987466122";

	@Test
	public void create() {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setBuyerName("wyx");
		orderDTO.setBuyerAddress("ctgu");
		orderDTO.setBuyerPhone("13871212412");
		orderDTO.setBuyerOpenid(BUYER_OPENID);

		//购物车
		List<OrderDetail> orderDetailList = new ArrayList<>();

		OrderDetail o1 = new OrderDetail();
		o1.setProductId("123458");
		o1.setProductQuantity(1);
		orderDetailList.add(o1);

		orderDTO.setOrderDetailList(orderDetailList);

		OrderDTO result = orderService.create(orderDTO);
		log.info("【创建订单】 result = {}", result);
	}

	@Test
	public void findByOrderId() {
		OrderDTO result = orderService.findByOrderId(ORDER_ID);
		log.info("【查询单个订单】 result = {}", result);
		Assert.assertEquals(ORDER_ID, result.getOrderId());
	}

	@Test
	public void findList() {

		Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, PageRequest.of(0, 6));
		Assert.assertNotEquals(0, orderDTOPage.getTotalElements());

	}

	@Test
	public void cancel() {
		OrderDTO orderDTO = orderService.findByOrderId("1563713037684333119");
		OrderDTO result = orderService.cancel(orderDTO);
		Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
	}

	@Test
	public void finish() {
		OrderDTO orderDTO = orderService.findByOrderId("1563713037684333119");
		OrderDTO result = orderService.finish(orderDTO);
		Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
	}

	@Test
	public void paid() {
		OrderDTO orderDTO = orderService.findByOrderId("1563713037684333119");
		OrderDTO result = orderService.paid(orderDTO);
		Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
	}

	@Test
	public void findList1() {

		Page<OrderDTO> orderDTOPage = orderService.findList(PageRequest.of(0, 10));
		//System.out.println(orderDTOPage.getContent());
		final List<OrderDTO> content = orderDTOPage.getContent();
		for (OrderDTO orderDTO : content) {
			System.out.println(orderDTO);
		}
		//Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
		System.out.println(orderDTOPage.getTotalElements());
		Assert.assertTrue("查询所有的订单列表", orderDTOPage.getTotalElements() > 0);
	}
}