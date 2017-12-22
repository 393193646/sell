package com.fizz.service;

import com.fizz.dateobject.OrderDetail;

import java.util.List;

/**
 * @author fizz
 * @since 2017/12/19 22:07
 */
public interface OrderDetailService {

	OrderDetail save(OrderDetail orderDetail);

	List<OrderDetail> findByOrderId(String orderId);
}
