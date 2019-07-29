package com.ctgu.sell.repository;

import com.ctgu.sell.domain.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Test
	@Transactional
	public void saveTest() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setDetailId("123456789");
		orderDetail.setOrderId("111000");
		orderDetail.setProductIcon("http://huaji.jpg");
		orderDetail.setProductId("110");
		orderDetail.setProductName("皮蛋瘦肉粥");
		orderDetail.setProductPrice(new BigDecimal(2.3));
		orderDetail.setProductQuantity(200);

		final OrderDetail result = orderDetailRepository.save(orderDetail);
		Assert.assertNotNull(result);

	}

	@Test
	public void findByOrderId() {
		final List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId("111000");
		Assert.assertNotEquals(0, orderDetailList.size());
	}
}