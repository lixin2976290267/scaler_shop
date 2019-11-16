package com.scaler.integral.mapper;

import com.scaler.integral.mapper.entity.IntegralEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


/**
 * 积分Mapper
 * 
 * @description:
 */
@Component
public interface IntegralMapper {
	@Insert("INSERT INTO `scaler_integral` VALUES (NULL, #{userId}, #{paymentId},#{integral}, #{availability}, 0, null, now(), null, now());")
	public int insertIntegral(IntegralEntity eiteIntegralEntity);

	@Select("SELECT  id as id ,USER_ID as userId, PAYMENT_ID as PAYMENTID ,INTEGRAL as INTEGRAL ,AVAILABILITY as AVAILABILITY  FROM scaler_integral where PAYMENT_ID=#{paymentId}  AND AVAILABILITY='1';")
	public IntegralEntity findIntegral(String paymentId);
}
