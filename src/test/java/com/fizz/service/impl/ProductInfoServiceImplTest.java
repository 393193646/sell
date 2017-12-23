package com.fizz.service.impl;

import com.fizz.constant.PageConstant;
import com.fizz.dateobject.ProductInfo;
import com.fizz.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * @author fizz
 * @time 2017/12/17 16:13
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
	@Autowired
	private ProductInfoService service;

	@Test
	public void findOne() throws Exception {
		Assert.assertEquals("66001", service.findOne("66001").getProductId());
	}

	@Test
	public void findUpAll() throws Exception {
		Assert.assertNotNull(service.findUpAll());
	}

	@Test
	public void findAll() throws Exception {
		Assert.assertNotNull(service.findAll(new PageRequest(PageConstant.PAGE, PageConstant.SIZE)));
	}

	@Test
	@Transactional
	public void save() throws Exception {
		Assert.assertNotNull(service.save(new ProductInfo("66001", "Mac Pro 15.4", new BigDecimal("14000"), 100, 1)));
	}

}
