package com.ctgu.sell.converter;

import com.ctgu.sell.domain.OrderDetail;
import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.enums.ResultEnum;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderForm  转成  OrderDTO
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

	public static OrderDTO convert(OrderForm orderForm) {
		Gson gson = new Gson();
		OrderDTO orderDTO = new OrderDTO();

		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerAddress(orderForm.getAddress());

		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		try {
			orderDetailList = gson.fromJson(orderForm.getItems(),
					new TypeToken<List<OrderDetail>>() {
					}.getType());
		} catch (Exception e) {
			log.error("【对象转换】 错误， String={}", orderForm.getItems());
			System.out.println(orderDetailList);
			System.out.println(orderForm);
			throw new SellException(ResultEnum.PARAM_ERROR);
		}

		orderDTO.setOrderDetailList(orderDetailList);

		return orderDTO;
	}

}
