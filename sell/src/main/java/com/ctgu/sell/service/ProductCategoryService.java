package com.ctgu.sell.service;

import com.ctgu.sell.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

	//根据id查找类目
	public ProductCategory findById(Integer catgoryId);

	//查找所有类目
	public List<ProductCategory> findAll();


	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);


	public ProductCategory save(ProductCategory productCategory);

}
