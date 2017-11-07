package com.dgut.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgut.app.AppConstants;
import com.dgut.app.pck.GeneralRestGateway;
import com.dgut.app.pck.GeneralRestGatewayInterface;
import com.dgut.app.service.ApplyService;
import com.dgut.app.service.LRService;
import com.dgut.app.service.MemberService;
import com.dgut.app.service.SNSService;
import com.dgut.app.service.ThumbsService;
import com.dgut.app.utils.JSONUtils;
import com.dgut.main.Constants;
import com.dgut.main.web.CmsUtils;

@Controller
public class AppController implements GeneralRestGatewayInterface {
	private static final Logger log = LoggerFactory
			.getLogger(AppController.class);

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public void getIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "-3");
		jsonMap.put("msg", "请使用post请求");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write(JSONUtils.printObject(jsonMap));
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/index.do", method = RequestMethod.POST)
	public void index(HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		StringBuilder errorDescription = new StringBuilder();
		int code = GeneralRestGateway.handle(Constants.APP_ENCRYPTION_KEY,
				3000, this, request, response, errorDescription);
		if (code < 0) {
			log.error("app请求失败:" + errorDescription.toString());
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("error", "-3");
			jsonMap.put("msg", errorDescription.toString());
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(JSONUtils.printObject(jsonMap));
			out.flush();
			out.close();
		}
	}

	@Override
	public String delegateHandleRequest(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters,
			StringBuilder paramStringBuilder) throws RuntimeException {

		String result = null;
		Integer OPT = Integer.valueOf(parameters.get("opt"));
		
		//需要登录
		if (OPT != AppConstants.APP_REGISTER && OPT != AppConstants.APP_Login&& OPT != AppConstants.APP_Citys
				&& OPT!=AppConstants.APP_ForgetPassword && OPT != AppConstants.APP_SelectInfo && OPT != AppConstants.APP_MemberInfo
				&& OPT != AppConstants.APP_APPLY && OPT != AppConstants.APP_THUMBS_UP) {
			if (CmsUtils.getMember(request) == null) {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("error", -2);
				jsonMap.put("msg", "太久未登录，请重新登录");
				try {
					return JSONUtils.printObject(jsonMap);
				} catch (IOException e) {
				}
			}
		}

		switch (OPT) {
		case AppConstants.APP_SENDCODE:
			try{
			  result = sNSService.sendCode(request, response, parameters);
			}catch(IOException e){
				log.error("发送验证码时：" + e.getMessage());
			}
			break;
		case AppConstants.APP_REGISTER:
			try {
				result = lRService.regist(request, response, parameters);
			} catch (IOException e) {
				log.error("注册时：" + e.getMessage());
			}
			break;
		case AppConstants.APP_Login:
			try {
				result = lRService.login(request, response, parameters);
			} catch (IOException e) {
				log.error("登录时：" + e.getMessage());
			}
			break;
		case AppConstants.APP_ForgetPassword:
			try {
				result = lRService.forgetPwd(request, response, parameters);
			} catch (IOException e) {
				log.error("忘记密码时：" + e.getMessage());
			}
			break;
		case AppConstants.APP_SelectInfo:
			try{
				result = memberService.getSelectInfo(request,response,parameters);
			}catch(IOException e){
				log.error("获取个人设置选项时:" + e.getMessage());
			}
			break;
		case AppConstants.APP_MemberInfo:
			try{
				result = memberService.getMemberInfo(request,response,parameters);
			}catch(IOException e){
				log.error("获取个人信息时:" + e.getMessage());
			}
			break;
		case AppConstants.APP_APPLY:
			try{
				result = applyService.apply(request, response,parameters);
			}catch(IOException e){
				log.error("提起申请时:" + e.getMessage());
			}
			break;
		case AppConstants.APP_Citys:
			try {
				result = memberService.citys(request, response, parameters);
			} catch (IOException e) {
				log.error("城市列表：" + e.getMessage());
			}
			break;
		case AppConstants.APP_THUMBS_UP:
			try{
				result = thumbsService.thumbsUp(request, response,parameters);
			}catch(IOException e){
				log.error("点赞时:" + e.getMessage());
			}
			break;
		
		default:
			break;
		}
		// System.out.println(" 返回结果："+result);
		return result;
	}

	@Autowired
	private LRService lRService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SNSService sNSService;
	
	@Autowired
	private ApplyService applyService;
	
	@Autowired
	private ThumbsService thumbsService;
}
