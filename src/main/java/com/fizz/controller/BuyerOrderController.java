package com.fizz.controller;

import com.fizz.Enums.ResultEnum;
import com.fizz.constant.PageConstant;
import com.fizz.converter.OrderForm2OrderDTOConverter;
import com.fizz.dto.OrderDTO;
import com.fizz.exception.SellException;
import com.fizz.form.OrderForm;
import com.fizz.service.BuyerService;
import com.fizz.service.OrderService;
import com.fizz.util.ResultVOUtil;
import com.fizz.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fizz
 * @since 2017/12/22 23:24
 */
@Controller
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private BuyerService buyerService;

	@GetMapping("/create")
	@ResponseBody
	public ResultVO<Map<String, String>> createOrder(@Valid OrderForm orderForm,
	                                                 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【创建订单】 参数不正确, orderForm={}", orderForm);
			throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())) {
			log.error("【创建订单】错误，购物车不能为空");
			throw new SellException(ResultEnum.CART_EMPTY);
		}
		orderDTO = orderService.create(orderDTO);
		Map<String, String> data = new HashMap<>();
		data.put("orderId", orderDTO.getOrderId());
		return ResultVOUtil.success(data);
	}

	@GetMapping("/list")
	@ResponseBody
	public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
	                                     @RequestParam(value = "page", defaultValue = PageConstant.PAGE_S) Integer page,
	                                     @RequestParam(value = "size", defaultValue = PageConstant.SIZE_S) Integer size) {
		if (StringUtils.isBlank(openid)) {
			log.error("【查询订单列表】openid为空");
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		Page<OrderDTO> orderDTOPage = orderService.findList(openid, new PageRequest(page, size));
		return ResultVOUtil.success(orderDTOPage.getContent());
	}

	@GetMapping("/detail")
	@ResponseBody
	public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
	                                    @RequestParam("orderId") String orderId) {
		return ResultVOUtil.success(buyerService.findOneOrder(openid, orderId));
	}

	@PostMapping("/cancel")
	@ResponseBody
	public ResultVO cancel(@RequestParam("openid") String openid,
	                                 @RequestParam("orderId") String orderId) {
		buyerService.cancelOrder(openid, orderId);
		return ResultVOUtil.success();
	}
}
