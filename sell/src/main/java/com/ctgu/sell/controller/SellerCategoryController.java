package com.ctgu.sell.controller;

import com.ctgu.sell.domain.ProductCategory;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.form.CategoryForm;
import com.ctgu.sell.service.ProductCategoryService;
import com.ctgu.sell.vo.CommonPage;
import com.ctgu.sell.vo.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	//@GetMapping("/list")
	//public ModelAndView list(Map<String, Object> map) {
	//
	//	List<ProductCategory> productCategoryList = productCategoryService.findAll();
	//	map.put("categoryList", productCategoryList);
	//
	//	return new ModelAndView("category/list", map);
	//}

	@GetMapping("/list")
	@ResponseBody
	public CommonResult list(@RequestParam(value = "page", defaultValue = "1") Integer page,
	                         @RequestParam(value = "size", defaultValue = "10") Integer size) {


		Page<ProductCategory> productCategoryPage = productCategoryService.findAll(PageRequest.of(page - 1, size));
		//map.put("categoryList", productCategoryList);
		CommonPage commonPage = CommonPage.restPage(productCategoryPage);
		//return new ModelAndView("category/list", map);

		//ResultVo resultVo = ResultVoUtil.success(productCategoryList);
		//CommonPage commonPage = new CommonPage();
		//Page<ProductCategory> categoryPage = new PageImpl<>(productCategoryList);
		//CommonPage<ProductCategory> commonPage = CommonPage.restPage(categoryPage);


		return CommonResult.success(commonPage);
	}



	//@GetMapping("/list/withChildren")
	//@ResponseBody
	//public CommonResult find

	/**
	 * 类目导航
	 * @param categoryId
	 * @param map
	 * @return
	 */
	@GetMapping(value = {"/index/{categoryId}", "index"})
	@ResponseBody
	public CommonResult index(@RequestBody @PathVariable (value = "categoryId", required = false) Integer categoryId,
	                          Map<String, Object> map) {
		ProductCategory productCategory = null;
		if (categoryId != null && categoryId >= 0) {
			productCategory = productCategoryService.findById(categoryId);
			map.put("productCategory", productCategory);
		}

		//return new ModelAndView("category/index", map);
		return CommonResult.success(productCategory);
	}

	/**
	 * 新增目录
	 * @param form
	 * @param bindingResult
	 * @param map
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public CommonResult save(
			//@RequestBody CategoryForm form,
	                         @Valid @RequestBody CategoryForm form,
	                         BindingResult bindingResult,
	                         Map<String, Object> map) {
		//System.out.println(form);
		ProductCategory result = null;
		if (bindingResult.hasErrors()) {
			map.put("url", "/sell/seller/category/index");
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
		}

		ProductCategory productCategory = new ProductCategory();
		try {
			if (form.getCategoryId() != null && form.getCategoryId() >= 0) {
				productCategory = productCategoryService.findById(form.getCategoryId());

			}
			BeanUtils.copyProperties(form, productCategory);
			result = productCategoryService.save(productCategory);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/category/index");
			//return new ModelAndView("common/error", map);
			return CommonResult.failed();
		}

		map.put("url", "/sell/seller/category/list");
		//return new ModelAndView("common/success", map);
		return CommonResult.success(form);
	}


}
