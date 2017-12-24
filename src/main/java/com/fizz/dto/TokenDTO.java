package com.fizz.dto;

import lombok.Data;

/**
 * @author fizz
 * @since 2017/12/24 21:38
 */
@Data
public class TokenDTO {

	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;
}
