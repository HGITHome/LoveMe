package com.dgut.app.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.dgut.app.SNSConstants;
import com.dgut.app.utils.JSONUtils;
import com.dgut.common.sns.spi.SmsVerifyKit;
import com.dgut.common.util.StrUtils;

@Service
public class SNSService {
	/**
	 * 发送验证码
	 * @param request
	 * @param response
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public String sendCode(HttpServletRequest request,HttpServletResponse response,Map<String, String> parameters) throws IOException  {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		String mobile = parameters.get("mobile");
		if(StringUtils.isBlank(mobile)){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "手机号码为空");
			return JSONUtils.printObject(jsonMap);
		}
		if(!StrUtils.isMobileNum(mobile)){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "请填写有效的手机号码");
			return JSONUtils.printObject(jsonMap);
		}
	 try{
		SmsVerifyKit smsVerifyKit = new SmsVerifyKit(SNSConstants.APP_SNS_KEY, mobile, "86");
		String result = smsVerifyKit.SemdCode();
		JSONObject object = JSONObject.fromObject(result);
        Integer code= (Integer) object.get("code");
        if (code==200) {
            jsonMap.put("error", "-1");
            jsonMap.put("msg", "申请验证码成功");
        } else if (code==403) {
            jsonMap.put("error", "-3");
            jsonMap.put("msg", "非法操作");
        } else if (code==414) {
            jsonMap.put("error", "-3");
            jsonMap.put("msg", "参数错误");
        } else if (code==416) {
            jsonMap.put("error", "-3");
            jsonMap.put("msg", "请求校验验证码频繁");
        } else if (code==500) {
            jsonMap.put("error", "-3");
            jsonMap.put("msg", "服务器内部错误");
        }

        else {
            jsonMap.put("error", "-3");
            jsonMap.put("msg", "code:" + code);
        }
    } catch (Exception e) {
        jsonMap.put("error", "-3");
        jsonMap.put("msg", "请求失败" + e.getLocalizedMessage());
    }
		return JSONUtils.printObject(jsonMap);
	}

	/**
	 * 手机验证码验证
	 * @return
	 */
	public Map<String,String> codeVerify(String mobile,String code)
	{
		Map<String, String> jsonMap = new HashMap<String, String>();
		if(StringUtils.isBlank(mobile)){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "手机号码为空");
			return jsonMap;
		}
		if (!StrUtils.isMobileNum(mobile)) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "手机号码错误");
			return jsonMap;
		}
		if (StringUtils.isBlank(code)) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "验证码不能为空");
			return jsonMap;
		}
		try {
			SmsVerifyKit smsVerifyKit = new SmsVerifyKit(SNSConstants.APP_SNS_KEY,mobile, "86", code);
			String result = smsVerifyKit.checkCode();
			JSONObject object = JSONObject.fromObject(result);
	        Integer c= (Integer) object.get("code");
			if (c.equals(200)) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "验证成功");
			}else if(c.equals(468)){
				jsonMap.put("error", "-3");
				jsonMap.put("msg", "验证码错误");
			}else if(c.equals(467)){
				jsonMap.put("error", "-3");
				jsonMap.put("msg", "请求校验验证码频繁");
			}else {
				jsonMap.put("error", "-3");
				jsonMap.put("msg", "code:"+c);
			}
		} catch (Exception e) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "请求失败"+e.getLocalizedMessage());
		}
		
		return jsonMap;
	}
}
