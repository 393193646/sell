package com.fizz.dao;

import com.fizz.dateobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * @author fizz
 * @time 2017/12/17 15:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {

	@Autowired
	private ProductInfoDao dao;

	@Test
	@Transactional
	public void saveTest() {
		Assert.assertNotNull(dao.save(new ProductInfo("66001", "Mac Pro 15.4", new BigDecimal("14000"), 100, 1)));
	}

	@Test
	public void findAllByProductStatusTest() throws Exception {
		Assert.assertNotNull(dao.findAllByProductStatus(0));
	}

}
