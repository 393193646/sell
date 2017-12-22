package com.fizz.service;

import com.fizz.Enums.OrderStatusEnum;
import com.fizz.Enums.PayStatusEnum;
import com.fizz.dateobject.OrderDetail;
import com.fizz.dto.OrderDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fizz
 * @since 2017/12/21 22:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImplTest.class);

	public static final String ORDER_ID = "1513947676370732246";

	public static final String BUYER_OPENID = "xll0509";

	@Autowired
	private OrderService service;

	@Test
	public void save() throws Exception {
	}

	@Test
	public void create() throws Exception {
		List<OrderDetail> orderDetails = new ArrayList<>();
		orderDetails.add(new OrderDetail("1",
				"mac pro", new BigDecimal(1), 1));
		orderDetails.add(new OrderDetail("2",
				"联想拯救者", new BigDecimal(1), 1));
		OrderDTO orderDto = new OrderDTO("薛露露", "18709278209",
				"西安市环村南路润景怡园11号楼1单元703", "xll0509", orderDetails);
		service.create(orderDto);
	}

	@Test
	public void findOne() throws Exception {
		OrderDTO one = service.findOne(ORDER_ID);
		logger.info("OrderDto : {}", one);
	}

	@Test
	public void findList() throws Exception {
		service.findList(BUYER_OPENID, new PageRequest(0, 10));
	}

	@Test
	public void cancel() throws Exception {
		OrderDTO cancel = service.cancel(service.findOne(ORDER_ID));
		Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), cancel.getOrderStatus());
	}

	@Test
	public void finish() throws Exception {
		OrderDTO finish = service.finish(service.findOne(ORDER_ID));
		Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), finish.getOrderStatus());
	}

	@Test
	public void paid() throws Exception {
		OrderDTO paid = service.paid(service.findOne(ORDER_ID));
		Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), paid.getPayStatus());
	}

}
