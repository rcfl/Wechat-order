package com.ctgu.sell.dto;

import com.ctgu.sell.domain.OrderDetail;
import com.ctgu.sell.enums.OrderStatusEnum;
import com.ctgu.sell.enums.PayStatusEnum;
import com.ctgu.sell.utils.serializer.Date2LongSerializer;
import com.ctgu.sell.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单信息
 *
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

	private String orderId;

	//买家姓名
	private String buyerName;

	//买家电话
	private String buyerPhone;

	//买家地址
	private String buyerAddress;

	//买家微信Openid
	private String buyerOpenid;

	//订单总金额
	private BigDecimal orderAmount;

	//订单状态 默认未 0 新下单
	private Integer orderStatus;

	//支付状态， 默认为 0 未支付
	private Integer payStatus;

	//创建时间
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	//更新时间
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;

	private List<OrderDetail> orderDetailList;

	@JsonIgnore
	public OrderStatusEnum getOrderStatusEnum() {
		return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
	}

	@JsonIgnore
	public PayStatusEnum getPayStatusEnum() {
		return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
	}
}
