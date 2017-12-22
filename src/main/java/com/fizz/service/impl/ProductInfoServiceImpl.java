package com.fizz.service.impl;

import com.fizz.Enums.ProductStatusEnum;
import com.fizz.Enums.ResultEnum;
import com.fizz.dao.ProductInfoDao;
import com.fizz.dateobject.ProductInfo;
import com.fizz.dto.CartDto;
import com.fizz.exception.SellException;
import com.fizz.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fizz
 * @since 2017/12/17 16:02
 */

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoDao dao;

	@Override
	public ProductInfo findOne(String productId) {
		return dao.findOne(productId);
	}

	@Override
	public List<ProductInfo> findUpAll() {
		return dao.findAllByProductStatus(ProductStatusEnum.Up.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		return dao.save(productInfo);
	}

	@Override
	public void increaseStock(List<CartDto> list) {
		for (CartDto cartDto : list) {
			ProductInfo productInfo = findOne(cartDto.getProductId());
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer surplusStock = productInfo.getProductStock() + cartDto.getProductStock();
			productInfo.setProductStock(surplusStock);
			save(productInfo);
		}
	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDto> list) {
		for (CartDto cartDto : list) {
			ProductInfo productInfo = findOne(cartDto.getProductId());
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer surplusStock = productInfo.getProductStock() - cartDto.getProductStock();
			if (surplusStock < 0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_UNABLED);
			}
			productInfo.setProductStock(surplusStock);
			save(productInfo);
		}
	}
}
