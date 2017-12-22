package com.fizz.dateobject;

import com.fizz.Enums.OrderStatusEnum;
import com.fizz.Enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class OrderMaster {
  
  @Id
  private String orderId;
  private String buyerName;
  private String buyerPhone;
  private String buyerAddress;
  private String buyerOpenid;
  private BigDecimal orderAmount;
  private Integer orderStatus = OrderStatusEnum.NEW.getCode();//默认0新下单
  private Integer payStatus = PayStatusEnum.WAIT.getCode();//默认0未支付
  private Date createTime;
  private Date updateTime;
}
