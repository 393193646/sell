package com.fizz.Enums;

import lombok.Getter;

/**
 * @author fizz
 * @time 2017/12/17 17:25
 */
@Getter
public enum OrderStatusEnum {
	NEW(0, "新订单"),
	FINISHED(1, "完结"),
	CANCEL(2, "取消订单");

	private Integer code;

	private String msg;

	OrderStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
