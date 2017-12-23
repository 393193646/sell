package com.fizz.service.impl;

import com.fizz.Enums.ResultEnum;
import com.fizz.dto.OrderDTO;
import com.fizz.exception.SellException;
import com.fizz.service.BuyerService;
import com.fizz.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fizz
 * @since 2017/12/23 22:48
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private OrderService orderService;

	@Override
	public OrderDTO findOneOrder(String openid, String orderId) {
		return checkOrderOwner(openid, orderId);
	}

	@Override
	public OrderDTO cancelOrder(String openid, String orderId) {
		OrderDTO orderDTO = checkOrderOwner(openid, orderId);
		return orderService.cancel(orderDTO);
	}

	private OrderDTO checkOrderOwner(String openid, String orderId) {
		OrderDTO one = orderService.findOne(orderId);
		if (one == null) {
			return null;
		}
		if (!one.getBuyerOpenid().equalsIgnoreCase(openid)) {
			log.error("【查看订单详情】错误，openid不一致，openid={}, orderDTO={}", openid, one);
			throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
		}
		return one;
	}
}
