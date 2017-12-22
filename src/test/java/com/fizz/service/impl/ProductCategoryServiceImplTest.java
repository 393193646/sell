package com.fizz.service.impl;

import com.fizz.dateobject.ProductCategory;
import com.fizz.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * @author fizz
 * @time 2017/12/17 15:00
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

	@Autowired
	private ProductCategoryService service;

	@Test
	public void findOne() throws Exception {
		Assert.assertEquals(new Integer(1), service.findOne(1).getCategoryId());
	}

	@Test
	public void findAll() throws Exception {
		Assert.assertNotNull(service.findAll());
	}

	@Test
	public void findByCategoryTypeIn() throws Exception {
		Assert.assertNotNull(service.findByCategoryTypeIn(Arrays.asList(1, 2)));
	}

	@Test
	@Transactional
	public void save() throws Exception {
		Assert.assertNotNull(service.save(new ProductCategory("d", 4)));
	}

}
