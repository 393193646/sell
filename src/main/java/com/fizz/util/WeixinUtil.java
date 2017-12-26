package com.fizz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fizz.constant.RedisConstant;
import com.fizz.constant.WeixinConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * @author fizz
 * @since 2017/12/26 21:09
 */
public class WeixinUtil {

	public static String getAccess_Token() {
		try {
			return getAccess_TokenFromRedisOrSet();
		} catch (Exception e) {
			return null;
		}
	}

	private static String getAccess_TokenFromRedisOrSet() throws IOException {
		Jedis jedis = new Jedis(RedisConstant.host, RedisConstant.port);
		String access_token = jedis.get(WeixinConstant.access_token);
		if (StringUtils.isNotBlank(access_token)) {
			return access_token;
		} else {
			String json = getAccess_TokenFromWeixin();
			if (StringUtils.isNotBlank(json)) {
				JSONObject jsonObject = JSON.parseObject(json);
				if (jsonObject.containsKey(WeixinConstant.access_token)
						&& jsonObject.containsKey(WeixinConstant.expires_in)) {
					access_token = (String) jsonObject.get(WeixinConstant.access_token);
					Long expires_in = (Long) jsonObject.get(WeixinConstant.expires_in);
					if (StringUtils.isNotBlank(access_token) && StringUtils.isNotBlank(expires_in.toString())) {
						jedis.set(WeixinConstant.access_token, access_token, null, RedisConstant.EX, expires_in);
						return access_token;
					}
				}
			}
		}
		return null;
	}

	private static String getAccess_TokenFromWeixin() throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(WeixinConstant.getAccess_TokenURL);
		CloseableHttpResponse response = client.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		}
		return null;
	}

	public static void main(String [] args){
		getAccess_Token();
	}
}
