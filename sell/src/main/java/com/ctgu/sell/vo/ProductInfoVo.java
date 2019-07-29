package com.ctgu.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详情
 */
@Data
public class ProductInfoVo implements Serializable {

	private static final long serialVersionUID = -4892906790281056582L;
	@JsonProperty("id")
	private String productId;

	@JsonProperty("name")
	private String productName;

	@JsonProperty("price")
	private BigDecimal productPrice;

	@JsonProperty("description")
	private String description;

	@JsonProperty("icon")
	private String productIcon;

}
