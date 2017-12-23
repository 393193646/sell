package com.fizz.dateobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fizz.util.serializer.Date2LongSerializerUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class OrderDetail {
  
  @Id
  private String detailId;
  private String orderId;
  private String productId;
  private String productName;
  private BigDecimal productPrice;
  private Integer productQuantity;
  private String productIcon;
  @JsonSerialize(using = Date2LongSerializerUtil.class)
  private Date createTime;
  @JsonSerialize(using = Date2LongSerializerUtil.class)
  private Date updateTime;

  public OrderDetail() {
  }

  public OrderDetail(String productId, String productName, BigDecimal productPrice, Integer productQuantity) {
    this.productId = productId;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productQuantity = productQuantity;
  }
}
