package com.ctgu.sell.dto;

import com.ctgu.sell.enums.ProductStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: ProductDTO
 * Description:
 * date: 2019/8/3 23:24
 *
 * @author crwen
 * @since JDK 1.8
 */
@Data
public class ProductDTO implements Serializable {


	private static final long serialVersionUID = -4244765939804373200L;
	@JsonProperty("id")
	private String productId;
	@JsonProperty("name")
	//名字
	private String productName;
	@JsonProperty("price")
	//单价
	private BigDecimal productPrice;
	@JsonProperty("stock")
	//库存
	private Integer productStock;
	@JsonProperty("description")
	//描述
	private String productDescription;
	@JsonProperty("bigPic")
	//小图
	private String productIcon;
	@JsonProperty("status")
	//状态， 0 正常， 1 下架
	private Integer productStatus = ProductStatusEnum.UP.getCode();
	@JsonProperty("productType")
	//商品类目编号
	private Integer categoryType;
	@JsonProperty("createTime")
	//创建时间
	private Date createTime;
	@JsonProperty("updateTime")
	//更新时间
	private Date updateTime;
}
