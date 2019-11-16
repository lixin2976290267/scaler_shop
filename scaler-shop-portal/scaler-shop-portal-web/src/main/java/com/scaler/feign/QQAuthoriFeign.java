package com.scaler.feign;

import com.scaler.member.service.QQAuthorService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @ClassName QQAuthoriFeign
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/22 23:07
 * @Version 1.0
 **/
@FeignClient("app-scaler-member")
@Component
public interface QQAuthoriFeign extends QQAuthorService {

}
