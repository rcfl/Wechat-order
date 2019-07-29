package com.ctgu.sell.service.impl;

import com.ctgu.sell.domain.ProductInfo;
import com.ctgu.sell.enums.ProductStatusEnum;
import com.ctgu.sell.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

	@Autowired
	private ProductInfoService productInfoService;

	@Test
	public void findById() {
		final ProductInfo productInfo = productInfoService.findById("123456");
		Assert.assertEquals("123456", productInfo.getProductId());

	}

	@Test
	public void findUpAll() {
		final List<ProductInfo> productInfoList = productInfoService.findUpAll();
		Assert.assertNotEquals(0, productInfoList.size());
	}

	@Test
	public void findAll() {
		final PageRequest page = PageRequest.of(0, 2);
		final Page<ProductInfo> list = productInfoService.findAll(page);
		//Assert.assertNotEquals(0, list.getContent().size());
		System.out.println(list.getContent());
	}

	@Test
	public void save() {
	}


	@Test
	public void increaseStock() {
	}

	@Test
	public void decreaseStock() {
	}

	@Test
	public void onSale(){
		ProductInfo result = productInfoService.onSale("123456");
		Assert.assertEquals(ProductStatusEnum.UP,result.getProductStatusEnum());
	}

	@Test
	@Transactional
	@Rollback(false)
	public void offSale(){
		ProductInfo result = productInfoService.offSale("123456");
		Assert.assertEquals(ProductStatusEnum.DOWN,result.getProductStatusEnum());
	}
}