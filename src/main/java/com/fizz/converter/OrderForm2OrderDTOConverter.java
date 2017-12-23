package com.fizz.converter;

import com.fizz.Enums.ResultEnum;
import com.fizz.dateobject.OrderDetail;
import com.fizz.dto.OrderDTO;
import com.fizz.exception.SellException;
import com.fizz.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author fizz
 * @since 2017/12/23 17:13
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

	public static OrderDTO convert(OrderForm orderForm) {
		if (orderForm == null) {
			return null;
		}
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());
		Gson gson = new Gson();
		List<OrderDetail> orderDetailList;
		try {
			orderDetailList = gson.fromJson(orderForm.getItems(),
					new TypeToken<List<OrderDetail>>() {
					}.getType());
		} catch (JsonSyntaxException e) {
			log.error("【对象转换】错误, json={}", orderForm.getItems());
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		orderDTO.setOrderDetails(orderDetailList);
		return orderDTO;
	}
}
