package com.ctgu.sell.repository;

import com.ctgu.sell.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

	@Autowired
	private OrderMasterRepository orderMasterRepository;

	@Test
	@Transactional
	public void saveTest() {
		OrderMaster orderMaster = new OrderMaster();
		orderMaster.setOrderId("12345679");
		orderMaster.setBuyerName("wyx");
		orderMaster.setBuyerPhone("13871212412");
		orderMaster.setBuyerAddress("ctgu");
		orderMaster.setBuyerOpenid("abc");
		orderMaster.setOrderAmount(new BigDecimal(3.3));
		final OrderMaster save = orderMasterRepository.save(orderMaster);
		Assert.assertNotNull(save);
	}

	@Test
	public void findByBuyerOpenid() {

		Page<OrderMaster> res = orderMasterRepository.findByBuyerOpenid("110110", PageRequest.of(0, 6));
		//System.out.println(res.getTotalElements());
		//System.out.println(res.getTotalPages());
		//System.out.println(res.getNumber());
		//System.out.println(res.getContent());
		Assert.assertNotEquals(0, res.getTotalElements());
	}
}