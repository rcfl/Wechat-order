package com.ctgu.sell.service.impl;

import com.ctgu.sell.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

	@Autowired
	private ProductCategoryServiceImpl productCategoryService;

	@Test
	public void findById() {
		ProductCategory productCategory = productCategoryService.findById(1);
		Assert.assertEquals(new Integer(1), productCategory.getCategoryId());

	}

	@Test
	public void findAll() {
		final List<ProductCategory> all = productCategoryService.findAll();
		Assert.assertNotEquals(0, all.size());
	}

	@Test
	public void findByCategoryTypeIn() {
		List<Integer> list = Arrays.asList(2, 3, 4, 5);
		final List<ProductCategory> byCategoryTypeIn = productCategoryService.findByCategoryTypeIn(list);
		Assert.assertNotEquals(0, byCategoryTypeIn.size());
	}

	@Test
	@Transactional
	public void save() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryType(10086);
		productCategory.setCategoryName("1111");
		final ProductCategory save = productCategoryService.save(productCategory);
		Assert.assertNotNull(save);

	}
}