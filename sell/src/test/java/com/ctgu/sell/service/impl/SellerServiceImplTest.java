package com.ctgu.sell.service.impl;

import com.ctgu.sell.domain.SellerInfo;
import com.ctgu.sell.service.SellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

	@Autowired
	private SellerService sellerService;

	private final String OPENID = "o92yt5kvdUrFKSdBV5IFNOlIyydY";

	@Test
	public void save() {
	}

	@Test
	public void findSellerInfoByOpenid() {
		final SellerInfo result = sellerService.findSellerInfoByOpenid(OPENID);
		Assert.assertEquals(OPENID, result.getOpenid());
	}
}