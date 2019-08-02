package com.ctgu.sell.controller;

import com.ctgu.sell.domain.ProductCategory;
import com.ctgu.sell.domain.ProductInfo;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.form.ProductForm;
import com.ctgu.sell.service.ProductCategoryService;
import com.ctgu.sell.service.ProductInfoService;
import com.ctgu.sell.utils.KeyUtil;
import com.ctgu.sell.utils.ResultVoUtil;
import com.ctgu.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端订单
 */
@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 商品列表
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	//@GetMapping("/list")
	//public ModelAndView findList(@RequestParam(value = "page", defaultValue = "1") Integer page,
	//                             @RequestParam(value = "size", defaultValue = "5") Integer size,
	//                             Map<String, Object> map) {
	//	Page<ProductInfo> productInfoPage = productInfoService.findAll(PageRequest.of(page - 1, size));
	//	map.put("productInfoPage", productInfoPage);
	//	map.put("currentPage", page);
	//	map.put("size", size);
	//	return new ModelAndView("product/list", map);
	//}

	@GetMapping("/list")
	@ResponseBody
	public ResultVo findList(@RequestParam(value = "page", defaultValue = "1") Integer page,
	                         @RequestParam(value = "size", defaultValue = "5") Integer size,
	                         Map<String, Object> map) {
		//Page<ProductInfo> productInfoPage = productInfoService.findAll(PageRequest.of(page - 1, size));
		List<ProductInfo> productInfoList = productInfoService.findAll(PageRequest.of(page - 1, size)).getContent();

		ResultVo resultVo = ResultVoUtil.success(productInfoList);
		return resultVo;

		//map.put("productInfoPage", productInfoPage);
		//map.put("currentPage", page);
		//map.put("size", size);
		//return new ModelAndView("product/list", map);
	}

	/**
	 * 商品上架
	 * @param productId
	 * @param map
	 * @return
	 */
	@GetMapping("/on_sale")
	public ModelAndView onSale(@RequestParam("productId") String productId,
	                           Map<String, Object> map) {

		ProductInfo productInfo = new ProductInfo();
		try {
			productInfo = productInfoService.onSale(productId);
			System.out.println(productInfo);
		} catch (SellException e) {
			log.error("【卖家端上架商品】 发生异常 {}", e);
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/list");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success", map);
	}

	/**
	 * 商品下架
	 * @param productId
	 * @param map
	 * @return
	 */
	@GetMapping("/off_sale")
	public ModelAndView offSale(@RequestParam("productId") String productId,
	                           Map<String, Object> map) {

		ProductInfo productInfo = new ProductInfo();
		try {
			productInfo = productInfoService.offSale(productId);
			System.out.println(productInfo);
		} catch (SellException e) {
			log.error("【卖家端下架商品】 发生异常 {}", e);
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/list");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success", map);
	}

	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
	                          Map<String, Object> map) {
		if (!StringUtils.isEmpty(productId)) {
			ProductInfo productInfo = productInfoService.findById(productId);
			map.put("productInfo", productInfo);
		}

		//查询所有类目
		List<ProductCategory> categoryList = productCategoryService.findAll();
		map.put("categoryList", categoryList);

		return new ModelAndView("product/index", map);
	}

	@PostMapping("/save")
	//@CachePut(cacheNames = "product", key = "123")
	@CacheEvict(cacheNames = "product", key = "123")
	public ModelAndView save(@Valid ProductForm form,
	                         BindingResult bindingResult,
	                         Map<String, Object> map) {

		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/sell/seller/product/index");
			return new ModelAndView("common/error", map);
		}

		ProductInfo productInfo = new ProductInfo();
		try {
			if (!StringUtils.isEmpty(form.getProductId())) {
				productInfo = productInfoService.findById(form.getProductId());
			} else  {
				form.setProductId(KeyUtil.getUniqueKey());
			}
			BeanUtils.copyProperties(form, productInfo);

			productInfoService.save(productInfo);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/index");
			return new ModelAndView("common/error", map);
		}

		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success", map);
	}

}
