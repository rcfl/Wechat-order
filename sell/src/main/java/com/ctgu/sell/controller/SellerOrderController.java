package com.ctgu.sell.controller;

import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.enums.ResultEnum;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.service.BuyerService;
import com.ctgu.sell.service.OrderService;
import com.ctgu.sell.vo.CommonPage;
import com.ctgu.sell.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

	//@GetMapping("/list")
	//public ModelAndView findList(@RequestParam(value = "page", defaultValue = "1") Integer page,
	//                             @RequestParam(value = "size", defaultValue = "10") Integer size,
	//                             Map<String, Object> map) {
	//	Page<OrderDTO> orderDTOPage = orderService.findList(PageRequest.of(page - 1, size));
	//	map.put("orderDTOPage", orderDTOPage);
	//	map.put("currentPage", page);
	//	map.put("size", size);
	//	return new ModelAndView("order/list", map);
	//}

	@GetMapping("/list")
	@ResponseBody
	public CommonResult findList(@RequestParam(value = "page", defaultValue = "1") Integer page,
	                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
		Page<OrderDTO> orderDTOPage = orderService.findList(PageRequest.of(page - 1, size));
		//List<OrderDTO> orderDTOList = orderService.findList(PageRequest.of(page - 1, size)).getContent();

		//OrderVo orderVo = new OrderVo();
		//orderVo.setOrderDTOList(orderDTOList);
		//orderVo.setTotal(orderVo.getOrderDTOList().size());
		//orderVo.setPageNum(page);
		//orderVo.setPageSize(size);
		//orderVo.setTotalPage(Math.round((float) orderVo.getTotal() / orderVo.getPageSize() + 0.5F));



		CommonPage commonPage = CommonPage.restPage(orderDTOPage);
		return CommonResult.success(commonPage);
		//CommonResult commonResult = new CommonResult(commonPage);
		//return CommonResult.success(commonPage);
		//map.put("orderDTOPage", orderDTOPage);
		//map.put("currentPage", page);
		//map.put("size", size);
		//return new ModelAndView("order/list", map);
	}


	/**
	 * 卖家端取消订单
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/cancel")
	@ResponseBody
	public CommonResult cancel(@RequestParam("orderId") String orderId,
	                           Map<String, Object> map) {
		OrderDTO orderDTO;
		try {
			orderDTO = orderService.findByOrderId(orderId);
			orderService.cancel(orderDTO);
		} catch (SellException e) {
			log.error("【卖家端取消订单】发生异常 {}", e);

			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			//return new ModelAndView("commoon/error", map);
			return CommonResult.failed();
		}

		map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
		map.put("url", "/sell/seller/order/list");
		//return new ModelAndView("common/success");
		return CommonResult.success(orderDTO);
	}

	/**
	 * 订单详情
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/detail/{orderId}")
	@ResponseBody
	public CommonResult detail(@RequestBody @PathVariable("orderId") String orderId,
	                           Map<String, Object> map) {
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = orderService.findByOrderId(orderId);

		} catch (SellException e) {
			log.error("【卖家端查询订单详情】发生异常 {}", e);

			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			//return new ModelAndView("commoon/error", map);
			return CommonResult.failed();
		}

		map.put("orderDTO", orderDTO);

		//return new ModelAndView("order/detail", map);
		return CommonResult.success(orderDTO);
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
