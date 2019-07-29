package com.ctgu.sell.repository;

import com.ctgu.sell.domain.SellerInfo;
import com.ctgu.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoReposityTest {

	@Autowired
	private SellerInfoReposity sellerInfoReposity;

	private final String OPENID = "o92yt5kvdUrFKSdBV5IFNOlIyydY";

	@Test
	public void saveTest() {
		SellerInfo sellerInfo = new SellerInfo();
		sellerInfo.setSellerId(KeyUtil.getUniqueKey());
		sellerInfo.setUsername("crw");
		sellerInfo.setPassword("123");
		sellerInfo.setOpenid(OPENID);
		final SellerInfo seller = sellerInfoReposity.save(sellerInfo);
		Assert.assertNotNull(seller);
	}

	@Test
	public void findByOpenidTest() {
		final SellerInfo sellerInfo = sellerInfoReposity.findByOpenid(OPENID);
		Assert.assertEquals(OPENID, sellerInfo.getOpenid());
	}

}