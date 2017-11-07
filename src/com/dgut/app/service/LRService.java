package com.dgut.app.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;













import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgut.app.utils.Encrypt;
import com.dgut.app.utils.JSONUtils;
import com.dgut.app.utils.LetterUtils;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.DisabledException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.common.web.RequestUtils;
import com.dgut.main.Constants;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberAuthentication;
import com.dgut.member.manager.AssesCategoryMng;
import com.dgut.member.manager.CityMng;
import com.dgut.member.manager.MajorMng;
import com.dgut.member.manager.MemberAuthenticationMng;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.ProvinceMng;
import com.dgut.member.manager.impl.MemberAuthenticationMngImpl;

@Service
public class LRService {
	
	
	/**
	 * 注册 opt = 1
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
			throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
        Member member = CmsUtils.getMember(request);
		String mobile = parameters.get("mobile");
		String code = parameters.get("code");

		Map<String, String> m = sNService.codeVerify(mobile, code);
		if (m.get("error").equals("-3")) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", m.get("msg"));
			return JSONUtils.printObject(jsonMap);
		}
		
		boolean notExist = memberMng.usernameNotExist(mobile);
		if(notExist==false){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "该手机号码已注册");
			memberLogMng.operating(request, "注册失败", "username：" + member.getUsername()+",该手机号码已注册");
			return JSONUtils.printObject(jsonMap);
		}
		String ip = RequestUtils.getIpAddr(request);
		member.setMobile(mobile);
		member = memberMng.registMember(member,ip);
		String easemob_username = LetterUtils.getRandomLetter() + member.getId();
		member.setEasemob_username(easemob_username);
		JSONObject result = easemobService.registUser(easemob_username, member.getUsername(),member.getPassword());
		if(result.getInt("responseStatus")!=200){
	           member.setEasemob_flag(false);
	        }
	        else{
	            member.setEasemob_flag(true);
	    }
	    memberMng.updateMember(member);
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "注册成功");
		jsonMap.put("username", member.getUsername());
		jsonMap.put("uid", Encrypt.encrypt3DES(member.getId() + "",
				Constants.ENCRYPTION_KEY));
		memberLogMng.operating(request, "注册成功", "username=" + member.getUsername());
		return JSONUtils.printObject(jsonMap);
	}
	
	/**
	 * 用户登录(opt=2) 
	 * 登录成功后返回的token时长是2天，也就是2天内没操作需要重新登录
	 * 如果在2天内的时候登录的话，token时长重新计算
	 * 
	 * @param accountNumber
	 *            用户名
	 * @param pwd
	 *            密码
	 * @throws IOException
	 */
	public String login(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
			throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String actNumber = parameters.get("actNumber");
		String password = parameters.get("pwd");

		if (StringUtils.isBlank(actNumber)) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "请输入账号");
			return JSONUtils.printObject(jsonMap);
		}
//		if(!StrUtils.isMobileNum(mobile)){
//			jsonMap.put("error", "-3");
//			jsonMap.put("msg", "请输入正确的手机号码");
//			return JSONUtils.printObject(jsonMap);
//		}

		if (StringUtils.isBlank(password)) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "请输入密码");
			return JSONUtils.printObject(jsonMap);
		}

		password = Encrypt.decrypt3DES(password,Constants.ENCRYPTION_KEY);

		String ip = RequestUtils.getIpAddr(request);
		Member member = null;
		MemberAuthentication auth;
		try {
			auth = memberAuthenMng
					.login(actNumber, password, ip, request, response, session);
			memberMng.updateLoginInfo(auth.getUid(), ip);
			member = memberMng.findById(auth.getUid());
			if (member.getDisabled()) {
				// 如果已经禁用，则退出登录。
				memberAuthenMng.deleteById(auth.getId());
				session.logout(request, response);
				throw new DisabledException("user disabled");
			}
			memberLogMng.loginSuccess(request, member, "登录成功");
			
		} catch (UsernameNotFoundException e) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "用户名不存在");
			memberLogMng.loginFailure(request, "登录失败", "username=" + actNumber);
			return JSONUtils.printObject(jsonMap);
		} catch (BadCredentialsException e) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "用户名或密码错误");
			memberLogMng.loginFailure(request, "登录失败", "password=" + password +",username+" + actNumber);
			return JSONUtils.printObject(jsonMap);
		} catch (DisabledException e) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "账户被禁用，请联系管理员");
			memberLogMng.loginFailure(request, "登录失败", "用户被禁用");
			return JSONUtils.printObject(jsonMap);
		}
		if(member.getMobile() != null){
		   jsonMap.put("error", "-1");
		   jsonMap.put("msg", "登录成功并已注册");
		}else{
			jsonMap.put("error", "-2");
			jsonMap.put("msg", "登录成功但并未注册");
		}
		jsonMap.put("accountNumber", member.getUsername());
//		jsonMap.put("realname", member.getRealname());
		jsonMap.put("uid", Encrypt.encrypt3DES(member.getId() + "",
				Constants.ENCRYPTION_KEY));
//		jsonMap.put("loginCount", member.getLoginCount());
//		jsonMap.put("lastLoginIp", member.getLastLoginIp());
//		jsonMap.put("lastLoginTime",
//				DateUtils.dateToString(member.getLastLoginTime()));
		jsonMap.put("token", auth.getId());
		jsonMap.put("expires_in", MemberAuthenticationMngImpl.timeout/1000);
		return JSONUtils.printObject(jsonMap);
	}
	/**
	 * 忘记密码 opt = 3
	 */
	public String forgetPwd(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
			throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String mobile = parameters.get("mobile");
		String password = parameters.get("pwd");
		String code = parameters.get("code");
		if (StringUtils.isBlank(password)) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "密码不能为空");
			return JSONUtils.printObject(jsonMap);
		}

		password = Encrypt.decrypt3DES(password, Constants.ENCRYPTION_KEY);
		
		Map<String, String> m = sNService.codeVerify(mobile, code);
		if (m.get("error").equals("-3")) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", m.get("msg"));
			return JSONUtils.printObject(jsonMap);
		}
		
		Member member = memberMng.findByUsername(mobile);
		if(member==null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "手机号码未注册");
			return JSONUtils.printObject(jsonMap);
		}
		member = memberMng.updateMember(member, password);
		jsonMap.put("error", -1);
		jsonMap.put("msg", "更新密码成功");
		return JSONUtils.printObject(jsonMap);
	}
	
	/**
	 * 获取个人设置信息选项
	 * @param request
	 * @param response
	 * @param parameters
	 * @return
	 */
	

	
	

	
	@Autowired
	private MemberAuthenticationMng memberAuthenMng;

	@Autowired
	private com.dgut.common.web.session.SessionProvider session;
	
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private MemberLogMng memberLogMng;
	
	@Autowired
	private CityMng cityMng;
	
	@Autowired
	private MajorMng majorMng;
	
	@Autowired
	private ProvinceMng provinceMng;
	
	@Autowired
	private AssesCategoryMng categoryMng;
	
	@Autowired
	private SNSService sNService;

	@Autowired
	private EasemobService easemobService;

	
}
