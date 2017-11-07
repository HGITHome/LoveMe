package com.dgut.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dgut.common.page.SimplePage.cpn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.Admin;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Activity;
import com.dgut.member.entity.LoverActivity;
import com.dgut.member.entity.LoverActivityRecord;
import com.dgut.member.entity.Lovership;
import com.dgut.member.manager.ActivityMng;
import com.dgut.member.manager.LoverActivityMng;
import com.dgut.member.manager.LoverShipMng;

@Controller
@RequestMapping("member/ship")
public class LovershipAct {
	
	@RequestMapping("v_list.do")
	public String loverList(HttpServletRequest request,HttpServletResponse response,String queryUsername,String queryStatus,Integer pageNo,ModelMap model){
		
		Pagination pagination = loverMng.getList(queryUsername, queryStatus, cpn(pageNo),CookieUtils.getPageSize(request));
		
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryStatus", queryStatus);
		model.addAttribute("pageNo",pageNo);
		model.addAttribute("pagination", pagination);
		return  "member/ship/list";
	}

	@RequestMapping("a_list.do")
	public String pairActivityList(HttpServletRequest request,HttpServletResponse response,ModelMap model ,String ownId, String pairUUID){
		Lovership lovership = loverMng.findByOwnId(Integer.parseInt(ownId));
		List<LoverActivity> loverActivityList = loverActivityMng.findByPairUUID(pairUUID);
		model.addAttribute("lovership", lovership);
		model.addAttribute("loverActivityList", loverActivityList);
		model.addAttribute("ownId", ownId);
		model.addAttribute("pairUUID", pairUUID);
		return "member/loverActivities/list";
	}
	
	@RequestMapping("v_add.do")
	public String ActivityList(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer pageNo,String ownId,String pairUUID){
		Pagination pagination = activityMng.getPage(null, null, null, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("ownId", ownId);
		model.addAttribute("pairUUID", pairUUID);
		model.addAttribute("pagination", pagination);
		return "member/loverActivities/add";
	}
	
	@RequestMapping("v_activityInfo.do")
	public String activityIf(HttpServletRequest request,HttpServletResponse response,ModelMap model,String activityId,String ownId,String pairUUID){
		Admin admin = CmsUtils.getAdmin(request);
		Activity activity = activityMng.findById(Integer.parseInt(activityId));
		model.addAttribute("activity", activity);
		model.addAttribute("admin", admin);
		model.addAttribute("ownId", ownId);
		model.addAttribute("pairUUID", pairUUID);
		return "member/loverActivities/info";
	}
	
	@RequestMapping("o_activityAdd.do")
	public String LoverActivityAdd(HttpServletRequest request,HttpServletResponse response,ModelMap model,String ownId, String pairUUID,String tableValues,String dayNumbers){
		loverActivityMng.saveLoverActivity(pairUUID,tableValues,dayNumbers);
		return "redirect:a_list.do?ownId="+ownId + "&pairUUID=" + pairUUID;
	}
	
	@RequestMapping("o_delete.do")
	public String deleteLoverActivity(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id,String ownId, String pairUUID){
		WebErrors errors = WebErrors.create(request);
		LoverActivity activity = loverActivityMng.deleteById(Integer.parseInt(id));
		if(activity == null){
			errors.addErrorString("删除失败!");
			return errors.showErrorPage(model);
		}
		return "redirect:a_list.do?ownId="+ownId + "&pairUUID=" + pairUUID;
	}
	
	@RequestMapping("o_info.do")
	public String loverActivityRecord(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer activityId,Integer dayNumber,String pairUUID,Integer ownId){
		Activity activity = activityMng.findById(activityId);
		LoverActivity loverActivity = loverActivityMng.findByDayAndPairUUID(dayNumber, pairUUID);
		List<LoverActivityRecord> recordPictures = loverActivity.getRecords();
		model.addAttribute("activity", activity);
		model.addAttribute("recordPictures", recordPictures);
		model.addAttribute("pairUUID", pairUUID);
		model.addAttribute("ownId", ownId);
		return "member/loverActivities/recordInfo";
	}
	@Autowired
	private LoverShipMng loverMng;
	
	@Autowired
	private LoverActivityMng loverActivityMng;
	
	@Autowired
	private ActivityMng activityMng;
}
