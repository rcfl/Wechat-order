package com.ctgu.sell.controller;

import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.enums.ResultEnum;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.service.BuyerService;
import com.ctgu.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 */
@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private BuyerService buyerService;

	@GetMapping("/list")
	public ModelAndView findList(@RequestParam(value = "page", defaultValue = "1") Integer page,
	                             @RequestParam(value = "size", defaultValue = "10") Integer size,
	                             Map<String, Object> map) {
		Page<OrderDTO> orderDTOPage = orderService.findList(PageRequest.of(page - 1, size));
		map.put("orderDTOPage", orderDTOPage);
		map.put("currentPage", page);
		map.put("size", size);
		return new ModelAndView("order/list", map);
	}

	/**
	 * 卖家端取消订单
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderId") String orderId,
	                           Map<String, Object> map) {
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = orderService.findByOrderId(orderId);
			orderService.cancel(orderDTO);
		} catch (SellException e) {
			log.error("【卖家端取消订单】发生异常 {}", e);

			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("commoon/error", map);
		}

		map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
		map.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success");
	}

	/**
	 * 订单详情
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("orderId") String orderId,
	                           Map<String, Object> map) {
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = orderService.findByOrderId(orderId);

		} catch (SellException e) {
			log.error("【卖家端查询订单详情】发生异常 {}", e);

			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("commoon/error", map);
		}

		map.put("orderDTO", orderDTO);

		return new ModelAndView("order/detail", map);
	}

	/**
	 * 完结订单
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/finish")
	public ModelAndView finish(@RequestParam("orderId") String orderId,
	                           Map<String, Object> map) {
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = orderService.findByOrderId(orderId);
			orderService.finish(orderDTO);
		} catch (SellException e) {
			log.error("【卖家端完结订单】发生异常 {}", e);

			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("commoon/error", map);
		}

		map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS);
		map.put("url", "/sell/seller/order/list");

		return new ModelAndView("common/success");

	}

}
