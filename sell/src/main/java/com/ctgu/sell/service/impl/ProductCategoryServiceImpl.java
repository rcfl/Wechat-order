package com.ctgu.sell.service.impl;

import com.ctgu.sell.domain.ProductCategory;
import com.ctgu.sell.repository.ProductCategoryRepository;
import com.ctgu.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public ProductCategory findById(Integer catgoryId) {
		return productCategoryRepository.findById(catgoryId).get();
	}

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<ProductCategory> findAll(Pageable pageable) {
		return productCategoryRepository.findAll(pageable);
	}

	/**
	 * 查询全部
	 * @return
	 */
	@Override
	public List<ProductCategory> findAll() {
		return productCategoryRepository.findAll();
	}


	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return productCategoryRepository.save(productCategory);
	}
}
