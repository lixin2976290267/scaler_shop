package com.xxl.sso.server.feign;

import com.scaler.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("app-scaler-member")
@Component
public interface MemberServiceFeign extends MemberService {

}
