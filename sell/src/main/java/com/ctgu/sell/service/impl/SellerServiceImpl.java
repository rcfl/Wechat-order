package com.ctgu.sell.service.impl;

import com.ctgu.sell.domain.SellerInfo;
import com.ctgu.sell.repository.SellerInfoReposity;
import com.ctgu.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerInfoReposity sellerInfoReposity;

	@Override
	public SellerInfo save(SellerInfo sellerInfo) {
		return sellerInfoReposity.save(sellerInfo);
	}

	@Override
	public SellerInfo findSellerInfoByOpenid(String openid) {
		return sellerInfoReposity.findByOpenid(openid);
	}

	@Override
	public SellerInfo findByUsernameAndPassword(String username, String password) {
		return sellerInfoReposity.findByUsernameAndPassword(username, password);
	}
}
