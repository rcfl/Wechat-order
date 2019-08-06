package com.ctgu.sell.converter;

import com.ctgu.sell.domain.ProductInfo;
import com.ctgu.sell.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderForm  转成  OrderDTO
 */
@Slf4j
public class ProductInof2ProductDTOConverter {

	public static ProductDTO convert(ProductInfo productInfo) {

		ProductDTO productDTO = new ProductDTO();

		BeanUtils.copyProperties(productInfo, productDTO);


		return productDTO;
	}

	public static List<ProductDTO> convert(List<ProductInfo> productInfoList) {
		return productInfoList.stream().map(e ->
				convert(e)
		).collect(Collectors.toList());

	}

}
