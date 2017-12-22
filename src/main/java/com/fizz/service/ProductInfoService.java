package com.fizz.service;

import com.fizz.dateobject.ProductInfo;
import com.fizz.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author fizz
 * @time 2017/12/17 15:58
 */
public interface ProductInfoService {

	ProductInfo findOne(String productId);

	List<ProductInfo> findUpAll();

	Page<ProductInfo> findAll(Pageable pageable);

	ProductInfo save(ProductInfo productInfo);

	//加库存
	void increaseStock(List<CartDto> list);

	//减库存
	void decreaseStock(List<CartDto> list);
}
