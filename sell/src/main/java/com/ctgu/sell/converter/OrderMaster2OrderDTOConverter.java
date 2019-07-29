package com.ctgu.sell.converter;

import com.ctgu.sell.domain.OrderMaster;
import com.ctgu.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderMaster 转成 OrderDTO
 * List<OrderMaster> 转成 List<OrderDTO>
 */
public class OrderMaster2OrderDTOConverter {

	public static OrderDTO convert(OrderMaster orderMaster) {

		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		return orderDTO;
	}

	public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
		return orderMasterList.stream().map(e ->
				convert(e)
		).collect(Collectors.toList());
	}

}
