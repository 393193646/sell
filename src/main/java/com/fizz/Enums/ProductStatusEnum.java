package com.fizz.Enums;

import lombok.Getter;

/**
 * @author fizz
 * @time 2017/12/17 16:07
 */
@Getter
public enum ProductStatusEnum {
	Up(0, "上架"), Down(1, "下架");

	private Integer code;

	private String msg;

	ProductStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
