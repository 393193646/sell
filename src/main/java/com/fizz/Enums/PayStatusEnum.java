package com.fizz.Enums;

import lombok.Getter;

/**
 * @author fizz
 * @time 2017/12/17 17:25
 */
@Getter
public enum PayStatusEnum {
	WAIT(0, "未支付"),
	SUCCESS(1, "已支付");

	private Integer code;

	private String msg;

	PayStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
