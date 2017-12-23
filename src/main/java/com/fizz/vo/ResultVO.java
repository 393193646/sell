package com.fizz.vo;

import lombok.Data;

/**
 * 统一返回给前端的对象
 * @author fizz
 * @time 2017/12/17 17:00
 */
@Data
public class ResultVO<T> {

	private Integer code;

	private String msg;

	private T data;

	public ResultVO() {
	}

	public ResultVO(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultVO(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}
