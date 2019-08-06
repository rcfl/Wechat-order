package com.ctgu.sell.vo;

import com.ctgu.sell.enums.ProductStatusEnum;
import com.ctgu.sell.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
	private String productDescription;

	@JsonProperty("icon")
	private String productIcon;

	@JsonProperty("stock")
	private Integer productStock;

	//状态， 0 正常， 1 下架
	@JsonProperty("status")
	private Integer productStatus = ProductStatusEnum.UP.getCode();

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;
	//@JsonProperty("list")
	//private List<ProductInfo> productInfoList;

}
