package com.dgut.common.sns.spi;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.dgut.app.SNSConstants;
import com.dgut.common.sns.utils.MobClient;


/**
 * 服务端发起验证请求验证移动端(手机)发送的短信
 * @author Administrator
 *
 */
public class SmsVerifyKit {
	
	private String appkey;
	private String phone ;
	private String zone ;
	private String code ;

	/**
	 * 
	 * @param appkey 应用key
	 * @param phone 电话号码xxxxx
	 * @param zone 区号86
	 */
	public SmsVerifyKit(String appkey,String phone,String zone){
		super();
		this.appkey = appkey;
		this.phone = phone;
		this.zone = zone;
	}
	
	/**
	 * 
	 * @param appkey 应用KEY
	 * @param phone 电话号码 xxxxxxxxx
	 * @param zone 区号 86
	 * @param code 验证码 xx
	 */
	public SmsVerifyKit(String appkey, String phone, String zone, String code) {
		super();
		this.appkey = appkey;
		this.phone = phone;
		this.zone = zone;
		this.code = code;
	}
	/**
	 * 服务器端发起验证码短信请求
	 * @return
	 * @throws Exception
	 */
	
	public String SemdCode() throws Exception{
		String address = "https://api.netease.im/sms/sendcode.action";
		MobClient client = null;
		try{
			String nonce = "asdhauisdasd";
			String time = System.currentTimeMillis()/1000+"";
			client = new MobClient(address);
			client.addParam("mobile", phone).addParam("templateid", SNSConstants.APP_SNS_TEMPLATEID).addParam("codeLen", SNSConstants.APP_SNS_CODELEN);
			client.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			client.addRequestProperty("AppKey", appkey);
			client.addRequestProperty("Nonce", nonce);
			client.addRequestProperty("CurTime",time);
			client.addRequestProperty("CheckSum", CheckSumBuilder.getCheckSum(SNSConstants.APP_SNS_Secret, nonce, time));

			String result = client.post();
	        return result;
		}finally{
			client.release();
		}
				
	}
	
	
	/**
	 * 服务端发起验证码验证
	 * @return
	 * @throws Exception
	 */
	public  String checkCode() throws Exception{
		
		String address = "https://api.netease.im/sms/verifycode.action";
		MobClient client = null;
		try{
			String nonce = "asdhauisdasd";
			String time = System.currentTimeMillis()/1000+"";
			client = new MobClient(address);
			client.addParam("mobile", phone).addParam("code", code);
			client.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			client.addRequestProperty("AppKey", appkey);
			client.addRequestProperty("Nonce", nonce);
			client.addRequestProperty("CurTime",time);
			client.addRequestProperty("CheckSum", CheckSumBuilder.getCheckSum(SNSConstants.APP_SNS_Secret, nonce, time));

			String result = client.post();
	        return result;
		}finally{
			client.release();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
//		String result = new SmsVerifyKit(SNSConstants.APP_SNS_KEY, "13631789659", "86").SemdCode();
		String re = new SmsVerifyKit(SNSConstants.APP_SNS_KEY, "13631789659", "86","9580").checkCode();
		System.out.println(re);
		
	}

}
