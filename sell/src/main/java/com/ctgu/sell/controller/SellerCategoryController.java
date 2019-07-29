package com.ctgu.sell.controller;

import com.ctgu.sell.domain.ProductCategory;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.form.CategoryForm;
import com.ctgu.sell.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@GetMapping("/list")
	public ModelAndView list(Map<String, Object> map) {

		List<ProductCategory> productCategoryList = productCategoryService.findAll();
		map.put("categoryList", productCategoryList);

		return new ModelAndView("category/list", map);
	}

	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
	                          Map<String, Object> map) {
		if (categoryId != null && categoryId >= 0) {
			ProductCategory productCategory = productCategoryService.findById(categoryId);
			map.put("productCategory", productCategory);
		}

		return new ModelAndView("category/index", map);
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid CategoryForm form,
	                         BindingResult bindingResult,
	                         Map<String, Object> map) {
		System.out.println(form);
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
			productCategoryService.save(productCategory);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/category/index");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/sell/seller/category/list");
		return new ModelAndView("common/success", map);
	}


}
