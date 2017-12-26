package com.fizz.controller.weixin;

import com.fizz.constant.Constant;
import com.fizz.constant.WeixinConstant;
import com.fizz.dto.TokenDTO;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author fizz
 * @since 2017/12/24 21:32
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController {

	/**
	 * 验证服务器接口是否为开发者的服务器
	 */
	@GetMapping("/getToken")
	@ResponseBody
	public String getToken(TokenDTO tokenDTO, HttpServletResponse response){
		if ((StringUtils.isBlank(tokenDTO.getSignature()) || StringUtils.isBlank(tokenDTO.getTimestamp())
				|| StringUtils.isBlank(tokenDTO.getNonce())
				|| StringUtils.isBlank(tokenDTO.getEchostr()))) {
			return "";
		}
		String[] arr = new String[]{tokenDTO.getTimestamp(), WeixinConstant.token, tokenDTO.getNonce()};
		Arrays.sort(arr);
		StringBuffer sb = new StringBuffer();
		sb.append(arr[0]);
		sb.append(arr[1]);
		sb.append(arr[2]);
		String hash = null;
		try {
			hash = new String(Hex.encodeHex(MessageDigest.getInstance("SHA-1").
					digest(sb.toString().getBytes(Constant.charset))));
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return (StringUtils.isNoneBlank(hash) && hash.equals(tokenDTO.getSignature()))
				? tokenDTO.getEchostr() : "";
	}
}
