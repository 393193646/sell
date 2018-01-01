package com.fizz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fizz.constant.WeixinConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author fizz
 * @since 2017/12/26 21:09
 */
@Slf4j
public class WeixinUtil {

	public static String getAccess_TokenFromRedisOrSet() {
		try {
			String access_token = JedisPoolUtil.getString(WeixinConstant.access_token);
			if (StringUtils.isNotBlank(access_token)) {
				log.info("get access_token from redis");
				return access_token;
			} else {
				String json = getAccess_TokenFromWeixin();
				if (StringUtils.isNotBlank(json)) {
					JSONObject jsonObject = JSON.parseObject(json);
					if (jsonObject.containsKey(WeixinConstant.access_token)
							&& jsonObject.containsKey(WeixinConstant.expires_in)) {
						access_token = (String) jsonObject.get(WeixinConstant.access_token);
						Integer expires_in = Integer.valueOf(jsonObject.get(WeixinConstant.expires_in).toString());
						if (StringUtils.isNotBlank(access_token) && expires_in != null) {
							JedisPoolUtil.setString(WeixinConstant.access_token, expires_in, access_token);
							log.info("get access_token from weixin");
							return access_token;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
		}
		return null;
	}

	private static String getAccess_TokenFromWeixin() {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(WeixinConstant.getAccess_TokenURL);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error("response close error!");
			}
			try {
				if (client != null) {
					client.close();
				}
			} catch (IOException e) {
				log.error("client close error!");
			}
		}
		return null;
	}

	public static void main(String [] args) throws IOException {
		getAccess_TokenFromRedisOrSet();
	}
}
