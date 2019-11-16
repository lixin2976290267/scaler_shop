package com.scaler.pay.feign;

import com.scaler.pay.service.api.PayMentTransacInfoService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;


@FeignClient("app-scaler-pay")
@Component
public interface PayMentTransacInfoFeign extends PayMentTransacInfoService {

}
