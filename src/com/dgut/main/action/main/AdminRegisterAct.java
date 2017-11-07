package com.dgut.main.action.main;



import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgut.common.web.RequestUtils;
import com.dgut.common.web.session.SessionProvider;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.Admin;
import com.dgut.main.entity.RegisterUser;
import com.dgut.main.manager.AdminMng;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

@Controller
public class AdminRegisterAct {
	
	private final static Logger log = LoggerFactory.getLogger(AdminRegisterAct.class);
	
	
	@RequestMapping(value="/register.do",method=RequestMethod.GET)
	public String input(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		log.debug("跳转登录界面!");
		model.addAttribute("user",new RegisterUser());
		return "register";
	}
	@RequestMapping(value="/register.do", method=RequestMethod.POST)
	public String submit(HttpServletRequest request,HttpServletResponse response,RegisterUser user,ModelMap model){
		WebErrors errors = validateSubmit(user, request, response);
		if(errors.hasErrors()){
			model.addAttribute("user", user);
				errors.toModel(model);		
			}else{
				log.debug("注册成功!");
				String ip = RequestUtils.getIpAddr(request);
				adminMng.saveAdmin(user.getUserName(), user.getEmail(), user.getPassword(), ip, 10, null, user.getRealName(), 1);
				return "login";
			}
		return "register";
	}
	private WebErrors validateSubmit(RegisterUser user,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		WebErrors errors = WebErrors.create(request);
		if (errors.ifOutOfLength(user.getUserName(), "userName",1,100)) {
			
			
		}else if(errors.ifReUserName(user.getUserName(),adminMng)){
			errors.addErrorCode("error.ReUserName","username");
	    	
	     }
		if (errors.ifOutOfLength(user.getRealName(), "realName", 1, 100)) {
			
		}
		if(errors.ifTheSame(user.getPassword(),user.getPassword1())){
			errors.addErrorCode("error.theSamePassword", "password");
			
		}
		if(errors.ifNotGender(user.getGender())){
			errors.addErrorCode("error.requiredGender", "gender");
			
		}
				
	  
	    if(errors.ifNotEmail(user.getEmail(), "email", 100)){
	    	 
			  
		}else if(errors.ifReEmail(user.getEmail(),adminMng)){
	    	 errors.addErrorCode("error.ReEmail","email");
	    	 
	     }
	    if (errors.ifCaptchaBlank(user.getCaptcha(), "captcha")) {
			
		}else{
		try {
			if (!imageCaptchaService.validateResponseForID(session
					.getSessionId(request, response), user.getCaptcha())) {
				errors.addErrorCode("error.invalidCaptcha");
				
			}
		} catch (CaptchaServiceException e) {
			errors.addErrorCode("error.exceptionCaptcha");
			log.warn("", e);
			return errors;
		}
	}
	    
	    
	 
	 return errors;
	}
	@Autowired
	private AdminMng adminMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
}
