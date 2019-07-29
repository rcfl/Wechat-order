package com.ctgu.sell.service;

import com.ctgu.sell.domain.SellerInfo;

public interface SellerService {

	public SellerInfo save(SellerInfo sellerInfo);

	/**
	 * 通过openid查找
	 * @param openid
	 * @return
	 */
	public SellerInfo findSellerInfoByOpenid(String openid);

	public SellerInfo findByUsernameAndPassword(String username, String password);
}
