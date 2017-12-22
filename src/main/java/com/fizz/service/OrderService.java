package com.fizz.service;

import com.fizz.dateobject.OrderMaster;
import com.fizz.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author fizz
 *         2017/12/19 20:46
 */
public interface OrderService {

	OrderMaster save(OrderMaster orderMaster);

	OrderDTO create(OrderDTO orderDTO);

	OrderDTO findOne(String orderId);

	Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);

	OrderDTO cancel(OrderDTO orderDTO);

	OrderDTO finish(OrderDTO orderDTO);

	OrderDTO paid(OrderDTO orderDTO);
}
