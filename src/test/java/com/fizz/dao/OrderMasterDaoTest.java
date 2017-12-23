package com.fizz.dao;

import com.fizz.constant.PageConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author fizz
 *         2017/12/17 17:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

	@Autowired
	private OrderMasterDao dao;

	@Test
	public void findAllByBuyerOpenid() throws Exception {
		dao.findAllByBuyerOpenid("123", new PageRequest(PageConstant.PAGE, PageConstant.SIZE));
	}

}
