package com.scaler.pay.feign;

import com.scaler.pay.service.api.PaymentChannelService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;


@FeignClient("app-scaler-pay")
@Component
public interface PaymentChannelFeign extends PaymentChannelService {

}
