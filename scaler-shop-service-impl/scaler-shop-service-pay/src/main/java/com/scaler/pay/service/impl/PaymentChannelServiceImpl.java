package com.scaler.pay.service.impl;

import java.util.List;

import com.scaler.base.BaseApiService;
import com.scaler.core.mapper.MapperUtils;
import com.scaler.pay.mapper.PaymentChannelMapper;
import com.scaler.pay.mapper.entity.PaymentChannelEntity;
import com.scaler.pay.out.dto.PaymentChannelDTO;
import com.scaler.pay.service.api.PaymentChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentChannelServiceImpl extends BaseApiService<List<PaymentChannelDTO>>
		implements PaymentChannelService {
	@Autowired
	private PaymentChannelMapper paymentChannelMapper;

	@Override
	public List<PaymentChannelDTO> selectAll() {
		List<PaymentChannelEntity> paymentChanneList = paymentChannelMapper.selectAll();
		return MapperUtils.mapAsList(paymentChanneList, PaymentChannelDTO.class);
	}

}
