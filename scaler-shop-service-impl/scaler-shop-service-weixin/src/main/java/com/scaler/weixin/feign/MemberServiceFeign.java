package com.scaler.weixin.feign;

import com.scaler.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @ClassName MemberServiceFeign
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/21 0:33
 * @Version 1.0
 **/
@FeignClient("app-scaler-member")
@Component
public interface MemberServiceFeign extends MemberService {
}
