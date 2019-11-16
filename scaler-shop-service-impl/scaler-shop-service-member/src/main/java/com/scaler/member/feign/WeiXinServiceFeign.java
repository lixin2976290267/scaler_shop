package com.scaler.member.feign;

import com.scaler.weixin.service.WeiXinService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("app-scaler-weixin")
@Component
public interface WeiXinServiceFeign extends WeiXinService {

}
