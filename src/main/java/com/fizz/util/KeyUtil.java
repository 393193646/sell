package com.fizz.util;

import java.util.Random;

/**
 * @author fizz
 * @since 2017/12/19 23:09
 */
public class KeyUtil {

	/**
	 * 生成数据库主键,格式为: 当前时间毫秒数 + 六位随机数
	 * 为保证System.currentTimeMillis()不同, 加上同步
	 * @return 返回随机数主键
	 */
	public static synchronized String genUniqueKey(){
		return System.currentTimeMillis() + String.valueOf(new Random().nextInt(900000) + 100000);
	}
}
