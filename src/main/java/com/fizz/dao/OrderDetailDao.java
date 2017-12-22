package com.fizz.dao;

import com.fizz.dateobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author fizz
 *         2017/12/17 17:42
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail, String> {

	List<OrderDetail> findAllByOrderId(String orderId);
}
