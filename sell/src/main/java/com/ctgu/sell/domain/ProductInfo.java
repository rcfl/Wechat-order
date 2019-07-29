package com.ctgu.sell.domain;

import com.ctgu.sell.enums.ProductStatusEnum;
import com.ctgu.sell.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {

	private static final long serialVersionUID = -4244765939804373200L;
	@Id
	private String productId;

	//名字
	private String productName;

	//单价
	private BigDecimal productPrice;

	//库存
	private Integer productStock;

	//描述
	private String productDescription;

	//小图
	private String productIcon;

	//状态， 0 正常， 1 下架
	private Integer productStatus = ProductStatusEnum.UP.getCode();

	//商品类目编号
	private Integer categoryType;

	//创建时间
	private Date createTime;

	//更新时间
	private Date updateTime;

	/**
	 * 获取商品状态
	 * @return ProductStatusEnum 枚举类型
	 */
	@JsonIgnore
	public ProductStatusEnum getProductStatusEnum() {
		return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
	}

}
