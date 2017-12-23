package com.fizz.service;

import com.fizz.dto.OrderDTO;

/**
 * @author fizz
 * @since 2017/12/23 22:44
 */
public interface BuyerService {

	OrderDTO findOneOrder(String openid, String orderId);

	OrderDTO cancelOrder(String openid, String orderId);
}
