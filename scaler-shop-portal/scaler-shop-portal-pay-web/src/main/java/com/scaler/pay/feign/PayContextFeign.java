package com.scaler.pay.feign;

import com.scaler.pay.service.api.PayContextService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;


@FeignClient("app-scaler-pay")
@Component
public interface PayContextFeign extends PayContextService {

}
