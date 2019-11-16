package com.scaler.zuul.mapper;

import com.scaler.zuul.mapper.entity.ScalerBlacklist;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


@Component
public interface BlacklistMapper {

	@Select(" select ID AS ID ,ip_addres AS ipAddres,restriction_type  as restrictionType, availability  as availability from scaler_blacklist where  ip_addres =#{ipAddres} and  restriction_type='1' ")
	ScalerBlacklist findBlacklist(String ipAddres);

}
