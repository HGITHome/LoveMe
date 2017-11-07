package com.dgut.member.action;

import static com.dgut.common.page.SimplePage.cpn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberLog;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;

@Controller
@RequestMapping("member/log")
public class MemberLogAct {
	
	private static Logger log = LoggerFactory.getLogger(MemberLogAct.class);
	

	@RequestMapping("v_list.do")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryUsername,Integer pageNo){
		Pagination pagination = memberMng.getPage(queryUsername,null,null,cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pagination", pagination);
		return "member/log/list";
	}
	
	
	@RequestMapping("v_list_operating.do")
	public String operatLog(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryUsername,String queryTitle,String queryIp,Integer pageNo){
		Pagination pagination = memberLogMng.getPage(MemberLog.OPERATING , queryUsername, queryTitle, queryIp, cpn(pageNo),CookieUtils.getPageSize(request));
		
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryIp", queryIp);
		model.addAttribute("pagination", pagination);
		return "member/log/list_operating";
	}
	
	@RequestMapping("v_list_login_success.do")
	public String successLog(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryUsername,String queryTitle,String queryIp,Integer pageNo){
		Pagination pagination = memberLogMng.getPage(MemberLog.LOGIN_SUCCESS , queryUsername, queryTitle, queryIp, cpn(pageNo),CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryIp", queryIp);
		model.addAttribute("pagination", pagination);
		return "member/log/list_login_success";
	}
	
	@RequestMapping("v_list_login_failure.do")
	public String failureLog(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryUsername,String queryTitle,String queryIp,Integer pageNo){
		Pagination pagination = memberLogMng.getPage(MemberLog.LOGIN_FAILURE , queryUsername, queryTitle, queryIp, cpn(pageNo),CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryIp", queryIp);
		model.addAttribute("pagination", pagination);
		return "member/log/list_login_failure";
	}
	@RequestMapping("o_edit.do")
	public String edit(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer id){
		WebErrors errors = validateSubmit(request,id);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Member member = memberMng.findById(id);
		model.addAttribute("cmsMember", member);
		return "member/log/edit";
	}
	
	@RequestMapping("o_update.do")
	public String save(HttpServletRequest request,HttpServletResponse response,ModelMap model,String psd,Member bean){
		WebErrors errors = validateSubmit(request,bean.getId());
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		bean = memberMng.updateMember(bean, psd);
		 log.info("update Member id={}.", bean.getId());
	        memberLogMng.operating(request, "cms.member.update", "id=" + bean.getId()
	                + ";username=" + bean.getUsername());
		return "redirect:v_list.do";
	}

	
	private WebErrors validateSubmit(HttpServletRequest request, Integer id) {
		WebErrors errors = WebErrors.create(request);
		if(errors.ifNull(id, "id")){
			return errors;
		}
		return errors;
	}
	
	
	@Autowired
	private MemberLogMng memberLogMng;
	
	@Autowired
	private MemberMng memberMng;
}
