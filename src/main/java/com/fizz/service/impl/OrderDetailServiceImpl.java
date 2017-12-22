package com.fizz.service.impl;

import com.fizz.dao.OrderDetailDao;
import com.fizz.dateobject.OrderDetail;
import com.fizz.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fizz
 * @since 2017/12/19 22:07
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailDao dao;

	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		return dao.save(orderDetail);
	}

	@Override
	public List<OrderDetail> findByOrderId(String orderId) {
		return dao.findAllByOrderId(orderId);
	}
}
