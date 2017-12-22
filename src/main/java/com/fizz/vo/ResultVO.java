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
}
