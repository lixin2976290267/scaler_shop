package com.scaler.product.api.service;

import java.util.List;

import com.scaler.base.BaseResponse;
import com.scaler.product.output.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 
 * 
 * 
 * @description: 商品搜索服务接口
 */
public interface ProductSearchService {

	@GetMapping("/search")
	public BaseResponse<List<ProductDto>> search(String name);

}
