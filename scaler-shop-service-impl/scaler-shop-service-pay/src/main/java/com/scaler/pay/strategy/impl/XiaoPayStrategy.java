package com.scaler.pay.strategy.impl;


import com.scaler.pay.mapper.entity.PaymentChannelEntity;
import com.scaler.pay.out.dto.PayMentTransacDTO;
import com.scaler.pay.strategy.PayStrategy;

public class XiaoPayStrategy implements PayStrategy {

	@Override
	public String toPayHtml(PaymentChannelEntity pymentChannel, PayMentTransacDTO payMentTransacDTO) {

		return "小米支付from表单提交";
	}

}
