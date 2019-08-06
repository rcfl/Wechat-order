package com.ctgu.sell.converter;

import com.ctgu.sell.domain.ProductInfo;
import com.ctgu.sell.vo.ProductInfoVo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderForm  转成  OrderDTO
 */
@Slf4j
public class ProductInfo2ProductInfoVoConverter {

	public static ProductInfoVo convert(ProductInfo productInfo) {
		Gson gson = new Gson();
		ProductInfoVo productInfoVo = new ProductInfoVo();

		BeanUtils.copyProperties(productInfo, productInfoVo);


		return productInfoVo;
	}

	public static List<ProductInfoVo> convert(List<ProductInfo> productInfoList) {
		return productInfoList.stream().map(e ->
				convert(e)
		).collect(Collectors.toList());

	}

}
