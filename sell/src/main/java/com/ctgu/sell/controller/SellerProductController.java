package com.ctgu.sell.controller;

import com.ctgu.sell.domain.ProductCategory;
import com.ctgu.sell.domain.ProductInfo;
import com.ctgu.sell.enums.ProductStatusEnum;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.form.ProductForm;
import com.ctgu.sell.service.ProductCategoryService;
import com.ctgu.sell.service.ProductInfoService;
import com.ctgu.sell.utils.KeyUtil;
import com.ctgu.sell.vo.CommonPage;
import com.ctgu.sell.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
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
	public CommonResult findList(@RequestParam(value = "page", defaultValue = "1") Integer page,
	                         @RequestParam(value = "size", defaultValue = "5") Integer size,
	                         Map<String, Object> map) {
		//Page<ProductInfo> productInfoPage = productInfoService.findAll(PageRequest.of(page - 1, size));
		Page<ProductInfo> productInfoPage = productInfoService.findAll(PageRequest.of(page - 1, size));
		//List<ProductInfoVo> productInfoVoList = ProductInfo2ProductInfoVoConverter.convert(productInfoPage.getContent());
		//List<ProductInfoVo> productInfoVoList = ProductInfo2ProductInfoVoConverter.convert(productInfoList);
		//List<ProductDTO> productDTOLisFt = ProductInof2ProductDTOConverter.convert(productInfoList);

		//ProductVo productVo = new ProductVo();
		//productVo.setProductInfoVoList(productInfoVoList);
		//productInfoVo.setProductDTOList(productDTOList);

		//ResultVo resultVo = ResultVoUtil.success(productVo);
		CommonPage commonPage = CommonPage.restPage(productInfoPage);

		//CommonResult commonResult = new CommonResult(commonPage);
		return CommonResult.success(commonPage);

		//map.put("productInfoPage", productInfoPage);
		//map.put("currentPage", page);
		//map.put("size", size);
		//return new ModelAndView("product/list", map);
	}

	/**
	 * 商品上架/下架
	 * @param productId
	 * @param map
	 * @return
	 */
	@GetMapping("/on_sale")
	@ResponseBody
	public CommonResult onSale(@RequestParam("productId") String productId,
	                           @RequestParam("productStatus") Integer productStatus,
	                           Map<String, Object> map) {

		ProductInfo productInfo = new ProductInfo();
		try {
			// 如果 productStatus 为down， 说明原来是 up
			if (productStatus.equals(ProductStatusEnum.DOWN.getCode())) {
				productInfoService.offSale(productId);
			} else  {
				productInfo = productInfoService.onSale(productId);
			}

			System.out.println(productInfo);
		} catch (SellException e) {
			log.error("【卖家端上架商品】 发生异常 {}", e);
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/list");
			//return new ModelAndView("common/error", map);
			return CommonResult.failed();
		}

		map.put("url", "/sell/seller/product/list");
		//return new ModelAndView("common/success", map);
		return CommonResult.success(productInfo);
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

	//@GetMapping("/index")
	@GetMapping("/productInfo/{productId}")
	@ResponseBody
	public CommonResult index(@PathVariable(value = "productId", required = false) String productId,
	                          Map<String, Object> map) {
		ProductInfo productInfo = null;

		if (!StringUtils.isEmpty(productId)) {
			productInfo = productInfoService.findById(productId);
			map.put("productInfo", productInfo);
		}

		//查询所有类目
		List<ProductCategory> categoryList = productCategoryService.findAll();
		map.put("categoryList", categoryList);

		//return new ModelAndView("product/index", map);
		return CommonResult.success(productInfo);
	}

	@PostMapping("/save")
	@ResponseBody
	//@CachePut(cacheNames = "product", key = "123")
	@CacheEvict(cacheNames = "product", key = "123")
	public CommonResult save(@Valid @RequestBody ProductForm form,
	                         BindingResult bindingResult,
	                         Map<String, Object> map) {
		System.out.println(form);
		if (bindingResult.hasErrors()) {
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/sell/seller/product/index");
			//return new ModelAndView("common/error", map);
			return CommonResult.failed();
		}

		ProductInfo productInfo = new ProductInfo();
		ProductInfo result = null;
		try {
			if (!StringUtils.isEmpty(form.getProductId())) {
				productInfo = productInfoService.findById(form.getProductId());
			} else  {
				form.setProductId(KeyUtil.getUniqueKey());
			}
			BeanUtils.copyProperties(form, productInfo);

			result = productInfoService.save(productInfo);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/index");
			//return new ModelAndView("common/error", map);
			return CommonResult.failed();
		}

		map.put("url", "/sell/seller/product/list");
		//return new ModelAndView("common/success", map);
		return CommonResult.success(result);
	}



}
