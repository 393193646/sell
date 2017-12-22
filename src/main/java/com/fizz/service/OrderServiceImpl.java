package com.fizz.service;

import com.fizz.Enums.OrderStatusEnum;
import com.fizz.Enums.PayStatusEnum;
import com.fizz.Enums.ResultEnum;
import com.fizz.dao.OrderMasterDao;
import com.fizz.dateobject.OrderDetail;
import com.fizz.dateobject.OrderMaster;
import com.fizz.dateobject.ProductInfo;
import com.fizz.dto.CartDto;
import com.fizz.dto.OrderDTO;
import com.fizz.exception.SellException;
import com.fizz.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fizz
 *         2017/12/19 20:56
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMasterDao dao;

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Override
	public OrderMaster save(OrderMaster orderMaster) {
		return dao.save(orderMaster);
	}

	/**
	 * 注意,因为查看库存是否充足,减库存,计算订单总价 都要进行相同的数据库查询,因此放在一起提高效率
	 * @param orderDTO 用户提交的订单对象
	 * @return 返回计算后的对象
	 */
	@Override
	@Transactional
	public OrderDTO create(OrderDTO orderDTO) {
		String orderId = KeyUtil.genUniqueKey();
		List<OrderDetail> orderDetails;
		if (orderDTO == null || (orderDetails = orderDTO.getOrderDetails()) == null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		BigDecimal amount = new BigDecimal(0);
		List<ProductInfo> productInfoListDB = new ArrayList<>();
		for (OrderDetail orderDetail : orderDetails) {
			ProductInfo productInfoDB = productInfoService.findOne(orderDetail.getProductId());
			//查看订单中的商品是否存在
			if (productInfoDB == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			//查库存是否充足
			if (productInfoDB.getProductStock() < orderDetail.getProductQuantity()) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_UNABLED);
			}
			productInfoListDB.add(productInfoDB); //暂存从数据库查出的商品
			//计算总金额
			amount = amount.add(productInfoDB.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));
		}
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyNotNullProperties(orderDTO, orderMaster);
		orderMaster.setOrderId(orderId);
		orderMaster.setOrderAmount(amount);
		//保存订单
		OrderMaster orderDB = save(orderMaster);
		//保存订单详情
		List<OrderDetail> orderDetailListsDB = new ArrayList<>();
		List<ProductInfo> productInfoList = new ArrayList<>();
		for (int i = 0; i < orderDetails.size(); i++) {
			OrderDetail orderDetail = orderDetails.get(i);
			BeanUtils.copyNotNullProperties(productInfoListDB.get(i), orderDetail);
			orderDetail.setDetailId(KeyUtil.genUniqueKey());
			orderDetail.setOrderId(orderId);
			orderDetailListsDB.add(orderDetailService.save(orderDetail));
		}
		List<CartDto> cartDtoList = orderDTO.getOrderDetails().stream()
				.map(o -> new CartDto(o.getProductId(), o.getProductQuantity())).collect(Collectors.toList());
		productInfoService.decreaseStock(cartDtoList);
		orderDTO.setOrderDetails(orderDetailListsDB);
		return orderDTO;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		OrderDTO orderDTO = new OrderDTO();
		OrderMaster one = dao.findOne(orderId);
		if (one == null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		orderDTO.setOrderDetails(orderDetailList);
		BeanUtils.copyNotNullProperties(dao.findOne(orderId), orderDTO);
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
		Page<OrderMaster> all = dao.findAllByBuyerOpenid(buyerOpenId, new PageRequest(0, 10));
		return all.map(o -> {
			OrderDTO orderDTO = new OrderDTO();
			BeanUtils.copyNotNullProperties(o, orderDTO);
			return orderDTO;
		});
	}

	@Override
	@Transactional
	public OrderDTO cancel(OrderDTO orderDTO) {
		//验证订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【取消订单】, 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//更新订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyNotNullProperties(orderDTO, orderMaster);
		OrderMaster save = save(orderMaster);
		if (save == null) {
			log.error("【取消订单】, 更新失败, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.CANCEL_ORDER_ERROR);
		}
		//恢复商品库存
		List<OrderDetail> orderDetails = orderDTO.getOrderDetails();
		if (orderDetails == null) {
			log.error("【取消订单】, 订单详情不存在, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		List<CartDto> cartDtoList = orderDetails.stream().map(o -> new CartDto(o.getProductId(),
				o.getProductQuantity())).collect(Collectors.toList());
		productInfoService.increaseStock(cartDtoList);
		//退款
		if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
			//todo
			save.getOrderAmount();
		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO finish(OrderDTO orderDTO) {
		//验证订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【完结订单】, 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//更新订单状态
		orderDTO.setOrderStatus(1);
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyNotNullProperties(orderDTO, orderMaster);
		OrderMaster save = save(orderMaster);
		if (save == null) {
			log.error("【完结订单】, 更新失败, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.FINISH_ORDER_ERROR);
		}
		//收款
		if (save.getPayStatus().equals(PayStatusEnum.WAIT)) {
			//todo
		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		//验证订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【支付订单】, 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//验证支付状态
		if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【支付订单】, 支付状态不正确, orderId={}, payStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//更新支付状态
		orderDTO.setPayStatus(1);
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyNotNullProperties(orderDTO, orderMaster);
		OrderMaster save = save(orderMaster);
		if (save == null) {
			log.error("【支付订单】, 更新失败, orderId={}, payStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
			throw new SellException(ResultEnum.FINISH_ORDER_ERROR);
		}
		return orderDTO;
	}

}
