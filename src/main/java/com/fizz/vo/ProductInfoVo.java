package com.fizz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author fizz
 * @time 2017/12/17 17:13
 */
@Data
public class ProductInfoVo {

	@JsonProperty("id")
	private String productId;

	@JsonProperty("name")
	private String productName;

	@JsonProperty("price")
	private BigDecimal productPrice;

	@JsonProperty("description")
	private String productDescription;

	@JsonProperty("icon")
	private String productIcon;
}
