package com.fizz.dao;

import com.fizz.dateobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * @author fizz
 * @time 2017/12/17 12:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

	@Autowired
	private ProductCategoryDao dao;

	@Test
	public void findOneTest() {
		ProductCategory one = dao.findOne(1);
		System.out.println(one);
	}

	@Test
	@Transactional
	public void saveTest() {
		ProductCategory productCategory = dao.findOne(1);
		productCategory.setCategoryName("c");
		dao.save(productCategory);
	}

	@Test
	public void findByCategoryTypeIn() throws Exception {
		List<Integer> categoryTypeList = Arrays.asList(1, 2);
		List<ProductCategory> byCategoryTypeIn = dao.findByCategoryTypeIn(categoryTypeList);
		Assert.assertNotNull(byCategoryTypeIn);
	}
}
