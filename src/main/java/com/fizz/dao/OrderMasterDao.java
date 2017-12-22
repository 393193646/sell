package com.fizz.dao;

import com.fizz.dateobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fizz
 * 2017/12/17 17:34
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster, String> {

	Page<OrderMaster> findAllByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
