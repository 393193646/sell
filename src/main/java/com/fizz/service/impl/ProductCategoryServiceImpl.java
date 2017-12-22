package com.fizz.service.impl;

import com.fizz.dao.ProductCategoryDao;
import com.fizz.dateobject.ProductCategory;
import com.fizz.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fizz
 * @time 2017/12/17 14:57
 */

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao dao;

	@Override
	public ProductCategory findOne(Integer categoryId) {
		return dao.findOne(categoryId);
	}

	@Override
	public List<ProductCategory> findAll() {
		return dao.findAll();
	}

	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		return dao.findByCategoryTypeIn(categoryTypeList);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return dao.save(productCategory);
	}
}
