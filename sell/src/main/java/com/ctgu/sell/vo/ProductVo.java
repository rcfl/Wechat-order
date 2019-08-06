package com.ctgu.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品（包含类目）
 */
@Data
public class ProductVo implements Serializable {

	private static final long serialVersionUID = 5726831982333515142L;
	@JsonProperty("name")
	private String categoryName;

	@JsonProperty("type")
	private Integer categoryType;

	//@JsonProperty("foods")
	@JsonProperty("list")
	private List<ProductInfoVo> productInfoVoList;

}
