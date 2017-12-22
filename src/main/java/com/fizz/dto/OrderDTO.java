package com.fizz.dto;

import com.fizz.dateobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fizz
 *         2017/12/19 20:50
 */
@Data
public class OrderDTO {

	private String orderId;
	private String buyerName;
	private String buyerPhone;
	private String buyerAddress;
	private String buyerOpenid;
	private BigDecimal orderAmount;
	private Integer orderStatus;//默认0新下单 1完成订单 2取消订单
	private Integer payStatus;//默认0未支付 1已支付 2取消支付
	private Date createTime;
	private Date updateTime;
	private List<OrderDetail> orderDetails;

	public OrderDTO() {
	}

	public OrderDTO(String buyerName, String buyerPhone, String buyerAddress, String buyerOpenid, List<OrderDetail> orderDetails) {
		this.buyerName = buyerName;
		this.buyerPhone = buyerPhone;
		this.buyerAddress = buyerAddress;
		this.buyerOpenid = buyerOpenid;
		this.orderDetails = orderDetails;
	}
}
