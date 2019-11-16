package com.scaler.zuul.feign;

import com.scaler.auth.service.api.AuthorizationService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;


@FeignClient("app-scaler-auth")
@Component
public interface AuthorizationServiceFeign extends AuthorizationService {

}
