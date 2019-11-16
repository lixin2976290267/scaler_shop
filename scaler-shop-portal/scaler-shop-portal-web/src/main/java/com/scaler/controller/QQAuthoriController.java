package com.scaler.controller;

import com.alibaba.fastjson.JSONObject;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.scaler.base.BaseResponse;
import com.scaler.constants.Constants;
import com.scaler.core.bean.ScalerBeanUtils;
import com.scaler.feign.MemberLoginServiceFeign;
import com.scaler.feign.QQAuthoriFeign;
import com.scaler.member.input.dto.UserLoginInpDTO;
import com.scaler.web.base.BaseWebController;
import com.scaler.web.constants.WebConstants;
import com.scaler.web.utils.CookieUtils;
import com.scaler.web.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName QQAuthoriController
 * @Description TODO
 * @Author Not a coconut
 * @Date 2019/8/22 21:56
 * @Version 1.0
 **/
@Slf4j
@Controller
public class QQAuthoriController extends BaseWebController {
    private static final String MB_QQ_QQLOGIN = "member/qqlogin";

    /**
     * 重定向到首页
     */
    private static final String REDIRECT_INDEX = "redirect:/";

    @Autowired
    private QQAuthoriFeign qqAuthoriFeign;

    @Autowired
    private MemberLoginServiceFeign memberLoginServiceFeign;
    //1.生成授权链接
    /**
     * 生成QQ授权回调地址
     *
     * @return
     */
    @RequestMapping("/qqAuth")
    public String qqAuth(HttpServletRequest request) {
        try {
            String authorizeURL = new Oauth().getAuthorizeURL(request);
            log.info("AuthorizeURL：{}",authorizeURL);
            return "redirect:" + authorizeURL;
        } catch (Exception e) {
            return ERROR_500_FTL;
        }
    }

    /**
     *
     * @param code
     * @return
     */
    @RequestMapping("/qqLoginBack")
    public String qqLoginBack(String code, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        try {
            // 使用授权码获取accessToken
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            if (accessTokenObj == null) {
                return ERROR_500_FTL;
            }
            String accessToken = accessTokenObj.getAccessToken();
            if (StringUtils.isEmpty(accessToken)) {
                return ERROR_500_FTL;
            }
            // 使用accessToken获取用户openid
            OpenID openIDObj = new OpenID(accessToken);
            String openId = openIDObj.getUserOpenID();
            if (StringUtils.isEmpty(openId)) {
                return ERROR_500_FTL;
            }
            // 使用openid 查询数据库是否已经关联账号信息
            BaseResponse<JSONObject> findByOpenId = qqAuthoriFeign.findByOpenId(openId);
            if (!isSuccess(findByOpenId)) {
                return ERROR_500_FTL;
            }
            // 如果调用接口返回203 ,跳转到关联账号页面
            if (findByOpenId.getCode().equals(Constants.HTTP_RES_CODE_NOTUSER_203)) {
                // 页面需要展示 QQ头像
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openId);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                if (userInfoBean == null) {
                    return ERROR_500_FTL;
                }
                // 用户的QQ头像
                String avatarURL100 = userInfoBean.getAvatar().getAvatarURL100();
                httpSession.setAttribute("avatarURL100", avatarURL100);
                // 需要将openid存入在session中
                httpSession.setAttribute(WebConstants.LOGIN_QQ_OPENID, openId);
                return MB_QQ_QQLOGIN;
            }
            // 如果能够查询到用户信息的话,直接自动登陆
            // 自动实现登陆
            JSONObject data = findByOpenId.getData();
            String token = data.getString("token");
            CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIENAME, token);
            return REDIRECT_INDEX;

        } catch (Exception e) {
            return ERROR_500_FTL;
        }

    }
    //绑定手机号
    @RequestMapping("/qqJointLogin")
    public String qqJointLogin(@ModelAttribute("loginVo") LoginVo loginVo, Model model, HttpServletRequest request,
                               HttpServletResponse response, HttpSession httpSession) {

        // 1.获取用户openid
        String qqOpenId = (String) httpSession.getAttribute(WebConstants.LOGIN_QQ_OPENID);
        if (StringUtils.isEmpty(qqOpenId)) {
            return ERROR_500_FTL;
        }

        // 2.将vo转换dto调用会员登陆接口
        UserLoginInpDTO userLoginInpDTO = ScalerBeanUtils.voToDto(loginVo, UserLoginInpDTO.class);
        userLoginInpDTO.setQqOpenId(qqOpenId);
        userLoginInpDTO.setLoginType(Constants.MEMBER_LOGIN_TYPE_PC);
        String info = webBrowserInfo(request);
        userLoginInpDTO.setDeviceInfor(info);
        BaseResponse<JSONObject> login = memberLoginServiceFeign.login(userLoginInpDTO);
        if (!isSuccess(login)) {
            setErrorMsg(model, login.getMsg());
            return MB_QQ_QQLOGIN;
        }
        // 3.登陆成功之后如何处理 保持会话信息 将token存入到cookie 里面 首页读取cookietoken 查询用户信息返回到页面展示
        JSONObject data = login.getData();
        String token = data.getString("token");
        CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIENAME, token);
        return REDIRECT_INDEX;
    }


}
