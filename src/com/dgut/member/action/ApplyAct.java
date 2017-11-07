package com.dgut.member.action;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dgut.common.page.SimplePage.cpn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.util.DateUtils;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.member.entity.ApplyLover;
import com.dgut.member.entity.Member;
import com.dgut.member.manager.ApplyLoverMng;
import com.dgut.member.manager.MemberMng;

@Controller
@RequestMapping("member/apply")
public class ApplyAct {
	@RequestMapping("v_list.do")
	public String applyList(String queryUsername,String queryStatus,HttpServletRequest request,HttpServletResponse response,Integer pageNo,ModelMap model){
		Pagination pagination = applyLoverMng.getList(queryUsername, queryStatus, cpn(pageNo),CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryStatus",queryStatus);
		return "member/apply/list";
	}

	@RequestMapping("o_info.do")
	public String applyInfo(HttpServletRequest request,HttpServletResponse response,Integer id,ModelMap model){
		ApplyLover applyInfo = applyLoverMng.findById(id);
		model.addAttribute("applyInfo", applyInfo);
		return "member/apply/info";
	}
	
	@RequestMapping("v_apply.do")
	public String applySave(HttpServletRequest request,HttpServletResponse response,Integer publisherId,Integer receiverId,ModelMap model){
		WebErrors errors = validateId(request,publisherId,receiverId);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Member publisher = memberMng.findById(publisherId);
		Member receiver = memberMng.findById(receiverId);
		try {
		   applyLoverMng.save(publisher,receiver,request.getParameter("apply_reason"));
		} catch (ParseException e) {
			errors.addErrorString("日期解析出错");
			return errors.showErrorPage(model);
		}
		return "redirect:v_list.do";
	}
	private WebErrors validateId(HttpServletRequest request,Integer publisherId, Integer receiverId) {
		WebErrors errors = WebErrors.create(request);
		if(errors.ifNull(publisherId, "id") || errors.ifNull(receiverId, "id")){
			return errors;
		}
		return errors;
	}
	@Autowired
	private ApplyLoverMng applyLoverMng;
	
	@Autowired
	private MemberMng memberMng;
}
