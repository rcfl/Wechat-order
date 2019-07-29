package com.ctgu.sell.controller;

import com.ctgu.sell.domain.ProductCategory;
import com.ctgu.sell.domain.ProductInfo;
import com.ctgu.sell.service.ProductCategoryService;
import com.ctgu.sell.service.ProductInfoService;
import com.ctgu.sell.utils.ResultVoUtil;
import com.ctgu.sell.vo.ProductInfoVo;
import com.ctgu.sell.vo.ProductVo;
import com.ctgu.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private ProductCategoryService productCategoryService;

	@GetMapping("list")
	@Cacheable(cacheNames = "product", key = "123")
	public ResultVo list() {
		//查询所有的上架商品
		List<ProductInfo> productInfoList = productInfoService.findUpAll();

		//查询类目（一次性查询）
		//List<Integer> categoryTypeList = new ArrayList<Integer>();
		List<Integer> categoryTypeList = productInfoList.stream().
				map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);
		//数据拼装
		List<ProductVo> productVoList = new ArrayList<>();
		for (ProductCategory productCategory : productCategoryList) {
			ProductVo productVo = new ProductVo();
			productVo.setCategoryType(productCategory.getCategoryType());
			productVo.setCategoryName(productCategory.getCategoryName());

			List<ProductInfoVo> productnfoVoList = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVo productInfoVo = new ProductInfoVo();
					BeanUtils.copyProperties(productInfo, productInfoVo);
					productnfoVoList.add(productInfoVo);
				}
			}
			productVo.setProductInfoVoList(productnfoVoList);
			productVoList.add(productVo);
		}

		ResultVo resultVo = ResultVoUtil.success(productVoList);

		return resultVo;
	}

}
