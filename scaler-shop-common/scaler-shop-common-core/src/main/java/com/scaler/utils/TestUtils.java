package com.scaler.utils;

import java.util.HashMap;
import java.util.Map;

import com.scaler.core.sign.SignUtil;

public class TestUtils {

	public static void main(String[] args) {
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("payAmount", "300222");
		sParaTemp.put("orderId", "2019010203501502");
		sParaTemp.put("userId", "644064");
		String reuslt = HttpClientUtils.doPost("http://127.0.0.1/api-pay/cratePayToken", SignUtil.sign(sParaTemp));
		System.out.println("reusltL:" + reuslt);
	}
}
