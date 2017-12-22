package com.fizz.dto;

import lombok.Data;

/**
 * @author fizz
 * @since 2017/12/21 21:17
 */
@Data
public class CartDto {

	private String productId;

	private Integer productStock;

	public CartDto() {
	}

	public CartDto(String productId, Integer productStock) {
		this.productId = productId;
		this.productStock = productStock;
	}

}
