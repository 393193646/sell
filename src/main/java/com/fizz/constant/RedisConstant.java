package com.fizz.constant;

/**
 * @author fizz
 * @since 2017/12/26 22:10
 */
public class RedisConstant {

	//Only set the key if it does not already exist.
	public static final String NX = "NX";

	//Only set the key if it already exist.
	public static final String XX = "XX";

	//EX = seconds;
	public static final String EX = "EX";

	//PX = milliseconds
	public static final String PX = "PX";
}
