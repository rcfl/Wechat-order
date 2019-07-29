package com.ctgu.sell.service.impl;

import com.ctgu.sell.domain.ProductInfo;
import com.ctgu.sell.dto.CartDTO;
import com.ctgu.sell.enums.ProductStatusEnum;
import com.ctgu.sell.enums.ResultEnum;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.repository.ProductInfoRepository;
import com.ctgu.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoRepository productInfoRepository;

	/**
	 * 根据商品id查找商品
	 * @param productId
	 * @return
	 */
	@Override
	public ProductInfo findById(String productId) {
		return   productInfoRepository.findById(productId).get();
	}

	/**
	 * 查找所有在架商品
	 * @return
	 */
	@Override
	public List<ProductInfo> findUpAll() {
		return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	/**
	 * 查找所有商品
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		return productInfoRepository.findAll(pageable);
	}

	/**
	 * 新增商品
	 * @param productInfo
	 * @return
	 */
	@Override
	public ProductInfo save(ProductInfo productInfo) {
		return productInfoRepository.save(productInfo);
	}

	/**
	 * 增加库存
	 * @param cartDTOList
	 */
	@Override
	@Transactional
	public void increaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO : cartDTOList) {
			//根据购物车中的product 查询出商品
			ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId()).get();
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}

			Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
			productInfo.setProductStock(result);
			productInfoRepository.save(productInfo);
		}


	}

	/**
	 * 减少库存
	 * @param cartDTOList
	 */
	@Override
	@Transactional
	public void decreaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO : cartDTOList) {
			//根据购物车中的 productId 查询出商品
			ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId()).get();
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}

			Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();

			if (result < 0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
			}

			productInfo.setProductStock(result);

			//更新
			productInfoRepository.save(productInfo);
		}
	}

	/**
	 * 上架
	 * @param productId
	 * @return
	 */
	@Override
	public ProductInfo onSale(String productId) {
		ProductInfo productInfo = productInfoRepository.findById(productId).get();

		if (productInfo == null) {
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}

		if (productInfo.getProductStatus() ==  ProductStatusEnum.UP.getCode()) {
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}

		productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
		productInfoRepository.save(productInfo);
		return productInfo;
	}

	/**
	 * 下架商品
	 * @param productId
	 * @return
	 */
	@Override
	@Transactional
	public ProductInfo offSale(String productId) {
		ProductInfo productInfo = productInfoRepository.findById(productId).get();

		if (productInfo == null) {
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}

		if (productInfo.getProductStatus() ==  ProductStatusEnum.DOWN.getCode()) {
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}

		productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
		productInfoRepository.save(productInfo);
		return productInfo;
	}

}
