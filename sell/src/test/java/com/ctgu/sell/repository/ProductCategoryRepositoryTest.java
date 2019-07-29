package com.ctgu.sell.repository;

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
public class ProductCategoryRepositoryTest {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Test
	public void findOne() {
		ProductCategory productCategory = productCategoryRepository.findById(1).get();
		Assert.assertNotEquals(null, productCategory);
		System.out.println(productCategory);
	}

	@Test
	@Transactional
	/* @Transactional在测试中强制回滚 */
	public void testSave() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryName("111");
		productCategory.setCategoryType(10086);
		ProductCategory result = productCategoryRepository.save(productCategory);
		Assert.assertNotEquals(null, result);

	}

	@Test
	public void findByCategoryTypeInTest() {
		List<Integer> list = Arrays.asList(2, 3, 4, 5);

		List<ProductCategory> result = productCategoryRepository.findByCategoryTypeIn(list);
		Assert.assertNotEquals(0, result.size());
	}

}