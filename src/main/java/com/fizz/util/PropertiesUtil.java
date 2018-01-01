package com.fizz.util;

import com.fizz.constant.Constant;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author fizz
 * @since 2018/1/1 20:33
 */
@Slf4j
public class PropertiesUtil {

	private static Properties prop = null;

	private static void init(){
		InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(Constant.redis_prop);
		prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			log.error("加载：" + PropertiesUtil.class.getClassLoader().getResource(Constant.redis_prop) + "失败！");
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				log.error("关闭" + PropertiesUtil.class.getClassLoader().getResource(Constant.redis_prop) + "输入流失败！");
			}
		}
	}

	public static String getProperty(String key) {
		if (prop == null) {
			init();
		}
		return prop.getProperty(key);
	}

	public static void setProperty(String key, String value) {
		if (prop == null) {
			init();
		}
		prop.setProperty(key, value);
	}
}
