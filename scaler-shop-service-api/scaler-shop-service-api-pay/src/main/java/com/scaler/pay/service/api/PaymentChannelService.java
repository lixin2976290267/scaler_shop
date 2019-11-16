package com.scaler.pay.service.api;

import java.util.List;

import com.scaler.pay.out.dto.PaymentChannelDTO;
import org.springframework.web.bind.annotation.GetMapping;

public interface PaymentChannelService {
	/**
	 * 查询所有支付渠道
	 * 
	 * @return
	 */
	@GetMapping("/selectAll")
	public List<PaymentChannelDTO> selectAll();
}
