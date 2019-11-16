package com.scaler.member.mapper;

import com.scaler.member.input.dto.UserInpDTO;
import com.scaler.member.mapper.entity.UserDo;
import com.scaler.member.output.dto.UserOutDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public interface UserMapper {

    @Insert("INSERT INTO `scaler_user` VALUES (null,#{mobile}, #{email}, #{password}, #{userName}, null, null, null, '1', null, null, null);")
    int register(UserDo userDo);

    @Select("SELECT * FROM scaler_user WHERE MOBILE=#{mobile};")
    UserDo existMobile(@Param("mobile") String mobile);

    @Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USERNAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID "
            + "  FROM scaler_user  WHERE MOBILE=#{mobile} and password=#{password};")
    UserDo login(@Param("mobile") String mobile, @Param("password") String password);

    @Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USER_NAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID"
            + " FROM scaler_user WHERE user_Id=#{userId}")
    UserDo findByUserId(@Param("userId") Long userId);


    @Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USER_NAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID"
            + " FROM scaler_user WHERE qq_openid=#{qqOpenId}")
    UserDo findByOpenId(@Param("qqOpenId") String qqOpenId);

    @Update("update scaler_user set QQ_OPENID =#{qqOpenId} WHERE USER_ID=#{userId}")
    int updateUserOpenId(@Param("qqOpenId") String qqOpenId, @Param("userId") Long userId);


}
