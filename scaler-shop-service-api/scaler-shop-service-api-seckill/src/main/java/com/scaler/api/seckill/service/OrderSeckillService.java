package com.scaler.api.seckill.service;

import com.scaler.base.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
/**
 * 查询秒杀记录
 * 
 * 
 * @description:
 */
public interface OrderSeckillService {

	@RequestMapping("/getOrder")
	public BaseResponse<JSONObject> getOrder(String phone, Long seckillId);

}
