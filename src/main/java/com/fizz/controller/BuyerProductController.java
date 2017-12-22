package com.fizz.controller;

import com.fizz.service.ProductInfoService;
import com.fizz.vo.ProductVo;
import com.fizz.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fizz
 * @time 2017/12/17 16:55
 */

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

	@Autowired
	private ProductInfoService service;

	@GetMapping("/list")
	public ResultVO list() {
		ResultVO resultVO = new ResultVO();
		resultVO.setData(new ProductVo());
		return resultVO;
	}

}
