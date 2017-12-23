package com.fizz.exception;

import com.fizz.Enums.ResultEnum;

/**
 * @author fizz
 * @since 2017/12/19 21:10
 */
public class SellException extends RuntimeException {

	private static final long serialVersionUID = -5870462106719690868L;

	private Integer code;

	public SellException(ResultEnum resultEnum) {
		super(resultEnum.getMsg());
		code = resultEnum.getCode();
	}

	public SellException(Integer code, String msg) {
		super(msg);
		this.code = code;
	}

}
