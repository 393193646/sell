package com.fizz.dao;

import com.fizz.dateobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author fizz
 * @time 2017/12/17 15:22
 */
public interface ProductInfoDao extends JpaRepository<ProductInfo, String> {

//	List<ProductInfo> findAllByCategoryTypeIn(List<Integer> categoryTypeList);

	List<ProductInfo> findAllByProductStatus(Integer productStatus);
}
