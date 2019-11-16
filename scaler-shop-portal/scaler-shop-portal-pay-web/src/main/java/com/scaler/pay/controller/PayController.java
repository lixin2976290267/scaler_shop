package com.scaler.pay.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.scaler.base.BaseResponse;
import com.scaler.pay.feign.PayContextFeign;
import com.scaler.pay.feign.PayMentTransacInfoFeign;
import com.scaler.pay.feign.PaymentChannelFeign;
import com.scaler.pay.out.dto.PayMentTransacDTO;
import com.scaler.pay.out.dto.PaymentChannelDTO;
import com.scaler.web.base.BaseWebController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @description: 支付网站
 */
@Controller
public class PayController extends BaseWebController {
    @Autowired
    private PayContextFeign payContextFeign;
    @Autowired
    private PayMentTransacInfoFeign payMentTransacInfoFeign;
    @Autowired
    private PaymentChannelFeign paymentChannelFeign;

    @RequestMapping("/pay")
    public String pay(String payToken, Model model) {
        // 1.验证payToken参数
        if (StringUtils.isEmpty(payToken)) {
            setErrorMsg(model, "支付令牌不能为空!");
            return ERROR_500_FTL;
        }
        // 2.使用payToken查询支付信息
        BaseResponse<PayMentTransacDTO> tokenByPayMentTransac = payMentTransacInfoFeign.tokenByPayMentTransac(payToken);
        if (!isSuccess(tokenByPayMentTransac)) {
            setErrorMsg(model, tokenByPayMentTransac.getMsg());
            return ERROR_500_FTL;
        }
        // 3.查询支付信息
        PayMentTransacDTO data = tokenByPayMentTransac.getData();
        model.addAttribute("data", data);
        // 4.查询渠道信息
		List<PaymentChannelDTO> paymentChanneList = paymentChannelFeign.selectAll();
		model.addAttribute("paymentChanneList", paymentChanneList);
        model.addAttribute("payToken", payToken);
        return "index";
    }

    /**
     * @param payToken
     * @return
     * @throws IOException
     */
    @RequestMapping("/payHtml")
    public void payHtml(String channelId, String payToken, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        BaseResponse<JSONObject> payHtmlData = payContextFeign.toPayHtml(channelId, payToken);
        if (isSuccess(payHtmlData)) {
            JSONObject data = payHtmlData.getData();
            String payHtml = data.getString("payHtml");
            response.getWriter().print(payHtml);
        }

    }

    @RequestMapping("/callBackResult")
    public String success(Map<String, Object> map){
        map.put("success","成功");
        return "success";
    }

}
