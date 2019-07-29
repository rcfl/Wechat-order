package com.ctgu.sell.repository;

import com.ctgu.sell.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

	/**
	 * 查询上架商品
	 */
	public List<ProductInfo> findByProductStatus(Integer productStatus);

}
