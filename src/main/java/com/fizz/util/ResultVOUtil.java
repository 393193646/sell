package com.fizz.util;

import com.fizz.vo.ResultVO;

/**
 * @author fizz
 * @since 2017/12/23 17:48
 */
public class ResultVOUtil {

	public static final Integer ERROR_CODE = 0;

	public static final Integer SUCCESS_CODE = 1;

	public static final String SUCCESS_MSG = "成功";

	public static final String ERROR_MSG = "失败";

	public static<T> ResultVO<T> success() {
		return new ResultVO<>(SUCCESS_CODE, SUCCESS_MSG);
	}

	public static<T> ResultVO<T> success(T t) {
		return new ResultVO<>(SUCCESS_CODE, SUCCESS_MSG, t);
	}

	public static<T> ResultVO<T> error() {
		return new ResultVO<>(ERROR_CODE, ERROR_MSG);
	}
}
