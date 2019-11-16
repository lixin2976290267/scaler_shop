package com.scaler.seckill.service.mapper;

import com.scaler.seckill.service.mapper.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface OrderMapper {

	@Insert("INSERT INTO `scaler_order` VALUES (#{seckillId},#{userPhone},#{state}, now());")
	int insertOrder(OrderEntity orderEntity);

	@Select("SELECT seckill_id AS seckillid,user_phone as userPhone , state as state FROM scaler_order WHERE USER_PHONE=#{phone}  and seckill_id=#{seckillId}  AND STATE='1';")
	OrderEntity findByOrder(@Param("phone") String phone, @Param("seckillId") Long seckillId);
}
