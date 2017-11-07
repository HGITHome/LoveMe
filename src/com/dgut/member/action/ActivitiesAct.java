package com.dgut.member.action;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dgut.common.page.SimplePage.cpn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.common.web.ResponseUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.Admin;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Activity;
import com.dgut.member.manager.ActivityMng;

@Controller
@RequestMapping("member/activity")
public class ActivitiesAct {
	
	@RequestMapping("v_list.do")
	public String activityList(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer pageNo,String queryAdminName,String queryActivityName,String queryActivityAddress){
		Pagination pagination = activityMng.getPage(queryAdminName, queryActivityName, queryActivityAddress, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryAdminName", queryAdminName);
		model.addAttribute("queryActivityName", queryActivityName);
		model.addAttribute("queryActivityAddress", queryActivityAddress);
		model.addAttribute("pagination", pagination);
		return "member/activity/list";
	}
	
	@RequestMapping("v_add.do")
	public String activityAdd(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		Admin admin = CmsUtils.getAdmin(request);
		model.addAttribute("admin", admin);
		return "member/activity/add";
	}
	
	@RequestMapping("/v_checkActivityName.do")
	public void checkActivityName(String queryActivityName,HttpServletRequest request,HttpServletResponse response){
		Activity activity = activityMng.findByActivityName(queryActivityName);
		boolean pass = activity == null;
		ResponseUtils.renderJson(response, pass?"true":"false");
	}
	
	@RequestMapping("o_save.do")
	public 	String activitySave(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		WebErrors errors = WebErrors.create(request);
		Admin admin = CmsUtils.getAdmin(request);
		String queryActivityName = request.getParameter("queryActivityName");
		String days = request.getParameter("day");
		String queryAddress = request.getParameter("queryAddress");
		String activityContent = request.getParameter("content");
		activityMng.save(admin,queryActivityName,queryAddress,days,activityContent);
		adminLogMng.operating(request, "添加活动", "activityName = " + queryActivityName);
		return "redirect:v_list.do";
	}
	
	@RequestMapping("o_info.do")
	public String  o_info(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer id){
		Admin admin = CmsUtils.getAdmin(request);
		WebErrors errors = validateId(request,id);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Activity activity = activityMng.findById(id);
		model.addAttribute("activity", activity);
		model.addAttribute("admin", admin);
		return "member/activity/info";
	}
	
	@RequestMapping("v_edit.do")
	public String o_edit(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer id){
		Admin admin = CmsUtils.getAdmin(request);
		WebErrors errors = validateId(request,id);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Activity activity = activityMng.findById(id);
		if(activity == null){
			errors.addErrorString("该活动不存在!");
			return errors.showErrorPage(model);
		}
		model.addAttribute("admin", admin);
		model.addAttribute("activity", activity);
		return "member/activity/edit";
	}
	
	@RequestMapping("v_update.do")
	public String update(HttpServletRequest request,HttpServletResponse response,ModelMap model,Activity activity,Integer id){
		activityMng.update(activity);
		adminLogMng.operating(request, "更新活动", "activity.id=" + id);
		return "redirect:v_list.do";
	}
	
	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request,HttpServletResponse response,Integer id,ModelMap model){
		WebErrors errors = validateId(request,id);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Activity activity = activityMng.deleteById(id);
		if(activity == null){
			errors.addErrorString("删除活动失败");
			return errors.showErrorPage(model);
		}
		adminLogMng.operating(request, "删除活动", "activity.id=" + id);
		return "redirect:v_list.do";
	}
	private WebErrors validateId(HttpServletRequest request, Integer id) {
		WebErrors errors = WebErrors.create(request);
		if(errors.ifNull(id, "id")){
			return errors;
		}
		return errors;
	}
	@Autowired
	private ActivityMng activityMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
}
