package com.scaler.auth.mapper;

import com.scaler.auth.mapper.entity.ScalerAppInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface AppInfoMapper {

	@Insert("INSERT INTO `scaler_app_info` VALUES (null,#{appName}, #{appId}, #{appSecret}, '0', null, null, null, null, null);")
	public int insertAppInfo(ScalerAppInfo scalerAppInfo);

	@Select("SELECT ID AS ID ,app_id as appId, app_name AS appName ,app_secret as appSecret  FROM scaler_app_info where app_id=#{appId} and app_secret=#{appSecret}; ")
	public ScalerAppInfo selectByAppInfo(@Param("appId") String appId, @Param("appSecret") String appSecret);

	@Select("SELECT ID AS ID ,app_id as appId, app_name AS appName ,app_secret as appSecret  FROM scaler_app_info where app_id=#{appId}  ")
	public ScalerAppInfo findByAppInfo(@Param("appId") String appId);
}
