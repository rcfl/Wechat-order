package com.ctgu.sell.service;

import com.ctgu.sell.domain.ProductInfo;
import com.ctgu.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

	public ProductInfo findById(String productId);

	/**
	 * 查询已上架的商品
	 * @return
	 */
	public List<ProductInfo> findUpAll();

	public Page<ProductInfo> findAll(Pageable pageable);

	public ProductInfo save(ProductInfo productInfo);

	//加库存
	public void increaseStock(List<CartDTO> cartDTOList);

	//减库存
	public void decreaseStock(List<CartDTO> cartDTOList);

	//上架
	public ProductInfo onSale(String productId);

	//下架
	public ProductInfo offSale(String productId);
}
