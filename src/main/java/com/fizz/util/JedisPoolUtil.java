package com.fizz.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fizz
 * @since 2018/1/1 10:44
 */
@Slf4j
public class JedisPoolUtil {

	private static ReentrantLock lockPool = new ReentrantLock();

	private static ReentrantLock lockJedis = new ReentrantLock();

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池 
	 */
	private static void initialPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(Integer.valueOf(PropertiesUtil.getProperty("maxActive")));
			config.setMaxIdle(Integer.valueOf(PropertiesUtil.getProperty("maxIdle")));
			config.setMaxWaitMillis(Integer.valueOf(PropertiesUtil.getProperty("maxWait")));
			config.setMinIdle(Integer.valueOf(PropertiesUtil.getProperty("minIdle")));
			config.setTestOnBorrow(Boolean.valueOf(PropertiesUtil.getProperty("testOnBorrow")));
			jedisPool = new JedisPool(config, PropertiesUtil.getProperty("server_host"),
					Integer.valueOf(PropertiesUtil.getProperty("server_port")),
					Integer.valueOf(PropertiesUtil.getProperty("timeout")), PropertiesUtil.getProperty("password"));
		} catch (Exception e) {
			log.error("create JedisPool error : " + e);
		}
	}

	/**
	 * 在多线程环境同步初始化 
	 */
	private static void poolInit() {
		lockPool.lock();
		try {
			if (jedisPool == null) {
				initialPool();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			lockPool.unlock();
		}
	}

	public static Jedis getJedis() {
		lockJedis.lock();
		if (jedisPool == null) {
			poolInit();
		}
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
			}
		} catch (Exception e) {
			log.error("Get jedis error : " + e);
			log.error(e.getMessage());
		} finally {
			closeResource(jedis);
			lockJedis.unlock();
		}
		return jedis;
	}

	/**
	 * 释放jedis资源 
	 *
	 * @param jedis
	 */
	public static void closeResource(final Jedis jedis) {
		if (jedis != null && jedisPool != null) {
			jedis.close();
		}
	}

	/**
	 * 设置 String 
	 */
	public synchronized static void setString(String key, String value) {
		try {
			value = StringUtils.isEmpty(value) ? "" : value;
			getJedis().set(key, value);
		} catch (Exception e) {
			log.error("Set key error : " + e);
		}
	}

	/**
	 * 设置 过期时间 
	 * @param seconds 以秒为单位
	 */
	public synchronized static void setString(String key, int seconds, String value) {
		try {
			value = StringUtils.isEmpty(value) ? "" : value;
			getJedis().setex(key, seconds, value);
		} catch (Exception e) {
			log.error("Set keyex error : " + e);
		}
	}

	/**
	 * 获取String值 
	 */
	public synchronized static String getString(String key) {
		if (getJedis() == null || !getJedis().exists(key)) {
			return null;
		}
		return getJedis().get(key);
	}
}
