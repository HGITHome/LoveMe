package com.dgut.main.action.main;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.DisabledException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.common.web.CookieUtils;
import com.dgut.common.web.RequestUtils;
import com.dgut.common.web.session.SessionProvider;
import com.dgut.core.entity.Config.ConfigLogin;
import com.dgut.core.manager.ConfigMng;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.Admin;
import com.dgut.main.entity.Authentication;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.AdminMng;
import com.dgut.main.manager.AuthenticationMng;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import static com.dgut.main.manager.AuthenticationMng.AUTH_KEY;;

@Controller
public class AdminLoginAct {

	public static final String PROCESS_URL = "processUrl";
	public static final String RETURN_URL = "returnUrl";
	public static final String MESSAGE = "message";
	
	
	/**
	 *使用指定类初始化日志对象
                    在日志输出的时候，可以打印出日志信息所在类
                    如：Logger logger = LoggerFactory.getLogger(com.Book.class);
      logger.debug("日志信息");
                    将会打印出: com.Book : 日志信息
	 */
	private static final Logger log = LoggerFactory
			.getLogger(AdminLoginAct.class);

	public static final String COOKIE_ERROR_REMAINING = "_error_remaining";

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String input(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String processUrl = RequestUtils.getQueryParam(request, PROCESS_URL);
		String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);
		String message = RequestUtils.getQueryParam(request, MESSAGE);
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId != null) {
			// 存在认证ID
			Authentication auth = authMng.retrieve(authId);
			// 存在认证信息，且未过期
			if (auth != null) {
				String view = getView(processUrl, returnUrl, auth.getId());
				if (view != null) {
					return view;
				} else {
					model.addAttribute("auth", auth);
					return "login";
				}
			}
		}
		writeCookieErrorRemaining(null, request, response, model);
		if (!StringUtils.isBlank(processUrl)) {
			model.addAttribute(PROCESS_URL, processUrl);
		}
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute(RETURN_URL, returnUrl);
		}
		if (!StringUtils.isBlank(message)) {
			model.addAttribute(MESSAGE, message);
		}
		return "login";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String submit(String username, String password, String captcha,
			String processUrl, String returnUrl, String message,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		Integer errorRemaining = adminMng.errorRemaining(username);
		WebErrors errors = validateSubmit(username, password, captcha,
				errorRemaining, request, response);
		if (!errors.hasErrors()) {
			try {
				String ip = RequestUtils.getIpAddr(request);
				Authentication auth = authMng.login(username, password, ip,
						request, response, session);
				// 是否需要在这里加上登录次数的更新？按正常的方式，应该在process里面处理的，不过这里处理也没大问题。
				adminMng.updateLoginInfo(auth.getUid(), ip);
				Admin user = adminMng.findById(auth.getUid());
				if (user.getDisabled()) {
					// 如果已经禁用，则退出登录。
					authMng.deleteById(auth.getId());
					session.logout(request, response);
					throw new DisabledException("user disabled");
				}
				removeCookieErrorRemaining(request, response);
				String view = getView(processUrl, returnUrl, auth.getId());
				adminLogMng.loginSuccess(request, user, "登录成功");
				if (view != null) {
					return view;
				} else {
					return "redirect:login.jspx";
				}
			} catch (UsernameNotFoundException e) {
				errors.addErrorString(e.getMessage());
				adminLogMng.loginFailure(request, "登录失败",
						"username=" + username + ";password=" + password);
			} catch (BadCredentialsException e) {
				errors.addErrorString(e.getMessage());
				adminLogMng.loginFailure(request, "登录失败",
						"username=" + username + ";password=" + password);
			} catch (DisabledException e) {
				errors.addErrorString(e.getMessage());
				adminLogMng.loginFailure(request, "登录失败",
						"username=" + username + ";password=" + password);
			}
		}
		// 登录失败
		writeCookieErrorRemaining(errorRemaining, request, response, model);
		errors.toModel(model);
		if (!StringUtils.isBlank(processUrl)) {
			model.addAttribute(PROCESS_URL, processUrl);
		}
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute(RETURN_URL, returnUrl);
		}
		if (!StringUtils.isBlank(message)) {
			model.addAttribute(MESSAGE, message);
		}
		return "login";
	}

	@RequestMapping(value = "/logout.do")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId != null) {
			authMng.deleteById(authId);
			session.logout(request, response);
		}
		String processUrl = RequestUtils.getQueryParam(request, PROCESS_URL);
		String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);
		String view = getView(processUrl, returnUrl, authId);
		if (view != null) {
			return view;
		} else {
			return "redirect:login.jspx";
		}
	}

	private WebErrors validateSubmit(String username, String password,
			String captcha, Integer errorRemaining, HttpServletRequest request,
			HttpServletResponse response) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifOutOfLength(username, "username", 1, 100)) {
			return errors;
		}
		if (errors.ifOutOfLength(password, "password", 1, 32)) {
			return errors;
		}
		// 如果输入了验证码，那么必须验证；如果没有输入验证码，则根据当前用户判断是否需要验证码。
		if (!StringUtils.isBlank(captcha)
				|| (errorRemaining != null && errorRemaining < 0)) {
			if (errors.ifBlank(captcha, "captcha", 100)) {
				return errors;
			}
			try {
				if (!imageCaptchaService.validateResponseForID(session
						.getSessionId(request, response), captcha)) {
					errors.addErrorCode("error.invalidCaptcha");
					return errors;
				}
			} catch (CaptchaServiceException e) {
				errors.addErrorCode("error.exceptionCaptcha");
				log.warn("", e);
				return errors;
			}
		}
		return errors;
	}

	/**
	 * 获得地址
	 * 
	 * @param processUrl
	 * @param returnUrl
	 * @param authId
	 * @param defaultUrl
	 * @return
	 */
	private String getView(String processUrl, String returnUrl, String authId) {
		if (!StringUtils.isBlank(processUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(processUrl).append("?").append(AUTH_KEY).append("=")
					.append(authId);
			if (!StringUtils.isBlank(returnUrl)) {
				sb.append("&").append(RETURN_URL).append("=").append(returnUrl);
			}
			return sb.toString();
		} else if (!StringUtils.isBlank(returnUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(returnUrl);
			return sb.toString();
		} else {
			return null;
		}
	}

	private void writeCookieErrorRemaining(Integer userErrorRemaining,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		// 所有访问的页面都需要写一个cookie，这样可以判断已经登录了几次。
		Integer errorRemaining = getCookieErrorRemaining(request, response);
		ConfigLogin configLogin = configMng.getConfigLogin();
		Integer errorInterval = configLogin.getErrorInterval();
		if (userErrorRemaining != null
				&& (errorRemaining == null || userErrorRemaining < errorRemaining)) {
			errorRemaining = userErrorRemaining;
		}
		int maxErrorTimes = configLogin.getErrorTimes();
		if (errorRemaining == null || errorRemaining > maxErrorTimes) {
			errorRemaining = maxErrorTimes;
		} else if (errorRemaining <= 0) {
			errorRemaining = 0;
		} else {
			errorRemaining--;
		}
		model.addAttribute("errorRemaining", errorRemaining);
		CookieUtils.addCookie(request, response, COOKIE_ERROR_REMAINING,
				errorRemaining.toString(), errorInterval * 60, null);
	}

	private void removeCookieErrorRemaining(HttpServletRequest request,
			HttpServletResponse response) {
		CookieUtils.cancleCookie(request, response, COOKIE_ERROR_REMAINING,
				null);
	}

	private Integer getCookieErrorRemaining(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookie = CookieUtils.getCookie(request, COOKIE_ERROR_REMAINING);
		if (cookie != null) {
			String value = cookie.getValue();
			if (NumberUtils.isDigits(value)) {
				return Integer.parseInt(value);
			}
		}
		return null;
	}

	@Autowired
	private AdminMng adminMng;
	@Autowired
	private ConfigMng configMng;
	@Autowired
	private AuthenticationMng authMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private AdminLogMng adminLogMng;
}
