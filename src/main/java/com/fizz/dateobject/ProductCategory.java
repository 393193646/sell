package com.fizz.dateobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author fizz
 * @time 2017/12/17 12:27
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {

	@Id
	@GeneratedValue
	private Integer categoryId;

	private String categoryName;

	private Integer categoryType;

	private Date createTime;

	private Date updateTime;

	public ProductCategory() {
	}

	public ProductCategory(String categoryName, Integer categoryType) {
		this.categoryName = categoryName;
		this.categoryType = categoryType;
	}
}
