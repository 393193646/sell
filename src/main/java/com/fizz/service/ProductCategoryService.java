package com.fizz.service;

import com.fizz.dateobject.ProductCategory;

import java.util.List;

/**
 * @author fizz
 * @time 2017/12/17 14:33
 */

public interface ProductCategoryService {

	ProductCategory findOne(Integer categoryId);

	List<ProductCategory> findAll();

	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

	ProductCategory save(ProductCategory productCategory);
}
