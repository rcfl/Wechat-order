package com.ctgu.sell.repository;

import com.ctgu.sell.domain.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoReposity extends JpaRepository<SellerInfo, String> {

	public SellerInfo findByOpenid(String openid);

	public SellerInfo findByUsernameAndPassword(String username, String password);

}
