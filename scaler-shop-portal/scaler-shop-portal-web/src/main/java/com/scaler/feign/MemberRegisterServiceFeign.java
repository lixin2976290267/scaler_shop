package com.scaler.feign;

import com.scaler.member.service.MemberRegisterService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("app-scaler-member")
@Component
public interface MemberRegisterServiceFeign extends MemberRegisterService {
}
