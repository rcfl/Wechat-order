package com.ctgu.sell.repository;

import com.ctgu.sell.domain.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

	@Autowired
	private ProductInfoRepository productInfoRepository;


	@Test
	@Transactional
	public void saveTest() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId(UUID.randomUUID().toString());
		productInfo.setProductName("皮蛋粥");
		productInfo.setProductPrice(new BigDecimal("3.2"));
		productInfo.setProductDescription("很好喝的哦");
		productInfo.setProductIcon("http://balabalabala.jpg");
		productInfo.setCategoryType(2);

		final ProductInfo result = productInfoRepository.save(productInfo);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByProductStatus() {
		final List<ProductInfo> list = productInfoRepository.findByProductStatus(0);
		Assert.assertNotEquals(0, list.size());

	}
}