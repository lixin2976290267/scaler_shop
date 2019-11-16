package com.scaler.pay.service.api;

import com.scaler.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * 
 * 
 * @description: 根据不同的渠道id(支付方式) 返回不同的支付提交表单
 */
public interface PayContextService {
	@GetMapping("/toPayHtml")
	public BaseResponse<JSONObject> toPayHtml(@RequestParam("channelId") String channelId,
											  @RequestParam("payToken") String payToken);

}
