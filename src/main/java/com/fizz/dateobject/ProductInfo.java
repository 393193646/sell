package com.fizz.dateobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class ProductInfo {

  @Id
  private String productId;

  private String productName;

  private BigDecimal productPrice;

  private Integer productStock;

  private String productDescription;

  private String productIcon;

  private Integer productStatus;//0正常1下架

  private Integer categoryType;

  private Date createTime;

  private Date updateTime;

	public ProductInfo() {
	}

	public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, Integer categoryType) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStock = productStock;
		this.categoryType = categoryType;
	}

}
