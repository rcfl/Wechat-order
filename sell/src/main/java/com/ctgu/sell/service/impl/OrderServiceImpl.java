package com.ctgu.sell.service.impl;

import com.ctgu.sell.converter.OrderMaster2OrderDTOConverter;
import com.ctgu.sell.domain.OrderDetail;
import com.ctgu.sell.domain.OrderMaster;
import com.ctgu.sell.domain.ProductInfo;
import com.ctgu.sell.dto.CartDTO;
import com.ctgu.sell.dto.OrderDTO;
import com.ctgu.sell.enums.OrderStatusEnum;
import com.ctgu.sell.enums.PayStatusEnum;
import com.ctgu.sell.enums.ResultEnum;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.repository.OrderDetailRepository;
import com.ctgu.sell.repository.OrderMasterRepository;
import com.ctgu.sell.service.OrderService;
import com.ctgu.sell.service.ProductInfoService;
import com.ctgu.sell.service.PushMessageService;
import com.ctgu.sell.service.WebSocket;
import com.ctgu.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private PushMessageService pushMessageService;
	@Autowired
	private WebSocket webSocket;

	/**
	 * 创建订单
	 * @param orderDTO
	 * @return
	 */
	@Override
	@Transactional
	public OrderDTO create(OrderDTO orderDTO) {
		String orderId = KeyUtil.getUniqueKey();
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

		//查询商品（数量、价格）
		for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			//根据productId 查询出商品 productInfo
			ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}

			//计算总价  单价（productInfo.getProductPrice()） * 数量（orderDetail.getProductQuantity()） + orderAmount
			orderAmount = productInfo.getProductPrice().multiply(
					new BigDecimal(orderDetail.getProductQuantity())
			).add(orderAmount);

			//订单详情入库
			//生成订单, 未orderDetail 赋值，保存
			orderDetail.setDetailId(KeyUtil.getUniqueKey());
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetail.setOrderId(orderId);
			orderDetailRepository.save(orderDetail);
		}

		//写入订单数据库（orderMaster（买家信息） 和 orderDetail（订单详情））
		OrderMaster orderMaster = new OrderMaster();
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);

		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterRepository.save(orderMaster);

		//扣库存
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
				new CartDTO(e.getProductId(), e.getProductQuantity())
		).collect(Collectors.toList());

		productInfoService.decreaseStock(cartDTOList);

		//发送websocket消息
		webSocket.sendMessage(orderDTO.getOrderId());

		return orderDTO;
	}

	/**
	 * 根据orderId 查找订单
	 * @param orderId
	 * @return
	 */
	@Override
	public OrderDTO findByOrderId(String orderId) {

		OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
		if (orderMaster == null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
		}

		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);


		return orderDTO;
	}

	/**
	 * 买家订单查询
	 * @param buyerOpenId
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
		//根据买家 openid 查找到买家
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);

		//将orderMasterPage 转成 orderDTO
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

		return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());

	}

	/**
	 * 取消订单
	 * @param orderDTO
	 * @return
	 */
	@Override
	@Transactional
	public OrderDTO cancel(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();

		//判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【取消订单】 订单状态不正确， orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		//orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【取消订单】 更新失败， orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		//返回库存
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【取消订单】 订单中无商品详情， orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
				.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productInfoService.increaseStock(cartDTOList);

		//如果已支付，需要退款
		if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)) {
			//TODO
		}
		return orderDTO;
	}

	/**
	 * 完成订单
	 * @param orderDTO
	 * @return
	 */
	@Override
	@Transactional
	public OrderDTO finish(OrderDTO orderDTO) {
		//判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【完成订单】 订单状态不正确， orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//修改状态
		orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResutl = orderMasterRepository.save(orderMaster);
		if (updateResutl == null) {
			log.error("【完结订单】 更新失败， orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		//推送模板消息
		pushMessageService.orderStatus(orderDTO);
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		//判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【完成订单】 订单状态不正确， orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//判断支付状态
		if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【支付订单】 订单支付状态不正确, orderDTO", orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}

		//修改支付状态
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【支付订单】 更新失败， orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(Pageable pageable) {

		//根据买家 openid 查找到买家
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

		//将orderMasterPage 转成 orderDTO
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

		return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
	}

	//@Override
	//public Page<OrderDTO> findByOpenId(Pageable pageable) {
	//	orderMasterRepository.findByBuyerOpenid()
	//	return null;
	//}

}
