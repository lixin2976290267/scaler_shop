package com.scaler.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101400684897";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCRMSrMD6aN/u2uWlFa8Xsz3sBJjxmBbDYzCLRxPhzl03Dvx8QkQZJQ/ji9Hbj4uSKYpOXlQMWdoFJalAU7R3HgThb2w/+r8yHdVNI40GXj+8hWI5Cckpk1G3zd4MrnEfb0CwF1ZnkiLKet2rAT/tMhCiCJoLmafBi7djkEGg6klIBARLHx4F2f2CM7484IXGr/9kcTq2YBpGVhD16tjxPCDYlVdcPW45mwUp4ZM4rE38X4Ai2pQrDo5/0yVls6LP0TSigAZ65AR09R9oT1YeCkBjkIEUPX19RQTrBcklKKxdHbzJwrzx6NuZeeBmS6QIeOPMwURTOHGISlbS56fp0XAgMBAAECggEAc1W3RXp5N/r2G7YrQi7pS377CHPgMuse8tNLY1mP4xFfeVoa+SINdME9mRzvPsTyModrB/23/UrJoKJMtZY/Y4skuslfY9wzXnputCr3hW8Q6Tw/N+FMSt+XBTn4dC3VGm9TrUrFltwaJwS9MmUOmgRNbIZ8abeCfgefqx5320vWM06nvxVIrhEZlPLHbBTW4Dyxjs5z/tmaCm6cp6JTTMRSSqr20gb3+JtNUP75XqJNwtCzjaUCKSTZvOP1FXl06eNRhPGS7p9IAoBOBGAVtH3QRRSAq1Re26O8niJ8Csfi3bVLs4ADuCw9Y/AM+iUDM9M6o3/eW9Zj55y0KT/QAQKBgQDfGi023msE3MtlaMxeUxE3r8+Wr8JGvobmv5b9O84BOnapvThq0T6g5ExDgwDWnLda7qgdf7szNpr0TpKDVHeuyorTqC9NhY9jA4RpoKd3ap7R3P5QnHSDuPNtvJnuJ+pketzciRXQ5NkZIvSEuaH56h+QT+jJlE7WvNrcj3KPFwKBgQCmmfsaJ8CalGJ7qmSyX8Tcs9a+Ll6xesrCh7MobkcWMVH+vJU5aHm1ZCw/a+gvAV76y9Uz8wqrKXvkq10ohBhZqAumheC7DR+Mn+psWMUPzaf5fvYL/zGeXWWlAMnc4EBTA3c51sprdU2sIg7ISiyFnJutYo0lYa5wLOaa+y0iAQKBgDls/nhiFb/hqUUIDZq13PetfEx4HxSglA6vAAlcrHDqgIK+Xtg31dwKB4tVHBEhs5xnU8SMDT5obsg1tUaypj/KiKNkZhCZMUg2Lo4VuSVmv0ybiqZrLx58q8PySNjhU1bbvR/S9VeYoDz1H2VOdt/iII+/0daZIxXo9JjTH5SvAoGBAIstZa0i+m7oeSFwZnDxOodZoZL9L9Q6jXpXXN7wyWgLycDyEAOGeO6FAcX1wmDKV2tSrwiYCBiXLk4IrmlzeNglX548h1IcE3gp+++JdQ7PsnzmiGvbQ7tVsN1YPErN+E6hPY/PFQkSVNHxHcJK3Mi93PvKsblB0jUZ/YgJP1oBAoGAV1rq8/cdFkiUxrciYfpFpyKTkEp1eA8arVBoQ0N2FgRraakaXsqc+B1D0Tvo1WiNcG3iZRKK1NBBRd2MQB8jR64Q7/kdllg0BHTwdHVGSsEf5WNLFkH1/A2rNvWV72g3tM9ySLq36bOxhNfzQFwIluanFDlteZdSTbH1uDUn6Jc=";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAueZfE1FP0la3mFOBt4KbD05k898PGNBWlk+KAa57vROlGaJev4izDd4vXcqJfSCb1zKAO011HXOJxizxHQ2nHRK1CAfPRjW5ERO23QdTdkjjS7mtdTjAoKu6Ildogjo3SLP2MkMmPqEgGzLpTk0QYup+HOuoSb1FnSbEyT97CCScdnWgG6IzgYyDpJKj7SiBdUM6sROXaZFQO8TDiw03SzI8amaJDIrYsK8oFrICq3lsQWy2XEtk0VqHVI2/OIuDlgIJfVoY5jYzmIpQKYAx6V6MsLISAb0w3ZwzQifHLvqRTOgliaEFquvyVN1VBpw1HLhbrSlP8zVDCfg+ht0MQwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://jscj9c.natappfree.cc/aliPayAsynCallback";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://127.0.0.1:8082/callBackResult";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "\t\n" +
            "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

