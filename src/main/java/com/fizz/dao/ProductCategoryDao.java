package com.fizz.dao;

import com.fizz.dateobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author fizz
 * @time 2017/12/17 12:53
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {

	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
