package com.fizz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author fizz
 * @time 2017/12/17 17:10
 */
@Data
public class ProductVo {

	@JsonProperty("name")
	private String categoryName;

	@JsonProperty("type")
	private Integer categoryType;

	@JsonProperty("foods")
	private List<ProductInfoVo> productInfoVoList;
}
