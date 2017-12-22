package com.fizz.Enums;

import lombok.Getter;

/**
 * @author fizz
 *         2017/12/19 21:12
 */
@Getter
public enum ResultEnum {

	ORDER_NOT_EXIST(10, "订单不存在"),
	ORDERDETAIL_NOT_EXIST(20, "订单详情不存在"),
	PRODUCT_NOT_EXIST(30, "商品不存在"),
	PRODUCT_STOCK_UNABLED(40, "商品库存不足"),
	ORDER_STATUS_ERROR(50, "订单状态错误"),
	CANCEL_ORDER_ERROR(60, "取消订单错误"),
	FINISH_ORDER_ERROR(70, "完结订单错误"),
	PAID_ORDER_ERROR(80, "收款错误"),
	;


	private Integer code;

	private String msg;

	ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
