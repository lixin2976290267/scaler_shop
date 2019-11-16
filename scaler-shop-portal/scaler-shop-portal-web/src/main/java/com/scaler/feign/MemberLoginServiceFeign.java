package com.scaler.feign;

import com.scaler.member.service.MemberLoginService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("app-scaler-member")
@Component
public interface MemberLoginServiceFeign extends MemberLoginService {
}
