package com.scaler.member.feign;

import com.scaler.weixin.service.VerificaCodeService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("app-scaler-weixin")
@Component
public interface VerificaCodeServiceFeign extends VerificaCodeService {
}
